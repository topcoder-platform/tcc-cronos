/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.prerequisite.CompetitionNotFoundException;
import com.topcoder.service.prerequisite.DocumentNotFoundException;
import com.topcoder.service.prerequisite.IllegalArgumentWSException;
import com.topcoder.service.prerequisite.PersistenceException;
import com.topcoder.service.prerequisite.PrerequisiteDocument;
import com.topcoder.service.prerequisite.PrerequisiteService;
import com.topcoder.service.prerequisite.RoleNotFoundException;
import com.topcoder.service.prerequisite.UserAlreadyAnsweredDocumentException;
import com.topcoder.service.prerequisite.UserNotAuthorizedException;
import com.topcoder.service.prerequisite.document.AuthorizationException;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.DocumentPersistenceException;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerRemote;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;
import com.topcoder.user.manager.Attribute;
import com.topcoder.user.manager.Profile;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the EJB implementation of the <code>{@link PrerequisiteService}</code> interface web service endpoint.
 * </p>
 * <p>
 * It uses an instance of <code>{@link DocumentManagerRemote}</code> (injected as EJB) to perform the logic of the
 * methods. The Web Service annotations are present in the endpoint interface, this bean contains only the annotation
 * to connect the endpoint interface and this implementation. The security is provided programmatically and also with
 * annotations. The exceptions are constructed using the fault bean because the fault message can be consumed as SOAP
 * message, this is necessary because it's a webservices. This implementations is designed to be used by the related
 * interface and also by a different webservices client: all the response, request and exceptions are automatically
 * transformed to SOAP element.
 * </p>
 * <p>
 * <b>Code Sample</b><br/>
 *
 * <pre>
 *  Properties env = new Properties();
 *  env.setProperty(Context.SECURITY_PRINCIPAL, &quot;admin&quot;);
 *  env.setProperty(Context.SECURITY_CREDENTIALS, &quot;password&quot;);
 *  env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
 *      &quot;org.jboss.security.jndi.JndiLoginInitialContextFactory&quot;);
 *  InitialContext initCtx = new InitialContext(env);
 *  // retrieve the service by ejb.
 *  prerequisiteService = (PrerequisiteService) initCtx.lookup(&quot;remote/PrerequisiteService&quot;);
 *
 *  // get all documents, only administrator can do this operation.
 *  List&lt;PrerequisiteDocument&gt; allDocuments = prerequisiteService.getAllPrerequisiteDocuments();
 *  // the list now contains the 2 documents, and using the entities of prerequisite
 *  // manager you can get all the sub-entities: version, competition documents etc..
 *
 *  // get the prerequisite document (similar to document version) of document with
 *  // id=10 and version=2
 *  PrerequisiteDocument prerequisiteDocument = prerequisiteService.getPrerequisiteDocument(10, 2);
 *  // get the content of document versions
 *  String content = prerequisiteDocument.getContent();
 *
 *  // get the document id
 *  long documentId = prerequisiteDocument.getDocumentId();
 *
 *  // get the name of document
 *  String name = prerequisiteDocument.getName();
 *
 *  // get the version
 *  int version = prerequisiteDocument.getVersion();
 *
 *  // get the timestamp of document version date
 *  XMLGregorianCalendar calendar = prerequisiteDocument.getVersionDate();
 *  // for example get the day
 *  int day = calendar.getDay();
 *
 *  // get the prerequisite documents of competition 20 and role id = 1, it is the role
 *  // of &quot;designer&quot;
 *  List&lt;PrerequisiteDocument&gt; prerequisiteDocuments = prerequisiteService.getPrerequisiteDocuments(20, 1);
 *  // it returns a single prerequisite document (document version) associated with this
 *  // competition and role
 *
 *  // create the timestamp and set to 27 February 2008
 *  GregorianCalendar cal = new GregorianCalendar();
 *  cal.set(Calendar.YEAR, 2008);
 *  cal.set(Calendar.MONTH, 1);
 *  cal.set(Calendar.DAY_OF_MONTH, 27);
 *
 *  // create the XMLGregorianCalendar
 *  XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
 *
 *  // record the member answer for the competition id = 20, the date previous created,
 *  // the prerequisite document retrieved and role of designer: role id =1
 *  prerequisiteService.recordMemberAnswer(20, xmlCal, true, prerequisiteDocument, 1);
 *  // now a MemberAnswer entity is saved into persistence layer and the previous
 *  // prerequisite document has an answer &quot;true&quot; for the competition id = 20, and the member
 *  // id = 17, the member id is the id user id of the caller (in UserProfilePrincipal)
 *
 * </pre>
 * </p>
 * <p>
 * <b>Thread Safety</b>: the implementation is thread safe because it doesn't change its state, the arguments don't
 * change.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 *
 */
@Stateless
@WebService(endpointInterface = "com.topcoder.service.prerequisite.PrerequisiteService")
@RunAs("Administrator")
@DeclareRoles( {"User", "Administrator"})
public class PrerequisiteServiceBean implements PrerequisiteService {

    /**
     * <p>
     * Represents the documentManager instance used by this bean. remote interface is used.
     * </p>
     */
    @EJB(name = "documentManager")
    private DocumentManager documentManager;

    /**
     * <p>
     * Represents the logger name used to retrieve the logger.
     * </p>
     */
    @Resource(name = "loggerName")
    private String loggerName;

    /**
     * <p>
     * Represents the session context of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the prerequisite elements factory to create the several SOAP elements for the web services: fault
     * beans and prerequisite documents.
     * </p>
     */
    private PrerequisiteElementsFactory elementsFactory;

    /**
     * <p>
     * Creates a <code>PrerequisiteServiceBean</code> instance.
     * </p>
     */
    public PrerequisiteServiceBean() {
    }

    /**
     * <p>
     * Initialize the bean. This is method is performed after the construction of the bean, at this point all the bean's
     * resources will be ready.
     * </p>
     *
     * @throws IllegalStateException
     *             If the loggerName property is empty.
     */
    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
        if (null == loggerName) {
            log = null;
        } else if (loggerName.trim().length() == 0) {
            throw new IllegalStateException("The 'loggerName' property is empty.");
        } else {
            log = LogManager.getLog(loggerName);
        }

        elementsFactory = new PrerequisiteElementsFactory();

        logMessage("Successfully initialize the PrerequisiteServiceBean.");
    }

    /**
     * <p>
     * Retrieves the details for a specific document as defined by a document id and optional version.
     * </p>
     * <p>
     * Gets specific version of prerequisite document. Version can be not specified and in this case methods should
     * return the latest version of document.
     * </p>
     * <p>
     * Notes, this method doesn't throw <code>{@link UserNotAuthorizedException}</code> because the user is authorized
     * and also the administrator.
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
     *             if the documentId &lt; 0
     */
    public PrerequisiteDocument getPrerequisiteDocument(long documentId, Integer version)
        throws DocumentNotFoundException, PersistenceException {
        logEnter("getPrerequisiteDocument(long, Integer)");
        logParameters("documentId:{0}, version: {1}", documentId, version);
        try {
            checkNumberNegative(documentId, "documentId");

            // retrieve specified document version.
            DocumentVersion documentVersion = getDocumentManager().getDocumentVersion(documentId, version);

            // document does not found
            if (null == documentVersion) {
                DocumentNotFoundFault faultInfo = getElementsFactory().createDocumentNotFoundFault();
                faultInfo.setDocumentIdNotFound(documentId);
                throw logException(new DocumentNotFoundException("No matched document.", faultInfo));
            }

            // create a prerequisite document.
            PrerequisiteDocument prerequisiteDocument = createPrerequisiteDocument(documentVersion);

            logReturn(formatPrerequisiteDocument(prerequisiteDocument));

            return prerequisiteDocument;
        } catch (AuthorizationException e) {
            // Normally, it is never thrown, as Administrator is configured as allowed role name.
            throw logAndWrapPersistenceException(e,
                    "'Administrator' is not configured as a allowed role name for DocumentManager bean.");
        } catch (DocumentPersistenceException e) {
            throw logAndWrapPersistenceException(e, "Problem to get the prerequisite document.");
        } finally {
            logExit("getPrerequisiteDocument(long, Integer)");
        }
    }

    /**
     * <p>
     * Retrieves all documents the specific user is required to agree to, for a particular role for a specific
     * competition.
     * </p>
     * <p>
     * This method is used to get all prerequisite documents for competition and user role for competition. Note that
     * such method returns only specific versions of documents; actually it can return two different version of the same
     * document. User can get documents' versions only if he has given role. Administrator can get any document and any
     * version of document.
     * </p>
     * <p>
     * Notes, the caller should have 'User' or 'Administrator' role.
     * </p>
     *
     * @param competitionId
     *            the competition id used to get the document versions
     * @param roleId
     *            the role id.
     * @return all prerequisite documents for competition and user role for competition, possibly empty.
     * @throws IllegalArgumentWSException
     *             if competitionId &lt; 0
     * @throws CompetitionNotFoundException
     *             if the competition is not found
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws RoleNotFoundException
     *             if the role is not found in the profile map
     * @throws UserNotAuthorizedException
     *             if the caller does not have 'User' or 'Administrator' role.
     * @throws IllegalStateException
     *             if the sessionContext is null (the webservices will transform this exception in a SOAP error the the
     *             endpoint interface will transform to RemoteException)
     */
    public List<PrerequisiteDocument> getPrerequisiteDocuments(long competitionId, long roleId)
        throws CompetitionNotFoundException, PersistenceException, RoleNotFoundException, UserNotAuthorizedException {
        logEnter("getPrerequisiteDocuments(long, long)");
        logParameters("competition id: {0}, role id: {1}", competitionId, roleId);

        try {
            checkNumberNegative(competitionId, "competitionId");
            checkNumberNegative(roleId, "roleId");

            // authenticate the caller
            boolean isAdmin = (Boolean) authenticateCaller(roleId)[1];

            List<CompetitionDocument> competitionDocuments = null;
            if (isAdmin) {
                competitionDocuments = getDocumentManager().getCompetitionDocumentsByCompetition(competitionId);
            } else {
                competitionDocuments = getDocumentManager().getCompetitionDocuments(competitionId, roleId);
            }

            if (competitionDocuments.isEmpty()) {
                CompetitionNotFoundFault faultInfo = getElementsFactory().createCompetitionNotFoundFault();
                faultInfo.setCompetitionIdNotFound(competitionId);
                throw logException(new CompetitionNotFoundException("No related competition.", faultInfo));
            }

            // it is possible that different competition refer to same document version.
            Set<Long> documentVersionIds = new HashSet<Long>();

            List<PrerequisiteDocument> prerequisiteDocuments = new ArrayList<PrerequisiteDocument>();
            for (CompetitionDocument competitionDocument : competitionDocuments) {
                DocumentVersion documentVersion = competitionDocument.getDocumentVersion();
                Long documentVersionId = documentVersion.getDocumentVersionId();

                if (documentVersionIds.contains(documentVersionId)) {
                    continue;
                } else {
                    documentVersionIds.add(documentVersionId);
                }

                prerequisiteDocuments.add(createPrerequisiteDocument(documentVersion));
            }

            logReturn(formatPrerequisiteDocuments(prerequisiteDocuments));

            return prerequisiteDocuments;
        } catch (AuthorizationException e) {
            // Normally, it is never thrown, as Administrator is configured as allowed role name.
            throw logException(new PersistenceException(e.getMessage(), e,
                    "'Administrator' is not configured as a allowed role name for DocumentManager bean."));
        } catch (DocumentPersistenceException e) {
            throw logException(new PersistenceException(e.getMessage(), e, "Fail to retrieve"));
        } finally {
            logExit("getPrerequisiteDocuments(long, long)");
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
     *  <p>
     * Notes, the caller should have 'User' or 'Administrator' role.
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
     * @throws RoleNotFoundException
     *             if the role is not found
     * @throws DocumentNotFoundException
     *             if the document is not found
     * @throws UserAlreadyAnsweredDocumentException
     *             if the user has already answered
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalArgumentWSException
     *             if any id is less than 0, if timestamp/prerequisiteDocument is null
     * @throws UserNotAuthorizedException
     *             if the caller does not have 'User' or 'Administrator' role.
     * @throws IllegalStateException
     *             if the sessionContext is null (the webservices will transform this exception in a SOAP error the the
     *             endpoint interface will transform to RemoteException)
     */
    public void recordMemberAnswer(long competitionId, XMLGregorianCalendar timestamp, boolean agrees,
            PrerequisiteDocument prerequisiteDocument, long roleId) throws RoleNotFoundException, PersistenceException,
        CompetitionNotFoundException, UserAlreadyAnsweredDocumentException, DocumentNotFoundException {
        logEnter("recordMemberAnswer(long, XMLGregorianCalendar, boolean, PrerequisiteDocument, long)");
        logParameters("competition id: {0}, timestamp: {1}, agreed: {2}, preprequisite document: {3}, role id: {4}",
                competitionId, timestamp == null ? null : timestamp.toGregorianCalendar(), agrees,
                prerequisiteDocument, roleId);

        try {
            checkNumberNegative(competitionId, "competitionId");
            checkObject(timestamp, "timestamp");
            checkObject(prerequisiteDocument, "prerequisiteDocument");
            checkNumberNegative(roleId, "roleId");

            // authenticate the caller
            Object[] info = authenticateCaller(roleId);
            long userId = (Long) info[0];
            boolean isAdmin = (Boolean) info[1];

            List<CompetitionDocument> competitionDocuments = null;

            if (!isAdmin) {
                competitionDocuments = getDocumentManager().getCompetitionDocuments(competitionId, roleId);
            } else {
                competitionDocuments = getDocumentManager().getCompetitionDocumentsByCompetition(competitionId);
            }

            if (competitionDocuments.isEmpty()) {
                CompetitionNotFoundFault faultInfo = getElementsFactory().createCompetitionNotFoundFault();
                faultInfo.setCompetitionIdNotFound(competitionId);
                throw logException(new CompetitionNotFoundException("No related competition.", faultInfo));
            }

            CompetitionDocument competitionDocument = null;
            for (CompetitionDocument compDocument : competitionDocuments) {
                DocumentVersion documentVersion = compDocument.getDocumentVersion();

                // role id never null
                if (!isAdmin && !compDocument.getRoleId().equals(roleId)) {
                    continue;
                }

                if (documentVersion.getDocument().getDocumentId().equals(prerequisiteDocument.getDocumentId())
                        && documentVersion.getDocumentVersion().equals(prerequisiteDocument.getVersion())) {
                    // matched
                    competitionDocument = compDocument;

                    break;
                }
            }

            if (null == competitionDocument) {
                DocumentNotFoundFault faultInfo = getElementsFactory().createDocumentNotFoundFault();
                faultInfo.setDocumentIdNotFound(prerequisiteDocument.getDocumentId());
                throw logException(new DocumentNotFoundException("No matched document.", faultInfo));
            }

            if (getDocumentManager().isCompetitionDocumentAnswered(competitionDocument.getCompetitionDocumentId(),
                    userId)) {
                UserAlreadyAnsweredDocumentFault faultInfo = getElementsFactory()
                        .createUserAlreadyAnsweredDocumentFault();
                faultInfo.setUserId(userId);
                throw logException(new UserAlreadyAnsweredDocumentException(
                        "The compeition document is already answered.", faultInfo));
            }

            MemberAnswer memberAnswer = new MemberAnswer();
            memberAnswer.setAnswerDate(timestamp.toGregorianCalendar().getTime());
            memberAnswer.setMemberId(userId);
            memberAnswer.setCompetitionDocument(competitionDocument);
            memberAnswer.setAnswer(agrees);

            getDocumentManager().addMemberAnswer(memberAnswer);
        } catch (AuthorizationException e) {
            // Normally, it is never thrown, as Administrator is configured as allowed role name.
            throw logAndWrapPersistenceException(e,
                    "'Administrator' is not configured as a allowed role name for DocumentManager bean.");
        } catch (DocumentPersistenceException e) {
            throw logAndWrapPersistenceException(e, "Fail to do document persistence.");
        } finally {
            logExit("recordMemberAnswer(long, XMLGregorianCalendar, boolean, PrerequisiteDocument, long)");
        }
    }

    /**
     * <p>
     * Retrieves all version of prerequisite document.
     * </p>
     * <p>
     * Notes, only administrator is authorized to do this operation.
     * </p>
     *
     * @return all versions of prerequisite document, possibly empty if no items exist.
     * @throws UserNotAuthorizedException
     *             if the caller is not administrator
     * @throws PersistenceException
     *             if some error occurs in the document manager bean usage (wrap its exceptions)
     * @throws IllegalStateException
     *             if the sessionContext is null (the webservices will transform this exception in a SOAP error the
     *             endpoint interface will transform to RemoteException)
     */
    public List<PrerequisiteDocument> getAllPrerequisiteDocuments() throws PersistenceException,
        UserNotAuthorizedException {
        logEnter("getAllPrerequisiteDocuments()");

        try {
            // checks the role of caller.
            SessionContext ctx = getSessionContext();
            if (null == ctx) {
                throw logException(new IllegalStateException("The session context is null."));
            } else if (!ctx.isCallerInRole("Administrator")) {
                throw logException(new UserNotAuthorizedException("The caller is not administrator", null));
            }

            List<Document> documents = getDocumentManager().getAllDocuments();

            // it is possible that different document refer to same document version.
            // it is handled
            Set<Long> documentVersionIds = new HashSet<Long>();

            List<PrerequisiteDocument> prerequisiteDocuments = new ArrayList<PrerequisiteDocument>();
            for (Document document : documents) {
                for (DocumentVersion documentVersion : document.getDocumentVersions()) {
                    Long documentVersionId = documentVersion.getDocumentVersionId();
                    if (documentVersionIds.contains(documentVersionId)) {
                        continue;
                    } else {
                        documentVersionIds.add(documentVersionId);
                    }

                    prerequisiteDocuments.add(createPrerequisiteDocument(documentVersion));
                }
            }

            logReturn(formatPrerequisiteDocuments(prerequisiteDocuments));
            return prerequisiteDocuments;
        } catch (AuthorizationException e) {
            // Normally, it is never thrown, as Administrator is configured as allowed role name.
            throw logAndWrapPersistenceException(e,
                    "'Administrator' is not configured as a allowed role name for DocumentManager bean.");
        } catch (DocumentPersistenceException e) {
            throw logAndWrapPersistenceException(e, "Problem to get the all prerequisite documents.");
        } finally {
            logExit("getAllPrerequisiteDocuments()");
        }
    }

    /**
     * <p>
     * Returns the session context. This method doesn't have logging.
     * </p>
     * <p>
     * Initialization of SessionContext instance is depending on dependency injection, it is possible does not work, in
     * some case.
     * </p>
     *
     * @return the session context, possibly null, if the injection does not work.
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * Returns the log.
     * </p>
     *
     * @return the log. It will return null if the log is not used in this class. This method doesn't need logging.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Returns the document manager. This method doesn't have logging.
     * </p>
     *
     * @return the document manager.
     */
    protected DocumentManager getDocumentManager() {
        return documentManager;
    }

    /**
     * <p>
     * Returns the prerequisite elements factory. This method doesn't have logging.
     * </p>
     *
     * @return the prerequisite elements factory
     */
    protected PrerequisiteElementsFactory getElementsFactory() {
        return elementsFactory;
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
        if (getLog() != null) {
            getLog().log(Level.INFO, "Enter into method [PrerequisiteServiceBean#{0}]", methodName);
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
        if (getLog() != null) {
            getLog().log(Level.INFO, "Exit method [PrerequisiteServiceBean#{0}].", methodName);
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
        if (getLog() != null) {
            getLog().log(Level.INFO, "Parameters: {0}", MessageFormat.format(pattern, parameters));
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
        if (getLog() != null) {
            getLog().log(Level.INFO, "Returns: {0}", message);
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
        if (getLog() != null) {
            getLog().log(Level.INFO, MessageFormat.format(pattern, parameters));
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
        if (getLog() != null) {
            getLog().log(Level.ERROR, exception, exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Formats the PrerequisiteDocument instance into string representation.
     * </p>
     *
     * @param prerequisiteDocument
     *            the PrerequisiteDocument instance to format.
     * @return the string representation of the PrerequisiteDocument instance.
     */
    private static String formatPrerequisiteDocument(PrerequisiteDocument prerequisiteDocument) {
        if (null == prerequisiteDocument) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("document id: ").append(prerequisiteDocument.getDocumentId());
        builder.append(", name: ").append(prerequisiteDocument.getName());
        builder.append(", version: ").append(prerequisiteDocument.getVersion());
        builder.append(", version date: ").append(prerequisiteDocument.getVersionDate());
        builder.append(", content: ").append(prerequisiteDocument.getContent());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the PrerequisiteDocument instance list into string representation.
     * </p>
     *
     * @param prerequisiteDocuments
     *            the PrerequisiteDocument instance list to format.
     * @return the string representation of the PrerequisiteDocument instance list.
     */
    private static String formatPrerequisiteDocuments(List<PrerequisiteDocument> prerequisiteDocuments) {
        // prerequisiteDocuments never null.

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        boolean first = true;
        for (PrerequisiteDocument prerequisiteDocument : prerequisiteDocuments) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(formatPrerequisiteDocument(prerequisiteDocument));
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Authenticate the current caller.
     * </p>
     *
     * @param roleId
     *            the role id.
     * @return the current caller's user id, and whether the caller is administrator
     * @throws IllegalStateException
     *             if the sessionContext is null (the Web Service will transform this exception in a SOAP error the
     *             endpoint interface will transform to RemoteException)
     * @throws RoleNotFoundException
     *             if the role is not found
     * @throws UserNotAuthorizedException
     *             if the caller does not have 'User' or 'Administrator' role.
     */
    private Serializable[] authenticateCaller(long roleId) throws RoleNotFoundException {
        // authorization check.
        try {
            SessionContext ctx = getSessionContext();
            if (null == ctx) {
                throw logException(new IllegalStateException("The session context is null."));
            }

            UserProfilePrincipal principal = (UserProfilePrincipal) ctx.getCallerPrincipal();

            boolean isAdmin = false;
            // not administrator or user.
            if (!ctx.isCallerInRole("Administrator")) {
                if (!ctx.isCallerInRole("User")) {
                    throw logException(new UserNotAuthorizedException(
                            "The caller does not have 'User' or 'Administrator' role.", null));
                }

                Profile profile = principal.getProfile();

                Attribute attr = profile.getAttribute(new Long(roleId));
                if (null == attr) {
                    RoleNotFoundFault roleNotFoundFault = getElementsFactory().createRoleNotFoundFault();
                    roleNotFoundFault.setRoleIdNotFound(roleId);
                    throw logException(new RoleNotFoundException("Unable to find role.", roleNotFoundFault));
                }
            } else {
                isAdmin = true;
            }

            return new Serializable[] {principal.getUserId(), isAdmin};
        } catch (ClassCastException e) {
            // this will occurs, when JBoss Login Module is not configured for this bean.
            throw logException(new UserNotAuthorizedException(
                    "JBoss Login Module is not configured for authorization.", null));
        }
    }

    /**
     * <p>
     * Creates a prerequisite document from the document version object.
     * </p>
     *
     * @param documentVersion
     *            the document version to extract data.
     * @return the newly created prerequisite document.
     */
    private PrerequisiteDocument createPrerequisiteDocument(DocumentVersion documentVersion) {
        PrerequisiteDocument prerequisiteDocument = getElementsFactory().createPrerequisiteDocument();

        prerequisiteDocument.setDocumentId(documentVersion.getDocument().getDocumentId());
        prerequisiteDocument.setName(documentVersion.getDocumentName().getName());
        prerequisiteDocument.setContent(documentVersion.getContent());
        prerequisiteDocument.setVersion(documentVersion.getDocumentVersion());

        try {
            // create the XMLGregorianCalendar
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(documentVersion.getVersionDate());
            XMLGregorianCalendar versionDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            prerequisiteDocument.setVersionDate(versionDate);
        } catch (DatatypeConfigurationException e) {
            logException(e);

            // ignore this exception, as normally, it never raise any problem.
        }

        return prerequisiteDocument;
    }

    /**
     * <p>
     * Checks that the number is not negative.
     * </p>
     *
     * @param number
     *            the number to check
     * @param name
     *            the parameter name
     * @throws IllegalArgumentWSException
     *             If the number is negative
     */
    private void checkNumberNegative(long number, String name) {
        if (number < 0) {
            IllegalArgumentWSFault illegalArgumentWSFault = getElementsFactory().createIllegalArgumentWSFault();
            illegalArgumentWSFault.setIllegalArgumentMessage("The [" + name + "] should not less than 0.");
            throw logException(new IllegalArgumentWSException("[" + name + "] param is invalid.",
                    illegalArgumentWSFault));
        }
    }

    /**
     * <p>
     * Checks that the object is not null.
     * </p>
     *
     * @param obj
     *            the object to check
     * @param name
     *            the parameter name
     * @throws IllegalArgumentWSException
     *             If the object is null
     */
    private void checkObject(Object obj, String name) {
        if (null == obj) {
            IllegalArgumentWSFault illegalArgumentWSFault = getElementsFactory().createIllegalArgumentWSFault();
            illegalArgumentWSFault.setIllegalArgumentMessage("The [" + name + "] should not be null.");
            throw logException(new IllegalArgumentWSException("[" + name + "] param is invalid.",
                    illegalArgumentWSFault));
        }
    }

    /**
     * <p>
     * Logs the exception and wrap with PersistenceException.
     * </p>
     *
     * @param e
     *            the exception to wrap.
     * @param persistenceMessage
     *            the persistence message.
     * @return a PersistenceException instance.
     */
    private PersistenceException logAndWrapPersistenceException(Exception e, String persistenceMessage) {
        logException(e);

        PersistenceFault persistenceFault = getElementsFactory().createPersistenceFault();
        persistenceFault.setPersistenceMessage(persistenceMessage);
        return new PersistenceException(e.getMessage(), persistenceFault, e);
    }
}
