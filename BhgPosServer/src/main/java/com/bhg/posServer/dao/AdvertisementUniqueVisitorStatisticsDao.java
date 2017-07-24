package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUniqueVisitorStatistic;


@Repository()
public class AdvertisementUniqueVisitorStatisticsDao extends BaseDao<AdvertisementUniqueVisitorStatistic> {

    @SuppressWarnings("unchecked")
	public AdvertisementUniqueVisitorStatistic getAdvertisementUniqueVisitorStatistic(AdvertisementUniqueVisitorStatistic advertisementUniqueVisitorStatistic) {
        String hql = "from AdvertisementUniqueVisitorStatistic a where a.time = :time and advertisementUuid = :advertisementUuid";
        Query query = entityManager.createQuery(hql);
        query.setParameter("time", advertisementUniqueVisitorStatistic.getTime());
        query.setParameter("advertisementUuid", advertisementUniqueVisitorStatistic.getAdvertisementUuid());
        query.setMaxResults(1);
        List<AdvertisementUniqueVisitorStatistic> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
