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
	<title>User Page</title>
</head>

<body>
	<form action="user_page" method="post">		
		<%
			DBApi dbapi = LoginPage.dbapi;
			List<produkt> produkty = dbapi.selectProdukt();	

		 %>	
		<div class="page_content"> 		
	 		<h1>User Page</h1>
                        
			<div class="background_content"  >			
				<div class="main_content">
					<h3>Produkty:</h3>
					<center><table> 
						<tr>
							<th>Nr</th>
							<th>id</th>
							<th>Nazwa</th>
							<th>Cena</th>
							<th>Opis</th>
							<th>Ilosc</th>						
						</tr>
						<% 
						for(int i=0;i<produkty.size();i++){
						%>
							<tr class="odd" onclick="toggle(this, <%=produkty.get(i).getId() %> )">
								<td><%=i %></td>
								<td><%=produkty.get(i).getId() %></td>
								<td><%=produkty.get(i).getNazwa() %></td>
								<td><%=produkty.get(i).getCena() %></td>
								<td><%=produkty.get(i).getOpis() %></td>
								<td><%=produkty.get(i).getIlosc() %></td>			
							</tr>	
						<% 
						}		
						%>
							<tr class="table_hover">
								<td><%=produkty.size()%></td>
								<td><% %></td>
								<td><input class="glowing-border" type="text" name="nazwa_input" placeholder="nazwa"/></td>
								
								<td><input class="glowing-border" type="text" name="opis_input" placeholder="opis"/></td>
                                                                 <td><input class="glowing-border" type="text" name="ilosc_input" placeholder="ilosc"/></td>
		
							</tr>							
					</table>
					<input type="hidden" name="produkt_to_delete" id="produkt_to_delete" value="">
					</center>
					<br>
					<% 
						String msg = (String) request.getAttribute("msg");
						if( msg == null)
							msg = "";
					%>
					Komunikat: <%=msg %>
					<br>				
					<input type="submit" name="delete_produkt" value="delete_button" id="delete_produkt_button" style="width: 120px; " disabled/>
					<input type="submit" name="create_produkt" value="create_produkt" style="width: 120px; "/>
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