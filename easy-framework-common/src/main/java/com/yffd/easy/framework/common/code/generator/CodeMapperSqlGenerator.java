package com.yffd.easy.framework.common.code.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.entity.CommonPartialTreeEntity;

/**
 * @Description  Mapper中的sql片段拼写生成器.
 * @Date		 2018年2月6日 下午2:56:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CodeMapperSqlGenerator extends CodeGenerator {
	public static final String NON_POJO_PROPERTY_NAME_SUFFIX = "Iter";
	
	protected List<String> makeWhereWithPojoPropsName(Class<?> pojoClazz, String tableAliasName, String paramAliasName, boolean autoLikeQuery) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		
		List<String> equalsList = new ArrayList<String>();
		List<String> likeList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyTypeName = propertyTypeClazz.getName();
			String propertyName = entry.getKey();
			// 过滤属性
			if(this.skipPropsName(propertyTypeClazz, propertyName)) continue;
						
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
			String parameterName = propertyName;
			if(null!=paramAliasName && !"".equals(paramAliasName)) {
				parameterName = paramAliasName + "." + parameterName;
			}
			
			if(String.class.getName().equals(propertyTypeName)) {
				if(autoLikeQuery && (propertyName.matches(".*((?i)n)ame.*"))) {
					if(autoLikeQuery) {
						String tmp = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> and " + columnName + " like CONCAT(CONCAT('%', #{"+parameterName+"}), '%') </if>";
						likeList.add(tmp);
					}
				} else {
					String tmp = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> and " + columnName + " = #{"+parameterName+"} </if>";
					equalsList.add(tmp);
				}
			} else if(Date.class.getName().equals(propertyTypeName)) {
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
	
	protected List<String> makeForeachWithPojoPropsName(Class<?> pojoClazz, String tableAliasName, String paramAliasName) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		
		List<String> inList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyTypeName = propertyTypeClazz.getName();
			String propertyName = entry.getKey();
			// 过滤属性
			if(this.skipPropsName(propertyTypeClazz, propertyName)) continue;
			
			if(propertyName.matches(".*((?i)i)d.*") || propertyName.matches(".*((?i)c)ode.*")) {
				
			} else {
				continue;
			}
			
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
			String parameterName = propertyName;
			if(!EasyStringCheckUtils.isEmpty(paramAliasName)) {
				parameterName = paramAliasName + "." + parameterName;
			}
			if("version".equals(propertyName)) {
				
			} else if("delFlag".equals(propertyName)) {
				
			} else if("createBy".equals(propertyName)) {
				
			} else if("createTime".equals(propertyName)) {
				
			} else if("updateBy".equals(propertyName)) {
				
			} else if("updateTime".equals(propertyName)) {
				
			} else if(Date.class.getName().equals(propertyTypeName)) {
				
			} else if(String.class.getName().equals(propertyTypeName)
					|| Byte.class.getName().equals(propertyTypeClazz)
					|| Character.class.getName().equals(propertyTypeClazz)
					|| Number.class.isAssignableFrom(propertyTypeClazz)
					|| propertyTypeClazz.isPrimitive()
					) {
				StringBuilder sb = new StringBuilder();
				parameterName += NON_POJO_PROPERTY_NAME_SUFFIX;
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
	
	public List<String> makeWhereWithNonPojoPropsName(Class<?> pojoClazz, String tableAliasName, String paramAliasName) {
//		List<String> pojoList = this.makeWhereWithPojoPropsName(pojoClazz, tableAliasName, paramAliasName, false);
//		retList.addAll(pojoList);
		List<String> inList = this.makeForeachWithPojoPropsName(pojoClazz, tableAliasName, paramAliasName);
		List<String> retList = new ArrayList<String>();
		if(inList.size()>0) {
			retList.add("<!-- In query -->");
			retList.addAll(inList);
		}
		return retList;
	}
	
	public List<String> makeUpdateSetWithPojoPropsName(Class<?> pojoClazz, String modelAliasName) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		
		List<String> setList = new ArrayList<String>();
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyTypeName = propertyTypeClazz.getName();
			String propertyName = entry.getKey();
			// 过滤属性
			if(this.skipPropsNameForUpdate(propertyTypeClazz, propertyName)) continue;
						
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			// set字段
			if("id".equals(propertyName) || "createBy".equals(propertyName) || "createTime".equals(propertyName)) {
				
			} else if("version".equals(propertyName)) {
				String tmp = "VERSION = VERSION + 1, ";
				setList.add(tmp);
			} else if(String.class.getName().equals(propertyTypeName)) {
				String parameterName = propertyName;
				if(!EasyStringCheckUtils.isEmpty(modelAliasName)) parameterName = modelAliasName + "." + parameterName;
				String columnName_new = columnName + " = " + "#{"+parameterName+"}, ";
				String tmp_new = "<if test=\""+parameterName+" != null and "+parameterName+" != ''\"> "+columnName_new+" </if>";
				setList.add(tmp_new);
			} else {
				String parameterName = propertyName;
				if(!EasyStringCheckUtils.isEmpty(modelAliasName)) parameterName = modelAliasName + "." + parameterName;
				String columnName_new = columnName + " = " + "#{"+parameterName+"}, ";
				String tmp_new = "<if test=\""+parameterName+" != null\"> "+columnName_new+" </if>";
				setList.add(tmp_new);
			}
		}
		return setList;
	}
	
	public String conditionsWhere(Class<?> pojoClazz, String tableAliasName, String pojoAliasName, String mapAliasName) {
		List<String> pojoList = this.makeWhereWithPojoPropsName(pojoClazz, tableAliasName, pojoAliasName, true);
		List<String> mapList = this.makeWhereWithNonPojoPropsName(pojoClazz, tableAliasName, mapAliasName);
		
		StringBuilder sb = new StringBuilder();
		if(null!=pojoList && pojoList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				sb.append("<if test=\""+pojoAliasName+" != null\">").append("\r\n");
			}
			for(String line : pojoList) {
				sb.append(this.fmtLines(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				sb.append(this.fmtLines(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 动态组装复合查询条件 -->").append("\r\n");
		tmp.append("<sql id=\"conditions_where\">").append("\r\n");
		tmp.append(this.fmtLines(sb.toString(), "\t", ""));
		tmp.append("</sql>").append("\r\n");
		
		return tmp.toString();
	}
	
	public String updateBy(Class<?> pojoClazz, String pojoAliasName, String oldPojoAliasName, String mapAliasName) {
		List<String> setList = this.makeUpdateSetWithPojoPropsName(pojoClazz, pojoAliasName);
		List<String> oldPojoList = this.makeWhereWithPojoPropsName(pojoClazz, "", oldPojoAliasName, false);
		List<String> mapList = this.makeWhereWithNonPojoPropsName(pojoClazz, "", mapAliasName);
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- 更新 -->").append("\r\n");
		tmp.append("<update id=\"updateBy\" parameterType=\"java.util.Map\">").append("\r\n");
		tmp.append("\t").append("update <include refid=\"table_name\" />").append("\r\n");
		tmp.append("\t").append("<set>").append("\r\n");
		// set字段
		if(null!=setList && setList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				tmp.append("\t").append("<if test=\""+pojoAliasName+" != null\">").append("\r\n");
			}
			for(String line : setList) {
				tmp.append(this.fmtLines(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		tmp.append("\t").append("</set>").append("\r\n");
		tmp.append("\t").append("<where>").append("\r\n");
		
		// 条件字段
		tmp.append("\t").append("<choose>").append("\r\n");
		tmp.append("\t").append("<when test=\""+oldPojoAliasName+" != null or "+mapAliasName+" != null\">").append("\r\n");
		if(null!=oldPojoList && oldPojoList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(oldPojoAliasName)) {
				tmp.append("\t").append("<if test=\""+oldPojoAliasName+" != null\">").append("\r\n");
			}
			for(String line : oldPojoList) {
				tmp.append(this.fmtLines(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(oldPojoAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				tmp.append("\t").append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				tmp.append(this.fmtLines(line, "\t\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				tmp.append("\t").append("</if>").append("\r\n");
			}
		}
		
		tmp.append("\t").append("</when>").append("\r\n");
		tmp.append("\t").append("<otherwise>").append("\r\n");
		tmp.append("\t").append("<!-- 防止没有参数传递，导致全部更新 -->").append("\r\n");
		tmp.append("\t").append("and 1=2").append("\r\n");
		tmp.append("\t").append("</otherwise>").append("\r\n");
		tmp.append("\t").append("</choose>").append("\r\n");
		
		tmp.append("\t").append("</where>").append("\r\n");
		tmp.append("</update>").append("\r\n");
		return tmp.toString();
	}
	
	public String deleteBy(Class<?> pojoClazz, String pojoAliasName, String mapAliasName) {
		List<String> pojoList = this.makeWhereWithPojoPropsName(pojoClazz, "", pojoAliasName, false);
		List<String> mapList = this.makeWhereWithNonPojoPropsName(pojoClazz, "", mapAliasName);
		StringBuilder sb = new StringBuilder();
		if(null!=pojoList && pojoList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				sb.append("<if test=\""+pojoAliasName+" != null\">").append("\r\n");
			}
			for(String line : pojoList) {
				sb.append(this.fmtLines(line, "\t", ""));
			}
			if(!EasyStringCheckUtils.isEmpty(pojoAliasName)) {
				sb.append("</if>").append("\r\n");
			}
		}
		//
		if(null!=mapList && mapList.size()>0) {
			if(!EasyStringCheckUtils.isEmpty(mapAliasName)) {
				sb.append("<if test=\""+mapAliasName+" != null\">").append("\r\n");
			}
			for(String line : mapList) {
				sb.append(this.fmtLines(line, "\t", ""));
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
		
		tmp.append("\t").append("<choose>").append("\r\n");
		tmp.append("\t").append("<when test=\""+pojoAliasName+" != null or "+mapAliasName+" != null\">").append("\r\n");
		tmp.append(this.fmtLines(sb.toString(), "\t", ""));
		tmp.append("\t").append("</when>").append("\r\n");
		tmp.append("\t").append("<otherwise>").append("\r\n");
		tmp.append("\t").append("<!-- 防止没有参数传递，导致全部删除 -->").append("\r\n");
		tmp.append("\t").append("and 1=2").append("\r\n");
		tmp.append("\t").append("</otherwise>").append("\r\n");
		tmp.append("\t").append("</choose>").append("\r\n");
		tmp.append("\t").append("</where>").append("\r\n");
		
		tmp.append("</delete>");
		return tmp.toString();
	}
	
	public String tableName(Class<?> pojoClazz) {
		String name = pojoClazz.getSimpleName();
		name = this.fmtPojoName(pojoClazz, null, null);
		name = this.property2column(name);
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
	
	public String conditionsOrderby() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 排序条件 -->").append("\r\n");
		sb.append("<sql id=\"conditions_orderby\">").append("\r\n");
		
		sb.append("\t").append("<choose>").append("\r\n");
		sb.append("\t").append("\t")
		.append("<when test=\"orderBy != null and orderBy !=''\">")
		.append(" ORDER BY #{orderBy} ")
		.append("</when>").append("\r\n");
		
		sb.append("\t").append("\t")
		.append("<otherwise>")
		.append(" ORDER BY id desc ")
		.append("</otherwise>").append("\r\n");
		sb.append("\t").append("</choose>").append("\r\n");
		
		sb.append("</sql>").append("\r\n");
		return sb.toString();
	}
	
	public String tableColumns(Class<?> pojoClazz, String tableAliasName) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		int num = 0;
		StringBuilder sb = new StringBuilder();
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyName = entry.getKey();
			// 过滤属性
			if(!this.filterPropsName(propertyTypeClazz, propertyName)) continue;
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			if(null!=tableAliasName && !"".equals(tableAliasName)) {
				columnName = tableAliasName + "." + columnName;
			}
						
			columnName += " as " + propertyName;
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
		tmp.append(this.fmtLines(content, "\t", ""));
		tmp.append("</sql>").append("\r\n");
		return tmp.toString();
	}
	
	public String selectListBy(String tableAliasName, Class<?> pojoClazz) {
		String asTableAliasName = "";
		if(null!=tableAliasName) asTableAliasName = " as " + tableAliasName + " ";
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 条件查询 -->").append("\r\n");
//		sb.append("<select id=\"selectListBy\" parameterType=\"java.util.Map\" resultMap=\"resutlId\">").append("\r\n");
		sb.append("<select id=\"selectListBy\" parameterType=\"java.util.Map\" resultType=\""+pojoClazz.getName()+"\">").append("\r\n");
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
		sb.append("<select id=\"selectCountBy\" parameterType=\"java.util.Map\" resultType=\"java.lang.Integer\">").append("\r\n");
		sb.append("\t").append("select count(1) from ").append("\r\n");
		sb.append("\t").append("<include refid=\"table_name\"/>").append(asTableAliasName).append("\r\n");
		sb.append("\t").append("<where>").append("\r\n");
		sb.append("\t").append("\t").append("<include refid=\"conditions_where\" />").append("\r\n");
		sb.append("\t").append("</where>").append("\r\n");
		sb.append("</select>").append("\r\n");
		return sb.toString();
	}
	
	public String selectOneBy(String tableAliasName, Class<?> pojoClazz) {
		String asTableAliasName = "";
		if(null!=tableAliasName) asTableAliasName = " as " + tableAliasName + " ";
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- 单条查询 -->").append("\r\n");
		sb.append("<select id=\"selectOneBy\" parameterType=\"java.util.Map\" resultType=\""+pojoClazz.getName()+"\">").append("\r\n");
		sb.append("\t").append("select <include refid=\"table_columns\" /> ").append("\r\n");
		sb.append("\t").append("from <include refid=\"table_name\"/>").append(asTableAliasName).append("\r\n");
		sb.append("\t").append("<where>").append("\r\n");
		sb.append("\t").append("\t").append("<include refid=\"conditions_where\" />").append("\r\n");
		sb.append("\t").append("</where>").append("\r\n");
		sb.append("</select>").append("\r\n");
		return sb.toString();
	}
	
	public String insertOneBy(Class<?> pojoClazz) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyName = entry.getKey();
			// 过滤属性
			if("id".equals(propertyName)) continue;
			if(!this.filterPropsName(propertyTypeClazz, propertyName)) continue;
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			columns.append(columnName).append(", ");
			params.append("#{").append(propertyName).append("}, ");
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
		tmp.append("<insert id=\"insertOneBy\" parameterType=\"java.util.Map\" keyProperty=\"id\" useGeneratedKeys=\"true\">").append("\r\n");
		tmp.append("\t").append("insert into").append("\r\n");
		tmp.append("\t").append("<include refid=\"table_name\" />").append("\r\n");
		tmp.append(this.fmtLines(content, "\t", ""));
		tmp.append("</insert>");
		return tmp.toString();
	}
	
	public String insertListBy(Class<?> pojoClazz) {
		LinkedHashMap<String, Class<?>> propsNameMap = this.sortPropsNames(pojoClazz);
		if(null==propsNameMap || propsNameMap.size()==0) return null;
		
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		int num =0;
		for(Iterator<Entry<String, Class<?>>> it = propsNameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, Class<?>> entry = (Entry<String, Class<?>>)it.next();
			Class<?> propertyTypeClazz = entry.getValue();
			String propertyName = entry.getKey();
			// 过滤属性
			if("id".equals(propertyName)) continue;
			if(!this.filterPropsName(propertyTypeClazz, propertyName)) continue;
			String columnName = this.property2column(propertyName);
			if(null==columnName || "".equals(columnName)) continue;
			columns.append(columnName).append(", ");
			params.append("#{item.").append(propertyName).append("},");
			
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
		tmp.append("<insert id=\"insertListBy\" parameterType=\"java.util.Map\" keyProperty=\"id\" useGeneratedKeys=\"true\">").append("\r\n");
		tmp.append("\t").append("insert into <include refid=\"table_name\" />").append("\r\n");
		tmp.append(this.fmtLines(content, "\t", ""));
		tmp.append("</insert>").append("\r\n");
		return tmp.toString();
	}
	
	public String voConditionsWhere(Class<?> pojoClazz, String tableAliasName, String pojoAliasName) {
		List<String> pojoList = this.makeWhereWithPojoPropsName(pojoClazz, tableAliasName, pojoAliasName, true);
		
		StringBuilder sb = new StringBuilder();
		if(null!=pojoList && pojoList.size()>0) {
			for(String line : pojoList) {
				sb.append(this.fmtLines(line, "\t", ""));
			}
		}
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<!-- vo动态组装复合查询条件 -->").append("\r\n");
		tmp.append("<sql id=\"vo_conditions_where\">").append("\r\n");
		tmp.append(this.fmtLines(sb.toString(), "\t", ""));
		tmp.append("</sql>").append("\r\n");
		
		return tmp.toString();
	}
	
	
	public static void main(String[] args) {
		CodeMapperSqlGenerator generator = new CodeMapperSqlGenerator();
		
		String tableAliasName = "t";
		String pojoAliasName = "entity";
		String oldAliasName = "entityOld";
		String mapAliasName = "propsMap";
		Class<?> pojoClazz = CommonPartialTreeEntity.class;
		
		String tableName = generator.tableName(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableName");
		System.out.println(tableName);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableName");
		System.out.println();
		 
		String conditionsLimit = generator.conditionsLimit();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsLimit");
		System.out.println(conditionsLimit);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsLimit");
		System.out.println();
		
		String conditionsOrderby = generator.conditionsOrderby();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsOrderby");
		System.out.println(conditionsOrderby);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsOrderby");
		
		System.out.println();
		String tableColumns = generator.tableColumns(pojoClazz, tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableColumns");
		System.out.println(tableColumns);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableColumns");
		System.out.println();
		
		String conditionsWhere = generator.conditionsWhere(pojoClazz, tableAliasName, pojoAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsWhere");
		System.out.println(conditionsWhere);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsWhere");
		System.out.println();
		
		String selectListBy = generator.selectListBy(tableAliasName, pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectListBy");
		System.out.println(selectListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectListBy");
		System.out.println();
		
		String selectCountBy = generator.selectCountBy(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectCountBy");
		System.out.println(selectCountBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectCountBy");
		System.out.println();
		
		String selectOneBy = generator.selectOneBy(tableAliasName, pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectOneBy");
		System.out.println(selectOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectOneBy");
		System.out.println();
		
		String insertOneBy = generator.insertOneBy(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertOneBy");
		System.out.println(insertOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertOneBy");
		System.out.println();
		
		String insertListBy = generator.insertListBy(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertListBy");
		System.out.println(insertListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertListBy");
		System.out.println();
		
		String updateBy = generator.updateBy(pojoClazz, pojoAliasName, oldAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::updateBy");
		System.out.println(updateBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::updateBy");
		System.out.println();
		
		String deleteBy = generator.deleteBy(pojoClazz, pojoAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::deleteBy");
		System.out.println(deleteBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::deleteBy");
		System.out.println();
		
	}
	
	public void writeToConsole(String tableAliasName, String pojoAliasName, String oldAliasName, String mapAliasName, Class<?> pojoClazz) {
		CodeMapperSqlGenerator generator = new CodeMapperSqlGenerator();
		
		String tableName = generator.tableName(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableName");
		System.out.println(tableName);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableName");
		System.out.println();
		 
		String conditionsLimit = generator.conditionsLimit();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsLimit");
		System.out.println(conditionsLimit);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsLimit");
		System.out.println();
		
		String conditionsOrderby = generator.conditionsOrderby();
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsOrderby");
		System.out.println(conditionsOrderby);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsOrderby");
		
		System.out.println();
		String tableColumns = generator.tableColumns(pojoClazz, tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::tableColumns");
		System.out.println(tableColumns);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::tableColumns");
		System.out.println();
		
		String conditionsWhere = generator.conditionsWhere(pojoClazz, tableAliasName, pojoAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::conditionsWhere");
		System.out.println(conditionsWhere);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::conditionsWhere");
		System.out.println();
		
		String selectListBy = generator.selectListBy(tableAliasName, pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectListBy");
		System.out.println(selectListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectListBy");
		System.out.println();
		
		String selectCountBy = generator.selectCountBy(tableAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectCountBy");
		System.out.println(selectCountBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectCountBy");
		System.out.println();
		
		String selectOneBy = generator.selectOneBy(tableAliasName, pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::selectOneBy");
		System.out.println(selectOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::selectOneBy");
		System.out.println();
		
		String insertOneBy = generator.insertOneBy(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertOneBy");
		System.out.println(insertOneBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertOneBy");
		System.out.println();
		
		String insertListBy = generator.insertListBy(pojoClazz);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::insertListBy");
		System.out.println(insertListBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::insertListBy");
		System.out.println();
		
		String updateBy = generator.updateBy(pojoClazz, pojoAliasName, oldAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::updateBy");
		System.out.println(updateBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::updateBy");
		System.out.println();
		
		String deleteBy = generator.deleteBy(pojoClazz, pojoAliasName, mapAliasName);
		System.out.println(">>>>>>>>>>>>>>>>>>>start::deleteBy");
		System.out.println(deleteBy);
		System.out.println(">>>>>>>>>>>>>>>>>>>end::deleteBy");
		System.out.println();
		
	}
}

