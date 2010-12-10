/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.detectors;

import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.Helper;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.impl.ResolutionDateDetector;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ResolutionDateDetector that uses phase end times to detect the resolution dates.
 * If a user registered but did not submit, the resolution date is the end of the submission phase. If a user
 * submitted but did not pass screening, the resolution date is the end of the screening phase. If a user passed
 * screening, the resolution date is the end of the appeals response phase (regardless of whether the member passed
 * review or not).
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
public class PhaseEndTimeBasedResolutionDateDetector implements ResolutionDateDetector {
    /**
     * <p>
     * Represents the submission phase is finished.
     * </p>
     */
    private static final int SUB_PHASE_FINISHED = 3;

    /**
     * <p>
     * Represents the submission failed screening.
     * </p>
     */
    private static final int SUB_FAILED_SCREENING = 2;

    /**
     * <p>
     * Represents the submission failed review.
     * </p>
     */
    private static final int SUB_FAILED_REVIEW = 3;

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = PhaseEndTimeBasedResolutionDateDetector.class.getName();

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is initialized in the configure() method and never changed after that. If is null, logging is not performed. Is
     * used in detect().
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Creates an instance of PhaseEndTimeBasedResolutionDateDetector.
     * </p>
     */
    public PhaseEndTimeBasedResolutionDateDetector() {
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
     * Detects the reliability resolution date for the specified project. The result is stored in resolutionDate
     * property of the input UserProjectParticipationData instance. If the resolution date cannot be detected at this
     * point of time, <code>null</code> is set to resolutionDate property.
     * </p>
     *
     * @param data
     *            the user project participation data for which resolution date must be detected.
     *
     * @throws IllegalArgumentException
     *             if data is <code>null</code>.
     */
    public void detect(UserProjectParticipationData data) {
        Date enterTimestamp = new Date();
        String signature = getSignature("detect(UserProjectParticipationData data)");

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"data"},
            new Object[] {data});

        try {
            Helper.checkNull(data, "data");

            // Get ID of the submission phase status
            long submissionPhaseStatusId = data.getSubmissionPhaseStatusId();
            // Get ID of the submission status
            Long submissionStatusId = data.getSubmissionStatusId();

            Date resolutionDate = null;

            if (submissionPhaseStatusId == SUB_PHASE_FINISHED) {
                // submission phase is finished

                if (submissionStatusId == null) {
                    // submission phase finished, but there is no submission

                    // Use submission phase end date as resolution date
                    resolutionDate = data.getSubmissionPhaseEnd();
                } else if (submissionStatusId == SUB_FAILED_SCREENING) {
                    // submission failed screening

                    // Use screening phase end date as resolution date
                    resolutionDate = data.getScreeningPhaseEnd();
                } else if ((submissionStatusId == SUB_FAILED_REVIEW) || data.isPassedReview()) {
                    // submission failed review or passed review

                    // Use appeals response end date as resolution date
                    resolutionDate = data.getAppealsResponsePhaseEnd();
                }
            }

            // Set the detected resolution date to the participation data
            data.setResolutionDate(resolutionDate);

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        }
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
