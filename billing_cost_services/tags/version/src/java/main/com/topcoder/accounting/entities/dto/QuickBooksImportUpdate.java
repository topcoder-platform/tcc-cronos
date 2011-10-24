/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import java.io.Serializable;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents an update to the export details. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class QuickBooksImportUpdate implements JsonPrintable, Serializable {
    /**
     * Represents the IDs of the export details records that are to be updated. It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private long[] billingCostExportDetailIds;

    /**
     * Represents the invoice number to be set in the records. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private String invoiceNumber;

    /**
     * Empty constructor.
     */
    public QuickBooksImportUpdate() {
        // Empty
    }

    /**
     * <p>
     * Getter method for billingCostExportDetailIds, simply return the namesake instance variable.
     * </p>
     *
     * @return the billingCostExportDetailIds
     */
    public long[] getBillingCostExportDetailIds() {
        return billingCostExportDetailIds;
    }

    /**
     * <p>
     * Setter method for billingCostExportDetailIds, simply assign the value to the instance variable.
     * </p>
     *
     * @param billingCostExportDetailIds
     *            the billingCostExportDetailIds to set
     */
    public void setBillingCostExportDetailIds(long[] billingCostExportDetailIds) {
        this.billingCostExportDetailIds = billingCostExportDetailIds;
    }

    /**
     * <p>
     * Getter method for invoiceNumber, simply return the namesake instance variable.
     * </p>
     *
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>
     * Setter method for invoiceNumber, simply assign the value to the instance variable.
     * </p>
     *
     * @param invoiceNumber
     *            the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        if (billingCostExportDetailIds != null) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < billingCostExportDetailIds.length; i++) {
                jsonArray.addLong(billingCostExportDetailIds[i]);
            }
            jsonObject.setArray("billingCostExportDetailIds", jsonArray);
        } else {
            jsonObject.setNull("billingCostExportDetailIds");
        }
        Helper.setJsonObject(jsonObject, "invoiceNumber", invoiceNumber);
        return jsonObject.toJSONString();
    }
}
