package com.bhg.posServer.storm.bolt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.posServer.service.AreaCodeService;
import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.Constants;
import com.bhg.posServer.utils.SpringApplicationContext;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class NginxLogParserBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(NginxLogParserBolt.class);
	
	private static final int TEN_MINUTES = 10 * 60;
	
	private static final int SHOW_CITY_MARKER = -1;

    private OutputCollector collector = null;

    private AtomicLong index = new AtomicLong(0);
    
    private AtomicLong pv = new AtomicLong(0);
    
    private AtomicLong click = new AtomicLong(0);
    
    private Map<String, Integer> nameWepiaoNos = new HashMap<String, Integer>();
    
    private AreaCodeService areaCodeService;
    
    @Override
    @SuppressWarnings("rawtypes")
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        areaCodeService = SpringApplicationContext.getBean(AreaCodeService.class);
        loadNameWepiaoNos();
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID, StormConstant.BOLT_FIELD_USER_ID, 
        		StormConstant.BOLT_FIELD_MATERIAL_ID, StormConstant.BOLT_FIELD_CITY, StormConstant.BOLT_FIELD_CINEMA_ID, 
        		StormConstant.BOLT_FIELD_MOVIE_ID, StormConstant.BOLT_FIELD_NGINX_ACCESS_TIME, StormConstant.BOLT_FIELD_TYPE, 
        		StormConstant.BOLT_FIELD_USER_AGENT,StormConstant.BOLT_FIELD_FRAME));
    }

    @Override
    public void execute(Tuple input) {
        if (input.contains(StormConstant.SPOUT_FIELD_LINE)) {
            try {
                String message = input.getStringByField(StormConstant.SPOUT_FIELD_LINE).trim();
                Map<String, String> params = BoltUtil.getParams(message);
                try {
					long advertisementUuid = params.get(Constants.PARAM_ADVERTISEMENT_UUID) == null 
											? Long.parseLong(params.get(Constants.PARAM_ITEMID))
											: Long.parseLong(params.get(Constants.PARAM_ADVERTISEMENT_UUID));
					String time = BoltUtil.getNginxAccessTime(message);
	                String uuid = params.get(Constants.PARAM_UUID);
	                String frame = params.get(Constants.FRAME_ID);
	                String materialId = params.get(Constants.PARAM_MATERIAL_ID);
	                int city = getCity(params);
	                String cinemaId = StringUtils.isBlank(params.get(Constants.PARAM_CINEMA_ID)) ? "0" : params.get(Constants.PARAM_CINEMA_ID);
	                String movieId = StringUtils.isBlank(params.get(Constants.PARAM_MOVIE_ID)) ? "0" : params.get(Constants.PARAM_MOVIE_ID);
	                String userAgent = params.get(Constants.PARAM_USER_AGENT);
	                if(BoltUtil.isView(message)) {
	                	collector.emit(new Values(advertisementUuid, uuid, materialId, city, cinemaId, movieId, time, StormConstant.BOLT_TYPE_VIEW, userAgent, frame));
	                	pv.incrementAndGet();
	                } else if(BoltUtil.isClick(message)) {
	                	click.incrementAndGet();
	                	collector.emit(new Values(advertisementUuid, uuid, materialId, city, cinemaId, movieId, time, StormConstant.BOLT_TYPE_CLICK, userAgent, frame));
	                	collector.emit(StormConstant.BOLT_CLICK, new Values(advertisementUuid, uuid, materialId, city, cinemaId, movieId, time, 
	                			StormConstant.BOLT_TYPE_CLICK, userAgent, frame));
	                }
	                if (index.incrementAndGet() % 50000 == 0) {
	                	log.info(index + "-->Nginx Log Bolt time = " + time + ", advertisementId = " + advertisementUuid + ", uuid = " + uuid 
	                			+ ", materialId = " + materialId + " pv= " + pv.get() + " click " + click.get());
	                }
				} catch (NumberFormatException e) {
					log.warn(message.split(" ")[9]+" Not advertisementUuid..");
					log.warn(params+" params  content..");
				}
                collector.ack(input);
            } catch (Throwable e) {
            	log.error(e.getMessage(), e);
            	collector.fail(input);
            }
        }
        if(input.getSourceStreamId().equals(backtype.storm.Constants.SYSTEM_TICK_STREAM_ID)) {
        	loadNameWepiaoNos();
        }
    }
    
    private void loadNameWepiaoNos() {
    	nameWepiaoNos = areaCodeService.getNameWepiaoNos();
    }

	private int getCity(Map<String, String> params) {
		int city = 0;
		try {
			city = Integer.parseInt(params.get(Constants.PARAM_CITY));
			if(city == SHOW_CITY_MARKER) {
				city = nameWepiaoNos.get(params.get(Constants.PARAM_CITY_NAME));
			}
		} catch (Throwable e) {
		}
		return city;
	}

    @Override
    public void cleanup() {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
    	Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, TEN_MINUTES);
		return conf;
    }

}
