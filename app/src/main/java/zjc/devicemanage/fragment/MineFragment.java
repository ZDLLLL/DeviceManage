package zjc.devicemanage.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjc.devicemanage.R;
import zjc.devicemanage.activity.LoginActivity;

public class MineFragment extends Fragment {
    private LinearLayout my_exit_ll;
    private static SharedPreferences.Editor editor;
    SharedPreferences preferences;
    public MineFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_mineView = inflater.inflate(R.layout.fragment_mine, container, false);
        my_exit_ll = fragment_mineView.findViewById(R.id.my_exit_ll);
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
        editor=preferences.edit();
        my_exit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                editor.remove("user_id");
                editor.apply();
                startActivity(intent);

                getActivity().finish();
            }
        });
//        my_exit_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView title=new TextView(getActivity());
//                title.setPadding(10, 10, 10, 10);
//                title.setText("设备管理");
//                title.setTextSize(18);
//                title.setTextColor(Color.parseColor("#000000"));
//                title.setGravity(Gravity.CENTER);
//                new android.support.v7.app.AlertDialog.Builder(getActivity())
//                        .setCustomTitle(title)
//                        .setMessage("是否要注销当前用户")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent=new Intent(getActivity(), LoginActivity.class);
//                                startActivity(intent);
//                                getActivity().finish();
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();
//            }
//        });
        return fragment_mineView;
    }
}
