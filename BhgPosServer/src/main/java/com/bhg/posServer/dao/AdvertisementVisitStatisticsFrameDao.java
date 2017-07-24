package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementVisitStatisticFrame;

@Repository()
public class AdvertisementVisitStatisticsFrameDao extends BaseDao<AdvertisementVisitStatisticFrame> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementVisitStatisticFrame getAdvertisementVisitStatistic(AdvertisementVisitStatisticFrame advertisementVisitStatistic){
		String hql = "from AdvertisementVisitStatisticFrame a where a.time = :time and advertisementUuid = :advertisementUuid";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementVisitStatistic.getTime());
		query.setParameter("advertisementUuid", advertisementVisitStatistic.getAdvertisementUuid());
		query.setMaxResults(1);
		List<AdvertisementVisitStatisticFrame> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
