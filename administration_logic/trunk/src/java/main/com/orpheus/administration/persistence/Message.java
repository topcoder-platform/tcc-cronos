
package com.orpheus.administration.persistence;
/**
 * Interface specifying the contract for a message class to be used as the DTO. It will include a guid, category, content and its type, and the time of the message. Implementations will be simple serializable transfer beans. It would transport the data between the client and the DAO layers. It is assembled at both those ends. The EJB layer does not operate on it.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface Message {
/**
 * <p>Gets the guid</p>
 * 
 * 
 * @return  the guid
 */
    public String getGuid();
/**
 * <p>Gets the category</p>
 * 
 * 
 * @return the category
 */
    public String getCategory();
/**
 * <p>Gets the content type</p>
 * 
 * 
 * @return the content type
 */
    public String getContentType();
/**
 * <p>Gets the content</p>
 * 
 * 
 * @return the content
 */
    public String getContent();
/**
 * <p>Gets the timestamp</p>
 * 
 * 
 * @return  the timestamp
 */
    public java.util.Date getTimestamp();
}


