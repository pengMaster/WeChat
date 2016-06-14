package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;

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
 * Created by Administrator on 2016/5/23 0023.
 */
public class DownloadPublicGroupTask extends BaseActivity {
    private static final String TAG = DownloadPublicGroupTask.class.getName();
    Context mContext;
    String userName;
    int page_id;
    int page_size;
    String path;

    public DownloadPublicGroupTask(Context mContext, String userName, int page_id, int page_size) {
        this.mContext = mContext;
        this.userName = userName;
        this.page_id = page_id;
        this.page_size = page_size;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.User.USER_NAME, userName)
                    .with(I.PAGE_ID, page_id + "")
                    .with(I.PAGE_SIZE, page_size + "")
                    .getRequestUrl(I.REQUEST_FIND_PUBLIC_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        executeRequest(new GsonRequest<Group[]>(path,Group[].class,
                responseDownloadPublicGroupTaskListener(),errorListener()));
    }

    private Response.Listener<Group[]> responseDownloadPublicGroupTaskListener() {
        return new Response.Listener<Group[]>() {
            @Override
            public void onResponse(Group[] response) {
                if (response!=null){
                    ArrayList<Group> publicGroupList = SuperWeChatApplication.getInstance().getPublicGroupList();
                    ArrayList<Group> list = Utils.array2List(response);
                    for (Group g:list){
                        if (!publicGroupList.contains(g)){
                            publicGroupList.add(g);
                        }
                    }
                   /* publicGroupList.clear();
                    publicGroupList.addAll(list);*/
                    mContext.sendStickyBroadcast(new Intent("update_public_group"));
                }
            }
        };
    }
}
