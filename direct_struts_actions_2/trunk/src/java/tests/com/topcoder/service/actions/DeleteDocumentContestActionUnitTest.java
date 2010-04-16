/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Unit tests for <code>DeleteDocumentContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DeleteDocumentContestActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private DeleteDocumentContestAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockDeleteDocumentContestAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof DeleteDocumentContestAction);

        TestHelper.injectSessionMapIntoProxy(proxy);

        return proxy;
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TestHelper.cleanupEnvironment();
        instance = new MockDeleteDocumentContestAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), ContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The document should be deleted successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        // add the document to facade
        UploadedDocument doc = new UploadedDocument();
        doc.setDocumentId(1);
        MockContestServiceFacade.addDocument(doc);

        // execute action to delete document
        instance.setDocumentId(1);
        instance.setContestId(1);
        instance.executeAction();

        // make sure document was deleted
        assertNull("document should have been deleted", MockContestServiceFacade.getStudioCompetitionDocument(doc
            .getDocumentId()));
    }

    /**
     * Failure test for executeAction. The document wasn't found, so DocumentNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure1() throws Exception {
        try {
            instance.setDocumentId(10);
            instance.setContestId(1);
            instance.executeAction();
            fail("DocumentNotFoundException is expected");
        } catch (DocumentNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for executeAction. The <code>contestServiceFacade</code> hasn't been injected, so
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure2() throws Exception {
        try {
            new DeleteDocumentContestAction().executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Accuracy test for getDocumentId. Verifies the returned value is correct.
     */
    @Test
    public void test_getDocumentId_Accuracy() {
        instance = new DeleteDocumentContestAction();
        assertEquals("incorrect default value", 0, instance.getDocumentId());
        instance.setDocumentId(5);
        assertEquals("incorrect value after setting", 5, instance.getDocumentId());
    }

    /**
     * Accuracy Test for setDocumentId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setDocumentId_Accuracy() {
        instance.setDocumentId(5);
        assertEquals("incorrect value after setting", 5, instance.getDocumentId());
    }

    /**
     * Failure test for setDocumentId method in struts environment. The value for the setter is 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDocumentId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        DeleteDocumentContestAction action = (DeleteDocumentContestAction) proxy.getAction();

        TestHelper.getFieldValues().put("documentId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "documentId", "documentId must be > 0");
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new DeleteDocumentContestAction();
        assertEquals("incorrect default value", 0, instance.getContestId());
        instance.setContestId(5);
        assertEquals("incorrect value after setting", 5, instance.getContestId());
    }

    /**
     * Accuracy Test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        instance.setContestId(5);
        assertEquals("incorrect value after setting", 5, instance.getContestId());
    }

    /**
     * Failure test for setContestId method in struts environment. The value for the setter is 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        DeleteDocumentContestAction action = (DeleteDocumentContestAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

}
