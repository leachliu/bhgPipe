package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementUserIncomeVisitStatistic;
import com.bhg.posServer.vo.AdvertisementUserIncomeStatisticVo;

@Repository()
public class AdvertisementUserIncomeVisitStatisticsDao extends BaseDao<AdvertisementUserIncomeVisitStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementUserIncomeVisitStatistic getAdvertisementUserIncomeVisitStatisticByVo(AdvertisementUserIncomeStatisticVo advertisementUserIncomeStatisticVo) {
		String hql = "from AdvertisementUserIncomeVisitStatistic a where a.time = :time and advertisementUuid = :advertisementUuid " 
	       + "and a.income = :income ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementUserIncomeStatisticVo.getStatisticsTime());
		query.setParameter("advertisementUuid", advertisementUserIncomeStatisticVo.getAdvertisementUuid());
		query.setParameter("income", advertisementUserIncomeStatisticVo.getIncome());
		query.setMaxResults(1);
		List<AdvertisementUserIncomeVisitStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
