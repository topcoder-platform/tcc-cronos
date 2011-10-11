/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>A mock implementation of {@link UploadPersistence} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared
 * with package private access so only the test cases could invoke them. The overridden methods simply
 * call the corresponding method of a super-class.
 * <p>
 * The version 1.4 add some methods: addSubmissionType, getAllSubmissionTypeIds, loadSubmissionType,
 * loadSubmissionTypes, removeSubmissionType, updateSubmissionType, to suit new UploadManager.
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
 * @author  isv, stevenfrog, TCSASSEMBLER
 * @version 1.6.2
 * @since 1.0
 */
public class MockUploadPersistence implements UploadPersistence {

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
     * <p>Constructs new <code>MockUploadPersistence</code> instance.</p>
     */
    public MockUploadPersistence() {
    }

    /**
     * <p>Constructs new <code>MockUploadPersistence</code> instance.</p>
     */
    public MockUploadPersistence(String namespace) {
    }

    /**
     * <p>Constructs new <code>MockUploadPersistence</code> instance.</p>
     */
    public MockUploadPersistence(DBConnectionFactory factory, String namespace) {
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
     * @throws UploadPersistenceException If parameter exception is not <code>null</code>
     * and an instance of UploadPersistenceException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of UploadPersistenceException.
     */
    private static void checkAndThrowException(Throwable exception) throws UploadPersistenceException {
        if (exception != null) {
            if (exception instanceof UploadPersistenceException) {
                throw (UploadPersistenceException) exception;
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
     * @param uploadType0 the UploadType.
     *
     * @see UploadPersistence#addUploadType(UploadType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addUploadType(UploadType uploadType0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addUploadType_UploadType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadType0 the UploadType.
     *
     * @see UploadPersistence#removeUploadType(UploadType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUploadType(UploadType uploadType0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeUploadType_UploadType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadType0 the UploadType.
     *
     * @see UploadPersistence#updateUploadType(UploadType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUploadType(UploadType uploadType0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateUploadType_UploadType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
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
     * @return the UploadType.
     *
     * @see UploadPersistence#loadUploadType(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadType loadUploadType(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploadType_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadType) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the UploadType Array.
     *
     * @see UploadPersistence#loadUploadTypes(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadType[] loadUploadTypes(long[] longA0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploadTypes_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadType[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the long Array.
     *
     * @see UploadPersistence#getAllUploadTypeIds()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getAllUploadTypeIds";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     *
     * @see UploadPersistence#addUploadStatus(UploadStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addUploadStatus(UploadStatus uploadStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addUploadStatus_UploadStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     *
     * @see UploadPersistence#removeUploadStatus(UploadStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUploadStatus(UploadStatus uploadStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeUploadStatus_UploadStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     *
     * @see UploadPersistence#updateUploadStatus(UploadStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUploadStatus(UploadStatus uploadStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateUploadStatus_UploadStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
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
     * @return the UploadStatus.
     *
     * @see UploadPersistence#loadUploadStatus(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadStatus loadUploadStatus(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploadStatus_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadStatus) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the UploadStatus Array.
     *
     * @see UploadPersistence#loadUploadStatuses(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadStatus[] loadUploadStatuses(long[] longA0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploadStatuses_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadStatus[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the long Array.
     *
     * @see UploadPersistence#getAllUploadStatusIds()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getAllUploadStatusIds";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     *
     * @see UploadPersistence#addSubmissionStatus(SubmissionStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addSubmissionStatus_SubmissionStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     *
     * @see UploadPersistence#removeSubmissionStatus(SubmissionStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeSubmissionStatus_SubmissionStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     *
     * @see UploadPersistence#updateSubmissionStatus(SubmissionStatus)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateSubmissionStatus_SubmissionStatus";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
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
     * @return the SubmissionStatus.
     *
     * @see UploadPersistence#loadSubmissionStatus(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionStatus loadSubmissionStatus(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissionStatus_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionStatus) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the SubmissionStatus Array.
     *
     * @see UploadPersistence#loadSubmissionStatuses(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] longA0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissionStatuses_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionStatus[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the long Array.
     *
     * @see UploadPersistence#getAllSubmissionStatusIds()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getAllSubmissionStatusIds";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long []) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param upload0 the Upload.
     *
     * @see UploadPersistence#addUpload(Upload)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addUpload(Upload upload0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addUpload_Upload";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param upload0 the Upload.
     *
     * @see UploadPersistence#removeUpload(Upload)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUpload(Upload upload0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeUpload_Upload";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param upload0 the Upload.
     *
     * @see UploadPersistence#updateUpload(Upload)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUpload(Upload upload0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateUpload_Upload";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
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
     * @return the Upload.
     *
     * @see UploadPersistence#loadUpload(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Upload loadUpload(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUpload_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Upload) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the Upload Array.
     *
     * @see UploadPersistence#loadUploads(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Upload[] loadUploads(long[] longA0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploads_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Upload[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     *
     * @see UploadPersistence#addSubmission(Submission)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addSubmission(Submission submission0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addSubmission_Submission";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     *
     * @see UploadPersistence#removeSubmission(Submission)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmission(Submission submission0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeSubmission_Submission";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     *
     * @see UploadPersistence#updateSubmission(Submission)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmission(Submission submission0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateSubmission_Submission";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
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
     * @return the Submission.
     *
     * @see UploadPersistence#loadSubmission(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission loadSubmission(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmission_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param longA0 the long Array.
     *
     * @return the Submission Array.
     *
     * @see UploadPersistence#loadSubmissions(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] loadSubmissions(long[] longA0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissions_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", longA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockUploadPersistence.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockUploadPersistence.methodArguments.get(methodSignature);
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
        return (List) MockUploadPersistence.methodArguments.get(methodSignature);
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
            MockUploadPersistence.throwExceptions.put(methodSignature, exception);
        } else {
            MockUploadPersistence.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockUploadPersistence.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockUploadPersistence</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockUploadPersistence.methodArguments.clear();
        MockUploadPersistence.methodResults.clear();
        MockUploadPersistence.throwExceptions.clear();
        MockUploadPersistence.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockUploadPersistence</code> class.</p>
     */
    public static void init() {
    }

// ------------------------------------------------------------ additional methods 1.6.2

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resultSet the CustomResultSet.
     *
     * @return the Submission Array.
     *
     * @see UploadPersistence#loadSubmissions(CustomResultSet)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissions_CustomResultSet";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resultSet);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param resultSet the CustomResultSet.
     *
     * @return the Upload Array.
     *
     * @see UploadPersistence#loadUploads(CustomResultSet)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadUploads_CustomResultSet";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", resultSet);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Upload[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionType.
     *
     * @see UploadPersistence#addSubmissionType(SubmissionType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addSubmissionType(SubmissionType arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addSubmissionType_SubmissionType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the long Array.
     *
     * @see UploadPersistence#getAllSubmissionTypeIds()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getAllSubmissionTypeIds";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long []) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the SubmissionType.
     *
     * @see UploadPersistence#loadSubmissionType(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionType loadSubmissionType(long arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissionType_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionType) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long Array.
     *
     * @return the SubmissionType Array.
     *
     * @see UploadPersistence#loadSubmissionTypes(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionType[] loadSubmissionTypes(long[] arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadSubmissionTypes_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionType[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionType.
     *
     * @see UploadPersistence#removeSubmissionType(SubmissionType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionType(SubmissionType arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeSubmissionType_SubmissionType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionType.
     *
     * @see UploadPersistence#updateSubmissionType(SubmissionType)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionType(SubmissionType arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateSubmissionType_SubmissionType";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionImage.
     *
     * @see UploadPersistence#addSubmissionImage(SubmissionImage)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void addSubmissionImage(SubmissionImage arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "addSubmissionImage_SubmissionImage";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the long Array.
     *
     * @see UploadPersistence#getAllMimeTypeIds()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public long[] getAllMimeTypeIds() throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getAllMimeTypeIds";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (long []) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the SubmissionImage Array.
     *
     * @see UploadPersistence#getImagesForSubmission(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionImage[] getImagesForSubmission(long arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getImagesForSubmission_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionImage[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the Submission Array.
     *
     * @see UploadPersistence#getProjectSubmissions(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] getProjectSubmissions(long arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getProjectSubmissions_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     * @param arg1 the long.
     *
     * @return the Submission Array.
     *
     * @see UploadPersistence#getUserSubmissionsForProject(long, long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] getUserSubmissionsForProject(long arg0, long arg1) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "getUserSubmissionsForProject_long_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        arguments.put("2", new Long(arg1));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long.
     *
     * @return the MimeType.
     *
     * @see UploadPersistence#loadMimeType(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public MimeType loadMimeType(long arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadMimeType_long";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(arg0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (MimeType) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the long Array.
     *
     * @return the MimeType Array.
     *
     * @see UploadPersistence#loadMimeTypes(long[])
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public MimeType[] loadMimeTypes(long[] arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "loadMimeTypes_long[]";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (MimeType[]) MockUploadPersistence.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionImage.
     *
     * @see UploadPersistence#removeSubmissionImage(SubmissionImage)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionImage(SubmissionImage arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "removeSubmissionImage_SubmissionImage";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param arg0 the SubmissionImage.
     *
     * @see UploadPersistence#updateSubmissionImage(SubmissionImage)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionImage(SubmissionImage arg0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadPersistence.globalException);

        String methodName = "updateSubmissionImage_SubmissionImage";

        Throwable exception = (Throwable) MockUploadPersistence.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", arg0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadPersistence.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadPersistence.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

}
