package com.bdf.entity.mix.pay;

public class PayResponse {
	
	public boolean requiresAction;
	public String clientSecret;
	public String error;
	
	public boolean isRequiresAction() { return requiresAction; }
	public void setRequiresAction(boolean requiresAction) { this.requiresAction = requiresAction; }
	public String getClientSecret() { return clientSecret; }
	public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
	public String getError() { return error; }
	public void setError(String error) { this.error = error; }
}
