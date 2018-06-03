package com.yffd.easy.demo.shiro.custom.serialize;

import com.yffd.easy.demo.shiro.uitl.SerializeUtils;

public class JedisByteSerializer implements IJedisSerializer {

	@Override
	public byte[] serialize(Object value) {
		return SerializeUtils.serialize(value);
	}
	
	@Override
	public Object deserialize(Object value) {
		return this.deserialize(value, Object.class);
	}
	
	@Override
	public <T> T deserialize(Object value, Class<T> clazz) {
		if(null==value) throw new NullPointerException("Can't deserialize null");
		if(value instanceof byte[]) {
			byte[] bytes = (byte[]) value;
			return SerializeUtils.deserialize(bytes, clazz);
		} else {
			throw new RuntimeException("value类型错误，应该为byte[]");
		}
	}

}
