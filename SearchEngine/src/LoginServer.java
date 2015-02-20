import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
/* Author: Prof. Engle
 * 
 */
public class LoginServer {
	private static int PORT = 8080;

	public static void main(String[] args) {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();

		handler.addServletWithMapping(LoginUserServlet.class,    "/login");
		handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
		handler.addServletWithMapping(LoginWelcomeServlet.class,  "/welcome");
		handler.addServletWithMapping(LoginRedirectServlet.class, "/*");
		server.setHandler(handler);



		try {
			server.start();
			server.join();

		}
		catch (Exception ex) {
			System.out.println("Interrupted while running server." + ex.getMessage());
			System.exit(-1);
		}
	}
}