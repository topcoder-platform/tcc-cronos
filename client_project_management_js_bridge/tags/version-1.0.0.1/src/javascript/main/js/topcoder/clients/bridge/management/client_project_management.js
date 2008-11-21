/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript Client Project Management 1.0
 */

/*
 * Set the component package.
 */
function registerNS(ns) {
    var nsParts = ns.split(".");
    var root = window;

    for (var i = 0; i < nsParts.length; i++) {
        if(typeof root[nsParts[i]] == "undefined")
        root[nsParts[i]] = new Object();
        root = root[nsParts[i]];
    }
}

registerNS("js.topcoder.clients.bridge.management.model");



/**
 * <p>
 * Creates new instance of <code>Helper</code> class.
 * </p>
 *
 * @private
 * @constructor
 *
 * @class
 *
 * <p>
 * <code>Helper</code> class contains methods to check arguments for this component classes methods.
 * </p>
 * <p>
 * This class should not be instantiated. Every method can be used in a static manner.
 * </p>
 * <p>
 * This class is immutable.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.Helper = function () {
    // empty constructor
}



/**
 * <p>
 * Returns false if the given value is undefined or null.
 * </p>
 *
 * @param value the given value needs to be checked.
 *
 * @returns true if the given value is null or undefined, otherwise false.
 */
js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull = /* void */ function(/* Object */ value) {
    return ((typeof(value) == "undefined") || (value == null));
}


/**
 * <p>
 * Checks if the given variable is of the correct type.
 * </p>
 *
 * @param variable variable to check.
 * @param name the name of the variable.
 * @param type the type of the variable.
 *
 * @throws IllegalArgumentException if <code>variable</code> is wrong type.
 */
js.topcoder.clients.bridge.management.Helper.checkType =
    /* void */ function( /* Object */ variable, /* String */ name, /* String */ type) {
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(variable)) {
        return;
    }
    if (type == "Date" && (variable instanceof Date)) {
        return;
    }
    if (type == "Array" && (variable instanceof Array)) {
        return;
    }
    if (type == "Company" && (variable instanceof js.topcoder.clients.bridge.management.model.Company)) {
        return;
    }
    if (type == "ClientStatus" && (variable instanceof js.topcoder.clients.bridge.management.model.ClientStatus)) {
        return;
    }
    if (type == "ProjectStatus" && (variable instanceof js.topcoder.clients.bridge.management.model.ProjectStatus)) {
        return;
    }
    if (type == "Client" && (variable instanceof js.topcoder.clients.bridge.management.model.Client)) {
        return;
    }
    if (type == "Project" && (variable instanceof js.topcoder.clients.bridge.management.model.Project)) {
        return;
    }
    if (typeof(variable) != type) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " is not " + type);
    }
}



/**
 * <p>
 * Checks if the given variable is of the json object.
 * </p>
 *
 * @param variable variable to check.
 * @param name the name of the variable.
 *
 * @throws IllegalArgumentException if <code>variable</code> is not json object.
 */
js.topcoder.clients.bridge.management.Helper.checkJSONObject =
    /* void */ function( /* Object */ variable, /* String */ name) {
    if (typeof(variable) == "undefined") {
        return;
    }
    if (variable == null) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " should not be null");
    }
    if (typeof(variable) != "object") {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " is not json object");
    }
}



/**
 * <p>
 * Checks if the given variable is function with only one parameter.
 * </p>
 *
 * @param variable variable to check.
 * @param name the name of the variable.
 *
 * @throws IllegalArgumentException if <code>variable</code> is not function or parameter's number is not one.
 */
js.topcoder.clients.bridge.management.Helper.checkFunction =
    /* void */ function( /* Object */ variable, /* String */ name) {
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(variable)) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " should not be undefined or null");
    }
    if (typeof(variable) != "function") {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " is not function");
    }
    if (variable.length != 1) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " is with one parameter");
    }
}




/**
 * <p>
 * Checks if the given string is not empty after trim.
 * </p>
 *
 * @param value the string to check.
 * @param name the name of the argument.
 *
 * @throws IllegalArgumentException if <code>string</code> is not string or is empty after trim.
 */
js.topcoder.clients.bridge.management.Helper.checkString =
    /* void */ function( /* String */ value, /* String */ name) {
    // check if string is defined and is a string
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(value)) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " should not be undefined or null");
    }
    if (typeof(value) != "string") {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " is not string");
    }

    // check empty
    if (value.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            "The " + name + " parameter can't be empty string.");
    }
}



/**
 * <p>
 * Returns milliseconds string if the given value not is undefined or null.
 * </p>
 *
 * @param value the given date value.
 *
 * @returns milliseconds string if the given value not is undefined or null, otherwise return value.
 */
js.topcoder.clients.bridge.management.Helper.getTime = /* String */ function(/* Date */ value) {
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(value)) {
        return value;
    } else {
        return "" + value.getTime();
    }
}

/**
 * <p>
 * Returns Date instance from the milliseconds string.
 * </p>
 *
 * @param milliseconds the milliseconds string.
 *
 * @returns Date instance from the milliseconds string.
 */
js.topcoder.clients.bridge.management.Helper.createTime = /* Date */ function(/* String */ milliseconds) {
    js.topcoder.clients.bridge.management.Helper.checkType(milliseconds, "milliseconds", "string");
    if (!milliseconds.match(/^\d+$/g)) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException("milliseconds",
            "Milliseconds string should be positive integer string.");
    }
    var time = parseInt(milliseconds);
    return new Date(time);
}



/**
 * <p>
 * Returns json object if the object is defined
 * </p>
 *
 * @param value the given object
 *
 * @returns json object if the object is defined
 */
js.topcoder.clients.bridge.management.Helper.getJSON = /* JSONObject */ function(/* Object */ value) {
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(value)) {
        return value;
    } else {
        return JSON.parse(value.toJSON());
    }
}



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
js.topcoder.clients.bridge.management.Helper.checkGreaterOrEqual = function(num, name, value) {
    // check if num is defined and is a number
    if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(num)) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name,
            name + " should not be undefined or null.");
    }
    js.topcoder.clients.bridge.management.Helper.checkType(num, name, "number");
    if (num < value) {
        throw new js.topcoder.clients.bridge.management.IllegalArgumentException(name
            + " should be greater or equal to " + value);
    }
}



/**
 * <p>
 * This exception is thrown if the argument is null or invalid.
 * </p>
 * <p>
 * This class is immutable.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @class
 *
 * @param newArgumentName the argument name.
 * @param newMessage the exception message.
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.IllegalArgumentException = function ( /*String*/ newArgumentName, /* String */ newMessage) {
    /**
     * <p>
     * Represent argument name.
     * User should set non-null, non-empty value to it.
     * But it's not validated in this class.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ argumentName;

    /**
     * <p>
     * Represent error message.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ message;

    /**
     * <p>
     * Returns the argumentName field.
     * </p>
     *
     * @return the argument name
     */
    this.getArgumentName = /* String */ function() {
        return argumentName;
    }

    /**
     * <p>
     * Returns the message field.
     * </p>
     *
     * @return the message
     */
    this.getMessage = /* String */ function() {
        return message;
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    argumentName = newArgumentName;
    message = newMessage;

    /*
     * End of constructor code.
     */
}

/**
 * <p>
 * This exception is thrown by the java script service classes if the received response is invalid.
 * (e.g. the received JSON string is not in correct format.)
 * </p>
 * <p>
 * This class is immutable.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @class
 *
 * @param newServiceName the service name.
 * @param newMethodName the method name.
 * @param newMessage the exception message.
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.InvalidResponseException = function ( /*String*/ newServiceName,
    /*String*/ newMethodName, /* String */ newMessage) {

    /**
     * <p>
     * Represent service name.
     * User should set non-null, non-empty value to it.
     * But it's not validated in this class.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ serviceName;

    /**
     * <p>
     * Represent method name.
     * User should set non-null, non-empty value to it.
     * But it's not validated in this class.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ methodName;

    /**
     * <p>
     * Represent error message.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ message;

    /**
     * <p>
     * Returns the serviceName field.
     * </p>
     *
     * @return the service name
     */
    this.getServiceName = /* String */ function() {
        return serviceName;
    }

    /**
     * <p>
     * Returns the methodName field.
     * </p>
     *
     * @return the method name
     */
    this.getMethodName = /* String */ function() {
        return methodName;
    }

    /**
     * <p>
     * Returns the message field.
     * </p>
     *
     * @return the message
     */
    this.getMessage = /* String */ function() {
        return message;
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    serviceName = newServiceName;
    methodName = newMethodName;
    message = newMessage;

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the base class for all audiatble entities. This means that all extending classes
 * will have these properties.
 * Note that this is only shown for notational convenience and developers can use the
 * JS prototype pattern for the creation of this extension (or just copy the fields
 * into each descendant class).
 *
 * This class basically defines the audit elements by which we will be able to track
 * such information as who has created or last-updated this entity as well as when that happened.
 * In addition the identification of the entity is defined as well as logical flag about the deletion
 * status of the entity (in some persistence).
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @abstract
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.AuditableEntity = function (/* JSONObject */ jsonObject) {
    /**
     * <p>
     * This is the id of the auditable entity.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * In the java bean this was a long which cannot be represented in java script
     * since the largest int is 9e+15 which is smaller than 2^63 which is roughly what a long can be.
     * Here we will assume that we will use a string conversion rather than a number conversion.
     * It will be assumed that the JSON object which will carry this data will have the data
     * properly set.
     * The equivalent value should be bigger or equal -1.
     *
     * --- Utilization
     * This is used to either set or get the id for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* String */ id;

    /**
     * <p>
     * This is the name of the user who created this entity
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the create username for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* String */ createUsername;

    /**
     * <p>
     * This is the date when this entity was created.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any valid date is accepted as well as a null.
     *
     * --- Utilization
     * This is used to either set or get the create date for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* Date */ createDate;

    /**
     * <p>
     * This is the name of the user who last moodified this entity
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the modify username for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* String */ modifyUsername;

    /**
     * <p>
     * This is the date when this entity was last modified.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any valid date is accepted as well as a null.
     *
     * --- Utilization
     * This is used to either set or get the last modification date for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* Date */ modifyDate;

    /**
     * <p>
     * This is the name of this entity
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the name for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* String */ name;

    /**
     * <p>
     * This is a flag which specifies whether this entity is deleted or not.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     * It defaults to "false"
     *
     * --- Range
     * True/False
     *
     * --- Utilization
     * This is used to either set or get the deleted flag for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     * </p>
     *
     * @private
     */
    var /* Boolean */ deleted = false;

    /**
     * Getter for id field.
     *
     * @return - the id for this entity
     */
    this.getID =  /* String */ function() {
        return id;
    }

    /**
     * Setter for id field.
     *
     * @param newId the id for this entity
     *
     * @throws IllegalArgumentException if the parsed int value it less than -1
     */
    this.setID = /* void */ function(/* String */ newId) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(newId)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newId",
                "Id should not be undefined or null.");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(newId, "newId", "string");
        if (!newId.match(/^-?\d+$/g)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newId",
                "Id should be integer string.");
        }
        var idNumber = parseInt(newId);
        if (idNumber < -1) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newId",
                "Id should be greater or equal to -1.");
        }
        id = newId;
    }

    /**
     * Getter for createUsername field.
     *
     * @return the name of the user who created this entity
     */
    this.getCreateUsername =  /* String */ function() {
        return createUsername;
    }

    /**
     * Setter for createUsername field.
     *
     * @param newUsername the user name of the user who created this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setCreateUsername = /* void */ function(/* String */ newUsername) {
        js.topcoder.clients.bridge.management.Helper.checkType(newUsername, "newUsername", "string");
        createUsername = newUsername;
    }

    /**
     * Getter for createDate field.
     *
     * @return the creation date for this entity.
     */
    this.getCreateDate =  /* Date */ function() {
        return createDate;
    }

    /**
     * Setter for createDate field.
     *
     * @param newDate the date when this entity was created. Can be any value including null.
     *
     * @throws IllegalArgumentException if the input parameter is not date
     */
    this.setCreateDate = /* void */ function(/* Date */ newDate) {
        js.topcoder.clients.bridge.management.Helper.checkType(newDate, "newDate", "Date");
        createDate = newDate;
    }

    /**
     * Getter for modifyUsername field.
     *
     * @return the name of the user who last modified this entity
     */
    this.getModifyUsername =  /* String */ function() {
        return modifyUsername;
    }

    /**
     * Setter for createUsername field.
     *
     * @param newUsername the user name of the user who last modified this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setModifyUsername = /* void */ function(/* String */ newUsername) {
        js.topcoder.clients.bridge.management.Helper.checkType(newUsername, "newUsername", "string");
        modifyUsername = newUsername;
    }

    /**
     * Getter for modifyDate field.
     *
     * @return the last modification date for this entity.
     */
    this.getModifyDate =  /* Date */ function() {
        return modifyDate;
    }

    /**
     * Setter for createDate field.
     *
     * @param newDate the date that this entity was last modified. Can be any value including null.
     *
     * @throws IllegalArgumentException if the input parameter is not date
     */
    this.setModifyDate = /* void */ function(/* Date */ newDate) {
        js.topcoder.clients.bridge.management.Helper.checkType(newDate, "newDate", "Date");
        modifyDate = newDate;
    }

    /**
     * Getter for name field.
     *
     * @return the name of this entity.
     */
    this.getName =  /* String */ function() {
        return name;
    }

    /**
     * Setter for createUsername field.
     *
     * @param newName the user name of this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setName = /* void */ function(/* String */ newName) {
        js.topcoder.clients.bridge.management.Helper.checkType(newName, "newName", "string");
        name = newName;
    }

    /**
     * Getter for deleted field.
     *
     * @return true if this entity is logically deleted; false otheriwse.
     */
    this.isDeleted = /* Boolean */ function() {
        return deleted;
    }

    /**
     * Setter for deleted field.
     *
     * @param newDeleted the flag which signifies if this entity is been deleted or not.
     *
     * @throws IllegalArgumentException if the input parameter is not boolean
     */
    this.setIsDeleted = /* void */ function(/* Boolean */ newDeleted) {
        if (typeof(newDeleted) != "boolean") {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newDeleted",
                "The newDeleted is not boolean.");
        }
        deleted = newDeleted;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    js.topcoder.clients.bridge.management.model.AuditableEntity.prototype.toJSON = /* String */ function() {
        var json = {"id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in AuditableEntity");

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["id"])) {
            this.setID(jsonObject["id"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["createUsername"])) {
            this.setCreateUsername(jsonObject["createUsername"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["createDate"])) {
            var date = js.topcoder.clients.bridge.management.Helper.createTime(jsonObject["createDate"]);
            this.setCreateDate(date);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["modifyUsername"])) {
            this.setModifyUsername(jsonObject["modifyUsername"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["modifyDate"])) {
            var date = js.topcoder.clients.bridge.management.Helper.createTime(jsonObject["modifyDate"]);
            this.setModifyDate(date);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["name"])) {
            this.setName(jsonObject["name"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["deleted"])) {
            this.setIsDeleted(jsonObject["deleted"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the entity which stores client status data.
 * Note that this is an auditable entity (and thus contains all the audit fields)
 * The client status data basically stores the description of the current client status
 * for some specific client.
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.Company = function (/* JSONObject */ jsonObject) {

    /**
     * <p>
     * This is the pass code for this company.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any string value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the pass code for this company.
     *
     * @private
     */
    var /* String */ passcode;

    /**
     * Getter for passcode field.
     *
     * @return the passcode for this company.
     */
    this.getPasscode =  /* String */ function() {
        return passcode;
    }

    /**
     * Setter for passcode field.
     *
     * @param newPasscode the passcode for this company. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setPasscode = /* void */ function(/* String */ newPasscode) {
        js.topcoder.clients.bridge.management.Helper.checkType(newPasscode, "newPasscode", "string");
        passcode = newPasscode;
    }

    // Override function

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {"passcode":passcode, "id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.management.model.AuditableEntity;

    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in Company");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["passcode"])) {
            this.setPasscode(jsonObject["passcode"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the entity which stores project status data.
 * Note that this is an auditable entity (and thus contains all the audit fields)
 * The project status data basically stores the description of the current project status
 * for some specific project.
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.ProjectStatus = function (/* JSONObject */ jsonObject) {

    /**
     * This is the description of this project status.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any string value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the description for this status.
     *
     * @private
     */
    var /* String */ description;

    /**
     * Getter for description field.
     *
     * @return the project status description
     */
    this.getDescription =  /* String */ function() {
        return description;
    }

    /**
     * Setter for passcode field.
     *
     * @param newDescription the description for this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setDescription = /* void */ function(/* String */ newDescription) {
        js.topcoder.clients.bridge.management.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    // Override function

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {"description":description, "id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.management.model.AuditableEntity;

    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in ProjectStatus");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the entity which stores client status data.
 * Note that this is an auditable entity (and thus contains all the audit fields)
 * The client status data basically stores the description of the current client status
 * for some specific client.
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.ClientStatus = function (/* JSONObject */ jsonObject) {

    /**
     * This is the description of this client status.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any string value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the description for this status.
     *
     * @private
     */
    var /* String */ description;

    /**
     * Getter for description field.
     *
     * @return the project status description
     */
    this.getDescription =  /* String */ function() {
        return description;
    }

    /**
     * Setter for passcode field.
     *
     * @param newDescription the description for this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setDescription = /* void */ function(/* String */ newDescription) {
        js.topcoder.clients.bridge.management.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    // Override function

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {"description":description, "id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.management.model.AuditableEntity;

    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in ClientStatus");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the entity which stores client data.
 * Note that this is an auditable entity (and thus contains all the audit fields)
 * The client data basically stores such information as which company the client is with,
 * payment tersm, tax data, as well as start and end date for this client.
 * It also holds a code name for this client.
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.Client = function (/* JSONObject */ jsonObject) {
    /**
     * This is the company for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the copmany for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Company */ company;

    /**
     * This is the payment terms id for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * This is a number (an integer) and should be >= -1
     *
     * --- Utilization
     * This is used to either set or get the payment terms id.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Number */ paymentTermsId;

    /**
     * This is the current status of this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * any value (of the ClientStatus type) is accepted.
     *
     * --- Utilization
     * This is used to either set or get the client status for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* ClientStatus */ status;

    /**
     * This is the specific sales tax for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Should be double >= 0.
     *
     * --- Utilization
     * This is used to either set or get the sales tax for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Number */ salesTax;

    /**
     * This is the start date for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Can be any value.
     *
     * --- Utilization
     * This is used to either set or get the start date for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Date */ startDate;

    /**
     * This is the end date for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Can be any value.
     *
     * --- Utilization
     * This is used to either set or get the end date for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Date */ endDate;

    /**
     * This is the code name for this client.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Can be any value.
     *
     * --- Utilization
     * This is used to either set or get the code name for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* String */ codeName;

    /**
     * Getter for company field.
     *
     * @return the company associated with this client
     */
    this.getCompany = /* Company */ function() {
        return company;
    }

    /**
     * Setter for company field.
     *
     * @param newDescription the company associated with this client.
     *
     * @throws IllegalArgumentException if the input parameter is not Company
     */
    this.setCompany = /* void */ function(/* Company */ newCompany) {
        js.topcoder.clients.bridge.management.Helper.checkType(newCompany, "newCompany", "Company");
        company = newCompany;
    }

    /**
     * Getter for paymentTermsId field.
     *
     * @return the payment terms id for this entity.
     */
    this.getPaymentTermsId = /* Number */ function() {
        return paymentTermsId;
    }

    /**
     * Setter for paymentTermsId field.
     *
     * @param newId the payment terms id for this entity
     *
     * @throws IllegalArgumentException if the parsed int value it less than -1
     */
    this.setPaymentTermsId = /* void */ function(/* Number */ newId) {
        js.topcoder.clients.bridge.management.Helper.checkGreaterOrEqual(newId, "newId", -1);
        paymentTermsId = newId;
    }

    /**
     * Getter for status field.
     *
     * @return the client status.
     */
    this.getStatus = /* ClientStatus */ function() {
        return status;
    }

    /**
     * Setter for status field.
     *
     * @param newStatus the client status.
     *
     * @throws IllegalArgumentException if the input parameter is not ClientStatus
     */
    this.setStatus = /* void */ function(/* ClientStatus */ newStatus) {
        js.topcoder.clients.bridge.management.Helper.checkType(newStatus, "newStatus", "ClientStatus");
        status = newStatus;
    }

    /**
     * Getter for salesTax field.
     *
     * @return the sales tax for this client
     */
    this.getSalesTax = /* Number */ function() {
        return salesTax;
    }

    /**
     * Setter for salesTax field.
     *
     * @param newSalesTax the sales tax amount for this client.
     *
     * @throws IllegalArgumentException if the parsed double value it less than 0
     */
    this.setSalesTax = /* void */ function(/* Number */ newSalesTax) {
        js.topcoder.clients.bridge.management.Helper.checkGreaterOrEqual(newSalesTax, "newSalesTax", 0);
        salesTax = newSalesTax;
    }

    /**
     * Getter for startDate field.
     *
     * @return the start date for this client
     */
    this.getStartDate = /* Date */ function() {
        return startDate;
    }

    /**
     * Setter for startDate field.
     *
     * @param newStartDate the start date for this client.
     *
     * @throws IllegalArgumentException if the input parameter is not Date
     */
    this.setStartDate = /* void */ function(/* Date */ newStartDate) {
        js.topcoder.clients.bridge.management.Helper.checkType(newStartDate, "newStartDate", "Date");
        startDate = newStartDate;
    }

    /**
     * Getter for endDate field.
     *
     * @return the end date for this client
     */
    this.getEndDate = /* Date */ function() {
        return endDate;
    }

    /**
     * Setter for endDate field.
     *
     * @param newEndDate the end date for this client.
     *
     * @throws IllegalArgumentException if the input parameter is not Date
     */
    this.setEndDate = /* void */ function(/* Date */ newEndDate) {
        js.topcoder.clients.bridge.management.Helper.checkType(newEndDate, "newEndDate", "Date");
        endDate = newEndDate;
    }

    /**
     * Getter for codeName field.
     *
     * @return the code name for this client
     */
    this.getCodeName = /* String */ function() {
        return codeName;
    }

    /**
     * Setter for endDate field.
     *
     * @param newCodeName the code name for this client
     *
     * @throws IllegalArgumentException if the input parameter is not String
     */
    this.setCodeName = /* void */ function(/* String */ newCodeName) {
        js.topcoder.clients.bridge.management.Helper.checkType(newCodeName, "newCodeName", "string");
        codeName = newCodeName;
    }

    // Override function

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {
            "company":js.topcoder.clients.bridge.management.Helper.getJSON(company),
            "paymentTermsId":paymentTermsId,
            "status":js.topcoder.clients.bridge.management.Helper.getJSON(status),
            "salesTax":salesTax, "startDate":js.topcoder.clients.bridge.management.Helper.getTime(startDate),
            "endDate":js.topcoder.clients.bridge.management.Helper.getTime(endDate), "codeName":codeName,
            "id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted()
        }
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.management.model.AuditableEntity;

    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in Client");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["company"])) {
            this.setCompany(new js.topcoder.clients.bridge.management.model.Company(jsonObject["company"]));
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["paymentTermsId"])) {
            this.setPaymentTermsId(jsonObject["paymentTermsId"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["status"])) {
            this.setStatus(new js.topcoder.clients.bridge.management.model.ClientStatus(jsonObject["status"]));
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["salesTax"])) {
            this.setSalesTax(jsonObject["salesTax"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["startDate"])) {
            var date = js.topcoder.clients.bridge.management.Helper.createTime(jsonObject["startDate"]);
            this.setStartDate(date);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["endDate"])) {
            var date = js.topcoder.clients.bridge.management.Helper.createTime(jsonObject["endDate"]);
            this.setEndDate(date);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["codeName"])) {
            this.setCodeName(jsonObject["codeName"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This is the entity which stores project data.
 * Note that this is an auditable entity (and thus contains all the audit fields)
 * The project data basically stores such information as which company the project is for
 * as well as the status, description, and other specifics of the project.
 * Note that the project can have sub-projects as well.
 * </p>
 * <p>
 * Note that we do not check for circular reference. This will have to be ensured
 * by the services using this entity.
 * </p>
 * <p>
 * This class is mutable, so it's not thread safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here.
 * Anyway, the external code should be careful if several threads are used to communicate with this class.
 * </p>
 *
 * @class
 *
 * @param jsonObject the initialization data for this entity.
 *
 * @throws IllegalArgumentException if the argument is null or invalid (e.g. doesn't have corresponding properties)
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.model.Project = function (/* JSONObject */ jsonObject) {
    /**
     * This is the company for this project
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the company for this project.
     *
     * @private
     */
    var /* Company */ company;

    /**
     * This is the flag which stipulates if this project is considered active or not..
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * True or False. It is true by default.
     *
     * --- Utilization
     * This is used to either set or get the active flag for this project.
     *
     * @private
     */
    var /* Boolean */ active = true;

    /**
     * This is the specific sales tax for this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Should be double >= 0.
     *
     * --- Utilization
     * This is used to either set or get the sales tax for this client.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Number */ salesTax;

    /**
     * This is the pobox number for this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the pobox number for this project.
     *
     * @private
     */
    var /* String */ poBoxNumber;

    /**
     * This is the payment terms id for this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * This is a number (an integer) and should be >= -1
     *
     * --- Utilization
     * This is used to either set or get the payment terms id.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* Number */ paymentTermsId;

    /**
     * This is the description of this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Any string value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the description of this project.
     *
     * @private
     */
    var /* String */ description;

    /**
     * This is the current status of this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * any value (of the ClientStatus type) is accepted.
     *
     * --- Utilization
     * This is used to either set or get the client status for this project.
     *
     * @private
     */
    var /* ProjectStatus */ status;

    /**
     * This is the list ( as an array) of all the child (i.s. sub) projects for this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * Cannot be null or contain null elements.
     * Note that we could have an issue with circular references here (i.e. one of the sub
     * projects is the current project)
     * Users must ensure that no such reference exists.
     *
     * --- Utilization
     * This is used to either set or get the child projects for this project.
     *
     * @private
     */
    var /* Project[] */ childProjects = new Array();

    /**
     * This is the parent project for this project if there is a child-parent relationship.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * In the java bean this was a long which cannot be represented in java script
     * since the largest int is 9e+15 which is < 2^63 which is roughly what a long can be.
     * Here we will assume that we will use a string conversion rather than a number conversion.
     * It will be assumed that the JSON object which will carry this data will have the data properly set.
     * The equivalent value should be >= -1.
     * -1 means there is no parent.
     *
     * --- Utilization
     * This is used to either set or get the parent id for this entity.
     * It will be read during the toJSON method call and will be written through
     * the constructor or the getter.
     *
     * @private
     */
    var /* String */ parentProjectId;

    /**
     * This is the client associated with this project.
     *
     * --- Initialization
     * This can be initialized directly through the constructor or through the provided setter.
     *
     * --- Range
     * any value is accepted.
     *
     * --- Utilization
     * This is used to either set or get the client for this project.
     *
     * @private
     */
    var /* Client */ client;

    /**
     * Getter for company field.
     *
     * @return the company associated with this project
     */
    this.getCompany = /* Company */ function() {
        return company;
    }

    /**
     * Setter for company field.
     *
     * @param newDescription the company associated with this project.
     *
     * @throws IllegalArgumentException if the input parameter is not Company
     */
    this.setCompany = /* void */ function(/* Company */ newCompany) {
        js.topcoder.clients.bridge.management.Helper.checkType(newCompany, "newCompany", "Company");
        company = newCompany;
    }

    /**
     * Getter for active field.
     *
     * @return the active flag for this project
     */
    this.isActive = /* Boolean */ function() {
        return active;
    }

    /**
     * Setter for active field.
     *
     * @param newActive true if the project is active and false otherwise
     *
     * @throws IllegalArgumentException if the input parameter is not Boolean
     */
    this.setIsActive = /* void */ function(/* Boolean */ newActive) {
        if (typeof(newActive) != "boolean") {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newActive",
                "The newActive is not boolean.");
        }
        active = newActive;
    }

    /**
     * Getter for salesTax field.
     *
     * @return the sales tax for this client
     */
    this.getSalesTax = /* Number */ function() {
        return salesTax;
    }

    /**
     * Setter for salesTax field.
     *
     * @param newSalesTax the sales tax amount for this client.
     *
     * @throws IllegalArgumentException if the parsed double value it less than 0
     */
    this.setSalesTax = /* void */ function(/* Number */ newSalesTax) {
        js.topcoder.clients.bridge.management.Helper.checkGreaterOrEqual(newSalesTax, "newSalesTax", 0);
        salesTax = newSalesTax;
    }

    /**
     * Getter for poBoxNumber field.
     *
     * @return the p.o.box number associated with this project
     */
    this.getPOBoxNumber = /* String */ function() {
        return poBoxNumber;
    }

    /**
     * Setter for poBoxNumber field.
     *
     * @param newSalesTax the p.o.box number associated with this project
     *
     * @throws IllegalArgumentException if the input parameter is not String
     */
    this.setPOBoxNumber = /* void */ function(/* String */ newPOBoxNumber) {
        js.topcoder.clients.bridge.management.Helper.checkType(newPOBoxNumber, "newPOBoxNumber", "string");
        poBoxNumber = newPOBoxNumber;
    }

    /**
     * Getter for paymentTermsId field.
     *
     * @return the payment terms id for this entity.
     */
    this.getPaymentTermsId = /* Number */ function() {
        return paymentTermsId;
    }

    /**
     * Setter for paymentTermsId field.
     *
     * @param newId the payment terms id for this entity
     *
     * @throws IllegalArgumentException if the parsed int value it less than -1
     */
    this.setPaymentTermsId = /* void */ function(/* Number */ newId) {
        js.topcoder.clients.bridge.management.Helper.checkGreaterOrEqual(newId, "newId", -1);
        paymentTermsId = newId;
    }

    /**
     * Getter for description field.
     *
     * @return the project description
     */
    this.getDescription = /* String */ function() {
        return description;
    }

    /**
     * Setter for description field.
     *
     * @param newDescription the project description
     *
     * @throws IllegalArgumentException if the input parameter is not String
     */
    this.setDescription = /* void */ function(/* String */ newDescription) {
        js.topcoder.clients.bridge.management.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    /**
     * Getter for status field.
     *
     * @return the project status.
     */
    this.getStatus = /* ProjectStatus */ function() {
        return status;
    }

    /**
     * Setter for status field.
     *
     * @param newStatus the project status.
     *
     * @throws IllegalArgumentException if the input parameter is not ProjectStatus
     */
    this.setStatus = /* void */ function(/* ProjectStatus */ newStatus) {
        js.topcoder.clients.bridge.management.Helper.checkType(newStatus, "newStatus", "ProjectStatus");
        status = newStatus;
    }

    /**
     * Getter for client field.
     *
     * @return the client associated with this project
     */
    this.getClient = /* Client */ function() {
        return client;
    }

    /**
     * Setter for client field.
     *
     * @param newClient the client associated with this project
     *
     * @throws IllegalArgumentException if the input parameter is not Client
     */
    this.setClient = /* void */ function(/* Client */ newClient) {
        js.topcoder.clients.bridge.management.Helper.checkType(newClient, "newClient", "Client");
        client = newClient;
    }

    /**
     * Getter for childProjects field.
     *
     * @return an array of sub projects associated with this project
     */
    this.getChildProjects = /* Project[] */ function() {
        return childProjects;
    }

    /**
     * Setter for childProjects field.
     *
     * @param newChildProjects an array of sub projects associated with this project
     *
     * @throws IllegalArgumentException if the input parameter is null or not Project[], or contains null element
     */
    this.setChildProjects = /* void */ function(/* Project[] */ newChildProjects) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(newChildProjects)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newChildProjects",
                "newChildProjects should not be undefined or null.");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(newChildProjects, "newChildProjects", "Array");
        for (var i = 0; i < newChildProjects.length; i++) {
            if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(newChildProjects[i])) {
                throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newChildProjects[" + i + "]",
                    "newChildProjects[" + i + "] should not be undefined or null.");
            }
            js.topcoder.clients.bridge.management.Helper.checkType(newChildProjects[i],
                "newChildProjects[" + i + "]", "Project");
        }
        childProjects = newChildProjects;
    }

    /**
     * Getter for parentProjectID field.
     *
     * @return an the id of the parent project
     */
    this.getParentProjectID = /* String */ function() {
        return parentProjectId;
    }

    /**
     * Setter for parentProjectID field.
     *
     * @param newParentProjectID The id of the parent project. If -1 there is no parent project
     *
     * @throws IllegalArgumentException if the parsed int value it less than -1
     */
    this.setParentProjectID = /* void */ function(/* String */ newParentProjectID) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(newParentProjectID)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newParentProjectID",
                "newParentProjectID should not be undefined or null.");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(newParentProjectID, "newParentProjectID", "string");
        if (!newParentProjectID.match(/^-?\d+$/g)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newParentProjectID",
                "newParentProjectID should be integer string.");
        }

        var idNumber = parseInt(newParentProjectID);
        if (idNumber < -1) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("newId",
                "newParentProjectID should be greater or equal to -1.");
        }
        parentProjectId = newParentProjectID;
    }

    // Override function

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * @return - the JSON string for this entity.
     */
    this.toJSON = /* JSONObject */ function() {
        var arrayProject = new Array();
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(childProjects)) {
            for (var i = 0; i < childProjects.length; i++) {
                arrayProject.push(JSON.parse(childProjects[i].toJSON()));
            }
        }

        var json = {
            "company":js.topcoder.clients.bridge.management.Helper.getJSON(company),
            "active":active, "salesTax":salesTax, "poBoxNumber":poBoxNumber,
            "paymentTermsId":paymentTermsId, "description":description,
            "status":js.topcoder.clients.bridge.management.Helper.getJSON(status),
            "client":js.topcoder.clients.bridge.management.Helper.getJSON(client),
            "childProjects":arrayProject, "parentProjectId":parentProjectId,

            "id":this.getID(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.management.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted()
        }
        return JSON.stringify(json);
    }


    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.management.model.AuditableEntity;

    js.topcoder.clients.bridge.management.Helper.checkJSONObject(jsonObject, "jsonObject in Project");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["company"])) {
            this.setCompany(new js.topcoder.clients.bridge.management.model.Company(jsonObject["company"]));
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["active"])) {
            this.setIsActive(jsonObject["active"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["salesTax"])) {
            this.setSalesTax(jsonObject["salesTax"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["poBoxNumber"])) {
            this.setPOBoxNumber(jsonObject["poBoxNumber"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["paymentTermsId"])) {
            this.setPaymentTermsId(jsonObject["paymentTermsId"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["status"])) {
            this.setStatus(new js.topcoder.clients.bridge.management.model.ProjectStatus(jsonObject["status"]));
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["client"])) {
            this.setClient(new js.topcoder.clients.bridge.management.model.Client(jsonObject["client"]));
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["childProjects"])) {
            for (var i = 0; i < jsonObject["childProjects"].length; i++) {
                childProjects.push(new js.topcoder.clients.bridge.management.model.Project(
                    jsonObject["childProjects"][i]));
            }
        }
        if (!js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(jsonObject["parentProjectId"])) {
            this.setParentProjectID(jsonObject["parentProjectId"]);
        }
    }

    /*
     * End of constructor code.
     */
}


/**
 * <p>
 * This JavaScript class defines the operations related to the company service.
 * It will use the AJAX Processor js component to communicate with the AjaxServlet.
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is
 * completed in asynchronous mode, so when the operation is completed.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @class
 *
 * @param newServletUrl the servlet url which will process the requests
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.CompanyService = function ( /*String*/ newServletUrl ) {
    /**
     * Represents the servlet url to communicate with.
     *
     * --- initialization
     * Initialized in constructor, and never changed afterwards.
     *
     * --- Range
     * It must be non-null, non-empty string.
     *
     * --- Utilisation
     * This is used to make the calls via AJAXprocessor to the servlet.
     *
     * @private
     * @final
     */
    var /* String */ servletUrl;

    /**
     * Create the Company asynchronously, if the company is created successfully,
     * onSuccess callback function will be called with the created company,
     * otherwise onError will be called.
     *
     * @param company the company to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.createCompany = /* void */ function( /* Company */ company, /* CompanyHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(company, onSuccess, onError, "createCompany");
    }

    /**
     * Update the Company asynchronously, if the Company is updated successfully,
     * onSuccess callback function will be called with the updated Company, otherwise
     * onError will be called.
     *
     * @param Company the Company to update.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.updateCompany = /* void */ function( /* Company */ company, /* CompanyHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(company, onSuccess, onError, "updateCompany");
    }

    /**
     * Delete the Company asynchronously, if the Company is deleted successfully,
     * onSuccess callback function will be called, otherwise onError will be called.
     *
     * @param Company the Company to delete.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.deleteCompany = /* void */ function( /* Company */ company, /* CompanyHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(company, onSuccess, onError, "deleteCompany");
    }

    /**
     * The method do the ajax process.
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param company the company to process
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function(/* Company */ company, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError, /* String */ methodStr) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(company)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("company",
                "The company should not be undefined or null");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(company, "company", "Company");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onError, "onError");
        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=CompanyService&method=" + methodStr + "&company=" + company.toJSON(),
            method: "POST",
            async: false,
            responseType : "text"
        });

        if (processor.getStatus() == 200) {
            var response = processor.getResponseText();
            // evaluate the response into a json object: jsonResp.
            var jsonResp = JSON.parse(response);
            if (jsonResp["success"] == true) {
                var company = new js.topcoder.clients.bridge.management.model.Company(jsonResp["json"]);
                onSuccess(company);
            } else {
                onError(jsonResp["error"]);
            }
        } else {
            throw new js.topcoder.clients.bridge.management.InvalidResponseException("CompanyService",
                methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
        }
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.management.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This JavaScript class defines the operations related to the client service.
 * It will use the AJAX Processor js component to communicate with the AjaxServlet.
 *
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is
 * completed in asynchronous mode, so when the operation is completed successfully, onSuccess will be called,
 * otherwise, onError will be called to notify user an error occured.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @class
 *
 * @param newServletUrl the servlet url which will process the requests
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.ClientService = function ( /*String*/ newServletUrl ) {
    /**
     * Represents the servlet url to communicate with.
     *
     * --- initialization
     * Initialized in constructor, and never changed afterwards.
     *
     * --- Range
     * It must be non-null, non-empty string.
     *
     * --- Utilisation
     * This is used to make the calls via AJAXprocessor to the servlet.
     *
     * @private
     * @final
     */
    var /* String */ servletUrl;

    /**
     * Create the Client asynchronously, if the client is created successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param client the client to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.createClient = /* void */ function( /* Client */ client, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(client, onSuccess, onError, "createClient", "");
    }

    /**
     * Update the Client asynchronously, if the Client is updated successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param client the client to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.updateClient = /* void */ function( /* Client */ client, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(client, onSuccess, onError, "updateClient", "");
    }

    /**
     * Delete the Client asynchronously, if the client is deleted successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param client the client to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.deleteClient = /* void */ function( /* Client */ client, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(client, onSuccess, onError, "deleteClient", "");
    }

    /**
     * <p>
     * Set the client code name asynchronously, if the update is done successfully,
     * onSuccess callback function will be called with the updated client,
     * otherwise onError will be called.
     *
     * @param client the client to update
     * @param name the client code name to update.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.setClientCodeName = /* void */ function(/* Client */ client, /* String */ name,
        /* ClientHandler */ onSuccess, /* ErrorHandler */ onError) {
        js.topcoder.clients.bridge.management.Helper.checkString(name, "name");
        doProcess(client, onSuccess, onError, "setClientCodeName", "&name=" + name);
    }

    /**
     * <p>
     * Set the client status for the given client asynchronously, if the update is done successfully,
     * onSuccess callback function will be called with the updated client,
     * otherwise onError will be called.
     *
     * @param client the client to update
     * @param status the client status to update the client with.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.setClientStatus = /* void */ function(/* Client */ client, /* ClientStatus */ status,
        /* ClientHandler */ onSuccess, /* ErrorHandler */ onError) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(status)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("status",
                "The status should not be undefined or null");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(status, "status", "ClientStatus");
        doProcess(client, onSuccess, onError, "setClientStatus", "&status=" + status.toJSON());
    }

    /**
     * The method do the ajax process.
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param client the client to process
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     * @param AddParameter the added parameter to url
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function(/* Client */ client, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError, /* String */ methodStr, /* String */ AddParameter) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(client)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("client",
                "The client should not be undefined or null");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(client, "client", "Client");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onError, "onError");

        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=ClientService&method=" + methodStr + "&client=" + client.toJSON()
                + AddParameter,
            method: "POST",
            async: false,
            responseType : "text"
        });

        if (processor.getStatus() == 200) {
            var response = processor.getResponseText();
            // evaluate the response into a json object: jsonResp.
            var jsonResp = JSON.parse(response);
            if (jsonResp["success"] == true) {
                var client = new js.topcoder.clients.bridge.management.model.Client(jsonResp["json"]);
                onSuccess(client);
            } else {
                onError(jsonResp["error"]);
            }
        } else {
            throw new js.topcoder.clients.bridge.management.InvalidResponseException("ClientService",
                methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
        }
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.management.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This JavaScript class defines the operations related to the project service.
 *
 * It will use the AJAX Processor js component to communicate with the AjaxServlet.
 *
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is
 * completed in asynchronous mode, so when the operation is completed successfully, onSuccess will be called,
 * otherwise, onError will be called to notify user an error occured.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * Thread-safety is not handled since it is a JavaScript class.
 * </p>
 *
 * @class
 *
 * @param newServletUrl the servlet url which will process the requests
 *
 * @constructor
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.clients.bridge.management.ProjectService = function ( /*String*/ newServletUrl ) {
    /**
     * Represents the servlet url to communicate with.
     *
     * --- initialization
     * Initialized in constructor, and never changed afterwards.
     *
     * --- Range
     * It must be non-null, non-empty string.
     *
     * --- Utilisation
     * This is used to make the calls via AJAXprocessor to the servlet.
     *
     * @private
     * @final
     */
    var /* String */ servletUrl;

    /**
     * Create the Project asynchronously, if the project is created successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param project the project to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.createProject = /* void */ function( /* Project */ project, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(project, onSuccess, onError, "createProject", "");
    }

    /**
     * Update the Project asynchronously, if the project is updated successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param project the project to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.updateProject = /* void */ function( /* Project */ project, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(project, onSuccess, onError, "updateProject", "");
    }

    /**
     * Delete the Project asynchronously, if the project is deleted successfully,
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param project the project to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.deleteProject = /* void */ function( /* Project */ project, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(project, onSuccess, onError, "deleteProject", "");
    }

    /**
     * Create the association between the ProjectStatus and the Project, if the action is successful,
     * onSuccess callback function will be called with the updated project,
     * otherwise onError will be called.
     *
     * @param project the project to create.
     * @param status the project status to associate
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.setProjectStatus = /* void */ function( /* Project */ project, /* ProjectStatus */ status,
        /* ClientHandler */ onSuccess, /* ErrorHandler */ onError) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(status)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("status",
                "The status should not be undefined or null");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(status, "status", "ProjectStatus");
        doProcess(project, onSuccess, onError, "setProjectStatus", "&status=" + status.toJSON());
    }

    /**
     * The method do the ajax process.
     * onSuccess callback function will be called with the created client,
     * otherwise onError will be called.
     *
     * @param project the project to process
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     * @param AddParameter the added parameter to url
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function(/* Project */ project, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError, /* String */ methodStr, /* String */ AddParameter) {
        if (js.topcoder.clients.bridge.management.Helper.isUndefinedOrNull(project)) {
            throw new js.topcoder.clients.bridge.management.IllegalArgumentException("project",
                "The project should not be undefined or null");
        }
        js.topcoder.clients.bridge.management.Helper.checkType(project, "project", "Project");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.management.Helper.checkFunction(onError, "onError");

        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=ProjectService&method=" + methodStr + "&project=" + project.toJSON()
                + AddParameter,
            method: "POST",
            async: false,
            responseType : "text"
        });

        if (processor.getStatus() == 200) {
            var response = processor.getResponseText();
            // evaluate the response into a json object: jsonResp.
            var jsonResp = JSON.parse(response);
            if (jsonResp["success"] == true) {
                var project = new js.topcoder.clients.bridge.management.model.Project(jsonResp["json"]);
                onSuccess(project);
            } else {
                onError(jsonResp["error"]);
            }
        } else {
            throw new js.topcoder.clients.bridge.management.InvalidResponseException("ProjectService",
                methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
        }
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.management.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}