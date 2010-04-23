/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.ContestAction;
import com.topcoder.service.actions.FileUploadAttachContestFileAction;

/**
 * Failure tests for <code>DeleteDocumentContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileUploadAttachContestFileActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private FileUploadAttachContestFileAction instance;

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
        instance = new FileUploadAttachContestFileAction() {};
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailureFileUploadAttachContestFileAction");
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
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_NullFacade() throws Exception {
        try {
            Helper.setField(ContestAction.class, instance, "contestServiceFacade", null);
            instance.executeAction();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_NullRetriever() throws Exception {
        try {
            Helper.setField(FileUploadAttachContestFileAction.class, instance, "mimeTypeRetriever", null);
            instance.executeAction();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestId() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = "test";
        FailureFileUploadAttachContestFileAction.documentTypeId = 1;
        FailureFileUploadAttachContestFileAction.contestId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestFileDescription_Null() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = null;
        FailureFileUploadAttachContestFileAction.documentTypeId = 1;
        FailureFileUploadAttachContestFileAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestFileDescription_Empty() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = "";
        FailureFileUploadAttachContestFileAction.documentTypeId = 1;
        FailureFileUploadAttachContestFileAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestFileDescription_TooLong() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = "";
        for (int i = 0; i < 4097; i++) {
            FailureFileUploadAttachContestFileAction.contestFileDescription += '0';
        }
        FailureFileUploadAttachContestFileAction.documentTypeId = 1;
        FailureFileUploadAttachContestFileAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setDocumentTypeId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetDocumentTypeId_Negative() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = "test";
        FailureFileUploadAttachContestFileAction.documentTypeId = -1;
        FailureFileUploadAttachContestFileAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setDocumentTypeId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetDocumentTypeId_TooBig() throws Exception {
        FailureFileUploadAttachContestFileAction action = (FailureFileUploadAttachContestFileAction) proxy.getAction();

        FailureFileUploadAttachContestFileAction.contestFileDescription = "test";
        FailureFileUploadAttachContestFileAction.documentTypeId = 25;
        FailureFileUploadAttachContestFileAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setMimeTypeRetriever}.
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
