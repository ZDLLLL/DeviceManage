package zjc.devicemanage.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zjc.devicemanage.ArcFace.common.Constant;
import zjc.devicemanage.R;
import zjc.devicemanage.model.User;
import zjc.devicemanage.service.UserService;
import zjc.devicemanage.service.imp.UserServiceImp;
import zjc.devicemanage.util.MyApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zjc.devicemanage.util.TimeCountUtil;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private User loginUser;
    private UserService userService;
    private static final String TAG = "LoginActivity";
    private static final String APP_ID = "1110167739";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private TextView reg_arcface;
    private Toast toast = null;
    private TextView mob_bttv;
    private TextView login_bttv;
    private LinearLayout login_ll;
    private LinearLayout mob_ll;
    private TimeCountUtil mTimeCountUtil; //按钮倒计时
    private Button mob_verification_send;
    private EditText mob_username;
    private EditText mob_password;
    private Button mob_button;
    String phone;
    EventHandler eh;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private FaceEngine faceEngine = new FaceEngine();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,LoginActivity.this.getApplicationContext());
        // 获得3个UI控件对象
        initView();
        mTimeCountUtil = new TimeCountUtil(mob_verification_send, 60000, 1000);

        InitEngine();

    }
    public void  initView(){
        etUsername=findViewById(R.id.login_username);
        etPassword=findViewById(R.id.login_password);
        btnLogin=findViewById(R.id.login_button);
        reg_arcface=findViewById(R.id.reg_arcface);
        mob_bttv=findViewById(R.id.mob_bttv);
        login_bttv=findViewById(R.id.login_bttv);
        login_ll=findViewById(R.id.login_ll);
        mob_ll=findViewById(R.id.mob_ll);
        mob_username=findViewById(R.id.mob_username);
        mob_button=findViewById(R.id.mob_button);
        mob_password=findViewById(R.id.mob_password);
        mob_verification_send=findViewById(R.id.mob_verification_send);
        //验证码登录
        mob_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=false;
                SMSSDK.submitVerificationCode("86",mob_username.getText().toString(),mob_password.getText().toString());

                if(TextUtils.isEmpty(mob_username.getText().toString())){
                    flag=true;
                }
                if(TextUtils.isEmpty(mob_password.getText().toString())){
                    flag=true;
                }
                if(flag){
                    Toast.makeText(LoginActivity.this,"请填写账号/验证码后再进行登陆",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //验证码登录切换
        mob_bttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_bttv.setVisibility(View.VISIBLE);
                mob_bttv.setVisibility(View.INVISIBLE);
                mob_ll.setVisibility(View.VISIBLE);
                login_ll.setVisibility(View.INVISIBLE);

            }
        });
        login_bttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mob_bttv.setVisibility(View.VISIBLE);
                login_bttv.setVisibility(View.INVISIBLE);
                login_ll.setVisibility(View.VISIBLE);
                mob_ll.setVisibility(View.INVISIBLE);

            }
        });
        reg_arcface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterAndRecognizeActivity.class);
                startActivity(intent);
            }
        });

        // 执行按钮btnLogin的onClick事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userService = new UserServiceImp(LoginActivity.this);
                userService.loginValidate(etUsername.getText().toString(),
                        etPassword.getText().toString());
            }
        });
        mob_verification_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86", mob_username.getText().toString());
                phone = mob_username.getText().toString();
            }
        });


        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    // 回调函数loginCallback
    public void loginCallback(User loginUser) {
        this.loginUser = loginUser;
        if (this.loginUser.getUserID() == null){
            MyApplication.subThreadToast("账号用户名或者密码输入错误");
        }else {
            // 将已登录成功的用户编号写入偏好文件中
            MyApplication.setUser_id(this.loginUser.getUserID());
            // 不能采用第一种方式在子线程中显示Toast
            // MyApplication.subThreadToast("用户编号（" + this.loginUser.getUserID() + ")登录成功");
            // 必须采用第二种方式在子线程中显示Toast
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginActivity.this,  "用户编号（" +
                                    LoginActivity.this.loginUser.getUserID() + ")登录成功",
                            Toast.LENGTH_LONG).show();
                }
            });
            Log.i("zjc","用户编号(" + this.loginUser.getUserID() + ")登录成功");
            // 跳转到MainActivity活动
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            // 关闭当前LoginActivity活动
            finish();
        }
    }
    public void qqlogin(View v){
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this,"all", mIUiListener);
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }
    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void InitEngine(){
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int activeCode = faceEngine.activeOnline(LoginActivity.this, Constant.APP_ID, Constant.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }


                        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
                        int res = faceEngine.getActiveFileInfo(LoginActivity.this,activeFileInfo);
                        if (res == ErrorInfo.MOK) {
                            Log.i(TAG, activeFileInfo.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {

            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) { // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    mTimeCountUtil.start();
                    Toast.makeText(getApplicationContext(),"发送验证码成功",Toast.LENGTH_LONG).show();

                } else {
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();

                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }
            } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证码验证通过的结果
                    Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    // TODO 处理错误的结果

                    ((Throwable) data).printStackTrace();
                    Toast.makeText(getApplicationContext(),"验证失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SMSSDK.registerEventHandler(eh);
    }

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
