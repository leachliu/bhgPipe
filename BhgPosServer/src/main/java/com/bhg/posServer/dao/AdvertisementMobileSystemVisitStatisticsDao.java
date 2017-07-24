package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementMobileSystemVisitStatistic;
import com.bhg.posServer.vo.AdvertisementMobileSystemStatisticVo;

@Repository()
public class AdvertisementMobileSystemVisitStatisticsDao extends BaseDao<AdvertisementMobileSystemVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementMobileSystemVisitStatistic getAdvertisementMobileSystemVisitStatisticByVo(AdvertisementMobileSystemStatisticVo advertisementMobileSystemStatisticVo){
		String hql = "from AdvertisementMobileSystemVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " +
				"and a.systemType = :systemType ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementMobileSystemStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementMobileSystemStatisticVo.getAdvertisementUuid());
		query.setParameter("systemType", advertisementMobileSystemStatisticVo.getSystemType());
		query.setMaxResults(1);
		List<AdvertisementMobileSystemVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
