/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

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
 * Database implementation of the RejectReasonDAO interface. It persisting and retrieving the
 * RejectReason information from the database. Create, Retrieve, Update, Delete and Enumerate (CRUDE) methods are
 * provided. There is also a search method that utilizes Filter classes from the Search Builder 1.2 component.
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
public class DbRejectReasonDAO implements RejectReasonDAO {

    /**
     * The delete sql query.
     */
    private static final String DELETE_REJECT_REASON =
        "DELETE FROM reject_reason WHERE reject_reason.reject_reason_id = ?";

    /**
     * The delete SQL query.
     */
    private static final String DELETE_COMP_REJ_REASON =
        "DELETE FROM comp_rej_reason WHERE comp_rej_reason.reject_reason_id = ?";

    /**
     * The update SQL query.
     */
    private static final String UPDATE_COMP_REJ_REASON =
        "UPDATE comp_rej_reason SET comp_rej_reason.company_id = ?, comp_rej_reason.modification_date = ?, "
        + "comp_rej_reason.modification_user = ? WHERE comp_rej_reason.reject_reason_id = ?";

    /**
     * The update SQL query.
     */
    private static final String UPDATE_REJECT_REASON =
        "UPDATE reject_reason SET reject_reason.description = ?, reject_reason.active = ?, "
        + "reject_reason.modification_date = ?, reject_reason.modification_user = ? "
        + "WHERE reject_reason.reject_reason_id = ?";

    /**
     * The insert sql query.
     */
    private static final String INSERT_COMP_REJ_REASON =
        "INSERT into comp_rej_reason(reject_reason_id, company_id, creation_date, creation_user, "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The insert sql query.
     */
    private static final String INSERT_REJECT_REASON =
        "INSERT INTO reject_reason(reject_reason_id, description, active, creation_date, creation_user,  "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ? ,?, ?)";

    /**
     * The main select query. It is use a as base for the filters.
     */
    private static final String BASE_QUERY =
        "SELECT reject_reason.reject_reason_id, reject_reason.description, reject_reason.active, "
        + "reject_reason.creation_date, reject_reason.creation_user, reject_reason.modification_date, "
        + "reject_reason.modification_user, comp_rej_reason.company_id FROM reject_reason, comp_rej_reason "
        + "WHERE comp_rej_reason.reject_reason_id = reject_reason.reject_reason_id ";

    /**
     * The base filter query.
     */
    private static final String BASE_FILTER_QUERY = BASE_QUERY + " AND ";

    /**
     * <p>
     * This is the connection factory where which the connections to the data store will be retrieved.
     * It may not be null.
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
     * Utilized In: createRejectReason, retrieveRejectReason, updateRejectReason, deleteRejectReason,
     * listRejectReasons, searchRejectReasons.
     * </p>
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
     * Utilized In: createRejectReason, retrieveRejectReason, updateRejectReason, deleteRejectReason,
     * listRejectReasons, searchRejectReasons,
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
     * Utilized In: createRejectReason, updateRejectReason
     * </p>
     *
     */
    private IDGenerator idGenerator;

    /**
     * <p>
     * This is the search builder that is used to perform the searches based on any provided filters. It is
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
     * Utilized In: searchRejectReasons
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
     * @throws IllegalArgumentException if connFactory, connName or idGenerator is null or an empty String.
     * @throws IDGenerationException if ID Generator cannot be created.
     */
    public DbRejectReasonDAO(DBConnectionFactory connFactory, String connName, String idGenerator)
            throws IDGenerationException {
        Utils.checkNull(connFactory, "connFactory");
        Utils.checkString(connName, "connName", false);
        Utils.checkString(idGenerator, "idGenerator", false);

        Map aliases = new HashMap();
        aliases.put(RejectReasonDAO.SEARCH_COMPANY_ID, "comp_rej_reason.company_id");
        aliases.put(RejectReasonDAO.SEARCH_DESCRIPTION, "reject_reason.description");
        aliases.put(RejectReasonDAO.SEARCH_ACTIVE_STATUS, "reject_reason.active");
        aliases.put(RejectReasonDAO.SEARCH_CREATED_USER, "reject_reason.creation_user");
        aliases.put(RejectReasonDAO.SEARCH_CREATED_DATE, "reject_reason.creation_date");
        aliases.put(RejectReasonDAO.SEARCH_MODIFICATION_DATE, "reject_reason.modification_date");
        aliases.put(RejectReasonDAO.SEARCH_MODIFICATION_USER, "reject_reason.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);
        connectionFactory = connFactory;
        connectionName = connName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGenerator);
    }

    /**
     * <p>
     * Creates a datastore entry for the given Reject Reason. An id is automatically generated by the DAO and
     * assigned to the reason. The RejectReason is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same rejectReason Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately.
     * @param rejectReason The rejectReason to create within the datastore.
     * @param username The username of the user responsible for creating the RejectReason entry within the
     *        datastore.
     * @throws IllegalArgumentException if the rejectReason or username is null, or if username is an empty String.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason createRejectReason(RejectReason rejectReason, String username)
            throws RejectReasonDAOException {
        checkRejectReason(rejectReason);
        Utils.checkString(username, "username", false);

        long rejectId = generateId(rejectReason.getId());
        Connection conn = createConnection();
        try {
            insertRejectReason(conn, rejectReason, rejectId, username);
            DBUtils.execute(conn, INSERT_COMP_REJ_REASON, rejectId, rejectReason.getCompanyId(), username);

            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectReasonDAOException("Error occurs while creating email.", ex, rejectReason);
        } finally {
            DBUtils.close(conn);
        }

        rejectReason.setId(rejectId);
        rejectReason.setChanged(false);
        return rejectReason;
    }

    /**
     * Check if all requires fields are set.
     *
     * @param rejectReason the reject reason to check.
     * @throws RejectReasonDAOException if any value is not set.
     */
    private static void checkRejectReason(RejectReason rejectReason) throws RejectReasonDAOException {
        Utils.checkNull(rejectReason, "rejectReason");

        if (rejectReason.getDescription() == null) {
            throw new RejectReasonDAOException("The description should be set.", null, rejectReason);
        }
    }

    /**
     * Inserts the reject reason into table.
     *
     * @param conn the database connection.
     * @param reason the reject reason to persist.
     * @param reasonId the reason id.
     * @param username the creation user name.
     * @throws SQLException if any error occurs while database operation.
     */
    private static final void insertRejectReason(Connection conn, RejectReason reason, long reasonId,
            String username) throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_REJECT_REASON);
            // set variables.
            pstmt.setLong(1, reasonId);
            pstmt.setString(2, reason.getDescription());
            pstmt.setInt(3, reason.isActive() ? 1 : 0);

            // set dates
            Date now = DBUtils.initUserAndDates(pstmt, username, 4);
            pstmt.executeUpdate();
            DBUtils.setCreationFields(reason, username, now);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the database connection with auto commit set to false.
     *
     * @return the database connection.
     * @throws RejectReasonDAOException if connection cannot be created.
     */
    private Connection createConnection() throws RejectReasonDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
 //           conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new RejectReasonDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new RejectReasonDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws RejectReasonDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws RejectReasonDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new RejectReasonDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * <p>
     * Retrieves a RejectReason from the datastore with the provided id. If no RejectReason with that id exists,
     * then a null is returned.
     * </p>
     *
     * @return the RejectReason with given id od <code>null</code>, if not found.
     * @param id The id of the RejectReason to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason retrieveRejectReason(long id) throws RejectReasonDAOException {
        Utils.checkPositive(id, "id");

        // create search filter and propagate to search method
        Filter filter = new EqualToFilter("reject_reason.reject_reason_id", new Long(id));
        RejectReason[] result = searchRejectReasons(filter);
        // if is something, return the 1st element
        if (result.length > 0) {
            return result[0];
        }

        return null;
    }

    /**
     * Creates <code>RejectReason</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the RejectReason created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private static RejectReason populateRejectReason(ResultSet rs) throws SQLException {
        RejectReason reason = new RejectReason();
        reason.setId(rs.getLong("reject_reason_id"));
        reason.setDescription(rs.getString("description"));
        reason.setActive(rs.getInt("active") == 1);
        reason.setCompanyId(rs.getLong("company_id"));

        reason.setModificationUser(rs.getString("modification_user"));
        reason.setCreationUser(rs.getString("creation_user"));
        reason.setModificationDate(rs.getTimestamp("modification_date"));
        reason.setCreationDate(rs.getTimestamp("creation_date"));
        return reason;
    }

    /**
     * <p>
     * Updates the given RejectReason in the data store. The RejectReason is considered to have been modified by
     * the specified username.
     * </p>
     *
     * @param rejectReason The RejectReason entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the rejectReason is null, or the username is null, or the username is an
     *         empty String.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     * @throws RejectReasonNotFoundException if the RejectReason to update was not found in the data store.
     */
    public void updateRejectReason(RejectReason rejectReason, String username) throws RejectReasonDAOException {
        checkRejectReason(rejectReason);
        Utils.checkString(username, "username", false);

        // check if there is anything to update
        if (!rejectReason.isChanged()) {
            return;
        }

        // get connection
        Connection conn = createConnection();
        try {
            int result = updateReason(conn, rejectReason, username);
            if (result == 0) {
                DBUtils.rollback(conn);
                throw new RejectReasonNotFoundException("Reject reason not exists.", null, rejectReason);
            }

            // update reason - company mapping
            DBUtils.executeUpdate(conn, UPDATE_COMP_REJ_REASON, rejectReason.getCompanyId(),
                    rejectReason.getId(), username);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectReasonNotFoundException("Error occur while updating email.", ex, rejectReason);
        } finally {
            DBUtils.close(conn);
        }

        rejectReason.setChanged(false);
    }

    /**
     * Updates the RejectReason in the persistence.
     *
     * @param conn the connection to be used.
     * @param rejectReason the reject reason to update.
     * @param username the update username.
     * @return the number of affected rows.
     * @throws SQLException if any database error occurs.
     */
    private static int updateReason(Connection conn, RejectReason rejectReason, String username)
            throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(UPDATE_REJECT_REASON);

            pstmt.setString(1, rejectReason.getDescription());
            pstmt.setInt(2, rejectReason.isActive() ? 1 : 0);

            // set times
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(3, time);
            pstmt.setString(4, username);
            pstmt.setLong(5, rejectReason.getId());

            // set modification data
            int result = pstmt.executeUpdate();
            DBUtils.setModificationFields(rejectReason, username, time);
            return result;
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * <p>
     * Removes the specified RejectReason from the data store.
     * </p>
     *
     * @param rejectReason The rejectReason to delete.
     * @throws IllegalArgumentException if the rejectReason is null.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     * @throws RejectReasonNotFoundException if the RejectReason to delete was not found in the data store.
     */
    public void deleteRejectReason(RejectReason rejectReason) throws RejectReasonDAOException {
        Utils.checkNull(rejectReason, "rejectReason");

        Connection conn = createConnection();
        try {

            DBUtils.executeDelete(conn, DELETE_COMP_REJ_REASON, rejectReason.getId());
            int count = DBUtils.executeDelete(conn, DELETE_REJECT_REASON, rejectReason.getId());
            if (count == 0) {
                DBUtils.rollback(conn);
                throw new RejectReasonNotFoundException("No reject reason exists.", null, rejectReason);
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RejectReasonDAOException("Error occur while deleting the reject reason.", ex, rejectReason);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Enumerates all the RejectReasons that are present within the data store.
     * </p>
     *
     * @return A list of all the RejectReasons within the data store.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason[] listRejectReasons() throws RejectReasonDAOException {
        return listRejectReasons(BASE_QUERY, false);
    }

    /**
     * <p>
     * Returns a list of all the RejectReasons within the datastore that satisfy the filters that are provided. The
     * filters are defined using classes from the Search Builder v1.2 component and com.topcoder.timetracker.
     * common.search package.
     * </p>
     *
     * @return A list of RejectReasons that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search.
     * @throws IllegalArgumentException if the filter is null.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason[] searchRejectReasons(Filter filter) throws RejectReasonDAOException {
        Utils.checkNull(filter, "filter");

        // synchronize the access to made the class thread safe
        synchronized (searchStringBuilder) {
            return listRejectReasons(searchStringBuilder.buildSearchString(filter), true);
        }
    }

    /**
     * Returns the array of reject reasons that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of rejected reasons found.
     * @throws RejectReasonDAOException if any error occurs.
     */
    private RejectReason[] listRejectReasons(String query, boolean fillStatement) throws RejectReasonDAOException {
        Connection conn = createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the statement and fill if required
            pstmt = conn.prepareStatement(query);
            if (fillStatement) {
                DBUtils.fillStatement(pstmt, searchStringBuilder.getValues());
            }

            // execute query
            rs = pstmt.executeQuery();
            List result = new ArrayList();
            // create reasons
            while (rs.next()) {
                result.add(populateRejectReason(rs));
            }

            return (RejectReason[]) result.toArray(new RejectReason[result.size()]);
        } catch (SQLException ex) {
            throw new RejectReasonDAOException("Error occur while retrieving the reject email.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }
}
