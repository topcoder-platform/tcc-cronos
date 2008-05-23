package com.topcoder.web.tc.distance;

import java.util.EnumSet;

/**
 * This is the simplest generation interface required for the component.  The component
 * will generate the XML data required to feed a user interface to render the distances.
 *
 * @author AdamSelene
 *
 */
public interface DistanceGenerator {

    /**
     * Generates a distance XML as specified by the required XSD.
     * The coder_id requested must be the first element in the XML,
     * following elements need not be ordered.
     *
     * @param coder_id The coder to generate distances from.
     * @param distanceType The distances requested to generate.
     * @param compType The competition types to generate.
     * @return An XML document conforming to the component XSD, containing first
     * the coder requested, followed by related coders with calculated distances.
     */
    String generateDistance(long coder_id, EnumSet<DistanceType> distanceTypes, EnumSet<CompetitionType> compTypes);

}
