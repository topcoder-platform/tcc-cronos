/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */


function getExtensionPath(extensionName)  {
    var chromeRegistry =
        Components.classes["@mozilla.org/chrome/chrome-registry;1"]
            .getService(Components.interfaces.nsIChromeRegistry);
            
    var uri =
        Components.classes["@mozilla.org/network/standard-url;1"]
            .createInstance(Components.interfaces.nsIURI);
    
    uri.spec = "chrome://" + extensionName + "/content/";
    
    var path = chromeRegistry.convertChromeURL(uri);
    if (typeof(path) == "object") {
        path = path.spec;
    }
    
    path = path.substring(0, path.indexOf("/chrome/") + 1);
    alert(path);
    return path;
}; 




/**
 * <p>
 * This javascript file contains wrapper functions used to reference the Java code in the
 * com.orpheus.plugin.firefox package.
 * </p>
 *
 * <p>
 * This file is additional functionality, allowing the user to access the functions of the Java component
 * directly through Javascript. This file handles creating the FirefoxExtensionHelper and
 * JavascriptUIEventListener instances. The init function handles instantiating the helper and listener member
 * variables. All the methods use those values when accessing the FirefoxExtensionHelper class.
 * The "init" function has to be called before any other functionality is available.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */

/**
 * <p>
 * This member variable holds a reference to the FirefoxExtensionHelper instance to wrap
 * the functionality in.
 * </p>
 *
 * <p>
 * This member variable is initialized in the "init" method, created via Java reflection through Javascript calls.
 * It is reference in all calls in this class. After this value has been initialized, all the method calls
 * can be made directly, like "helper.logInClick()".
 * </p>
 *
 * @type com.orpheus.plugin.firefox.FirefoxExtensionHelper
 */
var helper;

/**
 * <p>
 * This member variable is initialized via Java reflection in the "init" method to a new JavascriptUIEventListener
 * class. This is used when adding and removing event handlers in the add / remove * Handler methods.
 * </p>
 *
 * @type com.orpheus.plugin.firefox.eventListeners.JavascriptUIEventListener
 */
var listener;

/**
 * <p>
 * This member variable holds a reference to a parent window, which is used instead of the actual
 * helper instance, if this javascript is loaded in a child popup window. If this value is supplied, all
 * calls to these methods should delegate to the parent window, via "parentWindow.call("function name", [parameters])"
 * instead of delegating to the "helper" member variable.
 * </p>
 *
 * @type javascript.object
 */
var parentWindow;

/**
 * <p>
 * This method wraps the "helper.logInClick()" method, indicating the user clicked the "Log In" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function logInClick() {
    if (parentWindow != null) {
        parentWindow.eval("logInClick");
    } else {
        helper.logInClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.successfulLogIn()" method, indicating the user successfully logged in.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function successfulLogIn() {
    if (parentWindow != null) {
        parentWindow.eval("successfulLogIn");
    } else {
        helper.successfulLogIn();
    }
}

/**
 * <p>
 * This method wraps the "helper.showActiveGamesClick()" method, indicating the user clicked the
 * "Show Active Games" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showActiveGamesClick() {
    if (parentWindow != null) {
        parentWindow.eval("showActiveGamesClick");
    } else {
        helper.showActiveGamesClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.showMyGamesClick()" method, indicating the user clicked the "Show My Games" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showMyGamesClick() {
    if (parentWindow != null) {
        parentWindow.eval("showMyGamesClick");
    } else {
        helper.showMyGamesClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.showUnlockedDomainsClick()" method, indicating the user clicked the
 * "Show Unlocked Domains" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showUnlockedDomainsClick() {
    if (parentWindow != null) {
        parentWindow.eval("showUnlockedDomainsClick");
    } else {
        helper.showUnlockedDomainsClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.showUpcomingGamesClick()" method, indicating the user clicked the
 * "Show Upcoming Games" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showUpcomingGamesClick() {
    if (parentWindow != null) {
        parentWindow.eval("showUpcomingGamesClick");
    } else {
        helper.showUpcomingGamesClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.showLeadersClick()" method, indicating the user clicked the "Show Leaders" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showLeadersClick() {
    if (parentWindow != null) {
        parentWindow.eval("showLeadersClick");
    } else {
        helper.showLeadersClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.showLatestClueClick()" method, indicating the user clicked the
 * "Show Latest Clue" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function showLatestClueClick() {
    if (parentWindow != null) {
        parentWindow.eval("showLatestClueClick");
    } else {
        helper.showLatestClueClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.pageChanged()" method, indicating the user changed the currrent page.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} newPage The page the user switched to.
 */
function pageChanged(newPage) {
    if (parentWindow != null) {
        parentWindow.call("pageChanged", [newPage]);
    } else {
        helper.pageChanged(newPage);
    }
}

/**
 * <p>
 * This method wraps the "helper.setWorkingGameID()" method, which allows the user to set the ID of the working
 * game in the persistent store.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {long} id The working game ID to set.
 */
function setWorkingGameID(id) {
    if (parentWindow != null) {
        parentWindow.call("setWorkingGameID", [id]);
    } else {
        helper.setWorkingGameID(id);
    }
}

/**
 * <p>
 * This method wraps the "helper.getWorkingGameID()" method, which allows the user to get the ID of the working
 * game from the persistent store.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @return The current working game ID.
 * @type long
 */
function getWorkingGameID() {
    if (parentWindow != null) {
        return parentWindow.eval("getWorkingGameID");
    } else {
        return helper.getWorkingGameID();
    }
}

/**
 * <p>
 * This method wraps the "helper.setCurrentTargetID()" method, which allows the user to set the ID of the current
 * target in the persistent store.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} id The new id of the current target.
 * @param {int} sequenceNumber The sequence number to use.
 */
function setCurrentTargetID(id, sequenceNumber) {
    if (parentWindow != null) {
        parentWindow.call("setCurrentTargetID", [id, sequenceNumber]);
    } else {
        helper.setCurrentTargetID(id, sequenceNumber);
    }
}

/**
 * <p>
 * This method wraps the "helper.getCurrentTargetID()" method, which allows the user to get the ID of the current
 * target from the persistent store.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @return The ID of the current target.
 * @type String
 */
function getCurrentTargetID() {
    if (parentWindow != null) {
        return parentWindow.eval("getCurrentTargetID");
    } else {
        return helper.getCurrentTargetID();
    }
}

/**
 * <p>
 * This method wraps the "helper.pollServerNow()" method, which allows the user to force an immediate poll to the
 * Orpheus server.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function pollServerNow() {
    if (parentWindow != null) {
        parentWindow.eval("pollServerNow");
    } else {
        helper.pollServerNow();
    }
}

/**
 * <p>
 * This method wraps the "helper.setPollTime()" method, which allows the user to set the amount of time, in minutes,
 * between polls to the Orpheus server.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {int} time The time, in minutes, between polls.
 */
function setPollTime(time) {
    if (parentWindow != null) {
        parentWindow.call("setPollTime", [time]);
    } else {
        helper.setPollTime(time);
    }
}

/**
 * <p>
 * This method wraps the "helper.getPollTime()" method, which allows the user to get the amount of time,
 * in minutes, between polls to the server.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @return the amount of time, in minutes, between polls to the server.
 * @type int
 */
function getPollTime() {
    if (parentWindow != null) {
        return parentWindow.eval("getPollTime");
    } else {
        return helper.getPollTime();
    }
}

/**
 * <p>
 * This method is used to test if the String element given matches the current target. This method should just
 * wrap helper.currentTargetTest.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} element The element to test
 *
 * @return whether or not the object given is the current target.
 * @type boolean
 */
function currentTargetTest(element) {
    if (parentWindow != null) {
        parentWindow.call("currentTargetTest", [element]);
    } else {
        helper.currentTargetTest(element);
    }
}

/**
 * <p>
 * This method wraps the "helper.isPopupWindow()" method, which allows the user to see if the current browser
 * window is a popup opened by the component.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @return whether or not the current window is a pop-up.
 * @type boolean
 */
function isPopupWindow() {
    if (parentWindow != null) {
        parentWindow.eval("isPopupWindow");
    } else {
        return window.opener != null;
    }
}

/**
 * <p>
 * This method pops up a new window, containing the URL given. The options are all passed to "helper.popupWindow".
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} url The URL to open.
 * @param {boolean} status Whether or not to show the status bar.
 * @param {boolean} toolbar Whether or not to show the toolbar.
 * @param {boolean} location Whether or not to show the location bar.
 * @param {boolean} menubar Whether or not to show the menu bar.
 * @param {boolean} directories Whether or not to show the directories.
 * @param {boolean} resizable Whether or not the new window is resizable.
 * @param {boolean} scrollbars Whether or not to show scrollbars on the new window.
 * @param {int} height The height of the new window.
 * @param {int} width The width of the new window.
 */
function popupWindow(url, status, toolbar, location, menubar, directories, resizable, scrollbars, height, width) {
    if (parentWindow != null) {
        parentWindow.call("popupWindow", [url, status, toolbar, location, menubar, directories, resizable,
            scrollbars, height, width]);
    } else {
        helper.popupWindow(url, status, toolbar, location, menubar, directories, resizable,
            scrollbars, height, width);
    }
}

/**
 * <p>
 * This method pops up a new window, containing the content given. The options are all passed to "helper.popupWindow".
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} content The content the popup window will contain.
 * @param {boolean} status Whether or not to show the status bar.
 * @param {boolean} toolbar Whether or not to show the toolbar.
 * @param {boolean} location Whether or not to show the location bar.
 * @param {boolean} menubar Whether or not to show the menu bar.
 * @param {boolean} directories Whether or not to show the directories.
 * @param {boolean} resizable Whether or not the new window is resizable.
 * @param {boolean} scrollbars Whether or not to show scrollbars on the new window.
 * @param {int} height The height of the new window.
 * @param {int} width The width of the new window.
 */
function popupWindowWithContent(content, status, toolbar, location, menubar, directories, resizable,
    scrollbars, height, width) {
    if (parentWindow != null) {
        parentWindow.call("popupWindowWithContent", [content, status, toolbar, location, menubar, directories,
            resizable, scrollbars, height, width]);
    } else {
        helper.popupWindowWithContent(content, status, toolbar, location, menubar, directories, resizable,
            scrollbars, height, width);
    }
}

/**
 * <p>
 * This method wraps the "helper.logOutClick()" method, indicating the user clicked the "Log Out" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function logOutClick() {
    if (parentWindow != null) {
        parentWindow.eval("logOutClick");
    } else {
        helper.logOutClick();
    }
}

/**
 * <p>
 * This method wraps the "helper.successfulLogOut()" method, indicating the user successfully logged out.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 */
function successfulLogOut() {
    if (parentWindow != null) {
        parentWindow.eval("successfulLogOut");
    } else {
        helper.successfulLogOut();
    }
}

/**
 * <p>
 * This method initializes the member variables in this class.
 * This method uses Java reflection to set the listener and uiEventType values.
 * This method also sets the current page of the helper, allowing it to know what domain is the current one.
 * Note that if the parentWindow variable is set, this method should do nothing.
 * Since we can reference the applet code by its name as embedded on the page, we use that to set the helper
 * member variable value.
 * </p>
 *
 * @param {String} appletName The name of the applet to reference.
 */
function init(appletName) {
    if (parentWindow != null) {
        return;
    }
    alert("jestem initem");
    netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite");
    netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
    helper = document.getElementById(appletName);
    
    
    var firefoxClassLoaderURL = new java.net.URL(getExtensionPath("orpheus") + "components/firefoxClassLoader.jar"); 
    var bootstrapClassLoader = java.net.URLClassLoader.newInstance([ firefoxClassLoaderURL ]); 
    var policyClass = java.lang.Class.forName(
        "edu.mit.simile.firefoxClassLoader.URLSetPolicy",
        true,
        bootstrapClassLoader
    );
    var policy = policyClass.newInstance(); 
    policy.setOuterPolicy(java.security.Policy.getPolicy());
    java.security.Policy.setPolicy(policy); 
    policy.addPermission(new java.security.AllPermission()); 
    


		var libPath = getExtensionPath("orpheus") + "components/";
        var jarFilenames = [
        	"base_exception.jar",
        	"configuration_manager.jar",
        	"typesafe_enum.jar",
        	"object_factory.jar",
        	"bloom_filter.jar",
        	"hashing_utility.jar",
        	"rss_generator.jar",
            "client_logic_for_firefox.jar"
        ]; 
        
        var jarFilepaths = [];
        for (var i = 0; i < jarFilenames.length; i++) {
            var ur = new java.net.URL(libPath + jarFilenames[i]);
            jarFilepaths.push(ur);
            policy.addURL(ur);
        } 



	path = getExtensionPath("orpheus") + "components/client_logic_for_firefox.jar"
	var pURL = new java.net.URL(path);
	//policy.addURL(pURL); 
	//alert("path: " + path);
    var cl = new java.net.URLClassLoader(jarFilepaths);

//alert(cl);
var aClass = java.lang.Class.forName("com.orpheus.plugin.firefox.FirefoxExtensionHelperLite", true, cl);

helper = aClass.newInstance();
    alert(helper);
//    helper.initialize();
    listener = helper.getUIEventListener();
    alert("hay ho!");
}

function getPath() {
	return getExtensionPath("orpheus") + "components/all.jar";
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Log In" click event.
 * The Javascript function with the given name will be called in response to the "Log In" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addLogInClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addLogInClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("login"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Log In" click event.
 * The Javascript function with the given name will no longer be called in response to the "Log In" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeLogInClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeLogInClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("login"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the successful log in event.
 * The Javascript function with the given name will be called in response to a successful log in.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addSuccessfulLogInHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addSuccessfulLogInHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("successful login"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the successful login event
 * The Javascript function with the given name will no longer be called in response to the successful login event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeSuccessfulLogInHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeSuccessfulLogInHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("successful login"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Log Out" click event.
 * The Javascript function with the given name will be called in response to the "Log Out" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addLogOutClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addLogOutClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("logout"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Log Out" click event.
 * The Javascript function with the given name will no longer be called in response to the "Log In" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeLogOutClickHandler(functionName) {
     if (parentWindow != null) {
        parentWindow.call("removeLogOutClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("logout"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the successful log out event.
 * The Javascript function with the given name will be called in response to a successful log out.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addSuccessfulLogOutHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addSuccessfulLogOutHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("successful logout"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the successful logout event
 * The Javascript function with the given name will no longer be called in response to the successful logout event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeSuccessfulLogOutHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeSuccessfulLogOutHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("successful logout"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show Active Games" click event.
 * The Javascript function with the given name will be called in response to the "Show Active Games" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowActiveGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowActiveGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show active games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show Active Games" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show Active Games" button.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowActiveGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowActiveGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show active games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show My Games" click event.
 * The Javascript function with the given name will be called in response to the "Show My Games" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowMyGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowMyGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show my games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show My Games" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show My Games" button
 * click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowMyGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowMyGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show my games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show Unlocked Domains" click event.
 * The Javascript function with the given name will be called in response to the "Show Unlocked Domains" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowUnlockedDomainsClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowUnlockedDomainsClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show unlocked domains"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show Unlocked Domains" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show Unlocked Domains"
 * button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowUnlockedDomainsClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowUnlockedDomainsClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show unlocked domains"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show Upcoming Games" click event.
 * The Javascript function with the given name will be called in response to the "Show Upcoming Games" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowUpcomingGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowUpcomingGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show upcoming games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show Upcoming Games" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show Upcoming Games"
 * button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowUpcomingGamesClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowUpcomingGamesClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show upcoming games"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show Leaders" click event.
 * The Javascript function with the given name will be called in response to the "Show Leaders" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowLeadersClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowLeadersClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show leaders"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show Leaders" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show Leaders" button
 * click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowLeadersClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowLeadersClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show leaders"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the "Show Latest Clue" click event.
 * The Javascript function with the given name will be called in response to the "Show Latest Clue" button click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addShowLatestClueClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addShowLatestClueClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("show latest clue"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the "Show Latest Clue" click event.
 * The Javascript function with the given name will no longer be called in response to the "Show Latest Clue" button
 * click.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeShowLatestClueClickHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeShowLatestClueClickHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("show latest clue"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the page changed event.
 * The Javascript function with the given name will be called in response to the page changed event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addPageChangedHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addPageChangedHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("page changed"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the page changed event.
 * The Javascript function with the given name will no longer be called in response to the page changed event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removePageChangedHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removePageChangedHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("page changed"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method adds the given function name as an event handler for the user changing the working game
 * The Javascript function with the given name will be called in response to the working game change event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to add as an event handler.
 */
function addWorkingGameIDUpdateHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("addWorkingGameIDUpdateHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.addFunctionName(helper.getUIEventTypeEnum("working game id update"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method removes the given function name as an event handler for the working game ID update
 * The Javascript function with the given name will no longer be called in response to the update ID event.
 * </p>
 *
 * <p>
 * Note that the parentWindow should be used instead, if it is set.
 * </p>
 *
 * @param {String} functionName The name of the function to remove as an event handler.
 */
function removeWorkingGameIDUpdateHandler(functionName) {
    if (parentWindow != null) {
        parentWindow.call("removeWorkingGameIDUpdateHandler", [functionName]);
    } else {
        helper.removeEventListener(listener);
        listener.removeFunctionName(helper.getUIEventTypeEnum("working game id update"), functionName);
        helper.addEventListener(listener);
    }
}

/**
 * <p>
 * This method tells the Javascript that it is running as a client of the parent window given, instead
 * of as a standalone browser window. In this case, all methods will delegate back to the client window,
 * instead of creating a new FirefoxExtensionHelper instance and running off of it. For this,
 * the methods will call "parentWindow.call("function name", [parameters])", instead of delegating
 * to the helper instance. This allows both the client and parent windows to use the same instance of
 * the component.
 * </p>
 *
 * @param {object} parentWindow The parent window to delegate calls to.
 */
function setParentWindow(pWindow) {
    parentWindow = pWindow;
}


///// ------ FireFox Orpehus Extension part



var oLoggedIn = false;
var timer;
var prefs = Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);
var orpheus = {
	POPUP_NAME: "orpheus",
	
	service : Components.classes["@topcoder.com/orpheus;1"]
                                 .getService(Components.interfaces.tcIOrpheus),

	init : function initializer() {
		if (orpheus.service.isLoggedIn) {
			orpheus.loggedIn();
		} else {
			orpheus.loggedOut();
		}
		
		orpheus.service.registerToolbar(this);
		//content.alert("test");
		//init("TheApplet");
//		alert(prefs.getCharPref("extension.orpheus.context"));
	},
	
	close : function () {
		orpheus.service.unregisterToolbar(this);
	},

	logInClick : function() {
		//var _popup = createPopup(POPUP_NAME, "<html><body>TODO: redirect to login page</body></html>");
		/*var _popup = openPopup(POPUP_NAME, "http://topcoder.com/tc");
		_popup.title = "Login";
		_popup.focus();
		_popup.document.oLoggedIn = oLoggedIn;
		orpheus.loggedIn();
		oLoggedIn = true;
		_popup.addEventListener("onload", orpheus.init, false);
		*/
		//init('client_jar');
		//alert(window._content);
		//alert("login click: " + helper.setWindow(orpheus));
		//logInClick();
		
		//popupWindow("http://google.pl", true, true, true, false, false, false, true, 300, 400);
		
		//timer = setTimeout(timerEV, 10000);
		orpheus.loggedIn();
		orpheus.service.isLoggedIn = true;
		//createPopup(orpheus.POPUP_NAME, "<html><body>You're logged in!</body></html>");
		openPopup(orpheus.POPUP_NAME, "http://localhost:8080/orpheus/test.html");
	},
	
	logOutClick : function logOutClick() {
		var _popup = createPopup(orpheus.POPUP_NAME, "<html><body>TODO: redirect to logout page</body></html>");
		_popup.title = "Logout";
		_popup.focus();
		this.service.LoggedOut();
		this.loggedOut();
	},
	
	gamesClick : function gamesClick() {
		var _popup = createPopup(POPUP_NAME, "<html><body>TODO: redirect to all games page</body></html>");
		_popup.title = "All games";
		_popup.focus();
	},
	
	unlockedClick : function unlockedClick() {
		var _popup = createPopup(POPUP_NAME, "<html><body>TODO: redirect to unlocked sites page</body></html>");
		_popup.title = "Unlocked sites";
		_popup.focus();
	},
	
	upcomingClick : function upcominClick() {
		var _popup = createPopup(POPUP_NAME, "<html><body>TODO: redirect to upcoming sites page</body></html>");
		_popup.title = "Upcoming sites";
		_popup.focus();
	},
	
	leaderboardClick : function leaderboardClick() {
		var _popup = createPopup(POPUP_NAME, "<html><body>TODO: redirect to leaderboard sites page</body></html>");
		_popup.title = "Leaderboard";
		_popup.focus();
	},
	
	loggedIn : function () {
		hideElement("oLogIn", true);
		hideElement("oLogOut", false);
		hideElement("bbox", false);
		hideElement("context-testobject", false);
		hideElement("ctx-separator", false);
		oLoggedIn = true;
		return "dziala";
	},
	
	loggedOut : function loggedOut() {
		hideElement("oLogIn", false);
		hideElement("oLogOut", true);
		hideElement("bbox", true);
		hideElement("context-testobject", true);
		hideElement("ctx-separator", true);
		oLoggedIn = false;
	},
	
	testObject : function testObject() {
		var target = gContextMenu.target;
		
		alert(target.innerHTML);
	}

}

function openPopup(target, url) {
	return content.open(url, target, "height=500,width=600,left=100,top=100")
}

function openPopup2(target, url) {
	return window.open(url, target, "toolbars=yes,resizable=yes,scrollbars=yes,status=yes,height=500,width=600,left=100,top=100")
}

function createPopup(target, content) {
	var popup = openPopup(target, "about:blank");
	//popup.addEventListener("load", orpheus.init, false);
	popup.document.open();
	popup.document.write(content);
	popup.document.close();
	
	return popup;
}


function hideElement(id, hide) {
	var el = document.getElementById(id);
	if (el) {
		el.setAttribute("hidden", hide);
	}
}

window.addEventListener("load", orpheus.init, false);
window.addEventListener("unload", orpheus.close, false);

function timerEV() {
	timer = setTimeout(timerEV, 10000);
	createPopup(orpheus.POPUP_NAME, helper.longOp()).focus();
}

