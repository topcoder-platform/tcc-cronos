package webdemo;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonPaymentManager;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;
import com.topcoder.payments.amazonfps.model.PaymentDetails;

/**
 * Servlet implementation class AuthorizedPaymentServlet
 */
public class AuthorizedPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizedPaymentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // parse request parameters
        String amountStr = request.getParameter("amount2");
        String authIdStr = request.getParameter("authorizationId");

        boolean reservation = request.getParameter("reservation2") != null;
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
        long authorizationId = Long.parseLong(authIdStr);

        // setup authorization request
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(amount);
        paymentDetails.setReservation(reservation);

        // initiate authorization request
        long paymentId = 0;
        try {
            AmazonPaymentManager amazonPaymentManager = new AmazonPaymentManagerImpl();
            paymentId = amazonPaymentManager.processAuthorizedPayment(authorizationId, paymentDetails);
        } catch (AmazonFlexiblePaymentManagementException e) {
            e.printStackTrace();
        }

        response.sendRedirect("operation_completed2.jsp?paymentId=" + Long.toString(paymentId));
	}
}
