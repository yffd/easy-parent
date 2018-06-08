package com.yffd.easy.uupm.code;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.code.generator.CodeGenerator;
import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.framework.common.service.impl.CommonServiceImpl;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月5日 下午5:34:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LocalProjectConfigTest extends CodeGenerator {
	
	public static String author = "ZhangST";
	public static boolean covered = true;
	
	public static String daoPackageName = "com.yffd.easy.uupm.dao";
	public static Class<?> daoSuperClazz = DefaultMybatisDao.class;
	
	public static String servicePackageName = "com.yffd.easy.uupm.service";
	public static Class<?> serviceSuperClazz = CommonServiceImpl.class;
	
	
	public static String baseDirPath = "D:\\java\\git-easy\\easy-parent\\";
	public static String javaRootDirPath = baseDirPath + "easy-uupm\\easy-uupm-service\\src\\main\\java";
	public static String resourceRootDirPath = baseDirPath + "easy-uupm\\easy-uupm-service\\src";
	
	public static String outRootDirPath_dao = javaRootDirPath;
	public static String outRootDirPath_service = javaRootDirPath;
	public static String outRootDirPath_mapper_xml = resourceRootDirPath + "\\main\\resources\\mybatis\\mapper\\uupm";
	
	
	List<String> skipModel = new ArrayList<String>();
	{
		skipModel.add("EasyPersistModel");
		skipModel.add("BaseModel");
	}
	
	@Override
	protected List<String> getSkipPojoList() {
		return skipModel;
	}

	
}

