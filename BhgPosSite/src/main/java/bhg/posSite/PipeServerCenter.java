package bhg.posSite;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.bhg.pipeServer.vo.SchemaVo;
import com.bhg.pipeServer.vo.SiteVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PipeServerCenter {
	private String SERVER_ADDERSS;

	/**
	 * 配置服务地址
	 * 
	 * @param url
	 * @return
	 */
	public static PipeServerCenter connect(String url) {
		PipeServerCenter pipe = new PipeServerCenter();
		pipe.SERVER_ADDERSS = url;
		return pipe;
	}

	private String getSchemasAddress() {
		return SERVER_ADDERSS + "schemas";
	}

	private String getSitesAddress() {
		return SERVER_ADDERSS + "sites";
	}

	public List<SchemaVo> getSchemas() {
		String json = loadJson(get(getSchemasAddress() + ".json"));
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<SchemaVo>>() {
		}.getType());
	}

	public List<SiteVo> getSites() {
		String json = loadJson(get(getSitesAddress() + ".json"));
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<SiteVo>>() {
		}.getType());
	}

	private HttpResponse get(String url) throws PipeException {
		RequestBuilder requestBuilder = null;
		CloseableHttpClient client = HttpClients.createDefault();
		System.out.println(url);
		requestBuilder = RequestBuilder.get().setUri(url);

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		requestBuilder.setConfig(config);
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response;
		try {
			response = (HttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			throw new PipeException();
		} catch (IOException e) {
			throw new PipeException();
		}
		return response;
	}

	private HttpResponse put(String url, String json) throws PipeException {
		System.out.println("json:" + json);
		RequestBuilder requestBuilder = null;
		CloseableHttpClient client = HttpClients.createDefault();
		System.out.println(url);
		requestBuilder = RequestBuilder.put().setUri(url);

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		requestBuilder.setConfig(config);
		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		HttpUriRequest request = requestBuilder.setEntity(entity).build();

		HttpResponse response;
		try {
			response = (HttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new PipeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PipeException();
		}
		return response;
	}

	private HttpResponse delete(String url) throws PipeException {
		RequestBuilder requestBuilder = null;
		CloseableHttpClient client = HttpClients.createDefault();
		System.out.println(url);
		requestBuilder = RequestBuilder.delete().setUri(url);

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		requestBuilder.setConfig(config);
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response;
		try {
			response = (HttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new PipeException();
		} catch (IOException e) {
			throw new PipeException();
		}
		return response;
	}

	private HttpResponse post(String url, String json) throws PipeException {
		System.out.println("json:" + json);
		RequestBuilder requestBuilder = null;
		CloseableHttpClient client = HttpClients.createDefault();
		System.out.println(url);
		requestBuilder = RequestBuilder.post().setUri(url);

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		requestBuilder.setConfig(config);
		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		HttpUriRequest request = requestBuilder.setEntity(entity).build();

		HttpResponse response;
		try {
			response = (HttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new PipeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PipeException();
		}
		return response;
	}

	private String loadJson(HttpResponse response) throws PipeException {
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			System.out.println("load error");
			System.out.println(response.getStatusLine().getStatusCode());
			try {
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
				throw new PipeException();
			}
			throw new PipeException();
		} else {
			try {
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				throw new PipeException();
			}
		}
	}

	private HttpResponse httpExcute(String mothod, String url, HashMap<String, String> params) throws PipeException {
		RequestBuilder requestBuilder = null;
		CloseableHttpClient client = HttpClients.createDefault();
		System.out.println(url);
		if (mothod.equals("get")) {
			requestBuilder = RequestBuilder.get().setUri(url);
		} else if (mothod.equals("post")) {
			requestBuilder = RequestBuilder.post().setUri(url);
		} else if (mothod.equals("put")) {
			requestBuilder = RequestBuilder.put().setUri(url);
		} else if (mothod.equals("delete")) {
			requestBuilder = RequestBuilder.delete().setUri(url);
		}
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				requestBuilder = requestBuilder.addParameter(entry.getKey(), entry.getValue());
			}
		}

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		requestBuilder.setConfig(config);
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response;
		try {
			response = (HttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			throw new PipeException();
		} catch (IOException e) {
			throw new PipeException();
		}
		return response;
	}

	public static void main(String[] args) {
		PipeServerCenter server = PipeServerCenter.connect("http://localhost:8080/pipeServer/");
		System.out.println("---" + server.getSchemas());
	}
}
