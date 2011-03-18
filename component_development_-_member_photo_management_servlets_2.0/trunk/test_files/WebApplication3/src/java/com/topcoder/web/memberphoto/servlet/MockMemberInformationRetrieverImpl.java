/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

/**
 * <p>
 * Mock class for <code>MemberInformationRetriever</code> interface.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMemberInformationRetrieverImpl implements
        MemberInformationRetriever {
    /**
     * <p>
     * Retrieve member information.
     * </p>
     * @param memberId the member id.
     * @return member information
     * @throws MemberInformationRetrievalException if error occurs
     */
    public MemberInformation getMemberInformation(long memberId)
        throws MemberInformationRetrievalException {
        if ((memberId == 0) || (memberId == 622)) {
            return new MemberInformation();
        } else if (memberId < 0) {
            throw new MemberInformationRetrievalException(
                    "MemberInformationRetrievalException occurs.");
        } else if (memberId == 888) {
            MemberInformation info = new MemberInformation();
            info.setName("</DATA>");

            return info;
        } else {
            return new MemberInformation("name", "handler", "email_address");
        }
    }
}
