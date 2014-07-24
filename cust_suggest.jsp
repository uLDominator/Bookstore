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
	Suggested Books:
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="cust_suggest.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="login" length=20>
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
			String login = request.getParameter("login");
			%>  
			  The list of suggested books are as follows:<BR><BR>
			  <%=order.suggestions(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="cust_suggest.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>