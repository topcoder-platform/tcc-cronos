package com.topcoder.accounting.entities.dto;

import java.io.Serializable;
import java.lang.*;

/**
 * #### Purpose This class represents an update to the export details This class provides a method that serializes the
 * entity contents into a JSON string. #### Thread Safety This class is mutable and not thread safe
 */
public class QuickBooksImportUpdate implements Serializable {
    /**
     * #### Purpose Represents the IDs of the export details records that are to be updated #### Usage it is managed
     * with a getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private long[] billingCostExportDetailIds;
    /**
     * #### Purpose Represents the invoice number to be set in the records #### Usage it is managed with a getter and
     * setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private String invoiceNumber;

    /**
     * #### Purpose Empty constructor
     */
    public QuickBooksImportUpdate() {
    }

    /**
     * <p>
     * Getter method for the billingCostExportDetailIds.
     * </p>
     * @return the billingCostExportDetailIds
     */
    public long[] getBillingCostExportDetailIds() {
        return billingCostExportDetailIds;
    }

    /**
     * <p>
     * Setter method for the billingCostExportDetailIds.
     * </p>
     * @param billingCostExportDetailIds the billingCostExportDetailIds to set
     */
    public void setBillingCostExportDetailIds(long[] billingCostExportDetailIds) {
        this.billingCostExportDetailIds = billingCostExportDetailIds;
    }

    /**
     * <p>
     * Getter method for the invoiceNumber.
     * </p>
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>
     * Setter method for the invoiceNumber.
     * </p>
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * #### Purpose Provides the JSON representation of the contents of this entity. #### Parameters return - the JSON
     * representation of the contents of this entity. #### Implementation Notes See CS 1.3.2 for details
     * @param Return
     * @return
     */
    public String toJSONString() {
        throw new UnsupportedOperationException("Not implemented in mock.");
    }
}
