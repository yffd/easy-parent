package com.yffd.easy.common.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月14日 上午11:08:02 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyNamingFormatUtils {

	/**
	 * 驼峰格式转换为下划线格式
	 * @Date	2017年12月14日 下午1:57:41 <br/>
	 * @author  zhangST
	 * @param str				下划线源字符串
	 * @param returnUpperCase	转换后的字符串是否大写返回，true：大写返回，false：小写返回
	 * @param prefix			前缀字符串
	 * @param suffix			后缀字符串
	 * @return
	 */
	public static String camel2underline(String str, boolean returnUpperCase, String prefix, String suffix){
        if(str==null || "".equals(str.trim())) {
            return "";
        }
        if(str.indexOf("_")>-1) {
        	if(returnUpperCase) {
        		return str.toUpperCase();
        	} else {
        		return str;
        	}
        }
        str = String.valueOf(str.charAt(0)).toUpperCase().concat(str.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)+");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end()==str.length()?"":"_");
        }
        if(null!=suffix && !"".equals(suffix)) {
        	sb.append(suffix);
        }
        if(null!=prefix && !"".equals(prefix)) {
        	return prefix + sb.toString();
        }
        if(returnUpperCase) {
        	return sb.toString().toUpperCase();
        } else {
        	return sb.toString().toLowerCase();
        }
    }
	
	/**
	 * 下划线格式转驼峰格式
	 * @Date	2017年12月15日 上午9:50:15 <br/>
	 * @author  zhangST
	 * @param str			驼峰源字符串
	 * @param smallCamel	大小驼峰，true：小驼峰，false：大驼峰
	 * @param prefix		前缀字符串
	 * @param suffix		后缀字符串
	 * @return
	 */
	public static String underline2camel(String str, boolean smallCamel, String prefix, String suffix) {
		if(str==null || "".equals(str.trim())) {
            return "";
        }
		StringBuffer sb = new StringBuffer();
		if(str.indexOf("_")==-1) {
			Pattern pattern = Pattern.compile("([A-Z]+)?");
	        Matcher matcher = pattern.matcher(str);
	        if(matcher.matches()) {
	        	sb.append(smallCamel?Character.toLowerCase(str.charAt(0)):Character.toUpperCase(str.charAt(0)));
				sb.append(str.substring(1, str.length()).toLowerCase());
	        } else if(matcher.find()) {
	        	sb.append(smallCamel?Character.toLowerCase(str.charAt(0)):Character.toUpperCase(str.charAt(0)));
				sb.append(str.substring(1, str.length()));
	        } else {
	        	sb.append(smallCamel?Character.toLowerCase(str.charAt(0)):Character.toUpperCase(str.charAt(0)));
				sb.append(str.substring(1, str.length()).toLowerCase());
	        }
		} else {
			Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
	        Matcher matcher = pattern.matcher(str);
	        while(matcher.find()) {
	            String word = matcher.group();
	            sb.append(smallCamel && matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
	            int index = word.lastIndexOf('_');
	            if(index>0) {
	                sb.append(word.substring(1, index).toLowerCase());
	            } else {
	                sb.append(word.substring(1).toLowerCase());
	            }
	        }
		}
        if(null!=suffix && !"".equals(suffix)) {
        	sb.append(suffix);
        }
        if(null!=prefix && !"".equals(prefix)) {
        	return prefix + sb.toString();
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		String prefix = "";
		String suffix = "";
        System.out.println(camel2underline("username", false, prefix, suffix));
//        System.out.println(camel2underline("username", true, prefix, suffix));
//        System.out.println(camel2underline("userName", true, prefix, suffix));
//        System.out.println(camel2underline("userName", false, prefix, suffix));
//        System.out.println(camel2underline("user_name", true, prefix, suffix));
//        System.out.println(camel2underline("USER_NAME", true, prefix, suffix));
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        
        System.out.println(underline2camel("userName", true, prefix, suffix));
//        System.out.println(underline2camel("userName", false, prefix, suffix));
//        System.out.println(underline2camel("USER_NAME_PWD", true, prefix, suffix));
//        System.out.println(underline2camel("USER_NAME_PWD", false, prefix, suffix));
//        System.out.println(underline2camel("USER_NAME_", true, prefix, suffix));
//        System.out.println(underline2camel("USER_NAME_", false, prefix, suffix));
//        System.out.println(underline2camel("user_name_", true, prefix, suffix));
//        System.out.println(underline2camel("user_name_", false, prefix, suffix));
        System.out.println(underline2camel("AGE", true, prefix, suffix));
    }
}

