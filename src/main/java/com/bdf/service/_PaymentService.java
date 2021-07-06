package com.bdf.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.common.Global;
import com.bdf.dao.UserDAO;
import com.bdf.entity.User;
import com.bdf.entity.mix.pay.Checkout;
import com.bdf.entity.mix.pay.PayRequest;
import com.bdf.entity.mix.pay.PayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;

@Transactional
@Service("PaymentService")
public class _PaymentService  {

	@Autowired
	private UserDAO userDao; 
	
	public _PaymentService() {
		super();
		Stripe.apiKey = Global.STRIPE_SECRET_KEY;
	}

	public void setGlobalPayIntent(String paymentId, String eamil) throws Exception {
		//set global hashmap in order to detect user 
		List<User> userList = userDao.findUser(eamil);
		if(userList!=null) {
			if(userList.size() > 1) {
				throw new Exception("Your account over one account");
			}
			Global.g_userPaymentMap.put(paymentId, userList.get(0).getId());
		}
	}
	
	public int payWithWebhooks(String payload, String sigHeader) {
		Event event = null;
//		try {
////			event = Webhook.constructEvent(payload, sigHeader, Global.STRIPE_SECRET_KEY);
//		} catch (SignatureVerificationException e) {
//			return 400;
//		}
		event = ApiResource.GSON.fromJson(payload, Event.class);
		switch (event.getType()) {
		case "payment_intent.succeeded":
			//update user payment status
			ObjectMapper m = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> props = m.convertValue(event.getData(), Map.class);
			
			//check here from
		    Object dataMap = props.get("object");
		    @SuppressWarnings("unchecked")
		    Map<String, String> objectMapper = m.convertValue(dataMap, Map.class);
		    String paymentId = objectMapper.get("id");
		    //check here to
		    if(Global.g_userPaymentMap.containsKey(paymentId)) {
		    	Global.g_userPaymentMap.get(paymentId);
		    	long userId = Global.g_userPaymentMap.get(paymentId);
		    	User user = userDao.findById(userId);
		    	if(user==null) {
		    		return 400;
		    	}
		    	Date dtNow = new Date();
		    	Date dtService = user.getServicedate();
		    	Date nextServiDate = null;
		    	Calendar cal = null;
		    	if(dtService==null || dtService.before(dtNow)) {
		    		cal = Calendar.getInstance();
		    		cal.add(Calendar.MONTH, Global.SERVICE_MONTH_PER_PAY);
		    	}
		    	else {
		    		cal = Calendar.getInstance();
		    		cal.setTime(dtService);
		    		cal.add(Calendar.MONTH, Global.SERVICE_MONTH_PER_PAY);
		    	}
		    	nextServiDate = cal.getTime();
		    	user.setServicedate(nextServiDate);
		    	userDao.update(user);
		    }
			System.out.println("Payment success.");
			break;
		case "payment_intent.payment_failed":
			System.out.println("Payment failed.");
			break;
		default:
			return 400;
		}
		return 200;
	}
	
	public PayResponse payWithoutWebhooks(PayRequest request) {
		PaymentIntent intent = null;
		PayResponse responseBody = new PayResponse();
		try {
			if (request.getPaymentMethodId() != null) {
				
				long orderAmount = Global.PAYMENT_AMOUNT;
				PaymentIntentCreateParams.Builder createParamsBuilder = new PaymentIntentCreateParams.Builder()
						.setCurrency(Checkout.Currency.USD.name())
						.setAmount(new Long(orderAmount))
						.setPaymentMethod(request.getPaymentMethodId())
						.setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL).setConfirm(true);
				createParamsBuilder.setUseStripeSdk(true);
				PaymentIntentCreateParams createParams = createParamsBuilder.build();
				intent = PaymentIntent.create(createParams);
				// After create, if the PaymentIntent's status is succeeded, fulfill the order.
			}
			else if (request.getPaymentIntentId() != null) {
				// Confirm the PaymentIntent to finalize payment after handling a required
				// action on the client.
				intent = PaymentIntent.retrieve(request.getPaymentIntentId());
				intent = intent.confirm();
				// After confirm, if the PaymentIntent's status is succeeded, fulfill the order.
			}
			GenerateResponse(request.stripeEmail, intent, responseBody);
		} catch (Exception e) {
			// Handle "hard declines" e.g. insufficient funds, expired card, etc
			// See https://stripe.com/docs/declines/codes for more
			responseBody.setError(e.getMessage());
		}
		return responseBody;
	}

	private void GenerateResponse(String email, PaymentIntent intent, PayResponse response) {
		switch (intent.getStatus()) {
		case "requires_action":
		case "requires_source_action":
			// Card requires authentication
			response.setClientSecret(intent.getClientSecret());
			response.setRequiresAction(true);
			break;
		case "requires_payment_method":
		case "requires_source":
			// Card was not properly authenticated, suggest a new payment method
			response.setError("Your card was denied, please provide a new payment method");
			break;
		case "succeeded":
			System.out.println("Payment received!");
			// Payment is complete, authentication not required
			// To cancel the payment you will need to issue a Refund
			// (https://stripe.com/docs/api/refunds)
			response.setClientSecret(intent.getClientSecret());
			
			List<User> userList = userDao.findUser(email);
			if(userList==null || userList.size()==0) {
				response.setError("The system can't find user");
				return;
			}
			else if(userList.size() > 1) {
				response.setError("The system register find one more user");
				return;
			}
			
	    	User user = userList.get(0);
	    	Date dtNow = new Date();
	    	Date dtService = user.getServicedate();
	    	Date nextServiDate = null;
	    	Calendar cal = null;
	    	if(dtService==null || dtService.before(dtNow)) {
	    		cal = Calendar.getInstance();
	    		cal.add(Calendar.MONTH, Global.SERVICE_MONTH_PER_PAY);
	    	}
	    	else {
	    		cal = Calendar.getInstance();
	    		cal.setTime(dtService);
	    		cal.add(Calendar.MONTH, Global.SERVICE_MONTH_PER_PAY);
	    	}
	    	nextServiDate = cal.getTime();
	    	user.setServicedate(nextServiDate);
	    	userDao.update(user);
			
			break;
		default:
			response.setError("Unrecognized status");
		}
	}
}
