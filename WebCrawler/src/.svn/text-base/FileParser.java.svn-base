import java.io.*;


public class FileParser
{
	private InvertedIndex invertedMapObject;
	public FileParser(InvertedIndex invertedMapObject)
	{
		this.invertedMapObject = invertedMapObject;
	}
	
	/* parseFile function reads data line by line from input file path and splits them into 
	 * individual words.Then it passes each word to the function addToMap within WordsMap class 
	 * to store a mapping of the word to the filename and position where it was found.  
	 * 
	 */
	public void parseFile(File path)
	{
		
		int position = 0;
		try{
		 	FileReader fr = new FileReader(path);  
		 	BufferedReader br = new BufferedReader(fr);
		 	String data; 
		 	try {
		 			while((data = br.readLine()) != null)           
		 			{	
		 				String[] words = data.split("\\s+");
		 				for(String word: words)
		 				{
		 					if(word != null)
		 					{
		 						word = word.trim();
		 						word = word.toLowerCase();
		 						word = word.replaceAll("[^a-zA-Z0-9\\s]", "");
		 						word = word.replaceAll("\\W","");
		 						if(!word.isEmpty())
		 						{
		 							
		 							position = position + 1;
		 							invertedMapObject.addToMap(word, path.getAbsolutePath(), position);
		 							
		 						}
		 					}
		 				}	
		 			}
		 			br.close();
		 		} 
		 		catch (FileNotFoundException f) 
		 		{
					System.out.println("File is not present at the path provided:" + f);
				}
			}
		 	catch(IOException e)
		 	{
		 		System.out.println(e.getMessage());
		 	}
	}

}