package zjc.devicemanage.fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import zjc.devicemanage.R;
import zjc.devicemanage.adapter.DeviceAdapter;
import zjc.devicemanage.adapter.DeviceClassAdapter;
import zjc.devicemanage.adapter.EmbedDeviceAdapter;
import zjc.devicemanage.adapter.SampleAdapter;
import zjc.devicemanage.adapter.TestAdapter;
import zjc.devicemanage.model.DeviceClassList;
import zjc.devicemanage.model.DeviceList;
import zjc.devicemanage.model.User;
import zjc.devicemanage.service.DeviceClassService;
import zjc.devicemanage.service.DeviceService;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.service.imp.DeviceClassServiceImp;
import zjc.devicemanage.service.imp.DeviceServiceImp;
import zjc.devicemanage.service.imp.ShopingcartServiceImp;
import zjc.devicemanage.util.MyApplication;

public class HomeFragment extends Fragment {
    private Banner banner;
    // fragment在活动父窗口中的视图名称
    private View fragment_homeView;
    // fragment_home.xml中定义的列表控件
    private RecyclerView fragment_home_recyclerView;
    // fragment_homeView对应的Adapter对象
    private TestAdapter testAdapter;
    private DeviceClassAdapter deviceClassAdapter;
    private DeviceAdapter deviceAdapter;
    // embeddevice_viewholder1.xml中定义的整体视图
    private View embeddevice_viewholder1View;
    // embeddevice_viewholder1.xml中定义的列表控件
    private RecyclerView embeddevice_viewholder1_recyclerView;
    // embeddevice_viewholder1_recyclerView对应的Adapter对象
    private EmbedDeviceAdapter embedDeviceAdapter;

    public HomeFragment() {
    }

    private void initBanner() {
        List images = new ArrayList();
        images.add(R.drawable.bannerimg1);
        images.add(R.drawable.bannerimg2);
        images.add(R.drawable.bannerimg3);
        images.add(R.drawable.bannerimg4);
        images.add(R.drawable.bannerimg5);
        // homeView是在onCreateView函数中保存的Fragment视图控件变量
        // 在homeView中利用findViewById函数获得Banner对象
        banner = fragment_homeView.findViewById(R.id.home_banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // 利用Glide加载图片
                Glide.with(getContext()).load(path).into(imageView);
            }
        });
        banner.setImages(images);
        banner.start();
    }

    private void initUsers() {
        List<User> mUser;
        // 1. 创建模拟数据
        User user1 = new User();
        user1.setUserName("张三");
        user1.setUserPassword("123");
        User user2 = new User();
        user2.setUserName("李四");
        user2.setUserPassword("456");
        mUser = new ArrayList<User>();
        mUser.add(user1);
        mUser.add(user2);
        // 2. 创建适配器testAdapter，并将其作为home_recyclerView控件的适配器
        testAdapter = new TestAdapter(mUser);
        testAdapter.setOnItemClickListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(fragment_home_recyclerView.getContext(), "第" + position + "项被点击", Toast.LENGTH_LONG).show();
            }
        });
        fragment_home_recyclerView.setAdapter(testAdapter);
    }

    private SampleAdapter sampleAdapter;

    private void initContinentsAndCountries() {
        // 洲名：亚洲、欧洲、北美洲
        // 国家名：中国、印度、韩国、日本、英国、法国、德国、西班牙、美国、加拿大、古巴、墨西哥
        // 首都名：北京、新德里、首尔、东京、伦敦、巴黎、柏林、马德里、华盛顿、渥太华、哈瓦那、墨西哥城
        final List<String> continents = new ArrayList<String>();// 洲名列表
        final List<String> countries = new ArrayList<String>(); // 国家列表
        List<String> capitals = new ArrayList<String>();
        ; // 首都列表
        // 1. 创建模拟数据
        continents.add("亚洲");
        continents.add("欧洲");
        continents.add("北美洲");
        countries.add("中国");
        countries.add("印度");
        countries.add("韩国");
        countries.add("日本");
        countries.add("英国");
        countries.add("法国");
        countries.add("德国");
        countries.add("西班牙");
        countries.add("美国");
        countries.add("加拿大");
        countries.add("古巴");
        countries.add("墨西哥");
        capitals.add("北京");
        capitals.add("新德里");
        capitals.add("首尔");
        capitals.add("东京");
        capitals.add("伦敦");
        capitals.add("巴黎");
        capitals.add("柏林");
        capitals.add("马德里");
        capitals.add("华盛顿");
        capitals.add("渥太华");
        capitals.add("哈瓦那");
        capitals.add("墨西哥城");
        // 2. 创建适配器sampleAdapter，并将其作为home_recyclerView控件的适配器
        sampleAdapter = new SampleAdapter(continents, countries, capitals);
        fragment_home_recyclerView.setAdapter(sampleAdapter);
        sampleAdapter.setOnItemClickListener(new SampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int continentNum, int countryNum) {
                Toast.makeText(fragment_home_recyclerView.getContext(), "第" + position + "个单元项：" + continents.get(continentNum) + "-" + countries.get(countryNum) + "被点击", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemClick(int position, int continentNum) {
                Toast.makeText(fragment_home_recyclerView.getContext(), "第" + position + "个单元项：" + continents.get(continentNum) + "被点击", Toast.LENGTH_LONG).show();
            }
        });
    }

    private DeviceList deviceList = new DeviceList();
    private DeviceClassList deviceClassList = new DeviceClassList();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // 步骤1：
        // 读取布局资源文件embeddevice_viewholder1.xml，并生成视图界面对象
        embeddevice_viewholder1View = inflater.inflate(R.layout.embeddevice_viewholder1, container, false);
        // 生成设备列表适配器的第一种单元项视图界面
        embeddevice_viewholder1_recyclerView = embeddevice_viewholder1View.findViewById(R.id.embeddevice_viewholder1_deviceclass_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        embeddevice_viewholder1_recyclerView.setLayoutManager(gridLayoutManager);
        // 生成设备分类列表适配器DeviceClassAdapter
        deviceClassAdapter = new DeviceClassAdapter(deviceClassList);

        // 步骤2：
        // 将设备分类列表视图RecyclerView控件绑定适配器
        embeddevice_viewholder1_recyclerView.setAdapter(deviceClassAdapter);

        // 步骤3：
        // 读取布局资源文件fragment_home.xml，并生成视图界面对象
        fragment_homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initBanner();
        // 生成设备列表视图recyclerView
        fragment_home_recyclerView = fragment_homeView.findViewById(R.id.fragment_home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_home_recyclerView.setLayoutManager(linearLayoutManager);
        fragment_home_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // 生成设备列表适配器EmbedDeviceAdapter
        embedDeviceAdapter = new EmbedDeviceAdapter(deviceList);
        // 必须设置embedDeviceAdapter适配器的第一种单元项视图，否则将报错
        embedDeviceAdapter.setRecyclerViewDeviceClass(embeddevice_viewholder1View);

        // 步骤4：
        // 将设备列表视图控件RecyclerView绑定适配器
        fragment_home_recyclerView.setAdapter(embedDeviceAdapter);

        // 步骤5：
        // 调用initDeviceClassData，实现设备分类列表视图的数据显示
        initDeviceClassData();
        return fragment_homeView;
    }

    /*
        1. 在DeviceClassServiceImp类中通过okhttp异步访问数据库，获取设备分类的json串；
        2. 在DeviceClassServiceImp类中解析得到deviceClassList，并传递给回调函数showAllDeviceClassCallback；
        3. 在回调函数showAllDeviceClassCallback中给适配器deviceClassAdapter新增数据，并通知数据已改变，从而更新deviceClassAdapter所绑定的视图控件的显示值（如fragment_home_recyclerView和embeddevice_viewholder1_recyclerView）
    */
    public void initDeviceClassData() {
        DeviceClassService deviceClassService = new DeviceClassServiceImp(this);
        deviceClassService.findAllDeviceClass();
    }

    /*  1. 该函数是回调函数，它由DeviceClassServiceImp类的findAllDeviceClass的onResponse负责调用
        2. onResponse中将设备分类列表通过形参deviceClassListFromJson传入showAllDeviceClassCallback函数
        3. 在showAllDeviceClassCallback函数中，给适配器deviceClassAdapter新增数据，并通知数据已改变，从而更新deviceClassAdapter所绑定的视图控件的显示值（如fragment_home_recyclerView和embeddevice_viewholder1_recyclerView）
    */
    public void showAllDeviceClassCallback(final DeviceClassList deviceClassListFromJson) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                deviceClassAdapter.setDeviceClassList(deviceClassListFromJson);
                deviceClassAdapter.notifyDataSetChanged();
                deviceClassAdapter.setOnItemClickListener(new DeviceClassAdapter.OnItemClickListener() {
                    public void onItemClick(int deviceClassID) {
                        Toast.makeText(getContext(), "设备分类编号"
                                + deviceClassID + "被点击", Toast.LENGTH_LONG).show();
                        initDeviceData(deviceClassID);
                    }
                });
            }
        });
    }

    public void initDeviceData() {
        DeviceService deviceService = new DeviceServiceImp(this);
        deviceService.findAllDevice();
    }


    // 1. 该函数是回调函数，它由DeviceServiceImp类的findAllDevice的onResponse负责调用
    // 2. onResponse中将咨询列表通过形参deviceListFromJson传入该函数
    // 3. 在该函数中，将把设备列表放入DeviceAdapter中，并显示在列表控件home_recyclerView中
    public void showAllDeviceCallback(final DeviceList deviceListFromJson) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                deviceAdapter = new DeviceAdapter(deviceListFromJson);
                fragment_home_recyclerView.setAdapter(deviceAdapter);
                deviceAdapter.notifyDataSetChanged();
                deviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
                    public void onItemClick(int deviceID) {
                        Toast.makeText(fragment_home_recyclerView.getContext(), "设备编号" +
                                deviceID + "被点击", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void initDeviceData(int deviceClassID) {
        DeviceService deviceService = new DeviceServiceImp(this);
        deviceService.findDeviceByDeviceClassId(deviceClassID);
    }

    public void showDeviceByDeviceClassIdCallback(final DeviceList deviceListFromJson) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                embedDeviceAdapter.setDeviceList(deviceListFromJson);
                embedDeviceAdapter.notifyDataSetChanged();
                embedDeviceAdapter.setOnItemClickListener(new EmbedDeviceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int deviceID) {
                        Toast.makeText(getContext(), "设备编号" + deviceID + "被点击", Toast.LENGTH_LONG).show();
                    }
                });

                embedDeviceAdapter.setOnAddShopingcartClickListener(new EmbedDeviceAdapter.OnAddShopingcartClickListener() {
                    @Override
                    public void onAddShopingcartClick(int deviceID) {
                        ShopingcartService shopingcartService = new ShopingcartServiceImp(HomeFragment.this);
                        String userId = MyApplication.getUser_id();
                        shopingcartService.addShopingcart(new Integer(deviceID).toString(), "1", userId);
                    }
                });
            }
        });
    }
    public void showAddShopingcartCallback(final String addDeviceID) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "设备编号"+ addDeviceID + "加入购物车成功！", Toast.LENGTH_LONG).show();
            }
        });
    }

}
