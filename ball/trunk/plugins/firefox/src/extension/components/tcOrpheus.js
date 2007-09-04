/*
 * Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.
 *
 */
 
/**
 * This script contains implementation of the Orpheus service. 
 * @author kr00tki
 * @version 1.0
 */

const ORPHEUS_CID      = Components.ID("{696a715b-605f-4342-ae44-751024905c0c}");
const ORPHEUS_CONTRACTID   = "@topcoder.com/orpheus;1";

const PREFS_SERV = "@mozilla.org/preferences-service;1";

const GAMEID_PREF = "extension.orpheus.gameid";
const TIMESTAMP_PREF = "extension.orpheus.timestamp";
const SEQUENCE_PREF = "extension.orpheus.game_seq";
const HASH_PREF = "extension.orpheus.hash";
const POLL_PREF = "extension.orpheus.poll_interval";
const BLOOM_FILTER_PREF = "extension.orpheus.bloom_filter";

const URL_HASH_PREF = "extension.orpheus.url_hash";

const TEST_DOMAIN = "test_domain";
const TEST_DOMAIN_GAMES = "test_domain_games";
const POLLING = "polling";
const TEST_OBJECT = "test_object";
const BRAINTEASURE = "brainteasure";

const PREFS_URL_PREFIX = "extension.orpheus.url.";
const CTX = PREFS_URL_PREFIX + "context";
const DEFAULT_TIMESTAMP = "2007-01-01T00:00:02Z";

const DEBUG = "extension.orpheus.debug";
var debugEnabled = true;

var jars = [
	"client_logic_for_firefox.jar",
	"base_exception.jar",
	"configuration_manager.jar",
	"typesafe_enum.jar",
    "object_factory.jar",
    "bloom_filter.jar",
    "hashing_utility.jar",
    "rss_generator.jar",
    "config.jar", 
    "lightweight_xml_parser.jar",
    "base64_codec.jar",
    "string_class.jar"
    ];


/**
 * This methods extracts the file path to the extension folder.<b>
 * @param {String} the name of the extension. 
 */
function getExtensionPath(extensionName)  {
    var registry = Components.classes["@mozilla.org/chrome/chrome-registry;1"]
    	.getService(Components.interfaces.nsIChromeRegistry);
            
    var uri = Components.classes["@mozilla.org/network/standard-url;1"].createInstance(Components.interfaces.nsIURI);
    
    uri.spec = "chrome://" + extensionName + "/content/";
    
    // get path
    var path = registry.convertChromeURL(uri);
    if (typeof(path) == "object") {
        path = path.spec;
    }
    
    return path.substring(0, path.indexOf("/chrome/") + 1);
}; 


/**
 * The constructor of the Orpheus object. It sets the reference to itself to wrappedJSObject field.
 */
function Orpheus() {
	this.wrappedJSObject = this;
	debugEnabled = this.prefs.getBoolPref(DEBUG);
}

Orpheus.prototype = {
	
	/**
	 * The preferences service used to store the configuration.
	 */
	prefs : Components.classes[PREFS_SERV].getService(Components.interfaces.nsIPrefBranch),

	/**
	 * The timer instance used in background polling.
	 */
	timer : Components.classes["@mozilla.org/timer;1"].getService(Components.interfaces.nsITimer),
	
	/**
	 * The flag indicating if the user is logged in to server.
	 */
	isloggedIn : false,

	/**
	 * The object contains references to objects that are associated with concrete toolbars instances.
	 */
	windowsSet : new Map(),
	
	/**
	 * Indicates if the Orpheus service was already initialized.
	 */
	initialized : false,

	/**
	 * The Orpheus helper Java object used to perform operations like Bloom filter update or test object.
	 * @type FirefoxExtensionHelperLite
	 */
	helper : null,
	
	/**
	 * The last domain that has been tested in plug-in. If the next domain to check is the same as this one,
	 * the test will return false immediately.
	 */
	lastDomain : null,
	
	/**
	 * The getter for the isLoggedIn property.
	 */	
	get isLoggedIn() {
    	return this.isloggedIn;
    },
    
    /**
	 * The setter for the isLoggedIn property.
	 */	
	set isLoggedIn(val) {
    	this.isloggedIn = true;
    },
    
    popup_ : null,
    
    get popup() {
    	debug("Get popup: " + this.popup_);
    	return this.popup_;
    },
    
    /**
	 * The setter for the isLoggedIn property.
	 */	
	set popup(val) {
    	this.popup_ = val;
		debug("Set popup: " + val + " " + val.name);
    },
    
    /**
     * Initializes this component serviec. The parameter is LiveConnect java object used to create the
     * required Java object instances.
     * @param The LiveConnect java object.
     */
	init : function(java) {
		if (this.initialized) {
			return;
		}
		debug("Init first call.");	
		this.initialized = true;
		try {
		// get the absolute path
		var libPath = getExtensionPath("orpheus") + "components/lib/";
		
		// load our policy first
		var cl = java.net.URLClassLoader.newInstance([new java.net.URL(libPath + jars[0])]);
		var policyClass = java.lang.Class.forName("com.orpheus.plugin.firefox.CustomPolicy", true, cl);
        
    	var policy = policyClass.newInstance();
    	// set our policy
		java.security.Policy.setPolicy(policy);
	
		// create the URls	
		var jarURLs = [];
        for (i in jars) {
        	var url = new java.net.URL(libPath + jars[i]);
            jarURLs.push(url);
        } 
		
		// create the class loader
		cl = new java.net.URLClassLoader(jarURLs);
		//java.lang.Thread.currentThread().setContextClassLoader(cl);

		//this.initConfigManager(java, cl);
		
		// create instance of FirefoxExtensionHelper
		var helperClass = java.lang.Class.forName("com.orpheus.plugin.firefox.FirefoxExtensionHelperLite", true, cl);
		this.helper = helperClass.newInstance();
		this.helper.initialize();
				
		// set the timer
		debug("Time: " + this.timer);
		
		// create the bloom filter
		var filter = this.getSafeCharPref(BLOOM_FILTER_PREF);
		if (filter.length > 0) {
			debug("Using previous Bloom Filter: " + filter);
			this.helper.setBloomFilter(filter);
			debug("Filter initialization ok");
		}
		debug("Init - ok!");
		} catch(e) {
			debug("Error during component initilization: " + e);
		}
	},
	
	/**
	 * Initializes the ConfigManager.
	 * @param java LiveConnect Java object.
	 * @param cl the classloader to be used to load ConfigManager class.
	 */
	initConfigManager : function (java, cl) {
		var class = java.lang.Class.forName("com.topcoder.util.config.ConfigManager", true, cl);
		var cm = class.getMethod("getInstance", null).invoke(null, null);
		//cm.add(new java.net.URL("jar:" + getExtensionPath("orpheus") + "components/lib/config.jar!/config.xml"));
		cm.add(new java.net.URL(getExtensionPath("orpheus") + "defaults/preferences/config.xml"));
	},
	
	/**
	 * This method is intended to be called from the Javascript when the user logs in to the server.
	 * It calls all the registred toolbars and activates additional buttons.
	 * It also starts polling timer and perform poll update imidiately.
	 */ 
    loggedIn : function() {
    	if (!this.isLoggedIn) {
	    	this.isloggedIn = true;
    		var windows = this.windowsSet.allValues();
    		var i;
    		for(i in windows) {
    			windows[i].show();
    		}
    		// start polling
    		this.timer.init(this, 60 * 1000 * this.prefs.getIntPref(POLL_PREF), 2);
    		this.pollMessages();
		}
		
		this.isloggedIn = true;
    },
    
    /**
     * This method is intended to be called from the Javascript when the user logs out from the server.
     * It closes all the additional buttons and stops backgroud timer.
     */
    loggedOut : function() {
    	if (this.isLoggedIn) {
    		this.isloggedIn = false;
	    	var windows = this.windowsSet.allValues();
    		var i;
    		for(i in windows) {
    			windows[i].hide();
    		}
    		this.timer.cancel();
    	}
    },
    
    /**
     * This method is intended to be called from the Javascript when the active game is changed.
     * @param {long} the id of active game.
     */
    setWorkingGame : function(gameId) {
    	debug("Set working game: " + gameId);
    	if (this.getWorkingGame() != gameId) {
    		this.prefs.setIntPref(GAMEID_PREF, gameId);
    		var windows = this.windowsSet.allValues();
    		var i;
    		for(i in windows) {
    			windows[i].disableButtons(false);
    		}
    	}
    	
    },
    
    /**
     * This method is intended to be called from the Javascript. It returns the active game.
     * @return {long} the id of active game.
     */
    getWorkingGame : function()  {
    	return this.getSafeIntPref(GAMEID_PREF, -1);
    },
    
    /**
     * This method is intended to be called from the Javascript. It set the correct object hash and number.
     * @param hash {String} the hex-encoded hash of the test object.
     * @param sequence {long} the sequence number of the object.
     */
    setCurrentTarget : function(hash, urlHash, sequence) {
    	debug("Set current target. Hash: " + hash + "  urlHash: " + urlHash+ "  sequence: " + sequence);
	    this.prefs.setCharPref(HASH_PREF, hash);
	    this.prefs.setCharPref(URL_HASH_PREF, urlHash);
	    this.prefs.setIntPref(SEQUENCE_PREF, sequence);
	    
	    // test all windows for new url
	    var windows = this.windowsSet.allValues();
    	var i;
    	for(i in windows) {
    		windows[i].onTabChange(null);
    	}
    },
    
    /**
     * This method is intended to be called from the Javascript. It polls the server for new updated to the
     * Bloom filter or for the message to be show to the user.
     */
    pollMessages : function() {
    	var timestamp = this.getFeedTimestamp();
    	debug("Polling timestamp: " + timestamp);
    	var request = this.createRequest();
    	request.open("GET", this.getURL(POLLING, [timestamp]), false);
		request.send(null);
			
		if (request.status == 200) {
    		try {
	    		var message = this.helper.pollServerResponse(request.responseText);
    			debug("message: " + message);
    			// set the timestamp
    			this.prefs.setCharPref(TIMESTAMP_PREF, this.helper.getTimestamp());
    			
    			var filter = this.helper.getSerializedBloomFilter();
    			if (filter != null) {
    				debug("Storing updated filter: " + filter);
    				this.prefs.setCharPref(BLOOM_FILTER_PREF, filter);
    			}

    			if (message && (this.windowsSet.length() > 0)) {
    				var i = 0;
    				for (i=0; i < message.length; i++) {
    					this.getWindow().openPopupWithContent(message[i]);
    				}
    			}
    			
    			debug("Polling " + timestamp + " - ok! Feed: " + request.responseText);
    		} catch (e) {
    			debug("Poll error. Status code: " + request.status + ". Response text" + request.responseText + ". Exception: " + e );
    		}
    	} else if (request.status == 401) {
    		// session timeout - logout toolbar
    		debug("Session timeout. Logging off.");
    		this.loggedOut();
    	} else {
    		debug("Polling error. Status code: " + request.status + ". Response text" + request.responseText);
    	}
    },
    
    /**
     * This method is intended to be called from the Javascript. It will check if the window is popup or not.
     * @param window the window to be checked.
     */
    isPopup : function(window) {
    	debug("isPopup : " + window + " name: " + window.wrappedJSObject.name);
    	return window.wrappedJSObject.name == "orpheus";
    },
    
    /**
     * Register the toolbar object to the service.
     * @param toolbarWindow the toolbar window object.
     */
    registerToolbar : function(toolbarWindow) {
    	debug("Register: " + toolbarWindow + " id: " + toolbarWindow.id + " name: " + toolbarWindow.window.name);
   		this.windowsSet.add(toolbarWindow.id, toolbarWindow);
    	debug("Windows: " + this.windowsSet.length());
    },
    
    /**
     * Unregister the toolbar object from the service when the window is closed.
     * @param toolbarWindow the toolbar window object.
     */
    unregisterToolbar : function(toolbarWindow) {
    	debug("Unregister: " + toolbarWindow + " id: " + toolbarWindow.id);
   		this.windowsSet.remove(toolbarWindow.id);
   		if ((this.windowsSet.length() == 0) && this.initialized) {
   			// stop timer after last window closed
   			this.timer.cancel();
   		}
    	
   		debug("Windows: " + this.windowsSet.length());
    },
    
    /**
     * This method is executed when user selects to test object on the page. It calls Java code to check
     * if object hash is correct.
     * @param element {string} the content of the element.
     * @param location {string} the current location to which this object belongs to.
     */
    currentTargetTest : function(element, location) {
    	var domain = location.hostname;
    	var fullURL = location.href;
    	debug("currentTargetTest: " + element + " domain: " + domain + " url: " + fullURL);
    	element = element.wrappedJSObject;
    	try {
    		while(element != null) {
    			debug("Target test: " + element.innerHTML + "   in domain: " + domain + " tag name: " + element.tagName);
    		
    			var hash = this.getSafeCharPref(HASH_PREF, "");
    			var seq = this.getSafeIntPref(SEQUENCE_PREF, 0);
    			var text = element.textContent.replace(/[ \t\n\r\f\u00a0\u200b]+/gm, "").toLowerCase();
    			debug("Normalized text: [" + text + "] esc: "  + encodeURIComponent(text)
    				+ " url: "  + encodeURIComponent(fullURL));
    			if (this.helper.currentTargetTest(text, hash)) {
    				debug("Test object - hash equals. Redirecting...  ");
    				return this.getURL(TEST_OBJECT, [this.getWorkingGame(), domain, seq, encodeURIComponent(text),
    					encodeURIComponent(fullURL)]);
    			} 
    		
    			if ((element.tagName == "BODY") || (element.tagName == "HTML") || (text.length > 50)) {
    				return null;
    			}
    		
    			element = element.parentNode;
    		}
    	} catch (e) {
    		debug("Target test error: " + e);
    	}
    	return null;
    },
    
    forcedDomainTest : function (domain) {
	    debug("Force test domain: " + domain);
    	if (this.isLoggedIn) {
    		return this.getURL(TEST_DOMAIN_GAMES, [domain]);
    	}    	
    },
    
    /**
     * This method is executed when user loads new page. It calls Java code to check
     * if the domain is important and is is, than it returns url to which the user is redirected.
     * @param domain {string} the new domain to check.
     */
    domainTest : function(domain) {
    	debug("Test domain: " + domain);
    	if (this.isLoggedIn) {
    		if (this.lastDomain == domain) {
    			debug("Test domain. " + domain + " already tested.");
    			return null;
    		}
    	
	    	this.lastDomain = domain;
    		if (!this.helper.domainTest(domain)) {
    			debug("Test Domain. Not in filter. " + domain);
    			return null;
    		}
    		var request = this.createRequest();
            request.open("GET", this.getURL(TEST_DOMAIN, [domain]), false);
			request.send(null);
			if (request.status == 200) {
				debug("Test doamin. Response: " + request.responseText);
				var number = parseInt(request.responseText);
			
				if (number > 0) {
					return this.getURL(TEST_DOMAIN_GAMES, [domain]);
				}
			
			} else if (request.status == 401) {
    			// session timeout - logout toolbar
    			debug("Session timeout. Logging off.");
    			this.loggedOut();
    		} else {
    			debug("Test domain error. Status code: " + request.status + ". Response text" + request.responseText);
    		}
    		
			 		
    		return null;
    	} else {
    		debug("Test doamin - not logged In!");
    	}
    },
    
    isURLCorrect : function(url) {
    	debug("isURLCorrect#: " + url);
    	if (this.isLoggedIn) {
    		// convert to string
    		url = "" + url;
	    	// check if has fragment indetifier
	    	var idx = url.indexOf("#");
	    	// if so, remove it
	    	if (idx > 0) {
	    		url = url.substring(0, idx);
	    	}
	    	debug("isURLCorrect: " + url);
	    	var currentHash = this.getSafeCharPref(URL_HASH_PREF, "");
    		var response = this.helper.currentTargetTest(url, currentHash);   	
    		debug("isURLCorrect: " + url + " response: " + response);
	    	return response;
	    } else {
    		debug("isURLCorrect - not logged in!");
    	}
    },
    
    /** 
 	 * Helper method. Returns the configured URL for action.
 	 * @param action {String} the name of the action.
 	 * @param params {String[]} an array of string paramters that need to be replaced in url.
 	 * @returns the URL for acton.
 	 */
	getURL : function(action, params) {
		var url =  this.prefs.getCharPref(CTX) + this.prefs.getCharPref(PREFS_URL_PREFIX + action);
		for (var i in params) {
			url = url.replace("$" + i, params[i]);
		}
		
		return url;
	},
	
	/**
	 * Creates the XMLHTTPRequest object.
	 * @return the XMLHTTPRequest object.
	 */
	createRequest : function() {
		return Components.classes["@mozilla.org/xmlextras/xmlhttprequest;1"].createInstance(Components.interfaces.nsIXMLHttpRequest);
	},
    
    /**
     * Returns the last feed timestamp stored in preferenes.
     * @returns {String} the last rss update timestamp.
     */
    getFeedTimestamp : function() {
    	var timestamp = this.getSafeCharPref(TIMESTAMP_PREF, DEFAULT_TIMESTAMP);
    	if (timestamp.length == 0) {
    		timestamp = DEFAULT_TIMESTAMP;
    	}
    	
    	return timestamp;
    },
    
    /**
     * Returns the test object sequence number. If not defined yet, -1 will be returned.
     * @returns {long} the sequense number.
     */
    getTargetSequence : function() {
    	return this.getSafeIntPref(GAMEID_PREF, -1);
    },
    
    /**
     * Returns the first instance of toolbar object.
     * @returns the toolbar object.
     */
    getWindow : function () {
    	return this.windowsSet.allValues()[0];
    },
    
    /**
     * Returns true if the working game is set.
     * @returns true if the working game is set.
     */ 
    isWorkingGameSet : function() {
    	return this.getWorkingGame() != -1;
    },
    
    /**
     * The observe method required by time callback. Calls the pollMessages method.
     * @param subject not used.
     * @param topic not used.
     * @param data not used.
     */
    observe : function(subject, topic,data ) {
    	debug("Timer event start");
    	this.pollMessages();
    	debug("Timer event end");
    },
    
    /**
     * Returns the char value from preferences. If key not exists, default value will be returned.
     * @param key the preference configuration key.
     * @parm defaultValue the default value. 
     */
    getSafeCharPref : function(key, defaultValue) {
    	if (this.prefs.prefHasUserValue(key)) {
    		return this.prefs.getCharPref(key);
    	}
    	
    	return defaultValue;
    },
    
    /**
     * Returns the int value from preferences. If key not exists, default value will be returned.
     * @param key the preference configuration key.
     * @parm defaultValue the default value.
     */
    getSafeIntPref : function(key, defaultValue) {
    	if (this.prefs.prefHasUserValue(key)) {
    		return this.prefs.getIntPref(key);
    	}
    	
    	return defaultValue;
    },
    
  /**
   * Returns the interfaces implemnted by this object.
   * @param aCount the pointer the number of interfaces implemented by object.
   * @returns the array of implemented interfaces.
   */
  getInterfaces: function getInterfaces(aCount) {
    var interfaces = [Components.interfaces.tcIOrpheus,
                      Components.interfaces.nsIClassInfo];
    aCount.value = interfaces.length;
    return interfaces;
  },

  /**
   * Not used but required by nsICLassInfo.<b> 
   */
  getHelperForLanguage: function getHelperForLanguage(aLanguage) {
    return null;
  },

  /** The contract id for this object. */
  contractID: ORPHEUS_CONTRACTID,

  /** The "user-friendly" description of this class. */
  classDescription: "orpheus",

  /** The class ID of this object. */
  classID: ORPHEUS_CID,

  /** The implementation language. */
  implementationLanguage: Components.interfaces.nsIProgrammingLanguage.JAVASCRIPT,

  /** This flag indicates that this is singleton class. */
  flags: Components.interfaces.nsIClassInfo.SINGLETON,

  /** Queries for the implemented interfaces. */
  QueryInterface: function QueryInterface(aIID) {
    if (aIID.equals(Components.interfaces.tcIOrpheus) ||
        aIID.equals(Components.interfaces.nsIClassInfo) ||
        aIID.equals(Components.interfaces.nsISupports))
      return this;
  }
}

/** nsIClassInfo interface const */
const nsIClassInfo = Components.interfaces.nsIClassInfo;

/**
 * Implements the Module object that is needed to register service.<b> 
 */
var Module = {
    firstTime  : true,
    
    registerSelf: function (compMgr, fileSpec, location, type) {
  	compMgr = compMgr.QueryInterface(Components.interfaces.nsIComponentRegistrar);

    compMgr.registerFactoryLocation(ORPHEUS_CID,
                                     "Orpheus Service Component",
                                     ORPHEUS_CONTRACTID,
                                     fileSpec,
                                     location,
                                     type);
    },
    
    getClassObject : function (compMgr, cid, iid) {
  		if (!cid.equals(ORPHEUS_CID))
      		throw Components.results.NS_ERROR_NO_INTERFACE;
  
  		if (!iid.equals(Components.interfaces.nsIFactory))
      		throw Components.results.NS_ERROR_NOT_IMPLEMENTED;
  
  		return this.myFactory;
    },
  
    myFactory : {
		createInstance: function (outer, iid) {
        	if (outer != null) {
        		throw Components.results.NS_ERROR_NO_AGGREGATION;
        	}
        
        	return (new Orpheus( )).QueryInterface(iid);
  		}
	},
	
	canUnload: function(compMgr) {
  		return true;
	}
}

/**
 * This method is used by Gecko engine to register the XPCOM object.<b> 
 */
function NSGetModule(compMgr, fileSpec) { 
	return Module; 
}


/**
 * Debug method. If DEBUG const is true it prints the message to console.
 * @param {String} msg the message to be logged.
 */
function debug(msg) { 
	if(debugEnabled) { 
	 printToJSConsole(msg); 
	}
}
/**
 * This method creates the cosole service and logs the message to it.
 * @param {String} the message to be logged.
 */
function printToJSConsole(msg) {
    Components.classes["@mozilla.org/consoleservice;1"].getService(Components.interfaces.nsIConsoleService)
            .logStringMessage(msg);
}



/*--------------------- Map -------------------*/

/**
 * @author zz_zju
 * @version 1.0
 */

/**
 * <p>Constructs a <code>Map</code> instance.</p>
 *
 * @class
 * <p>
 * It is a map which stores the (key, value) pairs and provides methods to add/change/remove the entry
 * or retrieve the value by the given key. Also, the functionality of getting the map size, all
 * the keys and all the values are also provided.
 * </p>
 * <p>
 * <b>Note: </b>When comparing the keys, only the value is considered. If two values have the same value
 * but different type, they are still considered as the same. For example, if x = 5 and y = "5", they are
 * the same even they have different type(x is number and y is string).
 * <b>Note: </b>object can not be used as key in the map. Currently, only string, number and boolean are
 * supported as keys in the map, otherwise, <code>IllegalArgumentException</code> will be thrown.
 * </p>
 * @constructor
 * @returns a new Map object
 */
function Map() {

	/**
	 * Represents the object storing the entries in the map.
	 *
	 * @private
	 */
	var map = new Object();

	/**
	 * <p>Represents the cache to store the keys in the map.</p>
	 * <p>The cache will be updated after the map is changed.</p>
	 *
	 * @private
	 */
	var keyCache = new Array();

	/**
	 * <p>Represents the cache to store the values in the map.</p>
	 * <p>The cache will be updated after the map is changed.</p>
	 *
	 * @private
	 */
	var valueCache = new Array();

	/**
	 * <p>A flag denoting if the key/value cache should be updated.</p>
	 * <p>
	 * It will be set true after the map is changed and set false after the
	 * caches are updated accordingly.
	 * </p>
	 *
	 * @private
	 */
	var modified = true;

	/**
	 * Represents the size of the map. The initial value is 0.
	 */
	var size = 0;

	// define public methods.
	this.add = add;
	this.change = change;
	this.retrieve = retrieve;
	this.remove = remove;
	this.length = length;
	this.allKeys = allKeys;
	this.allValues = allValues;

	/**
	 * Adds the (key, value) pair to the map.
	 *
	 * @param {object} key the key of the pair to add.
	 * @param {object} value the value of the pair to add.
	 * @returns true if the (key, value) pair is added successfully, false if the entry
	 * with the same key is already in the map.
	 * @type boolean
	 * @throws IllegalArgumentException if any argument is null or undefined or key argument
	 * is an object.
	 */
	function add(key, value) {
		checkKey(key, "key");
		checkParam(value, "value");

		// if the map[key] is undefined, the key/value is not in the map before.
		if (map[key] == undefined) {
			map[key] = value;
			size++;
			// force to update the key and value cache.
			modified = true;
			return true;
		}

		// the key/value is already in the map, return false.
		return false;

	}

	/**
	 * Changes the value of the entry in the map which is identified by the given key.
	 *
	 * @param {object} key the key to find out the entry to change value.
	 * @param {object} value the new value for the entry identified by the given key.
	 * @returns true if the entry is changed successfully, false otherwise(e.x. the entry can not be found).
	 * @type boolean
	 * @throws IllegalArgumentException if any argument is null or undefined or the key argument is an object.
	 */
	function change(key, value) {
		checkKey(key, "key");
		checkParam(value, "value");
		if (map[key] == undefined) {
			// the entry identified by the key is not in the map, return false.
			return false;
		}
		map[key] = value;
		// force to update the key and value cache.
		modified = true;
		return true;
	}

	/**
	 * Retrieves the value of the entry in the map which is identified by the given key.
	 *
	 * @param {object} key the key to find out the entry to retrieve value.
	 * @returns the actual value of the entry identified by the key, or null if the entry can not
	 * be found.
	 * @type Object
	 * @throws IllegalArgumentException if the key argument is null, undefined or an object.
	 */
	function retrieve(key) {
		checkKey(key, "key");
		if (map[key] == undefined) {
			// the entry identified by the key is not in the map, return null.
			return null;
		}
		return map[key];
	}

	/**
	 * Removes the entry in the map which is identified by the given key.
	 *
	 * @param {object} key the key to find out the entry to remove.
	 * @returns true if the entry identified by the key could be removed successfully, false otherwise.
	 * @type Object
	 * @throws IllegalArgumentException if the key argument is null, undefined or an object.
	 */
	function remove(key) {
		checkKey(key, "key");
		if (map[key] == undefined) {
			// the entry identified by the key is not in the map, return false.
			return false;
		}
		// remove the entry
		delete map[key];
		size--;
		// force to update the key and value cache.
		modified = true;
		return true;
	}

	/**
	 * Returns the size of the map.
	 *
	 * @returns the size of the map.
	 * @type number
	 */
	function length() {
		return size;
	}

	/**
	 * Returns the keys as array in the map.
	 *
	 * @returns the keys as array in the map.
	 * @type Array
	 */
	function allKeys() {
		// if the map is modified, rebuild the key array.
		if (modified == true) {
			updateKeysAndValues();
		}
		modified = false;
		return keyCache;
	}

	/**
	 * Returns the values as array in the map.
	 *
	 * @returns the values as array in the map.
	 * @type Array
	 */
	function allValues() {
		// if the map is modified, rebuild the value array.
		if (modified == true) {
			updateKeysAndValues();
		}
		modified = false;
		return valueCache;
	}

	/**
	 * Updates the keys and values cache.
	 *
	 * @private
	 */
	function updateKeysAndValues() {
		keyCache = new Array();
		valueCache = new Array();
		for (var key in map) {
			// update keys and values.
			keyCache.push(key);
			valueCache.push(map[key]);
		}
	}

	/**
	 * Checks if the value is null or undefined.
	 *
	 * @param {object} value the value to check against null or undefined.
	 * @param {string} name the name of variable containing value.
	 * @throws IllegalArgumentException if the value parameter is null or undefined.
	 * @private
	 */
	function checkParam(value, name) {
		if (value == null) {
			throw new IllegalArgumentException("The parameter " + name + " could not be null.");
		}
		if (value == undefined) {
			throw new IllegalArgumentException("The parameter " + name + " is undefined.");
		}
	}

	/**
	 * Checks if the key's value is null, undefined or an object.
	 *
	 * @param {object} value the value to check against null, undefined or object.
	 * @param {string} name the name of variable containing value.
	 * @throws IllegalArgumentException if the value parameter is null, undefined or an object.
	 * @private
	 */
	function checkKey(value, name) {
		checkParam(value, name);
		var result = typeof(value);
		switch(result) {
			case 'string' :
			case 'number' :
			case 'boolean' :
				break;
			default :
				throw new IllegalArgumentException("The key parameter " + name + " could not be object.");
		}
	}

}

/*--------------------------------------------------Class Separator--------------------------------------------------*/


/**
 * <p>Constructs a <code>IllegalArgumentException</code> instance with the given reason.</p>
 *
 * @class It is an exception which should be thrown if the argument is illegal.
 * @param {object} reason the detail error message of this exception.
 * @returns a new IllegalArgumentException object
 * @constructor
 */
function IllegalArgumentException(reason) {

	/**
	 * Represents the detail error message for this exception.
	 * @private
	 */
	var rsn = reason;

	// Define public method
	this.getReason = getReason;

	/**
	 * Retrieves the detail error message(reason) for this exception.
	 *
	 * @return the detail error message(reason) for this exception.
	 */
	function getReason() {
		return rsn;
	}
}


