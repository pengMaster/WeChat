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
package cn.ucai.superwechat.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.easemob.chat.EMGroup;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.activity.MainActivity;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.data.RequestManager;
import cn.ucai.superwechat.db.UserDao;
import cn.ucai.superwechat.utils.UserUtils;

public class GroupAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String newGroup;
	private String addPublicGroup;

	ArrayList<Group> mGroupList;
	Context mContext;

	public GroupAdapter(Context context, int res, ArrayList<Group> groups) {
		mGroupList = groups;
		this.mContext = context;
		this.inflater = LayoutInflater.from(context);
		newGroup = context.getResources().getString(cn.ucai.superwechat.R.string.The_new_group_chat);
		addPublicGroup = context.getResources().getString(cn.ucai.superwechat.R.string.add_public_group_chat);
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		} else if (position == 2) {
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			if (convertView == null) {
				convertView = inflater.inflate(cn.ucai.superwechat.R.layout.search_bar_with_padding, null);
			}
			final EditText query = (EditText) convertView.findViewById(cn.ucai.superwechat.R.id.query);
			final ImageButton clearSearch = (ImageButton) convertView.findViewById(cn.ucai.superwechat.R.id.search_clear);
			query.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					//getFilter().filter(s);
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
				}
			});
		} else if (getItemViewType(position) == 1) {
			if (convertView == null) {
				convertView = inflater.inflate(cn.ucai.superwechat.R.layout.row_add_group, null);
			}
			((ImageView) convertView.findViewById(cn.ucai.superwechat.R.id.avatar)).setImageResource(cn.ucai.superwechat.R.drawable.create_group);
			((TextView) convertView.findViewById(cn.ucai.superwechat.R.id.name)).setText(newGroup);
		} else if (getItemViewType(position) == 2) {
			if (convertView == null) {
				convertView = inflater.inflate(cn.ucai.superwechat.R.layout.row_add_group, null);
			}
			((ImageView) convertView.findViewById(cn.ucai.superwechat.R.id.avatar)).setImageResource(cn.ucai.superwechat.R.drawable.add_public_group);
			((TextView) convertView.findViewById(cn.ucai.superwechat.R.id.name)).setText(addPublicGroup);
			((TextView) convertView.findViewById(cn.ucai.superwechat.R.id.header)).setVisibility(View.VISIBLE);

		} else {
			if (convertView == null) {
				convertView = inflater.inflate(cn.ucai.superwechat.R.layout.row_group, null);
			}
			Group group = getItem(position);
			((TextView) convertView.findViewById(cn.ucai.superwechat.R.id.name)).setText(group.getMGroupName());
			UserUtils.setGroupBeanAvatar(group.getMGroupHxid(), (NetworkImageView) convertView.findViewById(R.id.avatar));

		}

		return convertView;
	}

	@Override
	public int getCount() {
		return mGroupList==null?3:mGroupList.size() + 3;
	}

	@Override
	public Group getItem(int position) {
		if (position >=3) {
			return mGroupList.get(position - 3);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void initList(ArrayList<Group> list) {
		mGroupList.addAll(list);
		notifyDataSetChanged();
	}


}