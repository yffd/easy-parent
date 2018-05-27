package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.dao.UupmUserDao;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月10日 14时34分16秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmUserService extends UupmBaseService<UupmUserEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@Autowired
	private UupmUserDao uupmUserDao;

	@Override
	protected IMybatisCommonDao<UupmUserEntity> getBindDao() {
		return uupmUserDao;
	}
	
	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(userInfo, loginInfo);
		return this.uupmUserDao.findUserInfoPage(userInfo, pageParam);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserWithAccount(UupmUserEntity user, UupmAccountEntity account, LoginInfo loginInfo) {
		int result = this.save(user, loginInfo);
		// 生成普通账号
		this.uupmAccountService.addDefaultAccount(account, loginInfo);
		return result;
	}
}
