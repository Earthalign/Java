package pages;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import features.*;
import database.model.User;
import database.api.DBApi;
@WebServlet("/login")
public class LoginPage extends HttpServlet implements iCommunicatesHandling {
	private static final long serialVersionUID = 1L;
	public static DBApi dbapi = new DBApi();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String login = req.getParameter("login_field");		
		String password = req.getParameter("password_field");
		String username;
		Correctness correctness = new Correctness();
		List<User> users = dbapi.selectUsers();
 		if(correctness.checkFillOfFields("username", login, password)){
			if(correctness.checkIsInputCorrect(login, password)){
				if (users != null){
					for(User u:users){
						if ( login.equals(u.getLogin()) &&
								password.equals(u.getPassword())){
							System.out.println(Communicates.messagesArray[2] +
									", jego privileges: " + u.getPrivileges());
							if(u.getPrivileges()!= 0){
								resp.sendRedirect("admin_page.jsp");		
							}else{
								username = u.getUsername();
								req.getSession().setAttribute("username", username);
								resp.sendRedirect("success.jsp");	
							}						
						}
					}
				}
			}else{
				fillGapAgainAction("login.jsp", 1, req, resp);	
			}
		}else{		
			fillGapAgainAction("login.jsp", 0, req, resp);
		} 
	}

	@Override
	public void fillGapAgainAction(String jsp, int whichMsg, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException
	{
    	System.out.println(Communicates.messagesArray[whichMsg]);
    	req.setAttribute("msg", Communicates.messagesArray[whichMsg]);
    	RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
    	dispatcher.forward(req, resp);	
	}
}