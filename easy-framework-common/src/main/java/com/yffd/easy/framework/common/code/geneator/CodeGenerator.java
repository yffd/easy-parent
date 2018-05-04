package com.yffd.easy.framework.common.code.geneator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.yffd.easy.common.core.converter.EasyModelConverter;

/**
 * @Description  代码生成器.
 * @Date		 2018年2月6日 上午10:07:51 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeGenerator extends EasyModelConverter {
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	private String modelSuffix = "Entity";
	private List<String> sortedAttributeNameList = new ArrayList<String>();
	private List<String> skipModelNamesLike = new ArrayList<String>();
	
	{
		sortedAttributeNameList.add("version");
		sortedAttributeNameList.add("delFlag");
		sortedAttributeNameList.add("createBy");
		sortedAttributeNameList.add("createTime");
		sortedAttributeNameList.add("updateBy");
		sortedAttributeNameList.add("updateTime");
		
		sortedAttributeNameList.add("treeId");
		sortedAttributeNameList.add("nodeLayer");
		sortedAttributeNameList.add("nodeLeft");
		sortedAttributeNameList.add("nodeRight");
		sortedAttributeNameList.add("nodeCode");
		
		sortedAttributeNameList.add("tenantCode");
		sortedAttributeNameList.add("id");
		
		skipModelNamesLike.add("EasyPersistModel");
		skipModelNamesLike.add("BaseModel");
	}
	
	/**
	 * 属性排序
	 * @Date	2018年2月6日 上午10:09:11 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected List<String> getSortedAttributeNameList() {
		return sortedAttributeNameList;
	}
	
	/**
	 *  跳过的model名称，匹配模式为 like
	 * @Date	2018年2月6日 上午10:08:55 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected List<String> getSkipModelNamesLike() {
		return skipModelNamesLike;
	}
	
	/**
	 * 格式化Mapper的名称
	 * @Date	2018年2月6日 上午10:51:13 <br/>
	 * @author  zhangST
	 * @param modelClazz
	 * @return
	 */
	protected String fmtMapperName(Class<?> modelClazz) {
		return this.fmtModelName(modelClazz, null, "Mapper");
	}
	
	/**
	 * 格式化Mapper类名称，简短名称，非完整类名
	 * @Date	2018年2月6日 上午11:05:08 <br/>
	 * @author  zhangST
	 * @param modelClazz
	 * @return
	 */
	protected String fmtMapperSimpleName(Class<?> modelClazz) {
		return "I" + this.fmtModelName(modelClazz, null, "Mapper");
	}
	
	/**
	 * 格式化model的名称，简短名称，非完整类名
	 * @Date	2018年2月6日 上午10:33:35 <br/>
	 * @author  zhangST
	 * @param modelClazz
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	protected String fmtModelName(Class<?> modelClazz, String prefix, String suffix) {
		String modelName = modelClazz.getSimpleName();
		if(modelName.lastIndexOf(modelSuffix)!=-1)
			modelName = modelName.substring(0, modelName.lastIndexOf(modelSuffix));
		
		StringBuilder sb = new StringBuilder();
		if(null!=prefix && !"".equals(prefix.trim())) {
			sb.append(prefix);
		}
		sb.append(modelName);
		if(null!=suffix && !"".equals(suffix.trim())) {
			sb.append(suffix);
		}
		return sb.toString();
	}
	
	protected String fmtDate(Date date) {
		return DATE_FMT.format(new Date());
	}
	
	protected String fmtLine(String lines, String prefix, String suffix) {
		if(null==lines || "".equals(lines)) return null; 
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(lines.getBytes("utf8"))));
			String line;
			while((line = reader.readLine()) !=null) {
				sb.append(prefix).append(line).append("\r\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建多级目录
	 * @Date	2018年2月6日 上午10:34:03 <br/>
	 * @author  zhangST
	 * @param dirPath
	 */
	protected void makedirs(String dirPath) {
		File file = new File(dirPath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 
	 * @Date	2018年2月6日 下午2:27:51 <br/>
	 * @author  zhangST
	 * @param content
	 * @param outRootDirPath
	 * @param covered			是否覆盖旧文件
	 */
	protected void writeToFile(String content, String outRootDirPath, boolean covered) {
		try {
			if(new File(outRootDirPath).exists()) {
				if(covered) {
					System.out.println("覆盖文件：" + outRootDirPath + "， 原因：文件已存在！");
				} else {
					System.out.println("跳过文件：" + outRootDirPath + "， 原因：文件已存在！");
					return;
				}
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outRootDirPath))));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes("utf8"))));
			String line = null;
			while((line = reader.readLine()) != null) {
				writer.write(line + "\r\n");
			}
			writer.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected LinkedHashMap<String, Class<?>> getAttributeNames(Class<?> modelClazz) {
		try {
			LinkedHashMap<String, Class<?>> attributeNameMap = new LinkedHashMap<String, Class<?>>();
			BeanInfo beanInfo = Introspector.getBeanInfo(modelClazz, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				Class<?> typeClazz = pd.getPropertyType();
				String name = pd.getName();
				if(null==name || "".equals(name)) continue;
				attributeNameMap.put(name, typeClazz);
			}
			return attributeNameMap;
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected LinkedHashMap<String, Class<?>> sortAttributeNames(Class<?> modelClazz) {
		LinkedHashMap<String, Class<?>> attributeNameMap = this.getAttributeNames(modelClazz);
		if(null==attributeNameMap || attributeNameMap.size()==0) return null;
		
		List<String> sortedPropsList = this.getSortedAttributeNameList();
		LinkedHashMap<String, Class<?>> before = new LinkedHashMap<String, Class<?>>();
		for(String prop : sortedPropsList) {
			if(attributeNameMap.containsKey(prop)) {
				Class<?> value = attributeNameMap.get(prop);
				before.put(prop, value);
				attributeNameMap.remove(prop);
			}
		}
		before.putAll(attributeNameMap);
		return before;
	}
	
	protected boolean skipClazz(Class<?> clazz) {
//		if(
//				String.class.getName().equals(clazz.getName())
//				|| Date.class.getName().equals(clazz.getName())
//				|| BigDecimal.class.getName().equals(clazz.getName())
//				|| Byte.class.getName().equals(clazz.getName()) || "byte".equals(clazz.getName())
//				|| Character.class.getName().equals(clazz.getName()) || "char".equals(clazz.getName())
//				|| Short.class.getName().equals(clazz.getName()) || "short".equals(clazz.getName())
//				|| Integer.class.getName().equals(clazz.getName()) || "int".equals(clazz.getName())
//				|| Long.class.getName().equals(clazz.getName()) || "long".equals(clazz.getName())
//				|| Float.class.getName().equals(clazz.getName()) || "float".equals(clazz.getName())
//				|| Double.class.getName().equals(clazz.getName()) || "double".equals(clazz.getName())	
//				) return false;
		if(
				clazz.isPrimitive()
				|| String.class.getName().equals(clazz.getName())
				|| Date.class.getName().equals(clazz.getName())
				|| BigDecimal.class.getName().equals(clazz.getName())
				|| Byte.class.getName().equals(clazz.getName())
				|| Character.class.getName().equals(clazz.getName())
				|| Short.class.getName().equals(clazz.getName())
				|| Integer.class.getName().equals(clazz.getName())
				|| Long.class.getName().equals(clazz.getName())
				|| Float.class.getName().equals(clazz.getName())
				|| Double.class.getName().equals(clazz.getName())
				) return false;
		return true;
	}
	
	protected boolean skipAttributeName(Class<?> attributeClazz, String attributeName) {
		if(!filterArributeName(attributeClazz, attributeName)) return true;
		if(attributeName.matches(".*(?i)remark")) return true;
		if("nodeLeft".equals(attributeName) || "nodeRight".equals(attributeName)) return true;
		if(skipClazz(attributeClazz)) return true;
		return false;
	}
	
	/**
	 * 非数据库字段
	 * @Date	2018年4月13日 下午2:53:07 <br/>
	 * @author  zhangST
	 * @param attributeClazz
	 * @param attributeName
	 * @return
	 */
	protected boolean filterArributeName(Class<?> attributeClazz, String attributeName) {
		if(attributeName.matches("(^_.*)|(.*_$)")) return false;
		if(attributeName.matches("children")) return false;
		return true;
	}
	
	public static void main(String[] args) {
		String str = "qwe_";
		System.out.println(str.matches("(^_.*)|(.*_$)"));
		
		str = "qweRemArk";
		System.out.println(str.matches(".*(?i)remark"));
		str = "qweRemark";
		System.out.println(str.matches(".*((?i)r)emark"));
	}
	
}

