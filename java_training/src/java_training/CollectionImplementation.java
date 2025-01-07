package java_training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CollectionImplementation {

	public static void main(String[] args) {
		//List
				ArrayList<Integer>l=new ArrayList<>();
				l.add(7);
				l.add(3);
				l.add(2);
				l.add(1);
				l.add(9);
				l.add(6);
				Iterator<Integer> it=l.iterator();
				while(it.hasNext())
				{
					int num=it.next();
					System.out.println(num);
				}
				System.out.println("----------------------------------------------");
				//set
				Set<Integer>s=new HashSet<>();
				s.add(5);
				s.add(8);
				s.add(9);
				for(int num:s)
				{
					System.out.println(num);
				}
				System.out.println("----------------------------------------------");
				//treeset
				Set<Integer>s1=new TreeSet<>();
				s1.add(5);
				s1.add(4);
				s1.add(9);
				s1.add(3);
				s1.add(3);
				for(int num1:s1)
				{
					System.out.println(num1);
				}
				System.out.println("----------------------------------------------");
				//Map
				Map<String,Integer>m=new HashMap<>();
				m.put("string1",1);
				m.put("string2",2);
				System.out.println(m.get("string1"));
				

	}

}
