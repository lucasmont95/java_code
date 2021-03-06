package priorityqueue.a1posted;
import java.util.ArrayList;
import java.util.HashMap;

/* Assignment for comp251 Lucas Langer's Solution


public class IndexedHeap{   

	private ArrayList<Double>    priorities;
	private ArrayList<String>  	 names;     //   Think of this as a map:  indexToNames

	/*  
	 * 	This is not just a heap;  it is an indexed heap!  To index directly into the heap,
	 *  we need a map. 
	 */
	
	private HashMap<String,Integer>  nameToIndex;    

	// constructor

	public IndexedHeap(){
		
		//  A node in the heap keeps track of a object name and the priority of that object. 
		
		names = new ArrayList<String>();
		priorities = new ArrayList<Double>();

		/*
		 * Fill the first array slot (index 0) with dummy values, so that we can use usual 
		 * array-based heap parent/child indexing.   See my COMP 250 notes if you don't know 
		 * what that means.
		 */
								   
		names.add( null );    	
		priorities.add( 0.0 );      

		//  Here is the map that we'll need when we want to change the priority of an object.
		
		nameToIndex  = new HashMap<String,Integer>();
	}

	private int parent(int i){     
		return i/2;
	}
	    		
	private int leftChild(int i){ 
	    return 2*i;
	}
	
	private int rightChild(int i){ 
	    return 2*i+1;
	}
	
	private boolean is_leaf(int i){
		return (leftChild(i) >= priorities.size()) && (rightChild(i) >= priorities.size());
	}
	
	private boolean oneChild(int i){ 
	    return (leftChild(i) < priorities.size()) && (rightChild(i) >= priorities.size());
	}
	
	/* 
	 *  The upHeap and downHeap methods use the swap method which you need to implement.
	 */
	
	private void upHeap(int i){
		if (i > 1) {   // min element is at 1, not 0
			if ( priorities.get(i) < priorities.get(parent(i)) ) {

				swap(parent(i),i);
				upHeap(parent(i));
			}
		}
	}

	private void downHeap(int i){

		// If i is a leaf, heap property holds
		if ( !is_leaf(i)){

			// If i has one child...
			if (oneChild(i)){
				//  check heap property
				if ( priorities.get(i) > priorities.get(leftChild(i)) ){
					// If it fails, swap, fixing i and its child (a leaf)
					swap(i, leftChild(i));
				}
			}
			else	// i has two children...

				// check if heap property fails i.e. we need to swap with min of children

				if  (Math.min( priorities.get(leftChild(i)), priorities.get(rightChild(i))) < priorities.get(i)){ 

					//  see which child is the smaller and swap i's value into that child, then recurse

					if  (priorities.get(leftChild(i)) < priorities.get(rightChild(i))){
						swap(i,   leftChild(i));
						downHeap( leftChild(i) );
					}
					else{
						swap(i,  rightChild(i));
						downHeap(rightChild(i));
					}
				}
		}
	}	

	public boolean contains(String name){
		if (nameToIndex.containsKey( name ))
			return true;
		else
			return false;
	}
	
	public int sizePQ(){
		return priorities.size()-1;   //  not to be confused with the size() of the underlying ArrayList, which included a dummy element at 0
	}

	public boolean isEmpty(){
		return sizePQ() == 0;   
	}
	
	public double getPriority(String name){
		if  (!contains( name ))
			throw new IllegalArgumentException("nameToIndex map doesn't contain key " + String.valueOf(name));
		return priorities.get( nameToIndex.get(name) );	
	}
	
	public double getMinPriority(){
		return priorities.get( 1 );	
	}

	public String nameOfMin(){
		return names.get(1);
	}

	/*
	 *   Implement all methods below
	 */
	
	/*
	 *   swap( i, j) swaps the values in the nodes at indices i and j in the heap.   
	 */

	private void swap(int i, int j){
		if ((i != 0 && j != 0) && (priorities.get(i) != null) && (priorities.get(j) != null)){ //also check whether you are checking index 0, since we want index 0 to remain a dummy
			Double pri_i = priorities.get(i);
			Double pri_j = priorities.get(j);
			String name_i = names.get(i);
			String name_j = names.get(j);
			
			priorities.set(i, pri_j); 
			priorities.set(j, pri_i); 
			names.set(i, name_j);
			names.set(j, name_i); 
			
			nameToIndex.put(name_j, i);
			nameToIndex.put(name_i, j);
			
		}

		//----------------------- ADD YOUR CODE HERE ----------------------------		
		
	}

	
	
	//  returns (and removes) the name of the element with lowest priority value, and updates the heap
	
	public String removeMin(){

		if (names.size() > 2){
			String tmp = names.get(1);
			
			priorities.set(1, priorities.get(priorities.size() - 1));//replace priority at 1 with last one
			names.set(1, names.get(names.size() -1));
			
			priorities.remove(priorities.size() -1); // get rid of last one
			names.remove(names.size() -1); //get rid of duplicate
			
			downHeap(1);
			
			nameToIndex.remove(tmp);
			
			return tmp;

		}
		else{
			if (names.size() == 1){
				return null;
			}
			else{
				priorities.remove(1);
				nameToIndex.remove(names.get(1));
				return names.remove(1);
			}
		}
	}	

	/*
	 * There are two add methods.  The first assumes a specific priority.  That's the one
	 * you need to implement.   The second gives a default priority of Double.POSITIVE_INFINITY	  
	 */
	
	public void  add(String name, double priority){

		if  (contains( name ))
			throw new IllegalArgumentException("Trying to add " + String.valueOf(name) + ", but its already there.");

		//----------------------- ADD YOUR CODE HERE  ----------------------------
		priorities.add(priority);
		names.add(name);
		nameToIndex.put(name, priorities.size() - 1);
		
		upHeap(priorities.size() -1); // does it auto-increment size on line 203?
		
		
	}
	
	public void  add(String name){
		add(name, Double.POSITIVE_INFINITY);
	}

	/*
	 *   If new priority is different from the current priority then change the priority (and possibly modify the heap). 
	 *   If the name is not there, then throw an exception.
	 */
	
	public void changePriority(String name, double priority){

		if  (!contains( name ))
			throw new IllegalArgumentException("Trying to change priority of " + String.valueOf(name) + ", but its not there.");

		//-----------------------  ADD YOUR CODE HERE ----------------------------
		int index;
		index = nameToIndex.get(name);
		
		if (priorities.get(index) < priority){
			priorities.set(index, priority);
			downHeap(index);		
		}
		else{
			priorities.set(index, priority);
			upHeap(index);
		}
		
		
	}
	
}
