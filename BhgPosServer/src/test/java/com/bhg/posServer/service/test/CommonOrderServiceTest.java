package com.bhg.posServer.service.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.CommonOrderDao;
import com.bhg.posServer.po.CommonOrder;
import com.bhg.posServer.utils.DateUtil;

@Service
public class CommonOrderServiceTest {

	@Autowired
	private CommonOrderDao commonOrderDao;

	@Transactional
	public void updateCommonOrder(CommonOrder commonOrder) {
		commonOrderDao.update(commonOrder);
	}

	public static Date getMockDate() {
		int timeDiff = 120;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, timeDiff);
		return calendar.getTime();
	}

	@Transactional
	public void updateCommonOrderPaymentTime() throws IOException {
		int pageSize = 2000;
		int totalNum = commonOrderDao.findCommonOrdersTotalNumForTest();
		System.out.println(DateUtil.toString(new Date()) + " totalNum=" + totalNum);
		List<CommonOrder> commonOrders = new ArrayList<CommonOrder>();
		for (int start = 0; start < totalNum; start += pageSize) {
			List<CommonOrder> list = commonOrderDao.findCommonOrdersForTest(start, pageSize);
			commonOrders.addAll(list);
			System.out.println(DateUtil.toString(new Date()) + " after " + commonOrders.size());
		}

		List<String> lines = new ArrayList<String>();
		for (int i = 0; i < commonOrders.size(); i++) {
			CommonOrder commonOrder = commonOrders.get(i);
			commonOrder.setPaymentTime(getMockDate());
			updateCommonOrder(commonOrder);
			if (i % 50 == 0) {
				System.out.println(DateUtil.toString(new Date()) + " have finished read " + i + " item");
			}
			String line = commonOrder.getCommonOrderId() + ",";
			line += commonOrder.getOrderUserId() + ",";
			line += commonOrder.getMovieShow().getMovie().getMovieCode();
			lines.add(line);
		}
		System.out.println("begin to write orderInfo");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\xiaoai\\Desktop\\orderInfo.txt")));
		for (String line : lines) {
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
		System.out.println("finish write orderInfo");
	}
}
