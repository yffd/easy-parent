package com.yffd.easy.uumc.web.test;

import com.yffd.easy.framework.common.code.generator.CodeFileControllerGenerator;
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.web.controller.UumcWebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午3:58:13 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WriteConrollerTest {
	private static CodeFileControllerGenerator controllerGenerator = new CodeFileControllerGenerator();
	private static String author = "ZhangST";
	private static boolean covered = true;
	private static String baseDirPath = "D:\\java\\git-easy\\easy-parent\\";
	private static String outRootDirPath_controller = baseDirPath + "easy-uumc\\easy-uumc-web\\src\\main\\java";
	private static String servicePackageName = "com.yffd.easy.uumc.service";
	private static String controllerPackageName = "com.yffd.easy.uumc.web.controller";
	private static Class<?> controllerSuperClazz = UumcWebController.class;
	
	public static void main(String[] args) {
			// TODO
		Class<?> pojoClazz = UumcAccountEntity.class;
		controllerGenerator.writeToFile(pojoClazz, controllerSuperClazz, controllerPackageName, servicePackageName, author, outRootDirPath_controller, covered);
	
		// TODO
//		Class<?> pojoClazz = UumcSysApplicationEntity.class;
//		controllerGenerator.writeToConsole(pojoClazz, controllerSuperClazz, controllerPackageName, servicePackageName, author);
	}
	
}

