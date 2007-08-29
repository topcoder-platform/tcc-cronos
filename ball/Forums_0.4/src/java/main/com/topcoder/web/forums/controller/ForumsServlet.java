/*
 * Created on Apr 27, 2005
 */
package com.topcoder.web.forums.controller;

import com.jivesoftware.base.AuthFactory;
import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.forum.ForumFactory;
import com.topcoder.shared.util.TCContext;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.*;
import com.topcoder.web.common.error.RequestRateExceededException;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.ejb.forums.ForumsLocal;
import com.topcoder.web.ejb.forums.ForumsLocalHome;
import com.topcoder.web.forums.controller.request.ForumsProcessor;

import javax.naming.Context;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * @author mtong
 *         <p/>
 *         Provides session and authentication information to the forum's processors.
 */
public class ForumsServlet extends BaseServlet {
    private final static Logger log = Logger.getLogger(ForumsServlet.class);

    public synchronized void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Delete orphaned attachments daily
        Thread tOrphaned = new Thread() {
            public void run() {
                while (true) {
                    try {
                        log.info("Deleting orphaned attachments...");
                        ForumsLocal forumsBean = getForumsBean();
                        if (forumsBean != null) {
                            forumsBean.deleteOrphanedAttachments();
                        } else {
                            log.error("Could not delete orphaned attachments: forumsBean is null");
                        }
                        sleep(86400000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tOrphaned.start();
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        RequestProcessor rp = null;
        SessionInfo info = null;

        try {
            try {
                request.setCharacterEncoding("utf-8");
                if (log.isDebugEnabled()) {
                    log.debug("content type: " + request.getContentType());
                }

                AuthToken authToken = AuthFactory.getAnonymousAuthToken();
                try {
                    authToken = AuthFactory.getAuthToken(request, response);
                } catch (UnauthorizedException uae) {
                }

                TCRequest tcRequest = HttpObjectFactory.createRequest(request);
                TCResponse tcResponse = HttpObjectFactory.createResponse(response);
                
                BasicAuthentication auth = new BasicAuthentication(new SessionPersistor(request.getSession()), 
                        tcRequest, tcResponse);
                info = createSessionInfo(tcRequest, auth, new HashSet());
                tcRequest.setAttribute(SESSION_INFO_KEY, info);

                log.info("--> in ForumsServlet.process() ");
                String username = ForumsUtil.checkLoginCookie(tcRequest, "Ball_user_id").getUserName();                
                String servletPath = request.getContextPath() + request.getServletPath();
                String query = request.getQueryString();
                String queryString = (query == null) ? ("") : ("?" + query);
                StringBuffer buf = new StringBuffer(200);
                buf.append("http://");
                buf.append(request.getServerName());
                buf.append(servletPath);
                buf.append(queryString);
                String requestString = buf.toString();
                
                if (throttleEnabled) {
                    if (throttle.throttle(request.getSession().getId())) {
                        throw new RequestRateExceededException(request.getSession().getId(), username);
                    }
                }

                //we can let browsers/proxies cache pages if the user is anonymous or it's https (they don't really cache https setuff)
                if (!authToken.isAnonymous() &&
                        !request.getRequestURL().toString().toLowerCase().startsWith("https")) {
                    log.debug("using an uncached response");
                    tcResponse = HttpObjectFactory.createUnCachedResponse(response);
                }

                com.jivesoftware.base.User forumUser = null;
                ForumFactory forumFactory = ForumFactory.getInstance(authToken);
                if (!authToken.isAnonymous()) {
                    forumUser = forumFactory.getUserManager().getUser(authToken.getUserID());
                }

                StringBuffer loginfo = new StringBuffer(100);
                loginfo.append("[* ");
                loginfo.append(username);
                loginfo.append(" * ");
                loginfo.append(request.getRemoteAddr());
                loginfo.append(" * ");
                loginfo.append(request.getMethod());
                loginfo.append(" ");
                loginfo.append(requestString);
                loginfo.append(" *]");
                log.info(loginfo);

                try {
                    String cmd = StringUtils.checkNull((String) tcRequest.getAttribute(MODULE));
                    if (cmd.equals(""))
                        cmd = StringUtils.checkNull(tcRequest.getParameter(MODULE));

                    if (cmd.equals(""))
                        cmd = DEFAULT_PROCESSOR;
                    if (!isLegalCommand(cmd))
                        throw new NavigationException();

                    String processorName = getProcessor(cmd);
                    if (processorName.indexOf(".") == -1) {
                        processorName = PATH + (PATH.endsWith(".") ? "" : ".") + processorName;
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("creating request processor for " + processorName);
                    }
                    try {
                        rp = callProcess(processorName, request, response, tcRequest, tcResponse, auth, authToken, forumUser, forumFactory);
                    } catch (ClassNotFoundException e) {
                        throw new NavigationException("Invalid request", e);
                    }
                } catch (PermissionException pe) {
                    if (authToken.isAnonymous()) {
                        log.info(username + " does not have access to " + pe.getResource().getName() + " sending to login");
                        handleLogin(request, response, request.getServerName());
                        return;
                    } else {
                        log.info(username + " does not have access to " + pe.getResource().getName() + " sending to error");
                        throw pe;
                    }
                }
                if (!response.isCommitted()) {
                    fetchRegularPage(request, response, rp.getNextPage(), rp.isNextPageInContext());
                    return;
                }
            } catch (Throwable e) {
                handleException(request, response, e);
            }

            /* things are extremely broken, or perhaps some of the response
             * buffer had already been flushed when an error was thrown,
             * and the forward to error page failed.  in any event, make
             * one last attempt to get an error message to the browser
             */
        } catch (Throwable e) {
            log.fatal("forwarding to error page failed", e);
            e.printStackTrace();
            response.setContentType("text/html");
            response.setStatus(500);
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Internal Error</title></head>");
            out.println("<body><h4>Your request could not be processed.  Please inform The Ball.</h4>");
            out.println("</body></html>");
            out.flush();
        }
    }

    protected void handleLogin(HttpServletRequest request, HttpServletResponse response, String serverName) throws Exception {
        /* forward to the login page 
         * TODO: redirect back to the original page */
        StringBuffer nextPage = new StringBuffer("http://").append(serverName).append(LOGIN_SERVLET).append(LOGIN_PROCESSOR);
        log.info("--> in handleLogin; nextPage = " + nextPage.toString());
        fetchRegularPage(request, response, nextPage.toString(), false);
    }

    protected RequestProcessor callProcess(String processorName, HttpServletRequest httpRequest,
                                           HttpServletResponse httpResponse, TCRequest request, TCResponse response,
                                           WebAuthentication authentication, AuthToken authToken, com.jivesoftware.base.User user,
                                           ForumFactory factory) throws Exception {
        ForumsProcessor rp = null;

        rp = (ForumsProcessor) Class.forName(processorName).newInstance();
        rp.setRequest(request);
        rp.setResponse(response);
        rp.setAuthentication(authentication);
        rp.setAuthToken(authToken);
        rp.setUser(user);
        rp.setForumFactory(factory);
        rp.process();
        return rp;
    }

    private ForumsLocal getForumsBean() {
        ForumsLocal forumsBean = null;
        try {
            Context context = TCContext.getInitial();
            ForumsLocalHome forumsLocalHome = (ForumsLocalHome) context.lookup(ForumsLocalHome.EJB_REF_NAME);
            forumsBean = forumsLocalHome.create();
        } catch (Exception e) {
            log.error(e);
        }
        return forumsBean;
    }
}