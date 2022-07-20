/**
* Unchecked exception to be thrown if there are no blocks in the array.
* Contains a constructor method which calls the super classes' constructor
* to initialize a message with "No Block to Process".
* Also contains a toString method that returns the error message.
*/

public class NoBlockException extends Exception{

	/**
	* Class constructor that initializes an error message.
	*/
	
	public NoBlockException() {

		super("No Block to process");

	}

	/**
	* Gets the error message for this class.
	* @return a class-specific error message
	*/

	public String toString() {

		return "No Block to process";

	}
}