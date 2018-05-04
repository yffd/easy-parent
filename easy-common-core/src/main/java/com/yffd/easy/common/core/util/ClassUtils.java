package com.yffd.easy.common.core.util;

/**
 * @Description  函数工具类：类.
 * @Date		 2017年9月12日 下午2:00:11 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ClassUtils {
    
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private ClassUtils() {
        
    }
    
    /**
     * 
     * getDefaultClassLoader:获取类加载器. <br/>
     * @Date	2017年9月12日 下午2:01:29 <br/>
     * @author  zhangST
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (null == cl) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (null == cl) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with
                    // null...
                }
            }
        }
        return cl;
    }
    
}

