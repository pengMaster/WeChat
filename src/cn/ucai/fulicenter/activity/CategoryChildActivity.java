package cn.ucai.fulicenter.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;

import java.io.Serializable;
import java.util.ArrayList;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.ColorBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.CatChildFilterButton;
import cn.ucai.fulicenter.view.ColorFilterButton;
import cn.ucai.fulicenter.view.DisPlayUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryChildActivity extends BaseActivity {
    CategoryChildActivity mContext;
    ArrayList<NewGoodBean> mGoodList;
    GoodAdapter mAdapter;
    private int pageId = 0;
    private int action = I.ACTION_DOWNLOAD;
    String path;
    //分类Button
    CatChildFilterButton mCatChildFilterButton;
    //颜色筛选Button
    ColorFilterButton mColorFilterButton;
    /**
     * 下拉刷新空间
     */
    SwipeRefreshLayout msrl;
    RecyclerView mRecyclerView;
    TextView mtvHint;
    GridLayoutManager mGridLayoutManager;
    int catId;
    String groupName;
    ArrayList<CategoryChildBean> mChildList;

    /**
     * 按价格排序
     */
    Button mbtnPriceSort;
    /**
     * 按上架时间排序
     */
    Button mbtnAddTimeSort;
    /**
     * 商品按价格排序
     * true：升序排序
     * false：降序排序
     */
    boolean mSortByPriceAsc;
    /**
     * 商品按上架时间排序
     * true：升序排序
     * false：降序排序
     */
    boolean mSortByAddTimeAsc;
    /**
     * 当前排序
     */
    private int sortBy;
    public CategoryChildActivity() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_category_child);
        mContext = this;
        mGoodList = new ArrayList<NewGoodBean>();
        initView();
        initData();
        setListen
        er();
    }



    private void initData() {
        mChildList = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra("childList");
        catId = getIntent().getIntExtra(D.CategoryChild.PARENT_ID, 0);
        getPath(pageId,catId);
        mContext.executeRequest(new GsonRequest<NewGoodBean[]>(path, NewGoodBean[].class,
                responseDownloadNewGoodListener(), mContext.errorListener()));
        try {
            String colorPath = new ApiParams()
                    .with(I.Color.CAT_ID, catId + "")
                    .getRequestUrl(I.REQUEST_FIND_COLOR_LIST);
            mContext.executeRequest(new GsonRequest<ColorBean[]>(colorPath, ColorBean[].class,
                    responseDownloadColorListener(), mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<ColorBean[]> responseDownloadColorListener() {
        return new Response.Listener<ColorBean[]>() {
            @Override
            public void onResponse(ColorBean[] colorBeen) {
                if (colorBeen != null) {
                    mColorFilterButton.setVisibility(View.VISIBLE);
                    ArrayList<ColorBean> list = Utils.array2List(colorBeen);
                    mColorFilterButton.setOnColorFilterClickListener(groupName,mChildList,list);
                }
            }
        };
    }

    private Response.Listener<NewGoodBean[]> responseDownloadNewGoodListener() {
        return new Response.Listener<NewGoodBean[]>() {
            @Override
            public void onResponse(NewGoodBean[] newGoodBeen) {
                if (newGoodBeen != null) {
                    mAdapter.setMore(true);
                    msrl.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    mAdapter.setFooterText(getResources().getString(R.string.load_more));
                    //将数组转换为集合
                    ArrayList<NewGoodBean> list = Utils.array2List(newGoodBeen);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initContact(list);
                    } else if (action==I.ACTION_PULL_UP) {
                        mAdapter.addContact(list);
                    }
                    if (newGoodBeen.length < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                        mAdapter.setFooterText(getResources().getString(R.string.no_more));
                    }
                }
            }
        };
    }


    private String getPath(int pageId,int catId) {
        try {
            path = new ApiParams()
                    .with(I.NewAndBoutiqueGood.CAT_ID,  catId+ "")
                    .with(I.PAGE_ID, pageId + "")
                    .with(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                    .getRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setListener() {
        setPullDownRefreshListener();
        setPullUpRefreshListener();
        SortStateChangedListener mSortStateChangedListener = new SortStateChangedListener();
        mbtnPriceSort.setOnClickListener(mSortStateChangedListener);
        mbtnAddTimeSort.setOnClickListener(mSortStateChangedListener);
        mCatChildFilterButton.setOnCatFilterClickListener(groupName,mChildList);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastItemPosition == mAdapter.getItemCount() - 1) {
                    if (mAdapter.isMore()) {
                        msrl.setRefreshing(true);
                        action = I.ACTION_PULL_UP;
                        pageId+=I.PAGE_SIZE_DEFAULT;
                        getPath(pageId,catId);
                        mContext.executeRequest(new GsonRequest<NewGoodBean[]>(path,
                                NewGoodBean[].class, responseDownloadNewGoodListener(),
                                mContext.errorListener()));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
                msrl.setEnabled(mGridLayoutManager.findFirstCompletelyVisibleItemPosition()==0);
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
                getPath(pageId,catId);
                mContext.executeRequest(new GsonRequest<NewGoodBean[]>(path, NewGoodBean[].class,
                        responseDownloadNewGoodListener(), mContext.errorListener()));
            }
        });
    }

    private void initView( ) {
        groupName = getIntent().getStringExtra(D.CategoryGroup.NAME);
        mCatChildFilterButton = (CatChildFilterButton) findViewById(R.id.btnCatChildFilter);
        mColorFilterButton = (ColorFilterButton) findViewById(R.id.btnColorFilter);
        mColorFilterButton.setVisibility(View.GONE);
        mCatChildFilterButton.setText(groupName);
        msrl = (SwipeRefreshLayout)findViewById(R.id.srl_category_child);
        msrl.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mtvHint = (TextView) findViewById(R.id.tvRefreshHint);
        mGridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_category_child);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new GoodAdapter(mContext, mGoodList,I.SORT_BY_ADDTIME_DESC);
        mRecyclerView.setAdapter(mAdapter);
        DisPlayUtils.initBack(mContext);
        mbtnPriceSort = (Button) findViewById(R.id.btn_price_sort);
        mbtnAddTimeSort = (Button) findViewById(R.id.btn_add_time_sort);
    }

    class SortStateChangedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("error","SortStateChangedListener"+"SortStateChangedListener");
            Drawable right = null;
            int resId;
            switch (v.getId()) {
                case R.id.btn_price_sort:
                    Log.e("error","btnprice_sort"+"btnprice_sort");
                    if (mSortByPriceAsc) {
                        sortBy = I.SORT_BY_PRICE_ASC;
                        right = mContext.getResources().getDrawable(R.drawable.arrow_order_up);
                        resId = R.drawable.arrow_order_up;
                    } else {
                        sortBy = I.SORT_BY_PRICE_DESC;
                        right = mContext.getResources().getDrawable(R.drawable.arrow_order_down);
                        resId = R.drawable.arrow_order_down;
                    }
                    break;
                case R.id.btn_add_time_sort:
                    Log.e("error","btn_add_time_sort"+"btn_add_time_sort");
                    if (mSortByAddTimeAsc) {
                        sortBy = I.SORT_BY_ADDTIME_ASC;
                        right = mContext.getResources().getDrawable(R.drawable.arrow_order_up);
                        resId = R.drawable.arrow_order_up;
                    } else {
                        sortBy = I.SORT_BY_ADDTIME_DESC;
                        right = mContext.getResources().getDrawable(R.drawable.arrow_order_down);
                        resId = R.drawable.arrow_order_down;
                    }
                    mSortByAddTimeAsc=!mSortByAddTimeAsc;
                    right.setBounds(0,0, ImageUtils.getDrawableWidth(mContext,resId),
                            ImageUtils.getDrawableHeight(mContext,resId));
                    mbtnAddTimeSort.setCompoundDrawables(null,null,right,null);
                    break;
            }
            mAdapter.setSortBy(sortBy);
        }
    }

}
