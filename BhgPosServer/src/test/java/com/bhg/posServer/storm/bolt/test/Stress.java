package com.bhg.posServer.storm.bolt.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Stress {

	private String url;

	private String dataType;

	private int dataCount;


	private int timeOut;
	
	private int threadPoolCount;

	private AtomicInteger errorCount = new AtomicInteger(0);

	private AtomicLong sendNum = new AtomicLong(0);

	private AtomicLong successNum = new AtomicLong(0);

	public Stress(String ip, String dataType, int timeOut, 	int threadPoolCount, int dataCount) throws IOException {
		this.url = ip;
		this.dataCount = dataCount;
		this.dataType = dataType;
		this.timeOut = timeOut;
		this.threadPoolCount = threadPoolCount; 
	}

	public void run() throws Exception {
		HttpUtil.init(timeOut);
		ExecutorService service = Executors.newFixedThreadPool(threadPoolCount);
		System.out.println("Start....");
		long start = System.currentTimeMillis();
		Collection<Callable<String>> tasks = new ArrayList<Callable<String>>();
        int count = 0;
        do {
            for (int i = 0; i < threadPoolCount*10; i++,count++) {
                tasks.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        try {
                            access();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                });
            }
        }while (dataCount > count);


		service.invokeAll(tasks);
		System.out.println("Time Consuming = " + (System.currentTimeMillis() - start) + " ms");
		service.shutdown();
		HttpUtil.dispose();
		System.out.println("Request dataCount = " + sendNum.get() + ", error dataCount = " + errorCount.get() + ", successNum dataCount = " + successNum.get());
	}

	private void access() {
		for (int i = 0; i < 1; i++) {
			String getInfoUrl = url + dataType;
			getInfoUrl += ProduceClickAndPageViewData.getdata();
			try {
				sendNum.getAndIncrement();
				int statusCode = HttpUtil.getString(getInfoUrl);
				if (statusCode != -1) {
					successNum.getAndIncrement();
				} else {
					errorCount.getAndIncrement();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (sendNum.get() % 50 == 0) {
				System.out.println(Thread.currentThread().getName() + " sendNum = " + sendNum.get() + " successNum=" + successNum.get() + " " + getInfoUrl);
			}

		}
	}

	/**
	 * ip nginx的访问地址
     * dataType发送信息类型,click还是view
     * timeOut httpclient超时设置
	 * threadCount线程数目
     * dataCount每个线程发送的信息的数量
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String ip = args[0];
		String dataType = args[1];
		int timeOut = Integer.parseInt(args[2]);
		int threadPoolCount = Integer.parseInt(args[3]);
		int dataCount = Integer.parseInt(args[4]);
		Stress stress = new Stress(ip, dataType, timeOut, threadPoolCount, dataCount);
		stress.run();
	}

}
