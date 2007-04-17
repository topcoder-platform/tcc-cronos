
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to
 * access the various properties of a Time Tracker Project, such as the project name, timeline, 
 * contact information, and address.
 * </p>
 * <p>
 * It also extends from the base TimeTrackerBean to include the  creation and modification details.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7e88]
 */
public class Project extends TimeTrackerBean {

/**
 * <p>
 * This is the description of a project, which is a human-readable String that briefly 
 * describes the project.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getDescription
 * </p>
 * <p>
 * Modified In: setDescription
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7d29]
 */
    private String description;

/**
 * <p>
 * This is the name of the project.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getName
 * </p>
 * <p>
 * Modified In: setName
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null values, or Strings that are not empty
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7d17]
 */
    private String name;

/**
 * <p>
 * This is the date when the project started, or is estimated to start.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getStartDate
 * </p>
 * <p>
 * Modified In: setStartDate
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null values, or a Date object
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7d06]
 */
    private java.util.Date startDate;

/**
 * <p>
 * This is the date when the project ended, or is estimated to end.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getEndDate
 * </p>
 * <p>
 * Modified In: setEndDate
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null values, or a Date object
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7cf5]
 */
    private java.util.Date endDate;

/**
 * <p>
 * This is a number that uniquely identifies the company that this project is associated with.  This
 * is the company that owns the project.  This is used to tie into the Time Tracker Company
 * component.
 * </p>
 * <p>
 * Initial Value: 0 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getCompanyId
 * </p>
 * <p>
 * Modified In: setCompanyId
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Ids that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7ce4]
 */
    private long companyId;

/**
 * <p>
 * This is the relevant sales tax that may be applied for any invoices provided for the project.
 * </p>
 * <p>
 * Initial Value: 0 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getSalesTax
 * </p>
 * <p>
 * Modified In: setSalesTax
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: 0, or values that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7cd1]
 */
    private double salesTax;

/**
 * <p>
 * This is the contact information that may be used in order to get in touch with someone related
 * with the project.  For more information on the Contact class, see the Time Tracker Contact 
 * component.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getContact
 * </p>
 * <p>
 * Modified In: setContact
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null, or a Contact object
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7d18]
 */
    private com.topcoder.timetracker.contact.Contact contact;

/**
 * <p>
 * This is the Address, representing the location where this project is based.  For more information on the Address class, 
 * see the Time Tracker Contact component.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getAddress
 * </p>
 * <p>
 * Modified In: setAddress
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null, or an Address object
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7cae]
 */
    private com.topcoder.timetracker.contact.Address address;

/**
 * <p>
 * This is an identifier that is used to represent the client for the project.  This is the entity that has
 * contracted the company involved to perform work on the project.  This is used to tie into the 
 * Time Tracker Client component.
 * </p>
 * <p>
 * Initial Value:0 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getClientId
 * </p>
 * <p>
 * Modified In: setClientId
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Ids that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7cf6]
 */
    private long clientId;

/**
 * <p>
 * This indicates whether the Project is currently active or inactive.  It defaults to "false".
 * </p>
 * <p>
 * Initial Value: false
 * </p>
 * <p>
 * Accessed In: getActive, isActive
 * </p>
 * <p>
 * Modified In: setActive
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Valid boolean values (true/false)
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm7666]
 */
    private boolean active;

/**
 * <p>
 * This represents the Terms of payment for this project.  For more information on the PaymentTerm,
 * see the Time Tracker Commons project.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getTerms
 * </p>
 * <p>
 * Modified In: setTerms
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null, or a PaymentTerm object
 * </p>
 * 
 * @poseidon-object-id [Im12955c6cm110be84a1c6mm6dd6]
 */
    private com.topcoder.timetracker.common.PaymentTerm terms;

/**
 * <p>
 * Default Constructor.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7e6c]
 */
    public  Project() {        
        // your code here
    } 

/**
 * <p>
 * Retrieves the description of a project, which is a human-readable String that briefly
 * describes the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm60e4]
 * @return the description of a project.
 */
    public String getDescription() {        
        return description;
    } 

/**
 * <p>
 * Sets the description of a project, which is a human-readable String that briefly
 * describes the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm60c9]
 * @param description the description of a project.
 * @throws IllegalArgumentException if description is null or an empty String.
 */
    public void setDescription(String description) {        
        this.description = description;
    } 

/**
 * <p>
 * Retrieves the name of the project.
 * </p>
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm60a5]
 * @return the name of the project.
 */
    public String getName() {        
        return name;
    } 

/**
 * <p>
 * Sets the name of the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm608a]
 * @param name the name of the project.
 * @throws IllegalArgumentException if name is null or an empty String.
 */
    public void setName(String name) {        
        this.name = name;
    } 

/**
 * <p>
 * Retrieves the date when the project started, or is estimated to start.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm6066]
 * @return the date when the project started, or is estimated to start.
 */
    public java.util.Date getStartDate() {        
        return startDate;
    } 

/**
 * <p>
 * Sets the date when the project started, or is estimated to start.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm604b]
 * @param startDate the date when the project started, or is estimated to start.
 * @throws IllegalArgumentException if startDate is null.
 */
    public void setStartDate(java.util.Date startDate) {        
        this.startDate = startDate;
    } 

/**
 * <p>
 * Retrieves the date when the project ended, or is estimated to end.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm6027]
 * @return the date when the project ended, or is estimated to end.
 */
    public java.util.Date getEndDate() {        
        return endDate;
    } 

/**
 * <p>
 * Sets the date when the project ended, or is estimated to end.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm600c]
 * @param endDate the date when the project ended, or is estimated to end.
 * @throws IllegalArgumentException if endDate is null.
 */
    public void setEndDate(java.util.Date endDate) {        
        this.endDate = endDate;
    } 

/**
 * <p>
 * Retrieves a number that uniquely identifies the company that this project is associated with.  This
 * is the company that owns the project.  This is used to tie into the Time Tracker Company
 * component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5fe8]
 * @return a number that uniquely identifies the company that this project is associated with.
 */
    public long getCompanyId() {        
        return companyId;
    } 

/**
 * <p>
 * Sets a number that uniquely identifies the company that this project is associated with.  This
 * is the company that owns the project.  This is used to tie into the Time Tracker Company
 * component.
 * </p>
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5fcd]
 * @param companyId a number that uniquely identifies the company that this project is associated with.
 * @throws IllegalArgumentException if companyId is a negative number or 0.
 */
    public void setCompanyId(long companyId) {        
        this.companyId = companyId;
    } 

/**
 * <p>
 * Retrieves the relevant sales tax that may be applied for any invoices provided for the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5fa9]
 * @return the relevant sales tax that may be applied for any invoices provided for the project.
 */
    public double getSalesTax() {        
        return salesTax;
    } 

/**
 * <p>
 * Sets the relevant sales tax that may be applied for any invoices provided for the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5f8e]
 * @param salesTax the relevant sales tax that may be applied for any invoices provided for the project.
 * @throws IllegalArgumentException if salesTax is a negative number.
 */
    public void setSalesTax(double salesTax) {        
        this.salesTax = salesTax;
    } 

/**
 * <p>
 * Retrieves the contact information that may be used in order to get in touch with someone related
 * with the project.  For more information on the Contact class, see the Time Tracker Contact
 * component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5f6a]
 * @return the contact information for the project.
 */
    public com.topcoder.timetracker.contact.Contact getContact() {        
        return contact;
    } 

/**
 * <p>
 * Sets the contact information that may be used in order to get in touch with someone related
 * with the project.  For more information on the Contact class, see the Time Tracker Contact
 * component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5f4f]
 * @param contact the contact information for the project.
 * @throws IllegalArgumentException if contact is null.
 */
    public void setContact(com.topcoder.timetracker.contact.Contact contact) {        
        this.contact = contact;
    } 

/**
 * <p>
 * Gets the Address, representing the location where this project is based.  For more information on the Address class,
 * see the Time Tracker Contact component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5f2b]
 * @return The Address of the project.
 */
    public com.topcoder.timetracker.contact.Address getAddress() {        
        return address;
    } 

/**
 * <p>
 * Sets the Address, representing the location where this project is based.  For more information on the Address class,
 * see the Time Tracker Contact component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5f10]
 * @param address The Address of the project.
 * @throws IllegalArgumentException if address is null.
 */
    public void setAddress(com.topcoder.timetracker.contact.Address address) {        
        this.address = address;
    } 

/**
 * <p>
 * Gets an identifier that is used to represent the client for the project.  This is the entity that has
 * contracted the company involved to perform work on the project.  This is used to tie into the
 * Time Tracker Client component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5eec]
 * @return The identifier for the client associated with this project,
 */
    public long getClientId() {        
        return clientId;
    } 

/**
 * <p>
 * Sets an identifier that is used to represent the client for the project.  This is the entity that has
 * contracted the company involved to perform work on the project.  This is used to tie into the
 * Time Tracker Client component.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5ed1]
 * @param clientId The identifier for the client associated with this project,
 * @throws IllegalArgumentException if clientId is <= 0.
 */
    public void setClientId(long clientId) {        
        this.clientId = clientId;
    } 

/**
 * <p>
 * This indicates whether the Project is currently active or inactive.  It defaults to "false".
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5ead]
 * @return whether the Project is currently active or inactive.
 */
    public boolean isActive() {        
        return active;
    } 

/**
 * <p>
 * Sets whether the Project is currently active or inactive.  It defaults to "false".
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5e92]
 * @param active whether the Project is currently active or inactive.
 */
    public void setActive(boolean active) {        
        this.active = active;
    } 

/**
 * <p>
 * Gets the Terms of payment for this project.  For more information on the PaymentTerm,
 * see the Time Tracker Commons project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im3f7cca1bm110bffc04b6mm5e6e]
 * @return The terms of payment for the project.
 */
    public com.topcoder.timetracker.common.PaymentTerm getTerms() {        
        return terms;
    } 

/**
 * <p>
 * Sets the Terms of payment for this project.  For more information on the PaymentTerm,
 * see the Time Tracker Commons project.
 * </p>
 * 
 * 
 * @param terms the Terms of payment for this project.
 * @throws IllegalArgumentException if terms is null.
 */
    public void setTerms(com.topcoder.timetracker.common.PaymentTerm terms) {        
        this.terms = terms;
    } 

/**
 * <p>
 * This indicates whether the Project is currently active or inactive.  It defaults to "false".
 * </p>
 * 
 * @return whether the Project is currently active or inactive.
 */
    public boolean getActive() {        
        return this.active;
    } 
 }
