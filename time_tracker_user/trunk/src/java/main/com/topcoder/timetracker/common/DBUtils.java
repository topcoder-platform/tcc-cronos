/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.common;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * <p>
 * Helper class that maintain common database methods used in this component.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public final class DBUtils {

    /**
     * The SQL query. Updates the address table.
     */
    private static final String UPDATE_ADDRESS = "UPDATE Address SET address.line1 = ?, address.line2 = ?, "
            + "address.city = ?, address.state_name_id = ?, address.zip_code = ?, address.modification_date = ?, "
            + "address.modification_user = ? WHERE address.address_id = ?";

    /**
     * The SQL query. Updates the contact table.
     */
    private static final String UPDATE_CONTACT = "UPDATE contact SET contact.first_name = ?, "
            + "contact.last_name = ?, contact.phone = ?, contact.email = ?, contact.modification_date = ?, "
            + "contact.modification_user = ? WHERE contact.contact_id = ?";

    /**
     * The SQL query. Inserts the contact to table.
     */
    private static final String INSERT_CONTACT = "INSERT into contact(contact_id, first_name, last_name, "
            + "phone, email, creation_date, creation_user, modification_date, modification_user) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The SQL query. Inserts the address to table.
     */
    private static final String INSERT_ADDRESS = "INSERT INTO address(address_id, line1, line2, "
            + "city, state_name_id , zip_code, creation_date, creation_user, modification_date, modification_user) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

    /**
     * Empty constructor.
     */
    private DBUtils() {
    }

    /**
     * Closes the connection.
     *
     * @param conn the connection.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the statement.
     *
     * @param stmt the statement.
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the result set.
     *
     * @param rs the result set.
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Roll backs the transaction.
     *
     * @param conn connection.
     */
    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            // ignore
        }
    }

    /**
     * Inserts value into PreparedStatement.
     *
     * @param pstmt statement.
     * @param idx parameter index.
     * @param value the value to be set.
     * @throws SQLException if error occurs.
     */
    private static void insertValurOrNull(PreparedStatement pstmt, int idx, String value) throws SQLException {
        if (value == null) {
            pstmt.setNull(idx, Types.VARCHAR);
        } else {
            pstmt.setString(idx, value);
        }
    }

    /**
     * <p>
     * Sets the username and current date to the prepared statement. The parameters order in statement should be:
     * <ol>
     * <li>creation date</li>
     * <li>creation user</li>
     * <li>modification date</li>
     * <li>modification user</li>
     * </ol>
     * And they should start from the <code>idx</code> position.
     * </p>
     *
     * @param pstmt the PreparedStatement to which the values will be set.
     * @param username the name of the user.
     * @param idx the position of the first parameter to set.
     * @return returns the current date that is set to statement.
     * @throws SQLException if error occur during the operation.
     * @throws IllegalArgumentException if <code>pstmt</code> or <code>username</code> is <code>null</code>
     *         or empty.
     */
    public static Date initUserAndDates(PreparedStatement pstmt, String username, int idx) throws SQLException {
        Utils.checkNull(pstmt, "pstmt");
        Utils.checkString(username, "username", false);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        pstmt.setTimestamp(idx, time); // creation date
        pstmt.setString(idx + 1, username); // creation_user
        pstmt.setTimestamp(idx + 2, time); // modification date
        pstmt.setString(idx + 3, username); // modification user

        return time;
    }

    /**
     * <p>
     * Creates the <code>address</code> in the database. It may be use as a part of transaction, since it does
     * not commit or rollback or close the given connection.
     * </p>
     *
     * @param conn the database connection to be used.
     * @param address the <code>Address</code> instance that will be inserted into database.
     * @param addressId the id of the address. If operation success, it will be set to the address object.
     * @param username the creation user name.
     * @throws SQLException if database related error occurs.
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static void insertAddress(Connection conn, Address address, long addressId, String username)
        throws SQLException {

        Utils.checkNull(conn, "conn");
        Utils.checkNull(address, "address");
        Utils.checkString(username, "username", false);

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_ADDRESS);

            // set the values according to query order
            pstmt.setLong(1, addressId); // address_id
            pstmt.setString(2, address.getLine1()); // line1
            insertValurOrNull(pstmt, 3, address.getLine2()); // line_2 - optional
            pstmt.setString(4, address.getCity()); // city
            pstmt.setLong(5, address.getState().getId()); // state_name_id
            pstmt.setString(6, address.getZipCode()); // zip_code

            // set the creation/modification dates and user
            Date now = initUserAndDates(pstmt, username, 7);
            pstmt.executeUpdate();

            // set creation/modification dates and user
            setCreationFields(address, username, now);
            address.setId(addressId);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Creates the <code>contact</code> in the database. It may be use as a part of transaction, since it does
     * not commit or rollback or close the given connection.
     * </p>
     *
     * @param conn the database connection to be used.
     * @param contact the <code>Contact</code> instance that will be inserted into database.
     * @param contactId the id of the contact. If operation success, it will be set to the address object.
     * @param username the creation user name.
     * @throws SQLException if database related error occurs.
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static void insertContact(Connection conn, Contact contact, long contactId, String username)
        throws SQLException {

        Utils.checkNull(conn, "conn");
        Utils.checkNull(contact, "contact");
        Utils.checkString(username, "username", false);

        PreparedStatement pstmt = null;
        try {
            // create the insert query
            pstmt = conn.prepareStatement(INSERT_CONTACT);

            // set the values according to query order
            pstmt.setLong(1, contactId); // contact_id
            pstmt.setString(2, contact.getFirstName()); // first_name
            pstmt.setString(3, contact.getLastName()); // last_name
            pstmt.setString(4, contact.getPhoneNumber()); // phone
            pstmt.setString(5, contact.getEmailAddress()); // email

            // set the creation/modification dates and user
            Date now = initUserAndDates(pstmt, username, 6);
            pstmt.executeUpdate();

            // set creation/modification dates and user
            setCreationFields(contact, username, now);
            contact.setId(contactId);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Executes the given <code>query</code>. The <code>query</code> should have defined two placeholders for
     * <code>firstValue</code> and <code>secondValue</code> that will be set to it and also the place for the
     * creation date, user and modification date, user pairs.
     * </p>
     *
     * @param conn the database connection to be used to execute the query.
     * @param query the query to be executed.
     * @param firstValue the first value to be set.
     * @param secondValue the seconf value to be set.
     * @param username the creation user name.
     * @return the actual date where the query was executed.
     * @throws SQLException if any database error occur.
     * @throws IllegalArgumentException if any argument is <code>null</code> or String's are empty.
     */
    public static Date execute(Connection conn, String query, long firstValue, long secondValue, String username)
        throws SQLException {

        Utils.checkNull(conn, "conn");
        Utils.checkString(query, "query", false);
        Utils.checkString(username, "username", false);

        PreparedStatement pstmt = null;

        try {
            // prepare the connection
            pstmt = conn.prepareStatement(query);
            // set the values
            pstmt.setLong(1, firstValue);
            pstmt.setLong(2, secondValue);
            Date creationTime = initUserAndDates(pstmt, username, 3);

            pstmt.executeUpdate();
            return creationTime;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Executes the given update <code>query</code>. The <code>query</code> should have defined two
     * placeholders for <code>value</code> and <code>key</code> that will be set to it and also the place for
     * the modification date, user pair.
     * </p>
     *
     * @param conn the database connection to be used to execute the query.
     * @param query the query to be executed.
     * @param value the value to be set.
     * @param key the value's key.
     * @param username the creation user name.
     * @return the actual date where the query was executed.
     * @throws SQLException if any database error occur.
     * @throws IllegalArgumentException if any argument is <code>null</code> or String's are empty.
     */
    public static Date executeUpdate(Connection conn, String query, long value, long key, String username)
        throws SQLException {

        Utils.checkNull(conn, "conn");
        Utils.checkString(query, "query", false);
        Utils.checkString(username, "username", false);
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);

            pstmt.setLong(1, value);
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(2, updateTime);
            pstmt.setString(3, username);
            pstmt.setLong(4, key);

            pstmt.executeUpdate();
            return updateTime;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Executes the given delete <code>query</code>. The <code>query</code> should have defined a placeholder
     * for <code>value</code> which will identify the to delete.
     * </p>
     *
     * @param conn the database connection to be used to execute the query.
     * @param query the query to be executed.
     * @param value the value to be set.
     * @return the actual date where the query was executed.
     * @throws SQLException if any database error occur.
     * @throws IllegalArgumentException if any argument is <code>null</code> or String's are empty.
     */
    public static int executeDelete(Connection conn, String query, long value) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);

            pstmt.setLong(1, value);
            return pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    /**
     * Creates <code>Contact</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the Contact created from result set.
     * @throws SQLException if any database related error occurs.
     * @throws IllegalArgumentException if <code>rs</code> is <code>null</code>.
     */
    public static Contact populateContact(ResultSet rs) throws SQLException {
        Utils.checkNull(rs, "rs");
        Contact contact = new Contact();

        contact.setId(rs.getLong("contact_id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setEmailAddress(rs.getString("email"));
        contact.setPhoneNumber(rs.getString("phone"));
        contact.setLastName(rs.getString("last_name"));

        contact.setCreationDate(rs.getTimestamp("ContactCreationDate"));
        contact.setModificationDate(rs.getTimestamp("ContactModificationDate"));
        contact.setCreationUser(rs.getString("ContactCreationUser"));
        contact.setModificationUser(rs.getString("ContactModificationUser"));

        contact.setChanged(false);

        return contact;
    }

    /**
     * <p>
     * Sets the creation/modification date and user to the given TimeTrackerBean instance.
     * </p>
     *
     * @param bean the bean to be set.
     * @param username the creation username.
     * @param date the creation time.
     * @throws IllegalArgumentException if any argument is <code>null</code> or string is empty.
     */
    public static void setCreationFields(TimeTrackerBean bean, String username, Date date) {
        setModificationFields(bean, username, date);
        bean.setCreationDate(date);
        bean.setCreationUser(username);
    }

    /**
     * <p>
     * Sets the modification date and user to the given TimeTrackerBean instance.
     * </p>
     *
     * @param bean the bean to be set.
     * @param username the creation username.
     * @param date the creation time.
     * @throws IllegalArgumentException if any argument is <code>null</code> or string is empty.
     */
    public static void setModificationFields(TimeTrackerBean bean, String username, Date date) {
        Utils.checkNull(bean, "bean");

        bean.setModificationDate(date);
        bean.setModificationUser(username);
    }

    /**
     * Creates <code>State</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the State created from result set.
     * @throws SQLException if any database related error occurs.
     * @throws IllegalArgumentException if <code>rs</code> is <code>null</code>.
     */
    public static State populateState(ResultSet rs) throws SQLException {
        Utils.checkNull(rs, "rs");

        State state = new State();
        state.setId(rs.getLong("state_name_id"));
        state.setName(rs.getString("StateName"));
        state.setAbbreviation(rs.getString("Abbreviation"));

        state.setCreationDate(rs.getTimestamp("StateCreationDate"));
        state.setModificationDate(rs.getTimestamp("StateModificationDate"));
        state.setCreationUser(rs.getString("StateCreationUser"));
        state.setCreationUser(rs.getString("StateModificationUser"));

        state.setChanged(false);

        return state;
    }

    /**
     * Creates <code>Address</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the Address created from result set.
     * @throws SQLException if any database related error occurs.
     * @throws IllegalArgumentException if <code>rs</code> is <code>null</code>.
     */
    public static Address populateAddress(ResultSet rs) throws SQLException {
        Utils.checkNull(rs, "rs");
        Address address = new Address();

        address.setId(rs.getLong("address_id"));
        address.setLine1(rs.getString("line1"));
        address.setLine2(rs.getString("line2"));
        address.setCity(rs.getString("city"));
        address.setZipCode(rs.getString("zip_code"));

        address.setCreationDate(rs.getTimestamp("AddrCreationDate"));
        address.setModificationDate(rs.getTimestamp("AddrModificationDate"));
        address.setCreationUser(rs.getString("AddrCreationUser"));
        address.setModificationUser(rs.getString("AddrModificationUser"));

        address.setState(populateState(rs));
        address.setChanged(false);

        return address;
    }

    /**
     * <p>
     * Updates the <code>contact</code> table in database using data from given <code>Contact</code> object.
     * All database exception are propagated to the caller.
     * </p>
     *
     * @param conn the connection to be use.
     * @param contact the <code>Contact</code> instance that will be updated.
     * @param username the modification user name.
     * @return the number of affected rows.
     * @throws SQLException if any error occurs during update.
     * @throws IllegalArgumentException if any argument is <code>null</code>, or string is empty.
     */
    public static int updateContact(Connection conn, Contact contact, String username) throws SQLException {
        Utils.checkNull(conn, "conn");
        Utils.checkNull(contact, "contact");
        Utils.checkString(username, "username", false);

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(UPDATE_CONTACT);

            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getPhoneNumber());
            pstmt.setString(4, contact.getEmailAddress());

            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(5, time);
            pstmt.setString(6, username);
            pstmt.setLong(7, contact.getId());

            int result = pstmt.executeUpdate();
            setModificationFields(contact, username, time);
            return result;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Updates the <code>address</code> table in database using data from given <code>Address</code> object.
     * All database exception are propagated to the caller.
     * </p>
     *
     * @param conn the connection to be use.
     * @param address the <code>Address</code> instance that will be updated.
     * @param username the modification user name.
     * @return the number of affected rows.
     * @throws SQLException if any error occurs during update.
     * @throws IllegalArgumentException if any argument is <code>null</code>, or string is empty.
     */
    public static int updateAddress(Connection conn, Address address, String username) throws SQLException {
        Utils.checkNull(conn, "conn");
        Utils.checkNull(address, "address");
        Utils.checkString(username, "username", false);

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(UPDATE_ADDRESS);

            pstmt.setString(1, address.getLine1());
            insertValurOrNull(pstmt, 2, address.getLine2());
            pstmt.setString(3, address.getCity());
            pstmt.setLong(4, address.getState().getId());
            pstmt.setString(5, address.getZipCode());

            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(6, time);
            pstmt.setString(7, username);
            pstmt.setLong(8, address.getId());

            int result = pstmt.executeUpdate();
            setModificationFields(address, username, time);
            return result;
        } finally {
            close(pstmt);
        }
    }

    /**
     * Fills the given <code>PreparedStatement</code> with the <code>values</code>. There is no argument
     * checking, all the exception are propagated to the caller.
     *
     * @param pstmt the PreparedStatement to be filled.
     * @param values the values to fill the statement.
     * @throws SQLException error occurs while filling the statement.
     */
    public static void fillStatement(PreparedStatement pstmt, List values) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            final Object value = values.get(i);
            // if date then set as timestamp - we need it's precision
            if (value instanceof Date) {
                pstmt.setTimestamp(i + 1, new Timestamp(((Date) value).getTime()));
            } else {
                pstmt.setObject(i + 1, value);
            }
        }
    }
}
