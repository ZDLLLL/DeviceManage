package zjc.devicemanage.service.imp;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.model.DeviceList;
import zjc.devicemanage.service.DeviceService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class DeviceServiceImp implements DeviceService {
    private DeviceList deviceListFromJson;
    private void parseJSONtoDeviceList(String responseData)  {
        Gson gson=new Gson();
        deviceListFromJson = gson.fromJson(responseData, new TypeToken<DeviceList>(){}.getType());
        Log.i("zjc",deviceListFromJson.toString());
    }

    // 添加回调类
    private HomeFragment homeFragment;
    // 注入回调类的构造函数
    public DeviceServiceImp(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }
    public void findAllDevice() {
        // 构造findAllDevice的tomcat服务请求URL
        String findAllDeviceURL = MyHttpUtil.host + "/DeviceManage/findAllDevice";
        MyHttpUtil.sendOkhttpGetRequest(findAllDeviceURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoDeviceList(response.body().string());
                homeFragment.showAllDeviceCallback(deviceListFromJson);
            }
        });
    }
    public void findDeviceByDeviceClassId(int deviceClassId){
        // 构造findDeviceByDeviceClassId的tomcat服务请求URL
        String findDeviceByDeviceClassId = MyHttpUtil.host + "/DeviceManage/findDeviceByDeviceClassId?deviceClassId=" + new Integer(deviceClassId).toString();
        MyHttpUtil.sendOkhttpGetRequest(findDeviceByDeviceClassId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoDeviceList(response.body().string());
                homeFragment.showDeviceByDeviceClassIdCallback(deviceListFromJson);
            }
        });
    }


}
