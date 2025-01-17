package java_training;

import java.util.ArrayList;
import java.util.List;

sealed abstract  class Vehicle permits Car,Truck
{
	  private String make;
	  private String model;
      public Vehicle(String make, String model) {
		this.make = make;
		this.model = model;
	}
     
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	abstract double calculateInsurance();
     abstract  void  displayInfo();
}
final class Car extends Vehicle
{
	public int doors;
	public Car(String make, String model, int doors, boolean carstate) {
		super(make, model);
		this.doors = doors;
		this.carstate = carstate;
	}
	public boolean carstate;
	public double calculateInsurance()
	{
		if(carstate)
		{
			return(1000);
		}
		else
		{
			return(800);
		}
	}
	public void displayInfo()
	{
		System.out.println("Car:"+getMake()+","+"Model "+getModel()+","+"Doors:"+doors+","+"Electric:"+carstate);
	}
	
}
final class Truck extends Vehicle
{
	public double cargocapacity;
	public boolean trailer;
	public Truck(String make, String model, double cargocapacity, boolean trailer) {
		super(make, model);
		this.cargocapacity = cargocapacity;
		this.trailer = trailer;
	}
	public double calculateInsurance()
	{
		return(1000+(cargocapacity*0.1));
	}
	public void displayInfo()
	{
		System.out.println("Truck:"+getMake()+","+"Model "+getModel()+","+"Cargo Capacity:"+cargocapacity+" tons"+","+"has trailer"+trailer);
	}
}
public class VehicleInsuranseCalculator {

	public static void main(String[] args) {
		Car car=new Car("tesla","s",4,false);
		Truck truck=new Truck("ford","f15",2.5,true);
		List<Vehicle> objectlist=new ArrayList<>();
		objectlist.add(car);
		objectlist.add(truck);
		for(Vehicle obj:objectlist)
		{
		  obj.displayInfo();
		System.out.println("Insurance cost:"+obj.calculateInsurance());
		}
	}

}
