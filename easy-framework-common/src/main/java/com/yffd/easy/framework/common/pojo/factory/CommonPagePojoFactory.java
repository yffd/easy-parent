package com.yffd.easy.framework.common.pojo.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yffd.easy.framework.pojo.page.PageResult;

public class CommonPagePojoFactory extends CommonPojoFactory {
	
	public <T> List<T> pojo2pojo(List<?> origPojoList, Class<T> destPojoClazz) {
		if(null==destPojoClazz || null==origPojoList || origPojoList.size()==0) return null;
		List<T> list = new ArrayList<T>();
		for(Object pojo : origPojoList) {
			T tmp = this.pojo2pojo(pojo, destPojoClazz);
			if(null!=tmp) list.add(tmp);
		}
		return list;
		
	}
	
	public <T> List<T> map2pojo(List<Map<String, Object>> origMapList, Class<T> destPojoClazz) {
		if(null==destPojoClazz || null==origMapList || origMapList.size()==0) return null;
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map : origMapList) {
			T tmp = this.map2pojo(map, destPojoClazz);
			if(null!=tmp) list.add(tmp);
		}
		return list;
	}
	
	public <T> PageResult<T> page2page(PageResult<?> pagination, Class<T> destPojoClazz) {
		if(null==pagination || null==destPojoClazz) return null;
		List<T> targetList = null;
		List<?> recordList = pagination.getRecordList();
		if(null==recordList || recordList.size()==0) {

		} else {
			Object tmp = recordList.get(0);
			if(destPojoClazz.isInstance(tmp)) {
				targetList = (List<T>) recordList;
			} else if(tmp instanceof Map) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) recordList;
				targetList = this.map2pojo(list, destPojoClazz);
			} else {
				targetList = this.pojo2pojo(recordList, destPojoClazz);
			}
		}
		return new PageResult<T>(pagination.getPageParam(), targetList);
	}
}
