package cn.ucai.fulicenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BaiduMapActivity;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class CategoryAdapter extends BaseExpandableListAdapter{
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    Context mContext;

    public CategoryAdapter(ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList, Context mContext) {
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;
        this.mContext = mContext;
    }

    public void addItems(ArrayList<CategoryGroupBean> GroupList,ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        this.mGroupList.addAll(GroupList);
        this.mChildList.addAll(ChildList);
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return mGroupList==null?0:mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList==null||mChildList.get(groupPosition)==null?0:mChildList
                .get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewGroupHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_category, parent, false);
            groupHolder = new ViewGroupHolder();
            groupHolder.tvGroupName = (TextView) convertView.findViewById(R.id.tv_category_name);
            groupHolder.ivGroupThumb = (NetworkImageView) convertView.findViewById(R.id.niv_category);
            groupHolder.ivIndicator = (ImageView) convertView.findViewById(R.id.iv_expand);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewGroupHolder) convertView.getTag();
        }
        CategoryGroupBean group = (CategoryGroupBean) getGroup(groupPosition);
        groupHolder.tvGroupName.setText(group.getName());
        String imgUrl = group.getImageUrl();
        String url = I.DOWNLOAD_DOWNLOAD_CATEGORY_GROUP_IMAGE_URL+imgUrl;
        ImageUtils.setThumb(url,groupHolder.ivGroupThumb);
        if (isExpanded) {
            groupHolder.ivIndicator.setImageResource(R.drawable.expand_off);
        } else {
            groupHolder.ivIndicator.setImageResource(R.drawable.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildHolder itemHolder=null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_category_child, parent, false);
            itemHolder = new ViewChildHolder();
            itemHolder.ivCategoryChildThumb = (NetworkImageView) convertView.findViewById(R.id.niv_category_child);
            itemHolder.tvCategoryChildName = (TextView) convertView.findViewById(R.id.tv_category_child);
            itemHolder.layoutChild = (RelativeLayout) convertView.findViewById(R.id.layout_category_child);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewChildHolder) convertView.getTag();
        }
        String imaUrl = mChildList.get(groupPosition).get(childPosition).getImageUrl();
        String url = I.DOWNLOAD_DOWNLOAD_CATEGORY_CHILD_IMAGE_URL+imaUrl;
        Log.e("error", "categoryadapter url=" + url);
        ImageUtils.setThumb(url,itemHolder.ivCategoryChildThumb);
        itemHolder.tvCategoryChildName.setText(mChildList.get(groupPosition).get(childPosition).getName());
        itemHolder.layoutChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CategoryChildActivity.class)
                        .putExtra(D.CategoryChild.PARENT_ID,mChildList
                                .get(groupPosition)
                                .get(childPosition)
                                .getId())
                        .putExtra(D.CategoryGroup.NAME,mGroupList
                                .get(groupPosition).getName())
                        .putExtra("childList",(ArrayList<CategoryChildBean>)mChildList
                                        .get(groupPosition)));
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewGroupHolder {
        NetworkImageView ivGroupThumb;
        TextView tvGroupName;
        ImageView ivIndicator;
    }

    class ViewChildHolder {
        RelativeLayout layoutChild;
        NetworkImageView ivCategoryChildThumb;
        TextView tvCategoryChildName;
    }
}
