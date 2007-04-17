
package com.topcoder.timetracker.contact;

/**
 * <p>This enumeration represents the address type.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This enumeration will be used in AddressManager and implementations of AddressDAO.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread-safe by being immutable.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd3b]
 */
public class AddressType extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * <p>Represents the project address type. It will never be null. It will be referenced in AddressManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd3a]
 */
    public static final AddressType PROJECT = new AddressType(1, "PROJECT");

/**
 * <p>Represents the client address type. It will never be null. It will be referenced in AddressManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd37]
 */
    public static final AddressType CLIENT = new AddressType(2, "CLIENT");

/**
 * <p>Represents the company address type. It will never be null. It will be referenced in AddressManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd34]
 */
    public static final AddressType COMPANY = new AddressType(3, "COMPANY");

/**
 * <p>Represents the user address type. It will never be null. It will be referenced in AddressManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd31]
 */
    public static final AddressType USER = new AddressType(4, "USER");

/**
 * <p>Represents the id of the address type. This variable is set in constructor,&nbsp; is immutable and &gt;0. It is referenced by the getId method.</p>
 * 
 * @poseidon-object-id [I33e6bba6m110e66f68a9mm24f4]
 */
    private final long id;

/**
 * <p>Represents the string representation of the type. This variable is set in constructor,&nbsp; is immutable and non null, non empty. It is referenced by the toString method.</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m459a]
 */
    private final String type;

/**
 * <p>Get the id</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return id</p>
 * <p></p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd2e]
 * @param id >0 id of the type
 * @param type the non null non empty string representing the type
 */
    private  AddressType(long id, String type) {        /** lock-end */
        this.id = id;
        this.type = type;
    } /** lock-begin */

/**
 * <p>Get the id</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return id</p>
 * <p></p>
 * 
 * @poseidon-object-id [I33e6bba6m110e66f68a9mm24ea]
 * @return >0 id of the type
 */
    public long getId() {        /** lock-end */
        // your code here
        return 0;
    } /** lock-begin */

/**
 * <p>Get the string representation of the type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return type</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m4522]
 * @return the non null non empty string representing the type
 */
    public String toString() {        /** lock-end */
        // your code here
        return null;
    } /** lock-begin */
 }
