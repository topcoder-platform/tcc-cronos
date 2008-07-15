/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import org.easymock.EasyMock;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.ContestManagerConfigurationException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.service.digitalrun.contest.audit.interceptors.BaseAuditContestInterceptor;

/**
 * <p>
 * Failure tests for <code>BaseAuditContestInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseAuditContestInterceptorFailureTests extends BaseTestCase {

    /**
     * <p>
     * Failure test for the method <code>initialize(InvocationContext)</code> with the configuration file does not
     * exist, ContestManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitializeWithNoSuchFile() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("NoSuchFile.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("DigitalRunContestManagerBean");
        EasyMock.replay(CONTEXT);

        BaseAuditContestInterceptor interceptor = new MockBaseAuditContestInterceptor();
        setField(BaseAuditContestInterceptor.class, interceptor, "sessionContext", CONTEXT);

        try {
            interceptor.initialize(null);
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for the method <code>initialize(InvocationContext)</code> with the configuration namespace does
     * not exist, ContestManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitializeWithNoSuchNamespace() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("NoSuchNamespace");
        EasyMock.replay(CONTEXT);

        BaseAuditContestInterceptor interceptor = new MockBaseAuditContestInterceptor();
        setField(BaseAuditContestInterceptor.class, interceptor, "sessionContext", CONTEXT);

        try {
            interceptor.initialize(null);
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * This mock class extends <code>BaseAuditContestInterceptor</code> for test.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class MockBaseAuditContestInterceptor extends BaseAuditContestInterceptor {

        /**
         * <p>
         * Get the unit name.
         * </p>
         *
         * @return unit name.
         *
         * @throws Exception
         *             to JUnit.
         */
        public String getUnitName() throws Exception {
            return (String) getField(BaseAuditContestInterceptor.class, this, "unitName");
        }
    }
}
