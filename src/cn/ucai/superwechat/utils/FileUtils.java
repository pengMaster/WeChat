package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

	/**
	 * 获取sd卡的保存位置
	 * @param path:http:/10.0.2.2/images/aa.jpg
	 */
	public static String getDir(Context context,String path) {
//		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File dir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path=dir.getAbsolutePath()+"/"+path;
		return path;
	}
	
	/**
	 * 修改本地缓存的图片名称
	 * @param context
	 * @param oldImgName
	 * @param newImgName
	 */
	public static void renameImageFileName(Context context,String oldImgName,String newImgName){
		String dir = getDir(context, oldImgName);
		File oldFile=new File(dir);
		dir=getDir(context, newImgName);
		File newFile=new File(dir);
		oldFile.renameTo(newFile);
	}

	/**
	 * 返回头像的路径
	 * @param avatrType：头像的类型，user_avatar：用户头像，group_icon：群组logo
	 * @param fielName：头像的文件名，如a.jpg
	 * @return
	 */
	public static File getAvatarPath(Activity activity, String avatrType, String fielName) {
		File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File dir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		dir = new File(dir, avatrType);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, fielName);
		return file;
	}
}
