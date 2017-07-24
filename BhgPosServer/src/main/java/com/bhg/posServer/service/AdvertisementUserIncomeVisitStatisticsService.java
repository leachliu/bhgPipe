package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUserIncomeVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementUserIncomeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserIncomeStatisticVo;

@Repository()
public class AdvertisementUserIncomeVisitStatisticsService {

	@Autowired
	private AdvertisementUserIncomeVisitStatisticsDao dao;

	@Transactional
	public void addList(List<AdvertisementUserIncomeStatisticVo> list) {
		for (AdvertisementUserIncomeStatisticVo advertisementUserIncomeStatisticVo : list) {
			AdvertisementUserIncomeVisitStatistic educationPo = dao.getAdvertisementUserIncomeVisitStatisticByVo(advertisementUserIncomeStatisticVo);
			if (educationPo != null) {
				educationPo.setClicks(educationPo.getClicks() + advertisementUserIncomeStatisticVo.getClicks());
				educationPo.setTotalVisit(advertisementUserIncomeStatisticVo.getUserIds().size() + educationPo.getTotalVisit());
			} else {
				dao.create(advertisementUserIncomeStatisticVo.toPo());
			}

		}
	}
}
