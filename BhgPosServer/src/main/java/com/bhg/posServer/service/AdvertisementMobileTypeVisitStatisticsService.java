package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementMobileTypeVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementMobileTypeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementMobileTypeStatisticVo;

@Service
public class AdvertisementMobileTypeVisitStatisticsService {
	
	@Autowired
	private AdvertisementMobileTypeVisitStatisticsDao dao;
	
	@Transactional
	public void addList(List<AdvertisementMobileTypeStatisticVo> list) {
		for (AdvertisementMobileTypeStatisticVo advertisementMobileTypeCodeStatisticVo : list) {
			AdvertisementMobileTypeVisitStatistic mobileTypePo = dao.getAdvertisementMobileTypeVisitStatisticByVo(advertisementMobileTypeCodeStatisticVo);
			if(mobileTypePo != null){
				mobileTypePo.setClicks(mobileTypePo.getClicks() + advertisementMobileTypeCodeStatisticVo.getClicks());
				mobileTypePo.setTotalVisit(mobileTypePo.getTotalVisit() + advertisementMobileTypeCodeStatisticVo.getUserIds().size());
				dao.update(mobileTypePo);
			}else{
				dao.create(advertisementMobileTypeCodeStatisticVo.toPo());
			}
			
		}
	}
}
