package cn.ucai.fulicenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.RequestManager;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.utils.Utils;

/**
 * 显示分类中当前所属小类的列表
 * @author yao
 *
 */
public class CatChildFilterButton extends Button {
    Context mContext;
    CatChildFilterButton mbtnTop;
    PopupWindow mPopupWindow;
    GridView mgvCategory;
    CatFilterAdapter mAdapter;
    OnClickListener mListener;
    
    /** 
     * true:arrow down
     * false:arrow up
     * */
    boolean mExpandOff;
    
    public CatChildFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mbtnTop=this;
        mExpandOff=true;
        initGridView();
    }

    private void initPopupWindow() {
        mPopupWindow=new PopupWindow();
        mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
        if(mgvCategory.getAdapter().getCount()<16){
            mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        }else{
            mPopupWindow.setHeight(Utils.px2dp(mContext, 200));
        }
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setContentView(mgvCategory);
        mPopupWindow.showAsDropDown(mbtnTop);
    }

    private void initGridView() {
        mgvCategory=new GridView(mContext);
        mgvCategory.setColumnWidth(Utils.px2dp(mContext, 1500));
        mgvCategory.setHorizontalSpacing(Utils.px2dp(mContext, 10));
        mgvCategory.setVerticalSpacing(Utils.px2dp(mContext, 10));
        mgvCategory.setNumColumns(GridView.AUTO_FIT);
        mgvCategory.setBackgroundColor(Color.TRANSPARENT);
        mgvCategory.setPadding(3, 3, 3, 3);
        mgvCategory.setCacheColorHint(0);
    }
    
    private void setBtnTopArrow() {
        Drawable right=null;
        int resId;
        if(mExpandOff){
            right=mContext.getResources().getDrawable(R.drawable.arrow2_down);
            resId = R.drawable.arrow2_down;
        }else{
            right=mContext.getResources().getDrawable(R.drawable.arrow2_up);
            resId = R.drawable.arrow2_up;
        }
        right.setBounds(0, 0, ImageUtils.getDrawableWidth(mContext,resId), ImageUtils.getDrawableHeight(mContext,resId));
        mbtnTop.setCompoundDrawables(null, null, right, null);
        mExpandOff=!mExpandOff;
    }
    
    /**
     * 显示分类列表的适配器
     * @author yao
     *
     */
    class CatFilterAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> Children;

        public CatFilterAdapter(Context context,
                ArrayList<CategoryChildBean> list) {
            super();
            this.context = context;
            this.Children = list;
        }

        @Override
        public int getCount() {
            return Children==null?0:Children.size();
        }

        @Override
        public CategoryChildBean getItem(int position) {
            return Children.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View layout, final ViewGroup parent) {
            ViewChildHolder holder=null;
            if(layout==null){
                layout= View.inflate(context, R.layout.item_cat_filter, null);
                holder=new ViewChildHolder();
                holder.layoutItem=(RelativeLayout) layout.findViewById(R.id.layout_category_child);
                holder.ivThumb=(NetworkImageView) layout.findViewById(R.id.ivCategoryChildThumb);
                holder.tvChildName=(TextView) layout.findViewById(R.id.tvCategoryChildName);
                layout.setTag(holder);
            }else{
                holder=(ViewChildHolder) layout.getTag();
            }
            final CategoryChildBean child =getItem(position);
            String name=child.getName();
            holder.tvChildName.setText(name);
            String imgUrl=child.getImageUrl();
            String url= I.DOWNLOAD_DOWNLOAD_CATEGORY_CHILD_IMAGE_URL+imgUrl;

            holder.ivThumb.setDefaultImageResId(R.drawable.nopic);
            holder.ivThumb.setErrorImageResId(R.drawable.nopic);
            holder.ivThumb.setImageUrl(url, RequestManager.getImageLoader());

            holder.layoutItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPopupWindow.isShowing()){
                        mPopupWindow.dismiss();
                    }
                    Intent intent=new Intent(mContext, CategoryChildActivity.class);
                    intent.putExtra(I.CategoryChild.CAT_ID, child.getId());
                    intent.putExtra("childList", Children);
                    intent.putExtra(I.CategoryGroup.NAME, mbtnTop.getText().toString());
                    mContext.startActivity(intent);
                    ((CategoryChildActivity)mContext).finish();
                }
            });
            return layout;
        }

        class ViewChildHolder{
            RelativeLayout layoutItem;
            NetworkImageView ivThumb;
            TextView tvChildName;
        }
    }
    
    /**
     * 设置分类列表的下拉按钮单击事件监听
     * @param groupName
     * @param childList
     */
    public void setOnCatFilterClickListener(final String groupName,
            final ArrayList<CategoryChildBean> childList){
        mbtnTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnTop.setTextColor(Color.WHITE);
                mbtnTop.setText(groupName);
                if(mExpandOff){//若分类列表的窗口未打开，则弹出窗口
                    mAdapter=new CatFilterAdapter(mContext, childList);
                    mgvCategory.setAdapter(mAdapter);
                    initPopupWindow();
                }else{//否则，关闭窗口
                    if(mPopupWindow.isShowing()){
                        mPopupWindow.dismiss();
                    }
                }
                setBtnTopArrow();
            }
        });
    }
}
