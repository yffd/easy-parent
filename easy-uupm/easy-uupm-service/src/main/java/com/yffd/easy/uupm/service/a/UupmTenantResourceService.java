package com.yffd.easy.uupm.service.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.entity.a.UupmResourceEntity;
import com.yffd.easy.uupm.entity.a.UupmTenantResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 15时04分21秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmTenantResourceService extends UupmBaseService<UupmTenantResourceEntity> {

	/**
	 * 某租户所拥有的所有资源
	 * @Date	2018年5月23日 上午11:30:33 <br/>
	 * @author  zhangST
	 * @param ttCode
	 * @return
	 */
	public List<UupmResourceEntity> findTenantResource(String ttCode) {
		return this.selectTenantResource(ttCode);
	}
	
	/**
	 * 查找某租户已拥有的资源编号
	 * @Date	2018年5月23日 上午11:30:08 <br/>
	 * @author  zhangST
	 * @param ttCode
	 * @return
	 */
	public Set<String> findRsCodesByTtCode(String ttCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTtCode(ttCode);
		List<UupmTenantResourceEntity> list = this.findList(entity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UupmTenantResourceEntity rs : list) {
			codes.add(rs.getRsCode());
		}
		return codes;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveTenantResource(String ttCode, List<String> rsCodesList, LoginInfo loginInfo) {
		this.delByTtCode(ttCode);
		List<UupmTenantResourceEntity> modelList = new ArrayList<UupmTenantResourceEntity>();
		for(String rsCode : rsCodesList) {
			UupmTenantResourceEntity model = new UupmTenantResourceEntity();
			model.setTtCode(ttCode);
			model.setRsCode(rsCode);
			modelList.add(model);
		}
		if(modelList.size()==0) return 0;
		return this.save(modelList, loginInfo);
	}
	
	public int delByTtCode(String ttCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTtCode(ttCode);
		return this.delete(entity, null);
	}
	
	public int delByRsCodes(Set<String> rsCodes) {
		return this.deleteByProps("rsCodeIter", rsCodes);
	}
	
	
	
	public List<UupmResourceEntity> selectTenantResource(String ttCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTtCode(ttCode);
		return this.selectListByCustom("selectTenantResource", entity, true);
	}
	
}
