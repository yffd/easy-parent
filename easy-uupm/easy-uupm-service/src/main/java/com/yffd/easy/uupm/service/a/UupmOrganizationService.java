package com.yffd.easy.uupm.service.a;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.uupm.entity.a.UupmOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月04日 10时09分53秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmOrganizationService extends UupmBaseService<UupmOrganizationEntity> {

	public Integer deleteByIds(String idStr) {
		if(EasyStringCheckUtils.isEmpty(idStr)) return 0;
		String[] idsArr = idStr.split(",");
		if(idsArr.length==0) return 0;
		List<String> idsList = Arrays.asList(idsArr);
		Set<String> ids = new HashSet<String>(idsList);
		return this.deleteByIds(ids);
	}
	
	public Integer deleteByIds(Set<String> ids) {
		return this.deleteByProps("idIter", ids);
	}
	
}
