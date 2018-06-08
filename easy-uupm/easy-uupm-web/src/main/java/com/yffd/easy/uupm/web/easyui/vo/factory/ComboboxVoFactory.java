package com.yffd.easy.uupm.web.easyui.vo.factory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.uupm.pojo.enums.UupmAccountTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmAppSystemTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmStatusStyleEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantServeTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantStatusEnum;
import com.yffd.easy.uupm.pojo.enums.UupmTenantTypeEnum;
import com.yffd.easy.uupm.web.easyui.vo.ComboboxVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:45:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ComboboxVoFactory {

	public enum Style { STATUS_STYLE, ACCOUNT_TYPE, APP_SYS_TYPE, TT_TYPE, TT_STATUS, TT_SERVE_TYPE, }
	
	public String switchJson(Style flag) {
		String jsonData = "{}";
		switch (flag) {
		case STATUS_STYLE : jsonData = this.getStatusStyleJson(); break;
		case ACCOUNT_TYPE : jsonData = this.getAccountTypeJson(); break;
		case APP_SYS_TYPE : jsonData = this.getAppSystemTypeJson(); break;
		case TT_TYPE : jsonData = this.getTenantTypeJson(); break;
		case TT_STATUS : jsonData = this.getTenantStatusJson(); break;
		case TT_SERVE_TYPE : jsonData = this.getTenantServeTypeJson(); break;
		default : break;
		}
		return jsonData;
	}
	
	// 状态
	public String getStatusStyleJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmStatusStyleEnum> enumSet = EnumSet.allOf(UupmStatusStyleEnum.class);
		for (UupmStatusStyleEnum tmp : enumSet) {
			ComboboxVo vo = new ComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return JSON.toJSONString(list);
	}
	
	// 账号类型
	public String getAccountTypeJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmAccountTypeEnum> enumSet = EnumSet.allOf(UupmAccountTypeEnum.class);
		for (UupmAccountTypeEnum tmp : enumSet) {
			ComboboxVo vo = new ComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return JSON.toJSONString(list);
	}
	
	// 应用系统类型
	public String getAppSystemTypeJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmAppSystemTypeEnum> enumSet = EnumSet.allOf(UupmAppSystemTypeEnum.class);
        for (UupmAppSystemTypeEnum tmp : enumSet) {
        	ComboboxVo vo = new ComboboxVo();
        	vo.setLabel(tmp.getDesc());
        	vo.setValue(tmp.getCode());
        	list.add(vo);
        }
        return JSON.toJSONString(list);
	}
	
	// 租户类型
	public String getTenantTypeJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantTypeEnum> enumSet = EnumSet.allOf(UupmTenantTypeEnum.class);
		for (UupmTenantTypeEnum tmp : enumSet) {
			ComboboxVo vo = new ComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return JSON.toJSONString(list);
	}
	
	// 租户状态
	public String getTenantStatusJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantStatusEnum> enumSet = EnumSet.allOf(UupmTenantStatusEnum.class);
		for (UupmTenantStatusEnum tmp : enumSet) {
			ComboboxVo vo = new ComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return JSON.toJSONString(list);
	}
	
	// 租户服务类型
	public String getTenantServeTypeJson() {
		List<ComboboxVo> list = new ArrayList<>();
		EnumSet<UupmTenantServeTypeEnum> enumSet = EnumSet.allOf(UupmTenantServeTypeEnum.class);
		for (UupmTenantServeTypeEnum tmp : enumSet) {
			ComboboxVo vo = new ComboboxVo();
			vo.setLabel(tmp.getDesc());
			vo.setValue(tmp.getCode());
			list.add(vo);
		}
		return JSON.toJSONString(list);
	}
}

