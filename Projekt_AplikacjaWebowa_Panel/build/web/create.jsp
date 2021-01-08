<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="pages.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="script/mouseHighlight.js"></script>
	<title>CreateYourOwnProduct</title>
</head>
<body>	
    <center>
	<form action="/create" method="post">
		<div class="page_content">
			<h1>Create Product</h1>
			
			<div class="background_content"> 
				<div class="main_content">
					<table>
						<tr class="mouseOut">
							<td>Nazwa: </td>
							<td> <input class="glowing-border" type="text" name="nazwa_field" style="width: 150px; "/> </td>
						</tr>
						
						<tr class="mouseOut">
							<td>Opis: </td>
							<td> <input class="glowing-border" type="text" name="opis_field" style="width: 150px; "/> </td>
						</tr>
                                                <tr class="mouseOut">
							<td>Ilosc: </td>
							<td> <input class="glowing-border" type="text" name="ilosc_field" style="width: 150px; "/> </td>
						</tr>
                                                
					</table>
					<br>
					<br>
					<input type="button" onclick="location.href='success.jsp';" value="SuccesPage" />
					<input type="submit" id="create_produkt" value="Create produkt" style="width: 120px; "/>
                                        	<input type="button" onclick="location.href='https://earthalign.github.io/witoldwluczkowski/strona2.html'; " value="StronaInternetowa" />

					<br>
				
				</div>
			</div>
		</div>		
	</form>
        </center>
</body>
</html>