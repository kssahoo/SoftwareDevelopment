import java.io.*;
import java.util.*;
public class InvertedIndex 
{
	private TreeMap<String, TreeMap<String, ArrayList<Integer>>> outerMap = new TreeMap<String, TreeMap<String, ArrayList<Integer>>>();

	/**
	 * addAll method combines all the localIndex results to local map. 
	 * 
	 */
	public void addAll(InvertedIndex local) 
	{
		for (String key : local.outerMap.keySet()) 
		{
			TreeMap<String, ArrayList<Integer>> innerMap;
			innerMap = local.outerMap.get(key);
			if (!this.outerMap.containsKey(key))
			{
				this.outerMap.put(key, innerMap);
			}
			else
			{
				this.outerMap.get(key).putAll(innerMap);
			}
		}
	}

	/*
	 * addToMap function takes single word as key for outerMap and adds the keys
	 * and values to the respective innerMap for the particular key.
	 */
	public void addToMap(String word, String fileName, Integer position)
	{
		TreeMap<String, ArrayList<Integer>> innerMap;
		if (!outerMap.containsKey(word))
			outerMap.put(word, new TreeMap<String, ArrayList<Integer>>());

		innerMap = outerMap.get(word);
		if (!innerMap.containsKey(fileName))
			innerMap.put(fileName, new ArrayList<Integer>());
		innerMap.get(fileName).add(position);
	}

	/*
	 * printMap function prints the filename(key) and position(value) of each
	 * word encountered in a file.
	 */
	public void printMap(String txtFile) 
	{
		PrintWriter outFile;
		try {
			outFile = new PrintWriter(new File(txtFile));
			for (String outerKey : outerMap.keySet()) 
			{
				outFile.println(outerKey);
				TreeMap<String, ArrayList<Integer>> innerMap = outerMap.get(outerKey);
				for (String innerKey : innerMap.keySet()) 
				{
					ArrayList<Integer> innerValues = innerMap.get(innerKey);
					outFile.println("\""
							+ innerKey
							+ "\", "
							+ innerValues.toString().replace("[", "")
									.replace("]", ""));
				}
				outFile.println();
			}
			outFile.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * For each query word, it finds words in the inverted index that start with
	 * this query word. Then it either creates a new SearchResult or updates the
	 * existing SearchResult depending on whether the corresponding file path
	 * was previously added to locationsMap or not.
	 */
	public ArrayList<SearchResult> partialSearch(String input) 
	{
		/*
		 * The local hash map i.e locationsMap is where file path is stored as
		 * key and SearchResult object as value.
		 */
		HashMap<String, SearchResult> locationsMap = new HashMap<String, SearchResult>();
		SortedMap<String, TreeMap<String, ArrayList<Integer>>> outerTailMap = new TreeMap<String, TreeMap<String, ArrayList<Integer>>>();
		TreeMap<String, ArrayList<Integer>> innerTailMap = new TreeMap<String, ArrayList<Integer>>();
		String[] queryWords = input.split(" ");
		for (String word : queryWords) 
		{
			if (word != null) 
			{
				word = word.trim();
				word = word.toLowerCase();
				word = word.replaceAll("[^a-zA-Z0-9\\s]", "");
				word = word.replaceAll("\\W", "");
				outerTailMap = outerMap.tailMap(word);
				for (String key : outerTailMap.keySet()) 
				{
					if (key.startsWith(word)) 
					{
						SearchResult sr;
						innerTailMap = outerTailMap.get(key);
						for (String filePath : innerTailMap.keySet()) 
						{
							ArrayList<Integer> aList = innerTailMap
									.get(filePath);
							if (!locationsMap.containsKey(filePath)) 
							{
								sr = new SearchResult(filePath);
								locationsMap.put(filePath, sr);
							}
							else 
							{
								sr = locationsMap.get(filePath);
							}
							sr.update(aList.get(0), aList.size());
						}
					}
					else 
					{
						break;
					}
				}
			}
		}
		ArrayList<SearchResult> arrList = new ArrayList<SearchResult>(locationsMap.values());
		Collections.sort(arrList);
		return arrList;
	}
}
