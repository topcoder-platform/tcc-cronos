
package com.topcoder.timetracker.common;

/**
 * <strong>Usage:</strong> This exception is thrown by PaymentTermDAO to indicate that a PaymenTerm already exists when one was not expected.&nbsp;This method only really is thrown from a call to <em>addPaymentTerm().</em>&nbsp;
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>PaymenTermDAOException.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> Exceptions are thread-safe.</p>
 * 
 */
public class DuplicatePaymentTermException extends com.topcoder.timetracker.common.PaymentTermDAOException {

/**
 * <p><strong>Usage: </strong>The PaymentTerm that the DAO was trying to add but this already exist in the persistence. Initialized in&nbsp; the constructor and never changed afterwards. Accessed thru<em> getDupplicatePaymentTerm </em>method.</p>
 * <p><strong>Valid values:</strong> can not be &lt;=0.</p>
 * 
 */
    private final com.topcoder.timetracker.common.PaymentTerm duplicatePaymentTerm;

/**
 * <p><strong>Usage:</strong> Constructor with error message and&nbsp; dupplicate PaymentTerm.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>
 * <p><em>super(message);</em></p>
 * </li>
 * <li>
 * <p><em>this.duplicatePaymentTerm = duplicatePaymentTerm;</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param message the error message
 * @param duplicatePaymentTerm the PaymentTerm that the DAO was trying to add but this already exist in the persistence
 * @throws IllegalArgumentException if duplicatePaymentTerm is null
 */
    public  DuplicatePaymentTermException(String message, PaymentTerm duplicatePaymentTerm) {        
        super(message);
        this.duplicatePaymentTerm = duplicatePaymentTerm;
    } 

/**
 * <p><strong>Usage:</strong> Retrieves the PaymentTerm that the DAO was trying to add but this already exist in the persistence.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.duplicatePaymentTerm;</em></li>
 * </ul>
 * 
 * 
 * @return the PaymentTerm that the DAO was trying to add but this already exist in the persistence
 */
    public com.topcoder.timetracker.common.PaymentTerm getDuplicatePaymentTerm() {        
        // your code here
        return null;
    } 
 }
