package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUserAgeVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementUserAgeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserAgeStatisticVo;

@Service
public class AdvertisementUserAgeVisitStatisticsService{
	
	@Autowired
	private AdvertisementUserAgeVisitStatisticsDao dao;
	
	@Transactional
	public void addList(List<AdvertisementUserAgeStatisticVo> list) {
		for(AdvertisementUserAgeStatisticVo advertisementUserAgeStatisticVo : list){
			AdvertisementUserAgeVisitStatistic advertisementUserAgeVisitStatistic = dao.getAdvertisementUserAgeVisitStatisticByVo(advertisementUserAgeStatisticVo);
			if(advertisementUserAgeVisitStatistic != null){
				advertisementUserAgeVisitStatistic.setClicks(advertisementUserAgeVisitStatistic.getClicks()+advertisementUserAgeStatisticVo.getClicks());
				advertisementUserAgeVisitStatistic.setTotalVisit(advertisementUserAgeVisitStatistic.getTotalVisit()+advertisementUserAgeStatisticVo.getUserIds().size());
				dao.update(advertisementUserAgeVisitStatistic);
			}else{
				dao.create(advertisementUserAgeStatisticVo.toPo());
			}
		}
		
	}
}
