package com.yffd.easy.common.core.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  函数工具类：常用.
 * @Date		 2017年9月8日 下午3:04:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);
    
    /** 换行符 */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    /**
     * 
     * getLineSeparator:获取换行符. <br/>
     * @Date    2017年8月4日 上午10:40:35 <br/>
     * @author  zhangST
     * @return
     */
    public static String getLineSeparator() {
        return LINE_SEPARATOR;
    }
    
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private CommonUtils() {
    }
    
    /**
     * 
     * getExt:获得文件名的后缀名. <br/>
     * @Date	2017年9月8日 下午3:14:39 <br/>
     * @author  zhangST
     * @param fileName
     * @return
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 
     * getByteSize:计算采用utf-8编码方式时字符串所占字节数. <br/>
     * @Date	2017年9月8日 下午3:20:10 <br/>
     * @author  zhangST
     * @param content
     * @return
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                LOG.error("计算字符串所占字节数失败", e);
            }
        }
        return size;
    }
    
    /**
     * 
     * getByteSize:计算字符串所占字节数. <br/>
     * @Date	2017年9月8日 下午3:21:32 <br/>
     * @author  zhangST
     * @param content
     * @param charsetName
     * @return
     */
    public static int getByteSize(String content, String charsetName) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes(charsetName).length;
            } catch (UnsupportedEncodingException e) {
                LOG.error("计算字符串所占字节数失败", e);
            }
        }
        return size;
    }

}

