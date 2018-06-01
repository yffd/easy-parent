package com.yffd.easy.framework.common.code.generator;

import java.io.File;

import com.yffd.easy.framework.common.persist.entity.CommonPartialTreeEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月6日 下午3:16:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeFileMapperXmlGenerator extends CodeGenerator {
	
	private static final String FILE_SUFFIX = ".xml";
	private CodeMapperSqlGenerator mapperSqlGenerator = new CodeMapperSqlGenerator();
	
	public String fmtMapperXmlFileName(Class<?> pojoClazz) {
		return this.fmtPojoName(pojoClazz, null, "Mapper");
	}
	
	public String fmtMapperInterfaceName(Class<?> pojoClazz) {
		return "I" + this.fmtPojoName(pojoClazz, null, "Mapper");
	}
	
	public void writeAllToFile(String pojoRootDirPath, String pojoPackageName, String sqlNamespace, String outRootDirPath, boolean covered) throws Exception {
		if(!pojoRootDirPath.endsWith(File.separator)) pojoRootDirPath += File.separator;
		String modelPackageDirPath = pojoPackageName.replace(".", File.separator);
		String fullDirPath = pojoRootDirPath + modelPackageDirPath;
		
		File file = new File(fullDirPath);
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File childFile : files) {
				if(childFile.isDirectory()) {
					continue;
				} else {
					boolean skip = false;
					String javaFileName = childFile.getName();	// 不包含路径
					for(String skipName : this.getSkipPojoList()) {
						if(javaFileName.contains(skipName)) {
							skip = true;
							continue;
						}
					}
					if(!skip) {
						int endIndex = javaFileName.lastIndexOf(".java");
						if(endIndex!=-1) {
							javaFileName = javaFileName.substring(0, endIndex);
						}
						String modelFullClassName = pojoPackageName + "." + javaFileName;
						Class<?> pojoClazz = Class.forName(modelFullClassName);
						this.writeToFile(pojoClazz, sqlNamespace, outRootDirPath, covered);
					}
				}
			}
		}
	}
	
	public void writeToFile(Class<?> pojoClazz, String sqlNamespace, String outRootDirPath, boolean covered) {
		String tableAliasName = "t";
		String pojoAliasName = "entity";
		String oldAliasName = "entityOld";
		String mapAliasName = "propsMap";
		String content = this.makeContent(pojoClazz, tableAliasName, sqlNamespace, pojoAliasName, oldAliasName, mapAliasName);
		
		String fileName = outRootDirPath + File.separator + this.fmtMapperXmlFileName(pojoClazz) + FILE_SUFFIX;
		this.makedirs(outRootDirPath);
		this.writeToFile(content, fileName, covered);
		
	}
	
	public void writeToConsole(Class<?> pojoClazz, String sqlNamespace) {
		String tableAliasName = "t";
		String pojoAliasName = "entity";
		String oldAliasName = "entityOld";
		String mapAliasName = "propsMap";
		String content = this.makeContent(pojoClazz, tableAliasName, sqlNamespace, pojoAliasName, oldAliasName, mapAliasName);
		System.out.println(content);
		
	}
	
	protected String makeContent(Class<?> pojoClazz, String tableAliasName, String sqlNamespace, String modelAliasName, String oldAliasName, String mapAliasName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\r\n");
		sb.append("<mapper namespace=\"" + sqlNamespace + "\">").append("\r\n");
		
		sb.append("\r\n").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("<!-- #########################   common sql begin    ######################### -->").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("\r\n");
		
		String tableNameStr = this.mapperSqlGenerator.tableName(pojoClazz);
		sb.append(this.fmtLines(tableNameStr, "\t", ""));
		
		String tableColumnsStr = this.mapperSqlGenerator.tableColumns(pojoClazz, tableAliasName);
		sb.append(this.fmtLines(tableColumnsStr, "\t", ""));
		
		String conditionsLimitStr = this.mapperSqlGenerator.conditionsLimit();
		sb.append(this.fmtLines(conditionsLimitStr, "\t", ""));
		
		String conditionsOrderbyStr = this.mapperSqlGenerator.conditionsOrderby();
		sb.append(this.fmtLines(conditionsOrderbyStr, "\t", ""));
		
		String conditionsWhereStr = this.mapperSqlGenerator.conditionsWhere(pojoClazz, tableAliasName, modelAliasName, mapAliasName);
		sb.append(this.fmtLines(conditionsWhereStr, "\t", ""));
		
		String selectListByStr = this.mapperSqlGenerator.selectListBy(tableAliasName, pojoClazz);
		sb.append(this.fmtLines(selectListByStr, "\t", ""));
		
		String selectCountByStr = this.mapperSqlGenerator.selectCountBy(tableAliasName);
		sb.append(this.fmtLines(selectCountByStr, "\t", ""));
		
		String selectOneByStr = this.mapperSqlGenerator.selectOneBy(tableAliasName, pojoClazz);
		sb.append(this.fmtLines(selectOneByStr, "\t", ""));
		
		String insertOneByStr = this.mapperSqlGenerator.insertOneBy(pojoClazz);
		sb.append(this.fmtLines(insertOneByStr, "\t", ""));
		
		String insertListByStr = this.mapperSqlGenerator.insertListBy(pojoClazz);
		sb.append(this.fmtLines(insertListByStr, "\t", ""));
		
		String updateByStr = this.mapperSqlGenerator.updateBy(pojoClazz, modelAliasName, oldAliasName, mapAliasName);
		sb.append(this.fmtLines(updateByStr, "\t", ""));
		
		String deleteByStr = this.mapperSqlGenerator.deleteBy(pojoClazz, modelAliasName, mapAliasName);
		sb.append(this.fmtLines(deleteByStr, "\t", ""));
		
		if(CommonPartialTreeEntity.class.isAssignableFrom(pojoClazz)) {
			CodeMapperSqlTreeGenerator sqlTreeGenerator = new CodeMapperSqlTreeGenerator();
			String sqlTreeStr = sqlTreeGenerator.makeContent(pojoClazz);
			sb.append(this.fmtLines("<!-- 树结构相关操作 -->", "\t", ""));
			sb.append(this.fmtLines(sqlTreeStr, "\t", ""));
		}
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("<!-- ##########################   common sql end    ########################## -->").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\r\n");
		sb.append("</mapper>");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		CodeFileMapperXmlGenerator mapperFileGenerator = new CodeFileMapperXmlGenerator();
		Class<?> pojoClazz = CommonPartialTreeEntity.class;
		String sqlNamespace = pojoClazz.getName();
		String outRootDirPath = "D:\\ddd\\code\\xml";
		
		mapperFileGenerator.writeToConsole(pojoClazz, sqlNamespace);
		mapperFileGenerator.writeToFile(pojoClazz, sqlNamespace, outRootDirPath, true);
		System.out.println("已生成XML文件");
	}
}

