package cn.ucai.superwechat;

public interface I {

	  interface User {
		String TABLE_NAME							=		"t_superwechat_user";
		String USER_ID 								= 		"m_user_id";					//主键
		String USER_NAME 							= 		"m_user_name";					//用户账号
		String PASSWORD 							= 		"m_user_password";				//用户密码
		String NICK 								= 		"m_user_nick";					//用户昵称
		String UN_READ_MSG_COUNT 					= 		"m_user_unread_msg_count";		//未读消息数量
	}
	
	 interface Contact {
		String TABLE_NAME 							= 		"t_superwechat_contact";
		String CONTACT_ID 							= 		"m_contact_id";					//主键
		String USER_ID 								= 		"m_contact_user_id";			//用户id
		String USER_NAME 							= 		"m_contact_user_name";			//用户账号
		String CU_ID 								= 		"m_contact_cid";				//好友id
		String CU_NAME 								= 		"m_contact_cname";				//好友账号
	}
	
	 interface Group {
		String TABLE_NAME 							= 		"t_superwechat_group";
		String GROUP_ID 							= 		"m_group_id";					// 主键
		String HX_ID 								= 		"m_group_hxid";					//环信群组id
		String NAME 								= 		"m_group_name";					//群组名称
		String DESCRIPTION 							= 		"m_group_description";			//群组简介
		String OWNER 								= 		"m_group_owner";				//群组所有者－用户账号
		String MODIFIED_TIME 						= 		"m_group_last_modified_time";	//最后修改时间
		String MAX_USERS 							= 		"m_group_max_users";			//最大人数
		String AFFILIATIONS_COUNT 					= 		"m_group_affiliations_count";	//群组人数
		String IS_PUBLIC 							= 		"m_group_is_public";			//群组是否公开
		String ALLOW_INVITES 						= 		"m_group_allow_invites";		//是否可以邀请
	}
	
	 interface Member {
		String TABLE_NAME 							= 		"t_superwechat_member";
		String MEMBER_ID 							= 		"m_member_id";					//主键
		String USER_ID 								= 		"m_member_user_id";				//用户id
		String USER_NAME 							= 		"m_member_user_name";			//用户账号
		String GROUP_ID 							= 		"m_member_group_id";			//群组id
		String GROUP_HX_ID 							= 		"m_member_group_hxid";			//群组环信id
		String PERMISSION 							= 		"m_member_permission";			//用户对群组的权限\n0:普通用户\n1:群组所有者
	}
	
	 interface Avatar {
		String TABLE_NAME 							= 		"t_superwechat_avatar";
		String AVATAR_ID 							= 		"m_avatar_id";					//主键
		String USER_ID 								= 		"m_avatar_user_id";				//用户id或者群组id
		String USER_NAME 							= 		"m_avatar_user_name";			//用户账号或者群组账号
		String AVATAR_PATH 							= 		"m_avatar_path";				//保存路径
		String AVATAR_TYPE 							= 		"m_avatar_type";				//头像类型：\n0:用户头像\n1:群组头像
	}
	
	 interface Location {
		String TABLE_NAME 							= 		"t_superwechat_location";
		String LOCATION_ID 							= 		"m_location_id";				//主键
		String USER_ID 								= 		"m_location_user_id";			//用户id
		String USER_NAME 							= 		"m_location_user_name";			//用户账号
		String LATITUDE 							= 		"m_location_latitude";			//纬度
		String LONGITUDE 							= 		"m_location_longitude";			//经度
		String IS_SEARCHED 							= 		"m_location_is_searched";		//是否可以被搜索到
		String UPDATE_TIME 							= 		"m_location_last_update_time";	//最后更新时间
	}

	String AVATAR_PATH 								= 		"//Users/clawpo/work/ucai/work/projects/SuperWeChat/teaching/201603/superwechatDB/";
	String ISON8859_1 								= 		"iso8859-1";
	String UTF_8 									= 		"utf-8";
	String PAGE_ID 									= 		"page_id";						//分页的起始下标
	String PAGE_SIZE 								= 		"page_size";					//分页的每页数量
	int ID_DEFAULT									=		0;								//ID默认值
	int UN_READ_MSG_COUNT_DEFAULT					=		0;								//未读消息数量默认值
	int GROUP_MAX_USERS_DEFAULT 					= 		-1;								//群组最大人数默认值
	int GROUP_AFFILIATIONS_COUNT_DEFAULT 			= 		1;								//群组最大人数默认值
	int PERMISSION_NORMAL							= 		0;								//普通用户群组权限
	int PERMISSION_OWNER							= 		1;								//群组所有者群组权限
	int AVATAR_TYPE_USER							=		0;								//用户头像
	int AVATAR_TYPE_GROUP							=		1;								//群组头像
	int GROUP_PUBLIC								=		1;								//公开群组
	int GROUP_NO_PUBLIC								=		0;								//非公开群组
	String BACKSLASH								= 		"/";							//反斜杠
	String AVATAR_TYPE_USER_PATH					= 		"user_avatar";					//用户头像保存目录
	String AVATAR_TYPE_GROUP_PATH 					=		"group_icon";					//群组头像保存目录
	String AVATAR_SUFFIX_PNG						=		".png";							//PNG图片后缀名
	String AVATAR_SUFFIX_JPG						=		".jpg";							//JPG图片后缀名
	int LOCATION_IS_SEARCH_ALLOW					=		1;								//可以被搜索到地理位置
	int LOCATION_IS_SEARCH_INHIBIT					=		0;								//禁止被搜索到地理位置
	int MSG_CONNECTION_SUCCESS						=  		900;							//连接服务器成功
	int MSG_CONNECTION_FAIL							=  		901;							//连接服务器失败
	int MSG_UPLOAD_AVATAR_SUCCESS					=		902;							//上传头像成功
	int MSG_UPLOAD_AVATAR_FAIL						=		903;							//上传头像失败
	int MSG_REGISTER_SUCCESS						=  		101;							//注册成功
	int MSG_REGISTER_USERNAME_EXISTS				=		102;							//账号已经存在
	int MSG_REGISTER_UPLOAD_AVATAR_FAIL				=		103;							//上传头像失败
	int MSG_REGISTER_UPLOAD_AVATAR_SUCCESS			=		104;							//上传头像成功
	int MSG_REGISTER_FAIL							=		105;							//注册失败
	int MSG_UNREGISTER_SUCCESS						=  		106;							//注册成功
	int MSG_UNREGISTER_FAIL							=		107;							//注册失败
	int MSG_CONTACT_FIRENDED						=		201;							//已经是好友关系
	int MSG_CONTACT_FAIL							=		202;							//好友关系
	int MSG_GROUP_CREATE_SCUUESS					=		301;							//创建群组成功
	int MSG_GROUP_HXID_EXISTS						=		302;							//群组环信ID已经存在
	int MSG_GROUP_CREATE_FAIL						=		303;							//创建群组成功
	int MSG_GROUP_ADD_MEMBER_FAIL					=		304;							//添加群组成员失败
	int MSG_GROUP_ADD_MEMBER_SCUUESS				=		305;							//添加群组成员成功
	int MSG_GROUP_UNKONW							=		306;							//群组不存在
	int MSG_GROUP_SAME_NAME							=		307;							//群组名称未修改
	int MSG_GROUP_UPDATE_NAME_SUCCESS				=		308;							//群组名称修改成功
	int MSG_GROUP_UPDATE_NAME_FAIL					=		309;							//群组名称修改失败
	int MSG_GROUP_DELETE_MEMBER_SUCCESS				=		310;							//删除群组成员成功
	int MSG_GROUP_DELETE_MEMBER_FAIL				=		311;							//删除群组成员失败
	int MSG_GROUP_DELETE_SUCCESS					=		312;							//删除群组成功
	int MSG_GROUP_DELETE_FAIL						=		313;							//删除群组失败
	int MSG_LOGIN_UNKNOW_USER						=		401;							//账户不存在
	int MSG_LOGIN_ERROR_PASSWORD					=		402;							//账户密码错误
	int MSG_LOGIN_SUCCESS							=		403;							//登陆成功
	int MSG_USER_SAME_NICK							=		404;							//昵称未修改
	int MSG_USER_UPDATE_NICK_SUCCESS				=		405;							//昵称修改成功
	int MSG_USER_UPDATE_NICK_FAIL					=		406;							//昵称修改失败
	int MSG_USER_SAME_PASSWORD						=		407;							//昵称未修改
	int MSG_USER_UPDATE_PASSWORD_SUCCESS			=		408;							//昵称修改成功
	int MSG_USER_UPDATE_PASSWORD_FAIL				=		409;							//昵称修改失败
	int MSG_LOCATION_UPLOAD_SUCCESS					=		501;							//用户上传地理位置成功
	int MSG_LOCATION_UPLOAD_FAIL					=		502;							//用户上传地理位置失败
	int MSG_LOCATION_UPDATE_SUCCESS					=		503;							//用户更新地理位置成功
	int MSG_LOCATION_UPDATE_FAIL					=		504;							//用户更新地理位置失败
	int MSG_UNKNOW									=		999;							//未知错误
	String MSG_PREFIX_MSG 							=		"msg_";
	String KEY_REQUEST 								= 		"request";
	/** 上传图片的类型：user_avatar或group_icon */
	String AVATAR_TYPE 								= 		"avatarType";
	/** 服务器状态的请求 */
	String REQUEST_SERVERSTATUS 					= 		"server_status";
	/** 客户端发送的注册请求 */
	String REQUEST_REGISTER		 					= 		"register";
	/**  发送取消注册的请求 */
	String REQUEST_UNREGISTER 						= 		"unregister";
	/** 客户端上传头像的请求 */
	String REQUEST_UPLOAD_AVATAR 					= 		"upload_avatar";
	/** 客户端更新用户昵称的请求 */
	String REQUEST_UPDATE_USER_NICK 				= 		"update_nick";
	/** 客户端修改密码的请求 */
	String REQUEST_UPDATE_USER_PASSWORD 			= 		"update_password";
	/** 客户端上传头像的请求 */
	String REQUEST_UPLOAD_AVATAR_ID  		 		= 		"upload_avatar_id";
	/** 客户端发送的登陆请求 */
	String REQUEST_LOGIN 							= 		"login";
	/** 客户端发送的下载用户头像请求 */
	String REQUEST_DOWNLOAD_AVATAR	 				= 		"download_avatar";
	/** 客户端发送的下载群组头像请求 */
	String REQUEST_DOWNLOAD_GROUP_AVATAR 			= 		"download_group_avatar";
	/** 客户端发送的下载联系人请求 */
	String REQUEST_DOWNLOAD_CONTACTS			 	= 		"download_contacts";
	/** 客户端发送的下载联系人所有集合请求 */
	String REQUEST_DOWNLOAD_CONTACT_ALL_LIST 		= 		"download_contact_all_list";
	/** 客户端发送的下载联系人集合请求 */
	String REQUEST_DOWNLOAD_CONTACT_LIST 			= 		"download_contact_list";
	/** 客户端发送的删除联系人请求 */
	String REQUEST_DELETE_CONTACT 					= 		"delete_contact";
	/** 客户端发送的添加联系人请求 */
	String REQUEST_ADD_CONTACT 						= 		"add_contact";
	/** 客户端发送的查找用户请求 */
	String REQUEST_FIND_USER 						= 		"find_user";
	/** 客户端发送的根据用户名模糊查找用户请求 */
	String REQUEST_FIND_USERS 						= 		"find_users";
	/** 客户端发送的根据用户昵称模糊查找用户请求 */
	String REQUEST_FIND_USERS_BY_NICK 				= 		"find_users_by_nick";
	/** 客户端发送的根据用户昵称模糊查找用户请求 */
	String REQUEST_FIND_USERS_FOR_SEARCH			= 		"find_users_for_search";
	/** 客户端发送的下载联系人请求 */
	String REQUEST_DOWNLOAD_CONTACT 				= 		"download_contacts";
	/** 客户端发送的上传位置请求 */
	String REQUEST_UPLOAD_LOCATION 					= 		"upload_location";
	/** 客户端发送的更新位置请求 */
	String REQUEST_UPDATE_LOCATION 					= 		"update_location";
	/** 客户端发送的下载位置请求 */
	String REQUEST_DOWNLOAD_LOCATION 				= 		"download_location";
	/** 客户端发送的创建群组请求 */
	String REQUEST_CREATE_GROUP			 			= 		"create_group";
	/** 客户端发送的添加群成员请求 */
	String REQUEST_ADD_GROUP_MEMBER 				= 		"add_group_member";
	/** 客户端发送的添加多个群成员请求 */
	String REQUEST_ADD_GROUP_MEMBERS		 		= 		"add_group_members";
	/** 客户端发送的更新群名称请求 */
	String REQUEST_UPDATE_GROUP_NAME 				= 		"update_group_name";
	/** 客户端发送的下载多个群成员请求 */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS 			= 		"download_group_members";
	/** 客户端发送的下载多个群成员请求 */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_LIMIT 	= 		"download_group_members_by_limit";
	/** 客户端发送的下载多个群成员请求 */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID 	= 		"download_group_members_by_hxid";
	/** 客户端发送的下载多个群成员请求 */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID_LIMIT 	= 		"download_group_members_by_hxid_limit";
	/** 客户端发送的删除群成员请求 */
	String REQUEST_DELETE_GROUP_MEMBER 				= 		"delete_group_member";
	/** 客户端发送的删除多个群成员请求 */
	String REQUEST_DELETE_GROUP_MEMBERS 			= 		"delete_group_members";
	/** 客户端发送的删除群组请求 */
	String REQUEST_DELETE_GROUP 					= 		"delete_group";
	/** 客户端发送的下载群组请求 */
	String REQUEST_DOWNLOAD_GROUPS 					= 		"download_groups";
	/** 客户端发送的下载公开裙请求 */
	String REQUEST_FIND_PUBLIC_GROUPS 				= 		"download_public_groups";
	/** 客户端发送的根据群组名称模糊查找群组请求 */
	String REQUEST_FIND_GROUP 						= 		"find_group_by_group_name";
	/** 客户端发送的根据群组账号查找群组请求 */
	String REQUEST_FIND_GROUP_BY_ID					= 		"find_group_by_group_id";
	/** 客户端发送的根据群组环信id查找群组请求 */
	String REQUEST_FIND_GROUP_BY_HXID 				= 		"find_group_by_group_hxid";
	String REQUEST_DOWNLOAD_AVATAR_USER = SuperWeChatApplication.SERVER_ROOT + "?"
			+ KEY_REQUEST + "=" + REQUEST_DOWNLOAD_AVATAR + "&" + AVATAR_TYPE + "=";
	String REQUEST_DOWNLOAD_AVATAR_GROUP = SuperWeChatApplication.SERVER_ROOT + "?"
			+ KEY_REQUEST + "=" + REQUEST_DOWNLOAD_GROUP_AVATAR + "&" + AVATAR_TYPE + "=";
}
