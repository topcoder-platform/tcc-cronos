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
        <c:set var="ffPlugin" value="${orpheus:getFirefoxPluginInfo()}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="313" id="firefoxData">
            <tr>
                <th colspan="2">Auto-Detect:</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p>
                        <span style="float:left; margin-right: 10px;">
                            <img src="${ctx}/i/firefox-logo.jpg" alt="FireFox Plugin"/>
                        </span>
                        The following plug-in version was automatically detected for your browser and system. <a
                        href="${ctx}/public/download.jsp">Click here</a> to manually select a different
                        plug-in to download.
                    </p>
                </td>
            </tr>
            <tr>
                <td class="bold">File Size:</td>
                <td>${ffPlugin.fileSize}</td>
            </tr>
            <tr>
                <td class="bold">Date Posted:</td>
                <td>${ffPlugin.datePosted}</td>
            </tr>
            <tr>
                <td class="bold">Version:</td>
                <td>${ffPlugin.version}</td>
            </tr>
            <tr>
                <td class="bold">Language:</td>
                <td>${ffPlugin.language}</td>
            </tr>
            <tr>
                <td class="bold">Browser:</td>
                <td>${ffPlugin.browser}</td>
            </tr>
            <tr>
                <td colspan="2" class="buttons">
                    <a href="${ctx}/server/downloadPlugin.do?pluginId=FireFox" title="Download Now">
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
                        <li>You will be prompted to "Open With" or "Save to Disk." You can choose either option, but the
                            installation process is slightly different for the two choices.</li>
                        <li>If you choose "Open With", you will need to browse on your computer until you find and click
                            on the "Firefox.exe" file.  The Firefox.exe file is generally found under
                            ProgramFiles\Mozilla.</li>
                        <li>If you choose "Save to Disk," save the file to somewhere on your computer. Then select
                            "Open File" from the "File" menu in the browser, and locate the file you downloaded. Opening
                            the file will trigger the installation process.</li>
                        <li>You will need to have the Java Run Environment (JRE) 1.4 or higher on your computer in order
                            for the plugin to function properly. If you do not have the latest JRE, you can download it
                            from Sun's <a href="http://www.java.com/en/download/manual.jsp" target="_blank">website</a>.
                        </li>
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