package cs5530;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer 
{
	public Customer()
	{}
	
	public String getCustomer(String login, String password, Statement stmt)
	{
		String sql = "SELECT IsManager FROM Customer WHERE login='"+login+"' AND password='"+password+"'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
				output += rs.getString("IsManager");
			
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
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
		
		return output;
	}
	
	public String selectCount(String login, Statement stmt)
	{
		String sql = "SELECT COUNT(*) FROM Customer WHERE login='"+login+"'";
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			while(rs.next())
				output += rs.getString("Count(*)");
			
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
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
		
		return output;
	}
	
	public void insert(String name, String login, String password, String ccn, String address, String phone, String isManager, Statement stmt)
	{
		String sql = "INSERT INTO Customer(cname, login, password, creditcard, address, phone, ismanager) VALUES "
				+ "('"+name+"', '"+login+"', '"+password+"', '"+ccn+"', '"+address+"', '"+phone+"', "+isManager+")";
		ResultSet rs = null;
		
		try
		{
			stmt.execute(sql);
		}
		catch(Exception e)
		{
			System.err.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
	}
	
	public String getCid(String login, Statement stmt)
	{
		String sql = "SELECT cid FROM Customer WHERE login='"+login+"'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
				output += rs.getString("cid");
			
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
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String selectAll(String login, Statement stmt) 
	{
		String sql = "SELECT * FROM Customer WHERE login='"+login+"'";
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
				output += rs.getString("cname") + "\t" +rs.getString("login") + "\t" + rs.getString("password") + "\t" + rs.getString("creditcard") + "\t" + rs.getString("address") + "\t" + rs.getString("phone") + "\t" + rs.getString("ismanager");
			
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
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public String selectUser(String cid, Statement stmt)
	{
		String sql = "SELECT Cid, Login FROM Customer WHERE cid!="+cid;
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
				output += rs.getString("cid") + "\t" + rs.getString("login") + "\n";
				
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
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
		
		return output;
	}

	public void delete(String cid1, String cid2, Statement stmt) 
	{
		String sql = "DELETE FROM Trust WHERE cid1="+cid1+" AND cid2="+cid2;
		ResultSet rs =null;
		 	
	 	try
	 	{
	 		stmt.execute(sql);
	 	}
		catch(Exception e)
		{
			System.err.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
	}

	public void insertTrust(String cid1, String cid2, String trust, Statement stmt) 
	{
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String sql = "INSERT INTO Trust(cid1, cid2, isTrusted, date) VALUES ("+cid1+", "+cid2+", "+trust+", '"+sdf.format(date)+"')";
		ResultSet rs =null;
		 	
	 	try
	 	{
	 		stmt.execute(sql);
	 	}
		catch(Exception e)
		{
			System.err.println("Could not execute query.");
		}
		finally
		{
			try
			{
				if(rs != null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("Cannot close resultset.");
			}
		}
	}
}
