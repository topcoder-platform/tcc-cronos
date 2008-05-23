/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.data.MemberDataAccess;
import com.topcoder.web.tc.distance.weighting.WeightedAverageWeighting;

import junit.framework.TestCase;

/**
 * <p>
 * This class contains a demo of the component usage.
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * Loads tomek's data: coder_id = 144400 and print the xml output.
     */
    public void testDemo() {
        // Get the member data access implementations
        MemberDataAccess mda = new FlatFileMemberDataAccess("test_files/");

        // Create a weighted average weighting scheme to weight
        // OVERLAP distance by 20%, the GEOGRAPHICAL of 30%, and the RATING 50%
        Map<DistanceType, Integer> weighting = new HashMap<DistanceType, Integer>();
        weighting.put(DistanceType.OVERLAP, 20);
        weighting.put(DistanceType.GEOGRAPHICAL, 30);
        weighting.put(DistanceType.RATING, 50);

        DistanceWeighting dw = new WeightedAverageWeighting(weighting);

        // Create the various distance calculators
        List<DistanceCalculator> dcs = new ArrayList<DistanceCalculator>();
        dcs.add(new RatingDistanceCalculator());
        dcs.add(new MatchOverlapDistanceCalculator());
        dcs.add(new GeographicalDistanceCalculator());

        // Create the distance generator
        DistanceGenerator dg = new DefaultDistanceGenerator(mda, dw, dcs);

        // Let's calculate distances on all of them
        EnumSet<DistanceType> dtes = EnumSet.of(DistanceType.OVERLAP, DistanceType.GEOGRAPHICAL, DistanceType.RATING);

        // Let's calculate for only Design and Development
        EnumSet<CompetitionType> ctes = EnumSet.of(CompetitionType.DESIGN, CompetitionType.DEVELOPMENT);

        // Get the distances for coder_id 144400
        String xml = dg.generateDistance(144400, dtes, ctes);

        System.out.println("----- Demo Result -----");

        // Print out the results
        System.out.println(xml);
    }
}
