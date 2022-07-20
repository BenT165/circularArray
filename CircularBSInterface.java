public interface CircularBSInterface <T>{

//implements Comparable <T> 


public void insert(T newBlock);

public T archive();

public void archiveAll();

public T getFront();

public T getBack();

public int getStorageCapacity();

public int size();

public boolean isEmpty();

public boolean isFull();

}