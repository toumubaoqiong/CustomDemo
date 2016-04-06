//如果包名和应用程序的包名不一样，需要在build.gradle中添加代码
package com.vince.demo.customdemo.demo_aidl;

interface IMusicControlService
{
        void playMusic();//播放音乐
        void stopMusic();//停止播放音乐
}