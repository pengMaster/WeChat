package cn.ucai.fulicenter.fragment;


import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity. FuliCenterMainActivity;
import cn.ucai.fulicenter.activity. FuliCenterMainActivity;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 */
public class CartFragment extends Fragment {
    public static final String TAG = CartFragment.class.getName();
     FuliCenterMainActivity mContext;
    ArrayList<CartBean> mCartList;
    CartAdapter mAdapter;
    private int pageId = 0;
    private int action = I.ACTION_DOWNLOAD;
    String path;
    String username;


    /**
     * 下拉刷新空间
     */
    SwipeRefreshLayout msrl;
    RecyclerView mRecyclerView;
    TextView mtvHint,mtvBuy;
    TextView mtvSumPrice,mtvSavePrice,mtvNothing;
    LinearLayoutManager mLayoutManager;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = ( FuliCenterMainActivity)getActivity();
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        mCartList = new ArrayList<CartBean>();
        initView(layout);
        setListener();
        initData();
        return layout;

    }

    private void initData() {
        getPath(pageId);
        ArrayList<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
        Log.e(TAG, "cartList=" + cartList);
        mCartList.clear();
        mCartList.addAll(cartList);
        mAdapter.notifyDataSetChanged();
        sumPrice();
        if (mCartList == null || mCartList.size() == 0) {
            mtvNothing.setVisibility(View.VISIBLE);
        } else {
            mtvNothing.setVisibility(View.GONE);
        }
//        mContext.executeRequest(new GsonRequest<CartBean[]>(path, CartBean[].class,
//                responseDownloadCartListener(), mContext.errorListener()));
    }

    private Response.Listener<CartBean[]> responseDownloadCartListener() {
        return new Response.Listener<CartBean[]>() {
            @Override
            public void onResponse(CartBean[] cartBeen) {
                Log.e(TAG,"CartBean="+cartBeen);
                if (cartBeen != null) {
                    Log.e(TAG,"CartBean="+cartBeen.length);
//                    mCartList = Utils.array2List(cartBeen);
                    mAdapter.setMore(true);
                    msrl.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    //将数组转换为集合
                    ArrayList<CartBean> list = Utils.array2List(cartBeen);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initContact(list);
                    } else if (action==I.ACTION_PULL_UP) {
                        mAdapter.addContact(list);
                    }
                    if (cartBeen.length < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                }
            }
        };
    }

    private String getPath(int pageId) {
        username = FuliCenterApplication.getInstance().getUserName();
        Log.e(TAG, "username=" + username);
        try {
            path = new ApiParams()
                    .with(I.Cart.USER_NAME, username)
                    .with(I.PAGE_ID,I.PAGE_ID_DEFAULT+"")
                    .with(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                    .getRequestUrl(I.REQUEST_FIND_CARTS);
            Log.e(TAG, "path=" + path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setListener() {
        setPullDownRefreshListener();
        setPullUpRefreshListener();
        registerUpdateCartListener();
    }

    /**
     * 上拉刷新
     */
    private void setPullUpRefreshListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItemPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == mAdapter.getItemCount()) {
                    if (mAdapter.isMore()) {
                        msrl.setRefreshing(true);
                        action = I.ACTION_PULL_UP;
                        pageId+=I.PAGE_SIZE_DEFAULT;
                        initData();
                        getPath(pageId);
                        mContext.executeRequest(new GsonRequest<CartBean[]>(path, CartBean[].class,
                                responseDownloadCartListener(), mContext.errorListener()));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPosition = mLayoutManager.findLastVisibleItemPosition();
                msrl.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition()==0);
            }
        });
    }
    /**
     * 下拉刷新
     */
    private void setPullDownRefreshListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mtvHint.setVisibility(View.VISIBLE);
                pageId = 0;
                action = I.ACTION_PULL_DOWN;
                initData();
                getPath(pageId);
                mContext.executeRequest(new GsonRequest<CartBean[]>(path, CartBean[].class,
                        responseDownloadCartListener(), mContext.errorListener()));
            }
        });
    }

    private void initView(View layout ) {
        msrl = (SwipeRefreshLayout) layout.findViewById(R.id.srl_cart);
        msrl.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_cart);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mtvHint = (TextView) layout.findViewById(R.id.tvRefreshHint);
        mAdapter = new CartAdapter(mContext, mCartList);
        mRecyclerView.setAdapter(mAdapter);
        mtvBuy = (TextView) layout.findViewById(R.id.tv_buy);
        mtvSumPrice = (TextView) layout.findViewById(R.id.tv_sum_price);
        mtvSavePrice = (TextView) layout.findViewById(R.id.tv_save_price);
        mtvNothing = (TextView) layout.findViewById(R.id.tvNothing);
    }
    public void  sumPrice() {
        int sumPrice = 0;
        int currentPrice = 0;
        if (mCartList != null && mCartList.size() > 0) {
            for (CartBean cart : mCartList) {
                GoodDetailsBean goods = cart.getGoods();
                if (goods != null&&cart.isChecked()) {
                    sumPrice+=convertPrice(goods.getCurrencyPrice())*cart.getCount();
                    currentPrice += convertPrice(goods.getRankPrice())*cart.getCount();
                }
            }
        }
        int savePrice = sumPrice-currentPrice;
        mtvSumPrice.setText("￥"+sumPrice);
        mtvSavePrice.setText("￥"+savePrice);
    }

    private int convertPrice(String price) {
        price = price.substring(price.indexOf("￥")+1);
        int p1 = Integer.parseInt(price);
        return p1;
    }

    class UpdateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }
    UpdateCartReceiver mReceiver;
    private void registerUpdateCartListener() {
        mReceiver = new UpdateCartReceiver();
        IntentFilter filter = new IntentFilter("update_cart");
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = FuliCenterApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }
}
