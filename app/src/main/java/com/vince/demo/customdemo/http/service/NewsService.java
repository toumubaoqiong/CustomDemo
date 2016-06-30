package com.vince.demo.customdemo.http.service;

import android.util.Xml;
import com.vince.demo.customdemo.http.domain.News;
import com.vince.demo.customdemo.http.utils.StreamTool;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsService {
    /**
     * 获取最新视频资讯
     *
     * @return
     * @throws Exception
     */
    public static List<News> getJSONLastNews() throws Exception {
        String path = "http://192.168.56.1:8080/ServerForPicture/NewsListServlet";
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream json = conn.getInputStream();
            return parseJSON(json);
        }
        return null;
    }

    private static List<News> parseJSON(InputStream jsonStream) throws Exception {
        List<News> list = new ArrayList<News>();
        byte[] data = StreamTool.read(jsonStream);
        String json = new String(data);
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            int timelength = jsonObject.getInt("timelength");
            list.add(new News(id, title, timelength));
        }
        return list;
    }

    /**
     * 获取最新视频资讯
     *
     * @return
     * @throws Exception
     */
    public static List<News> getLastNews() throws Exception {
        String path = "http://192.168.56.1:8080/ServerForPicture/ServletForXML";
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream xml = conn.getInputStream();
            return parseXML(xml);
        }
        return null;
    }

    private static List<News> parseXML(InputStream xml) throws Exception {
        List<News> newes = new ArrayList<News>();
        News news = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xml, "UTF-8");
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    newes = new ArrayList<News>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("news".equals(pullParser.getName())) {
                        int id = new Integer(pullParser.getAttributeValue(0));
                        news = new News();
                        news.setId(id);
                    }

                    if ("title".equals(pullParser.getName())) {
                        news.setTitle(pullParser.nextText());
                    }

                    if ("timelength".equals(pullParser.getName())) {
                        news.setTimelength(new Integer(pullParser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("news".equals(pullParser.getName())) {
                        newes.add(news);
                        news = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        return newes;
    }
}
