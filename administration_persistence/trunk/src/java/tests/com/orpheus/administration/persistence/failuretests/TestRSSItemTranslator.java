/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.RSSItemTranslator;

import junit.framework.TestCase;
/**
 * Unit tests for RSSItemTranslator class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestRSSItemTranslator extends TestCase {
    /**
     * RSSItemTranslator used to test.
     */
    private RSSItemTranslator trans;
    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        trans = new RSSItemTranslator();
    }

    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests assembleMessageVO(Message messageDataTransferObject) method with null Message messageDataTransferObject,
     * Expected IllegalArgumentException.
     */
    public void testAssembleMessageVO_NullMessageDataTransferObject() {
        try {
            trans.assembleMessageVO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleMessageDTO(Object valueObject) method with null Object valueObject,
     * Expected IllegalArgumentException.
     */
    public void testAssembleMessageDTO_NullValueObject() {
        try {
            trans.assembleMessageDTO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleMessageDTO(Object valueObject) method with invalid Object valueObject,
     * Expected IllegalArgumentException.
     */
    public void testAssembleMessageDTO_InvalidValueObject() {
        try {
            trans.assembleMessageDTO(new Object());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}