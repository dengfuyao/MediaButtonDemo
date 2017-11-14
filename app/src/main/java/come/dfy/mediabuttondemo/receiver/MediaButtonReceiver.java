package come.dfy.mediabuttondemo.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.widget.Toast;

import static android.util.Log.i;

/**
 * 作者：dfy on 14/11/2017 13:13
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class MediaButtonReceiver extends BroadcastReceiver {
    private static String TAG = "MediaButtonReceiver";
    private Vibrator mVibrator;

    @Override
    public void onReceive(Context context, Intent intent) {

        // 获得Action
        String intentAction = intent.getAction();
        // 获得KeyEvent对象
        KeyEvent keyEvent = (KeyEvent) intent
                .getParcelableExtra(Intent.EXTRA_KEY_EVENT);

        i(TAG, "Action ---->" + intentAction + "  KeyEvent----->"
                + keyEvent.toString());
        // 按下 / 松开 按钮
        int keyAction = keyEvent.getAction();

        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)
                && (KeyEvent.ACTION_DOWN == keyAction)) {
            // 获得按键字节码
            int keyCode = keyEvent.getKeyCode();

            // 获得事件的时间
            long downtime = keyEvent.getEventTime();

            // 获取按键码 keyCode
            StringBuilder sb = new StringBuilder();
//          // 这些都是可能的按键码 ， 打印出来用户按下的键
            if (KeyEvent.KEYCODE_MEDIA_NEXT == keyCode) {
                sb.append("KEYCODE_MEDIA_NEXT");
            }
            // 说明：当我们按下MEDIA_BUTTON中间按钮时，实际出发的是 KEYCODE_HEADSETHOOK 而不是
            // KEYCODE_MEDIA_PLAY_PAUSE
            if (KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == keyCode) {
                sb.append("KEYCODE_MEDIA_PLAY_PAUSE");

            }
            if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) {
                sb.append("KEYCODE_HEADSETHOOK");
            }
            if (KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyCode) {
                sb.append("KEYCODE_MEDIA_PREVIOUS");
            }
            if (KeyEvent.KEYCODE_MEDIA_STOP == keyCode) {
                sb.append("KEYCODE_MEDIA_STOP");
            }
            // 输出点击的按键码
            //获取手机震动服务
            mVibrator = (Vibrator) context.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
            //设置震动周期，数组表示时间：等待+执行，单位是毫秒，下面操作代表:等待100，执行100，等待100，执行1000，
            //后面的数字如果为-1代表不重复，之执行一次，其他代表会重复，0代表从数组的第0个位置开始
            mVibrator.vibrate(new long[]{100, 100, 100, 1000}, -1);
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        } else if (KeyEvent.ACTION_UP == keyAction) {
            i("chengjie", "aaa");
            if (mVibrator != null) {
                mVibrator.cancel();
            }
        }
    }

}
