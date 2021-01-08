package pages;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import database.api.DBApi;

@WebServlet("/success")
public class Success extends HttpServlet{
	DBApi dbapi = LoginPage.dbapi;	
	public Success(){
		System.out.println("tu klasa Success!");				
	}
	public void close_db_connection(){
		System.out.println("tu klasa Success i close_db_connection!");
		
	}	
}
