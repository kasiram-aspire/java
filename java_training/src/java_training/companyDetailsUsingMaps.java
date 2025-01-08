package java_training;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AspireEmployeeDetails
{
	private String name;
	private int id;
	public AspireEmployeeDetails(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "AspireEmployeeDetails [name=" + name + ", id=" + id + "]";
	}
}
public class companyDetailsUsingMaps {

	public static void main(String[] args) {
		Map<Integer,List<AspireEmployeeDetails>> employeeMap=new HashMap<>();
		employeeMap.put(1,new ArrayList<AspireEmployeeDetails>(Arrays.asList(new AspireEmployeeDetails("kasiram",12515))));
		employeeMap.put(2,new ArrayList<AspireEmployeeDetails>(Arrays.asList(new AspireEmployeeDetails("jajaj",2888888))));
		for(Map.Entry<Integer,List<AspireEmployeeDetails>> empldtls:employeeMap.entrySet())
		{
			for(AspireEmployeeDetails asobj:empldtls.getValue())
			{
				System.out.println(asobj);
			}
		}
	}

}
