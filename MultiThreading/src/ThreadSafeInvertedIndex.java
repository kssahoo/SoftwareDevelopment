import java.io.*;
import java.util.*;

/**
 * ThreadSafeInvertedIndex class overrides all methods of InvertedIndex class to make it thread-safe using MultiReaderLock.
 *
 *
 */
public class ThreadSafeInvertedIndex extends InvertedIndex 
{
	private MultiReaderLock lock = new MultiReaderLock();
           
	/*
	 * addToMap function takes single word as key for outerMap and adds the keys
	 * and values to the respective innerMap for the particular key.
	 */
	@Override
	public void addToMap(String word, String fileName, Integer position) 
	{
		lock.lockWrite();
		super.addToMap(word, fileName, position);
		lock.unlockWrite();
	}

	@Override
	public void addAll(InvertedIndex local) 
	{
		lock.lockWrite();
		super.addAll(local);
		lock.unlockWrite();
	}

	/*
	 * printMap function prints the filename(key) and position(value) of each
	 * word encountered in a file.
	 */
	@Override
	public void printMap(String txtFile) 
	{
		lock.lockRead();
		super.printMap(txtFile);
		lock.unlockRead();
	}

	@Override
	public ArrayList<SearchResult> partialSearch(String input) 
	{
		lock.lockRead();
		ArrayList<SearchResult> arr = super.partialSearch(input);
		lock.unlockRead();
		return arr;
	}
}
