package com.yffd.easy.framework.common.code.geneator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description  sql tree代码生成器.
 * @Date		 2018年2月6日 下午1:55:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeMapperSqlTreeGenerator extends CodeGenerator {
	private static final String TEMPLATE_PATH = "com\\yffd\\easy\\framework\\common\\code\\template\\MapperSqlTree.template";
	
	public void writeToConsole(Class<?> modelClazz) {
		String content = this.makeContent(modelClazz);
		System.out.println(content);
	}
	
	protected String makeContent(Class<?> modelClazz) {
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
		
		content = content.replace("##model_full_name##", modelClazz.getName());
		
		return content;
	}
	
	public static void main(String[] args) {
		CodeMapperSqlTreeGenerator generator = new CodeMapperSqlTreeGenerator();
		Class<?> modelClazz = null;
		generator.writeToConsole(modelClazz);
	}
}

