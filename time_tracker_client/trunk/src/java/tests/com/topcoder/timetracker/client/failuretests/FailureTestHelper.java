/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * Helper class used for failure testing.
 * @author TCSDEVELOPER
 * @version 3.2
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
     * Create the client used for test with the specified client id.
     * @param id the id
     * @return the client
     */
    public static Client createCient(long id) {
        Client client = new Client();
        
        client.setActive(true);
        client.setAddress(new Address());
        client.setChanged(true);
        client.setCompanyId(id);
        client.setContact(new Contact());
        client.setCreationDate(new Date());
        client.setCreationUser("user");
        client.setEndDate(new Date());
        client.setId(id);
        client.setModificationDate(new Date());
        client.setModificationUser("modificationUser");
        client.setName("tc");
        PaymentTerm term = new PaymentTerm();
        term.setId(id);
        client.setPaymentTerm(term);
        client.setProjects(new Project[0]);
        client.setSalesTax(0.1);
        client.setStartDate(new Date());
        
        Contact contact = new Contact();
        contact.setId(id);
        client.setContact(contact);
        
        Address address = new Address();
        address.setId(id);
        client.setId(id);
        client.setAddress(address);
        
        Project project1 = new Project();
        project1.setId(id);
        Project project2 = new Project();
        project2.setId(id * 2);

        client.setProjects(new Project[]{project1, project2});
        return client;
    }
}
