package com.topcoder.accounting.entities.dao;
import java.lang.*;
/**
 * #### Purpose
 * This class represents the category of payment (Studio, Software Costs etc.)
 * 
 * This class provides a method that serializes the entity contents into a JSON string.
 * 
 * #### Thread Safety
 * This class is mutable and not thread safe
*/
public class PaymentArea extends IdentifiableEntity {
/**
 * #### Purpose
 * Represents the name of the payment category
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
private String name;
/**
 * #### Purpose
 * Empty constructor
*/
public PaymentArea() {
}

/**
 * <p>
 * Getter method for the name.
 * </p>
 * @return the name
 */
public String getName() {
    return name;
}

/**
 * <p>
 * Setter method for the name.
 * </p>
 * @param name the name to set
 */
public void setName(String name) {
    this.name = name;
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

