import java.io.File;
public class Driver
{ 	/* main function takes the command line inputs and calls constructor of ArgumentParser class that parses the list of command line arguments 
	* into flag/value pairs,which is passed to traverseDirectory function for file traversal.It provides the output filename to
	* printMap function to be created for storing the map data. In addition main function takes the query file as command line argument and carries out  
	* partial search operation on each query line and outputs the result to a text file.
	*   
	*  
	*  
 	*/
	public static void main(String[] args)
	{ 
		WordsMap wm = new WordsMap();
		DirectoryTraverser dt = new DirectoryTraverser();
		ArgumentParser ap = new ArgumentParser(args);
		IndexSearcher is = new IndexSearcher();
		if ((ap.hasFlag("-d")) && (ap.hasValue("-d")))
		{
			String dir = ap.getValue("-d");
			File file = new File(dir);
			dt.traverseDirectory(file, wm);
		}
		else
		{
			return;
		}
		if (ap.hasFlag("-i")) 
			if (ap.getValue("-i") != null) {
				String txtFile = ap.getValue("-i");
				wm.printMap(txtFile);
			}
			else
				wm.printMap("invertedindex.txt");
		if(ap.hasFlag("-q"))
		{
			String inputQuery = ap.getValue("-q");
			is.parseQueries(inputQuery, wm);
			if(ap.hasFlag("-r")) 
				if (ap.getValue("-r") != null)
				{
					String txtDoc = ap.getValue("-r");
					is.printSearchResults(txtDoc);
				}
				else
				{
					String txtDoc = "searchresults.txt";
					is.printSearchResults(txtDoc);
				}
			}
			else
			{
				if (!ap.hasFlag("-i"))
					wm.printMap("invertedindex.txt");
		}
	}
}