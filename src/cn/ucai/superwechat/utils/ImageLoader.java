package cn.ucai.superwechat.utils;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;

/**
 * 异步加载图片的框架
 * 1、从网络下载图片
 * 2、缓存图片至内存
 * 3、缓存图片至本地
 * 4、回调程序员写的显示图片的代码
 * @author yao
 *
 */
public class ImageLoader {
	public static ImageLoader mInstance;
	/**Activity实例*/
	Context mContext;
	/** 缓存图片至内存的集合*/
	LruCache<String, Bitmap> mCaches;
	
	/**定义图片下载事件完成的如何显示的接口
	 * 预存程序员编写的显示图片的代码.
	 * @author yao
	 *
	 */
	public interface OnImageLoadListener{
		/**
		 * 图片下载成功事件的处理
		 * @param path：图片的地址，用于防止错位显示
		 * @param bitmap:下载图片的位图格式
		 */
		void onSuccess(String path, Bitmap bitmap);
		
		/**
		 * 图片下载失败事件的处理
		 * @param errorMsg：错误信息
		 */
		void error(String errorMsg);
	}
	
	/**
	 * 私有的构造器
	 * @param context
	 */
	private ImageLoader(Context  context){
		mContext=context;
		//获取app的内存容量，单位：字节
		int maxSize=(int) Runtime.getRuntime().maxMemory();
		//创建缓存集合
		mCaches=new LruCache<String, Bitmap>(maxSize/4){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes()*value.getHeight();
			}
		};
	}
	
	/**
	 *  单例模式
	 * @param context
	 * @return
	 */
	public static ImageLoader getInstance(Context context){
		if(mInstance==null){
			mInstance=new ImageLoader(context);
		}
		return mInstance;
	}

	/**
	 * 定义一个封装了图片信息的实体类
	 */
	class ImageBean{
		/** 图片的下载地址*/
		String path;
		String imgName;
		int width,height;
		/** 保存程序员编写的处理图片的代码*/
		OnImageLoadListener listener;
		/** 下载的图片*/
		Bitmap bitmap;
		/** 下载失败的信息*/
		String msg;
		/**
		 * 
		 * @param path：图片的下载地址
		 * @param width：宽度
		 * @param height:高度
		 * @param listener：程序员写的处理图片的代码
		 */
		public ImageBean(String path, String imgName,int width, int height,
				OnImageLoadListener listener) {
			super();
			this.path = path;
			this.imgName=imgName;
			this.width = width;
			this.height = height;
			this.listener = listener;
		}
		
	}
	/**
	 * 在工作线程中下载图片，下载完成后显示图片
	 * @author yao
	 *
	 */
	class DownloadImageTask extends AsyncTask<ImageBean, Void, ImageBean>{
		@Override
		protected ImageBean doInBackground(ImageBean... params) {
			ImageBean bean=params[0];
			try {
				//下载图片，返回实体：entity
				HttpEntity entity = HttpUtils.getEntity(bean.path, null, HttpUtils.METHOD_GET);
				//获取图片字节数组
				byte[] data = EntityUtils.toByteArray(entity);
				//将字节数组转换为Bitmap
				bean.bitmap = BitmapUtils.getBitmap(data, bean.width, bean.height);
				//缓存至内存
				mCaches.put(bean.path, bean.bitmap);
				//缓存至sd卡
				BitmapUtils.saveBitmap(bean.bitmap, FileUtils.getDir(mContext,bean.imgName));
				bean.msg="图片下载成功";
			} catch (Exception e) {
				bean.msg="图片下载失败";
			}finally{
				HttpUtils.closeClient();
			}
			return bean;
		}
		
		@Override
		protected void onPostExecute(ImageBean result) {
			if(result==null){
				return ;
			}
			//显示图片，回调程序员写的处理图片下载结果的代码
			if(!result.msg.equals("图片下载失败")){
				result.listener.onSuccess(result.path, result.bitmap);
			}else{
				result.listener.error("图片下载失败");
			}
		}
	}
	
	/**
	 * 下载并显示图片
	 * @param path：图片的地址
	 * @param width：图片的宽度
	 * @param height：图片的高度
	 * @param listener:程序员写的处理图片的代码
	 * @return
	 */
	public Bitmap displayImage(String path,String imgName,int width,int height,OnImageLoadListener listener){
		//若图片缓存在内存，则直接返回该图片
		if(mCaches.get(path)!=null){
			return mCaches.get(path);
		}
		//若图片缓存在sd卡，则直接返回该图片
		Bitmap bitmap = BitmapUtils.getBitmap(FileUtils.getDir(mContext,imgName));
		if(bitmap!=null){
			return bitmap;
		}
		//创建一个ImageBean对象,    预存代码
		ImageBean bean=new ImageBean(path, imgName,width, height, listener);
		//创建DownloadImageTask对象并执行下载
		new DownloadImageTask().execute(bean);
		return null;
	}
	
}
