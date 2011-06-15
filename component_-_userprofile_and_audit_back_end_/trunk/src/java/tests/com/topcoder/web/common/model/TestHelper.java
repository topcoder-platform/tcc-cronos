/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.shared.util.DBMS;

/**
 * A Helper class for Unit testing.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * A Helper method to construct a UserProfile object with some details.
     * @return the constructed UserProfile object
     */
    public static UserProfile getUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("first");
        userProfile.setMiddleName("middle");
        userProfile.setLastName("last");
        userProfile.setId(100l);
        return userProfile;
    }

    /**
     * A Helper method to construct a set of Address objects.
     * @return set of Address objects
     */
    public static Set < Address > getAddresses() {
        Set < Address > addresses = new HashSet < Address >();

        Address address1 = new Address();
        address1.setAddressTypeId(Address.HOME_TYPE_ID);
        addresses.add(address1);

        Address address2 = new Address();
        address2.setAddressTypeId(Address.BILLING_TYPE_ID);
        addresses.add(address2);

        return addresses;
    }

    /**
     * A Helper method to construct a set of Phone objects.
     * @return a set of Phone objects
     */
    public static Set < Phone > getPhoneNumbers() {
        Set < Phone > phones = new HashSet < Phone >();
        phones.add(getPhone(true));
        phones.add(getPhone(false));
        return phones;
    }

    /**
     * A Helper method to construct a phone object.
     * @param primary
     *            if the phone number is primary
     * @return the constructed Phone object
     */
    private static Phone getPhone(boolean primary) {
        Phone phone = new Phone();
        phone.setPrimary(primary);
        return phone;
    }

    /**
     * A helper method to delete user with given id.
     * @param id
     *            the id of user to be deleted
     * @throws Exception
     *             to JUnit
     */
    public static void deleteUser(long id) throws Exception {
        Connection conn = DBMS.getConnection("userds");
        PreparedStatement ps = conn.prepareStatement("Delete from user where user_id = ?");
        ps.setLong(1, id);
        ps.executeUpdate();
    }
}
