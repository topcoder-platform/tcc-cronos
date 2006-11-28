/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.orpheus.administration.persistence.DuplicateEntryException;
import com.orpheus.administration.persistence.EntryNotFoundException;
import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslator;
import com.orpheus.administration.persistence.impl.SQLServerMessageDAO;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import junit.framework.TestCase;

import java.sql.Connection;

import java.util.Date;


/**
 * <p>Accuracy test cases for class SQLServerMessageDAO.</p>
 * @author waits
 * @version 1.0
 */
public class SQLServerMessageDAOAccTests extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** SQLServerMessageDAO instance to test against. */
    private SQLServerMessageDAO dao = null;

    /**
     * create instance.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //add config file
        TestHelper.addConfigFile(TestHelper.ADMINISTRATION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.SEARCH_BUNDLE_CONFIG_FILE);

        //insert data to database for testing
        connection = TestHelper.getConnection();

        //clear the database
        TestHelper.clearDatabase(connection);
        //create instance
        dao = new SQLServerMessageDAO(TestHelper.MESSAGE_DAO_NAMESPACE);
    }

    /**
     * <p>
     * Test the ctor. Simply verify
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCtor() throws Exception {
        assertNotNull("Fail to instantiate the SQLServerMessageDAO.", dao);
    }

    /**
     * <p>
     * Test the create message.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        dao.createMessage(toPersist);

        //get from database
        Message msg = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toPersist, msg);
    }

    /**
     * Test the updateMessage(message) method.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        dao.createMessage(toPersist);

        //update the message and persist
        timestamp = new Date(2000);

        Message toUpdate = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT + TestHelper.CONTENT, timestamp);
        dao.updateMessage(toUpdate);

        //get message from db
        Message updated = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toUpdate, updated);

        try {
            dao.createMessage(updated);
            fail("The message should be in db now.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test deleteMessage(message) method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testDeleteMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        dao.createMessage(toPersist);

        //delete the message
        dao.deleteMessage(toPersist);

        try {
            dao.updateMessage(toPersist);
            fail("Should be deleted.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test find message method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindMessages() throws Exception {
        //create message
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        dao.createMessage(toPersist);

        //find the mssages
        Message[] messages = dao.findMessages(new RSSSearchCriteriaTranslator().assembleSearchCriteriaDTO(
                    new SearchCriteria() {
                    public Filter getSearchFilter() {
                        return new EqualToFilter("guid", TestHelper.GUID);
                    }
                }));

        //verify
        assertEquals("The size of messages array is invalid.", 1, messages.length);
        TestHelper.assertEquals(toPersist, messages[0]);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            TestHelper.clearConfigManager();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
