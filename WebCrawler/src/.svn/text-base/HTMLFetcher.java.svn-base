import java.io.*;
import java.net.*;

public class HTMLFetcher 
{
	private static final int PORT = 80;
	public String fetchHTML(String urlDomain) throws MalformedURLException, IOException  
	{
		
	    URL url = new URL(urlDomain);
		System.out.println(url.getHost() + ":" + PORT);
		Socket socket = new Socket(url.getHost(), 80);
		PrintWriter writer = 
				new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			    BufferedReader reader=
	    		new BufferedReader(new InputStreamReader(socket.getInputStream()));
		StringBuilder content = new StringBuilder();
		String request;
	    // FILL IN CODE here: Create a String request that is an HTTP GET request to get file index.html from url domain.
	   
		request= "GET "+ url.getFile() + " HTTP/1.1\r\n";
		request +="Host: " + url.getHost() + "\r\n";
		request +="Connection: close\r\n";
	    request += "\r\n";
		writer.println(request);
		writer.flush();
		String input;
		boolean flag = false;
		while((input = reader.readLine()) != null)
		{
			if(input.contains("</html>")){
				content = content.append(input + " ");
				break;
			}
			if(input.startsWith("<!DOCTYPE") || input.startsWith("<html>"))
			{
				flag = true;
			}
			if(flag)
				content = content.append(input + " ");
		}
		writer.close();
		reader.close();
		socket.close();
		return content.toString();
	}
}










