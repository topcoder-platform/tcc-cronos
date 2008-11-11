/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.util.generator.guid.Generator;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;

import javax.servlet.ServletRequest;

/**
 * <p>
 * A class abstracting the parsing of <code>ServletRequest</code> to produce the
 * <code>FileUploadResult</code>. The information parsed in the <code>ServletRequest</code> includes
 * uploaded files and form parameters. Implementations should define the strategy to store the uploaded
 * files to persistence (e.g. in memory or some kind of persistent storage).
 * </p>
 *
 * <p>
 * The uploaded file can be retrieved later (e.g. after restarting the application) using a file id. The
 * file id should be unique to every uploaded file. Implementations should also define how to assign this
 * file id if applicable.
 * </p>
 *
 * <p>
 * This class should be initialized from a configuration namespace which should be preloaded, the same
 * properties are also supported through initialization through the <code>ConfigurationObject</code> input
 * in the second constructor. It will look for the following properties:
 * <ul>
 * <li> single_file_limit (optional) - the single file size limit for each uploaded file in the request, in
 * bytes. A value of -1 indicates no limit. Default value is -1. </li>
 * <li> total_file_limit (optional) - the total file size limit for all uploaded files in the request, in
 * bytes. A value of -1 indicates no limit. Default value is -1. </li>
 * </ul>
 *
 * The class can be configured through ConfigManager, Configuration API or programmatically through setters.
 * </p>
 *
 * <p>
 * <b>Change log:</b><br>
 * <ul>
 * <li><b>Version 2.1</b> added ConfigurationObject constructor and added setters for class members. </li>
 * <li><b>Version 2.2</b> The class provides the ability to filter some files in the ServletRequest based
 * on the pluggable file validation logic. This allows, for example, to load only files with certain
 * extension or MIME-type.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Usage example:
 * <pre>
 * Creating FileUpload instances
 *
 * // create instance for file upload to memory
 * FileUpload upload = new MemoryFileUpload(MEMORY_FILE_UPLOAD_NAMESPACE);
 *
 * // create instance for file upload to a directory in local file system,
 * // the directory can be specified dynamically
 * upload = new LocalFileUpload(LOCAL_FILE_UPLOAD_NAMESPACE, TEMP_DIR);
 *
 * // create instance for file upload to remote file system
 * upload = new RemoteFileUpload(REMOTE_FILE_UPLOAD_NAMESPACE);
 *
 * // We also create the instances programmatically
 * // ---------------------------------------------
 * // create instance for file upload to a directory in local file system,
 * // the directory can be specified dynamically
 * ConfigurationObject configObject = new
 * DefaultConfigurationObject("LocalFileUpload_configuration");
 *
 * configObject.setPropertyValues("allowed_dirs", new String[] {"test_files/temp", "test_files/files"});
 *
 * configObject.setPropertyValue("default_dir", "test_files/temp");
 * configObject.setPropertyValue("overwrite", "true");
 * configObject.setPropertyValue("single_file_limit", "200000");
 * configObject.setPropertyValue("total_file_limit", "20000000");
 *
 * // set configuration child object for MimeTypeAndFileExtensionValidator
 * ConfigurationObject fileValidatorConfig = new DefaultConfigurationObject("file_validator_config");
 * fileValidatorConfig.setPropertyValue("validate_file_type", "true");
 * fileValidatorConfig.setPropertyValue("validate_file_extension", "true");
 * fileValidatorConfig.setPropertyValues("allowed_file_types", new String[] {"type1", "type2"});
 * fileValidatorConfig.setPropertyValues("allowed_file_extensions", new String[] {"txt", "gif"});
 *
 * LocalFileUpload local = new LocalFileUpload(configObject);
 *
 * // create instance for file upload to remote file system
 * configObject = new DefaultConfigurationObject("RemoteFileUpload_configuration");
 *
 * configObject.setPropertyValues("allowed_dirs", new String[] {"test_files/temp", "test_files/files"});
 * configObject.setPropertyValue("overwrite", "true");
 * configObject.setPropertyValue("single_file_limit", "200000");
 * configObject.setPropertyValue("total_file_limit", "20000000");
 * configObject.setPropertyValue("temp_dir", "test_files/temp");
 * configObject.setPropertyValue("ip_address", "66.37.210.86");
 * configObject.setPropertyValue("port", "80");
 * configObject.setPropertyValue("message_namespace", "com.topcoder.processor.ipserver.message");
 * configObject.setPropertyValue("handler_id", "loader");
 *
 * RemoteFileUpload remote = new RemoteFileUpload(configObject);
 * </pre>
 * <pre>
 * Receiving transferred files as an InputStream
 *
 * UnitTestHelper.addConfig("StreamFileUploadConfig.xml");
 * // create instance for file upload in a streaming mode
 * StreamFileUpload upload = new StreamFileUpload("Valid");
 *
 * // parse the request using HTTP-based request parser
 * upload.parseRequest(request);

 * // process all the files in the request
 * while(upload.hasNextFile()) {
 *     String formFileName = upload.getFormFileName();
 *     String remoteFileName = upload.getRemoteFileName();
 *     String contentType = upload.getContentType();
 *
 *     InputStream in = upload.getUploadedFileInputStream();
 *
 * // process the file and form parameters...
 * }
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is thread-safe by synchronizing access to its members. Its
 * implementations should also be thread-safe (to be used in the servlet environment).
 * </p>
 *
 * @author colau, PE
 * @author AleaActaEst, TCSDEVELOPER
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 * @since 2.0
 */
public abstract class FileUpload {
    /**
     * <p>
     * The property key specifying the single file size limit for each uploaded file in the request. The
     * property value should be a long integer. This property is optional.
     * </p>
     */
    public static final String SINGLE_FILE_LIMIT_PROPERTY = "single_file_limit";

    /**
     * <p>
     * The property key specifying the total file size limit for all uploaded files in the request. The
     * property value should be a long integer. This property is optional.
     * </p>
     */
    public static final String TOTAL_FILE_LIMIT_PROPERTY = "total_file_limit";

    /**
     * <p>
     * The property key specifying the nested configuration object (either namespace for
     * ConfigurationManager or a child ConfigurationObject for Configuration API) for configuring
     * MimeTypeAndFileExtensionValidator. This property is optional. Can not be null or empty string. Set
     * once during construction and never changed later.
     * </p>
     *
     * @since 2.2
     */
    public static final String FILE_VALIDATOR_CONFIG_PROPERTY = "file_validator_config";

    /**
     * <p>
     * The GUID Generator to generate unique ids (for filenames).
     * </p>
     */
    private static final Generator GENERATOR = UUIDUtility.getGenerator(UUIDType.TYPEINT32);

    /**
     * <p>
     * Represents the single file size limit for each uploaded file in the request, in bytes. The value
     * range should be -1 or greater. A value of -1 indicates no limit. It is initialized in the constructor
     * but can be changed subsequently through a setter.
     * </p>
     * <p>
     * <b>Version 2.1 Changes</b> We have added the ability to initialize this variables through an
     * additional constructor (same constraints), as have also provided the ability to set the value through
     * a dedicated setter at any time in the object's life. This means that we have changed the immutable
     * aspect of this variable and it is NO LONGER immutable.
     * </p>
     */
    private long singleFileLimit;

    /**
     * <p>
     * Represents the total file size limit for all uploaded files in the request, in bytes. The value range
     * should be -1 or greater. A value of -1 indicates no limit. It is initialized in the constructor but
     * can be changed subsequently through a setter.
     * </p>
     * <p>
     * <b>Version 2.1 Changes</b> We have added the ability to initialize this variables through an
     * additional constructor (same constraints), as have also provided the ability to set the value through
     * a dedicated setter at any time in the object's life. This means that we have changed the immutable
     * aspect of this variable and it is NO LONGER immutable.
     * </p>
     */
    private long totalFileLimit;

    /**
     * <p>
     * An instance of <code>FileValidator</code> interface that is used to validate the files contained in
     * <code>ServletRequest</code>. If the file is considered invalid it could be skipped or exception
     * could be raised (depends on the value of skipInvalidFiles field). If null then no validation is
     * performed. Initially null. There is setter and getter for this field. The field is mutable and
     * therefore access to this field is synchronized.
     * </p>
     *
     * @since 2.2
     */
    private FileValidator fileValidator = null;

    /**
     * <p>
     * Specifies what should be done when some file in <code>ServletRequest</code> is considered invalid:
     * <ol>
     * <li>If this field is true then invalid files will simply be skipped and not included to the
     * FileUploadResult. </li>
     * <li>If this field is false then exception will be raised.</li>
     * </ol>
     * </p>
     * <p>
     * Initially set to false. There is setter and getter for this field. The field is mutable and therefore
     * access to this field is synchronized.
     * </p>
     *
     * @since 2.2
     */
    private boolean skipInvalidFiles = false;

    /**
     * <p>
     * Creates a new instance of <code>FileUpload</code> class to load configuration from the specified
     * namespace. The namespace should be preloaded.
     * </p>
     * <p>
     * This constructor will look for the following properties:
     * <ul>
     * <li> single_file_limit (optional) - the single file size limit for each uploaded file in the request,
     * in bytes. A value of -1 indicates no limit. Default value is -1. Any value under -1 will be regarded
     * as invalid. </li>
     * <li> total_file_limit (optional) - the total file size limit for all uploaded files in the request,
     * in bytes. A value of -1 indicates no limit. Default value is -1. Any value under -1 will be regarded
     * as invalid. </li>
     * </ul>
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> MimeTypeAndFileExtensionValidator's configuration is now also loaded
     * from the ConfigManager.
     * </p>
     *
     * @param namespace
     *            the configuration namespace to use. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if namespace is null or empty string.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     */
    protected FileUpload(String namespace) throws ConfigurationException {
        FileUploadHelper.validateString(namespace, "namespace");

        // get the property values
        this.singleFileLimit = FileUploadHelper.getLongPropertyValue(namespace, SINGLE_FILE_LIMIT_PROPERTY);
        this.totalFileLimit = FileUploadHelper.getLongPropertyValue(namespace, TOTAL_FILE_LIMIT_PROPERTY);

        // validate the property values
        if (this.singleFileLimit < -1) {
            throw new ConfigurationException("The singleFileLimit should not be below -1.");
        }

        if (this.totalFileLimit < -1) {
            throw new ConfigurationException("The totalFileLimit should not be below -1.");
        }

        // Use the Configuration Manager to read the FILE_VALIDATOR_CONFIG_PROPERTY property.
        String config = FileUploadHelper.getStringPropertyValue(namespace, FILE_VALIDATOR_CONFIG_PROPERTY, false);
        if (config != null) {
            // Create MimeTypeAndFileExtensionValidator instance passing the property value to its
            // constructor and assign it to fileValidator field.
            fileValidator = new MimeTypeAndFileExtensionValidator(config);
        }
    }

    /**
     * <p>
     * Constructor that loads configuration from the specified {@link ConfigurationObject}.
     * </p>
     * <p>
     * This constructor will look for the following properties:
     * <ul>
     * <li> single_file_limit (optional) - the single file size limit for each uploaded file in the request,
     * in bytes. A value of -1 indicates no limit. Default value is -1. Any value under -1 will be regarded
     * as invalid. </li>
     * <li> total_file_limit (optional) - the total file size limit for all uploaded files in the request,
     * in bytes. A value of -1 indicates no limit. Default value is -1. Any value under -1 will be regarded
     * as invalid. </li>
     * </ul>
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> MimeTypeAndFileExtensionValidator's configuration is now also loaded
     * from the Configuration API.
     * </p>
     *
     * @param configObject
     *            configuration object with initialized properties
     * @throws ConfigurationException
     *             if any configuration error occurs
     * @throws IllegalArgumentException
     *             if input is <code>null</code>.
     * @since 2.1
     */
    protected FileUpload(ConfigurationObject configObject) throws ConfigurationException {
        FileUploadHelper.validateNotNull(configObject, "Configuration object");

        // get the property values
        this.singleFileLimit = FileUploadHelper.getLongPropertyValue(configObject, SINGLE_FILE_LIMIT_PROPERTY);
        this.totalFileLimit = FileUploadHelper.getLongPropertyValue(configObject, TOTAL_FILE_LIMIT_PROPERTY);

        // validate the property values
        if (this.singleFileLimit < -1) {
            throw new ConfigurationException("The singleFileLimit should not be below -1.");
        }

        if (this.totalFileLimit < -1) {
            throw new ConfigurationException("The totalFileLimit should not be below -1.");
        }

        // get the FILE_VALIDATOR_CONFIG_PROPERTY child.
        try {
            ConfigurationObject child = configObject.getChild(FILE_VALIDATOR_CONFIG_PROPERTY);
            if (child != null) {
                // Get the child ConfigurationObject under the FILE_VALIDATOR_CONFIG_PROPERTY name from the
                // configObject. Create MimeTypeAndFileExtensionValidator instance passing this
                // ConfigurationObject to its constructor and assign it to fileValidator field.
                fileValidator = new MimeTypeAndFileExtensionValidator(child);
            }
        } catch (ConfigurationAccessException e) {
            throw new ConfigurationException(
                    "ConfigurationAccessException occurs whild getting the child ConfigurationObject.");
        }
    }

    /**
     * <p>
     * Gets the unique file name with the specified remote file name. Subclasses can use it to identify each
     * uploaded file if necessary. Using this method subclasses can support uploads with the same remote
     * file name in a single request or different requests.
     * </p>
     *
     * @param remoteFileName
     *            the remote file name.
     * @return the unique file name.
     * @throws IllegalArgumentException
     *             if remoteFileName is null or empty string.
     */
    protected static String getUniqueFileName(String remoteFileName) {
        FileUploadHelper.validateString(remoteFileName, "remoteFileName");

        return GENERATOR.getNextUUID().toString() + "_" + remoteFileName;
    }

    /**
     * <p>
     * Gets back the original remote file name from the unique file name. The unique file name should be the
     * one returned from getUniqueFileName() method.
     * </p>
     *
     * @param uniqueFileName
     *            the unique file name.
     * @return the remote file name.
     * @throws IllegalArgumentException
     *             if uniqueFileName is null or empty string, or does not contain "_" in the string.
     */
    protected static String getRemoteFileName(String uniqueFileName) {
        FileUploadHelper.validateString(uniqueFileName, "uniqueFileName");

        int index = uniqueFileName.indexOf("_");

        if (index < 0) {
            throw new IllegalArgumentException("The uniqueFileName: " + uniqueFileName
                    + " does not contain \"_\" in the string.");
        }

        return uniqueFileName.substring(index + 1);
    }

    /**
     * <p>
     * Gets the single file size limit for each uploaded file in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @return the single file size limit.
     */
    public long getSingleFileLimit() {
        synchronized (this) {
            return this.singleFileLimit;
        }
    }

    /**
     * <p>
     * Sets the single file size limit for each uploaded file in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @param singleFileLimit
     *            the new single file size limit
     * @throws IllegalArgumentException
     *             if singleFileLimit &lt; -1
     * @since 2.1
     */
    public void setSingleFileLimit(long singleFileLimit) {
        if (singleFileLimit < -1) {
            throw new IllegalArgumentException("Single file limit must not be smaller than -1");
        }

        synchronized (this) {
            this.singleFileLimit = singleFileLimit;
        }
    }

    /**
     * <p>
     * Returns the total file size limit for all uploaded files in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @return the total file size limit
     */
    public long getTotalFileLimit() {
        synchronized (this) {
            return this.totalFileLimit;
        }
    }

    /**
     * <p>
     * Sets the total file size limit for each uploaded file in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @param totalFileLimit
     *            the new single file size limit
     * @throws IllegalArgumentException
     *             if final &lt; -1
     * @since 2.1
     */
    public void setTotalFileLimit(long totalFileLimit) {
        if (totalFileLimit < -1) {
            throw new IllegalArgumentException("Total file limit must not be smaller than -1");
        }

        synchronized (this) {
            this.totalFileLimit = totalFileLimit;
        }
    }

    /**
     * <p>
     * Getter for fileValidator field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return An instance of FileValidator that is used to validate the files contained in ServletRequest.
     *         Can be null.
     * @since 2.2
     */
    public synchronized FileValidator getFileValidator() {
        return fileValidator;
    }

    /**
     * <p>
     * Setter for fileValidator field. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileValidator
     *            An instance of FileValidator that is used to validate the files contained in
     *            ServletRequest. Can be null.
     * @since 2.2
     */
    public synchronized void setFileValidator(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    /**
     * <p>
     * Getter for skipInvalidFiles field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return True if invalid files should be skipped or false if the exception should be raised.
     * @since 2.2
     */
    public synchronized boolean getSkipInvalidFiles() {
        return skipInvalidFiles;
    }

    /**
     * <p>
     * Setter for skipInvalidFiles field. Make sure to synchronize access to this method.
     * </p>
     *
     * @param skipInvalidFiles
     *            True if invalid files should be skipped or false if the exception should be raised.
     * @since 2.2
     */
    public synchronized void setSkipInvalidFiles(boolean skipInvalidFiles) {
        this.skipInvalidFiles = skipInvalidFiles;
    }

    /**
     * <p>
     * Parses the uploaded files and parameters from the given request, using the HTTP 1.1 standard. This is
     * equivalent to calling <code>uploadFiles(request, new HttpRequestParser())</code>. It will use the
     * <code>HttpRequestParser</code> to do the paring.
     * </p>
     * <p>
     * Note that a new parser is created each time this method is called to ensure thread-safety (since the
     * parser is not thread-safe itself).
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> For each new file whose parameters are read the validation is performed.
     * If the file is invalid it is either skipped or exception is thrown (depends on the skipInvalidFiles
     * field). However if the file is skipped its content should still be read in order to get to the next
     * file. The read content of the skipped file is not stored anywhere. The file validation is performed
     * only if fileValidator field is not null. Skipped files size are not checked against the
     * singleFileLimit constraint but their size is included to the total request size.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed. Should not be null.
     * @return the <code>FileUploadResult</code> containing uploaded files and parameters information. Can
     *         not be null.
     * @throws IllegalArgumentException
     *             if request is null.
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints, e.g.
     *             file size limit is exceeded.
     * @throws PersistenceException
     *             if it fails to upload the files to the persistence.
     * @throws InvalidFileException
     *             if any of the files in the ServletRequest is considered invalid (and if the
     *             skipInvalidFiles field is set to false).
     */
    public FileUploadResult uploadFiles(ServletRequest request) throws RequestParsingException, PersistenceException,
            InvalidFileException {
        return this.uploadFiles(request, new HttpRequestParser());
    }

    /**
     * <p>
     * Parses the uploaded files and parameters from the given request and parser. Depending on the subclass
     * implementation, the uploaded files should have been properly stored in the persistence after calling
     * this method.
     * </p>
     * <p>
     * The method synchronizes on the parser (via synchronization block).
     * </p>
     * <p>
     * Note that the parser should not be used by other threads when this method is called or they should
     * synchronize on it too.
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> For each new file whose parameters are read the validation is performed.
     * If the file is invalid it is either skipped or exception is thrown (depends on the skipInvalidFiles
     * field). However if the file is skipped its content should still be read in order to get to the next
     * file. The read content of the skipped file is not stored anywhere. The file validation is performed
     * only if fileValidator field is not null. Skipped files size are not checked against the
     * singleFileLimit constraint but their size is included to the total request size.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed. Should not be null.
     * @param parser
     *            the parser to use. Should not be null.
     * @return the <code>FileUploadResult</code> containing uploaded files and parameters information. Can
     *         not be null.
     * @throws IllegalArgumentException
     *             if request or parser is null.
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints, e.g.
     *             file size limit is exceeded.
     * @throws PersistenceException
     *             if it fails to upload the files to the persistence.
     * @throws InvalidFileException
     *             if any of the files in the ServletRequest is considered invalid (and if the
     *             skipInvalidFiles field is set to false).
     */
    public abstract FileUploadResult uploadFiles(ServletRequest request, RequestParser parser)
            throws RequestParsingException, PersistenceException, InvalidFileException;

    /**
     * <p>
     * Retrieves the uploaded file from the persistence with the specified file id. This method should use a
     * local copy of the file if available.
     * </p>
     * <p>
     * Note: It is assumed that the uploaded file will not be modified externally in the persistence. Users
     * can safely assume that the data in the returned instance is valid and accurate (compared to the
     * persistence), except when the file is removed.
     * </p>
     *
     * @param fileId
     *            the id of the file to retrieve.
     * @return the uploaded file.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws PersistenceException
     *             if any error occurs in retrieving the file from persistence.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public UploadedFile getUploadedFile(String fileId) throws PersistenceException, FileDoesNotExistException {
        return getUploadedFile(fileId, false);
    }

    /**
     * <p>
     * Retrieves the uploaded file from the persistence with the specified file id. The behavior depends on
     * the value of the refresh flag.
     * </p>
     * <p>
     * If refresh is true, the method should always retrieve the whole file from the persistence, in case
     * the uploaded file is modified externally.
     * </p>
     * <p>
     * If refresh is false, the method should use a local copy of the file if available. It is assumed that
     * the uploaded file will not be modified externally in the persistence. This is for performance gain.
     * </p>
     *
     * @param fileId
     *            the id of the file to retrieve.
     * @param refresh
     *            whether to refresh the cached file copy.
     * @return the uploaded file.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws PersistenceException
     *             if any error occurs in retrieving the file from persistence.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public abstract UploadedFile getUploadedFile(String fileId, boolean refresh) throws PersistenceException,
            FileDoesNotExistException;

    /**
     * <p>
     * Removes the uploaded file from the persistence with the specified file id. Once removed the file
     * contents are lost.
     * </p>
     *
     * @param fileId
     *            the id of the file to remove.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws PersistenceException
     *             if any error occurs in removing the file from persistence.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public abstract void removeUploadedFile(String fileId) throws PersistenceException, FileDoesNotExistException;
}
