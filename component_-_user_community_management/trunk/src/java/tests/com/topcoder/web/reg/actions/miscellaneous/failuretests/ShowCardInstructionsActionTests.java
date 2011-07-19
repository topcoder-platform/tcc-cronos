/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.shared.dataAccess.DataAccessConstants;

import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.actions.miscellaneous.ShowCardInstructionsAction;

import org.junit.After;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Failure tests for ShowCardInstructionsAction.
 *
 * @author gjw99
 * @version 1.0
 */
public class ShowCardInstructionsActionTests {
    /**
     * <p>The ShowCardInstructionsAction instance to test.</p>
     */
    private ShowCardInstructionsAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new ShowCardInstructionsAction();
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
    public void testExecute() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("skey", new MockBasicAuthentication(3L));
        instance.setSession(session);
        instance.execute();
        fail("exception required");
    }
}
