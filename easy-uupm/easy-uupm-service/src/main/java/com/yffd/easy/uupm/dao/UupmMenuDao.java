package com.yffd.easy.uupm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yffd.easy.uupm.entity.UupmMenuEntity;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmMenuDao extends UupmCommonDao<UupmMenuEntity> {

	public List<UupmMenuInfoVo> findMenuInfoList(UupmMenuInfoVo menuInfoVo) {
		return this.customSelectListBy("selectMenuInfo", menuInfoVo, true);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForAdminList(UupmMenuInfoVo menuInfoVo) {
		return this.customSelectListBy("selectMenuInfoForAdmin", menuInfoVo, true);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForDefaultList(UupmMenuInfoVo menuInfoVo) {
		return this.customSelectListBy("selectMenuInfoForDefault", menuInfoVo, true);
	}
}
