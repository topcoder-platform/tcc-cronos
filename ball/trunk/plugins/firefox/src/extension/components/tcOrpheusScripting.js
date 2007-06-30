/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

/**
 * This is the implementation of the scripting interface exposed to be call from untrusted code.
 *
 * @author kr00tki
 * @version 1.0
 */
const ORPHEUSSCRIPTING_CID      = Components.ID("{77252dde-18f7-49e1-907a-0baC2a78bffd}");
const ORPHEUSSCRIPTING_CONTRACTID   = "@topcoder.com/orpheusscripting;1";

/** The name of the object in window object properties. */
const WINDOW_NAME = "external";

function OrpheusScripting() {
}

OrpheusScripting.prototype = {
	/** The Orpheus service to which the call will be redirected. */
	service : Components.classes["@topcoder.com/orpheus;1"].getService(Components.interfaces.tcIOrpheus).wrappedJSObject,

    LoggedIn : function() {
    	this.service.loggedIn();
    },
    
    LoggedOut : function() {
    	this.service.loggedOut();
    },
    
    SetWorkingGame : function(aGameId) {
    	this.service.setWorkingGame(aGameId);
    },
    
    GetWorkingGame : function()  {
    	return this.service.getWorkingGame();
    },
    
    SetCurrentTarget : function(aHash, aSequence) {
    	this.service.setCurrentTarget(aHash, aSequence);
    },
    
    PollMessages : function() {
    	this.service.pollMessages();
    },
    
    IsPopup : function(aWindow) {
    	return this.service.isPopup(aWindow);
    },
    
  // nsISecurityCheckedComponent
  canCreateWrapper: function canCreateWrapper(iid)
  {
    return "AllAccess";
  },

  // nsISecurityCheckedComponent
  canCallMethod: function canCallMethod(iid)
  {
    return "AllAccess";
  },

  // nsISecurityCheckedComponent
  canGetProperty: function canGetProperty(iid)
  {
    return "NoAccess";
  },

  // nsISecurityCheckedComponent
  canSetProperty: function canSetProperty(iid)
  {
    return "NoAccess";
  },

  // nsIClassInfo
  getInterfaces: function getInterfaces(aCount) {
    var interfaces = [Components.interfaces.tcIOrpheusScripting,
                      Components.interfaces.nsISecurityCheckedComponent,
                      Components.interfaces.nsIClassInfo];
    aCount.value = interfaces.length;
    return interfaces;
  },

  // nsIClassInfo
  getHelperForLanguage: function getHelperForLanguage(aLanguage) {
    return null;
  },

  // nsIClassInfo
  contractID: ORPHEUSSCRIPTING_CONTRACTID,

  // nsIClassInfo
  classDescription: "orpheusscripting",

  // nsIClassInfo
  classID: ORPHEUSSCRIPTING_CID,

  // nsIClassInfo
  implementationLanguage: Components.interfaces.nsIProgrammingLanguage.JAVASCRIPT,

  // nsIClassInfo
  flags: Components.interfaces.nsIClassInfo.SINGLETON,

	// nsISupports
  	QueryInterface: function QueryInterface(aIID) {
    	if (aIID.equals(Components.interfaces.tcIOrpheusScripting) ||
        	aIID.equals(Components.interfaces.nsISecurityCheckedComponent) ||
        	aIID.equals(Components.interfaces.nsIClassInfo) ||
        	aIID.equals(Components.interfaces.nsISupports)) {
      
      		return this;
      	}

    }
}

const nsIClassInfo = Components.interfaces.nsIClassInfo;


var Module = {
    firstTime  : true,
    
    registerSelf: function (compMgr, fileSpec, location, type) {
  	compMgr = compMgr.QueryInterface(Components.interfaces.nsIComponentRegistrar);

    compMgr.registerFactoryLocation(ORPHEUSSCRIPTING_CID,
                                     "TheBall JS Component",
                                     ORPHEUSSCRIPTING_CONTRACTID,
                                     fileSpec,
                                     location,
                                     type);
 
     const CATMAN_CONTRACTID = "@mozilla.org/categorymanager;1";
     const nsICategoryManager = Components.interfaces.nsICategoryManager;
     var catman = Components.classes[CATMAN_CONTRACTID].
                             getService(nsICategoryManager);
 
     const JAVASCRIPT_GLOBAL_PROPERTY_CATEGORY = "JavaScript global property";
     catman.addCategoryEntry(JAVASCRIPT_GLOBAL_PROPERTY_CATEGORY,
                             WINDOW_NAME,
                             ORPHEUSSCRIPTING_CONTRACTID,
                             true,
                             true);
  	
  	
    },
    
    getClassObject : function (compMgr, cid, iid) {
  		if (!cid.equals(ORPHEUSSCRIPTING_CID))
      		throw Components.results.NS_ERROR_NO_INTERFACE;
  
  		if (!iid.equals(Components.interfaces.nsIFactory))
      		throw Components.results.NS_ERROR_NOT_IMPLEMENTED;
  
  		return this.myFactory;
  },
  
    
	myFactory: {
    	createInstance: function (outer, iid) {
        	if (outer != null) {
        		throw Components.results.NS_ERROR_NO_AGGREGATION;
        	}
        
        	return (new OrpheusScripting( )).QueryInterface(iid);
  		}
	},
	
	canUnload: function(compMgr) {
  		return true;
	}
}

function NSGetModule(compMgr, fileSpec) { return Module; }
