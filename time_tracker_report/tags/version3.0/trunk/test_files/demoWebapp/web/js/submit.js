var SEPARATOR = ']^[';
function submitEntries(tbody, form, action)
{
	var startRow = form.opt ? 3 : 2;
    return submitEntriesStartRow(tbody, form, action, startRow);
}

function submitEntriesStartRow(tbody, form, action, startRow)
{
    form.action.value = action;
    var idx = 0;
    function addInput(value)
    {
        var input;
        if (form.action.length) input = form.action[0].cloneNode(true);
        else input = form.action.cloneNode(true);
        input.name = "entry" + idx;
        input.value = value;
        form.appendChild(input);
    }
    function clearInputs()
    {
        if (form.elements["entry" + idx])
        {
            for (var i = 0; i < form.elements["entry" + idx].length; i++)
            {
                form.removeChild(form.elements["entry" + idx][i]);
            }
        }
    }
    function locateText(cell)
    {
        var select = cell.getElementsByTagName("select")[0];
        for (var i = 0; i < select.options.length; i++)
        {
            if (select.options[i].value == cell.getAttribute("selectedValue"))
            {
                return select.options[i].text;
            }
        }
        return "";
    }
    function getFirstLine(content)
    {
        var pos = content.toLowerCase().indexOf("<br");
        if (pos == -1) return content;
        return content.substring(0, pos);
    }
    for (var i = startRow; i < tbody.rows.length; i++)
    {
        var row = tbody.rows[i];
        if (row.cells.length == 1 || row.getAttribute("tot") || row.getAttribute("pending")) continue;		
        idx++;
        clearInputs();
        if (row.getAttribute("entryId")) addInput("id" + SEPARATOR + row.getAttribute("entryId"));
        if (row.cells[1].getElementsByTagName("input").length > 0)
        {
            addInput("date" + SEPARATOR + row.cells[0].originalValue);
			if (row.cells[3].originalValue.length > 0) addInput("desc" + SEPARATOR + row.cells[3].originalValue);
            addInput("amount" + SEPARATOR + row.cells[4].originalValue);
            if (row.cells[5].originalValue) addInput("billable");
            addInput("project" + SEPARATOR + row.cells[1].getAttribute("selectedValue") + SEPARATOR + locateText(row.cells[1]));
            addInput("type" + SEPARATOR + row.cells[2].getAttribute("selectedValue") + SEPARATOR + locateText(row.cells[2]));
            if (row.cells.length > 7) {
                addInput("status" + SEPARATOR + row.cells[6].getAttribute("selectedValue") + SEPARATOR + locateText(row.cells[6]));
            }
        }
        else
        {
            addInput("date" + SEPARATOR + row.cells[0].innerHTML);
			if (row.cells[3].innerHTML.length > 0) addInput("desc" + SEPARATOR + row.cells[3].innerHTML);
            addInput("amount" + SEPARATOR + row.cells[4].innerHTML);
            if (row.cells[5].getElementsByTagName("input")[0].checked) addInput("billable");
            addInput("project" + SEPARATOR + row.cells[1].getAttribute("selectedValue") + SEPARATOR + getFirstLine(row.cells[1].innerHTML));
            addInput("type" + SEPARATOR + row.cells[2].getAttribute("selectedValue") + SEPARATOR + row.cells[2].innerHTML);
            if (row.cells.length > 7) {
                addInput("status" + SEPARATOR + row.cells[6].getAttribute("selectedValue") + SEPARATOR + row.cells[6].innerHTML);
            }
        }		
        if (row.cells[1].getAttribute("rejectReason"))
        {
            addInput("reject" + SEPARATOR + row.cells[1].getAttribute("rejectReasonId") + SEPARATOR + row.cells[1].getAttribute("rejectReason"));
        }
    }
    form.entries.value = idx;
    form.submit();
    return false;
}

function processSelect(sel) {
  var options = sel.options;
  if (sel.selectedIndex == 0) {
    options[0].selected = false;
    for (var i = 1; i < options.length; i++) {
      options[i].selected = (options[i].value >= 0);
    }
  }
  else {
    var activeGroup = false;
    for (var i = 1; i < options.length; i++) {
      if (options[i].value < 0) {
        activeGroup = options[i].selected;
        options[i].selected = false;
      }
      else if (activeGroup) {
        options[i].selected = true;
      }
    }
  }
}

function submitEntryAdmin(form)
{
    var fields = new Array();
    fields.push(form.startDate);
    fields.push(form.endDate);
    fields.push(form.workerId);
    return js.util.FieldValidation.hardValidation(fields);
}

function submitReport(form)
{
    var fields = new Array();
    fields.push(form.time);
    fields.push(form.expenses);
    fields.push(form.billable);
    fields.push(form.nonBillable);
    fields.push(form.startDate);
    fields.push(form.endDate);
    if (form.user) fields.push(form.user);
    if (form.client) fields.push(form.client);
    if (form.project) fields.push(form.project);
    return js.util.FieldValidation.hardValidation(fields);
}

function forgetPassword(userName)
{
	if (trimStr(userName).length > 0)
	{
		return true;
	}
	alert("Please input Username!");
	return false;
	function trimStr(s) {
		s = s.replace( /^\s+/g, "" );// strip leading
		return s.replace( /\s+$/g, "" );// strip trailing
	}
}