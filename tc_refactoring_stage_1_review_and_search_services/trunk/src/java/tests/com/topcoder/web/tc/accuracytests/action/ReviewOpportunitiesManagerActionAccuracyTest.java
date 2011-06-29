/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.action;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.ReviewOpportunitiesManager;
import com.topcoder.web.tc.action.ReviewOpportunitiesManagerAction;

import com.topcoder.web.tc.dto.ReviewOpportunityDTO;
import com.topcoder.web.tc.implement.ReviewOpportunitiesManagerImpl;

/**
 * <p>
 * Accuracy test for ReviewOpportunitiesManagerAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewOpportunitiesManagerActionAccuracyTest extends StrutsSpringTestCase {
    /**
     * Represents the instance of ReviewOpportunitiesManagerAction used in test.
     */
    private ReviewOpportunitiesManagerAction action;

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        action = (ReviewOpportunitiesManagerAction) applicationContext.getBean("reviewOpportunitiesManagerAction");
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Accuracy test for ReviewOpportunitiesManagerAction(). The instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("The instance should be created.", new ReviewOpportunitiesManagerAction());
    }

    /**
     * <p>
     * Accuracy test for setReviewOpportunitiesManager(ReviewOpportunitiesManager reviewOpportunitiesManager).
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetReviewOpportunitiesManager() throws Exception {
        ReviewOpportunitiesManager manager = new ReviewOpportunitiesManagerImpl();
        action.setReviewOpportunitiesManager(manager);

        Field managerField = action.getClass().getDeclaredField("reviewOpportunitiesManager");
        managerField.setAccessible(true);

        assertEquals("reviewOpportunitiesManager should be set", manager, managerField.get(action));
    }

    /**
     * <p>
     * Accuracy tests the execute() method.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Conceptualization\"],"
            + "\"subtype\":[\"System Assembly\",\"Conceptualization\"],"
            + "\"catalog\":[\"Design\",\"UI Development\"],"
            + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2011/07/01\"},"
            + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
            + "\"reviewEndDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
            + "\"paymentStart\":400,\"paymentEnd\":1000},\"columnName\":\"primaryReviewerPayment\","
            + "\"sortingOrder\":\"ASCENDING\",\"pageNumber\":1,\"pageSize\":1}";

        request.setParameter("parameter", jsonString);
        action.setServletRequest(request);
        action.execute();

        List<ReviewOpportunityDTO> result = action.getReviewOpportunities();
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
            .getNumberOfReviewPositionsAvailable());
    }
}
