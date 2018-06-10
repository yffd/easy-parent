package com.yffd.easy.uupm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.uupm.pojo.entity.UupmResourceEntity;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 下午4:00:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
@Service
public class UupmMenuService{

	@Autowired
	private UupmResourceService uupmResourceService;
	
	public List<UupmResourceEntity> findAllMenu() {
		return this.uupmResourceService.findList(null , null);
	}
	
	
}
