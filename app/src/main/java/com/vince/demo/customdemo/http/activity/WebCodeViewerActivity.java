package com.vince.demo.customdemo.http.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.http.service.HtmlService;

/**
 * 下载code
 */
public class WebCodeViewerActivity extends Activity {
	private EditText pathText;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_code);
		pathText = (EditText) this.findViewById(R.id.path);
		textView = (TextView) this.findViewById(R.id.textView);
	}

	public void showhtml(View v) {
		String path = pathText.getText().toString();
		new DownLoadAsyncTask().execute(path);
	}

	public class DownLoadAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String html = null;
			try {
				html = HtmlService.getHtml(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return html;
		}

		@Override
		protected void onPostExecute(String code) {
			super.onPostExecute(code);

			if (!TextUtils.isEmpty(code)) {
				textView.setText(code);
			}
		}
	}
}