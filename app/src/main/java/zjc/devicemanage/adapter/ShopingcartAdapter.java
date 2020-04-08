package zjc.devicemanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;
import zjc.devicemanage.R;
import zjc.devicemanage.model.Device;
import zjc.devicemanage.model.ShopingcartList;

public class ShopingcartAdapter extends RecyclerView.Adapter {

    public class ShopingcartViewHolder extends RecyclerView.ViewHolder {
        // 用于该行的点击处理
        public LinearLayout shopingcart_viewholder_ll;
        // 用于勾选按钮的点击处理
        public LinearLayout shopingcart_choice_ll;
        public ImageView shopingcart_choice_iv;
        public ImageView shopingcart_deviceimage_iv;
        public TextView shopingcart_devicename_tv;
        public TextView shopingcart_deviceprice_tv;
        public ImageView shopingcart_subtraction_iv;
        public TextView shopingcart_buynum_tv;
        public ImageView shopingcart_add_iv;
        public ShopingcartViewHolder(View itemView) {
            super(itemView);
            shopingcart_viewholder_ll = itemView.findViewById(R.id.shopingcart_viewholder_ll);
            shopingcart_choice_ll = itemView.findViewById(R.id.shopingcart_choice_ll);
            shopingcart_choice_iv = itemView.findViewById(R.id.shopingcart_choice_iv);
            shopingcart_deviceimage_iv = itemView.findViewById(R.id.shopingcart_deviceimage_iv);
            shopingcart_devicename_tv = itemView.findViewById(R.id.shopingcart_devicename_tv);
            shopingcart_deviceprice_tv = itemView.findViewById(R.id.shopingcart_deviceprice_tv);
            shopingcart_subtraction_iv = itemView.findViewById(R.id.shopingcart_subtraction_iv);
            shopingcart_buynum_tv = itemView.findViewById(R.id.shopingcart_buynum_tv);
            shopingcart_add_iv = itemView.findViewById(R.id.shopingcart_add_iv);
        }
    }

    private ShopingcartList shopingcartList;
    public void setShopingcartList(ShopingcartList shopingcartList) {
        this.shopingcartList = shopingcartList;
    }
    // 用于标记每个购物车项是否被选中
    private HashMap<Integer, Boolean> choiceMap;
    // 由ShopingcartFragment调用，传入当前用户的选项Map对象
    public void setChoiceMap(HashMap<Integer, Boolean> choiceMap) {
        this.choiceMap = choiceMap;
    }

    public ShopingcartAdapter(ShopingcartList shopingcartList, HashMap<Integer, Boolean> choiceMap) {
        this.shopingcartList = shopingcartList;
        this.choiceMap = choiceMap;
    }
    // 用于访问适配器对应的ViewHolder对象
    private ShopingcartViewHolder shopingcartViewHolder;
    public ShopingcartViewHolder getShopingcartViewHolder() {
        return shopingcartViewHolder;
    }
    public void setShopingcartViewHolder(ShopingcartViewHolder shopingcartViewHolder) {
        this.shopingcartViewHolder = shopingcartViewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 利用布局渲染器LayoutInflater，生成布局资源文件shopingcart_viewholder.xml根视图
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.shopingcart_viewholder,parent,false);
        // 生成shopingcart_viewholder根视图对应的ShopingcartViewHolder变量
        shopingcartViewHolder = new ShopingcartViewHolder(itemView);
        return shopingcartViewHolder;
    }

    // 新增选中图片按钮的监听类，处理选中图片按钮的点击事件
    public interface OnItemChoiceListener{
        void onItemChoice(int position);
    }
    private OnItemChoiceListener onItemChoiceListener;
    public void setOnItemChoiceListener(OnItemChoiceListener onItemChoiceListener) {
        this.onItemChoiceListener = onItemChoiceListener;
    }

    // 新增增加图片按钮的监听类，处理增加图片按钮的点击事件
    public interface OnItemAddListener{
        void onItemAdd(int position);
    }
    private OnItemAddListener onItemAddListener;
    public void setOnItemAddListener(OnItemAddListener onItemAddListener) {
        this.onItemAddListener = onItemAddListener;
    }

    // 新增减少图片按钮的监听类，处理减少图片按钮的点击事件
    public interface OnItemSubtractionListener{
        void onItemSubtraction(int position);
    }
    private OnItemSubtractionListener onItemSubtractionListener;
    public void setOnItemSubtractionListener(OnItemSubtractionListener onItemSubtractionListener) {
        this.onItemSubtractionListener = onItemSubtractionListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 强制类型转换成ViewHolder子类ShopingcartViewHolder
        ShopingcartViewHolder shopingcartViewHolder = (ShopingcartViewHolder) holder;
        Device device = shopingcartList.getResult().get(position).getDevice();
        String buyNum = shopingcartList.getResult().get(position).getBuyNum();
        // 读取device对象的属性
        String deviceName = device.getDeviceName();
        String devicePrice = device.getDevicePrice();
        // 将3个属性值显示到3个TextView控件上
        shopingcartViewHolder.shopingcart_devicename_tv.setText(deviceName);
        shopingcartViewHolder.shopingcart_buynum_tv.setText(buyNum);
        shopingcartViewHolder.shopingcart_deviceprice_tv.setText(devicePrice);
        // 根据choiceMap，确定图片控件shopingcart_choice_iv显示图片是choice.jpg还是choiced.jpg
        if(choiceMap.get(position)){
            shopingcartViewHolder.shopingcart_choice_iv.setImageResource(
                    R.drawable.choiced);
        }else{
            shopingcartViewHolder.shopingcart_choice_iv.setImageResource(
                    R.drawable.choice);
        }
        // 根据设备的名称，确定图片控件shopingcart_deviceimage_iv显示的图片
        switch (deviceName){
            case  "打印机":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.printer);
                break;
            case "耳机":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.earphone);
                break;
            case  "鼠标":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.mouse);
                break;
            case "笔记本电脑":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.computer);
                break;
            case  "U盘":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.udisk);
                break;
            case "头盔":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource(R.drawable.helmet);
                break;
        }

        // 处理选中图片按钮的点击事件
        if(onItemChoiceListener != null) {
            shopingcartViewHolder.shopingcart_choice_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemChoiceListener.onItemChoice(position);
                }
            });
        }
        // 处理增加图片的点击事件
        if(onItemAddListener != null) {
            shopingcartViewHolder.shopingcart_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemAddListener.onItemAdd(position);
                }
            });
        }
        // 处理减少图片的点击事件
        if(onItemSubtractionListener != null) {
            shopingcartViewHolder.shopingcart_subtraction_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSubtractionListener.onItemSubtraction(position);
                }
            });
        }
    }



    public void shopingcartChoiceHandler(RecyclerView.ViewHolder holder, int position){
        boolean isChoiced = choiceMap.get(position);
        if (isChoiced){
            isChoiced = false;
            choiceMap.put(position, false);
            ShopingcartViewHolder scHolder = (ShopingcartViewHolder) holder;
            scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
        }else {
            isChoiced = true;
            choiceMap.put(position,true);
            ShopingcartViewHolder scHolder = (ShopingcartViewHolder) holder;
            scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choiced);
        }
    }

    public void shopingcartAddHandler(int position){
        String buyNumStr = shopingcartList.getResult().get(position).getBuyNum();
        int buyNum = new Integer(buyNumStr).intValue();
        buyNum ++;
        buyNumStr = new Integer(buyNum).toString();
        shopingcartList.getResult().get(position).setBuyNum(buyNumStr);
    }

    public void shopingcartSubtractionHandler(int position){
        String buyNumStr = shopingcartList.getResult().get(position).getBuyNum();
        int buyNum = new Integer(buyNumStr).intValue();
        buyNum --;
        buyNumStr = new Integer(buyNum).toString();
        shopingcartList.getResult().get(position).setBuyNum(buyNumStr);
    }

    @Override
    public int getItemCount() {
        return shopingcartList.getResult().size();
    }

}
