package bookstore.persistent;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.PreparedStatement;
import bookstore.object.User;

public class UserPersist {

	private static Connection conn = null;
	
	public static void registerUser(User user) {
		String insertSql = "INSERT INTO bookstore.users VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt1;
		
		try {
			try {
				conn = DbUtils.connect();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
			
			stmt1.setInt(1, user.getId());
			stmt1.setString(2,  user.getFirstName());
			stmt1.setString(3,  user.getLastName());
			stmt1.setString(4, user.getPhoneNumber());
			stmt1.setString(5,  user.getEmail());
			stmt1.setString(6,  user.getPassword());
			stmt1.setInt(7,  user.getUserType());
			
			stmt1.executeUpdate();
			
			System.out.println(stmt1.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static int verifyUser(String email, String pass) {
		int userType = -1;
		String sql = "SELECT usertype FROM bookstore.users WHERE email = ? AND password = ?";
		PreparedStatement stmt = null;
		try {
			conn = DbUtils.connect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2,  pass);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			int temp = 0;
			while (rs.next() && temp == 0) {
				userType = rs.getInt(1);
				temp++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return userType;
	}
}
