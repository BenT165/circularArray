/**
* Generic class that creates a circular array with elements of a specified type.
* A toString method is included to convert to the contents of the array into 
* a String format. The class also has a doubleCapacity method to
* create room for more elements if the array is full.
* The getStart and getEnd methods retrieve the start and end indices 
* of the array and the setStart and setEnd methods increment the start
* and end indices in a circular manner and prevent the indices from going out of bounds.
* Overrides the methods in the CircularBSInterface.
* Provides implementation for the insert and archive methods which respectively add an element to the end of the array
* and remove an element from the start of the array.
* Also provides methods to get the number of elements in the array (size), get the number of elements that
* can fit in the array (getStorageCapacity), and retrieve the elements at the
* array's start and end indices (getFront and getBack).  The isFull and isEmpty
* methods check that to see if the array's size is at maximumCapacity or is zero.
* @param <T> an object of any type
*/

public class CircularBS<T> implements CircularBSInterface<T> {
	

	/** 
	* The array of elements.
	*/

	protected T[] array;
	
	
	/** 
	* The index of the first element of the array.
	* Gets moved one space over when an element is
	* archived, and to the zero index if it would go out of bounds
	*/

	protected int startIndex;
	

	/** 
	* The index of the last element of the array.
	* Gets moved one space over when an element is
	* inserted and to the zero index if it would go out of bounds
	*/

	protected int endIndex;
	

	/**
	* The number of non-null elements the array contains.
	*/

	protected int size;

	
	/**
	* Class constructor with no parameters.
	* Initializes an empty array with a default length of 50.
	* The startIndex is set to 0 and the endIndex is set to 
	* the index of the last element in the array.
	* Size is set to 0 since the array will initially contain
	* all null elements.
	*/
	
	public CircularBS() {

		this.array = (T[]) new Object[50];
		this.startIndex = 0;
		this.endIndex = array.length - 1;
		this.size = 0;

	}


	/**
	* Class constructor with an integer parameter
	* Initializes an empty array with a length of capacity.
	* The startIndex is set to 0 and the endIndex is 
	* set to the index of the last element of the array.
	* Size is set to 0
	* @param capacity The length of the array
	*/
	
	public CircularBS(int capacity) {

		this.array = (T[]) new Object[capacity]; //type cast an array of Objects into array of T	
		this.startIndex = 0;
		this.endIndex = array.length - 1;
		this.size = 0;

	}


	/**
	* Method to double capacity of the array.
	* Creates of array called arrayCopy with twice the length of the array.
	* Elements are copied over to index 0 of the new array sequentially, incrementing the index number each time.
	* Starts from the element at startIndex and ends at endIndex of the old array.
	* After afterCopy has a size equal to the original array's,
	* startIndex is set back to 0 and endIndex gets
	* set to the index of the last element that was added.
	* The old array is then set to reference the newly created array.
	*/

	public void doubleCapacity() {
	
		T[] arrayCopy = (T [])new Object[this.array.length * 2];  //create a copy of the array with twice the length

		int start = startIndex; //where to begin
		int end = endIndex; //where to end
		int count = 0; //track number of elements
		//size is where to stop

		while(count < size()) {


			arrayCopy[count] = array[start]; //fill array left to right
			start = (start + 1) % array.length; //increment start copy
			count ++; //increment count

		}

		this.startIndex = 0; //set startIndex back to 0
		this.endIndex = size() - 1; //set endIndex to index of last element in the array

		this.array = arrayCopy; //set array equal to the double capacity array

	}


	/**
	* Creates a String representation of the array by traversing the array 
	* and adding the String representation of each element to an empty String.
	* If the array has no elements, returns a String representation of an empty array.  
	* @return a String representation of the array 
	*/
	
	public String toString() {

		String myString = "";
		int count = 0;


		if(isEmpty()) { //if the array is empty, just print []

			return "[]";

		}

		else {		

			myString += "[";
		
			while(count < array.length) {

				if(array[count] == null) { //skip over null elements

					count++; 

				}

				else if(array.length - count > 1 && array[(count + 1) % array.length] != null) { //If element is not null 

					//and we are not at the end of the array, and the next element in the array is not null 

					myString += (array[count].toString() + ","); //add the element to the String with a comma after
					count++; // increment count

				}

				else { //If count is at last element of the array or the next element in the array is null

					myString += (array[count].toString()); //add the element to the String without a comma
					count++;

				}		
			}
		}

		return myString + "]";
	
	}


	/**
	* Accessor method to retrieve the position of the first index in the array.
	* @return the index position of the block that has been in the array the longest
	*/

	public int getStart() {

		return startIndex;

	}


	/**
	* Accessor method to retrieve the position of the last index in the array. 
	* @return the index position of the block that has been added to the array most recently
	*/

	public int getEnd() {
		
		return endIndex;

	}


	/**
	* Mutator method to increment the position of the first index in the array.
	* If startIndex would reach the last index of the array, startIndex is set back to 0
	*/

	private void setStart() {

		this.startIndex = (startIndex + 1) % array.length;

	}
	
	
	/**
	* Mutator method to increment the position of the last index in the array.
	* if the the endIndex would be equal to array's length, set endIndex back to 0 	
	*/

	private void setEnd() {

		this.endIndex = (endIndex + 1) % array.length;

	} 


	/**
	* Adds a new block to the end of the array.
	* If the array is full before the block is added, the array's size gets doubled
	* endIndex is set to the next empty space and size is increased by one to accomodate the new entry
	* then, the element at endINdex is updated with the newBlock value
	* @param newBlock The element that is being added to the array 
	* @Override empty insert method in the CircularBSInterface
	*/

	public void insert(T newBlock) {


		if(isFull()) { //if the array is Full

			doubleCapacity(); //size of array gets doubled
						
		}

		setEnd(); //increment the endBlock
		size ++; //increment the size by 1	
		array[endIndex] = newBlock; //set the element at the endIndex index equal to newBlock
		
	}


	/**
	* Stores the element at the firstIndex in the array.
	* Replaces the element at the firstIndex with a null value
	* Then starIndex is moved over one space.
	* Throws a NoBlockException if there are not any
	* elements in the array.
	* @return the value at firstIndex
	* @Override empty archive method in the CircularBSInterface
	*/

	public T archive() {

		T archivedElement = array[this.startIndex]; //store the archived element

		try {

			if(isEmpty()) {	

				throw new NoBlockException(); //throw NoBlockException if the array is empty
			
			}
			
			this.array[startIndex] = null; //set the element at startIndex to null
			setStart(); //increment startIndex
			size --; //decrement size
			
		}
		
		catch(NoBlockException e) {

			System.err.println(e.toString());

		}

		return archivedElement;
		
	}


	/**
	* Removes all elements from the array.
	* startIndex is moved over one space and size is decreased by 1 for each deletion
	* throws a NoBlockException if there are no blocks to remove
	* @Override the archiveAll method from CircularBSInterface
	*/

	public void archiveAll(){

		try {

			if(isEmpty()) {

				throw new NoBlockException(); //throw NoBlockException if the array is empty
		
			}
		}

		catch(NoBlockException e) {

			System.err.println(e.toString());

		}

		while(size() != 0) { //while array is not all nulls

			array[startIndex] = null; //null element at startIndex
			setStart();	//adjust startIndex
			size --;
		}

	}	
	

	/**
	* Throws NoBlockException if array is empty
	* Retrieves the element add the index that has been in the array the longest.
	* @return the first block in the current structure
	* @Override the getFront method for CircularBSInterface
	*/
	
	public T getFront() { 

		try {	

			if(isEmpty()) {

				throw new NoBlockException();

			}
		}

		catch(NoBlockException e) {

			System.err.println(e.toString());

		}

		return array[startIndex];

	}


	/**
	* Retrieves the element the was most recently added to the array
	* and throws NoBlockException if array is empty.
	* @return the last block in the current structure
	* @Override the getBack method for CircularBSInterface 
	*/

	public T getBack() {

		try{
			
			if(isEmpty()) {

				throw new NoBlockException();

			}
		}

		catch(NoBlockException e) {

			System.err.println(e.toString());

		}

		return array[endIndex];

	}


	/**
	* Calculates the highest possible number of elements the array can accomodate
	* at it's current size.
	* @return the maximum number of elements that the array can accomodate
	* @Override the getStorageCapacity method from CircularBSInterface
	*/

	public int getStorageCapacity() {

		return ((array.length - 1));
	
	}

	
	/**
	* Retrieves the number of non-null elements in the array.
	* @return the number of elements that are being used to store Objects
	* @Override CircularBSInterface
	*/

	public int size() {

		return size;
	
	}


	/**
	* Method to check if there are any existing values in the array.
	* @return true if the array contains all null elements, false if the array contains a non-null element 
	* @Override the isEmpty method from CircularBSInteface
	*/

	public boolean isEmpty() {

		return size() == 0;	 

	}

	
	/** 
	* Determines if the array can accomodate any new elements.
	* Acounting for the fact that array must have at least one null element 
	* @return false if the array contains a null element, true otherwise
	* @Override the isFull method from CircularBSInterface
	*/

	public boolean isFull() {


		return array.length - this.size() <= 1;
		
		
	}
	

}