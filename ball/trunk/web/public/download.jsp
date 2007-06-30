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
    <script language="JavaScript" type="text/javascript">
          function selectPlugin(list) {
              if (list.selectedIndex > 0) {
                  if (list.selectedIndex < 4) {
                      window.location = '${ctx}/public/download-ie.jsp';
                  } else {
                      window.location = '${ctx}/public/download-firefox.jsp';
                  }
              }
          }
    </script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="download" scope="page"/>
<%@ include file="../includes/header.jsp" %>
<div id="wrap">
<h1><img src="${ctx}/i/h/title_downloadplugin.gif" width="182" height="16" alt="Download Plugin"/></h1>

<div style="display:block" id="autodetectMessage">
    <div id="table-details">
        <table border="0" cellpadding="0" cellspacing="0" width="313" id="table1">
            <tr>
                <th>Manual Select:</th>
            </tr>
            <tr>
                <td class="tdTop"><br/>
                    Select a new plug-in from the pulldown menu.<br/>
                    <br/>
                    <select name="pluginSelect" onChange="selectPlugin(this);return false;"
                            class="dropDown">
                        <option value="Select">Select</option>
                        <option value="ie4">Internet Explorer 6.0+</option>
                        <option value="ie5">Internet Explorer 5.x</option>
                        <option value="ie4">Internet Explorer 4.7 and below</option>
                        <option value="firefox5">Firefox 5.0 +</option>
                        <option value="firefox4">Firefox 4.x</option>
                    </select> <br/>
                    &nbsp;</td>
            </tr>
        </table>
    </div>
</div>

<div id="tabcontentcontainer">
    <!-- INSTALLAION INSTRUCTIONS TAB -->
<%--
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
                        <li>Click the "Download Now" button to the left. If your browser prompts you to open the file or
                            save to disk, select "save to disk" and save the file to your computer. </li>
                        <li>Once the file is downloaded, you will need to run the file to install the program. If you are
                            not prompted automatically to "Run" or "Install" program, choose "Open File" from under the
                            File menu in your browser.</li>
                        <li>Opening the file should result in a "Run" or "Install" prompt. Follow instructions to
                            complete the installation.</li>
                        <li>Once you've successfully installed the plug-in, you will need to restart your browser for
                            the toolbar to appear.</li>
                        <li>Once installed, you can log in to your account and register for an active game. If you do
                            not already have a player account, you will need to sign-up for one before you can play a
                            game.</li>
                        <li>If you have problems, visit our <a href="${ctx}/public/faq-download.jsp">FAQ Section</a> for
                            troubleshooting.</li>
                    </ol>
                </td>
            </tr>
        </table>
    </div>
--%>
</div>
</div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>