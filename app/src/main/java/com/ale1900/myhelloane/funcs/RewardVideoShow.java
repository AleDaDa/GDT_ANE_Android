package com.ale1900.myhelloane.funcs;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.ale1900.myhelloane.ExContext;

public class RewardVideoShow  implements FREFunction {
    public FREObject call(FREContext context, FREObject[] args) {
        // Try to process the call
        try {
            // Get The Extension Context
            ExContext cnt	= (ExContext) context;
            // Get the Extension context instance
            cnt.showRewardVideo();
        } catch (Exception e) {
            // Print the exception stack trace
            e.printStackTrace();
        }
        // Return
        return null;
    }
}