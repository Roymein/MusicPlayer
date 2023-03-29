package com.android.cloud.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.android.cloud.player.MusicActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    //声明一个MediaPlayer引用
    private MediaPlayer mMediaPlayer;
    //声明一个计时器引用
    private Timer mTimer;

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建音乐播放器对象
        mMediaPlayer = new MediaPlayer();
    }

    //添加计时器用于设置音乐播放器中的播放进度条
    private void addTimer() {
        //如果timer不存在，也就是没有引用实例
        if (mTimer == null) {
            //创建计时器对象
            mTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (mMediaPlayer == null) return;
                    int duration = mMediaPlayer.getDuration();//获取歌曲总时长
                    int currentPosition = mMediaPlayer.getCurrentPosition();//获取播放进度
                    Message msg = MusicActivity.handler.obtainMessage();//创建消息对象
                    //将音乐的总时长和播放进度封装至bundle中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    //再将bundle封装到msg消息对象中
                    msg.setData(bundle);
                    //最后将消息发送到主线程的消息队列
                    MusicActivity.handler.sendMessage(msg);
                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒（0.5s）执行一次
            mTimer.schedule(task, 5, 500);
        }
    }

    //Binder是一种跨进程的通信方式
    public class MusicControl extends Binder {

        public void play(int i) {//String path
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music" + i);
            try {
                //重置音乐播放器
                mMediaPlayer.reset();
                //加载多媒体文件
                mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mMediaPlayer.start();//播放音乐
                addTimer();//添加计时器
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //下面的暂停继续和退出方法全部调用的是MediaPlayer自带的方法
        public void pausePlay() {
            mMediaPlayer.pause();//暂停播放音乐
        }

        public void continuePlay() {
            mMediaPlayer.start();//继续播放音乐
        }

        public void seekTo(int progress) {
            mMediaPlayer.seekTo(progress);//设置音乐的播放位置
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();//停止播放音乐
        }
        mMediaPlayer.release();//释放占用的资源
        mMediaPlayer = null;//将player置为空
    }
}
