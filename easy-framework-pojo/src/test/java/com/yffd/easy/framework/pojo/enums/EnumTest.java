package com.yffd.easy.framework.pojo.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午3:57:32 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EnumTest {
	enum Color {RED, GREEN, BLUE;}
    enum Size {BIG, MIDDLE, SMALL;}
    
    @Test
    public void test() {
        System.out.println("=========== Print all Color ===========");
        for (Color c : Color.values()) {
            System.out.println(c + " ordinal: " + c.ordinal());
        }
        System.out.println("=========== Print all Size ===========");
        for (Size s : Size.values()) {
            System.out.println(s + " ordinal: " + s.ordinal());
        }
        
        Color green = Color.GREEN;
        System.out.println("green name(): " + green.name());
        System.out.println("green getDeclaringClass(): " + green.getDeclaringClass());
        System.out.println("green hashCode(): " + green.hashCode());
        System.out.println("green compareTo Color.GREEN: " + green.compareTo(Color.GREEN));
        System.out.println("green equals Color.GREEN: " + green.equals(Color.GREEN));
        System.out.println("green equals Size.MIDDLE: " + green.equals(Size.MIDDLE));
        System.out.println("green equals 1: " + green.equals(1));
        System.out.format("green == Color.BLUE: %b\n", green == Color.BLUE);
        
     // EnumSet的使用
        System.out.println("EnumSet展示");
        EnumSet<Color> errSet = EnumSet.allOf(Color.class);
        for (Color e : errSet) {
            System.out.println(e.name() + " : " + e.ordinal());
        }

        // EnumMap的使用
        System.out.println("EnumMap展示");
        // 第一种
        EnumMap<Color, String> enumMap = new EnumMap(Color.class);
        enumMap.put(Color.RED, "红灯");
        enumMap.put(Color.BLUE, "蓝灯");
        enumMap.put(Color.GREEN, "绿灯");
        //使用第二种构造
//        Map<Color, String> enumMap2 = new EnumMap<>(enumMap);
        //使用第三种构造
//        Map<Color, String> hashMap = new HashMap<>();
//        hashMap.put(Color.GREEN, "green");
//        hashMap.put(Color.BLUE, "blue");
//        Map<Color, String> enumMap3 = new EnumMap<>(hashMap);
        for (Iterator<Map.Entry<Color, String>> iter = enumMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<Color, String> entry = iter.next();
            System.out.println(entry.getKey().name() + " : " + entry.getValue());
        }
        
        System.out.println("...................");
        System.out.println(Color.valueOf(Color.class, "RED"));
    }
}

