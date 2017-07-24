package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementAreaVisitStatistic;

@Repository()
public class AdvertisementAreaVisitStatisticsDao extends BaseDao<AdvertisementAreaVisitStatistic> {

	@SuppressWarnings("unchecked")
	public AdvertisementAreaVisitStatistic getAdvertisementAreaVisitStatistic(AdvertisementAreaVisitStatistic advertisementAreaVisitStatistic) {
		String hql = "from AdvertisementAreaVisitStatistic a where a.time = :time and a.advertisementUuid = :advertisementUuid " + "and a.areaCode = :areaCode ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementAreaVisitStatistic.getTime());
		query.setParameter("advertisementUuid", advertisementAreaVisitStatistic.getAdvertisementUuid());
		query.setParameter("areaCode", advertisementAreaVisitStatistic.getAreaCode());
		query.setMaxResults(1);
		List<AdvertisementAreaVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

}
