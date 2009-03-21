<%--
  - Author: PE
  - Date: 8 Mar 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the page for the demonstration of the TopCoder Contest Service Facade web service.
  - The page lists the Category/Project Services operations provided by the TopCoder Contest Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Category/Project Services Operations Demo</title>
    <script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
    <style type="text/css">
        body {
            font-size: 14px;
            font-family: Arial Verdana sans-serif;
        }
    </style>
</head>

<body>
<p>Contest Service Facade Demo
    (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                        ? "Cockpit Administrator" : "Cockpit User"%>))</p><br/><br/>
<span style="color:blue"><b>Category/Project Services Operations Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ContestService.getActiveCategories() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getActiveCategories');"/></li><br/>
    <li>ContestService.getActiveTechnologies() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getActiveTechnologies');"/></li><br/>
    <li>ContestService.getPhases() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getPhases');"/></li><br/>
    <li>ContestService.assignUserToAsset() &nbsp; &nbsp;<br/>
        User ID: <input type="text" name="cid11" value=""> &nbsp; &nbsp;<br/>
        Asset ID: <input type="text" name="cid12" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('assignUserToAsset');"/></li><br/>
    <li>ContestService.removeUserFromAsset() &nbsp; &nbsp;<br/>
        User ID: <input type="text" name="cid21" value=""> &nbsp; &nbsp;<br/>
        Asset ID: <input type="text" name="cid22" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('removeUserFromAsset');"/></li><br/>
    <li>ContestService.findAllTcDirectProjects() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('findAllTcDirectProjects');"/></li><br/>
    <li>ContestService.findAllTcDirectProjectsForUser() &nbsp; &nbsp;<br/>
        Operator: <input type="text" name="cid31" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('findAllTcDirectProjectsForUser');"/></li><br/>
    <li>ContestService.getFullProjectData() &nbsp; &nbsp;<br/>
        Project Id: <input type="text" name="cid41" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getFullProjectData');"/></li><br/>
    <li>ContestService.createSoftwareContest() &nbsp; &nbsp;<br/>
        TC Direct Project Id: <input type="text" name="cid51" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('createSoftwareContest');"/></li><br/>
    <li>ContestService.updateSoftwareContest() &nbsp; &nbsp;<br/>
        TC Direct Project Id: <input type="text" name="cid61" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('updateSoftwareContest');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
