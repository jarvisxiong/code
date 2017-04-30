/**   
* @Title: JsonConvert.java 
* @Package cn.fuego.common.util.format 
* @Description: TODO
* @author Tang Jun   
* @date 2014-12-4 上午12:51:02 
* @version V1.0   
*/ 
package com.ff.common.util.format;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;

import com.ff.common.util.log.FFLogFactory;

 /** 
 * @ClassName: JsonConvert 
 * @Description: TODO
 * @author Tang Jun
 * @date 2014-12-4 上午12:51:02 
 *  
 */
public class JsonConvert
{
	private static final Logger log = FFLogFactory.getLog(DataTypeConvert.class);

	private static final boolean  isIgnore = true;
	public static String ObjectToJson(Object object)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		 String json = "";
		try
		{
			json = mapper.writeValueAsString(object);
		} catch (Exception e)
		{
			log.error("object to json failed",e);
		}
		return json;
	}
	public static <T> T jsonToObject(String json,Class<T> clazz,Class<?>... eType)
	{
		ObjectMapper mapper = null;
		if(isIgnore)
		{
			mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		else
		{
			mapper = new ObjectMapper();
		}
		
		T	rspObj = null;

		try
		{
			Class<?>[] eTypes = eType;
			
			JavaType javaType;
			if(eTypes.length == 1)
			{
				javaType = mapper.getTypeFactory().constructParametricType(clazz, eTypes[0]);
			}
			else
			{
				JavaType midType = mapper.getTypeFactory().constructParametricType(eTypes[eTypes.length-2], eTypes[eTypes.length-1]);
				for(int i=eType.length-3;i>0;i--)
				{
					midType = mapper.getTypeFactory().constructParametricType(eTypes[i], midType);
				}
				javaType = midType;
				javaType = mapper.getTypeFactory().constructParametricType(clazz,midType);
			}
  
			rspObj = mapper.readValue(json,javaType);
			
		} catch (Exception e)
		{
			log.error("json to object failed,the json is " + json,e);
		}
		return  rspObj;
	}
 
	public static <T> T jsonToObject(String json,Class<T> clazz)
	{
		ObjectMapper mapper = null;
		if(isIgnore)
		{
			mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		else
		{
			mapper = new ObjectMapper();
		}
		T	rspObj = null;
		try
		{
			rspObj = mapper.readValue(json,clazz);
			
		} catch (Exception e)
		{
			log.error("json to object failed,the json is " + json,e);
		}
		return (T) rspObj;
	}
}
