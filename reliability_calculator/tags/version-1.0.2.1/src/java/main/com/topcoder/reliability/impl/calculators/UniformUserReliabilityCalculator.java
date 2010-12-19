/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.calculators;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.Helper;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.impl.UserProjectReliabilityData;

/**
 * <p>
 * This class is an implementation of UserReliabilityCalculator that uses information about N (or less if not enough
 * exist yet) recent projects to calculate the reliability. The reliability calculation formula is A/B; here B is the
 * number of recent projects taken into account (i.e. N or less), and A - the number of reliable projects among ones
 * taken into account. I.e. impact of the recent projects on the reliability rating is distributed uniformly in this
 * calculator.
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
public class UniformUserReliabilityCalculator extends BaseUserReliabilityCalculator {
    /**
     * <p>
     * Represents the property key 'historyLength'.
     * </p>
     */
    private static final String KEY_HISTORY_LEN = "historyLength";

    /**
     * <p>
     * The history length that represents the number of recent resolved projects that affect the user's reliability.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateReliabilityAfterProjects().
     * </p>
     */
    private int historyLength;

    /**
     * <p>
     * Creates an instance of UniformUserReliabilityCalculator.
     * </p>
     */
    public UniformUserReliabilityCalculator() {
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
    @Override
    public void configure(ConfigurationObject config) {
        // Call super
        super.configure(config);

        historyLength = getPropertyPositive(config, KEY_HISTORY_LEN);
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
     */
    @Override
    protected void calculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData) {
        Helper.checkList(reliabilityData, "reliabilityData");

        Helper.checkState(historyLength == 0, "This calculator was not properly configured.");

        int reliabilityDataSize = reliabilityData.size();

        for (int i = 0; i < reliabilityDataSize; i++) {
            // Get the next user project reliability data instance
            UserProjectReliabilityData reliabilityProjectData = reliabilityData.get(i);

            // Get index of the first project that affects the reliability
            int start = Math.max(i - historyLength + 1, 0);
            // The total number of projects that counts towards the reliability
            double totalNum = i - start + 1;
            // The number of reliable projects among them
            int reliableNum = 0;

            for (int j = start; j <= i; j++) {
                // Get the next reliability data
                UserProjectReliabilityData curReliabilityProjectData = reliabilityData.get(j);

                // Check if this project was reliable
                if (curReliabilityProjectData.isReliable()) {
                    ++reliableNum;
                }
            }

            // Set reliability after resolution to the reliability data
            reliabilityProjectData.setReliabilityAfterResolution(reliableNum / totalNum);
        }
    }

    /**
     * <p>
     * Gets a required positive integer value from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     *
     * @return the retrieved property value.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if the property is missing, not a positive integer or some error occurred.
     */
    private static int getPropertyPositive(ConfigurationObject config, String key) {
        // Get the value
        String valueStr = Helper.getProperty(config, key, true);

        try {
            int value = Integer.parseInt(valueStr);

            if (value <= 0) {
                throw new ReliabilityCalculatorConfigurationException("The property '" + key
                    + "' should be a positive integer.");
            }

            return value;
        } catch (NumberFormatException e) {
            throw new ReliabilityCalculatorConfigurationException("The property '" + key
                + "' should be an positive integer.", e);
        }
    }
}
