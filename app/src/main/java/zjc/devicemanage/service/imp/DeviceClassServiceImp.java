package zjc.devicemanage.service.imp;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.model.DeviceClassList;
import zjc.devicemanage.service.DeviceClassService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class DeviceClassServiceImp implements DeviceClassService {
    private DeviceClassList deviceClassListFromJson;
    private void parseJSONtoDeviceClassList(String responseData)  {
        Gson gson=new Gson();
        deviceClassListFromJson = gson.fromJson(responseData,new TypeToken<DeviceClassList>(){}.getType());
        Log.i("zjc",deviceClassListFromJson.toString());
    }
    // 添加回调类
    private HomeFragment homeFragment;
    // 注入回调类的构造函数
    public DeviceClassServiceImp(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }
    @Override
    public void findAllDeviceClass() {
        // 构造findAllDeviceClass的tomcat服务请求URL
        String findAllDeviceClassURL = MyHttpUtil.host + "/DeviceManage/findAllDeviceClass";
        MyHttpUtil.sendOkhttpGetRequest(findAllDeviceClassURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoDeviceClassList(response.body().string());
                homeFragment.showAllDeviceClassCallback(deviceClassListFromJson);
            }
        });
    }
}