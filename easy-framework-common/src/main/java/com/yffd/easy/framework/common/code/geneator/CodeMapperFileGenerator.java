package com.yffd.easy.framework.common.code.geneator;

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
public class CodeMapperFileGenerator extends CodeGenerator {
	
	private static final String FILE_SUFFIX = ".xml";
	private CodeMapperSqlGenerator mapperSqlGenerator = new CodeMapperSqlGenerator();
	
	public void writeAllToFile(String modelRootDirPath, String modelPackageName, String mapperPackageName, String outRootDirPath, boolean covered) throws Exception {
		if(!modelRootDirPath.endsWith(File.separator)) modelRootDirPath += File.separator;
		String modelPackageDirPath = modelPackageName.replace(".", File.separator);
		String fullDirPath = modelRootDirPath + modelPackageDirPath;
		
		File file = new File(fullDirPath);
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File childFile : files) {
				if(childFile.isDirectory()) {
					continue;
				} else {
					boolean skip = false;
					String javaFileName = childFile.getName();	// 不包含路径
					for(String skipName : this.getSkipModelNamesLike()) {
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
						String modelFullClassName = modelPackageName + "." + javaFileName;
						Class<?> modelClazz = Class.forName(modelFullClassName);
						this.writeToFile(modelClazz, mapperPackageName, outRootDirPath, covered);
					}
				}
			}
		}
	}
	
	public void writeToFile(Class<?> modelClazz, String mapperPackageName, String outRootDirPath, boolean covered) {
		String tableAliasName = "t";
		String modelAliasName = "model";
		String oldAliasName = "old";
		String mapAliasName = "map";
		String namespace = mapperPackageName + "." + this.fmtMapperSimpleName(modelClazz);
		String content = this.makeContent(modelClazz, tableAliasName, namespace, modelAliasName, oldAliasName, mapAliasName);
		
		String fileName = outRootDirPath + File.separator + this.fmtMapperName(modelClazz) + FILE_SUFFIX;
		this.makedirs(outRootDirPath);
		this.writeToFile(content, fileName, covered);
		
	}
	
	public void writeToConsole(Class<?> modelClazz, String mapperPackageName) {
		String tableAliasName = "t";
		String modelAliasName = "model";
		String oldAliasName = "old";
		String mapAliasName = "map";
		String namespace = mapperPackageName + "." + this.fmtMapperSimpleName(modelClazz);
		String content = this.makeContent(modelClazz, tableAliasName, namespace, modelAliasName, oldAliasName, mapAliasName);
		System.out.println(content);
		
	}
	
	protected String makeContent(Class<?> modelClazz, String tableAliasName, String namespace, String modelAliasName, String oldAliasName, String mapAliasName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\r\n");
		sb.append("<mapper namespace=\"" + namespace + "\">").append("\r\n");
		
		sb.append("\r\n").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("<!-- #########################   common sql begin    ######################### -->").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("\r\n");
		
		String tableNameStr = this.mapperSqlGenerator.tableName(modelClazz);
		sb.append(this.fmtLine(tableNameStr, "\t", ""));
		
		String tableColumnsStr = this.mapperSqlGenerator.tableColumns(modelClazz, tableAliasName);
		sb.append(this.fmtLine(tableColumnsStr, "\t", ""));
		
		String conditionsLimitStr = this.mapperSqlGenerator.conditionsLimit();
		sb.append(this.fmtLine(conditionsLimitStr, "\t", ""));
		
		String conditionsOrderbyStr = this.mapperSqlGenerator.conditionsOrderby(tableAliasName);
		sb.append(this.fmtLine(conditionsOrderbyStr, "\t", ""));
		
		String conditionsWhereStr = this.mapperSqlGenerator.conditionsWhere(modelClazz, tableAliasName, modelAliasName, mapAliasName);
		sb.append(this.fmtLine(conditionsWhereStr, "\t", ""));
		
		String selectListByStr = this.mapperSqlGenerator.selectListBy(tableAliasName, modelClazz);
		sb.append(this.fmtLine(selectListByStr, "\t", ""));
		
		String selectCountByStr = this.mapperSqlGenerator.selectCountBy(tableAliasName);
		sb.append(this.fmtLine(selectCountByStr, "\t", ""));
		
		String selectOneByStr = this.mapperSqlGenerator.selectOneBy(tableAliasName, modelClazz);
		sb.append(this.fmtLine(selectOneByStr, "\t", ""));
		
		String insertOneStr = this.mapperSqlGenerator.insertOne(modelClazz);
		sb.append(this.fmtLine(insertOneStr, "\t", ""));
		
		String insertListStr = this.mapperSqlGenerator.insertList(modelClazz);
		sb.append(this.fmtLine(insertListStr, "\t", ""));
		
		String updateByStr = this.mapperSqlGenerator.updateBy(modelClazz, modelAliasName, oldAliasName, mapAliasName);
		sb.append(this.fmtLine(updateByStr, "\t", ""));
		
		String deleteByStr = this.mapperSqlGenerator.deleteBy(modelClazz, modelAliasName, mapAliasName);
		sb.append(this.fmtLine(deleteByStr, "\t", ""));
		
		if(CommonPartialTreeEntity.class.isAssignableFrom(modelClazz)) {
			CodeMapperSqlTreeGenerator sqlTreeGenerator = new CodeMapperSqlTreeGenerator();
			String sqlTreeStr = sqlTreeGenerator.makeContent(modelClazz);
			sb.append(this.fmtLine("<!-- 树结构相关操作 -->", "\t", ""));
			sb.append(this.fmtLine(sqlTreeStr, "\t", ""));
		}
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\t").append("<!-- ##########################   common sql end    ########################## -->").append("\r\n");
		sb.append("\t").append("<!-- ######################################################################### -->").append("\r\n");
		sb.append("\r\n");
		sb.append("</mapper>");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		CodeMapperFileGenerator mapperFileGenerator = new CodeMapperFileGenerator();
		Class<?> modelClazz = null;
		String mapperPackageName = "com.yffd.easy.uupm.mapper";
		String outRootDirPath = "D:\\ddd\\code\\xml";
		
		mapperFileGenerator.writeToConsole(modelClazz, mapperPackageName);
		mapperFileGenerator.writeToFile(modelClazz, mapperPackageName, outRootDirPath, true);
		System.out.println("已生成XML文件");
	}
}

