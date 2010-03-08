/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.accuracytests;

import java.util.Map;

import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;
import com.topcoder.service.actions.AggregateDataModel;

/**
 * Mock ValueStack.
 * @author onsky
 * @version 1.0
 */
public class MockValueStack implements ValueStack {

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findString(java.lang.String)
	 */
	public String findString(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findString(java.lang.String, boolean)
	 */
	public String findString(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String)
	 */
	public Object findValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String, boolean)
	 */
	public Object findValue(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String, java.lang.Class)
	 */
	public Object findValue(String arg0, Class arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String, java.lang.Class, boolean)
	 */
	public Object findValue(String arg0, Class arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#getContext()
	 */
	public Map<String, Object> getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#getExprOverrides()
	 */
	public Map<Object, Object> getExprOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#getRoot()
	 */
	public CompoundRoot getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#peek()
	 */
	public Object peek() {
        AggregateDataModel model = new AggregateDataModel();
        model.setData("result", new IllegalStateException("acc"));
        return model;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#pop()
	 */
	public Object pop() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#push(java.lang.Object)
	 */
	public void push(Object arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#set(java.lang.String, java.lang.Object)
	 */
	public void set(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#setDefaultType(java.lang.Class)
	 */
	public void setDefaultType(Class arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#setExprOverrides(java.util.Map)
	 */
	public void setExprOverrides(Map<Object, Object> arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#setValue(java.lang.String, java.lang.Object)
	 */
	public void setValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#setValue(java.lang.String, java.lang.Object, boolean)
	 */
	public void setValue(String arg0, Object arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.util.ValueStack#size()
	 */
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
