package cs5530;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order
{
	public Order() 
	{}
	
	public String getOid(Statement stmt)
	{
		String sql = "SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1";
		String output="";
		ResultSet rs=null;
		
		try
		{
			rs=stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("oid");
			
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
	
	public void placeOrder(int oid, String cid, String ISBN, String quantity, Statement stmt)
	{
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String sql  = "INSERT INTO Orders(OID, CID, ISBN, Quantity, Date) VALUES ("+oid+", "+cid+", '"+ISBN+"', "+quantity+", '"+sdf.format(date)+"')";
		
		try
		{
			stmt.execute(sql);
		}
		catch(Exception e)
		{
			System.out.println("Could not execute query.");
		}
	}

	public String salesHistory(String cid, Statement stmt)
	{
		String sql = "SELECT B.Title, O.Date, O.Quantity FROM Book B, Orders O WHERE O.cid="+cid+" AND B.ISBN=O.ISBN";
		String output="";
		ResultSet rs=null;
		
		try
		{
			rs=stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("title") + "\t" + rs.getString("date") + "\t" + rs.getString("quantity") + "\n";
			
			rs.close();
		}
		catch(Exception e)
		{
			System.err.println("Could not execute query.");
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

	public String suggestions(String cid, Statement stmt)
	{
		String sql = "SELECT B.Title, Count(*) AS num FROM Orders O, Book B WHERE B.ISBN=O.ISBN AND O.Cid IN "
				+ "(SELECT cid FROM Orders WHERE ISBN IN (SELECT ISBN FROM Order WHERE cid="+cid+")) GROUP BY O.ISBN ORDER BY num DESC";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs=stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("title") + "\n";
			
			rs.close();
		}
		catch(Exception e)
		{
			System.err.println("Could not execute query.");
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
