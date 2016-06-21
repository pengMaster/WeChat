package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.Contact;
import cn.ucai.fulicenter.bean.Message;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class DownloadCollectCountTask extends BaseActivity {
    private static final String TAG = DownloadCollectCountTask.class.getName();
    Context mContext;
    String username;
    String path;

    public DownloadCollectCountTask(Context mContext, String username) {
        this.mContext = mContext;
        this.username = username;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.Contact.USER_NAME, username)
                    .getRequestUrl(I.REQUEST_FIND_COLLECT_COUNT);
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
                Log.e(TAG,"contact.length="+messageBean);
                if (messageBean != null) {
                    int collectCount = FuliCenterApplication.getInstance().getCollectCount();
                    String msg = Utils.getResourceString(mContext, collectCount);
                    mContext.sendStickyBroadcast(new Intent("update_collect_count"));

                }
            }
        };
    }
}
