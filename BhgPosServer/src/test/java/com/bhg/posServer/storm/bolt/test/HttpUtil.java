package com.bhg.posServer.storm.bolt.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0";

	private static HttpClient client = null;
	private static MultiThreadedHttpConnectionManager connectionManager;
	private static HttpClientParams clientParams;

	public static void init(int timeOut) {
		log.info("HttpUtils init.");
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(10000);
		params.setSoTimeout(timeOut);
		params.setMaxTotalConnections(10000);
		clientParams = new HttpClientParams();
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		clientParams.setSoTimeout(timeOut);
		client = new org.apache.commons.httpclient.HttpClient(clientParams, connectionManager);
	}

	public static void dispose() {
		client.getHttpConnectionManager().closeIdleConnections(0);
	}

	public static int getString(String url) throws Exception {
		int status = -1;

		GetMethod method = new GetMethod(url);
		try {
			method.setFollowRedirects(false);
			method.addRequestHeader("User-Agent", USER_AGENT);
			int statusCode = client.executeMethod(method);
			if(url.contains("view")){
				 java.io.InputStream inputStream =  method.getResponseBodyAsStream();
				  inputStream.close();
			}else if(url.contains("click")){
				 method.getResponseBodyAsString();
			}
			
			if (statusCode == HttpStatus.SC_OK) {
				status = statusCode;
			} else {
				status = -1;
			}
			return status;
		} finally {
			method.abort();
			method.releaseConnection();
//			client.getHttpConnectionManager().closeIdleConnections(0);
		}
	}
}
