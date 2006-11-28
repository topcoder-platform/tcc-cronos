/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.persistence.impl.MessageImpl;
import com.orpheus.administration.persistence.impl.RSSItemTranslator;
import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslator;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.RSSCategory;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.SearchCriteria;

import java.util.Date;

/**
 * Unit tests for the <code>OrpheusMessageDataStore</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class OrpheusMessageDataStoreTests extends MessageTestBase {
    /**
     * The search criteria translator.
     */
    private static final SearchCriteriaTranslator SEARCH_TRANSLATOR = new RSSSearchCriteriaTranslator();

    /**
     * The item translator.
     */
    private static final MessageTranslator MESSAGE_TRANSLATOR = new RSSItemTranslator();

    /**
     * The data store to use for the test. Initialized in {@link #setUp setUp}.
     */
    private OrpheusMessageDataStore store;

    /**
     * Retrieves the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test.
     *
     * @return the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    abstract OrpheusMessageDataStore getInstance() throws Exception;

    /**
     * Pre-test setup.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        store = getInstance();
    }

    /**
     * Tests that <code>ejbFindMessages</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> criteria.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbFindMessages_null_criteria() throws Exception {
        try {
            getInstance().ejbFindMessages(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>ejbFindMessages</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbFindMessages() throws Exception {
        Date now = new Date();
        addMessage("1", "first category", "first content type", now, "first message");
        addMessage("2", "second category", "second content type", now, "second message");

        final Filter filter = new EqualToFilter("guid", "1");

        Message[] messages =
            store.ejbFindMessages(SEARCH_TRANSLATOR.assembleSearchCriteriaDTO(new SearchCriteria() {
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
     * Tests that <code>ejbCreateMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> item.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbCreateMessage_null_message() throws Exception {
        try {
            store.ejbCreateMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>ejbCreateMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbCreateMessage() throws Exception {
        Date timestamp = new Date();
        store.ejbCreateMessage(new MessageImpl("1", "category", "content type", "message", timestamp));

        assertTrue("the message should be created",
                   messageExists("1", "category", "content type", "message", timestamp));
    }

    /**
     * Tests that <code>ejbUpdateMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbUpdateMessage_null_message() throws Exception {
        try {
            store.ejbUpdateMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>ejbUpdateMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbUpdateMessage() throws Exception {
        Date now = new Date();
        addMessage("1", "initial category", "initial content type", now, "initial message");

        store.ejbUpdateMessage(new MessageImpl("1", "new category", "new content type", "new message", now));

        assertTrue("the message should be updated",
                   messageExists("1", "new category", "new content type", "new message", now));
    }

    /**
     * Tests that <code>ejbDeleteMessage</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> message.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbDeleteMessage_null_message() throws Exception {
        try {
            store.ejbDeleteMessage(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>ejbDeleteMessage</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ejbDeleteMessage() throws Exception {
        Date timestamp = new Date();
        Message message = new MessageImpl("1", "category", "content type", "message", timestamp);

        store.ejbCreateMessage(message);
        assertTrue("the message should now exist",
                   messageExists("1", "category", "content type", "message", timestamp));
        store.ejbDeleteMessage(message);
        assertFalse("the message should no longer exist",
                    messageExists("1", "category", "content type", "message", timestamp));
    }

    /**
     * Tests the <code>findFeeds</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findFeeds() throws Exception {
        assertEquals("findFeeds should return an empty array", 0, store.findFeeds(null).length);
    }

    /**
     * Tests that <code>findItems</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> criteria.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findItems_null_criteria() throws Exception {
        try {
            store.findItems(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>findItems</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findItems() throws Exception {
        Date now = new Date();
        addMessage("1", "first category", "first content type", now, "first message");
        addMessage("2", "second category", "second content type", now, "second message");

        final Filter filter = new EqualToFilter("guid", "1");

        RSSItem[] items = store.findItems(new SearchCriteria() {
                public Filter getSearchFilter() {
                    return filter;
                }
            });

        assertEquals("there should be 1 item", 1, items.length);

        // GUID
        assertEquals("ID should be guid", "1", items[0].getId());

        // category
        RSSCategory[] categories = items[0].getCategories();
        assertEquals("there should be 1 category", 1, categories.length);
        assertEquals("the category name should be category", "first category", categories[0].getName());

        // content type
        assertEquals("the link should be type content type", "first content type",
                     items[0].getSelfLink().getMimeType());

        // content
        assertEquals("the description should equal content", "first message",
                     items[0].getDescription().getElementText());
    }

    /**
     * Tests the <code>createFeed</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createFeed() throws Exception {
        try {
            store.createFeed(null);
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>createItem</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> item.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createItem_null_item() throws Exception {
        try {
            store.createItem(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>createItem</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createItem() throws Exception {
        Date timestamp = new Date();
        store.createItem((RSSItem) MESSAGE_TRANSLATOR.
                         assembleMessageVO(new MessageImpl("1", "category", "content type", "message", timestamp)));

        assertTrue("the message should be created",
                   messageExists("1", "category", "content type", "message", timestamp));
    }

    /**
     * Tests the <code>updateFeed</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateFeed() throws Exception {
        try {
            store.updateFeed(null);
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>updateItem</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> item.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateItem_null_item() throws Exception {
        try {
            store.updateItem(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>updateItem</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateItem() throws Exception {
        Date now = new Date();
        addMessage("1", "initial category", "initial content type", now, "initial message");

        store.updateItem((RSSItem) MESSAGE_TRANSLATOR.
                         assembleMessageVO(new MessageImpl("1", "new category", "new content type",
                                                           "new message", now)));

        assertTrue("the message should be updated",
                   messageExists("1", "new category", "new content type", "new message", now));
    }

    /**
     * Tests the <code>deleteFeed</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteFeed() throws Exception {
        try {
            store.deleteFeed(null);
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>deleteItem</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> item.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteItem_null_item() throws Exception {
        try {
            store.deleteItem(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>deleteItem</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteItem() throws Exception {
        Date timestamp = new Date();
        Message message = new MessageImpl("1", "category", "content type", "message", timestamp);
        RSSItem item = (RSSItem) MESSAGE_TRANSLATOR.assembleMessageVO(message);

        store.createItem(item);
        assertTrue("the message should now exist",
                   messageExists("1", "category", "content type", "message", timestamp));
        store.deleteItem(item);
        assertFalse("the message should no longer exist",
                    messageExists("1", "category", "content type", "message", timestamp));
    }
}
