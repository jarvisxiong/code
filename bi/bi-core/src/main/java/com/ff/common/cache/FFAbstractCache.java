/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ff.common.cache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public abstract class FFAbstractCache<K,E>
{
	private Logger log = FFLogFactory.getLog(getClass());
	protected Map<K,E> cache = new HashMap<K,E>();
	
	
	public FFAbstractCache()
	{
		load();
		log.info("the cache is " + cache);
	}
	
    public void add(List<E> objList)
    {
    	if(!ValidatorUtil.isEmpty(objList))
    	{
        	for(E e : objList)
        	{
        		add(e);
        	}
    	}
    }
    
    public void add(E obj)
    {
    	K key = getKeyValue(obj);
    	this.cache.put(key, obj);
    }
    public void add(K key,E e)
    {
    	cache.put(key, e);
    }

	abstract public void load();

    public E get(String key)
	{
		return cache.get(key);
	}

    private K getKeyValue(E e)
    {
    	List<Field> fieldList = ReflectionUtil.getFieldsByAnnotion(e, FFCacheAnno.class);
    	
    	Field keyField = null;
    	for(Field field : fieldList)
    	{
    		FFCacheAnno meta = field.getAnnotation(FFCacheAnno.class);
    		if(meta.isKey())
    		{
    			keyField = field;
    			break;
    		}
     	}
    	
    	K value = null;
    	if(null == keyField)
    	{
    		log.error("can not find the key field,pls add annotion FFCahceMeta");
    	}
    	else
    	{
    		value = (K) ReflectionUtil.getValueByFieldName(e, keyField.getName());
    	}
    	return value;
    }
}
