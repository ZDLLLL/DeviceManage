package zjc.devicemanage.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import zjc.devicemanage.R;
import zjc.devicemanage.activity.InformationDetailActivity;
import zjc.devicemanage.adapter.InformationAdapter;
import zjc.devicemanage.model.InformationList;
import zjc.devicemanage.service.InformationService;
import zjc.devicemanage.service.imp.InformationServiceImp;

public class InformationFragment extends Fragment {
    // 布局fragment_information.xml对应的视图对象
    private View fragment_informationView;
    // 布局fragment_information.xml中的列表控件对象
    private RecyclerView fragment_information_rv;
    // 咨询适配器，将链接RecyclerView控件和数据源InformationList
    private InformationAdapter informationAdapter;
    // 数据源，该属性必须new分配内存，否则后面引用到该属性时将报错
    private InformationList informationList = new InformationList();

    public void initInformationData(){
        InformationService informationService = new InformationServiceImp(this);
        informationService.findAllInformation();
    }


    public InformationFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //步骤1：读取布局资源文件fragment_information.xml，并生成视图界面对象
        fragment_informationView = inflater.inflate(R.layout.fragment_information, container, false);
        //步骤2：获取列表控件对象fragment_information_recyclerView
        fragment_information_rv = fragment_informationView.findViewById(R.id.fragment_information_rv);
        //步骤3：创建线性布局，设置列表控件对象的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_information_rv.setLayoutManager(linearLayoutManager);
        fragment_information_rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //步骤4：新建咨询适配器对象，连接数据源
        informationAdapter = new InformationAdapter(informationList);
        //步骤5：列表控件对象绑定适配器对象
        fragment_information_rv.setAdapter(informationAdapter);
        //步骤6：执行initInformationData方法，获取远程服务器数据，并回调显示
        initInformationData();
        return fragment_informationView;
    }



    // 1. 该函数是回调函数，它由InformationServiceImp类的findAllInformation的onResponse负责调用
    // 2. onResponse中将咨询列表通过形参informationListFromJson传入该函数
    // 3. 在该函数中，将把咨询列表放入InformationAdapter中，并显示在列表控件information_recyclerView中
    public void showAllInformationCallback(final InformationList informationListFromJson) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                informationAdapter.setInformationList(informationListFromJson);
                fragment_information_rv.setAdapter(informationAdapter);
                informationAdapter.notifyDataSetChanged();
                informationAdapter.setOnItemClickListener(new InformationAdapter.OnItemClickListener() {
                    public void onItemClick(int informationID) {
                        Toast.makeText(getContext(), "咨询编号" + informationID + "被点击", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InformationFragment.this.getActivity(), InformationDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("informationID", informationID);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
