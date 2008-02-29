/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import java.util.List;

import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * This interface provides great number of utility operations with documents like CRUD operations on document; CRUD
 * operations on member answer; CRUD operations on competition document (document which is specific for concrete
 * competition and role).
 * </p>
 * <p>
 * This should be used by <code>PreRequisiteService</code> implementation and also it can be used to define more
 * services.
 * </p>
 * <p>
 * Notes, according to the definition of the entities, currently, no cascade operations are performed for
 * add/update/delete.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface are not required to be thread safety. This component is
 * supposed to be run as stateless session beans. For EJB container, it will serve requests to different instances of
 * stateless session bean. So there is no thread safety requirement on implementations of this interface.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
public interface DocumentManager {
    /**
     * <p>
     * Adds new document to the persistence layer.
     * </p>
     *
     * @param document
     *            the new document to add to the persistence layer
     * @return the newly added document.
     * @throws IllegalArgumentException
     *             if document is null
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    Document addDocument(Document document) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets document by id from the persistence layer.
     * </p>
     *
     * @param documentId
     *            the id of the document to retrieve
     * @return the document from the persistence layer by document id or null if the document is not found
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    Document getDocument(long documentId) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets all documents in all versions from the persistence layer.
     * </p>
     * <p>
     * Note, an empty list will be returned if no record in the persistence layer.
     * </p>
     *
     * @return all the documents in all versions from persistence layer.
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    List<Document> getAllDocuments() throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Adds a new version of document to the persistence layer.
     * </p>
     * <p>
     * Note, the version of the document can be null and when it is null, it means it should be set with the latest
     * version of the corresponding document plus one.
     * </p>
     *
     * @param documentVersion
     *            the new document version to add
     * @return the newly added document version.
     * @throws IllegalArgumentException
     *             if document Version is null or any attribute (except id, version) of the given documentVersion is not
     *             set or if any id of the reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    DocumentVersion addDocumentVersion(DocumentVersion documentVersion) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Updates document version in persistence layer with the given document version. The primary key of the given
     * document version is used to locate the corresponding document version in persistence layer and update it.
     * </p>
     * <p>
     * Note, if the primary key is not found, then DocumentPersistenceException should be thrown.
     * </p>
     *
     * @param documentVersion
     *            the document version to update the corresponding record in persistence layer
     * @throws IllegalArgumentException
     *             if the given documentVersion is null or any attribute of the given documentVersion is not set or if
     *             any id of the reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    void updateDocumentVersion(DocumentVersion documentVersion) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets concrete document version from the persistence layer.
     * </p>
     * <p>
     * Note, version can be null and in this case the latest document version should be selected.
     * </p>
     *
     * @param documentId
     *            the document id to locate the document version
     * @param version
     *            the version of the document, can be null
     * @return the document version from the persistence layer with the given document id and version (if version is
     *         null, the latest version is used) or null if none document version matches the given values
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    DocumentVersion getDocumentVersion(long documentId, Integer version) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets document version by the given id from the persistence layer.
     * </p>
     *
     * @param documentVersionId
     *            the id of document version
     * @return the document version by the given id from the persistence layer or null if the id can't be found
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    DocumentVersion getDocumentVersion(long documentVersionId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Adds a member answer to the persistence layer.
     * </p>
     * <p>
     * Note that user can not answer twice for the same version of competition document.
     * </p>
     *
     * @param memberAnswer
     *            the member answer to add
     * @return the newly added member answer
     * @throws IllegalArgumentException
     *             if memberAnswer is null or any attribute (except id) of the member answer is not set or if any id of
     *             the reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws CompetitionDocumentAnsweredException
     *             if the user has answered the version of competition document
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    MemberAnswer addMemberAnswer(MemberAnswer memberAnswer) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Updates member answer with the given value in the persistence layer.
     * </p>
     * <p>
     * The primary key of the given member answer is used to locate the corresponding member answer in persistence layer
     * and update it.
     * </p>
     * <p>
     * Note, if the given member answer primary key can't be found in persistence layer then
     * DocumentPersistenceException can't be found
     * </p>
     *
     * @param memberAnswer
     *            the member answer to update the corresponding answer in the persistence layer
     * @throws IllegalArgumentException
     *             if memberAnswer is null or any attribute of the given memberAnswer is not set or if any id of the
     *             reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    void updateMemberAnswer(MemberAnswer memberAnswer) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Deletes the given member answer with the given id from the persistence layer.
     * </p>
     * <p>
     * If the record is deleted, then return true, otherwise false.
     * </p>
     *
     * @param memberAnswerId
     *            the id of the member answer to delete
     * @return true if the member answer is deleted, otherwise false
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    boolean deleteMemberAnswer(long memberAnswerId) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets member answer by member answer id from the persistence layer.
     * </p>
     *
     * @param memberAnswerId
     *            the id of the member answer
     * @return the member answer with the given id from the persistence layer or null if the id can't be found
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    MemberAnswer getMemberAnswer(long memberAnswerId) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Adds new version of document to competition for specific role.
     * </p>
     *
     * @param competitionDocument
     *            the competition document to add
     * @return the newly added competition document.
     * @throws IllegalArgumentException
     *             if competitionDocument is null or any attribute (except id) of the competition document is not set or
     *             if any id of the reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    CompetitionDocument addCompetitionDocument(CompetitionDocument competitionDocument) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Updates version of document associated with competition and specific role.
     * </p>
     * <p>
     * The primary key of the given member answer is used to locate the corresponding member answer in persistence layer
     * and update it.
     * </p>
     * <p>
     * Note, if the primary key in the given competition document can't be found in persistence layer then
     * DocumentPersistenceException can't be found
     * </p>
     *
     * @param competitionDocument
     *            the given competition document to update in the persistence layer
     * @throws IllegalArgumentException
     *             if competitionDocument is null or any attribute of the competition document is not set or if any id
     *             of the reference attributes is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    void updateCompetitionDocument(CompetitionDocument competitionDocument) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Deletes version of document from competition and specific role.
     * </p>
     * <p>
     * The primary key of the given competition document is used to locate the corresponding record in persistence layer
     * and delete it.
     * </p>
     *
     * @param competitionDocument
     *            the given competition document to delete from the persistence layer
     * @return true if the competition document is deleted, false otherwise
     * @throws IllegalArgumentException
     *             if competitionDocument is null or id of it is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    boolean deleteCompetitionDocument(CompetitionDocument competitionDocument) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Deletes all documents for the given competition id from persistence layer.
     * </p>
     *
     * @param competitionId
     *            the id of competition to locate all the competition documents.
     * @return true if there is any competition document is deleted, false otherwise
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    boolean deleteCompetitionDocuments(long competitionId) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Deletes all documents for role in competition.
     * </p>
     *
     * @param competitionId
     *            the id of competition
     * @param roleId
     *            the role id in the competition
     * @return true if there is any competition document is deleted, false otherwise
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    boolean deleteCompetitionDocuments(long competitionId, long roleId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets the competition document for the given competition document id from the persistence layer.
     * </p>
     *
     * @param competitionDocumentId
     *            the id of the competition document
     * @return the corresponding CompetitionDocument from the persistence layer or null if id can't be found
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    CompetitionDocument getCompetitionDocument(long competitionDocumentId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets the competition document from the persistence layer which has the given document version id, competition id
     * and role id.
     * </p>
     *
     * @param documentVersionId
     *            the id of the competition document version
     * @param competitionId
     *            the id of the competition
     * @param roleId
     *            the id of the role
     * @return the corresponding CompetitionDocument from the persistence layer or null if no competition document can
     *         match the above conditions
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    CompetitionDocument getCompetitionDocument(long documentVersionId, long competitionId, long roleId)
        throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets competition documents for specific role from persistence layer.
     * </p>
     *
     * @param roleId
     *            the id of role.
     * @return concrete document versions for specific role from persistence layer
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    List<CompetitionDocument> getCompetitionDocumentsByRole(long roleId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets competition documents for specific competition from persistence layer.
     * </p>
     * <p>
     * Note, an empty list will be returned if no corresponding records in the persistence layer.
     * </p>
     *
     * @param competitionId
     *            the id of competition
     * @return concrete document versions for specific competition from persistence layer.
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    List<CompetitionDocument> getCompetitionDocumentsByCompetition(long competitionId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Gets competition documents for specific role and competition from persistence layer.
     * </p>
     * <p>
     * Note, an empty list will be returned if no corresponding records in the persistence layer.
     * </p>
     *
     * @param competitionId
     *            the id of competition
     * @param roleId
     *            the id of role
     * @return concrete versions for specific role and competition from persistence layer.
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    List<CompetitionDocument> getCompetitionDocuments(long competitionId, long roleId) throws AuthorizationException,
        DocumentPersistenceException;

    /**
     * <p>
     * Adds new document name to the persistence layer.
     * </p>
     *
     * @param documentName
     *            the new document name to add to the persistence layer
     * @return the newly added document name.
     * @throws IllegalArgumentException
     *             if documentName is null
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    DocumentName addDocumentName(DocumentName documentName) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets document name by id from the persistence layer.
     * </p>
     *
     * @param documentNameId
     *            the id of the document name to retrieve
     * @return the document name from the persistence layer by document name id or null if the document name is not
     *         found
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    DocumentName getDocumentName(long documentNameId) throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Gets all document names from the persistence layer.
     * </p>
     * <p>
     * Note, an empty list will be returned if no record in the persistence layer.
     * </p>
     *
     * @return all the document names from persistence layer.
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    List<DocumentName> getAllDocumentNames() throws AuthorizationException, DocumentPersistenceException;

    /**
     * <p>
     * Returns whether the given competition document is answered by the given member or not.
     * </p>
     *
     * @param competitionDocumentId
     *            the id of the competition document
     * @param memberId
     *            the id of the the member who accepts or rejects the competition document
     * @return true if the competition document has been answered by the member, otherwise false
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    boolean isCompetitionDocumentAnswered(long competitionDocumentId, long memberId) throws AuthorizationException,
        DocumentPersistenceException;
}
