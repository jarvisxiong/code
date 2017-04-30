package com.ffzx.permission;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.ftp.session.FtpFileInfo;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.ffzx.commerce.framework.utils.FTPUtil;

/**
 * Ftp2Test
 * 
 * @author CMM
 *
 *         2016年5月6日 下午6:00:29
 */
public class FtpTest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext-core.xml");
		
		
		MessageChannel ftpUploadChannel = (MessageChannel)applicationContext.getBean("ftpUploadChannel");
		try {
			InputStream in = new FileInputStream("D:\\testftpdir\\1.txt");
			boolean uploadOriginal = FTPUtil.ftpUpload(ftpUploadChannel, in, "333.txt", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		MessageChannel ftpDownloadChannel = (MessageChannel)applicationContext.getBean("ftpDownloadChannel");
		boolean downloadOriginal = FTPUtil.ftpDownload(ftpDownloadChannel, "333.txt", "test");*/
		
		/*
		MessageChannel ftpDeleteloadChannel = (MessageChannel)applicationContext.getBean("ftpRemoveChannel");
		boolean deleteloadOriginal = FTPUtil.ftpRemove(ftpDeleteloadChannel, "333.txt", "test");*/
		
		
		/*MessageChannel listRemoteFileChannel = (MessageChannel)applicationContext.getBean("listRemoteFileChannel");
		QueueChannel listedRemoteFileChannel = (QueueChannel)applicationContext.getBean("listedRemoteFileChannel");
		listRemoteFileChannel.send(MessageBuilder.withPayload("/test").build());
		
		List<Message<?>> aa = listedRemoteFileChannel.purge(null);
		for(Message<?> mm : aa){
			System.out.println(mm.getPayload());
			List<FtpFileInfo> testList = (List<FtpFileInfo>)mm.getPayload();
			for(FtpFileInfo ffi : testList){
				System.err.println(ffi.getRemoteDirectory() + ffi.getFilename());
			}
		}*/
		
		applicationContext.close();
	}
}
