/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
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
import com.topcoder.util.log.LogManager;

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
 * @author ShindouHikaru
 * @author kzhu
 * @author George1
 * @version 1.0
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

        this.log = (logName != null) ? LogManager.getLog(logName) : LogManager.getLog();
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

        Map emails = new HashMap();

        collectEmails(notification, notification.getToClients(), ContactType.CLIENT, emails);
        collectEmails(notification, notification.getToProjects(), ContactType.PROJECT, emails);
        collectEmails(notification, notification.getToResources(), ContactType.USER, emails);

        sendToEmails(notification, emails);
    }

    /**
     * This method searches for the contacts by their associated entities' IDs and returns a map
     * where each key is unique e-mail to send to, and value contains additional information about
     * the contact associated with that e-mail.
     *
     * @param notificaion
     *            the notification to be sent
     * @param ids
     *            list of entity IDs.
     * @param contactType
     *            type of entity to search contacts for.
     * @param emails
     *            a map where each key is unique e-mail to send to, and value contains additional
     *            information about the contact associated with that e-mail.
     * @throws NotificationSendingException
     *             if any error occurrs.
     */
    private void collectEmails(Notification notificaion, long[] ids, ContactType contactType, Map emails)
            throws NotificationSendingException {
        List idsList = new ArrayList();

        for(int i = 0; i < ids.length; ++i)  {
            idsList.add(new Long(ids[i]));
        }

        Filter idsFilter = new InFilter("entity_id", idsList);
        Filter typeFilter = ContactFilterFactory.createTypeFilter(contactType);
        Filter combinedFilter = new AndFilter(idsFilter, typeFilter);
        Contact[] contacts;

        try {
            contacts = contactManager.searchContacts(combinedFilter);
        } catch (PersistenceException pe) {
            this.log.log(Level.ERROR, createLog(notificaion.getId(), pe.getMessage()));
            throw new NotificationSendingException("Error when retrieving contact information.", pe);
        } catch (AssociationException ae) {
            this.log.log(Level.ERROR, createLog(notificaion.getId(), ae.getMessage()));
            throw new NotificationSendingException("Error when retrieving contact information.", ae);
        }

        for (int i = 0; i < contacts.length; ++i) {
            final String email = contacts[i].getEmailAddress();

            try {
                new InternetAddress(email);
            } catch (javax.mail.internet.AddressException ae) {
                // E-mail address is incorrect. Ignore it
                continue;
            }

            if (!emails.containsKey(email)) {
                emails.put(email, contacts[i]);
            }
        }
    }

    /**
     * This method sends the notification to the list of e-mails.
     *
     * @param notificaion
     *            the notification to be sent.
     * @param emails
     *            map of e-mail/Contact pairs.
     * @throws NotificationSendingException
     *             if any error occurrs.
     */
    private void sendToEmails(Notification notificaion, Map emails) throws NotificationSendingException {
        // traverse the id list
        Collection contacts = emails.values();
        for (Iterator iter = contacts.iterator(); iter.hasNext(); ) {
            Contact contact = (Contact) iter.next();

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
                message.addToAddress(contactName + " <" + contact.getEmailAddress() + ">", TCSEmailMessage.TO);
                message.setFromAddress(notificaion.getFromAddress());
                message.setSubject(notificaion.getSubject());
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
     * Create the log entry.
     * @param notificationId the id of the notification
     * @param message the message
     * @return the log entry
     */
    private String createLog(long notificationId, String message) {
        return notificationId + ":" + (new Date()) + ":" + message;
    }
}
