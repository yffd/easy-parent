package com.yffd.easy.common.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.yffd.easy.common.core.exception.EasyCommonException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月18日 上午11:41:53 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyDateUtils {
	private static DateFormat DEFAULT_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Map<String, String> DATE_REG_FORMAT = new HashMap<String, String>();
	static {
		DATE_REG_FORMAT.put(
				"^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,3}\\D*$",
				"yyyy-MM-dd-HH-mm-ss.SSS");//2017年12月18日 11时46分34秒，2017-12-18 11:18:34 ，2017/12/18 11:46:34
		DATE_REG_FORMAT.put(
	        "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$",
	        "yyyy-MM-dd-HH-mm-ss");//2017年12月18日 11时46分34秒，2017-12-18 11:18:34 ，2017/12/18 11:46:34
		DATE_REG_FORMAT.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
	        "yyyy-MM-dd-HH-mm");//2017-12-18 11:18
		DATE_REG_FORMAT.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
	        "yyyy-MM-dd-HH");//2017-12-18 11
		DATE_REG_FORMAT.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2017-12-18
		DATE_REG_FORMAT.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2017-12
		DATE_REG_FORMAT.put("^\\d{4}$", "yyyy");//2017-12-18
		DATE_REG_FORMAT.put("^\\d{14}$", "yyyyMMddHHmmss");//20171218111834
		DATE_REG_FORMAT.put("^\\d{12}$", "yyyyMMddHHmm");//201712181118
		DATE_REG_FORMAT.put("^\\d{10}$", "yyyyMMddHH");//2017121811
		DATE_REG_FORMAT.put("^\\d{8}$", "yyyyMMdd");//20171218
		DATE_REG_FORMAT.put("^\\d{6}$", "yyyyMM");//201712
		DATE_REG_FORMAT.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$",
	        "yyyy-MM-dd-HH-mm-ss");//11:18:34 拼接当前日期
		DATE_REG_FORMAT.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//11:34 拼接当前日期
		DATE_REG_FORMAT.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//17.12.18(年.月.日)
		DATE_REG_FORMAT.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//18.12(日.月) 拼接当前年份
		DATE_REG_FORMAT.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.18.2017(日.月.年)
	  
	}
	
	public static Date parseToDate(String dateFmt) {
		DateFormat tmpFmt;
		for(String key : DATE_REG_FORMAT.keySet()) {
			if(Pattern.compile(key).matcher(dateFmt).matches()) {
				try {
					if(key.equals("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,3}\\D*$")) {
						tmpFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						return tmpFmt.parse(dateFmt);
					}
					tmpFmt = new SimpleDateFormat(DATE_REG_FORMAT.get(key));
					if(key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
					      || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//11:18:34 或 11:18 拼接当前日期
						String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						dateFmt = curDate + "-" + dateFmt;
					} else if(key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//18.12 (日.月) 拼接当前年份
						String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						dateFmt = curDate.substring(0, 4) + "-" + dateFmt;
					}
					String dateReplace = dateFmt.replaceAll("\\D+", "-");
					return tmpFmt.parse(dateReplace);
				} catch (ParseException e) {
					throw new EasyCommonException("日期格式无效：【" + dateFmt + "】", e);
				}
			}
		}
		return null;
	}
	
	public static String parseToFmt(String dateFmt) {
		String tmpFmt = null;
		for(String key : DATE_REG_FORMAT.keySet()) {
			if(Pattern.compile(key).matcher(dateFmt).matches()) {
				tmpFmt = DATE_REG_FORMAT.get(key);
				break;
			}
		}
		if(null==tmpFmt) {
			throw new EasyCommonException("日期格式无效：【" + dateFmt + "】");
		}
		return tmpFmt;
	}
	
	@Deprecated
	private static String changeFmt(String dateFmt) {
		DateFormat tmpFmt;
		String dateReplace;
	    String strSuccess = "";
	    try {
	    	for(String key : DATE_REG_FORMAT.keySet()) {
	    		if(Pattern.compile(key).matcher(dateFmt).matches()) {
	    			if(key.equals("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,3}\\D*$")) {
	    				tmpFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    				strSuccess = tmpFmt.format(tmpFmt.parse(dateFmt));
	    				return strSuccess;
	    			}
	    			tmpFmt = new SimpleDateFormat(DATE_REG_FORMAT.get(key));
	    			if(key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
			              || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//11:18:34 或 11:18 拼接当前日期
	    				String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    				dateFmt = curDate + "-" + dateFmt;
	    			} else if(key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//18.12 (日.月) 拼接当前年份
	    				String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    				dateFmt = curDate.substring(0, 4) + "-" + dateFmt;
	    			}
	    			dateReplace = dateFmt.replaceAll("\\D+", "-");
	    			strSuccess = DEFAULT_FMT.format(tmpFmt.parse(dateReplace));
	    			break;
	    		}
	    	}
	    } catch (Exception e) {
	    	throw new EasyCommonException("日期格式无效", e);
	    }
	    return strSuccess;
	}
	
	public static void main(String[] args) {
	    String[] dateStrArray = new String[]{
	    		"2017-03-12 12:05:34.123",
		        "2017-03-12 12:05:34",
		        "2017-03-12 12:05",
		        "2017-03-12 12",
		        "2017-03-12",
		        "2017-03",
		        "2017",
		        "20140312120534",
		        "2017/03/12 12:05:34",
		        "2017/3/12 12:5:34",
		        "2014年3月12日 13时5分34秒",
		        "201403121205",
		        "1234567890",
		        "20140312",
		        "201403",
		        "2000 13 33 13 13 13",
		        "30.12.2013",
		        "12.21.2013",
		        "21.1",
		        "13:05:34",
		        "12:05",
		        "14.1.8",
		        "14.10.18"
		        };
	    for(int i=0;i<dateStrArray.length;i++){
	      System.out.println(dateStrArray[i] +"------------------------------".substring(1,30-dateStrArray[i].length())+ parseToDate(dateStrArray[i]));
	    }
	    
	    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    try {
			Date date = fmt.parse("2017-11-29 17:57:52.12");
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    System.out.println(parseToFmt("2017-11-29 17:57:52.12"));
	  }
}

