
package com.orpheus.administration.persistence;

/**
 * Extends AdministrationException. This exception is thrown by MessageTranslator and SearchCriteriaTranslator and its subclasses if there is an error while doing the translations. At this point, the implementations do not use it because the translations are very simple and do not involve any checked exceptions.
 * <p></p>
 * 
 */
public class TranslationException extends com.orpheus.administration.AdministrationException {

/**
 * <p>Creates new exception with error message</p>
 * 
 * 
 * @param msg error message
 */
    public  TranslationException(String msg) {        
        super(msg);
    } 

/**
 * <p>Creates new exception with error message and cause of error</p>
 * 
 * 
 * @param msg error message
 * @param cause cause of error
 */
    public  TranslationException(String msg, Throwable cause) {        
        super(msg);
    } 
 }
