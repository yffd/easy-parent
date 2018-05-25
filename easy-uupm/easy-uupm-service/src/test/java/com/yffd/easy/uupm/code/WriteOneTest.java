package com.yffd.easy.uupm.code;

import org.junit.Test;

import com.yffd.easy.framework.common.code.geneator.CodeFileDaoGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeFileMapperXmlGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeFileServiceGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeMapperSqlGenerator;
import com.yffd.easy.framework.common.code.geneator.CodeMapperSqlTreeGenerator;
import com.yffd.easy.uupm.entity.UupmMenuEntity;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.entity.UupmUITreeEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月7日 下午2:20:00 <br/>
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
	public void writeToService() {
		// TODO
		Class<?> pojoClazz = UupmMenuEntity.class;
		serviceGenerator.writeToFile(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author, outRootDirPath_service, covered);
	}
	
	@Test
	public void consoleToService() {
		// TODO
		Class<?> pojoClazz = UupmMenuEntity.class;
		serviceGenerator.writeToConsole(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author);
	}
	
	/*****************************************************************************/
	
	/*****************************************************************************/
	
	@Test
	public void writeToDao() {
		// TODO
		Class<?> pojoClazz = UupmMenuEntity.class;
		daoGenerator.writeToFile(pojoClazz, daoSuperClazz, daoPackageName, author, outRootDirPath_dao, covered);
	}
	
	@Test
	public void consoleToDao() {
		// TODO
		Class<?> pojoClazz = UupmMenuEntity.class;
		daoGenerator.writeToConsole(pojoClazz, daoSuperClazz, daoPackageName, author);
	}
	
	/*****************************************************************************/
	
	/*****************************************************************************/
	
	@Test
	public void writeToMapper() {
		// TODO
		Class<?> pojoClazz = UupmUITreeEntity.class;
		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		mapperXmlGenerator.writeToFile(pojoClazz, sqlNamespace, outRootDirPath_mapper_xml, covered);
	}
	
	@Test
	public void consoleToMapper() {
		// TODO
		Class<?> pojoClazz = UupmMenuEntity.class;
		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		mapperXmlGenerator.writeToConsole(pojoClazz, sqlNamespace);
	}
	
	/*****************************************************************************/
	
	
	@Test
	public void consoleVoConditionsWhere() {
		// TODO
		Class<?> voClazz = UupmUserInfoVo.class;
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
		Class<?> pojoClazz = UupmResourceEntity.class;
		sqlGenerator.writeToConsole(tableAliasName, pojoAliasName, oldAliasName, mapAliasName, pojoClazz);
	}
	
	@Test
	public void consoleSqlTree() {
		// TODO
		Class<?> pojoClazz = UupmResourceEntity.class;
		sqlTreeGenerator.writeToConsole(pojoClazz);
	}
	
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	/*****************************************************************************/
	
	@Test
	public void writeToOneOfAll() {
		// TODO
		Class<?> pojoClazz = UupmUITreeEntity.class;
		String sqlNamespace = daoGenerator.fmtDaoFullName(daoPackageName, pojoClazz);
		mapperXmlGenerator.writeToFile(pojoClazz, sqlNamespace, outRootDirPath_mapper_xml, covered);
		daoGenerator.writeToFile(pojoClazz, daoSuperClazz, daoPackageName, author, outRootDirPath_dao, covered);
		serviceGenerator.writeToFile(pojoClazz, serviceSuperClazz, servicePackageName, daoPackageName, author, outRootDirPath_service, covered);
	}
	
	
	/*****************************************************************************/

}

