package cn.ucai.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {
    public TextView mtvFootertext;

    public FooterViewHolder(View itemView) {
        super(itemView);
        mtvFootertext = (TextView) itemView.findViewById(R.id.tv_footer);
    }
}
