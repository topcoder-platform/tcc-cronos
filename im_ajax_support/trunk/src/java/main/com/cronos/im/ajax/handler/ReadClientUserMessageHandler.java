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
import com.topcoder.service.Category;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.ServiceEngine;
import com.topcoder.util.log.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;

/**
 * <p>
 * This handler is only for users with client role. The handler will pull the pending messages from the user
 * message pool. In addition, the current position of the client (under the selected category) in the service
 * engine is included in the response. The request message for this handler is defined in
 * docs/ReadClientUserMessage.xsd. The response schema is given in the algorithm.
 * </p>
 * 
 * 
 * <p>
 * This class is thread safe since it's immutable.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ReadClientUserMessageHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     * 
     */
    public ReadClientUserMessageHandler() {
    }

    /**
     * Read user message for client role. The xmlRequest must conform to the schema defined in algorithm.
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

            long userId = profile.getId();
            // 2. get the messenger and message pool
            Messenger messenger = (Messenger) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getIMMessengerKey());
            MessagePool pool = messenger.getMessagePool();
            // 3. get the role name
            String[] roleNames = profile.getPropertyValue(IMAjaxSupportUtility.getRolePropertyKey());
            List roles = Arrays.asList(roleNames);
            if (!roles.contains(IMAjaxSupportUtility.getClientRoleName())) {
                res.getWriter().write("<response><failure>invalid role</failure></response>");
                return;
            }
            // 4. get the category
            Category category = (Category) req.getSession().getAttribute(
                    IMAjaxSupportUtility.getCategorySessionKey());
            // 5. get the session id and the serviceEngine
            long sessionId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "session_id"));
            ServiceEngine engine = (ServiceEngine) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getServiceEngineKey());
            // 6. create a request
            ServiceElement requester = new ServiceElement();
            requester.setProperty("user_id", new Long(userId));
            requester.setProperty("session_id", new Long(sessionId));
            long position = engine.indexOfRequester(requester, category);
            // 7 pull the message
            Message[] msgs = pool.pull(userId);
            StringBuffer responseTextSB = new StringBuffer();
            responseTextSB.append("<response><success>successfully</success><messages>");
            DateFormatContext formatContext = new DateFormatContext();
            for (int i = 0; i < msgs.length; i++) {
                responseTextSB.append(((XMLMessage) msgs[i]).toXMLString(formatContext));
            }
            responseTextSB.append("</messages><client_position>");
            responseTextSB.append(position);
            responseTextSB.append("</client_position></response>");
            res.getWriter().write(responseTextSB.toString());
            // Log the handler, see algorithm section.
            StringBuffer logMsgSB = new StringBuffer();
            logMsgSB.append(IMHelper.getLoggingHeader(userId));
            String logMsg = logMsgSB.toString();
            this.getLog().log(Level.DEBUG, logMsg);
        } catch (Exception e) {
            e.printStackTrace();
            IMHelper.writeFailureResponse(res);
        }
    }

}
