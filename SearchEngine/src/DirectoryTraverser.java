import java.io.File;

public class DirectoryTraverser
{
	FileParser fp;
	InvertedIndex invertedMapObject;
	public DirectoryTraverser(InvertedIndex invertedMapObject)
	{
		this.invertedMapObject = invertedMapObject;
		this.fp = new FileParser(this.invertedMapObject);
	}
	
	/* traverseDirectory function is traversing directories and sub-directories to list out all text files
	 * and passing individual file to FileParser class.
	 *  
	 */
	public void traverseDirectory(File path)
	{
		if(path.isDirectory())
		{
			File[] entries = path.listFiles();
			for(File entry : entries )
			{
				if(entry.isFile())
				{
					if(entry.getName().endsWith(".txt") || entry.getName().endsWith(".TXT"))
					{
						fp.parseFile(entry);
					}
				}
				else
				{
					traverseDirectory(entry);
				}
			}
				
		}
	}
}	


		