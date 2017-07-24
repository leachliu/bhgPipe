package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUserGenderVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementUserGenderVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserGenderStatisticVo;

@Service
public class AdvertisementUserGenderVisitStatisticsService {

	@Autowired
	private AdvertisementUserGenderVisitStatisticsDao dao;

	@Transactional
	public void addList(List<AdvertisementUserGenderStatisticVo> list) {
		for (AdvertisementUserGenderStatisticVo advertisementUserGenderStatisticVo : list) {
			AdvertisementUserGenderVisitStatistic educationPo = dao.getAdvertisementUserGenderVisitStatisticByVo(advertisementUserGenderStatisticVo);
			if (educationPo != null) {
				educationPo.setClicks(educationPo.getClicks() + advertisementUserGenderStatisticVo.getClicks());
				educationPo.setTotalVisit(advertisementUserGenderStatisticVo.getUserIds().size() + educationPo.getTotalVisit());
			} else {
				dao.create(advertisementUserGenderStatisticVo.toPo());
			}

		}
	}

}
