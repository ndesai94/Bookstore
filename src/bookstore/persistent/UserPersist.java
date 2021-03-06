package bookstore.persistent;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import bookstore.object.Book;
import bookstore.object.Cart;
import bookstore.object.CartItem;
import bookstore.object.Transaction;
import bookstore.object.User;
import bookstore.logic.*;
import bookstore.object.UserStatus;
import bookstore.object.UserType;
import javafx.util.Pair;

public class UserPersist {
	

	private static Connection conn = null;	
	public static void manageUser(int id, String fname, String lname, String email, String phone, String password,
			String shipaddress, String billaddress) {	
		String insertSql = "UPDATE bookstore.users SET firstname = ?, lastname = ?, phonenumber = ?, email = ?, shippingaddress = ?, billingaddress = ? WHERE userid = ?";
		PreparedStatement stmt1;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
				stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
				stmt1.setString(1, fname);
				stmt1.setString(2, lname);
				stmt1.setString(3, phone);
				stmt1.setString(4, email);
				stmt1.setString(5, shipaddress);
				stmt1.setString(6, billaddress);
				stmt1.setInt(7, id);
				stmt1.executeUpdate();
		}
				
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public static void updateUser(User user){
		String insertSql = "UPDATE bookstore.users SET firstname = ?, lastname = ?, phonenumber = ?, email = ?, shippingaddress = ?, billingaddress = ? WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
			stmt1.setString(1, user.getFirstName());
			stmt1.setString(2, user.getLastName());
			stmt1.setString(3, user.getPhoneNumber());
			stmt1.setString(4, user.getEmail());
			stmt1.setString(5, user.getShippingAddress());
			stmt1.setString(6, user.getBillingAddress());
			stmt1.setInt(7, user.getId());
			
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUserPassword(User user){
		String insertSql = "UPDATE bookstore.users SET password = ? WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
			stmt1.setString(1, user.getPassword());
			stmt1.setInt(2, user.getId());
			
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteBook(long iSBN){
		String sql = "DELETE FROM bookstore.book where isbn = ?";
		PreparedStatement stmt1;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setLong(1,iSBN);
			stmt1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void updateBook(int iSBN, String title, String price, int quantity, String coverphoto, String category,
			String description, int thresholdLimit, float rating, String author, int iSBN2){
		 String sql = "update bookstore.book set isbn = ?, title = ?, price = ?, quantity = ?, coverphoto = ?, category = ?, description = ?, threshholdlimit = ?, rating = ?, author = ? where iSBN = ?";
		//String sql = "update bookstore.book set isbn = " + iSBN + ", title = '"+ title + "', price = " + price + ", "
		//	+ "quantity = "+ quantity + ", coverphoto = '" + coverphoto + "', category = '" + category + "', description = '" + description + 
		//	"', threshholdlimit = " + thresholdLimit + ", rating = " + rating + ", author = '" + author + "' where iSBN = " + iSBN2;
		
		PreparedStatement stmt1;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			
			stmt1.setInt(1,iSBN);
			stmt1.setString(2, title);
			stmt1.setString(3,price);
			stmt1.setInt(4, quantity);
			stmt1.setString(5, coverphoto);
			stmt1.setString(6, category);
			stmt1.setString(7, description);
			stmt1.setInt(8, thresholdLimit);
			stmt1.setFloat(9, rating);
			stmt1.setString(10, author);
			stmt1.setInt(11, iSBN2);
			stmt1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void insertBook(int iSBN, String title, String price, int quantity, String coverphoto, String category,
			String description, int thresholdLimit, float rating, String author){
		String sql = "INSERT INTO bookstore.book VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt1;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			
			stmt1.setInt(1, iSBN);
			stmt1.setString (2, title);
			stmt1.setString(3, price);
			stmt1.setInt(4, quantity);
			stmt1.setString(5, coverphoto);
			stmt1.setString(6, category);
			stmt1.setString(7, description);
			stmt1.setInt(8, thresholdLimit);
			stmt1.setFloat(9, rating);
			stmt1.setString(10, author);
			stmt1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		

	public static void registerUser(User user) {
		/*1:	`userid` int(11) NOT NULL,
		  2:	`firstname` varchar(255) NOT NULL,
		  3:	`lastname` varchar(255) NOT NULL,
		  4:	`phonenumber` varchar(255) NOT NULL,
		  5:	`email` varchar(255) NOT NULL,
		  6:	`password` varchar(255) NOT NULL COMMENT 'Hashed and stored.',
		  7:	`usertype` int(11) NOT NULL COMMENT 'Type 2 is Admin.',
		  8:	`verifcode` tinyint(4) NOT NULL,
		  9:	`shippingaddress` varchar(255) NOT NULL,
		  10:	`billingaddress` varchar(255) NOT NULL,
		  11:	`status` int(11) NOT NULL,
		  12:	`fogotpasswordcode` varchar(255) NOT NULL*/
		String insertSql = "INSERT INTO bookstore.users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, ?)";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
			
			stmt1.setInt	(1, user.getId());
			stmt1.setString	(2, user.getFirstName());
			stmt1.setString	(3, user.getLastName());
			stmt1.setString	(4, user.getPhoneNumber());
			stmt1.setString	(5, user.getEmail());
			stmt1.setString	(6, user.getPassword());
			stmt1.setInt	(7, user.getUserType().ordinal());
			stmt1.setString	(8, user.getVerificationCode());
			stmt1.setString	(9, user.getShippingAddress());
			stmt1.setString	(10,user.getBillingAddress());
			stmt1.setInt(11,  user.getStatus().ordinal());
			if (user.isSuscribed()) {
				stmt1.setInt(12, 1);
			}else {
				stmt1.setInt(12, 0);
			}
			
			
			stmt1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static UserType[] userTypeValues = UserType.values();
	private static UserStatus[] userStatusValues = UserStatus.values();
	public static User verifyUser(String username, String pass) {
		User user = new User();
		String sql = "SELECT userid, firstname, lastname, usertype, status, shippingaddress, phonenumber FROM bookstore.users WHERE email = ? AND password = ?";
		PreparedStatement stmt;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		boolean notEmail = false;		
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2,  pass);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				notEmail = false;
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setUserType(userTypeValues[rs.getInt(4)]);
				user.setStatus(userStatusValues[rs.getInt(5)]);
				user.setEmail(username);
				user.setShippingAddress(rs.getString(6));
				user.setPhoneNumber(rs.getString(7));
			}
			else {
				notEmail = true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (notEmail) {
			String sql2 = "SELECT email, firstname, lastname, usertype, status, shippingaddress, phonenumber FROM bookstore.users WHERE userid = ? AND password = ?";
			try {
				stmt = (PreparedStatement) conn.prepareStatement(sql2);
				stmt.setString(1, username);
				stmt.setString(2,  pass);
				stmt.executeQuery();
				ResultSet rs = stmt.getResultSet();
				if (rs.next()) {
					user.setId(Integer.parseInt(username));
					user.setEmail(rs.getString(1));
					user.setFirstName(rs.getString(2));
					user.setLastName(rs.getString(3));
					user.setUserType(userTypeValues[rs.getInt(4)]);
					user.setStatus(userStatusValues[rs.getInt(5)]);
					user.setShippingAddress(rs.getString(6));
					user.setPhoneNumber(rs.getString(7));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}

	public static void setStatus(User currentUser, UserStatus status) {
		String insertSql = "UPDATE bookstore.users SET status = ? WHERE email = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(insertSql);
			
			stmt1.setInt(1, status.ordinal());
			stmt1.setString(2, currentUser.getEmail());
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Book> getBooksByTitle(String value) {
		List<Book> searchResults = new ArrayList<Book>();
		boolean isEmpty = true;
		String sql = "SELECT title, price, coverphoto, rating, author, isbn FROM bookstore.book WHERE title=? ORDER BY title ASC";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, value);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Book temp = new Book();
				temp.title = rs.getString(1);
				temp.price = rs.getFloat(2);
				temp.coverphoto = rs.getString(3);
				temp.rating = rs.getFloat(4);
				temp.author = rs.getString(5);
				temp.ISBN = rs.getLong(6);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
		
	}

	public static List<Book> getBooksBySubject(String value) {
		List<Book> searchResults = new ArrayList<Book>();
		boolean isEmpty = true;
		String sql = "SELECT title, price, coverphoto, rating, author, isbn FROM bookstore.book WHERE category=? ORDER BY title ASC";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, value);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Book temp = new Book();
				temp.title = rs.getString(1);
				temp.price = rs.getFloat(2);
				temp.coverphoto = rs.getString(3);
				temp.rating = rs.getFloat(4);
				temp.author = rs.getString(5);
				temp.ISBN = rs.getLong(6);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static List<Book> getBooksByAuthor(String value) {
		List<Book> searchResults = new ArrayList<Book>();
		boolean isEmpty = true;
		String sql = "SELECT title, price, coverphoto, rating, author, isbn FROM bookstore.book WHERE author=? ORDER BY title ASC";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, value);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Book temp = new Book();
				temp.title = rs.getString(1);
				temp.price = rs.getFloat(2);
				temp.coverphoto = rs.getString(3);
				temp.rating = rs.getFloat(4);
				temp.author = rs.getString(5);
				temp.ISBN = rs.getLong(6);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static List<Book> getBooksByISBN(String value) {
		List<Book> searchResults = new ArrayList<Book>();
		boolean isEmpty = true;
		String sql = "SELECT title, price, coverphoto, rating, author, isbn FROM bookstore.book WHERE isbn=? ORDER BY title ASC";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, value);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Book temp = new Book();
				temp.title = rs.getString(1);
				temp.price = rs.getFloat(2);
				temp.coverphoto = rs.getString(3);
				temp.rating = rs.getFloat(4);
				temp.author = rs.getString(5);
				temp.ISBN = rs.getLong(6);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}


	public static Book getBookByISBN(long value) {
		String sql = "SELECT title, price, coverphoto, rating, author, isbn FROM bookstore.book WHERE isbn=? ORDER BY title ASC";
		PreparedStatement stmt1;
		Book book = null;
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setLong(1, value);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
		
			book = new Book();
			while (rs.next()) {
				book.setTitle( rs.getString(1));
				book.setPrice(rs.getFloat(2));
				book.setCoverphoto(rs.getString(3));
				book.setRating(rs.getFloat(4));
				book.setAuthor(rs.getString(5));
				book.setISBN(rs.getLong(6));
			}
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	public static void addToCart(int id, int quantity, int i) {
		String sql = "INSERT INTO bookstore.shoppingcart VALUES (?, ?, ?)";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setInt(1, id);
			stmt1.setInt(2, quantity);
			stmt1.setInt(3,  i);
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addPromotion(String promocode, int percentage) {
		String sql = "INSERT INTO bookstore.promocodes VALUES (?, ?)";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, promocode);
			stmt1.setInt(2, percentage);
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<User> getSubscribedUsers() {
		List<User> results = new ArrayList<User>();
		boolean isEmpty = true;
		String sql = "SELECT email FROM bookstore.users WHERE subscribed = 1";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				User temp = new User();
				temp.setEmail(rs.getString(1));
				results.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return results;
		}
	}

	public static Vector<Long> getCart(int id) {
		Vector<Long> results = new Vector<Long>();
		//boolean isEmpty = true;
		String sql = "SELECT isbn FROM bookstore.shoppingcart WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setInt(1, id);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				//isEmpty = false;
				long isbn = rs.getInt(1);
				results.add(isbn);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//if (isEmpty) {
		//	return null;
		//}else {
		return results;
		//}
	}


	public static void placeOrder(Cart currentCart, String dateTime, int newID, int userID) {
		for (CartItem c : currentCart.cartItems) {
			String sql = "INSERT INTO bookstore.transactions VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt1;
			
			try {
				if(conn == null || conn.isClosed())
				{
					try {
						conn = DbUtils.connect();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				stmt1 = (PreparedStatement) conn.prepareStatement(sql);
	
				stmt1.setInt(1, newID);
				stmt1.setInt(2, userID);
				stmt1.setInt(3,  c.quantity);
				stmt1.setString(4, dateTime);
				stmt1.setInt(5,  currentCart.promoPercentage);
				stmt1.setDouble(6, currentCart.totalPrice);
				stmt1.setLong(7, c.book.getISBN());
				stmt1.setString(8,  "Not yet shipped");
				stmt1.executeUpdate();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static void emptyCart(int id) {
		String sql = "DELETE FROM bookstore.shoppingcart WHERE userid=?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setInt(1, id);
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static String getUserPasswordWithEmail(String emailEntered) {
		String password = "";
		boolean isEmpty = true;
		String sql = "SELECT password FROM bookstore.users WHERE email = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setString(1,  emailEntered);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				password = rs.getString(1);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return password;
		}
	}
	public static void updateOrderStatus(int transactionID, String status) {
		String sql = "UPDATE bookstore.transactions SET orderstatus = ? WHERE tid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setString(1, status);
			stmt1.setInt(2, transactionID);
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int getUserIDFromTransactionID(int transactionID) {
		int id = 0;
		String sql = "SELECT userid FROM bookstore.transactions WHERE tid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setInt(1, transactionID);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	public static String getEmailWithUserID(int id) {
		String email = "";
		String sql = "SELECT email FROM bookstore.users WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setInt(1, id);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				email = rs.getString(1);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return email;
	}

	public static void suspendAccount(int userID) {
		String sql = "UPDATE bookstore.users SET status = 3 WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.setInt(1, userID);
			stmt1.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Book> getBookQuantities() {
		List<Book> searchResults = new ArrayList<Book>();
		boolean isEmpty = true;
		String sql = "SELECT title, quantity FROM bookstore.book";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Book temp = new Book();
				temp.title = rs.getString(1);
				temp.quantity = rs.getInt(2);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static List<Transaction> getCurrentDaySales(String today, String tomorrow) {
		List<Transaction> searchResults = new ArrayList<Transaction>();
		boolean isEmpty = true;
		String sql = "SELECT DISTINCT tid, totalamountpaid FROM bookstore.transactions WHERE date > ? AND date < ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setString(1, today);
			stmt1.setString(2, tomorrow);

			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Transaction temp = new Transaction(rs.getInt(1), rs.getDouble(2));
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static List<Transaction> getOrderHistory(int id) {
		List<Transaction> searchResults = new ArrayList<Transaction>();
		boolean isEmpty = true;
		String sql = "SELECT DISTINCT tid, date, totalamountpaid, orderstatus FROM bookstore.transactions WHERE userid = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setInt(1, id);

			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Transaction temp = new Transaction();
				temp.transactionID = rs.getInt(1);
				temp.date = rs.getString(2);
				temp.totalAmountPaid = rs.getDouble(3);
				temp.status = rs.getString(4);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static List<Transaction> getTotalSales() {
		List<Transaction> searchResults = new ArrayList<Transaction>();
		boolean isEmpty = true;
		String sql = "SELECT DISTINCT tid, date, totalamountpaid, orderstatus FROM bookstore.transactions";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);

			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				isEmpty = false;
				Transaction temp = new Transaction();
				temp.transactionID = rs.getInt(1);
				temp.date = rs.getString(2);
				temp.totalAmountPaid = rs.getDouble(3);
				temp.status = rs.getString(4);
				searchResults.add(temp);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (isEmpty) {
			return null;
		}else {
			return searchResults;
		}
	}

	public static int verifyPromoCode(String promoEntered) {
		int percent = 0;
		String sql = "SELECT percentage FROM bookstore.promocodes WHERE promocode = ?";
		PreparedStatement stmt1;
		
		try {
			if(conn == null || conn.isClosed())
			{
				try {
					conn = DbUtils.connect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			stmt1 = (PreparedStatement) conn.prepareStatement(sql);
			stmt1.setString(1, promoEntered);
			stmt1.executeQuery();
			
			ResultSet rs = stmt1.getResultSet();
			while (rs.next()) {
				percent = rs.getInt(1);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return percent;
	}

}
