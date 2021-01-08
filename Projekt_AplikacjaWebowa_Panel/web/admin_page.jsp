<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="pages.*" 
    import="database.model.*" 
    import="database.api.*"
    import="java.util.List"    
%>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="script/selectItem.js"></script>
	<link rel="Stylesheet" type="text/css" href="style/style.css" />
	<title>Admin Page</title>
</head>

<body>
	<form action="admin_page" method="post">		
		<%
			DBApi dbapi = LoginPage.dbapi;
			List<User> users = dbapi.selectUsers();	

		 %>	
		<div class="page_content"> 		
	 		<h1>Admin Page</h1>
                        
			<div class="background_content"  >			
				<div class="main_content">
					<h3>Users:</h3>
					<center><table> 
						<tr>
							<th>Nr</th>
							<th>id</th>
							<th>login</th>
							<th>password</th>
							<th>username</th>
							<th>privileges</th>						
						</tr>
						<% 
						for(int i=0;i<users.size();i++){
						%>
							<tr class="odd" onclick="toggle(this, <%=users.get(i).getId() %> )">
								<td><%=i %></td>
								<td><%=users.get(i).getId() %></td>
								<td><%=users.get(i).getLogin() %></td>
								<td><%=users.get(i).getPassword() %></td>
								<td><%=users.get(i).getUsername() %></td>
								<td><%=users.get(i).getPrivileges() %></td>			
							</tr>	
                                                       
						<%
						}		
						%>
							<tr class="table_hover">
								<td><%=users.size()%></td>
								<td><% %></td>
								<td><input class="glowing-border" type="text" name="login_input" placeholder="login"/></td>
								<td><input class="glowing-border" type="text" name="password_input" placeholder="password"/></td>
								<td><input class="glowing-border" type="text" name="username_input" placeholder="input"/></td>
								<td><select name="privileges_select">
									<option>0</option>
									<option>1</option></select>
								</td>			
							</tr>							
					</table>
					<input type="hidden" name="user_to_delete" id="user_to_delete" value="">
					</center>
					<br>
					<% 
						String msg = (String) request.getAttribute("msg");
						if( msg == null)
							msg = "";
					%>
					Komunikat: <%=msg %>
					<br>				
					<input type="submit" name="delete_user" value="delete_button" id="delete_user_button" style="width: 120px; " disabled/>
					<input type="submit" name="create_user" value="create_user" style="width: 120px; "/>
					<br>
					<br>
				</div>	
				<br>				
				<center><input class="button" type="button" onclick="location.href='login.jsp';" 
					value=" back to Login" /></center>
				<br>	
				
			</div>
		</div>
	</form>
                                        
                                        
                                        
                                        
</body>
</html>