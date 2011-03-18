/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.calculators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.Helper;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.UserReliabilityCalculator;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an abstract implementation of UserReliabilityCalculator that performs tasks that are common for all
 * reliability calculators: initializes UserProjectReliabilityData instances, sets reliability before the projects and
 * reliability on the registration dates using the reliability after the projects calculated in
 * calculateReliabilityAfterProjects() method to be implemented by subclasses.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe. But it's assumed that configure()
 * method will be called just once right after instantiation, before calling any business methods (in this case this
 * class is used in thread safe manner).
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public abstract class BaseUserReliabilityCalculator implements UserReliabilityCalculator {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = BaseUserReliabilityCalculator.class.getName();

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is initialized in the configure() method and never changed after that. If is null, logging is not performed. Is
     * used in calculate(). Has a protected getter.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Creates an instance of BaseUserReliabilityCalculator.
     * </p>
     */
    protected BaseUserReliabilityCalculator() {
        // Empty
    }

    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if config is <code>null</code>.
     * @throws ReliabilityCalculatorConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        // Get logger
        log = Helper.getLog(config);
    }

    /**
     * <p>
     * Calculates reliability for all projects of specific user and project category.
     * </p>
     *
     * @param projects
     *            the project participation data for the user (must be in chronological order).
     *
     * @return the calculated project reliability data (not <code>null</code>, doesn't contain <code>null</code>).
     *
     * @throws IllegalArgumentException
     *             if projects is <code>null</code>/empty, contains <code>null</code> or contains an element with
     *             resolutionDate or userRegistrationDate is <code>null</code>.
     * @throws IllegalStateException
     *             if this calculator was not properly configured.
     * @throws UserReliabilityCalculationException
     *             if some error occurred when calculating the reliability rating.
     */
    public List<UserProjectReliabilityData> calculate(List<UserProjectParticipationData> projects)
        throws UserReliabilityCalculationException {
        Date enterTimestamp = new Date();
        String signature = getSignature("calculate(List<UserProjectParticipationData> projects)");

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projects"},
            new Object[] {projects});

        try {
            // Check argument
            Helper.checkNull(projects, "projects");
            if (projects.isEmpty()) {
                throw new IllegalArgumentException("'projects' should not be empty.");
            }

            for (UserProjectParticipationData project : projects) {
                Helper.checkNull(project, "element in projects");

                Helper.checkNull(project.getResolutionDate(), "project.getResolutionDate() in projects");
                Helper.checkNull(project.getUserRegistrationDate(), "project.getUserRegistrationDate() in projects");
            }

            int projectsSize = projects.size();
            int startIndex = 0;

            // Skip all initial projects that are not reliable for the user
            while ((startIndex < projectsSize) && (!projects.get(startIndex).isPassedReview())) {
                ++startIndex;
            }
            List<UserProjectParticipationData> countedProjects = projects.subList(startIndex, projectsSize);

            // The result reliability data
            List<UserProjectReliabilityData> result = getReliabilityData(countedProjects);

            // Calculate reliability after all projects
            calculateReliabilityAfterProjects(result);

            int resultSize = result.size();
            for (int i = 1; i < resultSize; i++) {
                // Get the next user project reliability data instance
                UserProjectReliabilityData userProjectReliabilityData = result.get(i);

                int j = i - 1;
                // Set "reliability before the project" to "reliability after the previous project"
                userProjectReliabilityData.setReliabilityBeforeResolution(
                    result.get(j).getReliabilityAfterResolution());

                Date userRegistrationDate = countedProjects.get(i).getUserRegistrationDate();
                while ((j >= 0) && result.get(j).getResolutionDate().after(userRegistrationDate)) {
                    // Skip all projects resolved after the registration date for this project
                    --j;
                }
                if (j >= 0) {
                    // Set the reliability after the found project
                    // as "reliability on registration" for the current project
                    userProjectReliabilityData.setReliabilityOnRegistration(
                        result.get(j).getReliabilityAfterResolution());
                }
            }

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);

            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (UserReliabilityCalculationException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                "UserReliabilityCalculationException is thrown when calculating reliability for all projects.");
        }
    }

    /**
     * <p>
     * Calculates the reliability after projects are resolved. This method should update
     * UserProjectParticipationData#reliabilityAfterResolution property for all elements in the input list.
     * </p>
     *
     * @param reliabilityData
     *            the list with user project reliability data.
     *
     * @throws IllegalArgumentException
     *             if reliabilityData is <code>null</code> or contains <code>null</code>.
     * @throws IllegalStateException
     *             if this calculator was not properly configured.
     * @throws UserReliabilityCalculationException
     *             if some error occurred when calculating the reliability rating.
     */
    protected abstract void calculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData)
        throws UserReliabilityCalculationException;

    /**
     * <p>
     * Ges the logger used by this class for logging errors and debug information.
     * </p>
     *
     * @return the logger used by this class for logging errors and debug information.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Gets the user project reliability data.
     * </p>
     *
     * @param countedProjects
     *            the counted project participation data for the user.
     *
     * @return the user project reliability data.
     */
    private static List<UserProjectReliabilityData> getReliabilityData(
        List<UserProjectParticipationData> countedProjects) {
        // Create a list for result reliability data
        List<UserProjectReliabilityData> result = new ArrayList<UserProjectReliabilityData>();
        for (UserProjectParticipationData countedProject : countedProjects) {
            // Create reliability data instance
            UserProjectReliabilityData reliabilityData = new UserProjectReliabilityData();

            // Set project ID to the reliability data
            reliabilityData.setProjectId(countedProject.getProjectId());
            // Copy user ID from participation data to reliability data
            reliabilityData.setUserId(countedProject.getUserId());
            // Copy passed review flag from participation data to reliability flag in the reliability data
            reliabilityData.setReliable(countedProject.isPassedReview());
            // Copy resolution date from participation data to reliability data
            reliabilityData.setResolutionDate(countedProject.getResolutionDate());

            result.add(reliabilityData);
        }

        return result;
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return CLASS_NAME + Helper.DOT + method;
    }
}
