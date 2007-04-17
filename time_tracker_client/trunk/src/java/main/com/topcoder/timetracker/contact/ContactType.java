
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;

/**
 * This enumeration represents the contact type.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This enumeration will be used in ContactManager and implementations of ContactDAO.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread-safe by being immutable.</p>
 * 
 * @poseidon-object-id [I771b1a16m110df07b0a1m4162]
 */
public class ContactType extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * <p>Represents the project contact type. It will never be null. It will be referenced in ContactManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2e15]
 */
    public static final com.topcoder.timetracker.contact.ContactType PROJECT = new ContactType(1, "PROJECT");

/**
 * <p>Represents the client contact type. It will never be null. It will be referenced in ContactManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2dfb]
 */
    public static final com.topcoder.timetracker.contact.ContactType CLIENT = new ContactType(2, "CLIENT");

/**
 * <p>Represents the company contact type. It will never be null. It will be referenced in ContactManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2de1]
 */
    public static final com.topcoder.timetracker.contact.ContactType COMPANY = new ContactType(3, "COMPANY");

/**
 * <p>Represents the user contact type. It will never be null. It will be referenced in ContactManager.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2db3]
 */
    public static final com.topcoder.timetracker.contact.ContactType USER = new ContactType(4, "USER");

/**
 * <p>Represents the id of the contact type. This variable is set in constructor,&nbsp; is immutable and &gt;0. It is referenced by the getId method.</p>
 * 
 * @poseidon-object-id [I33e6bba6m110e66f68a9mm2678]
 */
    private final long id = 0;

/**
 * <p>Represents the string representation of the type. This variable is set in constructor,&nbsp; is immutable and non null, non empty. It is referenced by the toString method.</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m46ba]
 */
    private final String type = null;

/**
 * <p>Private constructor of ContactType.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Empty constructor.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2d99]
 * @param id >0 id of the type
 * @param type the non null non empty string representing the type
 */
    private  ContactType(long id, String type) {        
        // your code here
    } 

/**
 * <p>Get the id</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return id</p>
 * <p></p>
 * 
 * @poseidon-object-id [I33e6bba6m110e66f68a9mm25cb]
 * @return >0 id of the type
 */
    public long getId() {        
        // your code here
        return 0;
    } 

/**
 * <p>Get the string representation of the type</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return type</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m46b7]
 * @return the non null non empty string representing the type
 */
    public String toString() {        
        // your code here
        return null;
    } 
 }
