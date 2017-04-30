/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月25日 下午4:47:46
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.adel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月25日 下午4:47:46
 * @version V1.0
 */
public class TestJdbc {

	/***
	 * 
	 * @param args
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月25日 下午4:47:47
	 * @version V1.0
	 * @return 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String url ="jdbc:sqlserver://123.207.252.106;DatabaseName=FPC_Hotel";
		  url = "jdbc:sqlserver://123.207.252.106:1433;DatabaseName=FPC_Hotel";

		  /*try {   
		   Class.forName(driver);
		   Connection conn=(Connection) DriverManager.getConnection(url,"sa","adel@123");
		   PreparedStatement pstmt=(PreparedStatement) conn.prepareStatement("select * from user");
		   ResultSet rs=pstmt.executeQuery();
		   while(rs.next()){
		   }
		   rs.close();
		   pstmt.close();
		   conn.close();
		  } catch (ClassNotFoundException e) {   
		   e.printStackTrace();
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }*/
		  
		  
		  Connection dbConn;

		  try {
		   Class.forName(driver);
		   dbConn = (Connection) DriverManager.getConnection(url,"sa","adel@123");
		   PreparedStatement pstmt=(PreparedStatement) dbConn.prepareStatement("select * from [user]");
		   
		   ResultSet rs=pstmt.executeQuery();
		   while(rs.next()){
			   System.out.println("=============="+rs);
		   }
		   System.out.println("Connection Successful!");  //如果连接成功 控制台输出Connection Successful!
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	
	
	

}
