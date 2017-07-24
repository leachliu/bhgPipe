package com.bhg.posServer.storm.bolt.test;

import java.util.concurrent.atomic.AtomicInteger;

public class ProduceClickAndPageViewData {

	private static String[] userIds = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	private static String[] advertisementIds = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	private static String[] citys = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	private static String[] materialIds = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

    private static AtomicInteger i = new AtomicInteger(0);

	public synchronized static String getdata() {
		String url = "";
		if (i.get() == materialIds.length) {
            i.getAndSet(0);
		}
		url += "?advertisementUuid=" + advertisementIds[i.get()];
		url += "&uuid=" + userIds[i.get()];
		url += "&city=" + citys[i.get()];
		url += "&materialId=" + materialIds[i.get()];
		i.getAndIncrement();
		return url;
	}
	public static void main(String[] args) {
		for(int i=0;i<11;i++)
		  System.out.println(getdata());
	}

}
