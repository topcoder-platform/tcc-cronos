/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import com.topcoder.web.memberphoto.servlet.MemberInformation;
import com.topcoder.web.memberphoto.servlet.MemberInformationRetrievalException;
import com.topcoder.web.memberphoto.servlet.MemberInformationRetriever;

/**
 * <p>
 * Mock implementation for <code>MemberInformationRetriever</code> interface.
 * </p>
 * Only used in accuracy test.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMemberInformationRetriever implements MemberInformationRetriever {

    /**
     * Retrieves the member information.
     * @param memberId the member id
     * @return the member information
     * @throws MemberInformationRetrievalException if error occurs
     */
    public MemberInformation getMemberInformation(long memberId) throws MemberInformationRetrievalException {
        if (memberId < 0) {
            throw new MemberInformationRetrievalException("Invalid member id.");
        }
        return new MemberInformation("jivern", "ivern", "ivern@topcoder.com");
    }
}