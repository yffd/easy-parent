package com.yffd.easy.demo.shiro.custom.serialize;

import com.alibaba.fastjson.JSON;

public class JedisJsonSerializer implements IJedisSerializer {

	@Override
	public String serialize(Object value) {
		if(null==value) throw new NullPointerException("Can't serialize null");
		return JSON.toJSONString(value);
	}
	
	@Override
	public Object deserialize(Object value) {
		return this.deserialize(value, Object.class);
	}
	
	@Override
	public <T> T deserialize(Object value, Class<T> clazz) {
		if(null==value) throw new NullPointerException("Can't deserialize null");
		if(value instanceof String) {
			String text = (String) value;
			return JSON.parseObject(text, clazz);
		} else {
			throw new RuntimeException("value类型错误，应该为String");
		}
	}

}
