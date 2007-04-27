/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.PersistenceOperationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.InvalidFilterException;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingStatusFilterFactory;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * This is an implementation of the <code>FixedBillingStatusDAO</code> interface that utilizes a database with the
 * schema provided in the Requirements Section of Time Tracker Fixed Billing Entry 1.0.
 * </p>
 *
 * <p>
 * Thread Safety: This implementation relies on Container-Managed Transactions to maintain thread-safety. All its state
 * is initialized during construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class DbFixedBillingStatusDAO extends BaseDAO implements FixedBillingStatusDAO {
    /** The SQL String for insert. */
    private static final String INSERT_STATUS_SQL = "insert into fix_bill_status(fix_bill_status_id, description,"
        + "creation_date,creation_user,modification_date,modification_user" + ") values (?, ?, ?, ?, ?, ?)";

    /** The SQL String for update. */
    private static final String UPDATE_STATUS_SQL = "update fix_bill_status set description=?, creation_date=?,"
        + "creation_user=?,modification_date=?,modification_user=? where fix_bill_status_id=?";

    /** The SQL String for delete. */
    private static final String DELETE_STATUS_SQL = "delete from fix_bill_status where fix_bill_status_id in (";

    /** The SQL String for select. */
    private static final String SELECT_STATUS_SQL = "select * from fix_bill_status where fix_bill_status_id in (";

    /** The SQL String for select all status. */
    private static final String SELECT_ALL_STATUS_SQL = "select * from fix_bill_status";

    /** The SQL String for select only one status. */
    private static final String SELECT_SINGLE_STATUS_SQL =
        "select * from fix_bill_status where fix_bill_status_id = ?";

    /** The SQL field String for id field. */
    private static final String STATUS_ID_FIELD = "fix_bill_status_id";

    /** The SQL field String for description field. */
    private static final String DESCRIPTION_FIELD = "description";

    /** The SQL field String for creation date field. */
    private static final String CREATION_DATE_FIELD = "creation_date";

    /** The SQL field String for creation user field. */
    private static final String CREATION_USER_FIELD = "creation_user";

    /** The SQL field String for modification user field. */
    private static final String MODIFICATION_DATE_FIELD = "modification_date";

    /** The SQL field String for modification user field. */
    private static final String MODIFICATION_USER_FIELD = "modification_user";

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for searching the datastore for
     * FixedBillingStatuses using this implementation.  It will be initialized in the constructor with the parameters
     * depending on the search query used by the developer.
     * </p>
     */
    private final MappedFixedBillingStatusFilterFactory filterFactory;

    /**
     * <p>
     * Constructor accepting the necessary parameters for this implementation to function properly.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use. If none is provided, then default is used.
     * @param idGen The id generator name to use.
     * @param searchBundleNamespace The search bundle namespace used to initialize the search strategy.
     *
     * @throws IllegalArgumentException if any argument is null, except for connName, or if any String argument is an
     *         empty String.
     * @throws ConfigurationException if has any configuration errors.
     */
    public DbFixedBillingStatusDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleNamespace) throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleNamespace, "StatusSearchBundle", null);
        //The idGen and searchBundleNamespace should not be null.
        Helper.checkNull("idGen", idGen);
        Helper.checkNull("searchBundleNamespace", searchBundleNamespace);

        this.filterFactory = new MappedFixedBillingStatusFilterFactory();
    }

    /**
     * <p>
     * Defines a set of FixedBillingStatuses to be recognized within the persistent store managed by this utility. A
     * unique status id will automatically be generated and assigned to the statuses. Note that the status' changed
     * flag will set to false.
     * </p>
     *
     * <p>
     * The implementation will set the status' creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void createFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        checkStatuses(statuses, false);

        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();
            state = conn.prepareStatement(INSERT_STATUS_SQL);

            for (int i = 0; i < statuses.length; i++) {
                statuses[i].setId(getNextId());
                int index = 0;
                state.setLong(++index, statuses[i].getId());
                state.setString(++index, statuses[i].getDescription());
                state.setTimestamp(++index, new Timestamp(statuses[i].getCreationDate().getTime()));
                state.setString(++index, statuses[i].getCreationUser());
                state.setTimestamp(++index, new Timestamp(statuses[i].getModificationDate().getTime()));
                state.setString(++index, statuses[i].getModificationUser());

                state.executeUpdate();

                //If update success, set the changed flag to false.
                statuses[i].setChanged(false);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to insert the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * Check the given FixedBillingStatus array.
     *
     * @param statuses the FixedBillingStatus array.
     * @param initedFlag the inited flag.
     *
     * @throws IllegalArgumentException if the array is invalid.
     * @throws DataAccessException if a problem occurs.
     */
    private void checkStatuses(FixedBillingStatus[] statuses, boolean initedFlag)
        throws DataAccessException {
        Helper.checkNull("statuses", statuses);

        //The array should not be empty.
        if (statuses.length == 0) {
            throw new IllegalArgumentException("The array should not be empty.");
        }

        for (int i = 0; i < statuses.length; i++) {
            //Check the required columns.
            Helper.checkNull("statuses[" + i + "]", statuses[i]);
        }

        for (int i = 0; i < statuses.length; i++) {
            Helper.checkRequiredColumn("description of statuses[" + i + "]", statuses[i].getDescription());
            Helper.checkRequiredColumn("creationUser of statuses[" + i + "]", statuses[i].getCreationUser());
            Helper.checkRequiredColumn("modificationUser of statuses[" + i + "]", statuses[i].getModificationUser());
            //The create and modify date should be updated in the Manager layer.
            Helper.checkRequiredColumn("creationDate of statuses[" + i + "]", statuses[i].getCreationDate());
            Helper.checkRequiredColumn("modificationDate of statuses[" + i + "]", statuses[i].getModificationDate());

            if (initedFlag) {
                //If inited, the id should not be -1.
                if (statuses[i].getId() == -1) {
                    throw new IllegalArgumentException("The statuses[" + i + "]'s id should not be -1 when updating.");
                }
                //The array should not contain duplicate item.
                for (int j = 0; j < statuses.length; j++) {
                    if ((i != j) && (statuses[i].getId() == statuses[j].getId())) {
                        throw new IllegalArgumentException("The array contains duplicate item.");
                    }
                }
            } else {
                //If not inited, the id should be -1.
                if (statuses[i].getId() != -1) {
                    throw new IllegalArgumentException("The statuses[" + i + "]'s id should be -1 when inserting.");
                }
                //The array should not contain duplicate item.
                for (int j = 0; j < statuses.length; j++) {
                    if ((i != j) && (statuses[i] == statuses[j])) {
                        throw new IllegalArgumentException("The array contains duplicate item.");
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingStatuses array
     * parameter. Note that it will only update the status which changed flag is true. And after update, the changed
     * flag will set to false.
     * </p>
     *
     * @param statuses An array of  statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException if the statuses are not in the database.
     */
    public void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        checkStatuses(statuses, true);

        //Update the statuses.
        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();
            //Check whether the status is in the database, if not, throw exception.
            checkIsExist(statuses, conn);
            state = conn.prepareStatement(UPDATE_STATUS_SQL);

            for (int i = 0; i < statuses.length; i++) {
                if (statuses[i].isChanged()) {
                    int index = 0;
                    state.setString(++index, statuses[i].getDescription());
                    state.setTimestamp(++index, new Timestamp(statuses[i].getCreationDate().getTime()));
                    state.setString(++index, statuses[i].getCreationUser());
                    state.setTimestamp(++index, new Timestamp(statuses[i].getModificationDate().getTime()));
                    state.setString(++index, statuses[i].getModificationUser());
                    state.setLong(++index, statuses[i].getId());
                    state.executeUpdate();
                    //If update success, set the changed flag to false.
                    statuses[i].setChanged(false);
                }
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to update the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * Check whether the given statuses are in the database, if not throw UnrecognizedEntityException.
     *
     * @param statuses the given array.
     * @param conn the given database connection
     *
     * @throws DataAccessException if unable to query the database.
     * @throws UnrecognizedEntityException if the given statuses are not in the database.
     */
    private void checkIsExist(FixedBillingStatus[] statuses, Connection conn)
        throws DataAccessException {
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            state = conn.prepareStatement(SELECT_SINGLE_STATUS_SQL);

            for (int i = 0; i < statuses.length; i++) {
                state.setLong(1, statuses[i].getId());
                rs = state.executeQuery();

                //If can not find the record, throw exception.
                if (!rs.next()) {
                    throw new UnrecognizedEntityException(statuses[i].getId(), "Unable to find the statuses[" + i
                            + "].");
                }

                Helper.closeResources(null, null, rs);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to get the records.", sqle);
        } finally {
            Helper.closeResources(null, state, rs);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the status with the specified ids.
     * </p>
     *
     * @param statusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the status is limited
     *         by the foreign key in the other table.
     */
    public void deleteFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        Helper.checkIdArray("statusIds", statusIds);

        //Delete the statuses.
        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();
            state = Helper.createInStatement(conn, state, statusIds, DELETE_STATUS_SQL);

            if (state.executeUpdate() != statusIds.length) {
                throw new DataAccessException("The deleted rows' count is not equal to the id's count.");
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to delete the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * <p>
     * Retrieves an array of FixedBillingStatus objects that reflects the data in the persistent  store on the status
     * with the specified Ids. Note that the status' changed flag will set to false.
     * </p>
     *
     * @param statusIds An array of statusIds for which status should be retrieved.
     *
     * @return The statuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if unable to find the status.
     */
    public FixedBillingStatus[] getFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        Helper.checkIdArray("statusIds", statusIds);

        //Get the statuses.
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        FixedBillingStatus[] statusArray = null;

        try {
            conn = getConnection();
            state = Helper.createInStatement(conn, state, statusIds, SELECT_STATUS_SQL);
            rs = state.executeQuery();
            statusArray = convertToStatusArray(rs);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        //Check the result size, if not equal to the ids' length, throw exception.
        Helper.checkResultItems(statusArray, statusIds);

        return statusArray;
    }

    /**
     * <p>
     * Searches the persistent store for any FixedBillingStatuses that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingStatusFilterFactory returned by getFixedBillingStatusEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingStatusFilterFactory. Note that the status' changed flag will set
     * to false.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     *
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @exception InvalidFilterException if the filter cannot be recognized.
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] searchFixedBillingStatuses(Filter criteria)
        throws DataAccessException {
        Helper.checkNull("criteria", criteria);

        FixedBillingStatus[] statuses = null;

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(criteria);
            List ids = new ArrayList();
            while (result.next()) {
                ids.add(new Long(result.getLong(1)));
            }
            long[] idArray = new long[ids.size()];
            int i = 0;
            for (Iterator itr = ids.iterator(); itr.hasNext(); ) {
                idArray[i] = ((Long) itr.next()).longValue();
                i++;
            }
            statuses = getFixedBillingStatuses(idArray);

        } catch (PersistenceOperationException poe) {
            throw new DataAccessException("Unable to search the records with search builder error.", poe);
        } catch (UnrecognizedFilterException ufe) {
            throw new InvalidFilterException("Unable to search the records.", ufe);
        } catch (InvalidCursorStateException icse) {
            throw new DataAccessException("Unable to search the records with invalid cursor state.", icse);
        } catch (NullPointerException npe) {
            throw new DataAccessException("Unable to search the records with null pointer exception.", npe);
        } catch (SearchBuilderException sbe) {
            throw new DataAccessException("Unable to search the records with search builder exception.", sbe);
        }

        return statuses;
    }

    /**
     * <p>
     * Retrieves the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching
     * for FixedBillingStatuses. This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingStatuses method.
     * </p>
     *
     * @return the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching for
     *         FixedBillingStatuses.
     */
    public FixedBillingStatusFilterFactory getFixedBillingStatusFilterFactory() {
        return filterFactory;
    }

    /**
     * <p>
     * Retrieves all the FixedBillingStatuses that are currently in the persistent store. Note that the status' changed
     * flag will set to false.
     * </p>
     *
     * @return An array of fixed billing status retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] getAllFixedBillingStatuses()
        throws DataAccessException {
        FixedBillingStatus[] statuses = null;
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(SELECT_ALL_STATUS_SQL);
            rs = state.executeQuery();
            statuses = convertToStatusArray(rs);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        return statuses;
    }

    /**
     * Convert to the status array.
     *
     * @param rs the ResultSet instance.
     *
     * @return a FixedBillingStatus array.
     *
     * @throws SQLException if any error occurs.
     */
    private FixedBillingStatus[] convertToStatusArray(ResultSet rs)
        throws SQLException {
        FixedBillingStatus[] statuses = null;
        List statusList = new ArrayList();

        while (rs.next()) {
            FixedBillingStatus st = new FixedBillingStatus();
            st.setId(rs.getLong(STATUS_ID_FIELD));
            st.setDescription(rs.getString(DESCRIPTION_FIELD));
            st.setCreationDate(rs.getTimestamp(CREATION_DATE_FIELD));
            st.setCreationUser(rs.getString(CREATION_USER_FIELD));
            st.setModificationDate(rs.getTimestamp(MODIFICATION_DATE_FIELD));
            st.setModificationUser(rs.getString(MODIFICATION_USER_FIELD));
            st.setChanged(false);
            statusList.add(st);
        }

        statuses = new FixedBillingStatus[statusList.size()];
        statusList.toArray(statuses);

        return statuses;
    }
}
