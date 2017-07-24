package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementMaterialAreaStatistic;

@Repository()
public class AdvertisementMaterialAreaVisitStatisticsDao extends BaseDao<AdvertisementMaterialAreaStatistic> {

	@SuppressWarnings("unchecked")
	public AdvertisementMaterialAreaStatistic getAdvertisementAreaVisitStatistic(
			AdvertisementMaterialAreaStatistic advertisementMaterialAreaStatistic) {
		String hql = "from AdvertisementMaterialAreaStatistic a where a.time = :time and materialUuid = :materialUuid "
				+ "and a.areaCode = :areaCode ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementMaterialAreaStatistic.getTime());
		query.setParameter("materialUuid", advertisementMaterialAreaStatistic.getMaterialUuid());
		query.setParameter("areaCode", advertisementMaterialAreaStatistic.getAreaCode());
		query.setMaxResults(1);
		List<AdvertisementMaterialAreaStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

}
