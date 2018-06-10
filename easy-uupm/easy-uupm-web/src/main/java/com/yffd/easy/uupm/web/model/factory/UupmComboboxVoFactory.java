package com.yffd.easy.uupm.web.model.factory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yffd.easy.uupm.pojo.enums.UupmAccountTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmAppSystemTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmResourceTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmStatusStyleEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantServeTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantServeStatusEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantTypeEnum;
import com.yffd.easy.uupm.web.model.UupmComboboxVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:45:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmComboboxVoFactory {

//	public enum Style { STATUS_STYLE, ACCOUNT_TYPE, APP_SYS_TYPE, TT_TYPE, TT_STATUS, TT_SERVE_TYPE, }
	
	public Map<String, List<UupmComboboxVo>> getComboboxData(String keyCodes) {
		Map<String, List<UupmComboboxVo>> map = new HashMap<>();
		String[] array = keyCodes.split(",");
		for (String keyCode : array) {
			List<UupmComboboxVo> voList = this.switchVo(keyCode);
			map.put(keyCode, voList);
		}
		return map;
	}
	
	public List<UupmComboboxVo> switchVo(String keyCode) {
		List<UupmComboboxVo> voList = null;
		switch (keyCode) {
		case "statusStyle" : voList = this.getStatusStyle(); break;
		case "accountType" : voList = this.getAccountType(); break;
		case "appSysType" : voList = this.getAppSystemType(); break;
		case "ttType" : voList = this.getTenantType(); break;
		case "ttServeStatus" : voList = this.getTenantServeStatus(); break;
		case "ttServeType" : voList = this.getTenantServeType(); break;
		case "rsType" : voList = this.getResourceType(); break;
		
		default : break;
		}
		return voList;
	}
	
	// 状态
	public List<UupmComboboxVo> getStatusStyle() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmStatusStyleEnum> enumSet = EnumSet.allOf(UupmStatusStyleEnum.class);
		for (UupmStatusStyleEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 账号类型
	public List<UupmComboboxVo> getAccountType() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmAccountTypeEnum> enumSet = EnumSet.allOf(UupmAccountTypeEnum.class);
		for (UupmAccountTypeEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 应用系统类型
	public List<UupmComboboxVo> getAppSystemType() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmAppSystemTypeEnum> enumSet = EnumSet.allOf(UupmAppSystemTypeEnum.class);
        for (UupmAppSystemTypeEnum tmp : enumSet) {
        	UupmComboboxVo vo = new UupmComboboxVo();
        	vo.setLabel(tmp.getDesc());
        	vo.setValue(tmp.getCode());
        	list.add(vo);
        }
        return list;
	}
	
	// 租户类型
	public List<UupmComboboxVo> getTenantType() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantTypeEnum> enumSet = EnumSet.allOf(UupmTenantTypeEnum.class);
		for (UupmTenantTypeEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 租户状态
	public List<UupmComboboxVo> getTenantServeStatus() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantServeStatusEnum> enumSet = EnumSet.allOf(UupmTenantServeStatusEnum.class);
		for (UupmTenantServeStatusEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 租户服务类型
	public List<UupmComboboxVo> getTenantServeType() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantServeTypeEnum> enumSet = EnumSet.allOf(UupmTenantServeTypeEnum.class);
		for (UupmTenantServeTypeEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
	
	// 资源类型
	public List<UupmComboboxVo> getResourceType() {
		List<UupmComboboxVo> list = new ArrayList<>();
		EnumSet<UupmResourceTypeEnum> enumSet = EnumSet.allOf(UupmResourceTypeEnum.class);
		for (UupmResourceTypeEnum tmp : enumSet) {
			UupmComboboxVo vo = new UupmComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return list;
	}
}

