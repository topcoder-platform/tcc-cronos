<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Review and Search Services Demo</title>
</head>
<body>
	<form action="parameterAction" method="post">
		<input type="hidden" name="target"
			value="reviewOpportunitiesManagerAction" /> Contest Name:
		<input type="text" name="contestName" />
		<br /> Catalog:
		<input type="text" name="catalog" />
		<br /> Type:
		<input type="text" name="type" />
		<br /> Subtype:
		<input type="text" name="subtype" />
		<br /> Registration Start Date:
		<select name="registStartType">
			<option value="NONE" selected="true">None</option>
			<option value="BEFORE">Before</option>
			<option value="AFTER">After</option>
			<option value="ON">On</option>
			<option value="BEFORE_CURRENT_DATE">Before Current</option>
			<option value="AFTER_CURRENT_DATE">After Current</option>
			<option value="BETWEEN_DATES">Between</option>
		</select>
		<input type="text" name="registStartDate1" /> (yyyy/MM/dd)
		<input type="text" name="registStartDate2" /> (yyyy/MM/dd)
		<br /> Submission End Date:
		<select name="submitEndType">
			<option value="NONE" selected="true">None</option>
			<option value="BEFORE">Before</option>
			<option value="AFTER">After</option>
			<option value="ON">On</option>
			<option value="BEFORE_CURRENT_DATE">Before Current</option>
			<option value="AFTER_CURRENT_DATE">After Current</option>
			<option value="BETWEEN_DATES">Between</option>
		</select>
		<input type="text" name="submitEndDate1" /> (yyyy/MM/dd)
		<input type="text" name="submitEndDate2" /> (yyyy/MM/dd)
		<br /> Prize Start:
		<input type="text" name="prizeStart" />
		<br /> Prize End:
		<input type="text" name="prizeEnd" />
		<br /> Sorting Column Name:
		<input type="text" name="columnName" />
		<br /> Sorting Order:
		<select name="sortingOrder">
			<option value="NONE" selected="true">None</option>
			<option value="ASCENDING">Ascending</option>
			<option value="DESCEDING">Descending</option>
		</select>
		<br /> Page Number:
		<input type="text" name="pageNumber" /> Page Size:
		<input type="text" name="pageSize" />
		<input type="submit" />
	</form>
</body>