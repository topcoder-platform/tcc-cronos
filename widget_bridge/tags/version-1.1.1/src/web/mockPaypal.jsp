<html>
This is a mock for Paypal.
The silent Post is called.

In order to return to Cockpit, press the button below.

<% long date = new java.util.Date().getTime(); %>

<body onload="document.forms['silentPost'].submit();">
<form name="confirm" action="/direct/cockpit/ajaxbridge/paypalConfirmation.jsp"  method="post" >
	<input type="hidden" name="PNREF" value="MockPayment_<%=  date %>" />
	<input type="hidden" name="USER1" value="<%= request.getParameter("USER1") %>" />
	<input type="hidden" name="USER2" value="<%= request.getParameter("USER2") %>" />
	<input type="hidden" name="USER3" value="<%= request.getParameter("USER3") %>" />
	<input type="hidden" name="USER4" value="<%= request.getParameter("USER4") %>" />
	<input type="hidden" name="USER5" value="<%= request.getParameter("USER5") %>" />
	<input type="hidden" name="USER6" value="<%= request.getParameter("USER6") %>" />
	<input type="hidden" name="USER7" value="<%= request.getParameter("USER7") %>" />
	<input type="hidden" name="USER8" value="<%= request.getParameter("USER8") %>" />
	<input type="hidden" name="USER9" value="<%= request.getParameter("USER9") %>" />
	<input type="hidden" name="USER10" value="<%= request.getParameter("USER10") %>" />

	<input type="button" value="Return to Cockpit" onClick="confirm.submit()" >
</form>


<form name="silentPost" action="/direct/cockpit/ajaxbridge/paypalSilentPost.jsp" method="post" target="silentPostFrame">
	<input type="hidden" name="PNREF" value="MockPayment_<%= date %>" />
	<input type="hidden" name="USER1" value="<%= request.getParameter("USER1") %>" />
	<input type="hidden" name="USER2" value="<%= request.getParameter("USER2") %>" />
	<input type="hidden" name="USER3" value="<%= request.getParameter("USER3") %>" />
	<input type="hidden" name="USER4" value="<%= request.getParameter("USER4") %>" />
	<input type="hidden" name="USER5" value="<%= request.getParameter("USER5") %>" />
	<input type="hidden" name="USER6" value="<%= request.getParameter("USER6") %>" />
	<input type="hidden" name="USER7" value="<%= request.getParameter("USER7") %>" />
	<input type="hidden" name="USER8" value="<%= request.getParameter("USER8") %>" />
	<input type="hidden" name="USER9" value="<%= request.getParameter("USER9") %>" />
	<input type="hidden" name="USER10" value="<%= request.getParameter("USER10") %>" />
</form>

	<iframe name="silentPostFrame" width="0" height="0" border="0">

</body>
</html>
