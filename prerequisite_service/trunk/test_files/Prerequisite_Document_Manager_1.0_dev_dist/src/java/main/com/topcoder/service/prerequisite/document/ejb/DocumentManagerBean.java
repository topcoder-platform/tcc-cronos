/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.topcoder.service.prerequisite.document.AuthorizationException;
import com.topcoder.service.prerequisite.document.CompetitionDocumentAnsweredException;
import com.topcoder.service.prerequisite.document.ConfigurationException;
import com.topcoder.service.prerequisite.document.DocumentPersistenceException;
import com.topcoder.service.prerequisite.document.Helper;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is a EJB3 stateless session which implements the <code>{@link DocumentManager}</code> interface. This
 * implementation utilizes JPA to interact with the persistence layer.
 * </p>
 * <p>
 * In this implementation, the following properties are configurable:
 * <ul>
 * <li>loggerName</li>
 * <li>persistenceUnitName</li>
 * <li>allowedRoleNames</li>
 * </ul>
 * </p>
 * <p>
 * <b>Sample configuration</b>:<br>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *  &lt;ejb-jar&gt;
 *  &lt;enterprise-beans&gt;
 *  &lt;session&gt;
 *  &lt;ejb-name&gt;DocumentManagerBean&lt;/ejb-name&gt;
 *  &lt;ejb-class&gt;com.topcoder.service.prerequisite.document.ejb.DocumentManagerBean&lt;/ejb-class&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;document_manager&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;persistenceUnitName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;document_manager&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;allowedRoleNames&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;admin,manager,Administrator&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;security-role-ref&gt;
 *  &lt;role-name&gt;admin&lt;/role-name&gt;
 *  &lt;role-link&gt;admin&lt;/role-link&gt;
 *  &lt;/security-role-ref&gt;
 *  &lt;/session&gt;
 *  &lt;/enterprise-beans&gt;
 *  &lt;assembly-descriptor&gt;
 *  &lt;security-role&gt;
 *  &lt;role-name&gt;admin&lt;/role-name&gt;
 *  &lt;/security-role&gt;
 *  &lt;method-permission&gt;
 *  &lt;role-name&gt;admin&lt;/role-name&gt;
 *  &lt;ejb-name&gt;DocumentManagerBean&lt;/ejb-name&gt;
 *  &lt;method-name&gt;*&lt;/method-name&gt;
 *  &lt;/method-permission&gt;
 *  &lt;/assembly-descriptor&gt;
 *  &lt;/ejb-jar&gt;
 * </pre>
 *
 * <b>Code Sample</b>:
 *
 * <pre>
 * Properties env = new Properties();
 * env.setProperty(Context.SECURITY_PRINCIPAL, &quot;user&quot;);
 * env.setProperty(Context.SECURITY_CREDENTIALS, &quot;password&quot;);
 * env.setProperty(Context.INITIAL_CONTEXT_FACTORY, &quot;org.jboss.security.jndi.JndiLoginInitialContextFactory&quot;);
 * context = new InitialContext(env);
 * DocumentManager documentManager = (DocumentManager) getInitialContext().lookup(JNDI_NAME);
 *
 * // add assign document
 * Document doc = new Document();
 * doc.setCreateDate(new Date());
 * doc.setDescription(&quot;assignment document for user confirmation!&quot;);
 * doc = documentManager.addDocument(doc);
 *
 * // add assign document name
 * DocumentName docName = new DocumentName();
 * docName.setName(&quot;Assignment Document&quot;);
 * docName = documentManager.addDocumentName(docName);
 *
 * // add the first assignment document version
 * DocumentVersion docVersion = new DocumentVersion();
 * docVersion.setDocumentVersion(1);
 * docVersion.setVersionDate(new Date());
 * docVersion.setContent(&quot;assignment document&quot;);
 * docVersion.setDocument(doc);
 * doc.getDocumentVersions().add(docVersion);
 * docVersion.setDocumentName(docName);
 * docName.getDocumentVersions().add(docVersion);
 * docVersion = documentManager.addDocumentVersion(docVersion);
 *
 * // add a competition document that requires assignment document
 * CompetitionDocument competitionDoc = new CompetitionDocument();
 * competitionDoc.setRoleId(new Long(1));
 * competitionDoc.setCompetitionId(new Long(1));
 * competitionDoc.setDocumentVersion(docVersion);
 * docVersion.getCompetitionDocuments().add(competitionDoc);
 * competitionDoc = documentManager.addCompetitionDocument(competitionDoc);
 *
 * // do some updates to assignment document version as requirement changes
 * DocumentVersion newVersion = documentManager.getDocumentVersion(doc.getDocumentId(), null);
 * newVersion.setContent(&quot;updated assignment document&quot;);
 * documentManager.updateDocumentVersion(newVersion);
 *
 * // record user answer to the competition document
 * MemberAnswer memberAnswer = new MemberAnswer();
 * memberAnswer.setAnswer(true);
 * memberAnswer.setCompetitionDocument(competitionDoc);
 * memberAnswer.setMemberId(new Long(1));
 * memberAnswer.setAnswerDate(new Date());
 * memberAnswer = documentManager.addMemberAnswer(memberAnswer);
 *
 * // remove the user answer
 * documentManager.deleteMemberAnswer(memberAnswer.getMemberAnswerId());
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: The class is not thread safe because it is mutable. The init() can change the state of this
 * EJB bean. But as this component is managed by EJB container, the init() will only be called once after the
 * constructor of this bean. For this aspect, this implementation can be counted as thread safety. The persistence layer
 * is thread safe too because this bean uses container-managed transaction. Transaction attributes are REQUIRED for all
 * methods (that has to be interacted with the persistence layer) in this bean class.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentManagerBean implements DocumentManagerLocal, DocumentManagerRemote {
    /**
     * <p>
     * Represents the query string for retrieving the max version of the specified document.
     * </p>
     */
    private static final String QUERY_MAX_DOCUMENT_VERSION = "SELECT MAX(dv.documentVersion) FROM DocumentVersion dv"
            + " WHERE dv.document.documentId = :id";

    /**
     * <p>
     * Represents the query string for retrieving all the documents.
     * </p>
     */
    private static final String RETRIEVE_ALL_DOCUMENTS = "SELECT d from Document d";

    /**
     * <p>
     * Represents the query string for retrieving all the document names.
     * </p>
     */
    private static final String RETRIEVE_ALL_DOCUMENTNAMES = "SELECT dn from DocumentName dn";

    /**
     * <p>
     * Represents the query string for retrieving competition documents by competition id and role id.
     * </p>
     */
    private static final String RETRIEVE_CD_BY_CID_RID = "SELECT cd FROM CompetitionDocument cd"
            + " WHERE cd.competitionId = :competitionId and cd.roleId = :roleId";

    /**
     * <p>
     * Represents the query string for retrieving competition documents by competition id.
     * </p>
     */
    private static final String RETRIEVE_CD_BY_CID = "SELECT cd FROM CompetitionDocument cd"
            + " WHERE cd.competitionId = :competitionId";

    /**
     * <p>
     * Represents the query string for retrieving competition documents by role id.
     * </p>
     */
    private static final String RETRIEVE_CD_BY_RID = "SELECT cd FROM CompetitionDocument cd WHERE cd.roleId = :roleId";

    /**
     * <p>
     * Represents the query string for retrieving competition documents by document version id, competition id and role
     * id.
     * </p>
     */
    private static final String RETRIEVE_CD_BY_ID_CID_RID = "SELECT cd FROM CompetitionDocument cd"
            + " WHERE cd.documentVersion.documentVersionId = :documentVersionId"
            + " AND cd.competitionId = :competitionId AND cd.roleId = :roleId";

    /**
     * <p>
     * Represents the query string for deleting competition documents by competition id and role id.
     * </p>
     */
    private static final String DELETE_CD_BY_CID_RID = "DELETE FROM CompetitionDocument cd"
            + " WHERE cd.competitionId = :competitionId AND cd.roleId = :roleId";

    /**
     * <p>
     * Represents the query string for deleting competition documents by competition id.
     * </p>
     */
    private static final String DELETE_CD_BY_CID = "DELETE FROM CompetitionDocument cd"
            + " WHERE cd.competitionId = :competitionId";

    /**
     * <p>
     * Represents the query string for retrieving document version by document id, and document version number.
     * </p>
     */
    private static final String RETRIEVE_DOCUMENT_VERSION_BY_VERSION = "SELECT dv FROM DocumentVersion dv"
            + " WHERE dv.document.documentId = :documentId AND dv.documentVersion = :documentVersion";

    /**
     * <p>
     * Represents the query string for retrieving max version of document version by document id.
     * </p>
     */
    private static final String RETRIEVE_LATEST_DOCUMENT_VERSION = "SELECT dv FROM DocumentVersion dv"
            + " WHERE dv.document.documentId = :documentId AND dv.documentVersion ="
            + " (SELECT MAX(dv.documentVersion) FROM DocumentVersion dv WHERE dv.document.documentId = :documentId)";

    /**
     * <p>
     * Represents the query string for check whether a competition document is answered by the specified member.
     * </p>
     */
    private static final String QUERY_COMPETITION_DOCUMENT_ANSWERED = "SELECT COUNT(ma) FROM MemberAnswer ma"
            + " WHERE ma.competitionDocument.competitionDocumentId = :competitonDocumentId"
            + " AND ma.memberId = :memberId";

    /**
     * Represents the current <code>SessionContext</code> interface for accessing to the container-provided runtime
     * context of the current bean. It is injected by EJB container at run time.
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the logger to log exceptions and invocation details.
     * </p>
     * <p>
     * It is set in the init method which is called after the bean is constructed by EJB container. This may be null if
     * the loggerName configuration property is missing, which means no logging. The logger name for this logger is
     * configurable, configuration looks like:
     *
     * <pre>
     *      &lt;env-entry&gt;
     *          &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
     *          &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     *          &lt;env-entry-value&gt;project_logger&lt;/env-entry-value&gt;
     *      &lt;/env-entry&gt;
     * </pre>
     *
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Represents the entity manager used in this class.
     * </p>
     * <p>
     * The persistence unit name can be configured. After the persistence unit name is read, this instance will be
     * looked up from session context with the configured persistence unit name.Sample configuration:
     *
     * <pre>
     * &lt;env-entry&gt;
     *     &lt;env-entry-name&gt;persistenceUnitName&lt;/env-entry-name&gt;
     *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     *     &lt;env-entry-value&gt;document_manager&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     *
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the role names that are allowed to get the methods in this bean.
     * </p>
     * <p>
     * J2EE security programmical API is used instead of annotation to make the authorization more flexible. The value
     * is looked up via JNDI. It should be configured in ejb-jar.xml for this authorization bean. It is set in the init
     * method which is called after the bean is constructed by EJB container. Configuration looks like the following:
     *
     * <pre>
     * &lt;env-entry&gt;
     *     &lt;env-entry-name&gt;allowedRoleNames&lt;/env-entry-name&gt;
     *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     *     &lt;env-entry-value&gt;admin,manager&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     *
     * Note, each allowed role name should be concatenated with a comma.
     * </p>
     */
    private String[] allowedRoleNames;

    /**
     * The default constructor for this bean. This is required by EJB3 specification. It does nothing.
     */
    public DocumentManagerBean() {
    }

    /**
     * This method will be called after this bean is constructed. This method will use SessionContext and ENC to set the
     * configuration properties of this bean.
     *
     * @throws IllegalStateException
     *             if the SessionContext instance is not set
     * @throws ConfigurationException
     *             if any required configuration properties are missing or invalid value(invalid configuration type,
     *             empty configuration value, etc).
     */
    @PostConstruct
    public void init() {
        try {
            InitialContext ctx = new InitialContext();

            // retrieve the optional loggerName property
            String loggerName = (String) getObject(ctx, "loggerName", false);

            // initialize the logger.
            if (null == loggerName) {
                logger = null;
            } else {
                logger = LogManager.getLog(loggerName);
            }

            logEnter("init()");

            // retrieve the required persistenceUnit property
            String persistenceUnit = (String) getObject(ctx, "persistenceUnitName", true);

            // initialize the entity manager
            entityManager = (EntityManager) getSessionContext().lookup(persistenceUnit);

            if (null == entityManager) {
                throw new ConfigurationException("Fail to retrieve EntityManager instance.");
            }

            // retrieve the required allowedRoleNames property
            String allowedRoleNamesValue = (String) getObject(ctx, "allowedRoleNames", true);
            if (0 == allowedRoleNamesValue.trim().length()) {
                throw logException(new ConfigurationException("the [allowedRoleNames] property should not empty."));
            }

            // initialize the allowed role names.
            allowedRoleNames = allowedRoleNamesValue.split(",");

        } catch (NamingException e) {
            throw logException(new ConfigurationException("Fail to retrieve config parameter from context.", e));
        } catch (ClassCastException e) {
            throw logException(new ConfigurationException("Invalid configuration type.", e));
        } catch (ConfigurationException e) {
            throw logException(e);
        } finally {
            logExit("init()");
        }
    }

    /**
     * Adds new document to the persistence layer.
     *
     * @param document
     *            the new document to add to the persistence layer
     * @return the newly added document.
     * @throws IllegalArgumentException
     *             if document is null
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Document addDocument(Document document) throws AuthorizationException, DocumentPersistenceException {
        logEnter("addDocument(Document)");
        logParameters("document:{0}", formatDocument(document));

        try {
            checkNull(document, "document");
            checkAuthorization();

            saveEntity(document);

            // create the DTO for transfer
            // just return document does not work. it seems the entity behave different
            // as it is controlled by EJB container, same to following methods.
            Document doc = copyDocument(document);
            logReturn(formatDocument(doc));
            return doc;
        } finally {
            logExit("addDocument(Document)");
        }
    }

    /**
     * Gets document by id from the persistence layer.
     *
     * @param documentId
     *            the id of the document to retrieve
     * @return the document from the persistence layer by document id or null if id can't be found.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Document getDocument(long documentId) throws AuthorizationException, DocumentPersistenceException {
        logEnter("getDocument(long)");
        logParameters("documentId: {0}", documentId);

        try {
            checkAuthorization();

            Document document = getEntity(Document.class, documentId);

            Document doc = copyDocument(document);
            logReturn(formatDocument(doc));
            return doc;
        } finally {
            logExit("getDocument(long)");
        }
    }

    /**
     * Gets all documents in all versions from the persistence layer. Note, an empty list will be returned if no record
     * in the persistence layer.
     *
     * @return all the documents in all versions from persistence layer.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Document> getAllDocuments() throws AuthorizationException, DocumentPersistenceException {
        logEnter("getAllDocuments()");
        try {
            checkAuthorization();

            List<Document> documents = getResultList(RETRIEVE_ALL_DOCUMENTS, null);

            List<Document> docs = new ArrayList<Document>();
            for (Document document : documents) {
                docs.add(copyDocument(document));
            }

            logReturn(formatDocuments(docs));
            return docs;
        } finally {
            logExit("getAllDocuments()");
        }
    }

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
     *            the new document version to add.
     * @return the newly added document version
     * @throws IllegalArgumentException
     *             if documentVersion is null or any attribute (except id, version) of the given documentVersion is not
     *             set or if any id of the reference attributes (except the associated set) is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentVersion addDocumentVersion(DocumentVersion documentVersion) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("addDocumentVersion(DocumentVersion)");
        logParameters("documentVersion: {0}", formatDocumentVersion(documentVersion));
        try {
            checkDocumentVersion(documentVersion, true);

            checkAuthorization();

            // try to find the current max version of the document.
            if (null == documentVersion.getDocumentVersion()) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("id", documentVersion.getDocument().getDocumentId());

                Integer maxVersion = (Integer) getSingleResult(QUERY_MAX_DOCUMENT_VERSION, parameters);

                // if no result found
                if (null == maxVersion) {
                    maxVersion = 0;
                }

                documentVersion.setDocumentVersion(maxVersion + 1);
            }
            saveEntity(documentVersion);

            DocumentVersion docVersion = copyDocumentVersion(documentVersion);
            logReturn(formatDocumentVersion(docVersion));

            return docVersion;
        } finally {
            logExit("addDocumentVersion(DocumentVersion)");
        }
    }

    /**
     * <p>
     * Updates document version in persistence layer with the given document version.
     * </p>
     * <p>
     * The primary key of the given document version is used to locate the corresponding document version in persistence
     * layer and update it.
     * </p>
     * <p>
     * Note, if the primary key is not found, then DocumentPersistenceException should be thrown.
     * </p>
     *
     * @param documentVersion
     *            the document version to update the corresponding record in persistence layer
     * @throws IllegalArgumentException
     *             if the given documentVersion is null or any attribute of the given documentVersion is not set or if
     *             any id of the reference attributes (except the associated set) is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDocumentVersion(DocumentVersion documentVersion) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("updateDocumentVersion(DocumentVersion)");
        logParameters("documentVersion: {0}", formatDocumentVersion(documentVersion));
        try {
            checkDocumentVersion(documentVersion, false);

            checkAuthorization();

            // retrieve the current entity in database.
            DocumentVersion dvEntity = getEntity(DocumentVersion.class, documentVersion.getDocumentVersionId());

            if (null == dvEntity) {
                throw logException(new DocumentPersistenceException("No corresponding record in persistence layer."));
            }

            // shallow copy all the attributes.
            dvEntity.setDocument(documentVersion.getDocument());
            // CompetionDocuments will not be updated.
            dvEntity.setCompetitionDocuments(documentVersion.getCompetitionDocuments());
            dvEntity.setContent(documentVersion.getContent());
            dvEntity.setDocumentName(documentVersion.getDocumentName());
            dvEntity.setDocumentVersion(documentVersion.getDocumentVersion());
            dvEntity.setVersionDate(documentVersion.getVersionDate());

            // updating will be done when the transaction is committed
            // in our case, it is after the invokation of this method by EJB container
        } finally {
            logExit("updateDocumentVersion(DocumentVersion)");
        }
    }

    /**
     * Gets concrete document version from the persistence layer. Note, version can be null and in this case the latest
     * document version should be selected.
     *
     * @param documentId
     *            the document id to locate the document version
     * @param version
     *            the version of the document, can be null
     * @return the document version from the persistence layer with the given document id and version (if version is
     *         null, the latest version is used) or null if no document version matches the given values.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentVersion getDocumentVersion(long documentId, Integer version) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getDocumentVersion(long, Integer)");
        logParameters("documentId: {0}, version: {1}", documentId, version);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("documentId", documentId);

            DocumentVersion documentVersion = null;
            if (null == version) {
                // retrieve the latest version of document version.
                documentVersion = (DocumentVersion) getSingleResult(RETRIEVE_LATEST_DOCUMENT_VERSION, parameters);
            } else {
                parameters.put("documentVersion", version);

                documentVersion = (DocumentVersion) getSingleResult(RETRIEVE_DOCUMENT_VERSION_BY_VERSION, parameters);
            }

            DocumentVersion docVersion = copyDocumentVersion(documentVersion);
            logReturn(formatDocumentVersion(docVersion));
            return docVersion;
        } finally {
            logExit("getDocumentVersion(long, Integer)");
        }
    }

    /**
     * Gets document version by the given id from the persistence layer. Parameters:
     *
     * @param documentVersionId
     *            the id of document version
     * @return the document version by the given id from the persistence layer or null if id can't be found
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentVersion getDocumentVersion(long documentVersionId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getDocumentVersion(long)");
        logParameters("documentVersionId: {0}", documentVersionId);

        try {
            checkAuthorization();

            DocumentVersion documentVersion = getEntity(DocumentVersion.class, documentVersionId);
            DocumentVersion docVersion = copyDocumentVersion(documentVersion);
            logReturn(formatDocumentVersion(docVersion));
            return docVersion;
        } finally {
            logExit("getDocumentVersion(long)");
        }
    }

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
     * @return the newly added member answer.
     * @throws IllegalArgumentException
     *             if memberAnswer is null or any attribute (except id) of the member answer is not set or if any id of
     *             the reference attributes (except the associated set) is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws CompetitionDocumentAnsweredException
     *             if the user has answered the version of competition document
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MemberAnswer addMemberAnswer(MemberAnswer memberAnswer) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("addMemberAnswer(MemberAnswer)");
        logParameters("memberAnswer: {0}", formatMemberAnswer(memberAnswer));

        try {
            checkMemberAnswer(memberAnswer, true);

            checkAuthorization();

            // a user can not answer twice for the same version of competition document.
            if (isCompetitionDocumentAnswered(memberAnswer.getCompetitionDocument().getCompetitionDocumentId(),
                    memberAnswer.getMemberId())) {
                throw logException(new CompetitionDocumentAnsweredException(
                        "The user has answered the version of competition document.", memberAnswer));
            }

            saveEntity(memberAnswer);

            MemberAnswer memAnswer = copyMemberAnswer(memberAnswer);
            logReturn(formatMemberAnswer(memAnswer));
            return memAnswer;
        } finally {
            logExit("addMemberAnswer(MemberAnswer)");
        }
    }

    /**
     * <p>
     * Updates member answer with the given value in the persistence layer. The primary key of the given member answer
     * is used to locate the corresponding member answer in persistence layer and update it.
     * </p>
     * <p>
     * If the primary key can't be found, DocumentPersistenceException should be thrown.
     * </p>
     *
     * @param memberAnswer
     *            the member answer to update the corresponding answer in the persistence layer
     * @throws IllegalArgumentException
     *             if memberAnswer is null or any attribute of the given memberAnswer is not set or if any id of the
     *             reference attributes is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMemberAnswer(MemberAnswer memberAnswer) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("updateMemberAnswer(MemberAnswer)");
        logParameters("memberAnswer: {0}", formatMemberAnswer(memberAnswer));
        try {
            checkMemberAnswer(memberAnswer, false);

            checkAuthorization();

            Long memberAnswerId = memberAnswer.getMemberAnswerId();
            MemberAnswer maEntity = getEntity(MemberAnswer.class, memberAnswerId);

            if (null == maEntity) {
                throw logException(new DocumentPersistenceException("No MemberAnswer record with id - "
                        + memberAnswerId));
            }

            // shallow copy all the attributes.
            maEntity.setAnswer(memberAnswer.getAnswer());
            maEntity.setAnswerDate(memberAnswer.getAnswerDate());
            maEntity.setCompetitionDocument(memberAnswer.getCompetitionDocument());
            maEntity.setMemberId(memberAnswer.getMemberId());
        } finally {
            logExit("updateMemberAnswer(MemberAnswer)");
        }
    }

    /**
     * Deletes the given member answer with the given id from the persistence layer. If the record is deleted, then
     * return true, otherwise false.
     *
     * @param memberAnswerId
     *            the id of the member answer to delete
     * @return true if the member answer is deleted, otherwise false
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteMemberAnswer(long memberAnswerId) throws AuthorizationException, DocumentPersistenceException {
        logEnter("deleteMemberAnswer(long)");
        logParameters("memberAnswerId: {0}", memberAnswerId);
        try {
            checkAuthorization();

            boolean removed = removeEntity(MemberAnswer.class, memberAnswerId);
            logReturn(String.valueOf(removed));
            return removed;
        } finally {
            logExit("deleteMemberAnswer(long)");
        }
    }

    /**
     * Gets member answer by member answer id from the persistence layer.
     *
     * @param memberAnswerId
     *            the id of the member answer
     * @return the member answer with the given id from the persistence layer or null if id can't be found
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MemberAnswer getMemberAnswer(long memberAnswerId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getMemberAnswer(long)");
        logParameters("memberAnswerId: {0}", memberAnswerId);
        try {
            checkAuthorization();

            MemberAnswer memAnswer = copyMemberAnswer(getEntity(MemberAnswer.class, memberAnswerId));
            logReturn(formatMemberAnswer(memAnswer));
            return memAnswer;
        } finally {
            logExit("getMemberAnswer(long)");
        }
    }

    /**
     * Adds new version of document to competition for specific role.
     *
     * @param competitionDocument
     *            the competition document to add
     * @return the newly added competition document
     * @throws IllegalArgumentException
     *             if competitionDocument is null or any attribute (except id) of the competition document is not set or
     *             if any id of the reference attributes (except the associated set) is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CompetitionDocument addCompetitionDocument(CompetitionDocument competitionDocument)
        throws AuthorizationException, DocumentPersistenceException {
        logEnter("addCompetitionDocument(CompetitionDocument)");
        logParameters("competitionDocument: {0}", formatCompetitionDocument(competitionDocument));

        try {
            checkCompetitionDocument(competitionDocument, true);

            checkAuthorization();

            saveEntity(competitionDocument);

            CompetitionDocument cd = copyCompetitionDocument(competitionDocument);
            logReturn(formatCompetitionDocument(cd));
            return cd;
        } finally {
            logExit("addCompetitionDocument(CompetitionDocument)");
        }
    }

    /**
     * <p>
     * Updates version of document associated with competition and specific role.
     * </p>
     * <p>
     * The primary key of the given member answer is used to locate the corresponding member answer in persistence layer
     * and update it. Note, if the primary key can't be not found, then DocumentPersistenceException should be thrown.
     * </p>
     *
     * @param competitionDocument
     *            the given competition document to update in the persistence layer
     * @throws IllegalArgumentException
     *             if competitionDocument is null or any attribute of the competition document is not set or if any id
     *             of the reference attributes (except the associated set) is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCompetitionDocument(CompetitionDocument competitionDocument) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("updateCompetitionDocument(CompetitionDocument)");
        logParameters("competitionDocument: {0}", formatCompetitionDocument(competitionDocument));

        try {
            checkCompetitionDocument(competitionDocument, false);
            checkAuthorization();

            Long competitionDocumentId = competitionDocument.getCompetitionDocumentId();
            CompetitionDocument cdEntity = getEntity(CompetitionDocument.class, competitionDocumentId);

            // no associated CompetitionDocument record in database.
            if (null == cdEntity) {
                throw logException(new DocumentPersistenceException("No CompetitionDocument record found with id - "
                        + competitionDocumentId));
            }

            // shallow copy all the attributes.
            cdEntity.setCompetitionId(competitionDocument.getCompetitionId());
            cdEntity.setDocumentVersion(competitionDocument.getDocumentVersion());

            // currently, the set is not cascading updated.
            cdEntity.setMemberAnswers(competitionDocument.getMemberAnswers());
            cdEntity.setRoleId(competitionDocument.getRoleId());
        } finally {
            logExit("updateCompetitionDocument(CompetitionDocument)");
        }
    }

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
     *             if competitionDocument is null or its id is not set
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteCompetitionDocument(CompetitionDocument competitionDocument) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("deleteCompetitionDocument(CompetitionDocument)");
        logParameters("competitionDocument: {0}", formatCompetitionDocument(competitionDocument));

        try {
            checkNull(competitionDocument, "competitionDocument");
            Long competitionDocumentId = competitionDocument.getCompetitionDocumentId();
            if (null == competitionDocumentId) {
                throw logException(new IllegalArgumentException("The id of CompetitionDocument is not set."));
            }

            checkAuthorization();

            boolean removed = removeEntity(CompetitionDocument.class, competitionDocumentId);
            logReturn(String.valueOf(removed));
            return removed;
        } finally {
            logExit("deleteCompetitionDocument(CompetitionDocument)");
        }
    }

    /**
     * Deletes all documents for the given competition id from persistence layer.
     *
     * @param competitionId
     *            the id of competition to locate all the competition documents.
     * @return true if there is any competition document is deleted, false otherwise
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteCompetitionDocuments(long competitionId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("deleteCompetitionDocuments(long)");
        logParameters("competitionId:", competitionId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("competitionId", competitionId);

            boolean success = executeUpdate(DELETE_CD_BY_CID, parameters) > 0;
            logReturn(String.valueOf(success));
            return success;
        } finally {
            logExit("deleteCompetitionDocuments(long)");
        }
    }

    /**
     * Delete all documents for role in competition.
     *
     * @param competitionId
     *            the id of competition
     * @param roleId
     *            the role id in the competition
     * @return true if there is any competition document is deleted, false otherwise
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteCompetitionDocuments(long competitionId, long roleId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("deleteCompetitionDocuments(long, long)");
        logParameters("competitionId: {0}, roleId: {1}", competitionId, roleId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("competitionId", competitionId);
            parameters.put("roleId", roleId);

            boolean success = executeUpdate(DELETE_CD_BY_CID_RID, parameters) > 0;
            logReturn(String.valueOf(success));
            return success;
        } finally {
            logExit("deleteCompetitionDocuments(long, long)");
        }
    }

    /**
     * Gets the competition document for the given competition document id from the persistence layer.
     *
     * @param competitionDocumentId
     *            the id of the competition document
     * @return the corresponding CompetitionDocument from the persistence layer or null if the id can't be found
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CompetitionDocument getCompetitionDocument(long competitionDocumentId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getCompetitionDocument(long)");
        logParameters("competitionDocumentId: {0}", competitionDocumentId);
        try {
            checkAuthorization();

            CompetitionDocument competitionDocument = getEntity(CompetitionDocument.class, competitionDocumentId);
            CompetitionDocument cd = copyCompetitionDocument(competitionDocument);
            logReturn(formatCompetitionDocument(cd));
            return cd;
        } finally {
            logExit("getCompetitionDocument(long)");
        }
    }

    /**
     * Gets the competition document from the persistence layer which has the given document version id, competition id
     * and role id.
     *
     * @param documentVersionId
     *            the id of the competition document version
     * @param competitionId
     *            the id of the competition
     * @param roleId
     *            the id of the role
     * @return the corresponding CompetitionDocument from the persistence layer or null if no competition document
     *         matches the given values
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CompetitionDocument getCompetitionDocument(long documentVersionId, long competitionId, long roleId)
        throws AuthorizationException, DocumentPersistenceException {
        logEnter("getCompetitionDocument(long, long, long)");
        logParameters("documentVersionId: {0}, competitionId: {1}, roleId: {2}", documentVersionId, competitionId,
                roleId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("documentVersionId", documentVersionId);
            parameters.put("competitionId", competitionId);
            parameters.put("roleId", roleId);

            CompetitionDocument cd = copyCompetitionDocument((CompetitionDocument) getSingleResult(
                    RETRIEVE_CD_BY_ID_CID_RID, parameters));
            logReturn(formatCompetitionDocument(cd));
            return cd;
        } finally {
            logExit("getCompetitionDocument(long, long, long)");
        }
    }

    /**
     * Gets competition documents for specific role from persistence layer. Note, an empty list will be returned if no
     * corresponding records in the persistence layer.
     *
     * @param roleId
     *            the id of role.
     * @return concrete document versions for specific role from persistence layer
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CompetitionDocument> getCompetitionDocumentsByRole(long roleId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getCompetitionDocumentsByRole(long)");
        logParameters("roleId: {0}", roleId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("roleId", roleId);

            List<CompetitionDocument> competitionDocuments = copyCompetitionDocuments(getResultList(RETRIEVE_CD_BY_RID,
                    parameters));

            logReturn(formatCompetitionDocuments(competitionDocuments));
            return competitionDocuments;
        } finally {
            logExit("getCompetitionDocumentsByRole(long)");
        }
    }

    /**
     * Gets competition documents for specific competition from persistence layer. Note, an empty list will be returned
     * if no corresponding records in the persistence layer.
     *
     * @param competitionId
     *            the id of competition
     * @return concrete document versions for specific competition from persistence layer.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CompetitionDocument> getCompetitionDocumentsByCompetition(long competitionId)
        throws AuthorizationException, DocumentPersistenceException {
        logEnter("getCompetitionDocumentsByCompetition(long)");
        logParameters("competitionId: {0}", competitionId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("competitionId", competitionId);

            List<CompetitionDocument> competitionDocuments = copyCompetitionDocuments(getResultList(RETRIEVE_CD_BY_CID,
                    parameters));

            logReturn(formatCompetitionDocuments(competitionDocuments));
            return competitionDocuments;
        } finally {
            logExit("getCompetitionDocumentsByCompetition(long)");
        }
    }

    /**
     * Gets competition documents for specific role and competition from persistence layer.
     *
     * @param competitionId
     *            the id of competition
     * @param roleId
     *            the id of role
     * @return concrete versions for specific role and competition from persistence layer.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CompetitionDocument> getCompetitionDocuments(long competitionId, long roleId)
        throws AuthorizationException, DocumentPersistenceException {
        logEnter("getCompetitionDocuments(long, long)");
        logParameters("competitionId: {0}, roleId: {1}", competitionId, roleId);

        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("competitionId", competitionId);
            parameters.put("roleId", roleId);

            List<CompetitionDocument> competitionDocuments = copyCompetitionDocuments(getResultList(
                    RETRIEVE_CD_BY_CID_RID, parameters));

            logReturn(formatCompetitionDocuments(competitionDocuments));
            return competitionDocuments;
        } finally {
            logExit("getCompetitionDocuments(long)");
        }
    }

    /**
     * Adds new document name to the persistence layer.
     *
     * @param documentName
     *            the new document name to add to the persistence layer
     * @return the newly added document name.
     * @throws IllegalArgumentException
     *             if document name is null
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentName addDocumentName(DocumentName documentName) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("addDocumentName(DocumentName)");
        logParameters("documentName: {0}", formatDocumentName(documentName));

        try {
            checkNull(documentName, "documentName");
            checkAuthorization();

            saveEntity(documentName);

            DocumentName docName = copyDocumentName(documentName);
            logReturn(formatDocumentName(docName));
            return docName;
        } finally {
            logExit("addDocumentName(DocumentName)");
        }
    }

    /**
     * Gets document name by id from the persistence layer.
     *
     * @param documentNameId
     *            the id of the document name to retrieve
     * @return the document name from the persistence layer by document name id or null if id can't be found
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any other error occurs in the persistence layer
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentName getDocumentName(long documentNameId) throws AuthorizationException,
        DocumentPersistenceException {
        logEnter("getDocumentName(long)");
        logParameters("documentNameId: {0}", documentNameId);

        try {
            checkAuthorization();

            DocumentName docName = copyDocumentName(getEntity(DocumentName.class, documentNameId));
            logReturn(formatDocumentName(docName));
            return docName;
        } finally {
            logExit("getDocumentName(long)");
        }
    }

    /**
     * Gets all document names from the persistence layer.
     *
     * @return all the document names from persistence layer. Note, an empty list will be returned if no record in the
     *         persistence layer.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DocumentName> getAllDocumentNames() throws AuthorizationException, DocumentPersistenceException {
        logEnter("getAllDocumentNames()");
        try {
            checkAuthorization();

            List<DocumentName> documentNames = getResultList(RETRIEVE_ALL_DOCUMENTNAMES, null);
            List<DocumentName> docNames = new ArrayList<DocumentName>();
            for (DocumentName documentName : documentNames) {
                docNames.add(copyDocumentName(documentName));
            }

            logReturn(formatDocumentNames(docNames));
            return docNames;
        } finally {
            logExit("getAllDocumentNames()");
        }
    }

    /**
     * Returns whether the given competition document is answered by the given member or not.
     *
     * @param competitionDocumentId
     *            the id of the competition document
     * @param memberId
     *            the id of the the member who accepts or rejects the competition document
     * @return true if the competition document has been answered by the member, otherwise false.
     * @throws IllegalStateException
     *             if the SessionContext instance or EntityManager instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     * @throws DocumentPersistenceException
     *             if any error occurs in the persistence layer.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean isCompetitionDocumentAnswered(long competitionDocumentId, long memberId)
        throws AuthorizationException, DocumentPersistenceException {
        logEnter("isCompetitionDocumentAnswered(long, long)");
        logParameters("competitionDocumentId: {0}, memberId: {1}, ", competitionDocumentId, memberId);
        try {
            checkAuthorization();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("competitonDocumentId", competitionDocumentId);
            parameters.put("memberId", memberId);

            Long count = (Long) getSingleResult(QUERY_COMPETITION_DOCUMENT_ANSWERED, parameters);

            boolean isAnswered = count.longValue() != 0;

            logReturn(String.valueOf(isAnswered));
            return isAnswered;
        } finally {
            logExit("isCompetitionDocumentAnswered(long, long)");
        }
    }

    /**
     * <p>
     * Gets the single result from persistence.
     * </p>
     *
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @return the found entity, or null if not found.
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any error occurs except the NoResultException.
     */
    private Object getSingleResult(String qlString, Map<String, Object> parameters)
        throws DocumentPersistenceException {
        logMessage("Executing query [{0}] with parameters {1} to get the single result.", qlString, parameters);

        try {
            Query query = buildQuery(qlString, parameters);
            Object obj = query.getSingleResult();

            logMessage("Successfully execute query [{0}] with parameters {1} to get the single result.", qlString,
                    parameters);

            return obj;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            logMessage("Failed to execute query [{0}] with parameters {1} to get the single result.", qlString,
                    parameters);
            throw logException(new DocumentPersistenceException("Multiple results exist.", e));
        } catch (IllegalStateException e) {
            logMessage("Failed to execute query [{0}] with parameters {1} to get the single result.", qlString,
                    parameters);
            throw logException(new DocumentPersistenceException(
                    "Called for a Java Persistence query language UPDATE or DELETE statement", e));
        }
    }

    /**
     * <p>
     * Builds the Query object from the query string and parameters map.
     * </p>
     *
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @return the created query
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any problem occurs.
     */
    private Query buildQuery(String qlString, Map<String, Object> parameters) throws DocumentPersistenceException {
        try {
            Query query = getEntityManager().createQuery(qlString);

            if (null != parameters) {
                for (Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return query;
        } catch (IllegalStateException e) {
            throw logException(new DocumentPersistenceException("the EntityManager has been closed.", e));
        } catch (IllegalArgumentException e) {
            throw logException(new DocumentPersistenceException("Fail to build the query.", e));
        }
    }

    /**
     * <p>
     * Gets the result list from persistence.
     * </p>
     *
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @return the result list, possible empty. never null.
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any error occurs except the NoResultException.
     */
    @SuppressWarnings("unchecked")
    private List getResultList(String qlString, Map<String, Object> parameters) throws DocumentPersistenceException {
        logMessage("Executing query [{0}] with parameters {1}.", qlString, parameters);

        try {
            Query query = buildQuery(qlString, parameters);
            List result = query.getResultList();
            logMessage("Sucessfully execute the query [{0}] with parameters {1}.", qlString, parameters);

            return result;
        } catch (IllegalStateException e) {
            logMessage("Failed to execute query [{0}] with parameters {1}.", qlString, parameters);
            throw logException(new DocumentPersistenceException(
                    "Called for a Java Persistence query language UPDATE or DELETE statement", e));
        }
    }

    /**
     * <p>
     * Executes the update against the persistence.
     * </p>
     *
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @return the count of the affected record.
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any problem occurs.
     */
    private int executeUpdate(String qlString, Map<String, Object> parameters) throws DocumentPersistenceException {
        logMessage("Executing update [{0}] with parameters {1}.", qlString, parameters);

        try {
            Query query = buildQuery(qlString, parameters);

            int count = query.executeUpdate();

            logMessage("Successfully execute update.");

            return count;
        } catch (IllegalStateException e) {
            logMessage("Fail to execute update.");
            throw logException(new DocumentPersistenceException(
                    "called for a Java Persistence query language SELECT statement", e));
        }
    }

    /**
     * <p>
     * Retrieve the configuration object from context.
     * </p>
     *
     * @param ctx
     *            the context to retrieve.
     * @param name
     *            the name of the object to look up
     * @param required
     *            whether the property is required.
     * @return the configured object.
     * @throws ConfigurationException
     *             If the required property is missing.
     * @throws NamingException
     *             If a naming exception is encountered.
     */
    private static Object getObject(InitialContext ctx, String name, boolean required) throws ConfigurationException,
        NamingException {
        Object obj = null;
        try {
            obj = ctx.lookup("java:comp/env/" + name);
        } catch (NameNotFoundException e) {
            obj = null;
        }

        if (required && null == obj) {
            throw new ConfigurationException("The required [" + name + "] is missing.");
        }
        return obj;

    }

    /**
     * <p>
     * Retrieves the <code>SessionContext</code> instance.
     * </p>
     *
     * @throws IllegalStateException
     *             if the SessionContext instance is not set
     * @return the SessionContext instance.
     */
    private SessionContext getSessionContext() {
        if (null == sessionContext) {
            throw logException(new IllegalStateException("the SessionContext instance is not set."));
        }

        return sessionContext;
    }

    /**
     * <p>
     * Authorizes the user request.
     * </p>
     *
     * @throws IllegalStateException
     *             if the SessionContext instance is not set
     * @throws AuthorizationException
     *             if the request is not authorized
     */
    private void checkAuthorization() throws AuthorizationException {
        logMessage("Authorizing user request.");

        boolean isValid = false;
        for (String roleName : allowedRoleNames) {
            if (getSessionContext().isCallerInRole(roleName)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            logMessage("User request unauthorized.");
            throw logException(new AuthorizationException("The user request is not authorized."));
        }

        logMessage("User request authorized.");
    }

    /**
     * <p>
     * Saves the entity into persistence.
     * </p>
     *
     * @param obj
     *            the entity object to save.
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any error occurs while saving the entity.
     */
    private void saveEntity(Object obj) throws DocumentPersistenceException {
        try {
            logMessage("Saving entity.");

            getEntityManager().persist(obj);

            logMessage("Successfully persist entity.");
        } catch (IllegalStateException e) {
            throw logException(new DocumentPersistenceException("the EntityManager has been closed.", e));
        } catch (IllegalArgumentException e) {
            throw logException(new DocumentPersistenceException("The object is not an entity.", e));
        } catch (EntityExistsException e) {
            throw logException(new DocumentPersistenceException("Entity already exists.", e));
        } catch (TransactionRequiredException e) {
            throw logException(new DocumentPersistenceException("No transaction", e));
        } catch (PersistenceException e) {
            throw logException(new DocumentPersistenceException("Persistence problem occurs.", e));
        }
    }

    /**
     * <p>
     * Removes the entity from persistence layer.
     * </p>
     *
     * @param entityClass
     *            the entity class type.
     * @param primaryKey
     *            the primary key to find the entity
     * @return true if the entity is removed, or false if the entity does not found
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any error occurs while removing the entity.
     */
    private <T> boolean removeEntity(Class<T> entityClass, Object primaryKey) throws DocumentPersistenceException {
        T entity = getEntity(entityClass, primaryKey);

        if (null == entity) {
            logMessage("Unable find entity {0} with primary key [{1}].", entityClass.getName(), primaryKey);
            return false;
        }
        try {
            logMessage("Removing entity {0} with primary key [{1}].", entityClass.getName(), primaryKey);

            getEntityManager().remove(entity);

        } catch (TransactionRequiredException e) {
            throw logException(new DocumentPersistenceException("No transaction", e));
        } catch (IllegalStateException e) {
            throw logException(new DocumentPersistenceException("the EntityManager has been closed.", e));
        } catch (IllegalArgumentException e) {
            throw logException(new DocumentPersistenceException("The object is not an entity.", e));
        }

        logMessage("Entity removed.");

        return true;
    }

    /**
     * <p>
     * Gets the entity from persistence layer.
     * </p>
     *
     * @param entityClass
     *            the entity class type.
     * @param primaryKey
     *            the primary key to find the entity
     * @return the entity instance, or null if not found.
     * @throws IllegalStateException
     *             if the EntityManager instance is not set
     * @throws DocumentPersistenceException
     *             If any error occurs while retrieving the entity.
     */
    private <T> T getEntity(Class<T> entityClass, Object primaryKey) throws DocumentPersistenceException {
        logMessage("Retrieving {0} by primary key [{1}].", entityClass.getName(), primaryKey);

        try {
            return getEntityManager().find(entityClass, primaryKey);
        } catch (IllegalStateException e) {
            throw logException(new DocumentPersistenceException("the EntityManager has been closed.", e));
        } catch (IllegalArgumentException e) {
            throw logException(new DocumentPersistenceException("The object is not an entity.", e));
        } finally {
            logMessage("Retrieving finished.");
        }
    }

    /**
     * <p>
     * Checks the validation of the DocumentVersion instance.
     * </p>
     *
     * @param documentVersion
     *            the DocumentVersion instance to check
     * @param isAdd
     *            whether it is add operation.
     * @throws IllegalArgumentException
     *             If violation presents.
     */
    private void checkDocumentVersion(DocumentVersion documentVersion, boolean isAdd) {
        try {
            Helper.checkNull(documentVersion, "documentVersion");

            if (!isAdd) {
                if (null == documentVersion.getDocumentVersionId()) {
                    throw new IllegalArgumentException("Attribute [documentVersionId] is not set.");
                }

                if (null == documentVersion.getDocumentVersion()) {
                    throw new IllegalArgumentException("Attribute [documentVersion] is not set.");
                }
            }

            if (null == documentVersion.getVersionDate()) {
                throw new IllegalArgumentException("Attribute [versionDate] is not set.");
            }

            // content can not be empty, as the setter does not allowed.
            if (null == documentVersion.getContent()) {
                throw new IllegalArgumentException("Attribute [content] is not set.");
            }

            if (null == documentVersion.getDocument()) {
                throw new IllegalArgumentException("Reference attribute [document] is not set.");
            } else if (null == documentVersion.getDocument().getDocumentId()) {
                throw new IllegalArgumentException("The Id of reference attribute [document] is not set.");
            }

            if (null == documentVersion.getDocumentName()) {
                throw new IllegalArgumentException("Reference attribute [documentName] is not set.");
            } else if (null == documentVersion.getDocumentName().getDocumentNameId()) {
                throw new IllegalArgumentException("The id of reference attribute [documentName] is not set.");
            }
        } catch (IllegalArgumentException e) {
            throw logException(e);
        }
    }

    /**
     * <p>
     * Checks the validation of the MemberAnswer instance.
     * </p>
     *
     * @param memberAnswer
     *            the MemberAnswer instance to check
     * @param isAdd
     *            whether it is add operation.
     * @throws IllegalArgumentException
     *             If violation presents.
     */
    private void checkMemberAnswer(MemberAnswer memberAnswer, boolean isAdd) {
        try {
            Helper.checkNull(memberAnswer, "memberAnswer");

            if (!isAdd && null == memberAnswer.getMemberAnswerId()) {
                throw new IllegalArgumentException("Attribute [memberAnswerId] is not set.");
            }

            if (null == memberAnswer.getMemberId()) {
                throw new IllegalArgumentException("Attribute [memberId] is not set.");
            }

            if (null == memberAnswer.getAnswerDate()) {
                throw new IllegalArgumentException("Attribute [answerDate] is not set.");
            }

            if (null == memberAnswer.getCompetitionDocument()) {
                throw new IllegalArgumentException("Reference attribute [competitionDocument] is not set.");
            } else if (null == memberAnswer.getCompetitionDocument().getCompetitionDocumentId()) {
                throw new IllegalArgumentException("The id of reference attribute [competitionDocument] is not set.");
            }
        } catch (IllegalArgumentException e) {
            throw logException(e);
        }
    }

    /**
     * <p>
     * Checks the validation of the CompetitionDocument instance.
     * </p>
     *
     * @param competitionDocument
     *            the CompetitionDocument instance to check
     * @param isAdd
     *            whether it is add operation.
     * @throws IllegalArgumentException
     *             If violation presents.
     */
    private void checkCompetitionDocument(CompetitionDocument competitionDocument, boolean isAdd) {
        try {
            Helper.checkNull(competitionDocument, "competitionDocument");

            if (!isAdd && null == competitionDocument.getCompetitionDocumentId()) {
                throw new IllegalArgumentException("Attribute [competitionDocumentId] is not set.");
            }

            if (null == competitionDocument.getCompetitionId()) {
                throw new IllegalArgumentException("Attribute [competitionId] is not set.");
            }

            if (null == competitionDocument.getRoleId()) {
                throw new IllegalArgumentException("Attribute [roleId] is not set.");
            }

            if (null == competitionDocument.getDocumentVersion()) {
                throw new IllegalArgumentException("Reference attribute [documentVersion] is not set.");
            } else if (null == competitionDocument.getDocumentVersion().getDocumentVersionId()) {
                throw new IllegalArgumentException("The id of reference attribute [documentVersion] is not set.");
            }
        } catch (IllegalArgumentException e) {
            throw logException(e);
        }
    }

    /**
     * <p>
     * Gets the <code>EntityManager</code> instance.
     * </p>
     *
     * @return the EntityManager instance
     * @throws IllegalStateException
     *             If the EntityManager instance is not initialized.
     */
    private EntityManager getEntityManager() {
        if (null == entityManager) {
            throw logException(new IllegalStateException("The entityManager instance is not initialized."));
        }

        return entityManager;
    }

    /**
     * <p>
     * Logs the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the method to enter.
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "Enter into method [DocumentManagerBean#{0}]", methodName);
        }
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "Exit method [DocumentManagerBean#{0}].", methodName);
        }
    }

    /**
     * <p>
     * Logs the parameters that when calling the specified method.
     * </p>
     *
     * @param pattern
     *            the pattern to fill the parameter values.
     * @param parameters
     *            an array of objects to be formatted and substituted.
     */
    private void logParameters(String pattern, Object... parameters) {
        if (logger != null) {
            logger.log(Level.INFO, "Parameters: {0}", MessageFormat.format(pattern, parameters));
        }
    }

    /**
     * <p>
     * Logs the returned value by a method call.
     * </p>
     *
     * @param message
     *            the message to log.
     */
    private void logReturn(String message) {
        if (logger != null) {
            logger.log(Level.INFO, "Returns: {0}", message);
        }
    }

    /**
     * <p>
     * Logs the parameters that when calling the specified method.
     * </p>
     *
     * @param pattern
     *            the pattern to fill the parameter values.
     * @param parameters
     *            an array of objects to be formatted and substituted.
     */
    private void logMessage(String pattern, Object... parameters) {
        if (logger != null) {
            logger.log(Level.INFO, MessageFormat.format(pattern, parameters));
        }
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * @param exception
     *            the exception thrown.
     * @return the logged exception
     */
    private <T extends Exception> T logException(T exception) {
        if (logger != null) {
            logger.log(Level.ERROR, exception, exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Makes a copy of the provided Document instance.
     * </p>
     *
     * @param document
     *            the Document instance to copy from.
     * @return the copied Document instance
     */
    private static Document copyDocument(Document document) {
        if (document == null) {
            return null;
        }

        Document doc = new Document();
        doc.setCreateDate(document.getCreateDate());
        doc.setDescription(document.getDescription());
        doc.setDocumentId(document.getDocumentId());
        doc.setDocumentVersions(document.getDocumentVersions());
        return doc;
    }

    /**
     * <p>
     * Makes a copy of the provided DocumentName instance.
     * </p>
     *
     * @param documentName
     *            the DocumentName instance to copy from.
     * @return the copied DocumentName instance
     */
    private static DocumentName copyDocumentName(DocumentName documentName) {
        if (documentName == null) {
            return null;
        }

        DocumentName docName = new DocumentName();
        docName.setDocumentNameId(documentName.getDocumentNameId());
        docName.setName(documentName.getName());
        docName.setDocumentVersions(documentName.getDocumentVersions());
        return docName;
    }

    /**
     * <p>
     * Makes a copy of the provided DocumentVersion instance.
     * </p>
     *
     * @param documentName
     *            the DocumentVersion instance to copy from.
     * @return the copied DocumentVersion instance
     */
    private static DocumentVersion copyDocumentVersion(DocumentVersion documentVersion) {
        if (documentVersion == null) {
            return null;
        }

        DocumentVersion docVersion = new DocumentVersion();
        docVersion.setCompetitionDocuments(documentVersion.getCompetitionDocuments());
        docVersion.setContent(documentVersion.getContent());
        docVersion.setDocument(documentVersion.getDocument());
        docVersion.setDocumentName(documentVersion.getDocumentName());
        docVersion.setDocumentVersion(documentVersion.getDocumentVersion());
        docVersion.setDocumentVersionId(documentVersion.getDocumentVersionId());
        docVersion.setVersionDate(documentVersion.getVersionDate());
        return docVersion;
    }

    /**
     * <p>
     * Makes a copy of the provided MemberAnswer instance.
     * </p>
     *
     * @param memberAnswer
     *            the MemberAnswer instance to copy from.
     * @return the copied MemberAnswer instance
     */
    private static MemberAnswer copyMemberAnswer(MemberAnswer memberAnswer) {
        if (memberAnswer == null) {
            return null;
        }

        MemberAnswer memAnswer = new MemberAnswer();
        memAnswer.setAnswer(memberAnswer.getAnswer());
        memAnswer.setAnswerDate(memberAnswer.getAnswerDate());
        memAnswer.setCompetitionDocument(memberAnswer.getCompetitionDocument());
        memAnswer.setMemberAnswerId(memberAnswer.getMemberAnswerId());
        memAnswer.setMemberId(memberAnswer.getMemberId());
        return memAnswer;
    }

    /**
     * <p>
     * Makes a copy of the provided CompetitionDocument instance.
     * </p>
     *
     * @param competitionDocument
     *            the CompetitionDocument instance to copy from.
     * @return the copied CompetitionDocument instance
     */
    private static CompetitionDocument copyCompetitionDocument(CompetitionDocument competitionDocument) {
        if (competitionDocument == null) {
            return null;
        }

        CompetitionDocument cd = new CompetitionDocument();
        cd.setCompetitionDocumentId(competitionDocument.getCompetitionDocumentId());
        cd.setCompetitionId(competitionDocument.getCompetitionId());
        cd.setDocumentVersion(competitionDocument.getDocumentVersion());
        cd.setMemberAnswers(competitionDocument.getMemberAnswers());
        cd.setRoleId(competitionDocument.getRoleId());
        return cd;
    }

    /**
     * <p>
     * Makes a copy of the provided list of CompetitionDocument instance .
     * </p>
     *
     * @param competitionDocuments
     *            the list of CompetitionDocument instance to copy from.
     * @return the copied CompetitionDocument instance list
     */
    private static List<CompetitionDocument> copyCompetitionDocuments(List<CompetitionDocument> competitionDocuments) {
        List<CompetitionDocument> cds = new ArrayList<CompetitionDocument>();
        for (CompetitionDocument competitionDocument : competitionDocuments) {
            cds.add(copyCompetitionDocument(competitionDocument));
        }

        return cds;
    }

    /**
     * <p>
     * Checks whether the specified object is null or not.
     * </p>
     *
     * @param obj
     *            the specified object to check.
     * @param name
     *            the object name.
     * @throws IllegalArgumentException
     *             If the object is null.
     */
    private void checkNull(Object obj, String name) {
        if (null == obj) {
            throw logException(new IllegalArgumentException("Parameter [" + name + "] can not be null."));
        }
    }

    /**
     * <p>
     * Formats the Document instance into string representation.
     * </p>
     *
     * @param document
     *            the Document instance to format.
     * @return the string representation of the Document instance.
     */
    private static String formatDocument(Document document) {
        if (null == document) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("documentId: ").append(document.getDocumentId());
        builder.append(", description: ").append(document.getDescription());
        builder.append(", createDate: ").append(document.getCreateDate());

        builder.append(", associated document version ids: [");
        boolean first = true;
        for (DocumentVersion documentVersion : document.getDocumentVersions()) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(documentVersion.getDocumentVersionId());
        }
        builder.append("]");
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the Document instance list into string representation.
     * </p>
     *
     * @param documents
     *            the Document instance list to format.
     * @return the string representation of the Document instance list.
     */
    private static String formatDocuments(List<Document> documents) {
        // documents never null.

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        boolean first = true;
        for (Document document : documents) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(formatDocument(document));
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the DocumentName instance into string representation.
     * </p>
     *
     * @param document
     *            the DocumentName instance to format.
     * @return the string representation of the DocumentName instance.
     */
    private static String formatDocumentName(DocumentName documentName) {
        if (null == documentName) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("documentNameId: ").append(documentName.getDocumentNameId());
        builder.append(", name: ").append(documentName.getName());
        builder.append(", associated document version ids: [");
        boolean first = true;
        for (DocumentVersion documentVersion : documentName.getDocumentVersions()) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(documentVersion.getDocumentVersionId());
        }
        builder.append("]");
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the DocumentName instance list into string representation.
     * </p>
     *
     * @param document
     *            the DocumentName instance list to format.
     * @return the string representation of the DocumentName list instance.
     */
    private static String formatDocumentNames(List<DocumentName> documentNames) {
        // documentNames never null.

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        boolean first = true;
        for (DocumentName documentName : documentNames) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(formatDocumentName(documentName));
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the DocumentVersion instance into string representation.
     * </p>
     *
     * @param documentVersion
     *            the DocumentVersion instance to format.
     * @return the string representation of the DocumentVersion instance.
     */
    private static String formatDocumentVersion(DocumentVersion documentVersion) {
        if (null == documentVersion) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("documentVersionId: ").append(documentVersion.getDocumentVersionId());
        builder.append(", document id: ").append(
                documentVersion.getDocument() == null ? null : documentVersion.getDocument().getDocumentId());
        builder.append(", document name id: ").append(
                documentVersion.getDocumentName() == null ? null : documentVersion.getDocumentName()
                        .getDocumentNameId());
        builder.append(", document version: ").append(documentVersion.getDocumentVersion());
        builder.append(", version date: ").append(documentVersion.getVersionDate());
        builder.append(", content: ").append(documentVersion.getContent());
        builder.append(", associated competition ids: [");
        boolean first = true;
        for (CompetitionDocument competitionDocument : documentVersion.getCompetitionDocuments()) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(competitionDocument.getCompetitionDocumentId());
        }
        builder.append("]");
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the MemberAnswer instance into string representation.
     * </p>
     *
     * @param memberAnswer
     *            the MemberAnswer instance to format.
     * @return the string representation of the MemberAnswer instance.
     */
    private static String formatMemberAnswer(MemberAnswer memberAnswer) {
        if (null == memberAnswer) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("memberAnswerId: ").append(memberAnswer.getMemberAnswerId());
        builder.append(", competition document id: ").append(
                memberAnswer.getCompetitionDocument() == null ? null : memberAnswer.getCompetitionDocument()
                        .getCompetitionDocumentId());
        builder.append(", memberId: ").append(memberAnswer.getMemberId());
        builder.append(", answer: ").append(memberAnswer.getAnswer());
        builder.append(", answer date: ").append(memberAnswer.getAnswerDate());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the CompetitionDocument instance into string representation.
     * </p>
     *
     * @param competitionDocument
     *            the CompetitionDocument instance to format.
     * @return the string representation of the CompetitionDocument instance.
     */
    private static String formatCompetitionDocument(CompetitionDocument competitionDocument) {
        if (null == competitionDocument) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("competitionDocumentId: ").append(competitionDocument.getCompetitionDocumentId());
        DocumentVersion docVersion = competitionDocument.getDocumentVersion();
        builder.append(", document version id: ").append(docVersion == null ? null : docVersion.getDocumentVersionId());
        builder.append(", competitionId: ").append(competitionDocument.getCompetitionId());
        builder.append(", roleId: ").append(competitionDocument.getRoleId());
        builder.append(", associated member answer ids: [");
        boolean first = true;
        for (MemberAnswer memberAnswer : competitionDocument.getMemberAnswers()) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(memberAnswer.getMemberAnswerId());
        }
        builder.append("]");
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the CompetitionDocument instance list into string representation.
     * </p>
     *
     * @param competitionDocument
     *            the CompetitionDocument instance list to format.
     * @return the string representation of the CompetitionDocument instance list.
     */
    private static String formatCompetitionDocuments(List<CompetitionDocument> competitionDocuments) {
        // the list never null.

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        boolean first = true;
        for (CompetitionDocument competitionDocument : competitionDocuments) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(formatCompetitionDocument(competitionDocument));
        }
        builder.append("]");

        return builder.toString();
    }
}
