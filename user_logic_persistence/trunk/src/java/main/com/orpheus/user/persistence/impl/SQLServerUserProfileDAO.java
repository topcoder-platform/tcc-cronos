/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.orpheus.user.persistence.DuplicateEntryException;
import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserLogicPersistenceHelper;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileBean;
import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.cache.Cache;

/**
 * <p>
 * An implementation of the {@link UserProfileDAO} which persists user profiles
 * in a SQL Server relational database.
 * </p>
 * <p>
 * A <code>DBConnectionFactory</code> is used to obtain a connection to the
 * underlying database. It is expected that the database connection will be
 * created from a <code>DataSource</code> provided by the application server.
 * To this end, the <code>DBConnectionFactory</code> should be configured to
 * use a <code>JNDIConnectionProducer</code> with a JNDI reference to the
 * <code>DataSource</code>. Please consult the DB Connection Factory
 * component specification for information on how to configure the
 * <code>JNDIConnectionProducer</code>.
 * </p>
 * <p>
 * User profiles are cached within this class using a <code>Cache</code>
 * instance in order to reduce the number of requests made to the underlying
 * database and to improve response times. The manner in which the profiles are
 * cached (including the caching strategy, cache size, etc) can be configured in
 * the Simple Cache configuration namespace. Please consult the Simple Cache
 * component specification for information on how to configure the cache.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * The names of the <code>DBConnectionFactory</code> and <code>Cache</code>
 * classes to use, as well as the name of the database connection to create,
 * need to be configured in the configuration namespace provided to the
 * {@link #SQLServerUserProfileDAO(String)} constructor. The configuration
 * parameters are listed in the table below.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>specNamespace</td>
 * <td>The ObjectFactory configuration namespace specifying the
 * <code>DBConnectionFactory</code> and <code>Cache</code> object creation.
 * This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>factoryKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>DBConnectionFactory</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>cacheKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>Cache</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>name</td>
 * <td>The name of the database connection to retrieve from the
 * <code>DBConnectionFactory</code>. If this property is not specified, the
 * default database connection will be retrieved</td>
 * <td>No</td>
 * </tr>
 * </tbody> </table>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileBean
 * @see UserProfileDTO
 */
public class SQLServerUserProfileDAO implements UserProfileDAO {

    /**
     * <p>
     * The SQL statement to insert a user into the database.
     * </p>
     */
    private static final String INSERT_USER_SQL_STATEMENT
        = "INSERT INTO any_user (id, handle, e_mail, passwd, is_active) VALUES (?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to insert a player into the database.
     * </p>
     */
    private static final String INSERT_PLAYER_SQL_STATEMENT
        = "INSERT INTO player (any_user_id, contact_info_id, payment_pref) VALUES (?, ?, ?)";

    /**
     * <p>
     * The SQL statement to insert an admin into the database.
     * </p>
     */
    private static final String INSERT_ADMIN_SQL_STATEMENT = "INSERT INTO admin (any_user_id) VALUES (?)";

    /**
     * <p>
     * The SQL statement to insert a sponsor into the database.
     * </p>
     */
    private static final String INSERT_SPONSOR_SQL_STATEMENT
        = "INSERT INTO sponsor (any_user_id, contact_info_id, fax, payment_pref, is_approved) VALUES (?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to insert contact information into the database.
     * </p>
     */
    private static final String INSERT_CONTACT_INFO_SQL_STATEMENT = "INSERT INTO contact_info "
            + "(id, first_name, last_name, address_1, address_2, city, state, postal_code, telephone) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to update a user in the database.
     * </p>
     */
    private static final String UPDATE_USER_SQL_STATEMENT
        = "UPDATE any_user SET handle=?, e_mail=?, passwd=?, is_active=? WHERE id=?";

    /**
     * <p>
     * The SQL statement to update a player in the database.
     * </p>
     */
    private static final String UPDATE_PLAYER_SQL_STATEMENT
        = "UPDATE player SET contact_info_id=?, payment_pref=? WHERE any_user_id=?";

    /**
     * <p>
     * The SQL statement to update a sponsor in the database.
     * </p>
     */
    private static final String UPDATE_SPONSOR_SQL_STATEMENT
        = "UPDATE sponsor SET contact_info_id=?, fax=?, payment_pref=?, is_approved=? WHERE any_user_id=?";

    /**
     * <p>
     * The SQL statement to update the contact information in the database.
     * </p>
     */
    private static final String UPDATE_CONTACTINFO_SQL_STATEMENT = "UPDATE contact_info "
            + "SET first_name=?, last_name=?, address_1=?, address_2=?, city=?, state=?, postal_code=?, telephone=? "
            + "WHERE id=?";

    /**
     * <p>
     * The SQL statement to select the player information from the database.
     * </p>
     */
    private static final String SELECT_PLAYER_SQL_STATEMENT = "SELECT * FROM ( "
            + "SELECT * FROM any_user INNER JOIN player ON any_user.id=player.any_user_id WHERE id=?) AS userplayer "
            + "LEFT OUTER JOIN contact_info ON userplayer.contact_info_id=contact_info.id";

    /**
     * <p>
     * The SQL statement to select the admin information from the database.
     * </p>
     */
    private static final String SELECT_ADMIN_SQL_STATEMENT = "SELECT * FROM any_user "
            + "INNER JOIN admin ON any_user.id=admin.any_user_id WHERE id=?";

    /**
     * <p>
     * The SQL statement to select the sponsor information from the database.
     * </p>
     */
    private static final String SELECT_SPONSOR_SQL_STATEMENT = "SELECT * FROM ( "
            + "SELECT * FROM any_user INNER JOIN sponsor ON any_user.id=sponsor.any_user_id WHERE id=?) AS usersponsor "
            + " LEFT OUTER JOIN contact_info ON usersponsor.contact_info_id=contact_info.id";

    /**
     * <p>
     * The SQL statement to select all the players from the database.
     * </p>
     */
    private static final String SELECT_ALL_PLAYERS_SQL_STATEMENT = "SELECT * FROM ( "
            + "SELECT * FROM any_user INNER JOIN player ON any_user.id=player.any_user_id) AS userplayer "
            + "LEFT OUTER JOIN contact_info ON userplayer.contact_info_id=contact_info.id";

    /**
     * <p>
     * The SQL statement to select all the admins from the database.
     * </p>
     */
    private static final String SELECT_ALL_ADMINS_SQL_STATEMENT = "SELECT * FROM any_user "
            + "INNER JOIN admin ON any_user.id=admin.any_user_id";

    /**
     * <p>
     * The SQL statement to select all the sponsors from the database.
     * </p>
     */
    private static final String SELECT_ALL_SPONSORS_SQL_STATEMENT = "SELECT * FROM ( "
            + "SELECT * FROM any_user INNER JOIN sponsor ON any_user.id=sponsor.any_user_id) AS usersponsor "
            + "LEFT OUTER JOIN contact_info ON usersponsor.contact_info_id=contact_info.id";

    /**
     * <p>
     * The connection factory that is used to obtain database connections. It
     * should be backed by a JNDI connection producer, which eases the obtaining
     * of a connection from a DataSource via JNDI.
     * </p>
     * <p>
     * This field is set in the constructor, and will never change afterwards.
     * It cannot be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * The name of the database connection to obtain from the connection factory.
     * </p>
     * <p>
     * This field is set in the constructor, and will never change afterwards.
     * The value of this field can be null to indicate that the default database
     * connection should be retrieved from the connection factory. It cannot be
     * a blank string.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * A cache of UserProfileDTO objects. The objects are indexed in the
     * by the corresponding user profile identifiers. The cache size,
     * replacement policy and other caching options are set in the Simple Cache
     * configuration namespace.
     * </p>
     * <p>
     * This field is created in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * Creates a new <code>SQLServerUserProfileDAO</code> instance using the
     * specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the
     * <code>DBConnectionFactory</code>, which this class uses to obtain
     * database connections, and the <code>Cache</code>, which is used to
     * cache user profiles within this class. It is also used to obtain the name
     * of the database connection to obtain from the
     * <code>DBConnectionFactory</code>. If an error occurs reading the
     * configuration information or while instantiating the
     * <code>DBConnectionFactory</code> and <code>Cache</code> objects, an
     * <code>ObjectInstantiationException</code> is thrown. Please consult the
     * class documentation for more information on the configuration parameters.
     * </p>
     *
     * @param namespace the configuration namespace from which to read
     *        configuration information
     * @throws IllegalArgumentException if the configuration namespace is
     *         <code>null</code> or a blank string
     * @throws ObjectInstantiationException if an error occurs reading from the
     *         configuration namespace or while instantiating the
     *         <code>DBConnectionFactory</code> and <code>Cache</code>
     *         objects
     */
    public SQLServerUserProfileDAO(String namespace) throws ObjectInstantiationException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(namespace, "namespace");

        // Create the DBConnectionFactory and Cache instances.
        connectionFactory = (DBConnectionFactory) UserLogicPersistenceHelper.createObject(namespace, "factoryKey",
                                                                                          DBConnectionFactory.class);
        cache = (Cache) UserLogicPersistenceHelper.createObject(namespace, "cacheKey", Cache.class);

        // Get the connection name.
        connectionName = UserLogicPersistenceHelper.getConfigProperty(namespace, "name", false);
    }

    /**
     * <p>
     * Inserts the given user profile into the database. If the database already
     * contains a user profile with the same identifier as that of the given
     * user profile, a <code>DuplicateEntryException</code> is thrown.
     * </p>
     * <p>
     * Once the user profile has been successfully inserted, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the database and to improve response times.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to insert
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws DuplicateEntryException if the database contains a user profile
     *         with the same identifier as that of the given user profile
     * @throws PersistenceException if inserting the user profile into the
     *         database fails
     */
    public void insertProfile(UserProfileDTO profile) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNull(profile, "user profile DTO");

        Long id = extractUserProfileId(profile);

        // Check if the user profile is in cache.
        if (cache.get(id) != null) {
            throw new DuplicateEntryException("A user profile with ID, " + id + ", already exists in the database", id);
        }

        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // Check if the user profile is already in the database.
            if (recordExists(id.longValue(), "any_user", "id", connection)) {
                throw new DuplicateEntryException("A user profile with ID, " + id + ", already exists in the database",
                                                  id);
            }

            // Insert the user profile.
            if (profile.contains(UserProfileDTO.PLAYER_KEY)) {
                insertPlayer(profile, connection);
            } else if (profile.contains(UserProfileDTO.ADMIN_KEY)) {
                insertAdmin(profile, connection);
            } else if (profile.contains(UserProfileDTO.SPONSOR_KEY)) {
                insertSponsor(profile, connection);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to insert user profile with ID, " + id + ", into the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        // Put the user profile in cache.
        cache.put(id, profile);
    }

    /**
     * <p>
     * Retrieves the <code>Player</code> object from the given user profile
     * DTO, and inserts the information contained in the object into the
     * database. If contact information is associated with the player, then the
     * contact information is inserted as well. If the user profile DTO does not
     * contain a <code>Player</code> object, an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the player information to
     *        insert into the database
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the {@link UserProfiledTO#PLAYER_KEY}
     *         maps to <code>null</code> or a non-<code>Player</code>
     *         object in the given user profile DTO
     * @throws SQLException if inserting the player information into the
     *         database fails
     */
    private void insertPlayer(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.PLAYER_KEY);

        if (!(bean instanceof Player)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.PLAYER_KEY
                    + " key in the UserProfileDTO does not map to a Player object");
        }

        Player player = (Player) bean;

        // Insert into the contact_info and user tables.
        ContactInfo contactInfo = insertContactInfo(profile, connection);
        insertUser(player, connection);

        // Insert into the player table...
        PreparedStatement sql = connection.prepareStatement(INSERT_PLAYER_SQL_STATEMENT);

        // Insert the user ID.
        sql.setLong(1, player.getId());

        // Insert the contact information ID if there are contact information
        // associated with the player. Otherwise, set the field to null.
        if (contactInfo != null) {
            sql.setLong(2, contactInfo.getId());
        } else {
            sql.setNull(2, Types.BIGINT);
        }

        // Insert the payment preference.
        sql.setString(3, player.getPaymentPref());

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves the <code>Admin</code> object from the given user profile
     * DTO, and inserts the information contained in the object into the
     * database. If the user profile DTO does not contain an <code>Admin</code>
     * object, an <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the admin information to
     *        insert into the database
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the {@link UserProfiledTO#ADMIN_KEY}
     *         maps to <code>null</code> or a non-<code>Admin</code> object
     *         in the given user profile DTO
     * @throws SQLException if inserting the admin information into the database
     *         fails
     */
    private void insertAdmin(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.ADMIN_KEY);

        if (!(bean instanceof Admin)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.ADMIN_KEY
                    + " key in the UserProfileDTO does not map to an Admin object");
        }

        Admin admin = (Admin) bean;

        // Insert into the user table.
        insertUser(admin, connection);

        // Insert into the admin table...
        PreparedStatement sql = connection.prepareStatement(INSERT_ADMIN_SQL_STATEMENT);

        // Insert the user ID.
        sql.setLong(1, admin.getId());

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves the <code>Sponsor</code> object from the given user profile
     * DTO, and inserts the information contained in the object into the
     * database. If contact information is associated with the sponsor, then the
     * contact information is inserted as well. If the user profile DTO does not
     * contain a <code>Sponsor</code> object, an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the sponsor information to
     *        insert into the database
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the
     *         {@link UserProfiledTO#SPONSOR_KEY} maps to <code>null</code> or
     *         a non-<code>Sponsor</code> object in the given user profile
     *         DTO
     * @throws SQLException if inserting the sponsor information into the
     *         database fails
     */
    private void insertSponsor(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.SPONSOR_KEY);

        if (!(bean instanceof Sponsor)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.SPONSOR_KEY
                    + " key in the UserProfileDTO does not map to a Sponsor object");
        }

        Sponsor sponsor = (Sponsor) bean;

        // Insert into the contact_info and user tables.
        ContactInfo contactInfo = insertContactInfo(profile, connection);
        insertUser(sponsor, connection);

        // Insert into the sponsor table...
        PreparedStatement sql = connection.prepareStatement(INSERT_SPONSOR_SQL_STATEMENT);

        // Insert the user ID.
        sql.setLong(1, sponsor.getId());

        // Insert the contact information ID if there are contact information
        // associated with the sponsor. Otherwise, set the field to null.
        if (contactInfo != null) {
            sql.setLong(2, contactInfo.getId());
        } else {
            sql.setNull(2, Types.BIGINT);
        }

        // Insert the fax number (must be a numeric/double value). Otherwise,
        // set the field to null.
        try {
            sql.setDouble(3, Double.valueOf(sponsor.getFax()).doubleValue());
        } catch (NullPointerException e) {
            // Sponsor.getFax() returned null.
            sql.setNull(3, Types.NUMERIC);
        } catch (NumberFormatException e) {
            sql.setNull(3, Types.NUMERIC);
        }

        // Insert the payment preference field.
        sql.setString(4, sponsor.getPaymentPref());

        // Insert the approved status. If the approved status is undecided
        // (null), set the field to null.
        if (sponsor.getApproved() != Sponsor.APPROVED_UNDECIDED) {
            sql.setBoolean(5, Boolean.valueOf(sponsor.getApproved()).booleanValue());
        } else {
            sql.setNull(5, Types.BOOLEAN);
        }

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves the <code>ContactInfo</code> object from the given user
     * profile DTO, inserts the contact information contained in the object into
     * the database, and returns the object. If the user profile DTO does not
     * contain a <code>ContactInfo</code> object, this method simply returns
     * <code>null</code>.
     * </p>
     *
     * @param profile the user profile DTO containing the contact information to
     *        insert into the database
     * @param connection the database connection to use
     * @return the <code>ContactInfo</code> object in the given user profile
     *         DTO, or <code>null</code> if no such object exists in the user
     *         profile DTO
     * @throws SQLException if inserting the contact information into the
     *         database fails
     */
    private ContactInfo insertContactInfo(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.CONTACT_INFO_KEY);

        // No contact information.
        if (bean == null) {
            return null;
        }

        if (!(bean instanceof ContactInfo)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.CONTACT_INFO_KEY
                    + " key in the UserProfileDTO does not map to a ContactInfo object");
        }

        ContactInfo contactInfo = (ContactInfo) bean;

        // Insert into the contact_info table.
        PreparedStatement sql = connection.prepareStatement(INSERT_CONTACT_INFO_SQL_STATEMENT);
        sql.setLong(1, contactInfo.getId());
        sql.setString(2, contactInfo.getFirstName());
        sql.setString(3, contactInfo.getLastName());
        sql.setString(4, contactInfo.getAddress1());
        sql.setString(5, contactInfo.getAddress2());
        sql.setString(6, contactInfo.getCity());
        sql.setString(7, contactInfo.getState());
        sql.setString(8 , contactInfo.getPostalCode());
        sql.setString(9, contactInfo.getTelephone());

        sql.executeUpdate();
        sql.close();

        return contactInfo;
    }

    /**
     * <p>
     * Inserts the given user into the database.
     * </p>
     *
     * @param user a <code>User</code> object containing the user information
     *        to insert into the database
     * @param connection the database connection to use
     * @throws SQLException if inserting the user information into the database
     *         fails
     */
    private void insertUser(User user, Connection connection) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(INSERT_USER_SQL_STATEMENT);
        sql.setLong(1, user.getId());
        sql.setString(2, user.getHandle());
        sql.setString(3, user.getEmail());
        sql.setString(4, user.getPassword());
        sql.setBoolean(5, Boolean.valueOf(user.getActive()).booleanValue());
        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Updates the given user profile in the database. If the user profile does
     * not exist in the database, an <code>EntryNotFoundException</code> is
     * thrown.
     * </p>
     * <p>
     * Once the user profile has been successfully update, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the database and to improve response times.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to update
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws EntryNotFoundException if the user profile does not exist in the
     *         database
     * @throws PersistenceException if updating the user profile in the database
     *         fails
     */
    public void updateProfile(UserProfileDTO profile) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNull(profile, "user profile DTO");

        Long id = extractUserProfileId(profile);

        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // Check if the user profile is in the database.
            if (!recordExists(id.longValue(), "any_user", "id", connection)) {
                throw new EntryNotFoundException("No user profile with ID, " + id + ", was found in the database", id);
            }

            // Update the user profile.
            if (profile.contains(UserProfileDTO.PLAYER_KEY)) {
                updatePlayer(profile, connection);
            } else if (profile.contains(UserProfileDTO.ADMIN_KEY)) {
                updateAdmin(profile, connection);
            } else if (profile.contains(UserProfileDTO.SPONSOR_KEY)) {
                updateSponsor(profile, connection);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to update user profile with ID, " + id, e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        // Put the user profile in cache.
        cache.put(id, profile);
    }

    /**
     * <p>
     * Retrieves the <code>Player</code> object from the given user profile
     * DTO, and updates the corresponding record in the database with the
     * updated player information contained in the object. If the user profile
     * contains the player's contact information, and then the contact
     * information is updated as well, or inserted into the database if there is
     * no entry for it yet. If there are no contact information in the user
     * profile, but the database contains an entry for the contact information,
     * then that entry is deleted from the database. If the user profile DTO
     * does not contain a <code>Player</code> object, an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the updated player
     *        information
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the {@link UserProfiledTO#PLAYER_KEY}
     *         maps to <code>null</code> or a non-<code>Player</code>
     *         object in the given user profile DTO
     * @throws SQLException if updating the player information in the database
     *         fails
     */
    private void updatePlayer(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.PLAYER_KEY);

        if (!(bean instanceof Player)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.PLAYER_KEY
                                               + " key in the UserProfileDTO does not map to an Player object");
        }

        Player player = (Player) bean;

        // Update the user table.
        updateUser(player, connection);

        // Update the contact_info table.
        ContactInfo contactInfo = null;
        if (!recordExists(player.getId(), "contact_info", "id", connection)) {
            // Insert the contact information if it does not exist in the
            // database.
            contactInfo = insertContactInfo(profile, connection);
        } else {
            // Otherwise, simply update the existing contact information.
            contactInfo = updateContactInfo(profile, connection);
        }

        // Update the player table...
        PreparedStatement sql = connection.prepareStatement(UPDATE_PLAYER_SQL_STATEMENT);

        // Update the contact information ID. If contact information is no
        // longer associated with the player, set the field to null.
        if (contactInfo != null) {
            sql.setLong(1, contactInfo.getId());
        } else {
            sql.setNull(1, Types.BIGINT);
        }

        // Update the payment preference.
        sql.setString(2, player.getPaymentPref());

        // WHERE id = ?
        sql.setLong(3, player.getId());

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves the <code>Admin</code> object from the given user profile
     * DTO, and updates the corresponding record in the database with the
     * updated admin information contained in the object. If the user profile
     * DTO does not contain an <code>Admin</code> object, an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the updated admin
     *        information
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the {@link UserProfiledTO#ADMIN_KEY}
     *         maps to <code>null</code> or a non-<code>Admin</code> object
     *         in the given user profile DTO
     * @throws SQLException if updating the admin information in the database
     *         fails
     */
    private void updateAdmin(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.ADMIN_KEY);

        if (!(bean instanceof Admin)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.ADMIN_KEY
                                               + " in the UserProfileDTO does not map to an Admin object");
        }

        Admin admin = (Admin) bean;

        // Update the user table.
        updateUser(admin, connection);
    }

    /**
     * <p>
     * Retrieves the <code>Sponsor</code> object from the given user profile
     * DTO, and updates the corresponding record in the database with the
     * updated sponsor information contained in the object. If the user profile
     * contains the sponsor's contact information, and then the contact
     * information is updated as well, or inserted into the database if there is
     * no entry for it yet. If there are no contact information in the user
     * profile, but the database contains an entry for the contact information,
     * then that entry is deleted from the database. If the user profile DTO
     * does not contain a <code>Sponsor</code> object, an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param profile the user profile DTO containing the updated sponsor
     *        information
     * @param connection the database connection to use
     * @throws IllegalArgumentException if the
     *         {@link UserProfiledTO#SPONSOR_KEY} maps to <code>null</code> or
     *         a non-<code>Sponsor</code> object in the given user profile
     *         DTO
     * @throws SQLException if updating the sponsor information in the database
     *         fails
     */
    private void updateSponsor(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.SPONSOR_KEY);

        if (!(bean instanceof Sponsor)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.SPONSOR_KEY
                    + " key in the UserProfileDTO does not map to a Sponsor object");
        }

        Sponsor sponsor = (Sponsor) bean;

        // Update the user table.
        updateUser(sponsor, connection);

        // Update the contact_info table.
        ContactInfo contactInfo = null;
        if (!recordExists(sponsor.getId(), "contact_info", "id", connection)) {
            // Insert the contact information if it does not exist in the
            // database.
            contactInfo = insertContactInfo(profile, connection);
        } else {
            // Otherwise, simply update the existing contact information.
            contactInfo = updateContactInfo(profile, connection);
        }

        // Update the sponsor table...
        PreparedStatement sql = connection.prepareStatement(UPDATE_SPONSOR_SQL_STATEMENT);

        // Update the contact information ID. If contact information is no
        // longer associated with the sponsor, set the field to null.
        if (contactInfo != null) {
            sql.setLong(1, contactInfo.getId());
        } else {
            sql.setNull(1, Types.BIGINT);
        }

        // Update the fax number (must be a numeric/double value). Otherwise,
        // set the field to null.
        try {
            sql.setDouble(2, Double.valueOf(sponsor.getFax()).doubleValue());
        } catch (NullPointerException e) {
            // Sponsor.getFax() returned null.
            sql.setNull(2, Types.NUMERIC);
        } catch (NumberFormatException e) {
            sql.setNull(2, Types.NUMERIC);
        }

        // Update the payment preference.
        sql.setString(3, sponsor.getPaymentPref());

        // Update the approved status. If the approved status is undecided
        // (null), set the field to null.
        if (sponsor.getApproved() == Sponsor.APPROVED_UNDECIDED) {
            sql.setNull(4, Types.BOOLEAN);
        } else {
            sql.setBoolean(4, Boolean.valueOf(sponsor.getApproved()).booleanValue());
        }

        // WHERE id = ?
        sql.setLong(5, sponsor.getId());

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Updates the user record in the database corresponding to the given
     * <code>User</code> object with the information it contains.
     * </p>
     *
     * @param user a <code>User</code> object containing the updated user
     *        information
     * @param connection the database connection to use
     * @throws SQLException if updating the user information fails
     */
    private void updateUser(User user, Connection connection) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(UPDATE_USER_SQL_STATEMENT);

        sql.setString(1, user.getHandle());
        sql.setString(2, user.getEmail());
        sql.setString(3, user.getPassword());
        sql.setBoolean(4 , Boolean.valueOf(user.getActive()).booleanValue());

        // WHERE id = ?
        sql.setLong(5, user.getId());

        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves the <code>ContactInfo</code> object from the given user
     * profile DTO, updates the corresponding record in the database with the
     * updated contact information contained in the object, and returns the
     * object. If the user profile DTO does not contain a
     * <code>ContactInfo</code> object, this method simply returns
     * <code>null</code>.
     * </p>
     *
     * @param profile the user profile DTO containing the updated contact
     *        information
     * @param connection the database connection to use
     * @return the <code>ContactInfo</code> object in the given user profile
     *         DTO, or <code>null</code> if no such object exists in the user
     *         profile DTO
     * @throws SQLException if updating the contact information in the database
     *         fails
     */
    private ContactInfo updateContactInfo(UserProfileDTO profile, Connection connection) throws SQLException {
        Object bean = profile.get(UserProfileDTO.CONTACT_INFO_KEY);

        // No contact information.
        if (bean == null) {
            return null;
        }

        if (!(bean instanceof ContactInfo)) {
            throw new IllegalArgumentException("The " + UserProfileDTO.CONTACT_INFO_KEY
                    + " key in the UserProfileDTO does not map to a ContactInfo object");
        }

        ContactInfo contactInfo = (ContactInfo) bean;

        // Update the contact_info table.
        PreparedStatement sql = connection.prepareStatement(UPDATE_CONTACTINFO_SQL_STATEMENT);

        sql.setString(1, contactInfo.getFirstName());
        sql.setString(2, contactInfo.getLastName());
        sql.setString(3, contactInfo.getAddress1());
        sql.setString(4, contactInfo.getAddress2());
        sql.setString(5, contactInfo.getCity());
        sql.setString(6, contactInfo.getState());
        sql.setString(7, contactInfo.getPostalCode());
        sql.setString(8, contactInfo.getTelephone());

        // WHERE id = ?
        sql.setLong(9, contactInfo.getId());

        sql.executeUpdate();
        sql.close();

        return contactInfo;
    }

    /**
     * <p>
     * Retrieves the user profile with the specified identifier from the
     * database. If no such user profile could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     * <p>
     * Once the user profile has been retrieved, it is cached within this class
     * in order to reduce the number of retrieval requests made to the database
     * and to improve response times.
     * </p>
     *
     * @param id the identifier of the user profile to retrieve
     * @return a <code>UserProfileDTO</code> representing the user profile
     *         with the specified identifier
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the database
     * @throws PersistenceException if retrieving the user profile from the
     *         database fails
     */
    public UserProfileDTO retrieveProfile(long id) throws PersistenceException {
        // Check if the user profile is in cache, and
        // if it is, return it.
        UserProfileDTO profile = (UserProfileDTO) cache.get(new Long(id));
        if (profile != null) {
            return profile;
        }

        // Otherwise, retrieve the user profile from the database.
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // Retrieve the player user profile. If no such player exists, try
            // an admin.
            if ((profile = retrievePlayer(id, connection)) != null) {

                // Retrieve the admin user profile. If no such admin exists, try
                // a sponsor.
            } else if ((profile = retrieveAdmin(id, connection)) != null) {

                // Retrieve the sponsor user profile. If no such player exists,
                // the retrieve failed.
            } else if ((profile = retrieveSponsor(id, connection)) != null) {

                // No such player, admin or sponsor found.
            } else {
                throw new EntryNotFoundException(
                        "No player, admin or sponsor was found in the database with ID, " + id, new Long(id));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to retrieve user profile with ID, " + id, e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        // Put the user profile in cache.
        cache.put(new Long(id), profile);

        return profile;
    }

    /**
     * <p>
     * Retrieves the player with the specified ID from the database, and returns
     * a user profile DTO containing the player information. If there are
     * contact information associated with the player, it is retrieved and put
     * into the DTO as well. If no such player was found in the database,
     * <code>null</code> is returned.
     * </p>
     *
     * @param id the ID of the player to retrieve
     * @param connection the connection to the database to use
     * @return a <code>UserProfileDTO</code> instance containing the player
     *         information, or <code>null</code> if no player with the
     *         specified ID exists in the database
     * @throws SQLException if retrieving the player information from the
     *         database fails
     */
    private UserProfileDTO retrievePlayer(long id, Connection connection) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(SELECT_PLAYER_SQL_STATEMENT);
        sql.setLong(1, id);
        ResultSet result = sql.executeQuery();

        UserProfileDTO player = null;

        // If player found.
        if (result.next()) {
            player = createPlayerUserProfileDTO(result);
        }

        sql.close();

        return player;
    }

    /**
     * <p>
     * Retrieves the admin with the specified ID from the database, and returns
     * a user profile DTO containing the admin information. If no such admin was
     * found in the database, <code>false</code> is returned. Otherwise,
     * <code>true</code> is returned.
     * </p>
     *
     * @param id the ID of the admin to retrieve
     * @param connection the connection to the database to use
     * @return a <code>UserProfileDTO</code> instance containing the admin
     *         information, or <code>null</code> if no admin with the
     *         specified ID exists in the database
     * @throws SQLException if retrieving the admin information from the
     *         database fails
     */
    private UserProfileDTO retrieveAdmin(long id, Connection connection) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(SELECT_ADMIN_SQL_STATEMENT);
        sql.setLong(1, id);
        ResultSet result = sql.executeQuery();

        UserProfileDTO admin = null;

        // If admin found.
        if (result.next()) {
            admin = createAdminUserProfileDTO(result);
        }

        sql.close();

        return admin;
    }

    /**
     * <p>
     * Retrieves the sponsor with the specified ID from the database, and
     * returns a user profile DTO containing the sponsor information. If there
     * are contact information associated with the sponsor, it is retrieved and
     * put into the DTO as well. If no such sponsor was found in the database,
     * <code>false</code> is returned. Otherwise, <code>true</code> is
     * returned.
     * </p>
     *
     * @param id the ID of the sponsor to retrieve
     * @param connection the connection to the database to use
     * @return a <code>UserProfileDTO</code> instance containing the sponsor
     *         information, or <code>null</code> if no sponsor with the
     *         specified ID exists in the database
     * @throws SQLException if retrieving the sponsor information from the
     *         database fails
     */
    private UserProfileDTO retrieveSponsor(long id, Connection connection) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(SELECT_SPONSOR_SQL_STATEMENT);
        sql.setLong(1, id);
        ResultSet result = sql.executeQuery();

        UserProfileDTO sponsor = null;

        // If sponsor found.
        if (result.next()) {
            sponsor = createSponsorUserProfileDTO(result);
        }

        sql.close();

        return sponsor;
    }

    /**
     * <p>
     * Deletes the user profile with the specified identifier from the database.
     * If no such user profile could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     * <p>
     * If the user profile is cached within this class, it is deleted from the
     * cache as well.
     * </p>
     *
     * @param id the identifier of the user profile to delete
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the database
     * @throws PersistenceException if deleting the user profile from the
     *         database fails
     */
    public void deleteProfile(long id) throws PersistenceException {
        // Remove the profile from cache.
        cache.remove(new Long(id));

        // Delete the user profile from database.
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // First check if the user profile is in the database.
            // If it is not, throw an EntryNotFoundException.
            if (!recordExists(id, "any_user", "id", connection)) {
                throw new EntryNotFoundException("No user profile with ID, " + id + ", was found in the database",
                        new Long(id));
            }

            // If the user is a player.
            if (recordExists(id, "player", "any_user_id", connection)) {
                deleteRecord(id, "player", "any_user_id", connection);
                deleteRecord(id, "any_user", "id", connection);

                // Delete the contact information if it exists.
                if (recordExists(id, "contact_info", "id", connection)) {
                    deleteRecord(id, "contact_info", "id", connection);
                }

                return;
            }

            // If the user is an admin.
            if (recordExists(id, "admin", "any_user_id", connection)) {
                deleteRecord(id, "admin", "any_user_id", connection);
                deleteRecord(id, "any_user", "id", connection);
                return;
            }

            // If the user is a sponsor.
            if (recordExists(id, "sponsor", "any_user_id", connection)) {
                deleteRecord(id, "sponsor", "any_user_id", connection);
                deleteRecord(id, "any_user", "id", connection);

                // Delete the contact information if it exists.
                if (recordExists(id, "contact_info", "id", connection)) {
                    deleteRecord(id, "contact_info", "id", connection);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to delete user profile with ID, " + id + ", from the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }
    }

    /**
     * <p>
     * Returns whether the record with the given ID exists in the specified
     * database table. The ID should appear in the specified primary key column.
     * </p>
     *
     * @param id the ID of the record to search for
     * @param tableName the name of the database table in which to search for
     *        the record
     * @param primaryKeyColumn the name of the column in which the ID appears
     * @param connection the database connection to use
     * @return <code>true</code> if the specified database table contains the
     *         record; <code>false</code> otherwise
     * @throws SQLException if searching for the record in the database fails
     */
    private boolean recordExists(long id, String tableName, String primaryKeyColumn, Connection connection)
            throws SQLException {
        PreparedStatement sql = connection.prepareStatement("SELECT " + primaryKeyColumn + " FROM " + tableName
                + " WHERE " + primaryKeyColumn + "=?");
        sql.setLong(1, id);

        boolean exists = sql.executeQuery().next();
        sql.close();

        return exists;
    }

    /**
     * <p>
     * Deletes the record with the given ID from the specified database table.
     * The ID should appear in the specified primary key column.
     * </p>
     *
     * @param id the ID of the record to delete from the database
     * @param tableName the name of the database table from which to delete the
     *        record
     * @param primaryKeyColumn the name of the column in which the ID appears
     * @param connection the database connection to use
     * @throws SQLException if deleting the record from the database fails
     */
    private void deleteRecord(long id, String tableName, String primaryKeyColumn, Connection connection)
            throws SQLException {
        String deleteStatement = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + "=?";
        PreparedStatement sql = connection.prepareStatement(deleteStatement);
        sql.setLong(1, id);
        sql.executeUpdate();
        sql.close();
    }

    /**
     * <p>
     * Retrieves all the user profiles from the database that match the given
     * criteria, and returns them in an array. A user profile must match all the
     * criteria for it to be included in the returned array. If no user profiles
     * match the criteria, an empty array is returned. If the given criteria map
     * is empty, all the user profiles are retrieved.
     * </p>
     * <p>
     * The keys in the criteria map must be one of the user profile property
     * constants defined in the {@link UserConstants} interface (those that are
     * not named <code>XXX_TYPE_NAME</code>). These property constants
     * correspond to table columns in the database. If a key is not equal to one
     * of the property constants, an <code>IllegalArgumentException</code> is
     * thrown. Note that this means that the user's email address, first name or
     * last name cannot be used as search criteria, because they do not
     * correspond to any property constants defined in the
     * <code>UserConstants</code> interface.
     * </p>
     * <p>
     * The values in the criteria map should be <code>String</code> instances,
     * because that is how this component stores data in its data transfer
     * objects. However, any <code>Object</code> instance compatible with the
     * corresponding database table column may be used. The criteria values are
     * compared to the corresponding table column fields, and, if equal, the
     * user profile containing those fields will be included in the returned
     * array. As such, the criteria map values may be <code>null</code> if the
     * database allows for <code>null</code> values in the corresponding table
     * column.
     * </p>
     * <p>
     * Examples on how to use this method are shown in the code snippet below.
     * </p>
     * <pre>
     * // Create the search criteria map.
     * Map criteria = new HashMap();
     *
     * // Find the user profiles of all the active users.
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, Boolean.TRUE);
     * UserProfileDTO[] profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the active players that have &quot;Wire transfer&quot;
     * // set as their payment preference.
     * criteria.put(UserConstants.PLAYER_PAYMENT_PREF, &quot;Wire transfer&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the non-active players and sponsors who live in
     * // Los Angeles, California.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, &quot;false&quot;);
     * criteria.put(UserConstants.ADDRESS_CITY, &quot;Los Angeles&quot;);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the approved sponsors in California.
     * criteria.clear();
     * criteria.put(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     *
     * // An empty criteria map retrieves all the user profiles.
     * criteria.clear();
     * profiles = findProfiles(criteria);
     *
     * // Assuming no user profile contains the handle, &quot;joeuser&quot;, the following
     * // will return an empty array.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_HANDLE, &quot;joeuser&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Results in an IllegalArgumentException being thrown, because &quot;InvalidCriteria&quot;
     * // does not correspond to a user profile property constant defined in the UserConstants
     * // interface.
     * criteria.put(&quot;InvalidCriteria&quot;, &quot;Some value&quot;);
     * profiles = findProfiles(criteria);
     * </pre>
     * <p>
     * Once the user profiles have been found, they are cached within this class
     * in order to reduce the number of retrieval requests made to the database
     * and to improve response times.
     * </p>
     *
     * @param criteria the criteria to use when searching for user profiles in
     *        the database to retrieve
     * @return a <code>UserProfileDTO[]</code> array containing all the user
     *         profiles in the database that match the given criteria; an empty
     *         array if there are no user profiles in the database that match
     *         the given criteria; or, if the criteria map is empty, a
     *         <code>UserProfileDTO[]</code> array containing all the user
     *         profiles in the database
     * @throws IllegalArgumentException if the criteria map is <code>null</code>,
     *         if it contains keys that are <code>null</code>, non-<code>String</code>
     *         instances or blank strings, of if the keys are not equal to one
     *         of the property constants defined in the {@link UserConstants}
     *         interface
     * @throws PersistenceException if retrieving the user profile from the
     *         database that match the given criteria fails
     * @see #retrieveProfile(long)
     * @see #retrieveAllProfiles()
     */
    public UserProfileDTO[] findProfiles(Map criteria) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNull(criteria, "search criteria map");

        // If the criteria map is empty, return all user profiles.
        if (criteria.isEmpty()) {
            return retrieveAllProfiles();
        }

        // Convert all the criteria map keys to the corresponding table column
        // names, and create a new criteria map from the "resolved" critera.
        Map resolvedCriteria = new HashMap();
        Set criteriaEntries = criteria.entrySet();
        Iterator itr = criteriaEntries.iterator();
        while (itr.hasNext()) {
            Map.Entry criterion = (Map.Entry) itr.next();
            Object key = criterion.getKey();

            // Check that the criterion key is not null or a non-String
            // instance.
            if (!(key instanceof String)) {
                throw new IllegalArgumentException("The criteria map cannot have null or non-String keys");
            }

            // Resolve the criterion key and put it and the corresponding value
            // in the resolved criteria map.
            String tableColumn = resolveCriterion((String) key);
            resolvedCriteria.put(tableColumn, criterion.getValue());
        }

        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);

        List matchingProfiles = new ArrayList();

        try {
            // Find the player profiles matching the criteria.
            findProfiles(resolvedCriteria, matchingProfiles, UserProfileDTO.PLAYER_KEY, connection);

            // Find the admin profiles matching the criteria.
            findProfiles(resolvedCriteria, matchingProfiles, UserProfileDTO.ADMIN_KEY, connection);

            // Find the sponsor profiles matching the criteria.
            findProfiles(resolvedCriteria, matchingProfiles, UserProfileDTO.SPONSOR_KEY, connection);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to find matching user profiles in the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        UserProfileDTO[] profiles = (UserProfileDTO[]) matchingProfiles.toArray(new UserProfileDTO[0]);

        // Put all the profiles found in cache.
        for (int i = 0; i < profiles.length; i++) {
            cache.put(extractUserProfileId(profiles[i]), profiles[i]);
        }

        return profiles;
    }

    /**
     * <p>
     * Returns the table column name corresponding to the given criterion. The
     * criterion is found in the criteria map given to the
     * {@link #findProfiles(Map)} method. It should be one of the user profile
     * property constants defined in the {@link UserConstants} interface. If the
     * criterion cannot be resolved to a table column name (i.e. if it is not
     * equal to one of the user profile constants defined in the
     * <code>UserConstants</code> interface), an
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param criterion the criterion to resolve to a table column name
     * @return the table column name corresponding to the given criterion
     * @throws IllegalArgumentException if the criterion cannot be resolved to a
     *         table column name
     */
    private String resolveCriterion(String criterion) {
        if (criterion.equals(UserConstants.PLAYER_PAYMENT_PREF)
                || criterion.endsWith(UserConstants.SPONSOR_PAYMENT_PREF)) {
            return "payment_pref";
        } else if (criterion.equals(UserConstants.SPONSOR_FAX_NUMBER)) {
            return "fax";
        } else if (criterion.equals(UserConstants.SPONSOR_APPROVED)) {
            return "is_approved";
        } else if (criterion.equals(UserConstants.ADDRESS_STREET_1)) {
            return "address_1";
        } else if (criterion.equals(UserConstants.ADDRESS_STREET_2)) {
            return "address_2";
        } else if (criterion.equals(UserConstants.ADDRESS_CITY)) {
            return "city";
        } else if (criterion.equals(UserConstants.ADDRESS_STATE)) {
            return "state";
        } else if (criterion.equals(UserConstants.ADDRESS_POSTAL_CODE)) {
            return "postal_code";
        } else if (criterion.equals(UserConstants.ADDRESS_PHONE_NUMBER)) {
            return "telephone";
        } else if (criterion.equals(UserConstants.CREDENTIALS_HANDLE)) {
            return "handle";
        } else if (criterion.equals(UserConstants.CREDENTIALS_PASSWORD)) {
            return "passwd";
        } else if (criterion.equals(UserConstants.CREDENTIALS_IS_ACTIVE)) {
            return "is_active";
        }

        // No table column mapping.
        throw new IllegalArgumentException("The criterion, '" + criterion
                + "', does not map to a table column in the database: "
                + "must be a property constant defined in UserConstants");
    }

    /**
     * <p>
     * Finds the user profiles in the database that match the given map of
     * criteria, and puts them in the given list. Note that the criteria map
     * keys do not correspond to UserConstants constants as in the
     * {@link #findProfiles(Map)} method. Rather, they should be the actual
     * table column names, after the criteria keys have been resolved using the
     * {@link #resolveCriterion(String)} method.
     * </p>
     *
     * @param criteria the search criteria to which the user profiles in the
     *        database should match
     * @param matchingProfiles the list to which the matching user profiles
     *        should be added
     * @param type the type of user profile to search for - either
     *        {@link UserConstants#PLAYER_TYPE_NAME},
     *        {@link UserConstants#ADMIN_TYPE_NAME} or
     *        {@link UserConstants#SPONSOR_TYPE_NAME}
     * @param connection the database connection to use
     * @throws SQLException if an error occurs while finding the user profiles
     *         in the database
     * @throws PersistenceException if the data type of a criteria value cannot
     *         be mapped to the data type of the corresponding table column
     */
    private void findProfiles(Map criteria, List matchingProfiles, String type, Connection connection)
            throws SQLException, PersistenceException {
        // Check that all the criteria correspond to a table column. If any
        // criterion does not match a table column, there is no point in doing
        // the search, as no profiles will match.
        if ((type.equals(UserProfileDTO.PLAYER_KEY) && !areValidPlayerCriteria(criteria))
                || (type.equals(UserProfileDTO.ADMIN_KEY) && !areValidAdminCriteria(criteria))
                || (!areValidSponsorCriteria(criteria))) {
            return;
        }

        // Use the query statement for the particular type of profile we are
        // searching for.
        String sqlStatement = null;
        if (type.equals(UserProfileDTO.PLAYER_KEY)) {
            sqlStatement = SELECT_ALL_PLAYERS_SQL_STATEMENT;
        } else if (type.equals(UserProfileDTO.ADMIN_KEY)) {
            sqlStatement = SELECT_ALL_ADMINS_SQL_STATEMENT;
        } else {
            sqlStatement = SELECT_ALL_SPONSORS_SQL_STATEMENT;
        }

        // Instead of retrieving ALL the profiles and then iterating the
        // ResultSet, a WHERE clause is simply appended to the query statement.
        // The DBMS then does the searching for us. This is far more
        // efficient, especially when the number of user profiles in the
        // database is large.
        Set criteriaEntries = criteria.entrySet();
        Iterator i = criteriaEntries.iterator();
        List sqlParams = new ArrayList();
        sqlStatement += " WHERE ";
        while (i.hasNext()) {
            Map.Entry criterion = (Map.Entry) i.next();
            if (criterion.getValue() != null) {
                // Append "xxx = ?", and save the criterion so that we can set
                // it as a parameter later.
                sqlStatement += criterion.getKey() + "=?";
                sqlParams.add(criterion);
            } else {
                // If the criterion value is null, append "xxx IS NULL".
                sqlStatement += criterion.getKey() + " IS NULL";
            }
            if (i.hasNext()) {
                sqlStatement += " AND ";
            }
        }

        // Set the criteria values as parameters in the WHERE clause of the SQL
        // statement created above.
        PreparedStatement sql = connection.prepareStatement(sqlStatement);
        i = sqlParams.iterator();
        for (int j = 1; i.hasNext(); j++) {
            Map.Entry criterion = (Map.Entry) i.next();
            String tableName = (String) criterion.getKey();
            Object criterionValue = criterion.getValue();

            if (criterionValue != null) {
                if (tableName.equals("is_active") || tableName.equals("is_approved")) {
                    // For the user.isactive and sponsor.is_approved columns,
                    // set the criterion value as a BIT or BOOLEAN.
                    sql.setBoolean(j, Boolean.valueOf(criterionValue.toString()).booleanValue());
                } else if (criterion.equals("fax")) {
                    // For the sponsor.fax column, we need a NUMERIC data type.
                    try {
                        sql.setDouble(j, Double.parseDouble(criterionValue.toString()));
                    } catch (NumberFormatException e) {
                        throw new PersistenceException("Could not map data type of the "
                                + UserConstants.SPONSOR_FAX_NUMBER + " criterion value to data type of the "
                                + tableName + " database table", e);
                    }
                } else {
                    sql.setObject(j, criterionValue);
                }
            }
        }

        // Execute the query. Create a UserProfile object from each row in the
        // ResultSet, and add it to the matching profiles list.
        ResultSet results = sql.executeQuery();
        while (results.next()) {
            UserProfileDTO profile = null;
            if (type.equals(UserProfileDTO.PLAYER_KEY)) {
                profile = createPlayerUserProfileDTO(results);
            } else if (type.equals(UserProfileDTO.ADMIN_KEY)) {
                profile = createAdminUserProfileDTO(results);
            } else {
                profile = createSponsorUserProfileDTO(results);
            }

            matchingProfiles.add(profile);
        }

        sql.close();
    }

    /**
     * <p>
     * Returns whether all the keys in the given map are names of columns in the
     * player, user or contact_info tables. This method is called when finding
     * user profiles in the database that match the given map of search
     * criteria.
     * </p>
     *
     * @param criteria the search criteria map
     * @return <code>true</code> if all the keys in the given map are names of
     *         columns in the player, user or contact_info tables;
     *         <code>false</code> otherwise
     */
    private boolean areValidPlayerCriteria(Map criteria) {
        Iterator i = criteria.keySet().iterator();
        while (i.hasNext()) {
            String criterion = (String) i.next();
            if (!isUserTableColumn(criterion) && !criterion.equals("payment_pref")
                    && !isContactInfoTableColumn(criterion)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Returns whether all the keys in the given map are names of columns in the
     * admin or user tables. This method is called when finding user profiles in
     * the database that match the given map of search criteria.
     * </p>
     *
     * @param criteria the search criteria map
     * @return <code>true</code> if all the keys in the given map are names of
     *         columns in the admin or user tables; <code>false</code>
     *         otherwise
     */
    private boolean areValidAdminCriteria(Map criteria) {
        Iterator i = criteria.keySet().iterator();
        while (i.hasNext()) {
            if (!isUserTableColumn((String) i.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Returns whether all the keys in the given map are names of columns in the
     * sponsor, user or contact_info tables. This method is called when finding
     * user profiles in the database that match the given map of search
     * criteria.
     * </p>
     *
     * @param criteria the search criteria map
     * @return <code>true</code> if all the keys in the given map are names of
     *         columns in the sponsor, user or contact_info tables;
     *         <code>false</code> otherwise
     */
    private boolean areValidSponsorCriteria(Map criteria) {
        Iterator i = criteria.keySet().iterator();
        while (i.hasNext()) {
            String criterion = (String) i.next();
            if (!isUserTableColumn(criterion) && !criterion.equals("fax") && !criterion.equals("payment_pref")
                    && !criterion.equals("is_approved") && !isContactInfoTableColumn(criterion)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Returns whether the given table column exists in the user table.
     * </p>
     *
     * @param tableColumn the table column to check for
     * @return <code>true</code> if the table column exists in the user table;
     *         <code>false</code> otherwise
     */
    private boolean isUserTableColumn(String tableColumn) {
        if (tableColumn.equals("handle") || tableColumn.equals("passwd") || tableColumn.equals("is_active")) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Returns whether the given table column exists in the contact_info table.
     * </p>
     *
     * @param tableColumn the table column to check for
     * @return <code>true</code> if the table column exists in the
     *         contact_info table; <code>false</code> otherwise
     */
    private boolean isContactInfoTableColumn(String tableColumn) {
        if (tableColumn.equals("address_1")
                || tableColumn.equals("address_2")
                || tableColumn.equals("city")
                || tableColumn.equals("state")
                || tableColumn.equals("postal_code")
                || tableColumn.equals("telephone")) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Retrieves all the user profiles from the database as an array. If no user
     * profiles exist in the database, an empty array is returned.
     * </p>
     * <p>
     * Once the user profiles have been retrieved, they are cached within this
     * class in order to reduce the number of retrieval requests made to the
     * database and to improve response times.
     * </p>
     *
     * @return a <code>UserProfileDTO[]</code> array containing all the user
     *         profiles in the database, or an empty array if there are no user
     *         profiles
     * @throws PersistenceException if retrieving all the user profiles from the
     *         database fails
     * @see #retrieveProfile(long)
     * @see #findProfiles(Map)
     */
    public UserProfileDTO[] retrieveAllProfiles() throws PersistenceException {
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);

        List profilesList = new ArrayList();

        try {
            retrieveAllPlayerProfiles(profilesList, connection);
            retrieveAllAdminProfiles(profilesList, connection);
            retrieveAllSponsorProfiles(profilesList, connection);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to retrieve all user profiles from the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        UserProfileDTO[] profiles = (UserProfileDTO[]) profilesList.toArray(new UserProfileDTO[0]);

        // Put all the user profiles in cache.
        for (int i = 0; i < profiles.length; i++) {
            cache.put(extractUserProfileId(profiles[i]), profiles[i]);
        }

        return profiles;
    }

    /**
     * <p>
     * Retrieves all the player user profiles from the database, and adds them
     * as <code>UserProfileDTO</code> instances to the given list.
     * </p>
     *
     * @param profilesList the list to which the player user profiles should be
     *        added
     * @param connection the database connection to use
     * @throws SQLException if retrieving all the player user profiles from the
     *         database fails
     */
    private void retrieveAllPlayerProfiles(List profilesList, Connection connection) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet results = sql.executeQuery(SELECT_ALL_PLAYERS_SQL_STATEMENT);
        while (results.next()) {
            profilesList.add(createPlayerUserProfileDTO(results));
        }
        sql.close();
    }

    /**
     * <p>
     * Retrieves all the admin user profiles from the database, and adds them as
     * <code>UserProfileDTO</code> instances to the given list.
     * </p>
     *
     * @param profilesList the list to which the admin user profiles should be
     *        added
     * @param connection the database connection to use
     * @throws SQLException if retrieving all the admin user profiles from the
     *         database fails
     */
    private void retrieveAllAdminProfiles(List profilesList, Connection connection) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet results = sql.executeQuery(SELECT_ALL_ADMINS_SQL_STATEMENT);
        while (results.next()) {
            profilesList.add(createAdminUserProfileDTO(results));
        }
        sql.close();
    }

    /**
     * <p>
     * Retrieves all the sponsor user profiles from the database, and adds them
     * as <code>UserProfileDTO</code> instances to the given list.
     * </p>
     *
     * @param profilesList the list to which the sponsor user profiles should be
     *        added
     * @param connection the database connection to use
     * @throws SQLException if retrieving all the sponsor user profiles from the
     *         database fails
     */
    private void retrieveAllSponsorProfiles(List profilesList, Connection connection) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet results = sql.executeQuery(SELECT_ALL_SPONSORS_SQL_STATEMENT);
        while (results.next()) {
            profilesList.add(createSponsorUserProfileDTO(results));
        }
        sql.close();
    }

    /**
     * <p>
     * Creates a <code>Player</code> object from the fields in the current row
     * of the given result set, and returns it in a <code>UserProfileDTO</code>
     * instance. If the result set contains contact information pertaining to
     * the player, then a <code>ContactInfo</code> object is created and put
     * in the <code>UserProfileDTO</code> instance as well.
     * </p>
     *
     * @param results the result set from which to create the
     *        <code>Player</code> object
     * @return a <code>UserProfileDTO</code> instance containing the
     *         <code>Player</code> object
     * @throws SQLException if creating the <code>Player</code> object from
     *         the given result set fails
     */
    private UserProfileDTO createPlayerUserProfileDTO(ResultSet results) throws SQLException {
        UserProfileDTO profile = new UserProfileDTO();

        // Create a Player object and put it in the user profile DTO.
        Player player = new Player(results.getLong(1));
        retrieveUserInfo(player, results);
        player.setPaymentPref(results.getString(results.findColumn("payment_pref")));
        profile.put(UserProfileDTO.PLAYER_KEY, player);

        // Retrieve the player's contact information if any.
        retrieveContactInfo(profile, results);

        return profile;
    }

    /**
     * <p>
     * Creates an <code>Admin</code> object from the fields in the current row
     * of the given result set, and returns it in a <code>UserProfileDTO</code>
     * instance.
     * </p>
     *
     * @param results the result set from which to create the <code>Admin</code>
     *        object
     * @return a <code>UserProfileDTO</code> instance containing the
     *         <code>Admin</code> object
     * @throws SQLException if creating the <code>Admin</code> object from the
     *         given result set fails
     */
    private UserProfileDTO createAdminUserProfileDTO(ResultSet results) throws SQLException {
        UserProfileDTO profile = new UserProfileDTO();

        // Create an Admin object and put it in the user profile DTO.
        Admin admin = new Admin(results.getLong(1));
        retrieveUserInfo(admin, results);
        profile.put(UserProfileDTO.ADMIN_KEY, admin);

        return profile;
    }

    /**
     * <p>
     * Creates a <code>Sponsor</code> object from the fields in the current
     * row of the given result set, and returns it in a
     * <code>UserProfileDTO</code> instance. If the result set contains
     * contact information pertaining to the sponsor, then a
     * <code>ContactInfo</code> object is created and put in the
     * <code>UserProfileDTO</code> instance as well.
     * </p>
     *
     * @param results the result set from which to create the
     *        <code>Sponsor</code> object
     * @return a <code>UserProfileDTO</code> instance containing the
     *         <code>Sponsor</code> object
     * @throws SQLException if creating the <code>Sponsor</code> object from
     *         the given result set fails
     */
    private UserProfileDTO createSponsorUserProfileDTO(ResultSet results) throws SQLException {
        UserProfileDTO profile = new UserProfileDTO();

        // Create a Sponsor object and put it in the user profile DTO.
        Sponsor sponsor = new Sponsor(results.getLong(1));
        retrieveUserInfo(sponsor, results);
        sponsor.setFax(results.getString(results.findColumn("fax")));
        sponsor.setPaymentPref(results.getString(results.findColumn("payment_pref")));

        // Be careful when getting the approved flag. It may be null.
        boolean approved = results.getBoolean(results.findColumn("is_approved"));
        if (!results.wasNull()) {
            sponsor.setApproved(String.valueOf(approved));
        } else {
            sponsor.setApproved(Sponsor.APPROVED_UNDECIDED);
        }

        profile.put(UserProfileDTO.SPONSOR_KEY, sponsor);

        // Retrieve the sponsor's contact information if any.
        retrieveContactInfo(profile, results);

        return profile;
    }

    /**
     * <p>
     * Retrieves the user information from the given result set and sets the
     * corresponding fields in the given <code>User</code> object.
     * </p>
     *
     * @param user the <code>User</code> object whose field should be set
     *        using the user information in the given result set
     * @param result the result set from which to retrieve the user information
     * @throws SQLException if retrieving the user information from the result
     *         set fails
     */
    private void retrieveUserInfo(User user, ResultSet result) throws SQLException {
        user.setHandle(result.getString(result.findColumn("handle")));
        user.setEmail(result.getString(result.findColumn("e_mail")));
        user.setPassword(result.getString(result.findColumn("passwd")));
        user.setActive(String.valueOf(result.getBoolean(result.findColumn("is_active"))));
    }

    /**
     * <p>
     * Retrieves the contact information from the given result set as a
     * <code>ContactInfo</code> object, and inserts it into the given user
     * profile DTO. If there are no contact information in the result set (i.e.
     * if the contact_info_id field is <code>null</code>), this method simply
     * returns.
     * </p>
     *
     * @param profile the user profile DTO into which the contact information
     *        will be put
     * @param result the result set from which to retrieve the contact
     *        information
     * @throws SQLException if retrieving the contact information from the
     *         result set fails
     */
    private void retrieveContactInfo(UserProfileDTO profile, ResultSet result) throws SQLException {
        long contactInfoId = result.getLong(result.findColumn("contact_info_id"));

        // If there are no contact information associated with the user (i.e. if
        // the contact_info_id field is null), simply return.
        if (result.wasNull()) {
            return;
        }

        // Create a ContactInfo object and put it in the user profile DTO.
        ContactInfo contactInfo = new ContactInfo(contactInfoId);
        contactInfo.setFirstName(result.getString(result.findColumn("first_name")));
        contactInfo.setLastName(result.getString(result.findColumn("last_name")));
        contactInfo.setAddress1(result.getString(result.findColumn("address_1")));
        contactInfo.setAddress2(result.getString(result.findColumn("address_2")));
        contactInfo.setCity(result.getString(result.findColumn("city")));
        contactInfo.setState(result.getString(result.findColumn("state")));
        contactInfo.setPostalCode(result.getString("postal_code"));
        contactInfo.setTelephone(result.getString("telephone"));
        profile.put(UserProfileDTO.CONTACT_INFO_KEY, contactInfo);
    }

    /**
     * <p>
     * Extracts and returns the user ID from the given user profile DTO. If no
     * <code>Player</code>, <code>Admin</code> or <code>Sponsor</code>
     * object is found in the DTO, an <code>IllegalArgumentException</code> is
     * thrown.
     * </p>
     *
     * @param profileDTO the user profile DTO from which the ID should be
     *        extracted
     * @return the user ID
     * @throws IllegalArgumentException if the DTO does not contain a
     *         <code>Player</code>, <code>Admin</code> or
     *         <code>Sponsor</code> object
     */
    private Long extractUserProfileId(UserProfileDTO profileDTO) {
        // Get the Player, Admin or Sponsor object.
        Object bean = null;
        if (profileDTO.contains("player")) {
            bean = profileDTO.get("player");
        } else if (profileDTO.contains("admin")) {
            bean = profileDTO.get("admin");
        } else if (profileDTO.contains("sponsor")) {
            bean = profileDTO.get("sponsor");
        }

        // No user information.
        if (!(bean instanceof User)) {
            throw new IllegalArgumentException(
                    "No player, admin or sponsor information was found in the given UserProfileDTO");
        }

        return new Long(((User) bean).getId());
    }

}
