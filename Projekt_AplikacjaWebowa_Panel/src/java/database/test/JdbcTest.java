package database.test;

import java.util.List;

import database.model.User;
import database.api.DBApi;
import database.model.produkt;
public class JdbcTest {
 
    public static void main(String[] args) {
    	
    	List<User> users;
        List<produkt> produkty;
       	DBApi databaseApi = new DBApi();
    	databaseApi.insertUser("admin", "admin", "admin", 1); 
        databaseApi.insertProdukt("Kawa", 1, "Po prostu KAWA", 1);
            //chociaż jeden użytkownik musi być w bazie
            // abym mógł zalogować się do panelu administratora 
    	users = databaseApi.selectUsers();
        produkty = databaseApi.selectProdukt();
        
        System.out.println("Lista userów: ");
        for(User u: users)
            System.out.println(u);
        
        System.out.println("Lista produktów: ");
        for(produkt p: produkty)
            System.out.println(p);

        
        
        
    }
}