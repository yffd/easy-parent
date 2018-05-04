package com.yffd.easy.common.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年9月8日 下午3:31:04 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ValidUtils {
    
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private ValidUtils() {
    }
    
    /**
     * 
     * isEmpty:判断字符串是否为空. <br/>
     * @Date    2017年9月8日 下午3:07:16 <br/>
     * @author  zhangST
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null==str || "".equals(str);
    }

    /**
     * 
     * isEmpty:判断对象数组是否为空. <br/>
     * @Date    2017年9月8日 下午3:07:37 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object[] obj) {
        return null==obj || 0==obj.length;
    }

    /**
     * 
     * isEmpty:判断对象是否为空. <br/>
     * @Date    2017年9月8日 下午3:07:51 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if(null==obj) return true;
        if(obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return (obj instanceof Number) ? true : false;
    }

    /**
     * 
     * isEmpty:判断集合是否为空. <br/>
     * @Date    2017年9月8日 下午3:13:09 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static boolean isEmpty(Collection<?> obj) {
        return null==obj || obj.isEmpty();
    }

    /**
     * 
     * isEmpty:判断Map集合是否为空. <br/>
     * @Date    2017年9月8日 下午3:14:02 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null==obj || obj.isEmpty();
    }
    
    /**
     * 
     * isNull:判断对象是否为null. <br/>
     * @Date	2017年9月8日 下午5:19:13 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return null==obj;
    }
    
    /**
     * 
     * isNumeric:验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false. <br/>
     * @Date    2017年9月8日 下午3:15:33 <br/>
     * @author  zhangST
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if(isBlank(str)) {
            return false;
        } else {
            return str.matches("\\d*");
        }
    }
    
    /**
     * 
     * isBlank:判断字符串是否为空. <br/>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     * @Date    2017年9月8日 下午3:25:12 <br/>
     * @author  zhangST
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if(cs==null || (strLen=cs.length())==0) {
            return true;
        }
        for(int i = 0; i < strLen; i++) {
            if(Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}

