/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

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

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.database.DatabaseSearchStringBuilder;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Database implementation of the RejectEmailDAO interface. It persisting and retrieving RejectEmail
 * information from the database. Create, Retrieve, Update, Delete and Enumerate (CRUDE) methods are provided.
 * There is also a search method that utilizes Filter classes from the Search Builder 1.2 component.
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
 */
public class DbRejectEmailDAO implements RejectEmailDAO {
    /**
     * The SQL query. Deletes the reject email.
     */
    private static final String DELETE_FROM_REJECT_EMAIL = "DELETE FROM reject_email WHERE reject_email_id = ?";

    /**
     *The SQL query. Deletes the reject email - company mapping.
     */
    private static final String DELETE_FROM_COMP_REJECT_EMAIL =
        "DELETE FROM comp_reject_email WHERE reject_email_id = ?";

    /**
     * The SQL query. Updates the reject email - company mapping.
     */
    private static final String UPDATE_COMP_REJECT_EMAIL = "UPDATE comp_reject_email SET company_id = ?, "
        + "modification_date = ?, modification_user = ? WHERE reject_email_id = ?";

    /**
     * The SQL query. Updates the reject email.
     */
    private static final String UPDATE_REJECT_EMAIL =
        "UPDATE reject_email SET reject_email.body = ?, reject_email.modification_date = ?, "
        + "reject_email.modification_user = ? WHERE reject_email_id = ?";

    /**
     * The SQL query. Inserts the reject email - company mapping.
     */
    private static final String INSERT_COMP_REJECT_EMAIL =
        "INSERT INTO comp_reject_email(reject_email_id, company_id, creation_date, creation_user, "
         + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The SQL query. Inserts the reject email.
     */
    private static final String INSERT_REJECT_EMAIL =
        "INSERT into reject_email(reject_email_id, body, creation_date, creation_user,  "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ? ,?)";

    /**
     * The base SQL query used in this class.
     */
    private static final String BASE_QUERY = "SELECT reject_email.reject_email_id, "
        + "reject_email.body, reject_email.creation_date, reject_email.creation_user, "
        + "reject_email.modification_date, reject_email.modification_user, comp_reject_email.company_id "
        + "FROM reject_email, comp_reject_email WHERE "
        + "comp_reject_email.reject_email_id = reject_email.reject_email_id ";

    /**
     * The base filter query. It extends NASE_QUERY by adding AND clause.
     */
    private static final String BASE_FILTER_QUERY = BASE_QUERY + " AND ";

    /**
     * <p>
     * This is the connection factory where from which the onnections to the data store will be retrieved.
     * It may not be null.
     * </p>
     * <p>
     * Initialized In: Constructor.
     * Modified In: Not Modified.
     * Accessed In: No Access.
     * </p>
     * <p>
     * Utilized In: createRejectEmail, retrieveRejectEmail, updateRejectEmail, deleteRejectEmail, listRejectEmails,
     * searchRejectEmails.
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
     * Utilized In: createRejectEmail, retrieveRejectEmail, updateRejectEmail, deleteRejectEmail, listRejectEmails,
     * searchRejectEmails,
     * </p>
     *
     */
    private String connectionName;

    /**
     * <p>
     * An instance of the id generator that is used to generate ids for the entities.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Utilized In: createRejectEmail, updateRejectEmail
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
     * Utilized In: searchRejectEmails
     * </p>
     *
     */
    private DatabaseSearchStringBuilder searchStringBuilder;

    /**
     * <p>
     * Constructor that accepts the connection factory, connection name, id generator name.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use when retrieving a connection from the connection factory.
     * @param idGenerator The Id Generator String name to use.
     * @throws IDGenerationException if ID Generator cannot be created.
     * @throws IllegalArgumentException if connFactory, connName or idGenerator is null or an empty String.
     */
    public DbRejectEmailDAO(DBConnectionFactory connFactory, String connName, String idGenerator)
            throws IDGenerationException {
        Utils.checkNull(connFactory, "connFactory");
        Utils.checkString(connName, "connName", false);
        Utils.checkString(idGenerator, "idGenerator", false);

        Map aliases = new HashMap();
        aliases.put(RejectEmailDAO.SEARCH_COMPANY_ID, "comp_reject_email.company_id");
        aliases.put(RejectEmailDAO.SEARCH_DESCRIPTION, "reject_email.description");
        aliases.put(RejectEmailDAO.SEARCH_CREATED_DATE, "reject_email.creation_date");
        aliases.put(RejectEmailDAO.SEARCH_CREATED_USER, "reject_email.creation_user");
        aliases.put(RejectEmailDAO.SEARCH_MODIFICATION_DATE, "reject_email.modification_date");
        aliases.put(RejectEmailDAO.SEARCH_MODIFICATION_USER, "reject_email.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);
        connectionFactory = connFactory;
        connectionName = connName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGenerator);
    }

    /**
     * <p>
     * Creates a datastore entry for the given Reject Email. An id is automatically generated by the DAO and
     * assigned to the Email. The RejectEmail is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same rejectEmail Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately.
     * @param rejectEmail The rejectEmail to create within the datastore.
     * @param username The username of the user responsible for creating the RejectEmail entry within the
     *        datastore.
     * @throws IllegalArgumentException if the rejectEmail or username is null, or if username is an empty String.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail createRejectEmail(RejectEmail rejectEmail, String username) throws RejectEmailDAOException {
        checkRejectEmail(rejectEmail);
        Utils.checkString(username, "username", false);
        // get the id
        long emailId = generateId(rejectEmail.getId());
        Connection conn = createConnection();
        try {
            // insert reject email
            insertRejectEmail(conn, rejectEmail, emailId, username);
            // insert reject email company mapping
            DBUtils.execute(conn, INSERT_COMP_REJECT_EMAIL, emailId, rejectEmail.getCompanyId(), username);

            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectEmailDAOException("Error occurs while creating email.", ex, rejectEmail);
        } finally {
            DBUtils.close(conn);
        }

        rejectEmail.setId(emailId);
        rejectEmail.setChanged(false);
        return rejectEmail;
    }

    /**
     * Checks if required email fields are set.
     *
     * @param rejectEmail the email to check.
     * @throws RejectEmailDAOException if any of required fields is not set.
     */
    private static void checkRejectEmail(RejectEmail rejectEmail) throws RejectEmailDAOException {
        Utils.checkNull(rejectEmail, "rejectEmail");

        if (rejectEmail.getBody() == null) {
            throw new RejectEmailDAOException("The reject email body should be set.", null, rejectEmail);
        }
    }

    /**
     * Inserts the reject email into the database.
     *
     * @param conn the database connection to be used.
     * @param email the email to persist.
     * @param emailId the email id.
     * @param username the creation username.
     * @throws SQLException if error occurs.
     */
    private static void insertRejectEmail(Connection conn, RejectEmail email, long emailId, String username)
            throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_REJECT_EMAIL);

            // set the id
            pstmt.setLong(1, emailId);
            pstmt.setString(2, email.getBody());
            // set creation dates
            Date now = DBUtils.initUserAndDates(pstmt, username, 3);
            // execute
            pstmt.executeUpdate();
            DBUtils.setCreationFields(email, username, now);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the database connection with auto commit set to false.
     *
     * @return the database connection.
     * @throws RejectEmailDAOException if connection cannot be created.
     */
    private Connection createConnection() throws RejectEmailDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new RejectEmailDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new RejectEmailDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws RejectEmailDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws RejectEmailDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new RejectEmailDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * <p>
     * Retrieves a RejectEmail from the datastore with the provided id. If no RejectEmail with that id exists, then
     * a null is returned.
     * </p>
     *
     * @return the RejectEmail for the id or <code>null</code>, if email not exists.
     * @param id The id of the RejectEmail to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail retrieveRejectEmail(long id) throws RejectEmailDAOException {
        Utils.checkPositive(id, "id");

        // create filter
        Filter filter = new EqualToFilter("reject_email.reject_email_id", new Long(id));

        // execute search
        RejectEmail[] result = searchRejectEmails(filter);
        // should be only one or zero, but return the first - just in case
        if (result.length > 0) {
            return result[0];
        }

        return null;
    }

    /**
     * Creates <code>RejectEmail</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the RejectEmail created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private static RejectEmail populateRejectEmail(ResultSet rs) throws SQLException {
        RejectEmail email = new RejectEmail();
        email.setId(rs.getLong("reject_email_id"));
        email.setBody(rs.getString("body"));
        email.setCompanyId(rs.getLong("company_id"));
        email.setModificationUser(rs.getString("modification_user"));
        email.setCreationUser(rs.getString("creation_user"));
        email.setModificationDate(rs.getTimestamp("modification_date"));
        email.setCreationDate(rs.getTimestamp("creation_date"));

        return email;
    }

    /**
     * <p>
     * Updates the given RejectEmail in the data store. The RejectEmail is considered to have been modified by the
     * specified username.
     * </p>
     *
     * @param rejectEmail The RejectEmail entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the rejectEmail is null, or the username is null, or the username is an
     *         empty String.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     * @throws RejectEmailNotFoundException if the RejectEmail to update was not found in the data store.
     */
    public void updateRejectEmail(RejectEmail rejectEmail, String username) throws RejectEmailDAOException {
        checkRejectEmail(rejectEmail);
        Utils.checkString(username, "username", false);

        // if was not changed - just return
        if (!rejectEmail.isChanged()) {
            return;
        }

        // get connection
        Connection conn = createConnection();
        try {
            // update email
            int result = updateEmail(conn, rejectEmail, username);
            // if noting was updated - email not exists - throw exception
            if (result == 0) {
                DBUtils.rollback(conn);
                throw new RejectEmailNotFoundException("Reject email not exists.", null, rejectEmail);
            }

            // update mapping
            DBUtils.executeUpdate(conn, UPDATE_COMP_REJECT_EMAIL,
                    rejectEmail.getCompanyId(), rejectEmail.getId(), username);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectEmailDAOException("Error occur while updating email.", ex, rejectEmail);
        } finally {
            DBUtils.close(conn);
        }

        // is updated, set flag to false.
        rejectEmail.setChanged(false);
    }

    /**
     * Updates the given RejectEmail in the database.
     *
     * @param conn the database connection to be used.
     * @param email the email to be updated.
     * @param username the update user name.
     * @return the number of affected rows.
     * @throws SQLException if any error occur.
     */
    private static int updateEmail(Connection conn, RejectEmail email, String username) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            // prepare update query
            pstmt = conn.prepareStatement(UPDATE_REJECT_EMAIL);

            // set variables
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setString(1, email.getBody());
            pstmt.setTimestamp(2, time);
            pstmt.setString(3, username);
            pstmt.setLong(4, email.getId());

            int result = pstmt.executeUpdate();
            // update the object status
            DBUtils.setModificationFields(email, username, time);
            return result;
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * <p>
     * Removes the specified RejectEmail from the data store.
     * </p>
     *
     * @param rejectEmail The rejectEmail to delete.
     * @throws IllegalArgumentException if the rejectEmail is null.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     * @throws RejectEmailNotFoundException if the RejectEmail to delete was not found in the data store.
     */
    public void deleteRejectEmail(RejectEmail rejectEmail) throws RejectEmailDAOException,
        RejectEmailNotFoundException {

        Utils.checkNull(rejectEmail, "rejectEmail");

        Connection conn = createConnection();
        try {
            // delete company - email mapping first
            DBUtils.executeDelete(conn, DELETE_FROM_COMP_REJECT_EMAIL, rejectEmail.getId());

            // delete email
            int count = DBUtils.executeDelete(conn, DELETE_FROM_REJECT_EMAIL, rejectEmail.getId());
            if (count == 0) {
                DBUtils.rollback(conn);
                throw new RejectEmailNotFoundException("No email exists.", null, rejectEmail);
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectEmailDAOException("Error occur while retrieving the reject email.", ex, rejectEmail);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Enumerates all the RejectEmails that are present within the data store.
     * </p>
     *
     * @return A list of all the RejectEmails within the data store.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail[] listRejectEmails() throws RejectEmailDAOException {
        return listRejectEmails(BASE_QUERY, false);
    }

    /**
     * <p>
     * Returns a list of all the RejectReasons within the datastore that satisfy the filters that are provided. The
     * filters are defined using classes from the Search Builder v1.2 component and com.cronos.timetracker.
     * common.search package.
     * </p>
     * <p>
     * Implementation Notes: - Create a new AND filter that is a conjunction of the baseFilter and the filter
     * parameter. - Use the searchBundle to conduct a search with the built AND filter. - Cast the resulting object
     * to a CustomResultSet. - Build the resulting RejectEmail objects similar to how it would be built from
     * retrieveRejectEmail ResultSet.
     * </p>
     *
     *
     *
     * @return A list of RejectReasons that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail[] searchRejectEmails(Filter filter) throws RejectEmailDAOException {
        Utils.checkNull(filter, "filter");

        // since searchStringBuilder is shared between methods, it must be synchronized
        // to make class thread safe
        synchronized(searchStringBuilder) {
            return listRejectEmails(searchStringBuilder.buildSearchString(filter), true);
        }
    }

    /**
     * Returns the array of reject email that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of rejected email found.
     * @throws RejectEmailDAOException if any error occurs.
     */
    private RejectEmail[] listRejectEmails(String query, boolean fillStatement) throws RejectEmailDAOException {
        Connection conn = createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the query and fill if required.
            pstmt = conn.prepareStatement(query);
            if (fillStatement) {
                DBUtils.fillStatement(pstmt, searchStringBuilder.getValues());
            }

            // execute
            rs = pstmt.executeQuery();
            List result = new ArrayList();
            // create list
            while (rs.next()) {
                result.add(populateRejectEmail(rs));
            }

            // return as array/
            return (RejectEmail[]) result.toArray(new RejectEmail[result.size()]);
        } catch (SQLException ex) {
            throw new RejectEmailDAOException("Error occur while retrieving the reject email.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }
}
