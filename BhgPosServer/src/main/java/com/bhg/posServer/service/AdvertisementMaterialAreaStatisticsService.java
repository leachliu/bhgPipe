package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementMaterialAreaVisitStatisticsDao;
import com.bhg.posServer.po.AdvertisementMaterialAreaStatistic;

@Service
public class AdvertisementMaterialAreaStatisticsService {

    @Autowired
    private AdvertisementMaterialAreaVisitStatisticsDao dao;

    @Transactional
    public void saveMaterialAreaStatistics(Collection<AdvertisementMaterialAreaStatistic> materialAreaVisitStatistics) {
    	int i =0;
        for (AdvertisementMaterialAreaStatistic materialAreaVisitStatistic : materialAreaVisitStatistics) {
            dao.create(materialAreaVisitStatistic);
            if (++i % 500 == 0) {
				System.out.println("saveMaterialAreaStatistics have process .." + i);
			}
        }
    }
    
}
