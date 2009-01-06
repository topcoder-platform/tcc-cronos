<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the TopCoder Project Service Facade web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the Project Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Project Service Facade Demo</title>
    <script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
</head>

<body>
<p>Project Service Facade Demo
    (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                        ? "Cockpit Administrator" : "Cockpit User"%>))</p><br/><br/>
<span style="color:blue"><b>Project Services Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ProjectService.createProject(ProjectData) <br/>&nbsp; &nbsp;
        Project Name: <input type="text" name="pname" value=""> &nbsp; &nbsp;
        Project Description: <input type="text" name="pdesc" value=""> &nbsp; : &nbsp;
        <input type="button" value="Execute" onclick="callService('createProject');"/></li><br/>
    <li>ProjectService.updateProject(ProjectData) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="uid" value=""> &nbsp; &nbsp;
        Project Name: <input type="text" name="uname" value=""> &nbsp; &nbsp;
        Project Description: <input type="text" name="udesc" value=""> &nbsp; : &nbsp;
        <input type="button" value="Execute" onclick="callService('updateProject');"/></li><br/>
    <li>ProjectService.deleteProject(long) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="did" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteProject');"/></li><br/>
    <li>ProjectService.getProject(long) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="pid" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getProject');"/></li><br/>
    <li>ProjectService.getGetProjectsForUser(long) <br/>&nbsp; &nbsp;
        User ID: <input type="text" name="userid" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getProjectsForUser');"/></li><br/>
    <li>ProjectService.getAllProjects() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllProjects');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
