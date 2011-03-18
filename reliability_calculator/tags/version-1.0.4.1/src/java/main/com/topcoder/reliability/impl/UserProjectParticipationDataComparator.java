/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.util.Comparator;

/**
 * <p>
 * This interface simply extends Comparator&lt;UserProjectParticipationData&gt; and doesn't define any new methods. It
 * is used by ReliabilityCalculatorImpl for sorting UserProjectParticipationData instances.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface UserProjectParticipationDataComparator extends Comparator<UserProjectParticipationData> {
    // Empty
}
