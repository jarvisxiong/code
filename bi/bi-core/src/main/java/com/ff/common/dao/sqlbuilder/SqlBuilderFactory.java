package com.ff.common.dao.sqlbuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.ff.common.dao.Dao;
import com.ff.common.dao.annotation.FFDao;
import com.ff.common.dao.mapper.FFAutoMapper;
import com.ff.common.dao.mapper.FFAutoMapperFactory;
import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * 
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public class SqlBuilderFactory   
{
	private final static Logger log = FFLogFactory.getLog(SqlBuilderFactory.class);

	/**
	 * 
	 * 缓存TableMapper
	 */
	private static HashMap<Class<?>, FFEntityMeta> tableMetaCache = new HashMap<Class<?>, FFEntityMeta>(128);
	
	private static Map<String,ISqlBuilder> sqlBuiderMapCache = new HashMap<String,ISqlBuilder>();
	
	private static Map<String,Method> idMethodMapCache = new HashMap<String,Method>();
	
	private static Map<String,Class<?>> idMapEntityClass = new HashMap<String,Class<?>>();

	private static Map<String,Boolean> isHandleSqlMap = new HashMap<>();
	
	private static Map<String,String> allIDMapSql = new HashMap<String,String>();
	
 
	private static SqlBuilderFactory instance;
	
 	private SqlBuilderFactory()
	{

	}
 	
 	public static final String ID_FLAG = "##_##";
 	
 	public String handleMapperID(String id,Class<?> entityClass)
 	{
 		if(id.contains(Dao.class.getName()))
 		{
 			return id + ID_FLAG + entityClass.getName();
 		}
 		return id;
 	}
 	public String getMapperID(String id)
 	{
 		String mapID = id;
 		if(id.contains(ID_FLAG))
 		{
 			String[] ids = id.split(ID_FLAG);
 			mapID = ids[0];
 		}
 		return mapID; 
 	}
 	
 	private String getEntityClassName(String id)
 	{
 		String name = "";
 		if(id.contains(ID_FLAG))
 		{
 			String[] ids = id.split(ID_FLAG);
 			name = ids[1];
 		}
 		return name; 
 	}
 	
	public static synchronized SqlBuilderFactory getInstance()
	{
		if (null == instance)
		{
			instance = new SqlBuilderFactory();
		}
		return instance;
	}
	
	public boolean isHandle(String metaID)
	{
		Boolean handle = isHandleSqlMap.get(metaID);
		if(null != handle)
		{
			return handle;
		}
		boolean result = isAutoMapper(metaID);
		
		synchronized (isHandleSqlMap)
		{
			if(!isHandleSqlMap.containsKey(metaID))
			{
				isHandleSqlMap.put(metaID, false);
			}
			else
			{
				result = isHandleSqlMap.get(metaID);
			}
		}

		
		return result;
	}
	
	public boolean isAutoMapper(String metaID)
	{
		Method method = this.getMapperMethod(metaID);
		FFDao anno = method.getAnnotation(FFDao.class);
		if(null == anno)
		{
			return false;
		}
		if(anno.exclude())
		{
			return false;
		}
		return true;
	}
	
	public ISqlBuilder getSqlBuilder(String metaID)
	{
		ISqlBuilder builder = sqlBuiderMapCache.get(metaID);
		
		
		if(null != builder)
		{
			return builder;
		}
		if(isAutoMapper(metaID))
		{
			builder = new JoinSqlBuilder();
			sqlBuiderMapCache.put(metaID, builder);
		}
		
		return builder;
	}
	
	public void putSqlBuilder(String metaID,Class<?> clazz)
	{
		ISqlBuilder builder = null;
		try
		{
			builder = (ISqlBuilder) clazz.newInstance();
 			sqlBuiderMapCache.put(metaID, builder);
		}
		catch(Exception e)
		{
			log.error("can not find the class " + clazz,e);
			throw new RuntimeException();
		}
	}
	
	private Class<?> getMapperClass(String id)
	{
		int index = id.lastIndexOf(".");
		String className = id.substring(0,index);
		
		Class<?> clazz = null;
		try
		{
			clazz = Class.forName(className);
		}
		catch(Exception e)
		{
			log.error("can not find the class " + className,e);
			throw new RuntimeException();
		}
		
		return clazz;
	}
	
	private Method getMapperMethod(String id)
	{
		Method method = null;

		method = idMethodMapCache.get(id);
		if(null != method)
		{
			return method;
		}
		int index = id.lastIndexOf(".");
		String methodName = id.substring(index+1,id.length());
		
		Class<?> clazz = this.getMapperClass(id);
		
		
		for(Method e : clazz.getMethods())
		{
			if(e.getName().equals(methodName))
			{
				method = e;
				if(null != e.getAnnotation(FFDao.class))
				{
					method = e;
					break;
				}
 			}
		}
		synchronized (idMethodMapCache)
		{
			if(!idMethodMapCache.containsKey(id))
			{
				idMethodMapCache.put(id, method);
			}
			else
			{
				method = idMethodMapCache.get(id);
			}
		}

		
		return method;
		
	}
	
	public String getSql(String allId)
	{
		String sql = allIDMapSql.get(allId);
		if(null != sql)
		{
			return sql;
		}
		
		String mapperId = this.getMapperID(allId);
		Class<?> entityClass = this.getEntityClass(allId);
		sql = SqlBuilderFactory.getInstance().getSqlBuilder(mapperId).getSql(entityClass);
		
		synchronized (allIDMapSql)
		{
			log.info("the id is "+allId+",the sql is " + sql);
			if(!allIDMapSql.containsKey(allId))
			{
				allIDMapSql.put(allId, sql);
			}
			else
			{
				sql = allIDMapSql.get(allId);
			}
		}
		return sql;
	}
	
	public Class<?> getEntityClass(String allId)
	{
		
		Class<?> entityClazz = null;

		entityClazz = idMapEntityClass.get(allId);
		if(null != entityClazz)
		{
			return entityClazz;
		}
		
		String id = SqlBuilderFactory.getInstance().getMapperID(allId);
		String className = SqlBuilderFactory.getInstance().getEntityClassName(allId);
		
		Method method = this.getMapperMethod(id);
		
		FFDao methodAnno = null;
		if(null != method)
		{
			methodAnno = method.getAnnotation(FFDao.class);
		}
		
		if(null  != methodAnno)
		{
			entityClazz = methodAnno.type();
		}
		else
		{
			log.info("there is not dao annotation at method");
		}
		if(null == entityClazz)
		{
			FFDao classAnno = this.getMapperClass(id).getAnnotation(FFDao.class);

			if(null != classAnno)
			{
				entityClazz = classAnno.type();
			}
			else
			{
				log.info("there is not dao annotation at class");
			}
		}
		if(null == entityClazz)
		{
			if(ValidatorUtil.isEmpty(className))
			{
				Class<?> mapperClass = this.getMapperClass(id);
		        Type[] types = mapperClass.getGenericInterfaces();
		        for (Type type : types) {
		            if (type instanceof ParameterizedType) {
		                ParameterizedType t = (ParameterizedType) type;
		                if (t.getActualTypeArguments()[0] instanceof Class) 
		                {
		                	entityClazz = (Class<?>) t.getActualTypeArguments()[0];
		                    break;
		                }
		            }
		        }
			}
			else
			{
				try
				{
					entityClazz = Class.forName(className);
				} catch (ClassNotFoundException e)
				{
					log.info("can not find the class",e);

				}
			}

		}
		
		synchronized (idMapEntityClass)
		{
			if(!idMapEntityClass.containsKey(allId))
			{
				idMapEntityClass.put(allId, entityClazz);
			}
			else
			{
				entityClazz = idMapEntityClass.get(allId);
			}
		}
 
		return entityClazz;
	}
 
	/**
	 * 
	 * @param dtoClass
	 * @return
	 */
	public FFEntityMeta getEntityMeta(Class<?> dtoClass)
	{

		Field[] fields = null;

		FFEntityMeta entityMeta = null;
		synchronized (tableMetaCache)
		{
			entityMeta = tableMetaCache.get(dtoClass);
			if (entityMeta != null)
			{
				return entityMeta;
			}

			entityMeta = new FFEntityMeta();

			
			FFAutoMapper mapper = FFAutoMapperFactory.getInstance().getAutoMpper(dtoClass);

			entityMeta.setMapper(FFAutoMapperFactory.getInstance().getMapperName(dtoClass));
			entityMeta.setTableName(mapper.getTableName(dtoClass));
			entityMeta.setClazz(dtoClass);
			fields = ReflectionUtil.getAllFields(dtoClass);

			Map<String, FFFieldMeta> fieldMap = new HashMap<String, FFFieldMeta>();
			Map<String, FFFieldMeta> columnMap = new HashMap<String, FFFieldMeta>();
			List<FFFieldMeta> fieldMetaList = new ArrayList<FFFieldMeta>();

			List<String> idColumnList = new ArrayList<String>();
			
			String defaultIDCol = "id";
			
			for (Field field : fields)
			{
				if (mapper.isExcludle(field))
				{
					log.warn("the field was excludle " + field);
					continue;
				}

				FFFieldMeta fieldMeta = new FFFieldMeta();
				fieldMeta.setField(field);
				fieldMeta.setJdbcType(mapper.getJdbcType(field));
				fieldMeta.setColumnName(mapper.getColumName(field));
				fieldMeta.setKey(mapper.isKey(field));
				fieldMeta.setStrategy(mapper.getStrategy(field));

				if (mapper.isKey(field))
				{
					idColumnList.add(fieldMeta.getColumnName());
					 
				}
				if(field.getName().equals("id"))
				{
					defaultIDCol = fieldMeta.getColumnName();
				}

				fieldMap.put(field.getName(), fieldMeta);
				columnMap.put(fieldMeta.getColumnName(), fieldMeta);
				fieldMetaList.add(fieldMeta);

			}
			
			if(ValidatorUtil.isEmpty(idColumnList))
			{
				idColumnList.add(defaultIDCol);
			}
			
			entityMeta.setIdColumnList(idColumnList);
			entityMeta.setFieldMetaList(fieldMetaList);
			entityMeta.setFieldMap(fieldMap);
			entityMeta.setColumnMap(columnMap);
 
			tableMetaCache.put(dtoClass, entityMeta);
			return entityMeta;
		}
	}
	
	
 

}
