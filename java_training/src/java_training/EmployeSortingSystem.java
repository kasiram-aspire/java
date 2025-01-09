package java_training;

import java.util.ArrayList;
import java.util.List;

class EmployeeDetailsInfo
{
	private String name;
	private Double salary;
	private int empId;
	public EmployeeDetailsInfo(String name, Double salary, int empId) {
		this.name = name;
		this.salary = salary;
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String toString() {
		return "EmployeeDetailsInfo [name=" + name + ", salary=" + salary + ", empId=" + empId + "]";
	}
	
	
	
}

public class EmployeSortingSystem {

	public static void main(String[] args) {
		     List<EmployeeDetailsInfo> empldts=new ArrayList<>();
		     empldts.add(new EmployeeDetailsInfo("kasi",2000.00,12516));
		     empldts.add(new EmployeeDetailsInfo("kaif",20000.00,12514));
		     empldts.add(new EmployeeDetailsInfo("karthi",1000.00,12513));
		     empldts.add(new EmployeeDetailsInfo("raja",200022.00,12512));
		     empldts.add(new EmployeeDetailsInfo("karim",249999.00,12511));
		     empldts.add(new EmployeeDetailsInfo("karthiraja",1000500.00,12510));
		     System.out.println("-----------------employes before sorting-------------------------");
		     System.out.println();
		     for(EmployeeDetailsInfo obj:empldts)
		     {
		    	 System.out.println(obj);
		     }
		     System.out.println();
		     System.out.println("-----------------employes After sorting-------------------------");
		     System.out.println();
		     empldts.stream()
		            .sorted((e1,e2)->e1.getSalary().compareTo(e2.getSalary()))
		            .forEach(n->System.out.println(n));	     
	}

}
