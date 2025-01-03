package java_training;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class SortArrayUsingStream {

	public static void main(String[] args) {
		ArrayList<Integer> num=new ArrayList<>();
		//first i perform aading values to arrylist
		num.add(1);
		num.add(3);
		num.add(10);
		num.add(5);
		//using stream to assign the list to stream
		Stream<Integer>s=num.stream();
		
		//the number which are divisible by zero is assign to new stream
		((Collection<Integer>) s).stream()
					.filter(n->n%2==0)
					.map(n->n>3)
					.forEach(e->System.out.println(e));
				
		//the number present in s1 is multiplied and stroe in s2
		//Stream<Integer>s2=s1.map(n->n*2);
		//now i add the entire value present in s2 stream
//		int total=s2.reduce(0,(c,e)->c+e);
//        num.forEach(n->System.out.println("the value befor sorting"+n));
//        System.out.println("----------------------------------------------");
//        System.out.println(total);
	}

}

