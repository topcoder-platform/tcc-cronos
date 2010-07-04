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
import com.topcoder.direct.services.view.action.contest.launch.ProjectBudgetModifyingAction;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.DirectServiceFacade;
import com.topcoder.service.facade.direct.ProjectBudget;

/**
 * <p>
 * Accuracy tests for class <code>ProjectBudgetModifyingAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ProjectBudgetModifyingActionAccTest {
    /**
     * <p>
     * Represent the ProjectBudgetModifyingAction instance that used to call its method for test. It
     * will be initialized in setUp().
     * </p>
     */
    private ProjectBudgetModifyingAction impl;

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
        impl = new ProjectBudgetModifyingAction();
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
     * Accuracy test for the constructor <code>ProjectBudgetModifyingAction()</code>.<br>
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
        impl.setBillingProjectId(2);
        impl.setChangedAmount(5.7);

        ProjectBudget budget = new ProjectBudget();

        EasyMock.expect(
            mock.updateProjectBudget((TCSubject) EasyMock.isNull(), EasyMock.eq(2L), EasyMock.eq(5.7d)))
                .andReturn(budget);
        impl.setDirectServiceFacade(mock);
        EasyMock.replay(mock);

        impl.executeAction();

        Object res = impl.getResult();

        assertNotNull("The result object should not be null", res);
        assertTrue("The result should be ProjectBudget type.", res instanceof ProjectBudget);
        assertEquals("the result is incorrect", budget, res);

        EasyMock.verify(mock);
    }

    /**
     * <p>
     * Accuracy test for the setter and getter of contestId.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfBillingProjectId_Accuracy() {
        impl.setBillingProjectId(5);

        assertEquals("The return result should be same as ", 5, impl.getBillingProjectId());
    }

    /**
     * <p>
     * Accuracy test for the setter and getter of changedAmount.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetterAndSetterOfChangedAmount_Accuracy() {
        impl.setChangedAmount(5.7);

        assertEquals("The return result should be same as ", 5.7, impl.getChangedAmount(), 0.00001);
    }
}
