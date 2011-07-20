/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.shared.dataAccess.DataAccessConstants;

import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.actions.miscellaneous.RetrieveCardDataAction;

import org.junit.After;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Failure tests for RetrieveCardDataAction.
 *
 * @author gjw99
 * @version 1.0
 */
public class RetrieveCardDataActionTests {
    /**
     * <p>The RetrieveCardDataAction instance to test.</p>
     */
    private RetrieveCardDataAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new RetrieveCardDataAction();
        DataAccessConstants.COMMAND = "COMMAND";
        instance.setAuthenticationSessionKey("skey");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Failure tests for execute() method.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute1() throws Exception {
        instance.setDataAccess(new MockDataAccess(true));

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("skey", new MockBasicAuthentication(2L));
        instance.setSession(session);
        instance.execute();
        fail("exception required");
    }

    /**
     * Failure tests for execute() method.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute2() throws Exception {
        instance.setDataAccess(new MockDataAccess(false));

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("skey", new MockBasicAuthentication(2L));
        instance.setSession(session);
        instance.execute();
        fail("exception required");
    }
}
