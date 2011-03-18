/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.comparators;

import java.util.Date;

import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectParticipationDataComparator;

/**
 * <p>
 * This class is an implementation of UserProjectParticipationDataComparator that can be used for sorting
 * UserProjectParticipationData instances by their resolution dates (ascendingly). It two UserProjectParticipationData
 * instances have the same resolution date, they are compared using project ID properties to always provide consistent
 * sorting results.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe when UserProjectParticipationData
 * instances passed to it are used by the caller in thread safe manner.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class UserProjectParticipationDataResolutionDateBasedComparator implements
    UserProjectParticipationDataComparator {
    /**
     * <p>
     * Creates an instance of UserProjectParticipationDataResolutionDateBasedComparator.
     * </p>
     */
    public UserProjectParticipationDataResolutionDateBasedComparator() {
        // Empty
    }

    /**
     * <p>
     * Compares two UserProjectParticipationData instances by their resolution dates. If resolution dates are equal,
     * project IDs are taken into account.
     * </p>
     *
     * @param data1
     *            the first user project participation data to be compared.
     * @param data2
     *            the second user project participation data to be compared.
     *
     * @return <code>-1</code> if data1 &lt; data2; <code>1</code> if data1 &gt; data2; <code>0</code> if data1 ==
     *         data2.
     */
    public int compare(UserProjectParticipationData data1, UserProjectParticipationData data2) {
        if (data1 == data2) {
            return 0;
        }
        boolean data1Null = (data1 == null);
        if (data1Null || (data2 == null)) {
            return data1Null ? -1 : 1;
        }

        // Get resolution dates
        Date resolutionDate1 = data1.getResolutionDate();
        Date resolutionDate2 = data2.getResolutionDate();

        int result;

        boolean resolutionDate1Null = (resolutionDate1 == null);
        boolean resolutionDate2Null = (resolutionDate2 == null);
        if (resolutionDate1Null || resolutionDate2Null) {
            result = resolutionDate1Null ? (resolutionDate2Null ? 0 : -1) : 1;
        } else {
            result = resolutionDate1.compareTo(resolutionDate2);
        }

        if (result == 0) {
            // The resolution dates are equal, compare project IDs

            result = Long.valueOf(data1.getProjectId()).compareTo(data2.getProjectId());
        }
        return result;
    }
}
