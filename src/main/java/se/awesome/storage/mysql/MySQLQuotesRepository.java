package se.awesome.storage.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import se.awesome.data.Quote;
import se.awesome.storage.QuotesRepository;

public class MySQLQuotesRepository implements QuotesRepository{
	
	String userName = "root";
	String pass = "";
	String url = "jdbc:mysql://localhost/";
	String driver = "com.mysql.jdbc.Driver";
	String db = "crazyQuotes";
	
	Connection con;
	Statement st;
	
	public MySQLQuotesRepository(){
		
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
	public List<Quote> readQuotes() {
		String sql = "SELECT text, author, year, createdBy FROM quotes";

		List<Quote> quotes = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String text = rs.getString("text");
				String author = rs.getString("author");
				int year = rs.getInt("year");
				String createdBy = rs.getString("createdBy");

				Quote currentQuote = new Quote(text, author, year, createdBy);

				quotes.add(currentQuote);
			}

			return quotes;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
	}

	@Override
	public boolean createQuote(Quote quote) {
		String sql = "INSERT INTO quotes (text, author, year, createdBy) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, quote.getText());
			pstmt.setString(2, quote.getAuthor());
			pstmt.setInt(3, quote.getYear());
			pstmt.setString(4, quote.getCreatedBy());

			pstmt.executeUpdate();
			
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
