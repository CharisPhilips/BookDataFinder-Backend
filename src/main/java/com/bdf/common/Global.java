package com.bdf.common;

import java.util.HashMap;

import com.bdf.entity.Bookcategory;

public class Global {

	public static final int MAX_RESULT = 1000;
	public static final long PAYMENT_AMOUNT = 10 * 100;
	
	public static String PATH_PDF_ROOT;
	public static String STRIPE_SECRET_KEY;
	public static String STRIPE_PUBLIC_KEY;
	
	public static boolean isTestMode = true;
	public static Bookcategory g_category = null;
	public static HashMap<String, Long> g_userPaymentMap = new HashMap<String, Long>();
	
	public static int SERVICE_MONTH_PER_PAY = 12;
//	static {
//		if(isTestMode) {
//			STRIPE_SECRET_KEY = "sk_test_gKOz9xx9Zlj9OnFazZoaYWrn00KMq15X2k";
//			STRIPE_PUBLIC_KEY = "pk_test_TsTLBeOdMLK9Gvb6SbhjaNKT00sdPe5adP";
//		}
//		else {
//			STRIPE_SECRET_KEY = "sk_live_dqrnNTI0m6uutN27sI0xc9xh003ZWicexl";
//			STRIPE_PUBLIC_KEY = "pk_live_MlMaqh0MXlmvihTvBdvRRqmN00IivszxVa";
//		}
//	}
}
