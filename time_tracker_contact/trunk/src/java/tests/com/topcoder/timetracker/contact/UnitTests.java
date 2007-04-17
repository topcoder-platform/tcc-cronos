/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.contact.ejb.AddressBeanTestAcc;
import com.topcoder.timetracker.contact.ejb.AddressBeanTestExp;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegateTestAcc;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegateTestExp;
import com.topcoder.timetracker.contact.ejb.ContactBeanTestAcc;
import com.topcoder.timetracker.contact.ejb.ContactBeanTestExp;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegateTestAcc;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegateTestExp;
import com.topcoder.timetracker.contact.persistence.DAOHelperTestAcc;
import com.topcoder.timetracker.contact.persistence.DAOHelperTestExp;
import com.topcoder.timetracker.contact.persistence.InformixAddressDAOTestAcc;
import com.topcoder.timetracker.contact.persistence.InformixAddressDAOTestExp;
import com.topcoder.timetracker.contact.persistence.InformixContactDAOTestAcc;
import com.topcoder.timetracker.contact.persistence.InformixContactDAOTestExp;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTests extends TestCase {
    /**
     * <p>Aggregates all Unit test cases.</p>
     *
     * @return All Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AssociationExceptionTestAcc.class);
        suite.addTestSuite(ContactExceptionTestAcc.class);
        suite.addTestSuite(ConfigurationExceptionTestAcc.class);
        suite.addTestSuite(IDGenerationExceptionTestAcc.class);
        suite.addTestSuite(InvalidPropertyExceptionTestAcc.class);
        suite.addTestSuite(EntityNotFoundExceptionTestAcc.class);
        suite.addTestSuite(AuditExceptionTestAcc.class);
        suite.addTestSuite(PersistenceExceptionTestAcc.class);
        suite.addTestSuite(BatchOperationExceptionTestAcc.class);

        suite.addTestSuite(HelperTestAcc.class);

        suite.addTestSuite(StateTestAcc.class);
        suite.addTestSuite(StateTestExp.class);
        suite.addTestSuite(CountryTestAcc.class);
        suite.addTestSuite(CountryTestExp.class);
        suite.addTestSuite(AddressTestAcc.class);
        suite.addTestSuite(AddressTestExp.class);
        suite.addTestSuite(ContactTestAcc.class);
        suite.addTestSuite(ContactTestExp.class);

        suite.addTestSuite(AddressTypeTestAcc.class);
        suite.addTestSuite(ContactTypeTestAcc.class);

        suite.addTestSuite(DAOHelperTestAcc.class);
        suite.addTestSuite(DAOHelperTestExp.class);

        suite.addTestSuite(InformixAddressDAOTestAcc.class);
        suite.addTestSuite(InformixContactDAOTestAcc.class);
        suite.addTestSuite(AddressBeanTestAcc.class);
        suite.addTestSuite(ContactBeanTestAcc.class);
        suite.addTestSuite(AddressManagerLocalDelegateTestAcc.class);
        suite.addTestSuite(ContactManagerLocalDelegateTestAcc.class);
        suite.addTestSuite(AddressFilterFactoryTestAcc.class);
        suite.addTestSuite(ContactFilterFactoryTestAcc.class);

        suite.addTestSuite(AddressFilterFactoryTestExp.class);
        suite.addTestSuite(ContactFilterFactoryTestExp.class);
        suite.addTestSuite(InformixAddressDAOTestExp.class);
        suite.addTestSuite(InformixContactDAOTestExp.class);
        suite.addTestSuite(AddressBeanTestExp.class);
        suite.addTestSuite(ContactBeanTestExp.class);
        suite.addTestSuite(AddressManagerLocalDelegateTestExp.class);
        suite.addTestSuite(ContactManagerLocalDelegateTestExp.class);

        suite.addTestSuite(DemoTest.class);
        return suite;
    }
}
