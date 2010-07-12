/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.action.contest.launch.ContestsLinkingAction;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.DirectServiceFacade;

/**
 * <p>
 * Accuracy tests for class <code>ContestsLinkingAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ContestsLinkingActionAccTest {
    /**
     * <p>
     * Represent the ContestsLinkingAction instance that used to call its method for test. It will
     * be initialized in setUp().
     * </p>
     */
    private ContestsLinkingAction impl;

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
        impl = new ContestsLinkingAction();
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
     * Accuracy test for the constructor <code>ContestsLinkingAction()</code>.<br>
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
        long[] parentProjectIds = new long[] {1L, 2L, 3L};
        impl.setParentProjectIds(parentProjectIds);
        long[] childProjectIds = new long[] {1L, 2L, 3L};
        impl.setChildProjectIds(childProjectIds);

        mock.updateProjectLinks((TCSubject) EasyMock.isNull(), EasyMock.eq(2L), EasyMock
                .aryEq(parentProjectIds), EasyMock.aryEq(childProjectIds));
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
     * Accuracy test for the setter and getter of parentProjectIds.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfParentProjectIds_Accuracy() {
        long[] parentProjectIds = new long[] {1L, 2L, 3L};

        impl.setParentProjectIds(parentProjectIds);

        assertEquals("The return result should be same as ", parentProjectIds, impl.getParentProjectIds());
    }

    /**
     * <p>
     * Accuracy test for the setter and getter of childProjectIds.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfChildProjectIds_Accuracy() {
        long[] childProjectIds = new long[] {1L, 2L, 3L};

        impl.setChildProjectIds(childProjectIds);

        assertEquals("The return result should be same as ", childProjectIds, impl.getChildProjectIds());
    }
}
