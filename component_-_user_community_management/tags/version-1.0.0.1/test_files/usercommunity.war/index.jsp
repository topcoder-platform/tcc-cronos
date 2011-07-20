<%@ page language="java" import="com.topcoder.web.reg.actions.miscellaneous.*, com.topcoder.shared.dataAccess.*" contentType="text/html" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>Demo</title>
  </head>
  <body>
  	
    	<% 
    	DataAccessConstants.COMMAND = "COMMAND";
    	request.getSession().setAttribute("authenticationkey", new MockBasicAuthentication()); %>
    	My Id: <% out.println(((MockBasicAuthentication)session.getAttribute("authenticationkey")).getActiveUser().getId()) ;%>
    </br>                                                                                                     
    	My Name: <% out.println(((MockBasicAuthentication)session.getAttribute("authenticationkey")).getActiveUser().getUserName()); %>
    </br>    
    	(The username/id is just mocked, check MockBasicAuthentication.java for more details.)
    </br>
    </br>    
    
    <a href="ViewReferralsAction.action">View referrals</a>
    <br/>
    <a href="DownloadBadgesAction.action">Download badges</a>
    <br/>
    <!--
    Unable to show the demo of these three actions as we're unable to deploy UserPreference services
    <a href="ShowCardInstructionsAction.action">Show card instructions</a>
    <br/>
    <a href="PreviewCardAction.action">Preview card</a>
    <br/>
    <a href="UnlockCardAction.action">Unlock</a> 
    <br/>
    -->
    <a href="RetrieveCardDataAction.action?coderId=100">retrive card data</a>
  </body>
</html>
