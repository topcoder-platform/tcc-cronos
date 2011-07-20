/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.ejb.user.UserPreference;
import com.topcoder.web.tc.Constants;

/**
 * This is a helper class that provides methods for checking whether the user's member card is unlocking and for
 * locking/unlocking this card.
 * <p>
 * Thread Safety:
 * This class is immutable and thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
class CardHelper {
    /**
     * Empty private constructor.
     */
    private CardHelper() {
    }

    /**
     * Checks whether the member card of the specified user is unlocked.
     *
     * @param userId the ID of the user
     * @return true if member card is unlocked, false otherwise
     * @throws IllegalArgumentException if userId is not positive
     * @throws TCWebException if any error occurred
     */
    static boolean isUnlocked(long userId) throws TCWebException {
        ParameterCheckUtility.checkGreaterThan(userId, 0, false, "userId");
        //Get user preference bean:
        UserPreference userPreference = getUserPreference();
        //Get card unlocked flag from the use preference
        try {
            String value = userPreference.getValue(userId, Constants.UNLOCK_CARD_PREFERENCE_ID,
                    DBMS.COMMON_OLTP_DATASOURCE_NAME);
            return Boolean.valueOf(value);
        } catch (RowNotFoundException e) {
            //ignore it and assume that value = "false"
            return false;
        } catch (RemoteException e) {
            throw new TCWebException("RemoteException occurs", e);
        }
    }

    /**
     * Sets the "unlocked" status of the member card of the specified user. I.e. locks or unlocks the member card.
     *
     * @param unlocked true if member card should be unlocked, false if member card should be locked
     * @param userId the ID of the user
     * @throws IllegalArgumentException if userId is not positive
     * @throws TCWebException if any error occurred
     */
    static void setUnlocked(long userId, boolean unlocked) throws TCWebException {
        ParameterCheckUtility.checkGreaterThan(userId, 0, false, "userId");
        //Get user preference bean:
        UserPreference userPreference = getUserPreference();
        //Get card unlocked flag from the use preference
        try {
            userPreference.createUserPreference(userId, Constants.UNLOCK_CARD_PREFERENCE_ID,
                    DBMS.COMMON_OLTP_DATASOURCE_NAME);
            userPreference.setValue(userId, Constants.UNLOCK_CARD_PREFERENCE_ID, String.valueOf(unlocked),
                    DBMS.COMMON_OLTP_DATASOURCE_NAME);
        } catch (RemoteException e) {
            throw new TCWebException("RemoteException occurs", e);
        }
    }

    /**
     * Retrieves the user preference bean.
     *
     * @throws TCWebException if any error occurred
     * @return the created user preference bean (not null)
     */
    private static UserPreference getUserPreference() throws TCWebException {
        //Invoke create method to create UserPreference EJB:
        UserPreference userPreference = null;
        try {
            //Create initial context
            InitialContext context = new InitialContext();
            // Lookup UserPreference home
            Object remoteHome = context.lookup(UserPreference.class.getName() + "Home");
            Method createMethod = PortableRemoteObject.narrow(remoteHome, remoteHome.getClass()).getClass()
                    .getMethod("create", new Class[0]);
            userPreference = (UserPreference) createMethod.invoke(remoteHome, new Object[0]);
        } catch (NamingException e) {
            throw new TCWebException("NamingException occurs when retrieve the user preference", e);
        } catch (SecurityException e) {
            throw new TCWebException("SecurityException occurs when retrieve the user preference", e);
        } catch (ClassCastException e) {
            throw new TCWebException("ClassCastException occurs when retrieve the user preference", e);
        } catch (NoSuchMethodException e) {
            throw new TCWebException("NoSuchMethodException occurs when retrieve the user preference", e);
        } catch (IllegalAccessException e) {
            throw new TCWebException("IllegalAccessException occurs when retrieve the user preference", e);
        } catch (InvocationTargetException e) {
            throw new TCWebException("InvocationTargetException occurs when retrieve the user preference", e);
        }
        if (userPreference == null) {
            throw new TCWebException("Unable to retrieve the user preference");
        }
        return userPreference;
    }
}
