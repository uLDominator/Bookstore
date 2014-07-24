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
	Popular Books:
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="man_stats.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="count" length=3>
		<input type=submit>
	</form>
	
	Popular Authors:
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="man_stats.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="count" length=3>
		<input type=submit>
	</form>
	
	Popular Publishers:
	<form name="list_books" method=get onsubmit="return check_all_fields(this)" action="man_stats.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="count" length=3>
		<input type=submit>
	</form>
	
	<BR><a href="managers.jsp"> Back </a></p>
<%
} 
else 
{
	cs5530.Connector con = new Connector();
	cs5530.Book book = new Book();
	cs5530.Order order = new Order();
	cs5530.Customer customer = new Customer();
	int c = Integer.parseInt(searchAttribute);
	String count = request.getParameter("count");

	switch(c)
	{
		case 1:
			%>  
			  The list of books are as follows:<BR><BR>
			  <%=book.popular(count, con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			%>  
			  The list of authors are as follows:<BR><BR>
			  <%=book.popularAuthors(count, con.stmt)%><BR><BR>
			<%
			break;
		case 3:
			%>  
			  The list of publishers are as follows:<BR><BR>
			  <%=book.popularPublishers(count, con.stmt)%><BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="man_stats.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>