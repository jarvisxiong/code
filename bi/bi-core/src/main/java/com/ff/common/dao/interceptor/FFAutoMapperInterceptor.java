/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.interceptor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;

import com.ff.common.dao.sqlbuilder.ISqlBuilder;
import com.ff.common.dao.sqlbuilder.SqlBuilderFactory;
import com.ff.common.util.log.FFLogFactory;

/**
 * @Description
 * @author tangjun
 * @date 2016年6月28日
 */

//@Intercepts({@Signature(type = Executor.class, method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class,ResultHandler.class})})
@Intercepts(
		{ @Signature(method="handleResultSets", type=ResultSetHandler.class, args={Statement.class}),
		 @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})}
		)  
public class FFAutoMapperInterceptor  implements Interceptor, Serializable
{
	private final static Logger log = FFLogFactory.getLog(FFAutoMapperInterceptor.class);
 
	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{

		 if(invocation.getTarget() instanceof Executor)
		 {
		        Object[] objects = invocation.getArgs();
		        MappedStatement mappedStatement = (MappedStatement) objects[0];
		        MetaObject msObject = SystemMetaObject.forObject(mappedStatement);
	            Object para = invocation.getArgs()[1];
 		        {
 		        	String metaId = SqlBuilderFactory.getInstance().getMapperID(mappedStatement.getId());
  
	  		        Class<?> entityClass = SqlBuilderFactory.getInstance().getEntityClass(mappedStatement.getId());

 		            if (mappedStatement.getSqlSource() instanceof ProviderSqlSource) 
		            {
 		            	Class<?> providerType = (Class<?>) msObject.getValue("sqlSource.providerType");
 	  					
 	  					SqlBuilderFactory.getInstance().putSqlBuilder(metaId, providerType);
  						String newSql = SqlBuilderFactory.getInstance().getSql(mappedStatement.getId());
  						
 		                msObject.setValue("sqlSource", this.buildXmlSource(mappedStatement.getConfiguration(), newSql));
 		                SqlBuilderFactory.getInstance().getSqlBuilder(metaId).handleResult(mappedStatement,entityClass);
 		            }
 		            else 
 		            {
 		            	 boolean isMapper = SqlBuilderFactory.getInstance().isHandle(metaId);
 		            	 if(isMapper)
 		            	 {
  		                    Object parameter = invocation.getArgs()[1];
  		                    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
  		            		String originalSql = boundSql.getSql().trim();
  		            		
  	  						String newSql = SqlBuilderFactory.getInstance().getSql(mappedStatement.getId());

  		            		originalSql += newSql;
  		            		msObject.setValue("sqlSource", this.buildXmlSource(mappedStatement.getConfiguration(), originalSql));
  	 		                SqlBuilderFactory.getInstance().getSqlBuilder(metaId).handleResult(mappedStatement,entityClass);
  		            	 }
 		            }
 		            
 		            ISqlBuilder sqlBuilder = SqlBuilderFactory.getInstance().getSqlBuilder(metaId);

		            if(null != sqlBuilder)
		            {
	 		           sqlBuilder.prepare(entityClass, para);
		            }

		        }
		 }
		
		// 传递给下一个拦截器处理
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties)
	{
 	}

	private SqlSource buildSqlSource(Configuration configuration, String originalSql, Class<?> parameterType)
	{
		SqlSourceBuilder builder = new SqlSourceBuilder(configuration);
		
		return builder.parse(originalSql, parameterType, null);
	}
	
	private SqlSource buildXmlSource(Configuration configuration, String xmlSql)
	{
		XMLLanguageDriver languageDriver = new XMLLanguageDriver();
		SqlSource dynamicSqlSource = languageDriver.createSqlSource(configuration, "<script>\n\t" + xmlSql + "</script>", null);
		
		return dynamicSqlSource;
	}
	
 
}
