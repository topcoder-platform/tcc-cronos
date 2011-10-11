/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.ScorecardIDInfo;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.validation.ValidationException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>A mock implementation of {@link ScorecardManager} class to be used for testing.
  * Overrides the protected methods declared by a super-class. The overridden methods are declared with
 * package private access so only the test cases could invoke them. The overridden methods simply call
 * the corresponding method of a super-class.
 *
 * <p>The version 1.4 add one method: getDefaultScorecardsIDInfo, to suit new ScorecardManager.</p>
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>unimplemented methods added.</li>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowException.</li>
 * </ol>
 * </p>
 *
 * @author  isv, stevenfrog, TMALBONPH
 * @version 1.6.2
 * @since 1.4
 */
public class MockScorecardManager implements ScorecardManager {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping
     * the <code>String</code> names of the arguments to <code>Object</code>s representing the values of  arguments
     * which have been provided by the caller of the method.</p>
     */
    private static HashMap<String, Object> methodArguments = new HashMap<String, Object>();

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
     * <p>Constructs new <code>MockScorecardManager</code> instance.</p>
     */
    public MockScorecardManager() {
    }

    /**
     * <p>Constructs new <code>MockScorecardManager</code> instance.</p>
     */
    public MockScorecardManager(String namespace) {
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
     * @throws PersistenceException If parameter exception is not <code>null</code>
     * and an instance of PersistenceException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of PersistenceException.
     */
    private static void checkAndThrowException(Throwable exception) throws PersistenceException {
        if (exception != null) {
            if (exception instanceof PersistenceException) {
                throw (PersistenceException) exception;
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
     * @param scorecard0 the Scorecard.
     * @param string0 the String.
     *
     * @see ScorecardManager#createScorecard(Scorecard, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void createScorecard(Scorecard scorecard0, String string0)
        throws PersistenceException, ValidationException {
        if (MockScorecardManager.globalException != null) {
            if (MockScorecardManager.globalException instanceof PersistenceException) {
                throw (PersistenceException) MockScorecardManager.globalException;
            } else if (MockScorecardManager.globalException instanceof ValidationException) {
                throw (ValidationException) MockScorecardManager.globalException;
            } else {
                throw new RuntimeException("The test may not be configured properly",
                    MockScorecardManager.globalException);
            }
        }

        String methodName = "createScorecard_Scorecard_String";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof PersistenceException) {
                throw (PersistenceException) exception;
            } else if (exception instanceof ValidationException) {
                throw (ValidationException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", scorecard0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param scorecard0 the Scorecard.
     * @param string0 the String.
     *
     * @see ScorecardManager#updateScorecard(Scorecard, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateScorecard(Scorecard scorecard0, String string0)
        throws PersistenceException, ValidationException {
        if (MockScorecardManager.globalException != null) {
            if (MockScorecardManager.globalException instanceof PersistenceException) {
                throw (PersistenceException) MockScorecardManager.globalException;
            } else if (MockScorecardManager.globalException instanceof ValidationException) {
                throw (ValidationException) MockScorecardManager.globalException;
            } else {
                throw new RuntimeException("The test may not be configured properly",
                    MockScorecardManager.globalException);
            }
        }

        String methodName = "updateScorecard_Scorecard_String";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof PersistenceException) {
                throw (PersistenceException) exception;
            } else if (exception instanceof ValidationException) {
                throw (ValidationException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", scorecard0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
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
     * @return the Scorecard.
     *
     * @see ScorecardManager#getScorecard(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public Scorecard getScorecard(long long0) throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getScorecard_long";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Scorecard) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     * @param boolean0 the boolean.
     *
     * @return the Scorecard[].
     *
     * @see ScorecardManager#searchScorecards(Filter, boolean)
     * @throws PersistenceException If any persistence error occur.
     */
    public Scorecard[] searchScorecards(Filter filter0, boolean boolean0) throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "searchScorecards_Filter_boolean";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        arguments.put("2", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Scorecard[]) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the ScorecardType[].
     *
     * @see ScorecardManager#getAllScorecardTypes()
     * @throws PersistenceException If any persistence error occur.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getAllScorecardTypes";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScorecardType[]) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the QuestionType[].
     *
     * @see ScorecardManager#getAllQuestionTypes()
     * @throws PersistenceException If any persistence error occur.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getAllQuestionTypes";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (QuestionType[]) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the ScorecardStatus[].
     *
     * @see ScorecardManager#getAllScorecardStatuses()
     * @throws PersistenceException If any persistence error occur.
     */
    public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getAllScorecardStatuses";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScorecardStatus[]) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long[].
     * @param boolean0 the boolean.
     *
     * @return the Scorecard[].
     *
     * @see ScorecardManager#getScorecards(long[], boolean)
     * @throws PersistenceException If any persistence error occur.
     */
    public Scorecard[] getScorecards(long[] longA0, boolean boolean0) throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getScorecards_long[]_boolean";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        arguments.put("2", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Scorecard[]) MockScorecardManager.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockScorecardManager.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockScorecardManager.methodArguments.get(methodSignature);
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
        return (List) MockScorecardManager.methodArguments.get(methodSignature);
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
            MockScorecardManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockScorecardManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockScorecardManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockScorecardManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockScorecardManager.methodArguments.clear();
        MockScorecardManager.methodResults.clear();
        MockScorecardManager.throwExceptions.clear();
        MockScorecardManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockScorecardManager</code> class.</p>
     */
    public static void init() {
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the ScorecardIDInfo[].
     *
     * @since 1.4
     * @see ScorecardManager#getDefaultScorecardsIDInfo(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long arg0) throws PersistenceException {
        checkAndThrowException(MockScorecardManager.globalException);

        String methodName = "getDefaultScorecardsIDInfo_long";

        Throwable exception = (Throwable) MockScorecardManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockScorecardManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockScorecardManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ScorecardIDInfo[]) MockScorecardManager.methodResults.get(methodName);

    }

}
