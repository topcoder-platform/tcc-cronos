/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.annotation.PostConstruct;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.Helper;
import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.util.log.Log;

/**
 * This class is the base class of the UserIdRetrievers. It simply stores the logger and the session
 * key if necessary for accessing by the subclasses.
 * <p/>
 * <b>Thread-safety</b> The class is mutable. However, the inserted configuration parameters will be
 * set only once by Spring framework and in the thread-safe manner. Under these conditions the class
 * is thread-safe.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public abstract class BaseUserIdRetriever implements UserIdRetriever {

    /**
     * This is the string key, under which the logged user data will be stored in http session. It
     * is modified by setter and used in getUserId() method. It can be any value, but should not be
     * null after spring initialization. Its' legality is checked in the checkInitialization()
     * method.
     */
    private String userSessionKey;

    /**
     * This is the log to perform logging. It is modified by setter and used in getUserId() method.
     * It can be any value, but should not be null after spring initialization. Its' legality is
     * checked in the checkInitialization() method.
     */
    private Log log;

    /**
     * The default do nothing constructor.
     */
    protected BaseUserIdRetriever() {
        // do nothing
    }

    /**
     * This is method is used to check whether all dependencies of this class were properly inserted
     * by Spring framework. This method should be configured as "init-method" in the bean definition
     * in spring configuration file.
     *
     * @throws CheckProfileCompletenessConfigurationException
     *             if any property was not properly injected
     */
    @PostConstruct
    protected void checkInitialization() {
        String method = "BaseUserIdRetriever.checkInitialization";
        Helper.checkConfigNull(userSessionKey, "userSessionKey", log, method);
        Helper.checkConfigNullEmpty(userSessionKey, "userSessionKey", log, method);
    }

    /**
     * This is a simple getter for field "userSessionKey". It is used to obtain the field by
     * subclasses.
     *
     * @return The value of field "userSessionKey"
     */
    protected String getUserSessionKey() {
        return this.userSessionKey;
    }

    /**
     * This is a simple setter for field "userSessionKey". It will be called by Spring framework in
     * order to properly initialize the object.
     *
     * @param userSessionKey
     *            the value of field "userSessionKey"
     */
    public void setUserSessionKey(String userSessionKey) {
        this.userSessionKey = userSessionKey;
    }

    /**
     * This is a simple getter for field "log". It is used to obtain the field by subclasses.
     *
     * @return The value of field "log"
     */
    protected Log getLog() {
        return this.log;
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
}
