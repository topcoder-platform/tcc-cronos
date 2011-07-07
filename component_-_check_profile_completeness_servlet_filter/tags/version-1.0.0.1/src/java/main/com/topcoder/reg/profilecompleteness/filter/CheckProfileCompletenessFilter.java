/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.util.log.Log;

/**
 * This class is the server filter, which is used to check, whether the topcoder member has provided
 * enough details to access some activity. The concrete details of the profile completeness checking
 * are defined in the pluggable CheckProfileCompletenessProcessor, implementation of which will be
 * inserted by spring.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The class is mutable. However, the setter of the CheckProfileCompletenessProcessor will be done
 * only once by Spring and call of doFilter method will be done in thread-safe manner by web
 * container. Therefore, the class is thread-safe.
 * <p>
 * <b>Usage</b>
 * </p>
 * <p>
 * In web.xml
 * </p>
 *
 * <pre>
 * &lt;filter&gt;
 * &lt;filter-name&gt;completenessFilter1&lt;/filter-name&gt;
 * &lt;filter-class&gt;com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessFilter&lt;/filter-class&gt;
 * &lt;init-param&gt;
 * &lt;param-name&gt;configurationFileName&lt;/param-name&gt;
 * &lt;param-value&gt;applicationContext.xml&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 * &lt;filter-name&gt;completenessFilter1&lt;/filter-name&gt;
 * &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre>
 *
 * The applicationContext.xml:
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;!DOCTYPE beans PUBLIC &quot;-//SPRING//DTD BEAN 2.0//EN&quot;
 *         &quot;http://www.springframework.org/dtd/spring-beans-2.0.dtd&quot;&gt;
 *
 * &lt;beans&gt;
 *
 *     &lt;!-- Create logger --&gt;
 *     &lt;bean id=&quot;log&quot; class=&quot;com.topcoder.util.log.LogManager&quot;
 *           factory-method=&quot;getLog&quot;&gt;
 *         &lt;constructor-arg value=&quot; com.topcoder.reg.profilecompleteness.filter&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;!-- Create user DAO --&gt;
 *     &lt;bean id=&quot;userDAO&quot; class=&quot;com.topcoder.web.common.dao.impl.UserDAOImpl&quot;/&gt;
 *
 *     &lt;bean id=&quot;checkProfileCompletenessProcessor&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.DefaultCheckProfileCompletenessProcessor&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *         &lt;property name=&quot;matchUriPatterns&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;/tc1&lt;/value&gt;
 *                 &lt;value&gt;/tc2&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;userIdRetriever&quot; ref=&quot;forumUserIdRetriever&quot;/&gt;
 *         &lt;property name=&quot;profileCompletenessCheckers&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;ref bean=&quot;ForumProfileCompletenessChecker&quot;/&gt;
 *                 &lt;ref bean=&quot;DirectProfileCompletenessChecker&quot;/&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;completeDataURIs&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;/tc1_form&lt;/value&gt;
 *                 &lt;value&gt;/tc2_form&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;userDAO&quot; ref=&quot;userDAO&quot;/&gt;
 *         &lt;property name=&quot;requestURISessionKey&quot; value=&quot;session_key&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;!-- Create user id retrievers --&gt;
 *     &lt;bean id=&quot;forumUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.ForumUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;userSessionKey&quot; value=&quot;authToken&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;onlineReviewUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.OnlineReviewUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;userSessionKey&quot; value=&quot;userId&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;studioUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.StudioUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;userSessionKey&quot; value=&quot;userid&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;tcSiteUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.TCSiteUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;userSessionKey&quot; value=&quot;userid&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;directUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.DirectUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;wikiUserIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.WikiUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;jiraIdRetriever&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.retrievers.JiraUserIdRetriever&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;userSessionKey&quot; value=&quot;user&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;!-- Create profile completeness checkers --&gt;
 *     &lt;bean id=&quot;ForumProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           ForumProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;DirectProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           DirectProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;JiraProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           JiraProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;VMProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           VMProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;CopilotProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           CopilotProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;WikiProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           WikiProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;OnlineReviewProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           OnlineReviewProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;SVNProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           SVNProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;CompetitionProfileCompletenessChecker&quot;
 *           class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.
 *           CompetitionProfileCompletenessChecker&quot;
 *           init-method=&quot;checkInitialization&quot;&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;messageSource&quot;
 *           class=&quot;org.springframework.context.support.ResourceBundleMessageSource&quot;&gt;
 *         &lt;property name=&quot;basenames&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;messages&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 *
 * messages.properties:
 *
 * <pre>
 * errorPageUri=/error.jsp
 * </pre>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class CheckProfileCompletenessFilter implements Filter {

    /**
     * This is the CheckProfileCompletenessProcessor instance, used to check profile completeness
     * for some user activity. It is initialized in init() method and used in doFilter() method. It
     * can be any value, but should not be null after spring initialization. Its' legality is
     * checked in the init() method.
     */
    private CheckProfileCompletenessProcessor checkProfileCompletenessProcessor;

    /**
     * This is the error page uri, which the user will be redirected to if his profile is incomplete
     * It is modified by setter and used in doFilter() method. It can be any value, but should not
     * be null or empty after spring initialization. Its' legality is checked in the init() method.
     */
    private String errorPageUri;

    /**
     * This is the log to perform logging. It is modified by setter and used in doFilter() method.
     * It can be any value, but should not be null after spring initialization. Its' legality is
     * checked in the init() method.
     */
    private Log log;

    /**
     * The default do nothing constructor.
     */
    public CheckProfileCompletenessFilter() {
        // do nothing
    }

    /**
     * This method is responsible for initializing the filter.
     *
     * @param filterConfig
     *            the filter configuration
     * @throws CheckProfileCompletenessConfigurationException
     *             if any property was not properly injected
     */
    public void init(FilterConfig filterConfig) {
        // NOTE: we can't log the entry of this method, because the field "log" is not initialized
        // yet

        // configurationFileName
        String configurationFileName = filterConfig.getInitParameter("configurationFileName");
        if (configurationFileName == null || configurationFileName.trim().isEmpty()) {
            throw new CheckProfileCompletenessConfigurationException(
                    "configurationFileName from filter config should not be null or empty: "
                            + configurationFileName);
        }

        // context
        ClassPathXmlApplicationContext context;
        try {
            context = new ClassPathXmlApplicationContext(configurationFileName);
            if (context.containsBean("log")) {
                log = (Log) context.getBean("log", Log.class);
            }
            checkProfileCompletenessProcessor = (CheckProfileCompletenessProcessor) context.
                    getBean("checkProfileCompletenessProcessor",
                        CheckProfileCompletenessProcessor.class);
            errorPageUri = context.getMessage("errorPageUri", null, null, null);
        } catch (BeansException e) {
            throw new CheckProfileCompletenessConfigurationException(
                    "BeansException happened when creating context of spring", e);
        }

        // errorPageUri
        Helper.checkConfigNullEmpty(errorPageUri, "errorPageUri", log, "CheckProfileCompletenessFilter.init");
    }

    /**
     * This method is responsible for filter processing. It simply delegates the actual work to
     * CheckProfileCompletenessProcessor, which is inserted by Spring. If any exception occurred
     * while processing the data, then redirect to the error page.
     *
     * @param response
     *            the servlet response
     * @param chain
     *            the filter chain
     * @param request
     *            the servlet request
     * @throws IOException
     *             if error occurs when redirecting to the error page
     * @throws ServletException
     *             if error occurs when redirecting to the error page
     */
    public void doFilter(final ServletRequest request, final ServletResponse response,
        final FilterChain chain) throws IOException, ServletException {
        String method = "CheckProfileCompletenessFilter.doFilter";
        Helper.logEntry(log, method, "request: %s, response: %s, chain: %s", request, response,
                chain);

        try {
            checkProfileCompletenessProcessor.process((HttpServletRequest) request,
                    (HttpServletResponse) response, chain);
        } catch (CheckProfileCompletenessProcessorException e) {
            Helper.logError(log, method, e);
            request.getRequestDispatcher(errorPageUri).forward(request, response);
        }

        Helper.logExit(log, method);
    }

    /**
     * This method is responsible for destroying the filter. For this filter, it does nothing.
     */
    public void destroy() {
        // do nothing, just log
        String method = "CheckProfileCompletenessFilter.destroy";
        Helper.logEntry(log, method);
        Helper.logExit(log, method);
    }

}
