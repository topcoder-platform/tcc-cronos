/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link PrerequisiteElementsFactory}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PrerequisiteElementsFactoryUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>PrerequisiteElementsFactory</code> instance.
     * </p>
     */
    private PrerequisiteElementsFactory prerequisiteElementsFactory;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PrerequisiteElementsFactoryUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        prerequisiteElementsFactory = new PrerequisiteElementsFactory();
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#PrerequisiteElementsFactory()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testPrerequisiteElementsFactory_accuracy() {
        assertNotNull("Instance should be always created.", prerequisiteElementsFactory);
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetPrerequisiteDocumentsRequest()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetPrerequisiteDocumentsRequest_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetPrerequisiteDocumentsRequest());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createGetPrerequisiteDocumentsRequest(), prerequisiteElementsFactory
                .createGetPrerequisiteDocumentsRequest());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetPrerequisiteDocumentsResponse()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetPrerequisiteDocumentsResponse_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetPrerequisiteDocumentsResponse());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createGetPrerequisiteDocumentsResponse(), prerequisiteElementsFactory
                .createGetPrerequisiteDocumentsResponse());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetAllPrerequisiteDocumentsRequest()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetAllPrerequisiteDocumentsRequest_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetAllPrerequisiteDocumentsRequest());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createGetAllPrerequisiteDocumentsRequest(), prerequisiteElementsFactory
                .createGetAllPrerequisiteDocumentsRequest());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetAllPrerequisiteDocumentsResponse()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetAllPrerequisiteDocumentsResponse_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetAllPrerequisiteDocumentsResponse());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createGetAllPrerequisiteDocumentsResponse(), prerequisiteElementsFactory
                .createGetAllPrerequisiteDocumentsResponse());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createPrerequisiteDocument()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreatePrerequisiteDocument_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createPrerequisiteDocument());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createPrerequisiteDocument(),
                prerequisiteElementsFactory.createPrerequisiteDocument());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createRecordMemberAnswerRequest()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateRecordMemberAnswerRequest_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createRecordMemberAnswerRequest());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createRecordMemberAnswerRequest(),
                prerequisiteElementsFactory.createRecordMemberAnswerRequest());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createDocumentNotFoundFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateDocumentNotFoundFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createDocumentNotFoundFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createDocumentNotFoundFault(),
                prerequisiteElementsFactory.createDocumentNotFoundFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createUserAlreadyAnsweredDocumentFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateUserAlreadyAnsweredDocumentFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createUserAlreadyAnsweredDocumentFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createUserAlreadyAnsweredDocumentFault(), prerequisiteElementsFactory
                .createUserAlreadyAnsweredDocumentFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createCompetitionNotFoundFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateCompetitionNotFoundFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createCompetitionNotFoundFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createCompetitionNotFoundFault(),
                prerequisiteElementsFactory.createCompetitionNotFoundFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createRoleNotFoundFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateRoleNotFoundFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createRoleNotFoundFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createRoleNotFoundFault(),
                prerequisiteElementsFactory.createRoleNotFoundFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetPrerequisiteDocumentRequest()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetPrerequisiteDocumentRequest_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetPrerequisiteDocumentRequest());
        assertNotSame("Always return new instance.",
                prerequisiteElementsFactory.createGetPrerequisiteDocumentRequest(), prerequisiteElementsFactory
                        .createGetPrerequisiteDocumentRequest());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createUserNotAuthorizedFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateUserNotAuthorizedFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createUserNotAuthorizedFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createUserNotAuthorizedFault(),
                prerequisiteElementsFactory.createUserNotAuthorizedFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createIllegalArgumentWSFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateIllegalArgumentWSFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createIllegalArgumentWSFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createIllegalArgumentWSFault(),
                prerequisiteElementsFactory.createIllegalArgumentWSFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createPersistenceFault()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreatePersistenceFault_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createPersistenceFault());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createPersistenceFault(),
                prerequisiteElementsFactory.createPersistenceFault());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createGetPrerequisiteDocumentResponse()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateGetPrerequisiteDocumentResponse_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createGetPrerequisiteDocumentResponse());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory
                .createGetPrerequisiteDocumentResponse(), prerequisiteElementsFactory
                .createGetPrerequisiteDocumentResponse());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteElementsFactory#createRecordMemberAnswerResponse()</code> method.
     * </p>
     * <p>
     * Should never return null, and always return a new instance.
     * </p>
     */
    public void testCreateRecordMemberAnswerResponse_accuracy() {
        assertNotNull("Never return null.", prerequisiteElementsFactory.createRecordMemberAnswerResponse());
        assertNotSame("Always return new instance.", prerequisiteElementsFactory.createRecordMemberAnswerResponse(),
                prerequisiteElementsFactory.createRecordMemberAnswerResponse());
    }
}
