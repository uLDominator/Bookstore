package cs5530;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback 
{
	public Feedback()
	{}

	public String selectUserFeedback(String cid, Statement stmt) 
	{
		String sql = "SELECT F.fid, C.cname, B.title, F.score, F.comment FROM Customer C, Feedback F, Book B WHERE B.ISBN=F.ISBN AND C.cid=F.cid AND F.cid="+cid;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("fid") + "\t" + rs.getString("cname") + "\t" + rs.getString("title") + "\t" + rs.getString("score") + "\t" + rs.getString("comment")+"\n";
			
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

	public String selectOrderedFeedback(String cid, Statement stmt) 
	{
		String sql = "SELECT C.cname, B.title, F.score, F.comment, F.date FROM Feedback F, Book B, Customer C "
				+ "WHERE B.ISBN=F.ISBN AND C.cid=F.cid AND F.cid IN (SELECT fid FROM Rate WHERE cid="+cid+" ORDER BY Rating DESC)";
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("name") + "\t" + rs.getString("title") + "\t" + rs.getString("score") + "\t" + rs.getString("comment") + "\t" + rs.getString("date") + "\n";			
			
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

	public String selectTrusted(String cid, Statement stmt) 
	{
		String sql = "SELECT C.login, T.isTrusted, T.date FROM Trust T, Customer C WHERE C.cid=T.cid2 AND T.cid1="+cid;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("login") + "\t" + rs.getString("isTrusted") + "\t" + rs.getString("date") + "\n";
			
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

	public String find(String ISBN, String cid, Statement stmt)
	{
		String sql = "SELECT COUNT(*) FROM Feedback WHERE ISBN='"+ISBN+"' AND cid="+cid;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("count(*)");
			
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

	public void insert(String score, String comment, String ISBN, String cid, Statement stmt)
	{
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String sql = "INSERT INTO Feedback(ISBN, cid, Score, Comment, Date) VALUES ('"+ISBN+"', "+cid+", "+score+", '"+comment+"', '"+sdf.format(date)+"')";
		ResultSet rs=null;
		
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

	public String selectAll(String cid, Statement stmt)
	{
		String sql = "SELECT F.fid, C.login, B.title, F.score, F.comment FROM Customer C, Feedback F, Book B "
				+ "WHERE B.ISBN=F.ISBN AND C.cid=F.cid AND F.cid!="+cid;
		String output = "";
		ResultSet rs = null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("fid") + "\t" + rs.getString("login") + "\t" + rs.getString("title") + "\t" + rs.getString("score") + "\t" + rs.getString("comment") + "\n";
			
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

	public void insertRating(String cid, String fid, String rate, Statement stmt)
	{
		String sql = "INSERT INTO Rate(Cid, Fid, Rating) VALUES ("+cid+", "+fid+", "+rate+")";
		ResultSet rs=null;
		
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

	public String selectUseful(String ISBN, String limit, Statement stmt) 
	{
		String sql = "SELECT C.login, F.date, F.score, avg(R.rating) as avgRating, F.comment FROM Feedback F, Customer C, Rate R "
				+ "WHERE F.fid=R.fid AND F.cid=C.cid AND F.ISBN='"+ISBN+"' GROUP BY F.fid ORDER BY avgRating DESC LIMIT "+limit;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("login") + "\t" + rs.getString("date") + "\t" + rs.getString("score") + "\t" + rs.getString("avgRating") + "\t" + rs.getString("comment") + "\n";
			
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

	
	public String selectMostTrusted(String m, Statement stmt)
	{
		String sql = "SELECT C.cname, COUNT(*) AS count FROM Trust T, Customer C WHERE T.cid2=C.cid AND T.isTrusted=1 GROUP BY T.cid2 ORDER BY count DESC LIMIT " + m;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("cname") + "\n";
			
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

	public String selectMostHelpful(String m, Statement stmt)
	{
		String sql = "SELECT C.cname, AVG(R.rating) as avgRating FROM Customer C, Feedback F, Rate R WHERE C.cid=F.cid AND R.fid=F.fid GROUP BY F.cid ORDER BY avgRating DESC LIMIT " + m;
		String output = "";
		ResultSet rs=null;
		
		try
		{
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
				output += rs.getString("cname") + "\n";
			
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
}
