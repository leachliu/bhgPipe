package com.bhg.posServer.kafka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.Configs;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.FileUtil;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class NginxLogProducer {

	private Producer<String, String> producer;

	private String nginxLogDir;

	private String logTopic;

	public NginxLogProducer(String nginxLogDir, String logTopic) {
		producer = new Producer<String, String>(createProducerConfig());
		this.nginxLogDir = nginxLogDir;
		this.logTopic = logTopic;
	}

	private ProducerConfig createProducerConfig() {
		Properties props = new Properties();
		props.put("metadata.broker.list", Configs.KAFKA_METADATA_BROKER_LIST);
		props.put("serializer.class", Configs.SERIALIZER_CLASS);
		props.put("request.required.acks", Configs.REQUEST_REQUIRED_ACKS);
		return new ProducerConfig(props);
	}

	public void run() {
		while (true) {
			List<File> files = FileUtil.listFilesDeep(nginxLogDir, new String[] { ".log" });

			Map<String, File> map = new TreeMap<String, File>();
			for (File file : files) {
				map.put(file.getName(), file);
			}

			for (String key : map.keySet()) {
				File file = map.get(key);
				try {
					int count = 0;
					System.out.println(DateUtil.toString(new Date()) + " begin to process " + file.getName());
					String line = null;
					BufferedReader reader = new BufferedReader(new FileReader(file));
					try {
						while ((line = reader.readLine()) != null) {
							if (BoltUtil.isClick(line) || BoltUtil.isView(line)) {
								producer.send(new KeyedMessage<String, String>(this.logTopic, line));
								count++;
							}
						}
					} finally {
						reader.close();
					}
					
					System.out.println(DateUtil.toString(new Date()) + " " + file.getName() + " send " + count + " messages to kafka..");
				} catch (IOException e) {
					e.printStackTrace();
				}
				String newFileName = file.getAbsolutePath() + ".done";
				file.renameTo(new File(newFileName));
				file = null;
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String nginxLogsDir = args[0];
		String logTopic = Configs.LOGTOPIC;
		new NginxLogProducer(nginxLogsDir, logTopic).run();
	}
}
