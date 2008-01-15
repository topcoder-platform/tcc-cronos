package com.topcoder.controlpanel.clientassociations.dao;

import javax.ejb.ApplicationException;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceException;
import com.topcoder.util.errorhandling.ExceptionData;

@ApplicationException(rollback=true)
public class ClientAssociationDAOException extends ClientAssociationServiceException {
    public ClientAssociationDAOException(String message) {
        super(message);
    }

    public ClientAssociationDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAssociationDAOException(String message, ExceptionData data) {
        super(message, data);
    }

    public ClientAssociationDAOException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
