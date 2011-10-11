/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGenerationException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>A mock implementation of {@link UploadManager} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared with
 * package private access so only the test cases could invoke them. The overridden methods simply call
 * the corresponding method of a super-class.
 * <p>
 * The version 1.4 add some methods: createSubmissionType, getAllSubmissionTypes,
 * removeSubmissionType, updateSubmissionType, to suit new UploadManager.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>unimplemented methods added.</li>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowException.</li>
 * <li>add common codes into method checkAndThrowBuilderException.</li>
 * </ol>
 * </p>
 *
 * @author  isv, stevenfrog, TCSASSEMBLER
 * @version 1.6.2
 * @since 1.0
 */
public class MockUploadManager implements UploadManager {

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
     * <p>Constructs new <code>MockUploadManager</code> instance.</p>
     */
    public MockUploadManager() {
    }

    /**
     * <p>Constructs new <code>MockUploadManager</code> instance.</p>
     */
    public MockUploadManager(String namespace) {
    }

    /**
     * Creates a new PersistenceUploadManager.
     *
     * @param persistence The persistence for Uploads, Submissions, and related objects
     * @param uploadSearchBundle The search bundle for searching uploads
     * @param submissionSearchBundle The search bundle for searching submissions
     * @param uploadIdGenerator The generator for Upload ids
     * @param uploadTypeIdGenerator The generator for UploadType ids
     * @param uploadStatusIdGenerator The generator for UploadStatus ids
     * @param submissionIDGenerator The generator for Submission ids
     * @param submissionStatusIdGenerator The generator for SubmissionStatus ids
     * @param submissionTypeIdGenerator the generator for submission type ids
     * @throws IllegalArgumentException If any argument is null
     */
    public MockUploadManager(UploadPersistence persistence, SearchBundle uploadSearchBundle,
            SearchBundle submissionSearchBundle, IDGenerator uploadIdGenerator,
            IDGenerator uploadTypeIdGenerator, IDGenerator uploadStatusIdGenerator,
            IDGenerator submissionIDGenerator, IDGenerator submissionStatusIdGenerator,
            IDGenerator submissionTypeIdGenerator) {
    }

    /**
     * Creates a new PersistenceUploadManager.
     *
     * @param persistence The persistence for Uploads and related objects
     * @param searchBundleManager The manager containing the various SearchBundles needed
     * @throws IllegalArgumentException If any argument is null
     * @throws IllegalArgumentException If any search bundle or id generator is not available under
     *             the required names
     * @throws IDGenerationException If any error occur when generating IDGenerators.
     */
    public MockUploadManager(UploadPersistence persistence, SearchBundleManager searchBundleManager)
        throws IDGenerationException {
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
     * @throws SearchBuilderException If parameter exception is not <code>null</code>
     * and an instance of SearchBuilderException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of UploadPersistenceException or SearchBuilderException.
     */
    private static void checkAndThrowBuilderException(Throwable exception)
        throws UploadPersistenceException, SearchBuilderException {
        if (exception != null) {
            if (exception instanceof UploadPersistenceException) {
                throw (UploadPersistenceException) exception;
            } else if (exception instanceof SearchBuilderException) {
                throw (SearchBuilderException) exception;
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
     * @param upload0 the Upload.
     * @param string0 the String.
     *
     * @see UploadManager#createUpload(Upload, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createUpload(Upload upload0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createUpload_Upload_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param upload0 the Upload.
     * @param string0 the String.
     *
     * @see UploadManager#updateUpload(Upload, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUpload(Upload upload0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateUpload_Upload_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param upload0 the Upload.
     * @param string0 the String.
     *
     * @see UploadManager#removeUpload(Upload, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUpload(Upload upload0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeUpload_Upload_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", upload0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
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
     * @see UploadManager#getUpload(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Upload getUpload(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getUpload_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Upload) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the Upload[].
     *
     * @see UploadManager#searchUploads(Filter)
     * @throws UploadPersistenceException If any persistence error occur.
     * @throws SearchBuilderException If any Search error occur.
     */
    public Upload[] searchUploads(Filter filter0)
        throws UploadPersistenceException, SearchBuilderException {

        checkAndThrowBuilderException(MockUploadManager.globalException);

        String methodName = "searchUploads_Filter";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Upload[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadType0 the UploadType.
     * @param string0 the String.
     *
     * @see UploadManager#createUploadType(UploadType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createUploadType(UploadType uploadType0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createUploadType_UploadType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadType0 the UploadType.
     * @param string0 the String.
     *
     * @see UploadManager#updateUploadType(UploadType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUploadType(UploadType uploadType0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateUploadType_UploadType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadType0 the UploadType.
     * @param string0 the String.
     *
     * @see UploadManager#removeUploadType(UploadType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUploadType(UploadType uploadType0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeUploadType_UploadType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadType0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the UploadType[].
     *
     * @see UploadManager#getAllUploadTypes()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadType[] getAllUploadTypes() throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getAllUploadTypes";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadType[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     * @param string0 the String.
     *
     * @see UploadManager#createUploadStatus(UploadStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createUploadStatus(UploadStatus uploadStatus0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createUploadStatus_UploadStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     * @param string0 the String.
     *
     * @see UploadManager#updateUploadStatus(UploadStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateUploadStatus(UploadStatus uploadStatus0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateUploadStatus_UploadStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param uploadStatus0 the UploadStatus.
     * @param string0 the String.
     *
     * @see UploadManager#removeUploadStatus(UploadStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeUploadStatus(UploadStatus uploadStatus0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeUploadStatus_UploadStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", uploadStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the UploadStatus[].
     *
     * @see UploadManager#getAllUploadStatuses()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getAllUploadStatuses";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (UploadStatus[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     * @param string0 the String.
     *
     * @see UploadManager#createSubmission(Submission, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createSubmission(Submission submission0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createSubmission_Submission_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     * @param string0 the String.
     *
     * @see UploadManager#updateSubmission(Submission, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmission(Submission submission0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateSubmission_Submission_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submission0 the Submission.
     * @param string0 the String.
     *
     * @see UploadManager#removeSubmission(Submission, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmission(Submission submission0, String string0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeSubmission_Submission_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submission0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
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
     * @see UploadManager#getSubmission(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission getSubmission(long long0) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getSubmission_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(long0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param filter0 the Filter.
     *
     * @return the Submission[].
     *
     * @see UploadManager#searchSubmissions(Filter)
     * @throws UploadPersistenceException If any persistence error occur.
     * @throws SearchBuilderException If any Search error occur.
     */
    public Submission[] searchSubmissions(Filter filter0)
        throws UploadPersistenceException, SearchBuilderException {

        checkAndThrowBuilderException(MockUploadManager.globalException);

        String methodName = "searchSubmissions_Filter";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowBuilderException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", filter0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     * @param string0 the String.
     *
     * @see UploadManager#createSubmissionStatus(SubmissionStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createSubmissionStatus(SubmissionStatus submissionStatus0, String string0)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createSubmissionStatus_SubmissionStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     * @param string0 the String.
     *
     * @see UploadManager#updateSubmissionStatus(SubmissionStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus0, String string0)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateSubmissionStatus_SubmissionStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionStatus0 the SubmissionStatus.
     * @param string0 the String.
     *
     * @see UploadManager#removeSubmissionStatus(SubmissionStatus, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus0, String string0)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeSubmissionStatus_SubmissionStatus_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionStatus0);
        arguments.put("2", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the SubmissionStatus[].
     *
     * @see UploadManager#getAllSubmissionStatuses()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionStatus[] getAllSubmissionStatuses() throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getAllSubmissionStatuses";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionStatus[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockUploadManager.methodResults.put(methodSignature, result);
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
        Map arguments = (Map) MockUploadManager.methodArguments.get(methodSignature);
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
        return (List) MockUploadManager.methodArguments.get(methodSignature);
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
            MockUploadManager.throwExceptions.put(methodSignature, exception);
        } else {
            MockUploadManager.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockUploadManager.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockUploadManager</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockUploadManager.methodArguments.clear();
        MockUploadManager.methodResults.clear();
        MockUploadManager.throwExceptions.clear();
        MockUploadManager.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockUploadManager</code> class.</p>
     */
    public static void init() {
        setMethodResult("searchSubmissions_Filter", new Submission[0]);
    }

// ---------------------------------------------------------- additional methods 1.6.2

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionType the SubmissionType.
     * @param operator the String.
     *
     * @see UploadManager#createSubmissionType(SubmissionType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createSubmissionType(SubmissionType submissionType, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createSubmissionType_SubmissionType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionType);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionType the SubmissionType.
     * @param operator the String.
     *
     * @see UploadManager#updateSubmissionType(SubmissionType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionType(SubmissionType submissionType, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateSubmissionType_SubmissionType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionType);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionType the SubmissionType.
     * @param operator the String.
     *
     * @see UploadManager#removeSubmissionType(SubmissionType, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionType(SubmissionType submissionType, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeSubmissionType_SubmissionType_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionType);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the SubmissionType[].
     *
     * @see UploadManager#getAllSubmissionTypes()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionType[] getAllSubmissionTypes() throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getAllSubmissionTypes";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionType[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionImage the SubmissionImage.
     * @param operator the String.
     *
     * @see UploadManager#createSubmissionImage(SubmissionImage, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void createSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "createSubmissionImage_SubmissionImage_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionImage);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionImage the SubmissionImage.
     * @param operator the String.
     *
     * @see UploadManager#updateSubmissionImage(SubmissionImage, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void updateSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "updateSubmissionImage_SubmissionImage_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionImage);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionImage the SubmissionImage.
     * @param operator the String.
     *
     * @see UploadManager#removeSubmissionImage(SubmissionImage, String)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public void removeSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "removeSubmissionImage_SubmissionImage_String";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", submissionImage);
        arguments.put("2", operator);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param id the long.
     *
     * @return the MimeType.
     *
     * @see UploadManager#getMimeType(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public MimeType getMimeType(long id) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getMimeType_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(id));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (MimeType) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the MimeType[].
     *
     * @see UploadManager#getAllMimeTypes()
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public MimeType[] getAllMimeTypes() throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getAllMimeTypes";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (MimeType[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param projectId the long.
     * @param ownerId the long.
     *
     * @return the Submission[].
     *
     * @see UploadManager#getUserSubmissionsForProject(long, long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] getUserSubmissionsForProject(long projectId, long ownerId) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getUserSubmissionsForProject_long_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(projectId));
        arguments.put("2", new Long(ownerId));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param projectId the long.
     *
     * @return the Submission[].
     *
     * @see UploadManager#getProjectSubmissions(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public Submission[] getProjectSubmissions(long projectId) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getProjectSubmissions_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(projectId));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Submission[]) MockUploadManager.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param submissionId the long.
     *
     * @return the SubmissionImage[].
     *
     * @see UploadManager#getImagesForSubmission(long)
     * @throws UploadPersistenceException If any persistence error occur.
     */
    public SubmissionImage[] getImagesForSubmission(long submissionId) throws UploadPersistenceException {
        checkAndThrowException(MockUploadManager.globalException);

        String methodName = "getImagesForSubmission_long";

        Throwable exception = (Throwable) MockUploadManager.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Long(submissionId));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockUploadManager.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockUploadManager.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SubmissionImage[]) MockUploadManager.methodResults.get(methodName);

    }

}
