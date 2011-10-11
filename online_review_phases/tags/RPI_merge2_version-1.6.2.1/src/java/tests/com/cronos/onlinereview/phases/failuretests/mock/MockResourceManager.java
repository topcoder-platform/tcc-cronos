/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.idgenerator.IDGenerator;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A mock implementation of {@link ResourceManager} class to be used for testing.
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
 * <li>add common codes into method checkAndThrowBuilderException.</li>
 * </ol>
 * </p>
 *
 * @author isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
public class MockResourceManager implements ResourceManager {

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
     * <p>Constructs new <code>MockResourceManager</code> instance.</p>
     */
    public MockResourceManager() {
    }

    /**
     * <p>Constructs new <code>MockResourceManager</code> instance.</p>
     */
    public MockResourceManager(String namespace) {
    }

    /**
     * Creates a new PersistenceResourceManager, initializing all fields to the given values.
     *
     * @param persistence
     *            The persistence for Resources and related objects
     * @param resourceSearchBundle
     *            The search bundle for searching resources
     * @param resourceRoleSearchBundle
     *            The search bundle for searching resource roles
     * @param notificationSearchBundle
     *            The search bundle for searching notifications
     * @param notificationTypeSearchBundle
     *            The search bundle for searching notification types
     * @param resourceIdGenerator
     *            The generator for Resource ids
     * @param resourceRoleIdGenerator
     *            The generator for ResourceRole ids
     * @param notificationTypeIDGenerator
     *            The generator for NotificationType ids
     * @throws IllegalArgumentException
     *             If any argument is null
     */
    public MockResourceManager(ResourcePersistence persistence, SearchBundle resourceSearchBundle,
            SearchBundle resourceRoleSearchBundle, SearchBundle notificationSearchBundle,
            SearchBundle notificationTypeSearchBundle, IDGenerator resourceIdGenerator,
            IDGenerator resourceRoleIdGenerator, IDGenerator notificationTypeIDGenerator) {

    }

    /**
     * creates a new PersistenceResourceManager. The SearchBundle fields should be initialized by retrieving the
     * SearchBundles from the SearchBundlerManager using the constants defined in this class. The IDGenerator fields
     * should be initialized by retrieving the IDGenerators from the IDGeneratorFactory using the constants defined in
     * this class.
     *
     * @param persistence
     *            The persistence for Resources and related objects
     * @param searchBundleManager
     *            The manager containing the various SearchBundles needed
     * @throws IllegalArgumentException
     *             If any argument is null or any search bundle or id generator is not available under the required
     *             names
     */
    public MockResourceManager(ResourcePersistence persistence, SearchBundleManager searchBundleManager) {
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
     * @throws ResourcePersistenceException If parameter exception is not <code>null</code>
     * and an instance of ResourcePersistenceException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of ResourcePersistenceException.
     */
    private static void checkAndThrowException(Throwable exception) throws ResourcePersistenceException {
        if (exception != null) {
            if (exception instanceof ResourcePersistenceException) {
                throw (ResourcePersistenceException) exception;
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
     * @throws ResourcePersistenceException If parameter exception is not <code>null</code>
     * and an instance of ResourcePersistenceException.
     * @throws SearchBuilderException If parameter exception is not <code>null</code>
     * and an instance of SearchBuilderException.
     * @throws SearchBuilderConfigurationException If parameter exception is not <code>null</code>
     * and an instance of SearchBuilderConfigurationException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of ResourcePersistenceException or SearchBuilderException
     * or SearchBuilderConfigurationException.
     */
    private static void checkAndThrowBuilderException(Throwable exception)
        throws ResourcePersistenceException, SearchBuilderException,
        SearchBuilderConfigurationException {
        if (exception != null) {
            if (exception instanceof ResourcePersistenceException) {
                throw (ResourcePersistenceException) exception;
            } else if (exception instanceof SearchBuilderException) {
                throw (SearchBuilderException) exception;
            } else if (exception instanceof SearchBuilderConfigurationException) {
                throw (SearchBuilderConfigurationException) exception;
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
     * @param resource0 the Resource.
     * @param string0 the String.
     *
     * @return the Resource.
     *
     * @see ResourceManager#updateResource(Resource, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public Resource updateResource(Resource resource0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "updateResource_Resource_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resource0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Resource) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resource0 the Resource.
     * @param string0 the String.
     *
     * @see ResourceManager#removeResource(Resource, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void removeResource(Resource resource0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "removeResource_Resource_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resource0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resourceA0 the Resource Array.
     * @param long0 the long.
     * @param string0 the String.
     *
     * @return the Resource Array.
     *
     * @see ResourceManager#updateResources(Resource[], long, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public Resource[] updateResources(Resource[] resourceA0, long long0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "updateResources_Resource[]_long_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resourceA0);
        arguments.put("2", new Long(long0));
        arguments.put("3", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Resource[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param long0 the long.
     *
     * @return the Resource.
     *
     * @see ResourceManager#getResource(long)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public Resource getResource(long long0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "getResource_long";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Resource) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resourceRole0 the ResourceRole.
     * @param string0 the String.
     *
     * @see ResourceManager#updateResourceRole(ResourceRole, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void updateResourceRole(ResourceRole resourceRole0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "updateResourceRole_ResourceRole_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resourceRole0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resourceRole0 the ResourceRole.
     * @param string0 the String.
     *
     * @see ResourceManager#removeResourceRole(ResourceRole, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void removeResourceRole(ResourceRole resourceRole0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "removeResourceRole_ResourceRole_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resourceRole0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the ResourceRole Array.
     *
     * @see ResourceManager#getAllResourceRoles()
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "getAllResourceRoles";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ResourceRole[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     * @param long0 the long.
     * @param long1 the long.
     * @param string0 the String.
     *
     * @see ResourceManager#addNotifications(long[], long, long, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void addNotifications(long[] longA0, long long0, long long1, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "addNotifications_long[]_long_long_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        arguments.put("2", new Long(long0));
        arguments.put("3", new Long(long1));
        arguments.put("4", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     * @param long0 the long.
     * @param long1 the long.
     * @param string0 the String.
     *
     * @see ResourceManager#removeNotifications(long[], long, long, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void removeNotifications(long[] longA0, long long0, long long1, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "removeNotifications_long[]_long_long_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        arguments.put("2", new Long(long0));
        arguments.put("3", new Long(long1));
        arguments.put("4", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param long0 the long.
     * @param long1 the long.
     *
     * @return the long Array.
     *
     * @see ResourceManager#getNotifications(long, long)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public long[] getNotifications(long long0, long long1) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "getNotifications_long_long";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        arguments.put("2", new Long(long1));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param notificationType0 the NotificationType.
     * @param string0 the String.
     *
     * @see ResourceManager#updateNotificationType(NotificationType, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void updateNotificationType(NotificationType notificationType0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "updateNotificationType_NotificationType_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", notificationType0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param notificationType0 the NotificationType.
     * @param string0 the String.
     *
     * @see ResourceManager#removeNotificationType(NotificationType, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void removeNotificationType(NotificationType notificationType0, String string0) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "removeNotificationType_NotificationType_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", notificationType0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the NotificationType Array.
     *
     * @see ResourceManager#getAllNotificationTypes()
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "getAllNotificationTypes";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (NotificationType[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockResourceManager.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockResourceManager.methodArguments.get(methodSignature);
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
        return (List) MockResourceManager.methodArguments.get(methodSignature);
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
            MockResourceManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockResourceManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockResourceManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockResourceManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockResourceManager.methodArguments.clear();
        MockResourceManager.methodResults.clear();
        MockResourceManager.throwExceptions.clear();
        MockResourceManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockResourceManager</code> class.</p>
     */
    public static void init() {
        setMethodResult("searchResources_Filter", new Resource[] {new Resource(1)});
        MockResourceManager.setMethodResult("getAllResourceRoles", new ResourceRole[0]);
    }

// ------------------------------------------------------------- additional member 1.6.2

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the Resource Array.
     *
     * @see ResourceManager#searchResources(Filter)
     * @throws ResourcePersistenceException If any Persistence error occur.
     * @throws SearchBuilderException If any search builder error occur.
     * @throws SearchBuilderConfigurationException If any search configuration error occur.
     */
    public Resource[] searchResources(Filter filter0)
        throws ResourcePersistenceException, SearchBuilderException,
        SearchBuilderConfigurationException {
        checkAndThrowBuilderException(MockResourceManager.globalException);

        String methodName = "searchResources_Filter";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Resource[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the ResourceRole Array.
     *
     * @see ResourceManager#searchResourceRoles(Filter)
     * @throws ResourcePersistenceException If any Persistence error occur.
     * @throws SearchBuilderException If any search builder error occur.
     * @throws SearchBuilderConfigurationException If any search configuration error occur.
     */
    public ResourceRole[] searchResourceRoles(Filter filter0)
        throws ResourcePersistenceException, SearchBuilderException,
        SearchBuilderConfigurationException {
        checkAndThrowBuilderException(MockResourceManager.globalException);

        String methodName = "searchResourceRoles_Filter";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (ResourceRole[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the Notification Array.
     *
     * @see ResourceManager#searchNotifications(Filter)
     * @throws ResourcePersistenceException If any Persistence error occur.
     * @throws SearchBuilderException If any search builder error occur.
     * @throws SearchBuilderConfigurationException If any search configuration error occur.
     */
    public Notification[] searchNotifications(Filter filter0)
        throws ResourcePersistenceException, SearchBuilderException,
        SearchBuilderConfigurationException {
        checkAndThrowBuilderException(MockResourceManager.globalException);

        String methodName = "searchNotifications_Filter";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Notification[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the NotificationType Array.
     *
     * @see ResourceManager#searchNotificationTypes(Filter)
     * @throws ResourcePersistenceException If any Persistence error occur.
     * @throws SearchBuilderException If any search builder error occur.
     * @throws SearchBuilderConfigurationException If any search configuration error occur.
     */
    public NotificationType[] searchNotificationTypes(Filter filter0)
        throws ResourcePersistenceException, SearchBuilderException,
        SearchBuilderConfigurationException {
        checkAndThrowBuilderException(MockResourceManager.globalException);

        String methodName = "searchNotificationTypes_Filter";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (NotificationType[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param user the long.
     * @param projects the long Array.
     * @param notificationType the long.
     * @param operator the String.
     *
     * @see ResourceManager#addNotifications(long, long[], long, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void addNotifications(long user, long[] projects, long notificationType, String operator)
        throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "addNotifications_long_long[]_long_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(user));
        arguments.put("2", projects);
        arguments.put("3", new Long(notificationType));
        arguments.put("4", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param user the long.
     * @param projects the long Array.
     * @param notificationType the long.
     * @param operator the String.
     *
     * @see ResourceManager#removeNotifications(long, long[], long, String)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public void removeNotifications(long user, long[] projects, long notificationType, String operator)
        throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "removeNotifications_long_long[]_long_String";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(user));
        arguments.put("2", projects);
        arguments.put("3", new Long(notificationType));
        arguments.put("4", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param user the long.
     * @param notificationType the long.
     *
     * @return the long Array.
     *
     * @see ResourceManager#getNotificationsForUser(long, long)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public long[] getNotificationsForUser(long user, long notificationType) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "getNotificationsForUser_long_long";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(user));
        arguments.put("2", new Long(notificationType));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long[]) MockResourceManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param projectId the long.
     * @param roleId the long.
     *
     * @return the Resource Array.
     *
     * @see ResourceManager#searchResources(long, long)
     * @throws ResourcePersistenceException If any Persistence error occur.
     */
    public Resource[] searchResources(long projectId, long roleId) throws ResourcePersistenceException {
        checkAndThrowException(MockResourceManager.globalException);

        String methodName = "searchResources_long_long";

        Throwable exception = (Throwable) MockResourceManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(projectId));
        arguments.put("2", new Long(roleId));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockResourceManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockResourceManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Resource[]) MockResourceManager.methodResults.get(methodName);

    }

}
