package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class DownloadCollectCountTask extends BaseActivity {
    private static final String TAG = DownloadCollectCountTask.class.getName();
    Context mContext;
    String username;
    String path;
    public DownloadCollectCountTask(Context mContext) {
        this.mContext = mContext;
        initPath();
    }

    public DownloadCollectCountTask(Context mContext, String username) {
        this.mContext = mContext;
        this.username = username;
        initPath();
    }

    private void initPath() {
        username = FuliCenterApplication.getInstance().getUserName();
        Log.e(TAG, "username=" + username);
        try {
            path = new ApiParams()
                    .with(I.Collect.USER_NAME, username)
                    .getRequestUrl(I.REQUEST_FIND_COLLECT_COUNT);
            Log.e("main", "path=" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void execute() {
        executeRequest(new GsonRequest<MessageBean>(path, MessageBean.class, responseDownloadCollectCountTaskListener(), errorListener()));
    }

    private Response.Listener<MessageBean> responseDownloadCollectCountTaskListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                Log.e(TAG,"messageBean="+messageBean);
                if (messageBean != null) {
                    if (messageBean.isSuccess()) {
                        FuliCenterApplication.getInstance().setCollectCount(Integer.parseInt(messageBean.getMsg()));
                        mContext.sendStickyBroadcast(new Intent("update_collect_count"));
                    }

                }
            }
        };
    }
}
