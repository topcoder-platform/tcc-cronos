/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.contact.State;

import junit.framework.TestCase;

/**
 * <p>Failure test cases for the class State.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class StateFailureTests extends TestCase {

	/**
	 * State to test.
	 */
	private State state = new State();
	
	/**
	 * Test setAbbreviation with null value, iae expected.
	 *
	 */
	public void testSetAbbreviation_nullValue(){
		try{
			this.state.setAbbreviation(null);
			fail("The Abbreviation is null.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * Test setAbbreviation with empty value, iae expected.
	 *
	 */
	public void testSetAbbreviation_emptyValue(){
		try{
			this.state.setAbbreviation(" ");
			fail("The Abbreviation is empty.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * Test the setname with null value, iae expected.
	 *
	 */
	public void testSetName_nullName() {
		try{
			this.state.setName(null);
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
			this.state.setName(" ");
			fail("The name is empty.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
}


