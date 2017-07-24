package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUserAgeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserAgeStatisticVo;

@Repository()
public class AdvertisementUserAgeVisitStatisticsDao extends BaseDao<AdvertisementUserAgeVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementUserAgeVisitStatistic getAdvertisementUserAgeVisitStatisticByVo(AdvertisementUserAgeStatisticVo advertisementUserAgeStatisticVo) {
		String hql = "from AdvertisementUserAgeVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " + "and a.ageRange = :ageRange ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementUserAgeStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementUserAgeStatisticVo.getAdvertisementUuid());
		query.setParameter("ageRange", advertisementUserAgeStatisticVo.getAgeRange());
		query.setMaxResults(1);
		List<AdvertisementUserAgeVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);

	}
}
