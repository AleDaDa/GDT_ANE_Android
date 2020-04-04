package com.ale1900.myhelloane;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.ale1900.myhelloane.funcs.*;

import java.util.HashMap;
import java.util.Map;

public class ExContext  extends FREContext {

    String CLASS = "ExContext -";

    @Override
    public void dispose() {

        log("dispose");
        // Remove all available Ads in the Map
        for (String bannerId : mBannersMap.keySet()) {
            removeBanner(bannerId);
        }
        // Clear the map
        mBannersMap = null;
    }

    @Override
    public Map<String, FREFunction> getFunctions()
    {
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
        String preFunName = "gdt";
        functions.put(preFunName+AneFunctions.enableDebug, new SetDebug());
        functions.put(preFunName+AneFunctions.SET_APPID,			new InitApp());
        functions.put(preFunName+AneFunctions.BANNER_CREATE,			new BannerCreate());
//		functions.put(AneFunctions.BANNER_CREATE_ABSOLUTE,	new BannerCreateAbsolute());
        functions.put(preFunName+AneFunctions.BANNER_SHOW,				new BannersShow());
        functions.put(preFunName+AneFunctions.BANNER_HIDE,				new BannerHide());
		functions.put(preFunName+AneFunctions.BANNER_REMOVE,			new BannerRemove());

        functions.put(preFunName+AneFunctions.INTERSTITIAL_CREATE,			new InterstitialCreate());
        functions.put(preFunName+AneFunctions.INTERSTITIAL_REMOVE,			new InterstitialRemove());
        functions.put(preFunName+AneFunctions.INTERSTITIAL_SHOW,			new InterstitialShow());
        functions.put(preFunName+AneFunctions.INTERSTITIAL_CACHE,			new InterstitialCache());
        functions.put(preFunName+AneFunctions.INTERSTITIAL_IS_LOADED,		new InterstitialIsLoaded());

        functions.put(preFunName+AneFunctions.VIDEO_CREATE,		new RewardVideoCreate());
        functions.put(preFunName+AneFunctions.VIDEO_SHOW,		new RewardVideoShow());
//        functions.put(preFunName+AneFunctions.VIDEO_CACHE,		new Re());
        functions.put(preFunName+AneFunctions.VIDEO_REMOVE,		new RewardVideoRemove());
        functions.put(preFunName+AneFunctions.VIDEO_IS_LOADED,		new RewardVideoIsLoaded());
        return functions;
    }
    public void log(String msg)
    {
        if( mEnabeDebug ){
            this.dispatchStatusEventAsync("onDebug", msg);
        }
    }



    private RelativeLayout mAdLayout;
    private Map<String, GdtBanner> mBannersMap;
    public void buildLayout(Activity act) {
        log("buildLayout");
        // Create the new relative Layout
        mAdLayout = new RelativeLayout(act);
        // Get the current Frame Layout
        ViewGroup fl = (ViewGroup)act.findViewById(android.R.id.content);
        fl = (ViewGroup)fl.getChildAt(0);
        // Add the new relative Layout to the Frame Layout
        fl.addView(mAdLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    String mAppID;
    boolean mEnabeDebug = false;
    public void setAppId(String id)
    {
        mAppID = id;
    }
    public void setEnabeDebug(boolean enableDebug)
    {
        mEnabeDebug = enableDebug;
    }
//=============================================

    public  void createBanner(Activity act, String bannerTag, String bannerId, int sizeType, int posX, int posAnch)
    {
        log("createBanner");
        if(mAdLayout == null) buildLayout(act);
        if(mBannersMap==null){
            mBannersMap = new HashMap<String, GdtBanner>();
        }
        // Add the Banner to the Banners Map
        mBannersMap.put(bannerTag, new GdtBanner(this, act, mAdLayout, bannerId, mAppID,0,0,posX,posAnch)  );
        // Create the Banner
        mBannersMap.get(bannerTag).create();
    }
    public void showBanner(String bannerId)
    {
        log("showBanner");
        // Remove the Banner
        mBannersMap.get(bannerId).show();
    }
    /**
     * Remove the Banner
     *
     * @param bannerId Banner id to be removed
     */
    public void removeBanner(String bannerId) {
        log("removeBanner");
        // Remove the Banner
        mBannersMap.get(bannerId).remove();
        // Remove the Banner from the Banners Map
        mBannersMap.remove(bannerId);
    }
    public void createBannerAbsolute(String bannerId, int sizeType, int px, int py)
    {
//        mBannersMap.put(bannerId, new GdtBanner(this, act, mAdLayout, bannerId, appId,0,1,posX,0)  );
    }
    public void hideBanner(String bannerId)
    {
        log("hideBanner");
        // Remove the Banner
        mBannersMap.get(bannerId).hide();
    }
    // =================================================================================================
    //	Manager Interstitial Functions
    // =================================================================================================
    GdtInterstitial mInterstitialAd;
    /**
     * Create the Interstitial
     *
     * @param act Activity instance
     * @param interstitialId AdMobId
     */
    public void createInterstitial(Activity act,String interstitialId) {
        log(CLASS+"createInterstitial.."+interstitialId);
        if( mInterstitialAd != null ){
            return;
        }
        // Create the Banner
        mInterstitialAd =  new GdtInterstitial(this, act, mAppID, interstitialId);
        mInterstitialAd.create();
    }
    /**
     * Cache the Interstitial
     *
     */
    public void cacheInterstitial() {
        log(CLASS+"cacheInterstitial");
        // Cache the Banner
        mInterstitialAd.cache();
    }

    /**
     * Remove the Interstitial
     *
     */
    public void removeInterstitial() {
        log(CLASS+"removeBanner");
        // Remove the Interstitial
        mInterstitialAd.remove();
        mInterstitialAd = null;
    }

    /**
     * Check the Interstitial Load state
     *
     */
    public Boolean isInterstitialLoaded() {
        log(CLASS+"isInterstitialLoaded");
        // Check the Interstitial
        return mInterstitialAd.isLoaded();
    }

    /**
     * Show the Interstitial
     *
     */
    public void showInterstitial() {
        log(CLASS+"showInterstitial");
        // Cache the Banner
        mInterstitialAd.show();
    }

    // =================================================================================================
    //	Manager video Functions
    // =================================================================================================
    public GdtVideo mVideo;
    public void createRewardVideo(Activity act, String videoID)
    {
        log(CLASS+"createRewardVideo");
        if(mVideo!= null){
            return;
        }
        mVideo = new GdtVideo(this, act, mAppID, videoID);
        mVideo.create();
        mVideo.cache();
    }
    public boolean isRewardVideoLoaded()
    {
        log(CLASS+"isRewardVideoLoaded: ");
        if( mVideo == null ){
            log(CLASS+" isRewardVideoLoaded: mVideo == null" );
            return false;
        }
        return mVideo.isLoaded();
    }
    public void showRewardVideo()
    {
        log(CLASS+"showRewardVideo");
        if( mVideo != null ){
            mVideo.show();
        }
    }
    public void removeRewardVideo()
    {
        log(CLASS+"removeRewardVideo");
        if( mVideo==null ){
            return;
        }
        mVideo.remove();
        mVideo = null;
    }


}
