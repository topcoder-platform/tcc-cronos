/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview.ejb;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.specreview.ReviewComment;
import com.topcoder.service.specreview.ReviewCommentView;
import com.topcoder.service.specreview.ReviewStatus;
import com.topcoder.service.specreview.ReviewUserRoleType;
import com.topcoder.service.specreview.SectionType;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.specreview.SpecReviewServiceException;
import com.topcoder.service.specreview.SpecReviewServiceLocal;
import com.topcoder.service.specreview.SpecReviewServiceRemote;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * The Class SpecReviewServiceBean.
 * 
 * @author snow01
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 * @version 1.0
 */
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class SpecReviewServiceBean implements SpecReviewServiceRemote, SpecReviewServiceLocal {

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * Represents the instance of entity manager.
     */
    private EntityManager entityManager;

    /**
     * Constant variable representing get spec review Named Query
     */
    private static final String SpecReviewsQuery = "getSpecReviews";

    /**
     * Constant variable representing get spec review by contest id and section name Named Query
     */
    private static final String SpecReviewByContestAndSectionQuery = "getSpecReviewByContestAndSection";

    /**
     * Constant variable representing get section type by name Named Query.
     */
    private static final String SectionTypeByNameQuery = "getSectionTypeByName";

    /**
     * Constant variable representing get review status by name Named Query
     */
    private static final String ReviewStatusByNameQuery = "getReviewStatusByName";

    /**
     * Constant variable representing get review user role type by name Named Query
     */
    private static final String ReviewUserRoleTypeByNameQuery = "getReviewUserRoleTypeByName";

    /**
     * Constant variable representing get review comment views by comment ids Named Query
     */
    private static final String ReviewCommentViewsByCommentIdsQuery = "getReviewCommentViewsByCommentIds";

    /**
     * Id that is set for unset entities.
     */
    private static final long UnsetId = -1;

    /**
     * A default empty constructor.
     */
    public SpecReviewServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");

        try {
            entityManager = getEntityManager();
        } catch (SpecReviewServiceException e) {
            throw new IllegalStateException("Error during init()", e);
        }
    }

    /**
     * Gets the spec reviews for specified contest id.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * 
     * @return the list of spec reviews that matches the specified contest id.
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    @SuppressWarnings("unchecked")
    public List<SpecReview> getSpecReviews(long contestId, boolean studio) throws SpecReviewServiceException {
        List<SpecReview> result = null;
        try {
            logEnter("getSpecReviews(contestId, studio)", contestId, studio);

            Query query = entityManager.createNamedQuery(SpecReviewsQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("studio", studio);

            result = query.getResultList();

        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the spec reviews.");
        } finally {
            logExit("getSpecReviews(contestId, studio)", result);
        }

        return result;
    }

    /**
     * Mark review comment with specified comment id as seen.
     * 
     * @param commentId
     *            the comment id
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void markReviewCommentSeen(long commentId) throws SpecReviewServiceException {
        try {
            logEnter("markReviewCommentSeen(commentId)", commentId);

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userName = p.getName();

            ReviewCommentView rcv = new ReviewCommentView();
            rcv.setViewId(UnsetId);
            rcv.setCommentId(commentId);
            rcv.setViewUser(userName);
            rcv.setViewTime(new Date());

            entityManager.persist(rcv);
            entityManager.flush();
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while saving the review comment seen.");
        } finally {
            logExit("markReviewCommentSeen(commentId)");
        }

    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void saveReviewComment(long contestId, boolean studio, String sectionName, String comment, String role)
            throws SpecReviewServiceException {
        try {
            logEnter("saveReviewComment(contestId, studio, sectionName, comment, role)", contestId, studio,
                    sectionName, comment, role);

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userName = p.getName();

            logDebug("UserName: " + userName);

            Query query = null;

            query = entityManager.createNamedQuery(SpecReviewByContestAndSectionQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("studio", studio);
            query.setParameter("name", sectionName);

            SpecReview specReview = null;

            try {
                specReview = (SpecReview) query.getSingleResult();

                logDebug("Found specReview: " + specReview);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specReview == null) {
                specReview = new SpecReview();
                specReview.setSpecReviewId(UnsetId);
                specReview.setContestId(contestId);
                specReview.setStudio(studio);

                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", "PENDING");

                ReviewStatus pendingRS = (ReviewStatus) query.getSingleResult();

                logDebug("Found pendingRS: " + pendingRS);

                query = entityManager.createNamedQuery(SectionTypeByNameQuery);
                query.setParameter("name", sectionName);
                query.setParameter("studio", studio);

                SectionType sectionType = (SectionType) query.getSingleResult();

                logDebug("Found sectionType: " + sectionType);

                specReview.setReviewStatus(pendingRS);
                specReview.setComments(null);
                specReview.setCreateUser(userName);
                specReview.setCreateTime(new Date());
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());
                specReview.setSectionType(sectionType);

                entityManager.persist(specReview);
                entityManager.flush();

                logDebug("Persisted specReview: " + specReview.getSpecReviewId());
            }

            query = entityManager.createNamedQuery(ReviewUserRoleTypeByNameQuery);
            query.setParameter("name", role);
            ReviewUserRoleType roleType = (ReviewUserRoleType) query.getSingleResult();

            logDebug("Found roleType: " + roleType);

            ReviewComment rc = new ReviewComment();
            rc.setCommentId(UnsetId);
            rc.setComment(comment);
            rc.setRoleType(roleType);
            rc.setSpecReviewId(specReview.getSpecReviewId());
            rc.setCreateUser(userName);
            rc.setCreateTime(new Date());

            entityManager.persist(rc);
            entityManager.flush();

            logDebug("Persisted ReviewComment: " + rc.getCommentId());
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while saving review comment.");
        } finally {
            logExit("saveReviewComment(contestId, studio, sectionName, comment, role)");
        }
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void saveReviewStatus(long contestId, boolean studio, String sectionName, String comment, boolean isPass,
            String role) throws SpecReviewServiceException {
        try {
            logEnter("saveReviewStatus(contestId, studio, sectionName, comment, isPass, role)", contestId, studio,
                    sectionName, comment, isPass, role);

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userName = p.getName();

            logDebug("UserName: " + userName);

            Query query = null;

            query = entityManager.createNamedQuery(SpecReviewByContestAndSectionQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("studio", studio);
            query.setParameter("name", sectionName);

            SpecReview specReview = null;

            try {
                specReview = (SpecReview) query.getSingleResult();

                logDebug("Found specReview: " + specReview);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specReview == null) {
                specReview = new SpecReview();
                specReview.setSpecReviewId(UnsetId);
                specReview.setContestId(contestId);
                specReview.setStudio(studio);

                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", isPass ? "PASSED" : "FAILED");

                ReviewStatus rs = (ReviewStatus) query.getSingleResult();

                logDebug("Found rs: " + rs);

                query = entityManager.createNamedQuery(SectionTypeByNameQuery);
                query.setParameter("name", sectionName);
                query.setParameter("studio", studio);
                
                SectionType sectionType = (SectionType) query.getSingleResult();

                logDebug("Found sectionType: " + sectionType);

                specReview.setReviewStatus(rs);
                specReview.setComments(null);
                specReview.setCreateUser(userName);
                specReview.setCreateTime(new Date());
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());
                specReview.setSectionType(sectionType);

                entityManager.persist(specReview);
                entityManager.flush();

                logDebug("Persisted specReview: " + specReview.getSpecReviewId());
            } else {
                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", isPass ? "PASSED" : "FAILED");

                ReviewStatus rs = (ReviewStatus) query.getSingleResult();
                specReview.setReviewStatus(rs);
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());

                entityManager.merge(specReview);
                entityManager.flush();
                logDebug("Persisted specReview: " + specReview.getSpecReviewId());
            }

            query = entityManager.createNamedQuery(ReviewUserRoleTypeByNameQuery);
            query.setParameter("name", role);
            ReviewUserRoleType roleType = (ReviewUserRoleType) query.getSingleResult();

            logDebug("Found roleType: " + roleType);

            ReviewComment rc = new ReviewComment();
            rc.setCommentId(UnsetId);
            rc.setComment(comment);
            rc.setRoleType(roleType);
            rc.setSpecReviewId(specReview.getSpecReviewId());
            rc.setCreateUser(userName);
            rc.setCreateTime(new Date());

            entityManager.persist(rc);
            entityManager.flush();

            logDebug("Persisted ReviewComment: " + rc.getCommentId());
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the spec reviews.");
        } finally {
            logExit("saveReviewStatus(contestId, studio, sectionName, comment, isPass, role)");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * 
     * @throws SpecReviewServiceException
     *             the spec review service exception
     */
    private EntityManager getEntityManager() throws SpecReviewServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapSpecReviewServiceException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapSpecReviewServiceException(e, "The jndi name for '" + unitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * This method used to log enter in method. It will persist both method name and it's parameters if any.
     * </p>
     * 
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Enter method UserSyncServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    /**
     * <p>
     * This method used to log arbitrary debug message. It will persist debug message.
     * </p>
     * 
     * @param message
     *            message information
     */
    private void logDebug(String msg) {
        if (logger != null) {
            logger.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     * 
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     * 
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, returnValue);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     * 
     * @param error
     *            exception describing error
     * @param message
     *            additional message information
     */
    private void logError(Throwable error, String message) {
        if (error == null) {
            logError(message);
        }
        if (logger != null) {
            logger.log(Level.ERROR, error, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     * 
     * @param message
     *            message information
     */
    private void logError(String message) {
        if (logger != null) {
            logger.log(Level.ERROR, message);
        }
    }

    /**
     * <p>
     * Creates a <code>SpecReviewServiceException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * 
     * @return the created exception
     */
    private SpecReviewServiceException wrapSpecReviewServiceException(Exception e, String message) {
        SpecReviewServiceException ce = new SpecReviewServiceException(message, e);
        logError(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     * 
     * @param message
     *            the error message
     * 
     * @return the created exception
     */
    private SpecReviewServiceException wrapSpecReviewServiceException(String message) {
        SpecReviewServiceException ce = new SpecReviewServiceException(message);
        logError(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }
}
