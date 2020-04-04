package com.ale1900.myhelloane;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class HelloAne  implements FREFunction {
    @Override
    public FREObject call(FREContext freContext, FREObject[] args)
    {

        try {
            return FREObject.newObject("Hello ANE!");
        }catch (Exception e){

        }
        return null;
    }
}
