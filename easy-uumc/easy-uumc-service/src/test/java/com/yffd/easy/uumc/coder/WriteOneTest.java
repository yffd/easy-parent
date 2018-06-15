package com.yffd.easy.uumc.coder;

import org.junit.Test;

import com.yffd.easy.framework.common.code.generator.CodeFileDaoGenerator;
import com.yffd.easy.framework.common.code.generator.CodeFileMapperXmlGenerator;
import com.yffd.easy.framework.common.code.generator.CodeFileServiceGenerator;
import com.yffd.easy.framework.common.code.generator.CodeMapperSqlGenerator;
import com.yffd.easy.framework.common.code.generator.CodeMapperSqlTreeGenerator;
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.pojo.entity.UumcPermissionEntity;
import com.yffd.easy.uumc.pojo.entity.UumcUserEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:48:01 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class WriteOneTest extends LocalProjectConfigTest {
	private CodeFileServiceGenerator serviceGenerator = new CodeFileServiceGenerator();
	private CodeFileDaoGenerator daoGenerator = new CodeFileDaoGenerator();
	private CodeFileMapperXmlGenerator mapperXmlGenerator = new CodeFileMapperXmlGenerator();
	private CodeMapperSqlTreeGenerator sqlTreeGenerator = new CodeMapperSqlTreeGenerator();
	private CodeMapperSqlGenerator sqlGenerator = new CodeMapperSqlGenerator();
	
	/*****************************************************************************/
	
	@Test
	public void writeToMapper() {
		// TODO
		Class<?> pojoClazz = UumcPermissionEntity.class;
//		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		String sqlNamespace = pojoClazz.getName();
		mapperXmlGenerator.writeToFile(pojoClazz, sqlNamespace, outRootDirPath_mapper_xml, covered);
	}
	
	@Test
	public void consoleToMapper() {
		// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		mapperXmlGenerator.writeToConsole(pojoClazz, sqlNamespace);
	}
	
	/*****************************************************************************/
	
	/*****************************************************************************/
	
	@Test
	public void writeToService() {
		// TODO
		Class<?> pojoClazz = UumcUserEntity.class;
		serviceGenerator.writeToFile(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author, outRootDirPath_service, covered);
	}
	
	@Test
	public void consoleToService() {
		// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		serviceGenerator.writeToConsole(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author);
	}
	
	
	/*****************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*****************************************************************************/
	
	@Test
	public void writeToDao() {
		// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		daoGenerator.writeToFile(pojoClazz, daoSuperClazz, daoPackageName, author, outRootDirPath_dao, covered);
	}
	
	@Test
	public void consoleToDao() {
		// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		daoGenerator.writeToConsole(pojoClazz, daoSuperClazz, daoPackageName, author);
	}
	
	/*****************************************************************************/
	
	
	
	@Test
	public void consoleVoConditionsWhere() {
		// TODO
		Class<?> voClazz = null;
		String content = sqlGenerator.voConditionsWhere(voClazz, "t", "vo");
		System.out.println(content);
	}
	
	@Test
	public void consoleSql() {
//		String tableAliasName = "";
//		String pojoAliasName = "";
//		String oldAliasName = "";
//		String mapAliasName = "";
		
		String tableAliasName = "t";
		String pojoAliasName = "entity";
		String oldAliasName = "entityOld";
		String mapAliasName = "map";
		// TODO
		Class<?> pojoClazz = null;
		sqlGenerator.writeToConsole(tableAliasName, pojoAliasName, oldAliasName, mapAliasName, pojoClazz);
	}
	
	@Test
	public void consoleSqlTree() {
		// TODO
		Class<?> pojoClazz = null;
		sqlTreeGenerator.writeToConsole(pojoClazz);
	}
	
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	/*****************************************************************************/
	
	@Test
	public void writeToOneOfAll() {
		// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		mapperXmlGenerator.writeToFile(pojoClazz, sqlNamespace, outRootDirPath_mapper_xml, covered);
		daoGenerator.writeToFile(pojoClazz, daoSuperClazz, daoPackageName, author, outRootDirPath_dao, covered);
		serviceGenerator.writeToFile(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author, outRootDirPath_service, covered);
	}
	
	
	/*****************************************************************************/

}

