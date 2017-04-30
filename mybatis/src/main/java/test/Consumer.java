/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:47:48
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package test;

import java.util.concurrent.BlockingQueue;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月5日 上午11:47:48
 * @version V1.0
 */
public class Consumer implements Runnable {

	protected BlockingQueue queue = null;  
	  
    public Consumer(BlockingQueue queue) {  
        this.queue = queue;  
    }  
  
    public void run() {  
        try {  
            System.out.println(queue.take());  
            System.out.println(queue.take());  
            System.out.println(queue.take());  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  

}
