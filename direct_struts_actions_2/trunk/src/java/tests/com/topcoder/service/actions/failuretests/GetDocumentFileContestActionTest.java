/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.test.context.ContextConfiguration;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.GetDocumentFileContestAction;

/**
 * Failure tests for <code>GetDocumentFileContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class GetDocumentFileContestActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private GetDocumentFileContestAction instance;

    /**
     * Proxy.
     */
    private ActionProxy proxy;

    /**
     * Sets up the environment.
     * @throws java.lang.Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new GetDocumentFileContestAction() {};
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailureGetDocumentFileContestAction");
        Helper.setUpSession(proxy);
    }

    /**
     * Cleans up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testDocumentId_Negative() throws Exception {
        FailureGetDocumentFileContestAction action = (FailureGetDocumentFileContestAction) proxy.getAction();

        FailureGetDocumentFileContestAction.documentId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testDocumentId_Zero() throws Exception {
        FailureGetDocumentFileContestAction action = (FailureGetDocumentFileContestAction) proxy.getAction();

        FailureGetDocumentFileContestAction.documentId = 0;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.GetDocumentFileContestAction#setMimeTypeRetriever}.
     * @throws Exception to JUnit
     */
    public void testSetMimeTypeRetriever_Null() throws Exception {
        try {
            instance.setMimeTypeRetriever(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
