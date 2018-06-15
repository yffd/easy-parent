package com.yffd.easy.uumc.web.model.factory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yffd.easy.framework.web.mvc.model.easyui.ComboboxModel;
import com.yffd.easy.uumc.pojo.enums.UumcAccountTypeEnum;
import com.yffd.easy.uumc.pojo.enums.UumcAppSystemTypeEnum;
import com.yffd.easy.uumc.pojo.enums.UumcResourceTypeEnum;
import com.yffd.easy.uumc.pojo.enums.UumcStatusEnum;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:45:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcComboboxModelFactory {

//	public enum Style { STATUS_STYLE, ACCOUNT_TYPE, APP_SYS_TYPE, TT_TYPE, TT_STATUS, TT_SERVE_TYPE, }
	
	public Map<String, List<ComboboxModel>> getComboboxData(String keyCodes) {
		Map<String, List<ComboboxModel>> map = new HashMap<>();
		String[] array = keyCodes.split(",");
		for (String keyCode : array) {
			List<ComboboxModel> modelList = this.switchModel(keyCode);
			map.put(keyCode, modelList);
		}
		return map;
	}
	
	public List<ComboboxModel> switchModel(String keyCode) {
		List<ComboboxModel> modelList = null;
		switch (keyCode) {
		case "status" : modelList = this.getStatus(); break;
		case "acntType" : modelList = this.getAcntType(); break;
		case "appType" : modelList = this.getAppType(); break;
		case "rsType" : modelList = this.getResourceType(); break;
//		case "ttType" : modelList = this.getTenantType(); break;
//		case "ttServeStatus" : modelList = this.getTenantServeStatus(); break;
//		case "ttServeType" : modelList = this.getTenantServeType(); break;
		
		default : break;
		}
		return modelList;
	}
	
	// 状态
	public List<ComboboxModel> getStatus() {
		List<ComboboxModel> list = new ArrayList<>();
		EnumSet<UumcStatusEnum> enumSet = EnumSet.allOf(UumcStatusEnum.class);
		for (UumcStatusEnum tmp : enumSet) {
			ComboboxModel vo = new ComboboxModel();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 账号类型
	public List<ComboboxModel> getAcntType() {
		List<ComboboxModel> list = new ArrayList<>();
		EnumSet<UumcAccountTypeEnum> enumSet = EnumSet.allOf(UumcAccountTypeEnum.class);
		for (UumcAccountTypeEnum tmp : enumSet) {
			ComboboxModel vo = new ComboboxModel();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 应用系统类型
	public List<ComboboxModel> getAppType() {
		List<ComboboxModel> list = new ArrayList<>();
		EnumSet<UumcAppSystemTypeEnum> enumSet = EnumSet.allOf(UumcAppSystemTypeEnum.class);
        for (UumcAppSystemTypeEnum tmp : enumSet) {
        	ComboboxModel vo = new ComboboxModel();
        	vo.setLabel(tmp.getDesc());
        	vo.setValue(tmp.getCode());
        	list.add(vo);
        }
        return list;
	}
	
	// 资源类型
	public List<ComboboxModel> getResourceType() {
		List<ComboboxModel> list = new ArrayList<>();
		EnumSet<UumcResourceTypeEnum> enumSet = EnumSet.allOf(UumcResourceTypeEnum.class);
		for (UumcResourceTypeEnum tmp : enumSet) {
			ComboboxModel vo = new ComboboxModel();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 租户类型
//		public List<ComboboxModel> getTenantType() {
//			List<ComboboxModel> list = new ArrayList<>();
//			EnumSet<UumcTenantTypeEnum> enumSet = EnumSet.allOf(UumcTenantTypeEnum.class);
//			for (UumcTenantTypeEnum tmp : enumSet) {
//				ComboboxModel vo = new ComboboxModel();
//				vo.setLabel(tmp.getDesc());
//				vo.setValue(tmp.getCode());
//				list.add(vo);
//			}
//			return list;
//		}
//		
//		// 租户状态
//		public List<ComboboxModel> getTenantServeStatus() {
//			List<ComboboxModel> list = new ArrayList<>();
//			EnumSet<UumcTenantServeStatusEnum> enumSet = EnumSet.allOf(UumcTenantServeStatusEnum.class);
//			for (UumcTenantServeStatusEnum tmp : enumSet) {
//				ComboboxModel vo = new ComboboxModel();
//				vo.setLabel(tmp.getDesc());
//				vo.setValue(tmp.getCode());
//				list.add(vo);
//			}
//			return list;
//		}
//		
//		// 租户服务类型
//		public List<ComboboxModel> getTenantServeType() {
//			List<ComboboxModel> list = new ArrayList<>();
//			EnumSet<UumcTenantServeTypeEnum> enumSet = EnumSet.allOf(UumcTenantServeTypeEnum.class);
//			for (UumcTenantServeTypeEnum tmp : enumSet) {
//				ComboboxModel vo = new ComboboxModel();
//				vo.setLabel(tmp.getDesc());
//				vo.setValue(tmp.getCode());
//				list.add(vo);
//			}
//			return list;
//		}
}

