package com.yffd.easy.uumc.coder;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.code.generator.CodeGenerator;
import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.uumc.service.UupmBaseService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:47:53 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class LocalProjectConfigTest extends CodeGenerator {
	
	public static String author = "ZhangST";
	public static boolean covered = true;
	
	public static String daoPackageName = "com.yffd.easy.uumc.dao";
	public static Class<?> daoSuperClazz = DefaultMybatisDao.class;
	
	public static String servicePackageName = "com.yffd.easy.uumc.service";
	public static Class<?> serviceSuperClazz = UupmBaseService.class;
	
	public static String baseDirPath = "D:\\java\\git-easy\\easy-parent\\";
//	public static String baseDirPath = "F:\\git_workspace\\easy-parent\\";
	public static String javaRootDirPath = baseDirPath + "easy-uumc\\easy-uumc-service\\src\\main\\java";
	public static String resourceRootDirPath = baseDirPath + "easy-uumc\\easy-uumc-service\\src";
	
	public static String outRootDirPath_dao = javaRootDirPath;
	public static String outRootDirPath_service = javaRootDirPath;
	public static String outRootDirPath_mapper_xml = resourceRootDirPath + "\\main\\resources\\mybatis\\mapper\\uumc";
	
	
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

