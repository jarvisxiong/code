/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 上午10:27:56
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月10日 上午10:27:56
 * @version V1.0
 */
public class HelloServer {

	/***
	 * 
	 * @param args
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月10日 上午10:27:56
	 * @version V1.0
	 * @return 
	 */
	public static void main(String[] args) {
		// Server 服务器启动
		 ServerBootstrap bootstrap = new ServerBootstrap(  
	                new NioServerSocketChannelFactory(  
	                        Executors.newCachedThreadPool(),  
	                        Executors.newCachedThreadPool()));  
		 
		 //设置一个处理客户端消息和各种消息事件的类
		 bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			public ChannelPipeline getPipeline() throws Exception {
				// TODO Auto-generated method stub
				return Channels.pipeline(new HelloServerHandler());
			}
		});
		 
		 //开放8000端口供客户访问。
		 bootstrap.bind(new InetSocketAddress(8000));
		 /*bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			    public ChannelPipeline getPipeline() throws Exception {
			        return Channels.pipeline(
			        new ObjectDecoder(ClassResolvers.cacheDisabled(this
			                .getClass().getClassLoader())),
			        new ObjectServerHandler());
			    }
			});*/
		int count = Runtime.getRuntime().availableProcessors() * 2;
		System.out.println(count);
		 
	}

	private static class HelloServerHandler extends SimpleChannelHandler {

		/**
		 * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
		 * 
		 * @alia OneCoder
		 * @author lihzh
		 */
		@Override
		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
			System.out.println("Hello world, I'm server.");
		}
	}
}  

