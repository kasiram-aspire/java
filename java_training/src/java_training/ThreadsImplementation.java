package java_training;

class a extends Thread
{
	public void run() {
		for(int i=0;i<5;i++)
		{
			System.out.println("hi");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
							
}
class b extends Thread
{

	public void run() {
		for(int i=0;i<5;i++)
		{
			System.out.println("hello");
			
		}
}
}
class c implements Runnable
{
	public void run() {
		for(int i=0;i<5;i++)
		{
			System.out.println("hello runnable");
			
		}
}
}
class counter 
{    int count=0;
	public synchronized void count()
	{
		count++;
	}
}

public class ThreadsImplementation {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		 counter c=new counter();
		a obj1=new a();
		b  obj2=new b();
		obj2.setPriority(Thread.MAX_PRIORITY);
		obj1.start();
		obj2.start();
		// implements runnable
		Runnable obj3=new c();
		Thread t1=new Thread(obj3);
		t1.start();
		//implemets runnbale using anonymous class
		Runnable obj4=new Runnable()
				{   public void run()
								{
					for(int i=0;i<5;i++)
					{
						System.out.println("hello runnable1");
						
					}
								}
				};
		Thread t3=new Thread(obj4);
		t3.start();
				//implemets runnbale using lambda 
				Runnable obj5=()->
						{   			
							for(int i=0;i<5;i++)
							{
								System.out.println("hello runnable1");
								
							}
										
						};
		// syncronized way of thread
						Runnable r1=()->
								{
									for(int i=0;i<5;i++)
									{
										c.count();
										
									}
							         
								};
						Runnable r2=()->
								{
									for(int i=0;i<5;i++)
									{
										c.count();
										
									}
								};
					  Thread t6=new Thread(r1);
					  Thread t7=new Thread(r2);
					  t6.start();
					  t7.start();
					  t6.join();
					  t7.join();
					  System.out.println("final count value:"+c.count);
					  
		
		
		

	}

}
