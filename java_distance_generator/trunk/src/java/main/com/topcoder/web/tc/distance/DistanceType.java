package com.topcoder.web.tc.distance;

/**
 * This enumeration contains the basic distances between users.
 *
 * @author AdamSelene
 */
public enum DistanceType {
    /**
     * The number of competitions shared between members.  Lower
     * overlap indicates more distance.
     */
    OVERLAP,
    /**
     * The geographical distance between members.  Larger geographical
     * distances indicates larger distance between two members.
     */
    GEOGRAPHICAL,
    /**
     * The absolute mathematical distance between two members.
     */
    RATING
}
