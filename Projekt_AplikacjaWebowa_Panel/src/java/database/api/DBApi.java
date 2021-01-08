package database.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import database.model.User;
import database.model.produkt;

public class DBApi {

    public static final String DRIVER = "org.sqlite.JDBC";   
    public static final String DB_URL = "jdbc:sqlite:/Users/Earthalign/Desktop/ProgramowanieWJava/apache-tomcat-9.0.30/projekt3/new_table.db";
    
    private Connection conn;
    private Statement stat;
    
    //laczenie z baza danych, stworzenie objektu stat
    public DBApi(){
        try {
            Class.forName(DBApi.DRIVER); 
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        } 
        try {
            conn = DriverManager.getConnection(DB_URL); 
            stat = conn.createStatement(); 
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } 
        
        createTables();    	
    }
    
// tworzenie tabeli
    public boolean createTables()  {
        String createUsers = "CREATE TABLE IF NOT EXISTS new_table (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        		+ "login varchar(255), password varchar(255), username varchar(255), privileges INTEGER)";

        try {
            stat.execute(createUsers);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
        public boolean createTable()  {
        String createProdukty = "CREATE TABLE IF NOT EXISTS produkty (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        		+ "nazwa varchar(255), cena INTEGER), opis varchar(255), ilosc INTEGER)";

        try {
            stat.execute(createProdukty);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
        
        
    }
    public boolean insertUser(String login, String password, 
    						  String username, int privileges) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into new_table values (NULL, ?, ?, ?, ?);");
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            prepStmt.setString(3, username);
            prepStmt.setInt(4, privileges);
            System.out.println("Wstawiam usera login: " + login + 
            		" password: " + password + " username: " + username
            		  + "privileges: " + privileges);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu uzytkownika!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertProdukt(String nazwa, int cena, 
    						  String opis, int ilosc) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into values (NULL, ?, ?, ?, ?);");
            prepStmt.setString(1, nazwa);
            prepStmt.setInt(2, cena);
            prepStmt.setString(3, opis);
            prepStmt.setInt(4, ilosc);
            System.out.println("Wstawiam nazwe: " + nazwa + 
            		" nazwa: " + cena + "cena: " + opis
            		  + "opis: " + ilosc + "ilosc");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu produktu!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public List<produkt> selectProdukt(){
    	List<produkt> produkty = new LinkedList<produkt>();
    	try{
    		ResultSet result = stat.executeQuery("SELECT * FROM produkty");
    		int id, cena, ilosc;
    		String nazwa, opis;
    		while(result.next()){
    			id = result.getInt("id");
		
    			nazwa = result.getString("nazwa"); 
    			cena = result.getInt("cena");
    			opis = result.getString("opis");
    			ilosc = result.getInt("ilosc");
    			produkt.add(new produkt(id, nazwa, cena, opis, ilosc));    			
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		return null;
    	}    	
    	return produkty;
    }
  
    public List<User> selectUsers(){
    	List<User> users = new LinkedList<User>();
    	try{
    		ResultSet result = stat.executeQuery("SELECT * FROM new_table");
    		int id, privileges;
    		String login, password, username;
    		while(result.next()){
    			id = result.getInt("id");
    			login = result.getString("login"); 
    			password = result.getString("password");
    			username = result.getString("username");
    			privileges = result.getInt("privileges");
    			users.add(new User(id, login, password, username, privileges));    			
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		return null;
    	}    	
    	return users;
    }
    
    public List<User> deletetUserByLogin(String l){
        
        System.out.println("** deletetUserByLogin method, calling with l: " + l);
    	
    	List<User> users = new LinkedList<User>();
    	    	
    	users = selectUsers();
    	boolean isUserFound = false;

        //check if choosen user exists
    	for(User u:users){
    		System.out.println(u);
    		if ( l.equals(u.getLogin()) ){  
    			System.out.println("login kasowanego usera: " + l);
    			isUserFound = true;
    		}	
    	} 
    	
    	if(isUserFound){
    	  
        	try{
            	
        		String deleteUser = "DELETE FROM new_table WHERE login='"+l+"'";
                stat.execute(deleteUser);
                System.out.println("DELETE USER command executed!");   		
           	}catch(SQLException e){
        		e.printStackTrace();
        		return null;
        	}    	
        	users = selectUsers();
        	
                
                //usuwanie uzytkownika z listy
        	return users;    	
    	}
		else{
			System.out.println("Nie znaleziono takiego usera");
			return users;
		}   
    }
    
    public List<User> deletetUserById(int id){
        
        System.out.println("** DeletetUserById method, calling with id: " + id);
        
        List<User> users = new LinkedList<User>();
                        
        users = selectUsers();
        boolean isUserFound = false;
        for(User u:users){
            System.out.println(u);
            if ( id == u.getId() ){  
                System.out.println("id kasowanego usera: " + id);
                isUserFound = true;
            }   
        } 
        
        if(isUserFound){
            
            try{
                
                String deleteUser = "DELETE FROM new_table WHERE id='"+id+"'";
                stat.execute(deleteUser);
                System.out.println("DELETE USER command executed!");        
            }catch(SQLException e){
                e.printStackTrace();
                return null;
            } 
                
            users = selectUsers();
            
           
            return users;       
        }
        else{
            System.out.println("Nie znaleziono takiego usera");
            return users;
        }   
    }
    
    public boolean deleteTable(){
    	
        String dropTable = "DROP TABLE new_table";
        try {
            stat.execute(dropTable);
            System.out.println("DROP command executed!");
        } catch (SQLException e) {
            System.err.println("Blad przy DROP!");
            e.printStackTrace();
            return false;
        }
        return true;   
    }
      
    public boolean deleteAllUsers(){
    	
        String deleteUsers = "DELETE FROM new_table";

        try {
            stat.execute(deleteUsers);
            System.out.println("DELETE command executed!");
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu!");
            e.printStackTrace();
            return false;
        }
        return true;    	 
    }
    
   //zamkykanie polaczenia z baza danych 
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }    
}