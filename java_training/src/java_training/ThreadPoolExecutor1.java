package java_training;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolExecutor1 {
	
	public static void main(String[] args) {
		ThreadPoolExecutor pool=new ThreadPoolExecutor(2,5,3,TimeUnit.SECONDS,new ArrayBlockingQueue<>(2),new kasiExecutor(),new kasiHandler());
		pool.allowCoreThreadTimeOut(true);
        for(int i=0;i<10;i++)
        {
        	pool.submit(()->
        	{ 
        		try
        		{
        			Thread.sleep(2000);
        		}
        		catch(Exception e)
        		{
        		      System.out.println(e);
        		}
        		System.out.println("Thread name:"+Thread.currentThread().getName());
        	}
        );
        	
        }
        pool.shutdown();
	}
	static class kasiExecutor implements ThreadFactory
	{

		@Override
		public Thread newThread(Runnable r) {
			Thread t=new Thread(r);
			return t;
		}
		
	}
	static class kasiHandler implements RejectedExecutionHandler
	{

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			System.out.println("Exited:"+Thread.currentThread().getName());
			
		}
		
	}

}
