package com.bhg.posServer.storm.bolt.test;

import kafka.producer.KeyedMessage;
import org.apache.commons.lang.StringUtils;

import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LogFileStatisticWithProgram {
    static int clicks = 0;
    static int uv = 0;
    static int pv = 0;
    static Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
    static Map<String, Integer> mapPv = new TreeMap<String, Integer>();
    static Map<String, Integer> mapClick = new TreeMap<String, Integer>();
    static Map<String, Integer> mapViewCityAdvertisement = new TreeMap<String, Integer>();
    static Map<String, Integer> mapClickCityAdvertisement = new TreeMap<String, Integer>();
    static int total=0;

    public static void main(String[] args) throws IOException {
        statisticCityAdvertisement("C:\\Users\\xiaoai\\Desktop\\数据\\2015-08-10.access/00-05");
    }

    public static void statisticCityAdvertisement(String nginxLogDir) {
        List<File> files = FileUtil.listFilesDeep(nginxLogDir, new String[]{".log"});

        for (File file : files) {
            System.out.println("begin to process " + file.getName());
            try {
                for (String line : FileUtil.readLines(file)) {
                    String request = BoltUtil.parseAccessUrl(line);

                    if (BoltUtil.isView(line)) {
                        total++;
                        Map<String, String> params = BoltUtil.getParams(request);
                        String uuid = params.get("uuid");
                        String advertisementUuid = params.get("advertisementUuid");

                        String city = params.get("city");

                        String materialId = params.get("materialId");

                        String movieId = params.get("materialId");

                        String cinemaId = params.get("cinemaId");

                       // String key = cinemaId + "#" + movieId;
                       // String key = advertisementUuid + "#" + city;
                       String key = advertisementUuid;
                     //   String key = materialId;
                     //   String key = materialId+"#"+city;
                        if(!StringUtils.isBlank(advertisementUuid)){
                            Integer pv = mapPv.get(key);
                            if (pv == null) {
                                mapPv.put(key, 1);
                            } else {
                                mapPv.put(key, 1 + pv);
                            }
                           Set<String>uuids = map.get(key);

                            if(uuids==null){
                                uuids = new HashSet<String>();
                                map.put(key,uuids);
                            }
                            uuids.add(uuid);
                        }

                    }else if(BoltUtil.isClick(line)){
                        total++;
                        Map<String, String> params = BoltUtil.getParams(request);
                        String uuid = params.get("uuid");
                        String advertisementUuid = params.get("advertisementUuid");

                        String city = params.get("city");

                        String materialId = params.get("materialId");

                        String movieId = params.get("materialId");

                        String cinemaId = params.get("cinemaId");
                       // String key = advertisementUuid + "#" + city;

                       // String key = cinemaId + "#" + movieId;
                        // String key = advertisementUuid ;
                        String key = advertisementUuid;
                       // String key = materialId;
                        //String key = materialId+"#"+city;
                        if(!StringUtils.isBlank(advertisementUuid)){
                            Integer click = mapClick.get(key);
                            if (click == null) {
                                mapClick.put(key, 1);
                            } else {
                                mapClick.put(key, 1 + click);
                            }
                        }

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            file = null;
        }
        int uvSum = 0;
        int pvSum = 0;
        int clickSum = 0;
        for (String key : mapPv.keySet()) {
            Integer pv = mapPv.get(key);
            pvSum += pv;
         //  System.out.println(" advertisementUuid = " + key + " ,pv=" + pv);
        }
        for (String key : mapClick.keySet()) {
            Integer click = mapClick.get(key);
            clickSum += click;
            // System.out.println(" advertisementUuid = " + key + " ,pv=" + pv);
        }
        for(String key : map.keySet()){
            Integer uv = map.get(key).size();
            uvSum += uv;
            System.out.println(" advertisementUuid = " + key + " ,pv=" + uv);
        }
        System.out.println("mapPv size=" + mapPv.size());
        System.out.println("pvSum = " + pvSum+" clickSum= "+clickSum+" uv= "+uvSum+" total ="+total);
    }

        public static void statisticTime(String nginxLogDir, String time) {
        List<File> files = FileUtil.listFilesDeep(nginxLogDir, new String[]{"00-00.access.log",
                "00-05.access.log",
                "00-10.access.log",
                "00-15.access.log",
                "00-20.access.log",
                "00-25.access.log",
                "00-30.access.log",
                "00-35.access.log",
                "00-40.access.log",
                "00-40.access.log",

        });
        for (File file : files) {
            System.out.println("begin to process " + file.getName());
            try {
                for (String line : FileUtil.readLines(file)) {
                    String request = BoltUtil.parseAccessUrl(line);
                    String clickTime = DateUtil.toString(DateUtil.getNginxDate(BoltUtil.getNginxAccessTime(line)));
                    Date statisticTime = DateUtil.getDate(clickTime);
                    Date pvAndClickStatisticTimePoint = DateUtil.getTimePointWithMinute(statisticTime, StormConstant.COMMIT_MINUTE_INTERVAL);
                    String logTime = DateUtil.toString(pvAndClickStatisticTimePoint);

                    if (logTime.equals(time)) {
                        if (!"".equals(request)) {
                            if (BoltUtil.isView(line)) {
                                Map<String, String> params = BoltUtil.getParams(request);
                                String uuid = params.get("uuid");
                                String advertisementUuid = params.get("advertisementUuid");
                                Set<String> set = map.get(advertisementUuid);

                                if (set == null) {
                                    set = new HashSet<String>();
                                    map.put(advertisementUuid, set);
                                }
                                if (advertisementUuid != null) {
                                    pv++;
                                    if (uuid != null) {
                                        set.add(uuid);
                                    }
                                    Integer pv = mapPv.get(advertisementUuid);
                                    if (pv == null) {
                                        mapPv.put(advertisementUuid, 1);
                                    } else {
                                        mapPv.put(advertisementUuid, 1 + pv);
                                    }
                                }
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            file = null;
        }
        int uvSum = 0;
        for (String key : map.keySet()) {
            Set<String> set = map.get(key);
            System.out.println("advertisementUuid = " + key + " ,uv=" + set.size());
            uvSum += set.size();
        }
        System.out.println( "uv=" + uvSum);
    }
}
