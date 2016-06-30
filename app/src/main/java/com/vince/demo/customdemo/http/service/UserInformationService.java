package com.vince.demo.customdemo.http.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInformationService {
	public static boolean save(String title, String length) throws Exception{
		String path = "http://192.168.56.1:8080/ServerForGETMethod/ServletForGETMethod";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", title);
		params.put("age", length);
		return sendHttpClientPOSTRequest(path, params, "UTF-8");
	}

	/**
	 * 发送GET请求    主要使用于数据量小于2kb并且安全性要求不是很高的情况
	 * @param path 请求路径
	 * @param params 请求参数
	 * @return
	 */
	private static boolean sendGETRequest(String path, Map<String, String> params, String encoding) throws Exception{
		// http://192.178.1.100:8080/ServerForGETMethod/ServletForGETMethod?title=xxxx&length=90
		StringBuilder sb = new StringBuilder(path);
		if(params!=null && !params.isEmpty()){
			sb.append("?");
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		HttpURLConnection conn = (HttpURLConnection) new URL(sb.toString()).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}

	/**
	 * 发送POST请求  适用于数据量大、数据类型复杂、数据安全性要求高的场合
	 * @param path 请求路径
	 * @param params 请求参数
	 * @return
	 */
	private static boolean sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		//  title=liming&length=30
		StringBuilder sb = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] data = sb.toString().getBytes();

		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//允许对外传输数据
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", data.length+"");
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}

	/**
	 * 开源框架HttpClient的方式
	 * @param path
	 * @param params
	 * @param encoding
	 * @return
     * @throws Exception
     */
	private static boolean sendHttpClientPOSTRequest(String path,Map<String,String> params,String encoding)throws Exception{

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if(params != null && !params.isEmpty()){
			for(Map.Entry<String,String> entry: params.entrySet()){
				pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,encoding);
		HttpPost post = new HttpPost(path);
		post.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);

		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}

		return false;
	}

	/**
	 * 发送xml数据
	 * @param path
	 * @param xml
	 * @return
	 * @throws Exception
     */
	private static boolean sendXML(String path,String xml) throws Exception{

		byte[] data = xml.getBytes();
		URL url = new URL(path);
		HttpURLConnection conn= (HttpURLConnection)url.openConnection();

		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5*1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
		conn.setRequestProperty("Content-Length",String.valueOf(data.length));
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();

		if(conn.getResponseCode() == 200){
			return true;
		}

		return false;
	}
}
