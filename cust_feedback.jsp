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
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="cust_feedback.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=submit>
	</form>
	
	Give Feedback:
	<form name="give_feedback" method=get onsubmit="return check_all_fields(this)" action="cust_feedback.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="login" length=20>
		<input type=text name="isbn" length=20>
		<input type=text name="rating">
		<input type=text name="comment" length=100>
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
	cs5530.Feedback feedback = new Feedback();
	
	int c = Integer.parseInt(searchAttribute);

	switch(c)
	{
		case 1:
			%>  
			  <p><b>Listing books in JSP:</b><BR><BR>
			  The list of books are as follows:<BR><BR>
			  <%=book.select("","","",con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			String login = request.getParameter("login");
			String isbn = request.getParameter("isbn");
			String rating = request.getParameter("rating");
			String comment = request.getParameter("comment");
			
			String count = feedback.find(isbn, customer.getCid(login, con.stmt), con.stmt);
			if(count.equals("1"))
			{
				%>  
				  You have already given feedback on this book.<BR><BR>
				<%
			}
			else
			{
				feedback.insert(rating, comment, isbn, customer.getCid(login, con.stmt), con.stmt);
				%>
				  Feedback entered, thank you.<BR><BR>
				<%
			}
			break;
	}	
	%>
		<BR><a href="cust_feedback.jsp"> New query </a></p>
	<%

	con.closeStatement();
	con.closeConnection();
}  // We are ending the braces for else here
%>

</body>