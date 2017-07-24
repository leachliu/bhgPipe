package com.bhg.posServer.storm.bolt;

import java.util.Date;

import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.Constants;
import com.bhg.posServer.utils.DateUtil;

import backtype.storm.tuple.Tuple;

public class AdvertisementCinemaMovieStatisticBolt extends AbstractVisitStatisticBolt {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean keyFieldExist(Tuple input) {
		return input.contains(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID) 
				&& input.contains(StormConstant.BOLT_FIELD_CINEMA_ID)
				&& input.contains(StormConstant.BOLT_FIELD_MOVIE_ID);
	}

	@Override
	protected String redisPVClickField(Tuple input) {
		return redisTime(input) + "#" + input.getLongByField(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID)
			+ "#" + input.getStringByField(StormConstant.BOLT_FIELD_CINEMA_ID)
			+ "#" + input.getStringByField(StormConstant.BOLT_FIELD_MOVIE_ID);
	}

	@Override
	protected String redisUVKey(Tuple input) {
		return Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#" + redisPVClickField(input);
	}

	@Override
	protected String redisTime(Tuple input) {
		Date nginxAccessTime = DateUtil.getNginxDate(input.getStringByField(StormConstant.BOLT_FIELD_NGINX_ACCESS_TIME));
		Date statisticTimePoint = DateUtil.getTimePointWithHour(nginxAccessTime, StormConstant.STATISTIC_HOUR_INTERVAL);
		return DateUtil.dateToRedisString(statisticTimePoint);
	}

	@Override
	protected String redisPVKey() {
		return Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV;
	}

	@Override
	protected String redisClickKey() {
		return Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK;
	}

	@Override
	protected int topologyTickTupleFreqSeconds() {
		return StormConstant.LIMIT_WRITE_REDIS_SECONDS_INTERVAL;
	}

}
