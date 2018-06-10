package com.yffd.easy.common.core.tree.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月15日 上午10:25:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasySampleTreeJson {

	public static <T extends EasySampleTree> String getTreeJson(T tree) {
		if(null==tree) return null;
		Map<String, Object> map = new HashMap<String, Object>();
		EasySampleTreeJson.children(tree, map);
		return JSON.toJSONString(map);
	}
	
	private static <T extends EasySampleTree> List<EasySampleTree> children(T tree, Map<String, Object> map) {
		Object node = tree.getDataValue();
		map.putAll(JSON.parseObject(JSON.toJSONString(node), Map.class));
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("chilren");
		if(null==list || list.size()==0) list = new ArrayList<Map<String, Object>>();
		map.put("chilren", list);
		
		List<EasySampleTree> children = tree.getChildren();
		if(null==children || children.size()==0) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.putAll(JSON.parseObject(JSON.toJSONString(node), Map.class));
			list.add(tmp);
		} else {
			for(EasySampleTree child : children) {
				Map<String, Object> tmp = new HashMap<>();
				tmp.putAll(JSON.parseObject(JSON.toJSONString(child.getDataValue()), Map.class));
				tmp.put("children", children(child, tmp));
				list.add(tmp);
			}
		}
		return children;
	}
	
}

