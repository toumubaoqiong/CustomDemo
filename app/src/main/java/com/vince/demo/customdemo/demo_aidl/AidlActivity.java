package com.vince.demo.customdemo.demo_aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.vince.demo.customdemo.R;

/**
 *description:aidl进行进程间通信
 *author:vince
 */
public class AidlActivity extends Activity {

    private IMusicControlService iMusicControlService;

    private final ServiceConnection serviceConnection=new ServiceConnection()
    {

        //第一次连接service时会调用这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            iMusicControlService=IMusicControlService.Stub.asInterface(service);
        }

        //service断开的时候会调用这个方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            System.out.println("service unconntection");
            iMusicControlService=null;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        /*两种启动方式*/
//        Intent intent = new Intent();
//        intent.setClass(AidlActivity.this, ControlMusicService.class);
        Intent intent = new Intent("com.vince.demo.customdemo.demo_aidl.ControlMusicService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.Button_play:
                try {
                    iMusicControlService.playMusic();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Button_stop:
                try {
                    iMusicControlService.stopMusic();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
