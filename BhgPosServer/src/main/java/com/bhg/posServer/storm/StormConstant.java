package com.bhg.posServer.storm;


public interface StormConstant {
	
	String SPOUT_FIELD_LINE = "line";
	
	String BOLT_FIELD_ADVERTISEMENT_UUID = "advertisementUuid";
	
	String BOLT_FIELD_NGINX_ACCESS_TIME = "nginxAccessTime";
	
	String BOLT_FIELD_USER_ID = "userId";
	
	String BOLT_FIELD_MATERIAL_ID = "materialId";
	
	String BOLT_FIELD_USER_AGENT = "userAgent";
	
	String BOLT_FIELD_CITY = "city";
	
	String BOLT_FIELD_TYPE = "type";
	
	String BOLT_FIELD_AGE = "age";
	
	String BOLT_FIELD_FRAME = "frame";
	
	String BOLT_FIELD_GENDER = "gender";
	
	String BOLT_FIELD_PROVINCE = "province";
	
	String BOLT_FIELD_INCOME = "revenue";
	
	String BOLT_FIELD_EDUCATION = "education";

    String BOLT_FIELD_CINEMA_ID = "cinemaId";

    String BOLT_FIELD_MOVIE_ID = "movieId";
	
	String BOLT_FIELD_USER_INFO_ITEM = "userInfoItem";
	
	int BOLT_TYPE_VIEW = 1;

	int BOLT_TYPE_CLICK = 2;
	
	String SPOUT_LOG_FROM_KAFKA = "logFromKafkaSpout";
	
	String BOLT_NGINX_LOG_PARSER = "nginxLogParserBolt";
	
	String BOLT_CLICK = "clickBolt";
	
	String BOLT_ADVERTISEMENT_STATISTIC = "advertisementStatisticBolt";
	
	String BOLT_ADVERTISEMENT_REDIS = "advertisementStatisticToRedis";
	
	String BOLT_ADVERTISEMENT_FRAME_STATISTIC = "advertisementStatisticFrameBolt";
	
	String BOLT_ADVERTISEMENT_CITY_STATISTIC = "advertisementCityStatisticBolt";
	
	String BOLT_ADVERTISEMENT_CINEMA_MOVIE_STATISTIC = "advertisementCinemaMovieStatisticBolt";
	
	String BOLT_MATERIAL_STATISTIC = "materialStatisticBolt";
	
	String BOLT_MATERIAL_CITY_STATISTIC = "materialCityStatisticBolt";
	
	String BOLT_DB = "DB_BOLT";
	
	//统计时间为5分钟
	int COMMIT_SECONDS_INTERVAL = 5 * 60;
	
	int COMMIT_MINUTE_INTERVAL = COMMIT_SECONDS_INTERVAL/60;
	
	int STATISTIC_HOUR_INTERVAL = 1;
	
	int LIMIT_WRITE_REDIS_SECONDS_INTERVAL = 1 * 60 ;

    int LIMIT_WRITE_AD_REDIS_SECONDS_INTERVAL = 5 ;
	
	String SEPARATOR = "|";
	
}
