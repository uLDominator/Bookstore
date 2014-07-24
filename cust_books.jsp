<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.attributeValue.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

</script> 
</head>
<body>
<%
String searchAttribute = request.getParameter("searchAttribute");
if( searchAttribute == null )
{
%>
	List Books:
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="cust_books.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=submit>
	</form>
	
	Order Books:
	<form name="order_books" method=get onsubmit="return check_all_fields(this)" action="cust_books.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="login" length=20>
		<input type=text name="isbn" length=11>
		<input type=text name="quantity" length=4>
		<input type=submit>
	</form>
	
	<BR><a href="customers.jsp"> Back </a></p>
<%
} 
else 
{
	cs5530.Connector con = new Connector();
	cs5530.Book book = new Book();
	cs5530.Order order = new Order();
	cs5530.Customer customer = new Customer();
	int c = Integer.parseInt(searchAttribute);

	switch(c)
	{
		case 1:
			%>  
			  The list of books are as follows:<BR><BR>
			  <%=book.select("","","",con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			int orderID = Integer.parseInt(order.getOid(con.stmt));
			orderID++;
			String login = request.getParameter("login");
			String ISBN = request.getParameter("isbn");
			String quantity = request.getParameter("quantity");
			String quantity_available = book.getQuantity(ISBN, con.stmt);
			String cid = customer.getCid(login, con.stmt);
			Double total = 0.0;

			if(Integer.parseInt(quantity_available) >= Integer.parseInt(quantity))
			{
				order.placeOrder(orderID, cid, ISBN, quantity, con.stmt);
				book.updateQuantity(ISBN, "-"+quantity, con.stmt);
				String price = book.getCost(ISBN, con.stmt).substring(1);
				total += Double.parseDouble(price) * Integer.parseInt(quantity);
				%>  
				  <p><b>Ordering books in JSP:</b><BR><BR>
				  The total cost of the books ordered is:<BR>
				  <%="$"+total%><BR><BR>
				<%
			}
			else
			{
				%>  
				  Not enough books in stock. :(<BR><BR>
				<%
			}
			break;
	}
	%>
		<BR><a href="cust_books.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>