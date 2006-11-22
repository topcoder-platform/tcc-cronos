package servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class MockHttpSession implements HttpSession {
	private Map attribute = new HashMap();
	private ServletContext context;
	
	public MockHttpSession(ServletContext context){
		this.context = context;
	}
	
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ServletContext getServletContext() {
		return context;
	}

	public void setMaxInactiveInterval(int arg0) {
		// TODO Auto-generated method stub

	}

	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAttribute(String arg0) {
		
		return attribute.get(arg0);
	}

	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String arg0, Object arg1) {
		this.attribute.put(arg0,arg1);

	}

	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeValue(String arg0) {
		// TODO Auto-generated method stub

	}

	public void invalidate() {
		// TODO Auto-generated method stub

	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

}
