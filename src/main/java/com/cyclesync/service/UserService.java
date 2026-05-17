package com.cyclesync.service;


/*
 * File name: UserService.java
 * Description: CycleSync Service Layer Component
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

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

/**
 * Class: UserService
 * Role: Coordinates business logic and orchestrates database transactions
 *
 * This handles primary logic and coordinates system processes.
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

    // Applies business rules and communicates with the data access layer
    public int registerMember(String fullName, String userEmail,
                              String userPassword, String phoneNumber,
                              String userAddress, String nationality) throws SQLException {

        if (userDao.emailExists(userEmail)) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Hash the password before storing
        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));

        UserModel newUser = new UserModel(fullName, userEmail,
                                          hashedPassword, phoneNumber, userAddress);
        newUser.setNationality(nationality != null && !nationality.trim().isEmpty() ? nationality : "Nepalese");
        return userDao.insertUser(newUser);
    }

    /**
     * Validates login credentials. Returns the UserModel on success, null on failure.
     */

    // Applies business rules and communicates with the data access layer
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


    // Applies business rules and communicates with the data access layer

    public List<UserModel> getAllMembers() throws SQLException {
        return userDao.findAllMembers();
    }


    // Applies business rules and communicates with the data access layer

    public List<UserModel> getAllUsers() throws SQLException {
        return userDao.findAllUsers();
    }


    // Applies business rules and communicates with the data access layer

    public UserModel getMemberById(int userId) throws SQLException {
        return userDao.findById(userId);
    }


    // Applies business rules and communicates with the data access layer

    public boolean suspendMember(int userId) throws SQLException {
        return userDao.updateAccountStatus(userId, "SUSPENDED");
    }


    // Applies business rules and communicates with the data access layer

    public boolean activateMember(int userId) throws SQLException {
        return userDao.updateAccountStatus(userId, "ACTIVE");
    }


    // Applies business rules and communicates with the data access layer

    public boolean deleteMember(int userId) throws SQLException {
        return userDao.deleteUser(userId);
    }

    // ----------------------------------------------------------------
    // Profile Management (Member self-service)
    // ----------------------------------------------------------------


    // Applies business rules and communicates with the data access layer

    public boolean updateProfile(int userId, String fullName, String userEmail,
                                 String phoneNumber, String nationality) throws SQLException {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        return userDao.updateProfile(userId, fullName.trim(), userEmail.trim(), phoneNumber, nationality);
    }


    // Applies business rules and communicates with the data access layer

    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        if (newPassword == null || newPassword.trim().isEmpty()) return false;
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        return userDao.updatePassword(userId, hashedPassword);
    }
}