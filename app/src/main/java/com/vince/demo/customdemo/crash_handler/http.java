package com.vince.demo.customdemo.crash_handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * decription ：
 * author ： zhua
 * Created at 2016/6/23.
 */
public class http {

    /**
     * 信息反馈请求
     */
    public static String feedBackInfopost(String httpUrl,
                                          Map<String, Object> param, File file) {
        HttpPost post = new HttpPost(httpUrl);
        MultipartEntity entity = new MultipartEntity();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                if (entry.getValue() != null
                        && entry.getValue().toString().trim().length() > 0) {
                    try {
                        entity.addPart(
                                entry.getKey(),
                                new StringBody(
                                        entry.getValue().toString(),
                                        Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 添加文件参数
        if (file != null && file.exists()) {
            entity.addPart("imgFile", new FileBody(file));
        }

        post.setEntity(entity);
        HttpResponse response = null;

        try {
            response = CustomerHttpClient.getHttpClient().execute(post);
        } catch (ConnectTimeoutException e2) {
            //throw new RequestFailException("连接超时");
        } catch (ClientProtocolException e) {
            //throw new RequestFailException("请求失败");
        } catch (IOException e) {
            //throw new RequestFailException("请求失败");
        }

        int stateCode = response.getStatusLine().getStatusCode();
        StringBuffer sb = new StringBuffer();

        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = null;
                try {
                    is = result.getContent();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String tempLine;
                try {
                    while ((tempLine = br.readLine()) != null) {
                        sb.append(tempLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
           // throw new RequestFailException("请求失败");
        }

        post.abort();
        return sb.toString();
    }
}
