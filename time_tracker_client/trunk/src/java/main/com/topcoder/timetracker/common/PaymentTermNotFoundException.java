
package com.topcoder.timetracker.common;

/**
 * <p><strong>Usage: </strong>This exception is thrown if the involved PaymentTerm was not found in the persistence. This is thrown during update/delete/retrieve methods.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>PaymenTermDAOException.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> Exceptions are thread-safe.</p>
 * 
 */
public class PaymentTermNotFoundException extends PaymentTermDAOException {

/**
 * <p><strong>Usage: </strong>The ID of the PaymentTerm that the DAO was working with when a problem occurred. Initialized in&nbsp; the constructor and never changed afterwards. Accessed thru<em> getProblemPaymentTermID </em>method.</p>
 * <p><strong>Valid values:</strong> can not be &lt;=0.</p>
 * 
 */
    private final long problemPaymentTermID;

/**
 * <p><strong>Usage:</strong> Constructor with error message and ID of problem PaymentTerm.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>
 * <p><em>super(message);</em></p>
 * </li>
 * <li>
 * <p><em>this.problemPaymentTermID = problemPaymentTermID;</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param message the error message
 * @param problemPaymentTermID the ID of the PaymentTerm that the DAO was working with when a problem occurred
 * @throws IllegalArgumentException if problemPaymentTerm ID <=0
 */
    public  PaymentTermNotFoundException(String message, long problemPaymentTermID) {        
        super(message);
        this.problemPaymentTermID = problemPaymentTermID;
    } 

/**
 * <p><strong>Usage:</strong> Retrieves the ID of the PaymentTerm that the DAO was working with when a problem occurred.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.problemPaymentTermID;</em></li>
 * </ul>
 * 
 * 
 * @return the ID of the PaymentTerm that the DAO was working with when a problem occurred
 */
    public long getProblemPaymentTermID() {        
        // your code here
        return 0;
    } 
 }
