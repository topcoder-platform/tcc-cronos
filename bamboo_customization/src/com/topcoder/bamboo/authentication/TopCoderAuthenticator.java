/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.authentication;

import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.user.BambooUser;
import com.atlassian.bamboo.user.authentication.BambooAuthenticator;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.NoSuchUserException;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrLocal;
import com.topcoder.security.login.LoginLocal;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.WebConstants;
import com.topcoder.web.common.security.Constants;

/**
 * Bamboo custom authenticator that validates user in TopCoder's database.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class TopCoderAuthenticator extends BambooAuthenticator {
    /**
     * The Log object.
     */
    private static final Logger LOG = Logger.getLogger(TopCoderAuthenticator.class);

    /**
     * Identity of the GUEST user.
     */
    private static final User GUEST = SimpleUser.createGuest();

    /**
     * Bamboo users group.
     */
    private static final String BAMBOO_GROUP_USERS = "bamboo-users";

    /**
     * Bamboo employees group.
     */
    private static final String BAMBOO_GROUP_STAFF = "topcoder-staff";

    /**
     * TC administrator group.
     */
    private static final String TC_GROUP_ADMIN = "group_Admin";

    /**
     * Fetches the user in Bamboo's database. If not found, tries to find the user in TopCoder's database and adds the
     * user to Bamboo. If found, returns the "case sensitive" principal.
     * 
     * @param userName the user to be retrieved.
     * @param the user's principal.
     * 
     * @see BambooAuthenticator#getUser(java.lang.String)
     */
    protected Principal getUser(String userName) {
        String lowerCaseUserName = userName.toLowerCase();

        if (super.getUser(lowerCaseUserName) == null) {
            try {
                PrincipalMgrLocal pmr = (PrincipalMgrLocal) Constants.createLocalEJB(PrincipalMgrLocal.class);
                UserPrincipal principal = pmr.getUser(userName);

                if (principal == null) {
                    return null;
                }

                if (principal.getId() == GUEST.getId()) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info("Blocking GUEST access: " + userName);
                    }
                    return null;
                }

                if (Arrays.binarySearch(WebConstants.ACTIVE_STATI, getStatus(principal.getId())) < 0) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info("Blocking inactive access: " + userName);
                    }
                    return null;
                }

                // "password" is not used since this class authenticates against TC database
                getBambooUserManager().addUser(lowerCaseUserName, "password", "topcoder@topcoder.com", userName);

                // Always add to Bamboo users group
                getBambooUserManager().addMembership(BAMBOO_GROUP_USERS, lowerCaseUserName);

            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("An error occurred", ex);
                }
                return null;
            }

        }

        return new CaseSensitivePrincipal(super.getUser(lowerCaseUserName), userName);
    }

    /**
     * Check user activity.
     * 
     * @param userId the id of the user in TC database.
     * @return char
     * @throws Exception if any exception occurs.
     */
    private char getStatus(long userId) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering getStatus: " + userId);
        }

        DataAccess dai = new DataAccess(DBMS.OLTP_DATASOURCE_NAME);
        Request dataRequest = new Request();
        dataRequest.setProperty(DataAccessConstants.COMMAND, "userid_to_password");
        dataRequest.setProperty("uid", Long.toString(userId));
        Map dataMap = dai.getData(dataRequest);
        ResultSetContainer rsc = (ResultSetContainer) dataMap.get("userid_to_password");

        char ret = rsc.getStringItem(0, "status").charAt(0);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Leaving getStatus: " + (int) ret);
        }

        return ret;
    }

    /**
     * Authenticates the user in TopCoder's database. If the user is not found, authenticates using Bamboo's default
     * authentication.
     * 
     * @param principal the user's principal.
     * @param password the user's password.
     * @return <code>true</code> if the user was authenticated.
     * @see BambooAuthenticator#authenticate(Principal, String)
     */
    protected boolean authenticate(Principal principal, String password) {

        String userName = ((CaseSensitivePrincipal) principal).getRealUserName();

        if (LOG.isDebugEnabled()) {
            LOG.debug("entering authenticate(" + userName + ")");
        }
        TCSubject sub;
        try {
            sub = dbAuthenticate(userName, password);

            if (sub == null) {
                return super.authenticate(principal, password);
            }

            if (Arrays.binarySearch(WebConstants.ACTIVE_STATI, getStatus(sub.getUserId())) < 0) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Blocking inactive access: " + userName);
                }
                return false;
            }

        } catch (Exception ex) {
            LOG.warn("An error occurred in authenticate(" + userName + ")");
            return false;
        }

        // Bamboo works with lower case names
        String lowerCaseUserName = userName.toLowerCase();

        BambooUser user = getBambooUserManager().getBambooUser(lowerCaseUserName);
        try {
            String tcEmail = getEmail(sub.getUserId());
            if (tcEmail != null) {
                String jiraEmail = user.getEmail();
                if (!tcEmail.equals(jiraEmail)) {
                    user.setEmail(tcEmail);
                    getBambooUserManager().saveUser(user);
                }
            }

            // If user us a TC admin, add to topcoder staff group, otherwise, remove it from the group
            if (isAdmin(userName)) {
                getBambooUserManager().addMembership(BAMBOO_GROUP_STAFF, lowerCaseUserName);
            } else {
                getBambooUserManager().removeMembership(BAMBOO_GROUP_STAFF, lowerCaseUserName);
            }

        } catch (Exception e) {
            // ignore
        }

        return true;
    }

    /**
     * Returns email from TC database.
     * 
     * @param userId the id of the user in TC database.
     * @return the email from TC database.
     * @throws Exception if any exception occurs.
     */
    private String getEmail(long userId) throws Exception {
        DataAccess dai = new DataAccess(DBMS.OLTP_DATASOURCE_NAME);
        Request dataRequest = new Request();
        dataRequest.setProperty(DataAccessConstants.COMMAND, "user_email");
        dataRequest.setProperty("uid", String.valueOf(userId));
        ResultSetContainer rsc = (ResultSetContainer) (dai.getData(dataRequest).get("user_email"));
        LOG.debug("email for user " + userId + " " + rsc.toString());
        return rsc.getStringItem(0, "address");
    }

    /**
     * Checks if the given user belongs to TC's administrator group.
     * 
     * @param userName the user name
     * @return whether the given user belongs to TC's administrator group.
     * @throws Exception if any unknown problem occurs while hitting the database.
     */
    private boolean isAdmin(String userName) throws Exception {
        try {
            PrincipalMgrLocal pmr = (PrincipalMgrLocal) Constants.createLocalEJB(PrincipalMgrLocal.class);
            TCSubject sub = pmr.getUserSubject(pmr.getUser(userName).getId());
            for (Iterator itr = sub.getPrincipals().iterator(); itr.hasNext();) {
                Object rp = itr.next();
                if (((RolePrincipal) rp).getName().equals(TC_GROUP_ADMIN)) {
                    return true;
                }
            }
            return false;
        } catch (NoSuchUserException e) {
            LOG.debug("no such user");
            return false;
        }
    }

    /**
     * Authenticates the user against TC's database.
     * 
     * @param userName the user name.
     * @param password the password.
     * @return true if password is correct.
     */
    private TCSubject dbAuthenticate(String userName, String password) {

        TCSubject ret = null;
        try {
            LoginLocal login = (LoginLocal) Constants.createLocalEJB(LoginLocal.class);
            ret = login.login(userName, password);

            if (LOG.isDebugEnabled()) {
                LOG.debug("correct user name and password");
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    /**
     * A principal that stores the real userName without converting to lowercase. This is useful to retrieve
     * TopCoder's username.
     */
    private class CaseSensitivePrincipal implements Principal {

        /**
         * Bamboo's principal being wrapped by this entity.
         */
        private final Principal wrapped;

        /**
         * The case-sensitive user name.
         */
        private final String realUserName;

        /**
         * Creates a CaseSensitivePrincipal wrapping an existing principal and using the real user name (without
         * converting it to lower case).
         * 
         * @param wrapped the wrapped principal object.
         * @param realUserName the case-sensitive user name.
         */
        public CaseSensitivePrincipal(Principal wrapped, String realUserName) {
            this.wrapped = wrapped;
            this.realUserName = realUserName;
        }

        /**
         * Returns the case-sensitive user name.
         * 
         * @return the case-sensitive user name.
         */
        public String getRealUserName() {
            return realUserName;
        }

        /**
         * Returns the name of this principal.
         * 
         * @return the name of this principal.
         */
        public String getName() {
            return wrapped.getName();
        }

        /**
         * Returns a string representation of this principal.
         * 
         * @return a string representation of this principal.
         */
        public String toString() {
            return "wrapped: " + wrapped.toString() + " realUserName: " + getRealUserName();
        }

        /**
         * Returns a hashcode for this principal.
         * 
         * @return a hashcode for this principal.
         */
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.realUserName == null) ? 0 : this.realUserName.hashCode());
            result = prime * result + ((this.wrapped == null) ? 0 : this.wrapped.hashCode());
            return result;
        }

        /**
         * Compares this principal to the specified object. Returns true if the object passed in matches the principal
         * represented by this implementation.
         * 
         * @param another principal to compare with.
         * @return true if the principal passed in is the same as that encapsulated by this principal, and false
         *         otherwise.
         */
        public boolean equals(Object another) {
            if (this == another) {
                return true;
            }
            if (another == null) {
                return false;
            }
            if (getClass() != another.getClass()) {
                return false;
            }
            final CaseSensitivePrincipal other = (CaseSensitivePrincipal) another;
            if (this.realUserName == null) {
                if (other.realUserName != null) {
                    return false;
                }
            } else if (!this.realUserName.equals(other.realUserName)) {
                return false;
            }
            if (this.wrapped == null) {
                if (other.wrapped != null) {
                    return false;
                }
            } else if (!this.wrapped.equals(other.wrapped)) {
                return false;
            }
            return true;
        }

    }
}
