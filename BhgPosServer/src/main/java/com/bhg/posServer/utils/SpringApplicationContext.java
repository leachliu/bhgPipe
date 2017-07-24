package com.bhg.posServer.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContext {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");;

	private SpringApplicationContext() {
	}

	public synchronized static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
}
