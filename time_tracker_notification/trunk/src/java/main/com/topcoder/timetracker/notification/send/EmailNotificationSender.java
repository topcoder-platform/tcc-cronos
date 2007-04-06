/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;

import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationSender;
import com.topcoder.timetracker.notification.NotificationSendingException;

import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * EmailNotificationSender is used to send the notification via email. The notification content is retrieved from
 * database. MessageBodyGenerator is employed to generate the email message body based on the contact name and
 * notification message from database.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe since it¡¯s immutable.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class EmailNotificationSender implements NotificationSender {
    /** Represents the SQL command to select the notification of specified id. */
    private static final String SQL_SELECT_NOTIFICATION = "select notification_id, company_id, from_address,"
        + " subject, message, last_time_sent, next_time_send, status, scheduleId, creation_user,"
        + " creation_date, modification_user, modification_date from notification where notification_id = ?";

    /** Represents the SQL command to select the client ids related to specified notification. */
    private static final String SQL_SELECT_CLIENTS = "select client_id from notify_clients where notification_id = ?";

    /** Represents the SQL command to select the resource ids related to specified notification. */
    private static final String SQL_SELECT_RESOURCES = "select user_account_id from notify_resources"
        + " where notification_id = ?";

    /** Represents the SQL command to select the project ids related to specified notification. */
    private static final String SQL_SELECT_PROJECTS = "select project_id from notify_projects"
        + " where notification_id = ?";

    /**
     * <p>
     * The DBConnectionFactory is used to create the named connection.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set
     * </p>
     */
    private final DBConnectionFactory dbFactory;

    /**
     * <p>
     * The connection name used to create the named connection.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and non-empty and immutable after set.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * ContactManager is used to get the contacts by the contact ids. The contact is actually the user acount id,
     * project id and client id.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final ContactManager contactManager;

    /**
     * <p>
     * MessageBodyGenerator is used to generate the email message body based on the contact name and the message from
     * notification table.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final MessageBodyGenerator generator;

    /**
     * The log used to log the error during notification. In the event of a failure to send a notification an entry in
     * the log should indicate the notification Id, date and time and the reason for failure. If individual email
     * addresses were involved list them as well. the log entry should be: [notification id]:[time]:[email address
     * list if involved] The log level is Level.ERROR. This variable is set in the constructor, no-null and immutable
     * after set
     */
    private final Log log;

    /**
     * Create the instance with given argument, assign the arguments to class variables. Get the log from LogFactory by
     * logName.
     *
     * @param dbFactory the db connection factory
     * @param connName the connection name
     * @param cm the contact manager
     * @param generator the email message body generator
     * @param logName the log name used to get the log
     *
     * @throws NotificationConfigurationException if failed to get the named log
     * @throws IllegalArgumentException if argument is null, string argument is empty
     */
    public EmailNotificationSender(DBConnectionFactory dbFactory, String connName, ContactManager cm,
        MessageBodyGenerator generator, String logName)
        throws NotificationConfigurationException {
        Helper.checkNull(dbFactory, "dbFactory");
        Helper.checkString(connName, "connName");
        Helper.checkNull(cm, "cm");
        Helper.checkNull(generator, "generator");
        Helper.checkString(logName, "logName");

        this.dbFactory = dbFactory;
        this.connectionName = connName;
        this.contactManager = cm;
        this.generator = generator;

        this.log = LogFactory.getLog(logName);
    }

    /**
     * Email the notification.
     *
     * @param notificationId the notification id to get the notification to send
     *
     * @throws NotificationSendingException if any error occurred
     */
    public void send(long notificationId) throws NotificationSendingException {
        Notification notification = getNotification(notificationId);

        // do nothing when there is no notification with the specified id or the
        // notification is inactive.
        if ((notification == null) || !notification.isActive()) {
            return;
        }

        sendToIds(notification, notification.getToClients());
        sendToIds(notification, notification.getToProjects());
        sendToIds(notification, notification.getToResources());
    }

    /**
     * Send the notification to the id list.
     *
     * @param notificaion the notification to be sent
     * @param ids the id list
     *
     * @throws NotificationSendingException if any error occurred
     */
    private void sendToIds(Notification notificaion, long[] ids)
        throws NotificationSendingException {
        // traverse the id list
        for (int i = 0; i < ids.length; i++) {
            Contact contact = null;

            try {
                // get the contact information.
                contact = contactManager.retrieveContact(ids[i]);
            } catch (PersistenceException pe) {
                this.log.log(Level.ERROR, createLog(notificaion.getId(), pe.getMessage()));
                throw new NotificationSendingException("Error when retrieve contact information.", pe);
            }

            // generate the message body
            String messageBody = null;

            String contactName = contact.getFirstName() + " " + contact.getLastName();

            try {
                messageBody = generator.generateMessage(contactName, notificaion.getMessage());
            } catch (MessageBodyGeneratorException mbge) {
                this.log.log(Level.ERROR, createLog(notificaion.getId(), mbge.getMessage()));
                throw new NotificationSendingException("Error generating message.", mbge);
            }

            // create the message
            TCSEmailMessage message = new TCSEmailMessage();

            try {
                message.addToAddress(contactName + "<" + contact.getEmailAddress() + ">", TCSEmailMessage.TO);
                message.setFromAddress(notificaion.getFromAddress());
                message.setBody(messageBody);

                EmailEngine.send(message);
            } catch (AddressException ae) {
                this.log.log(Level.ERROR, createLog(notificaion.getId(), ae.getMessage()));
                throw new NotificationSendingException("The email address is invalid.", ae);
            } catch (ConfigManagerException cme) {
                this.log.log(Level.ERROR, createLog(notificaion.getId(), cme.getMessage()));
                throw new NotificationSendingException("Can not load config.", cme);
            } catch (SendingException se) {
                this.log.log(Level.ERROR, createLog(notificaion.getId(), se.getMessage()));
                throw new NotificationSendingException("There is problem sending.", se);
            }
        }
    }

    /**
     * Get the notification from the database.
     *
     * @param notificationId the notification id.
     *
     * @return return the notification, if nothing is found, return null
     *
     * @throws NotificationSendingException if any error occurred
     */
    private Notification getNotification(long notificationId)
        throws NotificationSendingException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // create a DB connection
            conn = dbFactory.createConnection(connectionName);

            conn.setAutoCommit(true);

            stmt = conn.prepareStatement(SQL_SELECT_NOTIFICATION);

            stmt.setLong(1, notificationId);

            result = stmt.executeQuery();

            if (result.next()) {
                Notification notification = new Notification();

                notification.setId(result.getLong("notification_id"));
                notification.setFromAddress(result.getString("from_address"));
                notification.setSubject(result.getString("subject"));
                notification.setMessage(result.getString("message"));
                Date lastTimeSent = result.getDate("last_time_sent");
                if (lastTimeSent != null) {
                    notification.setLastTimeSent(result.getDate("last_time_sent"));
                }
                Date nextTimeSend = result.getDate("next_time_send");
                if (nextTimeSend != null) {
                    notification.setNextTimeToSend(result.getDate("next_time_send"));
                }
                notification.setActive((result.getInt("status") != 0) ? true : false);
                notification.setScheduleId(result.getLong("scheduleId"));
                notification.setCreationUser(result.getString("creation_user"));
                notification.setCreationDate(result.getDate("creation_date"));
                notification.setModificationUser(result.getString("modification_user"));
                notification.setModificationDate(result.getDate("creation_date"));

                notification.setToClients(getAssociation(conn, notification.getId(), SQL_SELECT_CLIENTS));
                notification.setToProjects(getAssociation(conn, notification.getId(), SQL_SELECT_PROJECTS));
                notification.setToResources(getAssociation(conn, notification.getId(), SQL_SELECT_RESOURCES));

                return notification;
            }

            return null;
        } catch (SQLException sqle) {
            throw new NotificationSendingException("Error occurred when retrieving data from database.", sqle);
        } catch (DBConnectionException dbce) {
            throw new NotificationSendingException("Error occurred when get the connection.", dbce);
        } finally {
            try {
                if ((conn != null) && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                // ignore it
            }
        }
    }

    /**
     * Get association such as notify_clients, notify_projects, notify_resources from the database.
     *
     * @param conn the Connection used to get the data
     * @param notificationId the association's notification id
     * @param command the SQL command
     *
     * @return the array of the association
     *
     * @throws NotificationSendingException if any error occurred
     */
    private long[] getAssociation(Connection conn, long notificationId, String command)
        throws NotificationSendingException {
        ResultSet result = null;
        PreparedStatement stmt = null;

        try {
            // prepare the statement
            stmt = conn.prepareStatement(command);

            stmt.setLong(1, notificationId);

            // do the query
            result = stmt.executeQuery();

            return retrieveArray(result);
        } catch (SQLException sqle) {
            throw new NotificationSendingException("Error when retrieve information from database.", sqle);
        } finally {
            releaseStatement(stmt, result);
        }
    }

    /**
     * <p>
     * A private helper method to retrieve all the long ids from a given ResultSet. SQLException will be thrown to
     * upper method to deal with.
     * </p>
     *
     * @param result the given result
     *
     * @return the retrieved array of long ids
     *
     * @throws SQLException if any error occurs with the sql executing.
     */
    private long[] retrieveArray(ResultSet result) throws SQLException {
        // Retrieve all the buddies from this result set.
        List list = new ArrayList();

        while (result.next()) {
            list.add(new Long(result.getLong(1)));
        }

        // Store it into an array
        long[] arr = new long[list.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = ((Long) list.get(i)).longValue();
        }

        return arr;
    }

    /**
     * <p>
     * Release the statement and result set.
     * </p>
     *
     * @param stmt the statement to release
     * @param result the result to release
     */
    private void releaseStatement(Statement stmt, ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * Create the log entry.
     * @param notificationId the id of the notification
     * @param message the message
     * @return the log entry
     */
    private String createLog(long notificationId, String message) {
        return notificationId + ":" + (new Date()) + ":" + message;
    }
}
