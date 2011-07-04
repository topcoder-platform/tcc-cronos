/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.User;

/**
 * Helper class used in testing.
 *
 * @author nowind_lee
 * @version 1.0
 */
public final class TestHelper {

    /**
     * Private empty constructor, to prevent creating instance of this class.
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * Get the value of a field (may be private).
     *
     * @param obj
     *            the object holds the field
     * @param fieldName
     *            the field name
     * @return the field value
     * @throws SecurityException
     *             if a security manager stops me to get the field
     * @throws NoSuchFieldException
     *             if the field doesn't exist
     * @throws IllegalArgumentException
     *             if the specified object is not an instance of the class or interface declaring
     *             the underlying field (or a subclass or implementor thereof).
     * @throws IllegalAccessException
     *             if we can't access the field
     */
    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException,
        IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * Set the value of a field (may be private).
     *
     * @param obj
     *            the object holds the field
     * @param fieldName
     *            the field name
     * @param value
     *            the new value
     * @throws SecurityException
     *             if a security manager stops me to get the field
     * @throws NoSuchFieldException
     *             if the field doesn't exist
     * @throws IllegalArgumentException
     *             if the specified object is not an instance of the class or interface declaring
     *             the underlying field (or a subclass or implementor thereof).
     * @throws IllegalAccessException
     *             if we can't access the field
     */
    public static void setField(Object obj, String fieldName, Object value)
        throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * Construct a set with specified items.
     *
     * @param <T>
     *            the type of items
     * @param items
     *            the specified items
     * @return a set
     */
    public static <T> Set<T> toSet(T... items) {
        Set<T> set = new HashSet<T>();
        for (T item : items) {
            set.add(item);
        }
        return set;
    }

    /**
     * Create an instance of User which has basic complete information.
     *
     * @return an instance of User which has basic complete information
     */
    public static User createBasicCompleteUser() {
        User user = new User();
        user.setFirstName("first");
        user.setLastName("last");

        Phone phone = new Phone();
        phone.setNumber("123456789");
        user.setPhoneNumbers(toSet(phone));

        Address address = new Address();
        address.setAddress1("address1");
        address.setCity("city");

        Country country = new Country();
        country.setName("US");
        address.setCountry(country);

        user.setAddresses(toSet(address));
        return user;
    }

}
