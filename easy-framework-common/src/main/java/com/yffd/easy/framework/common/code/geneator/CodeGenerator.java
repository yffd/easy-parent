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

/**
 * @Description  代码生成器.
 * @Date		 2018年2月6日 上午10:07:51 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeGenerator {
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	private String pojoSuffix = "Entity";
	private List<String> pojoPropsNameGroupList = new ArrayList<String>();
	private List<String> skipPojoList = new ArrayList<String>();
	private List<String> skipPojoPropsNameList = new ArrayList<String>(); // 非数据库属性
	
	{
		pojoPropsNameGroupList.add("version");
		pojoPropsNameGroupList.add("delFlag");
		pojoPropsNameGroupList.add("createBy");
		pojoPropsNameGroupList.add("createTime");
		pojoPropsNameGroupList.add("updateBy");
		pojoPropsNameGroupList.add("updateTime");
		
		pojoPropsNameGroupList.add("nodeTree");
		pojoPropsNameGroupList.add("nodeLayer");
		pojoPropsNameGroupList.add("nodeLeft");
		pojoPropsNameGroupList.add("nodeRight");
		pojoPropsNameGroupList.add("nodeCode");
		
		pojoPropsNameGroupList.add("tenantCode");
		pojoPropsNameGroupList.add("id");
		
		skipPojoList.add("EasyPersistModel");
		skipPojoList.add("BaseModel");
		skipPojoList.add("CommonModel");
		
		skipPojoPropsNameList.add("nodeStep");
		skipPojoPropsNameList.add("beginLayer");
		skipPojoPropsNameList.add("endLayer");
		
	}
	
	/**
	 * 属性排序
	 * @Date	2018年2月6日 上午10:09:11 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected List<String> getPojoPropsNameGroupList() {
		return pojoPropsNameGroupList;
	}
	
	/**
	 *  跳过的pojo名称，匹配模式为 like
	 * @Date	2018年2月6日 上午10:08:55 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected List<String> getSkipPojoList() {
		return skipPojoList;
	}
	
	protected List<String> getSkipPojoPropsNameList() {
		return skipPojoPropsNameList;
	}

	protected LinkedHashMap<String, Class<?>> getPropsNames(Class<?> pojoClazz) {
		try {
			LinkedHashMap<String, Class<?>> attributeNameMap = new LinkedHashMap<String, Class<?>>();
			BeanInfo beanInfo = Introspector.getBeanInfo(pojoClazz, Object.class);
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
	
	protected LinkedHashMap<String, Class<?>> sortPropsNames(Class<?> pojoClazz) {
		LinkedHashMap<String, Class<?>> propsNames = this.getPropsNames(pojoClazz);
		if(null==propsNames || propsNames.size()==0) return null;
		
		List<String> pojoPropsNameGroupList = this.getPojoPropsNameGroupList();
		LinkedHashMap<String, Class<?>> before = new LinkedHashMap<String, Class<?>>();
		for(String propsName : pojoPropsNameGroupList) {
			if(propsNames.containsKey(propsName)) {
				Class<?> value = propsNames.get(propsName);
				before.put(propsName, value);
				propsNames.remove(propsName);
			}
		}
		before.putAll(propsNames);
		return before;
	}
	
	protected boolean skipPropsName(Class<?> propsClazz, String propsName) {
		if(this.getSkipPojoPropsNameList().contains(propsName)) return true;
		if(!filterPropsName(propsClazz, propsName)) return true;
		if(propsName.matches(".*(?i)remark")) return true;
		if("nodeLeft".equals(propsName) || "nodeRight".equals(propsName)
				|| "nodeCode".equals(propsName) || "nodeLayer".equals(propsName)) return true;
		if(skipPropsNameByClazz(propsClazz)) return true;
		return false;
	}
	
	protected boolean skipPropsNameForUpdate(Class<?> propsClazz, String propsName) {
		if(this.getSkipPojoPropsNameList().contains(propsName)) return true;
		if(!filterPropsName(propsClazz, propsName)) return true;
		if("nodeLeft".equals(propsName) || "nodeRight".equals(propsName)
				|| "nodeCode".equals(propsName) || "nodeLayer".equals(propsName)) return true;
		if(skipPropsNameByClazz(propsClazz)) return true;
		return false;
	}
	
	protected boolean skipPropsNameByClazz(Class<?> clazz) {
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
	
	/**
	 * 非数据库字段
	 * @Date	2018年5月9日 下午2:18:29 <br/>
	 * @author  zhangST
	 * @param propsClazz
	 * @param propsName
	 * @return
	 */
	protected boolean filterPropsName(Class<?> propsClazz, String propsName) {
		if(this.getSkipPojoPropsNameList().contains(propsName)) return false;
		if(propsName.matches("(^_.*)|(.*_$)")) return false;
		if(propsName.matches("children")) return false;
		return true;
	}
	
	protected String fmtPojoName(Class<?> pojoClazz, String prefix, String suffix) {
		String modelName = pojoClazz.getSimpleName();
		if(modelName.lastIndexOf(pojoSuffix)!=-1)
			modelName = modelName.substring(0, modelName.lastIndexOf(pojoSuffix));
		
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
	
	protected String fmtLines(String text, String prefix, String suffix) {
		if(null==text || "".equals(text)) return null; 
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes("utf8"))));
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
	
	public static void main(String[] args) {
		String str = "qwe_";
		System.out.println(str.matches("(^_.*)|(.*_$)"));
		
		str = "qweRemArk";
		System.out.println(str.matches(".*(?i)remark"));
		str = "qweRemark";
		System.out.println(str.matches(".*((?i)r)emark"));
	}
	
}

