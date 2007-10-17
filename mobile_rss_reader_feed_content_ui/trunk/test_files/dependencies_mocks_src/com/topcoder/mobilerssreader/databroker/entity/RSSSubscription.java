
package com.topcoder.mobilerssreader.databroker.entity;
import com.topcoder.mobile.authenticator.Authenticator;
import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.NotificationType;
import java.io.*;

/**
 * This class extends Entity to represent RSS Subscription. It contains not only name, object id, but also description,
 * url, polling interval time and notification type. All these members can be &quot;get&quot; by &quot;getter&quot;
 * <p>
 * It implements getBytes method to provide its own byte array representation, which will be store into its RMS storage.
 * </p>
 * <p>
 * It also implements getTypeId method to provide its own type id, which will be used to name its RMS storage and refer
 * it.
 * </p>
 * <p>
 * Thread safety: All&nbsp; members are mutable. However, all &quot;setter&quot; are synchronized. Hence, it is thread
 * safe.
 * </p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7a74]
 */
public class RSSSubscription extends com.topcoder.mobilerssreader.databroker.entity.Entity {

/**
 * <p>
 * Represents description of RSS subscription.
 * </p>
 * <p>
 * It is initialized in constructor. it should not be null and may be empty.
 * </p>
 * <p>
 * It is used in relative &quot;getter&quot;/&quot;setter&quot; methods.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm76f8]
 */
    private String description;

/**
 * <p>
 * Represents url of the RSSSubscription.
 * </p>
 * <p>
 * It is initialized in constructor and should not be null.
 * </p>
 * <p>
 * It is used in relative &quot;getter&quot;/&quot;setter&quot; methods.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm76d1]
 */
    private URL url;

/**
 * The interval to poll RSS subscription. Its unit is minute.
 * <p>
 * It is initialized in constructor and should not be less than 1.
 * </p>
 * <p>
 * It is used in relative &quot;getter&quot;/&quot;setter&quot; methods.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm76aa]
 */
    private int interval ;

/**
 * <p>
 * Represents notification type for this RSS subscription. It has three pre-defined types: detaultsound, vibrate,
 * displaynewmsgicon, which are defined in class NotificationType.
 * </p>
 * <p>
 * It is initialized in constructor and should be one type defined in NotificationType.
 * </p>
 * <p>
 * It is used in relative &quot;getter&quot;/&quot;setter&quot; methods.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7686]
 */
    private int notificationType;

/**
 * <p>
 * Represents type id of RSSSubscription class. Its value is &quot;RSSSubscription&quot; string. It will be used to
 * refered as class type and RMS storage name.
 * </p>
 * <p>
 * It is initialized as final and static string.
 * </p>
 * <p>
 * It is used in method &quot;getTypeId&quot; to get its value.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm74ef]
 */
    public static final String TYPEID = "RSSSubscription";

/**
 * <p>
 * Represents the reference of Authenticator class, which contains information related authentication of RSS
 * subscription
 * </p>
 * <p>
 * It is initialized in constructor and should not be null.
 * </p>
 * <p>
 * It is got by &quot;getter&quot; and set by &quot;setter&quot;.
 * </p>
 * 
 * @poseidon-object-id [I743a2518m113c435414bmm5436]
 */
    private Authenticator authenticator;

/**
 * <p>
 * Constructor of RSSSubscription
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. super(name, objectId).
 * </p>
 * <p>
 * 2. set parameters to their relative members
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7507]
 * @param name
 *            name of the RSSSubscription, which should not be null or empty
 * @param objectId
 *            object id of the RSSSubscription, which may be null and should not be empty,
 * @param description
 *            description string of the RSSSubscription, which should not be null
 * @param url
 *            url of the RSSSubscription, which should not be null
 * @param interval
 *            int value of polling interval, which should be greater than 0
 * @param notificationType
 *            an int value defined in NotificationType class, if it is not defined in NotificationType, throw
 *            IllegalArgumentException
 * @param authenticator
 *            an instance of Authenticator related with RSSSubscription, which should not be null
 * @throws IllegalArgumentException
 *             if any parameter doesn't satisfy its condition
 */
    public  RSSSubscription(String name, String objectId, String description, URL url, int interval, int notificationType, Authenticator authenticator) {        
        super(name, objectId);
        this.description = description;
        this.url = url;
        this.interval = interval;
        this.notificationType = notificationType;
        this.authenticator = authenticator;
    } 

/**
 * <p>
 * Get description member.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return description
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm729e]
 * @return the description member
 */
    public String getDescription() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Get url string of the RSSSubscription
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return url
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7230]
 * @return the member url
 */
    public com.topcoder.mobile.httphandler.URL getURL() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Get polling interval of RSSSubscription
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return interval.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm71c2]
 * @return the polling interval of RSSSubscription
 */
    public String getInterval() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Get notification type of RSSSubscription
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return notificationType
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm713e]
 * @return the member notificationType
 */
    public int getNotificationType() {        
        // your code here
        return 0;
    } 

/**
 * <p>
 * Get byte array of RSSSubscription. If any Object member is null, throw IllegalStateException.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. open a ByteArrayOutputStream and a DataOutputStream <br/> [ByteArrayOutputStream baos = new
 * ByteArrayOutputStream();<br/> DataOutputStream dos = new DataOutputStream(baos);]
 * </p>
 * <p>
 * 2. write byte array of name's length(an int value) and byte array of name string
 * </p>
 * <p>
 * 3.&nbsp; write byte array of objectId's length(an int value) and byte array of objectId string. Especially, if
 * objectId is null, that is user doesn't know this object's id in record store, set objectId to be -1.
 * </p>
 * <p>
 * 4. write byte array of description's length(an int value) and byte array of description string
 * </p>
 * <p>
 * 5. write byte array of url's length(an int value) and byte array of url string
 * </p>
 * <p>
 * 6.write byte array of interval(an int value)
 * </p>
 * <p>
 * 7. write byte array of notification(an int value)
 * </p>
 * <p>
 * 8. write byte array of Authenticator's AuthenticatorType and persistence string array.<br/> [write byte array of
 * AuthenticationType(int value). write byte array of the size of persistence string array. For every persistence string
 * in AuthenticationType, write byte array of string's length(int value) and write byte array of string]
 * </p>
 * <p>
 * 9&nbsp; get all byte array [byte[] array = baos.toByteArray();]
 * </p>
 * <p>
 * 10. close ByteArrayOutputStream and DataOutputStream.
 * </p>
 * <p>
 * 10. return array.
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm70d0]
 * @return byte array of this RSSSubscription instance
 * @throws IllegalStateException
 *             if any Object member is null
 * @throws IOException
 *             if fails happen in writing content into byte array
 */
    public byte[] getBytes() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Get type id of RSSSubscription
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. reurn TYPEID;
 * </p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7036]
 * @return type id of the RSSSubscription
 */
    public String getTypeId() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Set member description.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1 this.description = description;
 * </p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm4dd6]
 * @param description
 *            new updated description, which should not be null or empty
 * @throws IllegalArgumentException
 *             if description is null or empty
 */
    public synchronized void setDescription(String description) {        
        // your code here
    } 

/**
 * <p>
 * Set member url
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. this.url = url
 * </p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm4d7e]
 * @param url
 *            new updated url string, which should not be null or empty
 * @throws IllegalArgumentException
 *             if url is null or empty
 */
    public synchronized void setURL(com.topcoder.mobile.httphandler.URL url) {        
        // your code here
    } 

/**
 * <p>
 * Set interval minutes to read subscription.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. this.interval = interval
 * </p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm4d2a]
 * @param interval
 *            the updated interval minutes to read subscription, which should not be less than 1
 * @throws IllegalArgumentException
 *             if interval is less than 1
 */
    public synchronized void setInterval(int interval) {        
        // your code here
    } 

/**
 * <p>
 * Set notification type member. The input argument should be one defined in NotificationType class
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. this.notificationType = notificationType
 * </p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm4cc4]
 * @param notificationType
 *            one defined in NotificationType, which should not be other value.
 * @throws IllegalArgumentException
 *             if notificationType is not defined in NotificationType class
 */
    public synchronized void setNotificationType(int notificationType) {        
        // your code here
    } 

/**
 * <p>
 * Return member &quot;authenticator&quot;
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return authenticator.
 * </p>
 * 
 * @poseidon-object-id [I743a2518m113c435414bmm5365]
 * @return member "authenticator"
 */
    public com.topcoder.mobile.authenticator.Authenticator getAuthenticator() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Set member &quot;authenticator&quot; to new reference.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. this.authenticator
 * </p>
 * 
 * @poseidon-object-id [I743a2518m113c435414bmm533f]
 * @param authenticator
 *            new Authenticator instance, which should not be null
 * @throws IllegalArgumentException
 *             if authenticator is null
 */
    public synchronized void setAuthenticator(com.topcoder.mobile.authenticator.Authenticator authenticator) {        
        // your code here
    } 

/**
 * <p>
 * Get EntitId instance that represents this entity.
 * </p>
 * <p>
 * Implementation:
 * </p>
 * <p>
 * 1. return new EntityId(super.objectId, TYPEID);
 * </p>
 * 
 * @poseidon-object-id [I14a7a8dam113c98ea041mm53ce]
 * @return EntityId instance that represents this entity
 */
    public com.topcoder.mobilerssreader.databroker.entity.EntityId getEntityId() {        
        // your code here
        return null;
    } 
 }
