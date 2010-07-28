/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.direct.actions.ViewSpecificationReviewAction;
import com.topcoder.direct.actions.ViewSpecificationReviewActionResultData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

/**
 * Stress tests for <code>ViewSpecificationReviewAction</code>.
 *
 * The only main method is <code>execute</code> and the only possible high load is the content.
 * This class is not thread safe so there is no thread-safety test.
 *
 * @author moon.river
 * @version 1.0
 */
public class ViewSpecificationReviewActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private ViewSpecificationReviewAction instance;

    /**
     * The spec review service.
     */
    private MockSpecificationReviewService service;

    /**
     * The comment service.
     */
    private MockSpecReviewCommentService commentService;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        instance = new ViewSpecificationReviewAction();
        service = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(service);
        commentService = new MockSpecReviewCommentService();
        instance.setSpecReviewCommentService(commentService);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("tcSubject", new TCSubject(1));
        instance.setSession(session);
    }

    /**
     * Test method for {@link com.topcoder.direct.actions.ViewSpecificationReviewAction#execute()}.
     */
    public void testExecute() {
        instance.setContestId(1);
        instance.setStudio(true);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            instance.execute();
        }
        long end = System.currentTimeMillis();

        // check the result
        assertEquals("The result is wrong.", Action.SUCCESS, instance.execute());

        assertNotNull("Model is wrong.", instance.getModel().getData("result"));
        assertTrue("Wrong model type", instance.getModel().getData("result") instanceof ViewSpecificationReviewActionResultData);

        ViewSpecificationReviewActionResultData result =
            (ViewSpecificationReviewActionResultData) instance.getModel().getData("result");

        assertEquals("The review status is wrong", SpecificationReviewStatus.PENDING_REVIEW,
                result.getSpecificationReviewStatus());

        assertEquals("There should be 1000 comments", 1000, result.getSpecReviewComments().size());

        System.out.println("StartAction : " + (end - start) + "ms");
    }

}
