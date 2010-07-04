/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.action.contest.launch.ContestDeletingAction;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.DirectServiceFacade;

/**
 * <p>
 * Accuracy tests for class <code>ContestDeletingAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ContestDeletingActionAccTest {
    /**
     * <p>
     * Represent the ContestDeletingAction instance that used to call its method for test. It will
     * be initialized in setUp().
     * </p>
     */
    private ContestDeletingAction impl;

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
        impl = new ContestDeletingAction();
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
     * Accuracy test for the constructor <code>ContestDeletingAction()</code>.<br>
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

        DirectServiceFacade mock = EasyMock.createMock(DirectServiceFacade.class);

        impl.prepare();
        impl.setContestId(2);
        impl.setStudio(true);

        mock.deleteContest((TCSubject) EasyMock.isNull(), EasyMock.eq(2L), EasyMock.eq(true));
        impl.setDirectServiceFacade(mock);
        EasyMock.replay(mock);

        impl.executeAction();

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

    /**
     * <p>
     * Accuracy test for the setter and getter of studio.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfStudio_Accuracy() {
        impl.setStudio(true);

        assertTrue("The return result should be same as ", impl.isStudio());

        impl.setStudio(false);

        assertFalse("The return result should be same as ", impl.isStudio());
    }
}
