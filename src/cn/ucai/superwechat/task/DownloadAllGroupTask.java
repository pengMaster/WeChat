package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.activity.BaseActivity;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.data.ApiParams;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.utils.Utils;

/**
 * Created by sks on 2016/5/23.
 */
public class DownloadAllGroupTask  extends BaseActivity {
    private static final String TAG = DownloadContactListTask.class.getName();
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
                    .with(I.Contact.USER_NAME, username)
                    .getRequestUrl(I.REQUEST_DELETE_GROUP);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void execute() {
        executeRequest(new GsonRequest<Contact[]>(path, Contact[].class,
                responseDownloadContactListTaskListener(), errorListener()));
    }

    private Response.Listener<Contact[]> responseDownloadContactListTaskListener() {
        return new Response.Listener<Contact[]>() {
            @Override
            public void onResponse(Contact[] response) {
                if (response != null) {
                    ArrayList<Contact> contactArrayList = SuperWeChatApplication.getInstance().getContactList();
                    ArrayList<Contact> list = Utils.array2List(response);
                    contactArrayList.clear();
                    contactArrayList.addAll(list);
                    HashMap<String, Contact> userList =
                            SuperWeChatApplication.getInstance().getUserList();
                    userList.clear();
                    for (Contact c : list) {
                        userList.put(c.getMContactCname(),c);


                    }
                    mContext.sendStickyBroadcast(new Intent("update_group_list"));
                }

            }
        };

    }
}
