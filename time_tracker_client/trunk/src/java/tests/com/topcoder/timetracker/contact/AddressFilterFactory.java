
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;

/**
 * This class is used to create pre-defined filters or AND/OR/NOT filters which are used to search addresses.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>This class can't be created. The application can create filters by its static methods. The column names used in this factory are aliases. The SearchBundle will convert the aliases to the actual names defined in the SearchBundle configuration files.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable.</p>
 * <ID="UML note. note-id=[I62bc0b48m1111c1c5704m1da3] ">AddressFilterFactory uses AddressType, state and country
 * <BR></ID>
 * @poseidon-object-id [I4887f14em110e3d467dfmdb5]
 */
public class AddressFilterFactory {

/**
 * <p>Represents the alias of creation_date column in the address table. It is set to &quot;creation_date&quot; initially. It is non null, non empty. It will be referenced by createCreatedInFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm10b3]
 */
    public static final String CREATION_DATE = "creation_date";

/**
 * <p>Represents the alias of modification_user column in the address table. It is set to &quot;modification_user&quot; initially. It is non null, non empty. It will be referenced by createModifiedByFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm10ba]
 */
    public static final String MODIFICATION_USER = "modification_user";

/**
 * <p>Represents the alias of modification_date column in the address table. It is set to &quot;modification_date&quot; initially. It is non null, non empty. It will be referenced by createModifiedInFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm10c1]
 */
    public static final String MODIFICATION_DATE = "modification_date";

/**
 * <p>Represents the alias of creation_user column in the address table. It is set to &quot;creation_user&quot; initially. It is non null, non empty. It will be referenced by createCreatedByFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm10c8]
 */
    public static final String CREATION_USER = "creation_user";

/**
 * <p>Represents the alias of city column in the address table. It is set to &quot;city&quot; initially. It is non null, non empty. It will be referenced by createCityFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm112f]
 */
    public static final String CITY = "city";

/**
 * <p>Represents the alias of state_name_id column in the address table. It is set to &quot;state_name_id&quot; initially. It is non null, non empty. It will be referenced by createStateIDFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm117e]
 */
    public static final String STATE_NAME_ID = "state_name_id";

/**
 * <p>Represents the alias of zip_code column in the address table. It is set to &quot;zip_code&quot; initially. It is non null, non empty. It will be referenced by createZipCodeFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm1185]
 */
    public static final String ZIP_CODE = "zip_code";

/**
 * <p>Represents the alias of country_name_id column in the address table. It is set to &quot;country_name_id&quot; initially. It is non null, non empty. It will be referenced by createCountryIDFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm11d4]
 */
    public static final String COUNTRY_NAME_ID = "country_name_id";

/**
 * <p>Represents the alias of address_type_id column in the address_relation table. It is set to &quot;address_type_id&quot; initially. It is non null, non empty. It will be referenced by createTypeFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm11db]
 */
    public static final String ADDRESS_TYPE_ID = "address_type_id";

/**
 * <p>Represents the alias of entity_id column in the address_relation table. It is set to &quot;entity_id&quot; initially. It is non null, non empty. It will be referenced by createEntityIDFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm1272]
 */
    public static final String ENTITY_ID = "entity_id";

/**
 * <p>Represents the alias of name column in the country_name table. It is set to &quot;country_name&quot; initially. It is non null, non empty. It will be referenced by createCountryNameFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm1279]
 */
    public static final String COUNTRY_NAME = "country_name";

/**
 * <p>Represents the alias of name column in the state_name table. It is set to &quot;state_name&quot; initially. It is non null, non empty. It will be referenced by createStateNameFilter method.</p>
 * 
 * @poseidon-object-id [I1fbfd5e4m1112f0b1b9dm130b]
 */
    public static final String STATE_NAME = "state_name";

/**
 * <p>Empty private constructor to ensure this class can't be instance.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmdb3]
 */
    private  AddressFilterFactory() {        
        // your code here
    } 

/**
 * <p>Create filter which will return all addresses created within a given inclusive date range (may be open-ended)</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If to is null, return new GreaterThanOrEqualToFilter(&quot;CREATION_DATE&quot;, from)</li>
 * <li>Else if from is null, return new GreaterThanOrEqualToFilter(&quot;CREATION_DATE&quot;, to)</li>
 * <li>Else return return new BetweenFilter(&quot;CREATION_DATE&quot;, to, from)</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmda6]
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
 * <p>Create filter which will return all addresses modified within a given inclusive date range (may be open-ended)</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If to is null, return new GreaterThanOrEqualToFilter(&quot;MODIFICATION_DATE&quot;, from)</li>
 * <li>Else if from is null, return new LessThanOrEqualToFilter(&quot;MODIFICATION_DATE&quot;, to)</li>
 * <li>Else return return new BetweenFilter(&quot;MODIFICATION_DATE&quot;, to, from)</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd9b]
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
 * <p>Create filter which will return all addresses created by a given username.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;CREATION_USER&quot;, userName)</p>
 * <p></p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd90]
 * @param userName non null, non empty(trim'd) name of the user
 * @return non null filter
 * @throws IllegalArgumentException if the name is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createCreatedByFilter(String userName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addresses modified by given user.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;MODIFICATION_USER&quot;, userName)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd87]
 * @param userName non null, non empty(trim'd) name of the user
 * @return non null filter
 * @throws IllegalArgumentException if the name is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createModifiedByFilter(String userName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addresses with a given city.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;CITY&quot;, city)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd7e]
 * @param city non null, non empty(trim'd) city of the user
 * @return non null filter
 * @throws IllegalArgumentException if the city is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createCityFilter(String city) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addressed with a given ID of state.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;STATE_NAME_ID&quot;, new Long(stateId))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd75]
 * @param stateId >0 id of state
 * @return non null filter
 * @throws IllegalArgumentException if the stateId <=0
 */
    public static com.topcoder.search.builder.filter.Filter createStateIDFilter(long stateId) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addresses with a given zip code</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new LikeFilter(&quot;ZIP_CODE&quot;, zipCode)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmdaf]
 * @param zipCode non null, non empty(trim'd) zip code
 * @return non null filter
 * @exception IllegalArgumentException if the zipCode is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createZipCodeFilter(String zipCode) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addresses with a given ID of country.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;COUNTRY_NAME_ID&quot;, new Long(countryId))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm2207]
 * @param countryId >0 id of the country
 * @return non null filter
 * @throws IllegalArgumentException if the countryId <= 0
 */
    public static com.topcoder.search.builder.filter.Filter createCountryIDFilter(long countryId) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addresses with a given type.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;ADDRESS_TYPE_ID&quot;, new Long(type.getId()))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd6c]
 * @param type the non null address type
 * @return non null filter
 * @throws IllegalArgumentException if the address type is null/empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createTypeFilter(AddressType type) {        
        return new EqualToFilter(ADDRESS_TYPE_ID, new Long(type.getId()));
    } 

/**
 * <p>Create filter which will return all addresses with a given entity id and address type.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new AndFilter(EqualToFilter(&quot;ENTITY_ID&quot;, new Long(id)), createTypeFilter(type))</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd63]
 * @param entityId the >=0 id
 * @param type non null type
 * @return non null filter
 * @throws IllegalArgumentException if the id < 0 or type is null
 */
    public static com.topcoder.search.builder.filter.Filter createEntityIDFilter(long entityId, AddressType type) {        
        return new AndFilter(new EqualToFilter(ENTITY_ID, new Long(entityId)), createTypeFilter(type));
    } 

/**
 * <p>Create filter which will return all addresses with a given name of country.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;COUNTRY_NAME&quot;, countryName)</p>
 * 
 * @poseidon-object-id [I3ef6073cm11126bc4bacmm25fe]
 * @param countryName non null. non empty name of country
 * @return non null filter
 * @throws IllegalArgumentException if the countryName is null or empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createCountryNameFilter(String countryName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create filter which will return all addressed with a given name of state.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new EqualToFilter(&quot;STATE_NAME&quot;, stateName)</p>
 * 
 * @poseidon-object-id [I3ef6073cm11126bc4bacmm25f5]
 * @param stateName non null, non empty(trim'd) name of state
 * @return non null filter
 * @throws IllegalArgumentException if the stateName is null or empty(trim'd)
 */
    public static com.topcoder.search.builder.filter.Filter createStateNameFilter(String stateName) {        
        // your code here
        return null;
    } 

/**
 * <p>Create AND filter of given filters</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return new AndFilter(first, second)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmd5c]
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
 * @poseidon-object-id [I4887f14em110e3d467dfmd51]
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
 * @poseidon-object-id [I4887f14em110e3d467dfmd46]
 * @param filter non null orginal filter
 * @return non null filter which is the not filter of the orginal one
 * @throws IllegalArgumentException if the filter is null
 */
    public static com.topcoder.search.builder.filter.Filter notFilter(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 
 }
