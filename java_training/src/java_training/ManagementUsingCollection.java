package java_training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
class s
{    String name;
     int rollno;
	public s(String name, int rollno) {
		super();
		this.name = name;
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	@Override
	public String toString() {
		return "s [name=" + name + ", rollno=" + rollno + "]";
	}
	 
	
}
class Student1 {
    String name;
    int score;

    public Student1(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return name + "=" + score;
    }
}

public class ManagementUsingCollection {

	public static void main(String[] args) {
		List<Student1> stu= Arrays.asList(
                new Student1("Alice", 85),
                new Student1("Bob", 40),
                new Student1("Alice", 85),
                new Student1("Charlie", 90),
                new Student1("David", 70),
                new Student1("Eva", 50),
                new Student1("George", 30)
        );
		//remove duplicates using set
		Set<String> uniqueNames=new HashSet<>();
		for(Student1 s:stu)
		{
			uniqueNames.add(s.getName());
		}
		System.out.println(uniqueNames);
		List<String>sortedname=new ArrayList<>(uniqueNames);
		Collections.sort(sortedname);
		System.out.println(sortedname);
		
		
		
		s stud1=new s("kasi",12);
		s stud2=new s("ram",13);
		Map<Integer,List<s>> student=new HashMap<>();
		List<s> class1Students = new ArrayList<>();
        class1Students.add(stud1);
        student.put(1, class1Students);  // Class 1 students
        class1Students.add(stud2);
        student.put(2, class1Students);  // Class 2 students
		
		System.out.println(student.get(2));

	}

}
