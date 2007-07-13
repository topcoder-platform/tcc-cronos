<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Download Plugin :: Installation FAQ</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="download" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_installationfaq.gif" alt="Installation FAQ"/></h1>
        <ol>
            <li><a href="#1">How do I install The Ball for Internet Explorer?</a></li>
            <li><a href="#2">How do I install The Ball for Mozilla Firefox?</a></li>
            <li><a href="#3">How do I uninstall The Ball in Internet Explorer?</a></li>
            <li><a href="#4">How do I uninstall The Ball in Firefox?</a></li>
            <li><a href="#5">I installed The Ball in Internet Explorer -- why won't it show up in Firefox (or vice versa)?</a></li>
        </ol>
        <hr class="grayline"/>
        <ol>
            <li><a name="1"></a><span class="faq">How do I install The Ball for Internet Explorer?</span><br/>
                To install The Ball for Internet Explorer, you must have Microsoft Windows 2000/XP/2003 and
                Microsoft Internet Explorer 5.5 and above. To install The Ball in Internet Explorer, follow these
                steps:
                <br/>
                <br/>
                1. Open a Microsoft Internet Explorer window.
                <br/>
                2. Visit www.theball.com, go to the "Download" page.  Our game will automatically detect your browser
                type recommend a plugin for you to download.  Click on the orange "Download Now" open to accept the
                default recommendation.
                <br/>
                3. When you see the next screen, choose "Open" or "Run."
                <br/>
                4. Review the Terms of Use, and choose "I Accept"
                <br/>
                5. Continue through the rest of the installation. When the installation is complete, you will need to
                re-start your browser before the Ball's Toolbar is incorporated at the top of your Internet Explorer
                screen. 
                <br/>&nbsp;</li>

            <li><a name="2"></a><span class="faq">How do I install The Ball for Mozilla Firefox?</span><br/>
                To install The Ball for Firefox, follow these steps:
                <br/>
                <br/>
                1. Open a Mozilla Firefox window.
                <br/>
                2. Visit www.theball.com, go to the "Download" page. Our game will automatically detect your browser
                type recommend a plugin for you to download. Click on the orange "Download Now" open to accept the
                default recommendation.
                <br/>
                3. You will be asked what you would like to do with the plugin file. You have a choice, you can either:
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;a. Choose the "Open with" option and browse through your Program Files to find
                the "firefox.exe" file, or
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;b. Choose to "Save to Disk" and save the file on your hard drive. After saving
                the file to your hard drive, go to "File" in the Firefox menu, and choose "Open File". Browse to where
                you saved the file in the previous step and open that file.
                <br/>
                4. Review the Terms of Use and click "Install."
                <br/>
                5. Continue through the rest of the installation.
                <br/>
                6. When installation is complete, close all your Firefox browsers and windows. When you restart it, The
                Ball should appear at the top of your Firefox screen.
                <br/>&nbsp;</li>

            <li><a name="3"></a><span class="faq"> How do I uninstall The Ball in Internet Explorer?</span><br/>
                1. Internet Explorer treats The Ball's plugin like any other installed program. To uninstall the plugin
                from Internet Explorer, go to the "Add and Remove" Programs under "Control Panel". Please close all
                opened instances of Internet Explorer while uninstalling the plugin.
                <br/>
                2. Scroll down the list of installed programs until you get to "The Ball" plugin. Click on details
                (optional) to see information about the plugin or click on "Remove" to uninstall the plugin.
                <br/>
                3. Confirm the uninstall procedure. The plugin will be removed.
                <br/>&nbsp;</li>

            <li><a name="4"></a>
                <span class="faq">How do I uninstall The Ball in Firefox?</span>
                <br/>
                1. Firefox treats The Ball's plugin like any other installed Add-On. To uninstall the plugin from
                Firefox, choose "Manage Add-Ons" from the Tools Menu, find the Ball plugin from the list of available
                Add-Ons, and click on "Uninstall" 
                <br/>
                2. You will need to restart Firefox to confirm the uninstall process.
                <br/>&nbsp;</li>

            <li><a name="5"></a>
                <span class="faq">I installed The Ball in Internet Explorer -- why won't it show up in Firefox (or vice versa)?</span>
                <br/>
                The Ball is available for both Microsoft Internet Explorer and Mozilla Firefox. However, you must
                install it separately for each browser. You may use either browser to play a game, but note that we do
                NOT recommend or support logging into both plugins with the same user id and password at the same time.
                <br/>&nbsp;</li>

        </ol>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>