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
	User Data:
	<form name="user_data" method=get onsubmit="return check_all_fields(this)" action="cust_records.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="login" length=20>
		<input type=submit>
	</form>
	
	Sales History:
	<form name="sales_history" method=get onsubmit="return check_all_fields(this)" action="cust_records.jsp">
		<input type=hidden name="searchAttribute" value="2">
		<input type=text name="login" length=20>
		<input type=submit>
	</form>
	
	Feedback History:
	<form name="feedback_history" method=get onsubmit="return check_all_fields(this)" action="cust_records.jsp">
		<input type=hidden name="searchAttribute" value="3">
		<input type=text name="login" length=20>
		<input type=submit>
	</form>
	
	Feedback by Usefulness:
	<form name="feedback_usefulness" method=get onsubmit="return check_all_fields(this)" action="cust_records.jsp">
		<input type=hidden name="searchAttribute" value="4">
		<input type=text name="login" length=20>
		<input type=submit>
	</form>
	
	Trusted/Non-Trusted Users:
	<form name="trusted_users" method=get onsubmit="return check_all_fields(this)" action="cust_records.jsp">
		<input type=hidden name="searchAttribute" value="5">
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
	cs5530.Feedback feedback = new Feedback();
	
	int c = Integer.parseInt(searchAttribute);
	String login = request.getParameter("login");

	switch(c)
	{
		case 1:
			%>  
			  <p><b>Current Users Data is:</b><BR><BR>
			  <%=customer.selectAll(login, con.stmt)%><BR><BR>
			<%
			break;
		case 2:
			%>  
			  <p><b>Current Users Sale History is:</b><BR><BR>
			  <%=order.salesHistory(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
		case 3:
			%>  
			  <p><b>Current Users Feedback History is:</b><BR><BR>
			  <%=feedback.selectUserFeedback(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
		case 4:
			%>  
			  <p><b>Current Users Feedback History is:</b><BR><BR>
			  <%=feedback.selectOrderedFeedback(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
		case 5:
			%>  
			  <p><b>Current Users Trusted/Non-Trusted Users is:</b><BR><BR>
			  <%=feedback.selectTrusted(customer.getCid(login, con.stmt), con.stmt)%><BR><BR>
			<%
			break;
	}	
	%>
		<BR><a href="cust_records.jsp"> New query </a></p>
	<%

	con.closeStatement();
	con.closeConnection();
}  // We are ending the braces for else here
%>

</body>