package java_training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionDemo {

	public static void main(String[] args) {
		//LIST
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		Iterator<Integer>iterator=list.iterator();
		while(iterator.hasNext())
		{
			System.out.println("element: "+iterator.next());
		}
		System.out.println("\n");
//      -------------------------------------------------------------------------------------------
		//set
		Set<Integer>set=new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		iterator=set.iterator();
		while(iterator.hasNext())
		{
			System.out.println("element set: "+iterator.next());
		}
		System.out.println("\n");
		// treeset
		Set<Integer>treeSet=new TreeSet<Integer>();
		treeSet.add(1);
		treeSet.add(2);
		treeSet.add(3);
		treeSet.add(4);
		iterator=treeSet.iterator();
		while(iterator.hasNext())
		{
			System.out.println("element treeSet: "+iterator.next());
		}
		System.out.println("\n");
//      --------------------------------------------------------------------------------------------------
		//hashmap
		Map<String,Integer> map=new HashMap<>();
	    map.put("kasi",1011);
	    map.put("ram",101123);
	    for(Entry<String, Integer> output:map.entrySet())
	    {
	    	System.out.println("Map: Key:"+output.getKey()+"|| value: "+output.getValue());
	    }
	    System.out.println("\n");
	    //treeMap
	    Map<String,Integer> treeMap=new TreeMap<>();
	    treeMap.put("ram",101123);
	    treeMap.put("kasi",1011);
	   
	    for(Entry<String, Integer> output:treeMap.entrySet())
	    {
	    	System.out.println("Map: Key:"+output.getKey()+"|| value: "+output.getValue());
	    }
	    System.out.println("\n");
// ----------------------------------------------------------------------------------------------
	   //queue
	    Queue<Integer> queue=new LinkedList<Integer>();
	    queue.add(1);
	    queue.add(2);
	    queue.add(3);
	    System.out.println("peek: "+queue.peek()+"\n");
	    iterator=queue.iterator();
	    while(iterator.hasNext())
	    {
	    	System.out.println("queue: "+iterator.next());
	    }
	    System.out.println("\npop: "+queue.poll()+"\n");
	    iterator = queue.iterator();
	    while(iterator.hasNext())
	    {
	    	System.out.println("queue: "+iterator.next());
	    }
	    System.out.println("\n");
//--------------------------------------------------------------------------------------------------------
	//stack
	Stack<Integer> stacks=new Stack<>();
	 stacks.push(1);
	 stacks.push(2);
	 stacks.push(3);
	 System.out.println("stack peek:"+ stacks.peek()+"\n");
	 iterator=stacks.iterator();
	    while(iterator.hasNext())
	    {
	    	System.out.println("stack: "+iterator.next());
	    }
	   System.out.println("\nstack pop:"+ stacks.pop()+"\n");
		 iterator=stacks.iterator();
		    while(iterator.hasNext())
		    {
		    	System.out.println("stack after pop: "+iterator.next());
		    }
	
	 System.out.println("\n");
	
//-----------------------------------------------------------------------------------------------  
	//linkedList
	LinkedList<Integer> linkedlist=new LinkedList<Integer>();
	linkedlist.push(1);
	linkedlist.push(2);
	linkedlist.push(3);
	System.out.println("remove no 3 from list:"+linkedlist.remove(2)+ "\n");
	 iterator=linkedlist.iterator();
	    while(iterator.hasNext())
	    {
	    	System.out.println("linkedlist after remove: "+iterator.next());
	    }

System.out.println("\n");
//--------------------------------------------------------------------------------------------------------
          //CopyOnWriteArrayList  FailFirstFailSafe
       CopyOnWriteArrayList<Integer> copywritearraylist=new CopyOnWriteArrayList<Integer>();
       copywritearraylist.add(1);
       copywritearraylist.add(2);
       copywritearraylist.add(3);
        iterator=copywritearraylist.iterator();
        while(iterator.hasNext())
        {
        	System.out.println("list element:"+iterator.next());
        	copywritearraylist.add(4);
        }
        System.out.println("\nlist[]:"+copywritearraylist);
  //---------------------------------------------------------------------------------------------------------
	}
}
