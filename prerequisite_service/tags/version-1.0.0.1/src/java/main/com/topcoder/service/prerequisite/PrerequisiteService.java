/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * <p>
 * The pre-requisite service provides the ability to retrieve required pre-requisite documents, and to record a user
 * response to the pre-requisite. This interface defines the webservices endpoint with the operation. It uses the
 * annotations to defined the requests and responses definitions.
 * </p>
 * <p>
 * <b>Thread Safety</b>: it's required that the implementations would be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "PrerequisiteService")
public interface PrerequisiteService {

    /**
     * <p>
     * Retrieves all documents the specific user is required to agree to, for a particular role for a specific
     * competition.
     * </p>
     * <p>
     * This method is used to get all prerequisite documents for competition and user role for competition. Note that
     * such methods returns only specific versions of documents; actually it can return two different version of the
     * same document. User can get documents' versions only if he has given role. Administrator can get any document
     * and any version of document.
     * </p>
     *
     * @param competitionId
     *            the competition id used to get the document versions
     * @param roleId
     *            the role id used to get the document versions
     * @return all prerequisite documents for competition and user role, possibly empty if no items exist.
     * @throws IllegalArgumentWSException
     *             if any id is less than 0
     * @throws CompetitionNotFoundException
     *             if the competition is not found
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws RoleNotFoundException
     *             if the role is not found in the profile map
     * @throws UserNotAuthorizedException
     *             if the caller does not have 'User' or 'Administrator' role.
     */
    @WebMethod(operationName = "getPrerequisiteDocuments")
    @WebResult(name = "prerequisiteDocuments")
    @RequestWrapper(localName = "getPrerequisiteDocuments",
        className = "com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentsRequest")
    @ResponseWrapper(localName = "getPrerequisiteDocumentsResponse",
        className = "com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentsResponse")
    List<PrerequisiteDocument> getPrerequisiteDocuments(@WebParam(name = "competitionId") long competitionId,
        @WebParam(name = "roleId") long roleId) throws CompetitionNotFoundException, PersistenceException,
        RoleNotFoundException;

    /**
     * <p>
     * Records a single member's answer (agree or not agree) to the specific documentId and versionId. This includes
     * recording negative agreements (i.e., do not agree) for the purpose of tracking such responses from the member
     * base to specific documents
     * </p>
     * <p>
     * User can accept or reject document by using this method. Based on parameters methods should create new instance
     * of MemberAnswer and record it. User can provide answer for document only if he has given role. Note that member
     * provide answer only for given role and can not answer twice for the same version of competition document.
     * </p>
     *
     * @param competitionId
     *            the competition id
     * @param timestamp
     *            the timestamp
     * @param agrees
     *            indicates the agrees of user
     * @param prerequisiteDocument
     *            the pre-requisite document
     * @param roleId
     *            the role id
     * @throws CompetitionNotFoundException
     *             if the competition is not found
     * @throws RoleNotFoundException,
     *             if the role is not found
     * @throws DocumentNotFoundException
     *             if the document is not found
     * @throws UserAlreadyAnsweredDocumentException
     *             if the user has already answered
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalArgumentWSException
     *             if any id is less than 0, if any object is null
     * @throws UserNotAuthorizedException
     *             if the caller does not have 'User' or 'Administrator' role.
     */
    @WebMethod(operationName = "recordMemberAnswer")
    @RequestWrapper(localName = "recordMemberAnswer",
        className = "com.topcoder.service.prerequisite.ejb.RecordMemberAnswerRequest")
    @ResponseWrapper(localName = "recordMemberAnswerResponse",
        className = "com.topcoder.service.prerequisite.ejb.RecordMemberAnswerResponse")
    void recordMemberAnswer(@WebParam(name = "competitionId") long competitionId,
        @WebParam(name = "timestamp") XMLGregorianCalendar timestamp,
        @WebParam(name = "agrees") boolean agrees,
        @WebParam(name = "prerequisiteDocument") PrerequisiteDocument prerequisiteDocument,
        @WebParam(name = "roleId") long roleId) throws CompetitionNotFoundException, RoleNotFoundException,
        DocumentNotFoundException, UserAlreadyAnsweredDocumentException, PersistenceException;

    /**
     * <p>
     * Retrieves the specific version of prerequisite document.
     * </p>
     * <p>
     * Notes, Version can be not specified and in this case methods should return the latest version of document
     * </p>
     * </p>
     * This method doesn't throw UserNotAuthorizedException because the user is authorized and also the administrator.
     * </p>
     *
     * @param documentId
     *            the document id used to retrieve the document
     * @param version
     *            the version of document, can be null.
     * @return specific version of prerequisite document
     * @throws DocumentNotFoundException
     *             if the document is not found
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalArgumentWSException
     *             if any id is less than 0
     */
    @WebMethod(operationName = "getPrerequisiteDocument")
    @WebResult(name = "prerequisiteDocument")
    @RequestWrapper(localName = "getPrerequisiteDocument",
        className = "com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentRequest")
    @ResponseWrapper(localName = "getPrerequisiteDocumentResponse",
        className = "com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentResponse")
    PrerequisiteDocument getPrerequisiteDocument(@WebParam(name = "documentId") long documentId,
        @WebParam(name = "version") Integer version) throws DocumentNotFoundException, PersistenceException;

    /**
     * <p>
     * Retrieves all version of prerequisite document. This is only administrative function.
     * </p>
     *
     * @return all versions of prerequisite document
     * @throws UserNotAuthorizedException
     *             if the caller is not administrator
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     */
    @WebMethod(operationName = "getAllPrerequisiteDocuments")
    @WebResult(name = "prerequisiteDocuments")
    @RequestWrapper(localName = "getAllPrerequisiteDocuments",
        className = "com.topcoder.service.prerequisite.ejb.GetAllPrerequisiteDocumentsRequest")
    @ResponseWrapper(localName = "getAllPrerequisiteDocumentsResponse",
        className = "com.topcoder.service.prerequisite.ejb.GetAllPrerequisiteDocumentsResponse")
    List<PrerequisiteDocument> getAllPrerequisiteDocuments() throws UserNotAuthorizedException, PersistenceException;
}
