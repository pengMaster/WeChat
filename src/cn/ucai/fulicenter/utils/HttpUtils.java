package cn.ucai.fulicenter.utils;
/*
 * 需要导入httpcore-4.3.2.jar和
 * httpmime-4.3.3.jar
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public final class HttpUtils {
	public static final int METHOD_GET=0;
	public static final int METHOD_POST=1;
	private static HttpClient mClient;
	private static final String UTF_8="utf-8";
	public static HttpEntity getEntity(String uri,ArrayList<BasicNameValuePair> params,int method) throws ClientProtocolException, IOException{
		mClient=new DefaultHttpClient();
		HttpUriRequest request=null;
		switch (method) {
		case METHOD_GET:
			StringBuilder sb=new StringBuilder(uri);
			if(params!=null && !params.isEmpty()){
			    sb.append("?");
				for (BasicNameValuePair param : params) {
					sb.append(param.getName()).append("=")
					  .append(URLEncoder.encode(param.getValue(),"UTF_8")).append("&");
				}
				sb.deleteCharAt(sb.length()-1);
			}
			request=new HttpGet(sb.toString());			
			break;
		case METHOD_POST:
			HttpPost post=new HttpPost(uri);
			if(params!=null && !params.isEmpty()){
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"UTF_8");
				post.setEntity(entity);
			}
			request=post;
			break;
		}
		mClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		mClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
		HttpResponse response = mClient.execute(request);
		System.err.println(response.getStatusLine().toString());
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			return response.getEntity();
		}
		return null;
	}
	
	public static final InputStream getInputStream(String uri,ArrayList<BasicNameValuePair> params,int method) throws IllegalStateException, ClientProtocolException, IOException{
		HttpEntity entity = getEntity(uri, params, method);
		if(entity==null){
			return null;
		}
		return entity.getContent();
	}
	
	public static void closeClient(){
		if(mClient!=null){
			mClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 以InputStream类型将上传的文件封装在HttpEntity中,用于servlet服务器
	 * @param file：上传的文件
	 * @return 
	 * @throws Exception
	 */
	public static HttpEntity createInputStreamEntity(File file) throws Exception{

		// 创建字节数组输出流，用于保存以上结构的字节
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		// 创建字节数组输入流,输入流的内容是：baos的字节数组内容

		FileInputStream fis = new FileInputStream(new File(file.getAbsolutePath()));
		// 将fis代表的图片输入中的数据保存在dos(baos)
		int len;
		byte[] buffer = new byte[1024 * 5];
		while ((len = fis.read(buffer)) != -1) {
			dos.write(buffer, 0, len);
		}
		//转换为输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		HttpEntity entity = new InputStreamEntity(bais, bais.available());
		return entity;
	}

	/**
	 * 将File类型直接封装在HttpEntity中，用于mvc服务器
	 * @param file
	 * @return
	 */
	public static HttpEntity createFileEntity(File file){
		//创建用于上传头像的文件
//		file=new File(file.getAbsolutePath()+".jpg");
//		FileBody fileBody = new FileBody(file);
//		
//		//创建包含上传文件和账号的实体
//		HttpEntity entity = MultipartEntityBuilder.create()
//			.addPart("file", fileBody)
//			.build();
//		return entity;
		return null;
	}
	
}
