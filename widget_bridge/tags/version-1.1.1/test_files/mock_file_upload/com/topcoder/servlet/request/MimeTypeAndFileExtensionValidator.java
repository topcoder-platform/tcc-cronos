/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.util.HashSet;
import java.util.Set;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This is a simple implementation of <code>FileValidator</code> interface that validates the files based
 * on their MIME-type and the extension. The file is considered valid if its MIME-type is contained in a set
 * of allowed MIME-types and its extension is contained in a set of allowed file extensions. There is an
 * option to perform validation based on the MIME-type only, extension only or both.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable but uses locking to ensure the thread-safety, therefore it
 * is thread safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class MimeTypeAndFileExtensionValidator implements FileValidator {
    /**
     * <p>
     * The property key specifying whether the file type validation should be performed. The property value
     * should be "true" or "false". This property is optional. Can not be null or empty string. Set once
     * during construction and never changed later.
     * </p>
     */
    public static final String VALIDATE_FILE_TYPE_PROPERTY = "validate_file_type";

    /**
     * <p>
     * The property key specifying whether the file extension validation should be performed. The property
     * value should be "true" or "false". This property is optional. Can not be null or empty string. Set
     * once during construction and never changed later.
     * </p>
     */
    public static final String VALIDATE_FILE_EXTENSION_PROPERTY = "validate_file_extension";

    /**
     * <p>
     * The property key specifying allowed file types. The property values should be non-null non-empty
     * strings. This property is optional. Can not be null or empty string. Set once during construction and
     * never changed later.
     * </p>
     */
    public static final String ALLOWED_FILE_TYPES_PROPERTY = "allowed_file_types";

    /**
     * <p>
     * The property key specifying allowed file extensions. The property values should be non-null (possibly
     * empty) strings. This property is optional. Can not be null or empty string. Set once during
     * construction and never changed later.
     * </p>
     */
    public static final String ALLOWED_FILE_EXTENSIONS_PROPERTY = "allowed_file_extensions";

    /**
     * <p>
     * A set of allowed file types. Values are of String type. Initialized once during construction and
     * never changed later. Can not be null. Can be empty. Values can not be null or empty strings. There
     * are methods to add, remove, get and clear the values of this set. Access to this field is
     * synchronized.
     * </p>
     */
    private final Set allowedFileTypes = new HashSet();

    /**
     * <p>
     * A set of allowed file extensions. Values are of String type. Initialized once during construction and
     * never changed later. Can not be null. Can be empty. Values can not be null but can be empty strings.
     * There are methods to add, remove, get and clear the values of this set. Access to this field is
     * synchronized.
     * </p>
     */
    private final Set allowedFileExtensions = new HashSet();

    /**
     * <p>
     * Shows whether to validate the file type. True by-default. There is setter and getter for this field.
     * Access to this field is synchronized.
     * </p>
     */
    private boolean validateFileType = true;

    /**
     * <p>
     * Shows whether to validate the file extension. True by-default. There is setter and getter for this
     * field. Access to this field is synchronized.
     * </p>
     */
    private boolean validateFileExtension = true;

    /**
     * <p>
     * Constructor that loads configuration from the specified namespace. The namespace should be preloaded.
     * </p>
     *
     * @param namespace
     *            The configuration namespace to use. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if namespace is null or empty string
     * @throws ConfigurationException
     *             if any configuration error occurs
     */
    public MimeTypeAndFileExtensionValidator(String namespace) throws ConfigurationException {
        FileUploadHelper.validateString(namespace, "namespace");

        // get the property values
        String validate = FileUploadHelper.getStringPropertyValue(namespace, VALIDATE_FILE_TYPE_PROPERTY, false);
        if (validate != null) {
            this.validateFileType = Boolean.valueOf(validate).booleanValue();
        }

        validate = FileUploadHelper.getStringPropertyValue(namespace, VALIDATE_FILE_EXTENSION_PROPERTY, false);
        if (validate != null) {
            this.validateFileExtension = Boolean.valueOf(validate).booleanValue();
        }

        // read ALLOWED_FILE_TYPES_PROPERTY property values and add them to allowedFileTypes set
        String[] fileTypes = this.getStringArrayPropertyValue(namespace, ALLOWED_FILE_TYPES_PROPERTY, false);
        if (fileTypes != null) {
            for (int i = 0; i < fileTypes.length; i++) {
                this.allowedFileTypes.add(fileTypes[i]);
            }
        }

        // read ALLOWED_FILE_EXTENSIONS_PROPERTY property values and add them to allowedFileExtensions set
        String[] fileExtensions = this.getStringArrayPropertyValue(namespace, ALLOWED_FILE_EXTENSIONS_PROPERTY, true);
        if (fileExtensions != null) {
            for (int i = 0; i < fileExtensions.length; i++) {
                this.allowedFileExtensions.add(fileExtensions[i]);
            }
        }
    }

    /**
     * <p>
     * Constructor that loads configuration from the specified <code>ConfigurationObject</code>.
     * </p>
     *
     * @param configObject
     *            Configuration object with initialized properties. Should not be null.
     * @throws IllegalArgumentException
     *             if configObject is null.
     * @throws ConfigurationException
     *             if any configuration error occurs
     */
    public MimeTypeAndFileExtensionValidator(ConfigurationObject configObject) throws ConfigurationException {
        FileUploadHelper.validateNotNull(configObject, "Configuration object");

        // get the property values
        String validate = FileUploadHelper.getStringPropertyValue(configObject, VALIDATE_FILE_TYPE_PROPERTY, false);
        if (validate != null) {
            this.validateFileType = Boolean.valueOf(validate).booleanValue();
        }

        validate = FileUploadHelper.getStringPropertyValue(configObject, VALIDATE_FILE_EXTENSION_PROPERTY, false);
        if (validate != null) {
            this.validateFileExtension = Boolean.valueOf(validate).booleanValue();
        }

        // read ALLOWED_FILE_TYPES_PROPERTY property values and add them to allowedFileTypes set
        String[] fileTypes = this.getStringArrayPropertyValue(configObject, ALLOWED_FILE_TYPES_PROPERTY, false);
        if (fileTypes != null) {
            for (int i = 0; i < fileTypes.length; i++) {
                this.allowedFileTypes.add(fileTypes[i]);
            }
        }

        // read ALLOWED_FILE_EXTENSIONS_PROPERTY property values and add them to allowedFileExtensions set
        String[] fileExtensions = this.getStringArrayPropertyValue(configObject, ALLOWED_FILE_EXTENSIONS_PROPERTY,
                true);
        if (fileExtensions != null) {
            for (int i = 0; i < fileExtensions.length; i++) {
                this.allowedFileExtensions.add(fileExtensions[i]);
            }
        }
    }

    /**
     * <p>
     * Getter for validateFileType field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return Shows whether to validate the file type.
     */
    public synchronized boolean getValidateFileType() {
        return this.validateFileType;
    }

    /**
     * <p>
     * Setter for validateFileType field. Make sure to synchronize access to this method.
     * </p>
     *
     * @param validateFileType
     *            Shows whether to validate the file type.
     */
    public synchronized void setValidateFileType(boolean validateFileType) {
        this.validateFileType = validateFileType;
    }

    /**
     * <p>
     * Adds file type to the set of allowed file types. If the file type already present in the set nothing
     * happens. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileType
     *            File type to be added. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if the parameter is null or empty string.
     */
    public synchronized void addFileType(String fileType) {
        FileUploadHelper.validateString(fileType, "fileType");
        allowedFileTypes.add(fileType.trim());
    }

    /**
     * <p>
     * Removes file type from the set of allowed file types. If the file type is not present in the set
     * nothing happens. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileType
     *            File type to be removed. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if the parameter is null or empty string.
     */
    public synchronized void removeFileType(String fileType) {
        FileUploadHelper.validateString(fileType, "fileType");
        allowedFileTypes.remove(fileType.trim());
    }

    /**
     * <p>
     * Returns an array containing all the allowed file types. Make sure to synchronize access to this
     * method.
     * </p>
     *
     * @return Array containing all the allowed file types. Can not be null. Can be empty. Values can not be
     *         null or empty strings.
     */
    public synchronized String[] getFileTypes() {
        return (String[]) allowedFileTypes.toArray(new String[allowedFileTypes.size()]);
    }

    /**
     * <p>
     * Removes all the allowed file types. Make sure to synchronize access to this method.
     * </p>
     */
    public synchronized void clearFileTypes() {
        allowedFileTypes.clear();
    }

    /**
     * <p>
     * Getter for validateFileExtension field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return Shows whether to validate the file extension.
     */
    public synchronized boolean getValidateFileExtension() {
        return this.validateFileExtension;
    }

    /**
     * <p>
     * Setter for validateFileExtension field. Make sure to synchronize access to this method.
     * </p>
     *
     * @param validateFileExtension
     *            Shows whether to validate the file extension.
     */
    public synchronized void setValidateFileExtension(boolean validateFileExtension) {
        this.validateFileExtension = validateFileExtension;
    }

    /**
     * <p>
     * Adds file extension to the set of allowed file extensions. If the file extension already present in
     * the set nothing happens. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileExtension
     *            File extension to be added. Should not be null. Can be empty string.
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    public synchronized void addFileExtension(String fileExtension) {
        FileUploadHelper.validateNotNull(fileExtension, "fileExtension");
        allowedFileExtensions.add(fileExtension.trim());
    }

    /**
     * <p>
     * Removes file extension to the set of allowed file extensions. If the file extension is not present in
     * the set nothing happens. Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileExtension
     *            File extension to be removed. Should not be null. Can be empty string.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public synchronized void removeFileExtension(String fileExtension) {
        FileUploadHelper.validateNotNull(fileExtension, "fileExtension");
        allowedFileExtensions.remove(fileExtension.trim());
    }

    /**
     * <p>
     * Returns an array containing all the allowed file extensions. Make sure to synchronize access to this
     * method.
     * </p>
     *
     * @return Array containing all the allowed file extensions. Can not be null. Can be empty. Values can
     *         not be null but can be empty.
     */
    public synchronized String[] getFileExtensions() {
        return (String[]) allowedFileExtensions.toArray(new String[allowedFileExtensions.size()]);
    }

    /**
     * <p>
     * Removes all the allowed file extensions. Make sure to synchronize access to this method.
     * </p>
     */
    public synchronized void clearFileExtensions() {
        allowedFileExtensions.clear();
    }

    /**
     * <p>
     * Validates file based on its MIME-type and filename. Returns true if the file is considered valid and
     * false otherwise. Make sure to synchronize access to this method.
     * </p>
     *
     * <p>
     * The file is considered valid if its MIME-type is contained in a set of allowed MIME-types (if
     * validateFileType field is true) and its extension is contained in a set of allowed file extensions
     * (if validateFileExtension field is true).
     * </p>
     *
     * @param fileData
     *            FileData object with the properties of the uploaded file. Should not be null.
     * @return true if the file is considered valid and false otherwise.
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    public synchronized boolean validateFile(FileData fileData) {
        FileUploadHelper.validateNotNull(fileData, "fileData");

        if (validateFileType && !allowedFileTypes.contains(fileData.getContentType())) {
            return false;
        }

        if (validateFileExtension) {
            String fileName = fileData.getFileName();
            if (fileName == null) {
                return false;
            }

            // get file extension from the fileData.getFileName() - that is a substring after the last
            // dot('.') in the filename or an empty string if there's no '.' in the filename
            String fileExtension = "";
            int index = fileName.lastIndexOf('.');
            if (index != -1) {
                fileExtension = fileData.getFileName().substring(index + 1).trim();
            }

            if (!allowedFileExtensions.contains(fileExtension)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>
     * Get the string array value in <code>ConfigManager</code> with specified namespace and name of
     * property.
     * </p>
     *
     * @param namespace
     *            the namespace of the property.
     * @param name
     *            the name of the property.
     * @param allowEmpty
     *            the string array may contain empty string or not.
     * @return the string array value in <code>ConfigManager</code> with specified namespace and name of
     *         property.
     * @throws ConfigurationException
     *             if any error occurs.
     */
    private String[] getStringArrayPropertyValue(String namespace, String name, boolean allowEmpty)
            throws ConfigurationException {
        try {
            String[] values = ConfigManager.getInstance().getStringArray(namespace, name);

            if (values == null) {
                return null;
            }

            for (int i = 0; i < values.length; i++) {
                if (!allowEmpty && values[i].trim().length() == 0) {
                    throw new ConfigurationException("The value in the string array can not be empty string.");
                }
            }

            return values;
        } catch (UnknownNamespaceException une) {
            throw new ConfigurationException("The namespace with the name of " + namespace + " doesn't exist.", une);
        }
    }

    /**
     * <p>
     * Get the string array value in configObject.
     * </p>
     *
     * @param configObject
     *            the ConfigurationObject of the property.
     * @param name
     *            the name of the property.
     * @param allowEmpty
     *            the string array may contain empty string or not.
     * @return the string array value in configObject.
     * @throws ConfigurationException
     *             if any error occurs.
     */
    private String[] getStringArrayPropertyValue(ConfigurationObject configObject, String name, boolean allowEmpty)
            throws ConfigurationException {
        try {
            Object[] objs = configObject.getPropertyValues(name);

            if (objs == null) {
                return null;
            }

            String[] values = new String[objs.length];

            for (int i = 0; i < objs.length; i++) {
                if (objs[i] == null) {
                    throw new ConfigurationException("The value in the string array can not be null.");
                }

                if (!(objs[i] instanceof String)) {
                    throw new ConfigurationException(name + " must only contain string values");
                }

                values[i] = (String) objs[i];

                if (!allowEmpty && values[i].trim().length() == 0) {
                    throw new ConfigurationException("The value in the string array can not be empty string.");
                }
            }

            return values;
        } catch (ConfigurationAccessException cae) {
            throw new ConfigurationException("Error accessing configuration.", cae);
        }
    }
}
