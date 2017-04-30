/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:50:44
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:50:44
 * @version V1.0
 */
public class BlockingQueueExample {

	/***
	 * 
	 * @param args
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月5日 上午11:50:44
	 * @version V1.0
	 * @return 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		 BlockingQueue queue = new ArrayBlockingQueue(1024);  
		  
	        Producer producer = new Producer(queue);  
	        Consumer consumer = new Consumer(queue);  
	  
	        new Thread(producer).start();  
	        new Thread(consumer).start();  
	        Thread.currentThread().getContextClassLoader();
	       // Thread.sleep(4000);  
	        
	        Map<String,Object> map = new HashMap<String,Object>();
	}

}
