package com.vince.demo.customdemo.http.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.http.domain.News;
import com.vince.demo.customdemo.http.service.NewsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取XMl数据
 */
public class GetXMLActivity extends Activity {
    /** Called when the activity is first created. */
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_xml);
        listView = (ListView) this.findViewById(R.id.listView);

        new DownLoadAsyncTask().execute();
    }

    public class DownLoadAsyncTask extends AsyncTask<String, Integer, List<News>> {

        @Override
        protected  List<News> doInBackground(String... params) {
            List<News> newes = null;
            try {
                newes = NewsService.getJSONLastNews();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newes;
        }

        @Override
        protected void onPostExecute(List<News> list) {
            super.onPostExecute(list);

            String length = "TimeLength：";
            if (list != null) {
                List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
                for(News news : list){
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    item.put("id", news.getId());
                    item.put("title", news.getTitle());
                    item.put("timelength", length+ news.getTimelength());
                    data.add(item);
                }
                SimpleAdapter adapter = new SimpleAdapter(GetXMLActivity.this, data, R.layout.item,
                        new String[]{"title", "timelength"}, new int[]{R.id.title, R.id.timelength});
                listView.setAdapter(adapter);
            }
        }
    }
}
