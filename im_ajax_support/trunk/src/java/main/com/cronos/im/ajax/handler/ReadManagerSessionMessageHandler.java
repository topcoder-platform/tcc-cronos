/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.IMHelper;
import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.XMLMessage;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.util.log.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

/**
 * <p>
 * This handler is only for users with manager role. The xml request message includes the session id. The
 * servlet will pull the pending messages from the session message pool of the user. The request message for
 * this handler is defined in docs/ReadManagerSessionMessage.xsd. The response schema is given in the
 * algorithm.
 * </p>
 * 
 * <p>
 * This class is thread safe since it's immutable.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ReadManagerSessionMessageHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     */
    public ReadManagerSessionMessageHandler() {
    }

    /**
     * Read user session message for manager or client role.
     * 
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
            // 1. get user profile
            ChatUserProfile profile = IMHelper.getProfile(req, res, getLog());

            if (profile == null) {
                return;
            }

            // 2. get the messenger and message pool
            Messenger messenger = (Messenger) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getIMMessengerKey());
            MessagePool pool = messenger.getMessagePool();
            // 3. get the role name
            String[] roleNames = profile.getPropertyValue(IMAjaxSupportUtility.getRolePropertyKey());
            // 4. check the role
            List roles = Arrays.asList(roleNames);
            if (!roles.contains(IMAjaxSupportUtility.getClientRoleName())
                    && !roles.contains(IMAjaxSupportUtility.getManagerRoleName())) {
                res.getWriter().write("<response><failure>invalid role</failure></response>");
                return;
            }
            // 5. get the user id from user profile, and get the session id from the
            // xmlRequest element
            long userId = profile.getId();
            long sessionId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "session_id"));
            // 6. pull the messages
            Message[] msgs = IMHelper.pull(req, pool, userId, sessionId, getLog());

            StringBuffer responseTextSB = new StringBuffer();
            responseTextSB.append("<response><success>successfully</success><messages>");
            DateFormatContext formatContext = new DateFormatContext();
            for (int i = 0; i < msgs.length; i++) {
                responseTextSB.append(((XMLMessage) msgs[i]).toXMLString(formatContext));
            }
            responseTextSB.append("</messages></response>");
            res.getWriter().write(responseTextSB.toString());
            // Log the handler, see algorithm section.
            StringBuffer logMsgSB = new StringBuffer();
            logMsgSB.append(IMHelper.getLoggingHeader(userId));
            logMsgSB.append(" affected entityIDs: sessionId ");
            logMsgSB.append(sessionId);
            String logMsg = logMsgSB.toString();
            this.getLog().log(Level.DEBUG, logMsg);
        } catch (Exception e) {
            e.printStackTrace();
            IMHelper.writeFailureResponse(res);
        }
    }

}
