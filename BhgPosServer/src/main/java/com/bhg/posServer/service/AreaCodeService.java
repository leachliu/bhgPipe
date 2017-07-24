package com.bhg.posServer.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AreaCodeDao;
import com.bhg.posServer.po.AreaCode;

@Service
public class AreaCodeService {
	
	private static Logger log = LoggerFactory.getLogger(AreaCodeService.class);

	@Autowired
	private AreaCodeDao areaCodeDao;
	
	@Transactional(readOnly = true)
	public Map<String, Integer> getNameWepiaoNos(){
		log.info("Start load area name and wepiaoNos...");
		Map<String, Integer> nameWepiaoNos = new HashMap<String, Integer>();
		for (AreaCode areaCode : areaCodeDao.findAreaCodes()) {
			nameWepiaoNos.put(areaCode.getName(), areaCode.getWepiaoNo());
		}
		log.info("Finished load area name and wepiaoNos. areaCode size = {}", nameWepiaoNos.size());
		return nameWepiaoNos;
	}
	
}
