/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessor;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessorException;
import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;

/**
 * This class is the default implementation of the CheckProfileCompletenessProcessor. It processes
 * the check only if the input request uri matches the configurable pattern. Then this
 * implementation retrieve the logged user and checks, whether he has provided enough profile fields
 * for performing some action. This check is done by configurable ProfileCompletenessChecker. If
 * these fields are not filled, the user will be redirected to page to complete data and request uri
 * will be stored in the session under the key requestURISessionKey.
 * <p/>
 * <b>Thread-safety</b>
 * <p/>
 * The class is mutable. However, the inserted configuration parameters will be set only once by
 * Spring framework and in the thread-safe manner. Under these conditions the class is thread-safe.
 * <p>
 * <b>Usage</b>
 * </p>
 * <p>
 * We can create it by spring:
 * </p>
 *
 * <pre>
 * &lt;bean id=&quot;checkProfileCompletenessProcessor&quot;
 * class=&quot;com.topcoder.reg.profilecompleteness.filter.impl.DefaultCheckProfileCompletenessProcessor&quot;
 * init-method=&quot;checkInitialization&quot;&gt;
 * &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 * &lt;property name=&quot;matchUriPatterns&quot;&gt;
 * &lt;list&gt;
 * &lt;value&gt;/tc1&lt;/value&gt;
 * &lt;value&gt;/tc2&lt;/value&gt;
 * &lt;/list&gt;
 * &lt;/property&gt;
 * &lt;property name=&quot;userIdRetriever&quot; ref=&quot;forumUserIdRetriever&quot;/&gt;
 * &lt;property name=&quot;profileCompletenessCheckers&quot;&gt;
 * &lt;list&gt;
 * &lt;ref bean=&quot;ForumProfileCompletenessChecker&quot;/&gt;
 * &lt;ref bean=&quot;DirectProfileCompletenessChecker&quot;/&gt;
 * &lt;/list&gt;
 * &lt;/property&gt;
 * &lt;property name=&quot;completeDataURIs&quot;&gt;
 * &lt;list&gt;
 * &lt;value&gt;/tc1_form&lt;/value&gt;
 * &lt;value&gt;/tc2_form&lt;/value&gt;
 * &lt;/list&gt;
 * &lt;/property&gt;
 * &lt;property name=&quot;userDAO&quot; ref=&quot;userDAO&quot;/&gt;
 * &lt;property name=&quot;requestURISessionKey&quot; value=&quot;session_key&quot;/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <b>Invoking:</b>
 *
 * <pre>
 * DefaultCheckProfileCompletenessProcessor processor = context.getBean(
 *     &quot;checkProfileCompletenessProcessor&quot;,
 *     DefaultCheckProfileCompletenessProcessor.class);
 * processor.process(request, response, chain);
 * </pre>
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class DefaultCheckProfileCompletenessProcessor implements CheckProfileCompletenessProcessor {

    /**
     * This is the list of match uri patterns, used to check whether the given page should be
     * verified for profile completeness. It is modified by setter and used in process() method. It
     * can be any value, but should not be null, contain null/empty elements after spring
     * initialization. Its' legality is checked in the checkInitialization() method.
     */
    private List<String> matchUriPatterns;

    /**
     * This is the UserIdRetriever instance, used to retrieve the user id. It is modified by setter
     * and used in process() method. It can be any value, but should not be null after spring
     * initialization. Its' legality is checked in the checkInitialization() method.
     */
    private UserIdRetriever userIdRetriever;

    /**
     * This is the list ProfileCompletenessChecker instances, used to verify profile completeness
     * for the specific user instance. It is modified by setter and used in process() method. It can
     * be any value, but should not be null,contain null elements after spring initialization. Its'
     * legality is checked in the checkInitialization() method.
     */
    private List<ProfileCompletenessChecker> profileCompletenessCheckers;

    /**
     * This is the list of the complete data uri strings, used to redirect user to the page, if the
     * profile is incomplete It is modified by setter and used in process() method. It can be any
     * value, but should not be null,contain null/empty elements after spring initialization. Its'
     * legality is checked in the checkInitialization() method.
     */
    private List<String> completeDataURIs;

    /**
     * This is the user dao, used to retrieve the user by the id. It is modified by setter and used
     * in process() method. It can be any value, but should not be null after spring initialization.
     * Its' legality is checked in the checkInitialization() method.
     */
    private UserDAO userDAO;

    /**
     * This is the string key, under which the requestURI will be stored in http session, if the
     * profile was not completed. It is modified by setter and used in process() method. It can be
     * any value, but should not be null/empty after spring initialization. Its' legality is checked
     * in the checkInitialization() method.
     */
    private String requestURISessionKey;

    /**
     * This is the log to perform logging. It is modified by setter and used in process() method. It
     * can be any value, but should not be null after spring initialization. Its' legality is
     * checked in the checkInitialization() method.
     */
    private Log log;

    /**
     * The default do nothing constructor.
     */
    public DefaultCheckProfileCompletenessProcessor() {
        // do nothing
    }

    /**
     * This is method is used to check whether all dependencies of this class were properly inserted
     * by Spring framework. This method should be configured as "init-method" in the bean definition
     * in spring configuration file.
     *
     * @throws CheckProfileCompletenessConfigurationException
     *             if anything of following is true after injecting:
     *             <ul>
     *             <li>matchUriPatterns is null, contain null/empty elements or
     *             <li>userIdRetriever is null or
     *             <li>profileCompletenessCheckers is null, contain null elements or
     *             <li>completeDataURIs is null, contain null/empty elements or
     *             <li>userDAO is null or
     *             <li>requestURISessionKey is null/empty or
     *             <li>log is null or
     *             <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     *             <li>matchUriPatterns.size() != completeDataURIs.size() or
     *             <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     *             </ul>
     */
    @PostConstruct
    protected void checkInitialization() {
        String method = "DefaultCheckProfileCompletenessProcessor.checkInitialization";
        checkNullOrContainNullEmpty(matchUriPatterns, "matchUriPatterns", method);
        Helper.checkConfigNull(userIdRetriever, "userIdRetriever", log, method);
        checkNullOrContainNull(profileCompletenessCheckers, "profileCompletenessCheckers", method);
        checkNullOrContainNullEmpty(completeDataURIs, "completeDataURIs", method);
        Helper.checkConfigNull(userDAO, "userDAO", log, method);
        Helper.checkConfigNullEmpty(requestURISessionKey, "requestURISessionKey", log, method);

        // check size
        checkConfigTrue(matchUriPatterns.size() == profileCompletenessCheckers.size(),
                "matchUriPatterns should have the same size as profileCompletenessCheckers");
        checkConfigTrue(matchUriPatterns.size() == completeDataURIs.size(),
                "matchUriPatterns should have the same size as completeDataURIs");
    }

    /**
     * This method is responsible for processing the profile completeness check. It processes the
     * check only if the input request uri matches the configurable pattern. Then this
     * implementation retrieve the logged user and checks, whether he has provided enough profile
     * fields for performing some action. This check is done by configurable
     * ProfileCompletenessChecker.
     * <p>
     * If these fields are not filled, the user will be redirected to page to complete data and
     * request uri will be stored in the session under the key requestURISessionKey.
     * </p>
     *
     * @param response
     *            http servlet response
     * @param chain
     *            filter chain
     * @param request
     *            http servlet request
     * @throws IllegalArgumentException
     *             if request is null, response is null, chain is null
     * @throws CheckProfileCompletenessProcessorException
     *             if any exception occurred while processing the data
     */
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws CheckProfileCompletenessProcessorException {
        // log entrance
        String method = "DefaultCheckProfileCompletenessProcessor.process";
        Helper.logEntry(log, method, "request: %s, response: %s, chain: %s", request, response,
                chain);

        // check arguments
        Helper.notNull(request, "request", log, method);
        Helper.notNull(response, "response", log, method);
        Helper.notNull(chain, "chain", log, method);

        // get request uri
        String requestURI = request.getRequestURI();

        // find first matched pattern and check if the user has complete profile
        try {
            boolean matchedOne = false;
            for (int i = 0; i < matchUriPatterns.size(); i++) {
                if (Pattern.matches(matchUriPatterns.get(i), requestURI)) {
                    matchedOne = true;
                    Long userId = userIdRetriever.getUserId(request);
                    if (userId == null) {
                        chain.doFilter(request, response);
                    } else {
                        User user = userDAO.find(userId);
                        if (profileCompletenessCheckers.get(i).isProfileComplete(user)) {
                            chain.doFilter(request, response);
                        } else {
                            request.getSession().setAttribute(requestURISessionKey, requestURI);
                            request.getRequestDispatcher(completeDataURIs.get(i)).forward(request,
                                    response);
                        }
                    }
                    break;
                }
            }

            if (!matchedOne) {
                chain.doFilter(request, response);
            }
        } catch (IllegalArgumentException e) {
            Helper.logError(log, method, e);
            throw new CheckProfileCompletenessProcessorException(
                    "IllegalArgumentException happened when processing the request", e);
        } catch (ServletException e) {
            Helper.logError(log, method, e);
            throw new CheckProfileCompletenessProcessorException(
                    "ServletException happened when processing the request", e);
        } catch (IOException e) {
            Helper.logError(log, method, e);
            throw new CheckProfileCompletenessProcessorException(
                    "IOException happened when processing the request", e);
        } catch (ProfileCompletenessCheckerException e) {
            Helper.logError(log, method, e);
            throw new CheckProfileCompletenessProcessorException(
                    "ProfileCompletenessCheckerException happened when processing the request", e);
        }

        Helper.logExit(log, method);
    }

    /**
     * This is a simple setter for field "userIdRetriever". It will be called by Spring framework in
     * order to properly initialize the object.
     *
     * @param matchUriPatterns
     *            the value of field "userIdRetriever"
     */
    public void setMatchUriPatterns(List<String> matchUriPatterns) {
        this.matchUriPatterns = matchUriPatterns;
    }

    /**
     * This is a simple setter for field "userIdRetriever". It will be called by Spring framework in
     * order to properly initialize the object.
     *
     * @param userIdRetriever
     *            the value of a field "userIdRetriever"
     */
    public void setUserIdRetriever(UserIdRetriever userIdRetriever) {
        this.userIdRetriever = userIdRetriever;
    }

    /**
     * This is a simple setter for field "profileCompletenessCheckers". It will be called by Spring
     * framework in order to properly initialize the object.
     *
     * @param profileCompletenessCheckers
     *            the value of field "profileCompletenessCheckers"
     */
    public void setProfileCompletenessCheckers(
            List<ProfileCompletenessChecker> profileCompletenessCheckers) {
        this.profileCompletenessCheckers = profileCompletenessCheckers;
    }

    /**
     * This is a simple setter for field "completeDataURIs". It will be called by Spring framework
     * in order to properly initialize the object.
     *
     * @param completeDataURIs
     *            the value of completeDataURIs.
     */
    public void setCompleteDataURIs(List<String> completeDataURIs) {
        this.completeDataURIs = completeDataURIs;
    }

    /**
     * This is a simple setter for field "userDAO". It will be called by Spring framework in order
     * to properly initialize the object.
     *
     * @param userDAO
     *            the value of field "userDAO"
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * This is a simple setter for field "requestURISessionKey". It will be called by Spring
     * framework in order to properly initialize the object.
     *
     * @param requestURISessionKey
     *            the value of the field "requestURISessionKey"
     */
    public void setRequestURISessionKey(String requestURISessionKey) {
        this.requestURISessionKey = requestURISessionKey;
    }

    /**
     * This is a simple setter for field "log". It will be called by Spring framework in order to
     * properly initialize the object.
     *
     * @param log
     *            the value of field "log"
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Check if the field contain empty string. CheckProfileCompletenessConfigurationException will
     * be thrown if is.
     *
     * @param list
     *            the list to be checked, we assume it non-null
     * @param fieldName
     *            the field name used in error message
     * @param method
     *            the method signature to be logged
     * @throws CheckProfileCompletenessConfigurationException
     *             if the list is null or contains empty string
     */
    private void checkNullOrContainNullEmpty(List<String> list, String fieldName, String method) {
        Helper.checkConfigNull(list, fieldName, log, method);
        for (String item : list) {
            if (item == null) {
                throw new CheckProfileCompletenessConfigurationException(fieldName
                        + " should not contain null item");
            }
            if (item.trim().isEmpty()) {
                throw new CheckProfileCompletenessConfigurationException(fieldName
                        + " should not contain empty item");
            }
        }
    }

    /**
     * Check if the field contain null item. CheckProfileCompletenessConfigurationException will be
     * thrown if is.
     *
     * @param list
     *            the list to be checked, we assume it non-null
     * @param fieldName
     *            the field name used in error message
     * @param method
     *            the method signature to be logged
     * @throws CheckProfileCompletenessConfigurationException
     *             if the list is null or contains null item
     */
    private void checkNullOrContainNull(List<?> list, String fieldName, String method) {
        Helper.checkConfigNull(list, fieldName, log, method);
        for (Object obj : list) { // to avoid NPE, not use "list.contains(null)"
            if (obj == null) {
                throw new CheckProfileCompletenessConfigurationException(fieldName
                        + " should not contain null item");
            }
        }
    }

    /**
     * Check if the result is true. CheckProfileCompletenessConfigurationException will be thrown if
     * it is false.
     *
     * @param result
     *            the result to be checked
     * @param message
     *            the error message
     * @throws CheckProfileCompletenessConfigurationException
     *             if the result is false
     */
    private static void checkConfigTrue(boolean result, String message) {
        if (!result) {
            throw new CheckProfileCompletenessConfigurationException(message);
        }
    }
}
