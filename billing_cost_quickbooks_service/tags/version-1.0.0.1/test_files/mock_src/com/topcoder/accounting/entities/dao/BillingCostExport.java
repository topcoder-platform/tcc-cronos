package com.topcoder.accounting.entities.dao;
import java.util.*;
import com.topcoder.accounting.entities.dao.*;
import java.lang.*;
/**
 * #### Purpose
 * This class represents a record of an export of billing costs
 * 
 * This class provides a method that serializes the entity contents into a JSON string.
 * 
 * #### Thread Safety
 * This class is mutable and not thread safe
*/
public class BillingCostExport extends IdentifiableEntity {
/**
 * #### Purpose
 * Represents the name of the accountant who performed the export
 * 
 * #### Usage
 * it is managed with a getter and setter
 * 
 * #### Legal Values
 * It may have any value
 * 
 * #### Mutability
 * It is fully mutable
*/
private String accountantName;
/**
 * #### Purpose
 * Represents the payment area
 * 
 * #### Usage
 * it is managed with a getter and setter
 * 
 * #### Legal Values
 * It may have any value
 * 
 * #### Mutability
 * It is fully mutable
*/
private PaymentArea paymentArea;
/**
 * #### Purpose
 * Represents the number of records exported as a batch
 * 
 * #### Usage
 * it is managed with a getter and setter
 * 
 * #### Legal Values
 * It may have any value, but is expected to be a positive number
 * 
 * #### Mutability
 * It is fully mutable
*/
private int recordsCount;
/**
 * #### Purpose
 * Represents the date and time at which the export was performed.
 * 
 * #### Usage
 * it is managed with a getter and setter
 * 
 * #### Legal Values
 * It may have any value
 * 
 * #### Mutability
 * It is fully mutable
*/
private Date timestamp;
/**
 * #### Purpose
 * Empty constructor
*/
public BillingCostExport() {
}

/**
 * <p>
 * Getter method for the accountantName.
 * </p>
 * @return the accountantName
 */
public String getAccountantName() {
    return accountantName;
}

/**
 * <p>
 * Setter method for the accountantName.
 * </p>
 * @param accountantName the accountantName to set
 */
public void setAccountantName(String accountantName) {
    this.accountantName = accountantName;
}

/**
 * <p>
 * Getter method for the paymentArea.
 * </p>
 * @return the paymentArea
 */
public PaymentArea getPaymentArea() {
    return paymentArea;
}

/**
 * <p>
 * Setter method for the paymentArea.
 * </p>
 * @param paymentArea the paymentArea to set
 */
public void setPaymentArea(PaymentArea paymentArea) {
    this.paymentArea = paymentArea;
}

/**
 * <p>
 * Getter method for the recordsCount.
 * </p>
 * @return the recordsCount
 */
public int getRecordsCount() {
    return recordsCount;
}

/**
 * <p>
 * Setter method for the recordsCount.
 * </p>
 * @param recordsCount the recordsCount to set
 */
public void setRecordsCount(int recordsCount) {
    this.recordsCount = recordsCount;
}

/**
 * <p>
 * Getter method for the timestamp.
 * </p>
 * @return the timestamp
 */
public Date getTimestamp() {
    return timestamp;
}

/**
 * <p>
 * Setter method for the timestamp.
 * </p>
 * @param timestamp the timestamp to set
 */
public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
}

/**
 * #### Purpose
 * Provides the JSON representation of the contents of this entity.
 * 
 * #### Parameters
 * return - the JSON representation of the contents of this entity.
 * 
 * #### Implementation Notes
 * See CS 1.3.2 for details
 * @param Return 
 * @return 
*/
public String toJSONString() {
    throw new UnsupportedOperationException("Not implemented in mock");
}
}

