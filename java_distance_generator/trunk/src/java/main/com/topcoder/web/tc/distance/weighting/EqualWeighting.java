/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.weighting;

import java.util.HashMap;

import com.topcoder.web.tc.distance.DistanceType;

/**
 * <p>
 * This implementation of the Distance Weighting will weight the passed distances using an equal weighting
 * algorithm. An equal weighting algorithm simply weights each distance type element equally.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator to weight the
 * results. The DistanceGenerator will then call this class with the distance types and their associate weight and
 * this implementation will return the calculated weight.
 * </p>
 * <p>
 * Thread-safety: this implementation is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class EqualWeighting extends WeightedAverageWeighting {
    /**
     * Creates an instance of this class using even weights. In other words, it will call its parent constructor
     * passing an empty map.
     */
    public EqualWeighting() {
        super(new HashMap<DistanceType, Integer>());
    }
}
