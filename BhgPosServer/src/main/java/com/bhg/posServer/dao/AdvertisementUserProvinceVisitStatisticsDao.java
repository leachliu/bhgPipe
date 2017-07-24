package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUserProvinceVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserProvinceStatisticVo;

@Repository()
public class AdvertisementUserProvinceVisitStatisticsDao extends BaseDao<AdvertisementUserProvinceVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementUserProvinceVisitStatistic getAdvertisementUserProvinceVisitStatisticByVo(AdvertisementUserProvinceStatisticVo advertisementUserProvinceStatisticVo) {
		String hql = "from AdvertisementUserProvinceVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " 
	       + "and a.province = :province ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementUserProvinceStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementUserProvinceStatisticVo.getAdvertisementUuid());
		query.setParameter("province", advertisementUserProvinceStatisticVo.getProvince());
		query.setMaxResults(1);
		List<AdvertisementUserProvinceVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
