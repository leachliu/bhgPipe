package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUserGenderVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserGenderStatisticVo;

@Repository()
public class AdvertisementUserGenderVisitStatisticsDao extends BaseDao<AdvertisementUserGenderVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementUserGenderVisitStatistic getAdvertisementUserGenderVisitStatisticByVo(AdvertisementUserGenderStatisticVo advertisementUserGenderStatisticVo) {
		String hql = "from AdvertisementUserGenderVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " 
	       + "and a.gender = :gender ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementUserGenderStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementUserGenderStatisticVo.getAdvertisementUuid());
		query.setParameter("gender", advertisementUserGenderStatisticVo.getGender());
		query.setMaxResults(1);
		List<AdvertisementUserGenderVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
