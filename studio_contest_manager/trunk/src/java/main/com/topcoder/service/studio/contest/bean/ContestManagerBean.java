/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.project.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestConfigurationException;
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
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.utils.FilterToSqlConverter;
import com.topcoder.service.studio.submission.Prize;
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
    public void updateContest(Contest contest) throws ContestManagementException {
        try {
            logEnter("updateContest()");
            Helper.checkNull(contest, "contest");

            if ((contest.getContestId() == null) || (getContest(contest.getContestId()) == null)) {
                throw wrapEntityNotFoundException("The contest of id '" + contest.getContestId() + "' doesn't exist.");
            }

            EntityManager em = getEntityManager();

            Contest result = getContest(contest.getContestId());

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
        if ((dest == null) || (dest.size() != src.size()) || !src.containsAll(dest)) {
            throw wrapContestManagementException("The set doesn't match.");
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

            Contest contest = em.find(Contest.class, new Long(contestId));

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

            // When moving the document,
            // the path in the database must be changed to the new location
            // (stored in document.path).
            FilePath path = document.getPath();
            path.setPath(path.getPath() + File.separator + contestId);
            document.setPath(path);
            em.persist(document);

            contest.getDocuments().add(document);
            document.getContests().add(contest);

            em.merge(contest);

            // [BUG TCCC-134]
            documentContentManager.moveDocumentToContestFolder(formPath(document), contestId);
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
     *             if any error occurs when getting MimeType
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

            Query query = em.createQuery("select c from Contest c");

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

    /**
     * <p>
     * This is going to get all the matching contest entities that fulfill the
     * input criteria.
     * </p>
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
            logger.log(Level.INFO, "[Enter method: " + methodName + "]");
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
            logger.log(Level.INFO, "[Exit method: " + methodName + "]");
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
            logger.log(Level.INFO, "[param1: {0}]", param);
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
            logger.log(Level.INFO, "[param1: {0}, param2: {1}]", param1, param2);
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
            em.persist(contestPayment);

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
     * Gets contest payment by contest id, and return the retrieved contest payment. If
     * the contest payment doesn't exist, null is returned.
     * </p>
     * 
     * @param contestId
     *            the contest id.
     * @return the retrieved contest payment, or null if id doesn't exist
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestPayment getContestPayment(long contestId) throws ContestManagementException {
        try {
            logEnter("getContestPayment()");
            logOneParameter(contestId);

            EntityManager em = getEntityManager();
            ContestPayment contestPayment = em.find(ContestPayment.class, new Long(contestId));
            return contestPayment;
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
            	for (ContestStatus s : list) {
                    fillNextStatuses(s);
                }
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
}
