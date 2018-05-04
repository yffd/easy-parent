package com.yffd.easy.uupm.code;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.code.geneator.CodeGenerator;
import com.yffd.easy.framework.common.persist.mybatis.mapper.ICommonMapper;

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
	
	public static String baseDirPath = "D:\\java\\git-easy\\";
	public static String modelRootDirPath = baseDirPath + "easy-master\\easy-uupm-service\\src\\main\\java";
	public static String modelPackageName = "com.yffd.easy.uupm.entity";
	
	public static Class<?> superMapperClazz = ICommonMapper.class;
	public static String mapperPackageName = "com.yffd.easy.uupm.mapper";
	
	public static String outRootDirPath_src = baseDirPath + "easy-master\\easy-uupm-service\\src";
	public static String outRootDirPath_src_main = outRootDirPath_src + "\\main\\java";
	public static String outRootDirPath_src_test = outRootDirPath_src + "\\test\\java";
	public static String outRootDirPath_mapper_xml = outRootDirPath_src + "\\main\\resources\\mybatis\\mapper\\uupm";
	
	List<String> skipModelNamesLike = new ArrayList<String>();
	{
		skipModelNamesLike.add("EasyPersistModel");
		skipModelNamesLike.add("BaseModel");
	}
	@Override
	protected List<String> getSkipModelNamesLike() {
		return skipModelNamesLike;
	}
	
	
}

