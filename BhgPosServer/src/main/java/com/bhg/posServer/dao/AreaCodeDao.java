package com.bhg.posServer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AreaCode;

@Repository
@SuppressWarnings("unchecked")
public class AreaCodeDao extends BaseDao<AreaCode> {
	
	public List<AreaCode> findAreaCodes(){
		return entityManager.createQuery("from AreaCode").getResultList();
	}

}
