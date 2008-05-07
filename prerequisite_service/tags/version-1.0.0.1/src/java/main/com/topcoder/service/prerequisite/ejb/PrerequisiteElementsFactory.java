/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import javax.xml.bind.annotation.XmlRegistry;

import com.topcoder.service.prerequisite.PrerequisiteDocument;

/**
 * <p>
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * <code>com.topcoder.service.prerequisite.ejb</code> package and <code>{@link PrerequisiteDocument}</code>. It is
 * used by the EJB to construct the soap elements.
 * </p>
 * <p>
 * An PrerequisiteElementsFactory allows you to programmatically construct new instances of the Java representation for
 * XML content. The Java representation of XML content can consist of schema derived interfaces and classes representing
 * the binding of schema type definitions, element declarations and model groups. Factory methods for each of these are
 * provided in this class.
 * </p>
 * <p>
 * <b>Thread Safety</b>: this class is thread safe because it has no state and it creates new instances every time.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlRegistry
public class PrerequisiteElementsFactory {
    /**
     * <p>
     * Creates a <code>PrerequisiteElementsFactory</code> instance.
     * </p>
     */
    public PrerequisiteElementsFactory() {
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetPrerequisiteDocumentsRequest}</code>.
     * </p>
     *
     * @return the <code>GetPrerequisiteDocumentsRequest</code> instance.
     */
    public GetPrerequisiteDocumentsRequest createGetPrerequisiteDocumentsRequest() {
        return new GetPrerequisiteDocumentsRequest();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetPrerequisiteDocumentsResponse}</code>.
     * </p>
     *
     * @return the <code>GetPrerequisiteDocumentsResponse</code> instance.
     */
    public GetPrerequisiteDocumentsResponse createGetPrerequisiteDocumentsResponse() {
        return new GetPrerequisiteDocumentsResponse();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetAllPrerequisiteDocumentsRequest}</code>.
     * </p>
     *
     * @return the <code>GetAllPrerequisiteDocumentsRequest</code> instance.
     */
    public GetAllPrerequisiteDocumentsRequest createGetAllPrerequisiteDocumentsRequest() {
        return new GetAllPrerequisiteDocumentsRequest();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetAllPrerequisiteDocumentsResponse}</code>.
     * </p>
     *
     * @return the <code>GetAllPrerequisiteDocumentsResponse</code> instance.
     */
    public GetAllPrerequisiteDocumentsResponse createGetAllPrerequisiteDocumentsResponse() {
        return new GetAllPrerequisiteDocumentsResponse();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link PrerequisiteDocument}</code>.
     * </p>
     *
     * @return the <code>PrerequisiteDocument</code> instance.
     */
    public PrerequisiteDocument createPrerequisiteDocument() {
        return new PrerequisiteDocument();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link RecordMemberAnswerRequest}</code>.
     * </p>
     *
     * @return the <code>RecordMemberAnswerRequest</code> instance.
     */
    public RecordMemberAnswerRequest createRecordMemberAnswerRequest() {
        return new RecordMemberAnswerRequest();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link DocumentNotFoundFault}</code>.
     * </p>
     *
     * @return the <code>DocumentNotFoundFault</code> instance.
     */
    public DocumentNotFoundFault createDocumentNotFoundFault() {
        return new DocumentNotFoundFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link UserAlreadyAnsweredDocumentFault}</code>.
     * </p>
     *
     * @return the <code>UserAlreadyAnsweredDocumentFault</code> instance.
     */
    public UserAlreadyAnsweredDocumentFault createUserAlreadyAnsweredDocumentFault() {
        return new UserAlreadyAnsweredDocumentFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link CompetitionNotFoundFault}</code>.
     * </p>
     *
     * @return the <code>CompetitionNotFoundFault</code> instance.
     */
    public CompetitionNotFoundFault createCompetitionNotFoundFault() {
        return new CompetitionNotFoundFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link RoleNotFoundFault}</code>.
     * </p>
     *
     * @return the <code>RoleNotFoundFault</code> instance.
     */
    public RoleNotFoundFault createRoleNotFoundFault() {
        return new RoleNotFoundFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetPrerequisiteDocumentRequest}</code>.
     * </p>
     *
     * @return the <code>GetPrerequisiteDocumentRequest</code> instance.
     */
    public GetPrerequisiteDocumentRequest createGetPrerequisiteDocumentRequest() {
        return new GetPrerequisiteDocumentRequest();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link UserNotAuthorizedFault}</code>.
     * </p>
     *
     * @return the <code>UserNotAuthorizedFault</code> instance.
     */
    public UserNotAuthorizedFault createUserNotAuthorizedFault() {
        return new UserNotAuthorizedFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link IllegalArgumentWSFault}</code>.
     * </p>
     *
     * @return the <code>IllegalArgumentWSFault</code> instance.
     */
    public IllegalArgumentWSFault createIllegalArgumentWSFault() {
        return new IllegalArgumentWSFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link PersistenceFault}</code>.
     * </p>
     *
     * @return the <code>PersistenceFault</code> instance.
     */
    public PersistenceFault createPersistenceFault() {
        return new PersistenceFault();
    }

    /**
     * <p>
     * Creates an instance of <code>{@link GetPrerequisiteDocumentResponse}</code>.
     * </p>
     *
     * @return the <code>GetPrerequisiteDocumentResponse</code> instance.
     */
    public GetPrerequisiteDocumentResponse createGetPrerequisiteDocumentResponse() {
        return new GetPrerequisiteDocumentResponse();
    }


    /**
     * <p>
     * Creates an instance of <code>{@link RecordMemberAnswerResponse}</code>.
     * </p>
     *
     * @return the <code>RecordMemberAnswerResponse</code> instance.
     */
    public RecordMemberAnswerResponse createRecordMemberAnswerResponse() {
        return new RecordMemberAnswerResponse();
    }
}
