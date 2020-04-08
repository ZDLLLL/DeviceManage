package zjc.devicemanage.adapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjc.devicemanage.R;
import zjc.devicemanage.model.DeviceList;

public class EmbedDeviceAdapter extends Adapter {
    // 第一种单元项的视图View内容(embeddevice_viewholder1.xml)，不需要定义子视图控件
    public class EmbedDeviceViewHolder1 extends RecyclerView.ViewHolder {
        public EmbedDeviceViewHolder1(View itemView) {
            super(itemView);
        }
    }

    private View recyclerViewDeviceClass;

    public void setRecyclerViewDeviceClass(View recyclerViewDeviceClass) {
        this.recyclerViewDeviceClass = recyclerViewDeviceClass;
    }

    // 第二种单元项的视图View内容(embeddevice_viewholder2.xml)，需要定义子视图控件
    public class EmbedDeviceViewHolder2 extends RecyclerView.ViewHolder {
        private LinearLayout embeddevice_viewholder2_ll;
        private ImageView embeddevice_viewholder2_device_iv;
        private TextView embeddevice_viewholder2_devicename_tv;
        private TextView embeddevice_viewholder2_deviceprice_tv;
        private ImageView embeddevice_viewholder2_addtoshopingcart;

        public EmbedDeviceViewHolder2(View itemView) {
            super(itemView);
            embeddevice_viewholder2_ll = itemView.findViewById(R.id.embeddevice_viewholder2_ll);
            embeddevice_viewholder2_device_iv = itemView.findViewById(R.id.embeddevice_viewholder2_device_iv);
            embeddevice_viewholder2_devicename_tv = itemView.findViewById(R.id.embeddevice_viewholder2_devicename_tv);
            embeddevice_viewholder2_deviceprice_tv = itemView.findViewById(R.id.embeddevice_viewholder2_deviceprice_tv);
            embeddevice_viewholder2_addtoshopingcart = itemView.findViewById(R.id.embeddevice_viewholder2_addtoshopingcart);
        }
    }

    private DeviceList deviceList;

    public EmbedDeviceAdapter(DeviceList deviceList) {
        this.deviceList = deviceList;
    }

    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }


    @Override
    public int getItemCount() {
        return 1 + deviceList.getResult().size();
    }

    // 新增2个表示单元项类型的整数变量
    private final int ItemType1 = 1;
    private final int ItemType2 = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ItemType1;
        } else {
            return ItemType2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        // view是单元项所显示的界面视图
        View view = null;
        switch (viewType) {
            // 第一种单元项，该种单元项就是embeddevice_viewholder1.xml界面
            case ItemType1:
                view = recyclerViewDeviceClass;
                viewHolder = new EmbedDeviceViewHolder1(view);
                break;
            // 第二种单元项，该种单元项就是embeddevice_viewholder2.xml界面
            case ItemType2:
                // parent是RecyclerView控件对象
                // 利用布局渲染器LayoutInflater，生成布局资源文件embeddevice_viewholder2.xml视图
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.embeddevice_viewholder2, parent, false);
                viewHolder = new EmbedDeviceViewHolder2(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 如果是第一个单元项，则不需要操作ViewHolder中的控件显示内容
        if (position == 0) {
            return;
        } else {
            // 其他单元项，则需要操作ViewHolder中的控件显示内容
            EmbedDeviceViewHolder2 embedDeviceViewHolder2 = (EmbedDeviceViewHolder2) holder;
            String deviceName = deviceList.getResult().get(position - 1).getDeviceName();
            embedDeviceViewHolder2.embeddevice_viewholder2_devicename_tv.setText(deviceName);
            String devicePrice = deviceList.getResult().get(position - 1).getDevicePrice();
            embedDeviceViewHolder2.embeddevice_viewholder2_deviceprice_tv.setText(devicePrice);
            switch (deviceName) {
                case "打印机":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.printer);
                    break;
                case "耳机":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.earphone);
                    break;
                case "鼠标":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.mouse);
                    break;
                case "笔记本电脑":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.computer);
                    break;
                case "U盘":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.udisk);
                    break;
                case "头盔":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.helmet);
                    break;
            }
            if (onItemClickListener != null) {
                final int deviceID = new Integer(deviceList.getResult().get(position-1).getDeviceID()).intValue();
                // embedDeviceViewHolder2.embeddevice_viewholder2_ll是LinearLayout布局控件
                // LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件处理
                // onClick(int deviceID)中的deviceID参数是当前列表项item的设备编号
                embedDeviceViewHolder2.embeddevice_viewholder2_ll.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(deviceID);
                    }
                });
            }
            // 加入购物车图片控件的点击处理
            if (onAddShopingcartClickListener != null) {
                final int deviceID = new Integer(deviceList.getResult().get(position-1).getDeviceID()).intValue();
                embedDeviceViewHolder2.embeddevice_viewholder2_addtoshopingcart.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onAddShopingcartClickListener.onAddShopingcartClick(deviceID);
                    }
                });
            }
        }
    }

    // 定义布局控件的接口OnItemClickListener，包含1个回调方法onItemClick，用于设置布局控件被点击后的逻辑
    public interface OnItemClickListener {
        void onItemClick(int deviceID);
    }
    // 增加回调接口属性，即监听类接口
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // 定义加入购物车图片控件的接口OnAddShopingcartClickListener，包含1个回调方法onAddShopingcartClick，用于设置购物车图片控件被点击后的逻辑
    public interface OnAddShopingcartClickListener {
        void onAddShopingcartClick(int deviceID);
    }
    // 增加回调接口属性，即监听类接口
    private OnAddShopingcartClickListener onAddShopingcartClickListener;
    public void setOnAddShopingcartClickListener(OnAddShopingcartClickListener onAddShopingcartClickListener) {
        this.onAddShopingcartClickListener = onAddShopingcartClickListener;
    }
}
