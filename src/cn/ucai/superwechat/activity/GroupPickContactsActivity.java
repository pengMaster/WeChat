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
package cn.ucai.superwechat.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import cn.ucai.superwechat.SuperWeChatApplication;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.adapter.ContactAdapter;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.utils.Utils;
import cn.ucai.superwechat.widget.Sidebar;

public class GroupPickContactsActivity extends BaseActivity {
	private ListView listView;
	/** 是否为一个新建的群组 */
	protected boolean isCreatingNewGroup;
	/** 是否为单选 */
	private boolean isSignleChecked;
	private PickContactAdapter contactAdapter;
	/** group中一开始就有的成员 */
	private List<String> exitingMembers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(cn.ucai.superwechat.R.layout.activity_group_pick_contacts);

		// String groupName = getIntent().getStringExtra("groupName");
		String groupId = getIntent().getStringExtra("groupId");
		if (groupId == null) {// 创建群组
			isCreatingNewGroup = true;
		} else {
			// 获取此群组的成员列表
			EMGroup group = EMGroupManager.getInstance().getGroup(groupId);
			exitingMembers = group.getMembers();
		}
		if(exitingMembers == null)
			exitingMembers = new ArrayList<String>();
		// 获取好友列表
		final ArrayList<Contact> alluserList = new ArrayList<Contact>();
		for (Contact user : SuperWeChatApplication.getInstance().getUserList().values()) {
			if (!user.getMContactCname().equals(Constant.NEW_FRIENDS_USERNAME) & !user.getMContactCname().equals(Constant.GROUP_USERNAME) & !user.getMContactCname().equals(Constant.CHAT_ROOM) & !user.getMContactCname().equals(Constant.CHAT_ROBOT))
				alluserList.add(user);
		}
		// 对list进行排序
		Collections.sort(alluserList, new Comparator<Contact>() {
			@Override
			public int compare(Contact lhs, Contact rhs) {
				return (lhs.getHeader().compareTo(rhs.getHeader()));

			}
		});

		listView = (ListView) findViewById(cn.ucai.superwechat.R.id.list);
		contactAdapter = new PickContactAdapter(this, cn.ucai.superwechat.R.layout.row_contact_with_checkbox, alluserList);
		listView.setAdapter(contactAdapter);
		((Sidebar) findViewById(cn.ucai.superwechat.R.id.sidebar)).setListView(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox checkBox = (CheckBox) view.findViewById(cn.ucai.superwechat.R.id.checkbox);
				checkBox.toggle();

			}
		});
	}

	/**
	 * 确认选择的members
	 * 
	 * @param v
	 */
	public void save(View v) {
		setResult(RESULT_OK, new Intent().putExtra("newmembers", getToBeAddMembers()));
		finish();
	}

	/**
	 * 获取要被添加的成员
	 * 
	 * @return
	 */
	private Contact[] getToBeAddMembers() {
		Contact[] contacts = new Contact[0];
		int length = contactAdapter.isCheckedArray.length;
		for (int i = 0; i < length; i++) {
			Contact contact = contactAdapter.getItem(i);
			if (contactAdapter.isCheckedArray[i] && !exitingMembers.contains(contact)) {
				contacts = Utils.add(contacts, contact);
			}
		}
		if(contacts.length>0){
			return contacts;
		}

		return null;
	}

	/**
	 * adapter
	 */
	private class PickContactAdapter extends ContactAdapter {

		private boolean[] isCheckedArray;

		public PickContactAdapter(Context context, int resource, ArrayList<Contact> users) {
			super(context, resource, users);
			isCheckedArray = new boolean[users.size()];
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
//			if (position > 0) {
				final String username = getItem(position).getMContactCname();
				// 选择框checkbox
				final CheckBox checkBox = (CheckBox) view.findViewById(cn.ucai.superwechat.R.id.checkbox);
				if(exitingMembers != null && exitingMembers.contains(username)){
					checkBox.setButtonDrawable(cn.ucai.superwechat.R.drawable.checkbox_bg_gray_selector);
				}else{
					checkBox.setButtonDrawable(cn.ucai.superwechat.R.drawable.checkbox_bg_selector);
				}
				if (checkBox != null) {
					// checkBox.setOnCheckedChangeListener(null);

					checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							// 群组中原来的成员一直设为选中状态
							if (exitingMembers.contains(username)) {
									isChecked = true;
									checkBox.setChecked(true);
							}
							isCheckedArray[position] = isChecked;
							//如果是单选模式
							if (isSignleChecked && isChecked) {
								for (int i = 0; i < isCheckedArray.length; i++) {
									if (i != position) {
										isCheckedArray[i] = false;
									}
								}
								contactAdapter.notifyDataSetChanged();
							}
							
						}
					});
					// 群组中原来的成员一直设为选中状态
					if (exitingMembers.contains(username)) {
							checkBox.setChecked(true);
							isCheckedArray[position] = true;
					} else {
						checkBox.setChecked(isCheckedArray[position]);
					}
				}
//			}
			return view;
		}
	}

	public void back(View view){
		finish();
	}
	
}
