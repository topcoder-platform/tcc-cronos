
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;

/**
 * This class holds the information of an address
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This class will be created by the application directly and created by the implementaions of AddressDAO. The application can get/set all the properties of it.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is not thread safe by being mutable. This class is not supposed to be used in multithread environment. If it would be used in multithread environment, it should be synchronized externally.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1de3]
 */
public class Address extends com.topcoder.timetracker.common.TimeTrackerBean {

/**
 * <p>Represents the first line of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c9b]
 */
    private String line1 = null;

/**
 * <p>Represents the second line of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c8a]
 */
    private String line2 = null;

/**
 * <p>Represents the city of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c79]
 */
    private String city = null;

/**
 * <p>Represents the postal code of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null, non empty string by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c68]
 */
    private String postalCode = null;

/**
 * <p>Represents the country of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null country by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c57]
 */
    private com.topcoder.timetracker.contact.Country country = null;

/**
 * <p>Represents the state of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null state by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c21]
 */
    private com.topcoder.timetracker.contact.State state = null;

/**
 * <p>Represents the type of the address. This variable is set to null initially,&nbsp; is mutable. It is only allowed to be set to non null type by the setter. It is access by its getter and setter methods.</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1bff]
 */
    private com.topcoder.timetracker.contact.AddressType addressType = null;

/**
 * <p>Constructs the Address.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Empty constructor.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1dcb]
 */
    public  Address() {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the first line</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return line1</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1c46]
 * @return possible null, non empty string representing line1
 */
    public String getLine1() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the first line</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variable</p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1bee]
 * @param line1 non null, non empty string representing line1
 * @throws IllegalArgumentException if the line1 is null or empty(trim'd)
 */
    public void setLine1(String line1) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the second line</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return line2</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1bae]
 * @return possible null, non empty string representing line2
 */
    public String getLine2() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the second line</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variable</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1b89]
 * @param line2 non null, non empty string representing line2
 * @throws IllegalArgumentException if the line2 is null or empty(trim'd)
 */
    public void setLine2(String line2) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the city</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return city</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1b49]
 * @return possible null, non empty string representing city
 */
    public String getCitry() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the city</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variable</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1b24]
 * @param city non null, non empty string representing city
 * @throws IllegalArgumentException if the city is null or empty(trim'd)
 */
    public void setCity(String city) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the postal code</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return postalCode</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1ae4]
 * @return possible null, non empty string representing postal code
 */
    public String getPostalCode() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the postal code</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variabl</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1abf]
 * @param postalCode non null, non empty string representing postal code
 * @throws IllegalArgumentException if the postalCode is null or empty(trim'd)
 */
    public void setPostalCode(String postalCode) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the country</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return country</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1a7f]
 * @return possible null country
 */
    public com.topcoder.timetracker.contact.Country getCountry() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the country</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variabl</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1a5a]
 * @param country non null country
 * @throws IllegalArgumentException if the country is null
 */
    public void setCountry(com.topcoder.timetracker.contact.Country country) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Set the state</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Save the argument to like named variabl</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1a1a]
 * @return possible null state
 */
    public com.topcoder.timetracker.contact.State getState() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the state</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variabl</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm19f5]
 * @param state non null state
 * @throws IllegalArgumentException if the state is null
 */
    public void setState(com.topcoder.timetracker.contact.State state) {        /** lock-end */
        // your code here
    } /** lock-begin */

/**
 * <p>Get the address type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return addressType</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm1950]
 * @return possible null address type
 */
    public AddressType getAddressType() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */

/**
 * <p>Set the address type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call setChanged(true) and save the argument to like named variabl</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im5c56fddm110d9eb0fafmm192b]
 * @param addressType non null address type
 * @throws IllegalArgumentException if the type is null
 */
    public void setAddressType(AddressType addressType) {        /** lock-end */
        // your code here
    } /** lock-begin */
 }
