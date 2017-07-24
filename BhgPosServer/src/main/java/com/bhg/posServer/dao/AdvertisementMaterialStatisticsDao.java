package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementMaterialVisitStatistic;
import com.bhg.posServer.vo.AdvertisementMaterialVisitStatisticVo;

@Repository()
@SuppressWarnings("unchecked")
public class AdvertisementMaterialStatisticsDao extends BaseDao<AdvertisementMaterialVisitStatistic> {
    public void updateAdvertisementMaterialVisitStatistic(AdvertisementMaterialVisitStatisticVo advertisementMaterialVisitStatisticVo) {
        AdvertisementMaterialVisitStatistic advertisementMaterialVisitStatistic = get(advertisementMaterialVisitStatisticVo.getMaterialUUID());
        if (advertisementMaterialVisitStatistic == null) {
            create(advertisementMaterialVisitStatisticVo.toPo());
        } else {
            advertisementMaterialVisitStatistic.setClicks(advertisementMaterialVisitStatistic.getClicks() + advertisementMaterialVisitStatisticVo.getClicks());
            advertisementMaterialVisitStatistic.setPageViews(advertisementMaterialVisitStatistic.getPageViews() + advertisementMaterialVisitStatisticVo.getPageViews());
            update(advertisementMaterialVisitStatistic);
        }
    }

	public AdvertisementMaterialVisitStatistic getAdvertisementMaterialVisitStatistic(AdvertisementMaterialVisitStatistic advertisementMaterialVisitStatistic) {
        String hql = "from AdvertisementMaterialVisitStatistic a where a.time = :time and a.materialUUID = :materialUUID ";
        Query query = entityManager.createQuery(hql);
        query.setParameter("time", advertisementMaterialVisitStatistic.getTime());
        query.setParameter("materialUUID", advertisementMaterialVisitStatistic.getMaterialUUID());
        query.setMaxResults(1);
        List<AdvertisementMaterialVisitStatistic> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
