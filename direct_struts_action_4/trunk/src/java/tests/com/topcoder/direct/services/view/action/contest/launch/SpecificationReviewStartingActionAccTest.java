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
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewStartingAction;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * Accuracy tests for class <code>SpecificationReviewStartingAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class SpecificationReviewStartingActionAccTest {
    /**
     * <p>
     * Represent the SpecificationReviewStartingAction instance that used to call its method for
     * test. It will be initialized in setUp().
     * </p>
     */
    private SpecificationReviewStartingAction impl;

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
        impl = new SpecificationReviewStartingAction();
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
     * Accuracy test for the constructor <code>SpecificationReviewStartingAction()</code>.<br>
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
        FullProjectData data = new FullProjectData();

        EasyMock.expect(mock.createSpecReview((TCSubject) EasyMock.isNull(), EasyMock.eq(2L)))
                .andReturn(data);
        mock.markSoftwareContestReadyForReview((TCSubject) EasyMock.isNull(), EasyMock.eq(2L));
        impl.setContestServiceFacade(mock);
        EasyMock.replay(mock);

        impl.executeAction();

        Object res = impl.getResult();

        assertTrue("The result object should be null", res == null);        
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
