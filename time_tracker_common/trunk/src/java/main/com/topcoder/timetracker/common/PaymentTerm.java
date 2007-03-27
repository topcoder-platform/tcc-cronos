
package com.topcoder.timetracker.common;

/**
 * <p><strong>Usage: </strong>This bean represents the Payment Terms that are used on an invoice to determine the payment agreement for that invoice. Clients and projects have default payment terms associated with their records. The payment term extends TimeTrackerBean and has the following attributes: term, description, active.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>TimeTrackerBean.</li>
 * </ul>
 * <p><strong>Thread Safety:</strong> This class is mutable, and not thread-safe. Multiple threads are advised to work with their own instance.</p>
 * 
 */
public class PaymentTerm extends TimeTrackerBean {

/**
 * <p><strong>Usage:</strong> Represents the actual number of days before a payment is due. It may be -1 when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. Initialized in: setTerm, modified in: setTerm, accessed in: getTerm.</p>
 * <p><strong>Valid values:</strong> can not be &lt;=0.</p>
 * 
 */
    private int term = -1;

/**
 * <p><strong>Usage:</strong> Represent the description which describes the number of days allowed before payment is due. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. Initialized in: setDescription, modified in: setDescription, accessed in: getDescription.</p>
 * <p><strong>Valid values:</strong> can not be null, empty string or length &gt;64.</p>
 * 
 */
    private String description = null;

/**
 * <p><strong>Usage: </strong>Represent the flag used by application to delete terms which are no longer valid, a setting of false will remove it from the active list. Initialized in: declaration, modified in: setActive, accessed in: isActive.</p>
 * 
 */
    private boolean active = false;

/**
 * <p><strong>Usage: </strong>Default constructor.</p>
 * <p><strong>Implementation notes: </strong></p>
 * <ul type="disc">
 * <li>does nothing.</li>
 * </ul>
 * 
 */
    public  PaymentTerm() {        
        // your code here
    } 

/**
 * <p><strong>Usage:</strong> Retrieves the actual number of days before a payment is due.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.term;</em></li>
 * </ul>
 * 
 * 
 * @return the actual number of days before a payment is due
 */
    public int getTerm() {        
        return term;
    } 

/**
 * <p><strong>Usage:</strong> Set the actual number of days before a payment is due. Manager should check the attribute and should throw IllegalArgumentException if term is &lt;=0.</p>
 * <p>I<strong>mplementation Notes: </strong></p>
 * <p>If the current value that was added is not equal to the previous value, then call <em>setChanged(true)</em>.</p>
 * <ul type="disc">
 * <li><em>if (term != this.term) {</em></li>
 * <li><em>&nbsp;&nbsp;&nbsp; this.term = term;</em></li>
 * <li><em>&nbsp;&nbsp;&nbsp; setChanged(true);</em></li>
 * <li><em>}</em></li>
 * </ul>
 * 
 * 
 * @param term the actual number of days before a payment is due
 */
    public void setTerm(int term) {        
       // Utils.checkPositive(companyId, "companyId");
        if (term != this.term) {
            this.term = term;
            setChanged(true);
        }
    } 

/**
 * <p><strong>Usage:</strong> Retrieves the description which describes the number of days allowed before payment is due.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.description;</em></li>
 * </ul>
 * 
 * 
 * @return the description which describes the number of days allowed before payment is due
 */
    public String getDescription() {        
        return description;
    } 

/**
 * <p><strong>Usage:</strong> Set the description which describes the number of days allowed before payment is due. Manager should check the attribute and should throw IllegalArgumentException if description is null, empty string or description.length()&gt;64.</p>
 * <p>I<strong>mplementation Notes: </strong></p>
 * <p>If the current value that was added is not equal to the previous value, then call <em>setChanged(true)</em>.</p>
 * <ul type="disc">
 * <li><em>if (!description.equals(this.description)) {</em></li>
 * <li><em>&nbsp;&nbsp;&nbsp; this.description = description;</em></li>
 * <li><em>&nbsp;&nbsp;&nbsp; setChanged(true);</em></li>
 * <li><em>}</em></li>
 * </ul>
 * 
 * 
 * @param description the description which describes the number of days allowed before payment is due
 */
    public void setDescription(String description) {        
        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    } 

/**
 * <p><strong>Usage: </strong>Checks the flag used by application to delete terms which are no longer valid.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.active;</em></li>
 * </ul>
 * 
 * 
 * @return whether the PaymentTerm is active or not
 */
    public boolean isActive() {        
        return active;
    } 

/**
 * <p><strong>Usage: </strong>Sets &nbsp;the flag used by application to delete terms which are no longer valid.</p>
 * <p><strong>Implementation Notes: </strong></p>
 * <p>If the current value that was added is not equal to the previous value, then call <em>setChanged(true)</em>.</p>
 * <ul type="disc">
 * <li>
 * <p><em>if (active != this.active) {</em></p>
 * </li>
 * <li>
 * <p><em>&nbsp;&nbsp;&nbsp; this.active = active;</em></p>
 * </li>
 * <li>
 * <p><em>&nbsp;&nbsp;&nbsp; setChanged(true);</em></p>
 * </li>
 * <li>
 * <p><em>}</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param active true if the PaymentTerm is active; false otherwise
 */
    public void setActive(boolean active) {        
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    } 
 }
