import java.io.*;
import java.util.*;

public class WordsMap
{
	private TreeMap<String, TreeMap<String, ArrayList<Integer>>> outerMap = new TreeMap<String, TreeMap<String, ArrayList<Integer>>>();
	
	/* addToMap function takes single word as key for outerMap and adds the keys
	 * and values to the respective innerMap for the particular key.
	 * 
	 */
	public void addToMap(String word, String fileName, Integer position)
	{
		TreeMap<String , ArrayList<Integer>> innerMap;
		if(!outerMap.containsKey(word))
			outerMap.put(word, new TreeMap<String, ArrayList<Integer>>());
		
		innerMap = outerMap.get(word);
		if(!innerMap.containsKey(fileName)) 
			innerMap.put(fileName, new ArrayList<Integer>());
		innerMap.get(fileName).add(position);	
	}	
	
	/* printMap function prints the filename(key) and position(value) 
	 * of each word encountered in a file. 
	 * 
	 * 
	 */
	public void printMap(String txtFile) 
	{
		PrintWriter outFile;
		try {
				outFile = new PrintWriter(new File(txtFile));
				for(String outerKey : outerMap.keySet())
				{
					outFile.println(outerKey);
					TreeMap<String, ArrayList<Integer>> innerMap = outerMap.get(outerKey);
					for(String  innerKey : innerMap.keySet())
					{
						ArrayList<Integer> innerValues = innerMap.get(innerKey);
						outFile.println("\""+ innerKey+"\", "+ innerValues.toString()
							.replace("[", "")   
                            .replace("]", ""));
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


	

	
	

