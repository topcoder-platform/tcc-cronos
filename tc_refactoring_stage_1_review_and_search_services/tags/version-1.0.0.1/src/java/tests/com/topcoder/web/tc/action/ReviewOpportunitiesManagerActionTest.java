/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.ReviewOpportunitiesManager;
import com.topcoder.web.tc.dto.ReviewOpportunityDTO;
import com.topcoder.web.tc.implement.ReviewOpportunitiesManagerImpl;

/**
 * <p>
 * Tests the ReviewOpportunitiesManagerAction class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ReviewOpportunitiesManagerActionTest extends StrutsSpringTestCase {

    /**
     * Represents the action to test
     */
    private ReviewOpportunitiesManagerAction action;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
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
     * Tears down testing environment. Set variable to null.
     * </p>
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
     * Tests the default constructor. It verifies nothing.
     * </p>
     */
    public void testCtor() {
        new ReviewOpportunitiesManagerAction();
    }

    /**
     * <p>
     * Tests the checkConfiguration() method. It invokes this method before the manager is set.
     * ContestServicesConfigurationException is expected to be thrown.
     * </p>
     */
    public void testCheckConfiguration_NullManager() {
        try {
            action = new ReviewOpportunitiesManagerAction();
            action.checkConfiguration();
            fail("ContestServicesConfigurationException should be thrown");
        } catch (ContestServicesConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the checkConfiguration() method. It invokes this method after the manager is set. No exception should be
     * thrown.
     * </p>
     */
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }

    /**
     * <p>
     * Tests the setReviewOpportunitiesManager(ReviewOpportunitiesManager) method. It uses valid manager. No exception
     * should be thrown.
     * </p>
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
     * Tests the execute() method. It uses invalid JSON string. ServletException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute_Exception() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Conceptualization\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"Development\"]," + "\"registrationStart\":{\"intervalType\":\"BEFORE\"}}}";
        request.setParameter("parameter", jsonString);
        try {
            executeAction("/reviewOpportunitiesManagerAction");
            fail("ServletException should be thrown");
        } catch (ServletException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string with pagination and verifies the reviewOpportunities
     * property is set correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_WithPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Conceptualization\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"Development\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"reviewEndDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"paymentStart\":100,\"paymentEnd\":1000},\"columnName\":\"primaryReviewerPayment\","
                + "\"sortingOrder\":\"ASCENDING\",\"pageNumber\":2,\"pageSize\":1}";
        request.setParameter("parameter", jsonString);
        executeAction("/reviewOpportunitiesManagerAction");
        List<ReviewOpportunityDTO> reviewOpportunities = (List<ReviewOpportunityDTO>) findValueAfterExecute("reviewOpportunities");
        assertEquals("1 review opportunity should be returned", 1, reviewOpportunities.size());
        ReviewOpportunityDTO reviewOpportunity = reviewOpportunities.get(0);
        assertEquals("type should be returned", "Conceptualization", reviewOpportunity.getType());
        assertEquals("subtype should be returned", "Conceptualization", reviewOpportunity.getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0,
                reviewOpportunity.getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0,
                reviewOpportunity.getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, reviewOpportunity.getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", reviewOpportunity.getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", reviewOpportunity.getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", reviewOpportunity.getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1,
                reviewOpportunity.getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string without pagination and verifies the reviewOpportunities
     * property is set correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_NoPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Conceptualization\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"Development\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"reviewEndDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"paymentStart\":100,\"paymentEnd\":1000}}";
        request.setParameter("parameter", jsonString);
        executeAction("/reviewOpportunitiesManagerAction");
        List<ReviewOpportunityDTO> reviewOpportunities = (List<ReviewOpportunityDTO>) findValueAfterExecute("reviewOpportunities");
        assertEquals("2 review opportunities should be returned", 2, reviewOpportunities.size());
        ReviewOpportunityDTO reviewOpportunity = reviewOpportunities.get(0);
        assertEquals("type should be returned", "Assembly", reviewOpportunity.getType());
        assertEquals("subtype should be returned", "System Assembly", reviewOpportunity.getSubtype());
        assertEquals("primary reviewer payment should be returned", 300.0,
                reviewOpportunity.getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 200.0,
                reviewOpportunity.getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 1, reviewOpportunity.getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", reviewOpportunity.getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", reviewOpportunity.getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", reviewOpportunity.getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 2,
                reviewOpportunity.getNumberOfReviewPositionsAvailable());
    }
}
