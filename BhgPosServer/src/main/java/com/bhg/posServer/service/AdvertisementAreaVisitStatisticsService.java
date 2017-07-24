package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementAreaVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementAreaVisitStatistic;

@Service
public class AdvertisementAreaVisitStatisticsService {

	@Autowired
	private AdvertisementAreaVisitStatisticsDao dao;

	@Transactional
	public void saveAdvertisementAreaCodes(Collection<AdvertisementAreaVisitStatistic> list) {
		int i = 0;
		for (AdvertisementAreaVisitStatistic advertisementAreaVisitStatistic : list) {
			dao.create(advertisementAreaVisitStatistic);
			if (++i % 500 == 0) {
				System.out.println("saveAdvertisementAreaCodes have process .." + i);
			}
		}
	}
}
