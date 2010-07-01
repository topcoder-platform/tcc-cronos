/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import java.util.HashMap;

import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestScheduleExtensionAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.service.facade.direct.ContestScheduleExtension;


/**
 * The accuracy tests for the class {@link ActiveContestScheduleExtensionAction}.
 *
 * @author KLW
 * @version 1.0
  */
public class ActiveContestScheduleExtensionActionAccTest {
	/**
	 * The instance for testing.
	 */
	private ActiveContestScheduleExtensionAction action;
    /**
     * set up the test environment.
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
    	action = new ActiveContestScheduleExtensionAction();
    	action.prepare();
    }

    /**
     * tears down the test environment.
     * @throws java.lang.Exception if any error occurs.
     */
    @After
    public void tearDown() throws Exception {
    	action = null;
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#executeAction()}.
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
    	MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
    	action.setDirectServiceFacade(directServiceFacade);
    	action.setContestId(1);
    	action.setStudio(true);
    	action.setRegistrationExtension(2);
    	action.setMilestoneExtension(3);
    	action.setSubmissionExtension(4);
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        ContestScheduleExtension extension = directServiceFacade.getContestScheduleExtension();
        assertNotNull("The result is incorrect.",  extension);
        assertEquals("The result is incorrect.", 1, extension.getContestId());
        assertEquals("The result is incorrect.", true, extension.isStudio());
        assertEquals("The result is incorrect.", new Integer(2), extension.getExtendRegistrationBy());
        assertEquals("The result is incorrect.", new Integer(3), extension.getExtendMilestoneBy());
        assertEquals("The result is incorrect.", new Integer(4), extension.getExtendSubmissionBy());
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#ActiveContestScheduleExtensionAction()}.
     */
    @Test
    public void testActiveContestScheduleExtensionAction() {
    	assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
    	action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
    	action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
    	action.setStudio(true);
    	assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
    	action.setStudio(true);
    	assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#getRegistrationExtension()}.
     */
    @Test
    public void testGetRegistrationExtension() {
    	action.setRegistrationExtension(1);
        assertEquals("The result is incorrect.", 1, action.getRegistrationExtension());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#setRegistrationExtension(int)}.
     */
    @Test
    public void testSetRegistrationExtension() {
    	action.setRegistrationExtension(1);
        assertEquals("The result is incorrect.", 1, action.getRegistrationExtension());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#getMilestoneExtension()}.
     */
    @Test
    public void testGetMilestoneExtension() {
    	action.setMilestoneExtension(1);
        assertEquals("The result is incorrect.", 1, action.getMilestoneExtension());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#setMilestoneExtension(int)}.
     */
    @Test
    public void testSetMilestoneExtension() {
    	action.setMilestoneExtension(1);
        assertEquals("The result is incorrect.", 1, action.getMilestoneExtension());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#getSubmissionExtension()}.
     */
    @Test
    public void testGetSubmissionExtension() {
    	action.setSubmissionExtension(1);
        assertEquals("The result is incorrect.", 1, action.getSubmissionExtension());
    }

    /**
     * Test method for {@link ActiveContestScheduleExtensionAction#setSubmissionExtension(int)}.
     */
    @Test
    public void testSetSubmissionExtension() {
    	action.setSubmissionExtension(1);
        assertEquals("The result is incorrect.", 1, action.getSubmissionExtension());
    }
}
