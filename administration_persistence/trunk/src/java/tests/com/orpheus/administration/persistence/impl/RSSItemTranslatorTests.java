/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Message;

import com.topcoder.util.rssgenerator.RSSCategory;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.RSSLink;
import com.topcoder.util.rssgenerator.RSSText;

import com.topcoder.util.rssgenerator.impl.RSSCategoryImpl;
import com.topcoder.util.rssgenerator.impl.RSSItemImpl;
import com.topcoder.util.rssgenerator.impl.RSSLinkImpl;
import com.topcoder.util.rssgenerator.impl.RSSObjectImpl;
import com.topcoder.util.rssgenerator.impl.RSSTextImpl;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>RSSItemTranslator</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RSSItemTranslatorTests extends TestCase {
    /**
     * The translator to use for the test.
     */
    private static final RSSItemTranslator TRANSLATOR = new RSSItemTranslator();

    /**
     * Tests that <code>assembleMessageVO</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> DTO.
     */
    public void test_assembleMessageVO_null_dto() {
        try {
            TRANSLATOR.assembleMessageVO(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>assembleMessageVO</code> method.
     */
    public void test_assembleMessageVO() {
        Date timestamp = new Date();

        RSSItem item = (RSSItem) TRANSLATOR.assembleMessageVO(new MessageImpl("guid", "category", "content type",
                                                                              "content", timestamp));

        // GUID
        assertEquals("ID should be guid", "guid", item.getId());

        // category
        RSSCategory[] categories = item.getCategories();
        assertEquals("there should be 1 category", 1, categories.length);
        assertEquals("the category name should be category", "category", categories[0].getName());

        // content type
        assertEquals("the link should be type content type", "content type", item.getSelfLink().getMimeType());

        // content
        assertEquals("the description should equal content", "content", item.getDescription().getElementText());

        // timestamp
        assertEquals("the timestamp should equal the timestamp", timestamp, item.getPublishedDate());
    }

    /**
     * Tests that <code>assembleMessageDTO</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> VO.
     */
    public void test_assembleMessageDTO_null_vo() {
        try {
            TRANSLATOR.assembleMessageDTO(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>assembleMessageDTO</code> method.
     */
    public void test_assembleMessageDTO() {
        Date timestamp = new Date();
        RSSItem item = new RSSItemImpl();

        item.setId("guid");

        RSSCategory category = new RSSCategoryImpl(new RSSObjectImpl());
        category.setName("category");
        item.addCategory(category);

        RSSLink link = new RSSLinkImpl(new RSSObjectImpl());
        link.setMimeType("content type");
        item.setSelfLink(link);

        RSSText text = new RSSTextImpl(new RSSObjectImpl());
        text.setElementText("content");
        item.setDescription(text);

        item.setPublishedDate(timestamp);

        Message message = TRANSLATOR.assembleMessageDTO(item);
        assertEquals("getGuid should equal guid", "guid", message.getGuid());
        assertEquals("getCategory should equal category", "category", message.getCategory());
        assertEquals("getContentType should equal content type", "content type", message.getContentType());
        assertEquals("getContent should equal content", "content", message.getContent());
        assertEquals("getTimestamp should equal timestamp", timestamp, message.getTimestamp());
    }
}
