package com.yffd.easy.uupm.service.a;

import java.util.List;

import org.springframework.stereotype.Service;

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
@Service
public class UupmMenuService extends UupmBaseService<UupmMenuEntity> {

	public List<UupmMenuInfoVo> findMenuList(String ttCode, String parentCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTtCode(ttCode);
		menuInfoVo.setParentCode(parentCode);
		return this.findMenuInfoList(menuInfoVo);
	}
	
//	public int addMenuForAdmin(String ttCode) {
//		List<UupmMenuInfoVo> list = this.findMenuInfoForAdmin(ttCode);
//		if(null==list || list.size()==0) return 0;
//		List<UupmMenuEntity> modelList = new ArrayList<UupmMenuEntity>();
//		for(UupmMenuInfoVo vo : list) {
//			try {
//				UupmMenuEntity model = EasyJavaBeanUtils.copyProperties(vo, UupmMenuEntity.class);
//				model.setTenantCode(ttCode);
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
	
//	public int addMenuForOther(String ttCode) {
//		List<UupmMenuInfoVo> list = this.findMenuInfoForDefault(ttCode);
//		if(null==list || list.size()==0) return 0;
//		List<UupmMenuEntity> modelList = new ArrayList<UupmMenuEntity>();
//		for(UupmMenuInfoVo vo : list) {
//			try {
//				UupmMenuEntity model = EasyJavaBeanUtils.copyProperties(vo, UupmMenuEntity.class);
//				model.setTenantCode(ttCode);
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
	
	public List<UupmMenuInfoVo> findMenuInfoForAdmin(String ttCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTtCode(ttCode);
		return this.findMenuInfoForAdminList(menuInfoVo);
	}
	
	public List<UupmMenuInfoVo> findMenuInfoForDefault(String ttCode) {
		UupmMenuInfoVo menuInfoVo = new UupmMenuInfoVo();
		menuInfoVo.setTtCode(ttCode);
		return this.findMenuInfoForDefaultList(menuInfoVo);
	}
	
	
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
