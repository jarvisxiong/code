/**   
* @Title: ReflectionUtil.java 
* @Package cn.fuego.util 
* @Description: TODO
* @author Tang Jun   
* @date 2014-9-24 下午06:16:47 
* @version V1.0   
*/ 
package com.ff.common.util.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;

import com.ff.common.util.format.DataTypeConvert;
import com.ff.common.util.log.FFLogFactory;

/** 
 * @ClassName: ReflectionUtil 
 * @Description: TODO
 * @author Tang Jun
 * @date 2014-9-24 下午06:16:47 
 *  
 */

public class ReflectionUtil
{
	private static final Logger log = FFLogFactory.getLog(ReflectionUtil.class);

	public static Class<?> getTypeByFieldName(Class<?> clazz,String fieldName) throws NoSuchFieldException, SecurityException
	{
		Class filedClass = getFieldByName(clazz, fieldName).getType();
		
		return filedClass;
	}
	
	public static <T> T newInstance(Class<T> clazz)
	{
		T obj;
		try
		{
			obj = clazz.newInstance();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} 
		return obj;
	}
	
	public static Field[] getAllFields(Object obj)
	{
		Field[] fields = getAllFields(obj.getClass());
		return fields;
	}
	public static Field[] getAllFields(Class<?> objClass)
	{
		List<Field> fields = new ArrayList<Field>();
		Class<?> clazz = objClass;  
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {    
            try {    
            	Field[] temp = clazz.getDeclaredFields();
            	if(null != temp && temp.length>0)
            	{
            		for(Field field : temp)
            		{
                     	fields.add(field);
             		}

            	}
             } catch (Exception e) {    
                log.warn("get fields failed",e);          
            }     
        } 
 
		
		return (Field[])fields.toArray(new Field[fields.size()]);
	}
	
	public static Field getFieldByName(Class<?> objClass,String fieldName)
	{
 		Class<?> clazz = objClass;  
 		for(; objClass != Object.class ; objClass = objClass.getSuperclass()) {    
            try {    
            	Field temp = objClass.getDeclaredField(fieldName);
            	if(null != temp)
            	{
            		return temp;

            	}
             } catch (Exception e) {    
     			log.info("get filed failed. the class is  " + clazz + " the field name is " + fieldName );
            }     
        } 
		log.error("get filed failed. the class is  " + clazz + " the field name is " + fieldName );
		return null;
	}
	public static <T extends Annotation>  List<Field> getFieldsByAnnotion(Object obj,Class<T> annotionClass)
	{
		Field[] fields = getAllFields(obj);
		List<Field> fieldList = new ArrayList<Field>();
		for(Field field : fields)
		{
			T annotion = field.getAnnotation(annotionClass);
			if(null != annotion)
			{
				fieldList.add(field);
			}
		}
		return fieldList;
	}
	
	public static <T extends Annotation>  List<T> getAnnotionList(Class objClass,Class<T> annotionClass)
	{
		Field[] fields = getAllFields(objClass);
		List<T> annotionList = new ArrayList<T>();
		for(Field field : fields)
		{
			T annotion = field.getAnnotation(annotionClass);
			if(null != annotion)
			{
				annotionList.add(annotion);
			}
		}
		return annotionList;
	}
	
	public static <T extends Annotation>  List<T> getAnnotionList(Object obj,Class<T> annotionClass)
	{
		Field[] fields = getAllFields(obj);
		List<T> annotionList = new ArrayList<T>();
		for(Field field : fields)
		{
			T annotion = field.getAnnotation(annotionClass);
			if(null != annotion)
			{
				annotionList.add(annotion);
			}
		}
		return annotionList;
	}
	public static Object getValueByFieldName(Object obj,String fieldName)
	{
		Object result = null;
		if(null == obj)
		{
			return null;
		}
		try
		{
			MetaObject meta = SystemMetaObject.forObject(obj);
			result = meta.getValue(fieldName);
 		}
		catch (Exception e)
		{
			log.error("get value from obj failed,the field name is " + fieldName,e);
		}
		return result;

	}
	
	public static List<?> getListByFieldName(List<?> objList,String fieldName)
	{
		List result = new ArrayList();
		for(Object obj : objList)
		{
			Object value = getValueByFieldName(obj, fieldName);
			result.add(value);
		}
		return result;
	}
	
 
	public static String ObjectToStr(Object obj)
	{
		String str = null;
		if(null != obj)
		{
			str = String.valueOf(obj);
		}
		return str;
	}

	public static boolean isInterface(Class c, String szInterface)
	{
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++)
		{
			if (face[i].getName().equals(szInterface))
			{
				return true;
			} else
			{
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++)
				{
					if (face1[x].getName().equals(szInterface))
					{
						return true;
					} else if (isInterface(face1[x], szInterface))
					{
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass())
		{
			return isInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}
	
	public static Object convertToType(Object value,Class clazz)
	{
		Object object = null;

		try
		{
			Class fieldClass = clazz;
			if(null == value)
			{
				log.warn("the value is null");
				return null;
			}
			
			/**如果类型一致，或者为子类型，就不需要转换*/
			if(value.getClass().equals(fieldClass) || isInterface(value.getClass(),fieldClass.getName()))
			{
				object = value;
			}
			else
			{
				/**这里主要是为让查询过滤条件的时候方便，当传入类型不一致的时候，尽可能的转换为所需要的类型
				 * 当传入类型是 String的时候 把String转换为所需要的类型
				 * 当传人类型是 集合类型的时候，把集合元素的类型转换为所需要的类型，
				 */
				if(isInterface(value.getClass(),Collection.class.getName()))
				{
					Collection<Object> listObject = new ArrayList<Object>();
					Collection<Object> oldList = (Collection<Object>) value;
					for(Object obj : oldList)
					{
						if(!obj.getClass().equals(fieldClass))
						{
							String objValue = obj.toString();
							listObject.add(DataTypeConvert.convertStrToObject(obj, fieldClass));	
						}
						else
						{
							listObject.add(obj);	
						}
						
	 				}
					object = listObject;
				}
				else if(value instanceof String)
				{
					String strValue = value.toString();
					object = DataTypeConvert.convertStrToObject(strValue, fieldClass);	
				}
				else
				{
					String strValue = String.valueOf(value);
					object = DataTypeConvert.convertStrToObject(value, fieldClass);	
				}
			}
			

		}
		catch(Exception e)
		{
			log.error("can not convert to right type.the class is " + clazz );
			log.error("convert data failed",e);
			throw new RuntimeException(e);
		}
		
	 
		
		return object;
	}
	public static Object convertToFieldObject(Class clazz,String fieldName,Object value)  
	{

		Object object = null;

		try
		{
			Class fieldClass = getFieldByName(clazz, fieldName).getType();
			object = convertToType(value,fieldClass);

		}
		catch(Exception e)
		{
			log.error("can not convert to right type.the class is " + clazz + " the field name is " + fieldName + "the value is " + value);
			log.error("convert data failed",e);
			throw new RuntimeException(e);
		}
		
	 
		
		return object;
	}
	
    public static void setObjectField(Object obj, String fieldName, Object value)
    {
    	try
    	{
    		if(null == obj)
    		{
    			log.warn("the obj is empty");
    			return;
    		}
    		if(null == fieldName)
    		{
    			log.warn("the fieldName is empty");
    			return;
    		}
    		
        	MetaObject meta = SystemMetaObject.forObject(obj);

        	Class<?> type = meta.getSetterType(fieldName);
  			
        	Object valueObject = value;
        	if(null != value && !value.getClass().equals(type) && !isInterface(value.getClass(),type.getName()))
			{
        		valueObject = convertToFieldObject(obj.getClass(),fieldName,value);
			}
        	
        	meta.setValue(fieldName, valueObject);
         
    	}
    	catch(Exception e)
    	{
    		log.error("set value failed. the class is " + obj.getClass().toString() + "the filedName is " + fieldName + "the value is " +value);
    		throw new RuntimeException(e);
    	}

 
    }
	public static List<Field> getStringFields(Object obj)
	{
		List<Field> fieldList = new ArrayList<Field>();
		if(null != obj)
		{
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field : fields)
			{
 				if(field.getType() == String.class)
				{
					fieldList.add(field);
				}
	 			
	 		}
		}
		return fieldList;
	}
	
	public static List<Field> getListFields(Object obj)
	{
		List<Field> fieldList = new ArrayList<Field>();
		if(null != obj)
		{
			Field[] fields = getAllFields(obj.getClass());
			for(Field field : fields)
			{
 				if(field.getType() == List.class)
				{
					fieldList.add(field);
				}
	 			
	 		}
		}
		return fieldList;
	}
	
	public static Type getListObjectType(Field field)
	{
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		if(null != pt && pt.getActualTypeArguments().length >0)
		{
			Type type = pt.getActualTypeArguments()[0];
			return type;
		}
		return null;
	}
	
	/**
	 * 获取自定义对象属性
	 * @param obj
	 * @return
	 */
	public static List<Field> getObjectFields(Object obj)
	{
		List<Field> fieldList = new ArrayList<Field>();

		Field[] fields = ReflectionUtil.getAllFields(obj);
		for(Field field : fields)
		{
			if(field.getType() != List.class && field.getType() != String.class && !field.getType().isPrimitive())
			{
				fieldList.add(field);
			}
		}
		return fieldList;
	}
	
    public static Class<Object> getSuperClassGenricType(final Class clazz)
    {
    	return getSuperClassGenricType(clazz,0);
    }
    public static Class<Object> getSuperClassGenricType(final Class clazz,int index)
	{

		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType))
		{
			log.error("can not get the class");
			return Object.class;
		}
		// 返回表示此类型实际类型参数的 Type 对象的数组。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0)
		{
			log.error("can not get the class");

			return Object.class;
		}
		if (!(params[index] instanceof Class))
		{
			log.error("can not get the class");
			return Object.class;
		}

		return (Class) params[index];
	}

}
