package com.bdf.entity.mix;

import com.google.gson.annotations.SerializedName;

public class ConfirmPaymentRequest {
	
	@SerializedName("items")
    Object[] items;
	
    @SerializedName("paymentIntentId")
    String paymentIntentId;
    
    @SerializedName("paymentMethodId")
    String paymentMethodId;
    
    @SerializedName("currency")
    String currency;
    
    @SerializedName("useStripeSdk")
    String useStripeSdk;

    public Object[] getItems() {
        return items;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getCurrency() {
        return currency;
    }

    public Boolean getUseStripeSdk() {
        return Boolean.parseBoolean(useStripeSdk);        
    }
}
