/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.TestCase;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import org.easymock.EasyMock;

/**
 * Base test case to test dao implementations.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class BaseTest extends TestCase {
    /**
     * Session context to use in tests.
     */
    protected SessionContext context;

    /**
     * Session context to use in tests.
     */
    protected EntityManager em;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        context = EasyMock.createMock(SessionContext.class);
        em = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(context.lookup("persistence_unit")).andReturn(em);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        EasyMock.reset(context, em);
        super.tearDown();
    }
}
