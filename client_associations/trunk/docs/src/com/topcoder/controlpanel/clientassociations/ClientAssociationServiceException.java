package com.topcoder.controlpanel.clientassociations;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

@ApplicationException(rollback=true)
public class ClientAssociationServiceException extends BaseCriticalException {
    public ClientAssociationServiceException(String message) {
        super(message);
    }

    public ClientAssociationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAssociationServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    public ClientAssociationServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
