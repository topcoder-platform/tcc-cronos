/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.direct.actions.ResubmitSpecificationReviewAction;
import com.topcoder.security.TCSubject;

/**
 * Stress tests for <code>ResubmitSpecificationReviewAction</code>.
 *
 * The only main method is <code>execute</code> and the only possible high load is the content.
 * This class is not thread safe so there is no thread-safety test.
 *
 * @author moon.river
 * @version 1.0
 */
public class ResubmitSpecificationReviewActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private ResubmitSpecificationReviewAction instance;

    /**
     * The service.
     */
    private MockSpecificationReviewService service;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        instance = new ResubmitSpecificationReviewAction();
        service = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(service);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("tcSubject", new TCSubject(1));
        instance.setSession(session);
    }

    /**
     * Test method for {@link com.topcoder.direct.actions.ResubmitSpecificationReviewAction#execute()}.
     */
    public void testExecute() {
        // this actually does not test this component. This is used here for future use, if we want
        // to replace the the mock with real services, this can be used for testing that
        String content = "";
        for (int i = 0; i < 1000; i++) {
            content = content + "test";
        }
        instance.setContent(content);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            instance.execute();
        }
        long end = System.currentTimeMillis();

        // check the result
        assertEquals("The content is wrong.", content, service.getContent());
        assertEquals("The result is wrong.", Action.SUCCESS, instance.execute());

        System.out.println("Resubmit : " + (end - start) + "ms");
    }

}
