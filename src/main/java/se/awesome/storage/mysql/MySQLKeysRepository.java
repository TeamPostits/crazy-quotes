package se.awesome.storage.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.awesome.storage.KeysRepository;

public class MySQLKeysRepository implements KeysRepository{
	
	String userName = "root";
	String pass = "";
	String url = "jdbc:mysql://localhost/";
	String driver = "com.mysql.jdbc.Driver";
	String db = "crazyQuotes";
	
	Connection con;
	Statement st;
	
	public MySQLKeysRepository(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url + db, userName, pass);
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean containsUser(int key, String username) {
		 String sql = "SELECT serviceKey, username FROM service_keys";
			
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					int dbKey = rs.getInt("serviceKey");
					String dbUsername = rs.getString("username");
					if(dbUsername.equals(username) && dbKey == key){
						return true;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}

}
