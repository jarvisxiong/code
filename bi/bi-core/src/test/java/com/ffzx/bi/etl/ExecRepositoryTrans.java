package com.ffzx.bi.etl;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * 
 * @Description: java调用kettle 数据库型资料库中的转换
 * @author chen 此类是调用kettle 4.2版本的测试类
 * @version 1.0,
 * @date 2013-5-23 下午02:40:50
 */
public class ExecRepositoryTrans {
	/**
	 * 本测试类慎用！！！！！！！
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// D:\java\workspace\sics\src\config\kettle
		String fname = "src/main/resources/etl/order/order_main.kjb";
		//runJob(fname);
		executeEtlJob("order","/ffzx_bi/order",0);

	}

	/**
	 * java 调用kettle 转换
	 */
	public static void runTransfer() {
		Trans trans = null;
		try {
			// 初始化
			String fName = "src/tjfolder/getCurrentJobTime.ktr";
			// 转换元对象
			KettleEnvironment.init();// 初始化
			EnvUtil.environmentInit();
			TransMeta transMeta = new TransMeta(fName);
			// 转换
			trans = new Trans(transMeta);
			// 执行转换
			trans.execute(null);
			// 等待转换执行结束
			trans.waitUntilFinished();
			// 抛出异常
			if (trans.getErrors() > 0) {
				throw new Exception("There are errors during transformation exception!(传输过程中发生异常)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * java 调用 kettle 的job
	 * 
	 * @param jobname
	 *            如： String fName= "D:\\kettle\\informix_to_am_4.ktr";
	 */
	public static void runJob(String jobname) {
		try {
			KettleEnvironment.init();
			// jobname 是Job脚本的路径及名称
			JobMeta jobMeta = new JobMeta(jobname, null);
			Job job = new Job(null, jobMeta);
			// 向Job 脚本传递参数，脚本中获取参数值：${参数名}
			// job.setVariable(paraname, paravalue);
			job.start();
			job.waitUntilFinished();
			if (job.getErrors() > 0) {
				throw new Exception("There are errors during job exception!(执行job发生异常)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 配置数据源 调用资源库中的相关job 、transfer
	 */
	public static void dbResource() {
		String transName = "t1";

		try {
			KettleEnvironment.init();
			DatabaseMeta dataMeta = new DatabaseMeta("KettleDBRep", "MSSQL", "Native", "127.0.0.1", "etl", "1433", "sa",
					"bsoft");
			KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
			repInfo.setConnection(dataMeta);
			KettleDatabaseRepository rep = new KettleDatabaseRepository();
			rep.init(repInfo);
			rep.connect("admin", "admin");

			RepositoryDirectoryInterface dir = new RepositoryDirectory();
			dir.setObjectId(rep.getRootDirectoryID());

			TransMeta tranMeta = rep.loadTransformation(rep.getTransformationID(transName, dir), null);
			Trans trans = new Trans(tranMeta);
			trans.execute(null);
			trans.waitUntilFinished();
		} catch (KettleException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 配置数据源 调用资源库中的相关transfer
	 */
	public static void dbResourceForTran(String name,String directoryName) {
		String host = System.getProperty("kettle.host");
		String db = System.getProperty("kettle.db");
		String port = System.getProperty("kettle.port");
		String username = System.getProperty("kettle.username");
		String password = System.getProperty("kettle.password");
		String repositoryUsername = System.getProperty("kettle.repository.username");
		String repositoryassword = System.getProperty("kettle.repository.password");
		KettleDatabaseRepository rep = null ;
		try {
			KettleEnvironment.init();
			DatabaseMeta dataMeta = new DatabaseMeta("ffzx_bi", "MYSQL", "JDBC", host, db, port, username,password);

			KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
			repInfo.setConnection(dataMeta);
		    rep = new KettleDatabaseRepository();
			rep.init(repInfo);
			rep.connect(repositoryUsername, repositoryassword);
			RepositoryDirectoryInterface directory = rep.findDirectory(directoryName);
			//创建ktr元对象
			TransMeta tranMeta = rep.loadTransformation(name, directory, null, true, null ) ;
			Trans trans = new Trans(tranMeta);
			trans.execute(null);
			trans.waitUntilFinished();
		} catch (KettleException e) {
			e.printStackTrace();
		}finally {
			if (rep != null) {
				if (rep.isConnected()) {
					rep.disconnect();
					rep = null;
				}
			}
		}
	}
	/**
	 * 
	 * executeEtlJob:(这里用一句话描述这个方法的作用). <br/>  
	 *  
	 * @author zhiyong.zhang
	 * @param name  job或tran的名字
	 * @param directoryName   资源库目录
	 * @param etlType  0:为执行job 其他执行转换
	 * @since JDK 1.7
	 */
	public static void executeEtlJob(String name,String directoryName,int etlType){
		
		if(0==etlType){
			dbResourceForJob(name,directoryName);
		}else{
			dbResourceForTran(name,directoryName);
		}
		
	}

	/**
	 * 配置数据源 调用资源库中的相关job
	 */
	public static void dbResourceForJob(String name,String directoryName) {
		String host = System.getProperty("kettle.host");
		String db = System.getProperty("kettle.db");
		String port = System.getProperty("kettle.port");
		String username = System.getProperty("kettle.username");
		String password = System.getProperty("kettle.password");
		String repositoryUsername = System.getProperty("kettle.repository.username");
		String repositoryassword = System.getProperty("kettle.repository.password");
		KettleDatabaseRepository rep = null ;
		try {
			KettleEnvironment.init();
			DatabaseMeta dataMeta = new DatabaseMeta("ffzx_bi", "MYSQL", "JDBC", host, db, port, username,password);
			KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
			repInfo.setConnection(dataMeta);
		    rep = new KettleDatabaseRepository();
			rep.init(repInfo);
			rep.connect(repositoryUsername, repositoryassword);

			RepositoryDirectoryInterface dir = rep.findDirectory(directoryName);
			JobMeta  jobMeta = rep.loadJob(name, dir, null,null);
			Job job = new Job(rep, jobMeta);
			job.execute(0, null);
			job.waitUntilFinished();
			
			rep.disconnect();
			rep = null;
		} catch (KettleException e) {
			e.printStackTrace();
		}finally {
			if (rep != null) {
				if (rep.isConnected()) {
					rep.disconnect();
					rep = null;
				}
			}
		}
	}


}