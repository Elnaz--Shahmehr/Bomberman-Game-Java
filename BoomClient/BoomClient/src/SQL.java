import java.sql.*;

/**
 * Creates a connection to the online database.
 */
public class SQL {

	public static Connection connection;

	SQL() {
		System.out.println("establishing connection");
		try{
			// Step 1: Load the JDBC driver.
			Class.forName("com.mysql.jdbc.Driver");
			// Step 2: Establish the connection to the database.
			String url = "jdbc:mysql://79.99.2.203:3306/groupg";
			connection = DriverManager.getConnection(url,"boom","ouagadougou");
		}catch (Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		if (connection != null){
			System.out.println("ok, connected");
		}else{
			System.out.println("not connected");
		}
	}
}