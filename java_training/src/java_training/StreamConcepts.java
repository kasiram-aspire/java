package java_training;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamConcepts {

	public static void main(String[] args) {
		List<Integer> list2=Arrays.asList(5,6,3,8,9);
		List<String> list=Arrays.asList("abcd","kitd","Pyth","Java");
		list.stream()
		            .filter(n->n.length()<=4)
		            .map(e->e.toUpperCase())
		            .distinct()
		            .sorted()
		            .forEach(System.out::println);
	   Optional<Integer> reducedlist=list2.stream()
			             .reduce((c,e)->(c+e));
	   reducedlist.ifPresent(System.out::println);
	   long countresult=list2.stream()
				.count();
		
		System.out.println("After performing count operator in stream : "+countresult);
		
		Optional<Integer> findfirstResult=list2.stream()
					.findFirst();
		
		findfirstResult.ifPresent(s->System.out.println("First Element in the stream : "+s));
		
		Boolean allmatchResult=list2.stream()
					.allMatch(n->n>3);    //Return boolean result true if all the element match the given condition
		
		System.out.println("All match result (element > 3) : "+allmatchResult);
		
		Boolean anymatchResult=list2.stream()
				.anyMatch(n->n>3);		//Return boolean result true if any one element match the given condition
	
		System.out.println("Any match result (element > 3) : "+anymatchResult);
		
		Boolean nonematchResult=list2.stream()
				.noneMatch(n->n>3);		//Return boolean true result if all the element not match the given condition
	
		System.out.println("None match result (element > 3) : "+nonematchResult);
		

	}

}
