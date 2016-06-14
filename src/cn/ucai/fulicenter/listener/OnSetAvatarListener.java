package cn.ucai.fulicenter.listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.FileUtils;


/**
 *
 * 获取头像的框架
 *     1、通过拍照获取头像
 *     2、从相册获取头像
 *     3、对头像进行裁剪，拍照的头像保存至sd卡的当前项目文件夹下
 * 技术点
 *     1、启动系统拍照的Activity，实现拍照并保存照片。
 *     2、启动系统的相册Activity，从相册获取头像
 *     3、启动系统的裁剪Activity，对头像进行裁剪并保存。
 *     4、监听拍照和从相册选取头像两个按钮的单击事件
 *     5、用PopuWindow实现悬浮窗口显示/隐藏
 *     6、处理拍照、相册选取、裁剪三个Activity返回值
 *     如此，调用本框架的Activity只需在启动处和onActivityResult是两个命令即可实现获取头像的
 *     功能。
 * Created by yao on 2016/3/19.
 *
 */
public class OnSetAvatarListener implements View.OnClickListener {
    private static final int REQUEST_TAKE_PICTURE=1;
    private static final int REQUEST_CHOOSE_PHOTO=2;
    public static final int REQUEST_CROP_PHOTO=3;
    private Activity mActivity;
    /** popuWindos的布局view*/
    private View mLayout;

    PopupWindow mPopuWindow;

    /**账号*/
    String mUserName;

    /**
     * 头像类型：
     * user_avatar:个人头像
     * group_cion:群主logo
     */
    String mAvatarType;

    /**
     * 构造器
     * @param mActivity：PopuWindow宿主Activity
     * @param parentId：宿主Activity的布局的id
     * @param userName：个人账号和群号
     * @param avatarType：头像类型：user_avatar或group_icon
     */
    public OnSetAvatarListener(Activity mActivity, int parentId, String userName, String avatarType) {
        //成员变量赋值
        this.mActivity = mActivity;
        mUserName=userName;
        mAvatarType=avatarType;

        //获取父容器的view
        View parentLayout = mActivity.findViewById(parentId);
        //获取PopuWindow的布局view
        mLayout= View.inflate(mActivity, R.layout.popu_show_avatar,null);

        //设置拍照和从相册选取两个按钮的单击事件响应
        mLayout.findViewById(R.id.btn_take_picture).setOnClickListener(this);
        mLayout.findViewById(R.id.btn_choose_photo).setOnClickListener(this);

        //显示PopuWindow
        showPopupWindow(parentLayout);
    }

    /**
     * 显示选择拍照和从相册选取两个按钮的PopuWindow
     * @param parentLayout
     */
    private void showPopupWindow(View parentLayout) {
        mPopuWindow = new PopupWindow(mLayout, getScreenDisplay().widthPixels, (int)(90*getScreenDisplay().density));
        //设置触摸PopupWindow之外的区域能关闭PopupWindow
        mPopuWindow.setOutsideTouchable(true);
        //设置PopupWindow可触摸
        mPopuWindow.setTouchable(true);
        //设置PopupWindow获取焦点
        mPopuWindow.setFocusable(true);
        //设置popuWindow的背景,必须设置，否则PopupWindow不能隐藏
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置popuWindow进入和隐藏的动画
        mPopuWindow.setAnimationStyle(R.style.styles_pop_window);
        //设置PopuWindow从屏幕底部进入
        mPopuWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 获取表示屏幕尺寸、密度等信息的对象
     * @return
     */
    private DisplayMetrics getScreenDisplay(){
        //创建用于获取屏幕尺寸、像素密度的对象
        Display defaultDisplay  = mActivity.getWindowManager().getDefaultDisplay();
        //创建用于获取屏幕尺寸、像素密度等信息的对象
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        return outMetrics;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_picture:
                takePicture();
                break;
            case R.id.btn_choose_photo:
                choosePhoto();
                break;
        }
    }

    /**
     * 从相册选择头像，启动系统预定义的Activity并要求返回结果
     */
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    /**拍照:启动系统拍照的Activity，要求返回拍照结果*/
    private void takePicture() {
        File file = FileUtils.getAvatarPath(mActivity,mAvatarType, mUserName + ".jpg");
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mActivity.startActivityForResult(intent,REQUEST_TAKE_PICTURE);
    }

    /**
     * 关闭PopuWindow
     */
    public void closePopuAvatar() {
        if (mPopuWindow != null) {
            mPopuWindow.dismiss();
        }
    }

    /**
     * 设置拍照或从相册选择返回的结果，本方法在Activity.onActivityResult()调用
     * @param requestCode
     * @param data
     * @param ivAvatar
     */
    public void setAvatar(int requestCode, Intent data, ImageView ivAvatar) {
        switch (requestCode) {
            case REQUEST_CHOOSE_PHOTO:
                if (data != null) {
                    startCropPhotoActivity(data.getData(), 200, 200,REQUEST_CROP_PHOTO);
                }
                break;
            case REQUEST_TAKE_PICTURE:
                if (data != null) {
                    startCropPhotoActivity(data.getData(), 200, 200,REQUEST_CROP_PHOTO);
                }
                break;
            case REQUEST_CROP_PHOTO:
                saveCropAndShowAvatar(ivAvatar, data);
                closePopuAvatar();
                break;
        }
    }

    /**
     * 保存头像至sd卡的Android文件夹，并显示头像
     * @param ivAvatar
     * @param data
     */
    private void saveCropAndShowAvatar(ImageView ivAvatar, Intent data) {
        Bundle extras = data.getExtras();
        Bitmap avatar = extras.getParcelable("data");
        if (avatar == null) {
            return;
        }
        ivAvatar.setImageBitmap(avatar);
        File file = FileUtils.getAvatarPath(mActivity,mAvatarType, mUserName + ".jpg");
        if(!file.getParentFile().exists()){
            Toast.makeText(mActivity, "照片保存失败,保存的路径不存在", Toast.LENGTH_LONG).show();
            return ;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            avatar.compress(Bitmap.CompressFormat.JPEG,100,out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("main", "头像保存失败");
        }
    }

    /**
     * 启动裁剪的Activity
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void startCropPhotoActivity(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mActivity.startActivityForResult(intent,requestCode);
    }

    /**
     * 返回拍照文件保存的位置
     * @return
     */
    public static File getAvatarFile(Activity activity, String avatar){
        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file;
        try {
            file = new File(dir,avatar);
            boolean isExists = file.getParentFile().exists();
            if(!isExists){
                isExists = file.getParentFile().mkdirs();
            }
            if(isExists){
                return file;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
