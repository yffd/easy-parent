package com.yffd.easy.uupm.web.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yffd.easy.framework.web.view.tree.TreeBuilder;
import com.yffd.easy.uupm.web.vo.UupmMenuTreeVO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月28日 下午5:03:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmMenuSupport {
	private TreeBuilder treeBuilder = new TreeBuilder();
	
	/**
	 * 同步tree数据转换
	 * @Date	2018年2月28日 下午5:12:45 <br/>
	 * @author  zhangST
	 * @param list
	 * @return
	 */
	public List<UupmMenuTreeVO> toSyncTreeVO(List<Map<String, Object>> list, String rootId) {
		if(null==list || list.isEmpty()) return null;
		List<UupmMenuTreeVO> voList = new ArrayList<UupmMenuTreeVO>();
		for(Map<String, Object> map : list) {
			UupmMenuTreeVO vo = new UupmMenuTreeVO();
			vo.setId_((String) map.get("menuCode"));		// treeNode:设置父子关系
			vo.setPid_((String) map.get("parentCode"));		// treeNode:设置父子关系
			vo.setText((String) map.get("menuName"));		// treeNode:设置文本
			vo.setState("open");
			vo.setId((long) map.get("id") + "");
			vo.setMenuCode((String) map.get("menuCode"));
			vo.setParentCode((String) map.get("parentCode"));
			vo.setMenuName((String) map.get("menuName"));
			vo.setMenuIcons((String) map.get("menuIcons"));
			
			String shortMenuUrl = (String) map.get("menuUrl");
//			String appDomain = (String) map.get("appDomain");
//			String appPort = (String) map.get("appPort");
//			String appContextPath = (String) map.get("appContextPath");
//			appDomain = null==appDomain?"":appDomain;
//			appPort = null==appPort?"":appPort;
//			appContextPath = null==appContextPath?"":appContextPath;
//			String menuUrl = "http://" + appDomain + ":" + appPort + "/" + appContextPath + "/" + shortMenuUrl;
//			vo.setMenuUrl(menuUrl);
			vo.setMenuUrl(shortMenuUrl);
			voList.add(vo);
		}
		List<UupmMenuTreeVO> treeList = (List<UupmMenuTreeVO>) treeBuilder.buildMulti(voList, rootId);
		return treeList;
	}

}

