package com.orpheus.game.result;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.DocumentHelper;
import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.JNDIHelper;
import com.orpheus.game.TestHelper;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;

public class MessagePollResultTest extends TestCase {
	
	private MessagePollResult messagePollResult;
	private ActionContext context;
	private MockHttpRequest request;
	private MockHttpSession session;
	private MockServletContext servletContext;
	private MockHttpResponse response;
	
	protected void setUp() throws Exception {
		super.setUp();
		tearDown();
		TestHelper.loadConfig();
		
		messagePollResult = new MessagePollResult("date",new String[]{"gameA","gameB","gameC"});
		
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance().getDataStoreKey(), new MockDataStore());
		
		session = new MockHttpSession(servletContext);
		request = new MockHttpRequest(session);
		
		response = new MockHttpResponse();
		context = new ActionContext(request, response);
        request.setParameter("date", "2006-10-10T12:33:32,000-05:00");
        JNDIHelper.initJNDI();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		TestHelper.unloadConfig();
	}

	/*
	 * Test method for 'com.orpheus.game.result.MessagePollResult.MessagePollResult(String, String[])'
	 */
	public void testMessagePollResultStringStringArray() {
        assertNotNull("MessagePollResult should be instantiated successfully",messagePollResult);
	}

	/*
	 * Test method for 'com.orpheus.game.result.MessagePollResult.MessagePollResult(Element)'
	 */
	public void testMessagePollResultElement() throws Exception{
        Element element = DocumentHelper.getDocument("/MessagePollResult.xml", "config", "valie_config");
        MessagePollResult result = new MessagePollResult(element);
        result.execute(context);
	}

	/*
	 * Test method for 'com.orpheus.game.result.MessagePollResult.execute(ActionContext)'
	 */
	public void testExecute() throws ResultExecutionException {
		messagePollResult.execute(context);
	}

}
