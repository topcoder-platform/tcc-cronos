/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewResultRetrievalAction;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * Accuracy tests for class <code>SpecificationReviewResultRetrievalAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class SpecificationReviewResultRetrievalActionAccTest {
    /**
     * <p>
     * Represent the SpecificationReviewResultRetrievalAction instance that used to call its method
     * for test. It will be initialized in setUp().
     * </p>
     */
    private SpecificationReviewResultRetrievalAction impl;

    /**
     * <p>
     * Set the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new SpecificationReviewResultRetrievalAction();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SpecificationReviewResultRetrievalAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testConstructor_Accuracy() {
        assertNotNull("The instance should be created successfully", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>executeAction()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecuteAction_Accuracy() throws Exception {
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));

        ContestServiceFacade mock = EasyMock.createMock(ContestServiceFacade.class);

        impl.prepare();
        impl.setContestId(2);
        ScorecardReviewData data = new ScorecardReviewData();

        EasyMock.expect(mock.getScorecardAndReview((TCSubject) EasyMock.isNull(), EasyMock.eq(2L)))
                .andReturn(data);
        impl.setContestServiceFacade(mock);
        EasyMock.replay(mock);

        impl.executeAction();

        Object res = impl.getResult();

        assertNotNull("The result object should not be null", res);
        assertTrue("The result should be ScorecardReviewData type.", res instanceof ScorecardReviewData);
        assertEquals("the result is incorrect", data, res);

        EasyMock.verify(mock);
    }

    /**
     * <p>
     * Accuracy test for the setter and getter of contestId.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfContestId_Accuracy() {
        impl.setContestId(5);

        assertEquals("The return result should be same as ", 5, impl.getContestId());
    }
}
