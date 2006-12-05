
package com.orpheus.administration.persistence;
/**
 * Interface specifying the contract for translating between SearchCriteria instances and their transport equivalents &ndash; SearchCriteriaDTO. The former are value objects used on the outside world and a DTO is an entity this component uses to ferry info between the clients and the DAOs. Implementations will constrain the data types they support.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface SearchCriteriaTranslator {
/**
 * <p>Assembles the SearchCriteriaDTO from the SearchCriteria VO. The VO represents a object that is used outside this component. The DTO is used as the custom transfer object inside this component to ensure serializability.</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>IllegalArgumentException If the parameter is null.</li>
 * <li>TranslationException If there is any problem during the translation.</li>
 * </ul>
 * <p></p>
 * <p></p>
 * <p></p>
 * 
 * 
 * @param searchCriteria SearchCriteria VO
 * @return SearchCriteria DTO
 */
    public com.orpheus.administration.persistence.SearchCriteriaDTO assembleSearchCriteriaDTO(com.orpheus.administration.persistence.SearchCriteria searchCriteria);
}


