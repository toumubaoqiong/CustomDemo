package com.vince.demo.customdemo.http.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.http.service.ImageService;

/**
 * 下载图片
 */
public class GetAPictureFromInternetActivity extends Activity {
    private EditText pathText;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);
        pathText = (EditText) this.findViewById(R.id.path);
        imageView = (ImageView) this.findViewById(R.id.imageView);
    }

    public void showimage(View v) {
        String path = pathText.getText().toString();
        new DownLoadAsyncTask().execute(path);
    }

    public class DownLoadAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = ImageService.getImage(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}