package com.yffd.easy.framework.web.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.ValidUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.framework.web.mvc.model.easyui.DataGridModel;
import com.yffd.easy.framework.web.mvc.model.easyui.SearchModel;

public class WebEasyuiController extends WebController {
	public static final Integer PAGE_LIMIT_DEFAUL = 30;
	
	/**
	 * 将后台分页结果转换成EasyUI分页数据格式
	 * @Date	2017年10月12日 下午6:13:01 <br/>
	 * @author  zhangST
	 * @param pageResult
	 * @return
	 */
	public DataGridModel toDataGrid(PageResult<?> pageResult) {
		if(null==pageResult) return null;
		DataGridModel vo = new DataGridModel();
		if(null!=pageResult.getRecordList())
			vo.setRows(pageResult.getRecordList());
		Long total = (long) pageResult.getPageParam().getTotalRecord();
		vo.setTotal(total);
		return vo;
	}
	
	/**
	 * 将后台集合结果转换成EasyUI分页数据格式
	 * @Date	2017年10月12日 下午6:13:01 <br/>
	 * @author  zhangST
	 * @param list
	 * @return
	 */
	public DataGridModel toDataGrid(List<?> list) {
		if(null==list) return null;
		DataGridModel vo = new DataGridModel();
		vo.setRows(list);
		Long total = (long) list.size();
		vo.setTotal(total);
		return vo;
	}
	
	/**
	 * 前端分页对象转换成后端分页对象
	 * @Date	2017年10月13日 上午10:06:32 <br/>
	 * @author  zhangST
	 * @param searchBoxVO
	 * @return
	 */
	public PageParam getPageParam(SearchModel searchBoxVO) {
		Integer pageNum = 1;
		Integer pageLimit = PAGE_LIMIT_DEFAUL;
		if(null==searchBoxVO) {
			return new PageParam(pageNum, pageLimit);
		}
		
		Integer _pageNum = searchBoxVO.getPage();
		Integer _pageLimit = searchBoxVO.getRows();
		
		if(null!=_pageNum && _pageNum>0) {
			pageNum = _pageNum;
		}
		if(null!=_pageLimit && _pageLimit>0) {
			pageLimit = _pageLimit;
		}
		return new PageParam(pageNum, pageLimit);
	}
	
	/**
	 * 前端分页对象转换成后端分页对象
	 * @Date	2017年12月12日 下午5:21:41 <br/>
	 * @author  zhangST
	 * @param paramMap
	 * @return
	 */
	public PageParam getPageParam(Map<String, Object> paramMap) {
		Integer pageNum = 1;
		Integer pageLimit = PAGE_LIMIT_DEFAUL;
		if(null==paramMap) {
			return new PageParam(pageNum, pageLimit);
		}
		
		String pageNumStr = (String) paramMap.get("page");
		String pageLimitStr = (String) paramMap.get("rows");
		
		Integer _pageNum = ValidUtils.isBlank(pageNumStr)?pageNum:Integer.parseInt(pageNumStr);
		Integer _pageLimit = ValidUtils.isBlank(pageLimitStr)?pageLimit:Integer.parseInt(pageLimitStr);
		
		if(null!=_pageNum && _pageNum>0) {
			pageNum = _pageNum;
		}
		if(null!=_pageLimit && _pageLimit>0) {
			pageLimit = _pageLimit;
		}
		return new PageParam(pageNum, pageLimit);
	}
	
	public <T> T getModelParam(Map<String, Object> paramMap, Class<T> clazz) {
		try {
			T model = clazz.newInstance();
			BeanUtils.copyProperties(model, paramMap);
			return model;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new EasyCommonException("反射复制属性值失败["+paramMap.getClass()+" -> "+clazz.getName()+"]", e);
		}
//		return JSON.parseObject(JSON.toJSONString(paramMap), clazz);
	}
	
}
