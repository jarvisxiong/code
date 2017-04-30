/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 * 
 */
package com.ff.common.dao.mapper;

import org.springframework.stereotype.Component;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 */
@Component(FFAutoMapper.SameMapper)
public class FFAutoMapperSameImpl extends FFAutoMapperAbstract
{
	@Override
	public String field2Column(String name)
	{
		
		return name;
	}
}
