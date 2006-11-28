/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.InstantiationException;
import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageTestBase;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import java.util.Date;

/**
 * Unit tests for the <code>SQLServerMessageDAO</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class SQLServerMessageDAOTests extends MessageTestBase {
    /**
     * The <code>SQLServerMessageDAO</code> instance to use for the test. This member is initialized in {@link
     * #setUp setUp} to be a new instance for each test.
     */
    private SQLServerMessageDAO dao;

    /**
     * Pre-test setup: initializes the <code>SQLServerMessageDAO</code> instance, config manager, and database
     * connection for the next step.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        dao = new SQLServerMessageDAO("message.dao.config");
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor_null_namespace() throws Exception {
        try {
            new SQLServerMessageDAO(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor_empty_namespace() throws Exception {
        try {
            new SQLServerMessageDAO("   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>InstantiationException</code> when passed an empty namespace.
     */
    public void test_ctor_missing_namespace() {
        try {
            new SQLServerMessageDAO("no.such.namespace");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>InstantiationException</code> when the configuration is invalid.
     */
    public void test_ctor_invalid_config() {
        try {
            new SQLServerMessageDAO("invalid.config");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    // the normal operation of the constructor is tested elsewhere, so no need for a special test

    /**
     * Tests that <code>findMessages</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> search criteria.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findMessages_null_search_criteria() throws Exception {
        try {
            dao.findMessages(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>findMessages</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findMessages() throws Exception {
        Date now = new Date();
        addMessage("1", "first category", "first content type", now, "first message");
        addMessage("2", "second category", "second content type", now, "second message");

        final Filter filter = new EqualToFilter("guid", "1");

        Message[] messages =
            dao.findMessages(new RSSSearchCriteriaTranslator().assembleSearchCriteriaDTO(new SearchCriteria() {
                    public Filter getSearchFilter() {
                        return filter;
                    }
                }));

        assertEquals("one message should be returned", 1, messages.length);
        assertEquals("the GUID should be 1", "1", messages[0].getGuid());
        assertEquals("the category should be first category", "first category", messages[0].getCategory());
        assertEquals("the content type should be first content type",
                     "first content type", messages[0].getContentType());
        assertEquals("the content should be first message", "first message", messages[0].getContent());
    }

    /**
     * Tests that <code>createMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createMessage_null_message() throws Exception {
        try {
            dao.createMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>createMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createMessage() throws Exception {
        Date timestamp = new Date();
        dao.createMessage(new MessageImpl("1", "category", "content type", "message", timestamp));

        assertTrue("the message should be created",
                   messageExists("1", "category", "content type", "message", timestamp));
    }

    /**
     * Tests that <code>updateMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateMessage_null_message() throws Exception {
        try {
            dao.updateMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>updateMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateMessage() throws Exception {
        Date now = new Date();
        addMessage("1", "initial category", "initial content type", now, "initial message");

        dao.updateMessage(new MessageImpl("1", "new category", "new content type", "new message", now));

        assertTrue("the message should be updated",
                   messageExists("1", "new category", "new content type", "new message", now));
    }

    /**
     * Tests that <code>deleteMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteMessage_null_message() throws Exception {
        try {
            // your code here
            dao.deleteMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>deleteMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteMessage() throws Exception {
        Date timestamp = new Date();
        Message message = new MessageImpl("1", "category", "content type", "message", timestamp);

        dao.createMessage(message);
        assertTrue("the message should now exist",
                   messageExists("1", "category", "content type", "message", timestamp));
        dao.deleteMessage(message);
        assertFalse("the message should no longer exist",
                    messageExists("1", "category", "content type", "message", timestamp));
    }
}
