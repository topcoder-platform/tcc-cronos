js.gui.TableEditor = (function() {

    //Superclass
    TableEditor.extendsClass(js.Class);

    //constants
    var BTN_ADD = 0;
    var BTN_EDIT = 1;
    var BTN_DELETE = 2;
    var BTN_SAVE = 3;
    var BTN_CANCEL = 4;

    var TOT_SUM = 1;
    var TOT_AVG = 2;

    //constructor
    function TableEditor(properties)
    {
        js.Class.apply(this, [ properties ]);

        this.properties.elem.onclick = js.Class.getCustomEventHandler(this, "click");

        /**
         * Locate the row where the totals is in. New entries are added before this row.
         * If the row is not located, -1 is returned instead.
         */
        function findTotalsRow(tbody)
        {
            for (var i = 0; i < tbody.rows.length; i++)
            {
                if (tbody.rows[i].getAttribute(js.util.ATTR_TOTALS) == -1) return i;
            };
            return -1;
        }

        /**
         * Add a row to the current table before the totals row.
         */
        function addRow(tr)
        {
            // Extracts all the input from the editing row.
            var inputs = getEditorFields(tr);
            if (inputs.length == 0) return;
            // If the input does not pass validation, simply let the user to continue with the edit.
            if (!js.util.FieldValidation.hardValidation(inputs)) return;

            var stringCount = 0;
            var cell = null;
            // Insert a new row at the position of the totals row (so that's before the totals row).
            var newRow = tr.parentNode.insertRow(findTotalsRow(tr.parentNode));
            newRow.className = (tr.parentNode.rows.length % 2 > 0) ? "" : "band";
            var editRow = tr.parentNode.rows[0];

            for (var i = 0; i < inputs.length; i++)
            {
                var node = null;
                // Make a new cell with the same style as the editing row.
                cell = newRow.insertCell(-1);
                if (editRow.cells[i].className)
                {
                    cell.className = editRow.cells[i].className;
                }
                var type = inputs[i].type.toLowerCase();
                switch (type)
                {
                    // Check box or radio button would be displayed in the disabled mode.
                    case "checkbox":
                    case "radio":
                        node = inputs[i].cloneNode(true);
                        node.disabled = true;
                        //inputs[i].checked = false;
                        break;

                    // Drop-down box would be displayed as the selected text. The original value is also
                    // backed up for post back to the server, or for re-editing the row.
                    case "select":
                    case "select-one":
                        stringCount++;
                        node = document.createTextNode(inputs[i].options[inputs[i].selectedIndex].text);
                        cell.setAttribute("selectedValue", inputs[i].options[inputs[i].selectedIndex].value);
                        //inputs[i].selectedIndex = 0;
                        //node.disabled = true;
                        break;

                    // Text inputs are displayed as formatted text.
                    case "text":
                        stringCount++;
                        node = document.createTextNode(js.util.FieldFormat.getFormattedFieldValue(inputs[i]));
                        inputs[i].value = "";
                        break;
                };
                if (inputs[i].style.textAlign)
                {
                    cell.style.textAlign = inputs[i].style.textAlign;
                };
                cell.appendChild(node);
                // IE bug prevents the setting of checked if the element is not
                // already in the doc, so set checked after appending.
                if (type == "checkbox" || type == "radio")
                {
                    node.checked = inputs[i].checked;
                };
            };

            cell = newRow.insertCell(-1);
            var btn = null;

            // Make two buttons, initially EDIT and DELETE.
            if (stringCount > 1)
            {
                btn = document.createElement("input");
                btn.type = "button";
                btn.value = "Edit";
                btn.className = "btn";
                btn.setAttribute(js.util.ATTR_CMD_ID, BTN_EDIT);

                cell.appendChild(btn);
            };

            btn = document.createElement("input");
            btn.type = "button";
            btn.value = "Delete";
            btn.className = "btn";
            btn.setAttribute(js.util.ATTR_CMD_ID, BTN_DELETE);

            cell.appendChild(btn);
        };

        /**
         * Edit the selected row.
         */
        function editRow(tr)
        {
            // Collect the input fields from the editing row.
            var editRow = tr.parentNode.rows[0];
            var fields = getEditorFields(editRow);

            for (var i = 0; i < fields.length; i++)
            {
                var td = tr.cells[i];
                var type = fields[i].type.toLowerCase();
                // Re-enable the check box or radio button.
                if (type == "checkbox" || type == "radio")
                {
                    var f = td.getElementsByTagName(fields[i].tagName)[0];
                    td.originalValue = f.checked;
                    f.disabled = false;
                }
                else
                {
                    var newInput = fields[i].cloneNode(true);
                    newInput.style.color = "#000000";
                    td.originalValue = td.innerHTML;
                    td.innerHTML = "";
                    // Put the text value back into text input.
                    if (type == "text")
                    {
                        newInput.value = td.originalValue;
                    }
                    else
                    {
                        // Otherwise look up the selected value.
                        for (var j = 0; j < newInput.options.length; j++)
                        {
                            if (newInput.options[j].value == td.getAttribute("selectedValue"))
                            {
                                newInput.selectedIndex = j;
                                break;
                            };
                        };
                    };
                    td.appendChild(newInput);
                };
            };

            // Changes the buttons from EDIT/DELETE to SAVE/CANCEL.
            switchEditButtons(tr);
        };

        /**
         * Switch the buttons between EDIT/DELETE and SAVE/CANCEL.
         */
        function switchEditButtons(tr)
        {
            var inputs = tr.cells[tr.cells.length - 1].getElementsByTagName("input");
            for (var i = 0; i < inputs.length; i++)
            {
                var td = inputs[i];
                switch (parseInt(td.getAttribute(js.util.ATTR_CMD_ID)))
                {
                    case BTN_EDIT:
                        td.value = "Save";
                        td.setAttribute(js.util.ATTR_CMD_ID, BTN_SAVE);
                        break;

                    case BTN_DELETE:
                        td.value = "Cancel";
                        td.setAttribute(js.util.ATTR_CMD_ID, BTN_CANCEL);
                        break;

                    case BTN_SAVE:
                        td.value = "Edit";
                        td.setAttribute(js.util.ATTR_CMD_ID, BTN_EDIT);
                        break;

                    case BTN_CANCEL:
                        td.value = "Delete";
                        td.setAttribute(js.util.ATTR_CMD_ID, BTN_DELETE);
                        break;
                };
            };
        };

        /**
         * Save a row that is just edited.
         */
        function saveRowEdit(tr)
        {
            var inputs = getEditorFields(tr);
            if (inputs.length == 0) return;
            if (!js.util.FieldValidation.hardValidation(inputs)) return;

            for (var i = 0; i < inputs.length; i++)
            {
                var td = tr.cells[i];
                td.originalValue = null;

                switch (inputs[i].type.toLowerCase())
                {
                    case "checkbox":
                    case "radio":
                        inputs[i].disabled = true;
                        break;

                    case "select":
                    case "select-one":
                        td.setAttribute("selectedValue", inputs[i].options[inputs[i].selectedIndex].value);
                        td.innerHTML = inputs[i].options[inputs[i].selectedIndex].text;
                        break;

                    case "text":
                        td.innerHTML = js.util.FieldFormat.getFormattedFieldValue(inputs[i]);
                        break;
                };

                // Also restore the reject reason for rejected entries.
                if (td.getAttribute("rejectReason") != null) {
                    td.innerHTML = td.innerHTML +
                        "<br /><font color=\"#CC0000\">" + td.getAttribute("rejectReason") + "</font>";
                }
            };

            switchEditButtons(tr);
        };

        /**
         * Cancel an edit. Simply restores the values to the backups.
         */
        function cancelEdit(tr)
        {
            var fields = getEditorFields(tr);

            for (var i = 0; i < fields.length; i++)
            {
                var td = tr.cells[i];
                var type = fields[i].type.toLowerCase();
                if (type == "checkbox" || type == "radio")
                {
                    var f = td.getElementsByTagName(fields[i].tagName)[0];
                    f.checked = td.originalValue;
                    f.disabled = true;
                }
                else
                {
                    td.innerHTML = td.originalValue;
                };
                td.originalValue = null;
            };

            switchEditButtons(tr);
        };

        /**
         * Collect all the input fields from a table row.
         */
        function getEditorFields(tr)
        {
            var fields = new Array();
            for (var i = 0; i < tr.cells.length; i++)
            {
                var td = tr.cells[i];

                var inputs = td.getElementsByTagName("input");
                for (var j = 0; j < inputs.length; j++)
                {
                    if (inputs[j].type.toLowerCase() != "button")
                    {
                        fields.push(inputs[j]);
                    };
                };

                inputs = td.getElementsByTagName("select");
                for (var j = 0; j < inputs.length; j++)
                {
                    fields.push(inputs[j]);
                };

            };

            return fields;
        };

        /**
         * Updates the totals row.
         */
        function editTotalsRow(tbody)
        {
            var totals = new Object();
            var hasTotals = false;
            var fields = getEditorFields(tbody.rows[0]);
            for (var i = 0; i < fields.length; i++)
            {
                if (fields[i].getAttribute(js.util.ATTR_TOTALS))
                {
                    hasTotals = true;
                    totals[i] = {
                        "type" : parseInt(fields[i].getAttribute(js.util.ATTR_TOTALS)),
                        "format" : fields[i].getAttribute(js.util.ATTR_FLD_FORMAT),
                        "values" : new Array()
                    };
                };
            };

            var totalsRowNo = findTotalsRow(tbody);
            if (!hasTotals || totalsRowNo == -1) return;

            // Only account for the entries before the totals row.
            for (var i = 1; i < totalsRowNo; i++)
            {
                for (var t in totals)
                {
                    totals[t].values.push(parseFloat(tbody.rows[i].cells[t].innerHTML));
                };

            };

            var totalsRow = tbody.rows[totalsRowNo];
            for (var t in totals)
            {
                var newValue = 0;
                switch (totals[t].type) {
                    case TOT_SUM:
                        for (var j = 0; j < totals[t].values.length; j++)
                        {
                            newValue += (isNaN(totals[t].values[j])) ? 0 : totals[t].values[j];
                        };
                        break;
                    case TOT_AVG:
                        for (var j = 0; j < totals[t].values.length; j++)
                        {
                            newValue += (isNaN(totals[t].values[j])) ? 0 : totals[t].values[j];
                        };
                        newValue = newValue / totals[t].values.length;
                        break;
                };
                var i = parseInt(t);
//                if (i > 0 && !(String.valueOf(i - 1) in totals))
//                {
//					if (tbody.parentNode.parentNode.getElementsByTagName("thead")[0].rows[0].cells[i])
//					{
//						totalsRow.cells[i - 1].innerHTML = "Total " + tbody.parentNode.parentNode.getElementsByTagName("thead")[0].rows[0].cells[i].innerHTML + ":";
//					}
//					else 
//					{
//						totalsRow.cells[i - 1].innerHTML = "Total " + tbody.parentNode.parentNode.getElementsByTagName("thead")[0].rows[1].cells[i].innerHTML + ":";
//					}
//				};
				totalsRow.cells[i].innerHTML = js.util.FieldFormat.getFormattedValue(totals[t].format, newValue);
			};

        };

        //privileged methods
        this.click = function TableEditor_click(e)
        {
            var src = js.util.Events.getEventTarget(e, this.properties.window);
            if (src.tagName.toLowerCase() != "input" || src.type.toLowerCase() != "button") return true;

            var row = js.util.DOM.findAncestorNodeByTagName(src, "tr");
            switch (parseInt(src.getAttribute(js.util.ATTR_CMD_ID)))
            {
                case BTN_ADD:
                    addRow(row);
                    editTotalsRow(row.parentNode);
                    break;

                case BTN_EDIT:
                    editRow(row);
				    editTotalsRow(row.parentNode);
                    break;

                case BTN_DELETE:
                    if (!confirm('Do you wish to delete this entry?')) return false;
                    var r = row.nextSibling;
                    var p = row.parentNode;
                    var totalsRow = p.rows[findTotalsRow(p)];/*
                    while (r) {
                        // Change the alternation styles after the deleted row.
                        if (r != totalsRow) {
                            r.className = (r.className == "band") ? "" : "band";
                        } else {
							totalsRow = p.rows[findTotalsRow(p)];
						};
                        r = r.nextSibling;
                    }*/
                    p.removeChild(row);
                    editTotalsRow(p);
                    break;

                case BTN_SAVE:
                    saveRowEdit(row);
                    editTotalsRow(row.parentNode);
                    break;

                case BTN_CANCEL:
                    cancelEdit(row);
                    break;
            }

            return false;
        };

    };

    return TableEditor;
 })();