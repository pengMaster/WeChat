package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class DisPlayUtils {
    public static void initBack(final Activity activity){
        View clickArea = activity.findViewById(R.id.iv_good_details_back);
        clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
    public static void initBackwithTitle(final Activity activity,String title) {
        TextView tvTitle = (TextView) activity.findViewById(R.id.tv_good_details);
        tvTitle.setText(title);
        initBack(activity);
    }
}
