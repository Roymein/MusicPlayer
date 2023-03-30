package com.android.cloud.player;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.cloud.R;
import com.android.cloud.server.MusicService;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static SeekBar mSeekBar;
    private static TextView sTvProgress;
    private static TextView sTvTotal;

    private ObjectAnimator mAnimator;
    private MusicService.MusicControl mMusicControl;
    private Intent intentExtra;
    private boolean mIsUnbind = false;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        intentExtra = getIntent();
        init();
    }

    private void init() {
        //进度条上小绿点的位置，也就是当前已播放时间
        sTvProgress = findViewById(R.id.tv_progress);
        //进度条的总长度，就是总时间
        sTvTotal = findViewById(R.id.tv_total);
        mSeekBar = findViewById(R.id.sb);
        TextView tvSongName = findViewById(R.id.song_name);

        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue_play).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);

        String name = intentExtra.getStringExtra("name");
        tvSongName.setText(name);
        Intent intent = new Intent(this, MusicService.class);

        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        //为滑动条添加事件监听，每个控件不同果然点击事件方法名都不同
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进当滑动条到末端时，结束动画
                if (progress == seekBar.getMax()) {
                    mAnimator.pause();//停止播放动画
                }
            }

            @Override
            //滑动条开始滑动时调用
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            //滑动条停止滑动时调用
            public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
                int progress = seekBar.getProgress();//获取seekBar的进度
                mMusicControl.seekTo(progress);//改变播放进度
            }
        });
        //声明并绑定音乐播放器的iv_music控件
        ImageView iconImageView = findViewById(R.id.iv_music);
        String position = intentExtra.getStringExtra("position");

        int i = Integer.parseInt(position);
        iconImageView.setImageResource(MusicFragment.sIcons[i]);
        //rotation和0f,360.0f就设置了动画是从0°旋转到360°
        mAnimator = ObjectAnimator.ofFloat(iconImageView, "rotation", 0f, 360.0f);
        mAnimator.setDuration(10000);//动画旋转一周的时间为10秒
        mAnimator.setInterpolator(new LinearInterpolator());//匀速
        mAnimator.setRepeatCount(-1);//-1表示设置动画无限循环
    }

    //判断服务是否被解绑
    private void unbind(boolean isUnbind) {
        //如果解绑了
        if (!isUnbind) {
            mMusicControl.pausePlay();//音乐暂停播放
            unbindService(mServiceConnection);//解绑服务
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play://播放按钮点击事件
                String position = intentExtra.getStringExtra("position");
                int i = Integer.parseInt(position);
                mMusicControl.play(i);
                mAnimator.start();
                break;

            case R.id.btn_pause://暂停按钮点击事件
                mMusicControl.pausePlay();
                mAnimator.pause();
                break;

            case R.id.btn_continue_play://继续播放按钮点击事件
                mMusicControl.continuePlay();
                mAnimator.start();
                break;

            case R.id.btn_exit://退出按钮点击事件
                unbind(mIsUnbind);
                mIsUnbind = true;
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind(mIsUnbind);
    }

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {//创建消息处理器对象
        //在主线程中处理从子线程发送过来的消息
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();//获取从子线程发送过来的音乐播放进度
            //获取当前进度currentPosition和总时长duration
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            //对进度条进行设置
            mSeekBar.setMax(duration);
            mSeekBar.setProgress(currentPosition);
            //歌曲是多少分钟多少秒钟
            int minute = duration / 1000 / 60;
            int second = duration / 1000 % 60;
            String strMinute = null;
            String strSecond = null;
            if (minute < 10) {//如果歌曲的时间中的分钟小于10
                strMinute = "0" + minute;//在分钟的前面加一个0
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {//如果歌曲中的秒钟小于10
                strSecond = "0" + second;//在秒钟前面加一个0
            } else {
                strSecond = second + "";
            }
            //这里就显示了歌曲总时长
            sTvTotal.setText(String.format("%s:%s", strMinute, strSecond));
            //歌曲当前播放时长
            minute = currentPosition / 1000 / 60;
            second = currentPosition / 1000 % 60;
            if (minute < 10) {//如果歌曲的时间中的分钟小于10
                strMinute = "0" + minute;//在分钟的前面加一个0
            } else {
                strMinute = minute + " ";
            }
            if (second < 10) {//如果歌曲中的秒钟小于10
                strSecond = "0" + second;//在秒钟前面加一个0
            } else {
                strSecond = second + " ";
            }

            sTvProgress.setText(String.format("%s:%s", strMinute, strSecond));
        }
    };
}
