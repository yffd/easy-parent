package com.yffd.easy.common.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 上午11:10:37 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyReflectionUtils {
	private static Logger LOG = LoggerFactory.getLogger(EasyReflectionUtils.class);
	
	/**
	 * 获取属性名称的差集(minuendClazz - subtractionClazz)
	 * @Date	2018年4月24日 下午4:13:30 <br/>
	 * @author  zhangST
	 * @param minuendClazz
	 * @param subtractionClazz
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getDiffFieldName(Class<?> minuendClazz, Class<?> subtractionClazz) throws Exception {
		Set<String> set1 = getAllFieldNames(minuendClazz);
		Set<String> set2 = getAllFieldNames(subtractionClazz);
		set1.removeAll(set2);
		return set1;
	}
	
	/**
	 * 获取属性名称（包括父类属性名称）：本身类和其父类、静态和实例属性、访问类型（所有）.</br>
	 * 			不包括stopClazz和其所有父类中的属性.</br>
	 * @Date	2018年4月24日 下午2:13:30 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @param stopClazz		
	 * @return
	 */
	public static Set<String> getAllFieldNames(Class<?> clazz) {
		return getAllFieldNames(clazz, Object.class);
	}
	
	/**
	 * 获取属性名称（包括父类属性名称）：本身类和其父类、静态和实例属性、访问类型（所有）.</br>
	 * 			不包括stopClazz和其所有父类中的属性.</br>
	 * @Date	2018年4月24日 下午2:13:30 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @param stopClazz		
	 * @return
	 */
	public static Set<String> getAllFieldNames(Class<?> clazz, Class<?> stopClazz) {
		Set<String> fieldNames = new LinkedHashSet<String>();
		for(Class<?> tmpClazz = clazz; tmpClazz != stopClazz; tmpClazz = tmpClazz.getSuperclass()) {
			Set<String> tmpNames = getFieldNames(tmpClazz);
			if(tmpNames.size()>0) fieldNames.addAll(tmpNames);
		}
		return fieldNames;
	}
	
	/**
	 * 获取本身属性名称（不包括父类属性名称）：本身类、静态和实例属性、访问类型（所有）.</br>
	 * @Date	2018年4月24日 下午2:04:55 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @return
	 */
	public static Set<String> getFieldNames(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Set<String> fieldNames = new LinkedHashSet<String>();
		if(fields.length==0) return fieldNames;
		for(Field field : fields) {
			LOG.info(field.getName() + " find : in " + clazz.getName());
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}
	
	/**
	 * 获取属性名称（包括父类属性）：本身和其父类、静态和实例属性、访问类型（public）.</br>
	 * 			例如：public、public static</br>
	 * @Date	2018年4月24日 下午1:59:27 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @return
	 */
	public static Set<String> getPublicFieldNames(Class<?> clazz) {
		Field[] fields = clazz.getFields();
		Set<String> fieldNames = new LinkedHashSet<String>();
		if(fields.length==0) return fieldNames;
		for(Field field : fields) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 获取属性值（包括父类属性）：本身和其父类、静态和实例属性、访问类型（所有）.</br>
	 * @Date	2018年4月24日 下午2:46:28 <br/>
	 * @author  zhangST
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(obj);
        return value;
    }
	
	/**
	 * 获取属性值（包括父类属性）：本身和其父类、静态和实例属性、访问类型（public）.</br>
	 * 			例如：public、public static</br>
	 * @Date	2018年4月24日 下午2:43:24 <br/>
	 * @author  zhangST
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
    public static Object getPublicFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getField(fieldName);
        Object value = field.get(obj);
        return value;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
	
    /**
     * 设置属性值（包括父类属性）：本身和其父类、静态和实例属性、访问类型（所有）.</br>
     * @Date	2018年4月24日 下午3:10:43 <br/>
     * @author  zhangST
     * @param obj
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
    	Field field = obj.getClass().getDeclaredField(fieldName);
    	field.setAccessible(true);
    	field.set(obj, value);
    }
    
    /**
     * 设置属性值（包括继承属性）：本身和其父类、静态和实例属性、访问类型（public）.</br>
	 * 			例如：public、public static</br>
     * @Date	2018年4月24日 下午3:12:23 <br/>
     * @author  zhangST
     * @param obj
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setPublicFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getField(fieldName);
        field.set(obj, value);
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 执行方法（包括父类的实例方法和静态方法）：访问类型（public）.</br>
     * @Date	2018年4月24日 下午4:09:29 <br/>
     * @author  zhangST
     * @param obj
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
	public static Object invokeMethodWithInherit(Object obj, String methodName, Object... args) throws Exception {
        Class<?> objClazz = obj.getClass();
        Class<?>[] argsClazz = new Class[args.length];
        for(int i = 0, j = args.length; i < j; i++) {
        	argsClazz[i] = args[i].getClass();
        }
        Method method = null;
        for(Class<?> clazz = objClazz; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, argsClazz);
                LOG.info(method + " find : in " + clazz.getName());
                return method;
            } catch(Exception e) {
            	LOG.info(methodName + " not find in " + clazz.getName());
            }
        }
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    /**
     * 执行本身方法（不包括父类）：访问类型（所有）.</br>
     * @Date	2018年4月24日 下午4:00:10 <br/>
     * @author  zhangST
     * @param obj
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object obj, String methodName, Object... args) throws Exception {
    	Class<?>[] argsClazz = null;
    	if(null != args && args.length > 0) {
    		argsClazz = new Class[args.length];
    		for(int i = 0, j = args.length; i < j; i++) {
    			argsClazz[i] = args[i].getClass();
    		}
    	}
    	Method method = obj.getClass().getDeclaredMethod(methodName, argsClazz);	// 所有访问类型、只在自己类中的
    	method.setAccessible(true);
    	return method.invoke(obj, args);
    }
    
	/**
	 * 执行方法（包括父类的实例方法和静态方法）：访问类型（public）.</br>
	 * 					例如：public、public static</br>
	 * @Date	2018年4月24日 下午4:00:17 <br/>
	 * @author  zhangST
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokePublicMethod(Object obj, String methodName, Object... args) throws Exception {
		Class<?>[] argsClazz = null;
		if(null != args && args.length > 0) {
			argsClazz = new Class[args.length];
			for(int i = 0, j = args.length; i < j; i++) {
				argsClazz[i] = args[i].getClass();
			}
		}
        Method method = obj.getClass().getMethod(methodName, argsClazz);	// 所有public类型、包括父类中的
        return method.invoke(obj, args);
    }

	/**
	 * 执行本身静态方法（不包括父类）：访问类型（所有）.</br>
	 * @Date	2018年4月24日 下午4:01:28 <br/>
	 * @author  zhangST
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeStaticMethod(String className, String methodName, Object... args) throws Exception {
        Class<?> objClazz = Class.forName(className);
        Class<?>[] argsClazz = new Class[args.length];
        for(int i = 0, j = args.length; i < j; i++) {
        	argsClazz[i] = args[i].getClass();
        }
        Method method = objClazz.getMethod(methodName, argsClazz);	
        method.setAccessible(true);
        return method.invoke(null, args);
    }
	
	/////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 根据类全名，创建其一个实例对象
	 * @Date	2018年4月24日 上午11:51:34 <br/>
	 * @author  zhangST
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(String className, Object[] args) throws Exception {
		return newInstance(className, args, null);
    }
	
	/**
	 * 根据类全名，创建其一个实例对象
	 * @Date	2018年4月24日 上午11:48:59 <br/>
	 * @author  zhangST
	 * @param className
	 * @param args
	 * @param argsType
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(String className, Object[] args, Class<?>[] argsType) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        return newInstance(ownerClass, args, argsType);
    }
	
	/**
	 * 根据类，创建其一个实例对象
	 * @Date	2018年4月24日 上午11:50:53 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @param args
	 * @param argsType
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(Class<?> clazz, Object[] args) throws Exception {
		return newInstance(clazz, args);
	}
	
	/**
	 * 根据类，创建其一个实例对象
	 * @Date	2018年4月24日 上午11:50:53 <br/>
	 * @author  zhangST
	 * @param clazz
	 * @param args
	 * @param argsType
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(Class<?> clazz, Object[] args, Class<?>[] argsType) throws Exception {
        Class<?> ownerClass = clazz;
        if(args==null) {
            return ownerClass.newInstance();
        } else {
            Constructor<?> constructor;
            if(argsType==null) {
                Class<?>[] argsClass = new Class[args.length];
                for(int i=0, j=args.length; i<j; i++) {
                    argsClass[i] = args[i].getClass();
                }
                constructor = ownerClass.getConstructor(argsClass);
            } else {
            	constructor = ownerClass.getConstructor(argsType);
            }
            return constructor.newInstance(args);
        }
    }
	
	/**
	 * 判断某个实例对象是否为某个类的一个实例
	 * @Date	2018年4月24日 上午11:47:10 <br/>
	 * @author  zhangST
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static boolean isInstance(Object obj, Class<?> clazz) {
        return clazz.isInstance(obj);
    }
	
	/**
	 * 获取某包下的所有类
	 * @Date	2018年4月24日 上午11:46:50 <br/>
	 * @author  zhangST
	 * @param packageName
	 * @return
	 */
	public static Class<?> getClassListByPackage(String packageName) {
		return Package.getPackage(packageName).getClass();
	}
	
}

