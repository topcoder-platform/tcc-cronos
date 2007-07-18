/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.IMHelper;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.SalesSession;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceEngine;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;
import com.topcoder.util.log.Level;
import java.io.IOException;

/**
 * <p>
 * The xml request message includes the request user id and session id. The handler will update the state in
 * the service engine to service for that session. The response will indicate whether the operation succeeds
 * or not. The request message for this handler is defined in docs/AccetChatRequestHandler.xsd. The response
 * schema is given in the algorithm.
 * </p>
 * 
 * <p>
 * This class is thread safe since it¡¯s immutable.
 * </p>
 * 
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AcceptChatRequestHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     * 
     */
    public AcceptChatRequestHandler() {
    }

    /**
     * Accpet chat request.
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
            // 1. get the http session from the request, and get the user profile from session
            String profileKey = IMAjaxSupportUtility.getUserProfileSessionKey();
            ChatUserProfile profile = (ChatUserProfile) req.getSession().getAttribute(profileKey);
            // 2. get the messenger and message pool
            // not used: Messenger messenger = (Messenger) req.getSession().getServletContext().getAttribute(
            // IMAjaxSupportUtility.getIMMessengerKey());
            // not used: MessagePool pool = messenger.getMessagePool();
            // 3. get the role name
            // not used: String[] roleNames =
            // profile.getPropertyValue(IMAjaxSupportUtility.getRolePropertyKey());
            // 5. get the user id from user profile, and create a responder
            
            // 6. get the session id and user id from xmlRequest element and create a requester
            long sessionId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "session_id"));
            long uId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "user_id"));
            ServiceElement requester = new ServiceElement();
            requester.setProperty("user_id", new Long(uId));
            requester.setProperty("session_id", new Long(sessionId));
            // 7. get the chatSessionManager
            String chatSessionMgrKey = IMAjaxSupportUtility.getChatSessionManagerKey();
            ChatSessionManager chatSessionMgr = (ChatSessionManager) req.getSession().getServletContext()
                    .getAttribute(chatSessionMgrKey);
            // 8. get the ChatSession
            ChatSession chatSession = chatSessionMgr.getSession(sessionId);
            long categoryId = ((SalesSession) chatSession).getCategoryId();
            String categoryName = ((SalesSession) chatSession).getCategoryName();
            Category category = new Category(categoryId, categoryName);
            // 9. get the serviceEngine
            ServiceEngine engine = (ServiceEngine) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getServiceEngineKey());
            
            long userId = profile.getId();
            ServiceElement responder = new ServiceElement();
            responder.setProperty("user_id", new Long(userId));

            engine.startService(requester, responder, category);
            // 10. construct success xml response, and set it on the http response
            res.getWriter().write("<response><success>the chat request is accepted</success></response>");
            // Logging
            StringBuffer logMsgSB = new StringBuffer();
            logMsgSB.append(IMHelper.getLoggingHeader(userId));
            logMsgSB.append(" action:AcceptChatRequest");
            logMsgSB.append(" affected entityIDs: requestUserId ");
            logMsgSB.append(uId);
            logMsgSB.append(" sessionId ");
            logMsgSB.append(sessionId);
            logMsgSB.append(" category id ");
            logMsgSB.append(categoryId);
            String logMsg = logMsgSB.toString();
            this.getLog().log(Level.INFO, logMsg);
        } catch (Exception e) {
            res.getWriter().write(
                    "<response><failure>Error occured during handling the request</failure></response>");
        }
    }

}
