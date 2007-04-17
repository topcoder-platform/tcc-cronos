
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;

/**
 * This class holds the information of a contact.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This class will be created by the application directly and created by the implementaions of ContactDAO. The application can get/set all the properties of it.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is not thread safe by being mutable. This class is not supposed to be used in multithread environment. If it would be used in multithread environment, it should be synchronized externally.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm313f]
 */
public class Contact extends com.topcoder.timetracker.common.TimeTrackerBean {

/**
 * <p>Represents the first name of this Contact. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm30cb]
 */
    private String firstName = null;

/**
 * <p>Represents the last name of this Contact. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm30b9]
 */
    private String lastName = null;

/**
 * <p>Represents the phone number of this Contact. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm660a]
 */
    private String phoneNumber = null;

/**
 * <p>Represents the email address of this Contact. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm65f9]
 */
    private String emailAddress = null;

/**
 * <p>Represents the type of this Contact. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null ContactType. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1d9c]
 */
    private com.topcoder.timetracker.contact.ContactType contactType = null;

/**
 * <p>Constructs the Contact.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Empty constructor.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm3096]
 */
    public  Contact() {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the first name.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return firstName</p>
 * <p></p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm3002]
 * @return the possible null, non empty first name
 */
    public String getFirstName() {        /** lock-end */
        return firstName;
    } /** lock-begin */

/**
 * <p>Set the first name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save firstName to the like named variable</p>
 * <p></p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm2fdd]
 * @param firstName the non null, non empty first name
 * @throws IllegalArgumentException if the firstName is null or empty(trim'd)
 */
    public void setFirstName(String firstName) {        /** lock-end */
        this.firstName = firstName;
    } /** lock-begin */

/**
 * <p>Get the last name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return lastName</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm65bd]
 * @return the possible null, non empty first name
 */
    public String getLastName() {        /** lock-end */
        return lastName;
    } /** lock-begin */

/**
 * <p>Set the last name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save lastName to the like named variable</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm6598]
 * @param lastName the non null, non empty last name
 * @throws IllegalArgumentException if the name is null or empty(trim'd)
 */
    public void setLastName(String lastName) {        /** lock-end */
        this.lastName = lastName;
    } /** lock-begin */

/**
 * <p>Get the phone number</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return phoneNumber</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm6558]
 * @return the non null, non empty phone number
 */
    public String getPhoneNumber() {        /** lock-end */
        return phoneNumber;
    } /** lock-begin */

/**
 * <p>Set the phone number</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save phoneNumber to the like named variable</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm6533]
 * @param phoneNumber the non null, non empty phone number
 * @throws IllegalArgumentException if the phone number is null or empty(trim'd)
 */
    public void setPhoneNumber(String phoneNumber) {        /** lock-end */
        this.phoneNumber = phoneNumber;
    } /** lock-begin */

/**
 * <p>Get the email address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return emailAddress</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm64f3]
 * @return the possible null, non empty email address
 */
    public String getEmailAddress() {        /** lock-end */
        return emailAddress;
    } /** lock-begin */

/**
 * <p>Set the email address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save emailAddress to the like named&nbsp; variable</p>
 * 
 * @poseidon-object-id [Im21c42aeem110abf33295mm64ce]
 * @param emailAddress the non null, non empty email address
 * @throws IllegalArgumentException if the email address is null or empty(trim'd)
 */
    public void setEmailAddress(String emailAddress) {        /** lock-end */
        this.emailAddress = emailAddress;
    } /** lock-begin */

/**
 * <p>Get the type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return type</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1d5a]
 * @return the possible null type
 */
    public com.topcoder.timetracker.contact.ContactType getContactType() {        /** lock-end */
        return contactType;
    } /** lock-begin */

/**
 * <p>Set the type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save type to the like named&nbsp; variable</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1d15]
 * @param contactType the non null type
 * @throws IllegalArgumentException if the type is null
 */
    public void setContactType(com.topcoder.timetracker.contact.ContactType contactType) {        /** lock-end */
        this.contactType = contactType;
    } /** lock-begin */
 }
