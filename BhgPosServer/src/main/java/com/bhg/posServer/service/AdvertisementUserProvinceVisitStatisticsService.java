package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUserProvinceVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementUserProvinceVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserProvinceStatisticVo;

@Repository()
public class AdvertisementUserProvinceVisitStatisticsService {
	@Autowired
	private AdvertisementUserProvinceVisitStatisticsDao dao;

	@Transactional
	public void addList(List<AdvertisementUserProvinceStatisticVo> list) {
		for (AdvertisementUserProvinceStatisticVo advertisementUserProvinceStatisticVo : list) {
			AdvertisementUserProvinceVisitStatistic educationPo = dao.getAdvertisementUserProvinceVisitStatisticByVo(advertisementUserProvinceStatisticVo);
			if (educationPo != null) {
				educationPo.setClicks(educationPo.getClicks() + advertisementUserProvinceStatisticVo.getClicks());
				educationPo.setTotalVisit(advertisementUserProvinceStatisticVo.getUserIds().size() + educationPo.getTotalVisit());
			} else {
				dao.create(advertisementUserProvinceStatisticVo.toPo());
			}

		}
	}
}
