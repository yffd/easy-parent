package com.yffd.easy.uupm.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.uupm.dao.UupmOrganizationDao;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月04日 10时09分53秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmOrganizationService extends CommonSimpleServiceImpl<UupmOrganizationEntity> {

	@Autowired
	private UupmOrganizationDao uupmOrganizationDao;
	
	@Override
	protected BakICommonExtDao<UupmOrganizationEntity> getBindDao() {
		return uupmOrganizationDao;
	}

	@Override
	public Integer save(UupmOrganizationEntity paramPo) {
		if("root".equals(paramPo.getParentCode())) {
			paramPo.setDataPath("root." + paramPo.getOrgCode());
		} else {
			UupmOrganizationEntity param = new UupmOrganizationEntity();
			param.setOrgCode(paramPo.getParentCode());
			UupmOrganizationEntity parent = this.findOne(param);
			paramPo.setDataPath(parent.getDataPath()  + "." + paramPo.getOrgCode());
		}
		return super.save(paramPo);
	}
	
	public String findParentNamePath(UupmOrganizationEntity paramPo) {
		UupmOrganizationEntity result = this.findOne(paramPo, null);
		if(null==result) return "";
		String dataPath = result.getDataPath();
		String[] orgCodes = dataPath.split("\\");
		List<UupmOrganizationEntity> resultList = this.uupmOrganizationDao.findByOrgCodes(Arrays.asList(orgCodes));
		if(null==resultList || resultList.size()==0) return "";
		StringBuilder sb = new StringBuilder();
		for(String orgCode : orgCodes) {
			for(UupmOrganizationEntity model : resultList) {
				if(orgCode.equals(model.getOrgCode())) {
					sb.append(model.getOrgName()).append("\\");
				}
			}
		}
		return sb.length()>0 ? sb.substring(0, sb.length()-1) : sb.toString();
	}

}
