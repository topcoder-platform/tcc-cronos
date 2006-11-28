/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Message;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>AdminMessageTranslator</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AdminMessageTranslatorTests extends TestCase {
    // the constructor is tested by the other tests, so no need for a special test

    /**
     * Tests that <code>assembleMessageVO</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> DTO.
     */
    public void test_assembleMessageVO_null_dto() {
        try {
            new AdminMessageTranslator().assembleMessageVO(null);
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
        AdminMessage vo = (AdminMessage) new AdminMessageTranslator().
            assembleMessageVO(new MessageImpl("guid", "category", "content type", "content", timestamp));

        assertEquals("GUID should be 'guid'", "guid", vo.getParameterValue(AdminMessage.GUID));
        assertEquals("category should be 'category'", "category", vo.getParameterValue(AdminMessage.CATEGORY));
        assertEquals("content type should be 'content type'",
                     "content type", vo.getParameterValue(AdminMessage.CONTENT_TYPE));
        assertEquals("content should be 'content'", "content", vo.getParameterValue(AdminMessage.CONTENT));
        assertEquals("timestamp should be '" + timestamp + "'",
                     timestamp, vo.getParameterValue(AdminMessage.TIMESTAMP));
    }

    /**
     * Tests that <code>assembleMessageDTO</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> VO.
     */
    public void test_assembleMessageDTO_null_vo() {
        try {
            new AdminMessageTranslator().assembleMessageDTO(null);
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
        AdminMessage message = new AdminMessage();
        message.setParameterValue(AdminMessage.GUID, "guid");
        message.setParameterValue(AdminMessage.CATEGORY, "category");
        message.setParameterValue(AdminMessage.CONTENT_TYPE, "content type");
        message.setParameterValue(AdminMessage.CONTENT, "content");
        message.setParameterValue(AdminMessage.TIMESTAMP, timestamp);

        Message dto = new AdminMessageTranslator().assembleMessageDTO(message);
        assertEquals("GUID should be 'guid'", "guid", dto.getGuid());
        assertEquals("category should be 'category'", "category", dto.getCategory());
        assertEquals("content type should be 'content type'", "content type", dto.getContentType());
        assertEquals("content should be 'content'", "content", dto.getContent());
        assertEquals("timestamp should be '" + timestamp + "'", timestamp, dto.getTimestamp());
    }
}
