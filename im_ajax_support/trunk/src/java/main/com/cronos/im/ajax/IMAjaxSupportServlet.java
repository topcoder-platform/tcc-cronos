/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatStatusTracker;
import com.topcoder.service.ServiceEngine;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

/**
 * <p>
 * IMAjaxSupportServlet provides servlet support for the application on AJAX interactions. It receives the
 * client request and output the XML response to indicate if the operation succeeds or not. The XSD schema for
 * the resposne and request messages are given in the docs/ directory. Please also see the algorithm section
 * for more detailed discussion.
 * </p>
 *
 * <p>
 * Servlet container can guarantee the thread safety of this class.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxSupportServlet extends HttpServlet {

    /**
     * The RequestHandlerManager is used to rount the request to the matching handler.
     */
    private RequestHandlerManager requestManager;

    /**
     * Empty constructor.
     *
     */
    public IMAjaxSupportServlet() {
    }

    /**
     * Init the servlet.
     *
     * @throws ServletException
     *             if any error occurred
     */
    public void init() throws ServletException {
        // 1. call base class init() method
        super.init();

        // get servlet config and context
        ServletConfig sc = getServletConfig();
        ServletContext context = this.getServletContext();

        try {
            // 2. create object factory
            String ofns = sc.getInitParameter("object_factory_namespace");
            if (ofns == null) {
                ofns = "com.cronos.im.ajax.objectfactory";
            }
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(ofns));
            // 3. create and store Messenger
            String key = IMAjaxSupportUtility.getIMMessengerKey();
            Messenger messenger = (Messenger) of.createObject(key);
            context.setAttribute(key, messenger);
            // 4. create and store ChatSessionManager
            key = IMAjaxSupportUtility.getChatSessionManagerKey();
            ChatSessionManager mgr = (ChatSessionManager) of.createObject(key);
            context.setAttribute(key, mgr);
            // 5. create and store ChatStatusTracker
            key = IMAjaxSupportUtility.getChatStatusTrackerKey();
            ChatStatusTracker tracker = (ChatStatusTracker) of.createObject(key);
            context.setAttribute(key, tracker);
            // 6. create and store ServiceEngine
            key = IMAjaxSupportUtility.getServiceEngineKey();
            ServiceEngine engine = (ServiceEngine) of.createObject(key);
            context.setAttribute(key, engine);

            // 7. create request handler
            String rhns = sc.getInitParameter("handler_manager_namespace");
            requestManager = rhns == null ? new RequestHandlerManager() : new RequestHandlerManager(rhns);
        } catch (Exception e) {
            throw new ServletException("Fail to initialize IMAjaxSupportServlet.", e);
        }
    }

    /**
     * Service the request.
     *
     *
     * @param req
     *            the http request
     * @param res
     *            the http response
     * @throws IOException
     *             if any i/o error ocurred
     * @throws ServletException
     *             if any other error occurred
     */
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException,
            ServletException {
        requestManager.handle(req, res);

        PrintWriter writer = res.getWriter();

        writer.flush();
        res.flushBuffer();
        writer.close();
    }

}
