package com.yffd.easy.framework.common.code.geneator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;

/**
 * @Description  Mapper中的sql片段拼写生成器.
 * @Date		 2018年2月6日 下午2:56:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeMapperSqlGenerator extends CodeGenerator {
	
	protected List<String> makeWhereForModel(Class<?> modelClazz, String tableAliasName, String modelAliasName, boolean autoLikeQuery) {
		LinkedHashMap<String, Class<?>> AttributeMap = this.sortAttributeNames(modelClazz);
		if(null==AttributeMap || AttributeMap.size()==0) return null;
		
		List<String> equalsList = new ArrayList<String>();
		List<String> likeList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = AttributeMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String typeName = typeClazz.getName();
			String attrName = entry.getKey();
			// 过滤属性
			if(this.skipAttributeName(typeClazz, attrName)) continue;
						
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
			String parameterName = attrName;
			if(null!=modelAliasName && !"".equals(modelAliasName)) {
				parameterName = modelAliasName + "." + parameterName;
			}
			
			if(String.class.getName().equals(typeName)) {
				if(autoLikeQuery && (attrName.matches(".*((?i)n)ame.*"))) {
					if(autoLikeQuery) {
						String tmp = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> and " + columnName + " like CONCAT(CONCAT('%', #{"+parameterName+"}), '%') </if>";
						likeList.add(tmp);
					}
				} else {
					String tmp = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> and " + columnName + " = #{"+parameterName+"} </if>";
					equalsList.add(tmp);
				}
			} else if(Date.class.getName().equals(typeName)) {
				String tmp = "<if test=\""+parameterName+" != null \"><![CDATA[ and " + columnName + " = #{"+parameterName+"}]]> </if>";
				equalsList.add(tmp);
			} else {
				String tmp = "<if test=\""+parameterName+" != null \"> and " + columnName + " = #{"+parameterName+"} </if>";
				equalsList.add(tmp);
			}
		}
		
		List<String> retList = new ArrayList<String>();
		if(equalsList.size()>0) {
			retList.add("<!-- Equal query -->");
			retList.addAll(equalsList);
		}
		if(likeList.size()>0) {
			retList.add("<!-- Like query -->");
			retList.addAll(likeList);
		}
		
		return retList;
	}
	
	protected List<String> makeForeach(Class<?> modelClazz, String tableAliasName, String mapAliasName) {
		LinkedHashMap<String, Class<?>> attributeNameMap = this.getAttributeNames(modelClazz);
		if(null==attributeNameMap || attributeNameMap.size()==0) return null;
		
		List<String> inList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = attributeNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String typeName = typeClazz.getName();
			String attrName = entry.getKey();
			// 过滤属性
			if(this.skipAttributeName(typeClazz, attrName)) continue;
						
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
			String parameterName = attrName;
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				parameterName = mapAliasName + "." + parameterName;
			}
			if("version".equals(attrName)) {
				
			} else if("delFlag".equals(attrName)) {
				
			} else if("createBy".equals(attrName)) {
				
			} else if("createTime".equals(attrName)) {
				
			} else if("updateBy".equals(attrName)) {
				
			} else if("updateTime".equals(attrName)) {
				
			} else if(Date.class.getName().equals(typeName)) {
				
			} else if(String.class.getName().equals(typeName)
					|| Byte.class.getName().equals(typeClazz)
					|| Character.class.getName().equals(typeClazz)
					|| Number.class.isAssignableFrom(typeClazz)
					|| typeClazz.isPrimitive()
					) {
				StringBuilder sb = new StringBuilder();
				parameterName += "List";
				sb.append("<if test=\""+parameterName+" != null and "+ parameterName +".size()>0\">").append("\r\n");
				sb.append("and ").append(columnName).append(" in ")
				.append("<foreach item=\"item\" index=\"index\" collection=\""+parameterName+"\" open=\"(\" separator=\",\" close=\") \">")
				.append("#{item}").append("</foreach>").append("\r\n");
				sb.append("</if>").append("\r\n");
				inList.add(sb.toString());
			}
		}
		return inList;
	}
	
	protected List<String> makeWhereForMap(Class<?> modelClazz, String tableAliasName, String mapAliasName) {
		List<String> modelList = this.makeWhereForModel(modelClazz, tableAliasName, mapAliasName, false);
		List<String> inList = this.makeForeach(modelClazz, tableAliasName, mapAliasName);
		List<String> retList = new ArrayList<String>();
		retList.addAll(modelList);
		if(inList.size()>0) {
			retList.add("<!-- In query -->");
			retList.addAll(inList);
		}
		return retList;
	}
	
	protected List<String> makeSetForModel(Class<?> modelClazz, String modelAliasName) {
		LinkedHashMap<String, Class<?>> attributeNameMap = this.getAttributeNames(modelClazz);
		if(null==attributeNameMap || attributeNameMap.size()==0) return null;
		
		List<String> setList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = attributeNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String typeName = typeClazz.getName();
			String attrName = entry.getKey();
			// 过滤属性
			if(this.skipAttributeName(typeClazz, attrName)) continue;
						
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			// set字段
			if("id".equals(attrName) || "createBy".equals(attrName) || "createTime".equals(attrName)) {
				
			} else if("version".equals(attrName)) {
				String tmp = "VERSION = VERSION + 1, ";
				setList.add(tmp);
			} else if(String.class.getName().equals(typeName)) {
				String parameterName = attrName;
				if(!EasyStringCheckUtils.isEmpty(modelAliasName)) parameterName = modelAliasName + "." + parameterName;
				String columnName_new = columnName + " = " + "#{"+parameterName+"}, ";
				String tmp_new = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> "+columnName_new+" </if>";
				setList.add(tmp_new);
			} else {
				String parameterName = attrName;
				if(!EasyStringCheckUtils.isEmpty(modelAliasName)) parameterName = modelAliasName + "." + parameterName;
				String columnName_new = columnName + " = " + "#{"+parameterName+"}, ";
				String tmp_new = "<if test=\""+parameterName+" != null\"> "+columnName_new+" </if>";
				setList.add(tmp_new);
			}
		}
		return setList;
	}
	
	protected String conditionsWhere(Class<?> modelClazz, String tableAliasName, String modelAliasName, String mapAliasName) {
		List<String> modelList = this.makeWhereForModel(modelClazz, tableAliasName, modelAliasName, true);
		List<String> mapList = this.makeWhereForMap(modelClazz, tableAliasName, mapAliasName);
		
		StringBuilder sb = new StringBuilder();
		if(null!=modelList && modelList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				sb.append("<if test=\""+modelAliasName+" != null\">").append("\r\n");
			}
			for(String line : modelList) {
				sb.append(this.fmtLine(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				sb.append(this.fmtLine(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 动态组装复合查询条件 -->").append("\r\n");
		tmp.append("<sql id=\"conditions_where\">").append("\r\n");
		tmp.append(this.fmtLine(sb.toString(), "\t", ""));
		tmp.append("</sql>").append("\r\n");
		
		return tmp.toString();
	}
	
	public String updateBy(Class<?> modelClazz, String modelAliasName, String oldAliasName, String mapAliasName) {
		List<String> setList = this.makeSetForModel(modelClazz, modelAliasName);
		List<String> oldList = this.makeWhereForModel(modelClazz, "", oldAliasName, false);
		List<String> mapList = this.makeWhereForMap(modelClazz, "", mapAliasName);
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 更新 -->").append("\r\n");
		tmp.append("<update id=\"updateBy\" parameterType=\"java.util.Map\">").append("\r\n");
		tmp.append("\t").append("update <include refid=\"table_name\" />").append("\r\n");
		tmp.append("\t").append("<set>").append("\r\n");
		// set字段
		if(null!=setList && setList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				tmp.append("\t").append("<if test=\""+modelAliasName+" != null\">").append("\r\n");
			}
			for(String line : setList) {
				tmp.append(this.fmtLine(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		tmp.append("\t").append("</set>").append("\r\n");
		tmp.append("\t").append("<where>").append("\r\n");
		// 条件字段
		if(null!=oldList && oldList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(oldAliasName)) {
				tmp.append("\t").append("<if test=\""+oldAliasName+" != null\">").append("\r\n");
			}
			for(String line : oldList) {
				tmp.append(this.fmtLine(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(oldAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				tmp.append("\t").append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				tmp.append(this.fmtLine(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		
		tmp.append("\t").append("<!-- 防止没有参数传递，导致全部更新 -->").append("\r\n");
		tmp.append("\t").append("or 1=2").append("\r\n");
		
		tmp.append("\t").append("</where>").append("\r\n");
		tmp.append("</update>").append("\r\n");
		return tmp.toString();
	}
	
	public String deleteBy(Class<?> modelClazz, String modelAliasName, String mapAliasName) {
		List<String> modelList = this.makeWhereForModel(modelClazz, "", modelAliasName, false);
		List<String> mapList = this.makeWhereForMap(modelClazz, "", mapAliasName);
		StringBuilder sb = new StringBuilder();
		if(null!=modelList && modelList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				sb.append("<if test=\""+modelAliasName+" != null\">").append("\r\n");
			}
			for(String line : modelList) {
				sb.append(this.fmtLine(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(modelAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				sb.append(this.fmtLine(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 删除 -->").append("\r\n");
		tmp.append("<delete id=\"deleteBy\" parameterType=\"java.util.Map\">").append("\r\n");
		tmp.append("\t").append("delete from <include refid=\"table_name\" />").append("\r\n");
		tmp.append("\t").append("<where>").append("\r\n");
		tmp.append(this.fmtLine(sb.toString(), "\t", ""));
		tmp.append("\t").append("<!-- 防止没有参数传递，导致全部删除 -->").append("\r\n");
		tmp.append("\t").append("or 1=2").append("\r\n");
		tmp.append("\t").append("</where>").append("\r\n");
		tmp.append("</delete>");
		return tmp.toString();
	}
	
	public String tableName(Class<?> modelClazz) {
		String name = modelClazz.getSimpleName();
		name = this.fmtModelName(modelClazz, null, null);
		name = this.name2column(name);
		name = name.toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 表名 -->").append("\r\n");
		sb.append("<sql id=\"table_name\"> "+name+" </sql>").append("\r\n");
		return sb.toString();
	}
	
	public String conditionsLimit() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 分页条件 -->").append("\r\n");
		sb.append("<sql id=\"conditions_limit\"><if test=\"page != null\"> limit #{page.startIndex}, #{page.pageLimit} </if></sql>").append("\r\n");
		return sb.toString();
	}
	
	public String conditionsOrderby(String tableAliasName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 排序条件 -->").append("\r\n");
		if(null!=tableAliasName) {
			sb.append("<sql id=\"conditions_orderby\"> ORDER BY ").append(tableAliasName).append(".CREATE_TIME desc </sql>").append("\r\n");
		} else {
			sb.append("<sql id=\"conditions_orderby\"> ORDER BY CREATE_TIME desc </sql>").append("\r\n");
		}
		return sb.toString();
	}
	
	public String tableColumns(Class<?> modelClazz, String tableAliasName) {
		LinkedHashMap<String, Class<?>> propsMap = this.sortAttributeNames(modelClazz);
		if(null==propsMap || propsMap.size()==0) return null;
		int num = 0;
		StringBuilder sb = new StringBuilder();
		for(Iterator<Entry<String, Class<?>>> it = propsMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String attrName = entry.getKey();
			// 过滤属性
			if(!this.filterArributeName(typeClazz, attrName)) continue;
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
						
			columnName += " as " + attrName;
			sb.append(columnName).append(", ");
			num++;
			if(num % 5 == 0) sb.append("\r\n");
		}
		if(sb.length()==0) return null;
		int endIndex = sb.lastIndexOf(",");
		String content = sb.toString().substring(0, endIndex);
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 列名 -->").append("\r\n");
		tmp.append("<sql id=\"table_columns\">").append("\r\n");
		tmp.append(this.fmtLine(content, "\t", ""));
		tmp.append("</sql>").append("\r\n");
		return tmp.toString();
	}
	
	public String selectListBy(String tableAliasName, Class<?> modelClazz) {
		String asTableAliasName = "";
		if(null!=tableAliasName) asTableAliasName = " as " + tableAliasName + " ";
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 条件查询 -->").append("\r\n");
//		sb.append("<select id=\"selectListBy\" parameterType=\"java.util.Map\" resultMap=\"resutlId\">").append("\r\n");
		sb.append("<select id=\"selectListBy\" parameterType=\"java.util.Map\" resultType=\""+modelClazz.getName()+"\">").append("\r\n");
		sb.append("\t").append("select <include refid=\"table_columns\" />").append("\r\n");
		sb.append("\t").append("from <include refid=\"table_name\"/>").append(asTableAliasName).append("\r\n");
		sb.append("\t").append("<where>").append("\r\n");
		sb.append("\t").append("\t").append("<include refid=\"conditions_where\" />").append("\r\n");
		sb.append("\t").append("</where>").append("\r\n");
		sb.append("\t").append("<include refid=\"conditions_orderby\" />").append("\r\n");
		sb.append("\t").append("<include refid=\"conditions_limit\" />").append("\r\n");
		sb.append("</select>").append("\r\n");
		return sb.toString();
	}
	
	public String selectCountBy(String tableAliasName) {
		String asTableAliasName = "";
		if(null!=tableAliasName) asTableAliasName = " as " + tableAliasName + " ";
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 统计查询 -->").append("\r\n");
		sb.append("<select id=\"selectCountBy\" parameterType=\"java.util.Map\" resultType=\"long\">").append("\r\n");
		sb.append("\t").append("select count(1) from ").append("\r\n");
		sb.append("\t").append("<include refid=\"table_name\"/>").append(asTableAliasName).append("\r\n");
		sb.append("\t").append("<where>").append("\r\n");
		sb.append("\t").append("\t").append("<include refid=\"conditions_where\" />").append("\r\n");
		sb.append("\t").append("</where>").append("\r\n");
		sb.append("</select>").append("\r\n");
		return sb.toString();
	}
	
	public String selectOneBy(String tableAliasName, Class<?> modelClazz) {
		String asTableAliasName = "";
		if(null!=tableAliasName) asTableAliasName = " as " + tableAliasName + " ";
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 单条查询 -->").append("\r\n");
		sb.append("<select id=\"selectOneBy\" parameterType=\"java.util.Map\" resultType=\""+modelClazz.getName()+"\">").append("\r\n");
		sb.append("\t").append("select <include refid=\"table_columns\" /> ").append("\r\n");
		sb.append("\t").append("from <include refid=\"table_name\"/>").append(asTableAliasName).append("\r\n");
		sb.append("\t").append("<where>").append("\r\n");
		sb.append("\t").append("\t").append("<include refid=\"conditions_where\" />").append("\r\n");
		sb.append("\t").append("</where>").append("\r\n");
		sb.append("</select>").append("\r\n");
		return sb.toString();
	}
	
	public String insertOne(Class<?> modelClazz) {
		LinkedHashMap<String, Class<?>> attributeNameMap = this.sortAttributeNames(modelClazz);
		if(null==attributeNameMap || attributeNameMap.size()==0) return null;
		
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		
		for(Iterator<Entry<String, Class<?>>> it = attributeNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String attrName = entry.getKey();
			// 过滤属性
			if("id".equals(attrName)) continue;
			if(!this.filterArributeName(typeClazz, attrName)) continue;
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			columns.append(columnName).append(", ");
			params.append("#{").append(attrName).append("}, ");
		}
		
		StringBuilder sb = new StringBuilder();
		if(columns.length()>0) {
			sb.append("(");
			sb.append(columns.substring(0, columns.lastIndexOf(",")));
			sb.append(")");
			sb.append("\r\n");
			sb.append("values");
			sb.append("\r\n");
			sb.append("(");
			sb.append(params.substring(0, params.lastIndexOf(",")));
			sb.append(")");
		}
		if(sb.length()==0) return null;
		String content = sb.toString();
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 单条插入 -->").append("\r\n");
		tmp.append("<insert id=\"insertOne\" parameterType=\"java.util.Map\" keyProperty=\"id\" useGeneratedKeys=\"true\">").append("\r\n");
		tmp.append("\t").append("insert into").append("\r\n");
		tmp.append("\t").append("<include refid=\"table_name\" />").append("\r\n");
		tmp.append(this.fmtLine(content, "\t", ""));
		tmp.append("</insert>");
		return tmp.toString();
	}
	
	public String insertList(Class<?> modelClazz) {
		LinkedHashMap<String, Class<?>> attributeNameMap = this.sortAttributeNames(modelClazz);
		if(null==attributeNameMap || attributeNameMap.size()==0) return null;
		
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		int num =0;
		for(Iterator<Entry<String, Class<?>>> it = attributeNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> typeClazz = entry.getValue();
			String attrName = entry.getKey();
			// 过滤属性
			if("id".equals(attrName)) continue;
			if(!this.filterArributeName(typeClazz, attrName)) continue;
			String columnName = this.name2column(attrName);
			if(null==columnName || "".equals(columnName)) continue;
			columns.append(columnName).append(", ");
			params.append("#{item.").append(attrName).append("},");
			
			num++;
			if(num % 5 == 0) params.append("\r\n");
		}
		
		StringBuilder sb = new StringBuilder();
		if(columns.length()>0) {
			sb.append("(");
			sb.append(columns.substring(0, columns.lastIndexOf(",")));
			sb.append(")");
			sb.append("\r\n");
			sb.append("values");
			sb.append("\r\n");
			sb.append("<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">");
			sb.append("\r\n");
			sb.append("(");
			sb.append(params.substring(0, params.lastIndexOf(",")));
			sb.append(")");
			sb.append("\r\n");
			sb.append("</foreach>").append("\r\n");
		}
		if(sb.length()==0) return null;
		String content = sb.toString();
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 批量插入 -->").append("\r\n");
		tmp.append("<insert id=\"insertList\" parameterType=\"java.util.Map\" keyProperty=\"id\" useGeneratedKeys=\"true\">").append("\r\n");
		tmp.append("\t").append("insert into <include refid=\"table_name\" />").append("\r\n");
		tmp.append(this.fmtLine(content, "\t", ""));
		tmp.append("</insert>").append("\r\n");
		return tmp.toString();
	}
	
	public static void main(String[] args) {
		CodeMapperSqlGenerator generator = new CodeMapperSqlGenerator();
		
		String tableAliasName = "t";
		String modelAliasName = "model";
		String oldAliasName = "old";
		String mapAliasName = "map";
		Class<?> modelClazz = null;
		
		String tableName = generator.tableName(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableName");
		System.out.println(tableName);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableName");
		System.out.println();
		 
		String conditionsLimit = generator.conditionsLimit();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsLimit");
		System.out.println(conditionsLimit);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsLimit");
		System.out.println();
		
		String conditionsOrderby = generator.conditionsOrderby(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsOrderby");
		System.out.println(conditionsOrderby);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsOrderby");
		
		System.out.println();
		String tableColumns = generator.tableColumns(modelClazz, tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableColumns");
		System.out.println(tableColumns);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableColumns");
		System.out.println();
		
		String conditionsWhere = generator.conditionsWhere(modelClazz, tableAliasName, modelAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsWhere");
		System.out.println(conditionsWhere);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsWhere");
		System.out.println();
		
		String selectListBy = generator.selectListBy(tableAliasName, modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectListBy");
		System.out.println(selectListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectListBy");
		System.out.println();
		
		String selectCountBy = generator.selectCountBy(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectCountBy");
		System.out.println(selectCountBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectCountBy");
		System.out.println();
		
		String selectOneBy = generator.selectOneBy(tableAliasName, modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectOneBy");
		System.out.println(selectOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectOneBy");
		System.out.println();
		
		String insertOne = generator.insertOne(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertOne");
		System.out.println(insertOne);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertOne");
		System.out.println();
		
		String insertList = generator.insertList(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertList");
		System.out.println(insertList);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertList");
		System.out.println();
		
		String updateBy = generator.updateBy(modelClazz, modelAliasName, oldAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::updateBy");
		System.out.println(updateBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::updateBy");
		System.out.println();
		
		String deleteBy = generator.deleteBy(modelClazz, modelAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::deleteBy");
		System.out.println(deleteBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::deleteBy");
		System.out.println();
		
	}
	
	public void writeToConsole(String tableAliasName, String modelAliasName, String oldAliasName, String mapAliasName, Class<?> modelClazz) {
		CodeMapperSqlGenerator generator = new CodeMapperSqlGenerator();
		
		String tableName = generator.tableName(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableName");
		System.out.println(tableName);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableName");
		System.out.println();
		 
		String conditionsLimit = generator.conditionsLimit();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsLimit");
		System.out.println(conditionsLimit);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsLimit");
		System.out.println();
		
		String conditionsOrderby = generator.conditionsOrderby(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsOrderby");
		System.out.println(conditionsOrderby);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsOrderby");
		
		System.out.println();
		String tableColumns = generator.tableColumns(modelClazz, tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableColumns");
		System.out.println(tableColumns);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableColumns");
		System.out.println();
		
		String conditionsWhere = generator.conditionsWhere(modelClazz, tableAliasName, modelAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsWhere");
		System.out.println(conditionsWhere);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsWhere");
		System.out.println();
		
		String selectListBy = generator.selectListBy(tableAliasName, modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectListBy");
		System.out.println(selectListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectListBy");
		System.out.println();
		
		String selectCountBy = generator.selectCountBy(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectCountBy");
		System.out.println(selectCountBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectCountBy");
		System.out.println();
		
		String selectOneBy = generator.selectOneBy(tableAliasName, modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectOneBy");
		System.out.println(selectOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectOneBy");
		System.out.println();
		
		String insertOne = generator.insertOne(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertOne");
		System.out.println(insertOne);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertOne");
		System.out.println();
		
		String insertList = generator.insertList(modelClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertList");
		System.out.println(insertList);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertList");
		System.out.println();
		
		String updateBy = generator.updateBy(modelClazz, modelAliasName, oldAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::updateBy");
		System.out.println(updateBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::updateBy");
		System.out.println();
		
		String deleteBy = generator.deleteBy(modelClazz, modelAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::deleteBy");
		System.out.println(deleteBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::deleteBy");
		System.out.println();
		
	}
}

