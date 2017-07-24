package com.bhg.posServer.redis.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bhg.posServer.redis.RedisUtil;
import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.FileUtil;

public class RedisReadTest {

    public static void main(String[] args) throws IOException {
        String nginxLogDir = args[0];
        statistic(nginxLogDir);
        readFromRedis();


    }

    public static void statistic(String nginxLogDir) throws IOException {
        long start = System.currentTimeMillis();
        List<File> files = FileUtil.listFilesDeep(nginxLogDir, new String[]{".log"});
        int count = 0;
        for (File file : files) {
            System.out.println("begin to process " + file.getName());
            for (String line : FileUtil.readLines(file)) {
                String request = BoltUtil.parseAccessUrl(line);
                if (!"".equals(request)) {
                    if (request.contains("/advertisement/view")) {
                        Map<String, String> params = BoltUtil.getParams(request);
                        String uuid = params.get("uuid");
                        String clickTime = DateUtil.toString(DateUtil.getNginxDate(BoltUtil.getNginxAccessTime(line)));
                        String advertisementUuid = params.get("advertisementUuid");
                        Date uvStatisticTimePoint = DateUtil.getTimePointWithHour(DateUtil.getDate(clickTime), 1);
                        String key = DateUtil.toString(uvStatisticTimePoint) + "#" + advertisementUuid;
                        RedisUtil.getInstance().sAdd(key, uuid);
                        count++;
                    }
                }
            }
            System.out.println("Store " + count + " userIds to redis");
        }
        long end = System.currentTimeMillis();
        System.out.println("Store " + count + " userIds to redis,and Use " + (end - start) + " ms");
    }

    public static void readFromRedis() throws IOException {
        long start = System.currentTimeMillis();
        Set<String> keys = RedisUtil.getInstance().keys("*");
        Map<String, Long> map = new HashMap<String, Long>();
        long count = 0;
        for (String key : keys) {
            if (key.contains("#")) {
                long size = RedisUtil.getInstance().getSetCount(key);
                map.put(key, size);
                count += size;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("get userIds from redis " + count + " and Use " + (end - start) + " ms");
    }

    public static void removeFromRedis() throws IOException {
        long start = System.currentTimeMillis();
        Set<String> keys = RedisUtil.getInstance().keys("*");
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        int count = 0;
        for (String key : keys) {
            if (key.contains("#")) {
                RedisUtil.getInstance().del(key);
            }
            count += map.get(key).size();
        }
        long end = System.currentTimeMillis();
        System.out.println("get userIds from redis " + count + " and Use " + (end - start) + " ms");
    }
}
