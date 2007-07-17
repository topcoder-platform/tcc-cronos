/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
 
 /**
 * This script contains the helper method for the tolobar.
 * @author kr00tki
 * @version 1.0
 */
const PREFS_SERV = "@mozilla.org/preferences-service;1";

const POPUP_NAME = "orpheus";
const PREFS_PREFIX = "extension.orpheus";
const PREFS_URL_PREFIX = "extension.orpheus.url.";
const CTX = PREFS_URL_PREFIX + "context";
const WINDOW_FEATURES = "dialog=yes,toolbar=no,resizable=yes,scrollbars=yes,status=yes,height=500,width=700";

/** The OrpheusToolbar object that will represent one toolbar. */
var orpheus = new Object();

/** A reference the Orpheus service. */
orpheus.service = Components.classes["@topcoder.com/orpheus;1"].getService(Components.interfaces.tcIOrpheus).wrappedJSObject;

/** The preferences service used for the action URLs. */
orpheus.prefs = Components.classes[PREFS_SERV].getService(Components.interfaces.nsIPrefBranch);

/** The toolbar window associated with the object. */
orpheus.window = window;

/** The id of the object. */	
orpheus.id = new Date().getTime() + "" + Math.random();
	
/** The test target. */
orpheus.target = null;

/** A reference to the parent window of the popup. */
orpheus.parent = null;

orpheus.initialized = false;

/** 
 * This method is called on document load event. It checks if the user is logged in and displays additiona 
 * buttons if needed. It also register itself in the Orpheus service.
 */
orpheus.load = function (){
	// init with the java object from LiveConnect
	if (orpheus.service.isLoggedIn) {
		orpheus.show();
	}

	orpheus.service.registerToolbar(orpheus);
	orpheus.window.addEventListener("unload", function(e){orpheus.unload();}, false);
	document.getElementById("appcontent").addEventListener("load", orpheus.onPageLoad, true); 
	gBrowser.mPanelContainer.addEventListener("select", orpheus.onTabChange, false);
};

/** This method is called  on unload event. It unregister window from the Orpheus service. */
orpheus.unload = function () {
	orpheus.service.unregisterToolbar(orpheus);
	gBrowser.mPanelContainer.removeEventListener("select", orpheus.onTabChange, false);
};

/** Implements the Login button action. */
orpheus.logInClick = function () {
	if (!orpheus.initialized) {
		orpheus.service.init(java);
		orpheus.load();
		orpheus.initialized = true;
	}
    
	orpheus.openPopup("login");
};
	
/** Implements the Logout button action. */
orpheus.logOutClick = function () {
	orpheus.openPopup("logout");
};

/** Implements the Games button action. */	
orpheus.gamesClick = function () {
	orpheus.openPopup("games");
};

/** Implements the Unlocked domains button action. */	
orpheus.unlockedClick = function () {
	orpheus.openPopup("unlocked", this.service.getWorkingGame());
};

/** Implements the Upcoming domains button action. */		
orpheus.upcomingClick = function () {
		orpheus.openPopup("upcoming", this.service.getWorkingGame());
};
	
/** Implements the Leaderboard button action. */	
orpheus.leaderboardClick = function leaderboardClick() {
	orpheus.openPopup("leaderboard", this.service.getWorkingGame());
};


orpheus.testDomainClick = function() {
   	var window = null;
	if (orpheus.window.content.name != POPUP_NAME) {
   		window = orpheus.window.content;
   	} else if (orpheus.parent) {
   		window = orpheus.parent;
   	}
   	
   	if ((window != null) && !window.closed) {
   		var result = orpheus.service.forcedDomainTest(window.location.hostname, true);
   		if (result) {
   			orpheus.openPopupURL(result);
   		}
   	}
   	
};

/** Implements the Test Object context menu action. */		
orpheus.testObject = function () {
	var target = gContextMenu.target;
	// the content of the test object - may behave oddly if contains inner html
	var result = orpheus.service.currentTargetTest(target, orpheus.window.content.location);
	if (result) {
		orpheus.openPopupURL(result);
	} else {
		alert("Nope, not quite.\nMake sure you've got the right clue and the right page on our domain," + 
		"and then try again. Good hunting!");
	}
};

/** 
 * Helper method. Returns the configured URL for action.
 * @param action {String} the name of the action.
 * @returns the URL for acton.
 */
orpheus.getURL = function(action) {
	return orpheus.prefs.getCharPref(CTX) + orpheus.prefs.getCharPref(PREFS_URL_PREFIX + action);
};
	
/** 
 * Opens a popup window for action and replaces the $0 placeholder in the URL with actual value.
 * @param action {String} the action to execute.
 * @param param the actual value of parameter.
 */
orpheus.openPopup = function (action, param) {
	var url = orpheus.getURL(action);
	if (param) {
		url = url.replace(/\$0/, param);
	}
	
	orpheus.openPopupURL(url);
};
	
/**
 * Opens a popup window for the given URL.
 * @param url the URL of target site.
 */
orpheus.openPopupURL = function (url) {
	var popup = orpheus.openPopupWindow(url);
	popup.focus();
};
	
/**
 * Opens a popup windows with the give content.
 * @param dialogContent the content of dialog box.
 */
orpheus.openPopupWithContent = function (dialogContent) {
	var popup = orpheus.window.openDialog("about:blank", new Date().getTime() + "" + Math.random(), 
		WINDOW_FEATURES);
	popup.document.open();
	popup.document.write(dialogContent);
	popup.document.close();
	popup.focus();
};
	
/**
 * Opens a popup window and returns reference to it.
 * @param url the popup URL.
 * @returns the reference to the new window.
 */
orpheus.openPopupWindow = function(url) {
	var win = orpheus.service.popup;
	if ((win == null) || win.closed) {
    	win = orpheus.window.open(url, POPUP_NAME, WINDOW_FEATURES);
    	orpheus.service.popup = win;
    	//win.name = POPUP_NAME;
    	//win.content.name = POPUP_NAME;
    
    } else {
    	win.location = url;
    }
    
    if ((orpheus.window.content.name == POPUP_NAME) &&  orpheus.parent) {
        window.opener = orpheus.parent;
    } else {
    	win.content.opener = orpheus.window.content;
    }
    
    return win;
    
}

/**
 * Hides the additional toolbar buttons.
 */
orpheus.hide = function() {
   	var doc = orpheus.window.document;
   	hideElement("oLogIn", false, doc);
	hideElement("oLogOut", true, doc);
	hideElement("bbox", true, doc);
	hideElement("context-testobject", true, doc);
	hideElement("ctx-separator", true, doc);
};
    
/**
 * Shows the additional toolbar buttons.
 */
orpheus.show = function() {
	var doc = orpheus.window.document;
    hideElement("oLogIn", true, doc);
	hideElement("oLogOut", false, doc);
	hideElement("bbox", false, doc);
	hideElement("context-testobject", false, doc);
	hideElement("ctx-separator", false, doc);
	// check if the active game is set and disable buttons if is not
	orpheus.disableButtons(!orpheus.service.isWorkingGameSet());
	
	if (orpheus.window.content.name == POPUP_NAME) {
		disableElement("oTestDoamin", true);		
	}
	
};
    

/**
 * These method disables the "Unlocked Sites", "Upcoming Sites" and "Leaderboard" buttons.
 * 
 * @param val the value of the "disabled" attribute.
 */ 
orpheus.disableButtons = function(val) {
	disableElement("oUnlockedSites", val);
	disableElement("oUpcomingSites", val);
	disableElement("oLeaderboard", val);
}
    
/**
 * The listener for the page changed event. It performs test domain action.
 * @param event the page load event.
 */
orpheus.onPageLoad = function (event)  {
	// if window is popup - set the parent to main window
	if (orpheus.window.content.name == POPUP_NAME) {
		orpheus.parent = window.content.opener;// orpheus.window.content.arguments[0];
	}
	// ignore the plug-in alert window
	if (orpheus.window.content.name != POPUP_NAME) {
   		var result = orpheus.service.isURLCorrect(orpheus.window.content.location);
   		orpheus.targetPageStatusChage(result);
   	}
}

orpheus.onTabChange = function (event) {
	var browser = gBrowser.getBrowserAtIndex(gBrowser.mTabContainer.selectedIndex);
	var result = orpheus.service.isURLCorrect(browser.contentDocument.location);
	orpheus.targetPageStatusChage(result);
 }

orpheus.targetPageStatusChage = function (isPageCorrect) {
  		var doc = orpheus.window.document;
   		if (isPageCorrect) {
   			hideElement("oPageIncorrect", true, doc);
			hideElement("oPageCorrect", false, doc);
   		} else {
   			hideElement("oPageIncorrect", false, doc);
			hideElement("oPageCorrect", true, doc);
   		}
}

/**
 * An utility function which sets the "hidden" attribute of the element to given value.
 * @param id the id of the element to be hidden.
 * @param hide the true/false value of the hidden attribute.
 * @param documnet the documnet from which the element is taken.
 */    
function hideElement(id, hide, document) {
	var el = document.getElementById(id);
	if (el) {
		el.setAttribute("hidden", hide);
	}
};

/**
 * An utility function which sets the "disabled" attribute of the element to given value.
 * @param id the id of the element to be hidden.
 * @param hide the true/false value of the disabled attribute.
 * @param documnet the documnet from which the element is taken.
 */    
function disableElement(id, value) {
	var el = document.getElementById(id);
	if (el) {
		el.setAttribute("disabled", value);
	}
}

// window events
window.addEventListener("load", orpheus.load, false);

/*window.addEventListener("unload", orpheus.unload, false);
// the page load event
document.getElementById("appcontent").addEventListener("load", orpheus.onPageLoad, true); 
*/