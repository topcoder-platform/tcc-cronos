/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;

/**
 * <p>A tracker for the online status of users logged to server. Maintains the separate lists of players, sponsors and
 * administrators currenly logged to server. Provides the methods to be used to notify the tracker on users login/logout
 * actions.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusOnlineStatusTracker implements Serializable {

    /**
     * <p>A <code>String</code> providing the name of the context attribute which the instance of this class can be
     * bound to. </p>
     */
    public static final String CONTEXT_ATTRIBUTE_NAME = "OrpheusOnlineStatusTracker";

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> IDs of active HTTP sessions to lists of <code>UserProfile
     * </code>s providing the details for the sponsors currently logged to server.</p>
     */
    private final Map sponsorSessions;

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> IDs of active HTTP sessions to lists of <code>UserProfile
     * </code>s providing the details for the players currently logged to server.</p>
     */
    private final Map playerSessions;

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> IDs of active HTTP sessions to lists of <code>UserProfile
     * </code>s providing the details for the administrators currently logged to server.</p>
     */
    private final Map adminSessions;

    /**
     * <p>Constructs new <code>OrpheusOnlineStatusTracker</code> instance. The list of active sessions is empty.</p>
     */
    public OrpheusOnlineStatusTracker() {
        this.adminSessions = Collections.synchronizedMap(new HashMap());
        this.playerSessions = Collections.synchronizedMap(new HashMap());
        this.sponsorSessions = Collections.synchronizedMap(new HashMap());
    }

    /**
     * <p>Notifies this status tracker on specified user who have just logged to server and is associated with the
     * specified session.</p>
     *
     * @param user a <code>UserProfile</code> providing the details for the user logged in.
     * @param session a <code>HttpSession</code> providing the details for the session associated with the specified
     *        user. 
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void userLoggedIn(UserProfile user, HttpSession session) {
        if (user == null) {
            throw new IllegalArgumentException("The parameter [user] is NULL");
        }
        if (session == null) {
            throw new IllegalArgumentException("The parameter [session] is NULL");
        }
        if (user.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
            this.playerSessions.put(session.getId(), user);
        } else if (user.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            this.sponsorSessions.put(session.getId(), user);
        } else if (user.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            this.adminSessions.put(session.getId(), user);
        } else {
            System.err.println("OrpheusOnlineStatusTracker.userLoggedIn : The user [" + user.getIdentifier()
                               + "] does not have a valid profile type");
        }
    }

    /**
     * <p>Notifies this status tracker on specified user who have just logged out of the server and is associated
     * the specified session.</p>
     *
     * @param user a <code>UserProfile</code> providing the details for the user logged in.
     * @param session a <code>HttpSession</code> providing the details for the session associated with the specified
     *        user.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void userLoggedOut(UserProfile user, HttpSession session) {
        if (user == null) {
            throw new IllegalArgumentException("The parameter [user] is NULL");
        }
        if (session == null) {
            throw new IllegalArgumentException("The parameter [session] is NULL");
        }
        if (user.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
            this.playerSessions.remove(session.getId());
        } else if (user.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            this.sponsorSessions.remove(session.getId());
        } else if (user.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            this.adminSessions.remove(session.getId());
        } else {
            System.err.println("OrpheusOnlineStatusTracker.userLoggedOut : The user [" + user.getIdentifier()
                               + "] does not have a valid profile type");
        }
    }

    /**
     * <p>Gets the list of players who have active HTTP sessions associated with them.</p>
     *
     * @return a <code>Collection</code> of <code>UserProfile</code> objects providing the details for the active
     *         players.  
     */
    public Collection getActivePlayers() {
        Collection users = this.playerSessions.values();
        synchronized (this.playerSessions) {
            return new ArrayList(users);
        }
    }

    /**
     * <p>Gets the list of sponsors who have active HTTP sessions associated with them.</p>
     *
     * @return a <code>Collection</code> of <code>UserProfile</code> objects providing the details for the active
     *         sponsors.
     */
    public Collection getActiveSponsors() {
        Collection users = this.sponsorSessions.values();
        synchronized (this.sponsorSessions) {
            return new ArrayList(users);
        }
    }

    /**
     * <p>Gets the list of administrators who have active HTTP sessions associated with them.</p>
     *
     * @return a <code>Collection</code> of <code>UserProfile</code> objects providing the details for the active
     *         administrators.
     */
    public Collection getActiveAdmins() {
        Collection users = this.adminSessions.values();
        synchronized (this.adminSessions) {
            return new ArrayList(users);
        }
    }
}
