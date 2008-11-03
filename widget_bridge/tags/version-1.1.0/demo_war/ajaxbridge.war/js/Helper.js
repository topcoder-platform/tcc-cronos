/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */
/**
 * @namespace js.topcoder.widgets.bridge
 * @class Helper
 *
 * Helper is a helper class providing method to validate arguments.
 *
 * This class is thread-safe because it is immutable.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.Helper = {
    /**
     * <p>
     * Checks if the given variable is defined, not <code>null</code> and is of the correct
     * type.
     * </p>
     *
     * @param {Object} variable variable to check.
     * @param {String} name the name of the variable.
     * @param {String} type the type of the variable.
     * @param {boolean} nullable true if it could be null.
     * @throws IllegalArgumentException if <code>variable</code> is not defined, <code>null</code>
     * or of wrong type.
     */
    checkDefined: function(variable, name, type, nullable){
        var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;
        if (nullable == null) {
            nullable = false;
        }

        if (typeof(variable) == "undefined") {
            throw new IllegalArgumentException(name + " is not defined.");
        }
        if (nullable && variable == null) {
            return;
        }
        if (variable == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
        if (typeof(variable) != type) {
            throw new IllegalArgumentException(name + " is not " + type);
        }
    }//checkDefined
    ,
    /**
     * <p>
     * Checks if the given number is greater or equal to the given value.
     * </p>
     *
     * @param {int} num the number to check
     * @param {String} name the name of the argument.
     * @param {int} value the value to compare <code>num</code> to.
     *
     * @throws IllegalArgumentException if <code>num</code> is not integer or less than <code>value</code>.
     */
    checkGreaterOrEqual: function(num, name, value){
        // check if num is defined and is a number
        js.topcoder.widgets.bridge.Helper.checkDefined(num, name, "number");
        var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;
        if (isNaN(value)) {
            throw new IllegalArgumentException(name + " could not be a NaN value.");
        }
        if (num < value) {
            throw new IllegalArgumentException(name + " should be greater or equal to " + value);
        }
    }//checkGreaterOrEqual
    ,
    /**
     * <p>
     * Checks if the given string is not empty after trim.
     * </p>
     *
     * @param {String} string the string to check.
     * @param {String} name the name of the argument.
     * @throws IllegalArgumentException if <code>string</code> is not string or is empty after trim.
     */
    checkString: function(string, name){
        // check if string is defined and is a string
        js.topcoder.widgets.bridge.Helper.checkDefined(string, name, "string");

        if (js.topcoder.widgets.bridge.Helper.trim(string).length == 0) {
            throw new js.topcoder.widgets.bridge.IllegalArgumentException(name + " cannot be empty after trim.");
        }
    } //checkString
    ,
    /**
     * Helper method to trim the white space of a string.
     *
     * @param {String} str the string to trim
     * @return {String} the trimmed string
     */
    trim: function(str){
        if (str == null) {
            return str;
        }
        return str.replace(/^\s*/, "").replace(/\s*$/, "");
    }
}
