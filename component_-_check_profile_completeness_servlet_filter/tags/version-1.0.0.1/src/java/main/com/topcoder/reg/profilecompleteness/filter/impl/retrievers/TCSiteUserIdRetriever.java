/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * This class is the implementation of UserIdRetriever for the topcoder site("/tc").
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
 *
 * <pre>
 * TCSiteUserIdRetriever retriever = new TCSiteUserIdRetriever();
 * retriever.setSessionKey(&quot;user_id&quot;);
 * retriever.setLog(getLog());
 * retriever.checkInitialization();
 * </pre>
 *
 * <b>Spring:</b>
 *
 * <pre>
 * &lt;bean id=&quot;tcSiteUserIdRetriever&quot;
 * class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.TCSiteUserIdRetriever&quot;
 * init-method=&quot;checkInitialization&quot;&gt;
 * &lt;property name=&quot;userSessionKey&quot; value=&quot;userid&quot;/&gt;
 * &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <b>Invoking:</b>
 *
 * <pre>
 * Long userId = retriever.getUserId(request);
 * </pre>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class TCSiteUserIdRetriever extends BaseUserIdRetriever {

    /**
     * The default do nothing constructor.
     */
    public TCSiteUserIdRetriever() {
        // do nothing
    }

    /**
     * This method retrieves the user id from the request.
     *
     * @param request
     *            http servlet request
     * @return The user id, stored in the request, null if the user is not logged or any exception
     *         occurred
     *
     * @throws IllegalArgumentException
     *             if request is null
     */
    public Long getUserId(HttpServletRequest request) {
        String method = "TCSiteUserIdRetriever.getUserId";
        Helper.logEntry(getLog(), method, "request: %s", request);
        Long userId = getUserId0(request, method);
        return Helper.logExit(getLog(), method, userId);
    }

    /**
     * This method retrieves the user id from the request.
     *
     * @param request
     *            http servlet request
     * @param method
     *            the method signature of invoker
     * @return The user id, stored in the request, null if the user is not logged or any exception
     *         occurred
     *
     * @throws IllegalArgumentException
     *             if request is null
     */
    private Long getUserId0(HttpServletRequest request, String method) {
        Helper.notNull(request, "request", getLog(), method);
        Object auth = request.getSession().getAttribute(getUserSessionKey());
        if (auth instanceof BasicAuthentication) {
            User user = ((BasicAuthentication) auth).getActiveUser();
            return user.isAnonymous() ? null : user.getId();
        }
        if (auth != null) {
            Helper.logError(getLog(), method, "auth should be an instance of "
                + BasicAuthentication.class + ", but: "
                    + auth.getClass());
        }
        return null;
    }
}
