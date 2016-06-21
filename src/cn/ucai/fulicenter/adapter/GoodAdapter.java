package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailActivity;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by sks on 2016/6/20.
 */
public class GoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    ArrayList<NewGoodBean> mNewGoodList;
    ViewGroup parent;
    String footerText;
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER=1;
    boolean isMore;
    int sortBy;
    FooterViewHolder mFooterViewHolder;
    public GoodAdapter(Context mContext,ArrayList<NewGoodBean> mNewGoodList,int sortBy) {
        this.mContext = mContext;
        this.sortBy = sortBy;
        this.mNewGoodList = mNewGoodList;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        sort(sortBy);
        notifyDataSetChanged();
    }

    private void sort(final int sortBy) {
        Collections.sort(mNewGoodList, new Comparator<NewGoodBean>() {
            @Override
            public int compare(NewGoodBean g1, NewGoodBean g2) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (g1.getAddTime()-g2.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (g2.getAddTime()-g1.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                    {
                        int p1 = convertPrice(g1.getCurrencyPrice());
                        int p2 = convertPrice(g2.getCurrencyPrice());
                        result = p1-p2;
                        break;
                    }
                    case  I.SORT_BY_PRICE_DESC:
                    {
                        int p1 = convertPrice(g1.getCurrencyPrice());
                        int p2 = convertPrice(g2.getCurrencyPrice());
                        result = p2-p1;
                    }
                    break;
                }
                return result;
            }
            private  int convertPrice(String price) {
                price = price.substring(price.indexOf("￥")+1);
                int p1 = Integer.parseInt(price);
                return p1;
            }
        });
    }

    public GoodAdapter(Context context, ArrayList<NewGoodBean> mNewGoodList) {
        this.mContext = context;
        this.mNewGoodList = mNewGoodList;
    }

    public boolean isMore() {
        return isMore;

    }

    public void setMore(boolean more) {
        isMore = more;
        if (isMore) {
            setFooterText("加载更多数据");
        } else {
            setFooterText("没有更多数据");
        }
        notifyDataSetChanged();
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }
    public void initContact(ArrayList<NewGoodBean> list) {
        this.mNewGoodList.clear();
        this.mNewGoodList.addAll(list);
        sort(sortBy);
        notifyDataSetChanged();
    }

    public void addContact(ArrayList<NewGoodBean> contactList) {
        this.mNewGoodList.addAll(contactList);
        sort(sortBy);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        RecyclerView.ViewHolder holder = null;
        View layout = null;
        final LayoutInflater filter = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_ITEM:
                layout = filter.inflate(R.layout.item_new_good, parent, false);
                holder = new GoodItemViewHolder(layout);
                break;
            case TYPE_FOOTER :
                layout = filter.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            mFooterViewHolder = (FooterViewHolder) holder;
            return;
        }
        GoodItemViewHolder holder1 = (GoodItemViewHolder)holder;
        final NewGoodBean good = mNewGoodList.get(position);
        holder1.mtvGoodName.setText(good.getGoodsName());
        holder1.mtvPrice.setText(good.getShopPrice());
        ImageUtils.setNewGoodThumb(good.getGoodsThumb(),holder1.mAvatar);
        holder1.ll_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodDetailActivity.class).putExtra(D.NewGood.KEY_GOODS_ID,good.getGoodsId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewGoodList==null?1:mNewGoodList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class GoodItemViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView mAvatar;
        TextView mtvGoodName,mtvPrice;
        LinearLayout ll_good;
        public GoodItemViewHolder(View itemView) {
            super(itemView);
            mAvatar = (NetworkImageView) itemView.findViewById(R.id.nivAvatar);
            mtvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            mtvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            ll_good = (LinearLayout) itemView.findViewById(R.id.layout_good);

        }
    }


}