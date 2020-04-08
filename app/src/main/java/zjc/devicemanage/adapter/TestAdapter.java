package zjc.devicemanage.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import zjc.devicemanage.R;
import zjc.devicemanage.model.User;

public class TestAdapter extends RecyclerView.Adapter {
    private List<User> userList;

    public TestAdapter(List<User> mUsers) {
        this.userList = mUsers;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 利用布局渲染器LayoutInflater，生成布局资源文件test_viewholder.xml根视图itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_viewholder,parent,false);
        // 生成test_viewholder.xml根视图对应的TestViewHolder变量testViewHolder
        TestViewHolder testViewHolder = new TestViewHolder(itemView);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // 强制类型转换成ViewHolder子类TestViewHolder
        TestViewHolder testViewHolder  = (TestViewHolder)holder;
        testViewHolder.test_viewholder_username_tv.setText(userList.get(position).getUserName());
        testViewHolder.test_viewholder_userpassword_tv.setText(userList.get(position).getUserPassword());
        if(onItemClickListener != null){
            // testViewHolder.test_viewholder_ll是LinearLayout布局控件
            // LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件的处理
            // onClick(View v)中的v参数是鼠标所点击的那个视图控件对象
            testViewHolder.test_viewholder_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout test_viewholder_ll;
        private TextView test_viewholder_username_tv;
        private TextView test_viewholder_userpassword_tv;
        public TestViewHolder(View itemView) {
            super(itemView);
            test_viewholder_ll = itemView.findViewById(R.id.test_viewholder_ll);
            test_viewholder_username_tv = itemView.findViewById(R.id.test_viewholder_username_tv);
            test_viewholder_userpassword_tv = itemView.findViewById(R.id.test_viewholder_userpassword_tv);
        }
    }

    // item的回调接口
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    // 增加回调接口属性，即监听类接口
    private OnItemClickListener onItemClickListener;
    // 对外暴露一个设置点击监听器的方法，其中传入需要OnItemClickListener接口
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
