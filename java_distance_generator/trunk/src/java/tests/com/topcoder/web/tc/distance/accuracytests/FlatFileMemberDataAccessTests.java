package com.topcoder.web.tc.distance.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;

public class FlatFileMemberDataAccessTests extends TestCase {

    private MemberDataAccess dataAccess = null;

    protected void setUp() {
        dataAccess = TestHelper.getTestDataAccess();
    }

    public void testGetMember() {
        for (int i = 0; i < 1; i++) {
            Member member = dataAccess.getMember(TestHelper.MEMBER_IDS[i]);
            assertNotNull("Member should not be null", member);
            assertEquals("Countries should be equal: " + member,
                    TestHelper.MEMBER_COUNTRIES[i], member.getCountry());
            assertEquals("Handle should be equal",
                    TestHelper.MEMBER_HANDLES[i], member.getHandle());

            assertEquals("Algorithm rating should be equal",
                    TestHelper.MEMBER_ALGO_RATINGS[i], member
                            .getRating(CompetitionType.ALGORITHM));
            assertEquals("Dev rating should be equal",
                    TestHelper.MEMBER_DEV_RATINGS[i], member
                            .getRating(CompetitionType.DEVELOPMENT));

            assertEquals("Design ratings should be equal",
                    TestHelper.MEMBER_DESIGN_RATINGS[i], member
                            .getRating(CompetitionType.DESIGN));

            assertEquals("Geo should be equal",
                    TestHelper.MEMBER_GEO[i], member
                            .getGeographicalDistance());

        }
        List<Member> members = dataAccess.getRelatedMembers(TestHelper.MEMBER_IDS[0], null);
        for (int i = 1; i < 5; i++) {
            Member member = members.get(i - 1);
            assertNotNull("Member should not be null", member);
            assertEquals("Countries should be equal",
                    TestHelper.MEMBER_COUNTRIES[i], member.getCountry());
            assertEquals("Handle should be equal",
                    TestHelper.MEMBER_HANDLES[i], member.getHandle());

            assertEquals("Algorithm rating should be equal",
                    TestHelper.MEMBER_ALGO_RATINGS[i], member
                            .getRating(CompetitionType.ALGORITHM));
            assertEquals("Dev rating should be equal",
                    TestHelper.MEMBER_DEV_RATINGS[i], member
                            .getRating(CompetitionType.DEVELOPMENT));

            assertEquals("Design ratings should be equal",
                    TestHelper.MEMBER_DESIGN_RATINGS[i], member
                            .getRating(CompetitionType.DESIGN));

            assertEquals("Geo should be equal",
                    TestHelper.MEMBER_GEO[i], member
                            .getGeographicalDistance());

        }
    }
}
