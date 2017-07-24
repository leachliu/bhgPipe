package com.bhg.posServer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhg.posServer.po.AdvertisementAreaVisitStatistic;
import com.bhg.posServer.po.AdvertisementCinemaMovieStatistic;
import com.bhg.posServer.po.AdvertisementMaterialAreaStatistic;
import com.bhg.posServer.po.AdvertisementMaterialVisitStatistic;
import com.bhg.posServer.po.AdvertisementUniqueVisitorStatistic;
import com.bhg.posServer.po.AdvertisementVisitStatistic;
import com.bhg.posServer.po.AdvertisementVisitStatisticFrame;
import com.bhg.posServer.redis.RedisUtil;
import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.Constants;
import com.bhg.posServer.utils.DateUtil;

/**
 * Created by xiaoai on 2015/8/26.
 */
@Service
public class RedisToDbService {

	private Logger log = LoggerFactory.getLogger(RedisToDbService.class);

	@Autowired
	private AdvertisementVisitStatisticsService advertisementVisitStatisticsService;

	@Autowired
	private AdvertisementUniqueVisitorStatisticsService advertisementUniqueVisitorStatisticsService;

	@Autowired
	private AdvertisementMaterialStatisticsService advertisementMaterialStatisticsService;

	@Autowired
	private AdvertisementMaterialAreaStatisticsService materialAreaStatisticsService;

	@Autowired
	private AdvertisementAreaVisitStatisticsService advertisementAreaVisitStatisticsService;

	@Autowired
	private AdvertisementCinemaMovieStatisticService advertisementCinemaMovieStatisticService;

	public void addAdvertisementPvAndClickToDb() {
		try {
			Map<String, AdvertisementVisitStatistic> map = new HashMap<String, AdvertisementVisitStatistic>();
			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_PV);

			log.info("AdvertisementPvAndClick get pv from Redis and pvKeys size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);

				int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_PV, key));
				AdvertisementVisitStatistic advertisementVisitStatistic = new AdvertisementVisitStatistic();
				advertisementVisitStatistic.setTime(time);
				advertisementVisitStatistic.setAdvertisementUuid(advertisementUuid);
				advertisementVisitStatistic.setPageView(pageView);
				map.put(key, advertisementVisitStatistic);
				RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_PV, key);
				count += pageView;

			}
			log.info("AdvertisementPvAndClick finish Strore pv to map,and  pvSize = " + count);

			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_CLICK);
			log.info("AdvertisementPvAndClick get click from Redis and clickKeys size = " + clickKeys.size());
			count = 0;
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);

				int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_CLICK, key));
				AdvertisementVisitStatistic advertisementVisitStatistic = map.get(key);
				if (advertisementVisitStatistic != null) {
					advertisementVisitStatistic.setClicks(click);
				} else {
					advertisementVisitStatistic = new AdvertisementVisitStatistic();
					advertisementVisitStatistic.setTime(time);
					advertisementVisitStatistic.setAdvertisementUuid(advertisementUuid);
					advertisementVisitStatistic.setClicks(click);
					map.put(key, advertisementVisitStatistic);
				}
				count += click;
				RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_CLICK, key);
			}

			log.info("AdvertisementPvAndClick finish Strore click to map,and  clickSize = " + count);

			log.info("AdvertisementPvAndClick to store pv ,click to db process ...list size = " + map.values().size());
			advertisementVisitStatisticsService.addList(map.values());
			log.info("AdvertisementPvAndClick to finish store pv ,click to db process ... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public void addAdvertisementPvAndClickToFrameDb() {
		try {
			Map<String, AdvertisementVisitStatisticFrame> map = new HashMap<String, AdvertisementVisitStatisticFrame>();

			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_PV_FRAME);

			log.info("AdvertisementPvAndClickFrame get pv from Redis and pvKeys size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);

				int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_PV_FRAME, key));
				int frame = BoltUtil.getFrame(key);
				AdvertisementVisitStatisticFrame advertisementVisitStatistic = new AdvertisementVisitStatisticFrame();
				advertisementVisitStatistic.setTime(time);
				advertisementVisitStatistic.setAdvertisementUuid(advertisementUuid);
				advertisementVisitStatistic.setPageView(pageView);
				advertisementVisitStatistic.setFrame(frame);
				map.put(key, advertisementVisitStatistic);
				RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_PV_FRAME, key);
				count += pageView;

			}
			log.info("AdvertisementPvAndClick finish Strore pv to map,and  pvSize = " + count);

			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_CLICK_FRAME);
			log.info("AdvertisementPvAndClick get click from Redis and clickKeys size = " + clickKeys.size());
			count = 0;
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);

				int frame = BoltUtil.getFrame(key);
				int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_CLICK_FRAME, key));
				AdvertisementVisitStatisticFrame advertisementVisitStatistic = map.get(key);
				if (advertisementVisitStatistic != null) {
					advertisementVisitStatistic.setClicks(click);
				} else {
					advertisementVisitStatistic = new AdvertisementVisitStatisticFrame();
					advertisementVisitStatistic.setTime(time);
					advertisementVisitStatistic.setAdvertisementUuid(advertisementUuid);
					advertisementVisitStatistic.setClicks(click);
					advertisementVisitStatistic.setFrame(frame);
					map.put(key, advertisementVisitStatistic);
				}
				count += click;
				RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_CLICK_FRAME, key);
			}

			log.info("AdvertisementPvAndClick finish Strore click to map,and  clickSize = " + count);

			log.info("AdvertisementPvAndClick to store pv ,click to db process ...list size = " + map.values().size());
			advertisementVisitStatisticsService.addFrameList(map.values());
			log.info("AdvertisementPvAndClick to finish store pv ,click to db process ... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public void addAdvertisementUvToDb() {
		try {
			List<AdvertisementUniqueVisitorStatistic> uvPeriodList = new ArrayList<AdvertisementUniqueVisitorStatistic>();

			Set<String> keys = RedisUtil.getInstance().keys(Constants.REDIS_KEY_ADVERTISEMENT_UV + "#*");
			log.info("addAdvertisementUvToDb begin to get UV from redis and  keys size = " + keys.size());
			long count = 0;
			for (String key : keys) {
				String[] parts = key.split("#");

				Date uvDate = DateUtil.getRedisDate(parts[1]);
				Date currentTime = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				if (uvDate.before(currentTime)) {
					long size = RedisUtil.getInstance().getSetCount(key);
					long advertisementUuid = Long.parseLong(parts[2]);
					AdvertisementUniqueVisitorStatistic advertisementUniqueVisitorStatistic = new AdvertisementUniqueVisitorStatistic();
					advertisementUniqueVisitorStatistic.setTime(uvDate);
					advertisementUniqueVisitorStatistic.setUserView(size);
					advertisementUniqueVisitorStatistic.setAdvertisementUuid(advertisementUuid);
					uvPeriodList.add(advertisementUniqueVisitorStatistic);
					RedisUtil.getInstance().del(key);
					count += size;
				}
			}
			log.info("addAdvertisementUvToDb finish store UV to map and uv count = " + count);
			log.info("addAdvertisementUvToDb to store uv to db ...list size = " + uvPeriodList.size());
			advertisementUniqueVisitorStatisticsService.addList(uvPeriodList);
			log.info("addAdvertisementUvToDb to finish store uv to db process ... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}

	}

	public void addMaterialStaticsToDb() {
		try {
			Map<String, AdvertisementMaterialVisitStatistic> map = new HashMap<String, AdvertisementMaterialVisitStatistic>();
			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_MATERIAL_PV);
			log.info("addMaterialStaticsToDb get pv from Redis and pvKeys size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				String materialUUID = parts[1];
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);

				if (time.before(currentTimePoint)) {
					int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_MATERIAL_PV, key));
					AdvertisementMaterialVisitStatistic materialVisitStatistic = new AdvertisementMaterialVisitStatistic();
					materialVisitStatistic.setTime(time);
					materialVisitStatistic.setMaterialUUID(materialUUID);
					materialVisitStatistic.setPageViews(pageView);
					map.put(key, materialVisitStatistic);
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_MATERIAL_PV, key);
					count += pageView;
				}
			}
			log.info("addMaterialStaticsToDb finish Strore uv to map,and  pvSize = " + count);

			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_MATERIAL_CLICK);
			log.info("addMaterialStaticsToDb get click from Redis and clickKeys size = " + clickKeys.size());
			count = 0;
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				String materialUUID = parts[1];
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);

				if (time.before(currentTimePoint)) {
					int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_MATERIAL_CLICK, key));
					AdvertisementMaterialVisitStatistic materialVisitStatistic = map.get(key);
					if (materialVisitStatistic != null) {
						materialVisitStatistic.setClicks(click);
					} else {
						materialVisitStatistic = new AdvertisementMaterialVisitStatistic();
						materialVisitStatistic.setTime(time);
						materialVisitStatistic.setMaterialUUID(materialUUID);
						materialVisitStatistic.setClicks(click);
						map.put(key, materialVisitStatistic);
					}
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_MATERIAL_CLICK, key);
					count += click;
				}
			}
			log.info("addMaterialStaticsToDb finish Strore click to map,and  clickSize = " + count);

			Set<String> uvKeys = RedisUtil.getInstance().keys(Constants.REDIS_KEY_MATERIAL_UV + "#*");
			log.info("addMaterialStaticsToDb get uv from Redis and uvKeys size = " + uvKeys.size());
			count = 0;
			for (String key : uvKeys) {
				String[] parts = key.split("#");

				Date uvDate = DateUtil.getRedisDate(parts[1]);
				Date currentTime = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				if (uvDate.before(currentTime)) {
					long size = RedisUtil.getInstance().getSetCount(key);
					String materialUUID = parts[2];
					String tempKey = DateUtil.dateToRedisString(uvDate) + "#" + materialUUID;
					AdvertisementMaterialVisitStatistic materialVisitStatistic = map.get(tempKey);

					if (materialVisitStatistic != null) {
						materialVisitStatistic.setUserView(size);
					} else {
						materialVisitStatistic = new AdvertisementMaterialVisitStatistic();
						materialVisitStatistic.setTime(uvDate);
						materialVisitStatistic.setMaterialUUID(materialUUID);
						materialVisitStatistic.setUserView(size);
						map.put(key, materialVisitStatistic);
					}
					RedisUtil.getInstance().del(key);
					count += size;
				}
			}
			log.info("addMaterialStaticsToDb store uv to map and  uvCount = " + count);
			log.info("addMaterialStaticsToDb begin db process ...list size = " + map.values().size());
			advertisementMaterialStatisticsService.saveMaterialStatistics(map.values());
			log.info("addMaterialStaticsToDb finish db process ...");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public void addMaterialAreaStaticsToDb() {
		try {
			Map<String, AdvertisementMaterialAreaStatistic> map = new HashMap<String, AdvertisementMaterialAreaStatistic>();

			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_MATERIAL_CITY_PV);
			log.info("addMaterialAreaStaticsToDb get pv from Redis and pvKeys size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				String materialUUID = parts[1];
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String city = parts[2];

				if (time.before(currentTimePoint)) {
					int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_MATERIAL_CITY_PV, key));
					AdvertisementMaterialAreaStatistic materialVisitAreaStatistic = new AdvertisementMaterialAreaStatistic();
					materialVisitAreaStatistic.setTime(time);
					materialVisitAreaStatistic.setMaterialUuid(materialUUID);
					materialVisitAreaStatistic.setPageView(pageView);
					materialVisitAreaStatistic.setAreaCode(city);
					map.put(key, materialVisitAreaStatistic);
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_MATERIAL_CITY_PV, key);
					count += pageView;
				}

			}
			log.info("addMaterialAreaStaticsToDb finish Strore uv to map,and  pvSize = " + count);

			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_MATERIAL_CITY_CLICK);
			count = 0;
			log.info("addMaterialAreaStaticsToDb get click from Redis and clickKeys size = " + clickKeys.size());
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				String materialUUID = parts[1];
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String city = parts[2];

				if (time.before(currentTimePoint)) {
					int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_MATERIAL_CITY_CLICK, key));
					AdvertisementMaterialAreaStatistic materialVisitAreaStatistic = map.get(key);
					if (materialVisitAreaStatistic != null) {
						materialVisitAreaStatistic.setClicks(click);
					} else {
						materialVisitAreaStatistic = new AdvertisementMaterialAreaStatistic();
						materialVisitAreaStatistic.setTime(time);
						materialVisitAreaStatistic.setMaterialUuid(materialUUID);
						materialVisitAreaStatistic.setAreaCode(city);
						materialVisitAreaStatistic.setClicks(click);
						map.put(key, materialVisitAreaStatistic);
					}
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_MATERIAL_CITY_CLICK, key);
				}
			}
			log.info("addMaterialAreaStaticsToDb finish Strore click to map,and  clickSize = " + count);

			Set<String> uvKeys = RedisUtil.getInstance().keys(Constants.REDIS_KEY_MATERIAL_CITY_UV + "#*");
			log.info("addMaterialAreaStaticsToDb get uv from Redis and uvKeys size = " + uvKeys.size());
			count = 0;
			for (String key : uvKeys) {
				String[] parts = key.split("#");

				Date uvDate = DateUtil.getRedisDate(parts[1]);
				Date currentTime = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				if (uvDate.before(currentTime)) {
					long size = RedisUtil.getInstance().getSetCount(key);
					String materialUUID = parts[2];
					String city = parts[3];
					String tempKey = DateUtil.dateToRedisString(uvDate) + "#" + materialUUID + "#" + city;
					AdvertisementMaterialAreaStatistic materialAreaVisitStatistic = map.get(tempKey);

					if (materialAreaVisitStatistic != null) {
						materialAreaVisitStatistic.setTotalVisit(size);
					} else {
						materialAreaVisitStatistic = new AdvertisementMaterialAreaStatistic();
						materialAreaVisitStatistic.setTime(uvDate);
						materialAreaVisitStatistic.setMaterialUuid(materialUUID);
						materialAreaVisitStatistic.setAreaCode(city);
						materialAreaVisitStatistic.setTotalVisit(size);
						map.put(key, materialAreaVisitStatistic);
					}
					RedisUtil.getInstance().del(key);
					count += size;
				}
			}
			log.info("addMaterialAreaStaticsToDb store uv to map and  uvCount = " + count);

			log.info("addMaterialAreaStaticsToDb begin db process...list size = " + map.values().size());
			materialAreaStatisticsService.saveMaterialAreaStatistics(map.values());
			log.info("addMaterialAreaStaticsToDb finish db process... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public void addAreaStaticsToDb() {

		try {
			Map<String, AdvertisementAreaVisitStatistic> map = new HashMap<String, AdvertisementAreaVisitStatistic>();

			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV);
			log.info("addAreaStaticsToDb get pv from Redis and size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String city = parts[2];

				if (time.before(currentTimePoint)) {
					int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV, key));
					AdvertisementAreaVisitStatistic advertisementMaterialAreaStatistic = new AdvertisementAreaVisitStatistic();
					advertisementMaterialAreaStatistic.setTime(time);
					advertisementMaterialAreaStatistic.setAdvertisementUuid(advertisementUuid);
					advertisementMaterialAreaStatistic.setPageView(pageView);
					advertisementMaterialAreaStatistic.setAreaCode(city);
					map.put(key, advertisementMaterialAreaStatistic);
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV, key);
					count += pageView;
				}
			}
			log.info("addAreaStaticsToDb finish Strore pv to map,and  pvSize = " + count);
			count = 0;
			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_CITY_CLICK);
			log.info("addAreaStaticsToDb get click from Redis and clickKeys size = " + clickKeys.size());
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String city = parts[2];

				if (time.before(currentTimePoint)) {
					int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_CLICK, key));
					AdvertisementAreaVisitStatistic advertisementMaterialAreaStatistic = map.get(key);
					if (advertisementMaterialAreaStatistic != null) {
						advertisementMaterialAreaStatistic.setClicks(click);
					} else {
						advertisementMaterialAreaStatistic = new AdvertisementAreaVisitStatistic();
						advertisementMaterialAreaStatistic.setTime(time);
						advertisementMaterialAreaStatistic.setAdvertisementUuid(advertisementUuid);
						advertisementMaterialAreaStatistic.setAreaCode(city);
						advertisementMaterialAreaStatistic.setClicks(click);
						map.put(key, advertisementMaterialAreaStatistic);
					}
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_CITY_CLICK, key);
				}
			}
			log.info("addAreaStaticsToDb finish Strore click to map,and clickSize = " + count);

			log.info("begin to get UV from redis...");
			Set<String> uvKeys = RedisUtil.getInstance().keys(Constants.REDIS_KEY_ADVERTISEMENT_CITY_UV + "#*");
			log.info("addAreaStaticsToDb get uv from Redis and uvKeys size = " + uvKeys.size());
			count = 0;
			for (String key : uvKeys) {
				String[] parts = key.split("#");
				Date uvDate = DateUtil.getRedisDate(parts[1]);
				Date currentTime = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				if (uvDate.before(currentTime)) {
					long size = RedisUtil.getInstance().getSetCount(key);
					long advertisementUuid = Long.parseLong(parts[2]);
					String city = parts[3];
					String tempKey = DateUtil.dateToRedisString(uvDate) + "#" + advertisementUuid + "#" + city;
					AdvertisementAreaVisitStatistic advertisementMaterialAreaStatistic = map.get(tempKey);

					if (advertisementMaterialAreaStatistic != null) {
						advertisementMaterialAreaStatistic.setTotalVisit(size);
					} else {
						advertisementMaterialAreaStatistic = new AdvertisementAreaVisitStatistic();
						advertisementMaterialAreaStatistic.setTime(uvDate);
						advertisementMaterialAreaStatistic.setAdvertisementUuid(advertisementUuid);
						advertisementMaterialAreaStatistic.setAreaCode(city);
						advertisementMaterialAreaStatistic.setTotalVisit(size);
						map.put(key, advertisementMaterialAreaStatistic);
					}
					RedisUtil.getInstance().del(key);
					count += size;
				}
			}
			log.info("addAreaStaticsToDb finish store UV to map and uv count = " + count);

			log.info("addAreaStaticsToDb begin db process...list size = " + map.values().size());
			advertisementAreaVisitStatisticsService.saveAdvertisementAreaCodes(map.values());
			log.info("addAreaStaticsToDb finish db process... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public void addCinemaMovieStaticsToDb() {
		try {
			Map<String, AdvertisementCinemaMovieStatistic> map = new HashMap<String, AdvertisementCinemaMovieStatistic>();

			Set<String> pvKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV);
			log.info("addCinemaMovieStaticsToDb get pv from Redis and size = " + pvKeys.size());
			long count = 0;
			for (String key : pvKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String cinema = parts[2];
				String movie = parts[3];

				if (time.before(currentTimePoint)) {
					int pageView = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV, key));
					AdvertisementCinemaMovieStatistic advertisementCinemaMovieStatistic = new AdvertisementCinemaMovieStatistic();
					advertisementCinemaMovieStatistic.setTime(time);
					advertisementCinemaMovieStatistic.setAdvertisementUuid(advertisementUuid);
					advertisementCinemaMovieStatistic.setPageView(pageView);
					advertisementCinemaMovieStatistic.setMovieId(movie);
					advertisementCinemaMovieStatistic.setCinemaId(cinema);
					map.put(key, advertisementCinemaMovieStatistic);
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV, key);
					count += pageView;
				}
			}
			log.info("addCinemaMovieStaticsToDb finish Strore pv to map,and  pvSize = " + count);

			Set<String> clickKeys = RedisUtil.getInstance().hKeys(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK);
			count = 0;
			log.info("addCinemaMovieStaticsToDb get click from Redis and size = " + clickKeys.size());
			for (String key : clickKeys) {
				String[] parts = key.split("#");
				Date time = DateUtil.getRedisDate(parts[0]);
				long advertisementUuid = Long.parseLong(parts[1]);
				Date currentTimePoint = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);
				String cinema = parts[2];
				String movie = parts[3];

				if (time.before(currentTimePoint)) {
					int click = Integer.parseInt(RedisUtil.getInstance().hGet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK, key));
					AdvertisementCinemaMovieStatistic advertisementCinemaMovieStatistic = map.get(key);
					if (advertisementCinemaMovieStatistic != null) {
						advertisementCinemaMovieStatistic.setClicks(click);
					} else {
						advertisementCinemaMovieStatistic = new AdvertisementCinemaMovieStatistic();
						advertisementCinemaMovieStatistic.setTime(time);
						advertisementCinemaMovieStatistic.setAdvertisementUuid(advertisementUuid);
						advertisementCinemaMovieStatistic.setClicks(click);
						advertisementCinemaMovieStatistic.setMovieId(movie);
						advertisementCinemaMovieStatistic.setCinemaId(cinema);
						map.put(key, advertisementCinemaMovieStatistic);
					}
					count += click;
					RedisUtil.getInstance().hDel(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK, key);
				}
			}
			log.info("addCinemaMovieStaticsToDb finish Strore click to map,and  clickSize = " + count);

			Set<String> uvKeys = RedisUtil.getInstance().keys(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#*");
			log.info("addCinemaMovieStaticsToDb get uv from Redis and size = " + uvKeys.size());
			count = 0;
			for (String key : uvKeys) {
				String[] parts = key.split("#");
				Date uvDate = DateUtil.getRedisDate(parts[1]);
				Date currentTime = DateUtil.getTimePointWithHour(new Date(), StormConstant.STATISTIC_HOUR_INTERVAL);

				if (uvDate.before(currentTime)) {
					long size = RedisUtil.getInstance().getSetCount(key);
					long advertisementUuid = Long.parseLong(parts[2]);
					String cinema = parts[3];
					String movie = parts[4];
					String tempKey = key.replace(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#", "");
					AdvertisementCinemaMovieStatistic advertisementCinemaMovieStatistic = map.get(tempKey);

					if (advertisementCinemaMovieStatistic != null) {
						advertisementCinemaMovieStatistic.setTotalVisit(size);
					} else {
						advertisementCinemaMovieStatistic = new AdvertisementCinemaMovieStatistic();
						advertisementCinemaMovieStatistic.setTime(uvDate);
						advertisementCinemaMovieStatistic.setAdvertisementUuid(advertisementUuid);
						advertisementCinemaMovieStatistic.setMovieId(movie);
						advertisementCinemaMovieStatistic.setCinemaId(cinema);
						advertisementCinemaMovieStatistic.setTotalVisit(size);
						map.put(key, advertisementCinemaMovieStatistic);
					}
					RedisUtil.getInstance().del(key);
					count += size;
				}
			}
			log.info("addCinemaMovieStaticsToDb finish store UV to map and uv count = " + count);
			log.info("addCinemaMovieStaticsToDb begin db process...list size = " + map.values().size());
			advertisementCinemaMovieStatisticService.saveAdvertisementCinemaMovies(map.values());
			log.info("addCinemaMovieStaticsToDb finish db process ... ");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}
}
