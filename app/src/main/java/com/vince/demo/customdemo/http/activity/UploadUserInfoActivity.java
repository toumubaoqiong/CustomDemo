package com.vince.demo.customdemo.http.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.http.service.UserInformationService;

/**
 * 上传用户信息  get/post
 */
public class UploadUserInfoActivity extends Activity {

    private EditText titleText;
    private EditText lengthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_info);

        titleText = (EditText) this.findViewById(R.id.title);
        lengthText = (EditText) this.findViewById(R.id.length);
    }

    public void save(View v) {
        String title = titleText.getText().toString();
        String length = lengthText.getText().toString();
        String[] param = new String[2];
        param[0] = title;
        param[1] = length;

        new UploadAsyncTask().execute(param);


    }

    public class UploadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                result = UserInformationService.save(params[0], params[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);

            if (flag) {
                Toast.makeText(UploadUserInfoActivity.this, R.string.success, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UploadUserInfoActivity.this, R.string.fail, Toast.LENGTH_LONG).show();
            }
        }
    }
}
