package java_training;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class Task implements Runnable
{
	public int taskid;

	public Task(int taskid) {
		this.taskid = taskid;
	}

	public void run() {
        System.out.println("Task:"+taskid+"is being executed by "+Thread.currentThread().getName());
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Task:"+taskid+"is being completed by "+Thread.currentThread().getName());
	}
	
}

public class ThreadPooling {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorservice=Executors.newFixedThreadPool(3);
		for(int i=0;i<6;i++)
		{
			executorservice.submit(new Task(i));
		}
		executorservice.shutdown();
		
		
	}
 
}