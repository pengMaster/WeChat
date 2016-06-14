package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.SuperWeChatApplication;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.Group;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by sks on 2016/5/23.
 */
public class DownloadAllGroupTask  extends BaseActivity {
    private static final String TAG = DownloadAllGroupTask.class.getName();
    Context mContext;
    String username;
    String path;

    public DownloadAllGroupTask (Context mContext, String username) {
        this.mContext = mContext;
        this.username = username;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.User.USER_NAME, username)
                    .getRequestUrl(I.REQUEST_DOWNLOAD_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void execute() {
        executeRequest(new GsonRequest<Group[]>(path, Group[].class,
                responseDownloadGroupListTaskListener(), errorListener()));
    }

    private Response.Listener<Group[]> responseDownloadGroupListTaskListener() {
        return new Response.Listener<Group[]>() {
            @Override
            public void onResponse(Group[] response) {
                if (response != null&&response.length>0) {
                    Log.e("main","response::::"+response.length);
                    ArrayList<Group> contactArrayList = SuperWeChatApplication.getInstance().getGroupList();
                    ArrayList<Group> list = Utils.array2List(response);
                    contactArrayList.clear();
                    contactArrayList.addAll(list);


                    mContext.sendStickyBroadcast(new Intent("update_group_list"));
                }
            }

        };
        }

    }

