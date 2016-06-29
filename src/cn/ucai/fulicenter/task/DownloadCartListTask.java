package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class DownloadCartListTask extends BaseActivity {
    private static final String TAG = DownloadCartListTask.class.getName();
    Context mContext;
    String username;
    String path;
    int listSize;
    ArrayList<CartBean> list;
    public DownloadCartListTask(Context mContext) {
        this.mContext = mContext;
        initPath();
    }

    private void initPath() {
        username = FuliCenterApplication.getInstance().getUserName();
        Log.e(TAG, "username=" + username);
        try {
            path = new ApiParams()
                    .with(I.Cart.USER_NAME, username)
                    .with(I.PAGE_ID,I.PAGE_ID_DEFAULT+"")
                    .with(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                    .getRequestUrl(I.REQUEST_FIND_CARTS);
            Log.e("main", "DownLoadCartListTask.path=" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void execute() {
        executeRequest(new GsonRequest<CartBean[]>(path, CartBean[].class,
                responseDownloadCartCountTaskListener(), errorListener()));
    }

    private Response.Listener<CartBean[]> responseDownloadCartCountTaskListener() {
        return new Response.Listener<CartBean[]>() {
            @Override
            public void onResponse(CartBean[] cartBean) {
                Log.e(TAG,"CartBean="+cartBean);
                if (cartBean != null) {
                    Log.e(TAG,"CartBean="+cartBean.length);
                    list = Utils.array2List(cartBean);
                    try {
                        for (CartBean cart : list) {
                            path = new ApiParams()
                                    .with(I.Collect.USER_NAME, cart.getUserName()+"")
                                    .with(I.Collect.GOODS_ID, cart.getGoodsId() + "")
                                    .getRequestUrl(I.REQUEST_FIND_GOOD_DETAILS);
                            Log.e(TAG, "path=" + path);
                            executeRequest(new GsonRequest<GoodDetailsBean>(path, GoodDetailsBean.class,
                                    responseDownloadGoodDetailListener(cart), errorListener()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.Listener<GoodDetailsBean> responseDownloadGoodDetailListener(final CartBean cart) {
        return new Response.Listener<GoodDetailsBean>() {
            @Override
            public void onResponse(GoodDetailsBean goodDetailsBean) {
                listSize++;
                if (goodDetailsBean != null) {
                    cart.setGoods(goodDetailsBean);
                    ArrayList<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
                    if (!cartList.contains(cart)) {
                        cartList.add(cart);
                    }
                }
                if (listSize == list.size()) {
                    mContext.sendStickyBroadcast(new Intent("update_cart_list"));
                }
            }
        };
    }
}
