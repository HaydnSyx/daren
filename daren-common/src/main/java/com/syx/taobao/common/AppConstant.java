package com.syx.taobao.common;

import java.util.Set;

import com.google.common.collect.Sets;

public class AppConstant {

	// 切换测试环境或者生产环境。发布到线上时记得修改这里 ！！！
	public static final boolean IS_PRODUCTION_ENVIRONMENT = false;

	/**
	 * 用户session
	 */
	public static final String SESSION_USERID = "userid";
	/**
	 * 管理用户session
	 */
	public static final String SESSION_MUSER = "muser";
	public static final String SESSION_MUSER_ID = "mid";
	public static final String SESSION_MUSER_NAME = "mname";

	// 网站域名
	public static String PROP_WEBSITE = "website";
	// 文件存储根路径
	public static String FILE_PATH = "file_path";
	// 图片存储根路径
	public static String FILE_IMG_PATH = "file_img_path";
	// 文本存储根路径
	public static String FILE_TEXT_PATH = "file_text_path";
	// 单品存储根路径
	public static String FILE_SINGLEPRODUCT_PATH = "file_singleproduct_path";
	// 清单存储根路径
	public static String FILE_DETAILLIST_PATH = "file_detaillist_path";
	// 帖子存储根路径
	public static String FILE_CARD_PATH = "file_card_path";
	// 通用存储根路径
	public static String FILE_COMMON_PATH = "file_common_path";
	// ueditor服务器域名
	public static String APACHE_SERVER_WEBSITE = "apache_server_website";
	// ueditor服务器真实路径
	public static String APACHE_SERVER_REAL_PATH = "apache_server_real_path";
	// ftp服务器域名
	public static String FTP_SERVER_WEBSITE = "ftp_server_website";
	// ftp服务器真实路径
	public static String FTP_SERVER_REAL_PATH = "ftp_server_real_path";
	// ftp服务器地址
	public static String FTP_SERVER_ADDR = "ftp_server_addr";
	// ftp服务器端口号
	public static String FTP_SERVER_PORT = "ftp_server_port";
	// ftp服务器用户名
	public static String FTP_SERVER_USERNAME = "ftp_server_username";
	// ftp服务器密码
	public static String FTP_SERVER_PASSWORD = "ftp_server_password";
	// 人物头像默认地址
	public static String USER_HEADER_IMG_DEFAULTURL = "http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg";

	static {
		if (!IS_PRODUCTION_ENVIRONMENT) {
			PROP_WEBSITE = "website_test";
			APACHE_SERVER_WEBSITE = "apache_server_website_test";
			FTP_SERVER_WEBSITE = "ftp_server_website_test";
		}
	}

	/**
	 * 返回状态-失败
	 */
	public static final int RESULT_STATUS_ERROR = 0;
	/**
	 * 返回状态-成功
	 */
	public static final int RESULT_STATUS_SUCCESS = 1;
	/**
	 * 返回状态-未验证
	 */
	public static final int RESULT_STATUS_UNVALID = 2;
	/**
	 * 返回状态-未登录
	 */
	public static final int RESULT_STATUS_NOUSER = 3;

	/**
	 * 状态-可用
	 */
	public static final String STATUS_AVAILABLE = "T";
	/**
	 * 状态-申请
	 */
	public static final String STATUS_APPLY = "P";
	/**
	 * 状态-不可用
	 */
	public static final String STATUS_UNAVAILABLE = "F";
	/**
	 * 状态-已还款
	 */
	public static final String STATUS_REPAYMENT = "R";
	/**
	 * 状态-预约
	 */
	public static final String STATUS_BOOKING = "B";
	/**
	 * 状态-自动
	 */
	public static final String STATUS_AUTO = "A";

	/**
	 * 角色-管理员
	 */
	public static final int ROLE_ADMIN = 1;
	/**
	 * 角色-达人
	 */
	public static final int ROLE_DAREN = 2;
	/**
	 * 角色-兼职
	 */
	public static final int ROLE_PARTJOB = 3;

	/**
	 * 工作-完成状态-草稿
	 */
	public static final int TASK_STATUS_DRAFT = 1;
	/**
	 * 工作-完成状态-已提交
	 */
	public static final int TASK_STATUS_SUMBIT = 2;
	/**
	 * 工作-审核状态-审核驳回
	 */
	public static final int TASK_STATUS_REJECT = 3;
	/**
	 * 工作-审核状态-审核通过
	 */
	public static final int TASK_STATUS_PASS = 4;

	/**
	 * 工作-结算状态-未结算
	 */
	public static final int TASK_SETTLE_STATUS_NONE = 1;
	/**
	 * 工作-结算状态-已结算
	 */
	public static final int TASK_SETTLE_STATUS_HAS = 2;

	/**
	 * 工作-结算状态-未录入
	 */
	public static final int TASK_ENTER_STATUS_NONE = 1;
	/**
	 * 工作-结算状态-录入中
	 */
	public static final int TASK_ENTER_STATUS_ING = 2;
	/**
	 * 工作-结算状态-已录入
	 */
	public static final int TASK_ENTER_STATUS_HAS = 3;

	/**
	 * 网站-淘宝
	 */
	public static final int WEB_TAOBAO = 1;
	/**
	 * 网站-天猫
	 */
	public static final int WEB_TIANMAO = 2;

	/**
	 * 产品-单品
	 */
	public static final int PRODUCT_SINGLEPRODUCT = 1;
	/**
	 * 产品-清单
	 */
	public static final int PRODUCT_DETAILLIST = 2;
	/**
	 * 产品-帖子
	 */
	public static final int PRODUCT_CARD = 3;

	/**
	 * 审核-提交
	 */
	public static final int AUDIT_SUBMIT = 1;
	/**
	 * 审核-驳回
	 */
	public static final int AUDIT_REJECT = 2;
	/**
	 * 审核-通过
	 */
	public static final int AUDIT_PASS = 3;
	/**
	 * 审核-已结算
	 */
	public static final int AUDIT_SETTED = 4;
	/**
	 * 审核-已录入
	 */
	public static final int AUDIT_ENTERD = 5;

	public static final Set<String> IMAGE_FILE_EXT = Sets.newHashSet("jpg", "jpeg", "png", "gif");

	/**
	 * IM好友状态-正常
	 */
	public static final int IM_FRIEND_STATUS_OK = 1;
	/**
	 * IM好友状态-黑名单
	 */
	public static final int IM_FRIEND_STATUS_BLACKLIST = 2;

	/**
	 * 有\没有 <br>
	 * 0:没有 NONE<br>
	 * 1:有 HAS<br>
	 */
	public static enum HasOrNone {
		HAS("1"), NONE("0");

		private final String value;

		HasOrNone(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}
	}
}
