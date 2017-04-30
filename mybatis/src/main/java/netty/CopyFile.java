/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 下午5:37:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package netty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 下午5:37:54
 * @version V1.0
 */
public class CopyFile {

	/***
	 * 
	 * @param args
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月10日 下午5:37:54
	 * @version V1.0
	 * @return 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inFile = "F:\\web.config";
		String outFile = "F:\\web_copy_1.config";
		//创建输入输出流
		FileInputStream fin = new FileInputStream(inFile);
		FileOutputStream fout = new FileOutputStream(outFile);
		//创建输入输出通道
		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();
		//创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while(true){
			//clear方法重设缓冲区，使它可以接受读入的数据
			buffer.clear();
			//从通道读入缓冲区
			int r = fcin.read(buffer);
			//read方法返回读取字节数，可能为零，如果该通道已达到末尾
			if(r==-1){
				break;
			}
			//flip方法让缓冲区可以将新读入的数据写入另个通道
			buffer.flip();//写模式 转换为读模式
			
			fcout.write(buffer);
		}
	}

}
