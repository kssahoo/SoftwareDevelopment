
/**
 * A simple custom lock that allows simultaneously read operations, but
 * disallows simultaneously write and read/write operations.
 *
 * You do not need to implement any form or priority to read or write
 * operations. The first thread that acquires the appropriate lock should be
 * allowed to continue.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 */
public class MultiReaderLock {
	// TODO: Add any necessary members here.
	  private int readers = 0;
	  private int writers = 0;

	/**
	 * Initializes a multi-reader (single-writer) lock.
	 */
	public MultiReaderLock() {
		// TODO: Initialize members.
	}

	/**
	 * Will wait until there are no active writers in the system, and then will
	 * increase the number of active readers.
	 * @throws InterruptedException 
	 */
	public synchronized void lockRead()
	{
		// TODO: Fill in. Do not modify method signature.
		while(writers > 0)
		{
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		readers++;
	}
	/**
	 * Will decrease the number of active readers, and notify any waiting
	 * threads if necessary.
	 */
	public synchronized void unlockRead() {
		// TODO: Fill in. Do not modify method signature.
		readers--;
	    notifyAll();
	}

	/**
	 * Will wait until there are no active readers or writers in the system, and
	 * then will increase the number of active writers.
	 * @throws InterruptedException 
	 */
	public synchronized void lockWrite()
	{
		// TODO: Fill in. Do not modify method signature.
		while(readers > 0 || writers > 0)
		{
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		 writers++;
	}
	/**
	 * Will decrease the number of active writers, and notify any waiting
	 * threads if necessary.
	 */
	public synchronized void unlockWrite() {
		// TODO: Fill in. Do not modify method signature.
		writers--;
	    notifyAll();
	}
}
