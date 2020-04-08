package zjc.devicemanage.fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import zjc.devicemanage.R;
import zjc.devicemanage.adapter.ShopingcartAdapter;
import zjc.devicemanage.model.Shopingcart;
import zjc.devicemanage.model.ShopingcartList;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.service.imp.ShopingcartServiceImp;
import zjc.devicemanage.util.MyApplication;

public class ShopingcartFragment extends Fragment {
    // 布局fragment_shopingcart.xml对应的视图对象
    private View fragment_shopingcartView;
    private SwipeRefreshLayout fragment_shopingcart_SRL;
    private RecyclerView fragment_shopingcart_rv;
    // 全部总金额文本控件
    private TextView fragment_shopingcart_sum_tv;
    private ImageView fragment_shopingcart_choiceall_iv;
    // 购物车适配器，将链接RecyclerView控件和数据源shopingcartList
    private ShopingcartAdapter shopingcartAdapter;
    // 购物车列表数据源，该属性必须new分配内存，否则后面引用到该属性时将报错
    private ShopingcartList shopingcartList = new ShopingcartList();
    // 购物车项是否被选中Map，该属性必须new分配内存，否则后面引用到该属性时将报错
    private HashMap<Integer, Boolean> choiceMap = new HashMap<Integer, Boolean>();
    // 当前用户编号
    private String userId;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    // 是否全选所有的购物车项
    private Boolean isChoiceAll = false;


    public ShopingcartFragment() {
        // Required empty public constructor
    }

    public void initShopingcartData(){
        ShopingcartService shopingcartService = new ShopingcartServiceImp(this);
        shopingcartService.findAllShopingcart();
    }

    public void initShopingcartData(String userId){
        ShopingcartService shopingcartService = new ShopingcartServiceImp(this);
        shopingcartService.findAllShopingcartByUserId(userId);
    }
    // 批处理所有的购物车选中图片
    public void updateBatchChoiceMap(Boolean value){
        // 初始化购物车项是否被选中Map，所有的购物车列表项都选中或未选中
        // 如果形参value=true，则所有的购物车列表项都选中，否则都未选中
        Integer key=0;
        for(Shopingcart sc: shopingcartList.getResult()){
            choiceMap.put(key,value);
            key++;
        }
        // 更改每个列表项的选择图片
        shopingcartAdapter.setChoiceMap(choiceMap);
        shopingcartAdapter.notifyDataSetChanged();
        // 更新总金额
        updateMoneySum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //步骤1：读取布局资源文件fragment_shopingcart.xml，并生成视图界面对象
        fragment_shopingcartView = inflater.inflate(R.layout.fragment_shopingcart, container, false);
        //步骤2：获取滑动刷新控件、列表控件
        fragment_shopingcart_SRL = fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_SRL);
        fragment_shopingcart_rv = fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_rv);
        //步骤3：获取总金额文本控件、全选图片控件
        fragment_shopingcart_sum_tv = fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_sum_tv);
        fragment_shopingcart_choiceall_iv = fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_choiceall_iv);
        //步骤4：创建线性布局，设置列表控件对象的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_shopingcart_rv.setLayoutManager(linearLayoutManager);
        fragment_shopingcart_rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //步骤5：新建购物车适配器对象，连接数据源和选项Map
        shopingcartAdapter = new ShopingcartAdapter(shopingcartList,choiceMap);
        //步骤6：列表控件对象绑定适配器对象
        fragment_shopingcart_rv.setAdapter(shopingcartAdapter);
        //步骤7：执行带用户编号参数的initShopingcartData方法，获取远程服务器数据，并回调显示
        //initShopingcartData();
        userId = MyApplication.getUser_id();
        /*只有第一次创建Fragment，需要从远程服务器获取数据。
          如果后面从其他Fragment切换回该Fragment时，则不能从远程服务器获取数据，
          否则用户在该Fragment上的操作都被还原了*/
        if(shopingcartList.getResult().size() == 0){
            initShopingcartData(userId);
        }else{
            updateMoneySum();
        }
        //步骤8：滑动刷新控件的刷新事件处理
        fragment_shopingcart_SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shopingcartList.getResult().clear();
                if(fragment_shopingcart_rv.getScrollState()== RecyclerView.SCROLL_STATE_IDLE || !fragment_shopingcart_rv.isComputingLayout()){
                    shopingcartAdapter.notifyDataSetChanged();
                }
                // 重新获取当前用户的购物车列表项
                initShopingcartData(userId);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 延时1秒
                            Thread.sleep(1000);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // 刷新Recycler列表控件
                                    shopingcartAdapter.notifyDataSetChanged();
                                    // 下拉刷新控件暂停刷新
                                    fragment_shopingcart_SRL.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        //步骤9：全选图片按钮点击事件处理
        fragment_shopingcart_choiceall_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChoiceAll == false){
                    isChoiceAll = true;
                    updateBatchChoiceMap(true);
                }else{
                    isChoiceAll = false;
                    updateBatchChoiceMap(false);
                }
            }
        });
        //步骤10：创建“选中”图片控件的匿名Shopingcart适配器的onItemChoiceListener监听类，
        // 并调用setOnItemChoiceListener函数将其注入给Shopingcart适配器。同时在匿名监听类中重载onItemChoice方法，
        // 这样执行“选中”图片控件的点击操作时将执行匿名监听类中重载onItemChoice方法
        shopingcartAdapter.setOnItemChoiceListener(new ShopingcartAdapter.OnItemChoiceListener() {
            @Override
            public void onItemChoice(int position) {
                boolean isChoiced = choiceMap.get(position);
                ShopingcartAdapter.ShopingcartViewHolder scHolder =  shopingcartAdapter.getShopingcartViewHolder();
                if (isChoiced){
                    choiceMap.put(position, false);
                    scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
                    updateMoneySum();
                    shopingcartAdapter.notifyDataSetChanged();
                }else {
                    choiceMap.put(position, true);
                    scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
                    updateMoneySum();
                    shopingcartAdapter.notifyDataSetChanged();
                }
            }
        });
        //步骤11：新增“增加”按钮的匿名接口实现类，并重载接口事件方法
        shopingcartAdapter.setOnItemAddListener(new ShopingcartAdapter.OnItemAddListener() {
            @Override
            public void onItemAdd(int position) {
                Boolean isEnabled = choiceMap.get(position);
                // 如果未选中当前购物车项，则返回，不能操作增加按钮
                if(isEnabled == false) return;
                Shopingcart sc = shopingcartList.getResult().get(position);
                int curBuynum = new Integer(sc.getBuyNum());
                curBuynum ++;
                sc.setBuyNum(new Integer(curBuynum).toString());
                // 修改界面上的2个控件的文本值
                ShopingcartAdapter.ShopingcartViewHolder scHolder =  shopingcartAdapter.getShopingcartViewHolder();
                scHolder.shopingcart_buynum_tv.setText(new Integer(curBuynum).toString());
                updateMoneySum();
                // 强制刷新界面
                shopingcartAdapter.notifyDataSetChanged();
            }
        });
        //步骤12：新增“减少”按钮的匿名接口实现类，并重载接口事件方法
        shopingcartAdapter.setOnItemSubtractionListener(new ShopingcartAdapter.OnItemSubtractionListener() {
            @Override
            public void onItemSubtraction(int position) {
                Boolean isEnabled = choiceMap.get(position);
                // 如果未选中当前购物车项，则返回，不能操作减少按钮
                if(isEnabled == false) return;
                Shopingcart sc = shopingcartList.getResult().get(position);
                int curBuynum = new Integer(sc.getBuyNum());
                curBuynum --;
                // 如果购买数量小于0，则返回，不能继续操作减少按钮
                if(curBuynum <0) return;
                sc.setBuyNum(new Integer(curBuynum).toString());
                // 修改界面上的2个控件的文本值
                ShopingcartAdapter.ShopingcartViewHolder scHolder =  shopingcartAdapter.getShopingcartViewHolder();
                scHolder.shopingcart_buynum_tv.setText(new Integer(curBuynum).toString());
                updateMoneySum();
                // 强制刷新界面
                shopingcartAdapter.notifyDataSetChanged();
            }
        });
        return fragment_shopingcartView;
    }

    // 计算所有处于选中状态的购物车的总金额
    public void updateMoneySum(){
        int moneySum = 0;
        List<Shopingcart> shopingcarts = shopingcartList.getResult();
        for(int i=0; i<shopingcarts.size();i++){
            // 如果当前购物车项被选中
            Boolean isChoiced = choiceMap.get(i);
            if(isChoiced){
                Shopingcart sc = shopingcarts.get(i);
                int buyNum = new Integer(sc.getBuyNum());
                int price = new Integer(sc.getDevice().getDevicePrice());
                moneySum += new Integer(buyNum * price).intValue();
            }
        }
        fragment_shopingcart_sum_tv.setText(new Integer(moneySum).toString());
    }

    public void showAllShopingcartCallback(final ShopingcartList shopingcartListFromJson) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                shopingcartList = shopingcartListFromJson;
                shopingcartAdapter.setShopingcartList(shopingcartList);
                fragment_shopingcart_rv.setAdapter(shopingcartAdapter);
                // 初始化购物车项是否被选中Map，所有的购物车列表项都未选中
                updateBatchChoiceMap(false);
                updateMoneySum();
            }
        });
    }



}
