/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;
import com.topcoder.timetracker.notification.NotificationSender;
import com.topcoder.timetracker.notification.NotificationSendingException;

import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

import java.util.Date;


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

        this.contactManager = cm;
        this.generator = generator;

        try {
            this.log = (logName != null) ? LogFactory.getLog(logName) : LogFactory.getLog();
        } catch (LogException le) {
            throw new NotificationConfigurationException("Error get the log.", le);
        }
    }

    /**
     * Email the notification.
     *
     * @param notificationId the notification id to get the notification to send
     * @param notificationManager the notification persistence used to get the notification
     *
     * @throws NotificationSendingException if any error occurred
     */
    public void send(long notificationId, NotificationPersistence notificationManager)
        throws NotificationSendingException {
        Notification notification = null;
        try {
            notification = notificationManager.getNotification(notificationId);
        } catch (NotificationPersistenceException npe) {
            throw new NotificationSendingException("Error get the notification from database.", npe);
        }

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
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), pe.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
                throw new NotificationSendingException("Error when retrieving contact information.", pe);
            } catch (AssociationException ae) {
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), ae.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
                throw new NotificationSendingException("Error when retrieving contact information.", ae);
            }

            // generate the message body
            String messageBody = null;

            String contactName = contact.getFirstName() + " " + contact.getLastName();

            try {
                messageBody = generator.generateMessage(contactName, notificaion.getMessage());
            } catch (MessageBodyGeneratorException mbge) {
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), mbge.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
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
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), ae.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
                throw new NotificationSendingException("The email address is invalid.", ae);
            } catch (ConfigManagerException cme) {
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), cme.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
                throw new NotificationSendingException("Can not load config.", cme);
            } catch (SendingException se) {
                try {
                    this.log.log(Level.ERROR, createLog(notificaion.getId(), se.getMessage()));
                } catch (LogException le) {
                    throw new NotificationSendingException("Error logging.");
                }
                throw new NotificationSendingException("There is problem sending.", se);
            }
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
