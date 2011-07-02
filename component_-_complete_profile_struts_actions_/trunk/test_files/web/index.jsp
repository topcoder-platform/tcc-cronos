<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Main page</title>
</head>
<body>    
	<a href="<%= request.getContextPath() %>/getUserProfileAllFields">Get User Profile with all fields.</a> </br>	
	<a href="<%= request.getContextPath() %>/getUserProfileForForum">Get User Profile for forum.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForWiki">Get User Profile for wiki.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForTCDirect">Get User Profile for TCDirect.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForOnlineReviewForCompetitor">Get User Profile for ORForCompetitor.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForOnlineReviewForCustomer">Get User Profile for ORForCustomer.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForJiraForCompetitor">Get User Profile for JIRAForComp.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForJiraForCustomer">Get User Profile for JIRAForCustom.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForSVNAccessForCompetitor">Get User Profile for SVNForComp.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForSVNAccessForCustomer">Get User Profile for SVNForCust.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForVMAccessForCompetitor">Get User Profile for VMForComp.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForVMAccessForCustomer">Get User Profile for VMForCust.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForCompetitionParticipation">Get User Profile for competition participation.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForCopilotContest">Get User Profile for copilot contest.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForStudioForCompetitor">Get User Profile for studio competitor.</a> </br>
	<a href="<%= request.getContextPath() %>/getUserProfileForStudioForCustomer">Get User Profile for studio customer.</a> </br>	
</body>
</html>