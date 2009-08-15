<%--
  - Author: PE
  - Date: 22 Mar 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the page for the demonstration of the TopCoder Contest Service Facade web service.
  - The page lists the Contest Sale related operations provided by the TopCoder Contest Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Contest Sale Related Operations Demo</title>
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
<span style="color:blue"><b>Contest Sale Related Operations Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ContestService.processContestCreditCardSale() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('processContestCreditCardSale');"/></li><br/>
    <li>ContestService.processContestPurchaseOrderSale() &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('processContestPurchaseOrderSale');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
