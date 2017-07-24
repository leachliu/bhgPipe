package com.bhg.posServer.storm.bolt.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bhg.posServer.utils.FileUtil;

public class CommonOrderClickInfo {
	
	private static int index = 1;
	
	public static void click(int size) throws IOException {
	    List<String> list = FileUtil.readLines(new File("orderInfo.txt"));
		//List<String> list = FileUtil.readLines(new File("C:\\Users\\xiaoai\\Desktop\\orderInfo.txt"));
		HttpUtil.init(1000);
		String ip = "10.10.3.54:8086";
		int length = list.size();
		if(size!=0){
			length = size;
		}
		
		for (int i = 0; i < length; i++) {
			String[] lines = list.get(i).split(",");
			String userId = lines[1];
			String movieCode = lines[2];

			String url = "http://" + ip + "/advertisement/click";
			url += "?advertisementUuid=" + ((i % 100)+1);
			url += "&movieId=" + movieCode;
			url += "&userId=" + userId;
			url += "&materialId=" + i;
			if (index % 50 == 0) {
				System.out.println(index + " " + url);
			}
			index++;
			try {
				HttpUtil.getString(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		if (args != null && args.length > 0) {
			if(args.length == 1){
				int round = Integer.parseInt(args[0]);
				for (int i = 0; i < round; i++) {
					click(0);
				}
			}else{
				int count = Integer.parseInt(args[1]);
				click(count);
			}
			
		} else {
			click(0);
		}

	}
}
