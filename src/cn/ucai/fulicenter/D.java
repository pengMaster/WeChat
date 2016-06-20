package cn.ucai.fulicenter;

public final class D {

	public static final String UTF_8 = "utf-8";
	public static final String TEXT_HTML = "text/html";

	public static class Good {
		public static final String KEY_RESULT = "result";
		public static final String RESPONSE_SUCCESS = "success";
		public static final String KEY_INFO = "info";
		public static final String KEY_GOODS = "goods";
		public static final String KEY_CURRENCY_PRICE = "currency_price";
		public static final String KEY_GOODS_ID = "goods_id";
		public static final String KEY_GOODS_NAME = "goods_name";
		public static final String KEY_ENGLISH_NAME = "english_name";
		
		public static final String HINT_DOWNLOAD_TITLE = "加载商品信息";
		public static final String HINT_DOWNLOADING = "加载中...";
		public static final String HINT_DOWNLOAD_FAILURE = "加载数据失败";
	}

	public static final class NewGood extends Good {
		public static final String KEY_THUMB_URL = "thumb";
		public static final String HINT_DOWNLOAD_TITLE = "加载新品列表";
		public static final String HINT_DOWNLOADING = "加载中...";
		public static final String HINT_DOWNLOAD_FAILURE = "加载数据失败";
	}

	public static final class GoodDetails extends Good{
		public static final String HINT_DOWNLOAD_TITLE = "加载商品详细信息";
		public static final String KEY_CAT_ID = "cat_id";
		public static final String KEY_ENGLISH_NAME = "goods_english_name";
		public static final String KEY_GOODS_BRIEF = "goods_brief";
		public static final String KEY_GOODS_DESC = "goods_desc";
		public static final String KEY_GOODS_IMG = "goods_img";
		public static final String KEY_GOODS_THUMB = "goods_thumb";
		public static final String KEY_SHOP_PRICE = "shop_price";
		public static final String KEY_PROPERTIES = "properties";
		public static final String KEY_ALBUMS = "albums";
	}
	
	public static final class Property {
		public static final String KEY_COLOR_ID = "colorid";
		public static final String KEY_COLOR_CODE = "colorcode";
		public static final String KEY_COLOR_IMG = "colorimg";
		public static final String KEY_COLOR_URL = "colorurl";
	}
	
	public static final class Album{
		public static final String KEY_IMG_ID = "img_id";
		public static final String KEY_IMG_URL = "img_url";
		public static final String KEY_THUMB_URL = "thumb_url";
	}
	
	public static final class Boutique extends Good{
		public static final int IMG_WIDTH = 480;
		public static final int IMG_HEIGHT = 246;
		public static final String KEY_CHILD_INFO = "child_info";
		public static final String KEY_ID = "id";
		public static final String KEY_TITLE = "title";
		public static final String KEY_NAME = "name";
		public static final String KEY_DESCRIPTION = "description";
		public static final String KEY_IMAGE_URL = "imageurl";
		public static final String KEY_URL = "url";
		public static final String PARAM_PAGE = "&page=";
		public static final String PARAM_C_ID = "&c_id=";
		public static final String HINT_DOWNLOAD_TITLE = "加载精选商品信息";
	}

	public static class CategoryGroup{
	    public static final String IMAGE_URL="imageurl";
	    public static final String NAME="name";
	}
	
	public static class CategoryChild extends CategoryGroup{
	    public static final String PARENT_ID="parentId";
	}
	
	public static class Category extends Good{
		public static final String HINT_DOWNLOAD_TITLE = "加载分类列表";
		public static final String KEY_ID = "id";
		public static final String KEY_NAME = "name";
		public static final String KEY_COLOR_ID = "colorid";
		public static final String KEY_COLOR_NAME = "colorname";
		
		public static final String KEY_CAT_ID = "cat_id";
		public static final String KEY_CATEGORY_INFO = "category_info";
		public static final String PARAM_PAGE = "&page=";
		public static final String PARAM_C_ID = "&c_id=";		
		public static final String PARAM_CAT_ID = "&cat_id=";
		public static final String PARAM_ORDER_PRICE = "&order_price=";
		public static final String PARAM_COLOR_ID = "&getcolorid=";
		
		public static final int SORT_DEFAULT = 0;// 排序默认值
		public static final int SORT_PRICE_ASC = 1;// 价格升序排序
		public static final int SORT_PRICE_DESC = 2;// 价格降序排序
		public static final int SORT_DATE_ASC = 3;// 日期升序排序
		public static final int SORT_DATE_DESC = 4;// 日期降序排序
		
		public static final int COLOR_DEFAULT = -1;// 排序默认值
	}
	
	public static class PersonalCenter extends Good{
		public static final String HINT_INTVEND_CODE_NOT_EMPTY="邀请码不能为空";
		public static final String HINT_MOBILE_NUMBER_INVALIDE="手机号格式不对";
		public static final String HINT_PASSWORD_NOT_EMPTY="密码不能为空";
		public static final String HINT_PASSWORD_CONFIRM_NOT_EMPTY="确认密码不能为空";
		public static final String HINT_INVENT_CODE_NOT_EMPTY="邀请码不能为空";
		public static final String HINT_SENDING="发送中...";
		public static final String HINT_FEEDBACK_EMPTY="反馈意见不能为空";
		public static final String HINT_LOGOUT_FAILUE="退出登陆失败";
		public static final String HINT_LOGOUT_SUCCESS="退出登陆成功";
		public static final String HINT_TOTAL_INVENTCODE="共有";
		public static final String HINT_TOTAL_INVENTCODE_UNIT="个邀请码";
		public static final String HINT_VALIDECODE_EMPTY="验证码不能为空";
		
		public static final String REGEX="1[358][\\d]{9}";
		public static final String PARAM_PASSWORD="&password=";
		public static final String PARAM_INVENT_CODE="&user_rand=";
		public static final String PARAM_PASSWORD_CONFIRM="&next_password=";
		public static final String PARAM_FEEDBACK_MSG="&msg=";
		public static final String PARAM_DEBUG_TRUE="&debug=true";
		public static final String PARAM_PWD="&pwd=";
		public static final String PARAM_USERNAME="&username=";
		public static final String PARAM_USER_NAME="&user_name=";
		public static final String PARAM_USER_ID="&user_id=";
		public static final String PARAM_MOBILE="&mobile=";
		public static final String PARAM_CODE="&code=";
		public static final String PARAM_SESSION_ID="&session_id=";
		
		
		public static final String HINT_REGISTER_TITLE="注册";
		public static final String HINT_REGISTER_MESSAGE="注册中...";
		public static final String HINT_REGISTER_FALSE="账户已被注册或邀请码已使用，请重新输入";
		public static final String HINT_REGISTER_SUCCESS="恭喜您，注册成功";
		public static final String HINT_LOGIN_TITLE="登陆";
		public static final String HINT_LOGIN_MESSAGE="登陆中...";
		public static final String HINT_LOGIN_SUCCESS="登陆成功";
		public static final String HINT_LOGIN_FALIURE="登陆失败";
		public static final String HINT_HELLO="你好,";
		
		/**登陆的手机号*/
		public static final String KEY_USER_NAME="user_name";
		public static final String KEY_USER_ID="user_id";
		public static final String KEY_INVENT_CODES="invent_codes";
		public static final String KEY_INVENT_CODE="invent_code";
		public static final String KEY_SESSION_ID="session_id";
		public static final String KEY_REGISTER_URL="url";
		
		/**
		 * 保存登陆信息
		 */
		public static final String PREFERENCE_NAME="login_info";
	}
	
	public static final class Wardrobe extends Good{
		public static final String HINT_PLEASE_LOGIN="请先去个人中心登录";
		
		/**操作衣橱的动作类型*/
		/**商品id,添加衣橱时，使用以下参数*/
		public static final String PARAM_ID="&id=";
		/** 取消衣橱时，使用以下的参数*/
		public static final String PARAM_GOODS_ID="&goodsId=";
		public static final String PARAM_USER_ID="&user_id=";
		public static final String PARAM_IDS="&ids=";
		
		public static final String ACTION_EDIT_COLLECTION="edit_collection";
		
		public static final String TEXT_ADD_WARDROBE="加入衣橱";
		public static final String TEXT_DELETE_WARDROBE="取消衣橱";
		
		public static final String HINT_ADD_WARDROBE_FAILURE="加入衣橱失败";
		public static final String HINT_ADD_WARDROBE_SUCCESS="加入衣橱成功";
		public static final String HINT_ACTION_WARDROBE_FAILURE="加入衣橱失败";
		public static final String HINT_WARDROBE_EXISTS="Collected in your favorite";
		public static final String HINT_DELETE_WARDROBE_SUCCESS="取消衣橱成功";
		public static final String HINT_DELETE_WARDROBE_FAILURE="取消衣橱失败";
		
		public static final String KEY_ADD_TIME="add_time";
		public static final String KEY_GOODS_IMG="goods_img";
		public static final String KEY_THUMB="thumb";
		public static final String KEY_REC_ID="rec_id";
		
	}
}
