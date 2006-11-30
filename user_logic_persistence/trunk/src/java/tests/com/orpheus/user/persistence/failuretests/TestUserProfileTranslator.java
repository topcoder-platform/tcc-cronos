/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.orpheus.user.persistence.impl.UserProfileTranslator;

import com.topcoder.user.profile.UserProfile;

import junit.framework.TestCase;


/**
 * Unit tests for UserProfileTranslator class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestUserProfileTranslator extends TestCase {
    /**
     * UserProfileTranslator used to test.
     */
    private UserProfileTranslator trans;

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Helper.clearNamespaces();
        Helper.loadConfig("test_conf/failure/logging.xml");
        Helper.loadConfig("test_conf/failure/UserProfileTranslator.xml");

        trans = new UserProfileTranslator("UserProfileTranslator.valid");
    }

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        Helper.clearNamespaces();
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with null String namespace, Expected
     * IllegalArgumentException.
     */
    public void testUserProfileTranslator_NullNamespace() {
        try {
            new UserProfileTranslator(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with empty String namespace, Expected
     * IllegalArgumentException.
     */
    public void testUserProfileTranslator_EmptyNamespace() {
        try {
            new UserProfileTranslator(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testUserProfileTranslator_LostSpecNamespace() {
        try {
            new UserProfileTranslator("UserProfileTranslator.fail1");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testUserProfileTranslator_EmptySpecNamespace() {
        try {
            new UserProfileTranslator("UserProfileTranslator.fail2");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testUserProfileTranslator_LostFactoryKey() {
        try {
            new UserProfileTranslator("UserProfileTranslator.fail3");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests UserProfileTranslator(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testUserProfileTranslator_EmptyFactoryKey() {
        try {
            new UserProfileTranslator("UserProfileTranslator.fail4");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
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
        try {
            trans.assembleVO(new UserProfileDTO());
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
     * Tests assembleDTO(Object valueObject) method with invalid Object valueObject type, Expected
     * IllegalArgumentException.
     */
    public void testAssembleDTO_InvalidValueObjectType() {
        try {
            trans.assembleDTO(new Object());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests assembleDTO(Object valueObject) method with invalid Object valueObject, Expected
     * TranslationException.
     */
    public void testAssembleDTO_InvalidValueObject() {
        try {
            trans.assembleDTO(new UserProfile(new Long(-1)));
            fail("TranslationException should be thrown.");
        } catch (TranslationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
