package mybatis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.portal.model.Catelog;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class InitFilesByModel {
	
	public static void main(String[] args) throws IOException {
		
		String workSpacePath = "F:\\git_workspace";
		String project = "Portal\\portal-core";
		Class c = Catelog.class;//NewPerson.class;
		String tableName = "portal_catelog";
		
		String alias = "t";
		createMyBatisXML(workSpacePath, project, c, tableName,alias);
		//createMapper(workSpacePath, project,c);
		//createService(workSpacePath, project, c);
	}
	
	/**
	 * 生成代码调用(同上main单独生成)
	 * @param workSpacePath	项目路径
	 * @param project		项目名
	 * @param clz			对象class
	 * @param tableName		数据库表名
	 * @param alias			数据库表名的别名
	 * @throws IOException
	 */
	public static void execute(String workSpacePath,String project,Class clz,String tableName,String alias) throws IOException{
		createMyBatisXML(workSpacePath, project, clz, tableName,alias);
		createMapper(workSpacePath, project, clz);
		createService(workSpacePath, project, clz);
	}
	
	private static <T extends BaseEntity> void createMyBatisXML(String workSpacePath,String project,Class<T> c,String tableName,String alias) throws IOException{
		String className = c.getName();
		String dir = workSpacePath + "/" + project + "/" + "src/main/resources/";
		dir += getModuleName(c).replaceAll("\\.", "/") + "/mapper/";
		File fileDir = new File(dir);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		if(alias == null || ("").equals(alias)){
			alias = "D";
		}
		String fileName = c.getSimpleName() + "Mapper.xml";
		File file = new File(dir + "/" + fileName);
		if(file.exists()){
			System.out.println("xml文件己存在");
			return;
		}
		
		String tmpString = getTmpFileDesc("mybatis.xml.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			String nameSpace = getModuleName(c) + ".mapper." + c.getSimpleName()+"Mapper";
			List<Field> fields = getFieldByClass(c, true);
			String baseColumnListSQL = getSelectSql(c, tableName, fields,alias);
			String conditionSQL = getConditionSQL(c, tableName, fields,alias);			
			String insertSQL = createInsertSql(c, tableName, fields);
			String insertSelectiveSQL = createInsertSelectiveSQL(c, tableName, fields);			
			String updateSQL = createUpdateSql(c, tableName, fields);
			String deleteSQL = createDeleteSql(tableName);
			String updateSelectiveSql = createUpdateSelectiveSql(c, tableName, fields);
			String getByIdSQL = createGetById(c, tableName, fields,alias);
			String selectSQL = getSelectSql(c, tableName, fields,alias);
			tmpString = tmpString.replaceAll("\\$\\{alias\\}", alias);
			tmpString = tmpString.replaceAll("\\$\\{dbName\\}", tableName);
			tmpString = tmpString.replaceAll("\\$\\{nameSpace\\}", nameSpace);
			tmpString = tmpString.replaceAll("\\$\\{className\\}", className);
			tmpString = tmpString.replaceAll("\\$\\{baseColumnListSQL\\}", baseColumnListSQL);
			tmpString = tmpString.replaceAll("\\$\\{conditionSQL\\}", conditionSQL);
			tmpString = tmpString.replaceAll("\\$\\{insertSQL\\}", insertSQL);
			tmpString = tmpString.replaceAll("\\$\\{insertSelectiveSQL\\}", insertSelectiveSQL);
			tmpString = tmpString.replaceAll("\\$\\{updateSQL\\}", updateSQL);			
			tmpString = tmpString.replaceAll("\\$\\{deleteSQL\\}", deleteSQL);
			tmpString = tmpString.replaceAll("\\$\\{updateSelectiveSql\\}", updateSelectiveSql);
			
			tmpString = tmpString.replaceAll("\\$\\{getByIdSQL\\}", getByIdSQL);
			tmpString = tmpString.replaceAll("\\$\\{selectSQL\\}", selectSQL);
			tmpString = tmpString.replaceAll("\\$\\{selectCountSQL\\}", getSelectCountSql(c, tableName, fields,alias));
		}
		FileWriter fw = new FileWriter(file);
		fw.write(tmpString);
		fw.close();
	}
	
	private static String getTmpFileDesc(String tmpFileName) throws IOException{
		//String path ="F:\\git_workspace\\mybatis\\src\\main\\java\\mybatis\\" + tmpFileName;
		String path ="src/main/java/mybatis/" + tmpFileName;
//		InputStream is = InitFilesByModel.class.getResourceAsStream(path);
		InputStream is = new FileInputStream(new File(path));
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] bytes = new byte[bis.available()];
		bis.read(bytes, 0, bis.available());
		return new String(bytes);
	}
	
	private static <T extends BaseEntity> String getModuleName(Class<T> c){
		//com.wuyizhiye.basedata.images.model.Photo
		String className = c.getName();
		className = className.substring(0, className.lastIndexOf("."));
		className = className.substring(0, className.lastIndexOf("."));
		return className;
	}
	
	private static String createInsertSelectiveSQL(Class c, String tableName,
			List<Field> fields){
		StringBuilder sql = new StringBuilder();
		sql.append("\tINSERT INTO ").append(tableName).append(" \n")
			.append("\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
		
		sql.append("\t\t<if test=\"id != null\">").append("id,</if>\n");
		
		for(Field f : fields){
			sql.append("\t\t<if test=\"").append(f.getField1()).append(" != null\" >")
				.append(f.getColumn()).append(",").append("</if>\n");
		}
		sql.append("\t</trim>\n")
			.append("\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n");
		
		sql.append("\t\t<if test=\"id != null\">");
		sql.append("#{id,jdbcType=VARCHAR},</if>\n");
		
		for(Field f : fields){
			sql.append("\t\t<if test=\"").append(f.getField1()).append(" != null\" >");
			sql.append("#{")
			.append(f.getField()).append(",jdbcType=")
			.append(f.getType()).append("},");
			sql.append("</if>\n");
		}
		sql.append("\t</trim>");
		return (sql.toString());
	}
	
	private static String getSelectSql(Class c, String tableName,
			List<Field> fields,String alias) {
		StringBuilder sql = new StringBuilder();
		sql.append("\t").append(alias).append(".id, \n");
		for (Field f : fields) {
			sql.append("\t\t").append(alias).append(".")
				.append(f.getColumn()).append(" AS \"")
					.append(f.getField()).append("\",\n");
		}
		sql.deleteCharAt(sql.lastIndexOf(",\n"));
		return (sql.toString());
	}
	
	private static String getConditionSQL(Class c, String tableName,
			List<Field> fields,String alias) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"null!=params\" >\n");
		
		sql.append("\t\t").append("<if test=\"params.id != null and '' != params.id\">\n");
		sql.append("\t\t\t").append("and ").append(alias).append(".id = ").append("#{params.id,jdbcType=VARCHAR}\n");
		
		sql.append("\t\t</if>\n");
		for (Field f : fields) {
			sql.append("\t\t").append("<if test=\"params.").append(f.getField1())
				.append(" != null and '' != params.").append(f.getField1()).append("\">\n");
			sql.append("\t\t\t").append("and ").append(alias).append(".").append(f.getColumn())
			.append(" = ").append("#{params.")
			.append(f.getField()).append(",jdbcType=")
			.append(f.getType()).append("}\n");
			sql.append("\t\t</if>\n");
		}	    
		
		//设置默认都查询del_flag = 0正常未删除
		sql.append("\t\t").append("<if test=\"params.delFlag == null or '' == params.delFlag or null == params\" >\n");
		sql.append("\t\t\t").append("and ").append(alias).append(".del_flag = 0\n").append("\t\t</if>\n");
		
		sql.append("\t</if>");
		return (sql.toString());
	}
	
	private static String createGetById(Class c, String tableName,
			List<Field> fields,String alias) {
		StringBuilder sql = new StringBuilder(getSelectSql(c, tableName, fields,alias));
		sql.append("\n\t\tWHERE ").append(alias).append(".FID = #{id}");
		return (sql.toString());
	}	
	
	private static String getSelectCountSql(Class c, String tableName,
			List<Field> fields,String alias){
		StringBuilder sql = new StringBuilder();
		sql.append("\tSELECT \n");
		sql.append("\t\t\tCOUNT(").append(alias).append(".FID)").append("\n");
		sql.append("\t\tFROM ").append(tableName).append(" ").append(alias);
		return (sql.toString());
	}

	private static String createDeleteSql(String tableName) {
		StringBuilder sql = new StringBuilder();
		sql.append("\tDELETE FROM ").append(tableName)
				.append(" WHERE id = #{id,jdbcType=VARCHAR}");
		return (sql.toString());
	}

	private static String createInsertSql(Class c, String tableName,
			List<Field> fields) {
		StringBuilder sql = new StringBuilder();
		sql.append("\tINSERT INTO ").append(tableName).append("\n");
		sql.append("\t\t(\n");
		sql.append("\t\t\tid,\n");
		for (Field f : fields) {
			sql.append("\t\t\t").append(f.getColumn()).append(",\n");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append("\t\t) VALUES\n\t\t(\n");
		sql.append("\t\t\t").append("#{id,jdbcType=VARCHAR},\n");
		for (Field f : fields) {
			sql.append("\t\t\t").append("#{").append(f.getField())
					.append(",jdbcType=").append(f.getType()).append("},\n");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append("\t\t)");
		return (sql.toString());
	}

	private static String createUpdateSelectiveSql(Class c, String tableName,
			List<Field> fields) {
		StringBuilder sql = new StringBuilder();
		sql.append("\tUPDATE ").append(tableName).append("\n");
		sql.append("\t<set>\n");
		for (Field f : fields) {
			sql.append("\t\t<if test=\"").append(f.getField1()).append(" != null\" >\n");
			sql.append("\t\t\t").append(f.getColumn())
				.append(" = ").append("#{").append(f.getField())
				.append(",jdbcType=").append(f.getType()).append("},\n");
			sql.append("\t\t</if>\n");
		}
		sql.append("\t</set>\n\twhere id = #{id,jdbcType=VARCHAR}");
		
		return (sql.toString());
	}
	
	private static String createUpdateSql(Class c, String tableName,
			List<Field> fields) {
		StringBuilder sql = new StringBuilder();
		sql.append("\tUPDATE ").append(tableName).append("\n");
		sql.append("\t\t<set>\n");
		for (Field f : fields) {
			sql.append("\t\t\t").append(f.getColumn()).append(" = ").append("#{")
					.append(f.getField()).append(",jdbcType=")
					.append(f.getType()).append("},\n");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append("\t\t</set>\n");
		sql.append("\t\tWHERE id = #{id,jdbcType=VARCHAR}");
		return (sql.toString());
	}

	private static List<Field> getFieldByClass(Class c, boolean includeSuper) {
		List<Field> fields = new ArrayList<InitFilesByModel.Field>();
		Class sc = c.getSuperclass();
		if (includeSuper) {
			if (BaseEntity.class.isAssignableFrom(sc)) {
				fields.addAll(getFieldByClass(sc, includeSuper));
			}
		}
		java.lang.reflect.Field[] fs = c.getDeclaredFields();
		for (java.lang.reflect.Field f : fs) {
			if ((Modifier.isPrivate(f.getModifiers()) ||
					Modifier.isProtected(f.getModifiers()))
					&& !Modifier.isStatic(f.getModifiers())
					&& !"id".equals(f.getName())) {
				Field fi = initField(f);
				if (fi != null) {
					fields.add(fi);
				}
			}
		}
		return fields;
	}

	private static Field initField(java.lang.reflect.Field field) {
		Field fi = null;
		if (BaseEntity.class.isAssignableFrom(field.getType())
				|| (field.getName().equals("parent")&&field.getType().isAssignableFrom(Collection.class))) {
			fi = new Field();
			fi.setField(field.getName() + ".id");
			fi.setField1(field.getName());
			fi.setColumn(columnLowerCase(field.getName()) + "_id");
			if(field.getName().equals("createBy") || field.getName().equals("lastUpdateBy")){
				fi.setColumn(columnLowerCase(field.getName()));
			}
			fi.setType("VARCHAR");
		} else if (field.getType().isAssignableFrom(Collection.class)
				|| field.getType().isAssignableFrom(Map.class)) {
			// 集合类
			return null;
		} else {
			fi = new Field();
			fi.setField(field.getName());
			fi.setField1(field.getName());
			fi.setColumn(columnLowerCase(field.getName()));
			Class type = field.getType();
			if (BaseEntity.class.isAssignableFrom(type)) {
				// 外键
				fi.setType("VARCHAR");
			} else if (Byte.class.isAssignableFrom(type)
					|| byte.class.isAssignableFrom(type)) {
				fi.setType("INTEGER");
			} else if (int.class.isAssignableFrom(type)
					|| Integer.class.isAssignableFrom(type)) {
				fi.setType("INTEGER");
			} else if (long.class.isAssignableFrom(type)
					|| Long.class.isAssignableFrom(type)) {
				fi.setType("INTEGER");
			} else if (short.class.isAssignableFrom(type)
					|| Short.class.isAssignableFrom(type)) {
				fi.setType("INTEGER");
			} else if (float.class.isAssignableFrom(type)
					|| Float.class.isAssignableFrom(type)) {
				fi.setType("FLOAT");
			} else if (double.class.isAssignableFrom(type)
					|| Double.class.isAssignableFrom(type)) {
				fi.setType("FLOAT");
			} else if (BigDecimal.class.isAssignableFrom(type)) {
				fi.setType("NUMERIC");
			} else if (boolean.class.isAssignableFrom(type)
					|| Boolean.class.isAssignableFrom(type)) {
				fi.setType("BOOLEAN");
			} else if (Date.class.isAssignableFrom(type)) {
				fi.setType("TIMESTAMP");
			} else if (type.isEnum()) {
				fi.setType("VARCHAR");
			} else if (Collection.class.isAssignableFrom(type)
					|| Map.class.isAssignableFrom(type) || type.isArray()) {
				// 集合或者数组
				return null;
			} else {
				fi.setType("VARCHAR");
			}
		}
		return fi;
	}
	
	public static String columnLowerCase(String column){
		String str = "";
		for (int i = 0; i < column.length(); i++) {
			char c = column.charAt(i);
			if (!Character.isLowerCase(c)) {
				if(i == 0){
					str += (c + "").toLowerCase();
				}else{
					str += ("_" + c + "").toLowerCase();
				}
			} else {
				str += c;
			}
		}
		return str;
	}
	
	public static class Field {
		private String field;
		private String field1;
		private String column;
		private String type;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getField1() {
			return field1;
		}

		public void setField1(String field1) {
			this.field1 = field1;
		}

		public String getColumn() {
			return "`"+column+"`";
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

	private static <T extends BaseEntity> void createMapper(String workSpacePath,String project,Class<T> c) throws IOException{
		String dir = workSpacePath + "/" + project + "/src/main/java/";
		String pg = getModuleName(c) + "." + "mapper";
		dir += getModuleName(c).replaceAll("\\.", "/") + "/mapper/";
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String javaName = c.getSimpleName() + "Mapper";
		File javaFile = new File(dir + javaName + ".java");
		
		String superPackage = pg;
		String superName = javaName;
		String component = javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
		pg = getModuleName(c) + ".mapper";
		
		javaFile = new File(dir + javaName + ".java");
		if(javaFile.exists()){
			System.out.println("mapper己存在");
			return ;
		}
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		String tmpString = getTmpFileDesc("mapper.java.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			String nameSpace = getModuleName(c) + ".dao." + c.getSimpleName()+"Dao";
			tmpString = tmpString.replaceAll("\\$\\{package\\}", pg);
			tmpString = tmpString.replaceAll("\\$\\{date\\}", date);
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", javaName);
			
			tmpString = tmpString.replaceAll("\\$\\{super\\}", superName);
			tmpString = tmpString.replaceAll("\\$\\{superPackage\\}", superPackage);
			tmpString = tmpString.replaceAll("\\$\\{component\\}", component);
			tmpString = tmpString.replaceAll("\\$\\{nameSpace\\}", nameSpace);
		}
		FileWriter fw = new FileWriter(javaFile);
		fw.write(tmpString);
		fw.close();
		
	}

	private static <T extends BaseEntity> void createService(String workSpacePath,String project,Class<T> c) throws IOException{
		String dir = workSpacePath + "/" + project + "/" + "src/main/java/";
		String pg = getModuleName(c) + "." + "service";
		dir += getModuleName(c).replaceAll("\\.", "/") + "/service/";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String javaName = c.getSimpleName() + "Service";
		File javaFile = new File(dir + javaName + ".java");
		if(javaFile.exists()){
			System.out.println("service己存在");
			return ;
		}
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		String tmpString = getTmpFileDesc("service.java.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			tmpString = tmpString.replaceAll("\\$\\{package\\}", pg);
			tmpString = tmpString.replaceAll("\\$\\{date\\}", date);
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", javaName);
			tmpString = tmpString.replaceAll("\\$\\{entity\\}", c.getName());
			tmpString = tmpString.replaceAll("\\$\\{entityName\\}", c.getSimpleName());
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", c.getSimpleName());
		}
		FileWriter fw = new FileWriter(javaFile);
		fw.write(tmpString);
		fw.close();
		String superPackage = pg;
		String superName = javaName;
		String component = javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
		pg = getModuleName(c) + ".service.impl";
		dir = workSpacePath + "/" + project + "/" + "src/main/java/";
		dir += pg.replaceAll("\\.", "/") + "/";
		javaName = c.getSimpleName() + "ServiceImpl";
		javaFile = new File(dir + javaName + ".java");
		if(javaFile.exists()){
			System.out.println("serviceImpl己存在");
			return ;
		}
		dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		String daojavaName = c.getSimpleName() + "Mapper";
		String daojavapg = getModuleName(c) + ".mapper." + daojavaName;
		tmpString = getTmpFileDesc("serviceImpl.java.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			tmpString = tmpString.replaceAll("\\$\\{package\\}", pg);
			tmpString = tmpString.replaceAll("\\$\\{date\\}", date);
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", javaName);
			tmpString = tmpString.replaceAll("\\$\\{superName\\}",  superName.substring(0, 1).toLowerCase() + superName.substring(1));
			tmpString = tmpString.replaceAll("\\$\\{super\\}", superName);
			tmpString = tmpString.replaceAll("\\$\\{superPackage\\}", superPackage);
			tmpString = tmpString.replaceAll("\\$\\{component\\}", component);
			tmpString = tmpString.replaceAll("\\$\\{baseService\\}", BaseCrudServiceImpl.class.getName());
			tmpString = tmpString.replaceAll("\\$\\{baseServiceName\\}", BaseCrudServiceImpl.class.getSimpleName());
			tmpString = tmpString.replaceAll("\\$\\{daoName\\}", daojavaName);
			tmpString = tmpString.replaceAll("\\$\\{daojavapg\\}", daojavapg);
			tmpString = tmpString.replaceAll("\\$\\{lowDaoName\\}", daojavaName.substring(0, 1).toLowerCase() + daojavaName.substring(1));
		}
		fw = new FileWriter(javaFile);
		fw.write(tmpString);
		fw.close();
	}
	
	private static <T extends BaseEntity> void createController(String workSpacePath,String project,Class<T> c) throws IOException{
		String dir = workSpacePath + "/" + project + "-web/" + "src/main/java/";
		String pg = getModuleName(c) + "." + "dao";
		dir += getModuleName(c).replaceAll("\\.", "/") + "/dao/";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String javaName = c.getSimpleName() + "Dao";
		File javaFile = new File(dir + javaName + ".java");
		if(javaFile.exists()){
			System.out.println("dao己存在");
			return ;
		}
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		String tmpString = getTmpFileDesc("dao.java.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			String nameSpace = getModuleName(c) + ".dao." + c.getSimpleName()+"Dao";
			tmpString = tmpString.replaceAll("\\$\\{package\\}", pg);
			tmpString = tmpString.replaceAll("\\$\\{date\\}", date);
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", javaName);
		}
		FileWriter fw = new FileWriter(javaFile);
		fw.write(tmpString);
		fw.close();
		
		String superPackage = pg;
		String superName = javaName;
		String component = javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
		pg = getModuleName(c) + ".dao.impl";
		dir = workSpacePath + "/" + project + "/" + "src/main/java/";
		dir += pg.replaceAll("\\.", "/") + "/";
		javaName = c.getSimpleName() + "DaoImpl";
		
		javaFile = new File(dir + javaName + ".java");
		if(javaFile.exists()){
			System.out.println("daoImpl己存在");
			return ;
		}
		dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		tmpString = getTmpFileDesc("daoImpl.java.temp");
		if(tmpString!=null && !"".equals(tmpString)){
			String nameSpace = getModuleName(c) + ".dao." + c.getSimpleName()+"Dao";
			tmpString = tmpString.replaceAll("\\$\\{package\\}", pg);
			tmpString = tmpString.replaceAll("\\$\\{date\\}", date);
			tmpString = tmpString.replaceAll("\\$\\{javaName\\}", javaName);
			
			tmpString = tmpString.replaceAll("\\$\\{super\\}", superName);
			tmpString = tmpString.replaceAll("\\$\\{superPackage\\}", superPackage);
			tmpString = tmpString.replaceAll("\\$\\{component\\}", component);
			tmpString = tmpString.replaceAll("\\$\\{nameSpace\\}", nameSpace);
		}
		fw = new FileWriter(javaFile);
		fw.write(tmpString);
		fw.close();
	}
}
