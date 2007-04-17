/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.contact.Country;

import junit.framework.TestCase;

/**
 * <p>Failure test cases for the class Country.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class CountryFailureTests extends TestCase {

	/**
	 * Country to test.
	 */
	private Country country = new Country();
	
	/**
	 * Test the setname with null value, iae expected.
	 *
	 */
	public void testSetName_nullName() {
		try{
			this.country.setName(null);
			fail("The name is null.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * Test the setname with empty value, iae expected.
	 *
	 */
	public void testSetName_emptyName() {
		try{
			this.country.setName(" ");
			fail("The name is empty.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
}

