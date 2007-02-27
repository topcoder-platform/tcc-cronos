/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.AdministrationExceptionUnitTests;

import com.orpheus.administration.persistence.impl.AdminMessageTests;
import com.orpheus.administration.persistence.impl.AdminMessageTranslatorTests;
import com.orpheus.administration.persistence.impl.AdminSummaryImplTests;
import com.orpheus.administration.persistence.impl.MessageImplTests;
import com.orpheus.administration.persistence.impl.PendingWinnerImplTests;
import com.orpheus.administration.persistence.impl.RSSItemTranslatorTests;
import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslatorTests;
import com.orpheus.administration.persistence.impl.SearchCriteriaDTOImplTests;
import com.orpheus.administration.persistence.impl.SQLServerAdminDataDAOTests;
import com.orpheus.administration.persistence.impl.SQLServerMessageDAOTests;

import com.orpheus.administration.persistence.impl.filter.AndFilterTests;
import com.orpheus.administration.persistence.impl.filter.BetweenFilterTests;
import com.orpheus.administration.persistence.impl.filter.EqualToFilterTests;
import com.orpheus.administration.persistence.impl.filter.GreaterThanFilterTests;
import com.orpheus.administration.persistence.impl.filter.GreaterThanOrEqualToFilterTests;
import com.orpheus.administration.persistence.impl.filter.InFilterTests;
import com.orpheus.administration.persistence.impl.filter.LessThanFilterTests;
import com.orpheus.administration.persistence.impl.filter.LessThanOrEqualToFilterTests;
import com.orpheus.administration.persistence.impl.filter.LikeFilterTests;
import com.orpheus.administration.persistence.impl.filter.NotFilterTests;
import com.orpheus.administration.persistence.impl.filter.NullFilterTests;
import com.orpheus.administration.persistence.impl.filter.OrFilterTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all unit test cases for the <i>Administration Persistence</i> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class UnitTests extends TestCase {
    /**
     * Returns the unit test suite for the <i>Administration Persistence</i> component.
     *
     * @return the unit test suite for the <i>Administration Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // this test should run first before DAOFactory caches values from other tests
        suite.addTestSuite(DAOFactoryTests.class);

        // the "impl" tests
        suite.addTestSuite(AdminMessageTests.class);
        suite.addTestSuite(AdminMessageTranslatorTests.class);
        suite.addTestSuite(AdminSummaryImplTests.class);
        suite.addTestSuite(MessageImplTests.class);
        suite.addTestSuite(PendingWinnerImplTests.class);
        suite.addTestSuite(RSSItemTranslatorTests.class);
        suite.addTestSuite(RSSSearchCriteriaTranslatorTests.class);
        suite.addTestSuite(SQLServerAdminDataDAOTests.class);
        suite.addTestSuite(SQLServerMessageDAOTests.class);
        suite.addTestSuite(SearchCriteriaDTOImplTests.class);

        // the filter tests
        addFilterTests(suite);

        // the main package tests
        suite.addTestSuite(AdministrationExceptionUnitTests.class);
        suite.addTestSuite(AdminDataBeanTests.class);
        suite.addTestSuite(DuplicateEntryExceptionTests.class);
        suite.addTestSuite(EntryNotFoundExceptionTests.class);
        suite.addTestSuite(InstantiationExceptionTests.class);
        suite.addTestSuite(InvalidEntryExceptionTests.class);
        suite.addTestSuite(MessageBeanTests.class);
        suite.addTestSuite(ObjectFactoryHelpersTests.class);
        suite.addTestSuite(PersistenceExceptionTests.class);
        suite.addTestSuite(TranslationExceptionTests.class);

        // the demo (part 1)
        suite.addTestSuite(AdminDataDemo.class);

        // comment the following tests if you have not deployed the EJB on an application server
        suite.addTestSuite(RemoteOrpheusMessageDataStoreTests.class);
        suite.addTestSuite(RemoteOrpheusMessengerPluginTests.class);
        suite.addTestSuite(MessageDemo.class);

        suite.addTestSuite(LocalOrpheusMessageDataStoreTests.class);
        suite.addTestSuite(LocalOrpheusMessengerPluginTests.class);

        return suite;
    }

    /**
     * Adds the filter tests to the suite. This method exists to get around the 30-statement limitation of the
     * coding standards.
     *
     * @param suite the test suite
     */
    private static void addFilterTests(TestSuite suite) {
        suite.addTestSuite(AndFilterTests.class);
        suite.addTestSuite(BetweenFilterTests.class);
        suite.addTestSuite(EqualToFilterTests.class);
        suite.addTestSuite(GreaterThanFilterTests.class);
        suite.addTestSuite(GreaterThanOrEqualToFilterTests.class);
        suite.addTestSuite(InFilterTests.class);
        suite.addTestSuite(LessThanFilterTests.class);
        suite.addTestSuite(LessThanOrEqualToFilterTests.class);
        suite.addTestSuite(LikeFilterTests.class);
        suite.addTestSuite(NotFilterTests.class);
        suite.addTestSuite(NullFilterTests.class);
        suite.addTestSuite(OrFilterTests.class);
    }
}
