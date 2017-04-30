/**
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 * 
 */
package com.ff.common.util.calc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.ff.common.util.log.FFLogFactory;

import net.sourceforge.jeval.Evaluator;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 */
public class CalcUtil
{
	private Logger log = FFLogFactory.getLog(getClass());
	private String para = "\\#\\{(.+?)\\}";

	private Pattern pattern;
	public CalcUtil()
	{
 		this.pattern = Pattern.compile(para);
	}
//	public CalcUtil(String para)
//	{
//		this.para = para;
//		this.pattern = Pattern.compile("\\#\\{(.+?)\\}");
//	}
	
	public double calc(String formula,Map<String,Object> paraMap)
	{
		double result = 0;
		Evaluator eval = new Evaluator();
		
		if(null != paraMap)
		{
			for(String key : paraMap.keySet())
			{
				Object value = paraMap.get(key);
				if(null == value)
				{
					eval.putVariable(key, "0");
							
				}
				else
				{
					eval.putVariable(key, String.valueOf(value));
				}
			}
		}
		try
		{
			result = Double.valueOf(eval.evaluate(formula));
			
			if(Double.isNaN(result) || Double.isInfinite(result))
			{
				result = 0;
			}
			
		} catch (Exception e)
		{
			log.error("calcular failed. the formula is " + formula + ", the para is " + paraMap,e);
			throw new RuntimeException(e);
		} 
		
		
		return result;
	}
	
	public String handleFormula(String formula,Map<String,String> paraMap)
	{
		String result = formula;
		for(String key : paraMap.keySet())
		{
			Object value = paraMap.get(key);
			
			result = result.replace("#{"+key+"}", String.valueOf(value));
 		}
		return result;
	}
	
	public Set<String> getVariable(String formula)
	{
		Set<String> paraList = new HashSet<String>();
		Matcher m = pattern.matcher(formula);

		while (m.find()) {
			paraList.add(m.group(1));
		}
		
		return paraList;
	}
	
 
	
 
}
