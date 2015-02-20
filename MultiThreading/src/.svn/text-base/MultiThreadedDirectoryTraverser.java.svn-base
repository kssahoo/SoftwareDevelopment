import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiThreadedDirectoryTraverser extends DirectoryTraverser 
{
	private static final Logger logger = LogManager.getLogger();
	private final WorkQueue minions;
	private int pending;
	public MultiThreadedDirectoryTraverser(ThreadSafeInvertedIndex index,WorkQueue minions) 
	{
		super(index);
		this.minions = minions;
		pending = 0;
	}

	/* traverseDirectory method traverses all the directories and sub-directories and creates a new thread for each text file.
	 * 
	 */
	public void traverseDirectory(File directory) 
	{
		if (directory.isDirectory())
		{
			File[] entries = directory.listFiles();
			for (File path : entries) 
			{
				if (path.isDirectory())
				{
					traverseDirectory(path);
				} 
				else if (path.getAbsolutePath().endsWith("txt")
						|| path.getAbsolutePath().endsWith("TXT")) 
				{
					minions.execute(new FileWorker(path));
				}
			}
		}
	}
	
	/* One FileWorker runnable is created for each text file that needs to be parsed.  
	 * When the file is being parsed, the words are added into the localIndex which is
	 * combined with the shared InvertedIndex class in the end of the run method. 
	 * 
	 */
	private class FileWorker implements Runnable 
	{
		public File path;
		public FileWorker(File path) 
		{
			this.path = path;
			incrementPending();
		}

		@Override
		public void run() 
		{
			InvertedIndex localIndex = new InvertedIndex();
			FileParser fp = new FileParser(localIndex);
			fp.parseFile(path);
			invertedMapObject.addAll(localIndex);
			decrementPending();
		}
	}
	
	public synchronized void finish() 
	{
		try {
			while (pending > 0) 
			{
				logger.debug("Waiting until finished");
				this.wait();
			}
		} 
		catch (InterruptedException e) 
		{
			logger.debug("Finish interrupted");
		}
	}
	
	public synchronized void shutdown() 
	{
		finish();
		minions.shutdown();
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

}
