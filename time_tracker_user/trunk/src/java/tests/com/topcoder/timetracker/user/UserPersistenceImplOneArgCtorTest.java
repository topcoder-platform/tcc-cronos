/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;


/**
 * <p>
 * Tests the UserPersistenceImpl class, when instantiated with the one-arg constructor.
 * Most test methods are actually in UserPersistenceImplTestCase.
 * </p>
 *
 * @see UserPersistenceImpl
 * @see UserPersistenceImplTestCase
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserPersistenceImplOneArgCtorTest extends UserPersistenceImplTestCase {


    /**
     * Provides a new instance of a UserPersistenceImpl object. In this class, the one-arg
     * constructor is used to instantiate the object.
     * @return a new instance of the UserPersistenceImpl class
     * @throws Exception never under normal conditions.
     */
    protected UserPersistenceImpl createUserPersistenceImpl() throws Exception {
        return new UserPersistenceImpl(NAMESPACE);
    }


    /**
     * Test that the 1-arg constructor throws NullPointerException if namespace is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test1ArgCtorThrowsNPE() throws Exception {

        try {
            new UserPersistenceImpl(null);
            fail("1-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 1-arg constructor throws IllegalArgumentException if namespace is the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test1ArgCtorThrowsIAE() throws Exception {

        try {
            new UserPersistenceImpl("");
            fail("1-arg ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 1-arg constructor throws ConfigurationException if any errors occur during
     * initialization.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test1ArgCtorThrowsConfigurationException() throws Exception {

        try {
            new UserPersistenceImpl("DOES NOT EXIST");
            fail("1-arg ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }


        // This should NOT throw IllegalArgumentException - since we don't trim
        // it should throw ConfigurationException
        try {
            new UserPersistenceImpl(" ");
            fail("1-arg ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }
}
