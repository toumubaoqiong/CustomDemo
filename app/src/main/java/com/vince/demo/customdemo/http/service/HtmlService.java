package com.vince.demo.customdemo.http.service;

import com.vince.demo.customdemo.http.utils.StreamTool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlService {
	/**
	 * 获取网页源码
	 * @param path 网页路径
	 * @return
	 */
	public static String getHtml(String path) throws Exception {
		HttpURLConnection conn = (HttpURLConnection)new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			InputStream inStream = conn.getInputStream();
			byte[] data = StreamTool.read(inStream);
			return new String(data);
		}
		return null;
	}
}
