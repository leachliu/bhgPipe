package com.bhg.posServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementMobileSystemVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementMobileSystemVisitStatistic;
import com.bhg.posServer.vo.AdvertisementMobileSystemStatisticVo;

@Service
public class AdvertisementMobileSystemVisitStatisticsService {

	@Autowired
	private AdvertisementMobileSystemVisitStatisticsDao dao;

	@Transactional
	public void addList(List<AdvertisementMobileSystemStatisticVo> list) {
		for (AdvertisementMobileSystemStatisticVo advertisementMobileSystemStatisticVo : list) {
			AdvertisementMobileSystemVisitStatistic advertisementMobileSystemVisitStatistic = dao.getAdvertisementMobileSystemVisitStatisticByVo(advertisementMobileSystemStatisticVo);
			if (advertisementMobileSystemVisitStatistic != null) {
				advertisementMobileSystemVisitStatistic.setClicks(advertisementMobileSystemVisitStatistic.getClicks() + advertisementMobileSystemStatisticVo.getClicks());
				advertisementMobileSystemVisitStatistic.setTotalVisit(advertisementMobileSystemVisitStatistic.getTotalVisit() + advertisementMobileSystemStatisticVo.getUserIds().size());
				dao.update(advertisementMobileSystemVisitStatistic);
			} else {
				dao.create(advertisementMobileSystemStatisticVo.toPo());
			}

		}
	}
}
