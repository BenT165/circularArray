/**
* Priority BS is a child class of CircularBS
* that offers the added utility of sorting the
* elements in the array.  It does so by overriding the 
* CompareTo method in the Comparable interface and providing
* implementation that allows values elements in the array to be compared 
* regardles of their types. PriorityBS also a
* adds a helper method called sortArray which
* uses the compareTo method to order all elements in the array
* whenever a new element is inserted.
*/


public class PriorityBS <T extends Comparable <T>> extends CircularBS <T> implements CircularBSInterface <T>{
	 

	/** 
	* Refers to a specific element in the array
	*/

	private T element; 



	/**
	* Constructor method to intialize an empty array with 50 elements.
	* Creates an array of Comparable objects and type casts them into T
	*/

	public PriorityBS() {

		this.array = (T[]) new Comparable[50]; //create a new array of objects that are comparable

		this.startIndex = 0; 
		this.endIndex = array.length - 1;
		this.size = 0;

		this.element = null;

	}

	
	
	/**
	* Constructor method to intialize an empty array with a number of elements equal to capacity.
	* Creates an array of Comparable objects and type casts them into T
	* @param capacity the number of elements in the array
	*/

	public PriorityBS(int capacity) {

		this.array = (T []) new Comparable[capacity];
		
		this.startIndex = 0;
		this.endIndex = array.length - 1;
		this.size = 0;

		this.element = null;

	}



	/**
	* Doubles the length of the array by creating a new array of Comparable objects with 
	* twice the length of the original array. Startng at the first block of the new array,
	* elements are added from the firstIndex of the old array
	* until the array copy has the same size as the original.  Then the old array
	* is changed to reference the new array.
	* Uses modulus operations to add elements in a circular order.
	* @Override the doubleCapacity method in Circular BS to create an array of Comparable objects
	*/

	public void doubleCapacity() {

		T[] arrayCopy = (T [])new Comparable[this.array.length * 2];  //create a copy of the array with twice the length

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
	* Helper method to retrieve an element from the array
	* @return an element in the array
	*/

	private T getElement() {

		return this.element;

	}





	/**
	* Adds a new element to the end of the array and then sorts the array.
	* If the array is full, its size gets doubled
	* to make room for the new element.  The end index
	* is moved over one space to take in the new entry and
	* the size gets incremented by one. 
	* After the element is added, the array gets sorted
	* if it contains more than one element.
	* @param newBlock the element of type T that is being added to the array
	* @Override insert method in the parent class to add sorting
	*/

	public void insert(T newBlock) {

		if(isFull()) {

			doubleCapacity();

		}


		endIndex = (endIndex + 1) % array.length;	
		this.size ++;
		array[endIndex] = newBlock;


		if(size() > 1) {

			sortArray();
		}
		
	}
	

	
	/**
	* Helper method to sort the array using the Bubblesort algorithm.
	* Each element is compared the element at the nextIndex 
	* and the elements are swapped if the element at the nextIndex
	* is smaller than its predecessor.  The Big O is n^2
	* because all elements are checked once while looking for swaps
	* and the array is traversed up to array.length times to ensure the 
	* element at the last index of the array gets swapped to the first index of the
	* in the case that the last element the smallest element.
	*/
	
	private void sortArray() {

		int startIndexCopy = startIndex; //copy of the start index
		int nextIndex = (startIndexCopy + 1) % array.length; //index after copy of the startIndex
		
		this.element = array[startIndexCopy]; //set element to reference the startIndexCopy, array[startIndexCopy] will be compared the element at array[nextIndex]
		T temp = null;



		for(int i = 0; i < size(); i++) { //swap


			for(int j = 0; j < size(); j++) { //cover all elements in the array, swapp


				//if statements can essentially be summarized as a complex dance to make sure we aren;t ever comparing null values



				if((array[startIndexCopy] == null && array[nextIndex] == null) || (nextIndex == (endIndex + 1) % array.length)) { //array at startIndexCopy and nextIndex are null

				//or nextIndex goes past endindex 

					startIndexCopy = (startIndexCopy + 2) % array.length; //have to skip over 2 for both (otherwise startIndex would reference null value)
					this.element = array[startIndexCopy];
					nextIndex = (nextIndex + 2) % array.length;

				}

	

				else if(array[nextIndex] == null)  { //if only nextIndex is null,

					nextIndex = (nextIndex + 1) % array.length; //only skip nextIndex


				}


				else if(array[startIndexCopy] == null) {

					startIndexCopy = (startIndexCopy + 2) % array.length; //have to skip over 2 indices for both start and end
					this.element = array[startIndexCopy];
					nextIndex = (nextIndex + 2) % array.length;
				}


				else { //we are comparing to non-null values

					if(compareTo(array[nextIndex]) > 0) { //true if element at nextIndex is less than the element at the previous index (sorting in ascending order)

						temp = array[startIndexCopy];
						array[startIndexCopy] = array[nextIndex]; //swap the elements
						array[nextIndex] = temp;

					}


					startIndexCopy = (startIndexCopy + 1) % array.length; //both increment start and next element indices once
					this.element = array[startIndexCopy];
					nextIndex = (nextIndex + 1) % array.length;
				
				}

			}

		}
	}

				


	/**
	* Compares a specific element in the array to the element instance variable.
	* returns a positive number if element > obj
	* returns a negative number if element < obj
	* returns 0 if element == object
	* @param obj an element in the array
	* @Override the compareTo method in the Comparable interface to compare to gtwo objects of a generic type
	*/


	public int compareTo(T obj) {
		

		return getElement().compareTo(obj);


	}


}