package com.topcoder.web.memberphoto.entities;
import java.io.Serializable;
import java.lang.*;
/**
USAGE:
     This class represents the Image java bean. An Image can contain an image identifier and a file name.
     This is a simple java bean (with a default no-arg constructor and for each property, a corresponding getter/setter method).
     Any attribute in this bean is OPTIONAL so NO VALIDATION IS PERFORMED here.     
     This class is Serializable (implements Serializable).
     The IoC should handle the validity if the passed values in setters.
ANNOTATIONS:
     @Entity - this annotation should be used to mark this java bean as an entity and to ease its use with JPA.  
IMPLEMENTATION NOTES:
     implements Serializable;
Thread safety:
     This class contains only mutable fields so therefore it is not thread safe.
*/
public class Image implements Serializable{
/**
USAGE:
     This field represents the 'id' property of the Image.
     Represents the identifier of the image. 
INITIAL VALUE:
     It is default to the default value of this data type when it is not assigned.
ACCESSED:
     Thru corresponding getter/setter methods.
     It is retrieved from Image.id [Image.getId()].
VALID VALUES:
     There are no restrictions at this moment. It can take any value. OPTIONAL.
*/
private long id;
/**
USAGE:
     This field represents the 'fileName' property of the Image.
     Represents the file name of the image. 
INITIAL VALUE:
     It is default to the default value of this data type when it is not assigned.
ACCESSED:
     Thru corresponding getter/setter methods.
     It is retrieved from Image.fileName [Image.getFileName()].
VALID VALUES:
     There are no restrictions at this moment. It can take any value. OPTIONAL.
*/
private String fileName;
/**
USAGE:
Default no-arg constructor. Constructs a new 'Image' instance.
IMPLEMENTATION NOTES:
Does nothing.
*/
public Image() {
}
/**
USAGE:
Getter for 'id' property. Please refer to the related 'id' field for more information.
IMPLEMENTATION NOTES:
return this.id;
@param Return the value of the 'id' property. It can be any value.
@return the value of the 'id' property. It can be any value.
*/
public long getId() {
    return 0;
}
/**
USAGE:
Setter for 'id' property. Please refer to the related 'id' field for more information.
IMPLEMENTATION NOTES:
this.id = id;
@param id the new id to be used for 'id' property. It can be any value.
@param Return 
*/
public void setId(long id) {
}
/**
USAGE:
Getter for 'fileName' property. Please refer to the related 'fileName' field for more information.
IMPLEMENTATION NOTES:
return this.fileName;
@param Return the value of the 'fileName' property. It can be any value.
@return the value of the 'fileName' property. It can be any value.
*/
public String getFileName() {
    return null;
}
/**
USAGE:
Setter for 'fileName' property. Please refer to the related 'fileName' field for more information.
IMPLEMENTATION NOTES:
this.fileName = fileName;
@param fileName the new fileName to be used for 'fileName' property. It can be any value.
@param Return 
*/
public void setFileName(String fileName) {
}
}

