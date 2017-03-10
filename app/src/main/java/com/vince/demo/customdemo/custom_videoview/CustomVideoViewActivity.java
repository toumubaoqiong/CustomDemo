package com.vince.demo.customdemo.custom_videoview;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vince.demo.customdemo.R;

/**
 *description:VideoView 实现引导页播放视频欢迎效果
 *author:vince
 */
public class CustomVideoViewActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "CustomVideoViewActivity";

    private CustomVideoView videoview;
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_video_view);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

       /* videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.media));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                pauseMusic();

                Toast.makeText(this,"进入了主页",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void pauseMusic() {
        /*Intent freshIntent = new Intent();
        freshIntent.setAction("com.android.music.musicservicecommand.pause");
        freshIntent.putExtra("command", "pause");
        sendBroadcast(freshIntent);*/

         AudioManager audioManager = ( AudioManager)getSystemService( Activity.AUDIO_SERVICE );
         audioManager.requestAudioFocus( null, AudioManager.STREAM_MUSIC,
         AudioManager.AUDIOFOCUS_GAIN );
    }

}
