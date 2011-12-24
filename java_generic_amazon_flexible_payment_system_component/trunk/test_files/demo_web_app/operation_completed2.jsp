<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>The authorized payment operation is completed. Please, use test receiver to see
additional payment events info</h2>
<h3>paymentId =<b><%= request.getParameter("paymentId") %></b></h3>
<i>NOTE: You can write down the payment id and use it later for testing</i> <br/>
<i>NOTE: Operation completion doesn't mean its success. If you see paymentId = 0 here, then operation failed.</i>
<br/><br/>
<a href="index.html">Return to main zone</a>
</body>
</html>
