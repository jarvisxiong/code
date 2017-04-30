/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 上午10:35:00
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 上午10:35:00
 * @version V1.0
 */
public class HelloClient {

	/***
	 * 
	 * @param args
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月10日 上午10:35:00
	 * @version V1.0
	 * @return 
	 */
	public static void main(String[] args) {
		// Client服务启动器
		ClientBootstrap bootstrap = new  ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		//设置一个处理服务端消息和各种消息事件的类《Handler》
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			 public ChannelPipeline getPipeline() throws Exception {
				 return Channels.pipeline(new HelloClientHandler());
			}
		});
		
		bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
	}
		private static class HelloClientHandler extends SimpleChannelHandler{
			/** 
	         * 当绑定到服务端的时候触发，打印"Hello world, I'm client." 
	         *  
	         * @alia OneCoder 
	         * @author lihzh 
	         */
			 @Override  
		        public void channelConnected(ChannelHandlerContext ctx,  
		                ChannelStateEvent e) {  
		            System.out.println("Hello world, I'm client.");  
		        }  
		}
}
