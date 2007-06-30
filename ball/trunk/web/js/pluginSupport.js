// Tests if the browser is Internet Explorer
function isIE() {
    var userAgent = navigator.userAgent;
    var appName = navigator.appName;
    return appName == 'Microsoft Internet Explorer';
}

// Tests if the browser is FireFox
function isFF() {
    var userAgent = navigator.userAgent;
    var appName = navigator.appName;
    return appName == 'Netscape';
}

// Notifies the Orpheus Plugin on user logon
function notifyOnLogin() {
    try {
        if (isIE()) {
            // For IE
            if (window.external) {
                window.external.LoggedIn();
            }
        } else {
            // For FF
            if (window.external) {
                window.external.LoggedIn();
            }
        }
    } catch(ex) {
        // ignore
    }
}

// Notifies the Orpheus Plugin on user logout
function notifyOnLogout(closeWindow) {
    try {
        if (isIE()) {
            // For IE
            if (window.external) {
                window.external.LoggedOut();
                if (closeWindow) {
                    window.close();
                }
            }
        } else {
            // For FF
            if (window.external) {
                window.external.LoggedOut();
                if (closeWindow) {
                    window.close();
                }
            }
        }
    } catch(ex) {
        // ignore
    }
}

// Notifies the Orpheus Plugin on currently selected game
function setCurrentGame(gameId) {
    if (isIE()) {
        // For IE
        if (window.external) {
            window.external.SetWorkingGame(gameId);
        }
    } else {
        // For FF
        if (window.external) {
            window.external.SetWorkingGame(gameId);
        }
    }
}


// Notifies the Orpheus Plugin on current target for the currently viewed domain
function setCurrentTarget(hash, sequence) {
    if (isIE()) {
        // For IE
        if (window.external) {
            window.external.SetCurrentTarget(hash, sequence);
        }
    } else {
        // For FF
        if (window.external) {
            window.external.SetCurrentTarget(hash, sequence);
        }
    }
}

// Notifies the Orpheus Plugin on necessity to poll server for messages
function pollMessages() {
    if (isIE()) {
        // For IE
        if (window.external) {
            window.external.PollMessages();
        }
    } else {
        // For FF
        if (window.external) {
            window.external.PollMessages();
        }
    }
}

// Attempts to open the resource referenced by link in plugin's parent window
function openInParentWindow(link) {
    if (isFF()) {
        if (window.arguments) {
            if (window.arguments[0] && (window.arguments[0] != window) && !window.arguments[0].closed) {
                window.arguments[0].location = link.href;
            } else {
                window.arguments[0] = window.open(link.href, 'OrpheusMainWindow');
            }
        } else {
            if (window.opener) {
                if (!window.opener.closed) {
                    window.opener.location = link.href;
                } else {
                    window.opener = window.open(link.href, 'OrpheusMainWindow');
                }
            } else {
                window.opener = window.open(link.href, 'OrpheusMainWindow');
            }
        }
    } else {
        window.open(link.href, 'OrpheusMainWindow');
    }
}

// Changes the window location to specified URL
function goto(url) {
    if (isFF()) {
        window.location = url;
    } else {
        window.location = url;
    }
    return false;
}
