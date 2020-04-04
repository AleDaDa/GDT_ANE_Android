package com.ale1900.myhelloane;

import android.app.Activity;
import android.os.SystemClock;

import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;


public class GdtVideo implements RewardVideoADListener {
    private static final String CLASS = "GdtVideo - ";


    // Working Instances
    private ExContext mContext;
    private Activity mActivity;
    private RewardVideoAD mRewardVideoAD ;

    // Banner Propriety
    private String mVideoId;
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

    public GdtVideo(ExContext ctx, Activity act, String appID, String videoId)
    {
        // Set Interstitial working Instances
        mContext		= ctx;
        mActivity		= act;

        // Set Interstitial Propriety
        mVideoId	= videoId;
        mAppID = appID;
        isAdLoaded = false;

        mContext.log(CLASS+" contruct");
    }

    /**
     * Create the interstitial
     *
     */
    public void create() {
        mContext.log(CLASS+"create RewardVideoAD");

        // Create the interstitial.
        mRewardVideoAD = new RewardVideoAD(mActivity, mAppID, mVideoId, this, false); // 有声播放
        isAdLoaded = false;
    }

    /**
     * Remove the Interstitial
     *
     */
    public void remove() {
        mContext.log(CLASS+"remove");
        mContext		= null;
        mActivity		= null;
        mVideoId	= null;
        if( mRewardVideoAD!= null ){
            mRewardVideoAD = null;
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
        mRewardVideoAD.loadAD();
    }

    /**
     * Check if the Interstitial is loaded
     *
     * @return Interstitial Load state
     */
    public Boolean isLoaded() {
        mContext.log(CLASS+"isLoaded: "+isAdLoaded);
        // Check the Interstitial
        if( isAdLoaded == false || mRewardVideoAD == null ){
            return false;
        }

        if( mRewardVideoAD.hasShown() ){
            return false;
        }
        long delta = 1000;
        if( SystemClock.elapsedRealtime() > (mRewardVideoAD.getExpireTimestamp() - delta )){
            return false;
        }

        return true;
    }

    /**
     * Show the interstitial
     *
     */
    public void show() {
        mContext.log(CLASS+"show");
        // Show the Interstitial
        if( isAdLoaded == true ){
            mRewardVideoAD.showAD();
        }
    }

    @Override
    public void onADLoad() {
        isAdLoaded = true;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoReceive, mVideoId);
    }

    @Override
    public void onVideoCached() {
        isAdLoaded = true;
        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoCache, mVideoId);
    }

    @Override
    public void onADShow() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoStart, mVideoId);
    }

    @Override
    public void onADExpose() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoOpen, mVideoId);
    }

    @Override
    public void onReward() {

        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoReward, mVideoId);
    }

    @Override
    public void onADClick() {

        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoDismiss, mVideoId);
    }

    @Override
    public void onVideoComplete() {

        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoFinish, mVideoId);
    }

    @Override
    public void onADClose() {
        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoClose, mVideoId);
    }

    @Override
    public void onError(AdError adError) {

        mContext.dispatchStatusEventAsync(ExtensionEvents.onRewardVideoFailedReceive, adError.getErrorMsg());
    }
}
