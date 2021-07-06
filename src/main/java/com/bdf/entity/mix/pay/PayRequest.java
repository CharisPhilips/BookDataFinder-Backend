package com.bdf.entity.mix.pay;

public class PayRequest {
	
    public String stripeEmail;
    public String paymentIntentId;
    public String paymentMethodId;
    
	public String getStripeEmail() { return stripeEmail; }
    public void setStripeEmail(String stripeEmail) { this.stripeEmail = stripeEmail; }
    
    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }
    
    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }
}
