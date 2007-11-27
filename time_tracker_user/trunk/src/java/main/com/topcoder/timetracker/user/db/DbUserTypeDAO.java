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
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeDAO;
import com.topcoder.timetracker.user.Util;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an implementation of the <code>UserTypeDAO</code> interface that utilizes a database with the schema
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
public class DbUserTypeDAO implements UserTypeDAO {

    /**
     * <p>
     * Represents the sql script for inserting a user type record.
     * </p>
     */
    private static final String INSERT_USER_TYPE_QUERY =
        "insert into user_type (user_type_id, company_id, description, active, creation_date, creation_user, "
            + "modification_date, modification_user) values (?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * Represents the sql script for updating a user type record.
     * </p>
     */
    private static final String UPDATE_USER_TYPE_QUERY =
        "update user_type set company_id = ?, description = ?, active = ?, creation_date = ?, creation_user = ?, "
            + "modification_date = ?, modification_user = ? where user_type_id = ?";

    /**
     * <p>
     * Represents the sql script for deleting a user type record.
     * </p>
     */
    private static final String DELETE_USER_TYPE_QUERY = "delete from user_type where user_type_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting a user type record.
     * </p>
     */
    private static final String SELECT_USER_TYPE_QUERY =
        "select user_type_id, description, active, company_id, creation_date, creation_user, modification_date, "
            + "modification_user from user_type where user_type_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting all user type records.
     * </p>
     */
    private static final String SELECT_ALL_USER_TYPES_QUERY =
        "select user_type_id, description, active, company_id, creation_date, creation_user, modification_date, "
            + "modification_user from user_type";

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
    private SearchBundle searchBundle;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a DbUserTypeDAO.
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
    public DbUserTypeDAO(DBConnectionFactory connFactory, String connName, String idGen,
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
     * Defines a set of user types to be recognized within the persistent store managed by this utility. A unique
     * user type id will automatically be generated and assigned to the user types.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values or the some of the user types are not valid.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserTypes(UserType[] userTypes) throws DataAccessException {
        checkUserTypes(userTypes);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(INSERT_USER_TYPE_QUERY);

            Throwable[] causes = new Throwable[userTypes.length];
            boolean success = true;

            for (int i = 0; i < userTypes.length; i++) {

                // checks that the user type doesn't have any id yet
                if (userTypes[i].getId() > 0) {
                    throw new IllegalArgumentException("The user type already has an id");
                }
                try {
                    // gets new id
                    long userTypeId = idGenerator.getNextID();
                    userTypes[i].setId(userTypeId);
                    DbUtil.fillPreparedStatement(stat, createInsertUserTypeParams(userTypes[i]));
                    DbUtil.executeUpdate(stat, -1, -1);

                    userTypes[i].setChanged(false);
                } catch (IDGenerationException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to add user types.", false);

        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserType parameter.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserTypes(UserType[] userTypes) throws DataAccessException {
        checkUserTypes(userTypes);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(UPDATE_USER_TYPE_QUERY);
            Throwable[] causes = new Throwable[userTypes.length];
            boolean success = true;

            for (int i = 0; i < userTypes.length; i++) {
                // checks that the user type already has an id
                if (userTypes[i].getId() <= 0) {
                    throw new IllegalArgumentException("userTypes contains user type with not positive id");
                }

                // only changed user types should be updated
                if (userTypes[i].isChanged()) {
                    try {
                        DbUtil.fillPreparedStatement(stat, createUpdateUserTypeParams(userTypes[i]));
                        DbUtil.executeUpdate(stat, userTypes[i].getId(), 1);

                        userTypes[i].setChanged(false);
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
     * Modifies the persistent store so that it no longer contains data on the user types with the specified ids.
     * </p>
     *
     * @param userTypeIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserTypes(long[] userTypeIds) throws DataAccessException {
        checkUserTypeIds(userTypeIds);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            stat = conn.prepareStatement(DELETE_USER_TYPE_QUERY);
            Throwable[] causes = new Throwable[userTypeIds.length];
            boolean success = true;

            for (int i = 0; i < userTypeIds.length; i++) {
                try {
                    // remove the user status
                    List params = new ArrayList();
                    params.add(new Long(userTypeIds[i]));
                    DbUtil.fillPreparedStatement(stat, params);
                    DbUtil.executeUpdate(stat, userTypeIds[i], 1);

                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                } catch (UnrecognizedEntityException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to remove user types.", false);
        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of UserType objects that reflects the data in the persistent store on the UserType with
     * the specified Ids.
     * </p>
     *
     * @param userTypeIds
     *            An array of userTypeIds for which user type should be retrieved.
     * @return The UserTypes corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserType[] getUserTypes(long[] userTypeIds) throws DataAccessException {
        checkUserTypeIds(userTypeIds);

        Connection conn = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, false);

            Throwable[] causes = new Throwable[userTypeIds.length];
            boolean success = true;

            UserType[] userTypes = new UserType[userTypeIds.length];
            for (int i = 0; i < userTypes.length; i++) {
                try {
                    userTypes[i] = getUserType(conn, userTypeIds[i]);
                } catch (DataAccessException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            if (!success) {
                throw new BatchOperationException("Failed to get user types.", causes);
            }

            return userTypes;
        } finally {
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the <code>UserType</code> instance from database for the given user id.
     * </p>
     * <p>
     * Note, if the user type id is cannot be found in the database, then UnrecognizedEntityException will be
     * thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userTypeId
     *            the user type id to find the user type
     * @return the <code>UserType</code> instance
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws UnrecognizedEntityException
     *             if the user type id cannot be located in the database
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    private UserType getUserType(Connection conn, long userTypeId) throws SQLException, DataAccessException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_USER_TYPE_QUERY);

            List params = new ArrayList();
            params.add(new Long(userTypeId));
            DbUtil.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return convertResultSetToUserType(rs);
            } else {
                throw new UnrecognizedEntityException(userTypeId);
            }

        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }

    }

    /**
     * <p>
     * Converts <code>ResultSet</code> to <code>UserType</code>.
     * </p>
     *
     * @param rs
     *            the given ResultSet
     * @return the user type
     * @throws SQLException
     *             if the result set is not compatible with UserType
     */
    private UserType convertResultSetToUserType(ResultSet rs) throws SQLException {
        UserType userType = new UserType();
        int pos = 1;
        userType.setId(rs.getLong(pos++));
        userType.setDescription(rs.getString(pos++));
        userType.setActive(rs.getInt(pos++) == 0 ? false : true);
        userType.setCompanyId(rs.getLong(pos++));
        userType.setCreationDate(rs.getDate(pos++));
        userType.setCreationUser(rs.getString(pos++));
        userType.setModificationDate(rs.getDate(pos++));
        userType.setModificationUser(rs.getString(pos++));

        userType.setChanged(false);

        return userType;
    }

    /**
     * <p>
     * Searches the persistent store for any user types that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the UserTypeFilterFactory or a composite Search Filters (such as AndFilter, OrFilter and NotFilter
     * from Search Builder component) that combines the filters created using UserTypeFilterFactory.
     * </p>
     *
     * @param filter
     *            The filter used to search for type.
     * @return The types satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] searchUserTypes(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) searchBundle.search(filter);

            UserType[] userTypes = new UserType[result.getRecordCount()];

            int i = 0;
            while (result.next()) {
                UserType userType = new UserType();
                userType.setId(result.getLong("user_type_id"));
                userType.setDescription(result.getString("description"));
                userType.setActive(result.getInt("active") == 0 ? false : true);
                userType.setCompanyId(result.getLong("company_id"));
                userType.setCreationUser(result.getString("creation_user"));
                userType.setCreationDate(result.getDate("creation_date"));
                userType.setModificationUser(result.getString("modification_user"));
                userType.setModificationDate(result.getDate("modification_date"));

                userTypes[i] = userType;
                i++;
            }

            return userTypes;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the users according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the users.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the UserTypes that are currently in the persistent store.
     * </p>
     *
     * @return An array of user types retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] getAllUserTypes() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection(connFactory, connName, false);
            pstmt = conn.prepareStatement(SELECT_ALL_USER_TYPES_QUERY);

            DbUtil.fillPreparedStatement(pstmt, null);

            rs = pstmt.executeQuery();

            List userTypeList = new ArrayList();
            while (rs.next()) {
                userTypeList.add(convertResultSetToUserType(rs));
            }

            UserType[] userTypes = (UserType[]) userTypeList.toArray(new UserType[userTypeList.size()]);

            return userTypes;
        } catch (SQLException e) {
            throw new DataAccessException("Can't retrieve all user types", e);
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given user type id array.
     * </p>
     *
     * @param userTypeIds
     *            the user type id array to check
     *
     * @throws IllegalArgumentException
     *             if userTypeIds is null, empty or contains values &lt;= 0.
     */
    private void checkUserTypeIds(long[] userTypeIds) {
        Util.checkNull(userTypeIds, "userTypeIds");

        if (userTypeIds.length == 0) {
            throw new IllegalArgumentException("The given user type ids array is empty.");
        }

        for (int i = 0; i < userTypeIds.length; i++) {
            if (userTypeIds[i] <= 0) {
                throw new IllegalArgumentException(
                    "The given user types id array contains non positive long value.");
            }
        }
    }

    /**
     * <p>
     * This method checks the given user types.
     * </p>
     *
     * @param userTypes
     *            the given user types to check
     *
     * @throws IllegalArgumentException
     *             if user types is null, empty or contains null values, or some user types contain null property
     *             which is required in the persistence
     */
    private void checkUserTypes(UserType[] userTypes) {
        Util.checkNull(userTypes, "userTypes");
        if (userTypes.length == 0) {
            throw new IllegalArgumentException("The given userTypes is empty.");
        }

        for (int i = 0; i < userTypes.length; i++) {
            Util.checkNull(userTypes[i], "userType in userTypes");

            // null description is not allowed
            if (userTypes[i].getDescription() == null) {
                throw new IllegalArgumentException("Some users have null description.");
            }

            // null company id is not allowed
            if (userTypes[i].getCompanyId() <= 0) {
                throw new IllegalArgumentException("Some users have non-positive company id.");
            }

            DbUtil.checkTimeTrackerBean(userTypes[i]);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a user type.
     * </p>
     *
     * @param userType
     *            the UserType to insert to database
     * @return the parameters needed to insert a user type.
     */
    private List createInsertUserTypeParams(UserType userType) {
        List params = new ArrayList();

        params.add(new Long(userType.getId()));
        prepareUserTypeParameters(userType, params);

        return params;
    }

    /**
     * <p>
     * This method creates the parameters needed to update a user type.
     * </p>
     *
     * @param userType
     *            the UserType to update to database
     * @return the parameters needed to update a user type.
     */
    private List createUpdateUserTypeParams(UserType userType) {
        List params = new ArrayList();

        prepareUserTypeParameters(userType, params);
        params.add(new Long(userType.getId()));

        return params;
    }

    /**
     * <p>
     * Prepares parameters for user type's query.
     * </p>
     *
     * @param userType
     *            the given UserType
     * @param params
     *            the in/out argument to which the parameters will be added
     */
    private void prepareUserTypeParameters(UserType userType, List params) {
        params.add(new Long(userType.getCompanyId()));
        params.add(userType.getDescription());
        params.add(userType.isActive() ? new Integer(1) : new Integer(0));
        DbUtil.prepareTimeTrackerBeanParams(params, userType);
    }

}
