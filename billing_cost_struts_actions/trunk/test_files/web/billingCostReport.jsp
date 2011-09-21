<%@page language="java" contentType="text/html" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
  <head> 
    <title></title>
  </head>
  <body>      
     <s:form action="getBillingCostReport">
     	<s:textfield name="criteria.projectId" key="projectId"></s:textfield>
      	<s:textfield name="criteria.action" key="action"></s:textfield>
      	<s:textfield name="criteria.startDate" key="startDate"></s:textfield>
        <s:textfield name="criteria.endDate" key="endDate"></s:textfield>
		<s:textfield name="pageNumber" key="pageNumber"></s:textfield>
		<s:textfield name="pageSize" key="pageSize"></s:textfield>
     	<s:submit value="view Billing Cost Report"></s:submit>
     </s:form>
     
     <s:form action="exportToQuickBooks">
     	<s:textfield name="paymentAreaId" key="paymentAreaId"></s:textfield>
     	<s:submit value="export To QuickBooks"></s:submit>
     </s:form>
  </body>
</html>
