/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.search.builder.filter.Filter;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>A mock implementation of {@link ProjectManager} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared
 * with package private access so only the test cases could invoke them. The overridden methods simply
 * call the corresponding method of a super-class.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>added unimplemented methods.</li>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowException.</li>
 * </ol>
 * </p>
 *
 * @author isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping the <code>
     * String</code> names of the arguments to <code>Object</code>s representing the values of  arguments
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
     * <p>Constructs new <code>MockProjectManager</code> instance.</p>
     */
    public MockProjectManager() {
    }

    /**
     * <p>Constructs new <code>MockProjectManager</code> instance.</p>
     */
    public MockProjectManager(String namespace) {
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
     * @throws ValidationException If parameter exception is not <code>null</code>
     * and an instance of ValidationException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of PersistenceException or ValidationException.
     */
    private static void checkThrowableException(Throwable exception) throws PersistenceException, ValidationException {
        if (exception != null) {
            if (exception instanceof PersistenceException) {
                throw (PersistenceException) exception;
            } else if (exception instanceof ValidationException) {
                throw (ValidationException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }
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
     * and not an instance of PersistenceException or ValidationException.
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
     * @see ProjectManager#createProject(Project, String)
     * @throws PersistenceException
     * @throws ValidationException
     */
    public void createProject(Project project0, String string0) throws PersistenceException, ValidationException {
        checkThrowableException(MockProjectManager.globalException);

        String methodName = "createProject_Project_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkThrowableException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", project0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#updateProject(Project, String, String)
     * @throws PersistenceException
     * @throws ValidationException
     */
    public void updateProject(Project project0, String string0, String string1) throws PersistenceException, ValidationException {
        checkThrowableException(MockProjectManager.globalException);

        String methodName = "updateProject_Project_String_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkThrowableException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", project0);
        arguments.put("2", string0);
        arguments.put("3", string1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getProject(long)
     * @throws PersistenceException
     */
    public Project getProject(long long0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProject_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getProjects(long[])
     * @throws PersistenceException
     */
    public Project[] getProjects(long[] longA0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjects_long[]";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#searchProjects(Filter)
     * @throws PersistenceException
     */
    public Project[] searchProjects(Filter filter0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "searchProjects_Filter";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getUserProjects(long)
     * @throws PersistenceException
     */
    public Project[] getUserProjects(long long0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getUserProjects_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getAllProjectTypes()
     * @throws PersistenceException
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getAllProjectTypes";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectType[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getAllProjectCategories()
     * @throws PersistenceException
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getAllProjectCategories";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectCategory[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getAllProjectStatuses()
     * @throws PersistenceException
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getAllProjectStatuses";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectStatus[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see ProjectManager#getAllProjectPropertyTypes()
     * @throws PersistenceException
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getAllProjectPropertyTypes";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectPropertyType[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose
     * create date is within current - days
     * </p>
     * @param days last 'days'
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException  {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjectsByCreateDate_int";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(days));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project[]) MockProjectManager.methodResults.get(methodName);
    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockProjectManager.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockProjectManager.methodArguments.get(methodSignature);
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
        return (List) MockProjectManager.methodArguments.get(methodSignature);
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
            MockProjectManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockProjectManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockProjectManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockProjectManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockProjectManager.methodArguments.clear();
        MockProjectManager.methodResults.clear();
        MockProjectManager.throwExceptions.clear();
        MockProjectManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockProjectManager</code> class.</p>
     */
    public static void init() {
    }

// ------------------------------------------------------------------ additional methods 1.6.2

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the FileType.
     * @param arg1 the String.
     *
     * @return the FileType.
     *
     * @since 1.6.2
     * @see ProjectManager#createFileType(FileType, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public FileType createFileType(FileType arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "createFileType_FileType_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (FileType) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the Prize.
     * @param arg1 the String.
     *
     * @return the Prize.
     *
     * @since 1.6.2
     * @see ProjectManager#createPrize(Prize, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public Prize createPrize(Prize arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "createPrize_Prize_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Prize) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the ProjectStudioSpecification.
     * @param arg1 the String.
     *
     * @return the ProjectStudioSpecification.
     *
     * @since 1.6.2
     * @see ProjectManager#createProjectStudioSpecification(ProjectStudioSpecification, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public ProjectStudioSpecification createProjectStudioSpecification(ProjectStudioSpecification arg0, String arg1)
        throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "createProjectStudioSpecification_ProjectStudioSpecification_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectStudioSpecification) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the FileType[].
     *
     * @since 1.6.2
     * @see ProjectManager#getAllFileTypes()
     * @throws PersistenceException If any persistence error occur.
     */
    public FileType[] getAllFileTypes() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getAllFileTypes";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (FileType[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the PrizeType[].
     *
     * @since 1.6.2
     * @see ProjectManager#getPrizeTypes()
     * @throws PersistenceException If any persistence error occur.
     */
    public PrizeType[] getPrizeTypes() throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getPrizeTypes";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PrizeType[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the FileType[].
     *
     * @since 1.6.2
     * @see ProjectManager#getProjectFileTypes(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public FileType[] getProjectFileTypes(long arg0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjectFileTypes_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (FileType[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the Prize[].
     *
     * @since 1.6.2
     * @see ProjectManager#getProjectPrizes(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public Prize[] getProjectPrizes(long arg0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjectPrizes_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Prize[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the ProjectStudioSpecification.
     *
     * @since 1.6.2
     * @see ProjectManager#getProjectStudioSpecification(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public ProjectStudioSpecification getProjectStudioSpecification(long arg0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjectStudioSpecification_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ProjectStudioSpecification) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the Project[].
     *
     * @since 1.6.2
     * @see ProjectManager#getProjectsByDirectProjectId(long)
     * @throws PersistenceException If any persistence error occur.
     */
    public Project[] getProjectsByDirectProjectId(long arg0) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "getProjectsByDirectProjectId_long";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Project[]) MockProjectManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the FileType.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#removeFileType(FileType, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void removeFileType(FileType arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "removeFileType_FileType_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the Prize.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#removePrize(Prize, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void removePrize(Prize arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "removePrize_Prize_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the ProjectStudioSpecification.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#removeProjectStudioSpecification(ProjectStudioSpecification, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void removeProjectStudioSpecification(ProjectStudioSpecification arg0, String arg1)
        throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "removeProjectStudioSpecification_ProjectStudioSpecification_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the FileType.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updateFileType(FileType, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateFileType(FileType arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updateFileType_FileType_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the Prize.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updatePrize(Prize, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updatePrize(Prize arg0, String arg1) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updatePrize_Prize_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     * @param arg1 the List<FileType>.
     * @param arg2 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updateProjectFileTypes(long, List<FileType>, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateProjectFileTypes(long arg0, List<FileType> arg1, String arg2) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updateProjectFileTypes_long_List<FileType>_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        arguments.put("2", arg1);
        arguments.put("3", arg2);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     * @param arg1 the List<Prize>.
     * @param arg2 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updateProjectPrizes(long, List<Prize>, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateProjectPrizes(long arg0, List<Prize> arg1, String arg2) throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updateProjectPrizes_long_List<Prize>_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        arguments.put("2", arg1);
        arguments.put("3", arg2);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the ProjectStudioSpecification.
     * @param arg1 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updateProjectStudioSpecification(ProjectStudioSpecification, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateProjectStudioSpecification(ProjectStudioSpecification arg0, String arg1)
        throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updateProjectStudioSpecification_ProjectStudioSpecification_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", arg1);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the ProjectStudioSpecification.
     * @param arg1 the long.
     * @param arg2 the String.
     *
     * @since 1.6.2
     * @see ProjectManager#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)
     * @throws PersistenceException If any persistence error occur.
     */
    public void updateStudioSpecificationForProject(ProjectStudioSpecification arg0, long arg1, String arg2)
        throws PersistenceException {
        checkAndThrowException(MockProjectManager.globalException);

        String methodName = "updateStudioSpecificationForProject_ProjectStudioSpecification_long_String";

        Throwable exception = (Throwable) MockProjectManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        arguments.put("2", new Long(arg1));
        arguments.put("3", arg2);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockProjectManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockProjectManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

}
