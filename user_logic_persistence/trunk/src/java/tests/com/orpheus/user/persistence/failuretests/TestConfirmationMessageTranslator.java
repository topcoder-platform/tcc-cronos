/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.orpheus.user.persistence.impl.ConfirmationMessageTranslator;

import junit.framework.TestCase;


/**
 * Unit tests for ConfirmationMessageTranslator class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestConfirmationMessageTranslator extends TestCase {
    /** ConfirmationMessageTranslator used to test. */
    private ConfirmationMessageTranslator trans;

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        trans = new ConfirmationMessageTranslator();
    }

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests assembleVO(Object dataTransferObject) method with null Object dataTransferObject,
     * Expected IllegalArgumentException.
     */
    public void testAssembleVO_NullDataTransferObject() {
        try {
            trans.assembleVO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleVO(Object dataTransferObject) method with invalid Object dataTransferObject
     * type, Expected IllegalArgumentException.
     */
    public void testAssembleVO_InvalidDataTransferObjectType() {
        try {
            trans.assembleVO(new Object());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleVO(Object dataTransferObject) method with invalid Object dataTransferObject,
     * Expected TranslationException.
     */
    public void testAssembleVO_InvalidDataTransferObject() {
        ConfirmationMessageDTO dto = new ConfirmationMessageDTO();

        try {
            trans.assembleVO(dto);
            fail("TranslationException should be thrown.");
        } catch (TranslationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleDTO(Object valueObject) method with null Object valueObject, Expected
     * IllegalArgumentException.
     */
    public void testAssembleDTO_NullValueObject() {
        try {
            trans.assembleDTO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleDTO(Object valueObject) method with invalid Object valueObject, Expected
     * IllegalArgumentException.
     */
    public void testAssembleDTO_InvalidValueObject() {
        try {
            trans.assembleDTO(new Object());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
