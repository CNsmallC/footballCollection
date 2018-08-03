package cn.smallc.footballcollection.util;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张超
 * @date 2016年11月29日 11:36:32
 */
public class HttpClientUtilsForAppV2 {
	private static Map<String, String> cookiesMap = new HashMap<>();
	private static HttpClient client = HttpClients.custom().setDefaultRequestConfig(getReqConfig()).build();
	public static void main(String[] args) {
		get("http://cpu.baidu.com");
		System.out.println(clearCookie());
	}
	/**
	 * 返回cookie的字符串
	 * @return
	 */
	public static String getCookieStr() {
		String ret = cookiesMap.toString();
		return ret.substring(1, ret.length()-1).replace(",", ";");
	}
	/**
	 * 清空cookie并返回cookie的字符串
	 * @return cookie字符串
	 */
	public static String clearCookie() {
		String ret = getCookieStr();
		cookiesMap.clear();
		return ret;
	}
	/**
	 * get请求，不带Header，带重试次数参数
	 * @param url
	 * @param retryTimes 请求重试次数
	 * @return 返回html字符串
	 * @return
	 */
	public static String get(String url, int retryTimes) {
		return get(url, new String[0], retryTimes);
	}
	/**
	 * get请求，不带Header，不带重试次数参数，重试次数默认为3
	 * @param url
	 * @return 返回html字符串
	 * @return
	 */
	public static String get(String url) {
		return get(url, new String[0]);
	}
	/**
	 * get请求，不带重试次数参数，重试次数默认为3
	 * @param url
	 * @param headers Header参数字符串，格式为：key1=value1&key2=value2
	 * @return html字符串
	 * @return
	 */
	public static String get(String url, String[] headers) {
		return get(url, headers, 3);
	}
	/**
	 * get请求，带header，带重试次数参数
	 * @param url
	 * @param headers Header参数字符串，格式为：key1=value1&key2=value2
	 * @param retryTimes 请求重试次数
	 * @return html字符串
	 */
	public static String get(String url, String[] headers, int retryTimes) {
		return sendRequest(new HttpGet(url), headers, retryTimes);
	}

	/**
	 * post请求，不带Header，不带重试次数参数，重试次数默认为3
	 * @param url
	 * @param entity post请求实体字符串，格式为：key1=value1&key2=value2
	 * @return html字符串
	 */
	public static String post(String url, String[] entity) {
		return post(url, entity, new String[0]);
	}
	/**
	 * post请求，不带重试次数参数，重试次数默认为3
	 * @param url
	 * @param entity post请求实体字符串，格式为：key1=value1&key2=value2
	 * @param headers Header参数字符串，格式为：key1=value1&key2=value2
	 * @return html字符串
	 */
	public static String post(String url, String[] entity, String[] headers) {
		return post(url, entity, headers, 3);
	}
	/**
	 * post请求，带header，带重试次数参数
	 * @param url
	 * @param entity post请求实体字符串，格式为：key1=value1&key2=value2
	 * @param headers Header参数字符串，格式为：key1=value1&key2=value2
	 * @param retryTimes 请求重试次数
	 * @return html字符串
	 */
	public static String post(String url, String[] entity, String[] headers, int retryTimes) {
		HttpPost post = new HttpPost(url);
		if(entity.length>0) {
			List<NameValuePair> formparams = new ArrayList<>(entity.length);
			for(int i = 0; i < entity.length; i++) {
				String[] pair = entity[i].split("=", 2);
				formparams.add(new BasicNameValuePair(pair[0], pair[1]));
			}
			try {
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams);
				post.setEntity(uefEntity);
			} catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sendRequest(post, headers, retryTimes);
	}
	private static String sendRequest(HttpRequestBase base, String[] headers, int retryTimes) {
		setHeader(base, headers);
		return executeRequest(base, retryTimes);
	}
	private static String executeRequest(HttpRequestBase base, int retryTimes) {
		String retStr = null;
		if(retryTimes>0) {
			for(int i = 0; retStr==null && i<retryTimes; i++) {
				try {
					HttpResponse response = client.execute(base);
					setCookie(response);
					retStr = new String(EntityUtils.toByteArray(response.getEntity()));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		} else throw new IllegalArgumentException("重试次数retrytimes不能小于等于0");
		return retStr;
	}
	private static void setCookie(HttpResponse response) {
		Header[] cookies = response.getHeaders("Set-Cookie");
		for(int i = 0; i < cookies.length; i++) {
			String[] pair = cookies[i].getValue().split(";")[0].split("=", 2);
			cookiesMap.put(pair[0], pair[1]);
		}
	}
	private static void setHeader(HttpRequestBase base, String[] headers) {
		for(int i = 0; i < headers.length; i++) {
			String[] pair = headers[i].split("=", 2);
			if(pair[0].trim().equals("Cookie")) {
				String[] cookieValues = pair[1].split(";");
				for(int j = 0; j < cookieValues.length; j++) {
					String[] pair1 = cookieValues[j].split("=", 2);
					if(!cookiesMap.containsKey(pair1[0])) cookiesMap.put(pair1[0], pair1[1]);
				}
			} else {
				base.addHeader(pair[0], pair[1]);
			}
			base.addHeader("Cookie", getCookieStr());
		}
	}
	/**
	 * 返回请求配置
	 * @return
	 */
	private static RequestConfig getReqConfig() {
		return RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
	}
}
