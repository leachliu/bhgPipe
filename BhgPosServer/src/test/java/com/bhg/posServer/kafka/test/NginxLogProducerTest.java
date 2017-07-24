package com.bhg.posServer.kafka.test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.lang.StringUtils;

import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.FileUtil;

public class NginxLogProducerTest {

    private Producer<String, String> producer;

    private String nginxLogDir;

    private String viewTopic;

    private String clickTopic;

    public NginxLogProducerTest(String nginxLogDir, String viewTopic, String clickTopic) {
        producer = new Producer<String, String>(createProducerConfig());
        this.nginxLogDir = nginxLogDir;
        this.viewTopic = viewTopic;
        this.clickTopic = clickTopic;
    }

    private ProducerConfig createProducerConfig() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "10.10.3.54:9092,10.10.3.56:9094");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        return new ProducerConfig(props);
    }

    public void run() {
        while (true) {
            while (true) {
                List<File> files = FileUtil.listFilesDeep(nginxLogDir, new String[]{".unread"});

                if (files.size() > 0) {
                    System.out.println(DateUtil.toString(new Date()) + " find " + files.size() + " nginx log files.");
                }
                for (File file : files) {
                    try {
                        int count = 0;
                        for (String line : FileUtil.readLines(file)) {
                            String topic = isViewAssceeLog(line);
                            if (!"".equals(topic)) {
                                producer.send(new KeyedMessage<String, String>(topic, line));
                                count++;
                            }
                        }
                        System.out.println(DateUtil.toString(new Date()) + " send " + count + " messages to kafka..");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String oldFileName = file.getAbsolutePath();
                    String newFileName = oldFileName.replace(".unread", ".read");
                    file.renameTo(new File(newFileName));
                }
                try {
                    Thread.sleep(20 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String isViewAssceeLog(String line) {
        try {
        	if (line.contains("GET /advertisement/view")) {
				return this.viewTopic;
			} else if (line.contains("GET /advertisement/click")) {
				return this.clickTopic;
			}
        } catch (Exception e) {
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
        new NginxLogProducerTest("", "view", "click").processTrueLog();

    }

    public void runTest(int lenth) throws IOException {
        long start = System.currentTimeMillis();
        File file = new File(nginxLogDir);
        List<String> list = FileUtil.readLines(file);
        int count = 0;
        while (count <= lenth) {
            for (String line : list) {
                String topic = isViewAssceeLog(line);
                if (!"".equals(topic)) {
                    producer.send(new KeyedMessage<String, String>(topic, line));
                    count++;
                    if (count % 1000 == 0) {
                        System.out.println(DateUtil.toString(new Date()) + " send " + count + " messages to kafka..");
                    }
                }
            }

        }
        System.out.println("Time Consuming = " + (System.currentTimeMillis() - start) + " ms");
    }

    public void processTrueLog() {
        int i = 0;
        List<String> lines = null;
        try {
            lines = FileUtil.readLines(new File("C:\\Users\\xiaoai\\Desktop\\2015-07-27-17-20.access.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Set<String>>map=new HashMap<String, Set<String>>();
        for (String line : lines) {

            String topic = isViewAssceeLog(line);
            if ("view".equals(topic)) {
                String request = BoltUtil.parseAccessUrl(line);
                Map<String, String> params = BoltUtil.getParams(request);
                String uuid = params.get("uuid");
                String advertisementUuid = params.get("advertisementUuid");
                Set<String> set = map.get(advertisementUuid);
                if(set == null){
                    set = new HashSet<String>();
                    map.put(advertisementUuid,set);
                }
                set.add(uuid);
                i++;

            }
        }
        int sum = 0;
        for(String key:map.keySet()){
            Set<String> set = map.get(key);
            System.out.println("advertisementUuid = "+key+" ,uv="+set.size());
            sum+=set.size();
        }
        System.out.println("sum = "+sum);
    }

}
