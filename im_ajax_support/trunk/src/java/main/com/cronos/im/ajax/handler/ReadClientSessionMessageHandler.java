/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

/**
 * <p>
 * This handler is only for users with client role. The xml request message includes the session id. The
 * handler will pull the pending messages from both the user message pool and session message pool. The
 * request message for this handler is defined in docs/ReadClientSessionMessage.xsd. The response schema is
 * given in the algorithm.
 * </p>
 * 
 * <p>
 * This class is thread safe since it's immutable.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ReadClientSessionMessageHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     * 
     */
    public void ReadManagerUserMessageHandler() {
    }

    /**
     * Read user session message for client role.
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
            if (!roles.contains(IMAjaxSupportUtility.getClientRoleName())) {
                res.getWriter().write("<response><failure>invalid role</failure></response>");
                return;
            }
            // 5. get the user id and session id
            long userId = profile.getId();
            long sessionId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "session_id"));
            // 6. pull the messages from user/session pool
            StringBuffer responseTextSB = new StringBuffer();
            responseTextSB.append("<response><success>successfully</success><messages>");
            DateFormatContext formatContext = new DateFormatContext();
            // fix for TCIM-9226
            // result is being ignored here
            // the purpose is to have the last update time to be "Updated"
            pool.pull(userId);

            Message[] msgs = IMHelper.pull(req, pool, userId, sessionId, getLog());

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
