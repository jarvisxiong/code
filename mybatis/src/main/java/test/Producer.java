/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:47:22
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package test;

import java.util.concurrent.BlockingQueue;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:47:22
 * @version V1.0
 */
public class Producer implements Runnable {
	
	protected BlockingQueue queue = null;
	
	public Producer(BlockingQueue queue){
		this.queue = queue;
	}
	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月5日 上午11:47:22
	 * @version V1.0
	 * @return 
	 */
	public void run() {
		try {  
            queue.put("1");  
            Thread.sleep(1000);  
            queue.put("2");  
            Thread.sleep(3000);  
            queue.put("3");  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
	}

}
