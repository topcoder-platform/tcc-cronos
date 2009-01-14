/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Helper;

import com.topcoder.predictor.analysis.SituationGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * <p>
 * A base class for generators of ComponentCompetitionSituation. It provides the place holder for the generated
 * situations, and the access to the iterator. It implements the SituationGenerator interface.
 * </p>
 *
 * <p>
 * This base class is available to future generators of ComponentCompetitionSituations.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseComponentCompetitionSituationGenerator implements
                SituationGenerator<ComponentCompetitionSituation> {
    /**
     * Represents the list of generated component competition situations. It is set in the constructor. It is accessed
     * as an iterator via the iterator method. It will have 0 to many non-null values. Once set, it will never change.
     */
    private final List<ComponentCompetitionSituation> generatedSituations;

    /**
     * Constructor with no args.
     */
    public BaseComponentCompetitionSituationGenerator() {
        this.generatedSituations = Collections.unmodifiableList(new ArrayList<ComponentCompetitionSituation>());
    }

    /**
     * Constructor accepting the generated situations.
     *
     * @param generatedSituations
     *            the generated situations
     * @throws IllegalArgumentException
     *             If generatedSituations is null or contains null elements, or it is empty
     */
    protected BaseComponentCompetitionSituationGenerator(List<ComponentCompetitionSituation> generatedSituations) {
        // it can not be empty, see http://forums.topcoder.com/?module=Thread&threadID=625584&start=0
        Helper.checkNotNullNotEmpty(generatedSituations, "generatedSituations");
        Helper.checkList(generatedSituations, "generatedSituations");
        this.generatedSituations = Collections.unmodifiableList(new ArrayList<ComponentCompetitionSituation>(
                        generatedSituations));
    }

    /**
     * Returns an iterator over a set of the generated situations. The iterator will be unmodifiable.
     *
     * @return the iterator of the generated situations
     */
    public Iterator<ComponentCompetitionSituation> iterator() {
        return generatedSituations.iterator();
    }
}
