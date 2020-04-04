package com.ale1900.myhelloane.funcs;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.ale1900.myhelloane.ExContext;

public class BannerCreateAbsolute implements FREFunction {
	public FREObject call(FREContext context, FREObject[] args) {
		// Try to process the call
		try {
			// Get The Extension Context
			ExContext cnt	= (ExContext) context;

			// Set the passed parameter
			String bannertag	= args[0].getAsString();
			String bannerId		= args[1].getAsString();
			int adSize			= args[2].getAsInt();
			int px			= args[3].getAsInt();
			int py 		= args[4].getAsInt();

			// Get the Extension context instance
			cnt.createBannerAbsolute(bannerId, adSize, px, py);
		} catch (Exception e) {
			// Print the exception stack trace
			e.printStackTrace();
		}
		// Return
		return null;
	}
}
