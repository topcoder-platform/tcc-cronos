/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.user.manager.Profile;
import com.topcoder.user.manager.Attribute;

import javax.ejb.SessionContext;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.TimerService;
import javax.xml.rpc.handler.MessageContext;
import javax.transaction.UserTransaction;
import java.util.Properties;
import java.security.Identity;
import java.security.Principal;

/**
 * The mock SessionContext.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSessionContext implements SessionContext {
    /**
     * is admin or not.
     */
    public static boolean isAdmin;

    /**
     * user id.
     */
    public static long userId;

    /**
     * default ctor.
     */
    public MockSessionContext() {
        isAdmin = true;
        userId = 33;
    }

    /**
     * get EJBLocalObject.
     *
     * @return the EJBLocalObject.
     * @throws UnsupportedOperationException always.
     */
    public EJBLocalObject getEJBLocalObject() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get EJBLocalObject.
     *
     * @return the EJBLocalObject.
     * @throws UnsupportedOperationException always.
     */
    public EJBObject getEJBObject() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get MessageContext.
     *
     * @return the MessageContext.
     * @throws UnsupportedOperationException always.
     */
    public MessageContext getMessageContext() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get BusinessObject.
     *
     * @param tClass the tClass
     * @param <T> the class.
     * @return the BusinessObject.
     * @throws UnsupportedOperationException always.
     */
    public <T> T getBusinessObject(Class<T> tClass) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get InvokedBusinessInterface.
     *
     * @return the InvokedBusinessInterface.
     * @throws UnsupportedOperationException always.
     */
    public Class getInvokedBusinessInterface() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get EJBHome.
     *
     * @return the EJBHome.
     * @throws UnsupportedOperationException always.
     */
    public EJBHome getEJBHome() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get EJBLocalHome.
     *
     * @return the EJBLocalHome.
     * @throws UnsupportedOperationException always.
     */
    public EJBLocalHome getEJBLocalHome() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get Environment.
     *
     * @return the Properties.
     * @throws UnsupportedOperationException always.
     */
    public Properties getEnvironment() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get Identity.
     *
     * @return the Identity.
     * @throws UnsupportedOperationException always.
     */
    public Identity getCallerIdentity() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get Principal.
     *
     * @return the Principal.
     */
    public Principal getCallerPrincipal() {
        return new UserProfilePrincipal(new MockProfile(), userId, "user15");
    }

    /**
     * set is caller in role property.
     *
     * @param identity the Identity.
     * @return is caller in role property.
     * @throws UnsupportedOperationException always.
     */
    public boolean isCallerInRole(Identity identity) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get is caller in role property.
     *
     * @param s the parameter
     *
     * @return is caller in role property.
     */
    public boolean isCallerInRole(String s) {
        if (isAdmin) {
            return s.contains("Administrator");
        } else {
            return "User".equals(s);
        }
    }

    /**
     * get UserTransaction.
     *
     * @return the UserTransaction.
     * @throws UnsupportedOperationException always.
     */
    public UserTransaction getUserTransaction() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * set the roll back only property.
     *
     * @throws UnsupportedOperationException always.
     */
    public void setRollbackOnly() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get the roll back only property.
     *
     * @return the roll back only property.
     * @throws UnsupportedOperationException always.
     */
    public boolean getRollbackOnly() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * get TimerService.
     *
     * @return the TimerService.
     * @throws UnsupportedOperationException always.
     */
    public TimerService getTimerService() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * lookup the object.
     *
     * @param s the parameter.
     * @return the object.
     * @throws UnsupportedOperationException always.
     */
    public Object lookup(String s) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * the mock Profile.
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class MockProfile implements Profile {
        /**
         * <p>
         * Gets the profile type. Profile type is represented by
         * <code>String</code> instance.
         * </p>
         *
         * @return Profile type. Should not be null or empty
         */
        public String getType() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p>
         * Adds an attribute to the profile.
         * </p>
         * <p>
         * If the profile previously contained an attribute with the passed
         * attribute's key, the old one is replaced by the specified one.
         * </p>
         *
         * @param attribute Attribute to add. Should not be null. Its key should
         *        not be null.
         * @throws IllegalArgumentException If given attribute or its key is
         *         null
         */
        public void addAttribute(Attribute attribute) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p>
         * Gets the attribute with the specified key.
         * </p>
         * <p>
         * If no attribute found, null is returned.
         * </p>
         *
         * @param key Key of the attribute to search for. Should not be null.
         *        Can be of any type
         * @return Attribute with the specified key. Can be null, if no
         *         attribute was found
         * @throws IllegalArgumentException If given key is null or empty
         */
        public Attribute getAttribute(Object key) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p>
         * Removes the attribute with the specified key.
         * </p>
         * <p>
         * Returns the previously associated attribute or null if the attribute
         * with the specified key was not found.
         * </p>
         *
         * @param key Key of the attribute to search for. Should not be null.
         *        Can be of any type
         * @return Previously associated attribute with the specified key. Can
         *         be null, if no attribute was found
         * @throws IllegalArgumentException If given key is null
         */
        public Attribute removeAttribute(Object key) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p>
         * Gets all the attributes of the profile.
         * </p>
         *
         * @return Array of attributes. Should not be null, but can be empty.
         *         Values should not be null
         */
        public Attribute[] getAttributes() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p>
         * Removes all the attributes from the profile.
         * </p>
         */
        public void clearAttributes() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }
    }
}
