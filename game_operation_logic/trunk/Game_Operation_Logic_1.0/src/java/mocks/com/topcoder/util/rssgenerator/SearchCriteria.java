package com.topcoder.util.rssgenerator;

/**
 * Purpose: This interface gives the contract for search criteria while
 * searching for feeds or items. This interface simply acts as a place holder
 * for the Filter interface of the Search Builder component which allows us to
 * utilize the powerful search capabilities of that component directly. The
 * reason for the presence of this interface instead of direct usage of the
 * Filter interface is due to the requirement specification which requires this
 * interface.
 * <p>
 * Implementations: Implementations need only act as a placeholder for a Filter
 * instance. This can be simply done by holding a member variable.
 * </p>
 * <p>
 * Thread Safety: Implementations are required to be thread safe.
 * </p>
 * 
 */
public interface SearchCriteria {

	/**
	 * <p>
	 * Purpose: This variable represents the String name of the alias to be used
	 * while building search filters to search based on the ID property of feeds
	 * or items. Any SearchCriteria implementation must map this alias to the
	 * appropriate field name in its persistence format. It is frozen and static
	 * as it will not change and is common to all instances of implementations
	 * of this interface.
	 * </p>
	 * 
	 * 
	 */
	public static final String ID = "id";

	/**
	 * <p>
	 * Purpose: This variable represents the String name of the alias to be used
	 * while building search filters to search based on the category name of
	 * feeds or items. Any SearchCriteria implementation must map this alias to
	 * the appropriate field name in its persistence format. It is frozen and
	 * static as it will not change and is common to all instances of
	 * implementations of this interface
	 * </p>
	 * 
	 * 
	 */
	public static final String CATEGORY = "category";

	/**
	 * <p>
	 * Purpose: This variable represents the String name of the alias to be used
	 * while building search filters to search based on the published date of
	 * feeds or items. Any SearchCriteria implementation must map this alias to
	 * the appropriate field name in its persistence format. It is frozen and
	 * static as it will not change and is common to all instances of
	 * implementations of this interface
	 * </p>
	 * 
	 * 
	 */
	public static final String PUBLISHED_DATE = "publishedDate";

	/**
	 * <p>
	 * Purpose: This variable represents the String name of the alias to be used
	 * while building search filters to search based on the updated date of
	 * feeds or items. Any SearchCriteria implementation must map this alias to
	 * the appropriate field name in its persistence format. It is frozen and
	 * static as it will not change and is common to all instances of
	 * implementations of this interface
	 * </p>
	 * 
	 * 
	 */
	public static final String UPDATED_DATE = "updatedDate";

	/**
	 * <p>
	 * Purpose: This variable represents the String name of the alias to be used
	 * while building search filters to search based on the author of feeds or
	 * items. Any SearchCriteria implementation must map this alias to the
	 * appropriate field name in its persistence format. It is frozen and static
	 * as it will not change and is common to all instances of implementations
	 * of this interface
	 * </p>
	 * 
	 * 
	 */
	public static final String AUTHOR = "author";

	/**
	 * <p>
	 * Purpose: This method returns the Filter which represents this search
	 * criteria.
	 * </p>
	 * <p>
	 * Args: None.
	 * </p>
	 * <p>
	 * Returns: The Filter which represents this search criteria.
	 * </p>
	 * <p>
	 * Exceptions: None.
	 * </p>
	 * 
	 * 
	 * @return
	 */
	public com.topcoder.search.builder.filter.Filter getSearchFilter();

}
