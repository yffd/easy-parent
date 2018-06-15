package com.yffd.easy.uumc.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.yffd.easy.uumc.pojo.entity.UumcOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时19分52秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcOrganizationService extends UupmBaseService<UumcOrganizationEntity> {
	
	public int delByIds(Set<String> ids) {
		return this.deleteByProps("idIter", ids);
	}
	
}
