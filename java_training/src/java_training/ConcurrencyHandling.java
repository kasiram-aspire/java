package java_training;

class counthandling
{
	int num;
	boolean value=false;
	public synchronized void put(int num)
	{   while(value)
	{
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    this.num=num;
		System.out.println("put: "+num);
		value=true;
		notify(); 
		
	}
	public synchronized void get()
	{
		while(!value)
		{
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("get: "+num);
		value=false;
		notify();
	}
}

class thread1 implements Runnable
{
	counthandling cth;
	public thread1(counthandling cth)
	{
		this.cth=cth;
		Thread t1=new Thread(this,"thread1");
		t1.start();
	}
	public void run() {
		  int i = 0;
		  while(true)
		  {
			  cth.put(i++);
			  try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
		
	}
	
}
class thread2 implements Runnable
{
	counthandling cth;
	public thread2(counthandling cth)
	{
		this.cth=cth;
		Thread t2=new Thread(this,"thread2");
		t2.start();
	}
	public void run() {
		  int i = 0;
		  while(true)
		  {
			  cth.get();
			  try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
		
	}
	
}
public class ConcurrencyHandling {
	
    public static void main(String[] args) {
    	counthandling cnth=new counthandling();
    	new thread1(cnth);
    	new thread2(cnth);
        	
        }
        
    }
