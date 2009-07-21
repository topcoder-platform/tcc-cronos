<%--
  - Author: PE
  - Date: 8 Mar 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the page for the demonstration of the TopCoder Contest Service Facade web service.
  - The page lists the Online Review Upload Services operations provided by the TopCoder Contest Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Online Review Upload Service Operations Demo</title>
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
<span style="color:blue"><b>Online Review Upload Service Operations Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ContestService.uploadSubmission() &nbsp; &nbsp;<br/>
        Project Id: <input type="text" name="cid11" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('uploadSubmission');"/></li><br/>
    <li>ContestService.uploadFinalFix() &nbsp; &nbsp;<br/>
        Project Id: <input type="text" name="cid21" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('uploadFinalFix');"/></li><br/>
    <li>ContestService.uploadTestCases() &nbsp; &nbsp;<br/>
        Project Id: <input type="text" name="cid31" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('uploadTestCases');"/></li><br/>
    <li>ContestService.setSubmissionStatus() &nbsp; &nbsp;<br/>
        Submission Id: <input type="text" name="cid41" value=""> &nbsp; &nbsp;<br/>
        SubmissionStatus Id: <input type="text" name="cid42" value=""> &nbsp; &nbsp;<br/>
        Operator: <input type="text" name="cid43" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('setSubmissionStatus');"/></li><br/>
    <li>ContestService.addSubmitter() &nbsp; &nbsp;<br/>
        Project Id: <input type="text" name="cid51" value=""> &nbsp; &nbsp;<br/>
        User Id: <input type="text" name="cid52" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('addSubmitter');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
