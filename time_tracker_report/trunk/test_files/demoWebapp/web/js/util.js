/**
 * @package js.util
 * Provides the classes necessary to create an application and
 * the classes an application uses to communicate with its objects.
 */
 js.util =
 {
    ISOPERA :   !!(navigator && navigator.userAgent && (navigator.userAgent.indexOf('Opera') > -1)),
    ISIE4 :     !!(top && top.document && top.document.all && !this.ISOPERA),
    ISW3C :     !!(top && top.document && top.document.getElementById && !this.ISIE4),

    ATTR_CMD_ID : "cmdid",
    ATTR_ORIG_VALUE : "ovalue",
    ATTR_TOTALS : "tot",
    ATTR_REG_EXP_POINTER : "rpp",
    ATTR_REG_EXP : "rexp",
    ATTR_REQUIRED_FLD : "rqf",
    ATTR_FLD_NAME : "fldn",
    ATTR_FLD_FORMAT : "ff",
    ATTR_DATE : "date",
    ATTR_OTHER_VALIDATION : "ov"
};

/**
 * Helper class for DHTML Event manipulations.
 */
 js.util.Events = (function(){

    Events.extendsClass(js.Class);

    //public static methods
    Events.getEventTarget = function Events_getEventTarget(e, hWin)
    {
        hWin = hWin || window;
        if (js.util.ISIE4)
            return hWin && hWin.event && hWin.event.srcElement;
        else if (js.util.ISW3C)
            return e && e.target;
        return null;
    };

    //constructor
    function Events(){};

    return Events;
 })();

 /**
 * Helper class for DOM walking.
 */
 js.util.DOM = (function(){

    DOM.extendsClass(js.Class);

    //public static methods
    DOM.findAncestorNodeByTagName = function DOM_findAncestorNodeByTagName(child, tagName)
    {
        while (child.parentNode)
        {
            if (child.nodeName.toLowerCase() == tagName.toLowerCase()) return child;
            child = child.parentNode;
        };
        return null;
    };

    //constructor
    function DOM(){};

    return DOM;
 })();

 /**
 * Helper class for Field validation.
 */
 js.util.FieldValidation = (function(){

    FieldValidation.extendsClass(js.Class);

    // Regular expression to validate float.
    FieldValidation.REG_EXP_NON_NEGATIVE_FLOAT = /(^\d*\.\d*$)|(^\.\d*$)|(^\d*\.*$)|(^\d*$)/;

    /**
     * Hard validation is performed on a list of fields. If validation fails, an alert would be popped
     * out. It returns whether the validation is successful.
     */
    FieldValidation.hardValidation = function FieldValidation_hardValidation(fields)
    {
    	var errMsg = "";

    	var ovOther = null;
    	var ovDesc = null;
    	var ovStart = null;
    	var ovEnd = null;
    	var ovBillable = null;
    	var ovNonBillable = null;
    	var ovTime = null;
    	var ovExpenses = null;

    	for (var i = 0; i < fields.length; i++)
    	{
    		var field = fields[i];

            // Locate the fields that require special validation.
    	    if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION)) {
    	        if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "other") ovOther = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "desc") ovDesc = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "start") ovStart = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "end") ovEnd = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "billable") ovBillable = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "nonBillable") ovNonBillable = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "time") ovTime = field;
    	        else if (field.getAttribute(js.util.ATTR_OTHER_VALIDATION) == "expenses") ovExpenses = field;
    	    };

	    	switch (field.type)
	    	{
		        case "text":
		        	if (!js.util.FieldValidation.testRequiredField(field))
		        	{
		        	    errMsg += "\n    '" + field.getAttribute(js.util.ATTR_FLD_NAME) + "' is a required field";
		        	}
		        	else if (!js.util.FieldValidation.testTextField(field))
		        	{
		        	    if (field.getAttribute(js.util.ATTR_DATE))
		        	    {
		        	        errMsg += "\n    '" + field.getAttribute(js.util.ATTR_FLD_NAME) + "' is not a valid date";
		        	    }
		        	    else
		        	    {
		        	        errMsg += "\n    '" + field.getAttribute(js.util.ATTR_FLD_NAME) + "' is not a valid amount";
		        	    };
		        	};
		        	break;

		        case "select":
		        case "select-one":
		        case "select-multiple":
		        	if (!js.util.FieldValidation.testRequiredField(field))
		        	{
		        	    errMsg += "\n    '" + field.getAttribute(js.util.ATTR_FLD_NAME) + "' is not selected";
		        	};
		        	break;
    		};
    	};

        // the below is commented out because description is now required for all fields
    	//if (ovOther && ovDesc)
    	//{
    	//    if (ovOther.options[ovOther.selectedIndex].text == 'Other' && ovDesc.value.length == 0)
    	//    {
    	//        errMsg += "\n    '" + ovDesc.getAttribute(js.util.ATTR_FLD_NAME) + "' is required when '"
    	//                            + ovOther.getAttribute(js.util.ATTR_FLD_NAME) + "' is Other";
    	//    };
    	//};

        if (ovStart && ovEnd)
        {
            if (js.util.FieldValidation.testDateField(ovStart) && js.util.FieldValidation.testDateField(ovEnd) &&
                !js.util.FieldValidation.testStartEndDate(ovStart, ovEnd))
            {
    	        errMsg += "\n    '" + ovStart.getAttribute(js.util.ATTR_FLD_NAME) + "' should not be after '"
    	                            + ovEnd.getAttribute(js.util.ATTR_FLD_NAME) + "'";
            }
        }

        if (ovBillable && ovNonBillable)
        {
            if (!ovBillable.checked && !ovNonBillable.checked)
            {
    	        errMsg += "\n    Either '" + ovBillable.getAttribute(js.util.ATTR_FLD_NAME) + "' or '"
    	                                   + ovNonBillable.getAttribute(js.util.ATTR_FLD_NAME) + "' should be checked";
            }
        }

        if (ovTime && ovExpenses)
        {
            if (!ovTime.checked && !ovExpenses.checked)
            {
    	        errMsg += "\n    Either '" + ovTime.getAttribute(js.util.ATTR_FLD_NAME) + "' or '"
    	                                   + ovExpenses.getAttribute(js.util.ATTR_FLD_NAME) + "' should be checked";
            }
        }

    	if (errMsg.length > 0)
    	{
    		alert("Please correct the following fields:\n" + errMsg);
    		return false;
    	}
    	else
    	{
    		return true;
    	};
    };

    /**
     * Soft validation verifies if a field input is valid, and changes the input to red if it does not pass validation.
     * If the input passes validation, it is formatted to the standard form.
     */
    FieldValidation.softValidation = function FieldValidation_softValidation(field)
    {
    	switch (field.type)
    	{
	        case "text":
				field.style.color = "#000000";
				if (field.value == null || field.value == "")
				{
				    return true;
				};
	        	if (!js.util.FieldValidation.testTextField(field))
	        	{
        			field.style.color = "#ff0000";
        			return false;
	        	};
	        	field.value = js.util.FieldFormat.getFormattedFieldValue(field);
	        	break;
    	};
    	return true;
    };

    /**
     * Tests whether a text field is valid. The validation supports date or regular expression.
     */
    FieldValidation.testTextField = function FieldValidation_testTextField(field)
    {
        if (field.getAttribute(js.util.ATTR_DATE))
        {
            return js.util.FieldValidation.testDateField(field);
        };
    	var exp = null;
    	if (field.getAttribute(js.util.ATTR_REG_EXP_POINTER))
    	{
    		exp = js.util.FieldValidation[field.getAttribute(js.util.ATTR_REG_EXP_POINTER)];
    	}
    	else if (field.getAttribute(js.util.ATTR_REG_EXP))
    	{
    		exp = new RegExp(field.getAttribute(js.util.ATTR_REG_EXP));
    	};
    	return (exp) ? exp.test(field.value) : true;
    };

    /**
     * Tests whether a date is valid. The method expects the date to be MM-dd-yyyy.
     */
    FieldValidation.testDateField = function FieldValidation_testDateField(field)
    {
        try {
            if (field.value.length != 10) return false;
            var month = (field.value.substring(0, 1) == "0" ?
                parseInt(field.value.substring(1, 2)) : parseInt(field.value.substring(0, 2))) - 1;
            if (field.value.substring(2, 3) != "-") return false;
            var day = field.value.substring(3, 4) == "0" ?
                parseInt(field.value.substring(4, 5)) : parseInt(field.value.substring(3, 5));
            if (field.value.substring(5, 6) != "-") return false;
            var year = parseInt(field.value.substring(6, 10));
            var date = new Date(year, month, day);
            return (date.getYear() == year || date.getYear() == year - 1900) &&
                    date.getMonth() == month && date.getDate() == day;
        }
        catch(e)
        {
            return false;
        }
    };

    /**
     * Tests whether the start date is not after the end date.
     */
    FieldValidation.testStartEndDate = function FieldValidation_testStartEndDate(start, end)
    {
        var month1 = (start.value.substring(0, 1) == "0" ?
            parseInt(start.value.substring(1, 2)) : parseInt(start.value.substring(0, 2))) - 1;
        var day1 = start.value.substring(3, 4) == "0" ?
            parseInt(start.value.substring(4, 5)) : parseInt(start.value.substring(3, 5));
        var year1 = parseInt(start.value.substring(6, 10));
        var month2 = (end.value.substring(0, 1) == "0" ?
            parseInt(end.value.substring(1, 2)) : parseInt(end.value.substring(0, 2))) - 1;
        var day2 = end.value.substring(3, 4) == "0" ?
            parseInt(end.value.substring(4, 5)) : parseInt(end.value.substring(3, 5));
        var year2 = parseInt(end.value.substring(6, 10));
        return year2 > year1 || (year2 == year1 && (month2 > month1 || (month2 == month1 && day2 >= day1)));
    }

    /**
     * Tests is a required field has input or is selected.
     */
    FieldValidation.testRequiredField = function FieldValidation_testRequiredField(field)
    {
    	if (!field.getAttribute(js.util.ATTR_REQUIRED_FLD) || field.getAttribute(js.util.ATTR_REQUIRED_FLD) != "-1") return true;
    	switch (field.type)
    	{
    		case "text":
    			return field.value.length > 0;
    		case "select":
    		case "select-one":
    			return (field.selectedIndex >= 1);
    		case "select-multiple":
    		    for (var i = 0; i < field.options.length; i++)
    		    {
    		        if (field.options[i].selected) return true;
    		    }
    		    return false;
    	};
		return true;
    };

    //constructor
    function FieldValidation(){};

    return FieldValidation;
 })();

 /**
 * Helper class for Field formatting.
 */
 js.util.FieldFormat = (function(){

    FieldFormat.extendsClass(js.Class);

    FieldFormat.FORMAT_NUMBER_FIXED = "1";
    FieldFormat.FORMAT_NUMBER_QUARTER = "2";

    //public static methods
    FieldFormat.getFormattedFieldValue = function FieldFormat_getFormattedFieldValue(field)
    {
    	return js.util.FieldFormat.getFormattedValue(field.getAttribute(js.util.ATTR_FLD_FORMAT), field.value);
    };

    /**
     * It now supports formatting a float to hundredth or to the quarter.
     */
    FieldFormat.getFormattedValue = function FieldFormat_getFormattedValue(format, value)
    {
    	if (!format) return value;
    	var formatValues = format.split("|");
    	switch (formatValues[0])
    	{
    		case js.util.FieldFormat.FORMAT_NUMBER_FIXED:
    			if (isNaN(value)) return value;
    			return parseFloat(value).toFixed(parseInt(formatValues[1]));
            case js.util.FieldFormat.FORMAT_NUMBER_QUARTER:
    			if (isNaN(value)) return value;
    			return ((parseFloat(value)*4).toFixed(0)/4).toFixed(parseInt(formatValues[1]));
    	};
    };

    //constructor
    function FieldFormat(){};

    return FieldFormat;
 })();
