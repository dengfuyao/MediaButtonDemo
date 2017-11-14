package come.dfy.mediabuttondemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import come.dfy.mediabuttondemo.receiver.MediaButtonReceiver;

public class MainActivity extends AppCompatActivity {

    private AudioManager  mAudioManager;
    private ComponentName mComponentName;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // AudioManager注册一个MediaButton对象
        mComponentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
    }



    @Override
    protected void onResume() {
        mAudioManager.registerMediaButtonEventReceiver(mComponentName);
        registerReceiver(headSetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 取消注册
        mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
        unregisterReceiver(headSetReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAudioManager = null;
        mComponentName = null;
        super.onDestroy();
    }

    private final BroadcastReceiver headSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                // phone headset plugged
                if (intent.getIntExtra("state", 0) == 1) {
                    // do something
//                  Log.d(TAG, "耳机检测：插入");
                Toast.makeText(context, "耳机检测：插入", Toast.LENGTH_SHORT) .show();
                    mAudioManager.registerMediaButtonEventReceiver(mComponentName);
                    // phone head unplugged
                } else {
                    // do something
//                  Log.d(TAG, "耳机检测：没有插入");
                 Toast.makeText(context, "耳机检测：没有插入", Toast.LENGTH_SHORT).show();
                    mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);

                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) { //按下了耳机键
            if (event.getRepeatCount() == 0) {  //如果长按的话，getRepeatCount值会一直变大
                //短按
            } else {
                //长按
                mVibrator = (Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
                //设置震动周期，数组表示时间：等待+执行，单位是毫秒，下面操作代表:等待100，执行100，等待100，执行1000，
                //后面的数字如果为-1代表不重复，之执行一次，其他代表会重复，0代表从数组的第0个位置开始
                mVibrator.vibrate(new long[]{100, 100, 100, 1000}, 0);
                Toast.makeText(this, "长按", Toast.LENGTH_SHORT).show();

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
