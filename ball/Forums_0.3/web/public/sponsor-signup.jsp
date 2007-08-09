<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: Sign Up</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sponsorregistration.gif" alt="Sponsor Registration"/></h1>

        <div id="breadcrumb">&raquo;
            <span class="active">1. Review Terms of Service</span> &nbsp;
            <span class="next"> &raquo; 2. Enter Account Information &nbsp; &raquo; 3. Submit Your Website &nbsp; &raquo;
            4. Confirm Registration</span>
            </div>

        <p class="MsoNormal">To bid in our Sponsorship Auction, you must first register as a sponsor. Once you have
            completed your profile, we will review it in order to verify key details about the URL you are representing.
            We may contact you in the event of questions - to make this process as quick as possible, please be sure to
            include accurate and up-to-date contact information in your Sponsor Profile.<br/>

            Please follow the rules for the submission of domain names and images when signing up for a sponsor account:
            <ul>
                <li>Domain names should be submitted in the format:  www.topcoder.com  - i.e. no http:// and as a domain
                    name, not a specific page on your website </li>
                <li>Images provided should be roughly between 350-400 pixels by 250-300 pixels</li>
            </ul>

            <b>Note:</b> At this time sponsors are not authorized or able to make changes to their profile, including
            name, address, contact information updates or the addition of domains or images to their existing account.
            In the event of any modifications required to an existing sponsor account, please contact us via
            <a href="mailto:${orpheus:getContactEmailAddress()}">email</a>.
        </p>

        <p class="MsoNormal"><b>Sponsor Eligibility<br/>
        </b>If we determine that a sponsor-submitted URL contains harmful or
            inappropriate content, or that a sponsor has violated the Terms &amp;
            Conditions of Use, we reserve the right to revoke the sponsor's
            eligibility.</p>

        <p>Please read over the terms and conditions below and click the checkbox at the bottom of the page to continue.
            <textarea class="textbox">
                NOTE: This page content is temporary and not intended to be used for the final ball website

                USER AGREEMENT.
                In order to protect the interests of all of our readers, as well as those of our information providers,
                we ask that you abide by the following rules. By using GTWS (the "Service"), you hereby agree to be
                bound by all of the following terms and conditions ("Terms of Service" or "TOS").

                LIMITATIONS ON USE.
                All of the information, content, and services (collectively, the "Content") within this Service is
                protected by copyright and other intellectual property laws. The Content is owned by GTWS and its
                affiliated companies, licensors, and suppliers. The Content is intended for the personal use of users of
                the Service. While you may interact with and download a single copy of Content for your personal,
                non-commercial entertainment, information or use, you may not reproduce, sell, publish, distribute,
                modify, display, repost or otherwise use any portion of the Content in any other way or for any other
                purpose without the written consent of teh Generic Theater. Requests regarding use of the Content for
                any purpose other than personal, non-commercial use should be directed to Steven Harders at _________
                You acknowledge that the Content includes certain trademarks and service marks owned by the Generic
                Theater, as well as trademarks owned by other information providers. You agree not to copy, use or
                otherwise infringe upon these trademarks or service marks. You further agree that you will not alter or
                remove any trademark, copyright, or any other notice from any copies of the Content.
                If you operate a website and wish to link to the Service, please link to the Service's home page. The
                Service reserves the right to reject or terminate any links to the Content or Service.
                Photos, graphics, audio and/or video materials shall not directly or indirectly be published, rewritten
                for broadcast or publication, or redistributed in any medium. No portion thereof of the Service may be
                stored in a computer except for personal and non-commercial use. You do not hold GTWS liable for any
                delays, inaccuracies, errors, or omissions therefrom or in the transmission or delivery of all or any
                part of thereof or for any damage arising from the foregoing.
                The Service's forums, bulletin boards, and chat rooms are provided to give users an interesting and
                stimulating forum in which they can express their opinions and share their ideas. GTWS does not endorse
                the accuracy or reliability of any advice, opinion, statement, or information posted on these forums
                [message boards/ bulletin boards/chat rooms]. Please use your best judgment and be respectful of other
                individuals using these forums [etc.]. Do not use vulgar, abusive, or hateful language. You may not use
                this forum [etc.] for advertising or promotional materials or other forms of solicitation. Uploading
                copyrighted or other proprietary material of any kind on the Service without the express permission of
                the owner of that material is prohibited and may result in civil and/or criminal liability.
                Given the nature of the Service and the volume of messages and postings, GTWS cannot and does not
                monitor all of the material posted or transmitted by users and third party information providers. While
                you agree that the Service will not be liable for such third party content, GTWS reserves the right to
                delete, move, or edit any communications which deems in its sole discretion to be: harmful to
                individuals or communities; obscene; defamatory; in violation of GTWS's or any third party's rights; or
                otherwise unacceptable.
                User Content, Messages and Postings. By placing material on or communicating with GTWS, you hereby grant
                to the Service, its affiliates, and related entities (collectively, GTWS) the royalty-free, perpetual,
                irrevocable non-exclusive right and license to use, copy, modify, display, distribute, and reproduce all
                such materials in any form, media, software or technology of any kind now existing or developed in the
                future. You also agree to indemnify, defend, and hold the Service, its affiliates, and related entities
                harmless against any claims or costs, including attorneys fees, arising from the use or distribution of
                those materials. You further grant GTWS the right to use your name in connection with the reproduction
                or distribution of such material.
                GTWS, its affiliates, and related entities do not accept unsolicited materials or ideas for use or
                publication in their newspapers, broadcast programming, books, or their digital and electronic media,
                except in connection with the forums, chat rooms, and bulletin boards on the Service. The Service, its
                affiliates, and related entities shall not be responsible for the similarity of any of its content or
                programming in any media to materials or ideas transmitted to the Service.

                RESERVATIONS.
                By placing an order via the Service does not guarentee availabity or pricing. GTWS reserves the right to
                change performances, pricing, or any other details concerning the season at any time. Reservations are
                not complete until notice is recieved from the Generic Theater claiming as such. All reservations,
                placed through the Service or otherwise, become null and void once the requested performance has passed.
                GTWS is not responsible for loss of transmission, invalid information, or any lost information in the
                process of making a reservation through the Service.

                General Disclaimer and Limitation of Liability.
                YOU EXPRESSLY AGREE THAT USE OF THE SERVICE IS AT YOUR OWN RISK. NEITHER THE SERVICE, GTWS, NOR ANY OF
                THEIR AFFILIATES, EMPLOYEES, AGENTS, CONTENT PROVIDERS, OR LICENSORS MAKES ANY REPRESENTATIONS OR
                WARRANTIES OF ANY KIND REGARDING THE SERVICE, THE CONTENT, OR THE RESULTS THAT MAY BE OBTAINED FROM THE
                USE OF THE SERVICE. THE SERVICE IS PROVIDED ON AN "AS IS" BASIS AND THE SERVICE SPECIFICALLY DISCLAIMS
                ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING WITHOUT LIMITATION, WARRANTIES OF FITNESS FOR A PARTICULAR
                PURPOSE, WARRANTIES OF MERCHANTABILITY, OR WARRANTIES AGAINST INFRINGEMENT.
                THE SERVICE, THEIR AFFILIATES, EMPLOYEES, AGENTS, CONTENT PROVIDERS, AND LICENSORS SHALL IN NO EVENT BE
                LIABLE FOR ANY DAMAGES OR LOSSES, INCLUDING WITHOUT LIMITATION, DIRECT, INDIRECT, CONSEQUENTIAL,
                SPECIAL, INCIDENTAL, OR PUNITIVE DAMAGES, RESULTING FROM OR CAUSED BY THE SERVICE OR ITS CONTENT,
                INCLUDING, WITHOUT LIMITATION, LOSSES RELATED TO: YOUR USE OR INABILITY TO USE THE SERVICE; ANY ERRORS,
                OMISSIONS OR DEFECTS IN THE CONTENT; OR ANY INTERRUPTIONS, DELAYS IN TRANSMISSION, OR COMPUTER VIRUSES.
                IN ANY EVENT, ANY LIABILITY OF THE SERVICE, DCHR, THEIR AFFILIATES, EMPLOYEES, AGENTS, CONTENT
                PROVIDERS, AND LICENSORS SHALL NOT EXCEED THE AMOUNT PAID BY YOU TO THE SERVICE IN THE 12 MONTHS PRIOR
                TO THE CLAIMED INJURY OR DAMAGE.

                MISCELLANEOUS.
                The Service reserves the right at its discretion to change these Terms of Service and will notify users
                of any such changes by online postings. Your continued use of the Service after the posting of any
                amended Terms of Service shall constitute your agreement to be bound by any such changes. The Service
                may modify, suspend, discontinue, or restrict the use of any portion of the Service, including the
                availability of any portion of the Content at any time, without notice or liability.
                These Terms of Service will be governed by and construed in accordance with the laws of the State of
                Virginia, without regard to its conflicts of law provisions. You hereby agree that any cause of action
                you may have with respect to GTWS must be filed in the City of Norfolk in the State of Virginia within
                one year after the cause of action arises or the cause is barred.
                IF YOU DO NOT AGREE TO THE TERMS STATED ABOVE OR ANY CHANGES THE SERVICE MAKES IN THESE TERMS, PLEASE
                EXIT THE SERVICE IMMEDIATELY.
            </textarea>
        </p>

        <p>
            <form action="${ctx}/public/sponsorRegistration.jsp?phase=step1&nextPhase=step2"
                  name="SignupForm" id="SignupForm" method="POST">
                <c:if test="${not empty requestScope['agreeToTerms.error']}">
                    <span class="fBold cRed">${requestScope['agreeToTerms.error']}</span><br/>
                </c:if>
                <input type="checkbox" name="agreeToTerms"
                       onkeypress="return submitOnEnter(event, this.form);"/>
                &nbsp;I agree to and accept the terms and conditions defined above.<br/>
                <br/>
            </form>

        </p>
        <a href="#" title="Continue" onclick="submitForm(document.SignupForm);return false;">
            <img src="${ctx}/i/b/btn_continue.gif" width="57" height="15" class="btn" alt="Continue"/>
        </a>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>