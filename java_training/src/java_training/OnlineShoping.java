package java_training;

import java.util.ArrayList;

abstract class  product
{   public String name;
    public double basePrice;
    
	public product(String name,double basePrice)
	{
		this.name=name;
		this.basePrice=basePrice;
	}
	public abstract double calculateDiscount();
    public abstract double applyTax();
    public abstract double getPrice();
    public abstract void dispalyProductDetails();
	
}
class Electronics extends product
{
	public int warrantyPeriod;
	
	public Electronics(String name, double basePrice,int warrantyPeriod) {
		super(name, basePrice);
		this.warrantyPeriod=warrantyPeriod;
		
	}
	public  double calculateDiscount()
	{
		if(warrantyPeriod>2)
		{
			return(basePrice*0.10);
		}
		else
		{
			return 0;
		}
	}
    public  double applyTax()
    {
    	return(basePrice*0.12);
    }
    public  double getPrice()
    {
    	return(basePrice-calculateDiscount()+applyTax());
    }
    public void dispalyProductDetails()
	{
		System.out.println("name:"+name);
		System.out.println("orginalPrice:"+basePrice);
		System.out.println("Discount:"+calculateDiscount());
		System.out.println("Tax:"+applyTax());
		System.out.println("FinalPrice:"+getPrice());
		System.out.println("-----------------------------------------------------");
	}
}
class clothing extends product
{

	public clothing(String name, double basePrice) {
		super(name, basePrice);
	}
	public  double calculateDiscount()
	{
		return basePrice*0.15;
	}
    public  double applyTax()
    {
    	return(basePrice*0.25);
    }
    public  double getPrice()
    {
    	return(basePrice-calculateDiscount()+applyTax());
    }
    public void dispalyProductDetails()
	{
		System.out.println("name:"+name);
		System.out.println("orginalPrice:"+basePrice);
		System.out.println("Discount:"+calculateDiscount());
		System.out.println("Tax:"+applyTax());
		System.out.println("FinalPrice:"+getPrice());
		System.out.println("-----------------------------------------------------");
	}
}
class groceries extends product
{

	public groceries(String name, double basePrice) {
		super(name, basePrice);
	}
	public  double calculateDiscount()
	{
		return basePrice*0.11;
	}
    public  double applyTax()
    {
    	return(basePrice*0.15);
    }
    public  double getPrice()
    {
    	return(basePrice-calculateDiscount()+applyTax());
    }
    public void dispalyProductDetails()
	{
		System.out.println("name:"+name);
		System.out.println("orginalPrice:"+basePrice);
		System.out.println("Discount:"+calculateDiscount());
		System.out.println("Tax:"+applyTax());
		System.out.println("FinalPrice:"+getPrice());
		System.out.println("-----------------------------------------------------");
	}
	
}
public class OnlineShoping {
          public static void main(String arg[])
          {
        	  ArrayList<product> onlshp=new ArrayList<>();
              onlshp.add(new Electronics("Smartphone", 1000.0, 3));
              onlshp.add(new  clothing("T-Shirt", 1000.5));
              onlshp.add(new groceries("apple",1000));
              for(product prod:onlshp)
              {
            	  prod.dispalyProductDetails();
              }
          }
}

