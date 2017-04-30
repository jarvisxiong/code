package mybatis;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.portal.model.Catelog;

/**
 * @ClassName BuildMyBatisXMLAndSQLByClass
 * @Description TODO
 * @author li.biao
 * @date 2015-4-8
 */
public class BuildMyBatisXMLAndSQLByClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class c = Catelog.class;
		String tableName = "portal_catelog";  //mortgage_calculator
		List<Field> fields = getFieldByClass(c, true);
		createTableSql(tableName, fields);
	}
	
	private static void createTableSql(String tableName, List<Field> fields) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append("(\n");
		sql.append("\tid ").append("VARCHAR(32)").append(",\n");
		for (Field f : fields) {
			String type = f.getType();
			if ("VARCHAR".endsWith(type)) {
				if (f.getColumn().startsWith("FK")
						&& f.getColumn().endsWith("ID")) {
					type = "VARCHAR(44)";
				} else {
					type = "VARCHAR(50)";
				}
			} else if ("NUMERIC".equals(type)) {
				type = "DECIMAL(13,2)";
			}else if("TIMESTAMP".equals(type)){
				type="TIMESTAMP NULL DEFAULT NULL";
			}
			sql.append("\t").append(f.getColumn()).append(" ").append(type);
			if(!StringUtil.isEmpty(f.getComment())){
				sql.append(" COMMENT ").append("'").append(f.getComment()).append("'");
			}
			sql.append(",\n");
		}
		sql.append("\t").append("PRIMARY KEY(id)\n").append(");\n");
		System.out.println(sql.toString());
	}

	private static List<Field> getFieldByClass(Class c, boolean includeSuper) {
		List<Field> fields = new ArrayList<BuildMyBatisXMLAndSQLByClass.Field>();
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
		Comment c=field.getAnnotation(Comment.class);
		if (BaseEntity.class.isAssignableFrom(field.getType())
				|| (field.getName().equals("parent")&&field.getType().isAssignableFrom(Collection.class))) {
			fi = new Field();
			fi.setField(field.getName() + ".id");
			fi.setColumn(columnLowerCase(field.getName()) + "_id");
			if(field.getName().equals("createBy") || field.getName().equals("lastUpdateBy")){
				fi.setColumn(columnLowerCase(field.getName()));
			}
			fi.setType("VARCHAR");
			if(null != c){
				String comment=c.value();
				fi.setComment(comment);//设置
			}
		} else if (field.getType().isAssignableFrom(Collection.class)
				|| field.getType().isAssignableFrom(Map.class)) {
			// 集合类
			return null;
		} else {
			fi = new Field();
			fi.setField(field.getName());
			fi.setColumn(columnLowerCase(field.getName()));
			Class type = field.getType();
			if (BaseEntity.class.isAssignableFrom(type)) {
				// 外键
				fi.setType("VARCHAR");
			} else if (Byte.class.isAssignableFrom(type)
					|| byte.class.isAssignableFrom(type)) {
				fi.setType("INT");
			} else if (int.class.isAssignableFrom(type)
					|| Integer.class.isAssignableFrom(type)) {
				fi.setType("INT");
			} else if (long.class.isAssignableFrom(type)
					|| Long.class.isAssignableFrom(type)) {
				fi.setType("INT");
			} else if (short.class.isAssignableFrom(type)
					|| Short.class.isAssignableFrom(type)) {
				fi.setType("INT");
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
			if(null != c){
				String comment=c.value();
				fi.setComment(comment);//设置注释
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
		private String column;
		private String type;
		private String comment;//注释
		
		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
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
	
	//by ljw
	public static String getTableSql(Class clz,String tableName){
		List<Field> fields = getFieldByClass(clz, true);
		return createTableSqlExt(tableName, fields);
	}
	public static String createTableSqlExt(String tableName, List<Field> fields){
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append("(\n");
		for (Field f : fields) {
			String type = f.getType();
			if ("VARCHAR".endsWith(type)) {
				if (f.getColumn().startsWith("FK")
						&& f.getColumn().endsWith("ID")) {
					type = "VARCHAR(44)";
				} else {
					type = "VARCHAR(50)";
				}
			} else if ("NUMERIC".equals(type)) {
				type = "NUMERIC(11,2)";
			}
			sql.append("\t").append(f.getColumn()).append(" ").append(type)
					.append(",\n");
		}
		sql.append("\t").append("PRIMARY KEY(FID)\n").append(");\n");
		return sql.toString();
	}

}