/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.HttpObjectFactory;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.LightAuthentication;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * This class is the implementation of UserIdRetriever for the topcoder confluence wiki.
 * <p/>
 * <b>Thread-safety</b>
 * <p/>
 * The class is mutable. However, the inserted configuration parameters will be set only once by
 * Spring framework and in the thread-safe manner. Under these conditions the class is thread-safe.
 * <p>
 * <b>Usage</b>
 * </p>
 * <p>
 * We can create this retriever programmatically or by spring:
 * </p>
 * <b>Programmatically:</b>
 * <pre>
 * WikiUserIdRetriever retriever = new WikiUserIdRetriever();
 * retriever.setLog(getLog());
 * retriever.setUserSessionKey("dummy"); // the key is not used, but required, so pass a dummy
 * retriever.checkInitialization();
 * </pre>
 * <b>Spring:</b>
 * <pre>
 * &lt;bean id=&quot;wikiUserIdRetriever&quot;
 * class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.WikiUserIdRetriever&quot;
 * init-method=&quot;checkInitialization&quot;&gt;
 * &lt;property name=&quot;userSessionKey&quot; value=&quot;dummy&quot;/&gt;
 * &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 * &lt;/bean&gt;
 * </pre>
 * <b>Invoking:</b>
 * <pre>
 * Long userId = retriever.getUserId(request);
 * </pre>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class WikiUserIdRetriever extends BaseUserIdRetriever {

    /**
     * The default do nothing constructor.
     */
    public WikiUserIdRetriever() {
        // do nothing
    }

    /**
     * This method retrieves the user id from the request.
     *
     * @param request http servlet request
     * @return The user id, stored in the request, null if the user is not logged or any exception
     *         occurred
     *
     * @throws IllegalArgumentException if request is null
     */
    public Long getUserId(HttpServletRequest request) {
        String method = "WikiUserIdRetriever.getUserId";
        Helper.logEntry(getLog(), method, "request: %s", request);
        Long userId = getUserId0(request, method);
        return Helper.logExit(getLog(), method, userId);
    }

    /**
     * This method retrieves the user id from the request.
     *
     * @param request http servlet request
     * @return The user id, stored in the request, null if the user is not logged or any exception
     *         occurred
     *
     * @throws IllegalArgumentException if request is null
     */
    private Long getUserId0(HttpServletRequest request, String method) {
        Helper.notNull(request, "request", getLog(), method);
        TCRequest tcRequest = HttpObjectFactory.createSimpleRequest(request);
        try {
            LightAuthentication auth = new LightAuthentication(new
                    SessionPersistor(request.getSession()), tcRequest, null,
                    BasicAuthentication.MAIN_SITE);
            User user = auth.getActiveUser();
            return user.isAnonymous() ? null : user.getId();
        } catch (Exception e) {
            // Exception should be caught here
            // http://apps.topcoder.com/forums/?module=Thread&threadID=712148&start=0
            Helper.logError(getLog(), "WikiUserIdRetriever.getUserId", e);
            return null;
        }
    }
}
