<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="javax.security.auth.Subject" %>
<%@ page import="javax.security.auth.login.LoginContext" %>
<%@ page import="javax.security.auth.callback.CallbackHandler" %>
<%@ page import="javax.security.auth.callback.Callback" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.security.auth.callback.UnsupportedCallbackException" %>
<%@ page import="javax.security.auth.callback.TextOutputCallback" %>
<%@ page import="javax.security.auth.callback.NameCallback" %>
<%@ page import="javax.security.auth.callback.PasswordCallback" %>
<%@ page import="com.topcoder.widgets.bridge.StudioServiceEJBLocator" %>
<%@ page import="com.topcoder.service.studio.StudioService" %>
<%@ page import="com.topcoder.service.studio.ContestPaymentData" %>
<%@ page import="javax.security.auth.login.LoginException" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.topcoder.service.studio.ContestData" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="javax.xml.datatype.DatatypeFactory" %>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="java.util.StringTokenizer" %>
<%
    ServletContext servletContext = pageContext.getServletContext();
    servletContext.log("paypalSilentPost.jsp : Got following Silent Post request from PayPal : ["
                       + request.getQueryString() + "]");
    int c;
    StringBuilder body = new StringBuilder();
    byte[] b = new byte[4096];
    ServletInputStream inputStream = request.getInputStream();
    while ((c = inputStream.read(b)) >= 0) {
        if (c > 0) {
            body.append(new String(b, 0, c));
        }
    }
    servletContext.log("paypalSilentPost.jsp : Body of Silent Post request from PayPal : [" + body + "]");

    Map<String, String> paramValues = new HashMap<String, String>();
    String[] params = body.toString().split("&");
    for (int i = 0; i < params.length; i++) {
        String param = params[i];
        String[] paramData = param.split("=");
        if (paramData.length > 1) {
            paramValues.put(paramData[0], URLDecoder.decode(paramData[1], "UTF-8"));
        }
    }

    boolean successful = false;
    // TODO : It would be nice to verify that the request originated from PayPal
    final String paypalOrderId = paramValues.get("PNREF");
    final String originalSessionId = paramValues.get("USER1");
    final String originalPrincipalName = paramValues.get("USER2");
    final String originalPaymentType = paramValues.get("USER3");
    final String originalContestId = paramValues.get("USER4");
    final String originalPaymentAmount = paramValues.get("USER5");
    final String originalSubmissionIds = paramValues.get("USER7");
    final String originalSubmissionPlacements = paramValues.get("USER8");

    // Get the parameters for establishing a security context
    final String clientLoginDomainName = servletContext.getInitParameter("clientLoginDomainName");
    final String ejbContextUsername = servletContext.getInitParameter("ejbContextUsername");
    final String ejbContextPassword = servletContext.getInitParameter("ejbContextPassword");

    LoginContext loginContext = null;
    try {
        Subject subject = new Subject();
        loginContext = new LoginContext(clientLoginDomainName, subject,
                                        new CallbackHandler() {
                                            public void handle(Callback[] callbacks)
                                                    throws IOException, UnsupportedCallbackException {
                                                for (int i = 0; i < callbacks.length; i++) {
                                                    if (callbacks[i] instanceof TextOutputCallback) {
                                                    } else if (callbacks[i] instanceof NameCallback) {
                                                        // prompt the user for a username
                                                        NameCallback nc = (NameCallback) callbacks[i];
                                                        nc.setName(ejbContextUsername);
                                                    } else if (callbacks[i] instanceof PasswordCallback) {
                                                        // prompt the user for sensitive information
                                                        PasswordCallback pc = (PasswordCallback) callbacks[i];
                                                        pc.setPassword(ejbContextPassword.toCharArray());
                                                    } else {
                                                        throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
                                                    }
                                                }
                                            }
                                        });
        loginContext.login();

        StudioServiceEJBLocator locator = new StudioServiceEJBLocator();
        StudioService studioService = locator.getService();

        if ("Launch".equals(originalPaymentType)) {
            Long numericContestId = new Long(originalContestId);
            ContestPaymentData payment = new ContestPaymentData();
            payment.setContestId(numericContestId);
            payment.setPaypalOrderId(paypalOrderId);
            payment.setPrice(new Double(originalPaymentAmount));
            payment.setPaymentStatusId(1L);
            payment.setCreateDate(new Date());
            studioService.createContestPayment(payment, originalPrincipalName);
            studioService.updateContestStatus(numericContestId, 9); // Update status to Scheduled

            // Load the contest details and check if it has to be launched immediately
            ContestData contest = studioService.getContest(numericContestId);
            if (contest.isLaunchImmediately()) {
                GregorianCalendar cal = new GregorianCalendar();
                XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                contest.setLaunchDateAndTime(xmlCalendar);
                studioService.updateContest(contest);
            }

            servletContext.log("paypalSilentPost.jsp : Created contest payment of $"
                               + originalPaymentAmount + " for contest " + originalContestId
                               + " on behalf of " + originalPrincipalName
                               + " as confirmed by PayPal order ID " + paypalOrderId);
            successful = true;
        } else if ("Submission".equals(originalPaymentType) || ("PurchasedSubmissions".equals(originalPaymentType))) {
            if (originalSubmissionIds != null) {
                String[] ids = originalSubmissionIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i].trim();
                    studioService.purchaseSubmission(Long.parseLong(id), paypalOrderId, originalPrincipalName);
                    servletContext.log("paypalSilentPost.jsp : Purchased submission "
                                       + id + " on behalf of " + originalPrincipalName
                                       + " as confirmed by PayPal order ID " + paypalOrderId);
                }
                if ("Submission".equals(originalPaymentType)) {
                    StringTokenizer tokenizer = new StringTokenizer(originalSubmissionPlacements, ",");
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        String[] placement = token.split("=");
                        if (placement.length == 2) {
                            studioService.setSubmissionPlacement(Long.parseLong(placement[0]),
                                                                 Integer.parseInt(placement[1]));
                            servletContext.log("paypalSilentPost.jsp : Recorded submission placement [" + token + "]");
                        } else {
                            servletContext.log("paypalSilentPost.jsp : skipped to record following submission placement "
                                               + "[" + token + "]");
                        }
                    }
                }
                successful = true;
            }
        }

    } catch (Throwable e) {
        servletContext.log("paypalSilentPost.jsp : An error [" + e + "] encountered while processing Silent Post "
                           + "request for contest = " + originalContestId + ", payment type = "
                           + originalPaymentType + ", user = " + originalPrincipalName + ", amount = "
                           + originalPaymentAmount + ", PayPal order ID = " + paypalOrderId);
        e.printStackTrace();
        successful = false;
    } finally {
        if (loginContext != null) {
            try {
                loginContext.logout();
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
    }

    if (successful) {
        servletContext.log("paypalSilentPost.jsp : Paypal Silent Post request has been processed successfully ; " +
                           "Responding with status code " + HttpServletResponse.SC_OK);
        response.setStatus(HttpServletResponse.SC_OK);
    } else {
        servletContext.log("paypalSilentPost.jsp : failed to process Paypal Silent Post request successfully : " +
                           "Responding with status code " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
%>
