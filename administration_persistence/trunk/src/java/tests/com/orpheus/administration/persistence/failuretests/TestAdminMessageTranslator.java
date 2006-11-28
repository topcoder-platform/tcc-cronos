/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.AdminMessageTranslator;

import junit.framework.TestCase;
/**
 * Unit tests for AdminMessageTranslator class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestAdminMessageTranslator extends TestCase {
    /**
     * AdminMessageTranslator used to test.
     */
    private AdminMessageTranslator trans;
    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        trans = new AdminMessageTranslator();
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