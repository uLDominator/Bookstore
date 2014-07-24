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
	List Books by Year:
	<form name="by_year" method=get onsubmit="return check_all_fields(this)" action="cust_search.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="authors" length=20>
		<input type=text name="title" length=20>
		<input type=submit>
	</form>
	
	List Books by Feedback Score:
	<form name="by_feedback" method=get onsubmit="return check_all_fields(this)" action="cust_search.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="authors" length=20>
		<input type=text name="title" length=20>
		<input type=submit>
	</form>
	
	List Books by Trusted Feedback Score:
	<form name="by_trust" method=get onsubmit="return check_all_fields(this)" action="cust_search.jsp">
		<input type=hidden name="searchAttribute" value="3">
		<input type=text name="login" length=20>
		<input type=text name="authors" length=20>
		<input type=text name="title" length=20>
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
	String authors = request.getParameter("authors");
	String title = request.getParameter("title");
	switch(c)
	{
		case 1:
			%>  
			  The list of books are as follows:<BR><BR>
			  <%=book.selectByYear(authors, title, con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			%>  
			  The list of books are as follows:<BR><BR>
			  <%=book.selectByFeedback(authors, title, con.stmt)%><BR><BR>
			<%
			break;
		case 3:
			String login = request.getParameter("login");
			%>  
			  The list of books are as follows:<BR><BR>
			  <%=book.selectByTrust(authors, title, customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="cust_search.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>