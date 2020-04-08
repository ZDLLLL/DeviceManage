package zjc.devicemanage.activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import zjc.devicemanage.util.MyApplication;

public class WelcomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获得偏好选项userid的值
        final String user_id = MyApplication.getUser_id();
        // 新建一个独立的线程
        Handler handler = new Handler();
        // 开启线程，运行run方法，根据userid的值跳转到不同的活动
        handler.postDelayed(new Runnable() {
            public void run() {
                // 用于活动跳转
                Intent intent;
                if (TextUtils.isEmpty(user_id)) {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    Toast.makeText(WelcomeActivity.this, "用户编号（" + user_id + "）登录成功", Toast.LENGTH_LONG).show();
                }
                // 利用intent跳转到下一个活动
                startActivity(intent);
                // 关闭当前活动WelcomeActivity
                finish();
            }
        }, 2000);
    }
}

