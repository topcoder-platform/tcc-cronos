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
 * Database implementation of the MileageRateDAO interface. It persisting and retrieving MileageRate
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
public class DbMileageRateDAO implements MileageRateDAO {
    /**
     * The SQL query. Deletes the mileage rate.
     */
    private static final String DELETE_FROM_MILEAGE_RATE = "DELETE FROM mileage_rate WHERE mileage_rate_id = ?";

    /**
     *The SQL query. Deletes the mileage rate - company mapping.
     */
    private static final String DELETE_FROM_COMP_MILEAGE_RATE =
        "DELETE FROM comp_mileage_rate WHERE mileage_rate_id = ?";

    /**
     * The SQL query. Updates the mileage rate - company mapping.
     */
    private static final String UPDATE_COMP_MILEAGE_RATE = "UPDATE comp_mileage_rate SET company_id = ?, "
        + "modification_date = ?, modification_user = ? WHERE mileage_rate_id = ?";

    /**
     * The SQL query. Updates the mileage rate.
     */
    private static final String UPDATE_MILEAGE_RATE =
        "UPDATE mileage_rate SET mileage_rate.rate = ?, mileage_rate.modification_date = ?, "
        + "mileage_rate.modification_user = ? WHERE mileage_rate_id = ?";

    /**
     * The SQL query. Inserts the mileage rate - company mapping.
     */
    private static final String INSERT_COMP_MILEAGE_RATE =
        "INSERT INTO comp_mileage_rate(mileage_rate_id, company_id, creation_date, creation_user, "
         + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The SQL query. Inserts the mileage rate.
     */
    private static final String INSERT_MILEAGE_RATE =
        "INSERT into mileage_rate(mileage_rate_id, rate, creation_date, creation_user,  "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ? ,?)";

    /**
     * The base SQL query used in this class.
     */
    private static final String BASE_QUERY = "SELECT mileage_rate.mileage_rate_id, "
        + "mileage_rate.rate, mileage_rate.creation_date, mileage_rate.creation_user, "
        + "mileage_rate.modification_date, mileage_rate.modification_user, comp_mileage_rate.company_id "
        + "FROM mileage_rate, comp_mileage_rate WHERE "
        + "comp_mileage_rate.mileage_rate_id = mileage_rate.mileage_rate_id ";

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
     * Utilized In: createMileageRate, retrieveMileageRate, updateMileageRate, deleteMileageRate, listMileageRates,
     * searchMileageRates.
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
     * Utilized In: createMileageRate, retrieveMileageRate, updateMileageRate, deleteMileageRate, listMileageRates,
     * searchMileageRates,
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
     * Utilized In: createMileageRate, updateMileageRate
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
     * Utilized In: searchMileageRates
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
    public DbMileageRateDAO(DBConnectionFactory connFactory, String connName, String idGenerator)
            throws IDGenerationException {
        Utils.checkNull(connFactory, "connFactory");
        Utils.checkString(connName, "connName", false);
        Utils.checkString(idGenerator, "idGenerator", false);

        Map aliases = new HashMap();
        aliases.put(MileageRateDAO.SEARCH_COMPANY_ID, "comp_mileage_rate.company_id");
        aliases.put(MileageRateDAO.SEARCH_DESCRIPTION, "mileage_rate.description");
        aliases.put(MileageRateDAO.SEARCH_CREATED_DATE, "mileage_rate.creation_date");
        aliases.put(MileageRateDAO.SEARCH_CREATED_USER, "mileage_rate.creation_user");
        aliases.put(MileageRateDAO.SEARCH_MODIFICATION_DATE, "mileage_rate.modification_date");
        aliases.put(MileageRateDAO.SEARCH_MODIFICATION_USER, "mileage_rate.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);
        connectionFactory = connFactory;
        connectionName = connName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGenerator);
    }

    /**
     * <p>
     * Creates a datastore entry for the given Mileage Rate. An id is automatically generated by the DAO and
     * assigned to the Rate. The MileageRate is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same mileageRate Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately.
     * @param mileageRate The mileageRate to create within the datastore.
     * @param username The username of the user responsible for creating the MileageRate entry within the
     *        datastore.
     * @throws IllegalArgumentException if the mileageRate or username is null, or if username is an empty String.
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     */
    public MileageRate createMileageRate(MileageRate mileageRate, String username) throws MileageRateDAOException {
        checkMileageRate(mileageRate);
        Utils.checkString(username, "username", false);
        // get the id
        long rateId = generateId(mileageRate.getId());
        Connection conn = createConnection();
        try {
            // insert mileage rate
            insertMileageRate(conn, mileageRate, rateId, username);
            // insert mileage rate company mapping
            DBUtils.execute(conn, INSERT_COMP_MILEAGE_RATE, rateId, mileageRate.getCompanyId(), username);

            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new MileageRateDAOException("Error occurs while creating rate.", ex, mileageRate);
        } finally {
            DBUtils.close(conn);
        }

        mileageRate.setId(rateId);
        mileageRate.setChanged(false);
        return mileageRate;
    }

    /**
     * Checks if required rate fields are set.
     *
     * @param mileageRate the rate to check.
     * @throws MileageRateDAOException if any of required fields is not set.
     */
    private static void checkMileageRate(MileageRate mileageRate) throws MileageRateDAOException {
        Utils.checkNull(mileageRate, "mileageRate");

        if (mileageRate.getRate() < 0.0) {
            throw new MileageRateDAOException("The mileage rate should be positive.", null, mileageRate);
        }
    }

    /**
     * Inserts the mileage rate into the database.
     *
     * @param conn the database connection to be used.
     * @param rate the rate to persist.
     * @param rateId the rate id.
     * @param username the creation username.
     * @throws SQLException if error occurs.
     */
    private static void insertMileageRate(Connection conn, MileageRate rate, long rateId, String username)
            throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_MILEAGE_RATE);

            // set the id
            pstmt.setLong(1, rateId);
            pstmt.setDouble(2, rate.getRate());
            // set creation dates
            Date now = DBUtils.initUserAndDates(pstmt, username, 3);
            // execute
            pstmt.executeUpdate();
            DBUtils.setCreationFields(rate, username, now);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the database connection with auto commit set to false.
     *
     * @return the database connection.
     * @throws MileageRateDAOException if connection cannot be created.
     */
    private Connection createConnection() throws MileageRateDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new MileageRateDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new MileageRateDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws MileageRateDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws MileageRateDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new MileageRateDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * <p>
     * Retrieves a MileageRate from the datastore with the provided id. If no MileageRate with that id exists, then
     * a null is returned.
     * </p>
     *
     * @return the MileageRate for the id or <code>null</code>, if rate not exists.
     * @param id The id of the MileageRate to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     */
    public MileageRate retrieveMileageRate(long id) throws MileageRateDAOException {
        Utils.checkPositive(id, "id");

        // create filter
        Filter filter = new EqualToFilter("mileage_rate.mileage_rate_id", new Long(id));

        // execute search
        MileageRate[] result = searchMileageRates(filter);
        // should be only one or zero, but return the first - just in case
        if (result.length > 0) {
            return result[0];
        }

        return null;
    }

    /**
     * Creates <code>MileageRate</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the MileageRate created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private static MileageRate populateMileageRate(ResultSet rs) throws SQLException {
        MileageRate rate = new MileageRate();
        rate.setId(rs.getLong("mileage_rate_id"));
        rate.setRate(rs.getDouble("rate"));
        rate.setCompanyId(rs.getLong("company_id"));
        rate.setModificationUser(rs.getString("modification_user"));
        rate.setCreationUser(rs.getString("creation_user"));
        rate.setModificationDate(rs.getTimestamp("modification_date"));
        rate.setCreationDate(rs.getTimestamp("creation_date"));

        return rate;
    }

    /**
     * <p>
     * Updates the given MileageRate in the data store. The MileageRate is considered to have been modified by the
     * specified username.
     * </p>
     *
     * @param mileageRate The MileageRate entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the mileageRate is null, or the username is null, or the username is an
     *         empty String.
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     * @throws MileageRateNotFoundException if the MileageRate to update was not found in the data store.
     */
    public void updateMileageRate(MileageRate mileageRate, String username) throws MileageRateDAOException {
        checkMileageRate(mileageRate);
        Utils.checkString(username, "username", false);

        // if was not changed - just return
        if (!mileageRate.isChanged()) {
            return;
        }

        // get connection
        Connection conn = createConnection();
        try {
            // update rate
            int result = updateRate(conn, mileageRate, username);
            // if noting was updated - rate not exists - throw exception
            if (result == 0) {
                DBUtils.rollback(conn);
                throw new MileageRateNotFoundException("Mileage rate not exists.", null, mileageRate);
            }

            // update mapping
            DBUtils.executeUpdate(conn, UPDATE_COMP_MILEAGE_RATE,
                    mileageRate.getCompanyId(), mileageRate.getId(), username);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new MileageRateDAOException("Error occur while updating rate.", ex, mileageRate);
        } finally {
            DBUtils.close(conn);
        }

        // is updated, set flag to false.
        mileageRate.setChanged(false);
    }

    /**
     * Updates the given MileageRate in the database.
     *
     * @param conn the database connection to be used.
     * @param rate the rate to be updated.
     * @param username the update user name.
     * @return the number of affected rows.
     * @throws SQLException if any error occur.
     */
    private static int updateRate(Connection conn, MileageRate rate, String username) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            // prepare update query
            pstmt = conn.prepareStatement(UPDATE_MILEAGE_RATE);

            // set variables
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setDouble(1, rate.getRate());
            pstmt.setTimestamp(2, time);
            pstmt.setString(3, username);
            pstmt.setLong(4, rate.getId());

            int result = pstmt.executeUpdate();
            // update the object status
            DBUtils.setModificationFields(rate, username, time);
            return result;
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * <p>
     * Removes the specified MileageRate from the data store.
     * </p>
     *
     * @param mileageRate The mileageRate to delete.
     * @throws IllegalArgumentException if the mileageRate is null.
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     * @throws MileageRateNotFoundException if the MileageRate to delete was not found in the data store.
     */
    public void deleteMileageRate(MileageRate mileageRate) throws MileageRateDAOException,
        MileageRateNotFoundException {

        Utils.checkNull(mileageRate, "mileageRate");

        Connection conn = createConnection();
        try {
            // delete company - rate mapping first
            DBUtils.executeDelete(conn, DELETE_FROM_COMP_MILEAGE_RATE, mileageRate.getId());

            // delete rate
            int count = DBUtils.executeDelete(conn, DELETE_FROM_MILEAGE_RATE, mileageRate.getId());
            if (count == 0) {
                DBUtils.rollback(conn);
                throw new MileageRateNotFoundException("No rate exists.", null, mileageRate);
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new MileageRateDAOException("Error occur while retrieving the mileage rate.", ex, mileageRate);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Enumerates all the MileageRates that are present within the data store.
     * </p>
     *
     * @return A list of all the MileageRates within the data store.
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     */
    public MileageRate[] listMileageRates() throws MileageRateDAOException {
        return listMileageRates(BASE_QUERY, false);
    }

    /**
     * <p>
     * Returns a list of all the MileageReasons within the datastore that satisfy the filters that are provided. The
     * filters are defined using classes from the Search Builder v1.2 component and com.topcoder.timetracker.
     * common.search package.
     * </p>
     * <p>
     * Implementation Notes: - Create a new AND filter that is a conjunction of the baseFilter and the filter
     * parameter. - Use the searchBundle to conduct a search with the built AND filter. - Cast the resulting object
     * to a CustomResultSet. - Build the resulting MileageRate objects similar to how it would be built from
     * retrieveMileageRate ResultSet.
     * </p>
     *
     *
     *
     * @return A list of MileageReasons that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws MileageRateDAOException if a problem occurs while accessing the datastore.
     */
    public MileageRate[] searchMileageRates(Filter filter) throws MileageRateDAOException {
        Utils.checkNull(filter, "filter");

        // since searchStringBuilder is shared between methods, it must be synchronized
        // to make class thread safe
        synchronized(searchStringBuilder) {
            return listMileageRates(searchStringBuilder.buildSearchString(filter), true);
        }
    }

    /**
     * Returns the array of mileage rate that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of mileage rate found.
     * @throws MileageRateDAOException if any error occurs.
     */
    private MileageRate[] listMileageRates(String query, boolean fillStatement) throws MileageRateDAOException {
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
                result.add(populateMileageRate(rs));
            }

            // return as array/
            return (MileageRate[]) result.toArray(new MileageRate[result.size()]);
        } catch (SQLException ex) {
            throw new MileageRateDAOException("Error occur while retrieving the mileage rate.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }
}
