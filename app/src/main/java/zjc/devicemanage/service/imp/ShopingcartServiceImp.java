package zjc.devicemanage.service.imp;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.fragment.ShopingcartFragment;
import zjc.devicemanage.model.ShopingcartList;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class ShopingcartServiceImp implements ShopingcartService {
    private ShopingcartList shopingcartListFromJson;
    private void parseJSONtoShopingcartList(String responseData)  {
        Gson gson=new Gson();
        shopingcartListFromJson = gson.fromJson(responseData,
                new TypeToken<ShopingcartList>(){}.getType());
        Log.i("zjc", shopingcartListFromJson.toString());
    }

    // 添加回调类
    private ShopingcartFragment shopingcartFragment;
    // 注入回调类的构造函数
    public ShopingcartServiceImp(ShopingcartFragment shopingcartFragment) {
        this.shopingcartFragment = shopingcartFragment;
    }

    private HomeFragment homeFragment;
    public ShopingcartServiceImp(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }

    public void findAllShopingcart (){
        // 构造findAllShopingcart的tomcat服务请求URL
        String findAllShopingcartURL = MyHttpUtil.host + "/DeviceManage/findAllShopingcart";
        MyHttpUtil.sendOkhttpGetRequest(findAllShopingcartURL, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoShopingcartList(response.body().string());
                shopingcartFragment.showAllShopingcartCallback(shopingcartListFromJson);
            }
        });
    }

    public void findAllShopingcartByUserId(String userId) {
        // 构造findAllShopingcartByUserId的tomcat服务请求URL
        String findAllShopingcartByUserId = MyHttpUtil.host + "/DeviceManage/findAllShopingcartByUserId?userId=" + userId;
        MyHttpUtil.sendOkhttpGetRequest(findAllShopingcartByUserId, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoShopingcartList(response.body().string());
                shopingcartFragment.showAllShopingcartCallback(shopingcartListFromJson);
            }
        });
    }

    @Override
    public void addShopingcart(final String addDeviceID, String addBuyNum, String addUserID) {
        // 构造addShopingcart的tomcat服务请求URL
        String addShopingcart = MyHttpUtil.host + "/DeviceManage/addShopingcart?addDeviceID=" + addDeviceID
                + "&addBuyNum=" + addBuyNum + "&addUserID=" + addUserID;
        MyHttpUtil.sendOkhttpGetRequest(addShopingcart, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                homeFragment.showAddShopingcartCallback(addDeviceID);
            }
        });
    }
}
