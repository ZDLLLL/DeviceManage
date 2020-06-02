package zjc.devicemanage.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import zjc.devicemanage.R;
import zjc.devicemanage.util.MyHttpUtil;

public class InformationDetailActivity extends AppCompatActivity {
    private WebView activity_infomation_detail_wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        activity_infomation_detail_wv = findViewById(R.id.activity_infomation_detail_wv);
        //通过Bundle获得咨询编号参数
        Bundle bundle= getIntent().getExtras();
        int informationID = bundle.getInt("informationID");
        //设置WebView控件的3个操作
        activity_infomation_detail_wv.getSettings().setJavaScriptEnabled(true);
        activity_infomation_detail_wv.setWebViewClient(new WebViewClient());
        String showInformationURL = MyHttpUtil.host + "/DeviceManage/showInformationByIdFromAppPortol?infoId=";
        activity_infomation_detail_wv.loadUrl(showInformationURL+informationID);
    }
}
