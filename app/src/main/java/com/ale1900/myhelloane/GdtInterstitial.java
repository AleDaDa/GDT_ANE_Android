package com.ale1900.myhelloane;

import android.app.Activity;

import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.interstitial2.UnifiedInterstitialMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

public class GdtInterstitial implements UnifiedInterstitialADListener, UnifiedInterstitialMediaListener {
    private static final String CLASS = "GdtInterstitial - ";


    // Working Instances
    private ExContext mContext;
    private Activity mActivity;
    private UnifiedInterstitialAD mInterstitialAd;

    // Banner Propriety
    private String mInterstitialId;
    private String mAppID;
    private boolean isAdLoaded;

    /**
     * Public Getter for Extension Context
     *
     * @return Extension Context Instance
     **/
    public ExContext getContext() { return mContext; }
    /**
     * Public Getter for Extension Activity
     *
     * @return Extension Activity Instance
     **/
    public Activity getActivity() { return mActivity; }

    public  GdtInterstitial(ExContext ctx, Activity act, String appID, String interstitialId)
    {
        // Set Interstitial working Instances
        mContext		= ctx;
        mActivity		= act;

        // Set Interstitial Propriety
        mInterstitialId	= interstitialId;
        mAppID = appID;
        isAdLoaded = false;
    }

    /**
     * Create the interstitial
     *
     */
    public void create() {
        mContext.log(CLASS+"create interstitial");

        // Create the interstitial.
        mInterstitialAd = new UnifiedInterstitialAD(mActivity, mAppID,mInterstitialId,this);
        isAdLoaded = false;
        mInterstitialAd.setMediaListener(this);
        mInterstitialAd.setMaxVideoDuration(30);
        cache();
    }

    /**
     * Remove the Interstitial
     *
     */
    public void remove() {
        mContext.log(CLASS+"remove");
        mContext		= null;
        mActivity		= null;
        mInterstitialId	= null;
        if( mInterstitialAd!= null ){
            mInterstitialAd.close();
            mInterstitialAd.destroy();
            mInterstitialAd = null;
        }
        isAdLoaded = false;
    }

    /**
     * Cache the interstitial
     *
     */
    public void cache() {
        mContext.log(CLASS+"cache interstitial");
        // Get the interstitial Request for adMob

        isAdLoaded = false;
        mInterstitialAd.loadAD();
    }

    /**
     * Check if the Interstitial is loaded
     *
     * @return Interstitial Load state
     */
    public Boolean isLoaded() {
        mContext.log(CLASS+"isLoaded: "+isAdLoaded);
        // Check the Interstitial
        return isAdLoaded;
    }

    /**
     * Show the interstitial
     *
     */
    public void show() {
        mContext.log(CLASS+"show");
        // Show the Interstitial
        if( isAdLoaded == true ){
            mInterstitialAd.showAsPopupWindow();
        }
    }

    @Override
    public void onADReceive() {
        isAdLoaded = true;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialReceive, mInterstitialId);

    }

    @Override
    public void onVideoCached() {
        isAdLoaded = true;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialReceive, mInterstitialId);
    }

    @Override
    public void onNoAD(AdError adError) {
        isAdLoaded = false;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialFailedReceive, adError.getErrorMsg());
    }

    @Override
    public void onADOpened() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialDismiss, mInterstitialId);
    }

    @Override
    public void onADExposure() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialPresent, mInterstitialId);
    }

    @Override
    public void onADClicked() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialDismiss, mInterstitialId);
    }

    @Override
    public void onADLeftApplication() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialLeaveApplication, mInterstitialId);
    }

    @Override
    public void onADClosed() {
        isAdLoaded = false;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onInterstitialClosed, mInterstitialId);
        mContext.removeInterstitial();
    }

    @Override
    public void onVideoInit() {

    }

    @Override
    public void onVideoLoading() {

    }

    @Override
    public void onVideoReady(long l) {

    }

    @Override
    public void onVideoStart() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onVideoComplete() {

    }

    @Override
    public void onVideoError(AdError adError) {

    }

    @Override
    public void onVideoPageOpen() {

    }

    @Override
    public void onVideoPageClose() {
        isAdLoaded = false;
    }
}
