package com.bhg.posServer.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.CommonOrder;
import com.bhg.posServer.utils.DateUtil;

@Repository
@SuppressWarnings("unchecked")
public class CommonOrderDao extends BaseDao<CommonOrder> {

	public CommonOrder getCommonOrder(String commonOrderId){
		return entityManager.find(CommonOrder.class, commonOrderId);
	}
	
	public List<CommonOrder> findYesterdayCommonOrders(int start, int size) {
		String hsql = "from CommonOrder co where co.paymentTime between :startTime and :endTime ";
		Date startTime = DateUtil.getDate(DateUtil.getYesterday() + " 00:00:00");
		Date endTime = DateUtil.getDate(DateUtil.getYesterday() + " 23:59:59");
		Query query = entityManager.createQuery(hsql).setParameter("startTime", startTime).setParameter("endTime", endTime);
		query.setFirstResult(start);
		query.setMaxResults(size);
		return query.getResultList();
	}

	public int getYesterdayCommonOrdersTotalNum() {
		String hsql = "select count(co) from CommonOrder co where co.paymentTime between :startTime and :endTime ";
		Date startTime = DateUtil.getDate(DateUtil.getYesterday() + " 00:00:00");
		Date endTime = DateUtil.getDate(DateUtil.getYesterday() + " 23:59:59");
		Query query = entityManager.createQuery(hsql).setParameter("startTime", startTime).setParameter("endTime", endTime);
		query.setMaxResults(1);
		return ((Long) query.getSingleResult()).intValue();
	}

	public List<CommonOrder> findCommonOrdersForTest(int start, int size) {
		String hsql = "from CommonOrder co ";
		Query query = entityManager.createQuery(hsql);
		query.setFirstResult(start);
		query.setMaxResults(size);
		return query.getResultList();
	}
	
	public int findCommonOrdersTotalNumForTest() {
		String hsql = "select count(co) from CommonOrder co";
		Query query = entityManager.createQuery(hsql);
		query.setMaxResults(1);
		return ((Long) query.getSingleResult()).intValue();
	}


	
	public List<CommonOrder> findYesterdayCommonOrdersForTest(int start, int size) {
		String hsql = "from CommonOrder co where co.paymentTime between :startTime and :endTime ";
		Date startTime = DateUtil.getDate(DateUtil.getToday() + " 00:00:00");
		Date endTime = DateUtil.getDate(DateUtil.getToday() + " 23:59:59");
		Query query = entityManager.createQuery(hsql).setParameter("startTime", startTime).setParameter("endTime", endTime);
		query.setFirstResult(start);
		query.setMaxResults(size);
		return query.getResultList();
	}

	public int getYesterdayCommonOrdersTotalNumForTest() {
		String hsql = "select count(co) from CommonOrder co where co.paymentTime between :startTime and :endTime ";
		Date startTime = DateUtil.getDate(DateUtil.getToday() + " 00:00:00");
		Date endTime = DateUtil.getDate(DateUtil.getToday() + " 23:59:59");
		Query query = entityManager.createQuery(hsql).setParameter("startTime", startTime).setParameter("endTime", endTime);
		query.setMaxResults(1);
		return ((Long) query.getSingleResult()).intValue();
	}
}
