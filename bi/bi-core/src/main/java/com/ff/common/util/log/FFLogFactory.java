/**   
* @Title: FuegoLogFactory.java 
* @Package cn.fuego.common.log 
* @Description: TODO
* @author Tang Jun   
* @date 2014-11-27 下午10:50:05 
* @version V1.0   
*/ 
package com.ff.common.util.log;


 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class FFLogFactory 
{
   public static Logger getLog(Class clazz)
   {
 	   return LoggerFactory.getLogger(clazz);
   }
}
