package com.syx.taobao.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.regex.Matcher;  
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class StringUtil {

	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * 判断是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s){
		return Strings.isNullOrEmpty(s);
	}
	/**
	 * 判断是否为非空
	 * @param s
	 * @return
	 */
	public static boolean isNotNull(String s){
		if("null".equals(s)) {
			return false;
		}
		return !Strings.isNullOrEmpty(s);
	}
	
	/**
	 * 生成随机验证码
	 * @return
	 */
	public static String getRandomCode(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int c = random.nextInt(10);
            sb.append(c);
        }
        return sb.toString();
	}
	public static String getRequestNo(Integer userId) {
		return getRequestNo(userId, null);
	}
	
	public static String getRequestNo(Integer userId,Integer bidId) {
		return getRequestNo(userId, bidId, "");
	}
	
	public static String getRequestNo(Integer userId,Integer bidId,String Pre) {
		String no = Pre;
		if(bidId != null && bidId > 0) {
			no = no + (bidId + "B");
		}
		if(userId != null && userId > 0) {
			no = no + (userId + "U");
		}
		no = no + System.currentTimeMillis() + getRandomCode(4);
		return no;
	}
	
	public static String getCpsRequestNo(Integer userId, Integer inviterId, Integer rId) {
		String no = "DT";
		if(rId != null && rId > 0) {
			no = no + (rId + "R");
		}
		if(inviterId != null && inviterId > 0) {
			no = no + (inviterId + "I");
		}
		if(userId != null && userId > 0) {
			no = no + (userId + "U");
		}
		no = no + System.currentTimeMillis();
		return no;
	}  
	
	
	public static String getPlatformUserNo(Integer userId) {
		if(userId != null) {
			return USERNO_PRE + userId;
		}
		return "";
	}
	public static String getPlatformUser(String platformUserNo) {
		if(isNotNull(platformUserNo) && platformUserNo.length() > 6) {
			return platformUserNo.substring(USERNO_PRE.length());
		}
		return "";
	}
	
	public static final String USERNO_PRE = "xunlei";
	public static final String EUSER_PRE = "EU";

	public static String getEUserNo(Integer euId) {
		if(euId != null) {
			return EUSER_PRE + euId;
		}
		return "";
	}
	
	public static String getEuId(String platformUserNo) {
		if(isNotNull(platformUserNo) && platformUserNo.length() > 2) {
			return platformUserNo.substring(EUSER_PRE.length());
		}
		return "";
	}
	public static String formatNumber(double data) {
		NumberFormat nf = new DecimalFormat("##0.00");
		String str = nf.format(data);
		return str;
	}
	
	public static double formatDouble(double f) {
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static String getBankNameStr(String cardNo, String bank) {
		String bankName = "";
		if (StringUtil.isNotNull(bank)) {
			if ("BOCO".equals(bank)){ 
				bankName = "交通银行";
			}else if ("CEB".equals(bank)){ 
				bankName = "光大银行";
			}else if ("SPDB".equals(bank)){ 
				bankName = "上海浦东发展银行";
			}else if ("ABC".equals(bank)){ 
				bankName = "农业银行";
			}else if ("ECITIC".equals(bank)){ 
				bankName = "中信银行";
			}else if ("CCB".equals(bank)){ 
				bankName = "建设银行";
			}else if ("CMBC".equals(bank)){ 
				bankName = "民生银行";
			}else if ("SDB".equals(bank)){ 
				bankName = "平安银行";
			}else if ("PSBC".equals(bank)){ 
				bankName = "中国邮政储蓄";
			}else if ("CMBCHINA".equals(bank)){ 
				bankName = "招商银行";
			}else if ("CIB".equals(bank)){ 
				bankName = "兴业银行";
			}else if ("ICBC".equals(bank)){ 
				bankName = "中国工商银行";
			}else if ("BOC".equals(bank)){ 
				bankName = "中国银行";
			}else if ("BCCB".equals(bank)){ 
				bankName = "北京银行";
			}else if ("GDB".equals(bank)){ 
				bankName = "广发银行";
			}else if ("HX".equals(bank)){ 
				bankName = "华夏银行";
			}else if ("XAYH".equals(bank)){ 
				bankName = "西安市商业银行";
			}else if ("SHYH".equals(bank)){ 
				bankName = "上海银行";
			}else if ("TJYH".equals(bank)){ 
				bankName = "天津市商业银行";
			}else if ("SZNCSYYH".equals(bank)){ 
				bankName = "深圳农村商业银行";
			}else if ("BJNCSYYH".equals(bank)){ 
				bankName = "北京农商银行";
			}else if ("HZYH".equals(bank)){ 
				bankName = "杭州市商业银行";
			}else if ("KLYH".equals(bank)){ 
				bankName = "昆仑银行";
			}else if ("ZHENGZYH".equals(bank)){ 
				bankName = "郑州银行";
			}else if ("WZYH".equals(bank)){ 
				bankName = "温州银行";
			}
		}
		
		String cardNoStr = "";
		if(StringUtil.isNotNull(cardNo)) {
			int length = cardNo.length();
			if(length > 4) {
				cardNoStr = cardNo.substring(length-4, length);
			}else {
				cardNoStr = cardNo;
			}
		}
		return bankName + ":" + cardNoStr;
	}
	public static Date parseDate(String dateStr, String pattern) {
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateStr);
		} catch (ParseException e) {
			logger.error("stringUtil parse Date failure! dateStr=" + dateStr + " pattern=" + pattern,e);
		}
		return null;
	}
	
	public static String formatDate(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	public static String getContractNo(Date startSellingTime, int bidId, Integer userId) {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		if(startSellingTime == null) {
			startSellingTime = now;
		}
		sb.append(new SimpleDateFormat("yyyyMMdd").format(startSellingTime));
		DecimalFormat df = new DecimalFormat("0000"); 
		sb.append(df.format(bidId));
		sb.append(df.format(userId));
		sb.append(now.getTime());
		return sb.toString();
	}
	
	public static String hideString(String str,int prelength,int suflength) {
		if(isNull(str)) {
			return "";
		}
		if (str.length() > (prelength + suflength)) {
			int right = str.length();
			int left = right - suflength;
			return str.substring(0, prelength) + "****" + str.substring(left, right);
		}
		
		return str;
	}
	
	public static String hideString(String str,int suflength) {
		if(isNull(str)) {
			return "";
		}
		if (str.length() > suflength) {
			int right = str.length();
			int left = right - suflength;
			return "*" + str.substring(left, right);
		}
		
		return str;
	}
	public static String getUserNickname(String username,String nickname) {
		if(StringUtil.isNotNull(nickname)) {
			if(nickname.length() >= 7) {
				return hideString(nickname, 6);
			}
			return nickname;
		}
		return hideString(username, 3, 4);
	}

	public static int compareVersion(String version1, String version2) throws Exception {
		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
			++idx;
		}
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}
	
	/**
	 * 获取当前期数
	 * @param repaymentDates
	 * @param repaymentDate
	 * @return
	 */
	public static int getThisPeriods(List<String> repaymentDates, String repaymentDate) {
		if (repaymentDates != null && repaymentDates.size() > 0) {
			for (int i = 0; i < repaymentDates.size(); i++) {
				if (repaymentDate.equals(repaymentDates.get(i))) {
					return i + 1;
				}
			}
		}
		return 0;
	}
	
	/** 检查字符串是否是手机**/
	public static boolean isMobilePhone(String phone) {//  
        Pattern pattern = Pattern  
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");  
        Matcher matcher = pattern.matcher(phone);  
        return matcher.matches();  
    }
	
}
