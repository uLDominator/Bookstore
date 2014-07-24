package cs5530;

import java.sql.*;

public class Book 
{
	public Book()
	{}
	
	public String select(String authors, String publisher, String published, Statement stmt)
	{
		String sql = "SELECT ISBN, Title FROM Book WHERE authors like '%"+authors+"%' AND publisher like '%"+publisher+"%' AND published like '%"+published+"%'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("ISBN") + "\t" + rs.getString("Title") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}
	
	public String getCost(String ISBN, Statement stmt)
	{
		String sql = "SELECT price FROM Book WHERE isbn='"+ISBN+"'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("Price"); 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}
	
	public String getQuantity(String ISBN, Statement stmt)
	{
		String sql = "SELECT copies FROM Book WHERE isbn='"+ISBN+"'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("copies"); 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}
	
	public void updateQuantity(String ISBN, String quantity, Statement stmt)
	{
		String sql  = "UPDATE Book SET Copies=Copies+"+quantity+" WHERE ISBN='"+ISBN+"'";
		
		try
		{
			stmt.execute(sql);
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
	}
	
	public String selectByYear(String authors, String title, Statement stmt)
	{
		String sql = "SELECT ISBN, Title FROM Book WHERE Title like '%"+title+"%' AND authors like '%"+authors+"%' ORDER BY published";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("ISBN") + "\t" + rs.getString("Title") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String selectByFeedback(String authors, String title, Statement stmt)
	{
		String sql = "SELECT B.ISBN, B.Title, avg(F.score) AS avgScore FROM Book B, Feedback F WHERE B.Title like '%"+title+"%' AND B.authors like '%"+authors+"%' AND B.ISBN=F.ISBN GROUP BY F.ISBN ORDER BY avgScore DESC";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("ISBN") + "\t" + rs.getString("Title") + "\t" + rs.getString("avgScore") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String selectByTrust(String authors, String title, String cid, Statement stmt)
	{
		String sql = "SELECT B.ISBN, B.Title, avg(F.score) AS avgScore FROM Book B, Feedback F"
				+ " WHERE B.Title like '%"+title+"%' AND B.authors like '%"+authors+"%' AND B.ISBN=F.ISBN GROUP BY F.ISBN AND F.cid IN "
						+ "(SELECT cid2 FROM Trust WHERE cid1="+cid+" AND isTrusted=1) ORDER BY avgScore DESC";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("ISBN") + "\t" + rs.getString("Title") + "\t" + rs.getString("avgScore") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}


	public String degree1(String author1, String author2, Statement stmt)
	{
		String sql = "SELECT COUNT(*) FROM Book B WHERE B.authors like '%"+author1+"%' AND B.authors like '%"+author2+"%'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("count(*)"); 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String degree2(String author1, String author2, Statement stmt)
	{
		String sql = "SELECT Count(*) "
				+ "FROM (SELECT ISBN, replace(authors, '"+author1+"', '') as auth from Book WHERE (authors LIKE '%"+author1+"%' AND authors!='"+author1+"')) as A, "
				+ "(SELECT ISBN, replace(authors, '"+author2+"', '') as auth from Book WHERE (authors LIKE '%"+author2+"%' AND authors!='"+author2+"')) as B "
				+ "WHERE A.auth LIKE B.auth";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("count(*)");
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public void newBook(String ISBN, String title, String authors, String publisher, String published, String inventory, String price, String format, String keywords, String subject, Statement stmt)
	{
		String sql  = "INSERT INTO Book(ISBN, Title, Authors, Publisher, Published, Copies, Price, Format, Keywords, Subject) VALUES "
				+ "('"+ISBN+"', '"+title+"', '"+authors+"', '"+publisher+"', '"+published+"', "+inventory+", '"+price+"', '"+format+"', '"+keywords+"', '"+subject+"')";
		
		try
		{
			stmt.execute(sql);
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
	}


	public String popular(String m, Statement stmt)
	{
		String sql = "SELECT B.title, SUM(O.Quantity) AS amount FROM Orders O, Book B WHERE B.ISBN=O.ISBN GROUP BY B.Title ORDER BY amount DESC LIMIT " + m;
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("title") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String popularAuthors(String m, Statement stmt) 
	{
		String sql = "SELECT B.authors, SUM(O.Quantity) AS amount FROM Orders O, Book B WHERE B.ISBN=O.ISBN GROUP BY B.authors ORDER BY amount DESC LIMIT " + m;
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("authors") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String popularPublishers(String m, Statement stmt) 
	{
		String sql = "SELECT B.publisher, SUM(O.Quantity) AS amount FROM Orders O, Book B WHERE B.ISBN=O.ISBN GROUP BY B.authors ORDER BY amount DESC LIMIT " + m;
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("publisher") + "\n"; 
	
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("Cannot close resultset.");
			}
		}
		
		return output;
	}	
}
