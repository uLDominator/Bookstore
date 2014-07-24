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
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="cust_useful.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=submit>
	</form>
	
	Get Top Feedback(s) for a Book:
	<form name="order_books" method=get onsubmit="return check_all_fields(this)" action="cust_useful.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="isbn" length=11>
		<input type=text name="count" length=4>
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
			String isbn = request.getParameter("isbn");
			String count = request.getParameter("count");
			%>  
			  The list of feedbacks are as follows:<BR><BR>
			  <%=feedback.selectUseful(isbn, count, con.stmt)%><BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="cust_useful.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>