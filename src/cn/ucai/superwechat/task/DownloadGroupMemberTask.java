package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.activity.BaseActivity;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.data.ApiParams;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.utils.Utils;

/**
 * Created by sks on 2016/5/31.
 */
public class DownloadGroupMemberTask extends BaseActivity {
    private static final String TAG = DownloadAllGroupTask.class.getName();
    Context mContext;
    String hxid;
    String path;

    public DownloadGroupMemberTask (Context mContext, String username) {
        this.mContext = mContext;
        this.hxid = username;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.Member.GROUP_HX_ID,hxid)
                    .getRequestUrl(I.REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void execute() {
        executeRequest(new GsonRequest<Member[]>(path, Member[].class,
                responseDownloadMemberListTaskListener(), errorListener()));
    }

    private Response.Listener<Member[]> responseDownloadMemberListTaskListener() {
        return new Response.Listener<Member[]>() {
            @Override
            public void onResponse(Member[] response) {
                if (response != null&&response.length>0) {
                    Log.e("main","response::::"+response.length);
                    ArrayList<Member> list = Utils.array2List(response);
                    HashMap<String, ArrayList<Member>> groupMembers = SuperWeChatApplication.getInstance().getGroupMembers();
                    ArrayList<Member> memberArrayList = groupMembers.get(hxid);
                    if (memberArrayList != null) {

                        memberArrayList.clear();
                        memberArrayList.addAll(list);
                    } else {
                        groupMembers.put(hxid, list);
                    }


                    mContext.sendStickyBroadcast(new Intent("update_member_list"));
                }
            }

        };
    }


}
