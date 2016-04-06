package com.vince.demo.customdemo.demo_aidl;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import com.vince.demo.customdemo.R;

/**
 * decription ：service，实现aidl里面的接口
 * author ： vince
 */
public class ControlMusicService extends Service {

    private MediaPlayer player;

    private final IMusicControlService.Stub binder=new IMusicControlService.Stub(){
        @Override
        public void playMusic() throws RemoteException {
            // TODO Auto-generated method stub

            player= MediaPlayer.create(ControlMusicService.this, R.raw.kanong);
            player.start();
        }

        @Override
        public void stopMusic() throws RemoteException {
            // TODO Auto-generated method stub

            if(player.isPlaying())
            {
                player.stop();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
