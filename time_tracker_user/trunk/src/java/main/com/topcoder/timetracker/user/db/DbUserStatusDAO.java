/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusDAO;
import com.topcoder.timetracker.user.Util;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an implementation of the <code>UserStatusDAO</code> interface that utilizes a database with the schema
 * provided in the Requirements Section of Time Tracker User 3.2.1.
 * </p>
 * <p>
 * Thread Safety: This implementation is required to be thread safe outside of database access. No Transactions are
 * used since the Time Tracker Application will rely on Container Managed transactions.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class DbUserStatusDAO implements UserStatusDAO {

    /**
     * <p>
     * Represents the sql script for inserting a user status record.
     * </p>
     */
    private static final String INSERT_USER_STATUS_QUERY =
        "insert into user_status (user_status_id, company_id, description, active, creation_date, creation_user, "
            + "modification_date, modification_user) values (?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * Represents the sql script for updating a user status record.
     * </p>
     */
    private static final String UPDATE_USER_STATUS_QUERY =
        "update user_status set company_id = ?, description = ?, active = ?, creation_date = ?, creation_user = ?, "
            + "modification_date = ?, modification_user = ? where user_status_id = ?";

    /**
     * <p>
     * Represents the sql script for deleting a user status record.
     * </p>
     */
    private static final String DELETE_USER_STATUS_QUERY = "delete from user_status where user_status_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting a user status record.
     * </p>
     */
    private static final String SELECT_USER_STATUS_QUERY =
        "select user_status_id, description, active, company_id, creation_date, creation_user, modification_date, "
            + "modification_user from user_status where user_status_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting all user status records.
     * </p>
     */
    private static final String SELECT_ALL_USER_STATUSES_QUERY =
        "select user_status_id, description, active, company_id, creation_date, creation_user, modification_date, "
            + "modification_user from user_status";

    /**
     * <p>
     * This is the connection name that is provided to the connection factory when a connection is acquired. If not
     * specified, then the default connection is used.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: All methods of this class
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private final String connName;

    /**
     * <p>
     * This is the connection factory that is used to acquire a connection to the persistent store when it is
     * needed.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: All methods of this class.
     * </p>
     * <p>
     * Valid Values: Not null connection factory implementation.
     * </p>
     */
    private final DBConnectionFactory connFactory;

    /**
     * <p>
     * This is the id generator that is used to generate an id for any new Projects that are added to the
     * persistent store.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: addUserStatuses.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addUsers
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * This is the SearchBundle used to search the database.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: searchUserStatuses
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: searchUserStatuses
     * </p>
     * <p>
     * Valid Values: not null
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a DbUserStatusDAO.
     * </p>
     *
     * @param connFactory
     *            The connection factory to use.
     * @param connName
     *            The connection name to use (or null if the default connection should be used).
     * @param idGen
     *            The name of the id generator to use.
     * @param searchBundleManagerNamespace
     *            The name of the configuration namespace that contains definition of a search bundle to use with
     *            this DAO.
     * @param searchBundleName
     *            The name of search bundle to use to perform searches.
     * @throws IllegalArgumentException
     *             if any argument except for connName is null or if String parameter is an empty String.
     * @throws ConfigurationException
     *             if fails to create a <code>DatabaseSearchStrategy</code> instance using the given namespace or
     *             if it is failed to create the id generator or it fails to build search bundle manager
     */
    public DbUserStatusDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName) throws ConfigurationException {
        Util.checkNull(connFactory, "connFactory");

        if (connName != null) {
            Util.checkString(connName, "connName");
        }

        Util.checkString(idGen, "idGen");

        this.searchBundle = DbUtil.prepareSearchBundle(searchBundleManagerNamespace, searchBundleName);

        this.connFactory = connFactory;
        this.connName = connName;

        this.idGenerator = DbUtil.createIDGenerator(idGen);
    }

    /**
     * <p>
     * Defines a set of user statuses to be recognized within the persistent store managed by this utility. A
     * unique user status id will automatically be generated and assigned to the user statuses.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values, or some user statuses are not valid.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        checkUserStatuses(userStatuses);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(INSERT_USER_STATUS_QUERY);

            Throwable[] causes = new Throwable[userStatuses.length];
            boolean success = true;

            for (int i = 0; i < userStatuses.length; i++) {
                // checks that id is not set
                if (userStatuses[i].getId() > 0) {
                    throw new IllegalArgumentException("The user status already has an id");
                }
                try {
                    // gets new id
                    long userStatusId = idGenerator.getNextID();
                    userStatuses[i].setId(userStatusId);
                    DbUtil.fillPreparedStatement(stat, createInsertUserStatusParams(userStatuses[i]));
                    DbUtil.executeUpdate(stat, -1, -1);

                    userStatuses[i].setChanged(false);
                } catch (IDGenerationException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to add user statuses.", false);

        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserStatuses parameter.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values, or if some user statuses &lt;= 0
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        checkUserStatuses(userStatuses);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(UPDATE_USER_STATUS_QUERY);
            Throwable[] causes = new Throwable[userStatuses.length];
            boolean success = true;

            for (int i = 0; i < userStatuses.length; i++) {
                // checks that id is already set
                if (userStatuses[i].getId() <= 0) {
                    throw new IllegalArgumentException(
                        "userStatuses contains user status with not positive id");
                }
                // only if isChanged is true, update is done
                if (userStatuses[i].isChanged()) {
                    try {
                        DbUtil.fillPreparedStatement(stat, createUpdateUserStatusParams(userStatuses[i]));
                        DbUtil.executeUpdate(stat, userStatuses[i].getId(), 1);

                        userStatuses[i].setChanged(false);
                    } catch (SQLException e) {
                        causes[i] = e;
                        success = false;
                    } catch (UnrecognizedEntityException e) {
                        causes[i] = e;
                        success = false;
                    }
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to add user statuses.", false);

        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user status with the specified ids.
     * </p>
     *
     * @param userStatusIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException {
        checkUserStatusIds(userStatusIds);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(DELETE_USER_STATUS_QUERY);
            Throwable[] causes = new Throwable[userStatusIds.length];
            boolean success = true;

            for (int i = 0; i < userStatusIds.length; i++) {
                try {
                    // remove the user status
                    List params = new ArrayList();
                    params.add(new Long(userStatusIds[i]));
                    DbUtil.fillPreparedStatement(stat, params);
                    DbUtil.executeUpdate(stat, userStatusIds[i], 1);
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                } catch (UnrecognizedEntityException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to remove user statuses.", false);
        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of UserStatus objects that reflects the data in the persistent store on the UserStatus
     * with the specified Ids.
     * </p>
     *
     * @param userStatusIds
     *            An array of userStatusIds for which time status should be retrieved.
     * @return The UserStatuses corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException {
        checkUserStatusIds(userStatusIds);

        Connection conn = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);

            Throwable[] causes = new Throwable[userStatusIds.length];
            boolean success = true;

            UserStatus[] userStatuses = new UserStatus[userStatusIds.length];
            for (int i = 0; i < userStatuses.length; i++) {
                try {
                    userStatuses[i] = getUserStatus(conn, userStatusIds[i]);
                } catch (DataAccessException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            if (!success) {
                throw new BatchOperationException("Failed to get user statuses.", causes);
            }

            return userStatuses;
        } finally {
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the <code>UserStatus</code> instance from database for the given user id.
     * </p>
     * <p>
     * Note, if the user status id is cannot be found in the database, then UnrecognizedEntityException will be
     * thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userStatusId
     *            the user status id to find the user status
     * @return the <code>UserStatus</code> instance
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws UnrecognizedEntityException
     *             if the user status id cannot be located in the database
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    private UserStatus getUserStatus(Connection conn, long userStatusId) throws SQLException,
        DataAccessException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_USER_STATUS_QUERY);

            List params = new ArrayList();
            params.add(new Long(userStatusId));
            DbUtil.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return convertResultSetToUserStatus(rs);
            } else {
                throw new UnrecognizedEntityException(userStatusId);
            }

        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }

    }

    /**
     * <p>
     * Converts <code>ResultSet</code> to <code>UserStatus</code>.
     * </p>
     *
     * @param rs
     *            the given ResultSet
     * @return the user status
     * @throws SQLException
     *             if the result set is not compatible with UserStatus
     */
    private UserStatus convertResultSetToUserStatus(ResultSet rs) throws SQLException {
        UserStatus userStatus = new UserStatus();
        int pos = 1;
        userStatus.setId(rs.getLong(pos++));
        userStatus.setDescription(rs.getString(pos++));
        userStatus.setActive(rs.getInt(pos++) == 0 ? false : true);
        userStatus.setCompanyId(rs.getLong(pos++));
        userStatus.setCreationDate(rs.getDate(pos++));
        userStatus.setCreationUser(rs.getString(pos++));
        userStatus.setModificationDate(rs.getDate(pos++));
        userStatus.setModificationUser(rs.getString(pos++));

        userStatus.setChanged(false);

        return userStatus;
    }

    /**
     * <p>
     * Searches the persistent store for any user statuses that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the UserStatusFilterFactory or a composite Search Filters (such as AndFilter, OrFilter and NotFilter
     * from Search Builder component) that combines the filters created using UserStatusFilterFactory.
     * </p>
     *
     * @param filter
     *            The filter used to search for statuses.
     * @return The statuses satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] searchUserStatuses(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) searchBundle.search(filter);

            UserStatus[] userStatuses = new UserStatus[result.getRecordCount()];

            int i = 0;
            while (result.next()) {
                UserStatus userStatus = new UserStatus();
                userStatus.setId(result.getLong("user_status_id"));
                userStatus.setDescription(result.getString("description"));
                userStatus.setActive(result.getInt("active") == 0 ? false : true);
                userStatus.setCompanyId(result.getLong("company_id"));
                userStatus.setCreationUser(result.getString("creation_user"));
                userStatus.setCreationDate(result.getDate("creation_date"));
                userStatus.setModificationUser(result.getString("modification_user"));
                userStatus.setModificationDate(result.getDate("modification_date"));

                userStatuses[i] = userStatus;
                i++;
            }

            return userStatuses;
        } catch (SearchBuilderException e) {
            throw new DataAccessException(
                "Failed to search the user statuses according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the user statuses.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the UserStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of user statuses retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] getAllUserStatuses() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            pstmt = conn.prepareStatement(SELECT_ALL_USER_STATUSES_QUERY);

            DbUtil.fillPreparedStatement(pstmt, null);

            rs = pstmt.executeQuery();

            List userStatusList = new ArrayList();
            while (rs.next()) {
                userStatusList.add(convertResultSetToUserStatus(rs));
            }

            UserStatus[] userStatuses =
                (UserStatus[]) userStatusList.toArray(new UserStatus[userStatusList.size()]);

            return userStatuses;
        } catch (SQLException e) {
            throw new DataAccessException("Can't retrieve all user statuses", e);
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given user status id array.
     * </p>
     *
     * @param userStatusIds
     *            the user status id array to check
     *
     * @throws IllegalArgumentException
     *             if userStatusIds is null, empty or contains values &lt;= 0.
     */
    private void checkUserStatusIds(long[] userStatusIds) {
        Util.checkNull(userStatusIds, "userStatusIds");

        if (userStatusIds.length == 0) {
            throw new IllegalArgumentException("The given user status ids array is empty.");
        }

        for (int i = 0; i < userStatusIds.length; i++) {
            if (userStatusIds[i] <= 0) {
                throw new IllegalArgumentException(
                    "The given user status id array contains non positive long value.");
            }
        }
    }

    /**
     * <p>
     * This method checks the given user statuses.
     * </p>
     *
     * @param userStatuses
     *            the given user statuses to check
     *
     * @throws IllegalArgumentException
     *             if user statuses is null, empty or contains null values, or some user statuses contain null
     *             property which is required in the persistence
     */
    private void checkUserStatuses(UserStatus[] userStatuses) {
        Util.checkNull(userStatuses, "userStatuses");
        if (userStatuses.length == 0) {
            throw new IllegalArgumentException("The given userStatuses is empty.");
        }

        for (int i = 0; i < userStatuses.length; i++) {
            Util.checkNull(userStatuses[i], "userStatus in userStatuses");

            // null description is not allowed
            if (userStatuses[i].getDescription() == null) {
                throw new IllegalArgumentException("Some users have null description.");
            }

            // null company id is not allowed
            if (userStatuses[i].getCompanyId() <= 0) {
                throw new IllegalArgumentException("Some users have non-positive company id.");
            }

            DbUtil.checkTimeTrackerBean(userStatuses[i]);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a user status.
     * </p>
     *
     * @param userStatus
     *            the UserStatus to insert to database
     * @return the parameters needed to insert a user status.
     */
    private List createInsertUserStatusParams(UserStatus userStatus) {
        List params = new ArrayList();

        params.add(new Long(userStatus.getId()));
        prepareUserStatusParameters(userStatus, params);

        return params;
    }

    /**
     * <p>
     * This method creates the parameters needed to update a user status.
     * </p>
     *
     * @param userStatus
     *            the UserStatus to update to database
     * @return the parameters needed to update a user status.
     */
    private List createUpdateUserStatusParams(UserStatus userStatus) {
        List params = new ArrayList();

        prepareUserStatusParameters(userStatus, params);
        params.add(new Long(userStatus.getId()));

        return params;
    }

    /**
     * <p>
     * Prepares parameters for user status' query.
     * </p>
     *
     * @param userStatus
     *            the given UserStatus
     * @param params
     *            the in/out argument to which the parameters will be added
     */
    private void prepareUserStatusParameters(UserStatus userStatus, List params) {
        params.add(new Long(userStatus.getCompanyId()));
        params.add(userStatus.getDescription());
        params.add(userStatus.isActive() ? new Integer(1) : new Integer(0));
        DbUtil.prepareTimeTrackerBeanParams(params, userStatus);
    }
}
