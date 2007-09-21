/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.SessionUnavailableMessage;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.database.statustracker.Status;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.ServiceEvent;
import com.topcoder.service.ServiceHandleException;
import com.topcoder.service.ServiceHandler;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This class handles main service logic for the IM application. This class implements the ServiceHandler interface and
 * will be plugged into the ServiceEngine.
 * </p>
 * <p>
 * There are two phases of service logic in this class:
 * </p>
 * <p>
 * 1. To-be-serviced logic happens when both requester and responder is available for specific category.
 * </p>
 * <p>
 * 2. Serviced logic deals with the actual service logic.
 * </p>
 * <p>
 * Thread-safety: This class is thread-safe. Although the responderMap variable may be changed, but it is synchronized
 * and thus thread-safe.
 * </p>
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class IMServiceHandler implements ServiceHandler {

    /**
     * <p>
     * Represents the default namespace to load the configuration when no namespace is provided in the constructor.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.IMServiceHandler";

    /**
     * <p>
     * Represents the key for the user id.
     * </p>
     */
    public static final String USER_ID_KEY = "user_id";

    /**
     * <p>
     * Represents the key for the session id.
     * </p>
     */
    public static final String SESSION_ID_KEY = "session_id";

    /**
     * <p>
     * Represents the key to store category in the ServiceElement.
     * </p>
     */
    private static final String CATEGORY_KEY = "IMServiceHandler_Category_Key";

    /**
     * <p>
     * Represents the waiting time in milliseconds for a responder to accept the response.
     * </p>
     */
    private final long responderWaitTime;

    /**
     * <p>
     * Represents the time interval in milliseconds for the onToBeServiced() to sleep when no responder is found. It is
     * non-negative.
     * </p>
     */
    private final long checkResponseInterval;

    /**
     * <p>
     * Represents the chat session manager used in this handler. This is used to request user to the chat session and
     * add user to the chat session. It is not null.
     * </p>
     */
    private final ChatSessionManager chatSessionManager;

    /**
     * <p>
     * Represents the messenger used to send messages to the user or session pool. It is not null.
     * </p>
     */
    private final Messenger messenger;

    /**
     * <p>
     * Represents the chat session status tracker used in this handler. This is to change the session status to be OPEN
     * when the responder is found. It is not null.
     * </p>
     */
    private final ChatSessionStatusTracker chatSessionStatusTracker;

    /**
     * <p>
     * Represents the chat user status tracker used in this handler. This is used to change user status to be OFFLINE
     * when no responser is found for the service. It is not null.
     * </p>
     */
    private final ChatUserStatusTracker chatUserStatusTracker;

    /**
     * <p>
     * Represents the responder map. The key is requester and category(ServiceElement is used to store these two values)
     * and the value is responder(ServiceElement).
     * </p>
     */
    private final Map responderMap = Collections.synchronizedMap(new HashMap());

    /**
     * <p>
     * Represents the logger instance of this event listener. This variable is used to log the operation in the
     * statusChanged method.
     * </p>
     * <p>
     * If it is null, then no logging is done.
     * </p>
     */
    private final IMLogger logger;

    /**
     * <p>
     * Constructor to create service handler with given parameters. "responder_wait_time" and "check_response_interval"
     * are loaded from default namespace.
     * </p>
     * 
     * @param sessionManager chat session manager. Can't be null
     * @param messenger messenger used to send the message. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to update the session status. Can't be null.
     * @param userStatusTracker chat user status tracker used to update the user status. Can't be null.
     * @param logger logger used to log the messages. Can be null.
     * @exception IllegalArgumentException if any argument except the logger is null or the responderWaitTime or
     *            checkResponseInterval is negative.
     * @throws IMConfigurationException if the configuration is incorrect.
     */
    public IMServiceHandler(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker userStatusTracker, IMLogger logger)
        throws IMConfigurationException {
        this(sessionManager, messenger, sessionStatusTracker, userStatusTracker, logger, DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Construcotr to create service handler with given parameters. "responder_wait_time" and "check_response_interval"
     * are loaded from the given namespace.
     * </p>
     * 
     * @param sessionManager chat session manager. Can't be null
     * @param messenger messenger used to send the message. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to update the session status. Can't be null.
     * @param userStatusTracker chat user status tracker used to update the user status. Can't be null.
     * @param logger logger used to log the messages. Can be null.
     * @param namespace namespace to load the configuration. Can't be null or empty.
     * @exception IllegalArgumentException if any argument except the logger is null or the responderWaitTime or
     *            checkResponseInterval is negative, or the namespace is empty.
     * @throws IMConfigurationException if the configuration is incorrect.
     */
    public IMServiceHandler(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker userStatusTracker, IMLogger logger,
        String namespace) throws IMConfigurationException {
        this(sessionManager, messenger, sessionStatusTracker, userStatusTracker, getConfigLong(namespace,
            "responder_wait_time"), getConfigLong(namespace, "check_response_interval"), logger);
    }

    /**
     * Get configuration long value.
     * 
     * @param namespace namespace to get configuration from
     * @param prop the property name
     * @return the configured long value
     * @throws IMConfigurationException if any error occurred
     */
    private static long getConfigLong(String namespace, String prop) throws IMConfigurationException {
        IMHelper.checkString(namespace, "namespace");
        long res;
        try {
            res = Long.parseLong(IMHelper.getRequiredConfigString(namespace, prop));
        } catch (NumberFormatException e) {
            throw new IMConfigurationException("Invalid integer string.", e);
        }
        if (res < 0) {
            throw new IMConfigurationException("Check response interval can not be negative.");
        }
        return res;
    }

    /**
     * <p>
     * Construcotr to create service handler with given parameters.
     * </p>
     * 
     * @param sessionManager chat session manager. Can't be null
     * @param messenger messenger used to send the message. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to update the session status. Can't be null.
     * @param userStatusTracker chat user status tracker used to update the user status. Can't be null.
     * @param responderWaitTime responder waiting time when requesting the responder to service. Should not be negative.
     * @param checkResponseInterval time interval used to sleep the thread when checking the response. Can't be negative
     *        value.
     * @param logger logger used to log the messages. Can be null.
     * @exception IllegalArgumentException if any argument except the logger is null or the responderWaitTime or
     *            checkResponseInterval is negative.
     */
    public IMServiceHandler(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker userStatusTracker, long responderWaitTime,
        long checkResponseInterval, IMLogger logger) {
        IMHelper.checkNull(sessionManager, "sessionManager");
        IMHelper.checkNull(messenger, "messenger");
        IMHelper.checkNull(sessionStatusTracker, "sessionStatusTracker");
        IMHelper.checkNull(userStatusTracker, "userStatusTracker");
        if (checkResponseInterval < 0) {
            throw new IllegalArgumentException("Check response interval can not be negative.");
        }
        if (responderWaitTime < 0) {
            throw new IllegalArgumentException("Responder wait time can not be negative.");
        }
        this.chatSessionManager = sessionManager;
        this.chatSessionStatusTracker = sessionStatusTracker;
        this.chatUserStatusTracker = userStatusTracker;
        this.logger = logger;
        this.messenger = messenger;
        this.checkResponseInterval = checkResponseInterval;
        this.responderWaitTime = responderWaitTime;
    }

    /**
     * <p>
     * Invoked in the "To-Be-Serviced" phase. The logic is as following (such logic is run with a background thread):
     * </p>
     * <p>
     * 1. Request the responder to the session.
     * </p>
     * <p>
     * 2. If the responder does not accept the request in a configurable period of time, request the remaining
     * responders of the same category to the session.
     * </p>
     * <p>
     * 3. If still no responder accepts the request in that period of time, move it to the servicing state with no
     * responder.
     * </p>
     * 
     * @param serviceEvent service event object used. Can't be null.
     * @throws IllegalArgumentException if any argument is null.
     * @throws ServiceHandleException this exception is never thrown.
     */
    public void onToBeServiced(ServiceEvent serviceEvent) throws ServiceHandleException {
        IMHelper.checkNull(serviceEvent, "serviceEvent");

        // create new thread to do the work
        // no exception will be thrown here
        OnToBeServicedThread thread = new OnToBeServicedThread(serviceEvent);
        thread.start();
    }

    /**
     * This is a thread used in the onToBeServiced method to do preparation for services.
     * <p>
     * Thread-safety: This class is thread-safe because it is immutable.
     * </p>
     * 
     * @author justforplay, TCSDEVELOPER
     * @version 1.0
     */
    private class OnToBeServicedThread extends Thread {
        /**
         * The service event used in the body of this thread.
         */
        private final ServiceEvent serviceEvent;

        /**
         * Constructor to create a thread to do preparation for services.
         * 
         * @param serviceEvent the service event used in the body of this thread
         */
        public OnToBeServicedThread(ServiceEvent serviceEvent) {
            this.serviceEvent = serviceEvent;
        }

        /**
         * <p>
         * The logic is as following:
         * </p>
         * <p>
         * 1. Request the responder to the session.
         * </p>
         * <p>
         * 2. If the responder does not accept the request in a configurable period of time, request the remaining
         * responders of the same category to the session.
         * </p>
         * <p>
         * 3. If still no responder accepts the request in that period of time, move it to the servicing state with no
         * responder.
         * </p>
         */
        public void run() {
            try {
                // get sessionId, requesterUserId, responderUserId.
                long sessionId = ((Long) serviceEvent.getRequester().getProperty(SESSION_ID_KEY)).longValue();
                long requesterUserId = ((Long) serviceEvent.getRequester().getProperty(USER_ID_KEY)).longValue();
                long responderUserId = ((Long) serviceEvent.getResponder().getProperty(USER_ID_KEY)).longValue();
                logger.log(Level.INFO, "IMServiceHandler.onToBeServiced",
                    new long[] { requesterUserId, responderUserId }, new long[] { sessionId });

                ServiceElement key = new ServiceElement();
                key.setProperty(USER_ID_KEY, new Long(requesterUserId));
                key.setProperty(CATEGORY_KEY, serviceEvent.getCategory());
                long categoryId = serviceEvent.getCategory().getId();

                // get current time
                long beginTime = System.currentTimeMillis();
                // get chat session
                ChatSession chatSession = chatSessionManager.getSession(sessionId);
                // Ignore any exception in this step, which may come from the unavilable of
                // responder.
                try {
                    // request user to session
                    chatSessionManager.requestUserToSession(chatSession, responderUserId);
                    boolean first = true;
                    do {
                        // sleep for a while between tests
                        if (!first) {
                            Thread.sleep(checkResponseInterval);
                        }
                        first = false;

                        if (!RequestTracker.getInstance().isRequesterPresent(requesterUserId, categoryId)) {
                            // user has accepted
                            return;
                        }
                    } while (System.currentTimeMillis() - beginTime < responderWaitTime);
                } catch (Exception e) {
                    // log the exception, but do not block the rest steps
                    if (logger != null) {
                        logger.log(Level.ERROR, "Responder " + responderUserId + " fail to handle the request. "
                            + e.getMessage());
                    }
                }

                // Request all the responders in the same category together.
                // get current time
                beginTime = System.currentTimeMillis();
                ServiceElement[] responders = serviceEvent.getServiceEngine().getResponders(serviceEvent.getCategory());
                for (int i = 0; i < responders.length; i++) {
                    // The responder that has been tried before should be excluded in this loop.
                    // I.e. the responder from the ServiceEvent.getResponder() should be excluded.
                    long userId = ((Long) responders[i].getProperty(USER_ID_KEY)).longValue();
                    if (userId != responderUserId) {
                        chatSessionManager.requestUserToSession(chatSession, userId);
                    }
                }
                
                // http://bugs.topcoder.com/issue/ViewIssue.aspx?id=8837
                if (responders.length > 1) {
                    boolean first = true;
                    do {
                        // sleep for a while between tests
                        if (!first) {
                            Thread.sleep(checkResponseInterval);
                        }
                        first = false;
    
                        // Check whether the responder exists in the responderMap
                        if (!RequestTracker.getInstance().isRequesterPresent(requesterUserId, categoryId)) {
                            // requester has left or request has been served.
                            return;
                        }
    
                    } while (System.currentTimeMillis() - beginTime < responderWaitTime);
                }

                if (RequestTracker.getInstance().isRequesterPresent(requesterUserId, categoryId)) {
                    // requester is still waiting but no reponse from all managers.
                    serviceEvent.getServiceEngine().startService(serviceEvent.getRequester(), null,
                        serviceEvent.getCategory());
                }

            } catch (Exception e) {
                // log the exception
                if (logger != null) {
                    logger.log(Level.ERROR, "Fail to perform the onToBeServiced operation. " + e.getMessage());
                }
            }
        }
    }

    /**
     * <p>
     * This method is invoked when both requester and responder becomes availabe for the category. The logic is as
     * following:
     * </p>
     * <p>
     * Invoked when the service is started.
     * </p>
     * <p>
     * 1. If there is a responder, add the responder to session, and update the session status to OPEN. Post Session
     * Unavailable Message to other responders requested before. Depending on the implementation, this should also
     * notify the handler that some responder accepts the request.
     * </p>
     * <p>
     * 2. If there is no responder, post Session Unavailable Message to the requester to notify him service is not
     * available. Remove the requester from session. Change the user status to OFFLINE.
     * </p>
     * 
     * @param serviceEvent service event object used in this phase. Can't be null. And the responder in this
     *        ServiceEvent should be the one that is in the responderMap.
     * @throws IllegalArgumentException if the argument is null or the responder is not the one that is in the
     *         responderMap
     * @throws ServiceHandleException if any exception occurs.
     */
    public void onServiced(ServiceEvent serviceEvent) throws ServiceHandleException {
        IMHelper.checkNull(serviceEvent, "serviceEvent");

        // get sessionId, requesterUserId
        long sessionId = ((Long) serviceEvent.getRequester().getProperty(SESSION_ID_KEY)).longValue();
        long requesterUserId = ((Long) serviceEvent.getRequester().getProperty(USER_ID_KEY)).longValue();
        RequestTracker.getInstance().removeRequester(requesterUserId, serviceEvent.getCategory().getId());

        try {

            // get chat session
            ChatSession chatSession = chatSessionManager.getSession(sessionId);

            if (serviceEvent.getResponder() == null) {
                logger.log(Level.INFO, "IMServiceHandler.onServiced", new long[] { requesterUserId },
                    new long[] { sessionId });

                SessionUnavailableMessage unavailableMsg = new SessionUnavailableMessage();
                unavailableMsg.setSender(new Long(-1));
                unavailableMsg.setChatSessionId(sessionId);
                IMHelper.postMessage(messenger, unavailableMsg, requesterUserId, logger);
                if (logger != null) {
                    logger.log(Level.INFO, "Post SessionUnavailableMessage to User", new String[] { "User - "
                        + requesterUserId });
                }

            } else {
                // get responderUserId
                long responderUserId = ((Long) serviceEvent.getResponder().getProperty(USER_ID_KEY)).longValue();
                logger.log(Level.INFO, "IMServiceHandler.onServiced", new long[] { requesterUserId, responderUserId },
                    new long[] { sessionId });

                ServiceElement key = createKey(serviceEvent, requesterUserId);
                responderMap.put(key, serviceEvent.getResponder());

                synchronized (IMServiceHandler.class) {
                    if (chatSession.getActiveUsers().length == 1) {
                        chatSessionManager.addUserToSession(chatSession, responderUserId);
                        if (logger != null) {
                            logger.log(Level.INFO, "Add User to Session", new String[] { "Session - " + sessionId,
                                "User - " + responderUserId });
                        }

                        // we need to reload the chat session, for it is changed after we add user to
                        // session.
                        chatSession = chatSessionManager.getSession(sessionId);

                        // 203 - OPEN
                        Status openStatus = new Status(IMHelper.SESSION_STATUS_OPEN);
                        chatSessionStatusTracker.setStatus(sessionId, openStatus);
                        if (logger != null) {
                            logger.log(Level.INFO, "Change Session Status to OPEN", new String[] { "Session - "
                                + sessionId });
                        }
                    } else if (chatSession.getActiveUsers().length == 2) {
                        if (logger != null) {
                            logger.log(Level.INFO, "Doing nothing, session already has 2 users",
                                new String[] { "Session - " + sessionId });
                        }
                        return;
                    } else {
                        if (logger != null) {
                            logger.log(Level.WARN, "Expecting 1 or 2 users in session but found: "
                                + chatSession.getActiveUsers().length, new String[] { "Session - " + sessionId });
                        }
                    }
                }
            }

            long resp = -1;
            if (serviceEvent.getResponder() != null) {
                resp = ((Long) serviceEvent.getResponder().getProperty(USER_ID_KEY)).longValue();
            }
        
            long[] requestedUsers = chatSession.getRequestedUsers();
            for (int i = 0; i < requestedUsers.length; i++) {
                SessionUnavailableMessage unavailableMsg2 = new SessionUnavailableMessage();
                unavailableMsg2.setSender(new Long(-1));
                unavailableMsg2.setChatSessionId(sessionId);
                if (resp != requestedUsers[i]) {
                    IMHelper.postMessage(messenger, unavailableMsg2, requestedUsers[i], logger);
                    if (logger != null) {
                        logger.log(Level.INFO, "Post SessionUnavailableMessage to User", new String[] { "User - "
                            + requestedUsers[i] });
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            if (logger != null) {
                logger.log(Level.ERROR, e.getMessage());
            }
            e.printStackTrace();
            // rethrow it
            throw e;
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.ERROR, e.getMessage());
            }
            e.printStackTrace();

            // wrap it
            throw new ServiceHandleException("Fail to perform the onServiced operation.", e);
        }
    }

    private ServiceElement createKey(ServiceEvent serviceEvent, long requesterUserId) {
        ServiceElement key = new ServiceElement();
        key.setProperty(USER_ID_KEY, new Long(requesterUserId));
        key.setProperty(CATEGORY_KEY, serviceEvent.getCategory());
        return key;
    }

    /**
     * <p>
     * Gets the corresponding responder for the requester and category.
     * </p>
     * <p>
     * Null value is returned if no responder is found.
     * </p>
     * 
     * @param requester requester of the service. Can't be null.
     * @param category category of the service. Can't be null.
     * @return responder of the service for the requester and category. Null is returned if no responder is found.
     * @exception IllegalArgumentException if the requester or category is null.
     */
    public ServiceElement getResponder(ServiceElement requester, Category category) {
        IMHelper.checkNull(requester, "requester");
        IMHelper.checkNull(category, "category");

        ServiceElement key = new ServiceElement();
        key.setProperty(USER_ID_KEY, requester.getProperty(USER_ID_KEY));
        key.setProperty(CATEGORY_KEY, category);
        return (ServiceElement) responderMap.get(key);
    }

    /**
     * Sets the corresponding responder for the requester and category.
     * 
     * @param requester requester of the service. Can't be null.
     * @param category category of the service. Can't be null.
     * @param responder actual responder of this service handler for the requester and category.
     * @exception IllegalArgumentException if any argument is null.
     */
    public void setResponder(ServiceElement requester, Category category, ServiceElement responder) {
        IMHelper.checkNull(requester, "requester");
        IMHelper.checkNull(category, "category");
        IMHelper.checkNull(responder, "responder");

        ServiceElement key = new ServiceElement();
        key.setProperty(USER_ID_KEY, requester.getProperty(USER_ID_KEY));
        key.setProperty(CATEGORY_KEY, category);
        responderMap.put(key, responder);
    }

    /**
     * <p>
     * Gets chat session manager instance used in this service handler.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * @return chat session manager instance used in this service handler.
     */
    public ChatSessionManager getChatSessionManager() {
        return this.chatSessionManager;
    }

    /**
     * <p>
     * Gets messager used to send messages in this service handler.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * @return messenger used in this service handler.
     */
    public Messenger getMessenger() {
        return this.messenger;
    }

    /**
     * <p>
     * Gets chat session status tracker used in this service handler.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * @return chat session status tracker used in this service handler.
     */
    public ChatSessionStatusTracker getChatSessionStatusTracker() {
        return this.chatSessionStatusTracker;
    }

    /**
     * <p>
     * Gets chat user status tracker used in this service handler.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * @return chat user status tracker used in this service handler.
     */
    public ChatUserStatusTracker getChatUserStatusTracker() {
        return this.chatUserStatusTracker;
    }

    /**
     * <p>
     * Gets responder wait time in milliseconds. This is the waiting time for a responder to accept the requester.
     * </p>
     * <p>
     * The return value will be not-negative long value.
     * </p>
     * 
     * @return responder wait time.
     */
    public long getResponderWaitTime() {
        return this.responderWaitTime;
    }

    /**
     * <p>
     * Gets time interval in milliseconds for the thread to sleep when checking whether a responder is found.
     * </p>
     * <p>
     * The return value will be not-negative long value.
     * </p>
     * 
     * @return time interval in milliseconds for the thread to sleep when checking whether a responder is found
     */
    public long getCheckResponseInterval() {
        return this.checkResponseInterval;
    }

    /**
     * <p>
     * Clears all the responders that are cached in this ServiceHandler.
     * </p>
     */
    public void clearAllResponders() {
        this.responderMap.clear();
    }
}
