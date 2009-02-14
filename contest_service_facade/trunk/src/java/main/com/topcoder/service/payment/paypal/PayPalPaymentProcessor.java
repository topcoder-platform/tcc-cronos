package com.topcoder.service.payment.paypal;

import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.CallerServices;
import com.paypal.soap.api.AckCodeType;
import com.paypal.soap.api.AddressType;
import com.paypal.soap.api.BasicAmountType;
import com.paypal.soap.api.CountryCodeType;
import com.paypal.soap.api.CreditCardDetailsType;
import com.paypal.soap.api.CreditCardTypeType;
import com.paypal.soap.api.CurrencyCodeType;
import com.paypal.soap.api.DoDirectPaymentRequestDetailsType;
import com.paypal.soap.api.DoDirectPaymentRequestType;
import com.paypal.soap.api.DoDirectPaymentResponseType;
import com.paypal.soap.api.PayerInfoType;
import com.paypal.soap.api.PaymentActionCodeType;
import com.paypal.soap.api.PaymentDetailsType;
import com.paypal.soap.api.PersonNameType;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;

/**
 * <p>
 * This implementation class processes the payment (<code>PaymentData</code>)
 * through PayPal SOAP APIs
 * </p>
 * 
 * @author TCSDEVELOPER
 * 
 */
public class PayPalPaymentProcessor implements PaymentProcessor {

	/**
	 * A instance to PayPal SOAP API caller services.
	 */
	CallerServices paypalServicesCaller;

	/**
	 * <p>
	 * This constructor initializes the API Profile to the
	 * <code>CallerServices</code>. The API profile are the merchant's (in this
	 * case TopCoder) PayPal API details.
	 * </p>
	 * 
	 * @throws PaymentException
	 *             it throws this exception on any issues during caller services
	 *             initialization. Issues can be: wrong authentication
	 *             information, invalid information etc.
	 */
	public PayPalPaymentProcessor() throws PaymentException {
		try {
			paypalServicesCaller = new CallerServices();

			APIProfile profile = ProfileFactory.createSignatureAPIProfile();

			// a test api profile for this assembly.
			profile.setAPIUsername("seller_1234307677_biz_api1.gmail.com");
			profile.setAPIPassword("1234307687");
			profile
					.setSignature("AnZ1R08OB-0kjv8ScKWWh7ksDw0gADepvbH.O3VcBMgT4iAXlSNSLuil");

			// we are using beta sandbox for testing.
			profile.setEnvironment("beta-sandbox");
			paypalServicesCaller.setAPIProfile(profile);
		} catch (PayPalException e) {
			throw new PaymentException(e);
		}
	}

	/**
	 * <p>
	 * An implementation method of the <code>PaymentProcessor</code> interface.
	 * This method simply delegates the responsibility to internal
	 * doDirectPayment(..) method.
	 * 
	 * On successful payment, internal method returns PayPal transaction id.
	 * This method wraps the transaction id in PaymentResult and return it to
	 * the caller.
	 * </p>
	 * 
	 * @param payment
	 *            the payment data that need to be processed.
	 * @param amount
	 *            the amount that need to be charged.
	 * @return a <code>PaymentResult</code> the payment result. In this case
	 *         transaction id from paypal.
	 * @throws PaymentException
	 *             on payment failure, it captures Error message and Error code
	 *             for the failure case.
	 */
	public PaymentResult process(PaymentData payment, String amount)
			throws PaymentException {
		try {
			String transactionId = doDirectPayment(
					(CreditCardPaymentData) payment, amount);
			PaymentResult result = new PaymentResult();
			result.setReferenceNumber(transactionId);
			return result;
		} catch (PayPalException e) {
			throw new PaymentException();
		}
	}

	/**
	 * <p>
	 * This internal method processes the given
	 * <code>CreditCardPaymentData</code> through PayPal SOAP SDK API.
	 * 
	 * Implementation simply sets various bean properties, as required by
	 * DoDirectPayment PayPal API and then calls the API.
	 * 
	 * </p>
	 * 
	 * @param paymentData
	 *            the given <code>CreditCardPaymentData</code> that need to be
	 *            processed.
	 * @param amount
	 *            a<code>String</code> amount that need to be charged for the
	 *            credit card details.
	 * @return on success it returns the PayPal transaction id.
	 * 
	 * @throws PayPalException
	 *             on some paypal internal api related exceptions/errors.
	 * @throws PaymentException
	 *             on payment failure, it captures Error message and Error code
	 *             for the failure case.
	 */
	private String doDirectPayment(CreditCardPaymentData paymentData,
			String amount) throws PayPalException, PaymentException {
		DoDirectPaymentRequestType request = new DoDirectPaymentRequestType();
		DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();

		// determine the card type.
		CreditCardTypeType cardType = null;
		if (paymentData.getCardType().equalsIgnoreCase("Visa")) {
			cardType = CreditCardTypeType.Visa;
		} else if (paymentData.getCardType().equalsIgnoreCase("MasterCard")) {
			cardType = CreditCardTypeType.MasterCard;
		} else if (paymentData.getCardType().equalsIgnoreCase("Amex")) {
			cardType = CreditCardTypeType.Amex;
		}

		// set card number, expiry month & year.
		CreditCardDetailsType creditCard = new CreditCardDetailsType();
		creditCard.setCreditCardNumber(paymentData.getCardNumber());
		creditCard.setCreditCardType(cardType);
		creditCard.setExpMonth(new Integer(paymentData.getCardExpiryMonth()));
		creditCard.setExpYear(new Integer(paymentData.getCardExpiryYear()));

		// set payer info
		PayerInfoType cardOwner = new PayerInfoType();

		// right now the country is hard-coded (as discussed in forum thread)
		cardOwner.setPayerCountry(CountryCodeType.US);

		// set payer address.
		AddressType address = new AddressType();
		address.setPostalCode(paymentData.getZipCode());
		address.setStateOrProvince(paymentData.getState());
		address.setStreet1(paymentData.getAddress());
		address.setCountryName(paymentData.getCountry());

		// right now the country is hard-coded (as discussed in forum thread)
		address.setCountry(CountryCodeType.US);
		address.setCityName(paymentData.getCity());
		address.setPhone(paymentData.getPhone());
		cardOwner.setAddress(address);

		// set payer name etc.
		PersonNameType payerName = new PersonNameType();
		payerName.setFirstName(paymentData.getFirstName());
		payerName.setLastName(paymentData.getLastName());
		cardOwner.setPayerName(payerName);

		creditCard.setCardOwner(cardOwner);
		details.setCreditCard(creditCard);

		// set payer ip address and session id etc.
		details.setIPAddress(paymentData.getIpAddress());

		if (paymentData.getSessionId() != null) {
			details.setMerchantSessionId(paymentData.getSessionId());
		}

		// mark this payment as final.
		details.setPaymentAction(PaymentActionCodeType.Sale);

		// capture the payment details - amount, current etc.
		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setCurrencyID(CurrencyCodeType.USD);
		orderTotal.set_value(amount);
		paymentDetails.setOrderTotal(orderTotal);

		details.setPaymentDetails(paymentDetails);
		request.setDoDirectPaymentRequestDetails(details);

		// process the payment through paypal soap api.
		DoDirectPaymentResponseType response = (DoDirectPaymentResponseType) paypalServicesCaller
				.call("DoDirectPayment", request);

		// if response is successful then return the transaction id.
		if (response.getAck().equals(AckCodeType.Success)
				|| response.getAck().equals(AckCodeType.SuccessWithWarning)) {
			return response.getTransactionID();
		} else {
			// if response is failure then throw the PaymentException.
			// response code and response long message from paypal is used in
			// the exception.
			throw new PaymentException(response.getErrors()[0].getErrorCode()
					.toString(), response.getErrors()[0].getLongMessage());
		}
	}

	/**
	 * <p>
	 * A simple main method to test the PayPal payment processing for specific
	 * values.
	 * </p>
	 * 
	 * @param args
	 *            arguments to main method. this doesn't receive any argument.
	 * @throws PaymentException
	 *             it throws this exception if any during processing payment
	 *             through PayPal.
	 */
	public static void main(String[] args) throws PaymentException {
		// hard-coded set of values for testing the payment.
		CreditCardPaymentData paymentData = new CreditCardPaymentData();
		paymentData.setCardNumber("4444101699032084");
		paymentData.setCardType("Visa");
		paymentData.setCardExpiryMonth("2");
		paymentData.setCardExpiryYear("2019");

		paymentData.setFirstName("TCS");
		paymentData.setLastName("Developer");
		paymentData.setAddress("TCSDeveloper are everywhere");
		paymentData.setCity("Dallas");
		paymentData.setState("TX");
		paymentData.setZipCode("75605");
		paymentData.setCountry("USA");
		paymentData.setPhone("9999999999");
		paymentData.setEmail("tcsdeveloper@topcoder.com");

		PayPalPaymentProcessor paymentProcessor = new PayPalPaymentProcessor();
		paymentProcessor.process(paymentData, "20");

	}
}
