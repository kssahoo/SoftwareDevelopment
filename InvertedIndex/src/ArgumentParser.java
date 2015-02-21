import java.util.HashMap;
public class ArgumentParser
{
	private HashMap<String, String> hmap=new HashMap<String, String>();
	public ArgumentParser(String[] args)
	{
		parseArguments(args);
	}
	
	/* parseArguments function, parses the command line inputs to identify associated keys and values.   
	 * 
	 */
	public void parseArguments(String[] args)
	{
		String value = null, key=null;
		for(int i=0; i<args.length; i++)
		{
			if(isFlag(args[i]) == true)
			{
			key = args[i];
			    if(i != args.length-1)
				{
			    	if(isValue(args[i+1]) == true)
					{
			    			value = args[i+1];
			    			i++;
					}
					else
					{
						value = null;
					}
				}
			    else
			    {
			    		value = null;
			    }
			    if(!hmap.containsKey(key) && key != null)
				{
					hmap.put(key, value);
				}
			 }
			 else 			 
			 {
				 	System.out.printf("Invalid value / No key exist for the value : %s \n", args[i]);
			 }
		}
		System.out.println('\n');
		System.out.println(hmap.entrySet());
	}
		
	public boolean isFlag(String flag)
	{
		if(flag.startsWith("-"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isValue(String flag)
	{
		if(!flag.startsWith("-"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasFlag(String flag)
	{
		if(hmap.containsKey(flag))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasValue(String flag)
	{
		if(hmap.get(flag) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getValue(String flag) 
	{
		String strValue = null;
		strValue = hmap.get(flag);
		return strValue;
	}
	
	public static void main(String[] args){}
}
