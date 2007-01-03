/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;


/**
 * <p>
 * Tests that the DbUserStore class operates normally, when the class is instantiated via its
 * default constructor.  There are no actual tests in this class; this class is only responsible
 * for creating the object, which is exercised in the superclass.  For the tests to be performed
 * (not including exception tests), please see DbUserStoreTestCase.  For exception tests
 * please see DbUserStoreExceptionsTest.
 * </p>
 *
 * @see DbUserStore
 * @see DbUserStoreTestCase
 * @see DbUserStoreExceptionsTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class DbUserStoreDefaultCtorTest extends DbUserStoreTestCase {

    /**
     * Provides a new instance of a DbUserStore object. In this test class, the default (no-arg)
     * constructor is used to instantiate the object.
     * @return a new instance of the DbUserStore class
     * @throws Exception never under normal conditions.
     */
    protected DbUserStore createDbStore() throws Exception {
        DbUserStore store = new DbUserStore();
        return store;
    }
}
