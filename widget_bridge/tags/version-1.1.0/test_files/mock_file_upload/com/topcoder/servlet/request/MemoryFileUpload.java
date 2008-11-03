/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import com.topcoder.configuration.ConfigurationObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * <p>
 * This class represents the memory storage of the uploaded files. This class works by saving the uploaded
 * files into memory (using a byte array). Overwriting of files is not allowed.
 * </p>
 * <p>
 * <code>MemoryFileUpload</code> can be used for fast retrieval of data (without I/O) when the file size
 * is small. It cannot be used in a clustered environment however. It is strongly recommended to limit the
 * file size by specifying the single_file_limit property in the configuration.
 * </p>
 * <p>
 * This class should be initialized from a configuration namespace which should be preloaded. This class
 * supports no extra property in addition to the base class.
 * </p>
 * <p>
 * The class can be configured through ConfigManager, Configuration API or programmatically through setters.
 * </p>
 *
 * <p>
 * <b>Change log:</b><br>
 * <ul>
 * <li><b>Version 2.1</b> added ConfigurationObject constructor. </li>
 * <li><b>Version 2.2 Changes:</b> The class provides the ability to filter some files in the
 * ServletRequest based on the pluggable file validation logic. This allows, for example, to load only files
 * with certain extension or MIME-type. The class also was extended to provide the ability to manage the
 * uploaded files. It is now possible to retrieve and remove the uploaded files.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is mutable and uses synchronization to ensure thread-safety.
 * </p>
 *
 * @author colau, PE
 * @author AleaActaEst, TCSDEVELOPER
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 * @since 2.0
 */
public class MemoryFileUpload extends FileUpload {
    /**
     * <p>
     * The Map instance that maps file id (String type) to uploaded files (UploadedFile type). New files are
     * added every time uploadFiles() method is called. The files can be retrieved in
     * {@link #getUploadFile()} method or removed in {@link #removeUploadedFile()} method. The map can not
     * be null. Can be empty. Once set during the construction will never change. Keys can not be null or
     * empty strings. Values can not be null. Access to this field is synchronized.
     * </p>
     *
     * @since 2.2
     */
    private final Map uploadedFiles = new HashMap();

    /**
     * <p>
     * Creates a new instance of <code>MemoryFileUpload</code> class to load configuration from the
     * specified namespace. The namespace should be preloaded.
     * </p>
     *
     * @param namespace
     *            the configuration namespace to use.
     * @throws IllegalArgumentException
     *             if namespace is null or empty string.
     * @throws ConfigurationException
     *             if any configuration error occurs.
     */
    public MemoryFileUpload(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * Creates a new instance of <code>MemoryFileUpload</code> class to load configuration from the
     * specified configObject.
     * </p>
     *
     * @param configObject
     *            the {@link ConfigurationObject} to get configuration from
     * @throws IllegalArgumentException
     *             if configObject is <code>null</code>
     * @throws ConfigurationException
     *             if any configuration error occurs.
     * @since 2.1
     */
    public MemoryFileUpload(ConfigurationObject configObject) throws ConfigurationException {
        super(configObject);
    }

    /**
     * <p>
     * Parses the uploaded files and parameters from the given request and parser. It saves each uploaded
     * file into a byte array.
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
     * singleFileLimit constraint but their size is included to the total request size. In this version of
     * the component we also add the uploaded files to the uploadedFiles map. Make sure to synchronize
     * access to this method.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed. Should not be null.
     * @param parser
     *            the parser to use. Should not be null.
     * @return the <code>FileUploadResult</code> containing uploaded files and parameters information. Can
     *         not be null.
     *
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
                // get the remote file name
                String fileName = parser.getRemoteFileName();
                // get the form file name
                String formFileName = parser.getFormFileName();
                // get the content type
                String contentType = parser.getContentType();

                ByteArrayOutputStream output = null;

                try {
                    output = new ByteArrayOutputStream();
                    parser.writeNextFile(output, Math.min(getSingleFileLimit(), getTotalFileLimit()
                            - currentTotalSize));

                    // add the new updated file
                    byte[] content = output.toByteArray();
                    List files = (List) uploadedFiles.get(formFileName);

                    if (files == null) {
                        files = new ArrayList();
                        uploadedFiles.put(formFileName, files);
                    }

                    files.add(new MemoryUploadedFile(content, fileName, contentType, FileUpload
                            .getUniqueFileName(fileName)));

                    // update the current total size of the uploaded files
                    currentTotalSize += content.length;
                } catch (IOException e) {
                    throw new PersistenceException("Fails to upload the files to the persistence.", e);
                } finally {
                    FileUploadHelper.closeOutputStream(output);
                }
            }

            FileUploadResult fileUploadResult = new FileUploadResult(parser.getParameters(), uploadedFiles);

            // for each file in the FileUploadResult add it to the uploadedFilesMap with the
            // UploadedFile#getFileId() as the key.
            UploadedFile[] files = fileUploadResult.getAllUploadedFiles();
            for (int i = 0; i < files.length; i++) {
                this.uploadedFiles.put(files[i].getFileId(), files[i]);
            }

            return fileUploadResult;
        }
    }

    /**
     * <p>
     * Retrieves the uploaded file from the persistence with the specified file id. The behavior does not
     * depend on the value of the refresh flag since the map is always up-to-date (since the map is the
     * persistence storage itself).
     * </p>
     * <p>
     * Retrieves the uploaded file from the uploadedFiles map with the specified file id.
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> The method does not throw UnsupportedOperationException anymore, but
     * retrieves the file from the internal map. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileId
     *            the id of the file to retrieve. Should not be null or empty string.
     * @param refresh
     *            whether to refresh the cached file copy.
     * @return the uploaded file. Can not be null.
     *
     * @throws IllegalArgumentException
     *             if fileId is null or empty string
     * @throws PersistenceException
     *             if any error occurs in retrieving the file from persistence. Actually never thrown in
     *             this implementation.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public synchronized UploadedFile getUploadedFile(String fileId, boolean refresh) throws PersistenceException,
            FileDoesNotExistException {
        FileUploadHelper.validateString(fileId, "fileId");

        if (!uploadedFiles.containsKey(fileId)) {
            throw new FileDoesNotExistException("The file doesn't exist.");
        }

        return (UploadedFile) uploadedFiles.get(fileId);
    }

    /**
     * <p>
     * Removes the uploaded file from the persistence with the specified file id. Once removed the file
     * contents are lost.
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> The method does not throw UnsupportedOperationException anymore, but
     * removes the file from the internal map. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileId
     *            the id of the file to remove. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if fileId is null or empty string.
     * @throws PersistenceException
     *             if any error occurs in removing the file from persistence. Actually never thrown in this
     *             implementation.
     * @throws FileDoesNotExistException
     *             if the file does not exist in persistence.
     */
    public synchronized void removeUploadedFile(String fileId) throws PersistenceException, FileDoesNotExistException {
        FileUploadHelper.validateString(fileId, "fileId");

        if (!uploadedFiles.containsKey(fileId)) {
            throw new FileDoesNotExistException("The file doesn't exist.");
        }

        uploadedFiles.remove(fileId);
    }
}
