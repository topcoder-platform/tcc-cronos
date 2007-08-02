/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.game.server.util.OrpheusSession;
import com.topcoder.user.profile.UserProfile;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import java.util.Iterator;

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
            recordSessionStart(this.playerSessions, session, user);
        } else if (user.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            recordSessionStart(this.sponsorSessions, session, user);
        } else if (user.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            recordSessionStart(this.adminSessions, session, user);
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
            recordSessionEnd(this.playerSessions, session, user);
        } else if (user.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            recordSessionEnd(this.sponsorSessions, session, user);
        } else if (user.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            recordSessionEnd(this.adminSessions, session, user);
        } else {
            System.err.println("OrpheusOnlineStatusTracker.userLoggedOut : The user [" + user.getIdentifier()
                               + "] does not have a valid profile type");
        }
    }

    /**
     * <p>Notifies this status tracker on a new request issued by the specified user in context of the specified
     * session.</p>
     *
     * @param user a <code>UserProfile</code> providing the details for the user logged in.
     * @param session a <code>HttpSession</code> providing the details for the session associated with the specified
     *        user.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void requestArrived(UserProfile user, HttpSession session) {
        if (user == null) {
            throw new IllegalArgumentException("The parameter [user] is NULL");
        }
        if (session == null) {
            throw new IllegalArgumentException("The parameter [session] is NULL");
        }
        if (user.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
            touchSession(this.playerSessions, session, user);
        } else if (user.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            touchSession(this.sponsorSessions, session, user);
        } else if (user.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            touchSession(this.adminSessions, session, user);
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
            ArrayList usersList = new ArrayList();
            List sessionUsers;
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                sessionUsers = (List) iterator.next();
                usersList.addAll(sessionUsers);
            }
            return usersList;
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
            ArrayList usersList = new ArrayList();
            List sessionUsers;
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                sessionUsers = (List) iterator.next();
                usersList.addAll(sessionUsers);
            }
            return usersList;
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
            ArrayList usersList = new ArrayList();
            List sessionUsers;
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                sessionUsers = (List) iterator.next();
                usersList.addAll(sessionUsers);
            }
            return usersList;
        }
    }

    /**
     * <p>Records the creation of specified new session which is associated with the specified user. The session is put
     * to specified map which currently keeps track of active sessions.</p>
     *
     * @param currentSessions a <code>Map</code> mapping the IDs for current active sessions to list of associated
     *        users.
     * @param session a <code>HttpSession</code> representing a newly created session. 
     * @param user a <code>UserProfile</code> representing the user associated with newly created session. 
     */
    private void recordSessionStart(Map currentSessions, HttpSession session, UserProfile user) {
        try {
            String sid = session.getId();
            synchronized (currentSessions) {
                List currentUsers;
                if (!currentSessions.containsKey(sid)) {
                    currentUsers = new LinkedList();
                    currentSessions.put(sid, currentUsers);
                } else {
                    currentUsers = (List) currentSessions.get(sid);
                }
                OrpheusSession onlineSession = new OrpheusSession(user, sid, new Date(session.getCreationTime()));
                onlineSession.touch();
                currentUsers.add(onlineSession);
            }
        } catch (Exception e) {
            System.err.println("OrpheusOnlineStatusTracker.recordSessionStart : An unexpected error while recording the"
                               + " start of session " + session.getId() + " for user [" + user.getIdentifier());
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>Records the end of specified session which is associated with the specified user. The session is removed from
     * specified map which currently keeps track of active sessions.</p>
     *
     * @param currentSessions a <code>Map</code> mapping the IDs for current active sessions to list of associated
     *        users.
     * @param session a <code>HttpSession</code> representing a newly created session.
     * @param user a <code>UserProfile</code> representing the user associated with newly created session.
     */
    private void recordSessionEnd(Map currentSessions, HttpSession session, UserProfile user) {
        try {
            String sid = session.getId();
            synchronized (currentSessions) {
                List currentUsers;
                if (currentSessions.containsKey(sid)) {
                    currentUsers = (List) currentSessions.get(sid);
                    Iterator iterator = currentUsers.iterator();
                    OrpheusSession onlineSession;
                    while (iterator.hasNext()) {
                        onlineSession = (OrpheusSession) iterator.next();
                        if (onlineSession.getUser().getIdentifier().equals(user.getIdentifier())) {
                            iterator.remove();
                        }
                    }
                    if (currentUsers.isEmpty()) {
                        currentSessions.remove(sid);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("OrpheusOnlineStatusTracker.recordSessionEnd : An unexpected error while recording the "
                               + " end of session " + session.getId() + " for user [" + user.getIdentifier());
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>Records the fact of arrival of new request issued in context of specified session which is associated with the
     * specified user. The session is expected to be located in specified map which currently keeps track of active
     * sessions.</p>
     *
     * @param currentSessions a <code>Map</code> mapping the IDs for current active sessions to list of associated
     *        users.
     * @param session a <code>HttpSession</code> representing a newly created session.
     * @param user a <code>UserProfile</code> representing the user associated with newly created session.
     */
    private void touchSession(Map currentSessions, HttpSession session, UserProfile user) {
        try {
            String sid = session.getId();
            synchronized (currentSessions) {
                List currentUsers;
                if (currentSessions.containsKey(sid)) {
                    currentUsers = (List) currentSessions.get(sid);
                    Iterator iterator = currentUsers.iterator();
                    OrpheusSession onlineSession;
                    while (iterator.hasNext()) {
                        onlineSession = (OrpheusSession) iterator.next();
                        if (onlineSession.getUser().getIdentifier().equals(user.getIdentifier())) {
                            onlineSession.touch();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("OrpheusOnlineStatusTracker.touchSession : An unexpected error while touching the "
                               + "session " + session.getId() + " for user [" + user.getIdentifier());
            e.printStackTrace(System.err);
        }
    }
}
