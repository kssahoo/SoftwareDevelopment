import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IndexSearcher
{	
	private LinkedHashMap<String, ArrayList<SearchResult>> searchMap = new LinkedHashMap<String, ArrayList<SearchResult>>();
	/* The method parseQueries, reads the query file line by line and passes individual query line to partialSearch method to get the array list of sorted 
	 * SearchResult objects. 
	 * In addition this method stores individual query line and associated sorted array list of SearchResult objects to a Linked Hash Map i.e searchMap.      
	 * 
	 * 
	 */
	public void parseQueries(String query, WordsMap wm)
	{
		ArrayList<SearchResult> arrSR = new ArrayList<SearchResult>();
		try{
			File file = new File(query);
			FileReader fr = new FileReader(file);  
			BufferedReader br = new BufferedReader(fr);
			String data;
			String[] queryWords = null;
			while((data = br.readLine()) != null)
			{
				queryWords = data.split("\\s+");
				arrSR = wm.partialSearch(queryWords);
				searchMap.put(data, arrSR);
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/* The method printSearchResults prints the individual query line and the associated location, position and frequency in different files into the specified 
	 * file.    
	 *
	 * 
	 */
	public void printSearchResults(String txtDoc)
	{   
		PrintWriter outFile;
		try {
			outFile = new PrintWriter(new File(txtDoc));
			for(String outerKey : searchMap.keySet())
			{
				outFile.println(outerKey);
				for(SearchResult s : searchMap.get(outerKey))
				{
					outFile.println("\""+ s.getLocation()+"\", "+ s.getFrequency() + ", " + s.getPosition());
				}
				outFile.println();
			}
			outFile.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		}
	}
}