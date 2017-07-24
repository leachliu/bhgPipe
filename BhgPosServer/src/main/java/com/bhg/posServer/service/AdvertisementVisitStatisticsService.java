package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementVisitStatisticsDao;
import com.bhg.posServer.dao.AdvertisementVisitStatisticsFrameDao;
import com.bhg.posServer.po.AdvertisementVisitStatistic;
import com.bhg.posServer.po.AdvertisementVisitStatisticFrame;

@Service
public class AdvertisementVisitStatisticsService {

    @Autowired
    AdvertisementVisitStatisticsDao dao;
    
    @Autowired
    AdvertisementVisitStatisticsFrameDao frameDao;

    @Transactional
    public void addList(Collection<AdvertisementVisitStatistic> list) {
    	int i = 0;
        for (AdvertisementVisitStatistic advertisementVisitStatisticTemp : list) {
        	 dao.create(advertisementVisitStatisticTemp);
        	 if (++i % 500 == 0) {
 				System.out.println("AdvertisementVisitStatistic have process .." + i);
 			}
        }
    }
    
    @Transactional
    public void addFrameList(Collection<AdvertisementVisitStatisticFrame> list) {
    	int i = 0;
        for (AdvertisementVisitStatisticFrame advertisementVisitStatisticTemp : list) {
        	frameDao.create(advertisementVisitStatisticTemp);
        	 if (++i % 500 == 0) {
 				System.out.println("AdvertisementVisitStatisticFrame have process .." + i);
 			}
        }
    }
}
