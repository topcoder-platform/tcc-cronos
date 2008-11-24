/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge.mocks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.service.prerequisite.CompetitionNotFoundException;
import com.topcoder.service.prerequisite.DocumentNotFoundException;
import com.topcoder.service.prerequisite.IllegalArgumentWSException;
import com.topcoder.service.prerequisite.PersistenceException;
import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.prerequisite.RoleNotFoundException;
import com.topcoder.service.prerequisite.UserAlreadyAnsweredDocumentException;
import com.topcoder.service.prerequisite.UserNotAuthorizedException;
import com.topcoder.widgets.bridge.TestHelper;

/**
 * <p>
 * Mock Prerequisite Service.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPrerequisiteService implements PrerequisiteService {
    /**
     * <p>
     * A list to hold some mock documents. Initiated in the constructor.
     * </p>
     */
    private final List<PrerequisiteDocument> documents;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public MockPrerequisiteService() {
        documents = new ArrayList<PrerequisiteDocument>();
        documents.add(getDocument(1));
        documents.add(getDocument(2));
    }

    /**
     * <p>
     * Retrieves all version of prerequisite document. This is only administrative function.
     * </p>
     *
     * @return all versions of prerequisite document
     * @throws UserNotAuthorizedException if the caller is not administrator
     * @throws PersistenceException if some error occurs in the document manager bean usage (wrap its exceptions)
     */
    public List<PrerequisiteDocument> getAllPrerequisiteDocuments() throws PersistenceException {
        return documents;
    }

    /**
     * <p>
     * Retrieves the specific version of prerequisite document.
     * </p>
     * <p>
     * Notes, Version can be not specified and in this case methods should return the latest version of document
     * </p>
     * <p>
     * This method doesn't throw UserNotAuthorizedException because the user is authorized and also the administrator.
     * </p>
     *
     * @param documentId the document id used to retrieve the document
     * @param version the version of document, can be null.
     * @return specific version of prerequisite document
     * @throws DocumentNotFoundException if the document is not found
     * @throws PersistenceException if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalArgumentWSException if any id is less than 0
     */
    public PrerequisiteDocument getPrerequisiteDocument(long documentId, Integer version)
        throws DocumentNotFoundException, PersistenceException {
        checkId(documentId, "document id");
        checkId(version, "version");
        for (PrerequisiteDocument document : documents) {
            if (document.getDocumentId() == documentId) {
                return document;
            }
        }
        if (documentId == 10) {
            throw new PersistenceException("mock error : persistence error", "persistence mock error.");
        } else {
            throw new DocumentNotFoundException("mock error : document not found.", documentId);
        }
    }

    /**
     * <p>
     * Retrieves all documents the specific user is required to agree to, for a particular role for a specific
     * competition.
     * </p>
     * <p>
     * This method is used to get all prerequisite documents for competition and user role for competition. Note that
     * such methods returns only specific versions of documents; actually it can return two different version of the
     * same document. User can get documents' versions only if he has given role. Administrator can get any document and
     * any version of document.
     * </p>
     *
     * @param competitionId the competition id used to get the document versions
     * @param roleId the role id used to get the document versions
     * @return all prerequisite documents for competition and user role, possibly empty if no items exist.
     * @throws IllegalArgumentWSException if any id is less than 0
     * @throws CompetitionNotFoundException if the competition is not found
     * @throws PersistenceException if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws RoleNotFoundException if the role is not found in the profile map
     * @throws UserNotAuthorizedException if the caller does not have 'User' or 'Administrator' role.
     */
    public List<PrerequisiteDocument> getPrerequisiteDocuments(long competitionId, long roleId)
        throws CompetitionNotFoundException, PersistenceException, RoleNotFoundException {
        checkId(competitionId, "competition id");
        checkId(roleId, "role id");
        if (competitionId == 1) {
            return documents.subList(0, 1);
        } else if (competitionId == 2) {
            throw new CompetitionNotFoundException("mock error : competition not found.", competitionId);
        } else if (competitionId == 3) {
            throw new UserNotAuthorizedException("mock error : user not authorized.", roleId);
        } else {
            throw new PersistenceException("mock error : persistence error.", "mock error.");
        }
    }

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
     * @param competitionId the competition id
     * @param timestamp the timestamp
     * @param agrees indicates the agrees of user
     * @param prerequisiteDocument the pre-requisite document
     * @param roleId the role id
     * @throws CompetitionNotFoundException if the competition is not found
     * @throws RoleNotFoundException if the role is not found
     * @throws DocumentNotFoundException if the document is not found
     * @throws UserAlreadyAnsweredDocumentException if the user has already answered
     * @throws PersistenceException if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalArgumentWSException if any id is less than 0, if any object is null
     * @throws UserNotAuthorizedException if the caller does not have 'User' or 'Administrator' role.
     */
    public void recordMemberAnswer(long competitionId, XMLGregorianCalendar timestamp, boolean agrees,
        PrerequisiteDocument prerequisiteDocument, long roleId) throws CompetitionNotFoundException,
        RoleNotFoundException, DocumentNotFoundException, UserAlreadyAnsweredDocumentException, PersistenceException {
        checkId(competitionId, "competition id");
        checkId(roleId, "role id");
        checkNull(timestamp, "time stamp");
        checkNull(prerequisiteDocument, "prerequisite document");
        if (competitionId == 1) {
            // good, do nothing
        } else {
            throw new PersistenceException("mock error : persistence error.", "mock error.");
        }
    }

    /**
     * <p>
     * Creates a mock document object.
     * </p>
     *
     * @param documentId document id to be used
     * @return mocked document object
     */
    private PrerequisiteDocument getDocument(long documentId) {
        PrerequisiteDocument document = new PrerequisiteDocument();
        document.setDocumentId(documentId);
        document.setVersion((int) documentId);
        document.setName("document" + documentId);
        document.setContent("content");
        try {
            document.setVersionDate(TestHelper.getXMLGregorianCalendar("2008-05-24 09:00"));
        } catch (ParseException e) {
            // do nothing. should not occur.
        }
        return document;
    }

    /**
     * <p>
     * Checks to see if object is null or not.
     * </p>
     *
     * @param object to be checked
     * @param name of the object
     * @throws IllegalArgumentWSException if object is null
     */
    private void checkNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentWSException(name + " should not be null.", name + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks to see if id is less than 0.
     * </p>
     *
     * @param id to be checked
     * @param name of the id
     * @throws IllegalArgumentWSException if id is less than 0
     */
    private void checkId(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentWSException(name + " should not be less than 0.", name
                + " should not be less than 0.");
        }
    }

}
