/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.DocumentNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when a document is not found. It is related with
 * "document_not_found_faultMsg" fault in WSDL, but it contains only annotations about "document_not_found_fault"
 * because the messages are automatically constructed.
 * </p>
 * <p>
 * The constructors with <code>{@link DocumentNotFoundFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "document_not_found_fault", faultBean = "com.topcoder.service.prerequisite.ejb.DocumentNotFoundFault")
public class DocumentNotFoundException extends PrerequisiteServiceException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final DocumentNotFoundFault faultInfo;

    /**
     * <p>
     * Represents the document id not found.
     * </p>
     */
    private final long documentIdNotFound;

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the document id not found.
     */
    public DocumentNotFoundException(String message, DocumentNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.documentIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message, cause exception and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the document id not found.
     * @param cause
     *            the cause exception
     */
    public DocumentNotFoundException(String message, DocumentNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.documentIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message and the document id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param documentIdNotFound
     *            the document id not found.
     */
    public DocumentNotFoundException(String message, long documentIdNotFound) {
        super(message);
        this.faultInfo = null;
        this.documentIdNotFound = documentIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message, inner cause and the document id
     * not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param documentIdNotFound
     *            the document id not found.
     */
    public DocumentNotFoundException(String message, Throwable cause, long documentIdNotFound) {
        super(message, cause);
        this.faultInfo = null;
        this.documentIdNotFound = documentIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message, exception data and the document
     * id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param documentIdNotFound
     *            the document id not found.
     */
    public DocumentNotFoundException(String message, ExceptionData data, long documentIdNotFound) {
        super(message, data);
        this.faultInfo = null;
        this.documentIdNotFound = documentIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>DocumentNotFoundException</code> instance with error message, exception data, inner cause and
     * the document id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param documentIdNotFound
     *            the document id not found.
     */
    public DocumentNotFoundException(String message, Throwable cause, ExceptionData data, long documentIdNotFound) {
        super(message, cause, data);
        this.faultInfo = null;
        this.documentIdNotFound = documentIdNotFound;
    }

    /**
     * <p>
     * Gets the document id not found.
     * </p>
     *
     * @return the document id not found.
     */
    public long getDocumentIdNotFound() {
        return documentIdNotFound;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     *
     * @return the fault bean, possibly null.
     */
    public DocumentNotFoundFault getFaultInfo() {
        return faultInfo;
    }
}
