/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger.impl;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.Helper;
import com.cronos.im.messenger.MessageTracker;
import com.cronos.im.messenger.MessageTrackerException;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * This class is used to store the tracking information in the database. It is called
 * by <c>MessengerImpl</c> when a chat message is posted to user’s session pool.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe since it does not contain any mutable inner status.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ChatMessageTrackerImpl implements MessageTracker {

    /**
     * The DBConnectionFactory used to create named connection.
     * It is set in the constructor to a non-null value and never changed later.
     */
    private final DBConnectionFactory dbFactory;

    /**
     * The connection name to be user by <c>dbFactory</c>.
     * It is set in the constructor to a non-null value and never changed later.
     */
    private final String connName;

    /**
     * The database user who creates or modifies the tracking entry.
     * It is set in the constructor to a non-null value and never changed later.
     */
    private final String user;

    /**
     * Represents the sql string used to retrieve the session used id. It is initialized in
     * the constructor and then used in <c>track(Message,long[],long)</c> method.
     */
    private static final String SQL_SELECT_USER_SESSION_ID
        = "SELECT session_user_id FROM session_user WHERE user_id =? AND session_id=?";

    /**
     * Represents the sql string used to insert a session user message. It is initialized in
     * the constructor and then used in <c>track(Message,long[],long)</c> method.
     */
    private final String SQL_INSERT_SESSION_USER_MESSAGE;

    /**
     * Creates a new instance.
     *
     * @param dbFactory The db connection factory.
     * @param connName  The connection name (used by db connection factory).
     * @param user      The database operator.
     * @throws IllegalArgumentException If <c>dbFactory</c> or <c>user</c> argument is null
     *                                  or if one of the string arguments is empty.
     */
    public ChatMessageTrackerImpl(DBConnectionFactory dbFactory, String connName, String user) {
        Helper.validateNotNull(dbFactory, "dbFactory");
        if (connName != null) {
            Helper.validateNotNullOrEmpty(connName, "connName");
        }
        Helper.validateNotNullOrEmpty(user, "user");

        this.dbFactory = dbFactory;
        this.connName = connName;
        this.user = user;

        StringBuffer sqlStringBuffer = new StringBuffer();
        // Build the sql string for tracking messages
        sqlStringBuffer.append("INSERT INTO session_user_message ").
            append("(session_user_id, message_text, create_date, create_user, modify_date, modify_user)").
            append(" VALUES (?, ?, ?, '").append(this.user).append("', ?, '").
            append(this.user).append("')");
        SQL_INSERT_SESSION_USER_MESSAGE = sqlStringBuffer.toString();
    }

    /**
     * <p>
     * Track the specified message for each of the given user ids and sessionId.
     * If the message is not of ChatMessage type nothing should be done.
     * <p>
     *
     * @param msg       The message to track.
     * @param userIds   The user session id.
     * @param sessionId The session id.
     * @throws IllegalArgumentException If <c>msg</c> or <c>userIds</c> argument is null
     *                                  or if <c>userIds</c> array argument is empty, contains duplicate id
     * @throws MessageTrackerException  Wraps any other exception that may occur.
     */
    public void track(Message msg, long[] userIds, long sessionId)
        throws MessageTrackerException {
        Helper.validateNotNull(msg, "msg");
        // If the message is not of ChatMessage type then nothing should be done.
        if (!(msg instanceof ChatMessage)) {
            return;
        }
        Helper.validateNotNull(userIds, "userIds");

        if (userIds.length == 0) {
            throw new IllegalArgumentException("userIds array parameter must not be empty");
        }

        ChatMessage chatMessage = (ChatMessage) msg;
        int index;
        
        // start of changes ================
        // @since 2007-07-15, for TCIM-8882, we only need to Insert to session_user_message for the
        // Sender of the Message
        // thus, we replace the userIds[] array to contain only one ID which is 
        // the sender's ID
        Object senderID = msg.getSender();
        // check the object type
        Long longUserID;
        if (senderID != null && senderID instanceof Long) {
            longUserID = (Long) senderID;
        } else {
            throw new IllegalArgumentException("Sender's ID is not of type java.lang.Long");
        }
        // set the new Array of User ID's, set only the sender's ID
        userIds = new long[]{longUserID.longValue()};
        // end of changes ================
        
        // Verify if there are duplicate elements in userIds array.
        for (index = 0; index < userIds.length; index++) {
            for (int i = index + 1; i < userIds.length; i++) {
                if (userIds[index] == userIds[i]) {
                    throw new IllegalArgumentException(
                        "userIds array parameter must not contain duplicate elements");
                }
            }
        }

        Connection conn = null;
        PreparedStatement userSessionIdPS = null;
        PreparedStatement messagePS = null;

        try {
            // Create connection
            if (connName == null) {
                conn = dbFactory.createConnection();
            } else {
                conn = dbFactory.createConnection(connName);
            }

            // Retrieve the session_user_id from session_user table by userId and sessionId
            userSessionIdPS = conn.prepareStatement(SQL_SELECT_USER_SESSION_ID);

            // Insert the msg and session_user_id  into the session_user_message table
            // (the session_user_id is retrieved with userSessionIdPS).
            // Only original chat text should be inserted if the message is formatter chat message.
            // Note that the creation/modification time is the current system time
            // and the creation/modification user is this.user
            messagePS = conn.prepareStatement(SQL_INSERT_SESSION_USER_MESSAGE);

            // The insertion of the new messages is made into a transactional manner
            conn.setAutoCommit(false);

            for (index = 0; index < userIds.length; index++) {
                // Set userId and sessionId for userSessionIdPS
                userSessionIdPS.setLong(1, userIds[index]);
                userSessionIdPS.setLong(2, sessionId);
                ResultSet rs = userSessionIdPS.executeQuery();

                long sessionUserId;
                if (rs.next()) {
                    sessionUserId = rs.getLong(1);
                } else {
                    throw new MessageTrackerException("There was no session_user_id found"
                        , msg, userIds[index], sessionId);
                }

                // Set the parameters for the stored procedure that tracks messages
                messagePS.setLong(1, sessionUserId);
                messagePS.setString(2, chatMessage.getChatText());
                Date currentTime = new Date(System.currentTimeMillis());
                messagePS.setDate(3, currentTime);
                messagePS.setDate(4, currentTime);

                // Executing prepared statement instead of running the batch was chosen for being
                // consistent with the current design. Doing the execution of the prepared statement
                // in batch mode can lead to errors, because the batch is executed outside the loop
                // and we don't keep track anymore of the current index.
                messagePS.execute();
            }
            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                // Just ignore
            }
        } catch (MessageTrackerException e) {
            throw e;
        } catch (Exception e) {
            throw new MessageTrackerException("Exception occured during the tracking: "
                + e.getMessage(), e, msg, userIds[index], sessionId);
        } finally {
            // Release JDBC resources.
            if (userSessionIdPS != null) {
                try {
                    userSessionIdPS.close();
                } catch (SQLException e) {
                } // Nothing we can do.
            }
            if (messagePS != null) {
                try {
                    messagePS.close();
                } catch (SQLException e) {
                } // Nothing we can do.
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                } // Nothing we can do.
            }
        }
    }
}
