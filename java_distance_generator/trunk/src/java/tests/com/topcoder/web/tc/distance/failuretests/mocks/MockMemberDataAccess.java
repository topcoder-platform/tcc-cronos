/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.failuretests.mocks;

import java.util.EnumSet;
import java.util.List;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;
import com.topcoder.web.tc.distance.data.MemberDataAccessException;

/**
 * <p>
 * Mock class for MemberDataAccess for the failure tests, to throw exceptions needed for testing.
 * </p>
 *
 * @author ivern, TheCois
 * @version 1.0
 */
public class MockMemberDataAccess implements MemberDataAccess {
    public Member getMember(long id) throws MemberDataAccessException {
        throw new MemberDataAccessException();
    }
    
    public List<Member> getRelatedMembers(long id,	EnumSet<CompetitionType> competitionTypes)
            throws MemberDataAccessException {
        throw new MemberDataAccessException();
    }
}
