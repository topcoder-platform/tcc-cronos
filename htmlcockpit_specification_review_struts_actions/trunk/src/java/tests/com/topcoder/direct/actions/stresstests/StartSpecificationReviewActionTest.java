/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.direct.actions.StartSpecificationReviewAction;
import com.topcoder.security.TCSubject;

/**
 * Stress tests for <code>StartSpecificationReviewAction</code>.
 *
 * The only main method is <code>execute</code> and the only possible high load is the content.
 * This class is not thread safe so there is no thread-safety test.
 *
 * @author moon.river
 * @version 1.0
 */
public class StartSpecificationReviewActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private StartSpecificationReviewAction instance;

    /**
     * The spec review service.
     */
    private MockSpecificationReviewService service;

    /**
     * The contest service facade.
     */
    private MockContestServiceFacade serviceFacade;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        instance = new StartSpecificationReviewAction();
        service = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(service);
        serviceFacade = new MockContestServiceFacade();
        instance.setContestServiceFacade(serviceFacade);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("tcSubject", new TCSubject(1));
        instance.setSession(session);
    }

    /**
     * Test method for {@link com.topcoder.direct.actions.StartSpecificationReviewAction#execute()}.
     */
    public void testExecute() {
        instance.setStartMode("now");
        instance.setContestId(1);
        instance.setStudio(false);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            instance.execute();
        }
        long end = System.currentTimeMillis();

        // check the result
        assertEquals("The result is wrong.", Action.SUCCESS, instance.execute());

        System.out.println("StartAction : " + (end - start) + "ms");
    }

}
