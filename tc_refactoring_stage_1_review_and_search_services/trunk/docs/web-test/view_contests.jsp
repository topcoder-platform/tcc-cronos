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
			<th>Catalog</th>
			<th>Type</th>
			<th>Subtype</th>
			<th>Number of Registrants</th>
			<th>Number of Submissions</th>
			<th>Passed Screening Count</th>
			<th>Winner Profile Link</th>
			<th>Winner Score</th>
		</tr>
		<c:forEach var="contest" items="${contests}">
		<tr>
			<td>${contest.contestName}</td>
			<td>${contest.catalog}</td>
			<td>${contest.type}</td>
			<td>${contest.subtype}</td>
			<td>${contest.numberOfRegistrants}</td>
			<td>${contest.numberOfSubmissions}</td>
			<td>${contest.passedScreeningCount}</td>
			<td>${contest.winnerProfileLink}</td>
			<td>${contest.winnerScore}</td>
		</tr>
		</c:forEach>
	</table>
</body>