package zjc.devicemanage.adapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import zjc.devicemanage.R;

public class SampleAdapter extends Adapter {
    // 洲名：亚洲、欧洲、北美洲
    // 国家名：中国、印度、韩国、日本、英国、法国、德国、西班牙、美国、加拿大、古巴、墨西哥
    // 首都名：北京、新德里、首尔、东京、伦敦、巴黎、柏林、马德里、华盛顿、渥太华、哈瓦那、墨西哥城
    private List<String> continents;// 洲名列表
    private List<String> countries; // 国家列表
    private List<String> capitals; // 首都列表

    public SampleAdapter(List<String> continents, List<String> countries, List<String> capitals) {
        this.continents = continents;
        this.countries = countries;
        this.capitals = capitals;
    }

    @Override
    // 由于列表中将显示洲名和各个洲中的国家，因此单元项总数是洲名数组长度和国家数组长度
    public int getItemCount() {
        return continents.size() + countries.size();
    }

    private final int ItemType1 = 1;
    private final int ItemType2 = 2;
    @Override
    // 由于每个洲中将显示4个国家，第一种类型的单元项的序号是[0,5,10,15]，即能被5整除的序号
    // 第二种类型的单元项的序号就是剩余的序号
    public int getItemViewType(int position) {
        if (position % 5 == 0){
            return ItemType1;
        }else{
            return ItemType2;
        }
    }

    @Override
    // 由于有2种单元项类型viewType，因此将创建2个ViewHolder对象（SampleViewHolder1对象和SampleViewHolder2对象）。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View itemView = null;
        switch (viewType){
            case ItemType1:
                // 利用布局渲染器LayoutInflater，生成布局资源文件sample_viewholder1.xml根视图
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.sample_viewholder1,parent,false);
                // 生成sample_viewholder1.xml根视图对应的SampleViewHolder1变量sampleViewHolder1
                viewHolder = new SampleViewHolder1(itemView);
                break;
            case ItemType2:
                // 利用布局渲染器LayoutInflater，生成布局资源文件sample_viewholder2.xml根视图
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.sample_viewholder2,parent,false);
                // 生成sample_viewholder2.xml根视图对应的SampleViewHolder2变量sampleViewHolder2
                viewHolder = new SampleViewHolder2(itemView);
                break;
       }
       return viewHolder;
    }

    // item的回调接口，continentNum和countryNum分别是点击的列表项item所属的continents数组和countries数组中的序号值
    public interface OnItemClickListener{
        // 点击单元项是国家类型时调用
        void onItemClick(int position, int continentNum, int countryNum);
        // 点击单元项是洲类型时调用
        void onItemClick(int position, int continentNum);
    }
    // 增加回调接口属性，即监听类接口
    private OnItemClickListener onItemClickListener;
    // 对外暴露一个设置点击监听器的方法，其中传入需要OnItemClickListener接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    // 确定每个单元格的布局界面中所有控件的显示内容
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // position的取值依次为[0,1,...,14],总数为15项
        // 根据当前项编号position，得到该项在洲数组continents中的序号
        final int continentNum = position / 5;
        // 根据当前项编号position，得到该项在国家数组countries中的序号
        final int countryNum = position  - continentNum - 1;

        // 第一种视图类型的单元项
        if (position % 5 == 0){
            // 强制类型转换成ViewHolder子类SampleViewHolder1
            SampleViewHolder1 sampleViewHolder1  = (SampleViewHolder1)holder;
            sampleViewHolder1.sample_viewholder1_continent_tv.setText(continents.get(continentNum));
            // 给sample_viewholder1_ll布局控件添加事件监听处理
            if(onItemClickListener != null){
                sampleViewHolder1.sample_viewholder1_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position,continentNum);
                    }
                });
            }
        }
        // 第二种视图类型的单元项
        else{
            // 强制类型转换成ViewHolder子类SampleViewHolder2
            SampleViewHolder2 sampleViewHolder2  = (SampleViewHolder2)holder;
            sampleViewHolder2.sample_viewholder2_country_tv.setText(countries.get(countryNum));
            sampleViewHolder2.sample_viewholder2_capital_tv.setText(capitals.get(countryNum));
            switch (countryNum){
                case 0:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.china);
                    break;
                case 1:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.yindu);
                    break;
                case 2:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.hanguo);
                    break;
                case 3:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.riben);
                    break;
                case 4:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.yingguo);
                    break;
                case 5:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.faguo);
                    break;
                case 6:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.deguo);
                    break;
                case 7:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.xibanya);
                    break;
                case 8:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.meiguo);
                    break;
                case 9:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.jianada);
                    break;
                case 10:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.guba);
                    break;
                case 11:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.moxige);
                    break;
            }
            // 给sample_viewholder1_ll布局控件添加事件监听处理
            if(onItemClickListener != null){
                sampleViewHolder2.sample_viewholder2_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position, continentNum, countryNum);
                    }
                });
            }
        }
    }



    // 第一种单元项视图类型的布局界面(sample_viewholder1.xml)
    public class SampleViewHolder1 extends RecyclerView.ViewHolder{
        private LinearLayout sample_viewholder1_ll;
        private TextView sample_viewholder1_continent_tv;
        public SampleViewHolder1(View itemView) {
            super(itemView);
            sample_viewholder1_ll = itemView.findViewById(R.id.sample_viewholder1_ll);
            sample_viewholder1_continent_tv = itemView.findViewById(R.id.sample_viewholder1_continent_tv);
        }
    }
    // 第二种单元项视图类型的布局界面(sample_viewholder2.xml)
    public class SampleViewHolder2 extends RecyclerView.ViewHolder{
        private LinearLayout sample_viewholder2_ll;
        private ImageView sample_viewholder2_country_iv;
        private TextView sample_viewholder2_country_tv;
        private TextView sample_viewholder2_capital_tv;
        public SampleViewHolder2(View itemView) {
            super(itemView);
            sample_viewholder2_ll = itemView.findViewById(R.id.sample_viewholder2_ll);
            sample_viewholder2_country_iv = itemView.findViewById(R.id.sample_viewholder2_country_iv);
            sample_viewholder2_country_tv = itemView.findViewById(R.id.sample_viewholder2_country_tv);
            sample_viewholder2_capital_tv = itemView.findViewById(R.id.sample_viewholder2_capital_tv);
        }
    }

}
