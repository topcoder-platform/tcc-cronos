/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.ExternalUser;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>A mock implementation of {@link UserRetrieval} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared
 * with package private access so only the test cases could invoke them. The overridden methods simply
 * call the corresponding method of a super-class.
 * <p>
 * The version 1.4 add some methods: addSubmissionType, getAllSubmissionTypeIds, loadSubmissionType,
 * loadSubmissionTypes, removeSubmissionType, updateSubmissionType, to suit new UploadManager.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowException.</li>
 * </ol>
 * </p>
 *
 * @author  isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
public class MockUserRetrieval implements UserRetrieval {

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
     * <p>Constructs new <code>MockUserRetrieval</code> instance.</p>
     */
    public MockUserRetrieval() {
    }

    /**
     * <p>Constructs new <code>MockUserRetrieval</code> instance.</p>
     */
    public MockUserRetrieval(String namespace) {
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
     * @throws RetrievalException If parameter exception is not <code>null</code>
     * and an instance of RetrievalException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of RetrievalException.
     */
    private static void checkAndThrowException(Throwable exception) throws RetrievalException {
        if (exception != null) {
            if (exception instanceof RetrievalException) {
                throw (RetrievalException) exception;
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
     *
     * @return the ExternalUser.
     *
     * @see UserRetrieval#retrieveUser(long)
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser retrieveUser(long long0) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUser_long";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param string0 the String.
     *
     * @return the ExternalUser.
     *
     * @see UserRetrieval#retrieveUser(String)
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser retrieveUser(String string0) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUser_String";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the ExternalUser Array.
     *
     * @see UserRetrieval#retrieveUsers(long[])
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser[] retrieveUsers(long[] longA0) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUsers_long[]";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser[]) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param stringA0 the String Array.
     *
     * @return the ExternalUser Array.
     *
     * @see UserRetrieval#retrieveUsers(String[])
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser[] retrieveUsers(String[] stringA0) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUsers_String[]";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", stringA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser[]) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param stringA0 the String Array.
     *
     * @return the ExternalUser Array.
     *
     * @see UserRetrieval#retrieveUsersIgnoreCase(String[])
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser[] retrieveUsersIgnoreCase(String[] stringA0) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUsersIgnoreCase_String[]";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", stringA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser[]) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param string0 the String.
     * @param string1 the String.
     *
     * @return the ExternalUser Array.
     *
     * @see UserRetrieval#retrieveUsersByName(String, String)
     * @throws RetrievalException If any retrieval error occur.
     */
    public ExternalUser[] retrieveUsersByName(String string0, String string1) throws RetrievalException {
        checkAndThrowException(MockUserRetrieval.globalException);

        String methodName = "retrieveUsersByName_String_String";

        Throwable exception = (Throwable) MockUserRetrieval.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", string1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUserRetrieval.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUserRetrieval.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ExternalUser[]) MockUserRetrieval.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockUserRetrieval.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockUserRetrieval.methodArguments.get(methodSignature);
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
        return (List) MockUserRetrieval.methodArguments.get(methodSignature);
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
            MockUserRetrieval.throwExceptions.put(methodSignature, exception);
        } else {
            MockUserRetrieval.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockUserRetrieval.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockUserRetrieval</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockUserRetrieval.methodArguments.clear();
        MockUserRetrieval.methodResults.clear();
        MockUserRetrieval.throwExceptions.clear();
        MockUserRetrieval.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockUserRetrieval</code> class.</p>
     */
    public static void init() {
    }

}
