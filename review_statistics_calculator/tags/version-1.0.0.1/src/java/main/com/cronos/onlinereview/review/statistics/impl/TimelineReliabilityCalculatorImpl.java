/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.TimelineReliabilityCalculator;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of TimelineReliabilityCalculator that uses configurable "penalty per hour" rates
 * for phase types and subtracts the penalty from the maximum timeline reliability coefficient equal to 1.
 * </p>
 *
 * <p>
 * Additionally this calculator allows setting the minimum limit for the calculated coefficient value. This class uses
 * Logging Wrapper logger to perform logging of errors and debug information.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread safe assuming that configure() method will be called just
 * once right after instantiation, before calling calculateReliability() method. The Log instance used by this class
 * is thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class TimelineReliabilityCalculatorImpl extends BaseCalculator implements TimelineReliabilityCalculator {
    /**
     * Class name.
     */
    private static final String CLASS_NAME = TimelineReliabilityCalculatorImpl.class.getName();

    /**
     * Configuration parameter name prefix.
     */
    private static final String PARAM_NAME = "penaltyPerHourForPhaseType";

    /**
     * Constant to convert to hour.
     */
    private static final double HOUR = 3600000.0;

    /**
     * <p>
     * The mapping from phase type ID to the penalty per hour for phases of this type. This penalty represents the
     * value deducted from the timeline reliability coefficient when reviewer's delay equals to 1 hour (accuracy of
     * the calculation is 1 millisecond).
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null, cannot contain null/not
     * positive key or null/negative value after initialization. Is used in calculateReliability().
     * </p>
     */
    private Map<Long, Double> penaltyPerHourByPhaseType;

    /**
     * <p>
     * The penalty per hour for all phase types not mentioned in penaltyPerHourByPhaseType mapping.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be negative after initialization. Is
     * used in calculateReliability().
     * </p>
     */
    private double defaultPenaltyPerHour = 0.0;

    /**
     * Creates an instance of TimelineReliabilityCalculatorImpl.
     */
    public TimelineReliabilityCalculatorImpl() {
        // does nothing
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * <pre>
     * Configuration example:
     * &lt;Property name="timelineReliabilityCalculatorConfig"&gt;
     *    &lt;Property name="loggerName"&gt;
     *       &lt;Value&gt;myLogger&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="minimumCoefficient"&gt;
     *       &lt;Value&gt;0.5&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="defaultPenaltyPerHour"&gt;
     *       &lt;Value&gt;0.02&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="penaltyPerHourForPhaseType3"&gt;
     *       &lt;Value&gt;0.04&lt;/Value&gt;
     *    &lt;/Property&gt;
     * &lt;/Property&gt;
     * </pre>
     *
     * @param config the configuration object
     *
     * @throws IllegalArgumentException if configuration object is null
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    public void configure(ConfigurationObject config) {
        super.configure(config);

        penaltyPerHourByPhaseType = new HashMap<Long, Double>();
        try {
            String[] configParamNames = config.getAllPropertyKeys();
            for (String configParamName : configParamNames) {
                // Check if property is of Phase Type
                if (configParamName.startsWith(PARAM_NAME)) {
                    // Gets the phase type id
                    long phaseTypeId = Long.parseLong(configParamName.substring(PARAM_NAME.length()));
                    Helper.checkNegative(phaseTypeId, "phaseTypeId");

                    // Parse the property value
                    double penaltyPerHour = Double.parseDouble((String) config.getPropertyValue(configParamName));
                    Helper.checkNegative(penaltyPerHour, "penaltyPerHour");

                    penaltyPerHourByPhaseType.put(phaseTypeId, penaltyPerHour);
                }
            }

            // Gets the default property value. If not present 0 is used.
            String penaltyPerHour = (String) config.getPropertyValue("defaultPenaltyPerHour");
            if (penaltyPerHour != null) {
                defaultPenaltyPerHour = Double.parseDouble(penaltyPerHour);
                Helper.checkNegative(defaultPenaltyPerHour, "defaultPenaltyPerHour");
            }
        } catch (ClassCastException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of TimelineReliabilityCalculatorImpl class.", e);
        } catch (IllegalArgumentException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of TimelineReliabilityCalculatorImpl class.", e);
        } catch (ConfigurationAccessException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of TimelineReliabilityCalculatorImpl class.", e);
        }
    }

    /**
     * Calculates the timeline reliability coefficient for one reviewer in specific contest. Elements with the same
     * indices in the input arrays correspond to the same phase.
     *
     * @param phaseTypeIds the IDs of the phase types
     * @param deadlines the deadlines for phases in which reviewer had to submit a required deliverable
     * @param committed the committed dates for the corresponding reviewer deliverables
     *
     * @return the calculated reliability coefficient (in the range [0 .. 1])
     *
     * @throws IllegalArgumentException if any argument is null, any two arguments have different number of elements,
     *             deadlines or committed contains null, phaseTypeIds contains not positive element
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     *             (penaltyPerHourByPhaseType is null)
     */
    public double calculateReliability(Date[] deadlines, Date[] committed, long[] phaseTypeIds) {
        Date start = new Date();
        String signature = CLASS_NAME
            + ".calculateReliability(Date[] deadlines, Date[] committed, long[] phaseTypeIds)";
        Log log = getLog();

        Helper.checkArray(log, signature, deadlines, "deadlines", true);
        Helper.checkArray(log, signature, committed, "committed", true);
        Helper.checkNull(log, signature, phaseTypeIds, "phaseTypeIds");
        for (long id : phaseTypeIds) {
            Helper.checkPositive(log, signature, id, "phaseTypeIds");
        }

        // Log entrance
        Helper.logEntrance(log, signature, new String[] {"deadlines", "committed", "phaseTypeIds"}, new Object[] {
            deadlines, committed, phaseTypeIds});

        Helper.checkState(log, signature, penaltyPerHourByPhaseType, "penaltyPerHourByPhaseType");

        if ((deadlines.length != committed.length) || (deadlines.length != phaseTypeIds.length)
            || (committed.length != phaseTypeIds.length)) {
            throw Helper.logException(log, signature, new IllegalArgumentException("Arguments size are different."),
                "Arguments size are different.");
        }

        double totalPenalty = 0;
        for (int i = 0; i < deadlines.length; i++) {
            // Calculate the delay for this phase in hours:
            double curDelayInHours = (committed[i].getTime() - deadlines[i].getTime()) / HOUR;

            if (curDelayInHours <= 0) {
                continue; // there is no delay
            }

            double penaltyPerHour;
            if (penaltyPerHourByPhaseType.containsKey(phaseTypeIds[i])) {
                // Get "penalty per hour" rate for this phase:
                penaltyPerHour = penaltyPerHourByPhaseType.get(phaseTypeIds[i]);
            } else {
                penaltyPerHour = defaultPenaltyPerHour;
            }
            // Calculate the penalty for this phase add it to the sum:
            totalPenalty += penaltyPerHour * curDelayInHours;
        }

        if (totalPenalty == 0) {
            return 1;
        }

        double timelineReliability = 1 - totalPenalty;

        double minimumCoefficient = getMinimumCoefficient();
        timelineReliability = (timelineReliability < minimumCoefficient) ? minimumCoefficient : timelineReliability;

        Helper.logExit(log, signature, new Object[] {timelineReliability}, start);

        return timelineReliability;
    }
}
