import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiThreadedIndexSearcher extends IndexSearcher 
{
	private WorkQueue minions;
	private MultiReaderLock lock;
	private int pending;
	private static final Logger logger = LogManager.getLogger();
	
	public MultiThreadedIndexSearcher(ThreadSafeInvertedIndex index, WorkQueue minions) 
	{
		super(index);
		this.minions = minions;
		lock = new MultiReaderLock();
		pending = 0;
	}
	
	/**
	 * parseQueries method takes query file as argument and for each query line adds a new SearchWorker to the queue. 
	 * 
	 */
	public void parseQueries(String query, ThreadSafeInvertedIndex index) 
	{
		try {
			File file = new File(query);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String queryLine;
			while ((queryLine = br.readLine()) != null) 
			{
				addResult(queryLine, new ArrayList<SearchResult>());
				minions.execute(new SearchWorker(index, queryLine));
			}
			br.close();
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	protected void addResult(String data, ArrayList<SearchResult> arrSR)
	{
		lock.lockWrite();
		super.addResult(data, arrSR);
		lock.unlockWrite();
	}
	
	/**
	 * A new SearchWorker is created for each query line and SearchWorker class adds each query line and 
	 * respective partialSearch results to the searchMap.
	 * 
	 */
	private class SearchWorker implements Runnable 
	{
		String queryLine;
		ThreadSafeInvertedIndex index;
		public SearchWorker(ThreadSafeInvertedIndex index, String queryLine) 
		{
			this.queryLine = queryLine;
			this.index = index;
			incrementPending();
		}

		@Override
		public void run() 
		{
			addResult(queryLine, index.partialSearch(queryLine));
			decrementPending();
		}
	}
	
	private synchronized void incrementPending() 
	{
		pending++;
	}

	private synchronized void decrementPending() 
	{
		pending--;
		if (pending <= 0) 
		{
			this.notifyAll();
		}
	}
	
	public synchronized void finish() 
	{
		try {
			while (pending > 0) {
				logger.debug("Waiting until finished");
				this.wait();
			}
		} 
		catch (InterruptedException e) 
		{
			logger.debug("Finish interrupted");
		}
	}

}