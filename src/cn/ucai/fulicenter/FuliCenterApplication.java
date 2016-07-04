/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.easemob.EMCallBack;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.Contact;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.data.RequestManager;

public class FuliCenterApplication extends Application {
//	public static String SERVER_ROOT = "http://10.0.2.2:8080/FuLiCenterServer/Server";
	public static String SERVER_ROOT = "http://192.168.8.11:8080/FuLiCenterServer/Server";
	//	public static String SERVER_ROOT = "http://192.168.1.44:8080/FuLiCenterServer/Server";
	public static Context applicationContext;
	private static FuliCenterApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	//全局的当前登录用户的收藏商品数量
	private int collectCount;


	public int getCollectCount() {
		Log.e("main", "FuliCenterApplication.getCollectCount:" + collectCount);
		return collectCount;
	}

	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
	public ArrayList<CartBean> cartList = new ArrayList<CartBean>();

	public ArrayList<CartBean> getCartList() {
		return cartList;
	}

	public void setCartList(ArrayList<CartBean> cartList) {
		this.cartList = cartList;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;
		RequestManager.init(applicationContext);
		/**
		 * this function will initialize the HuanXin SDK
		 *
		 * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
		 *
		 * 环信初始化SDK帮助函数
		 * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
		 *
		 * for example:
		 * 例子：
		 *
		 * public class DemoHXSDKHelper extends HXSDKHelper
		 *
		 * HXHelper = new DemoHXSDKHelper();
		 * if(HXHelper.onInit(context)){
		 *     // do HuanXin related work
		 * }
		 */
		hxSDKHelper.onInit(applicationContext);
		instance = this;
		refWatcher = LeakCanary.install(this);
	}

	private RefWatcher refWatcher;
	public static RefWatcher getRefWatcher(Context context) {
		return instance.refWatcher;
	}
	public static FuliCenterApplication getInstance() {
		return instance;
	}


	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getUserName() {
		return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 */
	public void setUserName(String username) {
		hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final boolean isGCM, final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
		hxSDKHelper.logout(isGCM, emCallBack);
	}

	private User user;
	private ArrayList<Contact> contactList = new ArrayList<Contact>();
	private HashMap<String, Contact> userList = new HashMap<String, Contact>();

	public HashMap<String, Contact> getUserList() {
		return userList;
	}

	public void setUserList(HashMap<String, Contact> userList) {
		this.userList = userList;
	}

	public ArrayList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
