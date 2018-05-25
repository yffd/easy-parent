package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.service.CommonService;
import com.yffd.easy.uupm.dao.UupmUserDao;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月10日 11时30分36秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmUserServiceDemo extends CommonService<UupmUserEntity> {

	@Autowired
	private UupmUserDao uupmUserDao;

	@Override
	protected IMybatisCommonDao<UupmUserEntity> getBindDao() {
		return uupmUserDao;
	}
	
//	public PageResult<UupmUserVo> findUserInfo(UupmUserVo vo, PageParam pageParam) {
//		UupmUserEntity entity = this.getCommonPojoConverter()
//				.convert2BeanWithSameProps(vo, UupmUserEntity.class);
////		return this.uupmUserDao.findOne(entity);
////		return this.uupmUserDao.findUserInfo(paramMap, pageParam);
//	}
	
	public UupmUserEntity findOneUser(UupmUserEntity entity) {
		return this.findOne(entity);
	}
	
	public UupmUserEntity findOneUser123(UupmUserInfoVo vo) {
		UupmUserEntity entity = this.getServiceSupport().getPojoConverter()
				.convert2BeanWithSameProps(vo, UupmUserEntity.class);
		return this.findOne(entity);
	}
	
	public UupmUserInfoVo findOneUser124(UupmUserInfoVo vo) {
		UupmUserEntity entity = this.getServiceSupport().getPojoConverter()
				.convert2BeanWithSameProps(vo, UupmUserEntity.class);
		UupmUserEntity result = this.findOne(entity);
		UupmUserInfoVo voResult = this.getServiceSupport().getPojoConverter()
				.convert2BeanWithSameProps(result, UupmUserInfoVo.class);
		return voResult;
	}
	
	public UupmUserEntity findOneUser125(UupmUserInfoVo vo) {
		UupmUserEntity entity = this.getServiceSupport().getVoSupport().findOne(vo);
		return entity;
	}
	
}
