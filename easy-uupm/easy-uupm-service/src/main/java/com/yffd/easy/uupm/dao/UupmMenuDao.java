package com.yffd.easy.uupm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.uupm.entity.a.UupmMenuEntity;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月24日 15时15分58秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmMenuDao extends DefaultMybatisDao<UupmMenuEntity> {

	public List<UupmMenuInfoVo> findMenuInfoList(UupmMenuInfoVo menuInfoVo) {
		return this.selectListByCustom("selectMenuInfo", menuInfoVo, true);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForAdminList(UupmMenuInfoVo menuInfoVo) {
		return this.selectListByCustom("selectMenuInfoForAdmin", menuInfoVo, true);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForDefaultList(UupmMenuInfoVo menuInfoVo) {
		return this.selectListByCustom("selectMenuInfoForDefault", menuInfoVo, true);
	}
	
}
