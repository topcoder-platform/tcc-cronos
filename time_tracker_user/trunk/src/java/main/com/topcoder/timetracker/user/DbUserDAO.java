/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.DBUtils;
import com.topcoder.timetracker.common.Utils;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.encryption.EncryptionException;
import com.topcoder.search.builder.database.DatabaseSearchStringBuilder;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Database implementation of the UserDAO interface. It persisting and retrieving the Time Tracker User
 * information from the database.
 * </p>
 * <p>
 * This DAO is capable of performing the basic CRUDE operations in non-batch mode. This means that only single
 * entities are processed at a time.
 * </p>
 * <p>
 * The DAO is capable of performing the operations on multiple Users simultaneously (batch mode). While in batch
 * mode, the operation can be done atomically, or separately. If done atomically, then a failure at any one of the
 * specified entries will mean that the entire batch will be rolled back. Otherwise, only the user where a failure
 * occurred will be rolled back.
 * </p>
 * <p>
 * Thread Safety: The class itself is thread safe because of immutability, but the thread safety issue of the class
 * extends to the back-end data store. The Transaction Isolation level of the JDBC connections will affect the
 * overall thread safety of this class. The class may be reused by multiple concurrent threads - depending on usage
 * and configuration.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 *
 */
public class DbUserDAO implements UserDAO {

    /**
     * SQL query. Deletes the user account from the database.
     */
    private static final String DELETE_USER_ACCOUNT =
        "DELETE FROM user_account WHERE user_account.user_account_id = ?";

    /**
     * SQL query. Deletes the address from the database.
     */
    private static final String DELETE_ADDRESS = "DELETE FROM address WHERE address.address_id = ?";

    /**
     * SQL query. Deletes the contact from the database.
     */
    private static final String DELETE_CONTACT = "DELETE FROM contact WHERE contact.contact_id = ?";

    /**
     * SQL query. Deletes the user address from the database.
     */
    private static final String DELETE_USER_ADDRESS =
        "DELETE FROM user_address WHERE user_address.user_account_id = ?";

    /**
     * SQL query. Deletes the user contact from the database.
     */
    private static final String DELETE_USER_CONTACT =
        "DELETE FROM user_contact WHERE user_contact.user_account_id = ?";

    /**
     * SQL query. Updates the user account in the database.
     */
    private static final String UPDATE_USER_ACCOUNT = "UPDATE user_account SET user_account.company_id = ?, "
            + "user_account.user_name = ?,  "
            + "user_account.password = ?, user_account.account_status_id = ?, user_account.modification_date = ?, "
            + "user_account.modification_user = ? WHERE user_account.user_account_id = ?";

    /**
     * SQL query. Updates the user address in the database.
     */
    private static final String UPDATE_USER_ADDRESS = "UPDATE user_address SET user_address.address_id = ?, "
            + "user_address.modification_date = ?, "
            + "user_address.modification_user = ? WHERE user_address.user_account_id = ?";

    /**
     * SQL query. Updates the user contact in the database.
     */
    private static final String UPDATE_USER_CONTACT = "UPDATE user_contact SET user_contact.contact_id = ?,"
            + " user_contact.modification_date = ?, "
            + "user_contact.modification_user = ? WHERE user_contact.user_account_id = ?";

    /**
     * SQL query. Inserts the user address in the database.
     */
    private static final String INSERT_USER_ADDRESS = "INSERT into user_address(user_account_id, address_id, "
            + "creation_date, creation_user, modification_date, "
            + "modification_user) VALUES(?,?,?,?,?,?)";

    /**
     * SQL query. Inserts the user contact in the database.
     */
    private static final String INSERT_USER_CONTACT = "INSERT into user_contact(user_account_id, contact_id, "
            + "creation_date, creation_user, modification_date, "
            + "modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * SQL query. Inserts the user account in the database.
     */
    private static final String INSERT_USER_ACCOUNT = "INSERT INTO user_account(user_account_id, company_id, "
            + "account_status_id, user_name, password, "
            + "creation_date, creation_user,  modification_date, modification_user) "
            + "VALUES (?, ?, ?, ?, ? ,?, ?, ?, ?)";

    /**
     * The base select SQL query.
     */
    private static final String BASE_QUERY = "SELECT user_account.user_account_id, user_account.company_id, "
            + "user_account.account_status_id, user_account.user_name, user_account.password, "
            + "user_account.creation_date AS UserCreationDate, user_account.creation_user AS UserCreationUser, "
            + "user_account.modification_date AS UserModDate, user_account.modification_user AS UserModUser, "
            + "address.address_id, address.line1, address.line2, address.creation_date AS AddrCreationDate, "
            + "address.creation_user AS AddrCreationUser, address.modification_date AS AddrModificationDate, "
            + "address.city, address.zip_code, "
            + "address.modification_user AS AddrModificationUser, state_name.state_name_id, "
            + "state_name.name AS StateName, state_name.abbreviation AS Abbreviation, "
            + "state_name.creation_date AS StateCreationDate, "
            + "state_name.modification_date AS StateModificationDate, "
            + "state_name.creation_user AS StateCreationUser, "
            + "state_name.modification_user AS StateModificationUser, contact.contact_id, "
            + "contact.first_name, contact.last_name, contact.phone, contact.email, "
            + "contact.creation_date AS ContactCreationDate, contact.modification_date AS ContactModificationDate, "
            + "contact.creation_user AS ContactCreationUser, contact.modification_user AS ContactModificationUser, "
            + "account_status.account_status_id AS AccStatusId, account_status.description AS AccStatusDesc, "
            + "account_status.creation_date AS AccStatusCreationDate, "
            + "account_status.creation_user AS AccStatusCreationUser,"
            + "account_status.modification_date AS AccStatusModDate, "
            + "account_status.modification_user AS AccStatusModUser "
            + "FROM user_account, user_address, address, state_name, account_status, contact, user_contact WHERE "
            + "user_account.user_account_id = user_contact.user_account_id "
            + " AND user_account.user_account_id = user_address.user_account_id "
            + "AND user_address.address_id = address.address_id "
            + "AND user_contact.contact_id = contact.contact_id "
            + "AND address.state_name_id = state_name.state_name_id "
            + "AND user_account.account_status_id = account_status.account_status_id ";

    /**
     * The base filter query.
     */
    private static final String BASE_FILTER_QUERY = BASE_QUERY + " AND ";

    /**
     * The 'create' operation flag. It is used in batch mode.
     */
    private static final int CREATE_OP = 1;

    /**
     * The 'delete' operation flag. It is used in batch mode.
     */
    private static final int DELETE_OP = 2;

    /**
     * The 'update' operation flag. It is used in batch mode.
     */
    private static final int UPDATE_OP = 3;

    /**
     * <p>
     * This is the connection factory where where connections to the data store will be retrieved. It may not be
     * null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Utilized In: createUser, retrieveUser, updateUser, deleteUser, listUsers, searchUsers, createUsers,
     * retrieveUsers, updateUsers, deleteUsers
     * </p>
     *
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * This is the connection name which will be used to retrieve a database connection from the connection
     * factory. It may not be null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Utilized In: createUser, retrieveUser, updateUser, deleteUser, listUsers, searchUsers, createUsers,
     * retrieveUsers, updateUsers, deleteUsers
     * </p>
     *
     */
    private String connectionName;

    /**
     * <p>
     * This is the algorithm name that is used when encrypting/decrypting the user password into the datastore.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setAlgorithmName
     * </p>
     * <p>
     * Accessed In: getAlgorithmName
     * </p>
     * <p>
     * Utilized In: createUser, retrieveUser, updateUser, createUsers, retrieveUsers and updateUsers.
     * </p>
     *
     *
     *
     */
    private String algorithmName;

    /**
     * <p>
     * An instance of the id generator that is used to generate ids for the entities.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Utilized In: createUser, updateUser, createUsers,and updateUsers.
     * </p>
     *
     */
    private IDGenerator idGenerator;

    /**
     * <p>
     * This is the search string builder that is used to perform the searches based on any provided filters. It is
     * preconfigured with an SQL Statement, and is coupled with another preconfigured Search Builder filter that is
     * used to tie the separate tables together.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not modified
     * </p>
     * <p>
     * Utilized In: searchUsers
     * </p>
     *
     */
    private DatabaseSearchStringBuilder searchStringBuilder;

    /**
     * <p>
     * Constructor that accepts the connection factory, connection name, id generator name, and algorithm name.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use when retrieving a connection from the connection factory.
     * @param algoName The encryption algorithm name to use when persisting the user password.
     * @param idGen The Id Generator String name to use.
     * @throws IDGenerationException if error occur while creating IDGenerator.
     * @throws IllegalArgumentException if connFactory, connName, algoName, idGen is null or connName, algoName,
     *         idGen is an empty String.
     */
    public DbUserDAO(DBConnectionFactory connFactory, String connName, String algoName, String idGen)
            throws IDGenerationException {
        Utils.checkNull(connFactory, "connFactory");
        Utils.checkString(connName, "connName", false);
        Utils.checkString(algoName, "algoName", false);
        Utils.checkString(idGen, "idGen", false);

        Map aliases = new HashMap();
        aliases.put(UserDAO.SEARCH_USERNAME, "user_account.user_name");
        aliases.put(UserDAO.SEARCH_PASSWORD, "user_account.password");
        aliases.put(UserDAO.SEARCH_FIRST_NAME, "contact.first_name");
        aliases.put(UserDAO.SEARCH_LAST_NAME, "contact.last_name");
        aliases.put(UserDAO.SEARCH_PHONE_NUMBER, "contact.phone");
        aliases.put(UserDAO.SEARCH_EMAIL, "contact.email");
        aliases.put(UserDAO.SEARCH_STREET_ADDRESS1, "address.line1");
        aliases.put(UserDAO.SEARCH_STREET_ADDRESS2, "address.line2");
        aliases.put(UserDAO.SEARCH_CITY, "address.city");
        aliases.put(UserDAO.SEARCH_STATE, "state_name.name");
        aliases.put(UserDAO.SEARCH_ZIP_CODE, "address.zip_code");
        aliases.put(UserDAO.SEARCH_CREATED_DATE, "user_account.creation_date");
        aliases.put(UserDAO.SEARCH_CREATED_USER, "user_account.creation_user");
        aliases.put(UserDAO.SEARCH_MODIFICATION_DATE, "user_account.modification_date");
        aliases.put(UserDAO.SEARCH_MODIFICATION_USER, "user_account.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);

        this.connectionFactory = connFactory;
        this.connectionName = connName;
        this.algorithmName = algoName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGen);
    }

    /**
     * <p>
     * Creates a datastore entry for the given user. An id is automatically generated by the DAO and assigned to
     * the user. The User is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same user Object, with an assigned id.
     * @param user The user to create within the datastore.
     * @param username The username of the user responsible for creating the User entry within the datastore.
     * @throws IllegalArgumentException if the user or username is null, or if username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.tenceException if a problem
     *         occurs while accessing the datastore.
     */
    public User createUser(User user, String username) throws UserDAOException {
        checkUser(user);
        Utils.checkString(username, "username", false);

        Connection conn = createConnection();

        try {
            user = createUser(conn, user, username);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new UserDAOException("Error occur during database operation.", ex, user);
        } finally {
            DBUtils.close(conn);
        }

        return user;
    }

    /**
     * Creates the user.
     *
     * @param conn the connection name.
     * @param user the user.
     * @param username the creation user.
     * @return The user.
     * @throws UserDAOException if error occurs.
     * @throws SQLException if error occurs.
     */
    private User createUser(Connection conn, User user, String username) throws UserDAOException, SQLException {
        // generate id first
        long contactId = generateId(user.getContactInfo().getId());
        long addressId = generateId(user.getAddress().getId());
        long userId = generateId(user.getId());

        // insert the contact and address
        DBUtils.insertContact(conn, user.getContactInfo(), contactId, username);
        DBUtils.insertAddress(conn, user.getAddress(), addressId, username);
        insertUserAccount(conn, user, userId, username);
        // insert the address - user and contact - user references
        DBUtils.execute(conn, INSERT_USER_ADDRESS, userId, addressId, username);
        DBUtils.execute(conn, INSERT_USER_CONTACT, userId, contactId, username);

        // set the ids
        user.setId(userId);
        user.getAddress().setId(addressId);
        user.getContactInfo().setId(contactId);

        return user;
    }

    /**
     * Creates the database connection with auto commit set to false.
     *
     * @return the database connection.
     * @throws UserDAOException if connection cannot be created.
     */
    private Connection createConnection() throws UserDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
 //           conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new UserDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new UserDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws UserDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws UserDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new UserDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * Inserts the account data.
     *
     * @param conn the connection to be used.
     * @param user the user.
     * @param userId the user id.
     * @param username the user name.
     * @throws SQLException the SQLException.
     */
    private void insertUserAccount(Connection conn, User user, long userId, String username) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_USER_ACCOUNT);
            // set the values
            pstmt.setLong(1, userId); // user_account_id
            pstmt.setLong(2, user.getCompanyId()); // company_id
            pstmt.setLong(3, user.getAccountStatus().getId()); // account_status_id
            pstmt.setString(4, user.getUsername()); // user_name
            pstmt.setString(5, Utils.encrypt(algorithmName, user.getPassword())); // password

            // init the dates
            Date now = DBUtils.initUserAndDates(pstmt, username, 6);
            pstmt.executeUpdate();
            DBUtils.setCreationFields(user, username, now);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves a User from the datastore with the provided id. If no User with that id exists, then a null is
     * returned.
     * </p>
     *
     * @param id The id of the user to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User retrieveUser(long id) throws UserDAOException {
        Utils.checkPositive(id, "id");

        Filter filter = new EqualToFilter("user_account.user_account_id", new Long(id));
        // search the users
        User[] list = searchUsers(filter);
        if (list.length == 0) {
            return null;
        }

        return list[0];
    }

    /**
     * Creates <code>User</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the User created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private User populateUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_account_id"));
        // company id is optional, so if is 0 then it cannot be set
        long companyId = rs.getLong("company_id");
        if (companyId > 0) {
            user.setCompanyId(companyId);
        }

        user.setUsername(rs.getString("user_name"));
        // set the current algorithm
        user.setAlgorithmName(algorithmName);
        // encrypt password and set it
        user.setPassword(Utils.decrypt(algorithmName, rs.getString("password")));

        user.setCreationDate(rs.getTimestamp("UserCreationDate"));
        user.setModificationDate(rs.getTimestamp("UserModDate"));
        user.setCreationUser(rs.getString("UserCreationUser"));
        user.setModificationUser(rs.getString("UserModUser"));

        // set the address, status and contact
        user.setAccountStatus(populateAccountStatus(rs));
        user.setAddress(DBUtils.populateAddress(rs));
        user.setContactInfo(DBUtils.populateContact(rs));

        user.setChanged(false);
        return user;
    }

    /**
     * Creates <code>AccountStatus</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the AccountStatus created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private static AccountStatus populateAccountStatus(ResultSet rs) throws SQLException {
        AccountStatus status = new AccountStatus();
        status.setId(rs.getLong("AccStatusId"));
        status.setDescription(rs.getString("AccStatusDesc"));

        status.setCreationDate(rs.getTimestamp("AccStatusCreationDate"));
        status.setModificationDate(rs.getTimestamp("AccStatusModDate"));
        status.setCreationUser(rs.getString("AccStatusCreationUser"));
        status.setCreationUser(rs.getString("AccStatusModUser"));

        status.setChanged(false);
        return status;
    }

    /**
     * <p>
     * Updates the given User in the data store. The User is considered to have been modified by the specified
     * username.
     * </p>
     *
     * @param user The User entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the user is null, or the username is null, or the username is an empty
     *         String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to update was not found in the data store.
     */
    public void updateUser(User user, String username) throws UserDAOException {
        Utils.checkNull(user, "user");
        Utils.checkString(username, "username", false);

        // check if was modified
        if (!user.isChanged()) {
            return;
        }

        Connection conn = createConnection();
        try {
            updateUser(conn, user, username);
            conn.commit();
        } catch (EncryptionException ex) {
            DBUtils.rollback(conn);
            throw new UserDAOException("Error occur during encryption.", ex, user);
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new UserDAOException("Error occur during update.", ex, user);
        } finally {
            DBUtils.close(conn);
        }
        user.setChanged(false);
    }

    /**
     * Updates the user in the persistence.
     *
     * @param conn the database connection to be used.
     * @param user the User object to be updated.
     * @param username the modification username.
     * @throws UserDAOException if any required value is missing.
     * @throws SQLException if database error occur.
     */
    private void updateUser(Connection conn, User user, String username) throws UserDAOException, SQLException {
        checkUser(user);

        try {
            int count = updateUserImpl(conn, user, username);
            if (count == 0) {
                throw new UserNotFoundException("Missing company.", null, user);
            }

            // update address if was modified
            if (user.getAddress().isChanged()) {
                DBUtils.updateAddress(conn, user.getAddress(), username);
            }
            // update contract if was modified
            if (user.getContactInfo().isChanged()) {
                DBUtils.updateContact(conn, user.getContactInfo(), username);
            }

            // update references
            DBUtils.executeUpdate(conn, UPDATE_USER_ADDRESS, user.getAddress().getId(), user.getId(), username);
            DBUtils
                    .executeUpdate(conn, UPDATE_USER_CONTACT, user.getContactInfo().getId(), user.getId(),
                            username);

        } catch (EncryptionException ex) {
            throw new UserDAOException("Error occur during encryption.", ex, user);
        }
    }

    /**
     * Updates the <code>user_account</code> table using data from given user object.
     *
     * @param conn the database connection to be used.
     * @param user the User object to be updated.
     * @param username the modification username.
     * @return the numberof affected rows.
     * @throws SQLException if error occurs during update.
     */
    private int updateUserImpl(Connection conn, User user, String username) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(UPDATE_USER_ACCOUNT);
            // set the variables
            pstmt.setLong(1, user.getCompanyId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, Utils.encrypt(algorithmName, user.getPassword()));
            pstmt.setLong(4, user.getAccountStatus().getId());

            // set times
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(5, time);
            pstmt.setString(6, username);
            pstmt.setLong(7, user.getId());

            int result = pstmt.executeUpdate();
            DBUtils.setModificationFields(user, username, time);
            return result;
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * This method checks is all required fields are set.
     *
     * @param user the User object to be checked.
     * @throws UserDAOException if any required property is missing.
     */
    private static void checkUser(User user) throws UserDAOException {
        Utils.checkNull(user, "user");
        if (user.getUsername() == null) {
            throw new UserDAOException("The username should be set.", null, user);
        }

        if (user.getPassword() == null) {
            throw new UserDAOException("The password should be set.", null, user);
        }

        if (user.getAccountStatus() == null) {
            throw new UserDAOException("The account status should be set.", null, user);
        }
        checkAddress(user);
        checkContact(user);
    }

    /**
     * Checks if all required fields in contact are set.
     *
     * @param user the user with contact to check.
     * @throws UserDAOException if any value is missing.
     */
    private static void checkContact(User user) throws UserDAOException {
        Contact contact = user.getContactInfo();
        if (contact == null) {
            throw new UserDAOException("The contact should be set.", null, user);
        }

        if (contact.getEmailAddress() == null) {
            throw new UserDAOException("The contact's email should be set.", null, user);
        }

        if (contact.getFirstName() == null) {
            throw new UserDAOException("The contact's first name should be set.", null, user);
        }

        if (contact.getLastName() == null) {
            throw new UserDAOException("The contact's last name should be set.", null, user);
        }

        if (contact.getPhoneNumber() == null) {
            throw new UserDAOException("The contact's phone number should be set.", null, user);
        }
    }

    /**
     * Checks if all required fields in address are set.
     *
     * @param user the user with address to check.
     * @throws UserDAOException if any value is missing.
     */
    private static void checkAddress(User user) throws UserDAOException {
        Address address = user.getAddress();
        if (address == null) {
            throw new UserDAOException("The address should be set.", null, user);
        }

        if (address.getCity() == null) {
            throw new UserDAOException("The address' city should be set.", null, user);
        }

        if (address.getLine1() == null) {
            throw new UserDAOException("The address' line 1 should be set.", null, user);
        }

        if (address.getZipCode() == null) {
            throw new UserDAOException("The address' zip code be set.", null, user);
        }
    }

    /**
     * <p>
     * Removes the specified User from the data store.
     * </p>
     *
     * @param user The user to delete.
     * @throws IllegalArgumentException if the user is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to delete was not found in the data store.
     */
    public void deleteUser(User user) throws UserDAOException {
        Utils.checkNull(user, "user");
        Connection conn = createConnection();
        try {
            deleteUser(conn, user);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new UserDAOException("Error occur while deleting the user.", ex, user);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Deletes the given user from the database.
     *
     * @param conn the connection to be used.
     * @param user the User instance to be removed.
     * @throws UserDAOException if user not exists in database.
     * @throws SQLException if database error occurs.
     */
    private void deleteUser(Connection conn, User user) throws UserDAOException, SQLException {
        // delete references first
        DBUtils.executeDelete(conn, DELETE_USER_CONTACT, user.getId());
        DBUtils.executeDelete(conn, DELETE_USER_ADDRESS, user.getId());
        // delete address and contact
        DBUtils.executeDelete(conn, DELETE_CONTACT, user.getContactInfo().getId());
        DBUtils.executeDelete(conn, DELETE_ADDRESS, user.getAddress().getId());

        // delete the user
        int count = DBUtils.executeDelete(conn, DELETE_USER_ACCOUNT, user.getId());
        if (count == 0) {
            throw new UserNotFoundException("User not exists.", null, user);
        }
    }

    /**
     * <p>
     * Enumerates all the Users that are present within the data store.
     * </p>
     *
     * @return A list of all the Users within the data store.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] listUsers() throws UserDAOException {
        return listUsers(BASE_QUERY, false);
    }

    /**
     * Returns the array of users that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of companies found.
     * @throws UserDAOException if any error occurs.
     */
    private User[] listUsers(String query, boolean fillStatement) throws UserDAOException {
        Connection conn = createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // prepare the statement and fill if requested
            pstmt = conn.prepareStatement(query);
            if (fillStatement) {
                DBUtils.fillStatement(pstmt, searchStringBuilder.getValues());
            }

            // execute the query
            rs = pstmt.executeQuery();
            List result = new ArrayList();
            // populate the list
            while (rs.next()) {
                result.add(populateUser(rs));
            }

            // return the users array
            return (User[]) result.toArray(new User[result.size()]);
        } catch (SQLException ex) {
            throw new UserDAOException("Error occur while retrieving the user.", ex, null);
        } catch (EncryptionException ex) {
            throw new UserDAOException("Error occur while decryption the passcode.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Returns a list of all the users within the datastore that satisfy the filters that are provided. The filters
     * are defined using classes from the Search Builder v1.2 component.
     * </p>
     *
     *
     * @return A list of users that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] searchUsers(Filter filter) throws UserDAOException {
        Utils.checkNull(filter, "filter");

        // synchronize the access to searchStringBuilder to make class thread safe
        synchronized (searchStringBuilder) {
            return listUsers(searchStringBuilder.buildSearchString(filter), true);
        }
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given Users. An id is automatically generated by the DAO and
     * assigned to the user. The User is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same User objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the User in the returned array corresponds to the index of the
     *         User in the method argument.
     * @param users The users to create.
     * @param username The username of the one responsible for creating the users.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if users is null, or username is null, or username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public User[] createUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException {
        Utils.checkString(username, "user", false);
        Utils.checkNull(users, "users");
        return batchOperation(users, username, atomicBatchMode, CREATE_OP);
    }

    /**
     * <p>
     * Retrieves the users with the specified ids from the datastore.
     * </p>
     *
     * @param ids The ids of the Users to retrieve.
     * @throws IllegalArgumentException if the ids is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] retrieveUsers(long[] ids) throws UserDAOException {
        Utils.checkNull(ids, "ids");

        List idsList = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            idsList.add(new Long(ids[i]));
        }
        // create the filter
        Filter filter = new InFilter("user_account.user_account_id", idsList);
        return searchUsers(filter);
    }

    /**
     * <p>
     * Updates the given Users in the data store. The companies are considered to have been modified by the
     * specified username.
     * </p>
     *
     * @param users The Users to update.
     * @param username The username of the one responsible for modifying the users.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if the users is null, or the username is null or an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public void updateUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException {
        Utils.checkString(username, "username", false);
        Utils.checkNull(users, "users");
        batchOperation(users, username, atomicBatchMode, UPDATE_OP);
    }

    /**
     * <p>
     * Deletes the specified Users from the data store.
     * </p>
     *
     * @param users The users to delete.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if users is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public void deleteUsers(User[] users, boolean atomicBatchMode) throws UserDAOException {
        Utils.checkNull(users, "users");
        batchOperation(users, null, atomicBatchMode, DELETE_OP);
    }

    /**
     * It executes operation in batch mode.
     *
     * @param users the User array to process.
     * @param user the user.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @param operation the operation to perform.
     * @return the operation result.
     * @throws UserDAOException if any error occurs.
     */
    private User[] batchOperation(User[] users, String user, boolean atomicBatchMode, int operation)
            throws UserDAOException {

        // check if array is not null
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                throw new IllegalArgumentException("Users array contains null at index: " + i);
            }
        }

        // creates the connection
        Connection conn = createConnection();
        Throwable[] exceptions = new Throwable[users.length];
        boolean wasError = false;
        try {
            for (int i = 0; i < users.length; i++) {
                try {
                    if (operation == CREATE_OP) {
                        users[i] = createUser(conn, users[i], user);
                    } else if (operation == UPDATE_OP) {
                        updateUser(conn, users[i], user);
                    } else if (operation == DELETE_OP) {
                        deleteUser(conn, users[i]);
                    }
                } catch (Exception ex) {
                    exceptions[i] = ex;
                    wasError = true;

                    // rollback the last operation or whole block if in atomic mode
                    DBUtils.rollback(conn);
                    if (atomicBatchMode) {
                        throw new BatchUserDAOException("Error occurs during batch operation.", exceptions, users);
                    }
                } finally {
                    if (!atomicBatchMode) {
                        conn.commit();
                    }
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new UserDAOException("Error occurs while batch operation.", ex, null);
        } finally {
            DBUtils.close(conn);
        }

        if (wasError) {
            throw new BatchUserDAOException("Error occurs during batch operation.", exceptions, users);
        }

        return users;
    }

}
