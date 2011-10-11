/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.search.builder.filter.Filter;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * A mock implementation of {@link ReviewManager} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared with
 * package private access so only the test cases could invoke them. The overridden methods simply call
 * the corresponding method of a super-class.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>unimplemented methods added.</li>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkGlobalException.</li>
 * </ol>
 * </p>
 *
 * @author isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
public class MockReviewManager implements ReviewManager {

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
     * <p>Constructs new <code>MockReviewManager</code> instance.</p>
     */
    public MockReviewManager() {
    }

    /**
     * <p>Constructs new <code>MockReviewManager</code> instance.</p>
     */
    public MockReviewManager(String namespace) {
    }

    /**
     * <p>Common method to check for globalException.</p>
     * <p>
     * Version 1.6.2 (Online Review Phases) Change notes:
     * <ol>
     * <li>move all common code checks in here.</li>
     * <li>added to make this class small in size.</li>
     * </ol>
     * </p>
     * @throws ReviewManagementException If globalException is not <code>null</code>
     * and an instance of ReviewManagementException.
     * @throws RuntimeException If globalException is not <code>null</code>
     * and not an instance of ReviewManagementException.
     */
    private void checkGlobalException() throws ReviewManagementException {
        if (MockReviewManager.globalException != null) {
            if (MockReviewManager.globalException instanceof ReviewManagementException) {
                throw (ReviewManagementException) MockReviewManager.globalException;
            } else {
                throw new RuntimeException("The test may not be configured properly",
                    MockReviewManager.globalException);
            }
        }
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#createReview(Review, String)
     * @throws ReviewManagementException
     */
    public void createReview(Review review0, String string0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "createReview_Review_String";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", review0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#updateReview(Review, String)
     * @throws ReviewManagementException
     */
    public void updateReview(Review review0, String string0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "updateReview_Review_String";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", review0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#getReview(long)
     * @throws ReviewManagementException
     */
    public Review getReview(long long0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "getReview_long";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Review) MockReviewManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#searchReviews(Filter, boolean)
     * @throws ReviewManagementException
     */
    public Review[] searchReviews(Filter filter0, boolean boolean0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "searchReviews_Filter_boolean";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        arguments.put("2", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Review[]) MockReviewManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#addReviewComment(long, Comment, String)
     * @throws ReviewManagementException
     */
    public void addReviewComment(long long0, Comment comment0, String string0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "addReviewComment_long_Comment_String";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        arguments.put("2", comment0);
        arguments.put("3", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#addItemComment(long, Comment, String)
     * @throws ReviewManagementException
     */
    public void addItemComment(long long0, Comment comment0, String string0) throws ReviewManagementException {
        checkGlobalException();

        String methodName = "addItemComment_long_Comment_String";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        arguments.put("2", comment0);
        arguments.put("3", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ReviewManager#getAllCommentTypes()
     * @throws ReviewManagementException
     */
    public CommentType[] getAllCommentTypes() throws ReviewManagementException {
        checkGlobalException();

        String methodName = "getAllCommentTypes";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (CommentType[]) MockReviewManager.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockReviewManager.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockReviewManager.methodArguments.get(methodSignature);
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
        return (List) MockReviewManager.methodArguments.get(methodSignature);
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
            MockReviewManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockReviewManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockReviewManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockReviewManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockReviewManager.methodArguments.clear();
        MockReviewManager.methodResults.clear();
        MockReviewManager.throwExceptions.clear();
        MockReviewManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockReviewManager</code> class.</p>
     */
    public static void init() {
        setMethodResult("searchReviews_Filter_boolean", new Review[] {new Review(1)});
    }

 // ------------------------------------------------------------------ additional methods 1.6.2

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @since 1.6.2
     * @see ReviewManager#getAllEvaluationTypes()
     * @throws ReviewManagementException
     */
    @Override
    public EvaluationType[] getAllEvaluationTypes() throws ReviewManagementException {
        checkGlobalException();

        String methodName = "getAllEvaluationTypes";

        Throwable exception = (Throwable) MockReviewManager.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof ReviewManagementException) {
                throw (ReviewManagementException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockReviewManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockReviewManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (EvaluationType[]) MockReviewManager.methodResults.get(methodName);

    }
}
