package zjc.devicemanage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import zjc.devicemanage.model.School;
import zjc.devicemanage.model.SchoolList;
import zjc.devicemanage.util.MyGsonUtil;
import zjc.devicemanage.util.MyHttpUtil;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // 1. 单个对象的JSON解析
        String schoolJson = "{'school': [{'name': '学军中学','province': '浙江','city':'杭州'}]}";
        School school = MyGsonUtil.parseJSONtoSchool(schoolJson);
        String showContent = MyGsonUtil.schoolToString(school);
        Log.i("zjc", showContent);

        // 2. 多个对象的JSON解析
        String schoolListJson = "{'schoolList': [{'name': '学军中学','province': '浙江','city':'杭州'},{'name': '鲁迅中学','province': '浙江','city':'绍兴'},{'name': '孝实中学','province': '浙江','city':'宁波'}]}";
        SchoolList schools = MyGsonUtil.parseJSONtoSchoolList(schoolListJson);
        showContent = MyGsonUtil.schoolListToString(schools);
        Log.i("zjc", showContent);
        assertEquals("zjc.devicemanage", appContext.getPackageName());
    }
}
