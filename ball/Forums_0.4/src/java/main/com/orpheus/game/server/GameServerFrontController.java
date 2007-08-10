/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.server;

import com.topcoder.web.frontcontroller.Action;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.rest.RESTFrontController;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.Level;
import com.orpheus.game.server.util.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>A front controller to be utilized by <code>Game Server</code> module. The purpose of this servlet is to
 * log errors to the configured logger and to direct the application to the correct error page</p>
 *
 * @author isv
 * @version 1.0
 */
public class GameServerFrontController extends RESTFrontController {

    /**
     * <p>A <code>Log</code> used to log errors caught while servicing the request.</p>
     */
    private final Log log;

    /**
     * <p>Constructs new <code>GameServerFrontController</code> instance. This implementation does nothing.</p>
     */
    public GameServerFrontController() {
        this.log = LogFactory.getLog();
    }

    /**
     * {@inheritDoc}
     */
    protected ActionContext createActionContext(HttpServletRequest request, HttpServletResponse response) {
        return new OrpheusActionContext(super.createActionContext(request, response));
    }

    /**
     * <p>Execute the action with the given ActionContext object. Any exception which may occur in the course of
     * executing an action will result in request being forwarded to error page.</p>
     *
     * @param action the Action to execute.
     * @param context the ActionContext object to pass into Action object.
     * @throws ServletException if the request could not be forwarded to error page in case some exception is
     *         encountered.
     */
    protected void executeAction(Action action, ActionContext context) throws ServletException {
        try {
            super.executeAction(action, context);
        } catch (Exception e) {
            getLogger().error("executeAction", e);
            try {
                HttpServletRequest request = context.getRequest();

                request.setAttribute("exception", e);
                if (isRequestFromPlugin(request)) {
                    LogHelper logger = new LogHelper("got an error - " + LogHelper.getExceptionMessages(e),
                                                     LogHelper.EMPTY_ARRAY, "PLUGIN");
                    logger.log(context, this.log, Level.INFO);
                    request.getRequestDispatcher("/WEB-INF/plugin/error.jsp").forward(request, context.getResponse());
                } else {
                    LogHelper logger = new LogHelper("got an error - " + LogHelper.getExceptionMessages(e),
                                                     LogHelper.EMPTY_ARRAY, "WEB SITE");
                    logger.log(context, this.log, Level.INFO);
                    request.getRequestDispatcher("/public/error.jsp").forward(request, context.getResponse());
                }
            } catch (Exception e1) {
                getLogger().error("executeAction", e1);
                throw new ServletException("Could not forward request to ERROR page", e1);
            }
        }
    }

    /**
     * <p>Checks if the specified request represents a request issued by plugin. The check is based on rule that all
     * requests issued by plugins are routed to handlers with URL pattern starting with <code>/server/plugin/</code>.
     * </p>
     *
     * @param request an <code>HttpServletRequest</code> representing the incoming request from the client.
     * @return <code>true</code> if specified request has been issued by plugin; <code>false</code> otherwise.
     */
    private boolean isRequestFromPlugin(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/server/plugin/");
    }
}
