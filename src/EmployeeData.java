import java.sql.*;
import javax.swing.*;
/*
 This class is used for connected to the sqlite database, which is used when 
 loading the list, adding, deleting, and editing.
 */

public class EmployeeData {
	
	public static Connection ConnectDB()
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:employee.db");
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Connection Error");
			return null;
		}
	}
	
	
}
