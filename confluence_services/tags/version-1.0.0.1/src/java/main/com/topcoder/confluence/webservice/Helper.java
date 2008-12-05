/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class for the component.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @return the object to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static Object checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
        return arg;
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * </p>
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     * @throws IllegalArgumentException
     *             if the given string is null or empty
     */
    public static void checkStringNullOrEmpty(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Log method entry.
     * </p>
     *
     * @param log
     *            the log instance used to log
     * @param methodName
     *            the method to log
     */
    private static void logMethodEntry(Log log, String methodName) {
        log.log(Level.DEBUG, "[Entering method {" + methodName + "}]");
    }

    /**
     * <p>
     * Log method exit.
     * </p>
     *
     * @param log
     *            the log instance used to log
     * @param methodName
     *            the method to log
     */
    private static void logMethodExit(Log log, String methodName) {
        log.log(Level.DEBUG, "[Exiting method {" + methodName + "}]");
    }

    /**
     * <p>
     * Log exception thrown from public methods in <code>ConfluenceManagementServiceBean</code> and
     * <code>ConfluenceManagerWebServiceDelegate</code>.
     * </p>
     *
     * @param errorMsg
     *            the error message to log
     * @param methodName
     *            the method which error occurred
     * @param e
     *            the exception thrown from corresponding method
     * @param log
     *            the log instance used to log
     */
    public static void logException(String errorMsg, String methodName, Throwable e, Log log) {
        if (log != null) {
            log.log(Level.WARN, e, "[Error in method {" + methodName + "}:Details {" + errorMsg + "}]");
        }
    }

    /**
     * <p>
     * Get need string form given object.
     * </p>
     *
     * @param s
     *            the given object
     * @return the string represents the given object
     */
    private static String getStringLogMessage(Object s) {
        return s == null ? "" : s.toString();
    }

    /**
     * <p>
     * Get the page string representation.
     * </p>
     *
     * @param page
     *            the page to deal with
     * @return the string represents the given page
     */
    private static String getPageValueLogMessage(Page page) {
        if (page == null) {
            return "";
        }

        return "basePageUrl:" + getStringLogMessage(page.getBasePageUrl()) + "," + "versionUrl:"
            + getStringLogMessage(page.getVersionUrl()) + "," + "assetName:"
            + getStringLogMessage(page.getAssetName()) + "," + "version:" + getStringLogMessage(page.getVersion())
            + "," + "applicationCode:" + getStringLogMessage(page.getApplicationCode()) + "," + "content:"
            + getStringLogMessage(page.getContent()) + "," + "assetType:"
            + getStringLogMessage(page.getAssetType()) + "," + "catalog:" + getStringLogMessage(page.getCatalog())
            + "," + "componentType:" + getStringLogMessage(page.getComponentType());
    }

    /**
     * <p>
     * Log output parameter ConfluencePageCreationResult and method exit.
     * </p>
     *
     * @param methodName
     *            the exit method name
     * @param result
     *            the output parameter ConfluencePageCreationResult to log
     * @param log
     *            the log instance used to log
     */
    public static void logConfluencePageCreationResultOutputAndMethodExit(String methodName,
        ConfluencePageCreationResult result, Log log) {
        if (log != null) {
            logMethodExit(log, methodName);
            String pageValue = getPageValueLogMessage(result.getPage());
            if (pageValue.length() > 0) {
                pageValue = "[" + pageValue + "]";
            }
            log.log(Level.DEBUG, "[Output parameter {page:" + pageValue + ", actionTaken:"
                + getStringLogMessage(result.getActionTaken()) + "}]");
        }
    }

    /**
     * <p>
     * Get the input parameter name/value pair string representation.
     * </p>
     *
     * @param paramName
     *            the parameter name
     * @param value
     *            the parameter value
     * @return the string representation of input parameter name/value pair
     */
    private static String getInputNameValuePair(String paramName, String value) {
        return "{" + paramName + "}:{" + value + "}";
    }

    /**
     * <p>
     * Log output parameter Page and method exit.
     * </p>
     *
     * @param methodName
     *            the exit method name
     * @param page
     *            the output parameter Page
     * @param log
     *            the log instance used to log
     */
    public static void logPageOutputAndMethodExit(String methodName, Page page, Log log) {
        if (log != null) {
            logMethodExit(log, methodName);
            log.log(Level.DEBUG, "[Output parameter {" + getPageValueLogMessage(page) + "}]");
        }
    }

    /**
     * <p>
     * Log login method output and method exit.
     * </p>
     *
     * @param methodName
     *            the exit method
     * @param token
     *            the output parameter token
     * @param log
     *            the log instance used to log
     */
    public static void logLoginOutputAndMethodExit(String methodName, String token, Log log) {
        if (log != null) {
            logMethodExit(log, methodName);
            log.log(Level.DEBUG, "[Output parameter {" + getStringLogMessage(token) + "}]");
        }
    }

    /**
     * <p>
     * Log login method entry and input parameters.
     * </p>
     *
     * @param className
     *            the class entry
     * @param userName
     *            the input parameter userName
     * @param password
     *            the input parameter password
     * @param log
     *            the log instance used to log
     */
    public static void logLoginMethodEntryAndInput(String className, String userName, String password, Log log) {
        if (log != null) {
            logMethodEntry(log, className + ".login");
            log.log(Level.DEBUG, "[Input parameters["
                + getInputNameValuePair("userName", getStringLogMessage(userName)) + ","
                + getInputNameValuePair("password", getStringLogMessage(password)) + "]]");
        }
    }

    /**
     * <p>
     * Log logout method entry and input parameter.
     * </p>
     *
     * @param className
     *            the class entry
     * @param token
     *            the input parameter token
     * @param log
     *            the log instance used to log
     */
    public static void logLogoutMethodEntryAndInput(String className, String token, Log log) {
        if (log != null) {
            logMethodEntry(log, className + ".logout");
            log.log(Level.DEBUG, "[Input parameters[" + getInputNameValuePair("token", getStringLogMessage(token))
                + "]]");
        }
    }

    /**
     * <p>
     * Log createPage or retrievePage method with ComponentType entry and input parameters.
     * </p>
     *
     * @param methodName
     *            the method name entry
     * @param token
     *            the token input parameter
     * @param pageName
     *            the pageName input parameter
     * @param version
     *            the version input parameter
     * @param assetType
     *            the assetType input parameter
     * @param catalog
     *            the catalog input parameter
     * @param componentType
     *            the componentType input parameter
     * @param log
     *            the log instance used to log
     */
    public static void logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput(String methodName,
        String token, String pageName, String version, ConfluenceAssetType assetType, ConfluenceCatalog catalog,
        ComponentType componentType, Log log) {
        if (log != null) {
            logMethodEntry(log, methodName);
            log.log(Level.DEBUG, "[Input parameters[" + getInputNameValuePair("token", getStringLogMessage(token))
                + "," + getInputNameValuePair("pageName", getStringLogMessage(pageName)) + ","
                + getInputNameValuePair("version", getStringLogMessage(version)) + ","
                + getInputNameValuePair("assetType", getStringLogMessage(assetType)) + ","
                + getInputNameValuePair("catalog", getStringLogMessage(catalog)) + ","
                + getInputNameValuePair("componentType", getStringLogMessage(componentType)) + "]]");
        }
    }

    /**
     * <p>
     * Log createPage or retrievePage method with applicationCode entry and input parameters.
     * </p>
     *
     * @param methodName
     *            the method name entry
     * @param token
     *            the token input parameter
     * @param pageName
     *            the pageName input parameter
     * @param version
     *            the version input parameter
     * @param assetType
     *            the assetType input parameter
     * @param catalog
     *            the catalog input parameter
     * @param applicationCode
     *            the applicationCode input parameter
     * @param log
     *            the log instance used to log
     */
    public static void logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput(String methodName,
        String token, String pageName, String version, ConfluenceAssetType assetType, ConfluenceCatalog catalog,
        String applicationCode, Log log) {
        if (log != null) {
            logMethodEntry(log, methodName);
            log.log(Level.DEBUG, "[Input parameters[" + getInputNameValuePair("token", getStringLogMessage(token))
                + "," + getInputNameValuePair("pageName", getStringLogMessage(pageName)) + ","
                + getInputNameValuePair("version", getStringLogMessage(version)) + ","
                + getInputNameValuePair("assetType", getStringLogMessage(assetType)) + ","
                + getInputNameValuePair("catalog", getStringLogMessage(catalog)) + ","
                + getInputNameValuePair("applicationCode", getStringLogMessage(applicationCode)) + "]]");
        }
    }

    /**
     * <p>
     * Log createPage or retrievePage method with page entry and input parameters.
     * </p>
     *
     * @param className
     *            the class entry
     * @param token
     *            the token input parameter
     * @param page
     *            the page input parameter
     * @param log
     *            the log instance used to log
     */
    public static void logCreatePageWithPageMethodEntryAndInput(String className, String token, Page page, Log log) {
        if (log != null) {
            logMethodEntry(log, className + ".createPage");
            log.log(Level.DEBUG, "[Input parameters[" + getInputNameValuePair("token", getStringLogMessage(token))
                + "," + getInputNameValuePair("page", getPageValueLogMessage(page)) + "]]");
        }
    }

    /**
     * <p>
     * Checks whether the given Object is null and log IllegalArgumentException if the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @param methodName
     *            the method entering in
     * @param log
     *            the log instance used to log
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static void checkNullAndLog(Object arg, String name, String methodName, Log log) {
        if (arg == null) {
            String errorMsg = name + " should not be null.";
            IllegalArgumentException e = new IllegalArgumentException(errorMsg);
            logException(errorMsg, methodName, e, log);
            throw e;
        }
    }

    /**
     * <p>
     * Checks whether the given Object is null or empty and log IllegalArgumentException if the given Object is
     * null or empty.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @param log
     *            the log instance used to log
     * @param methodName
     *            the method entering in
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static void checkStringNullOrEmptyAndLog(String arg, String name, String methodName, Log log) {
        checkNullAndLog(arg, name, methodName, log);
        if (arg.trim().length() == 0) {
            String errorMsg = name + " should not be empty.";
            IllegalArgumentException e = new IllegalArgumentException(errorMsg);
            logException(errorMsg, methodName, e, log);
            throw e;
        }
    }
}