package com.ale1900.myhelloane.funcs;

import android.app.Activity;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.ale1900.myhelloane.ExContext;

public class BannerCreate implements FREFunction {
	public FREObject call(FREContext context, FREObject[] args) {
		// Try to process the call
		try {
			// Get The Extension Context
			ExContext cnt	= (ExContext) context;
			Activity act			= context.getActivity();

			// Set the passed parameter
			String bannerTag	= args[0].getAsString();
			String bannerId		= args[1].getAsString();
			int adSize			= args[2].getAsInt();
			int posX			= args[3].getAsInt();
			int posAnch			= args[4].getAsInt();

			// Get the Extension context instance
			cnt.createBanner(act, bannerTag, bannerId, adSize, posX, posAnch);
		} catch (Exception e) {
			// Print the exception stack trace
			e.printStackTrace();
		}
		// Return
		return null;
	}
}
