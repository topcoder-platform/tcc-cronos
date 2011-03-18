package com.topcoder.web.memberphoto.manager;
import com.topcoder.util.errorhandling.*;
import java.lang.*;
/**
USAGE:
     This exception is the base exception for all exceptions raised from operations performed in this design.
     This exception wraps exceptions raised from persistence, from usage of the JPA Hibernate utilities or used TopCoder components.
IMPLEMENTATION NOTES:
     extends BaseCriticalException;
THREAD SAFETY:
     This exception is not thread safe because parent exception is not thread safe.
     The application should handle this exception in a thread-safe manner.
*/
public class MemberPhotoManagementException extends BaseCriticalException{
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoManagementException' instance with the given message.
IMPLEMENTATION NOTES:
     super(message);

     @param message the exception message
@param message 
*/
public MemberPhotoManagementException(String message) {
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoManagementException' exception with the given message and cause.
IMPLEMENTATION NOTES:
     super(message,cause);

     @param message the exception message
     @param cause the exception cause
@param message 
@param cause 
*/
public MemberPhotoManagementException(String message, Throwable cause) {
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoManagementException' exception with the given message and data.
IMPLEMENTATION NOTES:
     super(message,data);

     @param message the exception message
     @param data the additional exception data
@param message 
@param data 
*/
public MemberPhotoManagementException(String message, ExceptionData data) {
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoManagementException' exception with the given message, cause, and data.
IMPLEMENTATION NOTES:
     super(message,cause,data);

     @param message the exception message
     @param cause the exception cause
     @param data the additional exception data
@param message 
@param cause 
@param data 
*/
public MemberPhotoManagementException(String message, Throwable cause, ExceptionData data) {
}
}

