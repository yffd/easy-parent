package com.yffd.easy.common.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Description  函数工具类：序列化.
 * @Date		 2017年9月12日 下午2:03:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SerializeUtils {
    
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private SerializeUtils() {
        
    }
    
    /**
     * 
     * serialize:对象序列化. <br/>
     * @Date    2017年6月22日 上午10:54:51 <br/>
     * @author  zhangST
     * @param obj
     * @return
     */
    public static byte[] serialize(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bytes = baos.toByteArray();
            baos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    
    /**
     * 
     * deserialize:对象反序列化. <br/>
     * @Date    2017年6月22日 上午10:55:12 <br/>
     * @author  zhangST
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        Object obj=null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            bais.close();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static void main(String[] args) {
    	byte[] key = SerializeUtils.serialize("admin");
    	String value = (String) SerializeUtils.deserialize(key);
    	System.out.println(value);
	}
}

