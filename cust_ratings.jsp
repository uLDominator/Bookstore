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
	List Feedback:
	<form name="list_feedback" method=get onsubmit="return check_all_fields(this)" action="cust_ratings.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="login" length=20>
		<input type=submit>
	</form>
	
	Rate a Feedback:
	<form name="rate_feedback" method=get onsubmit="return check_all_fields(this)" action="cust_ratings.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="login" length=20>
		<input type=text name="fid" length=11>
		<input type=text name="rating" length=1>
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
	String login = request.getParameter("login");
	switch(c)
	{
		case 1:
			%>  
			  The list of feedbacks are as follows:<BR><BR>
			  <%=feedback.selectAll(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			String fid = request.getParameter("fid");
			String rate = request.getParameter("rating");
			feedback.insertRating(customer.getCid(login, con.stmt), fid, rate, con.stmt);			
			%>
			  Feedback entered, thank you.<BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="cust_ratings.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>