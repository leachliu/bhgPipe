package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementMobileTypeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementMobileTypeStatisticVo;

@Repository()
public class AdvertisementMobileTypeVisitStatisticsDao extends BaseDao<AdvertisementMobileTypeVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementMobileTypeVisitStatistic getAdvertisementMobileTypeVisitStatisticByVo(AdvertisementMobileTypeStatisticVo advertisementMobileTypeStatisticVo){
		String hql = "from AdvertisementMobileTypeVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " +
				"and a.mobileType = :mobileType ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementMobileTypeStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementMobileTypeStatisticVo.getAdvertisementUuid());
		query.setParameter("mobileType", advertisementMobileTypeStatisticVo.getMobileType());
		query.setMaxResults(1);
		List<AdvertisementMobileTypeVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
