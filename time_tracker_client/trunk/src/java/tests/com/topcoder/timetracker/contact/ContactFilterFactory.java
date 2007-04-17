
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;

/**
 * This class is used to create pre-defined filters or AND/OR/NOT filters which are used to search contacts.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This class can't be created. The application can create filters by its static methods. The column names used in this factory are aliases. The SearchBundle will convert the aliases to the actual names defined in the SearchBundle configuration files.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable.</p>
 * <ID="UML note. note-id=[I62bc0b48m1111c1c5704m1db1] ">ContactFilterFactory uses ContactType
 * <BR></ID>
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm31a4]
 */
public class ContactFilterFactory {

/**
 * <p>Represents the alias of last name column in the contact table. It is set to &quot;last_name&quot; initially. It is non null, non empty. It will be referenced by createNameKeywordFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmda1]
 */
    public static final String FIRST_NAME = "first_name";

/**
 * <p>Represents the alias of first name column in the contact table. It is set to &quot;first_name&quot; initially. It is non null, non empty. It will be referenced by createNameKeywordFilter method.</p>
 * 
 * @poseidon-object-id [I158f8e4cm1112fa339e6m15e2]
 */
    public static final String LAST_NAME = "last_name";

/**
 * <p>Represents the alias of creation_date column in the contact table. It is set to &quot;creation_date&quot; initially. It is non null, non empty. It will be referenced by createCreatedInFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dme1e]
 */
    public static final String CREATION_DATE = "creation_date";

/**
 * <p>Represents the alias of modification_date column in the contact table. It is set to &quot;modification_date&quot; initially. It is non null, non empty. It will be referenced by createModifiedInFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dme5a]
 */
    public static final String MODIFICATION_DATE = "modification_date";

/**
 * <p>Represents the alias of creation_user column in the contact table. It is set to &quot;creation_user&quot; initially. It is non null, non empty. It will be referenced by createCreatedByFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dme9a]
 */
    public static final String CREATION_USER = "creation_user";

/**
 * <p>Represents the alias of modification_user column in the contact table. It is set to &quot;modification_user&quot; initially. It is non null, non empty. It will be referenced by createModifiedByFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmee9]
 */
    public static final String MODIFICATION_USER = "modification_user";

/**
 * <p>Represents the alias of email column in the contact table. It is set to &quot;email&quot; initially. It is non null, non empty. It will be referenced by createEmailAddressFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmf38]
 */
    public static final String EMAIL = "email";

/**
 * <p>Represents the alias of phone column in the contact table. It is set to &quot;phone&quot; initially. It is non null, non empty. It will be referenced by createPhoneNumberFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmf3f]
 */
    public static final String PHONE = "phone";

/**
 * <p>Represents the alias of contact_type_id column in the contact_relation table. It is set to &quot;contact_type_id&quot; initially. It is non null, non empty. It will be referenced by createTypeFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmf8e]
 */
    public static final String CONTACT_TYPE_ID = "contact_type_id";

/**
 * <p>Represents the alias of entity_id column in the contact_relation table. It is set to &quot;entity_id&quot; initially. It is non null, non empty. It will be referenced by createEntityIDFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dmf95]
 */
    public static final String ENTITY_ID = "entity_id";
/**
 * 
 * 
 * @poseidon-object-id [I62bc0b48m1111c1c5704m1d6c]
 */
    public com.topcoder.timetracker.contact.ContactType contactType;

/**
 * <p>Empty private constructor to ensure this class can't be instance.</p>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm318c]
 */
    private  ContactFilterFactory() {        
        // your code here
    } 

/**
 * <p>Create filter which will return all contacts with name that contains a given string.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return orFilter(new LikeFilter(FIRST_NAME, keyword), new LikeFilter(LAST_NAME, keyword))</p>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm3110]
 * @param keyword non null, non empty(trim'd) string which is expected in the name
 * @return non null filter
 * @exception IllegalArgumentException if the keyword is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createNameKeywordFilter(String keyword) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts created within a given inclusive date range (may be open-ended)</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If to is null, return new GreaterThanOrEqualToFilter(CREATION_DATE, from)</li>
 * <li>Else if from is null, return new LessThanOrEqualToFilter(CREATION_DATE,&nbsp; to)</li>
 * <li>Else return return new BetweenFilter(CREATION_DATE,&nbsp; to, from)</li>
 * </ol>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm30d0]
 * @param from possible null start date of the range
 * @param to possible null end date of the range
 * @return non null filter
 * @throws IllegalArgumentException if both to and from are null
 */
    public static com.topcoder.search.builder.filter.Filter createCreatedInFilter(java.util.Date from, java.util.Date to) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts modified within a given inclusive date range (may be open-ended)</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If to is null, return new GreaterThanOrEqualToFilter(MODIFICATION_DATE,&nbsp; from)</li>
 * <li>Else if from is null, return new LessThanOrEqualToFilter(MODIFICATION_DATE, to)</li>
 * <li>Else return return new BetweenFilter(MODIFICATION_DATE, to, from)</li>
 * </ol>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm3067]
 * @param from possible null start date of the range
 * @param to possible null end date of the range
 * @return non null filter
 * @throws IllegalArgumentException if both to and from are null
 */
    public static com.topcoder.search.builder.filter.Filter createModifiedInFilter(java.util.Date from, java.util.Date to) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts created by a given username.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(CREATION_USER, userName)</p>
 * <p></p>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm2ffe]
 * @param userName non null, non empty(trim'd) name of the user
 * @return non null filter
 * @throws IllegalArgumentException if the name is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createCreatedByFilter(String userName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts modified by given user.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(MODIFICATION_USER, userName)</p>
 * 
 * @poseidon-object-id [Imdbdf826m110ba0e6805mm2fbe]
 * @param userName non null, non empty(trim'd) name of the user
 * @return non null filter
 * @throws IllegalArgumentException if the name is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createModifiedByFilter(String userName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts with a given email address.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(EMAIL, emailAddress)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm27ec]
 * @param emailAddress non null, non empty(trim'd) email address of the user
 * @return non null filter
 * @throws IllegalArgumentException if the email address is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createEmailAddressFilter(String emailAddress) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts with a given phone number.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(PHONE, phoneNumber)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm27c5]
 * @param phoneNumber non null, non empty(trim'd) phone number of the user
 * @return non null filter
 * @throws IllegalArgumentException if the phone number is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createPhoneNumberFilter(String phoneNumber) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all contacts with a given type.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(CONTACT_TYPE_ID, new Long(type.getId()))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm279e]
 * @param type the non null contact type
 * @return non null filter
 * @throws IllegalArgumentException if the contact type is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createTypeFilter(com.topcoder.timetracker.contact.ContactType type) {        
        return new EqualToFilter(CONTACT_TYPE_ID, new Long(type.getId()));
    } 

/**
 * <p>Create filter which will return all contacts with a given entity id and address type.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new AndFilter(EqualToFilter(ENTITY_ID, new Long(id)), createTypeFilter(type))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm2724]
 * @param entityId the >=0 id
 * @param type the non null contact type
 * @return non null filter
 * @throws IllegalArgumentException if the id < 0 or the type is null
 */
    public static com.topcoder.search.builder.filter.Filter createEntityIDFilter(long entityId, com.topcoder.timetracker.contact.ContactType type) {        
        return new AndFilter(new EqualToFilter(ENTITY_ID, new Long(entityId)), createTypeFilter(type));
    } 

/**
 * <p>Create AND filter of given filters</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new AndFilter(first, second)</p>
 * 
 * @poseidon-object-id [Ime76d06fm110c03d35e8mmb64]
 * @param first non null first orginal filter
 * @param second non null second orginal filter
 * @return non null and filter of given filters
 * @throws IllegalArgumentException if any filter is null
 */
    public static com.topcoder.search.builder.filter.Filter andFilter(com.topcoder.search.builder.filter.Filter first, com.topcoder.search.builder.filter.Filter second) {        
        // your code here
        return null;
    } 

/**
 * <p>Create OR filter of given filters</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new OrFilter(first, second)</p>
 * <p></p>
 * <p></p>
 * 
 * @poseidon-object-id [Ime76d06fm110c03d35e8mmb3f]
 * @param first non null first orginal filter
 * @param second non null second orginal filter
 * @return non null or filter of given filters
 * @throws IllegalArgumentException if any filter is null
 */
    public static com.topcoder.search.builder.filter.Filter orFilter(com.topcoder.search.builder.filter.Filter first, com.topcoder.search.builder.filter.Filter second) {        
        // your code here
        return null;
    } 

/**
 * <p>Create NOT filter of given filter</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new NotFilter(filter)</p>
 * <p></p>
 * 
 * @poseidon-object-id [Ime76d06fm110c03d35e8mmb1a]
 * @param filter non null orginal filter
 * @return non null filter which is the not filter of the orginal one
 * @throws IllegalArgumentException if the filter is null
 */
    public static com.topcoder.search.builder.filter.Filter notFilter(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 
 }
