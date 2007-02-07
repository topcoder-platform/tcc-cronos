function addTaskType() {
	descriptionText = document.getElementById('taskDescription').value;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter a task type description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for task type description is 64 characters.');
		return;
	}
	doTaskType("1", "", descriptionText);
}

function editTaskType(id) {
	doTaskType("2", id, "");
}

function removeTaskType(id, taskDesc) {
	if (!confirm('Are you sure you want to delete tasktype: ' + taskDesc + ' ?')) {
		return;
	}
	doTaskType("3", id, "");
}

function updateTaskType(id) {
	descriptionText = document.getElementById('taskDescription'+id).value ;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter a task type description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for task type description is 64 characters.');
		return;
	}
	doTaskType("5", id, descriptionText);
}

function doTaskType(opt, id, content) {
	doConfig(opt, id, content, "taskType");
}

function addExpenseType() {
	descriptionText = document.getElementById('expenseDescription').value ;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter an expense type description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for expense type description is 64 characters.');
		return;
	}
	doExpenseType("1", "", descriptionText);
}

function editExpenseType(id) {
	doExpenseType("2", id, "");
}

function removeExpenseType(id, expenseDesc) {
	if (!confirm('Are you sure you want to delete expense type: ' + expenseDesc + ' ?')) {
		return;
	}
	doExpenseType("3", id, "");
}

function updateExpenseType(id) {
	descriptionText = document.getElementById('expenseDescription'+id).value ;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter a expense type description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for expense type description is 64 characters.');
		return;
	}
	doExpenseType("5", id, descriptionText);
}

function doExpenseType(opt, id, content) {
	doConfig(opt, id, content, "expenseType");
}

function addRejectReason() {
	descriptionText = document.getElementById('rejectReasonDescription').value ;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter a reject reason description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for reject reason description is 64 characters.');
		return;
	}
	doRejectReason("1", "", descriptionText);
}

function editRejectReason(id) {
	doRejectReason("2", id, "");
}

function removeRejectReason(id, rejectReasonDesc) {
	if (!confirm('Are you sure you want to delete reject reason: ' + rejectReasonDesc + ' ?')) {
		return;
	}
	doRejectReason("3", id, "");
}

function updateRejectReason(id) {
	descriptionText = document.getElementById('rejectReasonDescription'+id).value ;
	if (!descriptionText || trimStr(descriptionText).length == 0) {
		alert('Please enter a reject reason description.');
		return;
	}
	if (descriptionText.length > 64) {
		alert('The maximum length for ereject reason description is 64 characters.');
		return;
	}
	doRejectReason("5", id, descriptionText);
}

function doRejectReason(opt, id, content) {
	doConfig(opt, id, content, "rejectReason");
}

function saveReject(id) {
	rejectEmailBodyText = document.getElementById('rejectEmailBody').value ;
	if (!rejectEmailBodyText || trimStr(rejectEmailBodyText).length == 0) {
		alert('Please enter a reject email body.');
		return;
	}
	doConfig("5", id, rejectEmailBodyText, "rejectEmailBody");
}

function doConfig(opt, id, content, type) {
	document.configForm.opt.setAttribute("value", opt);
	document.configForm.id.setAttribute("value", id);
	document.configForm.content.setAttribute("value", content);
	document.configForm.configType.setAttribute("value", type);
	document.configForm.submit();
}

function trimStr(s) {
	s = s.replace( /^\s+/g, "" );// strip leading
	return s.replace( /\s+$/g, "" );// strip trailing
}


function validTimeOrExpense(form) {
	var fields = new Array();
    fields.push(form.startDate);
    fields.push(form.endDate);
	fields.push(form.worker);
    return js.util.FieldValidation.hardValidation(fields);
}