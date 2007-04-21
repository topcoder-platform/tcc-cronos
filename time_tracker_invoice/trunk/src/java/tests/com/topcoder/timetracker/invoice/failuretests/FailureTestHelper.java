/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * Helper class used for failure testing.
 * @author enefem21
 * @version 1.0
 *
 */
public class FailureTestHelper {
	/**
	 * private constructor.
	 *
	 */
	private FailureTestHelper() {
		
	}
	
    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * Remove all the namespace in this component.
     *
     */
    public static void removeNamespaces() {
    	ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();

            if (cm.existsNamespace(ns)) {
                try {
                    cm.removeNamespace(ns);
                } catch (UnknownNamespaceException e) {
                    System.err.println("error occurs in removing namespace : " +
                        ns);
                }
            }
        }
    }
    
    /**
     * Create Invoice instance.
     * @return the Invoice instance.
     */
    public static Invoice createInvoice() {
    	Invoice invoice = new Invoice();
    	invoice.setChanged(false);
    	invoice.setCompanyId(1);
    	invoice.setCreationDate(new Date());
    	invoice.setCreationUser("C_U");
    	invoice.setDueDate(new Date());
    	ExpenseEntry e = new ExpenseEntry();
    	e.setId(1);
    	e.setAmount(new BigDecimal(0));
    	invoice.setExpenseEntries(new ExpenseEntry[]{e});
    	invoice.setId(1);
    	return invoice;
    }
    
}
