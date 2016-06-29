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
import cn.ucai.fulicenter.bean.Message;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class UpdateCartTask extends BaseActivity {
    public static final String TAG = UpdateCartTask.class.getName();
    Context mContext;
    CartBean cart;
    String path;
    int actionType;

    public UpdateCartTask(Context mContext, CartBean cart) {
        this.mContext = mContext;
        this.cart = cart;
        initPath();
    }

    private void initPath() {
        ArrayList<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
        try {
            if (cartList.contains(cart)) {
                if (cart.getCount() <= 0) {
                    actionType = 0;
                    path = new ApiParams()
                            .with(I.Cart.ID, cart.getId() + "")
                            .getRequestUrl(I.REQUEST_DELETE_CART);
                    Log.e("main","Update.delete.path:"+path);
                } else {
                    actionType = 1;
                    path = new ApiParams()
                            .with(I.Cart.IS_CHECKED, cart.isChecked() + "")
                            .with(I.Cart.COUNT, cart.getCount() + "")
                            .with(I.Cart.ID, cart.getId() + "")
                            .getRequestUrl(I.REQUEST_UPDATE_CART);
                    Log.e("main","Update.update.path:"+path);
                }
            } else {
                actionType = 2;
                path = new ApiParams()
                        .with(I.Cart.USER_NAME, FuliCenterApplication.getInstance().getUserName())
                        .with(I.Cart.GOODS_ID, cart.getGoods().getGoodsId() + "")
                        .with(I.Cart.COUNT, cart.getCount() + "")
                        .with(I.Cart.IS_CHECKED, cart.isChecked() + "")
                        .getRequestUrl(I.REQUEST_ADD_CART);
                Log.e("main","Update.add.path:"+path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void execute() {
        executeRequest(new GsonRequest<MessageBean>(path, MessageBean.class,
                responseUpdateCartListener(), errorListener()));
    }

    private Response.Listener<MessageBean> responseUpdateCartListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                ArrayList<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
                if (messageBean.isSuccess()) {
                    if (actionType == 0) {
                        cartList.remove(cart);
                    }
                    if (actionType == 1) {
                        cartList.set(cartList.indexOf(cart), cart);
                    }
                    if (actionType == 2) {
                        cart.setId(Integer.parseInt(messageBean.getMsg()));
                        cartList.add(cart);
                    }
                    mContext.sendStickyBroadcast(new Intent("update_cart"));
                }
            }
        };
    }

}
