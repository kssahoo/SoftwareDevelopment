public class SearchResult implements Comparable<SearchResult>
{
	private String location;
	private int frequency;
	private int position;
	public SearchResult(String location)
	{
		this.location = location;
		frequency = 0;
		position = Integer.MAX_VALUE;
	}
	
	/* update method updates the values of SearchResult objects based on the new frequency and position encountered for an individual word.    
	 * 
	 * 
	 */
	public void update(int anotherPosition, int anotherFrequency)
	{
		frequency = frequency + anotherFrequency;
		if(position > anotherPosition)
			position = anotherPosition;
		
	}
	
	/* compareTo method is overridden to compare and sort the SearchResult objects based on the frequencies, positions and locations.   
	 * 
	 * 
	 */
	@Override
	public int compareTo(SearchResult other) 
	{
		if(this.frequency > other.frequency)
			return -1;
		else if (this.frequency < other.frequency)
			return 1;
		else {
				if(this.position < other.position)
					return -1;
				else if(this.position > other.position)
					return 1;
				else
					return location.compareTo(other.location);
			}
	}
	public String getLocation()
	{
		
		return location;
	}
	public int getPosition()
	{
		return position;
	}
	public int getFrequency()
	{
		return frequency;
	}
}