package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
//import cn.ucai.fulicenter.fragment.BoutiqueFragment;
//import cn.ucai.fulicenter.fragment.CategoryFragment;
//import cn.ucai.fulicenter.fragment.NewGoodFragment;
//import cn.ucai.fulicenter.fragment.PersonalCenterFragment;
//import cn.ucai.fulicenter.view.DisPlayUtils;

public class FuliCenterMainActivity extends BaseActivity {
    public static final String TAG = "FuliCenterMainActivity";
    RadioButton mRadioNewGood,mRadioBoutique,mRadioCategory,mRadioCart,mRadioPersonalCenter;
    TextView mtvCount;
    int index;
    int currentTabIndex;
    RadioButton[] mRadios = new RadioButton[5];
//    NewGoodFragment mNewGoodFragment;
//    BoutiqueFragment mBoutiqueFragment;
//    CategoryFragment mCategoryFragment;
//    PersonalCenterFragment mPersonalCenterFragment;
    Fragment[] mFragments = new Fragment[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli_center_main);
        initFragment();
//        mFragments = new Fragment[] { mNewGoodFragment ,mBoutiqueFragment,mCategoryFragment};
        // 添加显示第一个fragment
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fl_contains, mNewGoodFragment)
//                .add(R.id.fl_contains,mBoutiqueFragment).hide(mBoutiqueFragment)
////                .add(R.id.fragment_container, contactListFragment)
//                .add(R.id.fl_contains,mCategoryFragment).hide(mCategoryFragment)
//                .show(mNewGoodFragment)
//                .commit();
        initView();
    }

    private void initFragment() {
//        mNewGoodFragment = new NewGoodFragment();
//        mBoutiqueFragment = new BoutiqueFragment();
//        mCategoryFragment= new CategoryFragment();
//        mPersonalCenterFragment = new PersonalCenterFragment();
//        mFragments[0] = mNewGoodFragment;
//        mFragments[1] = mBoutiqueFragment;
//        mFragments[2] = mCategoryFragment;
//        mFragments[4] = mPersonalCenterFragment;
    }

    private void initView() {
        mRadioNewGood = (RadioButton) findViewById(R.id.new_good);
        mRadioBoutique = (RadioButton) findViewById(R.id.boutique);
        mRadioCategory = (RadioButton) findViewById(R.id.categroy);
        mRadioCart = (RadioButton) findViewById(R.id.cart);
        mRadioPersonalCenter = (RadioButton) findViewById(R.id.personal_center);
        mtvCount = (TextView) findViewById(R.id.tvCartHint);


        mRadios[0] = mRadioNewGood;
        mRadios[1] = mRadioBoutique;
        mRadios[2] = mRadioCategory;
        mRadios[3] = mRadioCart;
        mRadios[4] = mRadioPersonalCenter;
    }
    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.categroy:
                index = 2;
                break;
            case R.id.cart:
                index = 3;
                break;
            case R.id.personal_center:
                if (FuliCenterApplication.getInstance().getUser() != null) {
                    index = 4;
                } else {
                    gotoLogin();
                }
                break;
        }
        if (currentTabIndex != index) {
//            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//            trx.hide(mFragments[currentTabIndex]);
//            if (!mFragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, mFragments[index]);
//            }
//            trx.show(mFragments[index]).commit();
            setRadioChecked(index);
//            mRadios[index].setSelected(true);
            currentTabIndex = index;
        }
    }

    private void gotoLogin() {
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void setRadioChecked(int index) {
        for (int i = 0; i <mRadios.length ; i++) {
            if (i == index) {
                mRadios[i].setChecked(true);
            } else {
                mRadios[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "currentTabIndex =" + currentTabIndex+",index="+index);
        Log.e(TAG,"user="+FuliCenterApplication.getInstance().getUser());
        if (FuliCenterApplication.getInstance().getUser() != null) {
            index = 4;
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(mFragments[currentTabIndex]);
                if (!mFragments[index].isAdded()) {
                    trx.add(R.id.fragment_container, mFragments[index]);
                }
                trx.show(mFragments[index]).commit();
                setRadioChecked(index);
                currentTabIndex = index;
            }
        } else {
            setRadioChecked(index);
        }
    }
}
