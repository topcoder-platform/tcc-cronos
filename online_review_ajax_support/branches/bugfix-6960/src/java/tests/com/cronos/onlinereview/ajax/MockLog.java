/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax;

import com.topcoder.util.format.ObjectFormatter;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A mock implementation of {@link Log} class to be used for testing. Overrides the protected methods declared by a
 * super-class. The overridden methods are declared with package private access so only the test cases could invoke
 * them. The overridden methods simply call the corresponding method of a super-class.
 *
 * @author isv
 * @version 1.0
 */
public class MockLog implements Log {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping the <code>
     * String</code> names of the arguments to <code>Object</code>s representing the values of  arguments which have
     * been provided by the caller of the method.</p>
     */
    private static Map methodArguments = new HashMap();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Exception</code>s to be thrown
     * by methods.</p>
     */
    private static Map throwExceptions = new HashMap();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Object</code>s to be returned by
     * methods.</p>
     */
    private static Map methodResults = new HashMap();

    /**
     * <p>A <code>Throwable</code> representing the exception to be thrown from any method of the mock class.</p>
     */
    private static Throwable globalException = null;

    /**
     * <p>Constructs new <code>MockLog</code> instance.</p>
     */
    public MockLog() {
    }

    /**
     * <p>Constructs new <code>MockLog</code> instance.</p>
     *
     * @param name a <code>String</code> providing the log name.
     */
    public MockLog(String name) {
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through {@link
     * #setMethodResult(String, Object)} method.</p>
     *
     * @see Log#log(com.topcoder.util.log.Level, Object)
     */
    public void log(Level level0, Object object0) {
        if (MockLog.globalException != null) {
            throw new RuntimeException("The test may not be configured properly", MockLog.globalException);
        }

        String methodName = "log_Level_Object";

        Throwable exception = (Throwable) MockLog.throwExceptions.get(methodName);
        if (exception != null) {
            throw new RuntimeException("The test may not be configured properly", exception);
        }

        HashMap arguments = new HashMap();
        arguments.put("1", level0);
        arguments.put("2", object0);
        List args = (List) MockLog.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList();
            MockLog.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through {@link
     * #setMethodResult(String, Object)} method.</p>
     *
     * @see Log#isEnabled(Level)
     */
    public boolean isEnabled(Level level0) {
        if (MockLog.globalException != null) {
            throw new RuntimeException("The test may not be configured properly", MockLog.globalException);
        }

        String methodName = "isEnabled_Level";

        Throwable exception = (Throwable) MockLog.throwExceptions.get(methodName);
        if (exception != null) {
            throw new RuntimeException("The test may not be configured properly", exception);
        }

        HashMap arguments = new HashMap();
        arguments.put("1", level0);
        List args = (List) MockLog.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList();
            MockLog.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockLog.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockLog.methodResults.put(methodSignature, result);
    }

    /**
     * <p>Gets the value of the specified argument which has been passed to the specified method by the caller.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @param argumentName a <code>String</code> providing the name of the argument to get the value for.
     * @return an <code>Object</code> (including <code>null</code>) providing the value of the specified argument which
     *         has been supplied by the caller of the specified method.
     * @throws IllegalArgumentException if the specified argument does not exist.
     */
    public static Object getMethodArgument(String methodSignature, String argumentName) {
        Map arguments = (Map) MockLog.methodArguments.get(methodSignature);
        if (!arguments.containsKey(argumentName)) {
            throw new IllegalArgumentException("The argument name " + argumentName + " is unknown.");
        }
        return arguments.get(argumentName);
    }

    /**
     * <p>Checks if the specified method has been called during the test by the caller.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @return <code>true</code> if specified method was called; <code>false</code> otherwise.
     */
    public static boolean wasMethodCalled(String methodSignature) {
        return methodArguments.containsKey(methodSignature);
    }

    /**
     * <p>Gets the values of the argumenta which have been passed to the specified method by the caller.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @return a <code>List</code> of <code>Map</code> providing the values of the arguments on each call. which has
     *         been supplied by the caller of the specified method.
     */
    public static List getMethodArguments(String methodSignature) {
        return (List) MockLog.methodArguments.get(methodSignature);
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @param exception a <code>Throwable</code> representing the exception to be thrown when the specified method is
     * called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwException(String methodSignature, Throwable exception) {
        if (exception != null) {
            MockLog.throwExceptions.put(methodSignature, exception);
        } else {
            MockLog.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is called.
     * If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockLog.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockLog</code> so all collected method arguments, configured method results and
     * exceptions are lost.</p>
     */
    public static void releaseState() {
        MockLog.methodArguments.clear();
        MockLog.methodResults.clear();
        MockLog.throwExceptions.clear();
        MockLog.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockLog</code> class.</p>
     */
    public static void init() {
    }

    /**
     * <p>
     * Logs a given message using a given logging level, message object and
     * formatter for the object.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level is null. The message should be
     * formated with the specified formatter (if specified) regardless if the
     * underlying implementation supports object formatting.
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the
     * objectFormatter is null, this method should behave the same as the
     * log(level,message) method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param message
     *            A possibly null message object to log
     * @param objectFormatter
     *            A possibly null message object formatter
     */
    public void log(Level level, Object message, ObjectFormatter objectFormatter) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, message format and a
     * single parameter for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1 can be passed to the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     */
    public void log(Level level, String messageFormat, Object arg1) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, message format and two
     * parameters for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1/arg2 can be used for the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     * @param arg2
     *            A possibly null argument for the message format
     */
    public void log(Level level, String messageFormat, Object arg1, Object arg2) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, message format and
     * three parameters for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1/arg2/arg3 can be used for the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     * @param arg2
     *            A possibly null argument for the message format
     * @param arg3
     *            A possibly null argument for the message format
     */
    public void log(Level level, String messageFormat, Object arg1,
            Object arg2, Object arg3) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, message format and an
     * array of parameters to use with the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arguments can be used for the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param args
     *            A possibly null, possibly empty array of arguments for the
     *            message format
     */
    public void log(Level level, String messageFormat, Object[] args) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable and message
     * object.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level is null. The message should be
     * formated from the <code>LogManager.getObjectFormatter</code> unless the
     * underlying implementation can do the formatting.
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the throwable is
     * null, this method should log in the same way as the
     * <code>log(Level, Object)</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> Added to the contract that the message should be
     * formatted via the <code>LogManager.getObjectFormatter</code> (rather
     * thantoString()).
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param message
     *            A possibly null message object to log
     */
    public void log(Level level, Throwable cause, Object message) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable, message
     * object and formatter for the object.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level is null. The message should be
     * formated with the specified formatter (if specified) regardless if the
     * underlying implementation supports object formatting.
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the
     * objectFormatter is null, this method should behave the same as the
     * <code>log(Level, Object)</code> method. If the throwable is null, this
     * method should log in the same way as the
     * <code>log(Level, Object, ObjectFormatter)</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param message
     *            A possibly null message object to log
     * @param objectFormatter
     *            A possibly null message object formatter
     */
    public void log(Level level, Throwable cause, Object message,
            ObjectFormatter objectFormatter) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable, message
     * format and a single parameter for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1 can be passed to the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the throwable is
     * null, this method should log in the same way as the
     * <code>log(Level, String, Object)</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     */
    public void log(Level level, Throwable cause, String messageFormat,
            Object arg1) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable, message
     * format and a two parameters for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1/arg2 can be used for the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the throwable is
     * null, this method should log in the same way as the
     * <code>log(Level, String, Object, Object)</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     * @param arg2
     *            A possibly null argument for the message format
     */
    public void log(Level level, Throwable cause, String messageFormat,
            Object arg1, Object arg2) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable, message
     * format and three parameters for the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arg1/arg2/arg3 can be used for the message format however). Any exception
     * that occurs should be silently ignored.
     * </p>
     *
     * <p>
     * If the throwable is null, this method should log in the same way as the
     * <code>log(Level, String, Object, Object, Object)</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param arg1
     *            A possibly null argument for the message format
     * @param arg2
     *            A possibly null argument for the message format
     * @param arg3
     *            A possibly null argument for the message format
     */
    public void log(Level level, Throwable cause, String messageFormat,
            Object arg1, Object arg2, Object arg3) {
        // empty
    }

    /**
     * <p>
     * Logs a given message using a given logging level, throwable, message
     * format and an array of parameters to use with the format.
     * </p>
     *
     * <p>
     * Nothing should be logged if the level or message format is null (null
     * arguments can be used for the message format however).
     * </p>
     *
     * <p>
     * Any exception that occurs should be silently ignored. If the throwable is
     * null, this method should log in the same way as the
     * <code>log(Level, String, Object[])</code> method.
     * </p>
     *
     * <p>
     * <b>Changes to v2.0: </b> This is a new method for the interface.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param messageFormat
     *            A possibly null, possibly empty (trim'd) message format string
     * @param args
     *            A possibly null, possibly empty array of arguments for the
     *            message format
     */
    public void log(Level level, Throwable cause, String messageFormat,
            Object[] args) {
        // empty
    }
}
