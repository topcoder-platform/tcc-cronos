/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 *
 * Transaction Assessment View Details 1.0.0
 */

/**
 * <p>
 * Default constructor. Creates a new instance.
 * </p>
 *
 * @constructor
 * @class ViewDetailsAjaxControl
 *
 * <p>
 * This Javascript class provides an AJAX control that is able to send an asynchronous request
 * to the server to retrieve the transaction details from the server and display it in the HTML
 * DOM when the response arrives.
 * </p>
 *
 * <p>
 * Thread Safety: No thread safety issues since Javascript is single-threaded.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
ViewDetailsAjaxControl = function() {
    /**
     * <p>
     * This will be used for pre table html generation. Example: &lt;table&gt;.
     * </p>
     */
    this.preTableHtmlString;

    /**
     * <p>
     * This will be used for pre table html generation. Example: &lt;/table&gt;
     * </p>
     */
    this.postTableHtmlString;

    /**
     * <p>
     * Table header template.
     * </p>
     *
     * <p>
     * Example: &lt;tr&gt;&tl;td&gt;Title1&lt;/td&gt;&lt;td&gt;Title2&lt;/td&gt;&lt;/tr&gt;.
     * </p>
     */
    this.tableHeaderHtmlString;

    /**
     * <p>
     * This will be used for pre row html generation. Example: &lt;tr&gt;.
     * </p>
     */
    this.preRowHtmlString;

    /**
     * <p>
     * This will be used for post row html generation. Example: &lt;/tr&gt;.
     * </p>
     */
    this.postRowHtmlString;

    /**
     * <p>
     * This will be used for pre data html generation. Example: &lt;td&gt;.
     * </p>
     */
    this.preDataHtmlString;

    /**
     * <p>
     * This will be used for post data html generation. Example: &lt;/td&gt;.
     * </p>
     */
    this.postDataHtmlString;

    /**
     * <p>
     * An array of strings providing the path to the transactionDetails' json field (eg: "clientIDKey").
     * Each string tells how to retrieve the value of the i-th column from the transactionDetails array
     * in the JSON returned by the server.
     * </p>
     */
    this.columns = new Array();

    /**
     * <p>
     * Retrieve the transaction details from the server and display it in the HTML DOM.
     * </p>
     *
     * <p>
     * The transaction details are retrieved by a GET request with the following parameters:
     * <ul>
     * <li>txn_id - the specified transaction ID</li>
     * <li>achvmt_posting_mth - the specified achivement posting month</li>
     * <li>txn_seq_num - the transaction sequence number</li>
     * </ul>
     * </p>
     *
     * @param {Number} transactionId the transaction id, must be positive
     * @param {String} achievementPostingMonth the posting month, cannot be null/empty
     * @param {Number} transactionNumber the transaction number identifier, must be positive
     * @param {String} prototypeElementId the id of DOM element to be updated with transaction details
     * @param {String} servletAddress the address of the http servlet which provides transaction details
     */
    this.sendRequest = function(transactionId, achievementPostingMonth, transactionNumber, prototypeElementId,
        servletAddress) {
        // The XML HTTP request object to be created
        var xmlHttp;
        if (window.XMLHttpRequest) {
            // Firefox/Opera/Safari
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            // Internet Explorer 6+
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }

        var thisControl = this;
        var requestUrl = servletAddress + "?txn_id=" + transactionId + "&achvmt_posting_mth="
            + achievementPostingMonth + "&txn_seq_num=" + achievementPostingMonth;
        xmlHttp.open("GET", requestUrl, true);
        if (xmlHttp.overrideMimeType) {
            xmlHttp.overrideMimeType("application/json");
        }
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState == 4) {
                if (xmlHttp.status == 200) {
                    if (!xmlHttp.responseText) {
                        // No response text received. Display an error message to the user.
                        alert("Did not receive a valid response from " + servletAddress);
                        return;
                    }

                    thisControl.showDetails(eval("(" + xmlHttp.responseText + ")"), prototypeElementId);
                } else {
                    alert("Invalid HTTP status code returned with the response: " + xmlHttp.status);
                }
            }
        }
        xmlHttp.send();
    }

    /**
     * <p>
     * This is the callback which will get the JSON data and populate the table.
     * </p>
     *
     * @param {Object} data the JSON data
     * @param {String} prototypeElementId the id of DOM element to be updated with transaction details
     */
    this.showDetails = function(data, prototypeElementId) {
        var prototypeElement = document.getElementById(prototypeElementId);
        var html = this.preTableHtmlString + this.tableHeaderHtmlString;

        var transactionDetails = data.transaction.transactionDetails;
        if (transactionDetails) {
            for (var i = 0; i < transactionDetails.length; i++) {
                html += this.preRowHtmlString;
                for (var j = 0; j < this.columns.length; j++) {
                    html += this.preDataHtmlString
                        + transactionDetails[i][this.columns[j]] + this.postDataHtmlString;
                }
                html += this.postRowHtmlString;
            }
        }
        html += this.postTableHtmlString;
        prototypeElement.innerHTML = html;

    }
}