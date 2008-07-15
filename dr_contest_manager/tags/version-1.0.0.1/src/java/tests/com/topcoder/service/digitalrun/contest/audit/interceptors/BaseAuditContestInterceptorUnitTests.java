/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.audit.interceptors;

import javax.interceptor.InvocationContext;

import org.easymock.EasyMock;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.ContestManagerConfigurationException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.service.digitalrun.contest.MockAuditDAO;

/**
 * <p>
 * Unit tests for <code>BaseAuditContestInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseAuditContestInterceptorUnitTests extends BaseTestCase {

    /**
     * <p>
     * Instance of <code>BaseAuditContestInterceptor</code> should be created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull(new BaseAuditContestInterceptor() { });
    }

    /**
     * <p>
     * Test method {@link BaseAuditContestInterceptor#initialize(InvocationContext)}.
     * </p>
     *
     * <p>
     * See /test_files/config.properties and /test_files/Auditor.xml.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Acc() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("DigitalRunContestManagerBean");
        EasyMock.replay(CONTEXT);

        ExtendedBaseAuditContestInterceptor interceptor = new ExtendedBaseAuditContestInterceptor();
        setField(BaseAuditContestInterceptor.class, interceptor, "sessionContext", CONTEXT);

        interceptor.initialize(EasyMock.createNiceMock(InvocationContext.class));

        assertEquals("persistenceUnit", interceptor.getUnitName());
        assertEquals("TCSDEVELOPER", interceptor.getAuditorUser());
        assertTrue(interceptor.getAuditor().getAuditDAO() instanceof MockAuditDAO);
    }

    /**
     * <p>
     * Test method {@link BaseAuditContestInterceptor#initialize(InvocationContext)}.
     * </p>
     *
     * <p>
     * Configuration file does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NoSuchFile() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("NoSuchFile.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("DigitalRunContestManagerBean");
        EasyMock.replay(CONTEXT);


        BaseAuditContestInterceptor interceptor = new ExtendedBaseAuditContestInterceptor();
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
     * Test method {@link BaseAuditContestInterceptor#initialize(InvocationContext)}.
     * </p>
     *
     * <p>
     * Configuration namespace does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NoSuchNamespace() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("NoSuchNamespace");
        EasyMock.replay(CONTEXT);


        BaseAuditContestInterceptor interceptor = new ExtendedBaseAuditContestInterceptor();
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
     * Test method {@link BaseAuditContestInterceptor#getEntityManager()}.
     * </p>
     *
     * <p>
     * The session context has not been injected.
     * </p>
     */
    public void testGetEntityManager() {
        try {
            new ExtendedBaseAuditContestInterceptor().getEntityManager();
            fail("Exception expected");
        } catch (Exception e) {
            // pass
        }
    }

    /**
     * <p>
     * Extended <code>BaseAuditContestInterceptor</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class ExtendedBaseAuditContestInterceptor extends BaseAuditContestInterceptor {

        /**
         * <p>
         * Get the unit name.
         * </p>
         *
         * @return unit name.
         *
         * @throws Exception to JUnit.
         */
        public String getUnitName() throws Exception {
            return (String) getField(BaseAuditContestInterceptor.class, this, "unitName");
        }
    }
}
