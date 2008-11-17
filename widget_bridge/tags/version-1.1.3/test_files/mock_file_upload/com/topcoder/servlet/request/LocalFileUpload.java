/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import com.topcoder.configuration.ConfigurationObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * <p>
 * This class represents the persistent storage of the uploaded files in the local file system. This class
 * works by saving the uploaded files into a default or specified directory (which should be among the
 * allowed directories). In addition, the user can specify whether overwriting of files (based on the remote
 * file name) is desirable. If overwrite is false, a unique file name is generated for each uploaded file
 * and this is used as the file id. If overwrite is true, the remote file name will simply be used as the
 * file id. This will allow uploads with the same remote file name under a request or different requests.
 * </p>
 *
 * <p>
 * <code>LocalFileUpload</code> can be used to store files whose size is moderately large. The advantage
 * is the saving of memory and persistent storage. It cannot be used in the clustered environment however.
 * </p>
 *
 * <p>
 * Note that content type information is not stored permanently in the persistence. When retrieving the
 * uploaded file with the file id, the content type will not be available.
 * </p>
 *
 * <p>
 * This class should be initialized from a configuration namespace which should be preloaded, the same
 * properties are also supported through initialization through the <code>ConfigurationObject</code> input
 * in the fourth constructor. In addition to the base class, this class supports extra properties:
 * <ul>
 * <li> allowed_dirs (required) - the allowed directories to write uploaded files under. The default or
 * specified directory should be one of the allowed directories. </li>
 * <li> default_dir (optional) - the default directory to write uploaded files under. It is required if the
 * directory is not specified in the constructor. </li>
 * <li> overwrite (optional) - whether overwriting of files is allowed by default. It is required if the
 * overwrite flag is not specified in the constructor. </li>
 * </ul>
 *
 * The class can be configured through <tt>ConfigManager</tt>, <tt>Configuration API</tt> or
 * programmatically through setters.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is thread safe since the methods that access mutable fields are
 * synchronized and the base class is also thread-safe.
 * </p>
 *
 * <p>
 * <b>Change log:</b><br>
 * <ul>
 * <li><b>Version 2.1</b> added ConfigurationObject constructor and added setters for class members. </li>
 * <li><b>Version 2.2 Changes:</b> The class provides the ability to filter some files in the
 * ServletRequest based on the pluggable file validation logic. This allows, for example, to load only files
 * with certain extension or MIME-type.</li>
 * </ul>
 * </p>
 *
 * @author colau, PE
 * @author AleaActaEst, TCSDEVELOPER
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 * @since 2.0
 */
public class LocalFileUpload extends FileUpload {
    /**
     * <p>
     * The property key specifying the allowed directories to write uploaded files under. The default or
     * specified directory should be one of the allowed directories. This property is required.
     * </p>
     */
    public static final String ALLOWED_DIRS_PROPERTY = "allowed_dirs";

    /**
     * <p>
     * The property key specifying the default directory to write uploaded files under. It is required if
     * the directory is not specified in the constructor.
     * </p>
     */
    public static final String DEFAULT_DIR_PROPERTY = "default_dir";

    /**
     * <p>
     * The property key specifying whether overwriting of files is allowed by default. It is required if the
     * overwrite flag is not specified in the constructor.
     * </p>
     */
    public static final String OVERWRITE_PROPERTY = "overwrite";

    /**
     * <p>
     * Represents the allowed directories to write uploaded files under. The default or specified directory
     * should be one of the allowed directories. It cannot be null, but can be empty. Each String should be
     * non-null and non-empty. It is initialized in the constructor but can be changed subsequently through
     * a setter.
     * </p>
     * <p>
     * <b>Version 2.1 Changes</b> We have added the ability to initialize this variables through an
     * additional constructor (same constraints), as have also provided the ability to set the value through
     * a dedicated setter at any time in the object's life. This means that we have changed the immutable
     * aspect of this variable and it is NO LONGER immutable.
     * </p>
     */
    private String[] allowedDirs;

    /**
     * <p>
     * Represents the directory to write uploaded files under. It cannot be null or empty string. It is
     * initialized in the constructor but can be changed subsequently through a setter.
     * </p>
     * <p>
     * <b>Version 2.1 Changes</b> We have added the ability to initialize this variables through an
     * additional constructor (same constraints), as have also provided the ability to set the value through
     * a dedicated setter at any time in the object's life. This means that we have changed the immutable
     * aspect of this variable and it is NO LONGER immutable.
     * </p>
     */
    private String dir;

    /**
     * <p>
     * Represents whether overwriting of files is allowed. It is initialized in the constructor but can be
     * changed subsequently through a setter.
     * </p>
     * <p>
     * <b>Version 2.1 Changes</b> We have added the ability to initialize this variables through an
     * additional constructor (same constraints), as have also provided the ability to set the value through
     * a dedicated setter at any time in the object's life. This means that we have changed the immutable
     * aspect of this variable and it is NO LONGER immutable.
     * </p>
     */
    private boolean overwrite;

    /**
     * <p>
     * Creates a new instance of <code>LocalFileUpload</code> class to load configuration from the
     * specified namespace. The namespace should be preloaded.
     * </p>
     *
     * @param namespace
     *            the configuration namespace to use.
     * @throws IllegalArgumentException
     *             if namespace is null or empty string.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     * @throws DisallowedDirectoryException
     *             if the directory is not one of the allowed directories.
     */
    public LocalFileUpload(String namespace) throws ConfigurationException, DisallowedDirectoryException {
        super(namespace);
        this.allowedDirs = FileUploadHelper.getStringArrayPropertyValue(namespace, ALLOWED_DIRS_PROPERTY);
        this.dir = FileUploadHelper.getStringPropertyValue(namespace, DEFAULT_DIR_PROPERTY, true);
        this.overwrite = FileUploadHelper.getBooleanPropertyValue(namespace, OVERWRITE_PROPERTY);

        // Check if the directory is one of the allowed directories.
        checkConfiguredDirectory(this.dir);
    }

    /**
     * <p>
     * Creates a new instance of <code>LocalFileUpload</code> class to load configuration from the
     * specified namespace. The namespace should be preloaded. The directory is given.
     * </p>
     *
     * @param namespace
     *            the configuration namespace to use.
     * @param dir
     *            the directory to write uploaded files under.
     * @throws IllegalArgumentException
     *             if namespace or dir is null or empty string.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     * @throws DisallowedDirectoryException
     *             if the directory is not one of the allowed directories.
     */
    public LocalFileUpload(String namespace, String dir) throws ConfigurationException, DisallowedDirectoryException {
        super(namespace);
        FileUploadHelper.validateString(dir, "dir");

        this.allowedDirs = FileUploadHelper.getStringArrayPropertyValue(namespace, ALLOWED_DIRS_PROPERTY);
        this.dir = dir;
        this.overwrite = FileUploadHelper.getBooleanPropertyValue(namespace, OVERWRITE_PROPERTY);

        // Check if the directory is one of the allowed directories.
        checkConfiguredDirectory(this.dir);
    }

    /**
     * <p>
     * Creates a new instance of <code>LocalFileUpload</code> class to load configuration from the
     * specified namespace. The namespace should be preloaded. The directory and overwrite flag are given.
     * </p>
     *
     * @param namespace
     *            the configuration namespace to use.
     * @param dir
     *            the directory to write uploaded files under.
     * @param overwrite
     *            whether overwriting of files is allowed.
     * @throws IllegalArgumentException
     *             if namespace or dir is null or empty string.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     * @throws DisallowedDirectoryException
     *             if the directory is not one of the allowed directories.
     */
    public LocalFileUpload(String namespace, String dir, boolean overwrite) throws ConfigurationException,
            DisallowedDirectoryException {
        super(namespace);
        FileUploadHelper.validateString(dir, "dir");

        this.allowedDirs = FileUploadHelper.getStringArrayPropertyValue(namespace, ALLOWED_DIRS_PROPERTY);
        this.dir = dir;
        this.overwrite = overwrite;

        // Check if the directory is one of the allowed directories and valid.
        checkConfiguredDirectory(this.dir);
    }

    /**
     * <p>
     * Creates a new instance of <code>LocalFileUpload</code> class to load configuration from the
     * specified {@link ConfigurationObject}.
     * </p>
     *
     * @param configObject
     *            the {@link ConfigurationObject} to use.
     * @throws IllegalArgumentException
     *             if configObject is <code>null</code>.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     * @throws DisallowedDirectoryException
     *             if the directory is not one of the allowed directories.
     * @since 2.1
     */
    public LocalFileUpload(ConfigurationObject configObject) throws ConfigurationException,
            DisallowedDirectoryException {
        super(configObject);
        this.allowedDirs = FileUploadHelper.getStringArrayPropertyValue(configObject, ALLOWED_DIRS_PROPERTY);
        this.dir = FileUploadHelper.getStringPropertyValue(configObject, DEFAULT_DIR_PROPERTY, true);
        this.overwrite = FileUploadHelper.getBooleanPropertyValue(configObject, OVERWRITE_PROPERTY);

        // Check if the directory is one of the allowed directories.
        checkConfiguredDirectory(this.dir);
    }

    /**
     * <p>
     * Check if the directory is one of the allowed directories.
     * </p>
     *
     * @param directory
     *            the directory to be checked
     * @throws DisallowedDirectoryException
     *             if the directory is not one of the allowed directories.
     * @throws ConfigurationException
     *             if the given path is not a directory
     * @since 2.1
     */
    private void checkConfiguredDirectory(String directory) throws DisallowedDirectoryException,
            ConfigurationException {
        if (!new File(directory).isDirectory()) {
            throw new ConfigurationException(directory + " is not a directory");
        }

        if (!this.checkDirectoryIsAllowed(directory, this.allowedDirs)) {
            throw new DisallowedDirectoryException(directory + " is not allowed");
        }
    }

    /**
     * <p>
     * Check if the directory is one of the allowed directories.
     * </p>
     *
     * @param directory
     *            the directory to be checked
     * @param allowed
     *            the list of allowed directories
     * @return <code>true</code> if the given path is a allowed directory, <code>false</code> otherwise.
     * @since 2.1
     */
    private boolean checkDirectoryIsAllowed(String directory, String[] allowed) {
        File df = new File(directory);

        for (int i = 0; (i < allowed.length); i++) {
            File curAllowed = new File(allowed[i]);
            String curAllowedPath = new File(allowed[i]).getAbsolutePath();

            if (curAllowed.isDirectory()) {
                if (!curAllowedPath.endsWith(File.separator)) {
                    curAllowedPath += File.separator;
                }

                // sub directory can also be regarded as allowed
                if (curAllowed.equals(df) || df.getAbsolutePath().startsWith(curAllowedPath)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * <p>
     * Gets the allowed directories to write uploaded files under.
     * </p>
     *
     * @return the allowed directories to write uploaded files under.
     */
    public String[] getAllowedDirs() {
        synchronized (this) {
            return (String[]) this.allowedDirs.clone();
        }
    }

    /**
     * <p>
     * Sets the allowed directories to write uploaded files under.
     * </p>
     *
     * @param allowedDirs
     *            the allowed directories to write uploaded files under.
     * @throws IllegalArgumentException
     *             if the input is null, empty or contains null or empty strings
     * @throws IllegalArgumentException
     *             if the array of allowed directories does not contain the current directory
     * @since 2.1
     */
    public void setAllowedDirs(String[] allowedDirs) {
        FileUploadHelper.validateNotNull(allowedDirs, "Allowed directories array");

        if (allowedDirs.length == 0) {
            throw new IllegalArgumentException("Allowed directories array must not be empty");
        }

        for (int i = 0; i < allowedDirs.length; i++) {
            FileUploadHelper.validateString(allowedDirs[i], "Allowed directory entry");
        }

        synchronized (this) {
            if (!this.checkDirectoryIsAllowed(this.dir, allowedDirs)) {
                throw new IllegalArgumentException("Current directory is not in the new allowed directories");
            }

            this.allowedDirs = (String[]) allowedDirs.clone();
        }
    }

    /**
     * <p>
     * Gets the directory to write uploaded files under.
     * </p>
     *
     * @return the directory to write uploaded files under.
     */
    public String getDir() {
        synchronized (this) {
            return this.dir;
        }
    }

    /**
     * <p>
     * Sets the directory to write uploaded files under.
     * </p>
     *
     * @param dir
     *            the directory to write uploaded files under.
     * @throws IllegalArgumentException
     *             if the input is null, empty string or a non-existent directory path
     * @since 2.1
     */
    public void setDir(String dir) {
        FileUploadHelper.validateString(dir, "Directory");

        if (!new File(dir).isDirectory()) {
            throw new IllegalArgumentException(dir + " is not a directory");
        }

        synchronized (this) {
            if (!this.checkDirectoryIsAllowed(dir, this.allowedDirs)) {
                throw new IllegalArgumentException(dir + " is not a allowed directory");
            }

            this.dir = dir;
        }
    }

    /**
     * <p>
     * Gets whether overwriting of files is allowed (based on remote file name).
     * </p>
     *
     * @return true if upload can overwrite files, false otherwise.
     */
    public boolean canOverwrite() {
        synchronized (this) {
            return this.overwrite;
        }
    }

    /**
     * <p>
     * Sets whether overwriting of files is allowed (based on remote file name).
     * </p>
     *
     * @param overwrite
     *            true if upload can overwrite files, false otherwise.
     * @since 2.1
     */
    public void setOverwrite(boolean overwrite) {
        synchronized (this) {
            this.overwrite = overwrite;
        }
    }

    /**
     * <p>
     * Parses the uploaded files and parameters from the given request and parser. It saves each uploaded
     * file under the directory using a unique file name if overwrite is false, or remote file name if
     * overwrite is true.
     * </p>
     * <p>
     * Note that if there is any failure, the file just written will be deleted. All the previously written
     * files are kept.
     * </p>
     * <p>
     * The method synchronizes on the parser (via synchronization block). Note that the parser should not be
     * used by other threads when this method is called or they should synchronize on it too.
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
    public FileUploadResult uploadFiles(ServletRequest request, RequestParser parser) throws RequestParsingException,
            PersistenceException, InvalidFileException {
        FileUploadHelper.validateNotNull(request, "request");
        FileUploadHelper.validateNotNull(parser, "parser");

        synchronized (parser) {
            // parse the request with the parse
            parser.parseRequest(request);

            Map uploadedFiles = new HashMap();
            long currentTotalSize = 0;

            while (parser.hasNextFile()) {
                String remoteFileName = parser.getRemoteFileName();

                // get the unique file name (if overwrite is false) or simply
                // use the remote file name (if overwrite is true).
                String fileName = this.canOverwrite() ? ("overwrite_" + remoteFileName)
                        : getUniqueFileName(remoteFileName);

                // get the form file name
                String formFileName = parser.getFormFileName();

                // get the content type
                String contentType = parser.getContentType();

                if (!getSkipInvalidFiles()) {
                    FileValidator validator = getFileValidator();
                    if (validator == null) {
                        throw new InvalidFileException("The file validator should not be null.");
                    }

                    // Use remote name for filename validation.
                    FileData fileData = new FileData(remoteFileName, contentType);
                    // if any of the files in the ServletRequest is considered invalid (and if the
                    // skipInvalidFiles field is set to false), throws InvalidFileException
                    if (!validator.validateFile(fileData)) {
                        throw new InvalidFileException("The file in the ServletRequest is considered invalid "
                                + "and the skipInvalidFiles field is false.");
                    }
                }

                File uploadedFile = null;
                OutputStream output = null;

                try {
                    uploadedFile = new File(dir, fileName);

                    output = new FileOutputStream(uploadedFile);
                    parser.writeNextFile(output, Math.min(getSingleFileLimit(), getTotalFileLimit()
                            - currentTotalSize));

                    // add the new updated file
                    List files = (List) uploadedFiles.get(formFileName);

                    if (files == null) {
                        files = new ArrayList();
                        uploadedFiles.put(formFileName, files);
                    }

                    files.add(new LocalUploadedFile(uploadedFile, contentType));
                } catch (IOException e) {
                    // if there is any failure, the file just written will be
                    // deleted.
                    uploadedFile.delete();
                    throw new PersistenceException("Fails to upload the files to the persistence.", e);
                } catch (FileSizeLimitExceededException e) {
                    uploadedFile.delete();
                    throw e;
                } finally {
                    FileUploadHelper.closeOutputStream(output);
                }

                // update the current total size of the uploaded files
                currentTotalSize += uploadedFile.length();
            }

            return new FileUploadResult(parser.getParameters(), uploadedFiles);
        }
    }

    /**
     * <p>
     * Retrieves the uploaded file from the persistence with the specified file id. In this case, retrieve
     * the file from the local file system. The file id should act the name of the saved file.
     * </p>
     * <p>
     * Note that content type information is not stored permanently in the persistence. When retrieving the
     * uploaded file with the file id, the content type will not be available.
     * </p>
     *
     * @param fileId
     *            the id of the file to retrieve.
     * @param refresh
     *            whether to refresh the cached file copy, ignored here.
     * @return the uploaded file.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public UploadedFile getUploadedFile(String fileId, boolean refresh) throws FileDoesNotExistException {
        FileUploadHelper.validateString(fileId, "fileId");

        File file = new File(this.getDir(), fileId);

        if (!file.exists()) {
            throw new FileDoesNotExistException(fileId);
        }

        return new LocalUploadedFile(file, null);
    }

    /**
     * <p>
     * Removes the uploaded file from the persistence with the specified file id. Once removed the file
     * contents are lost. In this case, simply remove the file from the local file system. The file id
     * should act the name of the saved file.
     * </p>
     *
     * @param fileId
     *            the id of the file to remove.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public void removeUploadedFile(String fileId) throws FileDoesNotExistException {
        FileUploadHelper.validateString(fileId, "fileId");

        File file = new File(this.getDir(), fileId);

        if (!file.exists()) {
            throw new FileDoesNotExistException(fileId);
        }

        file.delete();
    }
}
