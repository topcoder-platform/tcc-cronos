/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.SQLServerMessageDAO;

import junit.framework.TestCase;
/**
 * Unit tests for SQLServerMessageDAO class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestSQLServerMessageDAO extends TestCase {
    
    /**
     * SQLServerMessageDAO used to test.
     */
    private SQLServerMessageDAO dao;
    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Helper.clearNamespaces();
        Helper.loadConfig("test_files/failure/message.xml");
        
        dao = new SQLServerMessageDAO("message.dao.config");
    }

    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        Helper.clearNamespaces();
    }

    /**
     * Tests SQLServerMessageDAO(String namespace) method with null String namespace,
     * Expected IllegalArgumentException.
     */
    public void testSQLServerMessageDAO_NullNamespace() {
        try {
            new SQLServerMessageDAO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    /**
     * Tests SQLServerMessageDAO(String namespace) method with empty String namespace,
     * Expected IllegalArgumentException.
     */
    public void testSQLServerMessageDAO_EmptyNamespace() {
        try {
            new SQLServerMessageDAO(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests findMessages(SearchCriteriaDTO criteria) method with null SearchCriteriaDTO criteria,
     * Expected IllegalArgumentException.
     */
    public void testFindMessages_NullCriteria() {
        try {
            dao.findMessages(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests createMessage(Message message) method with null Message message,
     * Expected IllegalArgumentException.
     */
    public void testCreateMessage_NullMessage() {
        try {
            dao.createMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests updateMessage(Message message) method with null Message message,
     * Expected IllegalArgumentException.
     */
    public void testUpdateMessage_NullMessage() {
        try {
            dao.updateMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests deleteMessage(Message message) method with null Message message,
     * Expected IllegalArgumentException.
     */
    public void testDeleteMessage_NullMessage() {
        try {
            dao.deleteMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    
}