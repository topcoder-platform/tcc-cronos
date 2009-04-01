/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.project.Project;
import com.topcoder.service.studio.PaymentType;
import com.topcoder.service.studio.contest.ContestChangeHistory;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestConfigurationException;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.ContestManagerRemote;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentContentManagementException;
import com.topcoder.service.studio.contest.DocumentContentManager;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.utils.FilterToSqlConverter;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This bean class implements the <code>ContestManagerLocal</code> and
 * <code>ContestManagerRemote</code> interfaces. It is a stateless session bean
 * with
 * 
 * @Stateless annotation. It would simply use the JPA to manage the entities in
 *            the persistence. It uses contains to maintain the transaction
 *            issues.
 *            </p>
 * 
 *            <p>
 *            It should have annotations:<br/> 1.
 * @Stateless<br/> 2.
 * @TransactionManagement(TransactionManagementType.CONTAINER)<br/> 3.
 * @DeclareRoles("Administrator")<br/> </p>
 * 
 *                                     <p>
 *                                     And all public methods in this bean
 *                                     should have the following
 *                                     annotations:<br/> 1.
 * @PermitAll -- indicating only "Administrator" role is allowed to perform the
 *            operation.<br/> 2.
 * @TransactionAttribute(TransactionAttributeType.REQUIRED) -- indicating the
 *                                                             transaction is
 *                                                             required.
 *                                                             </p>
 * 
 *                                                             <p>
 *                                                             1.1 change: 2 new
 *                                                             methods
 *                                                             <code>searchContests(Filter)</code>
 *                                                             and
 *                                                             <code>getAllContests()</code>
 *                                                             are added.
 *                                                             </p>
 * 
 *                                                             <p>
 *                                                             It should be
 *                                                             configured before
 *                                                             loaded:
 * 
 *                                                             <pre>
 *         &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;unitName&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;contest_submission&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;activeContestStatusId&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.long&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;defaultDocumentPathId&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.Long&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;1&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;contestManager&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;documentContestManagerClassName&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;
 *          com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager
 *          &lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;documentContentManagerAttributeKeys&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;serverAddress,serverPort&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;serverAddress&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;127.0.0.1&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 *                 &lt;env-entry&gt;
 *                         &lt;env-entry-name&gt;serverPort&lt;/env-entry-name&gt;
 *                         &lt;env-entry-type&gt;java.lang.Integer&lt;/env-entry-type&gt;
 *                         &lt;env-entry-value&gt;30000&lt;/env-entry-value&gt;
 *                 &lt;/env-entry&gt;
 * </pre>
 * 
 *                                                             </p>
 * 
 *                                                             <p>
 *                                                             <strong>Thread
 *                                                             safety:</strong>
 *                                                             The variables in
 *                                                             this class are
 *                                                             initialized once
 *                                                             in the initialize
 *                                                             method after the
 *                                                             bean is
 *                                                             instantiated by
 *                                                             EJB container.
 *                                                             They would be
 *                                                             never be changed
 *                                                             afterwards. So
 *                                                             they won't affect
 *                                                             the thread-safety
 *                                                             of this class
 *                                                             when its EJB
 *                                                             methods are
 *                                                             called. So this
 *                                                             class can be used
 *                                                             thread-safely in
 *                                                             EJB container.
 *                                                             </p>
 * 
 * @author Standlove, TCSDEVELOPER
 * @author AleaActaEst, BeBetter
 * @version 1.1
 * @since 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
public class ContestManagerBean implements ContestManagerRemote, ContestManagerLocal {
    /**
     * <p>
     * This field represents the <code>SessionContext</code> injected by the EJB
     * container automatically. It is marked with
     * 
     * @Resource annotation. It's non-null after injected when this bean is
     *           instantiated. And its reference is not changed afterwards. It
     *           is used in the initialize method to lookup JNDI resources.
     *           </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represent the default document path id, it is used to retrieve
     * the <code>FilePath</code> object to set to the added document if its path
     * is not set. It is initialized in the initialize method, and never changed
     * afterwards. It can be any long value. It must be non-null after set.
     * </p>
     */
    private Long defaultDocumentPathId;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the
     * <code>EntityManager</code> from the <code>SessionContext</code>. It is
     * initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private String unitName;

    /**
     * <p>
     * This field represents the logger to log invocation information and
     * exception. It is initialized in the initialize method, and never changed
     * afterwards. It can be null, and if so, logging is disabled.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * This field represents the <code>DocumentContentManager</code> object to
     * manage the document content. It is initialized in the
     * <code>initialize</code> method, and never changed afterwards. It must be
     * non-null after set.
     * </p>
     */
    private DocumentContentManager documentContentManager;

    /**
     * <p>
     * This field represents the id of contest status indicating the contest is
     * active. It is initialized in the <code>initialize</code> method, and
     * never changed afterwards. It can be any long value. It must be non-null
     * after set.
     * </p>
     */
    private Long activeContestStatusId;

    /**
     * Represents whether contest change will be audited.
     */
    private Boolean auditChange;
    
    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public ContestManagerBean() {
    }

    /**
     * <p>
     * This method is called after this bean is constructed by the EJB
     * container. This method should be marked with
     * 
     * @PostConstruct annotation. This method will load the unitName and other
     *                parameters from the sessionContext via the lookup method.
     *                </p>
     * 
     * @throws ContestConfigurationException
     *             if any required parameter is missing, or any configured value
     *             is invalid, it is also used to wrap the underlying
     *             exceptions.
     */
    @PostConstruct
    private void initialize() {
        unitName = getStringParameter("unitName", true, false);

        activeContestStatusId = getLongParameter("activeContestStatusId");
        defaultDocumentPathId = getLongParameter("defaultDocumentPathId");
        auditChange = getBooleanParameter("auditChange");

        String loggerName = getStringParameter("loggerName", false, false);

        if (loggerName != null) {
            logger = LogManager.getLog(loggerName);
        }

        String documentManagerClassName = getStringParameter("documentContentManagerClassName", true, false);

        String attributeKeys = getStringParameter("documentContentManagerAttributeKeys", true, false);

        String[] keys = attributeKeys.split(",");
        Map<String, Object> attrs = new HashMap<String, Object>();

        for (String key : keys) {
            attrs.put(key, sessionContext.lookup(key));
        }

        try {
            Class managerclass = Class.forName(documentManagerClassName);
            Object obj = managerclass.getConstructor(new Class[] { Map.class }).newInstance(new Object[] { attrs });

            if (!(obj instanceof DocumentContentManager)) {
                throw new ContestConfigurationException(
                        "The 'documentContestManagerClassName' should be instance of DocumentContentManager");
            }

            documentContentManager = (DocumentContentManager) obj;
        } catch (ClassNotFoundException e) {
            throw new ContestConfigurationException(
                    "The class specified by 'documentContentManagerClassName' doesn't exist.", e);
        } catch (SecurityException e) {
            throw new ContestConfigurationException("There are security problem to access the constructor.", e);
        } catch (InstantiationException e) {
            throw new ContestConfigurationException("The class is a abstract class.", e);
        } catch (IllegalAccessException e) {
            throw new ContestConfigurationException("Access control fails in the constructor.", e);
        } catch (InvocationTargetException e) {
            throw new ContestConfigurationException("Error occurs when calling the constructor.", e);
        } catch (NoSuchMethodException e) {
            throw new ContestConfigurationException(
                    "The constructor with specified parameter Map<String, Object> doesn't exist.", e);
        }
    }

    /**
     * <p>
     * Gets a string parameter from the session context.
     * </p>
     * 
     * @param paramName
     *            the name of the parameter
     * @param required
     *            if it's true, the parameter should be present
     * @param canEmpty
     *            if it's false, the parameter should not be empty string
     * @return the parameter value
     * 
     * @throws ContestConfigurationException
     *             if required is true and the parameter is missing, or if the
     *             parameter isn't a String value, or if canEmpty is false and
     *             the parameter is a empty string.
     */
    private String getStringParameter(String paramName, boolean required, boolean canEmpty) {
        Object obj = sessionContext.lookup(paramName);

        if (required && (obj == null)) {
            throw new ContestConfigurationException("The parameter '" + paramName + "' is missing.");
        }

        if (obj != null) {
            if (!(obj instanceof String)) {
                throw new ContestConfigurationException("The parameter '" + paramName + "' should be a String.");
            }

            String param = (String) obj;

            if (!canEmpty && (param.trim().length() == 0)) {
                throw new ContestConfigurationException("The parameter '" + paramName + "' should not be empty.");
            }

            return param;
        }

        return null;
    }

    /**
     * <p>
     * Gets a Long parameter from the session context.
     * </p>
     * 
     * @param paramName
     *            the name of the parameter
     * @return the parameter value
     * 
     * @throws ContestConfigurationException
     *             if the parameter is missing or it isn't a Long value.
     */
    private Long getLongParameter(String paramName) {
        Object obj = sessionContext.lookup(paramName);

        if (obj == null) {
            throw new ContestConfigurationException("The parameter '" + paramName + "' is missing.");
        }

        if (!(obj instanceof Long)) {
            throw new ContestConfigurationException("The parameter '" + paramName + "' should be a Long.");
        }

        return (Long) obj;
    }

    /**
     * <p>
     * Gets a Boolean parameter from the session context.
     * </p>
     * 
     * @param paramName
     *            the name of the parameter
     * @return the parameter value
     * 
     * @throws ContestConfigurationException
     *             if the parameter is present but it isn't a Boolean value.
     */
    private Boolean getBooleanParameter(String paramName) {
        Object obj = sessionContext.lookup(paramName);

        if (!(obj instanceof Boolean)) {
            throw new ContestConfigurationException("The parameter '" + paramName + "' should be a Boolean.");
        }

        return (Boolean) obj;
    }
    
    /**
     * <p>
     * Creates a new contest, and return the created contest.
     * </p>
     * 
     * @param contest
     *            the contest to create
     * @return the created contest
     * 
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Contest createContest(Contest contest) throws ContestManagementException {
        try {
            logEnter("createContest()");

            Helper.checkNull(contest, "contest");
            logOneParameter(contest.getContestId());

            if ((contest.getContestId() != null) && (getContest(contest.getContestId()) != null)) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The contest already exist.");
                logException(e, "The contest already exist.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            EntityManager em = getEntityManager();
            em.persist(contest);

            return contest;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("createContest()");
        }
    }

    /**
     * <p>
     * Gets contest by id, and return the retrieved contest. If the contest
     * doesn't exist, null is returned.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @return the retrieved contest, or null if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs when getting contest.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Contest getContest(long contestId) throws ContestManagementException {
        try {
            logEnter("getContest()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();
            Contest contest = em.find(Contest.class, new Long(contestId));
            if (contest != null && contest.getStatus() != null) {
                fillNextStatuses(contest.getStatus());
            }
            return contest;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContest()");
        }
    }

    /**
     * <p>
     * Gets projects' contests by the project id. A list of contests associated
     * with the given tcDirectProjectId should be returned. If there is no such
     * contests, an empty list should be returned.
     * </p>
     * 
     * @param tcDirectProjectId
     *            the project id.
     * @return a list of associated contests.
     * @throws ContestManagementException
     *             if any error occurs when getting contests.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        try {
            logEnter("getContestForProject()");
            logOneParameter(tcDirectProjectId);

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select c from Contest c where c.tcDirectProjectId = :tcDirectProjectId");
            query.setParameter("tcDirectProjectId", tcDirectProjectId);

            List list = query.getResultList();

            List<Contest> result = new ArrayList<Contest>();

            for (int i = 0; i < list.size(); i++) {
                result.add((Contest) list.get(i));
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestsForProject()");
        }
    }

    /**
     * <p>
     * Updates contest data. Note that all data can be updated only if contest
     * is not active. If contest is active it is possible to increase prize
     * amount and duration.
     * </p>
     * 
     * @param contest
     *            the contest to update
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityNotFoundException
     *             if the contest doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any error occurs when updating contest.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContest(Contest contest, long transactionId, String username, boolean userAdmin) throws ContestManagementException{
        try {
            logEnter("updateContest()");
            Helper.checkNull(contest, "contest");

            if ((contest.getContestId() == null) || (getContest(contest.getContestId()) == null)) {
                throw wrapEntityNotFoundException("The contest of id '" + contest.getContestId() + "' doesn't exist.");
            }

            EntityManager em = getEntityManager();

            Contest result = getContest(contest.getContestId());

            ContestConfig[] contestConfigs = result.getConfig().toArray(new ContestConfig[] {});
            // Add missing contest configurations.
            for (int i = 0; i < contestConfigs.length; ++i) {
                ContestConfig config = contestConfigs[i];
                if (!contest.getConfig().contains(config)) {
                    contest.getConfig().add(config);
                }
            }

            auditChanges(contest, transactionId, username, userAdmin, result);
            
            // Restore documents.
            contest.setDocuments(result.getDocuments());

            // [TCCC-435] Restore registrations.
            contest.setContestRegistrations(result.getContestRegistrations());
            
            if (result.getStatus().getContestStatusId().equals(activeContestStatusId)) {
                checkSet(result.getConfig(), contest.getConfig());

                if (contest.getContestChannel() == null) {
                    throw wrapContestManagementException("contest.contestChannel is null.");
                }
                if ((contest.getContestChannel().getContestChannelId() != result.getContestChannel()
                        .getContestChannelId())) {
                    throw wrapContestManagementException(MessageFormat
                            .format(
                                    "The contest channel doesn't match. persisted contest channel id: {0} updated contest channel id : {1}",
                                    result.getContestChannel().getContestChannelId(), contest.getContestChannel()
                                            .getContestChannelId()));
                }

                checkSet(result.getDocuments(), contest.getDocuments());

                checkSet(result.getFileTypes(), contest.getFileTypes());

                checkSet(result.getResults(), contest.getResults());

                checkSet(result.getSubmissions(), contest.getSubmissions());
            }

            // Restore submissions.
            contest.setSubmissions(result.getSubmissions());

            // Ensure that the created user isn't updated.
            contest.setCreatedUser(result.getCreatedUser());
            
            em.merge(contest);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateContest()");
        }
    }

    private void auditChanges(Contest contest, long transactionId, String username, boolean userAdmin, Contest result)
            throws ContestManagementException {
        if(!Boolean.TRUE.equals(auditChange)){
            return;
        }
        
        // Contest title:
        auditChange(result.getName(), contest.getName(), transactionId, username, userAdmin, contest, "title");
/*        
        // Contest Type:
        auditChange(result.getContestType().getDescription(), contest.getContestType().getDescription(),
                transactionId, username, userAdmin, contest, "contest_type");

        // Contest Admin Fee:
        // 25
        long propertyId = 25;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "contest_admin_fee");

        // DR Points
        // 24
        propertyId = 24;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "dr_points");
        
        // Start Date of Contest:
        auditChange(result.getStartDate().toString(), contest.getStartDate().toString(), transactionId, username, userAdmin, contest, "start_date");
        
        // End Date of Contest: 
        auditChange(result.getEndDate().toString(), contest.getEndDate().toString(), transactionId, username, userAdmin, contest, "end_date");
        
        // 1st Place Prize dollar amount
        
        // Brief one-paragraph description of your contest
        // 1
        propertyId = 1;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "contest_description");
        
        // Upload new files or images and create files description.
        
        // Delete existing files or images.
        
        // Overwrite and replace existing file.
        
        // Edit Existing File Description
        
        // Colors requirements, preferences or restrictions  
        // 14
        propertyId = 14;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "color_requirement");
        
        // Fonts requirements, preferences or restrictions  
        // 15
        propertyId = 15;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "fonts_requirement");
        
        // Size requirements, preferences or restrictions   
        // 16
        propertyId = 16;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "size_requirement");
        
        // Other requirements or restrictions?
        // 18
        propertyId = 18;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "other_requirements");
        
        // Add (check) a new file format type
        
        // Remove (uncheck) an existing file format type
     
        // Modify the "Other formats not listed above" textbox
        // 23
        propertyId = 23;
        auditChange(getSpecificProperty(result, propertyId), getSpecificProperty(result, propertyId), transactionId,
                username, userAdmin, contest, "other_formats");
*/
    }

    private String getSpecificProperty(Contest contest, long propertyId) {
        String value = null;
        for (ContestConfig cc : contest.getConfig()) {
            if (cc.getId().getProperty().getPropertyId() == propertyId) {
                value = cc.getValue();
            }
        }
        return value;
    }

    private void auditChange(String oldData, String newData, long transactionId, String username, boolean userAdmin,
            Contest contest, String fieldName) throws ContestManagementException {
        if (!oldData.equals(newData)) {
            ContestChangeHistory cch = new ContestChangeHistory();
            cch.setContestId(contest.getContestId());
            cch.setFieldName(fieldName);
            cch.setNewData(newData);
            cch.setOldData(oldData);
            cch.setTransactionId(Long.valueOf(transactionId));
            cch.setUserAdmin(userAdmin);
            cch.setUserName(username);

            EntityManager em = getEntityManager();
            em.persist(cch);
        }
    }

    /**
     * <p>
     * Checks whether the set has the same elements.
     * </p>
     * 
     * @param src
     *            the source set
     * @param dest
     *            the destination set
     * @throws ContestManagementException
     *             if two sets don't match
     */
    private void checkSet(Set<?> src, Set<?> dest) throws ContestManagementException {
        if ((dest == null)) {
            throw wrapContestManagementException("Contest config set to be saved is null.");
        }
    }

    /**
     * <p>
     * Updates contest status to the given value.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param newStatusId
     *            the new status id
     * @throws EntityNotFoundException
     *             if there is no corresponding Contest or ContestStatus in
     *             persistence.
     * @throws ContestStatusTransitionException
     *             if it's not allowed to update the contest to the given
     *             status.
     * @throws ContestManagementException
     *             if any error occurs when updating contest's status.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
        try {
            logEnter("updateContestStatus()");
            logTwoParameters(contestId, newStatusId);

            EntityManager em = getEntityManager();

            Contest contest = getContest(contestId);

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            ContestStatus contestStatus = em.find(ContestStatus.class, new Long(newStatusId));

            if (contestStatus == null) {
                throw wrapEntityNotFoundException("The contest status with id '" + newStatusId + "' doesn't exist.");
            }

            ContestStatus status = contest.getStatus();

            if (status == null) {
                throw wrapContestManagementException("The contest's status should not be null.");
            }

            if (status.getStatuses() == null) {
                throw wrapContestManagementException("The next statuses list should not be null.");
            }

            if (!status.getStatuses().contains(contestStatus)) {
                ContestStatusTransitionException e = new ContestStatusTransitionException(
                        "The contest's status can't be change to dest status: " + contestStatus.getName());
                logException(e, "The contest's status can't be change to dest status.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            contest.setStatus(contestStatus);
            em.merge(contest);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateContestStatus()");
        }
    }

    /**
     * <p>
     * Gets client for contest, the client id is returned.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @return the client id
     * @throws EntityNotFoundException
     *             if there is no corresponding contest (or project) in
     *             persistence.
     * @throws ContestManagementException
     *             if any error occurs when retrieving the client id.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long getClientForContest(long contestId) throws ContestManagementException {
        try {
            logEnter("getClientForContest()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            Project project = em.find(Project.class, contest.getTcDirectProjectId());

            if (project == null) {
                throw wrapEntityNotFoundException("The project with id '" + contest.getTcDirectProjectId()
                        + "' doesn't exist.");
            }

            return project.getUserId();
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getClientForContest()");
        }
    }

    /**
     * <p>
     * Get client for project, and return the retrieved client id.
     * </p>
     * 
     * @param projectId
     *            the project id
     * @return the client id
     * @throws EntityNotFoundException
     *             if there is no corresponding project in persistence.
     * @throws ContestManagementException
     *             if any error occurs when retrieving the client id.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long getClientForProject(long projectId) throws ContestManagementException {
        try {
            logEnter("getClientForProject()");
            logOneParameter(projectId);

            EntityManager em = getEntityManager();

            Project project = em.find(Project.class, new Long(projectId));

            if (project == null) {
                throw wrapEntityNotFoundException("The project with id '" + projectId + "' doesn't exist.");
            }

            return project.getUserId();
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getClientForProject()");
        }
    }

    /**
     * <p>
     * Adds contest status, and return the added contest status.
     * </p>
     * 
     * @param contestStatus
     *            the contest status to add
     * @return the added contest status
     * 
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        try {
            logEnter("addContestStatus()");

            Helper.checkNull(contestStatus, "contestStatus");
            logOneParameter(contestStatus.getContestStatusId());

            if ((contestStatus.getContestStatusId() != null)
                    && (getContestStatus(contestStatus.getContestStatusId()) != null)) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The contest status already exists.");

                logException(e, "The contest status already exists.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            EntityManager em = getEntityManager();
            em.persist(contestStatus);

            return contestStatus;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addContestStatus()");
        }
    }

    /**
     * <p>
     * Updates the contest status.
     * </p>
     * 
     * @param contestStatus
     *            the contest status to update
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityNotFoundException
     *             if the contestStatus doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        try {
            logEnter("updateContestStatus()");

            Helper.checkNull(contestStatus, "contestStatus");
            logOneParameter(contestStatus.getContestStatusId());

            EntityManager em = getEntityManager();

            if ((contestStatus.getContestStatusId() == null)
                    || (em.find(ContestStatus.class, contestStatus.getContestStatusId()) == null)) {
                throw wrapEntityNotFoundException("The contest status with id '" + contestStatus.getContestStatusId()
                        + "' doesn't exist.");
            }

            em.merge(contestStatus);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("UpdateContestStatus()");
        }
    }

    /**
     * <p>
     * Removes contest status, return true if the contest status exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     * 
     * @param contestStatusId
     *            the contest status id
     * @return true if the contest status exists and removed successfully,
     *         return false if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        try {
            logEnter("removeContestStatus()");
            logOneParameter(contestStatusId);

            EntityManager em = getEntityManager();

            ContestStatus status = em.find(ContestStatus.class, new Long(contestStatusId));

            if (status == null) {
                return false;
            }

            em.remove(status);

            return true;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removeContestStatus()");
        }
    }

    /**
     * <p>
     * Gets contest status, and return the retrieved contest status. Return null
     * if it doesn't exist.
     * </p>
     * 
     * @param contestStatusId
     *            the contest status id
     * @return the retrieved contest status, or null if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest status
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        try {
            logEnter("getContestStatus()");
            logOneParameter(contestStatusId);

            EntityManager em = getEntityManager();

            ContestStatus contestStatus = em.find(ContestStatus.class, new Long(contestStatusId));
            if (contestStatus != null) {
                fillNextStatuses(contestStatus);
            }
            return contestStatus;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestStatus()");
        }
    }

    /**
     * <p>
     * Adds new document, and return the added document.
     * </p>
     * 
     * @param document
     *            the document to add
     * @return the added document
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the document already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Document addDocument(Document document) throws ContestManagementException {
        try {
            logEnter("addDocument()");

            Helper.checkNull(document, "document");
            logOneParameter(document.getDocumentId());

            EntityManager em = getEntityManager();

            if ((document.getDocumentId() != null) && (em.find(Document.class, document.getDocumentId()) != null)) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The document already exists.");
                logException(e, "The document already exists.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            if (document.getPath() == null) {
                FilePath docPath = (FilePath) em.find(FilePath.class, defaultDocumentPathId);

                if (docPath == null) {
                    throw wrapContestManagementException("The file path with specified path id doesn't exist.");
                }

                document.setPath(docPath);
            }

            if (document.getSystemFileName() == null) {
                document.setSystemFileName(UUID.randomUUID().toString());
            }
            
            em.persist(document);

            return document;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addDocument()");
        }
    }

    /**
     * <p>
     * Updates the specified document.
     * </p>
     * 
     * @param document
     *            the document to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDocument(Document document) throws ContestManagementException {
        try {
            logEnter("updateDocument()");
            Helper.checkNull(document, "document");

            logOneParameter(document.getDocumentId());

            EntityManager em = getEntityManager();

            if ((document.getDocumentId() == null) || (em.find(Document.class, document.getDocumentId()) == null)) {
                throw wrapEntityNotFoundException("The document with id '" + document.getDocumentId()
                        + "' doesn't exist.");
            }

            em.merge(document);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateDocument()");
        }
    }

    /**
     * <p>
     * Gets document by id, and return the retrieved document. Return null if
     * the document doesn't exist.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @return the retrieved document, or null if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs when getting document
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Document getDocument(long documentId) throws ContestManagementException {
        try {
            logEnter("getDocument()");
            logOneParameter(documentId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            return document;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getDocument()");
        }
    }

    /**
     * <p>
     * Removes the specified document, return true if the document exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @return true if the document exists and removed successfully, return
     *         false if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removeDocument(long documentId) throws ContestManagementException {
        try {
            logEnter("removeDocument()");
            logOneParameter(documentId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                return false;
            }

            em.remove(document);

            return true;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removeDocument()");
        }
    }

    /**
     * <p>
     * Adds document to contest. Nothing happens if the document already exists
     * in contest.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @param contestId
     *            the contest id
     * 
     * @throws EntityNotFoundException
     *             if there is no corresponding document or contest in
     *             persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {
        try {
            logEnter("addDocumentToContest()");
            logTwoParameters(documentId, contestId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                throw wrapEntityNotFoundException("The document with id '" + documentId + "' doesn't exist.");
            }

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            // [BUG TCCC-134]
            documentContentManager.moveDocumentToContestFolder(formPath(document), contestId);

            // When moving the document,
            // the path in the database must be changed to the new location
            // (stored in document.path).
            FilePath path = document.getPath();
            String initialPath = path.getPath();
            if (!initialPath.endsWith(File.separator)) {
            	initialPath = initialPath + File.separator;
            }
                                   
            path.setPath(initialPath + contestId + File.separator);
            document.setPath(path);
            em.persist(document);

            contest.getDocuments().add(document);
            document.getContests().add(contest);

            em.merge(contest);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (IOException e) {
            throw wrapContestManagementException(e, "There are errors while moving document file.");
        } finally {
            logExit("addDocumentToContest()");
        }
    }

    /**
     * <p>
     * Removes document from contest. Return true if the document exists in the
     * contest and removed successfully, return false if it doesn't exist in
     * contest.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @param contestId
     *            the contest id
     * @return true if the document exists in the contest and removed
     *         successfully, return false if it doesn't exist in contest.
     * @throws EntityNotFoundException
     *             if there is no corresponding document or contest in
     *             persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {
        try {
            logEnter("removeDocumentFromContest()");
            logTwoParameters(documentId, contestId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                throw wrapEntityNotFoundException("The document with id '" + documentId + "' doesn't exist.");
            }

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            if (contest.getDocuments().contains(document)) {
                contest.getDocuments().remove(document);
                document.getContests().remove(contest);

                em.merge(contest);

                return true;
            }

            return false;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removeDocumentFromContest()");
        }
    }

    /**
     * <p>
     * Adds contest channel, and return the added contest channel.
     * </p>
     * 
     * @param contestChannel
     *            the contest channel to add
     * @return the added contest channel
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        try {
            logEnter("addContestChannel()");

            Helper.checkNull(contestChannel, "contestChannel");
            logOneParameter(contestChannel.getContestChannelId());

            EntityManager em = getEntityManager();

            if ((contestChannel.getContestChannelId() != null)
                    && (em.find(ContestChannel.class, contestChannel.getContestChannelId()) != null)) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The contest channel with id '"
                        + contestChannel.getContestChannelId() + "' already exists.");
                logException(e, "The contest channel with id '" + contestChannel.getContestChannelId()
                        + "' already exists.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            em.persist(contestChannel);

            return contestChannel;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addContestChannel()");
        }
    }

    /**
     * <p>
     * Updates the contest channel.
     * </p>
     * 
     * @param contestChannel
     *            the contest category to update.
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contestChannel doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        try {
            logEnter("updateContestChannel()");

            Helper.checkNull(contestChannel, "contestChannel");
            logOneParameter(contestChannel.getContestChannelId());

            EntityManager em = getEntityManager();

            if ((contestChannel.getContestChannelId() == null)
                    || (em.find(ContestChannel.class, contestChannel.getContestChannelId()) == null)) {
                throw wrapEntityNotFoundException("The contest channel with id '"
                        + contestChannel.getContestChannelId() + "' doesn't exist.");
            }

            em.merge(contestChannel);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateContestChannel()");
        }
    }

    /**
     * <p>
     * Removes contest category, return true if the contest category exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     * 
     * @param contestChannelId
     *            the contest channel id
     * @return true if the contest category exists and removed successfully,
     *         return false if it doesn't exist.
     * @throws ContestManagementException
     *             if fails to remove the contest category when it exists
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {
        try {
            logEnter("removeContestChannel()");
            logOneParameter(contestChannelId);

            EntityManager em = getEntityManager();

            ContestChannel contestChannel = em.find(ContestChannel.class, new Long(contestChannelId));

            if (contestChannel == null) {
                return false;
            }

            em.remove(contestChannel);

            return true;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removeContestChannel()");
        }
    }

    /**
     * <p>
     * Gets contest channel, and return the retrieved contest channel. Return
     * null if it doesn't exist.
     * </p>
     * 
     * @param contestChannelId
     *            the contest channel id
     * @return the retrieved contest channel, or null if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest channel
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {
        try {
            logEnter("getContestChannel()");
            logOneParameter(contestChannelId);

            EntityManager em = getEntityManager();

            ContestChannel contestChannel = em.find(ContestChannel.class, new Long(contestChannelId));

            return contestChannel;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestChannel()");
        }
    }

    /**
     * <p>
     * Adds contest configuration parameter, and return the added contest
     * configuration parameter.
     * </p>
     * 
     * @param contestConfig
     *            the contest configuration parameter to add.
     * @return the added contest configuration parameter.
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        try {
            logEnter("addConfig()");
            Helper.checkNull(contestConfig, "contestConfig");
            String idStr = "(" + contestConfig.getId().getContest() + ", " + contestConfig.getId().getProperty() + ")";
            logOneParameter(idStr);

            EntityManager em = getEntityManager();

            if (em.find(ContestConfig.class, contestConfig.getId()) != null) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The contest config with id '"
                        + idStr + "' already exists.");
                logException(e, "The contest config with id '" + idStr + "' already exists.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            em.persist(contestConfig);

            return contestConfig;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addConfig()");
        }
    }

    /**
     * <p>
     * Updates contest configuration parameter.
     * </p>
     * 
     * @param contestConfig
     *            the contest configuration parameter to update.
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contest configuration parameter doesn't exist in
     *             persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {
        try {
            logEnter("updateConfig()");
            Helper.checkNull(contestConfig, "contestConfig");
            String idStr = "(" + contestConfig.getId().getContest() + ", " + contestConfig.getId().getProperty() + ")";
            logOneParameter(idStr);

            EntityManager em = getEntityManager();
            if (em.find(ContestConfig.class, contestConfig.getId()) == null) {
                throw wrapEntityNotFoundException("The contest config with id '" + idStr + "' doesn't exist");
            }

            em.merge(contestConfig);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateConfig()");
        }
    }

    /**
     * <p>
     * Gets contest configuration parameter by id, and return the retrieved
     * contest configuration parameter. Return null if it doesn't exist.
     * </p>
     * 
     * @param contestConfigId
     *            the contest configuration parameter id.
     * @return the retrieved contest configuration parameter, or null if it
     *         doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest configuration
     *             parameter
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        try {
            logEnter("getConfig()");
            logOneParameter(contestConfigId);

            EntityManager em = getEntityManager();

            ContestConfig contestConfig = em.find(ContestConfig.class, new Long(contestConfigId));

            return contestConfig;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getConfig()");
        }
    }

    /**
     * <p>
     * Save document content in file system. This methods should use
     * DocumentContentManager interface.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @param documentContent
     *            the file data of the document to save
     * @throws IllegalArgumentException
     *             if fileData argument is null or empty array.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {
        try {
            logEnter("saveDocumentContent()");
            Helper.checkNull(documentContent, "documentContent");

            if (documentContent.length == 0) {
                throw new IllegalArgumentException("The document content should not be empty.");
            }

            logTwoParameters(documentId, documentContent);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                throw wrapEntityNotFoundException("The document with id '" + documentId + "' doesn't exist.");
            }

            String path = formPath(document);

            documentContentManager.saveDocumentContent(path, documentContent);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (DocumentContentManagementException e) {
            throw wrapContestManagementException(e, "There are errors while adding the document content.");
        } catch (IOException e) {
            throw wrapContestManagementException(e, "There are io errors while adding the document content.");
        } finally {
            logExit("saveDocumentContent()");
        }
    }

    /**
     * <p>
     * Gets document content to return. If the document is not saved, null is
     * returned. It will use DocumentContentManager to get document content. It
     * can also return empty array if the document content is empty.
     * </p>
     * 
     * 
     * @param documentId
     *            the document id
     * @return the document content in byte array. If the document is not saved,
     *         null is returned.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        try {
            logEnter("getDocumentContent()");
            logOneParameter(documentId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                throw wrapEntityNotFoundException("The document with id '" + documentId + "' doesn't exist.");
            }

            String path = formPath(document);

            return documentContentManager.getDocumentContent(path);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (DocumentContentManagementException e) {
            throw wrapContestManagementException(e, "There are errors while getting the document content.");
        } catch (IOException e) {
            throw wrapContestManagementException(e, "There are io errors while getting the document content.");
        } finally {
            logExit("getDocumentContent()");
        }
    }

    /**
     * <p>
     * Forms the path of the specified document's content.
     * </p>
     * 
     * @param document
     *            the document to get path
     * @return the path of the document's content
     */
    private String formPath(Document document) {
        String path = document.getPath().getPath();

        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }

        path = path + document.getSystemFileName();

        return path;
    }

    /**
     * <p>
     * Checks the document's content exists or not. Return true if it exists,
     * return false otherwise. It will use DocumentContentManager to check
     * document content's existence.
     * </p>
     * 
     * @param documentId
     *            the document id
     * @return true if the document content exists, return false otherwise.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        try {
            logEnter("existDocumentContent()");
            logOneParameter(documentId);

            EntityManager em = getEntityManager();

            Document document = em.find(Document.class, new Long(documentId));

            if (document == null) {
                throw wrapEntityNotFoundException("The document with id '" + documentId + "' doesn't exist.");
            }

            String path = formPath(document);

            return documentContentManager.existDocumentContent(path);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (DocumentContentManagementException e) {
            throw wrapContestManagementException(e,
                    "There are errors while getting the exist flag of the document content.");
        } catch (IOException e) {
            throw wrapContestManagementException(e,
                    "There are io errors while getting the exist flag of the document content.");
        } finally {
            logExit("existDocumentContent()");
        }
    }

    /**
     * <p>
     * Gets all contest statuses to return. If no contest status exists, return
     * an empty list.
     * </p>
     * 
     * @return a list of contest statuses
     * @throws ContestManagementException
     *             if any error occurs when getting contest statuses
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        try {
            logEnter("getAllContestSatuses()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select s from ContestStatus s");

            List list = query.getResultList();

            List<ContestStatus> result = new ArrayList<ContestStatus>();
            for (int i = 0; i < list.size(); i++) {
                ContestStatus status = (ContestStatus) list.get(i);
                fillNextStatuses(status);
                result.add(status);
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getAllContestSatuses()");
        }
    }

    /**
     * <p>
     * Gets all contest categories to return. If no contest category exists,
     * return an empty list.
     * </p>
     * 
     * @return a list of contest channels
     * @throws ContestManagementException
     *             if any error occurs when getting contest categories.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {
        try {
            logEnter("getAllContestChannels()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select s from ContestChannel s");

            List list = query.getResultList();

            List<ContestChannel> result = new ArrayList<ContestChannel>();

            for (int i = 0; i < list.size(); i++) {
                result.add((ContestChannel) list.get(i));
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getAllContestChannels()");
        }
    }

    /**
     * <p>
     * Gets all studio file types to return. If no studio file type exists,
     * return an empty list
     * </p>
     * 
     * @return a list of studio file types
     * @throws ContestManagementException
     *             if any error occurs when getting studio file types.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        try {
            logEnter("getAllStudioFileTypes()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select s from StudioFileType s");

            List list = query.getResultList();

            List<StudioFileType> result = new ArrayList<StudioFileType>();

            for (int i = 0; i < list.size(); i++) {
                result.add((StudioFileType) list.get(i));
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getAllStudioFileTypes()");
        }
    }

    /**
     * <p>
     * Adds contest type configuration parameter, and return the added contest
     * type configuration parameter.
     * </p>
     * 
     * @param contestTypeConfig
     *            the contest type configuration parameter to add
     * @return the added contest type configuration parameter.
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        try {
            logEnter("addContestTypeConfig()");
            Helper.checkNull(contestTypeConfig, "contestTypeConfig");
            logOneParameter(contestTypeConfig.getId());

            EntityManager em = getEntityManager();

            if (em.find(ContestTypeConfig.class, contestTypeConfig.getId()) != null) {
                EntityAlreadyExistsException e = new EntityAlreadyExistsException("The contest type config with id '"
                        + contestTypeConfig.getId() + "' already exists.");
                logException(e, "The contest type config with id '" + contestTypeConfig.getId() + "' already exists.");
                sessionContext.setRollbackOnly();

                throw e;
            }

            em.persist(contestTypeConfig);

            return contestTypeConfig;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addContestTypeConfig()");
        }
    }

    /**
     * <p>
     * Updates contest type configuration parameter.
     * </p>
     * 
     * @param contestTypeConfig
     *            the contest type configuration parameter to update.
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contest type configuration parameter doesn't exist in
     *             persistence.
     * @throws ContestManagementException
     *             if any other error occurs
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {
        try {
            logEnter("updateContestTypeConfig()");
            Helper.checkNull(contestTypeConfig, "contestTypeConfig");
            logOneParameter(contestTypeConfig.getId());

            EntityManager em = getEntityManager();

            if (em.find(ContestTypeConfig.class, contestTypeConfig.getId()) == null) {
                throw wrapEntityNotFoundException("The contest type config with id '" + contestTypeConfig.getId()
                        + "' doesn't exist");
            }

            em.merge(contestTypeConfig);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("updateContestTypeConfig()");
        }
    }

    /**
     * <p>
     * Gets contest type configuration parameter by id, and return the retrieved
     * contest type configuration parameter. Return null if it doesn't exist.
     * </p>
     * 
     * @param contestTypeConfigId
     *            the contest type configuration parameter id.
     * @return the retrieved contest type configuration parameter, or null if it
     *         doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs when getting contest type configuration
     *             parameter
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        try {
            logEnter("getContestTypeConfig()");
            logOneParameter(contestTypeConfigId);

            EntityManager em = getEntityManager();

            ContestTypeConfig contestTypeConfig = em.find(ContestTypeConfig.class, new Long(contestTypeConfigId));

            return contestTypeConfig;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestTypeConfig()");
        }
    }

    /**
     * <p>
     * Adds prize to the given contest. Nothing happens if the prize already
     * exists in contest.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param prizeId
     *            the prize id
     * @throws EntityNotFoundException
     *             if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {
        try {
            logEnter("addPrizeToContest()");
            logTwoParameters(contestId, prizeId);

            EntityManager em = getEntityManager();

            Prize prize = em.find(Prize.class, new Long(prizeId));

            if (prize == null) {
                throw wrapEntityNotFoundException("The prize with id '" + prizeId + "' doesn't exist.");
            }

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            prize.getContests().add(contest);
            em.merge(prize);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addPrizeToContest()");
        }
    }

    /**
     * <p>
     * Removes prize from contest. Return true if the prize exists in the
     * contest and removed successfully, return false if it doesn't exist in
     * contest.
     * </p>
     * 
     * @param prizeId
     *            the prize id
     * @param contestId
     *            the contest id
     * @return true if the prize exists in the contest and removed successfully,
     *         return false if it doesn't exist in contest.
     * @throws EntityNotFoundException
     *             if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        try {
            logEnter("removePrizesFromContest()");
            logTwoParameters(contestId, prizeId);

            EntityManager em = getEntityManager();

            Prize prize = em.find(Prize.class, new Long(prizeId));

            if (prize == null) {
                throw wrapEntityNotFoundException("The prize with id '" + prizeId + "' doesn't exist.");
            }

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            if (prize.getContests().contains(contest)) {
                prize.getContests().remove(contest);
                em.merge(prize);

                return true;
            }

            return false;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removePrizesFromContest()");
        }
    }

    /**
     * <p>
     * Retrieves all prizes in the given contest to return. An empty list is
     * returned if there is no such prizes.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @return a list of prizes
     * @throws EntityNotFoundException
     *             if there is no corresponding contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        try {
            logEnter("getContestPrizes()");

            EntityManager em = getEntityManager();

            Contest contest = em.find(Contest.class, new Long(contestId));

            if (contest == null) {
                throw wrapEntityNotFoundException("The contest with id '" + contestId + "' doesn't exist.");
            }

            Query query = em.createQuery("select p from Prize p where :contest member of p.contests");
            query.setParameter("contest", contest);

            List list = query.getResultList();

            List<Prize> result = new ArrayList<Prize>();
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestPrizes()");
        }
    }

    /**
     * <p>
     * Get all the ContestProperty objects.
     * </p>
     * 
     * @return the list of all available ContestProperty
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestProperty> getAllContestProperties() throws ContestManagementException {
        try {
            logEnter("getAllContestProperties()");

            return getAll(ContestProperty.class);

        } finally {
            logExit("getAllContestProperties()");
        }
    }

    /**
     * <p>
     * Get the ContestProperty with the specified id.
     * </p>
     * 
     * @param contestPropertyId
     *            id to look for
     * @return the ContestProperty with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestProperty getContestProperty(long contestPropertyId) throws ContestManagementException {
        try {
            logEnter("getContestProperty()");
            logOneParameter(contestPropertyId);

            EntityManager em = getEntityManager();

            return em.find(ContestProperty.class, contestPropertyId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestProperty()");
        }
    }

    /**
     * <p>
     * Get all the MimeType objects.
     * </p>
     * 
     * @return the list of all available MimeType
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting MimeType.
     * 
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MimeType> getAllMimeTypes() throws ContestManagementException {
        try {
            logEnter("getAllMimeTypes()");

            return getAll(MimeType.class);

        } finally {
            logExit("getAllMimeTypes()");
        }
    }

    /**
     * <p>
     * Get all the PrizeType objects.
     * </p>
     * 
     * @return the list of all available PrizeType
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting PrizeType.
     * @since TCCC-349
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException {
        try {
            logEnter("getAllPrizeTypes()");

            return getAll(PrizeType.class);

        } finally {
            logExit("getAllPrizeTypes()");
        }
    }

    /**
     * <p>
     * Get the PrizeType with the specified id.
     * </p>
     * 
     * @param prizeTypeId
     *            id to look for
     * @return the PrizeType with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting PrizeType.
     * @since TCCC-349
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PrizeType getPrizeType(long prizeTypeId) throws ContestManagementException {
        try {
            logEnter("getPrizeType()");
            logOneParameter(prizeTypeId);

            EntityManager em = getEntityManager();

            return em.find(PrizeType.class, prizeTypeId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getPrizeType()");
        }
    }

    /**
     * <p>
     * Get all the PaymentStatus objects.
     * </p>
     * 
     * @return the list of all available PaymentStatus
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting PaymentStatus.
     * 
     * @since TCCC-349
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PaymentStatus> getAllPaymentStatuses() throws ContestManagementException {
        try {
            logEnter("getAllPaymentStatuses()");

            return getAll(PaymentStatus.class);

        } finally {
            logExit("getAllPaymentStatuses()");
        }
    }

    /**
     * <p>
     * Get the PaymentStatus with the specified id.
     * </p>
     * 
     * @param paymentStatusId
     *            id to look for
     * @return the PaymentStatus with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting PaymentStatus
     * @since TCCC-349
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PaymentStatus getPaymentStatus(long paymentStatusId) throws ContestManagementException {
        try {
            logEnter("getPaymentStatus()");
            logOneParameter(paymentStatusId);

            EntityManager em = getEntityManager();

            return em.find(PaymentStatus.class, paymentStatusId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getPaymentStatus()");
        }
    }

    /**
     * <p>
     * Get the MimeType with the specified id.
     * </p>
     * 
     * @param mimeTypeId
     *            id to look for
     * @return the MimeType with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MimeType getMimeType(long mimeTypeId) throws ContestManagementException {
        try {
            logEnter("getMimeType()");
            logOneParameter(mimeTypeId);

            EntityManager em = getEntityManager();

            return em.find(MimeType.class, mimeTypeId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getMimeType()");
        }
    }

    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     * 
     * @return the list of all available DocumentType
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DocumentType> getAllDocumentTypes() throws ContestManagementException {
        try {
            logEnter("getAllDocumentTypes()");

            return getAll(DocumentType.class);

        } finally {
            logExit("getAllDocumentTypes()");
        }
    }

    /**
     * <p>
     * Get the DocumentType with the specified id.
     * </p>
     * 
     * @param documentTypeId
     *            id to look for
     * @return the DocumentType with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentType getDocumentType(long documentTypeId) throws ContestManagementException {
        try {
            logEnter("getDocumentType()");
            logOneParameter(documentTypeId);

            EntityManager em = getEntityManager();

            return em.find(DocumentType.class, documentTypeId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getDocumentType()");
        }
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     * 
     * @return the list of all available contents (or empty if none found)
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
     * @since 1.1
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Contest> getAllContests() throws ContestManagementException {
        try {
            logEnter("getAllContests()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select c from Contest c where not c.tcDirectProjectId is null and c.deleted = false");

            List list = query.getResultList();

            List<Contest> result = new ArrayList<Contest>();
            for (Contest contest : result) {
                fillNextStatuses(contest.getStatus());
            }
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getAllContests()");
        }
    }
    
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleContestData> getSimpleContestData() throws ContestManagementException {
        try {
            logEnter("getAllContestsForMonitor()");

            EntityManager em = getEntityManager();


            String qstr="select c.contest_id, c.name as cname, c.start_time,  c.end_time,  "
                + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1 "
                +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub "
                + " from contest c "
                + " where c.tc_direct_project_id is not null and c.deleted = 0 and c.contest_detailed_status_id!=3";

            Query query = em.createNativeQuery(qstr);

            List list = query.getResultList();

            List<SimpleContestData> result = new ArrayList<SimpleContestData>();
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            for (int i = 0; i < list.size(); i++) {
            	
            	SimpleContestData c=new SimpleContestData();
            	Object [] os=(Object [])list.get(i);
            	if(os[0]!=null)c.setContestId(Long.parseLong(os[0].toString()));
            	if(os[1]!=null)c.setName(os[1].toString());
				if (os[2] != null)
				{
                    Date st = myFmt.parse(os[2].toString());
            	    c.setStartDate(getXMLGregorianCalendar(st));
				}
                if (os[3] != null)
				{
					Date en = myFmt.parse(os[3].toString());
                    c.setEndDate(getXMLGregorianCalendar(en));
				}
            	if(os[4]!=null)c.setNum_reg(Integer.parseInt(os[4].toString()));
            	if(os[5]!=null)c.setNum_sub(Integer.parseInt(os[5].toString()));
            	result.add(c);
            	
            }
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (ParseException e) {
        	throw wrapContestManagementException(e, "There are errors while persisting the entity.");
		} finally {
            logExit("getAllContestsForMonitor()");
        }
    }

	
	/**
     * <p>
     * This is going to fetch all the currently available contests related to given project.
     * </p>
     * @param the given project id;
     * @return the list of all available contents (or empty if none found)
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
     * @since 1.1
     */
	@PermitAll
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleContestData> getSimpleContestData(long pid) throws ContestManagementException
    {
    	try {
			logEnter("getAllContestsForMonitor()");

			EntityManager em = getEntityManager();

            String qstr = "select c.contest_id, c.name as cname, c.start_time,  c.end_time,  "
                    + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                    + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1"
                    +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub "
                    + " from contest c "
                    + " where c.tc_direct_project_id ="+pid+" and c.deleted = 0 and c.contest_detailed_status_id!=3";

			Query query = em.createNativeQuery(qstr);

			List list = query.getResultList();

			List<SimpleContestData> result = new ArrayList<SimpleContestData>();
			SimpleDateFormat myFmt = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");

            for (int i = 0; i < list.size(); i++) {

                SimpleContestData c = new SimpleContestData();
                Object[] os = (Object[]) list.get(i);
                if (os[0] != null)
                    c.setContestId(Long.parseLong(os[0].toString()));
                if (os[1] != null)
                    c.setName(os[1].toString());
                if (os[2] != null)
				{
                    Date st = myFmt.parse(os[2].toString());
            	    c.setStartDate(getXMLGregorianCalendar(st));
				}
                if (os[3] != null)
				{
					Date en = myFmt.parse(os[3].toString());
                    c.setEndDate(getXMLGregorianCalendar(en));
				}
                if (os[4] != null)
                    c.setNum_reg(Integer.parseInt(os[4].toString()));
                if (os[5] != null)
                    c.setNum_sub(Integer.parseInt(os[5].toString()));
                result.add(c);

			}
			return result;
		} catch (IllegalStateException e) {
			throw wrapContestManagementException(e,
					"The EntityManager is closed.");
		} catch (PersistenceException e) {
			throw wrapContestManagementException(e,
					"There are errors while persisting the entity.");
		} catch (ParseException e) {
			throw wrapContestManagementException(e,
					"There are errors while persisting the entity.");
		} finally {
			logExit("getAllContestsForMonitor()");
		}
    }

	@PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleContestData> getSimpleContestDataForUser(long createdUser) throws ContestManagementException {
        try {
            logEnter("getAllContestsForMonitor()");

            EntityManager em = getEntityManager();


            String qstr="select c.contest_id, c.name as cname, c.start_time,  c.end_time,  "
                + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1 "
                +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub "
                + " from contest c "
                + " where c.tc_direct_project_id is not null and c.deleted = 0 and c.contest_detailed_status_id!=3 and c.create_user_id = "+createdUser;

            Query query = em.createNativeQuery(qstr);

            List list = query.getResultList();

            List<SimpleContestData> result = new ArrayList<SimpleContestData>();
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            for (int i = 0; i < list.size(); i++) {

                SimpleContestData c = new SimpleContestData();
                Object[] os = (Object[]) list.get(i);
                if (os[0] != null)
                    c.setContestId(Long.parseLong(os[0].toString()));
                if (os[1] != null)
                    c.setName(os[1].toString());
                if (os[2] != null)
				{
                    Date st = myFmt.parse(os[2].toString());
            	    c.setStartDate(getXMLGregorianCalendar(st));
				}
                if (os[3] != null)
				{
					Date en = myFmt.parse(os[3].toString());
                    c.setEndDate(getXMLGregorianCalendar(en));
				}
                if (os[4] != null)
                    c.setNum_reg(Integer.parseInt(os[4].toString()));
                if (os[5] != null)
                    c.setNum_sub(Integer.parseInt(os[5].toString()));
                result.add(c);

            }
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } catch (ParseException e) {
        	throw wrapContestManagementException(e, "There are errors while persisting the entity.");
		} finally {
            logExit("getAllContestsForMonitor()");
        }
    }
    
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws ContestManagementException {
        try {
            logEnter("getSimpleProjectContestData()");

            EntityManager em = getEntityManager();
            
            
            String qstr="select p.project_id , p.name as pname, c.contest_id,  c.name as cname, "
                    + " c.start_time, c.end_time,  ds.name as sname, p.description, c.forum_id, "
                    + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                    + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1 "
                    +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub, "
                    + " (select count(*) from jivemessage where forumid = c.forum_id ) as num_for "
                    + " from tc_direct_project p left OUTER JOIN contest c ON c.tc_direct_project_id = p.project_id "
                    + " left outer join contest_detailed_status_lu ds on c.contest_detailed_status_id = ds.contest_detailed_status_id "
                    + "  where c.deleted = 0 and c.contest_detailed_status_id!=3 order by p.project_id";

            Query query = em.createNativeQuery(qstr,"ContestForMyProjectResults");

            List list = query.getResultList();

            List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            for (int i = 0; i < list.size(); i++) {
/*
                SimpleProjectContestData c = new SimpleProjectContestData();
                Object[] os = (Object[]) list.get(i);
                if (os[0] != null)
                    c.setProjectId(Long.parseLong(os[0].toString()));
                if (os[1] != null)
                    c.setPname(os[1].toString());
                if (os[2] != null)
                    c.setContestId(Long.parseLong(os[2].toString()));
                if (os[3] != null)
                    c.setCname(os[3].toString());

                if (os[4] != null)
                    c.setStartDate(myFmt.parse(os[4].toString()));
                if (os[5] != null)
                    c.setEndDate(myFmt.parse(os[5].toString()));
                if (os[6] != null)
                    c.setSname(os[6].toString());
                if (os[7] != null)
                    c.setNum_reg(Integer.parseInt(os[7].toString()));
                if (os[8] != null)
                    c.setNum_sub(Integer.parseInt(os[8].toString()));

                if (os[9] != null)
                    c.setNum_for(Integer.parseInt(os[9].toString()));

                result.add(c);*/
            	result.add((SimpleProjectContestData) list.get(i));

            }
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        }  finally {
            logExit("getSimpleProjectContestData()");
        }
    }


	/**
     * <p>
     * This is going to fetch all the currently available contests related the given projects.
     * </p>
     * 
     * @param the given project id
     * @return the list of all available contents (or empty if none found)
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
     * @since 1.1
     */

	@PermitAll
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid) throws ContestManagementException
    {
		try {
			logEnter("getSimpleProjectContestData(pid)");

			EntityManager em = getEntityManager();

            String qstr = "select p.project_id , p.name as pname, c.contest_id,  c.name as cname, "
                    + " c.start_time, c.end_time,  ds.name as sname, p.description, c.forum_id, "
                    + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                    + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1 "
                    +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub, "
                    + " (select count(*) from jivemessage where forumid = c.forum_id ) as num_for "
                    + " from tc_direct_project p left OUTER JOIN contest c ON c.tc_direct_project_id = p.project_id "
                    + " left outer join contest_detailed_status_lu ds on c.contest_detailed_status_id = ds.contest_detailed_status_id "
                    + "  where c.deleted = 0 and c.contest_detailed_status_id!=3 "
                    + " and p.project_id = "+pid;

			Query query = em.createNativeQuery(qstr,"ContestForMyProjectResults");

			List list = query.getResultList();

			List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();
			SimpleDateFormat myFmt = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");

			for (int i = 0; i < list.size(); i++) {


            	result.add((SimpleProjectContestData) list.get(i));

			}
			return result;
		} catch (IllegalStateException e) {
			throw wrapContestManagementException(e,
					"The EntityManager is closed.");
		} catch (PersistenceException e) {
			throw wrapContestManagementException(e,
					"There are errors while persisting the entity.");
		} finally {
			logExit("getSimpleProjectContestData(pid)");
		}
    }

	 @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleProjectContestData> getSimpleProjectContestDataForUser(long createdUser) throws ContestManagementException {
        try {
            logEnter("getSimpleProjectContestDataForUser()");

            EntityManager em = getEntityManager();
            
            
            String qstr="select p.project_id , p.name as pname, c.contest_id,  c.name as cname, "
                    + " c.start_time, c.end_time,  ds.name as sname, p.description, c.forum_id, "
                    + " (select count(*) from contest_registration where contest_id = c.contest_id ) as num_reg, "
                    + " (select count(*) from submission where contest_id = c.contest_id and submission_status_id = 1 "
                    +     "  and rank is not null and rank <= (select NVL(property_value, 10000) from contest_config where contest_id = c.contest_id and property_id = 8)) as num_sub, "
                    + " (select count(*) from jivemessage where forumid = c.forum_id ) as num_for "
                    + " from tc_direct_project p left OUTER JOIN contest c ON c.tc_direct_project_id = p.project_id "
                    + " left outer join contest_detailed_status_lu ds on c.contest_detailed_status_id = ds.contest_detailed_status_id "
                    + "  where c.deleted = 0 and c.contest_detailed_status_id!=3 "
                    // here we check both user in project and contest
                    + "  and p.user_id = " + createdUser  + " and (c.create_user_id = "+createdUser+" or c.create_user_id is null)";

            Query query = em.createNativeQuery(qstr,"ContestForMyProjectResults");

            List list = query.getResultList();

            List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            for (int i = 0; i < list.size(); i++) {
            	result.add((SimpleProjectContestData) list.get(i));

            }
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getSimpleProjectContestDataForUser()");
        }
    }

    /**
     * <p>
     * This gets list of <code>SimpleContestData</code> for given project.
     * </p>
     * 
     * @return the list of all available <code>SimpleContestData</code> (or empty if none found)
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1
     */
    public List<SimpleContestData> getContestDataOnly() throws ContestManagementException {
        try {
            logEnter("getContestDataOnly()");

            return getContestDataOnlyInternal(-1, -1);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestDataOnly()");
        }
    }

	/**
     * <p>
     * This gets list of <code>SimpleContestData</code> for given project.
     * </p>
     * 
     * @param pid tc_project_id for which to get list of <code>SimpleContestData</code>.
     * @return the list of all available <code>SimpleContestData</code> (or empty if none found)
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SimpleContestData> getContestDataOnly(long pid) throws ContestManagementException {
        try {
            logEnter("getContestDataOnly(pid)");

            return getContestDataOnlyInternal(-1, pid);

        } finally {
            logExit("getContestDataOnly(pid)");
        }
    }

    /**
     * <p>
     * This gets list of <code>SimpleContestData</code> for given user.
     * </p>
     * 
     * @param createdUser
     *            create_user_id for which to get list of <code>SimpleContestData</code>
     * @return the list of all available <code>SimpleContestData</code> (or empty if none found)
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1
     */
    public List<SimpleContestData> getContestDataOnlyForUser(long createdUser) throws ContestManagementException {
        try {
            logEnter("getContestDataOnlyForUser(createdUser)");

            return getContestDataOnlyInternal(createdUser, -1);

        } finally {
            logExit("getContestDataOnlyForUser(createdUser)");
        }
    }

    /**
     * <p>
     * This gets list of <code>SimpleContestData</code> for given user and project.
     * If createdUserUser == -1, then no filter is applied on create_user_id.
     * If pid == -1, then no filter is applied on tc_project_id
     * </p>
     * 
     * @param createdUser
     *            create_user_id for which to get list of <code>SimpleContestData</code>. If this value is -1, then all
     *            users are considered.
     * @param pid
     *            tc_project_id for which to get list of <code>SimpleContestData</code>. If this value is -1, then all
     *            projects are considred.
     * @return the list of all available <code>SimpleContestData</code> (or empty if none found)
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * @since 1.1
     */
    private List<SimpleContestData> getContestDataOnlyInternal(long createdUser, long pid)
            throws ContestManagementException {
        try {
            logEnter("getContestDataOnlyInternal(createdUser, pid)");

			EntityManager em = getEntityManager();

            String qstr = "select contest_id,  "
                    + " name,  "
                    + " contest_detailed_status_id,  "
                    + " (select contest_detailed_status_desc  "
                    + " from contest_detailed_status_lu ds "
                    + " where ds.contest_detailed_status_id = c.contest_detailed_status_id) as sname, "
                    + " NVL((select amount "
                    + " from prize as p "
                    + " where p.prize_id IN (select prize_id from contest_prize_xref as cpx where cpx.contest_id = c.contest_id) "
                    + " and p.place = 1),0) as prize_1, "
                    + " NVL((select amount "
                    + " from prize as p "
                    + " where p.prize_id IN (select prize_id from contest_prize_xref as cpx where cpx.contest_id = c.contest_id) "
                    + " and p.place = 2),0) as prize_2, "
                    + " NVL((select amount "
                    + " from prize as p "
                    + " where p.prize_id IN (select prize_id from contest_prize_xref as cpx where cpx.contest_id = c.contest_id) "
                    + " and p.place = 3),0) as prize_3, "
                    + " NVL((select amount "
                    + " from prize as p "
                    + " where p.prize_id IN (select prize_id from contest_prize_xref as cpx where cpx.contest_id = c.contest_id) "
                    + " and p.place = 4),0) as prize_4, "
                    + " NVL((select amount "
                    + " from prize as p "
                    + " where p.prize_id IN (select prize_id from contest_prize_xref as cpx where cpx.contest_id = c.contest_id) "
                    + " and p.place = 5),0) as prize_5 " + " from contest c  "
                    + " where not c.tc_direct_project_id is null  " + " and c.deleted = 0 and c.contest_detailed_status_id!=3 ";
            if (createdUser != -1) {
                qstr = qstr + " and c.create_user_id = " + createdUser;
            }

            if (pid != -1) {
                qstr = qstr + " and c.tc_direct_project_id = " + pid;
            }

            Query query = em.createNativeQuery(qstr);

            List list = query.getResultList();

            List<SimpleContestData> result = new ArrayList<SimpleContestData>();

            for (int i = 0; i < list.size(); i++) {

                SimpleContestData c = new SimpleContestData();
                Object[] os = (Object[]) list.get(i);
                if (os[0] != null)
                    c.setContestId(Long.parseLong(os[0].toString()));
                if (os[1] != null)
                    c.setName(os[1].toString());
                if (os[2] != null)
                    c.setStatusId(Long.parseLong(os[2].toString()));
                if (os[3] != null)
                    c.setSname(os[3].toString());

                List<Double> prizeList = new ArrayList<Double>(5);
                c.setPrizes(prizeList);

                // set the prizes.
                for (int j = 0; j < 5; j++) {
                    prizeList.add(new Double(0));
                }

                // set the prizes.
                for (int j = 0; j < 5; j++) {
                    if (os[j + 4] != null)
                        prizeList.set(j, Double.parseDouble(os[j + 4].toString()));
                }

                result.add(c);
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logEnter("getContestDataOnlyInternal(createdUser, pid)");
        }
    }

    /**
     * <p> This is going to get all the matching contest entities that fulfill the input criteria. </p>
     * 
     * @param filter
     *            a search filter used as criteria for contests.
     * @return a list (possibly empty) of all the matched contest entities.
     * 
     * @throws IllegalArgumentException
     *             if the input filter is null or filter is not supported for
     *             searching
     * @throws ContestManagementException
     *             if any error occurs when getting contest categories
     * 
     * @since 1.1
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Contest> searchContests(Filter filter) throws ContestManagementException {
        try {
            logEnter("searchContests()");

            EntityManager em = getEntityManager();

            Object[] searchSQLResult = FilterToSqlConverter.convert(filter);
            logger.log(Level.DEBUG,searchSQLResult[0]);
            
            Query query = em.createNativeQuery((String) searchSQLResult[0], Contest.class);
            
            List bindVariableValues = (List) searchSQLResult[1];
            int index = 1;
            // set all the parameter
            for (Object bindVariableValue : bindVariableValues) {
                query.setParameter(index++, bindVariableValue);
            }

            List list = query.getResultList();

            List<Contest> result = new ArrayList<Contest>();
            result.addAll(list);

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("searchContests()");
        }
    }

    /**
     * <p>
     * Gets all the currently available contests types.
     * </p>
     * 
     * @return the list of all available contents types (or empty if none found)
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest types
     * 
     * @since 1.1
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestType> getAllContestTypes() throws ContestManagementException {
        try {
            logEnter("getAllContestTypes()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select c from ContestType c");

            List list = query.getResultList();

            List<ContestType> result = new ArrayList<ContestType>();
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getAllContestTypes()");
        }
    }

    /**
     * Generic method for getting all the entities
     * 
     * @param clazz
     *            class to get all the entities.
     * @return a list with the all required entities
     * @throws ContestManagementException
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> getAll(Class<T> clazz) throws ContestManagementException {
        try {
            EntityManager em = getEntityManager();

            Query query = em.createQuery("from " + clazz.getName());

            List<T> list = query.getResultList();

            List<T> result = new ArrayList<T>();
            result.addAll(list);
            return result;

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session
     * context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws ContestManagementException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapContestManagementException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapContestManagementException(e, "The jndi name for '" + unitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * Log the entrance of a method.
     * </p>
     * 
     * @param methodName
     *            the method name
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Enter method: ContestManagerBean." + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     * 
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Exit method: " + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the parameter.
     * </p>
     * 
     * @param param
     *            the parameter value
     */
    private void logOneParameter(Object param) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}]", param);
        }
    }

    /**
     * <p>
     * Log the parameters.
     * </p>
     * 
     * @param param1
     *            the first parameter values
     * @param param2
     *            the second parameter value
     */
    private void logTwoParameters(Object param1, Object param2) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}, param2: {1}]", param1, param2);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     * 
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

    /**
     * <p>
     * Creates an <code>EntityNotFoundException</code> with specified message,
     * and log the exception, set the sessionContext to correct state.
     * </p>
     * 
     * @param message
     *            the error message
     * @return the created EntityNotFoundException
     */
    private EntityNotFoundException wrapEntityNotFoundException(String message) {
        EntityNotFoundException e = new EntityNotFoundException(message);
        logException(e, message);
        sessionContext.setRollbackOnly();

        return e;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with the error message.
     * It will log the exception, and set the sessionContext to rollback only.
     * </p>
     * 
     * @param message
     *            the error message
     * @return the created exception
     */
    private ContestManagementException wrapContestManagementException(String message) {
        ContestManagementException e = new ContestManagementException(message);
        logException(e, message);
        sessionContext.setRollbackOnly();

        return e;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception
     * and message. It will log the exception, and set the sessionContext to
     * rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private ContestManagementException wrapContestManagementException(Exception e, String message) {
        ContestManagementException ce = new ContestManagementException(message, e);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a new prize, and return the created prize.
     * </p>
     * 
     * @param prize
     *            the prize to create
     * @return the created prize
     * 
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Prize createPrize(Prize prize) throws ContestManagementException {
        try {
            logEnter("createPrize()");

            Helper.checkNull(prize, "prize");

            // if ((prize.getPrizeId() != null) &&
            // (getContest(contest.getContestId()) != null)) {
            // EntityAlreadyExistsException e = new
            // EntityAlreadyExistsException("The contest already exist.");
            // logException(e, "The contest already exist.");
            // sessionContext.setRollbackOnly();
            //
            // throw e;
            // }

            EntityManager em = getEntityManager();
            em.persist(prize);

            return prize;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("createPrize()");
        }
    }

    /**
     * <p>
     * Creates a new contest payment and returns the created contest payment.
     * </p>
     * 
     * @param contestPayment
     *            the contest payment to create
     * @return the created contest payment.
     * 
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestPayment createContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        try {
            logEnter("createContestPayment()");

            Helper.checkNull(contestPayment, "contestPayment");
            logOneParameter(contestPayment.getPayPalOrderId());

            // if ((contest.getContestId() != null) &&
            // (getContest(contest.getContestId()) != null)) {
            // EntityAlreadyExistsException e = new
            // EntityAlreadyExistsException("The contest already exist.");
            // logException(e, "The contest already exist.");
            // sessionContext.setRollbackOnly();
            //
            // throw e;
            // }

            EntityManager em = getEntityManager();
            em.merge(contestPayment);

            return contestPayment;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("createContestPayment()");
        }
    }

    /**
     * <p>
     * Gets contest payment by contest id, and return the retrieved contest
     * payment. If the contest payment doesn't exist, null is returned.
     * </p>
     * 
     * @param contestId
     *            the contest id.
     * @return the retrieved contest payment, or null if id doesn't exist
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest.
     * @since BUGR-1363 changed method signature
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestPayment> getContestPayments(long contestId) throws ContestManagementException {
        try {
            logEnter("getContestPayments()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();
            Query query = em.createQuery("select c from ContestPayment c where c.contestId = "  + contestId);
            List list = query.getResultList();
            List<ContestPayment> result = new ArrayList<ContestPayment>();
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getContestPayment()");
        }
    }

    /**
     * <p>
     * Updates contest payment data.
     * </p>
     * 
     * @param contestPayment
     *            the contest payment to update
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityNotFoundException
     *             if the contest payment doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any error occurs when updating contest payment.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        try {
            logEnter("editContestPayment()");
            Helper.checkNull(contestPayment, "contestPayment");

            // if ((contest.getContestId() == null) ||
            // (getContest(contest.getContestId()) == null)) {
            // throw wrapEntityNotFoundException("The contest of id '" +
            // contest.getContestId() + "' doesn't exist.");
            // }

            EntityManager em = getEntityManager();

            // Contest result = getContest(contest.getContestId());
            //
            // if (result.getStatus().getContestStatusId().equals(
            // activeContestStatusId)) {
            // checkSet(result.getConfig(), contest.getConfig());
            //
            // if (contest.getContestChannel() == null) {
            // throw
            // wrapContestManagementException("contest.contestChannel is null."
            // );
            // }
            // if ((contest.getContestChannel().getContestChannelId() !=
            // result.getContestChannel()
            // .getContestChannelId())) {
            // throw wrapContestManagementException(MessageFormat
            // .format(
            // "The contest channel doesn't match. persisted contest channel id: {0} updated contest channel id : {1}"
            // ,
            // result.getContestChannel().getContestChannelId(),
            // contest.getContestChannel()
            // .getContestChannelId()));
            // }
            //
            // checkSet(result.getDocuments(), contest.getDocuments());
            //
            // checkSet(result.getFileTypes(), contest.getFileTypes());
            //
            // checkSet(result.getResults(), contest.getResults());
            //
            // checkSet(result.getSubmissions(), contest.getSubmissions());
            // }

            em.merge(contestPayment);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("editContestPayment()");
        }
    }

    /**
     * <p>
     * Removes contest payment, return true if the contest payment exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     * 
     * @param contestId
     *            the contest id.
     * @return true if the contest payment exists and removed successfully,
     *         return false if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean removeContestPayment(long contestId) throws ContestManagementException {
        try {
            logEnter("removeContestPayment()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();

            ContestPayment payment = em.find(ContestPayment.class, new Long(contestId));

            if (payment == null) {
                return false;
            }

            em.remove(payment);

            return true;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("removeContestPayment()");
        }
    }

    /**
     * <p>
     * Fill status's statuses field (next statuses).
     * </p>
     * 
     * @param status
     *            status whose statuses field to be filled.
     * @throws ContestManagementException
     *             if any error occurs when filling the status.
     * @since 1.1.2
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void fillNextStatuses(ContestStatus status) throws ContestManagementException {
        try {
            logEnter("fillToStatuses(ContestStsta)");
            logOneParameter(status);

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("SELECT csl.* FROM contest_detailed_status_lu csl, "
                    + "contest_detailed_status_relation csr " + "WHERE "
                    + "csl.contest_detailed_status_id = csr.to_contest_status_id AND "
                    + "csr.from_contest_status_id = ?", ContestStatus.class);
            query.setParameter(1, status.getContestStatusId());
            List<ContestStatus> list = query.getResultList();
            if (list != null) {
                /*
                 * for (ContestStatus s : list) { fillNextStatuses(s); }
                 */
                status.setStatuses(list);
            }
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("fillToStatuses(ContestStatus)");
        }
    }

    /**
     * <p>
     * Gets contests by the created user. If there is no such contests, an empty
     * list should be returned.
     * </p>
     * 
     * @param createdUser
     *            the created user.
     * @return a list of associated contests
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contests
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Contest> getContestsForUser(long createdUser) throws ContestManagementException {
        try {
            logEnter("getContestsForUser(createdUser)");
            logOneParameter(createdUser);

            EntityManager em = getEntityManager();

            Query query = em
                    .createQuery("select c from Contest c where not c.tcDirectProjectId is null and c.deleted = false and c.createdUser = "
                            + createdUser);

            List list = query.getResultList();

            List<Contest> result = new ArrayList<Contest>();
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while retrieving the contest.");
        } finally {
            logExit("getContestsForUser()");
        }
    }

    /**
     * Returns all media.
     * 
     * @return all media.
     * @throws ContestManagementException
     *             if any error occurs when getting contests
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Medium> getAllMedia() throws ContestManagementException {
        try {
            logEnter("getAllMedia()");

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select m from Medium m");

            List list = query.getResultList();
            List<Medium> result = new ArrayList<Medium>();
            result.addAll(list);
            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while retrieving medium.");
        } finally {
            logExit("getAllMedia()");
        }
    }


    /**
     * Returns contest post count.
     * 
     * @return contest post count.
     * @throws ContestManagementException
     *             if any error occurs when getting contest post count.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int getContestPostCount(long forumId) throws ContestManagementException {
        try {
            logEnter("getContestPostCount()");

            EntityManager em = getEntityManager();

            Query query = em.createNativeQuery("select count(*) from jivemessage where forumid=?");
            query.setParameter(1, forumId);

            BigDecimal count = (BigDecimal) query.getSingleResult();
            return count.intValue();
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while retrieving medium.");
        } finally {
            logExit("getContestPostCount()");
        }
    }
    


    /**
     * Returns contest post count list.
     * 
     * @param forumIds forum ids.
     * @return contest post count list.
     * @throws ContestManagementException
     *             if any error occurs when getting contest post count.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Map<Long,Long> getContestPostCount(List<Long> forumIds) throws ContestManagementException{
        try {
            logEnter("getContestPostCount()");

            if (forumIds.isEmpty()) {
                return new HashMap<Long, Long>();
            }
            EntityManager em = getEntityManager();

            StringBuilder sb = new StringBuilder();
            for (long forumId : forumIds) {
                sb.append("? , ");
            }

            Query query = em.createNativeQuery(MessageFormat.format(
                    "select forumid, count(*) from jivemessage where forumid in ({0}) group by forumid", sb.toString()
                            .substring(0, sb.toString().length() - 2)));

            for (int i = 0; i < forumIds.size(); ++i) {
                query.setParameter(i + 1, forumIds.get(i));
            }

            List<Object[]> resultList = query.getResultList();
            Map<Long, Long> ret = new HashMap<Long, Long>();

            for (Object[] arr : resultList) {
                ret.put(Long.parseLong(arr[0].toString()), Long.parseLong(arr[1].toString()));
            }

            return ret;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while retrieving forum post count.");
        } finally {
            logExit("getContestPostCount()");
        }
    }
    

    /**
     * <p>
     * Creates a new contest result and returns the created contest result.
     * </p>
     * 
     * @param contestResult
     *            the contest result to create
     * @return the created contest result.
     * 
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestResult createContestResult(ContestResult contestResult) throws ContestManagementException
    {
        try {
            logEnter("createContestResult()");

            Helper.checkNull(contestResult, "contestResult");

            EntityManager em = getEntityManager();
            em.persist(contestResult);

            return contestResult;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("createContestResult()");
        }
    }

    
    
    
    /**
     * <p>
     * Returns the contest result associated with submissionId, contestId if any.
     * </p>
     * 
     * @param submissionId
     *            the submission Id
     * @param contestId
     * 			  the contest Id
     * @return the contest result or null.
     * 
     * @throws ContestManagementException
     *             if any error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContestResult findContestResult(long submissionId, long contestId) throws ContestManagementException
    {
        try {
            logEnter("findContestResult()");
            
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT c from ContestResult c where c.submission.submissionId=" + submissionId + " and c.contest.contestId=" + contestId);
            return (ContestResult) query.getSingleResult();
        } catch (NoResultException e) {
        	return null;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("findContestResult()");
        }
    }

    /**
     * Add a change history entity.
     * 
     * @param history
     *            Change history entity to be added.
     * 
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addChangeHistory(List<ContestChangeHistory> history) throws ContestManagementException {
        try {
            logEnter("addChangeHistory()");

            Helper.checkNull(history, "history");

            EntityManager em = getEntityManager();
            em.persist(history);

            return;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapContestManagementException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addChangeHistory()");
        }
    }

    /**
     * Returns change history entity list.
     * 
     * @param contestId
     *            contest id to search for.
     * @return Change history entities match the contest id.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestChangeHistory> getChangeHistory(long contestId) throws ContestManagementException {
        try {
            logEnter("getChangeHistory()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();
            Query query = em.createQuery("select ch from ContestChangeHistory ch where ch.contestId = " + contestId);
            List list = query.getResultList();

            List<ContestChangeHistory> result = new ArrayList<ContestChangeHistory>();

            for (int i = 0; i < list.size(); i++) {
                result.add((ContestChangeHistory) list.get(i));
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getChangeHistory()");
        }
    }

    /**
     * Returns change history entity list.
     * 
     * @param contestId
     *            contest id to search for.
     * @param transactionId
     *            transaction id to search for.
     * 
     * @return Change history entities match the contest id and transaction id.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestChangeHistory> getChangeHistory(long contestId, long transactionId) throws ContestManagementException {
        try {
            logEnter("getChangeHistory()");
            logTwoParameters(contestId, transactionId);

            EntityManager em = getEntityManager();
            Query query = em.createQuery("select ch from ContestChangeHistory ch where ch.contestId = " + contestId  + " AND ch.transactionId = " + transactionId);
            List list = query.getResultList();

            List<ContestChangeHistory> result = new ArrayList<ContestChangeHistory>();

            for (int i = 0; i < list.size(); i++) {
                result.add((ContestChangeHistory) list.get(i));
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getChangeHistory()");
        }
    }

    /**
     * Returns latest transaction id.
     * 
     * @param contestId
     *            contest id to search for.
     * @return Transaction id.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long getLatestTransactionId(long contestId) throws ContestManagementException {
        try {
            logEnter("getLatestTransactionId()");
            logOneParameter(contestId);

            List<ContestChangeHistory> changeHistory = getChangeHistory(contestId);
            Date latest = null;
            Long transactionId = null;

            for (ContestChangeHistory cch : changeHistory) {
                if (latest == null || cch.getTimestamp().after(latest)) {
                    latest = cch.getTimestamp();
                    transactionId = cch.getTransactionId();
                }
            }
            
            return transactionId;
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getLatestTransactionId()");
        }
    }
    

    /**
     * Delete contest.
     * 
     * @param contestId
     *            contest id to delete.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteContest(long contestId) throws ContestManagementException{
        try {
            logEnter("deleteContest()");
            logOneParameter(contestId);

            Contest contest = this.getContest(contestId);
            contest.setDeleted(true);

            EntityManager em = getEntityManager();
            em.merge(contest);
        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while deleting the contest.");
        } finally {
            logExit("deleteContest()");
        }
    }

    /**
     * <p>
     * Gets all payment types to return. If no payment types exist, return an empty list.
     * </p>
     * 
     * @return a list of payment types
     * @throws ContestManagementException
     *             if any error occurs when getting payment types
     * @since BUGR-1076
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PaymentType> getAllPaymentTypes() throws ContestManagementException {
        try {
            logEnter("getAllPaymentTypes()");

            return getAll(PaymentType.class);

        } finally {
            logExit("getAllPaymentTypes()");
        }
    }

    /**
     * <p>
     * Get the PaymentType with the specified id.
     * </p>
     * 
     * @param paymentTypeId
     *            id to look for
     * @return the PaymentType with the specified id.
     * @throws ContestManagementException
     *             if any error occurs when getting PaymentStatus
     * @since BUGR-1076
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PaymentType getPaymentType(long paymentTypeId) throws ContestManagementException {
        try {
            logEnter("getPaymentType()");
            logOneParameter(paymentTypeId);

            EntityManager em = getEntityManager();

            return em.find(PaymentType.class, paymentTypeId);

        } catch (IllegalStateException e) {
            throw wrapContestManagementException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapContestManagementException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("getPaymentType()");
        }
    }


	/**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date
     *            Date object to convert
     * @return converted calendar instance
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            // can't create calendar, return null
            return null;
        }
    }
}
