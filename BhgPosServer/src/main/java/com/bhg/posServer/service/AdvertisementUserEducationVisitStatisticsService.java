package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUserEducationVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementUserEducationVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserEducationStatisticVo;

@Service
public class AdvertisementUserEducationVisitStatisticsService{
	
	@Autowired 
	private AdvertisementUserEducationVisitStatisticsDao dao;
	
	@Transactional
	public void addList(List<AdvertisementUserEducationStatisticVo> list) {
		for (AdvertisementUserEducationStatisticVo advertisementUserEducationStatisticVo : list) {
			AdvertisementUserEducationVisitStatistic educationPo = dao.getAdvertisementUserEducationVisitStatisticByVo(advertisementUserEducationStatisticVo);
			if(educationPo != null){
				educationPo.setClicks(educationPo.getClicks()+advertisementUserEducationStatisticVo.getClicks());
				educationPo.setTotalVisit(advertisementUserEducationStatisticVo.getUserIds().size()+educationPo.getTotalVisit());
			}else{
				dao.create(advertisementUserEducationStatisticVo.toPo());
			}
			
		}
	}
}
