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

@WebServlet("/admin_page")
public class AdminPage extends HttpServlet
                        implements iCommunicatesHandling{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		DBApi dbapi = LoginPage.dbapi;
		Correctness correctness = new Correctness();
		
	    if (req.getParameter("create_user") != null) {
	        //tworzenie użytkownika
	         String login_input = req.getParameter("login_input");
	         String password_input = req.getParameter("password_input");
	         String username_input = req.getParameter("username_input");
	         String privileges_input = req.getParameter("privileges_select");
	         int privileges = Integer.parseInt(privileges_input);
	         if(correctness.checkFillOfFields(login_input, password_input, 
	        		 username_input, privileges_input)){
	             
	             if(correctness.isLoginAvailable(login_input)){	             
	                 
	                 boolean result = dbapi.insertUser(login_input, password_input, username_input, privileges);
	                 
	                 if(result){ 
	                     req.setAttribute("msg", "Udalo sie stworzyc usera: " + username_input + "!");
	                     RequestDispatcher dispatcher = req.getRequestDispatcher("admin_page.jsp");
	                     dispatcher.forward(req, resp);                      
	                 }
	                 else{ 
	                     System.out.println("!! dbapi.insertUser zwraca false!!");
	                 }               
	             }else {
	                 fillGapAgainAction("admin_page.jsp", 2, req, resp);
	             }
			 }else {
			     fillGapAgainAction("admin_page.jsp", 0, req, resp);
			 }
	    } else if (req.getParameter("delete_user") != null) {
	        
	        //kasowanie      
	        String idDoWywalenia = req.getParameter("user_to_delete");         
	        dbapi.deletetUserById(Integer.parseInt(idDoWywalenia));  
            req.setAttribute("msg", "User z id: " + idDoWywalenia + " Skasowany!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin_page.jsp");
            dispatcher.forward(req, resp);            
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