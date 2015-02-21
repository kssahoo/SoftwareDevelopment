import java.io.File;

public class DirectoryTraverser
{
	/* traverseDirectory function is traversing directories and sub-directories to list out all text files
	 * and passing individual file to FileParser class.
	 *  
	 */
	public void traverseDirectory( File path, WordsMap wm)
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
						FileParser fp = new FileParser();
						fp.parseFile(entry, wm);
					}
				}
				else
				{
					traverseDirectory(entry, wm);
				}
			}
				
		}
	}
}	


		