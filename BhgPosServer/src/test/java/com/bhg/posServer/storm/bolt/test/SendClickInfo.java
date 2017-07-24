package com.bhg.posServer.storm.bolt.test;
public class SendClickInfo {
	public static int length = 100;
	private static int successNum = 0;
	private static int sendNum = 0;
	private static String ip = "10.10.3.96";
	private static String url = "http://" + ip + "/advertisement_test/";
	public static void main(String[] args) throws Exception {
		if (args != null && args.length > 0) {
			if (args.length > 1) {
				length = Integer.parseInt(args[1]);
			}
			if (args[0].equals("click")) {
				click();
			}
			if (args[0].equals("view")) {
				view();
			}
		}
	}

	public static void click() throws Exception {

		HttpUtil.init(2000);
		
		for (int i = 0; i < length; i++) {
			String clickUrl = url + "click";
			clickUrl += ProduceClickAndPageViewData.getdata();
			try {
				sendNum++;
				int statusCode = HttpUtil.getString(clickUrl);
				if (statusCode != -1) {
					successNum++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sendNum = " + sendNum + " successNum=" + successNum + " " + clickUrl);
		}
	}

	public static void view() throws Exception {
		HttpUtil.init(2000);
		for (int i = 0; i < length; i++) {
			String veiwUrl = url + "view";
			veiwUrl += ProduceClickAndPageViewData.getdata();
			try {
				sendNum++;
				int statusCode = HttpUtil.getString(veiwUrl);
				if (statusCode != -1) {
					successNum++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sendNum = " + sendNum + " successNum=" + successNum + " " + veiwUrl);
		}
	}
}
