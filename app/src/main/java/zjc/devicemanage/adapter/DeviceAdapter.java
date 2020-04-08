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

public class DeviceAdapter extends Adapter {
    private DeviceList deviceList;
    public DeviceAdapter(DeviceList deviceList) {
        this.deviceList = deviceList;
    }
    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }
    @Override
    public int getItemCount() {
        return deviceList.getResult().size();
    }
    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 利用布局渲染器LayoutInflater，生成布局资源文件device_viewholder.xml根视图
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.device_viewholder,parent,false);
        // 生成device_viewholder根视图对应的DeviceViewHolder变量deviceViewHolder
        DeviceViewHolder deviceViewHolder = new DeviceViewHolder(itemView);
        return deviceViewHolder;
    }

    // item的回调接口,deviceID是点击的列表项item所属的设备编号值
    public interface OnItemClickListener{
        void onItemClick(int deviceID);
    }
    // 增加回调接口属性，即监听类接口
    private DeviceAdapter.OnItemClickListener onItemClickListener;
    // 对外暴露一个设置点击监听器的方法，其中传入需要OnItemClickListener接口
    public void setOnItemClickListener(DeviceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 强制类型转换成ViewHolder子类DeviceViewHolder
        DeviceViewHolder deviceViewHolder  = (DeviceViewHolder)holder;
        String deviceName = deviceList.getResult().get(position).getDeviceName();
        deviceViewHolder.device_viewholder_devicename_tv.setText(deviceName);
        String devicePrice = deviceList.getResult().get(position).getDevicePrice();
        deviceViewHolder.device_viewholder_deviceprice_tv.setText(devicePrice);
        switch (deviceName){
            case "打印机":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.printer);
                break;
            case "耳机":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.earphone);
                break;
            case "鼠标":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.mouse);
                break;
            case "笔记本电脑":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.computer);
                break;
            case "U盘":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.udisk);
                break;
            case "头盔":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(R.drawable.helmet);
                break;
        }
        if(onItemClickListener != null){
            final int deviceID = new Integer(deviceList.getResult().get(position).getDeviceID()).intValue();
            // deviceViewHolder.device_viewholder_ll是LinearLayout布局控件
            // LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件的处理
            // onClick(int deviceID)中的deviceID参数是当前列表项item的设备编号
            deviceViewHolder.device_viewholder_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(deviceID);
                }
            });
        }
    }
    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout device_viewholder_ll;
        private ImageView device_viewholder_device_iv;
        private TextView device_viewholder_devicename_tv;
        private TextView device_viewholder_deviceprice_tv;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            device_viewholder_ll = itemView.findViewById(R.id.device_viewholder_ll);
            device_viewholder_device_iv = itemView.findViewById(R.id.device_viewholder_device_iv);
            device_viewholder_devicename_tv = itemView.findViewById(R.id.device_viewholder_devicename_tv);
            device_viewholder_deviceprice_tv = itemView.findViewById(R.id.device_viewholder_deviceprice_tv);
        }
    }
}
