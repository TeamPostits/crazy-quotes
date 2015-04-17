package se.awesome.storage.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.awesome.storage.UserRepository;

public class MySQLUserRepository implements UserRepository {

	String userName = "root";
	String pass = "";
	String url = "jdbc:mysql://localhost/";
	String driver = "com.mysql.jdbc.Driver";
	String db = "crazyQuotes";

	Connection con;
	Statement st;

	public MySQLUserRepository() {

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
	public boolean containsUser(String username, String password) {
		String sql = "SELECT username, password FROM users";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String dbUsername = rs.getString("username");
				String dbPassword = rs.getString("password");
				if (dbUsername.equals(username)
						&& dbPassword.equals(password)) {
					return true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public String getSalt(String username) {
		String sql = "SELECT salt FROM users WHERE username = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			rs.first();
			String salt = rs.getString("salt");
			return salt;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public String getPassword(String username) {
		String sql = "SELECT password FROM users WHERE username = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			rs.first();
			String password = rs.getString("password");
			return password;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean createUser(String username, String password, String salt) {
		String sql = "INSERT INTO users(username, password, salt) VALUES(?, ?, ?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, salt);

			pstmt.execute();

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

}
