/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.action.contest.launch.ContestLinksRetrievalAction;
import com.topcoder.management.project.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.DirectServiceFacade;

/**
 * <p>
 * Accuracy tests for class <code>ContestLinksRetrievalAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ContestLinksRetrievalActionAccTest {
    /**
     * <p>
     * Represent the ContestLinksRetrievalAction instance that used to call its method for test. It
     * will be initialized in setUp().
     * </p>
     */
    private ContestLinksRetrievalAction impl;

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
        impl = new ContestLinksRetrievalAction();
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
     * Accuracy test for the constructor <code>ContestLinksRetrievalAction()</code>.<br>
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
    @SuppressWarnings("unchecked")
    @Test
    public void testExecuteAction_Accuracy() throws Exception {
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));

        DirectServiceFacade mock = EasyMock.createMock(DirectServiceFacade.class);

        impl.prepare();
        impl.setContestId(2);

        List<Project> parentProjects = new ArrayList<Project>();
        List<Project> childProjects = new ArrayList<Project>();

        EasyMock.expect(mock.getParentProjects((TCSubject) EasyMock.isNull(), EasyMock.eq(2L))).andReturn(
            parentProjects);
        EasyMock.expect(mock.getChildProjects((TCSubject) EasyMock.isNull(), EasyMock.eq(2L))).andReturn(
            childProjects);
        impl.setDirectServiceFacade(mock);
        EasyMock.replay(mock);

        impl.executeAction();

        Object res = impl.getResult();

        assertNotNull("The result object should not be null", res);
        assertTrue("The result should be Long type.", res instanceof Map);
        assertEquals("the result is incorrect", parentProjects, ((Map) res).get("parentProjects"));
        assertEquals("the result is incorrect", childProjects, ((Map) res).get("childProjects"));

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
