package com.ale1900.myhelloane.funcs;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.ale1900.myhelloane.ExContext;

public class RewardVideoIsLoaded  implements FREFunction {
	public FREObject call(FREContext context, FREObject[] args) {
		// Try to process the call
		try {
			// Get The Extension Context
			ExContext cnt	= (ExContext) context;
			
			 return FREObject.newObject(cnt.isRewardVideoLoaded());
		} catch (Exception e) {
			// Print the exception stack trace
			e.printStackTrace();
		}
		// Return
		return null;
	}
}