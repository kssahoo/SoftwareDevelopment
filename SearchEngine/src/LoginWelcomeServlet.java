import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginWelcomeServlet extends LoginBaseServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String user = getUsername(request);

	     if (user != null) {
			prepareResponse("Welcome", response);

			PrintWriter writer = response.getWriter();
			
			writer.println("<p>Hello " + user + "!</p>");
			writer.println("<p><a href=\"/login?logout\">(logout)</a></p>"); 
			System.out.println("There");

			
			finishResponse(response);
		}
		else {
			  response.sendRedirect("/login");
	    }
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}