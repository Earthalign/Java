package pages;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import features.Communicates;
import features.Correctness;
import features.iCommunicatesHandling;
import database.api.DBApi;

@WebServlet("/register")
public class RegisterPage extends HttpServlet
							implements iCommunicatesHandling {
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		DBApi dbapi = LoginPage.dbapi;	
		String username = req.getParameter("name_field");
		String login = req.getParameter("login_field");		
		String password = req.getParameter("password_field");
		Correctness correctness = new Correctness();
		if(correctness.checkFillOfFields(username, login, password)){
			if(correctness.isLoginAvailable(login)){
				
				boolean result = dbapi.insertUser(login, password, username, 0);

				//open next site (success)
				if(result){
					req.getSession().setAttribute("username", username);
					resp.sendRedirect("success.jsp");
				}else{
					resp.sendRedirect("login.jsp");
				}	
			}else{			
				fillGapAgainAction("register.jsp", 2, req, resp);
			}
		}else{
			fillGapAgainAction("register.jsp", 0, req, resp);			
		}
	}
	
	@Override
	public void fillGapAgainAction(String which_jsp, int whichMsg, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException
	{
    	System.out.println(Communicates.messagesArray[whichMsg]);
    	req.setAttribute("msg", Communicates.messagesArray[whichMsg]);
    	RequestDispatcher dispatcher = req.getRequestDispatcher(which_jsp);
    	dispatcher.forward(req, resp);	
	}
}
