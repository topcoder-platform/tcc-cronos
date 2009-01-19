/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import java.util.List;

/**
 * <p>
 * This class extends BaseComponentCompetitionSituationGenerator and is used to test
 * BaseComponentCompetitionSituationGenerator.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseComponentCompetitionSituationGeneratorTester extends
                BaseComponentCompetitionSituationGenerator {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -1039904071806401672L;

    /**
     * Constructor accepting the generated situations.
     *
     * @param generatedSituations
     *            the generated situations
     * @throws IllegalArgumentException
     *             If generatedSituations is null or contains null elements, or it is empty
     */
    public BaseComponentCompetitionSituationGeneratorTester(List<ComponentCompetitionSituation> generatedSituations) {
        super(generatedSituations);
    }

}
