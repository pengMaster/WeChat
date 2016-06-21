package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.activity.BoutiqueDetailsActivity;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    ArrayList<BoutiqueBean> mBoutiqueList;
    ViewGroup parent;
    String footerText;
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER=1;
    boolean isMore;
    FooterViewHolder mFooterViewHolder;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mBoutiqueList) {
        this.mContext = context;
        this.mBoutiqueList = mBoutiqueList;
    }

    public boolean isMore() {
        return isMore;

    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }
    public void initContact(ArrayList<BoutiqueBean> list) {
        this.mBoutiqueList.clear();
        this.mBoutiqueList.addAll(list);
        notifyDataSetChanged();
    }

    public void addContact(ArrayList<BoutiqueBean> contactList) {
        this.mBoutiqueList.addAll(contactList);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View layout = null;
        final LayoutInflater filter = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_ITEM:
                layout = filter.inflate(R.layout.item_boutique, parent, false);
                holder = new BoutiqueItemViewHolder(layout);
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
        BoutiqueItemViewHolder holder1 = (BoutiqueItemViewHolder)holder;
        final BoutiqueBean boutique = mBoutiqueList.get(position);
        Log.e("error", "boutique" + boutique);
        holder1.mtvBoutiqueName.setText(boutique.getName());
        holder1.mtvBoutiqueTitle.setText(boutique.getTitle());
        holder1.mtvBoutiqueDesc.setText(boutique.getDescription());
        ImageUtils.setNewGoodThumb(boutique.getImageurl(),holder1.mAvatar);
        holder1.ll_boutique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BoutiqueDetailsActivity.class)
                        .putExtra(D.Boutique.KEY_NAME, boutique.getName())
                        .putExtra(D.Boutique.KEY_ID, boutique.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoutiqueList==null?1:mBoutiqueList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class BoutiqueItemViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView mAvatar;
        TextView mtvBoutiqueName,mtvBoutiqueDesc,mtvBoutiqueTitle;
        RelativeLayout ll_boutique;
        public BoutiqueItemViewHolder(View itemView) {
            super(itemView);
            mAvatar = (NetworkImageView) itemView.findViewById(R.id.niv_boutique_avatar);
            mtvBoutiqueName = (TextView) itemView.findViewById(R.id.tv_boutique_name);
            mtvBoutiqueDesc = (TextView) itemView.findViewById(R.id.tv_boutique_desc);
            mtvBoutiqueTitle=(TextView) itemView.findViewById(R.id.tv_boutique_title);
            ll_boutique = (RelativeLayout) itemView.findViewById(R.id.layout_boutique);

        }
    }


}
