package cn.ucai.fulicenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sks on 2016/6/20.
 */
public class GoodAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class GoodItemViewHolder extends RecyclerView.ViewHolder {

        public GoodItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
