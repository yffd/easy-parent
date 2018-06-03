package com.yffd.easy.demo.shiro.custom.serialize;

public interface IJedisSerializer {

	Object serialize(Object value);
	
	Object deserialize(Object value);
	
	<T> T deserialize(Object value, Class<T> clazz);
	
}
