package com.topcoder.web.memberphoto.manager;
import com.topcoder.util.errorhandling.ExceptionData;
/**
USAGE:
     This exception signals an issue if the member photo does not exists for the given member id.
      Has an member id argument in each constructor and a getter for this property.
IMPLEMENTATION NOTES:
     extends MemberPhotoManagementException;
THREAD SAFETY:
     This exception is not thread safe because parent exception is not thread safe.
     The application should handle this exception in a thread-safe manner.
*/
public class MemberPhotoNotFoundException extends MemberPhotoManagementException{
/**
USAGE:
     This field represents the member's id who's photo was not found.
INITIAL VALUE:
     It is initialized during construction by any of the constructors.
ACCESSED:
     By the getMemberId() method.
MODIFIED:
     Will not change after initialization.
VALID VALUES:
   Can be any value.
*/
private final long memberId;
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoNotFoundException' exception with the given message and memberId.
IMPLEMENTATION NOTES:
     super(message);
     this.memberId=memberId;

     @param message the exception message
     @param memberId the memberId
@param message 
@param memberId 
*/
public MemberPhotoNotFoundException(String message, long memberId) {
	super(message);
	this.memberId = memberId;
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoNotFoundException' exception with the given message, cause and memberId.
IMPLEMENTATION NOTES:
     super(message,cause);
     this.memberId=memberId;

     @param message the exception message
     @param cause the exception cause
     @param memberId the memberId
@param message 
@param cause 
@param memberId 
*/
public MemberPhotoNotFoundException(String message, Throwable cause, long memberId) {
	super(message,cause);
	this.memberId = memberId;
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoNotFoundException' exception with the given message, data and memberId.
IMPLEMENTATION NOTES:
     super(message,data);
     this.memberId=memberId;

     @param message the exception message
     @param data the additional exception data
     @param memberId the memberId
@param message 
@param data 
@param memberId 
*/
public MemberPhotoNotFoundException(String message, ExceptionData data, long memberId) {
super(message,data);
this.memberId = memberId;
}
/**
USAGE:
     Constructor. Constructs a new 'MemberPhotoNotFoundException' exception with the given message, cause, data and memberId.
IMPLEMENTATION NOTES:
     super(message,cause,data);
     this.memberId=memberId;

     @param message the exception message
     @param cause the exception cause
     @param data the additional exception data
     @param memberId the memberId
@param message 
@param cause 
@param data 
@param memberId 
*/
public MemberPhotoNotFoundException(String message, Throwable cause, ExceptionData data, long memberId) {
super(message,cause,data);
this.memberId = memberId;
}
/**
USAGE:
     Simply return the memberId field.
IMPLEMENTATION NOTES:
     return this.memberId;

     @return the memberId field
@param Return 
@return 
*/
public long getMemberId() {
    return 0;
}
}

