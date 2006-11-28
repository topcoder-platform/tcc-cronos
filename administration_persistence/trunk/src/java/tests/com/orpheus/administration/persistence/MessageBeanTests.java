/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;


import com.orpheus.administration.persistence.impl.MessageImpl;
import com.orpheus.administration.persistence.impl.RSSItemTranslator;
import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslator;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import java.util.Date;

/**
 * Unit tests for the <code>MessageBean</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class MessageBeanTests extends MessageTestBase {
    /**
     * The search criteria translator.
     */
    private static final SearchCriteriaTranslator SEARCH_TRANSLATOR = new RSSSearchCriteriaTranslator();

    /**
     * The item translator.
     */
    private static final MessageTranslator MESSAGE_TRANSLATOR = new RSSItemTranslator();

    /**
     * The message bean to use for the tests.
     */
    private static final MessageBean BEAN = new MessageBean();


    /**
     * Tests that <code>findMessages</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> criteria.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findMessages_null_criteria() throws Exception {
        try {
            BEAN.findMessages(null);
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
            BEAN.findMessages(SEARCH_TRANSLATOR.assembleSearchCriteriaDTO(new SearchCriteria() {
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
     * <code>null</code> item.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createMessage_null_message() throws Exception {
        try {
            BEAN.createMessage(null);
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
        BEAN.createMessage(new MessageImpl("1", "category", "content type", "message", timestamp));

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
            BEAN.updateMessage(null);
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

        BEAN.updateMessage(new MessageImpl("1", "new category", "new content type", "new message", now));

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
            BEAN.deleteMessage(null);
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

        BEAN.createMessage(message);
        assertTrue("the message should now exist",
                   messageExists("1", "category", "content type", "message", timestamp));
        BEAN.deleteMessage(message);
        assertFalse("the message should no longer exist",
                    messageExists("1", "category", "content type", "message", timestamp));
    }
}
