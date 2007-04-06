/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.base.ejb.ConfigHelperTest;
import com.topcoder.timetracker.entry.base.ejb.EntryDelegateTest;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBeanTest;
import com.topcoder.timetracker.entry.base.persistence.InformixCutoffTimeDaoTest;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(InformixCutoffTimeDaoTest.class);
        suite.addTestSuite(BaseDaoTest.class);

        suite.addTestSuite(PersistenceExceptionTest.class);
        suite.addTestSuite(IdGenerationExceptionTest.class);
        suite.addTestSuite(EntryNotFoundExceptionTest.class);
        suite.addTestSuite(DuplicateEntryExceptionTest.class);
        suite.addTestSuite(ConfigurationExceptionTest.class);
        suite.addTestSuite(EntryManagerExceptionTest.class);
        suite.addTestSuite(EntrySessionBeanTest.class);
        suite.addTestSuite(EntryDelegateTest.class);
        suite.addTestSuite(CutoffTimeBeanTest.class);
        suite.addTestSuite(BaseEntryTest.class);
        
        suite.addTestSuite(ConfigHelperTest.class);
        suite.addTestSuite(ParameterCheckTest.class);
        suite.addTestSuite(DemoTest.class);
        return suite;
    }

}
