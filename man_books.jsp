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
	Add New Book:
	<form name="new_book" method=get onsubmit="return check_all_fields(this)" action="man_books.jsp">
		<input type=hidden name="searchAttribute" value="1">
		<input type=text name="isbn" length=20>
		<input type=text name="authors" length=20>
		<input type=text name="title" length=20>
		<input type=text name="publisher" length=20>
		<input type=text name="published" length=20>
		<input type=text name="inventory" length=20>
		<input type=text name="price" length=20>
		<input type=text name="format" length=20>
		<input type=text name="keywords" length=20>
		<input type=text name="subject" length=20>
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
	cs5530.Feedback feedback = new Feedback();
	int c = Integer.parseInt(searchAttribute);
	String isbn = request.getParameter("isbn");
	String authors = request.getParameter("authors");
	String title = request.getParameter("title");
	String publisher = request.getParameter("publisher");
	String published = request.getParameter("published");
	String inventory = request.getParameter("inventory");
	String price = request.getParameter("price");
	String format = request.getParameter("format");
	String keywords = request.getParameter("keywords");
	String subject = request.getParameter("subject");
	switch(c)
	{
		case 1:
			book.newBook(isbn, title, authors, publisher, published, inventory, price, format, keywords, subject, con.stmt);
			%>  
			  The book has been entered, thank you!<BR><BR>
			<%
			break;
	}
	%>
		<BR><a href="man_books.jsp"> New query </a></p>
	<%

 con.closeStatement();
 con.closeConnection();
}  // We are ending the braces for else here
%>


</body>