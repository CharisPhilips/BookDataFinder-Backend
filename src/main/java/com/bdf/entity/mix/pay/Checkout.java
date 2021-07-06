package com.bdf.entity.mix.pay;

public class Checkout {
	public enum Currency {
        USD, EUR;
    }
	
	private String stripePublicKey;
	private String clientSecret;
	private long amount;
    private Currency currency;
    
	public String getStripePublicKey() { return stripePublicKey; }
	public void setStripePublicKey(String stripePublicKey) { this.stripePublicKey = stripePublicKey; }
	
	public String getClientSecret() { return clientSecret; }
	public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
	
	public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }
    
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    
}
