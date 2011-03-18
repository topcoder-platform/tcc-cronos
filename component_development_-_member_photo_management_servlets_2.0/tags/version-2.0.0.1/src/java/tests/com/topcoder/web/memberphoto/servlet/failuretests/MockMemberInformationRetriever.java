/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import com.topcoder.web.memberphoto.servlet.MemberInformation;
import com.topcoder.web.memberphoto.servlet.MemberInformationRetrievalException;
import com.topcoder.web.memberphoto.servlet.MemberInformationRetriever;

/**
 * Mock implementation of MemberInformationRetriever interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMemberInformationRetriever implements MemberInformationRetriever {
    /**
     * Default constructor.
     */
    public MockMemberInformationRetriever() {
    }

    /**
     * Mock implementation.
     *
     * @param memberId
     *            the member id.
     * @return MemberInformation the MemberInformation instance.
     *
     * @throws MemberInformationRetrievalException
     *             if memberId &gt; 201.
     */
    public MemberInformation getMemberInformation(long memberId) throws MemberInformationRetrievalException {
        if (memberId >= 201) {
            throw new MemberInformationRetrievalException("");
        }
        return new MemberInformation();
    }
}