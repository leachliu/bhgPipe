package com.bhg.posServer.service.test;
//package com.wxmovie.advertisement.service.test;
//
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import com.wxmovie.advertisement.utils.Constants;
//import com.wxmovie.advertisement.utils.DateUtil;
//
//import org.hibernate.cfg.Configuration;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.wxmovie.advertisement.dao.AdvertisementAreaVisitStatisticsDao;
//import com.wxmovie.advertisement.dao.AdvertisementMaterialAreaVisitStatisticsDao;
//import com.wxmovie.advertisement.po.AdvertisementAreaVisitStatistic;
//import com.wxmovie.advertisement.po.AdvertisementMaterialAreaStatistic;
//import com.wxmovie.advertisement.redis.RedisUtil;
//import com.wxmovie.advertisement.service.AdvertisementMaterialAreaStatisticsService;
//import com.wxmovie.advertisement.service.RedisToDbService;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:applicationContext.xml"})
//public class RedisToDbServiceTest {
//
//    @Autowired
//	AdvertisementAreaVisitStatisticsDao dao;
//    
//	@Test
//	@Transactional()
//	@Rollback(false)
//	public void testStatistic() {
//		AdvertisementAreaVisitStatistic ad = new AdvertisementAreaVisitStatistic();
//		ad.setAdvertisementUuid(411);
//		ad.setAreaCode("240");
//		ad.setTime(DateUtil.getDate("2015-08-10 10:00:00"));
//		dao.getAdvertisementAreaVisitStatistic(ad);
//	}
//
////    @Autowired
////	private RedisToDbService redisToDbService;
////	
////    @Test
////    public void addAdvertisementDataToRedis(){
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_PV,"20150826151500#1","50");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_PV,"20150826151500#2","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_PV,"20150826152000#2","150");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CLICK,"20150826152000#1","250");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CLICK,"20150826152000#2","200");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_UV + "#"+"20150826151500#1","1");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_UV + "#"+"20150826151500#1","2");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_UV + "#"+"20150826151500#3","2");
////    }
////
////	@Test
////	@Transactional()
////	@Rollback(false)
////	public void testStatistic() {
////        redisToDbService.addAdvertisementPvAndClickToDb();
////        redisToDbService.addAdvertisementUvToDb();
////	}
////
////    @Test
////    public void addMaterialDataToRedis(){
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_PV,"20150826151500#1","50");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_PV,"20150826151500#2","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_PV,"20150826152000#2","150");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CLICK,"20150826152000#1","250");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CLICK,"20150826152000#2","200");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_UV + "#"+"20150826151500#1","1");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_UV + "#"+"20150826151500#1","2");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_UV + "#"+"20150826151500#3","2");
////    }
////
////    @Test
////    @Transactional()
////    @Rollback(false)
////    public void testMaterialStatistic() {
////        redisToDbService.addMaterialStaticsToDb();
////    }
////
////    @Test
////    public void addMaterialAreaDataToRedis(){
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CITY_PV,"20150826151500#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CITY_PV,"20150826151500#2#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CITY_PV,"20150826152000#2#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CITY_CLICK,"20150826152000#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_MATERIAL_CITY_CLICK,"20150826152000#2#1","100");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_CITY_UV + "#"+"20150826151500#1#1","1");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_CITY_UV + "#"+"20150826151500#1#1","2");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_MATERIAL_CITY_UV + "#"+"20150826151500#3#3","1");
////    }
////
////    @Test
////    @Transactional()
////    @Rollback(false)
////    public void testMaterialAreaStatistic() {
////        redisToDbService.addMaterialAreaStaticsToDb();
////    }
////    
////    
////    @Test
////    public void addAdvertisementAreaDataToRedis(){
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV,"20150826151500#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV,"20150826151500#2#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_PV,"20150826152000#2#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_CLICK,"20150826152000#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_CITY_CLICK,"20150826152000#2#1","100");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_CITY_UV + "#"+"20150826151500#1#1","1");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_CITY_UV + "#"+"20150826151500#1#1","2");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_CITY_UV + "#"+"20150826151500#3#3","1");
////    }
////
////    @Test
////    @Transactional()
////    @Rollback(false)
////    public void testAdvertisementAreaStatistic() {
////        redisToDbService.addAreaStaticsToDb();
////    }
////    
////    
////    @Test
////    public void addAdvertisementCinemaMovieDataToRedis(){
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV,"20150826151500#1#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV,"20150826151500#2#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_PV,"20150826152000#2#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK,"20150826152000#1#1#1","100");
////        RedisUtil.getInstance().hSet(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_CLICK,"20150826152000#2#1#1","100");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#"+"20150826151500#1#1#1","1");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#"+"20150826151500#1#1#1","2");
////        RedisUtil.getInstance().sAdd(Constants.REDIS_KEY_ADVERTISEMENT_MOVIE_CINEMA_UV + "#"+"20150826151500#3#3#1","1");
////    }
////
////    @Test
////    @Transactional()
////    @Rollback(false)
////    public void testAdvertisementCinemaMovieStatistic() {
////        redisToDbService.addCinemaMovieStaticsToDb();
////    }
////    
////    @Test
////    @Transactional()
////    @Rollback(false)
////    public void test() {
////    	redisToDbService.addAdvertisementPvAndClickToDb();
////		redisToDbService.addAdvertisementUvToDb();
////		redisToDbService.addAreaStaticsToDb();
////		redisToDbService.addCinemaMovieStaticsToDb();
////		redisToDbService.addMaterialAreaStaticsToDb();
////		redisToDbService.addMaterialStaticsToDb();
////    }
//
//
//}
