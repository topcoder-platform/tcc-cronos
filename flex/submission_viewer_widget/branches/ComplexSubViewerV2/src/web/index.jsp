<%@ page import="com.topcoder.security.auth.module.UserProfilePrincipal" %>
<%@ page import="com.topcoder.security.authenticationmanager.credentials.UserPasswordCredentials" %>


<!--
Smart developers always View Source.

This application was built using Adobe Flex, an open source framework
for building rich Internet applications that get delivered via the
Flash Player or to desktops via Adobe AIR.

Learn more about Flex at http://flex.org
// -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="expires" content="0" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->

<title>TopCoder Direct 2.1 : Welcome</title>

<!--  BEGIN Browser History required section -->
<script src="/i/cockpit/history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 10;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
// -->
</script>

<link rel="stylesheet" type="text/css" media="screen" href="http://www.topcoder.com/css/home/screen.css" />
<link rel="stylesheet" type="text/css" media="screen" href="http://www.topcoder.com/css/home/main-navigation.css" />

<!--[if IE 7]><link rel="stylesheet" type="text/css" href="http://www.topcoder.com/css/home/screen-ie7.css" /><![endif]-->
<!--[if IE 6]><link rel="stylesheet" type="text/css" href="http://www.topcoder.com/css/home/screen-ie6.css" /><![endif]-->

<script src="http://www.topcoder.com/js/home/jquery-1.2.6.min.js" type="text/javascript"></script>
<script src="http://www.topcoder.com/js/home/jquery.hoverIntent.minified.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".expand-group").click(function(){
		var active = $(this).next("ul");
		var activeState = $(active).css("display");
		
		if (activeState == "none") {
			$(active).slideDown();
			$(".submenu").not(active).slideUp();
			return false;
		} 
		else 
		{
			$(this).next("ul").slideUp();
			return false;
		}
		});
		
	$("#nav ul li").hoverIntent(function(){
		$(this).children("ul").slideDown("fast");
	}, function() {
		$(this).children("ul").slideUp("fast");
	});
	
	$("#nav ul ul li").hover(function() {
		$(this).parents("#nav ul li").children('a').addClass("active-item");
	}, function() {
		$(this).parents("#nav ul li").children('a').removeClass("active-item");
	});
	
});
</script>

<script type="text/javascript" src="/i/cockpit/js/swfobject.js"></script>

</head>

<% response.setHeader("Cache-Control","no-cache, must-revalidate, no-store\nPragma: no-cache\n"); %>

<%
		
    UserProfilePrincipal principal=(UserProfilePrincipal)request.getUserPrincipal();
	String projectwsdl = getServletConfig().getServletContext().getInitParameter("project_service_facade_wsdl");
	String contestwsdl = getServletConfig().getServletContext().getInitParameter("contest_service_facade_wsdl");
	String blazedsendpoint = getServletConfig().getServletContext().getInitParameter("blazeds-endpoint");
	String hostAddress=getServletConfig().getServletContext().getInitParameter("hostAddress");
	String studioAddress=getServletConfig().getServletContext().getInitParameter("studioAddress");
	long t = new java.util.Date().getTime();
    String portNumber=Integer.toString(request.getServerPort());
%>

<body scroll="auto" onload="init()">
    <div id="header">
        <div class="wrapper">
            
<%-- MASTHEAD AND LOGO --%>
            <h1><a href="http://www.topcoder.com" title="TopCoder"><span>TopCoder Direct</span></a> <sup><small>BETA</small></sup></h1>
            <h2 id="ready_engage"><span>Ready.. ENGAGE</span></h2>
            
<%-- MAIN NAVIGATION --%>
            <div id="nav">
                <h3 class="hide">Main Navigation</h3>
                <ul>
                    <li class="left"><a href="http://www.topcoder.com">TopCoder Home</a></li>
                    <li><a href="http://software.topcoder.com/">Engage</a>
                        <ul>
                            <li><a href="http://www.topcoder.com/direct/">TopCoder Direct</a></li>
                            <li><a href="http://software.topcoder.com/catalog/">Components</a></li>
                            <li><a href="http://software.topcoder.com/indexTCS.jsp">Software</a></li>
                            <li><a href="http://software.topcoder.com/TCD/platform-tools.jsp">Platform Tools</a></li>
                            <li><a href="http://software.topcoder.com/TCD/training.jsp">Training &amp; Mentoring</a></li>
                            <li><a href="http://software.topcoder.com/TCD/support.jsp">Support &amp; Maintenance</a></li>
                            <li class="last-li"><a href="http://software.topcoder.com/contact.jsp">Contact Us</a></li>
                        </ul>
                    </li>
                    <li><a href="http://www.topcoder.com/tc">Compete</a>
                        <ul>
                        	<li><a href="http://www.topcoder.com/tc?module=ActiveContests&pt=23">Conceptualization</a></li>
                    	    <li><a href="http://www.topcoder.com/tc?module=ActiveContests&pt=6">Specification</a></li>
                    	    <li><a href="http://www.topcoder.com/tc?module=ActiveContests&pt=7">Architecture</a></li>
                            <li><a href="http://www.topcoder.com/tc?module=ViewActiveContests&ph=112">Component Design</a></li>
                            <li><a href="http://www.topcoder.com/tc?module=ViewActiveContests&ph=113">Component Development</a></li> 
                            <li><a href="http://www.topcoder.com/tc?module=ViewAssemblyActiveContests">Assembly</a></li>
                            <li><a href="http://www.topcoder.com/longcontest/?module=ViewActiveContests">Marathon Matches</a></li>
                            <li><a href="http://www.topcoder.com/wiki/display/tc/Bug+Races">Bug Races</a></li>
                            <li class="last-li"><a href="http://studio.topcoder.com/?module=ViewActiveContests">Studio Competitions</a></li>
                        </ul>
                    </li>
                    <li><a href="http://studio.topcoder.com/">Studio</a>
                        <ul>
                            <li><a href="http://studio.topcoder.com/?module=ViewActiveContests">Active Contests</a></li>
                            <li><a href="http://www.topcoder.com/direct/">Launch a Contest</a></li>
                            <li><a href="http://studio.topcoder.com/forums">Studio Forums</a></li>
                            <li><a href="http://studio.topcoder.com/blog/">Studio Blog</a></li>
                            <li><a href="http://studio.topcoder.com/?module=MyStudioHome">My Studio</a></li>
                            <li class="last-li"><a href="http://studio.topcoder.com/?module=Static&amp;d1=contactUs">Contact Studio</a></li>    
                        </ul>
                    </li>
                    <li><a href="http://www.topcoder.comtc">Community</a>
                        <ul>
                            <li><a href="http://www.topcoder.com/reg/">Join TopCoder</a></li>
                            <li><a href="http://www.topcoder.com/tc?module=MyHome">My TopCoder</a></li>
                            <li><a href="http://forums.topcoder.com/">TopCoder Forums</a></li>
                            <li class="last-li"><a href="http://studio.topcoder.com/forums">Studio Forums</a></li>
                         </ul>
                    </li>
                </ul>
            </div><%-- #navigation ends --%>
            
            <%-- SUB NAVIGATION --%>
            <div id="nav_support">
                <h3 class="hide">SUB Navigation</h3>
                <ul>
                   	<li class="left"><a href="http://www.topcoder.com">Topcoder.com</a></li>
                    <li><a href="http://software.topcoder.com/about.jsp">About TopCoder</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=pressroom&amp;d2=index">News</a></li>
                    <li><a href="http://software.topcoder.com/contact.jsp">Contact Us</a></li>    
                    <!--<li class="right"><a class="gMetal" href="/tc?&amp;module=Login">Login</a></li>-->
					<li class="logged-in"><span class="welcome">Hello,&nbsp;</span><span class="direct-user"><%=request.getUserPrincipal().getName()%></span></li>
					<li><a href="<%=request.getContextPath()%>/Logout">logout</a></li> 
                </ul>
            </div><%-- #navigation ends --%>
        </div>
      <%-- .wrapper ends --%>
    </div><%-- #header ends --%>

<%-- CONTENT BLOCKS --%>



	<div id="flashcontent" style="margin-left: 20px; margin-top: 20px; margin-right: 20px; margin-bottom: 20px;"> 

<!--
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                id="main" width="100%" height="100%"
                codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
                <param name="movie" value="main.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <embed id="main2" src="main.swf" quality="high" flashvars="flashlet={}" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100%" height="100%"> </embed>
        </object>


	<div id="flashcontent">
		This content requires the Adobe Flash Player. <a href=http://www.adobe.com/go/getflash/>Get Flash</a>
	</div>
-->


    </div>

	<script type="text/javascript">
		// <![CDATA[

		var so = new SWFObject("/i/cockpit/Flex_Widget_Layout_Framework_App.swf?t=<%=t%>", "Flex_Widget_Layout_Framework_App", "100%", "100%", "10.0.0", "#dcdcdc");
		so.useExpressInstall('/i/cockpit/js/expressinstall.swf');
		so.addVariable("retrieveWidgetLayoutServiceUrl", "/directapp/servlet/RetrieveWidgetsLayoutService");
		so.addVariable("saveWidgetLayoutServiceUrl", "/directapp/servlet/SaveWidgetLayoutService");
		so.addVariable("widgetUrl", "/directapp/servlet/RetrieveWidgets");
		so.addVariable("username", "<%=request.getUserPrincipal().getName()%>");
        so.addVariable("userid", "<%=principal.getUserId()%>");
		so.addVariable("blazedsendpoint", "<%=blazedsendpoint%>");
		so.addVariable("projectServiceFacadeWsdl", "<%=projectwsdl%>");
		so.addVariable("contestServiceFacadeWsdl", "<%=contestwsdl%>");
		so.addVariable("hostAddress", "<%=hostAddress%>");
		so.addVariable("studioAddress", "<%=studioAddress%>");
        so.addVariable("portNumber", "<%=portNumber%>");
		so.addParam("wmode","transparent");
		so.write("flashcontent");

		// ]]>
	</script>
	
	<script type="text/javascript">
    <!--
        // Moved to global scope for BUGR-1703
        var flashObject = document.getElementById('Flex_Widget_Layout_Framework_App');
        var flashContainer = document.getElementById('flashcontent');
        
        // Javascript code to resize the "main" Flash object
        function resizeMain(newHeight, newWidth) {	
        	flashObject.height = newHeight;
        	
        	// BUGR-1216 fix
        	flashContainer.height = parseInt(newHeight) + 20;
        	
        	// BUGR-1267 fix
        	flashObject.width = newWidth;
        	flashContainer.width = parseInt(newWidth);
        	
        	/*
        	// Note: This only works with IE/Firefox, other browsers not tested
        
            if (navigator.userAgent.indexOf('MSIE') >= 0) {
            	// Internet Explorer, access the object tag
                document.getElementById('main').height = newHeight;
            } else {
                // object not found, try the embed instead
                document.getElementById('main2').height = newHeight;
            }
        	*/
        }
    
        function getVisualWindowX() {
            return document.body.clientWidth/2 + document.body.scrollLeft;
        }
        
        function getVisualWindowY() {
            return document.body.clientHeight/2 + document.body.scrollTop;
        }
        
        // BUGR-1702
        var counter = 0;
        function updateCounter() {
            if (counter <= 3) {
                counter += 1;
            }
            
            return counter;
        }
        
        function resetCounter() {
            counter = 0;
            //alert("Counter reset.");
        }
        
        function reloadApp() {
            // Rewrite the flash content in order to reload
            so.write("flashcontent");      
        }
        
        /**
         * BUGR-1703
         */
        
        // Minimum width of framework application without scrollbars/clipped content
        var minFrameworkAppWidth = 1000;
        
        // Called when the page loads
        function init() {
            window.onresize = handleBrowserResize;
    	}
    	
    	// Called when the browser is resized (or maximized)
    	function handleBrowserResize() {
            // Get the width of the browser
            var curWidth = 0;
    		
            if (typeof(window.innerWidth) == 'number') {
                // Not IE
                curWidth = window.innerWidth;
            } else {
                if (document.documentElement && document.documentElement.clientWidth) {
                    // IE6+ (standards-compliant mode)
                    curWidth = document.documentElement.clientWidth;
                } else if (document.body && document.body.clientWidth && curWidth == 0) {
                    // Other IE mode, if the first one isn't available
                    curWidth = document.body.clientWidth;
                }
            }
            
            // Note, we only need to handle changing the width when resized.
            
            // We have left and right margins of 20px each, so we need to factor that in
            var newAppWidth = curWidth - 40;
            var newWidth = Math.max(newAppWidth, minFrameworkAppWidth);
            flashObject.width = newWidth;
    	    flashContainer.width = newWidth; 
    	}
    -->
    </script>




  <%-- LINKS BLOCK --%>
    <div id="links">
        <div class="wrapper">
            <div class="col">
                <h4>Customer Service</h4>
                <ul>
                    <li><a href="http://software.topcoder.com/contact.jsp">Contact Support</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=about&amp;d2=privacy">Privacy Policy</a></li>
                    <li><a href="http://www.topcoder.com/tc?module=Static&d1=about&d2=terms">Terms &amp; Conditions</a></li>
                </ul>
            </div>
            
            <div class="col">
                <h4>About TopCoder</h4>
                <ul>
                    <li><a href="http://software.topcoder.com/contact.jsp">Contact Us</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=pressroom&amp;d2=mediaRequestForm">Public Relations</a></li>
                    <li><a href="http://software.topcoder.com/TCD/rss.jsp">RSS Feeds</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=pressroom&amp;d2=index">Press Room</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=about&amp;d2=jobs">Working at TopCoder</a></li>
                    <li><a href="http://www.topcoder.comtc?module=Static&amp;d1=about&amp;d2=terms">Legal Information</a></li>
                </ul>

            </div>
            
            <div class="col">
                <h4>Platform Tools</h4>
                <ul><li><a href="http://www.topcoder.com/wiki/display/tc/Upcoming+Contests">Pipeline</a></li>
                    <li><a href="http://www.topcoder.com/wiki/display/tc/TopCoder+UML+Tool">UML Tool</a></li>
                    <li><a href="http://forums.topcoder.com/?module=Category&amp;categoryID=22">TopCoder Forums</a></li>
                    <li><a href="http://software.topcoder.com/catalog/index.jsp">Component Catalog</a></li>
                    <li><a href="http://www.topcoder.com/wiki/">TopCoder Wiki</a></li>
                </ul>
          </div>
            
            <div class="col">
                <h4>TopCoder Community</h4>
                <ul>
                    <li><a href="http://www.topcoder.com/tc">TopCoder Community Home</a></li>
                    <li><a href="http://forums.topcoder.com/">TopCoder Forums</a></li>
                    <li><a href="http://studio.topcoder.com/forums">Studio Forums</a></li>
                </ul>
            
                <h4>TopCoder Blogs</h4>
                <ul>
                    <li><a href="http://www.topcoder.com/direct/blogs/">TopCoder Direct</a></li>
                    <li><a href="http://studio.topcoder.com/blog/">Studio TopCoder</a></li>
                </ul>
            </div>
            
            <div class="col">
                <h4>My Account</h4>
                <ul>
                    <li><a href="http://www.topcoder.com/reg/">TopCoder Registration</a></li>
                    <li><a href="http://www.topcoder.com/tc?module=MyHome">Manage Profile</a></li>
                    <li><a href="http://www.topcoder.com/dr">TopCoder Digital Run</a></li>
                    <li><a href="http://studio.topcoder.com/?module=Static&amp;d1=digitalrun&amp;d2=2008v2&amp;d3=home">Studio Cup</a></li>
                </ul>
            </div>
            
            <div class="col">
                <h4>Powered by TopCoder</h4>
                <ul>
                    <li><a href="http://software.topcoder.com/">TopCoder Direct</a></li>
                    <li><a href="http://www.topcoder.com">Topcoder.com</a></li>
                    <li><a href="http://studio.topcoder.com/">Studio TopCoder</a></li>
                </ul>
            </div>

        </div><%-- .wrapper ends --%>
    </div><%-- #links block ends --%>




<%-- FOOTER BLOCK --%>
    <div id="footer">
        <div class="wrapper">
            <p id="footer_1800"><strong>1-866-TOPCODER or Service@topcoder.com</strong></p>
            <p>TopCoder is the world's largest competitive software development community with <tc-webtag:format object="${sessionInfo.memberCount}" format="#,##0"/> developers representing over 200 countries.</p>
            <p>Copyright &copy; 2001-2009, TopCoder, Inc. All rights reserved.</p>
        </div><%-- .wrapper ends --%>
		<!--<button type="button" onclick="reloadApp()">Click Me!</button>-->
    </div><%-- #footer ends --%>  
</body>
</html>
