package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementUniqueVisitorStatisticsDao;
import com.bhg.posServer.po.AdvertisementUniqueVisitorStatistic;

@Service
public class AdvertisementUniqueVisitorStatisticsService {

    @Autowired
    AdvertisementUniqueVisitorStatisticsDao dao;

    @Transactional
    public void addList(Collection<AdvertisementUniqueVisitorStatistic> list) {
    	int i=0;
        for (AdvertisementUniqueVisitorStatistic advertisementUniqueVisitorStatistic : list) {
            dao.create(advertisementUniqueVisitorStatistic);
            if (++i % 500 == 0) {
				System.out.println("saveAdvertisementUniqueVisitorStatistic have process .." + i);
			}
        }
    }
}
