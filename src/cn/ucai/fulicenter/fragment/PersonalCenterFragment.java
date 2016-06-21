package cn.ucai.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.Inflater;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.FuliCenterMainActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.task.DownloadCollectCountTask;
import cn.ucai.fulicenter.utils.UserUtils;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class PersonalCenterFragment extends Fragment {
    FuliCenterMainActivity mContext;
    TextView mtvSettings;
    ImageView mivPersonalCenterMsg;
    NetworkImageView nivUserAvatar;
    TextView mtvUserName;
    ImageView miv_user_qrcode;
//    View layout;
    TextView mtvCollectCount;
    int mCollectCount;
    public PersonalCenterFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.personal_center, container, false);
        mContext = (FuliCenterMainActivity) getActivity();

        registerCollectCountChangedListener();
        registerUpdateUserReceiver();
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        mCollectCount = FuliCenterApplication.getInstance().getCollectCount();
        mtvCollectCount.setText(""+mCollectCount);
        if (FuliCenterApplication.getInstance().getUser() != null) {
            UserUtils.setCurrentUserBeanAvatar(nivUserAvatar);
            UserUtils.setCurrentUserBeanNick(mtvUserName);
        }
    }

    private void initView(View layout) {
        mtvSettings = (TextView) layout.findViewById(R.id.tv_personal_center_settings);
        mivPersonalCenterMsg = (ImageView) layout.findViewById(R.id.iv_personal_center_msg);
        mtvUserName = (TextView) layout.findViewById(R.id.tv_user_name);
        miv_user_qrcode = (ImageView) layout.findViewById(R.id.iv_user_qrcode);
        nivUserAvatar = (NetworkImageView) layout.findViewById(R.id.iv_user_avatar);
        initOrderList(layout);
        mtvCollectCount = (TextView) layout.findViewById(R.id.tvCollectCount);
    }
    private void initOrderList(View layout) {
        //显示GridView的界面
        GridView mOrderList = (GridView) layout.findViewById(R.id.center_user_order_list);
        ArrayList<HashMap<String, Object>> imageList = new ArrayList<HashMap<String, Object>>();
        //使用HashMap将突破添加到一个数组中，注意一定要是HashMap<String,Object>类型的，因为装到map中的图片要是资源ID，而不是图片本身
        //如果是使用findviewById(R.drawable.image)这样把真正的图片取出来了，放到map中是无法正常显示的
        HashMap<String, Object> map1 = new HashMap<String,Object>();
        map1.put("image", R.drawable.order_list1);
        HashMap<String, Object> map2 = new HashMap<String,Object>();
        map2.put("image", R.drawable.order_list2);
        HashMap<String, Object> map3 = new HashMap<String,Object>();
        map3.put("image", R.drawable.order_list3);
        HashMap<String, Object> map4 = new HashMap<String,Object>();
        map4.put("image", R.drawable.order_list4);
        HashMap<String, Object> map5 = new HashMap<String,Object>();
        map5.put("image", R.drawable.order_list5);
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, imageList,
                R.layout.simple_grid_item, new String[]{"image"},
                new int[]{R.id.image});
        mOrderList.setAdapter(simpleAdapter);
    }

    class CollectCountChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            initData();
        }
    }
    CollectCountChangedReceiver mReceiver;
    private void registerCollectCountChangedListener() {
        mReceiver = new CollectCountChangedReceiver();
        IntentFilter filter = new IntentFilter("update_collect_count");
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
        if (mUserReceiver != null) {
            mContext.unregisterReceiver(mUserReceiver);
        }
    }

    class UpdateUserChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            new DownloadCollectCountTask(mContext).execute();
            initData();
        }
    }
    UpdateUserChangedReceiver mUserReceiver;
    private void registerUpdateUserReceiver() {
        mUserReceiver = new UpdateUserChangedReceiver();
        IntentFilter filter = new IntentFilter("update_user");
        mContext.registerReceiver(mUserReceiver, filter);

    }
}
