package com.yffd.easy.framework.common.persist.mybatis.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.yffd.easy.framework.common.exception.CommonBizException;

/**
 * @Description  mybatis持久化操作，默认以指定的泛型类的全名为sql mapper 的 命名空间，一般以实体类（entity）作为泛型.
 * @Date		 2018年4月28日 下午2:57:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DefaultMybatisDao<E> extends MybatisDaoAbstract<E> {

	@Override
	protected String getSqlNamespace() {
//		return this.getClass().getName();
		return this.getGenericType().getName();
	}

	protected Class<E> getGenericType() {
		Type genericClazz = this.getClass().getGenericSuperclass();
		if(genericClazz instanceof ParameterizedType) {
			return (Class<E>) ((ParameterizedType) genericClazz).getActualTypeArguments()[0];
		} else {
			throw CommonBizException.newInstance("泛型解析错误");
		}
	}
	
}

