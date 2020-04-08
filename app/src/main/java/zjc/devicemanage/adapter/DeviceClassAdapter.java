package zjc.devicemanage.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjc.devicemanage.R;
import zjc.devicemanage.model.DeviceClassList;

public class DeviceClassAdapter extends RecyclerView.Adapter {
    private DeviceClassList deviceClassList;
    public DeviceClassAdapter(DeviceClassList deviceClassList) {
        this.deviceClassList = deviceClassList;
    }
    public void setDeviceClassList(DeviceClassList deviceClassList) {
        this.deviceClassList = deviceClassList;
    }

    @Override
    public int getItemCount() {
        return deviceClassList.getResult().size();
    }

    public class DeviceClassViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout deviceclass_viewholder_ll;
        private ImageView deviceclass_viewholder_iv;
        private TextView deviceclass_viewholder_tv;

        public DeviceClassViewHolder(View itemView) {
            super(itemView);
            deviceclass_viewholder_ll = itemView.findViewById(R.id.deviceclass_viewholder_ll);
            deviceclass_viewholder_iv = itemView.findViewById(R.id.deviceclass_viewholder_iv);
            deviceclass_viewholder_tv = itemView.findViewById(R.id.deviceclass_viewholder_tv);
        }
    }

    @Override
    public DeviceClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 利用布局渲染器LayoutInflater，生成布局资源文件deviceclass_viewholder.xml根视图
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.deviceclass_viewholder,parent,false);
        // 生成deviceclass_viewholder根视图对应的DeviceClassViewHolder变量deviceClassViewHolder
        DeviceClassViewHolder deviceClassViewHolder = new DeviceClassViewHolder(itemView);
        return deviceClassViewHolder;
    }

    // item的回调接口,deviceClassID是点击的列表项item所属的设备分类编号值
    public interface OnItemClickListener{
        void onItemClick(int deviceClassID);
    }
    // 增加回调接口属性，即监听类接口
    private OnItemClickListener onItemClickListener;
    // 对外暴露一个设置点击监听器的方法，其中传入需要OnItemClickListener接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // 强制类型转换成ViewHolder子类DeviceClassViewHolder
        DeviceClassViewHolder deviceClassViewHolder  = (DeviceClassViewHolder)holder;
        String itemText = deviceClassList.getResult().get(position).getDeviceClassName();
        deviceClassViewHolder.deviceclass_viewholder_tv.setText(itemText);
        switch (itemText){
            case "办公设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.office);
                break;
            case "生活设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.live);
                break;
            case "学习设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.study);
                break;
            case "户外设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.outdoor);
                break;
            case "电子设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.elecdevice);
                break;
            case "其他设备":
                deviceClassViewHolder.deviceclass_viewholder_iv.setImageResource(R.drawable.other);
                break;
        }
        if(onItemClickListener != null){
            final int deviceClassID = new Integer(deviceClassList.getResult().get(position).getDeviceClassID()).intValue();
            // deviceClassViewHolder.deviceclass_viewholder_ll是LinearLayout布局控件
            // LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件的处理
            // onClick(int deviceClassID)中的deviceClassID参数是当前列表项item的设备分类编号
            deviceClassViewHolder.deviceclass_viewholder_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(deviceClassID);
                }
            });
        }
    }
}
