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
package cn.ucai.fulicenter.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.activity.AddContactActivity;
import cn.ucai.fulicenter.activity.ChatActivity;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.activity.PublicChatRoomsActivity;
import cn.ucai.fulicenter.activity.RobotsActivity;
import cn.ucai.fulicenter.applib.controller.HXSDKHelper;

import com.android.volley.Response;
import com.easemob.chat.EMContactManager;
import cn.ucai.fulicenter.Constant;
import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.adapter.ContactAdapter;
import cn.ucai.fulicenter.bean.Contact;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.db.InviteMessgeDao;
import cn.ucai.fulicenter.db.EMUserDao;
import cn.ucai.fulicenter.domain.EMUser;
import cn.ucai.fulicenter.utils.UserUtils;
import cn.ucai.fulicenter.widget.Sidebar;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;

/**
 * 联系人列表页
 * 
 */
public class ContactlistFragment extends Fragment {
	public static final String TAG = "ContactlistFragment";
	private ContactAdapter adapter;
	//private List<EMUser> contactList;
	private List<Contact> mContactList;
	private ListView listView;
	private boolean hidden;
	private Sidebar sidebar;
	private InputMethodManager inputMethodManager;
	private List<String> blackList;
	ImageButton clearSearch;
	EditText query;
	HXContactSyncListener contactSyncListener;
	HXBlackListSyncListener blackListSyncListener;
	HXContactInfoSyncListener contactInfoSyncListener;
	View progressBar;
	Handler handler = new Handler();
    private Contact toBeProcessUser;
    private String toBeProcessUsername;
	ContactListChangedReceiver mReceiver;

	class HXContactSyncListener implements HXSDKHelper.HXSyncListener {
		@Override
		public void onSyncSucess(final boolean success) {
			EMLog.d(TAG, "on contact list sync success:" + success);
			ContactlistFragment.this.getActivity().runOnUiThread(new Runnable() {
				public void run() {
				    getActivity().runOnUiThread(new Runnable(){

		                @Override
		                public void run() {
		                    if(success){
		                        progressBar.setVisibility(View.GONE);
                                refresh();
		                    }else{
		                        String s1 = getResources().getString(cn.ucai.fulicenter.R.string.get_failed_please_check);
		                        Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
		                        progressBar.setVisibility(View.GONE);
		                    }
		                }
		                
		            });
				}
			});
		}
	}
	
	class HXBlackListSyncListener implements HXSDKHelper.HXSyncListener {

        @Override
        public void onSyncSucess(boolean success) {
            getActivity().runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    blackList = EMContactManager.getInstance().getBlackListUsernames();
                    refresh();
                }
                
            });
        }
	    
	};
	
	class HXContactInfoSyncListener implements HXSDKHelper.HXSyncListener{

		@Override
		public void onSyncSucess(final boolean success) {
			EMLog.d(TAG, "on contactinfo list sync success:" + success);
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					progressBar.setVisibility(View.GONE);
					if(success){
						refresh();
					}
				}
			});
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(cn.ucai.fulicenter.R.layout.fragment_contact_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
		if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
		    return;
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		listView = (ListView) getView().findViewById(cn.ucai.fulicenter.R.id.list);
		sidebar = (Sidebar) getView().findViewById(cn.ucai.fulicenter.R.id.sidebar);
		sidebar.setListView(listView);
        
		//黑名单列表
		blackList = EMContactManager.getInstance().getBlackListUsernames();
		//contactList = new ArrayList<EMUser>();
		mContactList = new ArrayList<Contact>();
		// 获取设置contactlist
		getContactList();
		
		//搜索框
		query = (EditText) getView().findViewById(cn.ucai.fulicenter.R.id.query);
		query.setHint(cn.ucai.fulicenter.R.string.search);
		clearSearch = (ImageButton) getView().findViewById(cn.ucai.fulicenter.R.id.search_clear);
		query.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s);
				if (s.length() > 0) {
					clearSearch.setVisibility(View.VISIBLE);
				} else {
					clearSearch.setVisibility(View.INVISIBLE);

				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		clearSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				query.getText().clear();
				hideSoftKeyboard();
			}
		});
		
		// 设置adapter
		adapter = new ContactAdapter(getActivity(), cn.ucai.fulicenter.R.layout.row_contact, mContactList);
		listView.setAdapter(adapter);




		registerForContextMenu(listView);
		
		progressBar = (View) getView().findViewById(cn.ucai.fulicenter.R.id.progress_bar);

		contactSyncListener = new HXContactSyncListener();
		HXSDKHelper.getInstance().addSyncContactListener(contactSyncListener);
		
		blackListSyncListener = new HXBlackListSyncListener();
		HXSDKHelper.getInstance().addSyncBlackListListener(blackListSyncListener);
		
		contactInfoSyncListener = new HXContactInfoSyncListener();
		((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);
		
		if (!HXSDKHelper.getInstance().isContactsSyncedWithServer()) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}

		registerContactListChangedReceiver();
		setListener();
	}

	private void setListener() {
		setContactItemClickListener();
		setContactListTouchListener();
		setAddContactListener();
	}

	private void setAddContactListener() {
		ImageView addContactView = (ImageView) getView().findViewById(cn.ucai.fulicenter.R.id.iv_new_contact);
		// 进入添加好友页
		addContactView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AddContactActivity.class));
			}
		});
	}

	private void setContactListTouchListener() {
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏软键盘
				if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getActivity().getCurrentFocus() != null)
						inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
	}

	private void setContactItemClickListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String username = adapter.getItem(position).getMContactCname();
				if (Constant.NEW_FRIENDS_USERNAME.equals(username)) {
					// 进入申请与通知页面
					EMUser user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME);
					user.setUnreadMsgCount(0);
				} else if(Constant.CHAT_ROBOT.equals(username)){
					//进入Robot列表页面
					startActivity(new Intent(getActivity(), RobotsActivity.class));
				}else {
					// demo中直接进入聊天页面，实际一般是进入用户详情页
					startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", adapter.getItem(position).getMContactCname()));
				}
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (((AdapterContextMenuInfo) menuInfo).position > 0) {
		    toBeProcessUser = adapter.getItem(((AdapterContextMenuInfo) menuInfo).position);
		    toBeProcessUsername = toBeProcessUser.getMContactCname();
			getActivity().getMenuInflater().inflate(cn.ucai.fulicenter.R.menu.context_contact_list, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == cn.ucai.fulicenter.R.id.delete_contact) {
			try {
                // 删除此联系人
                deleteContact(toBeProcessUser);
                // 删除相关的邀请消息
                InviteMessgeDao dao = new InviteMessgeDao(getActivity());
                dao.deleteMessage(toBeProcessUser.getMContactCname());
            } catch (Exception e) {
                e.printStackTrace();
            }
			return true;
		}else if(item.getItemId() == cn.ucai.fulicenter.R.id.add_to_blacklist){
			moveToBlacklist(toBeProcessUsername);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
			refresh();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
		}
	}

	/**
	 * 删除联系人
	 * @param tobeDeleteUser
     */
	public void deleteContact(final Contact tobeDeleteUser) {
		String st1 = getResources().getString(cn.ucai.fulicenter.R.string.deleting);
		final String st2 = getResources().getString(cn.ucai.fulicenter.R.string.Delete_failed);
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage(st1);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		try {
			String path = new ApiParams()
					.with(I.Contact.USER_NAME, FuliCenterApplication.getInstance().getUserName())
					.with(I.Contact.CU_NAME, tobeDeleteUser.getMContactCname())
					.getRequestUrl(I.REQUEST_DELETE_CONTACT);
			((MainActivity)getActivity()).executeRequest(new GsonRequest<Boolean>(path,Boolean.class,responsedeleteListner(tobeDeleteUser),((MainActivity)getActivity()).errorListener()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					EMContactManager.getInstance().deleteContact(tobeDeleteUser.getMContactCname());
					// 删除db和内存中此用户的数据
					EMUserDao dao = new EMUserDao(getActivity());
					dao.deleteContact(tobeDeleteUser.getMContactCname());
					((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().remove(tobeDeleteUser.getMContactCname());
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							adapter.remove(tobeDeleteUser);
							adapter.notifyDataSetChanged();

						}
					});
				} catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							Toast.makeText(getActivity(), st2 + e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});

				}

			}
		}).start();

	}

	private Response.Listener<Boolean> responsedeleteListner(final Contact tobeDeleteUser) {
		return new Response.Listener<Boolean>() {
			@Override
			public void onResponse(Boolean aBoolean) {
				FuliCenterApplication.getInstance().getContactList().remove(tobeDeleteUser);
				FuliCenterApplication.getInstance().getUserList().remove(tobeDeleteUser.getMContactCname());
				getActivity().sendBroadcast(new Intent("update_contact_list"));
			}
		};

	}

	/**
	 * 把user移入到黑名单
	 */
	private void moveToBlacklist(final String username){
		final ProgressDialog pd = new ProgressDialog(getActivity());
		String st1 = getResources().getString(cn.ucai.fulicenter.R.string.Is_moved_into_blacklist);
		final String st2 = getResources().getString(cn.ucai.fulicenter.R.string.Move_into_blacklist_success);
		final String st3 = getResources().getString(cn.ucai.fulicenter.R.string.Move_into_blacklist_failure);
		pd.setMessage(st1);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new Thread(new Runnable() {
			public void run() {
				try {
					//加入到黑名单
					EMContactManager.getInstance().addUserToBlackList(username,false);
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
							refresh();
						}
					});
				} catch (EaseMobException e) {
					e.printStackTrace();
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							Toast.makeText(getActivity(), st3, Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
		
	}
	
	// 刷新ui
	public void refresh() {
		try {
			// 可能会在子线程中调到这方法
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					getContactList();
					adapter.notifyDataSetChanged();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		if (contactSyncListener != null) {
			HXSDKHelper.getInstance().removeSyncContactListener(contactSyncListener);
			contactSyncListener = null;
		}
		
		if(blackListSyncListener != null){
		    HXSDKHelper.getInstance().removeSyncBlackListListener(blackListSyncListener);
		}
		
		if(contactInfoSyncListener != null){
			((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().removeSyncContactInfoListener(contactInfoSyncListener);
		}
		if(mReceiver!=null){
			getActivity().unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}
	
	public void showProgressBar(boolean show) {
		if (progressBar != null) {
			if (show) {
				progressBar.setVisibility(View.VISIBLE);
			} else {
				progressBar.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 获取联系人列表，并过滤掉黑名单和排序
	 */
	private void getContactList() {
		//contactList.clear();
		mContactList.clear();
		ArrayList<Contact> contactList = FuliCenterApplication.getInstance().getContactList();
		mContactList.addAll(contactList);
		// 添加"群聊"
		Contact groupUser = new Contact();
		String strGroup = getActivity().getString(cn.ucai.fulicenter.R.string.group_chat);
		groupUser.setMContactId(-2);
		groupUser.setMContactCname(Constant.GROUP_USERNAME);
		groupUser.setMUserName(Constant.GROUP_USERNAME);
		groupUser.setMUserNick(strGroup);
		if(mContactList.indexOf(groupUser)==-1){
			contactList.add(0, groupUser);
		}

		// 添加user"申请与通知"
		Contact newFriends = new Contact();
		newFriends.setMContactId(-1);
		newFriends.setMContactCname(Constant.NEW_FRIENDS_USERNAME);
		newFriends.setMUserName(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getActivity().getString(cn.ucai.fulicenter.R.string.Application_and_notify);
		newFriends.setMUserNick(strChat);
		if(!mContactList.contains(newFriends) ){
			contactList.add(0, newFriends);
		}
		for( Contact contact :mContactList){
			UserUtils.setUserHearder(contact.getMContactCname(),contact);
		}

		//排序
		Collections.sort(this.mContactList, new Comparator<Contact>() {

			@Override
			public int compare(Contact lhs, Contact rhs) {
				return lhs.getHeader().compareTo(rhs.getHeader());
			}
		});

	}
	
	void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	    if(((MainActivity)getActivity()).isConflict){
	    	outState.putBoolean("isConflict", true);
	    }else if(((MainActivity)getActivity()).getCurrentAccountRemoved()){
	    	outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
	    }
	    
	}

	class ContactListChangedReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			refresh();
		}
	}

	private void registerContactListChangedReceiver() {
		mReceiver = new ContactListChangedReceiver();
		IntentFilter filter = new IntentFilter("update_contact_list");
		getActivity().registerReceiver(mReceiver,filter);
	}
}
