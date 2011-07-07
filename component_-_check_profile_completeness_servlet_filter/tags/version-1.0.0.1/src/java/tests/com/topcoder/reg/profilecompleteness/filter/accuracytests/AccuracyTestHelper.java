/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;

import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.User;


/**
 * Helper class for accuracy tests.
 *
 * @author gets0ul
 * @version 1.0
 */
public final class AccuracyTestHelper {
    /**
     * Empty constructor.
     */
    private AccuracyTestHelper() {
    }

    /**
     * Creates a User instance with complete profile.
     *
     * @return a User instance with complete profile
     */
    public static User createUser() {
        User user = new User();
        user.setFirstName("first");
        user.setLastName("last");

        Address address = new Address();
        address.setAddress1("address1");
        address.setCity("city");

        Country country = new Country();
        country.setName("country");

        address.setCountry(country);

        user.addAddress(address);

        Phone phone = new Phone();
        phone.setNumber("0123456789");

        user.setPhoneNumbers(new HashSet<Phone>(Arrays.asList(phone)));

        return user;
    }

    /**
     * <p>
     * Gets the field value of given object by using reflection. If field with given name is not found in the obj'
     * class then the field is searched in obj' superclass.
     * </p>
     *
     * @param obj
     *            the object to get its field value.
     * @param fieldName
     *            the field name.
     * @return the field value.
     * @throws Exception
     *             if any error occurs while retrieving field value.
     */
    public static Object getField(Object obj, String fieldName) throws Exception {
        Field field = null;
        try {
            // get the class
            Class<?> cls = obj.getClass();
            try {
                // get the field
                field = cls.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // field is not found
                // check the superclass
                field = cls.getSuperclass().getDeclaredField(fieldName);
            }
            field.setAccessible(true);
            return field.get(obj);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }
}
