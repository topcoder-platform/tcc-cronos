/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A mock implementation of {@link ScreeningManager} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared with
 * package private access so only the test cases could invoke them. The overridden methods simply call
 * the corresponding method of a super-class.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowScreeningException.</li>
 * </ol>
 * </p>
 *
 * @author  isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
public class MockScreeningManager implements ScreeningManager {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping
     * the <code>String</code> names of the arguments to <code>Object</code>s representing the values of  arguments
     * which have been provided by the caller of the method.</p>
     */
    static HashMap<String, Object> methodArguments = new HashMap<String, Object>();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Exception</code>s
     * to be thrown by methods.</p>
     */
    private static HashMap<String, Throwable> throwExceptions = new HashMap<String, Throwable>();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Object</code>s to be
     * returned by methods.</p>
     */
    private static HashMap<String, Object> methodResults = new HashMap<String, Object>();

    /**
     * <p>A <code>Throwable</code> representing the exception to be thrown from any method of the mock class.</p>
     */
    private static Throwable globalException = null;

    /**
     * <p>Constructs new <code>MockScreeningManager</code> instance.</p>
     */
    public MockScreeningManager() {
    }

    /**
     * <p>Constructs new <code>MockScreeningManager</code> instance.</p>
     */
    public MockScreeningManager(String namespace) {
    }

    /**
     * <p>Common method to check if method is to throw the given exception.</p>
     * <p>
     * Version 1.6.2 (Online Review Phases) Change notes:
     * <ol>
     * <li>move all common code checks in here.</li>
     * <li>added to make this class small in size.</li>
     * </ol>
     * </p>
     *
     * @param exception the Exception to check.
     *
     * @throws ScreeningTaskDoesNotExistException If parameter exception is not <code>null</code>
     * and an instance of ScreeningTaskDoesNotExistException.
     * @throws PersistenceException If parameter exception is not <code>null</code>
     * and not an instance of PersistenceException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of ScreeningTaskDoesNotExistException or PersistenceException.
     */
    private static void checkAndThrowScreeningException(Throwable exception)
        throws ScreeningTaskDoesNotExistException, PersistenceException {
        if (exception != null) {
            if (exception instanceof ScreeningTaskDoesNotExistException) {
                throw (ScreeningTaskDoesNotExistException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param long0 the long.
     * @param string0 the String.
     *
     * @see ScreeningManager#initiateScreening(long, String)
     * @throws ScreeningTaskAlreadyExistsException If any Screening task error occur.
     */
    public void initiateScreening(long long0, String string0)
        throws ScreeningTaskAlreadyExistsException, PersistenceException {
        if (MockScreeningManager.globalException != null) {
            if (MockScreeningManager.globalException instanceof PersistenceException) {
                throw (PersistenceException) MockScreeningManager.globalException;
            } else if (MockScreeningManager.globalException instanceof ScreeningTaskAlreadyExistsException) {
                throw (ScreeningTaskAlreadyExistsException) MockScreeningManager.globalException;
            } else {
                throw new RuntimeException("The test may not be configured properly",
                    MockScreeningManager.globalException);
            }
        }

        String methodName = "initiateScreening_long_String";

        Throwable exception = (Throwable) MockScreeningManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof PersistenceException) {
                throw (PersistenceException) exception;
            } else if (exception instanceof ScreeningTaskAlreadyExistsException) {
                throw (ScreeningTaskAlreadyExistsException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScreeningManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScreeningManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param long0 the long.
     *
     * @return the ScreeningTask.
     *
     * @see ScreeningManager#getScreeningDetails(long)
     * @throws ScreeningTaskDoesNotExistException If any Screening task error occur.
     */
    public ScreeningTask getScreeningDetails(long long0)
        throws ScreeningTaskDoesNotExistException, PersistenceException {
        checkAndThrowScreeningException(MockScreeningManager.globalException);

        String methodName = "getScreeningDetails_long";

        Throwable exception = (Throwable) MockScreeningManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowScreeningException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScreeningManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScreeningManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScreeningTask) MockScreeningManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the ScreeningTask Array.
     *
     * @see ScreeningManager#getScreeningTasks(long[])
     * @throws ScreeningTaskDoesNotExistException If any Screening task error occur.
     */
    public ScreeningTask[] getScreeningTasks(long[] longA0)
        throws ScreeningTaskDoesNotExistException, PersistenceException {
        checkAndThrowScreeningException(MockScreeningManager.globalException);

        String methodName = "getScreeningTasks_long[]";

        Throwable exception = (Throwable) MockScreeningManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowScreeningException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScreeningManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScreeningManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScreeningTask[]) MockScreeningManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     * @param boolean0 the boolean.
     *
     * @return the ScreeningTask Array.
     *
     * @see ScreeningManager#getScreeningTasks(long[], boolean)
     * @throws ScreeningTaskDoesNotExistException If any Screening task error occur.
     */
    public ScreeningTask[] getScreeningTasks(long[] longA0, boolean boolean0)
        throws ScreeningTaskDoesNotExistException, PersistenceException {
        checkAndThrowScreeningException(MockScreeningManager.globalException);

        String methodName = "getScreeningTasks_long[]_boolean";

        Throwable exception = (Throwable) MockScreeningManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowScreeningException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        arguments.put("2", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScreeningManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScreeningManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScreeningTask[]) MockScreeningManager.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockScreeningManager.methodResults.put(methodSignature, result);
    }

    /**
     * <p>Gets the value of the specified argument which has been passed to the specified method by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @param  argumentName a <code>String</code> providing the name of the argument to get the value for.
     * @return an <code>Object</code> (including <code>null</code>) providing the value of the specified argument
     *         which has been supplied by the caller of the specified method.
     * @throws IllegalArgumentException if the specified argument does not exist.
     */
    public static Object getMethodArgument(String methodSignature, String argumentName) {
        @SuppressWarnings("rawtypes")
        Map arguments = (Map) MockScreeningManager.methodArguments.get(methodSignature);
        if (!arguments.containsKey(argumentName)) {
            throw new IllegalArgumentException("The argument name " + argumentName + " is unknown.");
        }
        return arguments.get(argumentName);
    }

    /**
     * <pChecks if the specified method has been called during the test by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @return <code>true</code> if specified method was called; <code>false</code> otherwise.
     */
    public static boolean wasMethodCalled(String methodSignature) {
        return methodArguments.containsKey(methodSignature);
    }

    /**
     * <p>Gets the values of the arguments which have been passed to the specified method by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @return a <code>List</code> of <code>Map</code> providing the values of the arguments on each call.
     *         which has been supplied by the caller of the specified method.
     */
    @SuppressWarnings("rawtypes")
    public static List getMethodArguments(String methodSignature) {
        return (List) MockScreeningManager.methodArguments.get(methodSignature);
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @param exception a <code>Throwable</code> representing the exception to be thrown when the specified method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwException(String methodSignature, Throwable exception) {
        if (exception != null) {
            MockScreeningManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockScreeningManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockScreeningManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockScreeningManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockScreeningManager.methodArguments.clear();
        MockScreeningManager.methodResults.clear();
        MockScreeningManager.throwExceptions.clear();
        MockScreeningManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockScreeningManager</code> class.</p>
     */
    public static void init() {
    }

}
