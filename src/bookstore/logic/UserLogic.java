package bookstore.logic;

import bookstore.object.User;
import bookstore.persistent.UserPersist;

public class UserLogic {
	
	public static void registerUser(User user) {
		UserPersist.registerUser(user);
	}

	public static User verifyUser(String email, String pass) {
		return UserPersist.verifyUser(email, pass);
	}

	public static void setVerificationCode(User newUser, String verificationCode) {
		UserPersist.setVerificationCode(newUser, verificationCode);		
	}

	public static void setStatus(User currentUser, String string) {
		UserPersist.setStatus(currentUser, string);
	}

}
