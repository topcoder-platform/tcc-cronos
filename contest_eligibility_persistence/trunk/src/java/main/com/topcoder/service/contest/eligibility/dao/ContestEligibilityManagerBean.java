/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It is a stateless session bean that uses JPA to manage the persistence of eligibility entities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is thread safe because all operations are transactions in the EJB
 * environment.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //Acquire a ContestEligibilityManager
 * Context context = new InitialContext();
 * ContestEligibilityManager contestEligibilityManager =
 *     (ContestEligibilityManager) context
 *         .lookup(&quot;contest_eligibility_persistence/ContestEligibilityManagerBean/remote&quot;);
 *
 * // create a GroupContestEligibility instance named groupContestEligibility
 * ContestEligibility groupContestEligibility = new GroupContestEligibility();
 * groupContestEligibility.setContestId(16);
 * groupContestEligibility.setStudio(true);
 *
 * // insert groupContestEligibility into DB
 * groupContestEligibility = contestEligibilityManager.create(groupContestEligibility);
 *
 * // get a list of eligibilities for a contest
 * List&lt;ContestEligibility&gt; list = contestEligibilityManager.getContestEligibility(16, true);
 * System.out.println(list.size());
 *
 * // Save a list of eligibilities,you can add/update/delete entities.Here we just update it. You also
 * // can refer to ContestEligibilityManagerBeanTests for more tests to add/update/delete.
 * contestEligibilityManager.save(list);
 *
 * // Remove a contest eligibility
 * contestEligibilityManager.remove(groupContestEligibility);
 * </pre>
 *
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Stateless
public class ContestEligibilityManagerBean implements ContestEligibilityManagerLocal,
    ContestEligibilityManagerRemote {

    /**
     * <p>
     * This field represents the entity manager which is used to communicate with the persistence.
     * </p>
     * <p>
     * It's automatically injected by EJB container.
     * </p>
     */
    @PersistenceContext(name = "unitName")
    private EntityManager entityManager;

    /**
     * The logger is used to log the methods.
     */
    private Log logger = null;

    /**
     * Represents the log name.Default value is 'contest_eligibility_logger'.You also could change the default value
     * via deploy descriptor.
     */
    @Resource(name = "logName")
    private String logName = "contest_eligibility_logger";

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ContestEligibilityManagerBean() {
        // Does nothing
    }

    /**
     * Handle the post-construct event. It will initialize the logger.
     */
    @PostConstruct
    protected void initialize() {
        logger = LogManager.getLog(logName);
    }

    /**
     * Add a contest eligibility.
     *
     * @param contestEligibility
     *            contest eligibility
     * @return the added contest eligibility
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when persisting the given contest eligibility
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContestEligibility create(ContestEligibility contestEligibility)
        throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#create", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            entityManager.persist(contestEligibility);
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while persisting the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#create");
        return contestEligibility;
    }

    /**
     * Remove a contest eligibility.
     *
     * @param contestEligibility
     *            the contest eligibility to remove
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when removing the given contest eligibility
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(ContestEligibility contestEligibility) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#remove", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            entityManager.remove(entityManager.merge(contestEligibility));
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while removing the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#remove");
    }

    /**
     * <p>
     * Save a list of eligibilities, if can be create/update/delete.
     * </p>
     * <ol>
     * You will get a list of eligibilities as input parameter, for each eligibility in the list, you do one
     * of these,
     * <li>create/insert, if id is 0 then insert.</li>
     * <li>update, if id is not 0, do an update.</li>
     * <li>delete, if 'delete' flag is true, you remove the eligibility.</li>
     * </ol>
     *
     * @param list
     *            a list of eligibilities,can be empty,then do nothing
     * @return the added/updated eligibilities
     * @throws IllegalArgumentException
     *             if list is null or it contains null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when saving eligibilities
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContestEligibility> save(List<ContestEligibility> list)
        throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#save", new String[] {"list"}, new Object[] {list});
        checkNull(list, "list");
        for (ContestEligibility contestEligibility : list) {
            checkNull(contestEligibility, "contest eligibility in list");
        }
        try {
            for (Iterator<ContestEligibility> iterator = list.iterator(); iterator.hasNext();) {
                ContestEligibility contestEligibility = iterator.next();
                if (contestEligibility.isDeleted()) {
                    entityManager.remove(entityManager.merge(contestEligibility));
                    iterator.remove();
                    continue;
                }

                if (contestEligibility.getId() != 0) {
                    entityManager.merge(contestEligibility);
                    continue;
                }
                entityManager.persist(contestEligibility);
            }
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while saving the list of contestEligibility.", e));
        }
        logExit("ContestEligibilityManagerBean#save");
        return list;
    }

    /**
     * Return a list of eligibilities for a contest.
     *
     * @param contestId
     *            the contest id
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of eligibilities
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    @SuppressWarnings("unchecked")
    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio) {
        logEntrance("ContestEligibilityManagerBean#getContestEligibility", new String[] {"contestId",
            "isStudio"}, new Object[] {contestId, isStudio});
        checkPositive(contestId, "contestId");
        final Query query =
            entityManager
                .createQuery("from ContestEligibility c where c.contestId=:contestId and c.studio=:studio");
        query.setParameter("contestId", contestId);
        query.setParameter("studio", isStudio);
        List<ContestEligibility> results = query.getResultList();
        logExit("ContestEligibilityManagerBean#getContestEligibility");
        return results;
    }

    /**
     * <p>
     * Logs the error.
     * </p>
     *
     * @param <T>
     *            the generic class type of error
     * @param error
     *            the error needs to be logged.
     * @return the error
     */
    private <T extends Exception> T logError(T error) {
        logger.log(Level.ERROR, error, "Error recognized: {0}", error.getMessage());
        return error;
    }

    /**
     * <p>
     * Log the entrance of a method and all the input arguments.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param paramNames
     *            the name of the parameters
     * @param params
     *            the parameters
     */
    @SuppressWarnings("unchecked")
    private void logEntrance(String methodName, String[] paramNames, Object[] params) {
        logger.log(Level.DEBUG, "Enter into Method: " + methodName + " At " + new Date());
        if (paramNames != null) {
            StringBuilder logInfo = new StringBuilder("Parameters:");
            for (int i = 0; i < paramNames.length; i++) {
                if (params[i] instanceof ContestEligibility) {
                    ContestEligibility contestEligibility = (ContestEligibility) params[i];
                    logInfo.append(" [ " + paramNames[i] + " = " + contestEligibility.getClass().getName()
                        + " with id=" + contestEligibility.getId() + " ]");
                    continue;
                }
                if (params[i] instanceof List && ((List<ContestEligibility>) params[i]).size() != 0) {
                    List<ContestEligibility> list = (List<ContestEligibility>) params[i];
                    StringBuilder paramLog = new StringBuilder();
                    for (ContestEligibility contestEligibility : list) {
                        if (contestEligibility == null) {
                            paramLog.append("  null");
                            continue;
                        }
                        paramLog.append(contestEligibility.getClass().getName() + " with id="
                            + contestEligibility.getId() + "  ");
                    }
                    logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
                    continue;
                }
                logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
            }
            logger.log(Level.INFO, logInfo);
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logExit(String methodName) {
        logger.log(Level.DEBUG, "Exit out Method: " + methodName + " At " + new Date());
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private void checkNull(Object arg, String name) {
        if (arg == null) {
            IllegalArgumentException e =
                new IllegalArgumentException("The argument " + name + " cannot be null.");
            throw logError(e);
        }
    }

    /**
     * <p>
     * Checks whether the given argument is positive.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given argument is not positive
     */
    private void checkPositive(long arg, String name) {
        if (arg <= 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should be positive.");
            throw logError(e);
        }
    }
}