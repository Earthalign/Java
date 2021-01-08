package database.model;
public class User {
    
	private int id;
	private String login;
	private String password;
	private String username;
	private int privileges;
			
	public User(int id, String login, String password, String username, int privileges) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.username = username;
		this.privileges = privileges; //jeśli jest administratorem
	}
		
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", username=" + username
				+ ", privileges=" + privileges + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPrivileges() {
		return privileges;
	}
	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}
}