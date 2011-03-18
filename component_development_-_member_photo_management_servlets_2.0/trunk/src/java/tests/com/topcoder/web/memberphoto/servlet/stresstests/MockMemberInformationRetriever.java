/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.stresstests;

import com.topcoder.web.memberphoto.servlet.MemberInformationRetriever;
import com.topcoder.web.memberphoto.servlet.MemberInformationRetrievalException;
import com.topcoder.web.memberphoto.servlet.MemberInformation;

/**
 * Mock for MemberInformationRetriever interface.
 * @author orange_cloud
 * @version 1.0
 */
public class MockMemberInformationRetriever implements MemberInformationRetriever {
    /**
     * Retrieve the member information for the given member id. Same object returned each time.
     * @param memberId the member id.
     * @return the member information.
     * @throws MemberInformationRetrievalException
     *             if fail to retrieve the member information.
     */
    public MemberInformation getMemberInformation(long memberId) throws MemberInformationRetrievalException {
        return new MemberInformation("some_name", "some_handle", "some@email.com");
    }
}
