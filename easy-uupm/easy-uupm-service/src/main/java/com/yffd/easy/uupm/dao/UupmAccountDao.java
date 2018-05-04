package com.yffd.easy.uupm.dao;

import org.springframework.stereotype.Repository;

import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.pojo.vo.UupmLoginInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmAccountDao extends UupmCommonDao<UupmAccountEntity> {

	public UupmLoginInfoVo findLoginInfo(UupmLoginInfoVo loginInfoVo) {
		return this.customSelectOneBy("selectLoginInfo", loginInfoVo, true);
	}
}
