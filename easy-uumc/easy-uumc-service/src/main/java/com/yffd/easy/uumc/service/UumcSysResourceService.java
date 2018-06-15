package com.yffd.easy.uumc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.uumc.pojo.entity.UumcSysResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时22分23秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcSysResourceService extends UupmBaseService<UumcSysResourceEntity> {
	
	@Autowired
	private UumcPermissionService uumcPermissionService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByIds(Set<String> ids) {
		if (null == ids || ids.size() == 0) return 0;
		// 删除关联关系： 资源-权限
		List<UumcSysResourceEntity> list = this.selectListByProps("idIter", ids, null);
		if (null != list && list.size() > 0) {
			Set<String> pmsCodeSet = new HashSet<>();
			for (UumcSysResourceEntity tmp : list) {
				pmsCodeSet.add(tmp.getAppCode() + "-" + tmp.getRsCode());
			}
			this.uumcPermissionService.delCascadeByPmsCode(pmsCodeSet);
		}
		// 删除资源
		int num = this.deleteByProps("idIter", ids);
		return num;
	}
	
}
