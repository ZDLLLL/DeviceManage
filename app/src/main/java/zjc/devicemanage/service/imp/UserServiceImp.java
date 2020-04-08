package zjc.devicemanage.service.imp;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.activity.LoginActivity;
import zjc.devicemanage.model.User;
import zjc.devicemanage.service.UserService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class UserServiceImp implements UserService {
    private User userFromJson= new User();
    private void parseJSONtoUser(String responseData) {
        try {
            JSONObject jsonObject=new JSONObject(responseData);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            String userId=jsonObject1.getString("UserID");
            userFromJson.setUserID(userId);
            String userName=jsonObject1.getString("UserName");
            userFromJson.setUserName(userName);
            String userPassword=jsonObject1.getString("UserPassword");
            userFromJson.setUserPassword(userPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // 添加回调类
    private LoginActivity loginActivity;
    // 注入回调类的构造函数
    public UserServiceImp(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void loginValidate(String username, String password) {
        // 构造loginValidate的tomcat服务请求URL
        String loginValidateURL = MyHttpUtil.host + "/DeviceManage/loginValidate";
        loginValidateURL += "?username=" + username + "&password=" + password;
        final String finalLoginValidateURL = loginValidateURL;
        MyHttpUtil.sendOkhttpGetRequest(loginValidateURL, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc", finalLoginValidateURL);

                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoUser(response.body().string());
                loginActivity.loginCallback(userFromJson);
            }
        });
    }
}
