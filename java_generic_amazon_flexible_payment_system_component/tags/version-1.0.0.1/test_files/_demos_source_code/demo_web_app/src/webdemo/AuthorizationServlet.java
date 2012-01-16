package webdemo;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonPaymentManager;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;

/**
 * Servlet implementation class AuthorizationServlet
 */
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // parse request parameters
        String authTypeParam = request.getParameter("authType");
        String amountStr = request.getParameter("amount1");
        String thresholdStr = request.getParameter("threshold");

        boolean multiplePayments = authTypeParam.equals("1");
        boolean reservation = request.getParameter("reservation1") != null;
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
        BigDecimal threshold = BigDecimal.valueOf(Double.parseDouble(thresholdStr));

        // setup authorization request
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(amount);
        paymentDetails.setReservation(reservation);

        PaymentAuthorizationRequest authRequest = new PaymentAuthorizationRequest();
        authRequest.setFutureChargesAuthorizationRequired(multiplePayments);
        authRequest.setTotalChargesThreshold(threshold);
        authRequest.setPaymentDetails(paymentDetails);

        Properties properties = loadProperties("webapp.properties");
        String  webappUrl = properties.getProperty("url");
        authRequest.setRedirectUrl(webappUrl + "/AuthorizationReadyServlet");

        // initiate authorization request
        PaymentAuthorizationData paymentAuthorizationData = null;
        try {
            AmazonPaymentManager amazonPaymentManager = new AmazonPaymentManagerImpl();
            paymentAuthorizationData = amazonPaymentManager.initiatePaymentAuthorization(authRequest);
        } catch (AmazonFlexiblePaymentManagementException e) {
            e.printStackTrace();
        }

        // redirect the client to the authorization page
        String authorizationUrl = paymentAuthorizationData.getAuthorizationUrl();
        response.sendRedirect(authorizationUrl);
	}

	private static Properties loadProperties(String fileName) throws IOException {
	    URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
	    InputStream inputStream = resourceUrl.openStream();
	    Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}
