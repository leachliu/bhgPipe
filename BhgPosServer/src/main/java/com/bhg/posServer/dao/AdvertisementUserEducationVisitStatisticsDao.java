package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUserEducationVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserEducationStatisticVo;

@Repository()
public class AdvertisementUserEducationVisitStatisticsDao extends BaseDao<AdvertisementUserEducationVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementUserEducationVisitStatistic getAdvertisementUserEducationVisitStatisticByVo(AdvertisementUserEducationStatisticVo advertisementUserEducationStatisticVo){
		String hql = "from AdvertisementUserEducationVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " +
				"and a.education = :education ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementUserEducationStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementUserEducationStatisticVo.getAdvertisementUuid());
		query.setParameter("education", advertisementUserEducationStatisticVo.getEducation());
		query.setMaxResults(1);
		List<AdvertisementUserEducationVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
