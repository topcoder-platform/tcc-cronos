<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: Sign Up :: Add Domains</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script language="JavaScript" type="text/javascript">
        var currentDomain = 1;
        var currentImages = new Array();
        currentImages[0] = 1;
        currentImages[1] = 1;
        currentImages[2] = 1;
        currentImages[3] = 1;
        currentImages[4] = 1;
        currentImages[5] = 1;

        function addAnotherImage(domain) {
            if (currentImages[domain] < 5) {
                currentImages[domain]++;
                var td = document.getElementById('domain_images_cell_' + domain);
                var div = document.createElement('div');
                div.id = 'imageUpload_' + domain + "_" + currentImages[domain];
                div.style.display = 'block';
                div.appendChild(createImageTable(domain, currentImages[domain]));
                td.insertBefore(div, td.childNodes[td.childNodes.length - 1]);
            }
            if (currentImages[domain] >= 5) {
                document.getElementById("addLink_" + domain).style.display = "none";
            }
        }

        function addAnotherDomain() {
            if (currentDomain >= 5) {
                alert('Maximum 5 domains are allowed to be registered');
                return;
            }
            currentDomain++;
            currentImages[currentDomain] = 1;

            var table = document.getElementById('domainImagesTable');

            // Create 1st row
            var row = table.insertRow(table.rows.length - 1);
            var td = row.insertCell(0);
            var span = document.createElement('span');
            span.className = 'fBold';
            span.appendChild(document.createTextNode('URL ' + currentDomain + ':'));
            td.appendChild(span);
            var input = document.createElement('input');
            input.setAttribute('name', 'domain_' + currentDomain);
            input.setAttribute('type', 'text');
            input.className = 'inputBox';
            input.style.width = '250px';
            input.onkeypress = new Function('return submitOnEnter(event, this.form)');
            td.appendChild(input);

            // Create second row
            row = table.insertRow(table.rows.length - 1);
            td = row.insertCell(0);
            td.id = 'domain_images_cell_' + currentDomain;
            //   Create DIV for input
            var div1 = document.createElement('div');
            div1.id = 'imageUpload_' + currentDomain + '_1';
            div1.style.display = 'block';
            div1.appendChild(createImageTable(currentDomain, 1));
            td.appendChild(div1);

            //   Create DIV for add link
            var div2 = document.createElement('div');
            div2.id = 'addLink_' + currentDomain;
            div2.style.display = 'block';
            var center = document.createElement('center');
            var a = document.createElement('a');
            a.href = '#';
            a.onclick = new Function('addAnotherImage(' + currentDomain + ');return false;');
            a.appendChild(document.createTextNode('+ add another image'));
            center.appendChild(a);
            div2.appendChild(center);
            td.appendChild(div2);
        }

        function createImageTable(domain, imageIndex) {
            var newTable = document.createElement('table');
            newTable.className = 'imageUploadTable';

            // Insert 1st row
            var row1 = newTable.insertRow(0);
            var th = document.createElement('th');
            th.rowSpan = '2';
            th.style.textAlign = 'center';
            th.appendChild(document.createTextNode('Image'));
            th.appendChild(document.createElement('br'));
            var font = document.createElement('font');
            font.setAttribute('size', '5');
            font.appendChild(document.createTextNode('' + imageIndex));
            th.appendChild(font);
            row1.appendChild(th)

            var td1 = row1.insertCell(1);
            td1.className = 'formLabel';
            td1.appendChild(document.createTextNode('File:'));
            td1 = row1.insertCell(2);
            var input = document.createElement('input');
            input.setAttribute('name', 'imageUpload_' + domain + '_' + imageIndex);
            input.setAttribute('type', 'file');
            input.setAttribute('size', '15');
            input.className = 'inputBox';
            input.style.height = '18px';
            input.onkeypress = new Function('return submitOnEnter(event, this.form)');
            td1.appendChild(input);

            // Insert 2nd row
            var row2 = newTable.insertRow(1);
            var td2 = row2.insertCell(0);
            td2.className = 'formLabel';
            td2.appendChild(document.createTextNode('Name:'));
            td2 = row2.insertCell(1);
            input = document.createElement('input');
            input.setAttribute('name', 'imageName_' + domain + '_' + imageIndex);
            input.setAttribute('type', 'text');
            input.setAttribute('size', '25');
            input.className = 'inputBox';
            input.onkeypress = new Function('return submitOnEnter(event, this.form)');
            td2.appendChild(input);

            return newTable;
        }

        function submitSponsorForm() {
            var form = document.SponsorDomainsForm;
            var a = '${ctx}/server/sponsor/preSubmitDomains.do?';
            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].name.substring(0, 7) == 'domain_') {
                    a += form.elements[i].name + '=' + form.elements[i].value + '&';
                } else if (form.elements[i].name.substring(0, 10) == 'imageName_') {
                    a += form.elements[i].name + '=' + form.elements[i].value + '&';
                }
            }
            form.action = a;
            submitForm(form);
        }
    </script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sponsorregistration.gif" alt="Sponsor Registration"/></h1>

        <div id="breadcrumb">&raquo; 1. Review Terms of Service &nbsp; &raquo; 2. Enter Account Information &nbsp;
            <span class="active"> &raquo; 3. Submit Your Website &nbsp;</span>
            <span class="next"> &raquo; 4. Confirm Registration</span>
        </div>


        <span class="fBold">Step 3: Enter in the Domain of the website that you would like to host the ball.<br/></span>
        For each website, you will need to upload at least one (1) image that may be used as a puzzle for players that
        unlock the ball. The following rules apply for the submission of domain names and images:
        <ul>
            <li>Domain names should be submitted in the format: www.topcoder.com (i.e. no http:// and not as an explicit
                or specific page on your website, but as the main domain name)</li>
            <li>Images provided should be roughly between 350-400 pixels by 250-300 pixels</li>
        </ul>
        <b>Note:</b> Domain names and images submitted with be vetted for content, size and other suitability criteria
        and may be rejected at the discretion of the game administrator. Please contact us if you have any questions or
        are unsure about sponsor submission and sign-up criteria.
        <br/><br/>
        <b>Disclaimer:</b> We are not responsible for the accuracy of the information provided to us when signing up for
        a sponsor account. Your display information to other players or other sponsors will be exactly as created here,
        unless otherwise explicitly specified by a game administrator as an authorized change to sponsor profile data.
        <br/><br/>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">&nbsp;Host Website Information</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/sponsor/preSubmitDomains.do" name="SponsorDomainsForm"
                              id="SponsorDomainsForm"
                              method="POST" enctype="multipart/form-data">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" id="domainImagesTable">
                                <tr>
                                    <td class="tdTop"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="fBold">URL 1:</span>
                                        <input name="domain_1" type="text" id="url" class="inputBox"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:250px;" value=""/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="domain_images_cell_1">
                                        <div id="imageUpload_1_1" style="display:block;">
                                            <table class="imageUploadTable">
                                                <tr>
                                                    <th rowspan="2" style="text-align:center;">Image<br/><font size="5">
                                                        1</font></th>
                                                    <td class="formlabel">File:</td>
                                                    <td>
                                                        <input name="imageUpload_1_1" type="file" id="Image1"
                                                            onkeypress="return submitOnEnter(event, this.form);"
                                                               class="inputBox" style="height:18px"
                                                               size="15"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="formlabel">Name:</td>
                                                    <td>
                                                        <input name="imageName_1_1" type="text" id="imagename1"
                                                            onkeypress="return submitOnEnter(event, this.form);"
                                                               size="25" class="inputBox" value=""/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>

                                        <div id="addLink_1" style="display:block;">
                                            <center>
                                                <a href="#" onClick="addAnotherImage(1);return false;">+ add another
                                                    image</a>
                                            </center>
                                            <br/>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center">
                                        <img src="${ctx}/i/b/btn_saveandaddurl.gif" width="148" height="15" class="btn"
                                             alt="Save and Add URL"
                                             onclick="addAnotherDomain();return false;"/>
                                        <img src="${ctx}/i/b/btn_saveandfinish.gif" width="94" height="15" class="btn"
                                             alt="Save and Finish"
                                             onclick="submitSponsorForm();return false;"/>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
        <br/>

    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>