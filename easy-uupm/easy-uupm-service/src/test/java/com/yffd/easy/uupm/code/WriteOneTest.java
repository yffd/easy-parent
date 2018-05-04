package com.yffd.easy.uupm.code;

import org.junit.Test;

import com.yffd.easy.framework.common.code.geneator.CodeMapperFileGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeMapperSqlGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeMapperSqlTreeGenerator;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.entity.UupmRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月7日 下午2:20:00 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WriteOneTest extends LocalProjectConfigTest {
	private CodeMapperFileGenerator mapperFileGenerator = new CodeMapperFileGenerator();
	private CodeMapperSqlGenerator mapperSqlGenerator = new CodeMapperSqlGenerator();
	private CodeMapperSqlTreeGenerator mapperSqlTreeGenerator = new CodeMapperSqlTreeGenerator();
	
	private Class<?> modelClazz = UupmRoleEntity.class;
	
	@Test
	public void writeTo_File_Mapper() {
		mapperFileGenerator.writeToFile(modelClazz, mapperPackageName, outRootDirPath_mapper_xml, covered);
	}
	
	/*****************************************************************************/
	/*****************************************************************************/
	
	@Test
	public void writeTo_Console_Mapper() {
		mapperFileGenerator.writeToConsole(modelClazz, mapperPackageName);
	}
	
	@Test
	public void writeTo_Console_MapperSql() {
//		String tableAliasName = "";
//		String modelAliasName = "";
//		String oldAliasName = "";
//		String mapAliasName = "";
		
		String tableAliasName = "t";
		String modelAliasName = "model";
		String oldAliasName = "old";
		String mapAliasName = "map";
		
		Class<?> modelClazz = UupmResourceEntity.class;
		mapperSqlGenerator.writeToConsole(tableAliasName, modelAliasName, oldAliasName, mapAliasName, modelClazz);
	}
	
	@Test
	public void writeTo_Console_MapperSqlTree() {
		
		Class<?> modelClazz = UupmResourceEntity.class;
		mapperSqlTreeGenerator.writeToConsole(modelClazz);
	}
	
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

}

