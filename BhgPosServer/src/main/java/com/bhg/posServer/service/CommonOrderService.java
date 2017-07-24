package com.bhg.posServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.CommonOrderDao;
import com.bhg.posServer.po.CommonOrder;
import com.bhg.posServer.vo.CommonOrderVo;

@Service
public class CommonOrderService {

	@Autowired
	private CommonOrderDao commonOrderDao;

	public void updateCommonOrder(CommonOrderVo commonOrderVo) {
		CommonOrder commonOrder = commonOrderDao.getCommonOrder(commonOrderVo.getCommonOrderId());
		commonOrder.setAdvertisementUuid(commonOrderVo.getAdvertisementUuid());
		commonOrderDao.update(commonOrder);
	}

	public int getYesterdayCommonOrdersTotalNum() {
		return commonOrderDao.getYesterdayCommonOrdersTotalNum();
	}

	@Transactional
	public List<CommonOrderVo> findYesterdayCommonOrderVos(int start, int size) {
		List<CommonOrder> commonOrderList = commonOrderDao.findYesterdayCommonOrders(start, size);
		List<CommonOrderVo> commonOrderVoList = new ArrayList<CommonOrderVo>();
		for (CommonOrder commonOrder : commonOrderList) {
			CommonOrderVo commonOrderVo = new CommonOrderVo();
			commonOrderVo.setCommonOrderId(commonOrder.getCommonOrderId());
			commonOrderVo.setMovieCode(commonOrder.getMovieShow().getMovie().getMovieCode());
			commonOrderVo.setOrderUserId(commonOrder.getOrderUserId());
			commonOrderVo.setPaymentTime(commonOrder.getPaymentTime());
			commonOrderVoList.add(commonOrderVo);
		}
		return commonOrderVoList;
	}

	@Transactional
	public void updateCommonOrderList(List<CommonOrderVo> list) {
		for (CommonOrderVo commonOrderVo : list) {
			updateCommonOrder(commonOrderVo);
		}
	}

}
