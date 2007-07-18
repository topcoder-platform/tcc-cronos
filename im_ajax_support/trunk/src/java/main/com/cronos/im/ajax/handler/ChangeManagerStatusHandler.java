/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.IMHelper;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.service.Category;
import com.topcoder.chat.status.ChatStatusTracker;
import com.topcoder.database.statustracker.Status;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.ServiceEngine;
import com.topcoder.util.log.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 * This handler is only for users with manager role. The request parameter includes the status. The handler
 * will change the user status. If it is ONLINE, the hande will enqueue the manager to the service engine for
 * each of the selected categories. Conversely, if it is BUSY, the handler will dequeue the manager from the
 * service engine. The request message for this handler is defined in docs/ChangeManagerStatusHandler.xsd. The
 * response schema is given in the algorithm.
 * </p>
 * 
 * <p>
 * This class is thread safe since it¡¯s immutable.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ChangeManagerStatusHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     */
    public ChangeManagerStatusHandler() {
    }

    /**
     * change the manager status.
     * 
     * @param xmlRequest
     *            the xml request element
     * @param req
     *            the http request
     * @param res
     *            the http response
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws IOException
     *             if I/O error occurs
     */
    public void handle(Element xmlRequest, HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        IMHelper.checkNull(xmlRequest, "xmlRequest");
        IMHelper.checkNull(req, "req");
        IMHelper.checkNull(res, "res");
        try {
            // 1. get the user profile
            String profileKey = IMAjaxSupportUtility.getUserProfileSessionKey();
            ChatUserProfile profile = (ChatUserProfile) req.getSession().getAttribute(profileKey);
            // 2. get the messenger and message pool
            // not used: Messenger messenger = (Messenger) req.getSession().getServletContext().getAttribute(
            // IMAjaxSupportUtility.getIMMessengerKey());
            // not used : MessagePool pool = messenger.getMessagePool();
            // 3. get the user id from user profile
            long userId = profile.getId();
            // 4. get the role name
            String[] roleNames = profile.getPropertyValue(IMAjaxSupportUtility.getRolePropertyKey());
            if (!Arrays.asList(roleNames).contains(IMAjaxSupportUtility.getManagerRoleName())) {
                res.getWriter().write("<response><failure>invalid role</failure></response>");
                return;
            }
            // 5. get the AbstractChatStatusTracker
            ChatStatusTracker tracker = (ChatStatusTracker) req.getSession().getServletContext()
                    .getAttribute(IMAjaxSupportUtility.getChatStatusTrackerKey());
            // 6. get the status from xmlRequest element.
            // the status must be the follwing three values(case insensive):
            // Online
            // Busy
            // Offline
            // If the status is not one of the above values, create a failure xml
            // response and set the response on the http response, and return.
            // Create the Status object from the status name.
            // 101 - Online
            // 102 - Busy
            // 103 - Offline
            // and call AbstractChatStatusTracker.setStatus(userId, status) to
            // update the status
            String status = IMHelper.getSubElementContent(xmlRequest, "status");
            long statusId;
            if ("Online".equalsIgnoreCase(status)) {
                statusId = 101;
            } else if ("Busy".equalsIgnoreCase(status)) {
                statusId = 102;
            } else if ("Offline".equalsIgnoreCase(status)) {
                statusId = 103;
            } else {
                res.getWriter().write(
                        "<response><failure>status must be Online, Busy or Offline</failure></response>");
                return;
            }
            Status status2 = new Status(statusId);
            status2.setName(status);
            tracker.setStatus(userId, status2);
            // 7. get the serviceEngine
            ServiceEngine engine = (ServiceEngine) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getServiceEngineKey());
            // 8. get the category array from http session
            Category[] categories = (Category[]) req.getSession().getAttribute("selectedCategories");
            ServiceElement responder = new ServiceElement();
            responder.setProperty("user_id", new Long(userId));
            if (categories != null) {
                // process each category
                for (int i = 0; i < categories.length; i++) {
                    if (statusId == 101) {
                        // online
                        engine.addResponder(responder, categories[i]);
                    } else if (statusId == 102) {
                        // busy
                        engine.removeResponder(responder, categories[i]);
                    }
                }
            }
            // return success message
            res.getWriter().write(
                    "<response><success>the manager status is successfully changed</success></response>");
            // Logging
            StringBuffer logMsgSB = new StringBuffer();
            logMsgSB.append(IMHelper.getLoggingHeader(userId));
            logMsgSB.append(" action:ChangeManagerStatus");
            logMsgSB.append(" affected entityIDs: statusId ");
            logMsgSB.append(statusId);
            logMsgSB.append(" category ids");
            for (int loop = 0; loop < categories.length; loop++) {
                logMsgSB.append(" " + categories[loop].getId());
            }
            String logMsg = logMsgSB.toString();
            this.getLog().log(Level.INFO, logMsg);
        } catch (Exception e) {
            res.getWriter().write(
                    "<response><failure>Error occured during handling the request</failure></response>");
        }
    }

}
