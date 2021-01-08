<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" import="pages.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="style/style.css" />
	<title>UDAŁO SIE!</title>
</head>
<body>
	<form action="/success" method="post">
		<div class="page_content">
			<h1>Success Page</h1>
			
			<div class="background_content"> 
				<div class="main_content">
					
					<br>	
					<%	
						Success s = new Success();
						String userName = (String) session.getAttribute("username");
					%>
					Hello <%=userName %>!
					<br>
					<input type="button" onclick="location.href='login.jsp';" 
						value="back to Login" />
                                        <input type="button" onclick="location.href='create.jsp';" 
						value="Go to UserPage" />
				</div>
			</div>	
		</div>
	</form>	
</body>
</html>