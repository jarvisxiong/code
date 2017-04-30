package com.ff.common.dao.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;

import com.ff.common.dao.sqlbuilder.SqlBuilderFactory;
import com.ff.common.util.log.FFLogFactory;

public class FFConfiguration extends Configuration
{
	private Logger log = FFLogFactory.getLog(getClass());

	public FFConfiguration()
	{
		super();
		mapperRegistry = new FFMapperRegistry(this);
	}

	public <T> void addMapper(Class<T> type)
	{
		mapperRegistry.addMapper(type);
	}

	public MappedStatement getMappedStatement(String id)
	{
		return this.getMappedStatement(id, true);
	}
	public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements)
	{
		if (validateIncompleteStatements)
		{
			buildAllStatements();
		}
		MappedStatement ms = null;
		
		
		
		try
		{
			ms = mappedStatements.get(id);
		}
		catch(Exception e)
		{
			log.trace("can not find the id " + id);
		}
		if(null == ms)
		{
			ms = mappedStatements.get(SqlBuilderFactory.getInstance().getMapperID(id));
 			Builder builder = new Builder(this,id,ms.getSqlSource(),ms.getSqlCommandType());
			ms = builder.build();
		}
		return ms;
	}
}
