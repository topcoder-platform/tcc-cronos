/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Tests the DbUserStore class operates normally, when the class is instantiated via its
 * two-arg constructor.  There are no actual tests in this class; this class is only responsible
 * for creating the object, which is exercised in the superclass.  For the tests to be performed
 * (not including exception tests), please see DbUserStoreTestCase.  For exception tests
 * please see DbUserStoreExceptionsTest.
 * </p>
 *
 * @see DbUserStore
 * @see DbUserStoreExceptionsTest
 * @see DbUserStoreTestCase
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class DbUserStoreTwoArgCtorTest extends DbUserStoreTestCase {

    /**
     * Provides a new instance of a DbUserStore object. In this test class, the two-arg
     * constructor is used to instantiate the object, using the standard connection name
     * and a DBConnectionFactory configured using the standard namespace name.
     * @return a new instance of the DbUserStore class constructed with the 2-arg constructor.
     * @throws Exception never under normal conditions.
     */
    protected DbUserStore createDbStore() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(UserManager.CONFIG_NAMESPACE);
        DbUserStore store = new DbUserStore(CONNECTION_NAME, factory);
        return store;
    }
}
