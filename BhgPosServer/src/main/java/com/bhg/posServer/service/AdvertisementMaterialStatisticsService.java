package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementMaterialStatisticsDao;
import com.bhg.posServer.po.AdvertisementMaterialVisitStatistic;

@Service
public class AdvertisementMaterialStatisticsService {

    @Autowired
    private AdvertisementMaterialStatisticsDao dao;

    @Transactional
    public void saveMaterialStatistics(Collection<AdvertisementMaterialVisitStatistic> materialVisitStatistics) {
    	int i=0;
        for (AdvertisementMaterialVisitStatistic materialVisitStatistic : materialVisitStatistics) {    
            dao.create(materialVisitStatistic);
            if (++i % 500 == 0) {
				System.out.println("saveAdvertisementMaterialVisitStatistic have process .." + i);
			}
        }
    }
}
