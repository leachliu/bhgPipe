package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementVisitStatistic;


@Repository()
public class AdvertisementVisitStatisticsDao extends BaseDao<AdvertisementVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementVisitStatistic getAdvertisementVisitStatistic(AdvertisementVisitStatistic advertisementVisitStatistic){
		String hql = "from AdvertisementVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementVisitStatistic.getTime());
		query.setParameter("advertisementUuid", advertisementVisitStatistic.getAdvertisementUuid());
		query.setMaxResults(1);
		List<AdvertisementVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
