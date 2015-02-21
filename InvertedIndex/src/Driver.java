import java.io.File;
public class Driver
{ 	/* main function takes the command line inputs and calls constructor of 
	* ArgumentParser class that parses the list of command line arguments 
	* into flag/value pairs,which is passed to traverseDirectory function 
	* for file traversal.In addition it provides the output filename to 
	* printMap function to be created for storing the map data. 
 	*/
	public static void main(String[] args)
	{ 
		WordsMap wm = new WordsMap();
		DirectoryTraverser dt = new DirectoryTraverser();
		ArgumentParser ap = new ArgumentParser(args);
		if(ap.hasFlag("-d"))
		{
			String dir = ap.getValue("-d");
			File file = new File(dir);
			dt.traverseDirectory(file, wm);
		}
		else
		{
			System.out.println("Please enter the input path");
			return;
		}
		if(ap.hasFlag("-i") && ap.getValue("-i")!=null)
		{
			String txtFile = ap.getValue("-i");
			wm.printMap(txtFile);
		}
		else
		{
			String txtFile = "invertedindex.txt";
			wm.printMap(txtFile);
		}
	}
}