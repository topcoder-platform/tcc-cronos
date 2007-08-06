//window.addEventListener("load", init2, false); 
//window.addEventListener("popupshowing", function eve(e){alert("ctx:" + e.target);}, false);
function abpInit() {
	document.addEventListener("contextmenu", displaymenu, true);
	alert("init");
}

 function displaymenu(e){
 	alert(e.target.tagName);
// 	e.preventDefault();
 	return false;
 }

function someEvent(e) {
	var target = gContextMenu.target;//(gContextMenu.target ? new XPCNativeWrapper(gContextMenu.target) : null); 
	alert("Event: " + target);
}


function popUp(url) {
		return window.openDialog(url, "mine", "addressbar=yes,resizable=yes,scrollbars=yes,status=yes,height=500,width=600,left=100,top=100");
	};


	
	
	function newContent()
{
var newWindow = popUp("http://topcoder.com");
newWindow.focus();
//alert(newWindow);
//newWindow.document.open();
//newWindow.document.write("<h1>Out with the old - in with the new!</h1>");
//newWindow.document.close();

//alert("load new content");
}
	function close() {
		
	};
	
	
	function testObject() {
	alert(content);
	//gBrowser.removeTab(gBrowser.mCurrentTab);
	
	var txt = '';
	var foundIn = '';
	if (content.getSelection)
	{
		txt = content.getSelection();
		foundIn = 'window.getSelection()';
	}
	else if (content.document.getSelection)
	{
		txt = document.getSelection();
		foundIn = 'document.getSelection()';
	} else if (content.document.selection)
	{
		txt = content.document.selection.createRange().text;
		foundIn = 'document.selection.createRange()';
	}
    else {
	    alert("DUPA");
	}
	alert('Found in: ' + foundIn + '\n' + txt);
	
	};
	
	function testObject(menu) {
		alert(menu.abpBgData);
	}
	
	
	function testObject() {
		var focusedElement = document.commandDispatcher.focusedElement;
		alert(document.commandDispatcher);
	}
	
	function hide() {
		var el = document.getElementById("TC");
		el.setAttribute("hidden", true);
	}
	
	
	
	var ctxMenu = {
    QueryInterface: function QueryInterface(aIID) {
        if (aIID.equals(Components.interfaces.nsIContextMenuListener) ||
            aIID.equals(Components.interfaces.nsISupports))
            return this;
        throw Components.results.NS_NOINTERFACE;
    },
    onShowContextMenu: function onShowContextMenu(contextFlags,event ,node) {
    	alert("hey! " + node);
    },
    
    handleEvent: function handleEvent(e) {
    	alert("yo! " + e.target);
    }
    }
    
    function init2() {
//    gBrowser.mCurrentBrowser.addContextMenuListener(ctxMenu); 
document.addEventListener("contextmenu", ctxMenu, true);
}