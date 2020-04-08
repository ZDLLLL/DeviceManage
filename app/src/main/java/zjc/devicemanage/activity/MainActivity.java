package zjc.devicemanage.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import zjc.devicemanage.JPush.ExampleUtil;
import zjc.devicemanage.R;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.fragment.InformationFragment;
import zjc.devicemanage.fragment.MineFragment;
import zjc.devicemanage.fragment.ShopingcartFragment;
import zjc.devicemanage.util.MyApplication;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // 标题文本控件
    private TextView mainTitle_tv;
    // 4个Fragment控件
    private HomeFragment homeFragment;
    private InformationFragment informationFragment;
    private ShopingcartFragment shoppingcartFragment;
    private MineFragment mineFragment;
    // 4个标签Tab布局控件
    private LinearLayout tab_home_ll;
    private LinearLayout tab_information_ll;
    private LinearLayout tab_shoppingcart_ll;
    private LinearLayout tab_mine_ll;
    // 4个图片按钮控件
    private ImageButton tab_home_ib;
    private ImageButton tab_information_ib;
    private ImageButton tab_shoppingcart_ib;
    private ImageButton tab_mine_ib;
    // 4个文本控件
    private TextView tab_home_tv;
    private TextView tab_information_tv;
    private TextView tab_shoppingcart_tv;
    private TextView tab_mine_tv;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerMessageReceiver();
        initControls();
        initControlsEvent();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 用4种fragment视图控件替换activity_main.xml中的FrameLayout控件
        fragmentTransaction.replace(R.id.main_fl, fragment);
        fragmentTransaction.commit();
        if (fragment instanceof HomeFragment){
            mainTitle_tv.setText("首页");
            tab_home_ib.setImageResource(R.drawable.home_click);
            tab_home_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof InformationFragment){
            mainTitle_tv.setText("咨询");
            tab_information_ib.setImageResource(R.drawable.information_click);
            tab_information_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof ShopingcartFragment){
            mainTitle_tv.setText("购物车");
            tab_shoppingcart_ib.setImageResource(R.drawable.shoppingcart_click);
            tab_shoppingcart_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof MineFragment){
            mainTitle_tv.setText("我");
            tab_mine_ib.setImageResource(R.drawable.mine_click);
            tab_mine_tv.setTextColor(Color.parseColor("#00BFFF"));
        }
    }

    private void initControls(){
        // 载入控件
        mainTitle_tv = findViewById(R.id.mainTitle_tv);
        tab_home_ll = findViewById(R.id.tab_home_ll);
        tab_information_ll = findViewById(R.id.tab_information_ll);
        tab_shoppingcart_ll = findViewById(R.id.tab_shoppingcart_ll);
        tab_mine_ll = findViewById(R.id.tab_mine_ll);
        tab_home_ib = findViewById(R.id.tab_home_ib);
        tab_information_ib = findViewById(R.id.tab_information_ib);
        tab_shoppingcart_ib = findViewById(R.id.tab_shoppingcart_ib);
        tab_mine_ib = findViewById(R.id.tab_mine_ib);
        tab_home_tv = findViewById(R.id.tab_home_tv);
        tab_information_tv = findViewById(R.id.tab_information_tv);
        tab_shoppingcart_tv = findViewById(R.id.tab_shoppingcart_tv);
        tab_mine_tv = findViewById(R.id.tab_mine_tv);
        // 生成4个Fragment对象
        homeFragment = new HomeFragment();
        informationFragment = new InformationFragment();
        shoppingcartFragment = new ShopingcartFragment();
        mineFragment = new MineFragment();
    }

    private void resetImageAndTextColor(){
        // 重置首页按钮图片和文本颜色
        tab_home_ib.setImageResource(R.drawable.home);
        tab_home_tv.setTextColor(Color.parseColor("#272727"));
        // 重置咨询按钮图片和文本颜色
        tab_information_ib.setImageResource(R.drawable.information);
        tab_information_tv.setTextColor(Color.parseColor("#272727"));
        // 重置购物车按钮图片和文本颜色
        tab_shoppingcart_ib.setImageResource(R.drawable.shoppingcart);
        tab_shoppingcart_tv.setTextColor(Color.parseColor("#272727"));
        // 重置我按钮图片和文本颜色
        tab_mine_ib.setImageResource(R.drawable.mine);
        tab_mine_tv.setTextColor(Color.parseColor("#272727"));
    }
    @Override
    public void onClick(View v) {
        resetImageAndTextColor();
        switch (v.getId()){
            case R.id.tab_home_ll:
                loadFragment(homeFragment);
                break;
            case R.id.tab_information_ll:
                loadFragment(informationFragment);
                break;
            case R.id.tab_shoppingcart_ll:
                loadFragment(shoppingcartFragment);
                break;
            case R.id.tab_mine_ll:
                loadFragment(mineFragment);
                break;
        }
    }

    private  void initControlsEvent(){
        tab_home_ll.setOnClickListener(this);
        tab_information_ll.setOnClickListener(this);
        tab_shoppingcart_ll.setOnClickListener(this);
        tab_mine_ll.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }

                }
            } catch (Exception e){
            }
        }
    }
}
