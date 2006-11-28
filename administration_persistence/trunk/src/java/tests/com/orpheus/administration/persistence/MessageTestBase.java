/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;

/**
 * Base class containing common helper methods used by the message tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class MessageTestBase extends DAOTestBase {
    /**
     * Flag indicating whether {@link #setUp setUp} has been called before. It is initially <code>true</code> and
     * will be set to <code>false</code> the first time <code>setUp</code> runs.
     */
    private static boolean firstTime = true;

    /**
     * Pre-test setup: initializes the config manager and database connection for the next step.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        removeAllNamespaces();

        getConfigManager().add("database_config.xml");
        getConfigManager().add("message_dao_tests.xml");
        getConfigManager().add("dao_factory_config.xml");

        super.setUp();

        if (firstTime) {
            clearAllTables();
            firstTime = false;
        }
    }

    /**
     * Post-test cleanup: clears all tables, closes the connection, and clears the configuration manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        clearAllTables();
        super.tearDown();
    }

    /**
     * Clears all database tables associated with the message tests.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void clearAllTables() throws Exception {
        clearTable("message");
    }

    /**
     * Adds a message to the <code>message</code> table with the specified values.
     *
     * @param guid the value for the <code>guid</code> column
     * @param category the value for the <code>category</code> column
     * @param contentType the value for the <code>contentType</code> column
     * @param updateTime the value for the <code>update_time</code> column
     * @param content the value for the <code>content</code> column
     * @throws Exception if a database error occurs
     * @throws junit.framework.AssertionFailedError if the insertion fails
     */
    protected void addMessage(String guid, String category, String contentType, Date updateTime, String content)
        throws Exception {
        Connection connection = getConnection();

        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO message (guid, category, content_type, update_time, content) "
                                        + "VALUES (?, ?, ?, ?, ?)");
        try {
            statement.setString(1, guid);
            statement.setString(2, category);
            statement.setString(3, contentType);
            statement.setTimestamp(4, new Timestamp(updateTime.getTime()));
            statement.setString(5, content);

            assertEquals("should have inserted 1 row", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Asserts that a message with the specified values exists in the <code>message</code> table.
     *
     * @param guid the value for the <code>guid</code> column
     * @param category the value for the <code>category</code> column
     * @param contentType the value for the <code>contentType</code> column
     * @param message the value for the <code>content</code> column
     * @param timestamp the value for the <code>update_time</code> column
     * @return <code>true</code> if the message exists, otherwise <code>false</code>
     * @throws Exception if a database error occurs
     */
    protected boolean messageExists(String guid, String category, String contentType, String message, Date timestamp)
        throws Exception {
        Connection connection = getConnection();

        PreparedStatement statement =
            connection.prepareStatement("SELECT content FROM message WHERE guid = ? AND category = ? AND "
                                        + "content_type = ?");
        try {
            statement.setString(1, guid);
            statement.setString(2, category);
            statement.setString(3, contentType);

            ResultSet results = statement.executeQuery();

            try {
                return results.next() && results.getString(1).equals(message);
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }
}

