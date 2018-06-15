package com.yffd.easy.framework.common.code.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.yffd.easy.framework.common.service.impl.CommonServiceImpl;
import com.yffd.easy.framework.pojo.entity.CommonPartialTreeEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月9日 下午4:16:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeFileServiceGenerator extends CodeGenerator {
	private static final String FILE_SUFFIX = ".java";
	private static final String TEMPLATE_PATH = "com\\yffd\\easy\\framework\\common\\code\\template\\XxxService.template";
	private CodeFileDaoGenerator daoGenerator = new CodeFileDaoGenerator();
	
	public String fmtServiceFullName(String servicePackageName, Class<?> pojoClazz) {
		return servicePackageName + "." + this.fmtPojoName(pojoClazz, null, "Service");
	}
	
	public String fmtServiceAliasName(Class<?> pojoClazz) {
		String simpleName = this.fmtServiceSimpleName(pojoClazz);
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
	}
	
	public String fmtServiceSimpleName(Class<?> pojoClazz) {
		return this.fmtPojoName(pojoClazz, null, "Service");
	}
	
	public String fmtClassHeader(Class<?> pojoClazz, Class<?> superClazz) {
		StringBuffer sb = new StringBuffer();
		sb.append("public class").append(" ").append(fmtServiceSimpleName(pojoClazz));
		if(null!=superClazz) {
			sb.append(" ").append("extends ").append(superClazz.getSimpleName());
			sb.append("<").append(pojoClazz.getSimpleName()).append(">");
		}
		return sb.toString();
	}
	
	
	public void writeAllToFile(String pojoRootDirPath, String pojoPackageName, Class<?> superClazz, String packageName, String daoPackageName, String author, String outRootDirPath, boolean covered) throws Exception {
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
						this.writeToFile(pojoClazz, superClazz, packageName, daoPackageName, author, outRootDirPath, covered);
					}
				}
			}
		}
	}
	
	public void writeToFile(Class<?> pojoClazz, Class<?> superClazz, String packageName, String daoPackageName, String author, String outRootDirPath, boolean covered) {
		String content = this.makeContent(pojoClazz, superClazz, packageName, daoPackageName, author);
		
		if(!outRootDirPath.endsWith(File.separator)) outRootDirPath += File.separator;
		String tmp = packageName.replace(".", File.separator);
		String packageFullDirPath = outRootDirPath + tmp;
		this.makedirs(packageFullDirPath);
		
		String fileName = packageFullDirPath + File.separator + this.fmtServiceSimpleName(pojoClazz) + FILE_SUFFIX;
		this.writeToFile(content, fileName, covered);
	}
	
	public void writeToConsole(Class<?> pojoClazz, Class<?> superClazz, String packageName, String daoPackageName, String author) {
		String content = this.makeContent(pojoClazz, superClazz, packageName, daoPackageName, author);
		System.out.println(content);
	}
	
	protected String makeContent(Class<?> pojoClazz, Class<?> superClazz, String packageName, String daoPackageName, String author) {
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
		content = content.replace("##DAO_FULL_NAME##", daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz));
		content = content.replace("##DAO_SIMPLE_NAME##", daoGenerator.fmtDaoSimpleName(pojoClazz));
		content = content.replace("##DAO_ALIAS_NAME##", daoGenerator.fmtDaoAliasName(pojoClazz));
		return content;
	}
	
	public static void main(String[] args) {
		CodeFileServiceGenerator generator = new CodeFileServiceGenerator();
		String author = "ZhangST";
		Class<?> pojoClazz = CommonPartialTreeEntity.class;
		Class<?> superClazz = CommonServiceImpl.class;
		String packageName = "com.yffd.easy.xxx.service";
		String daoPackageName = "com.yffd.easy.xxx.dao";
		generator.writeToConsole(pojoClazz, superClazz, packageName, daoPackageName, author);
		
		String outRootDirPath = "D:\\ddd\\code\\dao";
		boolean covered = true;
		
		generator.writeToFile(pojoClazz, superClazz, packageName, daoPackageName, author, outRootDirPath, covered);
	}
	
}

