package com.topcoder.web.tc.distance.data;

import java.util.EnumSet;
import java.util.List;

import com.topcoder.web.tc.distance.CompetitionType;

/**
 * The data access interface for retrieving member data.
 *
 * @author AdamSelene
 */
public interface MemberDataAccess {

    /**
     * Retrieves a member by their coder_id.  This method is intended
     * to provide the details for the member from which distances are
     * calculated.
     * @param id The identifier for the member desired.
     * @return A Member instance with all data populated.
     * @throws MemberDataAcesssException if there is any error retrieving member data.
     */
    Member getMember(long id) throws MemberDataAccessException;

    /**
     * Retrieves members related to a specified member.  The number of
     * members returned will vary based on the input to the method, as
     * well as the particular data retrieval implementation.
     * @param id The member of interest.
     * @param competitionTypes The competition types to filter the data.
     * @return An unordered list of members related in some way
     * to the requested member.  The set selected will vary by the
     * implementation of the interface.
     * @throws MemberDataAcesssException if there is any error retrieving member data.
     */
    List<Member> getRelatedMembers(long id,
            EnumSet<CompetitionType> competitionTypes) throws MemberDataAccessException;
}
