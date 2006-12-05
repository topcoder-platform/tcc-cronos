
package com.orpheus.administration.persistence;
/**
 * Interface specifying the contract for translating between message-related instances and their transport equivalents &ndash; Message. The former are value objects used on the outside world and a DTO is an entity this component uses to ferry info between the clients and the DAOs. Implementations will constrain the data types they support.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface MessageTranslator {
/**
 * <p>Assembles the VO from the Message DTO. The VO represents a object that is used outside this component. The DTO is used as the custom transfer object for messages inside this component to ensure serializability.</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>IllegalArgumentException If the parameter is null.</li>
 * <li>TranslationException If there is any problem during the translation.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param messageDataTransferObject Message DTO
 * @return Value Object converted from Message
 */
    public Object assembleMessageVO(com.orpheus.administration.persistence.Message messageDataTransferObject);
/**
 * <p>Assembles the DTO from the VO. The VO represents a object that is used outside this component. The DTO is used as the custom transfer object for messages inside this component to ensure serializability.</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>IllegalArgumentException If the parameter is null.</li>
 * <li>TranslationException If there is any problem during the translation.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param valueObject Value Object to conver to Message
 * @return Message DTO
 */
    public com.orpheus.administration.persistence.Message assembleMessageDTO(Object valueObject);
}


