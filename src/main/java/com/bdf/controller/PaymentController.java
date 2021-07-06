package com.bdf.controller;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bdf.common.Global;
import com.bdf.controller.base.BaseController;
import com.bdf.entity.User;
import com.bdf.entity.mix.pay.Checkout;
import com.bdf.entity.mix.pay.PayRequest;
import com.bdf.entity.mix.pay.Checkout.Currency;
import com.bdf.entity.mix.pay.PayResponse;
import com.bdf.service._PaymentService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class PaymentController extends BaseController {

	private String stripePublicKey;
	@Autowired
	private _PaymentService paymentsService;
//	@Autowired
//	private UserService userService;

	public PaymentController() {
		super();
		stripePublicKey = Global.STRIPE_PUBLIC_KEY;
	}

	@GetMapping("/checkout")
	public ResponseEntity<Checkout> checkout(@RequestParam Map<String, String> userParams) throws Exception {
		
		PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
				.setCurrency(Currency.USD.name()).setAmount(Global.PAYMENT_AMOUNT)
				.build();
		PaymentIntent intent = PaymentIntent.create(createParams);
		Checkout response = new Checkout();
		response.setStripePublicKey(this.stripePublicKey);
		response.setClientSecret(intent.getClientSecret());
		response.setCurrency(Currency.USD);
		response.setAmount(Global.PAYMENT_AMOUNT / 100);
		//set global hashmap in order to detect user
		String email = userParams.get("email");
		paymentsService.setGlobalPayIntent(intent.getId(), email);//check here
		return new ResponseEntity<Checkout>(response, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value="/webhook", method=RequestMethod.POST)
	public ResponseEntity<Response> webhook(@RequestBody(required=true)String request, @RequestHeader HttpHeaders headers) {
		
		Response result = new Response(); 
		String signature = headers.getFirst("Stripe-Signature");
		int status = paymentsService.payWithWebhooks(request, signature);
		
		result.setStatus(status);
		return new ResponseEntity<Response>(result, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value="/pay", method=RequestMethod.POST)
	public ResponseEntity<PayResponse> pay(@RequestBody(required=true)PayRequest request) {
		PayResponse response = paymentsService.payWithoutWebhooks(request);
		return new ResponseEntity<PayResponse>(response, HttpStatus.CREATED);
	}
//	/**
//	 * Confirms the payment on the server and allows you to run any post-payment logic right after.
//	 * 
//	 * @param confirmRequest
//	 * @return
//	 */
//	public ResponseEntity<PayResponseBody> payWithoutWebhook(ConfirmPaymentRequest confirmRequest) {
//		PayResponseBody result = paymentsService.payWithoutWebhooks(confirmRequest);
//		return new ResponseEntity<PayResponseBody>(result, HttpStatus.CREATED);
//	}
}
