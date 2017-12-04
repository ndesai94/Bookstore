package bookstore.boundary;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.logic.UserLogic;
import bookstore.object.*;
import bookstore.persistent.UserPersist;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import javafx.util.Pair;
import java.util.Random;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private User currentUser;
	private Cart currentCart;
	private TemplateProcessor processor;
	private String host;
	private String port;
	private String user;
	private String pass;

    public HomeServlet() {
    	super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    	processor = new TemplateProcessor(getServletContext());
    	currentUser = null;
    	currentCart = null;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		if (request.getParameter("register") != null) {
			registerUser(request, response);
		}
		if (request.getParameter("login") != null) {
			login(request, response);
		}
		if (request.getParameter("forgotpassword") != null) {
			forgotPassword(request, response);
		}
		if (request.getParameter("verify") != null) {
			verifyUser(request, response);
		}
		if (request.getParameter("logout") != null){
			logout(request, response);
		}
		if (request.getParameter("searchBook") != null) {
			searchBook(request, response);
		}
		if (request.getParameter("addtocart") != null) {
			addToCart(request, response);
		}
		if (request.getParameter("mycart") != null) {
			goToCart(request, response);
		}
		if (request.getParameter("addPayment") != null) {
			System.out.println("were here");
			addPayment(request, response);
		}
		if (request.getParameter("submitPayment") != null) {
			submitPayment(request, response);
		}
		if (request.getParameter("placeOrder") != null) {
			placeOrder(request, response);
		}
		if (request.getParameter("emailSubmit") != null) {
			sendPassword(request, response);
		}
		if (request.getParameter("editProfile") != null) {
			editProfile(request, response);
		}
		if (request.getParameter("gotoEditProfile") != null) {
			gotoEditProfile(request, response);
		}
		if (request.getParameter("editPassword") != null) {
			editPassword(request, response);
		}
		if (request.getParameter("gotoEditPassword") != null) {
			gotoEditPassword(request, response);
		}
	}
	
	private void gotoEditPassword(HttpServletRequest request, HttpServletResponse response) {
		String templateName = "editPassword.ftl";
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		
		processor.runTemp(templateName, root, request, response);
	}

	private void editPassword(HttpServletRequest request, HttpServletResponse response) {
		String currentPw = request.getParameter("password");
		String newPw = request.getParameter("newpassword");
		String newPw2 = request.getParameter("newpassword2");
	}

	private void gotoEditProfile(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "editProfile.ftl";
		root.put("fname",currentUser.getFirstName());
		root.put("lname",currentUser.getLastName());
		root.put("phone",currentUser.getPhoneNumber());
		root.put("email",currentUser.getEmail());
		root.put("mail", currentUser.getShippingAddress());
		processor.runTemp(templateName, root, request, response);
	}

	private void editProfile(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String phoneNumber = request.getParameter("phone");
		String emailAddress = request.getParameter("email");
		String mailingAddress = request.getParameter("address");
		String subscribeForPromo = request.getParameter("subscribe");
		
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "editProfile.ftl";
		boolean error = false;
		try {
			javax.mail.internet.InternetAddress addr = new javax.mail.internet.InternetAddress(emailAddress);
			addr.validate();
		}
		catch (javax.mail.internet.AddressException e){
			root.put("email_error", "Please enter a valid email address.");
			error = true;
		}
		root.put("fname",currentUser.getFirstName());
		root.put("lname",currentUser.getLastName());
		root.put("phone",currentUser.getPhoneNumber());
		root.put("email",currentUser.getEmail());
		root.put("mail", currentUser.getShippingAddress());
		
		if (!error) {
			boolean updated = false;
			if(!currentUser.getFirstName().equals(firstName))
			{
				updated = true;
				currentUser.setFirstName(firstName);
			}
			if(!currentUser.getLastName().equals(lastName))
			{
				updated = true;
				currentUser.setLastName(lastName);
			}
			if(!currentUser.getPhoneNumber().equals(phoneNumber))
			{
				updated = true;
				currentUser.setPhoneNumber(phoneNumber);
			}
			if(!currentUser.getEmail().equals(emailAddress))
			{
				updated = true;
				currentUser.setEmail(emailAddress);
			}
			if(!currentUser.getShippingAddress().equals(mailingAddress))
			{
				updated = true;
				currentUser.setShippingAddress(mailingAddress);
			}
			
			if(updated){
				UserPersist.updateUser(currentUser);
			}
		}
		
		processor.runTemp(templateName, root, request, response);
	}

	private void sendPassword(HttpServletRequest request, HttpServletResponse response) {
		String emailEntered = request.getParameter("emailValue");
		String password = UserLogic.getUserPasswordWithEmail(emailEntered);
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "login.ftl";
		if (password != null) {
			String subject = "Password Information";
			String content = "Your password: " + password;
			try {
	            EmailUtility.sendEmail(host, port, user, pass, emailEntered, subject, content);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			root.put("error", "Your password has been emailed to you.");
		} else {
			root.put("error", "Email entered does not match any records in our system.");
		}
		processor.runTemp(templateName, root, request, response);
	}

	private void forgotPassword(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "forgotpassword.ftl";
		processor.runTemp(templateName, root, request, response);
	}

	private void placeOrder(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		int newID = Integer.parseInt(getRandomID());
		UserLogic.placeOrder(currentCart, dateFormat.format(date), newID, currentUser.getId());
		String subject = "Order Confirmation";
		String content = "Thank you for placing an order with us. You will be emailed again once the order has been delievered. Here is the order summary: \n\n\n";
		for (CartItem c : currentCart.cartItems) {
			content += c.book.getTitle() + "\t\t x " + c.quantity + "\t\t Price per unit: $" + c.book.getPrice() + "\n";
		}
		content += "\n\n\n \t Total Amount Paid: \t" + currentCart.totalPrice;
		
		try {
            EmailUtility.sendEmail(host, port, user, pass, currentUser.getEmail(), subject, content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		UserLogic.emptyCart(currentUser.getId());
		currentCart = null;
		templateName = "logged_in.ftl";
		String message = "Your order has been placed. A confirmation email was sent to you and another email will be sent when the order is delivered. Thank you for shopping with us!";
		root.put("first", currentUser.getFirstName());
		root.put("last",  currentUser.getLastName());
		root.put("message", message);
		processor.runTemp(templateName, root, request, response);
	}

	private void submitPayment(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName;
		if (currentUser != null) {
			templateName = "checkout.ftl";
			int numberOfItems = currentCart.cartItems.size();
			String payment = request.getParameter("cardType");
			double totalPrice = currentCart.totalPrice;
			root.put("numberOfItems", numberOfItems);
			root.put("payment", payment);
			root.put("totalPrice", totalPrice);
			processor.runTemp(templateName, root, request, response);
		}
	}

	private void addPayment(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "addPayment.ftl";
		processor.runTemp(templateName, root, request, response);
	}

	private void goToCart(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName;
		if (currentUser == null) {
			templateName = "login.ftl";
			root.put("error", "Please login in order to view your shopping cart.");
			processor.runTemp(templateName, root, request, response);
		} else {
			/*
			Vector<Integer> bookNumbers = new Vector<Integer>();
			bookNumbers = UserLogic.getBookNumbers(currentUser.getId());
			for (int i : bookNumbers) {
				Book book = new Book();
			}
			*/
			Vector<Pair<Long,Integer>> cart = UserLogic.getCart(currentUser.getId());
			List<CartItem> books = new ArrayList<CartItem>();
			for (Pair<Long,Integer> p : cart) {
				Book b = UserLogic.getBookByISBN(p.getKey());
				CartItem c = new CartItem(b,p.getValue());
				books.add(c);
			}
			double subtotal = 0;
			int numberOfItems = 0;
			for (CartItem c : books) {
				subtotal += c.book.getPrice() * c.quantity;
				numberOfItems++;
			}
			double tax = 0.07 * subtotal;
			double total = subtotal + tax;
			Cart newCart = new Cart(books, total, 0, "Not Yet Selected");
			currentCart = newCart;
			/*
			List<CartItem> books = cart
					.stream()
					.map(p -> new CartItem(UserPersist.getBookByISBN(p.getKey()),p.getValue()))
					.toList(List<CartItem>::new);
					*/
			templateName = "cart.ftl";
			root.put("books", books);
			root.put("subtotal", subtotal);
			root.put("tax", tax);
			root.put("total", total);
			root.put("numberOfItems", numberOfItems);
			processor.runTemp(templateName, root, request, response);
		}
	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response) {
		String isbn = request.getParameter("addtocart");
		UserLogic.addToCart(currentUser.getId(), 1, Integer.parseInt(isbn));
	}

	private void searchBook(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("searchType");
		String value = request.getParameter("searchValue");
		boolean loggedin = (currentUser != null);
		if(type != null && value != null) {
			DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(db.build());
			root.put("loggedin", loggedin);
			String templateName = "searchresults.ftl";
			if (type.equals("title")) {
				List<Book> listOfBooks = UserLogic.getBooksByTitle(value);
				root.put("books", listOfBooks);
				processor.runTemp(templateName, root, request, response);
			}else if (type.equals("subject")) {
				List<Book> listOfBooks = UserLogic.getBooksBySubject(value);
				root.put("books", listOfBooks);
				processor.runTemp(templateName, root, request, response);
			}else if (type.equals("author")) {
				List<Book> listOfBooks = UserLogic.getBooksByAuthor(value);
				root.put("books", listOfBooks);
				processor.runTemp(templateName, root, request, response);
			}else if (type.equals("isbn")) {
				List<Book> listOfBooks = UserLogic.getBooksByISBN(value);
				root.put("books", listOfBooks);
				processor.runTemp(templateName, root, request, response);
			}
		}
	}

	//logout
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		currentUser = null;
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        request.getRequestDispatcher("index.html").include(request, response);  
          
        HttpSession session=request.getSession();  
        session.invalidate();  
          
        out.print("You are successfully logged out!");  
          
        out.close(); 
	}
	
	private void verifyUser(HttpServletRequest request, HttpServletResponse response) {
		String verificationCode = request.getParameter("code");
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName;
		if (verificationCode!= null && currentUser != null && verificationCode.equals(currentUser.getVerificationCode())) {
			System.out.println(verificationCode);
			UserLogic.setStatus(currentUser, UserStatus.Active);
			root.put("error",  "Your account has successfully been verified. You may now log in.");
			templateName = "login.ftl";
		}
		else{
			root.put("name", currentUser.getFirstName());
			root.put("error",  "Failed to validate account.");
			templateName = "verify.ftl";
		}
		processor.runTemp(templateName, root, request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName;
		User user = UserLogic.verifyUser(username, pass);
		if (user.getId() == 0) {
			templateName = "login.ftl";
			root.put("error", "Invalid email/ID or password. Please try again.");
			processor.runTemp(templateName, root, request, response);
		}else if (user.getStatus() == UserStatus.Waiting){
			templateName = "login.ftl";
			root.put("error", "Account pending verification. Please verify your account and try logging in again.");
			processor.runTemp(templateName, root, request, response);
		} //TODO: Add conditions for UserStatus.Supsended... ?
		else{
			currentUser = user;
			UserType userType = user.getUserType();
			switch(userType) {
				case Customer:
					templateName = "logged_in.ftl";
					root.put("first", user.getFirstName());
					root.put("last", user.getLastName());
					root.put("message", "");
					processor.runTemp(templateName, root, request, response);
					break;
				case Admin:
					templateName = "adminhome.ftl";
					root.put("hello", "Hi there " + user.getFirstName());
					processor.runTemp(templateName, root, request, response);
					break;
				case Manager:
					templateName = "managerhome.ftl";
					root.put("hello", "Hi there " + user.getFirstName());
					processor.runTemp(templateName, root, request, response);
					break;
				case ShipmentEmployee:
					templateName = "shipperhome.ftl";
					root.put("hello", "Hi there " + user.getFirstName());
					processor.runTemp(templateName, root, request, response);
					break;
				case None:
				default:
					// TODO: Display an error or something...
					break;
			}
		}
	}

	@SuppressWarnings("unused")
	private void registerUser(HttpServletRequest request, HttpServletResponse response) {
		int id = 0;
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String phoneNumber = request.getParameter("phone");
		String emailAddress = request.getParameter("email");
		String password = request.getParameter("password");
		String mailingAddress = request.getParameter("address");
		String subscribeForPromo = request.getParameter("subscribe");
		boolean subscribe = (subscribeForPromo.equals("on"));
		UserType userType = UserType.Customer;
		
		
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(db.build());
		String templateName = "registration.ftl";
		boolean error = false;
		try {
			javax.mail.internet.InternetAddress addr = new javax.mail.internet.InternetAddress(emailAddress);
			addr.validate();
		}
		catch (javax.mail.internet.AddressException e){
			root.put("email_error", "Please enter a valid email address.");
			error = true;
		}
		
		if(!password.equals( request.getParameter("password2"))){
			root.put("pwd_error", "Passwords do not match.");
			error = true;
		}
		// TODO: Validate info: Check if the submitted email is already used for another account
		else if(false/*email already in use*/){
			error = true;
		}
		else if (!error) {
			User newUser = new User(id, firstName, lastName, phoneNumber, emailAddress, password, userType, mailingAddress, mailingAddress, UserStatus.Waiting, subscribe);
			String verificationCode = getRandomString();
			newUser.setVerificationCode(verificationCode);
			UserLogic.registerUser(newUser);
			String subject = "Verify your New Account";
			String content = "Your verification code is " + verificationCode;
			currentUser = newUser;;
			
			try {
	            EmailUtility.sendEmail(host, port, user, pass, emailAddress, subject, content);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			templateName = "verify.ftl";
			root.put("name", newUser.getFirstName());
			processor.runTemp(templateName, root, request, response);
		}
		
		if(error){
			root.put("fname",firstName);
			root.put("lname",lastName);
			root.put("phone",phoneNumber);
			root.put("email",emailAddress);
			root.put("mail", mailingAddress);
			processor.runTemp(templateName, root, request, response);
		}
	}
	
	protected String getRandomString() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();
        while (str.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * allChars.length());
            str.append(allChars.charAt(index));
        }
        String randomString = str.toString();
        return randomString;

    }
	
	protected String getRandomID() {
        String allChars = "1234567890";
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();
        while (str.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * allChars.length());
            str.append(allChars.charAt(index));
        }
        String randomString = str.toString();
        return randomString;

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
