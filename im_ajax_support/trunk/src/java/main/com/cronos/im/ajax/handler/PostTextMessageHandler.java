/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.IMHelper;
import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.DateFormatContext;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.XMLMessage;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.util.log.Level;

/**
 * <p>
 * The xml request message includes the session id and text. The handler will post the submitted message to
 * all users of the session. In addition, the handlerwill pull the pending messages from the session message
 * pool of the user and return them in the response. The request message for this handler is defined in
 * docs/PostTextMessageHandler.xsd. The response schema is given in the algorithm.
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
public class PostTextMessageHandler extends AbstractRequestHandler {

    /**
     * Empty constructor.
     */
    public PostTextMessageHandler() {
    }

    /**
     * post chat message for client or manager role.
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
        Date date = new Date();

        try {
            // 1. get user profile
            ChatUserProfile profile = IMHelper.getProfile(req, res, getLog());

            if (profile == null) {
                return;
            }

            // 2. get the messenger and message pool
            Messenger messenger = (Messenger) req.getSession().getServletContext().getAttribute(
                    IMAjaxSupportUtility.getIMMessengerKey());
            // 3. get the user id from user profile
            long userId = profile.getId();
            // 4. get the session id from xmlRequest element
            long sessionId = Long.parseLong(IMHelper.getSubElementContent(xmlRequest, "session_id"));
            // 5. get the chat text from xmlRequest element
            String chatText = IMHelper.getSubElementContent(xmlRequest, "chat_text");

            // if there is no chat text, silently ignore the request
            if (chatText == null || chatText.trim().length() == 0) {
                return;
            }

            // 6. create ChatMessage, set the chat message fields as follow
            // sender --> new Long(userId) (in step 3)
            // timestamp --> current time
            // sessiondId --> session id (in step 4)
            // chat text -- chat text (in step 5)
            
            ChatMessage chatMsg = new ChatMessage();
            chatMsg.setChatProfileProperties(getProperties(profile));
            chatMsg.setSender(new Long(userId));
            chatMsg.setTimestamp(new Date());
            chatMsg.setChatSessionId(sessionId);
            chatMsg.setChatText(chatText);
            
            // 7. get the chatSessionManager
            String chatSessionMgrKey = IMAjaxSupportUtility.getChatSessionManagerKey();
            ChatSessionManager chatSessionMgr = (ChatSessionManager) req.getSession().getServletContext()
                    .getAttribute(chatSessionMgrKey);
            // 8. get the ChatSession
            ChatSession chatSession = chatSessionMgr.getSession(sessionId);

            IMHelper.postMessageToAll(messenger, chatMsg, chatSession, getLog());

            // 9. pull the messages from the messagePool
            // fix for TCIM-9226
            // the message should be pulled from User's Session's Pool
            MessagePool pool = messenger.getMessagePool();
            Message[] msgs = IMHelper.pull(req, pool, userId, sessionId, getLog());

            StringBuffer responseTextSB = new StringBuffer();
            responseTextSB.append("<response><success>the text is posted</success><messages>");
            DateFormatContext formatContext = new DateFormatContext();
            for (int i = 0; i < msgs.length; i++) {
                if (msgs[i] instanceof XMLMessage) {
                    responseTextSB.append(((XMLMessage) msgs[i]).toXMLString(formatContext));
                } else {
                    responseTextSB.append(msgs[i].toString());
                }
            }
            responseTextSB.append("</messages></response>");
            res.getWriter().write(responseTextSB.toString());
            // Log the handler, see algorithm section.
            StringBuffer logMsgSB = new StringBuffer();
            logMsgSB.append(IMHelper.getLoggingHeader(userId));
            logMsgSB.append(" affected entityIDs: sessionId ");
            logMsgSB.append(sessionId);
            logMsgSB.append(" chat text: ");
            logMsgSB.append(chatText);
            logMsgSB.append(" processing time: ");
            logMsgSB.append(new Date().getTime() - date.getTime());
            logMsgSB.append("ms");
            String logMsg = logMsgSB.toString();
            this.getLog().log(Level.DEBUG, logMsg);
        } catch (Exception e) {
            this.getLog().log(Level.ERROR, e.toString());
            IMHelper.writeFailureResponse(res);
        }
    }
    
    private Map getProperties(ChatUserProfile profile) {
        Map properties = new HashMap();
        String[] propertyNames = profile.getPropertyNames();
        for (int i = 0; i < propertyNames.length; i++) {
            String propertyName = propertyNames[i];
            properties.put(propertyName, profile.getPropertyValue(propertyName));
        }
        return properties;
    }


}
