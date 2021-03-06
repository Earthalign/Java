<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="pages.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="script/mouseHighlight.js"></script>
	<link rel="Stylesheet" type="text/css" href="style/style.css" />
	<title>Register Page</title>
</head>
<body>	
	<form action="register" method="post">
		<div class="page_content">
			<h1>Register Page</h1>
			
			<div class="background_content"> 
				<div class="main_content">
					<table>
						<tr class="mouseOut">
							<td>Name: </td>
							<td> <input class="glowing-border" type="text" name="name_field" style="width: 150px; "/> </td>
						</tr>
						<tr class="mouseOut">
							<td>Login: </td>
							<td> <input class="glowing-border" type="text" name="login_field" style="width: 150px; "/> </td>
						</tr>
						<tr class="mouseOut">
							<td>Password: </td>
							<td> <input class="glowing-border" type="password" name="password_field" style="width: 150px; "/> </td>
						</tr>
					</table>
					<br>
					<br>
					<input type="button" onclick="location.href='login.jsp';" value="Back" />
					<input type="submit" id="create_account" value="Create Account" style="width: 120px; "/>
					<br>
					<% 
						String msg = (String) request.getAttribute("msg");
						if( msg == null)
							msg = "";
					%>
					<%=msg %>
				</div>
			</div>
		</div>		
	</form>
</body>
</html>