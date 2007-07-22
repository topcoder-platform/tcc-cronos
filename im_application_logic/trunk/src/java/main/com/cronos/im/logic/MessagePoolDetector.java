/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.database.statustracker.Status;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.SearchResult;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.service.ServiceEngine;
import com.topcoder.service.ServiceElement;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.log.Level;
import java.util.List;
import java.util.Iterator;
import java.text.DateFormat;

/**
 * This class provide command line interface to detect inactivity of the message pools. It searches for the
 * message pools that have not been pulled in a configurable time period. Below is the logic when the message
 * pool is inactive:
 * <p>
 * 1. If session message pool is inactive, Remove user from session
 * </p>
 * <p>
 * 2. If user message pool is inactive,
 * </p>
 * <p>
 * 1) Remove the user as requester and responder from the service engine
 * </p>
 * <p>
 * 2) Remove user from all sessions
 * </p>
 * <p>
 * 3) Change user status to OFFLINE
 * </p>
 * <p>
 * The Job Scheduler will be used to run the job for inactivity detection at configurable intervals. The
 * run_interval is also configured in the same namespace as the inactive_time_interval property. But it is not
 * used in this component, it is used by the Job Scheduler to run the inactivity detection.
 * </p>
 * <p>
 * This class is thread-safe as it is immutable.
 * </p>
 * 
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class MessagePoolDetector {

    /**
     * <p>
     * Represents the default namespace used to load the configuration.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.MessagePoolDetector";

    /**
     * <p>
     * Private constructor to prevent direct instantiation.
     * </p>
     */
    private MessagePoolDetector() {
    }

    /**
     * <p>
     * This is the main entry of command line interface of MessagePoolDetector. This method is responsible for
     * parsing the command line and initializing the varibles and do the job.
     * </p>
     * <p>
     * The acceptable command line parameters are:
     * </p>
     * <p>
     * 1) -namespace : namespace to load the configuration. If not specified, then DEFAULT_NAMESPACE is used
     * </p>
     * <p>
     * 2) -help or -h : print help message of this command
     * </p>
     * 
     * @param args
     *            arguements passed from the command line.
     */
    public static void main(String[] args) {
        IMLogger logger = null;
        try {
            // Prepare command line parser
            CommandLineUtility clu = new CommandLineUtility();
            clu.addSwitch(new Switch("namespace", false, 0, 1, null));
            clu.addSwitch(new Switch("h", false, 0, 0, null));
            clu.addSwitch(new Switch("help", false, 0, 0, null));
            // Parse the command line switches
            clu.parse(args);
            // Get a list of valid switches
            List switches = clu.getValidSwitches();
            // the namespace option
            String ns = null;
            for (Iterator it = switches.iterator(); it.hasNext();) {
                Switch sw = (Switch) it.next();
                // If helpSwitch is selected, print out a usage message
                if ("h".equals(sw.getName()) || "help".equals(sw.getName())) {
                    printHelperMsg();
                    return;
                } else if ("namespace".equals(sw.getName())) {
                    // get the namespace option
                    ns = sw.getValue();
                }
            }
            if (ns == null) {
                // use default namespace
                ns = DEFAULT_NAMESPACE;
            }
            // Create object factory
            String ofns = IMHelper.getRequiredConfigString(ns, "factory_namespace");
            if (ofns == null) {
                ofns = DEFAULT_NAMESPACE;
            }
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(ofns));
            // Load "service_engine_namespace", "chat_user_status_tracker_namespace" properties
            String sens = IMHelper.getOptionalConfigString(ns, "service_engine_namespace");
            String custns = IMHelper.getOptionalConfigString(ns, "chat_user_status_tracker_namespace");
            // Create serviceEngine and chatUserStatusTracker
            ServiceEngine serviceEngine = sens == null ? new ServiceEngine() : new ServiceEngine(sens);
            ChatUserStatusTracker chatUserStatusTracker = custns == null ? new ChatUserStatusTracker()
                    : new ChatUserStatusTracker(custns);
            // Create the ChatSessionManager instance
            String csmKey = IMHelper.getRequiredConfigString(ns, "chat_session_manager_key");
            String csmIdentifier = IMHelper.getOptionalConfigString(ns, "chat_session_manager_identifier");
            ChatSessionManager chatSessionManager = (ChatSessionManager) (csmIdentifier == null ? of
                    .createObject(csmKey) : of.createObject(csmKey, csmIdentifier));
            // Create the MessagePool instance
            String msgPoolKey = IMHelper.getRequiredConfigString(ns, "message_pool_key");
            String msgPoolIdentifier = IMHelper.getOptionalConfigString(ns, "message_pool_identifier");
            MessagePool messagePool = (MessagePool) (msgPoolIdentifier == null ? of.createObject(msgPoolKey)
                    : of.createObject(msgPoolKey, msgPoolIdentifier));
            // Load inactive_time_interval
            long inactiveTimeInterval;
            try {
                inactiveTimeInterval = Long.parseLong(IMHelper.getRequiredConfigString(ns,
                        "inactive_time_interval"));
            } catch (NumberFormatException e) {
                throw new IMConfigurationException("Inactive time interval string is invalid integer string.");
            }
            if (inactiveTimeInterval < 0) {
                throw new IMConfigurationException("Inactive time interval can not be negative.");
            }
            // Load "whether_log" property
            String whetherLog = IMHelper.getOptionalConfigString(ns, "whether_log");
            if (whetherLog != null && !"Yes".equalsIgnoreCase(whetherLog)
                    && !"No".equalsIgnoreCase(whetherLog)) {
                throw new IMConfigurationException("The 'whether_log' property must be either 'Yes' or 'No'.");
            }
            if (!"No".equalsIgnoreCase(whetherLog)) {
                String logName = IMHelper.getOptionalConfigString(ns, "log_name");
                DateFormat df = null;
                // create date format
                String dateFormatKey = IMHelper.getOptionalConfigString(ns, "date_format_key");
                if (dateFormatKey != null) {
                    String dateFormatIdentifier = IMHelper.getOptionalConfigString(ns,
                            "date_format_identifier");
                    df = (DateFormat) (dateFormatIdentifier == null ? of.createObject(dateFormatKey) : of
                            .createObject(dateFormatKey, dateFormatIdentifier));
                }
                // create logger
                logger = logName == null ? new IMLogger(df) : new IMLogger(logName, df);
            }
            // Invoke detectInactiveMessagePool method
            detectInactiveMessagePool(serviceEngine, chatUserStatusTracker, chatSessionManager, messagePool,
                    inactiveTimeInterval, logger);
        } catch (Exception e) {
            e.printStackTrace();
            if (logger != null) {
                logger.log(Level.ERROR, "Fail to run the command: " + e.getMessage());
            }
        }
    }

    /**
     * Print out help message.
     */
    private static void printHelperMsg() {
        System.out.println("-namespace:  namespace to load the configuration.");
        System.out.println("             if not specified, com.cronos.im.logic.MessagePoolDetector is used");
        System.out.println("-help or -h: print help message");
    }

    /**
     * <p>
     * Detect the inactive message pool and do the following logics.
     * </p>
     * <p>
     * If session message pool is inactive,
     * </p>
     * <p>
     * (1) Remove user from session
     * </p>
     * <p>
     * If user message pool is inactive,
     * </p>
     * <p>
     * (1) Remove the user as requester and responder from the service engine
     * </p>
     * <p>
     * (2) Remove user from all sessions
     * </p>
     * <p>
     * (3) Change user status to OFFLINE
     * </p>
     * <p>
     * Note: Any exception in this method is thrown to the Main function directly.
     * </p>
     * 
     * 
     * @param serviceEngine
     *            service engine used to remove the responder. Can't be null.
     * @param userStatusTracker
     *            user status tracker used to change the user status. Can't be null.
     * @param sessionManager
     *            chat session manager used to remove user from the session. Can't be null.
     * @param messagePool
     *            message pool used to search the inactive message pool. Can't be null.
     * @param timeInterval
     *            time value to judge whether one message pool is inactive. Can't be null.
     * @param logger
     *            logger used to log the message. Can be null.
     * @throws IllegalArgumentException
     *             if any parameter except the logger is null.
     * @throws Exception
     *             to the main method
     */
    private static void detectInactiveMessagePool(ServiceEngine serviceEngine,
            ChatUserStatusTracker userStatusTracker, ChatSessionManager sessionManager,
            MessagePool messagePool, long timeInterval, IMLogger logger) throws Exception {
        IMHelper.checkNull(serviceEngine, "serviceEngine");
        IMHelper.checkNull(userStatusTracker, "userStatusTracker");
        IMHelper.checkNull(sessionManager, "sessionManager");
        IMHelper.checkNull(messagePool, "messagePool");

        // Search the inactive session message pool
        // not pulled for long time
        SearchResult[] result = messagePool.searchLastSessionPoolActivity(false, System.currentTimeMillis()
                - timeInterval, true);
        // for each inactive session pool
        for (int i = 0; i < result.length; i++) {
            // result[i] is inactive session message pool
            // remove user from session
            ChatSession chatSession = sessionManager.getSession(result[i].getSession());
            sessionManager.removeUserFromSession(chatSession, result[i].getUser());
            if (logger != null) {
                logger.log(Level.INFO, "Remove User From Session", new String[] { "User - "
                        + result[i].getUser() });
            }
        }

        // Search the inactive user message pools
        // not pulled for long time
        result = messagePool.searchLastUserPoolActivity(false, System.currentTimeMillis() - timeInterval,
                true);
        
        // for each inactive user message pool
        for (int i = 0; i < result.length; i++) {
            // result[i] is inactive user message pool
            // Remove the user as requester and responder from the service engine
            ServiceElement user = new ServiceElement();
            user.setProperty(IMServiceHandler.USER_ID_KEY, new Long(result[i].getUser()));
            SearchResult[] userSessions = messagePool.searchLastSessionPoolActivity(false, result[i].getUser(), System
                .currentTimeMillis() - timeInterval, true);

            // remove requester from all his sessions
            for (int j = 0; j < userSessions.length; j++) {
                user.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(userSessions[j].getSession()));
                try {
                    serviceEngine.removeRequester(user);
                    if (logger != null) {
                        logger.log(Level.INFO, "Remove Requester ", new String[] { "User - " + result[i].getUser() });
                    }
                } catch (Exception e) {
                    if (logger != null) {
                        logger.log(Level.ERROR, e.getMessage());
                    }
                    e.printStackTrace();
                }
            }
            
            try {
                serviceEngine.removeResponder(user);
            } catch (Exception e) {
                if (logger != null) {
                    logger.log(Level.ERROR, e.getMessage());
                }
                e.printStackTrace();
            }
            // Remove user from all sessions
            sessionManager.removeUserFromSessions(result[i].getUser());
            if (logger != null) {
                logger.log(Level.INFO, "Remove User From Sessions", new String[] { "User - "
                        + result[i].getUser() });
            }
            // Change user status to OFFLINE
            Status offlineStatus = new Status(IMHelper.USER_STATUS_OFFLINE); // 103 - OFFLINE
            userStatusTracker.setStatus(result[i].getUser(), offlineStatus);
            if (logger != null) {
                logger.log(Level.INFO, "Update User Status to be OFFLINE", new String[] { "User - "
                        + result[i].getUser() });
            }
        }
    }
}
