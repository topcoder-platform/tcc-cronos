<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Download Plugin</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="download" scope="page"/>
<%@ include file="../includes/header.jsp" %>
<div id="wrap">
<h1><img src="${ctx}/i/h/title_downloadplugin.gif" width="182" height="16" alt="Download Plugin"/></h1>

<div style="display:block" id="autodetectMessage">
    <div id="table-details">
        <c:set var="iePlugin" value="${orpheus:getInternetExplorerPluginInfo()}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="313" id="ieData">
            <tr>
                <th colspan="2">Auto-Detect:</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p>
                       <span style="float:left; margin-right: 10px;">
                           <img src="${ctx}/i/ie-logo.jpg" alt="Internet Explorer Plugin"/>
                        </span>
                        The following plug-in version was automatically detected for your browser and system. <a
                        href="${ctx}/public/download.jsp">Click here</a> to manually select a different
                        plug-in to download.
                    </p>
                </td>
            </tr>
            <tr>
                <td class="bold">File Size:</td>
                <td>${iePlugin.fileSize}</td>
            </tr>
            <tr>
                <td class="bold">Date Posted:</td>
                <td>${iePlugin.datePosted}</td>
            </tr>
            <tr>
                <td class="bold">Version:</td>
                <td>${iePlugin.version}</td>
            </tr>
            <tr>
                <td class="bold">Language:</td>
                <td>${iePlugin.language}</td>
            </tr>
            <tr>
                <td class="bold">Browser:</td>
                <td>${iePlugin.browser}</td>
            </tr>
            <tr>
                <td colspan="2" class="buttons">
                    <a href="${ctx}/server/downloadPlugin.do?pluginId=IE" title="Download Now">
                    <img src="${ctx}/i/b/btn_downloadnow.gif" width="94" height="15" alt="Download Now"/></a>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="tabcontentcontainer">
    <!-- INSTALLAION INSTRUCTIONS TAB -->
    <ul id="tablist">
        <li id="current">
            <a href="#table-tabs">Installation Instructions</a>
        </li>
    </ul>

    <div style="clear:both;"></div>

    <div id="table-tabs">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                    <ol>
                        <li>Click the "Download Now" button.</li>
                        <li>Click "Run" if you wish to run the installation process automatically. Alternately you can
                            click "Save" and save the file on your machine. Once the file is saved, you will need to find
                            the file and double-click on it to run the installation process.</li>
                        <li>Follow the on-screen instructions to run the installation process. If your computer does not
                            have a Microsoft's .NET framework installed on it, you will be prompted to download a copy,
                            before installing the plugin. You may download a free copy of the .NET Framework from
                            Microsoft's <a href="http://www.microsoft.com/downloads/details.aspx?displaylang=en&FamilyID=262D25E3-F589-4842-8157-034D1E7CF3A3" target="_blank">website</a>.</li>
                        <li>You must click on the "I agree" button after reading through our agreement and terms of use.
                            If you do not agree to the conditions, the plugin will not be installed on your machine.
                        </li>
                        <li>When the installation process is complete, you will need to close down all instances of your
                            browser windows, and restart your browser for the toolbar to appear.</li>
                        <li>Once the Ball's toolbar is successfully installed, you will see a "Login" icon in your
                            browser. If you already have a player account, you can log into the plugin and begin playing
                            in an active game. If you do not have a player account, you will need to sign up for one
                            before you can play in a game.</li>
                        <li>If you have problems, visit our <a href="${ctx}/public/faq-download.jsp">FAQ Section</a> for
                            troubleshooting.</li>
                    </ol>
                </td>
            </tr>
        </table>
    </div>
</div>
</div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>