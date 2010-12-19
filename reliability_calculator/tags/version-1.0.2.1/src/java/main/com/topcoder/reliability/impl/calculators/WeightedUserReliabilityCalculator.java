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
 * exist yet) recent projects to calculate the reliability. The reliability calculation formula is
 * SUM(reliable_project_weights)/SUM(all_project_weights); here weights are configurable and are specified based on
 * the chronological order of the projects: i.e. weight for the most recent project, weight for the last but one
 * project, etc. The expected usage of this class is to use higher weight for newer projects and lower weights for
 * older projects.
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
public class WeightedUserReliabilityCalculator extends BaseUserReliabilityCalculator {
    /**
     * <p>
     * Represents the property key 'weights'.
     * </p>
     */
    private static final String KEY_WEIGHTS = "weights";

    /**
     * <p>
     * The weights for the recent resolved projects that affect the user's reliability. The last element in the array
     * corresponds to the most recently resolved project.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null or empty after
     * initialization. All elements must be positive. Is used in calculateReliabilityAfterProjects().
     * </p>
     */
    private double[] weights;

    /**
     * <p>
     * Creates an instance of WeightedUserReliabilityCalculator.
     * </p>
     */
    public WeightedUserReliabilityCalculator() {
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

        String[] weightsStr = Helper.getPropertyValues(config, KEY_WEIGHTS);
        int weightsSize = weightsStr.length;
        weights = new double[weightsSize];
        for (int i = 0; i < weightsSize; i++) {
            try {
                weights[i] = Double.parseDouble(weightsStr[i]);

                if (weights[i] <= 0) {
                    throw new ReliabilityCalculatorConfigurationException(
                        "The property 'weights' should contain positive double only.");
                }
            } catch (NumberFormatException e) {
                throw new ReliabilityCalculatorConfigurationException(
                    "The property 'weights' should contain positive double only.", e);
            }
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
     */
    @Override
    protected void calculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData) {
        Helper.checkList(reliabilityData, "reliabilityData");

        Helper.checkState(weights == null, "This calculator was not properly configured.");

        int reliabilityDataSize = reliabilityData.size();
        for (int i = 0; i < reliabilityDataSize; i++) {
            // Get the next user project reliability data instance
            UserProjectReliabilityData reliabilityProjectData = reliabilityData.get(i);

            int startIndex = i - weights.length + 1;

            // Get index of the first project that affects the reliability
            int start = Math.max(startIndex, 0);
            // The total sum of weights of projects that affect reliability
            double totalSum = 0;
            // The sum of weights of reliable projects only
            double reliableSum = 0;

            for (int j = start; j <= i; j++) {
                // Get the next reliability data
                UserProjectReliabilityData curReliabilityProjectData = reliabilityData.get(j);
                // Get the weight of this project
                double weight = weights[j - startIndex];

                if (curReliabilityProjectData.isReliable()) {
                    reliableSum += weight;
                }

                totalSum += weight;
            }

            // Set reliability after resolution to the reliability data
            reliabilityProjectData.setReliabilityAfterResolution(reliableSum / totalSum);
        }
    }
}
