<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<th>Register Date</th>
			<th>Submit Date</th>
			<th>Duration</th>
			<th>Technologies</th>
			<th>Status</th>
			<th>First Prize</th>
		</tr>
		<c:forEach var="upcomingContest" items="${upcomingContests}">
			<tr>
				<td>${upcomingContest.contestName}</td>
				<td>${upcomingContest.type}</td>
				<td>${upcomingContest.subtype}</td>
				<td>${upcomingContest.registerDate}</td>
				<td>${upcomingContest.submitDate}</td>
				<td>${upcomingContest.duration}</td>
				<td>${upcomingContest.technologies}</td>
				<td>${upcomingContest.status}</td>
				<td>${upcomingContest.firstPrize}</td>
			</tr>
		</c:forEach>
	</table>
</body>