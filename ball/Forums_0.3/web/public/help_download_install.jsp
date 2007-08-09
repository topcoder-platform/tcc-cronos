<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Help</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/download.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/help/img_download.gif" alt="How to Play" width="239" height="18"/></p>

        <p class="cOrange"><strong>Download the Plugin:</strong></p>
        <ol>
            <li> The Ball&rsquo;s plugin comes in two different flavours: Internet Explorer and Mozilla Firefox.</li>
            <li>
                To download the plugin go to:
                <a href="#" onclick="showDownload('${ctx}');return false;">http://www.theball.com/public/download.jsp</a>
            </li>
            <li>We will automatically detect your browser type and present you with the appropriate browser type as
                shown in the screen-shots below:
            </li>
        </ol>
        <p><img style="padding: 5px;" src="${ctx}/i/help/download_01.jpg" width="350" height="270" alt=""/><img
            style="padding: 5px;" src="${ctx}/i/help/download_02.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Install the Plugin in Internet Explorer:</strong></p>
        <ol>
            <li>Click on the &ldquo;Download Now&rdquo; button.&nbsp; You will be prompted to &ldquo;Run&rdquo;
                or &ldquo;Save&rdquo; the file.&nbsp; You may do either but &ldquo;Run&rdquo; is the simpler method to
                perform the installation.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/download_03.jpg" width="350" height="270" alt=""/></p>

        <p>&nbsp;</p>
        <ol start="2">
            <li>If you choose &ldquo;Run&rdquo; the installation process will automatically run the program to install
                the plugin on your browser.
            </li>
            <li>If you choose &ldquo;Save&rdquo; you will need to save the file to somewhere on your desktop.&nbsp; You
                will then need to &ldquo;Open File&rdquo; from the Main Menu in the browser and browse to the place on
                your hard drive where you stored the saved file.&nbsp; Opening the file will trigger the installation
                process.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/download_04.jpg" width="350" height="270" alt=""/></p>

        <p align="left">

        </p>
        <ol start="4">
            <li>Note: You will need to have the Microsoft .NET framework installed on your computer for the plugin to
                install properly.&nbsp; In most cases your computer will already have this software installed, however
                if you do not have .NET framework installed on your machine, the installer will prompt you to install it
                first.&nbsp; You may download a free copy of the .NET Framework from Microsoft&rsquo;s website at: <a
                href="http://www.microsoft.com/downloads/details.aspx?displaylang=en&amp;FamilyID=262D25E3-F589-4842-8157-034D1E7CF3A3"
                target="_blank">http://www.microsoft.com/downloads/details.aspx?displaylang=en&amp;FamilyID=262D25E3-F589-4842-8157-034D1E7CF3A3</a>
            </li>
            <li>Once you have successfully installed the plugin, you can sign-up for an account and register to play one
                of our Active Games.&nbsp; If you already have a player account, you can simply log into the plugin,
                register for a game and begin playing.
            </li>
            <li>If you have any difficulties, you can visit our <a href="faq-download.jsp">FAQ Section</a> for
                trouble-shooting tips or assistance.
            </li>
        </ol>
        <p class="cOrange"><strong>Install the Plugin in Firefox:</strong></p>
        <ol>
            <li>Click on the &ldquo;Download Now&rdquo; button.&nbsp; You will be prompted to &ldquo;Open With&rdquo;
                or &ldquo;Save to Disk&rdquo;.&nbsp; You may choose either to continue with the installation, but the
                installation process is slightly different for the two choices.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/download_06.jpg" width="350" height="270" alt=""/></p>

        <p>

        </p>
        <ol start="2">
            <li>
                <p>If you choose &ldquo;Open With&rdquo;, you will need to browse on your desktop until you find and
                    choose the &ldquo;Firefox.exe&rdquo; file on your machine.</p>
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/download_05.jpg" width="350" height="270" alt=""/></p>

        <p align="left">

        </p>
        <ol start="3">
            <li> If you choose &ldquo;Save to Disk&rdquo; you will need to save the file to somewhere on your desktop.&nbsp;
                You will then need to &ldquo;Open File&rdquo; from the Main Menu in the browser and browse to the place
                on your hard drive where you stored the saved file.&nbsp; Opening the file will trigger the installation
                process.
            </li>
        </ol>
        <p align="left"><img style="padding: 5px;" src="${ctx}/i/help/download_06.jpg" width="350" height="270" alt=""/><img
            style="padding: 5px;" src="${ctx}/i/help/download_07.jpg" width="350" height="270" alt=""/></p>

        <p align="left">

        </p>
        <ol start="4">
            <li>Note: You will need to have the Java Run Environment (JRE) 1.4 of higher in order for the plugin to
                function properly.
            </li>
            <li>Once you have successfully installed the plugin, you can sign-up for an account and register to play one
                of our Active Games.&nbsp; If you already have a player account, you can simply log into the plugin,
                register for a game and begin playing.
            </li>
            <li>If you have any difficulties, you can visit our <a href="faq-download.jsp">FAQ Section</a> for
                trouble-shooting tips or assistance.
            </li>
        </ol>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>