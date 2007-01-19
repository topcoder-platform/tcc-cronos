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
 * Database implementation of the RateDAO interface. It persisting and retrieving Rate
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
public class DbRateDAO implements RateDAO {
    /**
     * The SQL query. Deletes the rate.
     */
    private static final String DELETE_FROM_RATE = "DELETE FROM rate WHERE rate_id = ?";

    /**
     *The SQL query. Deletes the rate - company mapping.
     */
    private static final String DELETE_FROM_COMP_RATE =
        "DELETE FROM comp_rate WHERE rate_id = ?";

    /**
     * The SQL query. Updates the rate - company mapping.
     */
    private static final String UPDATE_COMP_RATE = "UPDATE comp_rate SET company_id = ?, "
        + "modification_date = ?, modification_user = ? WHERE rate_id = ?";

    /**
     * The SQL query. Updates the rate.
     */
    private static final String UPDATE_RATE =
        "UPDATE rate SET rate.rate = ?, rate.description = ?, rate.modification_date = ?, "
        + "rate.modification_user = ? WHERE rate_id = ?";

    /**
     * The SQL query. Inserts the rate - company mapping.
     */
    private static final String INSERT_COMP_RATE =
        "INSERT INTO comp_rate(rate_id, company_id, creation_date, creation_user, "
         + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The SQL query. Inserts the rate.
     */
    private static final String INSERT_RATE =
        "INSERT into rate(rate_id, description, rate, creation_date, creation_user,  "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ? ,?)";

    /**
     * The base SQL query used in this class.
     */
    private static final String BASE_QUERY = "SELECT rate.rate_id, rate.description, "
        + "rate.rate, rate.creation_date, rate.creation_user, "
        + "rate.modification_date, rate.modification_user, comp_rate.company_id "
        + "FROM rate, comp_rate WHERE "
        + "comp_rate.rate_id = rate.rate_id ";

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
     * Utilized In: createRate, retrieveRate, updateRate, deleteRate, listRates,
     * searchRates.
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
     * Utilized In: createRate, retrieveRate, updateRate, deleteRate, listRates,
     * searchRates,
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
     * Utilized In: createRate, updateRate
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
     * Utilized In: searchRates
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
    public DbRateDAO(DBConnectionFactory connFactory, String connName, String idGenerator)
            throws IDGenerationException {
        Utils.checkNull(connFactory, "connFactory");
        Utils.checkString(connName, "connName", false);
        Utils.checkString(idGenerator, "idGenerator", false);

        Map aliases = new HashMap();
        aliases.put(RateDAO.SEARCH_COMPANY_ID, "comp_rate.company_id");
        aliases.put(RateDAO.SEARCH_DESCRIPTION, "rate.description");
        aliases.put(RateDAO.SEARCH_CREATED_DATE, "rate.creation_date");
        aliases.put(RateDAO.SEARCH_CREATED_USER, "rate.creation_user");
        aliases.put(RateDAO.SEARCH_MODIFICATION_DATE, "rate.modification_date");
        aliases.put(RateDAO.SEARCH_MODIFICATION_USER, "rate.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);
        connectionFactory = connFactory;
        connectionName = connName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGenerator);
    }

    /**
     * <p>
     * Creates a datastore entry for the given rate. An id is automatically generated by the DAO and
     * assigned to the Rate. The Rate is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same Rate Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately.
     * @param Rate The Rate to create within the datastore.
     * @param username The username of the user responsible for creating the Rate entry within the
     *        datastore.
     * @throws IllegalArgumentException if the Rate or username is null, or if username is an empty String.
     * @throws RateDAOException if a problem occurs while accessing the datastore.
     */
    public Rate createRate(Rate rate, String username) throws RateDAOException {
        checkRate(rate);
        Utils.checkString(username, "username", false);
        // get the id
        long rateId = generateId(rate.getId());
        Connection conn = createConnection();
        try {
            // insert rate
            insertRate(conn, rate, rateId, username);
            // insert rate company mapping
            DBUtils.execute(conn, INSERT_COMP_RATE, rateId, rate.getCompanyId(), username);

            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RateDAOException("Error occurs while creating rate.", ex, rate);
        } finally {
            DBUtils.close(conn);
        }

        rate.setId(rateId);
        rate.setChanged(false);
        return rate;
    }

    /**
     * Checks if required rate fields are set.
     *
     * @param rate the rate to check.
     * @throws rateDAOException if any of required fields is not set.
     */
    private static void checkRate(Rate rate) throws RateDAOException {
        Utils.checkNull(rate, "rate");

        if (rate.getValue() < 0.0) {
            throw new RateDAOException("The  rate should be positive.", null, rate);
        }
    }

    /**
     * Inserts the  rate into the database.
     *
     * @param conn the database connection to be used.
     * @param rate the rate to persist.
     * @param rateId the rate id.
     * @param username the creation username.
     * @throws SQLException if error occurs.
     */
    private static void insertRate(Connection conn, Rate rate, long rateId, String username)
            throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_RATE);

            // set the id
            pstmt.setLong(1, rateId);
			pstmt.setString(2, rate.getDescription());
            pstmt.setDouble(3, rate.getValue());
            // set creation dates
            Date now = DBUtils.initUserAndDates(pstmt, username, 4);
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
     * @throws rateDAOException if connection cannot be created.
     */
    private Connection createConnection() throws RateDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new RateDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new RateDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws rateDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws RateDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new RateDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * <p>
     * Retrieves a rate from the datastore with the provided id. If no rate with that id exists, then
     * a null is returned.
     * </p>
     *
     * @return the rate for the id or <code>null</code>, if rate not exists.
     * @param id The id of the rate to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws rateDAOException if a problem occurs while accessing the datastore.
     */
    public Rate retrieveRate(long id) throws RateDAOException {
        Utils.checkPositive(id, "id");

        // create filter
        Filter filter = new EqualToFilter("rate.rate_id", new Long(id));

        // execute search
        Rate[] result = searchRates(filter);
        // should be only one or zero, but return the first - just in case
        if (result.length > 0) {
            return result[0];
        }

        return null;
    }

    /**
     * Creates <code>rate</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the rate created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private static Rate populateRate(ResultSet rs) throws SQLException {
        Rate rate = new Rate();
        rate.setId(rs.getLong("rate_id"));
		rate.setDescription(rs.getString("description"));
        rate.setValue(rs.getDouble("rate"));
        rate.setCompanyId(rs.getLong("company_id"));
        rate.setModificationUser(rs.getString("modification_user"));
        rate.setCreationUser(rs.getString("creation_user"));
        rate.setModificationDate(rs.getTimestamp("modification_date"));
        rate.setCreationDate(rs.getTimestamp("creation_date"));

        return rate;
    }

    /**
     * <p>
     * Updates the given rate in the data store. The rate is considered to have been modified by the
     * specified username.
     * </p>
     *
     * @param rate The rate entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the rate is null, or the username is null, or the username is an
     *         empty String.
     * @throws rateDAOException if a problem occurs while accessing the datastore.
     * @throws rateNotFoundException if the rate to update was not found in the data store.
     */
    public void updateRate(Rate rate, String username) throws RateDAOException {
        checkRate(rate);
        Utils.checkString(username, "username", false);

        // if was not changed - just return
        if (!rate.isChanged()) {
            return;
        }

        // get connection
        Connection conn = createConnection();
        try {
            // update rate
            int result = updateRate(conn, rate, username);
            // if noting was updated - rate not exists - throw exception
            if (result == 0) {
                DBUtils.rollback(conn);
                throw new RateNotFoundException(" rate not exists.", null, rate);
            }

            // update mapping
            DBUtils.executeUpdate(conn, UPDATE_COMP_RATE,
                    rate.getCompanyId(), rate.getId(), username);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RateDAOException("Error occur while updating rate.", ex, rate);
        } finally {
            DBUtils.close(conn);
        }

        // is updated, set flag to false.
        rate.setChanged(false);
    }

    /**
     * Updates the given rate in the database.
     *
     * @param conn the database connection to be used.
     * @param rate the rate to be updated.
     * @param username the update user name.
     * @return the number of affected rows.
     * @throws SQLException if any error occur.
     */
    private static int updateRate(Connection conn, Rate rate, String username) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            // prepare update query
            pstmt = conn.prepareStatement(UPDATE_RATE);

            // set variables
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setDouble(1, rate.getValue());
			pstmt.setString(2, rate.getDescription());			
            pstmt.setTimestamp(3, time);
            pstmt.setString(4, username);
            pstmt.setLong(5, rate.getId());

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
     * Removes the specified rate from the data store.
     * </p>
     *
     * @param rate The rate to delete.
     * @throws IllegalArgumentException if the rate is null.
     * @throws rateDAOException if a problem occurs while accessing the datastore.
     * @throws rateNotFoundException if the rate to delete was not found in the data store.
     */
    public void deleteRate(Rate rate) throws RateDAOException,
        RateNotFoundException {

        Utils.checkNull(rate, "rate");

        Connection conn = createConnection();
        try {
            // delete company - rate mapping first
            DBUtils.executeDelete(conn, DELETE_FROM_COMP_RATE, rate.getId());

            // delete rate
            int count = DBUtils.executeDelete(conn, DELETE_FROM_RATE, rate.getId());
            if (count == 0) {
                DBUtils.rollback(conn);
                throw new RateNotFoundException("No rate exists.", null, rate);
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new RateDAOException("Error occur while retrieving the  rate.", ex, rate);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Enumerates all the rates that are present within the data store.
     * </p>
     *
     * @return A list of all the rates within the data store.
     * @throws rateDAOException if a problem occurs while accessing the datastore.
     */
    public Rate[] listRates() throws RateDAOException {
        return listRates(BASE_QUERY, false);
    }

    /**
     * <p>
     * Returns a list of all the Rates within the datastore that satisfy the filters that are provided. The
     * filters are defined using classes from the Search Builder v1.2 component and com.topcoder.timetracker.
     * common.search package.
     * </p>
     * <p>
     * Implementation Notes: - Create a new AND filter that is a conjunction of the baseFilter and the filter
     * parameter. - Use the searchBundle to conduct a search with the built AND filter. - Cast the resulting object
     * to a CustomResultSet. - Build the resulting rate objects similar to how it would be built from
     * retrieverate ResultSet.
     * </p>
     *
     *
     *
     * @return A list of Rates that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws rateDAOException if a problem occurs while accessing the datastore.
     */
    public Rate[] searchRates(Filter filter) throws RateDAOException {
        Utils.checkNull(filter, "filter");

        // since searchStringBuilder is shared between methods, it must be synchronized
        // to make class thread safe
        synchronized(searchStringBuilder) {
            return listRates(searchStringBuilder.buildSearchString(filter), true);
        }
    }

    /**
     * Returns the array of  rate that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of  rate found.
     * @throws rateDAOException if any error occurs.
     */
    private Rate[] listRates(String query, boolean fillStatement) throws RateDAOException {
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
                result.add(populateRate(rs));
            }

            // return as array/
            return (Rate[]) result.toArray(new Rate[result.size()]);
        } catch (SQLException ex) {
            throw new RateDAOException("Error occur while retrieving the  rate.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }
}
