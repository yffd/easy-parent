package com.yffd.easy.uupm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.uupm.dao.UupmMenuDao;
import com.yffd.easy.uupm.entity.UupmMenuEntity;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月24日 15时15分58秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmMenuService extends UupmBaseService<UupmMenuEntity> {

	@Autowired
	private UupmMenuDao uupmMenuDao;

	@Override
	protected IMybatisCommonDao<UupmMenuEntity> getBindDao() {
		return uupmMenuDao;
	}
	
	public List<UupmMenuInfoVo> findMenuList(String tenantCode, String parentCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTenantCode(tenantCode);
		menuInfoVo.setParentCode(parentCode);
		return this.uupmMenuDao.findMenuInfoList(menuInfoVo);
	}
	
//	public int addMenuForAdmin(String tenantCode) {
//		List<UupmMenuInfoVo> list = this.findMenuInfoForAdmin(tenantCode);
//		if(null==list || list.size()==0) return 0;
//		List<UupmMenuEntity> modelList = new ArrayList<UupmMenuEntity>();
//		for(UupmMenuInfoVo vo : list) {
//			try {
//				UupmMenuEntity model = EasyJavaBeanUtils.copyProperties(vo, UupmMenuEntity.class);
//				model.setTenantCode(tenantCode);
////			String nodeLabel = (String) map.get("nodeLabel");
////			String parentCode = (String) map.get("parentCode");
////			if(nodeLabel.equals(parentCode)) {
////				model.setParentCode("root");
////			} else {
////				model.setParentCode(parentCode);
////			}
//				modelList.add(model);
//			} catch (EasyCommonException | ReflectiveOperationException e) {
//				e.printStackTrace();
//			}
//		}
//		if(modelList.size()==0) return 0;
//		return this.save(modelList);
//	}
	
//	public int addMenuForOther(String tenantCode) {
//		List<UupmMenuInfoVo> list = this.findMenuInfoForDefault(tenantCode);
//		if(null==list || list.size()==0) return 0;
//		List<UupmMenuEntity> modelList = new ArrayList<UupmMenuEntity>();
//		for(UupmMenuInfoVo vo : list) {
//			try {
//				UupmMenuEntity model = EasyJavaBeanUtils.copyProperties(vo, UupmMenuEntity.class);
//				model.setTenantCode(tenantCode);
////			String nodeLabel = (String) map.get("nodeLabel");
////			String parentCode = (String) map.get("parentCode");
////			if(nodeLabel.equals(parentCode)) {
////				model.setParentCode("root");
////			} else {
////				model.setParentCode(parentCode);
////			}
//				modelList.add(model);
//			} catch (EasyCommonException | ReflectiveOperationException e) {
//				e.printStackTrace();
//			}
//		}
//		if(modelList.size()==0) return 0;
//		return this.save(modelList);
//	}
	
	public List<UupmMenuInfoVo> findMenuInfoForAdmin(String tenantCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTenantCode(tenantCode);
		return this.uupmMenuDao.findMenuInfoForAdminList(menuInfoVo);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForDefault(String tenantCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTenantCode(tenantCode);
		return this.uupmMenuDao.findMenuInfoForDefaultList(menuInfoVo);
	}
	
}
