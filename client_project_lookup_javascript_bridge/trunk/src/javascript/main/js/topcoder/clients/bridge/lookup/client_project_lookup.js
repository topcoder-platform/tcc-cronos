/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript Client Project Lookup 1.0
 */

/*
 * Set the component package.
 */
function registerNS(ns) {
    var nsParts = ns.split(".");
    var root = window;

    for (var i = 0; i < nsParts.length; i++) {
        if(typeof root[nsParts[i]] == "undefined"){
            root[nsParts[i]] = new Object();
        }
        root = root[nsParts[i]];
    }
}

registerNS("js.topcoder.clients.bridge.lookup.entities");



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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.Helper = function () {
    // empty constructor
}



/**
 * <p>
 * Returns true if the given value is undefined or null.
 * </p>
 *
 * @param value the given value needs to be checked.
 *
 * @returns true if the given value is null or undefined, otherwise false.
 */
js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull = /* void */ function(/* Object */ value) {
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
js.topcoder.clients.bridge.lookup.Helper.checkType =
    /* void */ function( /* Object */ variable, /* String */ name, /* String */ type) {
    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(variable)) {
        return;
    }
    if (type == "Date" && (variable instanceof Date)) {
        return;
    }
    if (type == "Array" && (variable instanceof Array)) {
        return;
    }
    if (type == "Company" && (variable instanceof js.topcoder.clients.bridge.lookup.entities.Company)) {
        return;
    }
    if (type == "ClientStatus" && (variable instanceof js.topcoder.clients.bridge.lookup.entities.ClientStatus)) {
        return;
    }
    if (type == "ProjectStatus" && (variable instanceof js.topcoder.clients.bridge.lookup.entities.ProjectStatus)) {
        return;
    }
    if (type == "Client" && (variable instanceof js.topcoder.clients.bridge.lookup.entities.Client)) {
        return;
    }
    if (type == "Project" && (variable instanceof js.topcoder.clients.bridge.lookup.entities.Project)) {
        return;
    }
    if (typeof(variable) != type) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
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
js.topcoder.clients.bridge.lookup.Helper.checkJSONObject =
    /* void */ function( /* Object */ variable, /* String */ name) {
    if (typeof(variable) == "undefined") {
        return;
    }
    if (variable == null) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " should not be null");
    }
    if (typeof(variable) != "object") {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
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
js.topcoder.clients.bridge.lookup.Helper.checkFunction =
    /* void */ function( /* Object */ variable, /* String */ name) {
    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(variable)) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " should not be undefined or null");
    }
    if (typeof(variable) != "function") {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " is not function");
    }
    if (variable.length != 1) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
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
js.topcoder.clients.bridge.lookup.Helper.checkString =
    /* void */ function( /* String */ value, /* String */ name) {
    // check if string is defined and is a string
    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(value)) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " should not be undefined or null");
    }
    if (typeof(value) != "string") {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " is not string");
    }

    // check empty
    if (value.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException(name,
            "The " + name + " parameter can't be empty string.");
    }
}



/**
 * <p>
 * Returns milliseconds string if the given value is not undefined or null.
 * </p>
 *
 * @param value the given date value.
 *
 * @returns milliseconds string if the given value not is undefined or null, otherwise return value.
 */
js.topcoder.clients.bridge.lookup.Helper.getTime = /* String */ function(/* Date */ value) {
    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(value)) {
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
js.topcoder.clients.bridge.lookup.Helper.createTime = /* Date */ function(/* String */ milliseconds) {
    js.topcoder.clients.bridge.lookup.Helper.checkType(milliseconds, "milliseconds", "string");
    if (!milliseconds.match(/^\d+$/g)) {
        throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException("milliseconds",
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
js.topcoder.clients.bridge.lookup.Helper.getJSON = /* JSONObject */ function(/* Object */ value) {
    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(value)) {
        return value;
    } else {
        return JSON.parse(value.toJSON());
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.IllegalArgumentException = function ( /*String*/ newArgumentName, /* String */ newMessage) {
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.InvalidResponseException = function ( /*String*/ newServiceName,
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
 * This Java Script object represents the base entity for other entities,
 * all its variables and methods should be defined in subclasses.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.BaseEntity = function (/* JSONObject */ jsonObject) {
    /**
     * <p>
     * This field represents the 'id' property.
     * Represents the identifier of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.id [Entity.getId()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Number */ id;

    /**
     * <p>
     * This field represents the 'createUsername' property.
     * Represents the creation user of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.createUsername [Entity.getCreateUsername()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* String */ createUsername;

    /**
     * <p>
     * This field represents the 'createDate' property.
     * Represents the creation date of the entity.
     *
     * INITIAL VALUE:
     *
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.createDate [Entity.getCreateDate()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Date */ createDate;

    /**
     * <p>
     * This field represents the 'modifyUsername' property.
     * Represents the modification user of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.modifyUsername [Entity.getModifyUsername()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* String */ modifyUsername;

    /**
     * <p>
     * This field represents the 'modifyDate' property.
     * Represents the modification date of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.modifyDate [Entity.getModifyDate()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Date */ modifyDate;

    /**
     * <p>
     * This field represents the 'name' property.
     * Represents the name of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.name [Entity.getName()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* String */ name;

    /**
     * <p>
     * This field represents the 'deleted' property.
     * Represents the deleted flag of the entity.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Entity.deleted [Entity.isDeleted()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Boolean */ deleted;

    /**
     * Getter for id field.
     *
     * @return the id for this entity
     */
    this.getId =  /* Number */ function() {
        return id;
    }

    /**
     * Setter for id field.
     *
     * @param newId the id for this entity
     *
     * @throws IllegalArgumentException if the input parameter is not number
     */
    this.setId = /* void */ function(/* Number */ newId) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newId, "newId", "number");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newUsername, "newUsername", "string");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDate, "newDate", "Date");
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
     * Setter for modifyUsername field.
     *
     * @param newUsername the user name of the user who last modified this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setModifyUsername = /* void */ function(/* String */ newUsername) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newUsername, "newUsername", "string");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDate, "newDate", "Date");
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
     * Setter for name field.
     *
     * @param newName the user name of this entity. Can be any value.
     *
     * @throws IllegalArgumentException if the input parameter is not string
     */
    this.setName = /* void */ function(/* String */ newName) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newName, "newName", "string");
        name = newName;
    }

    /**
     * Getter for deleted field.
     *
     * @return true if this entity is logically deleted; false otherwise.
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
    this.setDeleted = /* void */ function(/* Boolean */ newDeleted) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDeleted, "newDeleted", "boolean");
        deleted = newDeleted;
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in BaseEntity");

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["id"])) {
            this.setId(jsonObject["id"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["createUsername"])) {
            this.setCreateUsername(jsonObject["createUsername"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["createDate"])) {
            var date = js.topcoder.clients.bridge.lookup.Helper.createTime(jsonObject["createDate"]);
            this.setCreateDate(date);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["modifyUsername"])) {
            this.setModifyUsername(jsonObject["modifyUsername"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["modifyDate"])) {
            var date = js.topcoder.clients.bridge.lookup.Helper.createTime(jsonObject["modifyDate"]);
            this.setModifyDate(date);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["name"])) {
            this.setName(jsonObject["name"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["deleted"])) {
            this.setDeleted(jsonObject["deleted"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This Java Script object represents the company data.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.Company = function (/* JSONObject */ jsonObject) {

    /**
     * <p>
     * This field represents the 'passcode' property of the Company.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Company.passcode [Company.getPasscode()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newPasscode, "newPasscode", "string");
        passcode = newPasscode;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * The json format is:
     * { "passcode":passcode, "id":id, "createUsername":createUsername,
     * "createDate":createDate, "modifyUsername":modifyUsername,
     * "modifyDate":modifyDate, "name":name, "deleted":deleted };
     *
     * @return the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {"passcode":passcode, "id":this.getId(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.lookup.entities.BaseEntity;

    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in Company");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["passcode"])) {
            this.setPasscode(jsonObject["passcode"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This Java Script object represents the project status data.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.ProjectStatus = function (/* JSONObject */ jsonObject) {

    /**
     * <p>
     * This field represents the 'description' property of the ProjectStatus.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from ProjectStatus.description [ProjectStatus.getDescription()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * The json format is:
     * { "description":description, "id":id, "createUsername":createUsername,
     * "createDate":createDate, "modifyUsername":modifyUsername,
     * "modifyDate":modifyDate, "name":name, "deleted":deleted };
     *
     * @return the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {"description":description, "id":this.getId(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.lookup.entities.BaseEntity;

    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in ProjectStatus");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This Java Script object represents the client status data.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.ClientStatus = function (/* JSONObject */ jsonObject) {

    /**
     * <p>
     * This field represents the 'description' property of the ClientStatus.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from ClientStatus.description [ClientStatus.getDescription()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * The json format is:
     * { "description":description, "id":id, "createUsername":createUsername,
     * "createDate":createDate, "modifyUsername":modifyUsername,
     * "modifyDate":modifyDate, "name":name, "deleted":deleted };
     *
     * @return the JSON string for this entity.
     *
     */
    this.toJSON = /* String */ function() {
        var json = {"description":description, "id":this.getId(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted() };
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.lookup.entities.BaseEntity;

    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in ClientStatus");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This Java Script object represents the client data.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.Client = function (/* JSONObject */ jsonObject) {
    /**
     * <p>
     * This field represents the 'company' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.company [Client.getCompany()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Company */ company;

    /**
     * <p>
     * This field represents the 'paymentTermsId' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.paymentTermsId [Client.getPaymentTermsId()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Number */ paymentTermsId;

    /**
     * <p>
     * This field represents the 'clientStatus' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.clientStatus [Client.getClientStatus()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* ClientStatus */ clientStatus;

    /**
     * <p>
     * This field represents the 'salesTax' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.salesTax [Client.getSalesTax()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Number */ salesTax;

    /**
     * <p>
     * This field represents the 'startDate' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.startDate [Client.getStartDate()].
     *
     * VALID VALUES:
     There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Date */ startDate;

    /**
     * <p>
     * This field represents the 'endDate' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.endDate [Client.getEndDate()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Date */ endDate;

    /**
     * <p>
     * This field represents the 'codeName' property of the Client.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Client.codeName [Client.getCodeName()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newCompany, "newCompany", "Company");
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
     * @throws IllegalArgumentException if the input parameter is not number
     */
    this.setPaymentTermsId = /* void */ function(/* Number */ newId) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newId, "newId", "number");
        paymentTermsId = newId;
    }

    /**
     * Getter for clientStatus field.
     *
     * @return the client status.
     */
    this.getClientStatus = /* ClientStatus */ function() {
        return clientStatus;
    }

    /**
     * Setter for clientStatus field.
     *
     * @param newStatus the client status.
     *
     * @throws IllegalArgumentException if the input parameter is not ClientStatus
     */
    this.setClientStatus = /* void */ function(/* ClientStatus */ newStatus) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newStatus, "newStatus", "ClientStatus");
        clientStatus = newStatus;
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
     * @throws IllegalArgumentException if if the input parameter is not number
     */
    this.setSalesTax = /* void */ function(/* Number */ newSalesTax) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newSalesTax, "newSalesTax", "number");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newStartDate, "newStartDate", "Date");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newEndDate, "newEndDate", "Date");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newCodeName, "newCodeName", "string");
        codeName = newCodeName;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * The json format is:
     * { "company":company, "paymentTermsId":paymentTermsId, "clientStatus":clientStatus,
     * "salesTax":salesTax, "startDate":startDate, "endDate":endDate, "codeName":codeName,
     * "id":id, "createUsername":createUsername,
     * "createDate":createDate, "modifyUsername":modifyUsername,
     * "modifyDate":modifyDate, "name":name, "deleted":deleted };
     *
     * @return the JSON string for this entity.
     */
    this.toJSON = /* String */ function() {
        var json = {
            "company":js.topcoder.clients.bridge.lookup.Helper.getJSON(company),
            "paymentTermsId":paymentTermsId,
            "clientStatus":js.topcoder.clients.bridge.lookup.Helper.getJSON(clientStatus),
            "salesTax":salesTax, "startDate":js.topcoder.clients.bridge.lookup.Helper.getTime(startDate),
            "endDate":js.topcoder.clients.bridge.lookup.Helper.getTime(endDate), "codeName":codeName,
            "id":this.getId(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted()
        }
        return JSON.stringify(json);
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.lookup.entities.BaseEntity;

    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in Client");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["company"])) {
            this.setCompany(new js.topcoder.clients.bridge.lookup.entities.Company(jsonObject["company"]));
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["paymentTermsId"])) {
            this.setPaymentTermsId(jsonObject["paymentTermsId"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["clientStatus"])) {
            this.setClientStatus(new js.topcoder.clients.bridge.lookup.entities.ClientStatus(jsonObject["clientStatus"]));
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["salesTax"])) {
            this.setSalesTax(jsonObject["salesTax"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["startDate"])) {
            var date = js.topcoder.clients.bridge.lookup.Helper.createTime(jsonObject["startDate"]);
            this.setStartDate(date);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["endDate"])) {
            var date = js.topcoder.clients.bridge.lookup.Helper.createTime(jsonObject["endDate"]);
            this.setEndDate(date);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["codeName"])) {
            this.setCodeName(jsonObject["codeName"]);
        }
    }

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This Java Script object represents the project data.
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.entities.Project = function (/* JSONObject */ jsonObject) {
    /**
     * <p>
     * This field represents the 'company' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.company [Project.getCompany()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Company */ company;

    /**
     * <p>
     * This field represents the 'active' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.active [Project.isActive()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Boolean */ active;

    /**
     * <p>
     * This field represents the 'salesTax' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.salesTax [Project.getSalesTax()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Number */ salesTax;

    /**
     * <p>
     * This field represents the 'pOBoxNumber' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.pOBoxNumber [Project.getPOBoxNumber()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* String */ pOBoxNumber;

    /**
     * <p>
     * This field represents the 'paymentTermsId' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.paymentTermsId [Project.getPaymentTermsId()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     * @private
     */
    var /* Number */ paymentTermsId;

    /**
     * <p>
     * This field represents the 'description' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.description [Project.getDescription()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* String */ description;

    /**
     * <p>
     * This field represents the 'projectStatus' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.projectStatus [Project.getProjectStatus()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* ProjectStatus */ projectStatus;

    /**
     * <p>
     * This field represents the 'childProjects' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.childProjects [Project.getChildProjects()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Project[] */ childProjects = new Array();

    /**
     * <p>
     * This field represents the 'parentProjectId' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.parentProjectId [Project.getParentProjectId()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
     *
     * @private
     */
    var /* Number */ parentProjectId;

    /**
     * <p>
     * This field represents the 'client' property of the Project.
     *
     * INITIAL VALUE:
     * It is default to the default value of this data type when it is not assigned.
     *
     * ACCESSED:
     * Thru corresponding getter or setter methods.
     * It is retrieved from Project.client [Project.getClient()].
     *
     * VALID VALUES:
     * There are no restrictions at this moment. It can take any value. OPTIONAL.
     * </p>
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newCompany, "newCompany", "Company");
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
    this.setActive = /* void */ function(/* Boolean */ newActive) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newActive, "newActive", "boolean");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newSalesTax, "newSalesTax", "number");
        salesTax = newSalesTax;
    }

    /**
     * Getter for pOBoxNumber field.
     *
     * @return the p.o.box number associated with this project
     */
    this.getPOBoxNumber = /* String */ function() {
        return pOBoxNumber;
    }

    /**
     * Setter for pOBoxNumber field.
     *
     * @param newSalesTax the p.o.box number associated with this project
     *
     * @throws IllegalArgumentException if the input parameter is not String
     */
    this.setPOBoxNumber = /* void */ function(/* String */ newPOBoxNumber) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newPOBoxNumber, "newPOBoxNumber", "string");
        pOBoxNumber = newPOBoxNumber;
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
     * @throws IllegalArgumentException if the input parameter is not number
     */
    this.setPaymentTermsId = /* void */ function(/* Number */ newId) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newId, "newId", "number");
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newDescription, "newDescription", "string");
        description = newDescription;
    }

    /**
     * Getter for projectStatus field.
     *
     * @return the project status.
     */
    this.getProjectStatus = /* ProjectStatus */ function() {
        return projectStatus;
    }

    /**
     * Setter for projectStatus field.
     *
     * @param newStatus the project status.
     *
     * @throws IllegalArgumentException if the input parameter is not ProjectStatus
     */
    this.setProjectStatus = /* void */ function(/* ProjectStatus */ newStatus) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newStatus, "newStatus", "ProjectStatus");
        projectStatus = newStatus;
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
        js.topcoder.clients.bridge.lookup.Helper.checkType(newClient, "newClient", "Client");
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
     * @param newChildProjects an array of sub projects associated with this project. It can be null.
     *
     * @throws IllegalArgumentException if the input parameter is not Project[], or contains null element
     */
    this.setChildProjects = /* void */ function(/* Project[] */ newChildProjects) {
        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(newChildProjects)) {
            childProjects = newChildProjects;
            return;
        }
        js.topcoder.clients.bridge.lookup.Helper.checkType(newChildProjects, "newChildProjects", "Array");
        for (var i = 0; i < newChildProjects.length; i++) {
            if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(newChildProjects[i])) {
                throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException("newChildProjects[" + i + "]",
                    "newChildProjects[" + i + "] should not be undefined or null.");
            }
            js.topcoder.clients.bridge.lookup.Helper.checkType(newChildProjects[i],
                "newChildProjects[" + i + "]", "Project");
        }
        childProjects = newChildProjects;
    }

    /**
     * Getter for parentProjectId field.
     *
     * @return an the id of the parent project
     */
    this.getParentProjectId = /* Number */ function() {
        return parentProjectId;
    }

    /**
     * Setter for parentProjectId field.
     *
     * @param newParentProjectId The id of the parent project.
     *
     * @throws IllegalArgumentException if the input parameter is not number
     */
    this.setParentProjectId = /* void */ function(/* Number */ newParentProjectId) {
        js.topcoder.clients.bridge.lookup.Helper.checkType(newParentProjectId, "newParentProjectId", "number");
        parentProjectId = newParentProjectId;
    }

    /**
     * Convert this JavaScript object to a JSON string and return.
     *
     * It will generate json string in the following format:
     * (The $xxx should be replaced by corresponding variables.)
     * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.
     * Properties with null value should also be added to the generated json string with null value.
     *
     * The json format is:
     * { "company":company, "active":active, "salesTax":salesTax, "pOBoxNumber":pOBoxNumber,
     * "paymentTermsId":paymentTermsId, "description":description,
     * "projectStatus":projectStatus, "client":client,
     * "childProjects":arrayProject, "parentProjectId":parentProjectId,
     * "id":id, "createUsername":createUsername,
     * "createDate":createDate, "modifyUsername":modifyUsername,
     * "modifyDate":modifyDate, "name":name, "deleted":deleted };
     *
     * @return the JSON string for this entity.
     */
    this.toJSON = /* JSONObject */ function() {
        var arrayProject = new Array();
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(childProjects)) {
            for (var i = 0; i < childProjects.length; i++) {
                arrayProject.push(JSON.parse(childProjects[i].toJSON()));
            }
        }

        var json = {
            "company":js.topcoder.clients.bridge.lookup.Helper.getJSON(company),
            "active":active, "salesTax":salesTax, "pOBoxNumber":pOBoxNumber,
            "paymentTermsId":paymentTermsId, "description":description,
            "projectStatus":js.topcoder.clients.bridge.lookup.Helper.getJSON(projectStatus),
            "client":js.topcoder.clients.bridge.lookup.Helper.getJSON(client),
            "childProjects":arrayProject, "parentProjectId":parentProjectId,

            "id":this.getId(), "createUsername":this.getCreateUsername(),
            "createDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getCreateDate()),
            "modifyUsername":this.getModifyUsername(),
            "modifyDate":js.topcoder.clients.bridge.lookup.Helper.getTime(this.getModifyDate()),
            "name":this.getName(), "deleted":this.isDeleted()
        }
        return JSON.stringify(json);
    }


    /*
     * Start of constructor code.
     */

    // constructor code
    this.base = js.topcoder.clients.bridge.lookup.entities.BaseEntity;

    js.topcoder.clients.bridge.lookup.Helper.checkJSONObject(jsonObject, "jsonObject in Project");

    this.base(jsonObject);

    if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject)) {
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["company"])) {
            this.setCompany(new js.topcoder.clients.bridge.lookup.entities.Company(jsonObject["company"]));
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["active"])) {
            this.setActive(jsonObject["active"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["salesTax"])) {
            this.setSalesTax(jsonObject["salesTax"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["pOBoxNumber"])) {
            this.setPOBoxNumber(jsonObject["pOBoxNumber"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["paymentTermsId"])) {
            this.setPaymentTermsId(jsonObject["paymentTermsId"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["description"])) {
            this.setDescription(jsonObject["description"]);
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["projectStatus"])) {
            this.setProjectStatus(new js.topcoder.clients.bridge.lookup.entities.ProjectStatus(jsonObject["projectStatus"]));
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["client"])) {
            this.setClient(new js.topcoder.clients.bridge.lookup.entities.Client(jsonObject["client"]));
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["childProjects"])) {
            for (var i = 0; i < jsonObject["childProjects"].length; i++) {
                childProjects.push(new js.topcoder.clients.bridge.lookup.entities.Project(
                    jsonObject["childProjects"][i]));
            }
        }
        if (!js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonObject["parentProjectId"])) {
            this.setParentProjectId(jsonObject["parentProjectId"]);
        }
    }

    /*
     * End of constructor code.
     */
}


/**
 * <p>
 * This JavaScript class defines the operations related to the project status service.
 * It will use the AJAX Processor js component to communicate with the ClientProjectLookupServiceBridgeServlet.
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is completed
 * in asynchronous mode, so when the operation is completed successfully, onSuccess will be called,
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.ProjectStatusService = function ( /* String */ newServletUrl ) {
    /**
     * <p>
     * Represents the servlet url to communicate with.
     * Initialized in constructor, and never changed afterwards.
     * It must be non-null, non-empty string.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ servletUrl;

    /**
     * Create project status asynchronously, if the operation is successful, onSuccess callback function
     * will be called with the created project status, otherwise onError will be called.
     *
     * @param status the project status to create.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.createProjectStatus = /* void */ function( /* ProjectStatus */ status, /* ProjectStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "createProjectStatus");
    }

    /**
     * Update project status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the updated project status,
     * otherwise onError will be called.
     *
     * @param status the project status to update.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.updateProjectStatus = /* void */ function( /* ProjectStatus */ status, /* ProjectStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "updateProjectStatus");
    }

    /**
     * Delete project status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the deleted project status,
     * otherwise onError will be called.
     *
     * @param status the project status to delete.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.deleteProjectStatus = /* void */ function( /* ProjectStatus */ status, /* ProjectStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "deleteProjectStatus");
    }

    /**
     * The method do the ajax process.
     *
     * @private
     *
     * @param status the project status to process
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function( /* ProjectStatus */ status, /* ProjectStatusHandler */ onSuccess,
        /* ErrorHandler */ onError, /* String */ methodStr) {
        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(status)) {
            throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException("status",
                "The company should not be undefined or null");
        }
        js.topcoder.clients.bridge.lookup.Helper.checkType(status, "status", "ProjectStatus");
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onError, "onError");
        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=projectStatus&method=" + methodStr + "&projectStatus=" + status.toJSON(),
            method: "POST",
            async: true,
            responseType : "text",
            onStateChange: function() {
                // Check the response and return json object
                if (processor.getStatus() == 200 && processor.getState() == 4) {
                    var response = processor.getResponseText();
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(response)) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The response should not be undefined or null");
                    }

                    // evaluate the response into a json object: jsonResp.
                    var jsonResp = JSON.parse(response);
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["success"])) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The jsonResp[success] should not be undefined or null");
                    }

                    if (jsonResp["success"] == true) {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["json"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                                methodStr, "The jsonResp[json] should not be undefined or null");
                        }
                        var projectStatus =
                            new js.topcoder.clients.bridge.lookup.entities.ProjectStatus(jsonResp["json"]);
                        onSuccess(projectStatus);
                    } else {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["error"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                                methodStr, "The jsonResp[error] should not be undefined or null");
                        }
                        onError(jsonResp["error"]);
                    }
                } else {
                    throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                        methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
                }
            }
        });
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.lookup.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This JavaScript class defines the operations related to the client status service.
 * It will use the AJAX Processor js component to communicate with the ClientProjectLookupServiceBridgeServlet.
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is completed
 * in asynchronous mode, so when the operation is completed successfully, onSuccess will be called,
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.ClientStatusService = function ( /* String */ newServletUrl ) {
    /**
     * <p>
     * Represents the servlet url to communicate with.
     * Initialized in constructor, and never changed afterwards.
     * It must be non-null, non-empty string.
     * </p>
     *
     * @private
     * @final
     */
    var /* String */ servletUrl;

    /**
     * Create client status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the created client status,
     * otherwise onError will be called.
     *
     * @param status the client status to create
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.createClientStatus = /* void */ function( /* ClientStatus */ status, /* ClientStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "createClientStatus");
    }

    /**
     * Update client status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the updated client status,
     * otherwise onError will be called.
     *
     * @param status the client status to update
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.updateClientStatus = /* void */ function( /* ClientStatus */ status, /* ClientStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "updateClientStatus");
    }

    /**
     * Delete client status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the deleted client status,
     * otherwise onError will be called.
     *
     * @param status the client status to delete
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.deleteClientStatus = /* void */ function( /* ClientStatus */ status, /* ClientStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, onSuccess, onError, "deleteClientStatus");
    }

    /**
     * The method do the ajax process.
     *
     * @private
     *
     * @param status the project status to process
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function( /* ClientStatus */ status, /* ClientStatusHandler */ onSuccess,
        /* ErrorHandler */ onError, /* String */ methodStr) {
        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(status)) {
            throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException("status",
                "The company should not be undefined or null");
        }
        js.topcoder.clients.bridge.lookup.Helper.checkType(status, "status", "ClientStatus");
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onError, "onError");
        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=clientStatus&method=" + methodStr + "&clientStatus=" + status.toJSON(),
            method: "POST",
            async: true,
            responseType : "text",
            onStateChange: function() {
                // Check the response and return json object
                if (processor.getStatus() == 200 && processor.getState() == 4) {
                    var response = processor.getResponseText();
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(response)) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The response should not be undefined or null");
                    }

                    // evaluate the response into a json object: jsonResp.
                    var jsonResp = JSON.parse(response);
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["success"])) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The jsonResp[success] should not be undefined or null");
                    }

                    if (jsonResp["success"] == true) {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["json"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                                methodStr, "The jsonResp[json] should not be undefined or null");
                        }
                        var clientStatus =
                            new js.topcoder.clients.bridge.lookup.entities.ClientStatus(jsonResp["json"]);
                        onSuccess(clientStatus);
                    } else {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["error"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                                methodStr, "The jsonResp[error] should not be undefined or null");
                        }
                        onError(jsonResp["error"]);
                    }
                } else {
                    throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                        methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
                }
            }
        });
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.lookup.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}



/**
 * <p>
 * This JavaScript class defines the operations related to the client project lookup service.
 * It will use the AJAX Processor js component to communicate with the ClientProjectLookupServiceBridgeServlet.
 * Each method has onSuccess and onError callback functions as its arguments, and as the operation is completed
 * in asynchronous mode, so when the operation is completed successfully, onSuccess will be called,
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
 * @author zsudraco, stevenfrog
 * @version 1.0
 */
js.topcoder.clients.bridge.lookup.LookupService = function ( /*String*/ newServletUrl ) {
    /**
     * <p>
     * Represents the servlet url to communicate with.
     * Initialized in constructor, and never changed afterwards.
     * It must be non-null, non-empty string.
     * </p>
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
     * @param id the client id.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveClient = /* void */ function( /* Number */ id, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(id, "number", onSuccess, onError, "retrieveClient");
    }

    /**
     * Retrieve all clients asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved clients,
     * otherwise onError will be called.
     *
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveAllClients = /* void */ function(/* ClientsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(null, "null", onSuccess, onError, "retrieveAllClients");
    }

    /**
     * Search clients by name asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the searched clients,
     * otherwise onError will be called.
     *
     * @param name the client name
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.searchClientsByName = /* void */ function( /* String */ name, /* ClientsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(name, "string", onSuccess, onError, "searchClientsByName");
    }

    /**
     * Get cilent status by id asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the got client status,
     * otherwise onError will be called.
     *
     * @param id the client status id.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getClientStatus = /* void */ function( /* Number */ id, /* ClientStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(id, "number", onSuccess, onError, "getClientStatus");
    }

    /**
     * Get all client statuses asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved client statuses,
     * otherwise onError will be called.
     *
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getAllClientStatuses = /* void */ function(/* ClientStatusesHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(null, "null", onSuccess, onError, "getAllClientStatuses");
    }

    /**
     * Get clients for status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved clients,
     * otherwise onError will be called.
     *
     * @param status the client status
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getClientsForStatus = /* void */ function( /* ClientStatus */ status, /* ClientsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, "ClientStatus", onSuccess, onError, "getClientsForStatus");
    }

    /**
     * Retrieve project by id asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved project,
     * otherwise onError will be called.
     *
     * @param id the client status id.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveProject = /* void */ function( /* Number */ id, /* ProjectHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(id, "number", onSuccess, onError, "retrieveProject");
    }

    /**
     * Retrieve projects for client asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved projects,
     * otherwise onError will be called.
     *
     * @param client the client.
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveProjectsForClient = /* void */ function( /* Client */ client, /* ProjectsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(client, "Client", onSuccess, onError, "retrieveProjectsForClient");
    }

    /**
     * Retrieve all projects asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved projects,
     * otherwise onError will be called.
     *
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveAllProjects = /* void */ function( /* ProjectsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(null, "null", onSuccess, onError, "retrieveAllProjects");
    }

    /**
     * Search projects by name asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the searched projects,
     * otherwise onError will be called.
     *
     * @param name the project name
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.searchProjectsByName = /* void */ function( /* String */ name, /* ProjectsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(name, "string", onSuccess, onError, "searchProjectsByName");
    }

    /**
     * Get project status by id asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved project status,
     * otherwise onError will be called.
     *
     * @param id the project status id
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getProjectStatus = /* void */ function( /* Number */ id, /* ProjectStatusHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(id, "number", onSuccess, onError, "getProjectStatus");
    }

    /**
     * Get all project statuses asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved project statuses,
     * otherwise onError will be called.
     *
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getAllProjectStatuses = /* void */ function( /* ProjectStatusesHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(null, "null", onSuccess, onError, "getAllProjectStatuses");
    }

    /**
     * Get projects for status asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved projects,
     * otherwise onError will be called.
     *
     * @param name the project name
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getProjectsForStatus = /* void */ function( /* ProjectStatus */ status, /* ProjectsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(status, "ProjectStatus", onSuccess, onError, "getProjectsForStatus");
    }

    /**
     * Retrieve company by id asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved company,
     * otherwise onError will be called.
     *
     * @param id the company id
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveCompany = /* void */ function( /* Number */ id, /* CompanyHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(id, "number", onSuccess, onError, "retrieveCompany");
    }

    /**
     * Retrieve all companies asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved companies,
     * otherwise onError will be called.
     *
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.retrieveAllCompanies = /* void */ function( /* CompaniesHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(null, "null", onSuccess, onError, "retrieveAllCompanies");
    }

    /**
     * Search companies by name asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved companies,
     * otherwise onError will be called.
     *
     * @param name the company name
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.searchCompaniesByName = /* void */ function( /* String */ name, /* CompaniesHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(name, "string", onSuccess, onError, "searchCompaniesByName");
    }

    /**
     * Get clients for company asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved clients,
     * otherwise onError will be called.
     *
     * @param company the company
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getClientsForCompany = /* void */ function( /* Company */ company, /* ClientHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(company, "Company", onSuccess, onError, "getClientsForCompany");
    }

    /**
     * Get projects for company asynchronously, if the operation is successful,
     * onSuccess callback function will be called with the retrieved projects,
     * otherwise onError will be called.
     *
     * @param company the company
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getProjectsForCompany = /* void */ function( /* Company */ company, /* ProjectsHandler */ onSuccess,
        /* ErrorHandler */ onError) {
        doProcess(company, "Company", onSuccess, onError, "getProjectsForCompany");
    }

    /**
     * The method do the ajax process.
     *
     * @private
     *
     * @param input the input parameter
     * @inputType the input parameter's type
     * @param onSuccess the successful data retrieval handler to be called.
     * @param onError the error handler to be called
     * @param methodStr the method string
     *
     * @throws IllegalArgumentException if any argument is null or the parameters are wrong type
     * @throws InvalidResponseException if the received response is invalid.
     */
    var doProcess = /* void */ function( /* number or string or or ProjectStatus or ClientStatus */ input,
        /* String*/ inputType, /* SuccessHandler */ onSuccess, /* ErrorHandler */ onError, /* String */ methodStr) {
        if (inputType != "null" && js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(input)) {
            throw new js.topcoder.clients.bridge.lookup.IllegalArgumentException("input",
                "The input in LookService should not be undefined or null");
        }
        if (inputType != "null") {
            js.topcoder.clients.bridge.lookup.Helper.checkType(input, "input", inputType);
        }
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onSuccess, "onSuccess");
        js.topcoder.clients.bridge.lookup.Helper.checkFunction(onError, "onError");

        // create servlet parameter string
        var parameterString = "";
        if (inputType == "number") {
            parameterString = "&id=" + input;
        } else if (inputType == "string") {
            parameterString = "&name=" + input;
        } else if (inputType == "ProjectStatus") {
            parameterString = "&projectStatus=" + input.toJSON();
        } else if (inputType == "ClientStatus") {
            parameterString = "&clientStatus=" + input.toJSON();
        } else if (inputType == "Client") {
            parameterString = "&client=" + input.toJSON();
        } else if (inputType == "Company") {
            parameterString = "&company=" + input.toJSON();
        }

        // Create AJAXProcessor object
        var processor = new js.topcoder.ajax.AjaxProcessor();
        // Send a request asynchronously
        processor.request({
            // the json string should be escaped properly here.
            url: servletUrl+"?service=lookup&method=" + methodStr + parameterString,
            method: "POST",
            async: true,
            responseType : "text",
            onStateChange: function() {
                // Check the response and return json object
                if (processor.getStatus() == 200 && processor.getState() == 4) {
                    var response = processor.getResponseText();
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(response)) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The response should not be undefined or null");
                    }

                    // evaluate the response into a json object: jsonResp.
                    var jsonResp = JSON.parse(response);
                    if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["success"])) {
                        throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                            methodStr, "The jsonResp[success] should not be undefined or null");
                    }

                    if (jsonResp["success"] == true) {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["json"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("lookup",
                                methodStr, "The jsonResp[json] should not be undefined or null");
                        }

                        // Change the json into object instance
                        var res;
                        if (methodStr == "retrieveClient") {
                            res = new js.topcoder.clients.bridge.lookup.entities.Client(jsonResp["json"]);
                        } else if (methodStr == "retrieveAllClients" || methodStr == "searchClientsByName"
                            || methodStr == "getClientsForCompany") {
                            res = new Array();
                            for (var i = 0; i < jsonResp["json"].length; i++) {
                                res.push(new js.topcoder.clients.bridge.lookup.entities.Client(jsonResp["json"][i]));
                            }
                        } else if (methodStr == "getClientStatus") {
                            res = new js.topcoder.clients.bridge.lookup.entities.ClientStatus(jsonResp["json"]);
                        } else if (methodStr == "getAllClientStatuses" || methodStr == "getClientsForStatus") {
                            res = new Array();
                            for (var i = 0; i < jsonResp["json"].length; i++) {
                                res.push(
                                    new js.topcoder.clients.bridge.lookup.entities.ClientStatus(jsonResp["json"][i]));
                            }
                        } else if (methodStr == "retrieveProject") {
                            res = new js.topcoder.clients.bridge.lookup.entities.Project(jsonResp["json"]);
                        } else if (methodStr == "retrieveProjectsForClient" || methodStr == "retrieveAllProjects"
                            || methodStr == "searchProjectsByName" || methodStr == "getProjectsForStatus"
                            || methodStr == "getProjectsForCompany") {
                            res = new Array();
                            for (var i = 0; i < jsonResp["json"].length; i++) {
                                res.push(new js.topcoder.clients.bridge.lookup.entities.Project(jsonResp["json"][i]));
                            }
                        } else if (methodStr == "getProjectStatus") {
                            res = new js.topcoder.clients.bridge.lookup.entities.ProjectStatus(jsonResp["json"]);
                        } else if (methodStr == "getAllProjectStatuses") {
                            res = new Array();
                            for (var i = 0; i < jsonResp["json"].length; i++) {
                                res.push(
                                    new js.topcoder.clients.bridge.lookup.entities.ProjectStatus(jsonResp["json"][i]));
                            }
                        } else if (methodStr == "retrieveCompany") {
                            res = new js.topcoder.clients.bridge.lookup.entities.Company(jsonResp["json"]);
                        } else if (methodStr == "retrieveAllCompanies" || methodStr == "searchCompaniesByName") {
                            res = new Array();
                            for (var i = 0; i < jsonResp["json"].length; i++) {
                                res.push(new js.topcoder.clients.bridge.lookup.entities.Company(jsonResp["json"][i]));
                            }
                        }
                        onSuccess(res);
                    } else {
                        if (js.topcoder.clients.bridge.lookup.Helper.isUndefinedOrNull(jsonResp["error"])) {
                            throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                                methodStr, "The jsonResp[error] should not be undefined or null");
                        }
                        onError(jsonResp["error"]);
                    }
                } else {
                    throw new js.topcoder.clients.bridge.lookup.InvalidResponseException("projectStatus",
                        methodStr, "The response status is [" + processor.getStatus() + "] and not 200.");
                }
            }
        });
    }

    /*
     * Start of constructor code.
     */

    // constructor code
    js.topcoder.clients.bridge.lookup.Helper.checkString(newServletUrl, "newServletUrl");
    servletUrl = newServletUrl;

    /*
     * End of constructor code.
     */
}