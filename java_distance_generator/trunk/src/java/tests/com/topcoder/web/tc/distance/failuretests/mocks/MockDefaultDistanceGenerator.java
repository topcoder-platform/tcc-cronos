/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.failuretests.mocks;

import java.util.List;

import com.topcoder.web.tc.distance.DefaultDistanceGenerator;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceWeighting;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;

/**
 * <p>
 * Mock class for DefaultDistanceGenerator for the failure tests, to export the writeResult method for testing.
 * </p>
 *
 * @author ivern, TheCois
 * @version 1.0
 */
public class MockDefaultDistanceGenerator extends DefaultDistanceGenerator {
    public MockDefaultDistanceGenerator(MemberDataAccess memberDataAccess, DistanceWeighting distanceWeighting,
                                        List<DistanceCalculator> distanceCalculatorsList) {
        super(memberDataAccess, distanceWeighting, distanceCalculatorsList);
    }
    
    public String writeResult(Member fromMember, List<Member> members, List<Double> memberResults) {
        return super.writeResult(fromMember, members, memberResults);
    }
}
