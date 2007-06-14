/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.orpheus.user.persistence.impl.SQLServerUserProfileDAOTest;
import com.orpheus.user.persistence.impl.PlayerPreferencesInfoTest;
import com.orpheus.user.persistence.impl.UserTest;
import com.orpheus.user.persistence.impl.AdminTest;
import com.orpheus.user.persistence.impl.UserProfileTranslatorTest;
import com.orpheus.user.persistence.impl.ContactInfoTest;
import com.orpheus.user.persistence.impl.SponsorTest;
import com.orpheus.user.persistence.impl.PlayerTest;
import com.orpheus.user.persistence.impl.SQLServerPendingConfirmationDAOTest;
import com.orpheus.user.persistence.impl.ConfirmationMessageTranslatorTest;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTOTest;
import com.orpheus.user.persistence.ejb.UserProfileDTOTest;
import com.orpheus.user.persistence.ejb.UserProfileBeanTest;
import com.orpheus.user.persistence.ejb.PendingConfirmationBeanTest;

/**
 * <p>
 * This test case aggregates all unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all the unit tests for the component.
     * </p>
     *
     * @return a test suite containing all the unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Component demo.
        suite.addTest(Demo.suite());

        // Tests the com.orpheus.user.persistence package.
        suite.addTest(DAOFactoryTest.suite());
        suite.addTest(DuplicateEntryExceptionTest.suite());
        suite.addTest(EntryNotFoundExceptionTest.suite());
        suite.addTest(LocalOrpheusPendingConfirmationStorageTest.suite());
        suite.addTest(LocalOrpheusUserProfilePersistenceTest.suite());
        suite.addTest(ObjectInstantiationExceptionTest.suite());
        suite.addTest(OrpheusPendingConfirmationStorageTest.suite());
        suite.addTest(OrpheusUserProfilePersistenceTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());
        suite.addTest(RemoteOrpheusPendingConfirmationStorageTest.suite());
        suite.addTest(RemoteOrpheusUserProfilePersistenceTest.suite());
        suite.addTest(TranslationExceptionTest.suite());
        suite.addTest(UserConstantsTest.suite());
        suite.addTest(UserLogicPersistenceHelperTest.suite());
        suite.addTest(UserPersistenceExceptionTest.suite());

        // Tests the com.orpheus.user.persistence.ejb package.
        suite.addTest(ConfirmationMessageDTOTest.suite());
        suite.addTest(UserProfileBeanTest.suite());
        suite.addTest(UserProfileDTOTest.suite());
        suite.addTest(PendingConfirmationBeanTest.suite());

        // Tests the com.orpheus.user.persistence.impl package.
        suite.addTest(AdminTest.suite());
        suite.addTest(ConfirmationMessageTranslatorTest.suite());
        suite.addTest(ContactInfoTest.suite());
        suite.addTest(PlayerTest.suite());
        suite.addTest(PlayerPreferencesInfoTest.suite());
        suite.addTest(SponsorTest.suite());
        suite.addTest(SQLServerPendingConfirmationDAOTest.suite());
        suite.addTest(SQLServerUserProfileDAOTest.suite());
        suite.addTest(UserProfileTranslatorTest.suite());
        suite.addTest(UserTest.suite());

        return suite;
    }

}
