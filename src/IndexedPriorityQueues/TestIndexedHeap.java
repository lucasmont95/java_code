package priorityqueue.a1posted;
import java.util.ArrayList;
import java.util.Random;

public class TestIndexedHeap {
	
	/*   
	test code provided in 251 for this assignment
	 *  
	 */  

	public static void main(String[] args) {
		
		Random generator = new Random();
		double d;

		//   Here the names of the elements will be of type String. 
		//   I am naming the heap "pq" just as a reminder what the heap is for. 
		
		IndexedHeap  pq = new IndexedHeap();

		/*    Now add some elements into the heap.
		 *    The name of element i is v_i.  For example, these might be the names of
		 *    vertices in a graph.   The priority is a random number.   Adding a sequence
		 *    of them should produce a heap. 
		 */

		int numElements = 200;
		for (int i=1; i <= numElements; i++){
			
			//  give this element a random priority
			
			d = generator.nextDouble();
			pq.add( "v_" + new Integer(i).toString() , d);
		}

		/*
		 *  Change the priority so that the ith random element has priority i.
		 */
		
		for (int i=1; i <= numElements; i++){
			pq.changePriority("v_" + new Integer(i).toString(), i*1.0);
			System.out.println("changed v_" + new Integer(i).toString() + " to " + pq.getPriority("v_"+ new Integer(i).toString()));
		}

		System.out.println("\nRemoving all the elements (in order of priority) ");

		/*   Remove all elements from the priority queue.  v_i should have priority i.
		 * 	 They should print out in order of priority,  (v_1, 1.0),  (v_2, 2.0), etc.
		 * 	 Notice we can use an array now since we know the number of elements in the heap.
		 */

		String    names[]      = new String[numElements]; 
		double    priorities[] = new double[numElements]; 

		for (int i=0; i < numElements; i++){
			priorities[i] = pq.getMinPriority();			
			names[i] = pq.removeMin();
		}

		for (int i=0; i < numElements; i++){
			System.out.println( String.valueOf(names[i]) + " " + String.valueOf(priorities[i])    );
		}
	}

}
