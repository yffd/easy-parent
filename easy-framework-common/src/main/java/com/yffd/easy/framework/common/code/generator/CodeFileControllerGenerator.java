package com.yffd.easy.framework.common.code.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.yffd.easy.common.core.util.EasyNamingFormatUtils;

/**
 * @Description  XxxController代码生成器.
 * @Date		 2018年2月6日 下午1:55:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeFileControllerGenerator extends CodeGenerator {
	private static final String FILE_SUFFIX = ".java";
	private static final String TEMPLATE_PATH = "com\\yffd\\easy\\framework\\common\\code\\template\\XxxController.template";
	private CodeFileServiceGenerator serviceGenerator = new CodeFileServiceGenerator();
	
	public void writeAllToFile(String pojoRootDirPath, String pojoPackageName, Class<?> superClazz, String packageName, String servicePackageName, String author, String outRootDirPath, boolean covered) throws Exception {
		if(!pojoRootDirPath.endsWith(File.separator)) pojoRootDirPath += File.separator;
		String tmp = pojoPackageName.replace(".", File.separator);
		String pojoPackageFullDirPath = pojoRootDirPath + tmp;
		
		File file = new File(pojoPackageFullDirPath);
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File childFile : files) {
				if(childFile.isDirectory()) {
					continue;
				} else {
					boolean skip = false;
					String pojoFileName = childFile.getName();	// 不包含路径
					for(String skipName : this.getSkipPojoList()) {
						if(pojoFileName.contains(skipName)) {
							skip = true;
							continue;
						}
					}
					if(!skip) {
						String pojoFullClassName = pojoPackageName + "." + pojoFileName.substring(0, pojoFileName.lastIndexOf(FILE_SUFFIX));
						Class<?> pojoClazz = Class.forName(pojoFullClassName);
						this.writeToFile(pojoClazz, superClazz, packageName, servicePackageName, author, outRootDirPath, covered);
					}
				}
			}
		}
	}
	
	public void writeToFile(Class<?> pojoClazz, Class<?> superClazz, String packageName, String servicePackageName, String author, String outRootDirPath, boolean covered) {
		String content = this.makeContent(pojoClazz, superClazz, packageName, servicePackageName, author);
		
		if(!outRootDirPath.endsWith(File.separator)) outRootDirPath += File.separator;
		String tmp = packageName.replace(".", File.separator);
		String packageFullDirPath = outRootDirPath + tmp;
		this.makedirs(packageFullDirPath);
		
		String fileName = packageFullDirPath + File.separator + this.fmtControllerSimpleName(pojoClazz) + FILE_SUFFIX;
		this.writeToFile(content, fileName, covered);
	}
	
	public void writeToConsole(Class<?> pojoClazz, Class<?> superClazz, String packageName, String servicePackageName, String author) {
		String content = this.makeContent(pojoClazz, superClazz, packageName, servicePackageName, author);
		System.out.println(content);
	}
	
	protected String makeContent(Class<?> pojoClazz, Class<?> superClazz, String packageName, String servicePackageName, String author) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_PATH);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			String line = null;
			while((line=reader.readLine())!=null) {
				sb.append(line).append("\r\n");
			}
			reader.close();
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String content = sb.toString();
		
		content = content.replace("##DATE##", this.fmtDate(new Date()));
		content = content.replace("##AUTHOR##", author);
		content = content.replace("##PACKAGE_NAME##", packageName);
		content = content.replace("##POJO_FULL_NAME##", pojoClazz.getName());
		String classNameLine = this.fmtClassHeader(pojoClazz, superClazz);
		content = content.replace("##CLASS_HEAD##", classNameLine);
		if(null==superClazz) {
			content = content.replace("##IMPORT_SUPER_CLASS_FULL_NAME##", "");
		} else {
			content = content.replace("##IMPORT_SUPER_CLASS_FULL_NAME##", "\r\nimport " + superClazz.getName() + ";");
		}
		
		content = content.replace("##POJO_SIMPLE_NAME##", pojoClazz.getSimpleName());
		content = content.replace("##SERVICE_FULL_NAME##", serviceGenerator.fmtServiceFullName(servicePackageName, pojoClazz));
		content = content.replace("##SERVICE_SIMPLE_NAME##", serviceGenerator.fmtServiceSimpleName(pojoClazz));
		content = content.replace("##SERVICE_ALIAS_NAME##", serviceGenerator.fmtServiceAliasName(pojoClazz));
		
		content = content.replace("##REQUEST_MAPPING##", this.fmtRequestMapping(pojoClazz));
		return content;
	}
	
	public String fmtRequestMapping(Class<?> pojoClazz) {
		String tmp = this.fmtPojoName(pojoClazz, null, null);
		String underlineStr = EasyNamingFormatUtils.camel2underline(tmp, false, null, null);
		String ret = "/" + underlineStr.replace("_", "/");
		return ret;
	}
	
	public String fmtControllerSimpleName(Class<?> pojoClazz) {
		return this.fmtPojoName(pojoClazz, null, "Controller");
	}
	
	public String fmtClassHeader(Class<?> pojoClazz, Class<?> superClazz) {
		StringBuffer sb = new StringBuffer();
		sb.append("public class").append(" ").append(fmtControllerSimpleName(pojoClazz));
		if(null!=superClazz) {
			sb.append(" ").append("extends ").append(superClazz.getSimpleName());
//			sb.append("<").append(pojoClazz.getSimpleName()).append(">");
		}
		return sb.toString();
	}
	
}

