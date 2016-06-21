package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.ColorBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.bean.Properties;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 * 自定义控件,显示颜色列表
 * @author yao
 */
public class ColorFilterButton extends Button {
    CategoryChildActivity mContext;
    ColorFilterButton mbtnTop;
    PopupWindow mPopupWindow;
    GridView mgvColor;
    ColorFilterAdapter mAdapter;
    ArrayList<CategoryChildBean> mChildList;
    String mGroupName;
    
    public ColorFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext= (CategoryChildActivity) context;
        mbtnTop=this;
        initGridView();
    }

    private void initGridView() {
        mgvColor=new GridView(mContext);
        mgvColor.setColumnWidth(Utils.px2dp(mContext, 800));
        mgvColor.setNumColumns(GridView.AUTO_FIT);
        mgvColor.setHorizontalSpacing(Utils.px2dp(mContext, 3));
        mgvColor.setVerticalSpacing(Utils.px2dp(mContext, 3));
        mgvColor.setScrollingCacheEnabled(true);
        mgvColor.setCacheColorHint(0);
        mgvColor.setBackgroundColor(Color.TRANSPARENT);
        mgvColor.setPadding(3, 3, 3, 3);
    }

    class ColorFilterAdapter extends BaseAdapter {
        Context context;
        ArrayList<ColorBean> colorList;
        public ColorFilterAdapter(Context context,
                ArrayList<ColorBean> colorList) {
            super();
            this.context = context;
            this.colorList = colorList;
        }
        
        @Override
        public int getCount() {
            return colorList==null?0:colorList.size();
        }

        @Override
        public ColorBean getItem(int position) {
            return colorList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View layout, ViewGroup parent) {
            ViewHolder holder=null;
            if(layout==null){
                layout= View.inflate(context, R.layout.item_color_filter, null);
                holder=new ViewHolder();
                holder.layoutItem=(LinearLayout) layout.findViewById(R.id.layout_color_filter);
                holder.tvName=(TextView) layout.findViewById(R.id.tvName);
                layout.setTag(holder);
            }else{
                holder=(ViewHolder) layout.getTag();
            }
            final ColorBean color = getItem(position);
            String colorName=color.getColorName();
            if(colorName.length()>4){
                colorName=colorName.substring(0,3);
            }
            holder.tvName.setText(colorName);
            holder.layoutItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String path = new ApiParams().with(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT+"")
                                .with(I.PAGE_ID, 0+"")
                                .with(I.NewAndBoutiqueGood.CAT_ID, color.getCatId()+"")
                                .getRequestUrl(I.REQUEST_FIND_GOODS_DETAILS);
                        mContext.executeRequest(new GsonRequest<GoodDetailsBean[]>(path,GoodDetailsBean[].class,
                                responseDownloadListener(color.getCatId()),mContext.errorListener()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return layout;
        }
        /**
         * 下载分类的大类和小类商品的数据
         *
         */
        private Response.Listener<GoodDetailsBean[]> responseDownloadListener(final int catId) {
            return new Response.Listener<GoodDetailsBean[]>() {
                @Override
                public void onResponse(GoodDetailsBean[] goodArray) {
                    if(goodArray!=null){
                        ArrayList<GoodDetailsBean> goods = Utils.array2List(goodArray);

                        //将GoodDetailsBean类型的集合转换为NewGoodBean类型的集合
                        ArrayList<NewGoodBean> goodList=new ArrayList<NewGoodBean>();
                        for(int i=0;i<goods.size();i++){
                            GoodDetailsBean goodDetails = goods.get(i);
                            NewGoodBean good=new NewGoodBean();
                            good.setAddTime(goodDetails.getAddTime());
                            good.setCatId(goodDetails.getCatId());
                            Properties p = goodDetails.getPropertyBean()[0];
                            good.setColorCode(p.getColorCode());
                            good.setColorId(p.getColorId());
                            good.setColorName(p.getColorName());
                            good.setColorUrl(p.getColorUrl());
                            good.setCurrencyPrice(goodDetails.getCurrencyPrice());
                            good.setGoodsBrief(goodDetails.getGoodsBrief());
                            good.setGoodsEnglishName(goodDetails.getGoodsEnglishName());
                            good.setGoodsId(goodDetails.getGoodsId());
                            good.setGoodsImg(goodDetails.getGoodsImg());
                            good.setGoodsName(goodDetails.getGoodsName());
                            good.setGoodsThumb(goodDetails.getGoodsThumb());
                            good.setId(goodDetails.getId());
                            good.setIsPromote(goodDetails.isPromote());
                            good.setPromotePrice(goodDetails.getPromotePrice());
                            good.setRankPrice(goodDetails.getRankPrice());
                            good.setShopPrice(goodDetails.getShopPrice());
                            goodList.add(good);
                        }
                        Intent intent=new Intent(mContext, CategoryChildActivity.class);
                        intent.putExtra(D.CategoryGroup.NAME, mGroupName);
                        intent.putExtra(D.CategoryChild.CAT_ID, catId);
                        intent.putExtra("childList", mChildList);
                        intent.putExtra("goodList", goodList);
                        context.startActivity(intent);
                        ((Activity)mContext).finish();
                    }
                }
            };
        }

        class ViewHolder{
            LinearLayout layoutItem;
            TextView tvName;
        }
    }

    public void setOnColorFilterClickListener(String groupName, ArrayList<CategoryChildBean> childList,
                                              final ArrayList<ColorBean> colorList){
        mChildList=childList;
        mGroupName=groupName;
        mbtnTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow!=null){
                    if(mPopupWindow.isShowing()){
                        mPopupWindow.dismiss();
                        mPopupWindow=null;
                    }
                }else{
                    initGridView();
                    mAdapter=new ColorFilterAdapter(mContext, colorList);
                    mgvColor.setAdapter(mAdapter);
                    
                    mPopupWindow=new PopupWindow();
                    mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
                    if(mAdapter.getCount()<16){
                        mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
                    }else{
                        mPopupWindow.setHeight(Utils.px2dp(mContext, 7000));
                    }
                    mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
                    mPopupWindow.setFocusable(true);
                    mPopupWindow.setTouchable(true);
                    mPopupWindow.setOutsideTouchable(true);
                    mPopupWindow.setContentView(mgvColor);
                    mPopupWindow.showAsDropDown(mbtnTop);
                }
            }
        });
    }
}
