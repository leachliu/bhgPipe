package com.bhg.posServer.storm.bolt.test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.bhg.posServer.service.test.CommonOrderServiceTest;
import com.bhg.posServer.utils.SpringApplicationContext;

public class CommonOrderBoltTest {
	public static Date getMockDate() {
		int timeDiff = 90;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, timeDiff);
		return calendar.getTime();
	}

	public static void updateCommonOrderPaymentTime() throws IOException {
		CommonOrderServiceTest commonOrderService = SpringApplicationContext.getBean(CommonOrderServiceTest.class);
		commonOrderService.updateCommonOrderPaymentTime();
	}

	public static void main(String[] args) throws IOException {
		updateCommonOrderPaymentTime();
	}
}
