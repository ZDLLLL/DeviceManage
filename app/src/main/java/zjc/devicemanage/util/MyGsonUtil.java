package zjc.devicemanage.util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zjc.devicemanage.model.School;
import zjc.devicemanage.model.SchoolList;

public class MyGsonUtil {
    static public School parseJSONtoSchool(String responseData) {
        try {
            School schoolFromJson = new School();
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray jsonArray = jsonObject.getJSONArray("school");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            String name = jsonObject1.getString("name");
            schoolFromJson.setName(name);
            String province = jsonObject1.getString("province");
            schoolFromJson.setProvince(province);
            String city = jsonObject1.getString("city");
            schoolFromJson.setCity(city);
            return schoolFromJson;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    static public String schoolToString(School school){
        String schoolStr = "学校显示：\n";
        schoolStr += "学校名：" + school.getName() + ",所在省:" + school.getProvince()  + ",所在市:" + school.getCity();
        return schoolStr;
    }

    static public SchoolList parseJSONtoSchoolList(String responseData) {
        SchoolList schoolList;
        Gson gson=new Gson();
        schoolList = gson.fromJson(responseData,new TypeToken<SchoolList>(){}.getType());
        return schoolList;
    }

    static public String schoolListToString(SchoolList list){
        String schoolListStr = "学校列表显示：\n";
        for (School school : list.getSchoolList()){
            schoolListStr +=  "学校名：" + school.getName() + ",所在省:" + school.getProvince()  + ",所在市:" + school.getCity() + "\n";
        }
        return schoolListStr;
    }
}
