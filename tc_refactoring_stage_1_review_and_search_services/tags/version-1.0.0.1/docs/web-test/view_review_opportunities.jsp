<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Review and Search Services Demo</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Contest Name</th>
			<th>Type</th>
			<th>Subtype</th>
			<th>Primary Reviewer Payment</th>
			<th>Secondary Reviewer Payment</th>
			<th>Submission Number</th>
			<th>Opens On</th>
			<th>Review Start</th>
			<th>Review End</th>
			<th>Number of Review Positions Available</th>
		</tr>
		<c:forEach var="reviewOpportunity" items="${reviewOpportunities}">
		<tr>
			<td>${reviewOpportunity.contestName}</td>
			<td>${reviewOpportunity.type}</td>
			<td>${reviewOpportunity.subtype}</td>
			<td>${reviewOpportunity.primaryReviewerPayment}</td>
			<td>${reviewOpportunity.secondaryReviewerPayment}</td>
			<td>${reviewOpportunity.submissionsNumber}</td>
			<td>${reviewOpportunity.opensOn}</td>
			<td>${reviewOpportunity.reviewStart}</td>
			<td>${reviewOpportunity.reviewEnd}</td>
			<td>${reviewOpportunity.numberOfReviewPositionsAvailable}</td>
		</tr>
		</c:forEach>
	</table>
</body>