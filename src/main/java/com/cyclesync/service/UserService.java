package com.cyclesync.service;

import com.cyclesync.dao.UserDao;
import com.cyclesync.model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

/**
 * UserService - Business logic for authentication and member management.
 *
 * NOTE: This class uses jBCrypt for password hashing.
 * Add bcrypt JAR to WEB-INF/lib: https://github.com/djmdjm/jBCrypt
 * If you prefer not to use BCrypt yet, swap the two marked methods for
 * plain-text comparison — but never do this in production.
 */
public class UserService {

    private final UserDao userDao = new UserDao();

    // ----------------------------------------------------------------
    // Authentication
    // ----------------------------------------------------------------

    /**
     * Registers a new member. Returns the new userId, or -1 on failure.
     * Throws IllegalArgumentException for duplicate email.
     */
    public int registerMember(String fullName, String userEmail,
                              String userPassword, String phoneNumber,
                              String userAddress) throws SQLException {

        if (userDao.emailExists(userEmail)) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Hash the password before storing
        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));

        UserModel newUser = new UserModel(fullName, userEmail,
                                          hashedPassword, phoneNumber, userAddress);
        return userDao.insertUser(newUser);
    }

    /**
     * Validates login credentials. Returns the UserModel on success, null on failure.
     */
    public UserModel loginUser(String userEmail, String userPassword) throws SQLException {
        UserModel user = userDao.findByEmail(userEmail);
        if (user == null) return null;
        if (!user.isActive()) return null;  // Suspended accounts cannot log in

        // BCrypt password check
        if (BCrypt.checkpw(userPassword, user.getUserPassword())) {
            return user;
        }
        return null;
    }

    // ----------------------------------------------------------------
    // Member Management (Admin use)
    // ----------------------------------------------------------------

    public List<UserModel> getAllMembers() throws SQLException {
        return userDao.findAllMembers();
    }

    public List<UserModel> getAllUsers() throws SQLException {
        return userDao.findAllUsers();
    }

    public UserModel getMemberById(int userId) throws SQLException {
        return userDao.findById(userId);
    }

    public boolean suspendMember(int userId) throws SQLException {
        return userDao.updateAccountStatus(userId, "SUSPENDED");
    }

    public boolean activateMember(int userId) throws SQLException {
        return userDao.updateAccountStatus(userId, "ACTIVE");
    }

    public boolean deleteMember(int userId) throws SQLException {
        return userDao.deleteUser(userId);
    }

    // ----------------------------------------------------------------
    // Profile Management (Member self-service)
    // ----------------------------------------------------------------

    public boolean updateProfile(int userId, String fullName,
                                 String phoneNumber, String userAddress) throws SQLException {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        return userDao.updateProfile(userId, fullName.trim(), phoneNumber, userAddress);
    }
}