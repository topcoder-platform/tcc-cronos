package com.topcoder.service.studio.accuracytests;

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


public class MockSessionContext implements SessionContext {
    public static boolean isAdmin;
    public static long userId;

    public MockSessionContext() {
        isAdmin = true;
        userId = 33;
    }

    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public EJBObject getEJBObject() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public MessageContext getMessageContext() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public <T> T getBusinessObject(Class<T> tClass) throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public Class getInvokedBusinessInterface() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public EJBHome getEJBHome() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public EJBLocalHome getEJBLocalHome() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public Properties getEnvironment() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public Identity getCallerIdentity() {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public Principal getCallerPrincipal() {
        return new UserProfilePrincipal(new MockProfile(), userId, "user15");
    }

    public boolean isCallerInRole(Identity identity) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public boolean isCallerInRole(String s) {
        if (isAdmin) {
            return s.contains("Administrator");
        } else {
            return "User".equals(s);
        }
    }

    public UserTransaction getUserTransaction() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public void setRollbackOnly() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public boolean getRollbackOnly() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public TimerService getTimerService() throws IllegalStateException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    public Object lookup(String s) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }


    private static class MockProfile implements Profile {
        /**
         * <p> Gets the profile type. Profile type is represented by <code>String</code> instance. </p>
         *
         * @return Profile type. Should not be null or empty
         */
        public String getType() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p> Adds an attribute to the profile. </p> <p> If the profile previously contained an attribute with the passed
         * attribute's key, the old one is replaced by the specified one. </p>
         *
         * @param attribute Attribute to add. Should not be null. Its key should not be null.
         * @throws IllegalArgumentException If given attribute or its key is null
         */
        public void addAttribute(Attribute attribute) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p> Gets the attribute with the specified key. </p> <p> If no attribute found, null is returned. </p>
         *
         * @param key Key of the attribute to search for. Should not be null. Can be of any type
         * @return Attribute with the specified key. Can be null, if no attribute was found
         * @throws IllegalArgumentException If given key is null or empty
         */
        public Attribute getAttribute(Object key) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p> Removes the attribute with the specified key. </p> <p> Returns the previously associated attribute or null if
         * the attribute with the specified key was not found. </p>
         *
         * @param key Key of the attribute to search for. Should not be null. Can be of any type
         * @return Previously associated attribute with the specified key. Can be null, if no attribute was found
         * @throws IllegalArgumentException If given key is null
         */
        public Attribute removeAttribute(Object key) {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p> Gets all the attributes of the profile. </p>
         *
         * @return Array of attributes. Should not be null, but can be empty. Values should not be null
         */
        public Attribute[] getAttributes() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }

        /**
         * <p> Removes all the attributes from the profile. </p>
         */
        public void clearAttributes() {
            throw new UnsupportedOperationException("Method is not implemented yet.");
        }
    }
}
