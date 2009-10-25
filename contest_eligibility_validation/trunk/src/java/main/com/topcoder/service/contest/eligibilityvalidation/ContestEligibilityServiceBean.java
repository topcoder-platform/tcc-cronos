/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import java.util.List;
import java.util.ArrayList;

import org.jboss.logging.Logger;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManager;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerException;




/**
 * <p>
 * This is an implementation of <code>Contest Service Facade</code> web service
 * in form of stateless session EJB. It holds a reference to
 * {@link StudioService} which is delegated the fulfillment of requests.
 * </p>

 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestEligibilityServiceBean implements ContestEligibilityServiceLocal,
    ContestEligibilityServiceRemote {

    /**
     * The logger instance for logging the information in
     * ContestServiceFacadeBean.
     *
     * @since 1.1
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * <p>
     * A <code>ContestEligibilityValidationManager</code> providing access to available
     * <code>Contest Eligibility Validation EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/contest_eligibility_validation")
    private ContestEligibilityValidationManager contestEligibilityValidationManager = null;

    /**
     * <p>
     * A <code>ContestEligibilityManager</code> providing access to available
     * <code>Contest Eligibility Persistence EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/contest_eligibility_persistence")
    private ContestEligibilityManager contestEligibilityManager = null;
   
    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestEligibilityValidatorException {
        String methodName = "isEligible";
        logger.info("Enter: " + methodName);

        boolean eligible = false;
    	
    	try {
			List<ContestEligibility> eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
			eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
		} catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		} catch (ContestEligibilityValidationManagerException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		}
    	    	
        logger.info("Exit: " + methodName);
    	return eligible;
    }

     /**
     * Returns whether a contest has any eligibility
     *
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean hasEligibility(long contestId, boolean isStudio) throws ContestEligibilityValidatorException {

        String methodName = "hasEligibility";
        logger.info("Enter: " + methodName);

        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
    	
    	try {
			 eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
		} catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		}
    	    	
        if (eligibilities == null || eligibilities.size() == 0)
        {
            return false;
        }

        return true;
    }

}
