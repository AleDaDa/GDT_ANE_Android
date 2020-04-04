package com.ale1900.myhelloane;

import com.adobe.fre.FREExtension;

public class Extension  implements FREExtension {
    public static ExContext context;

    @Override
    public ExContext createContext(String arg0)
    {
        context = new ExContext();
        return context;
    }

    @Override
    public void dispose()
    {
        context = null;
    }

    @Override
    public void initialize() { }
}
