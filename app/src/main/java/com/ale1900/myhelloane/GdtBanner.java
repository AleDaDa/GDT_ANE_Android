package com.ale1900.myhelloane;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;

public class GdtBanner implements UnifiedBannerADListener {

		// Position Types Constants
		//private static int POS_RELATIVE		= 0;
		private static int POS_ABSOLUTE		= 1;
		private static final String CLASS = GdtBanner.class.getSimpleName();
		// Working Instances
		private ExContext mContext;	// Extension Context
		private Activity mActivity;			// Extension Activity
		private RelativeLayout mAdLayout;	// adView Instance
		private ViewGroup bannerContainer;
		private UnifiedBannerView mAdView;				// adView Instance

		// Banner Propriety
	    private String mBannerId;
	    private String mAdMobId;
//	    private AdSize mAdMobSize;
	    private int mAdPositionType;
	    private int mAdRelPosition;
	    private int mAdAbsPositionX;
	    private int mAdAbsPositionY;

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

		/**
		 * Public Getter for Banner AdView
		 *
		 * @return Banner AdView Instance
		 **/
		public UnifiedBannerView getAdView() { return mAdView; }

		/**
		 * AdMobBanner Constructor
		 *
		 * @param ctx Extension context instance
		 * @param act Activity instance
		 * @param lay Layout Instance
		 * @param bannerId Unique banner ID
		 * @param adMobId AdMobId
		 * @param adSize Banner size index
		 * @param posType Type of position (POS_RELATIVE|POS_ABSOLUTE)
		 * @param position1 First position data
		 * @param position2 Second position data
		 *
		 **/
	    public GdtBanner(
				ExContext ctx, Activity act, RelativeLayout lay,
				String bannerId, String adMobId, int adSize,
				int posType, int position1, int position2)
	    {
	    	// Set banner working Instances
	    	mContext	= ctx;
	    	mActivity	= act;
	    	mAdLayout	= lay;

//			setContentView(R.layout.activity_unified_banner);
//			bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);
//			((EditText) findViewById(R.id.posId)).setText(Constants.UNIFIED_BANNER_POS_ID);
//			this.findViewById(R.id.refreshBanner).setOnClickListener(this);
//			this.findViewById(R.id.closeBanner).setOnClickListener(this);

	    	mContext.log(CLASS+"Constructor");

	    	// Set banner Propriety
	        mBannerId		= bannerId;
	        mAdMobId		= adMobId;
//	        mAdMobSize		= getAdSize(adSize);
	        mAdPositionType	= posType;
	        mAdRelPosition	= mAdAbsPositionX = position1;
	        mAdAbsPositionY = position2;
	    }

		/**
		 * Create the Banner
		 *
		 */
		public void create() {
	    	mContext.log(CLASS+"create adview");

	    	// Create the new adView
			mAdView = new UnifiedBannerView(mActivity,mAdMobId, mBannerId,this);
//			mAdView.setAdUnitId(mAdMobId);
//			mAdView.setAdSize(mAdMobSize);

			// Force Hardware Rendering on supported devices
	    	if (Build.VERSION.SDK_INT >= 11) {
	    		mAdView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
	    	}
//			mContext.getActivity().setContentView(R.layout.activity_unified_banner)
	    	mContext.log(CLASS+"set adview Listeners");
			// Add the Listener for the adView

			// Set the adView visibility as hidden by default
//			mAdView.setVisibility(View.GONE);
	    	mContext.log(CLASS+"set adview Layout");

			// Set the view Position in the layout
			RelativeLayout.LayoutParams layoutParams;
			if(mAdPositionType == POS_ABSOLUTE){
				layoutParams = getAbsoluteParams();
			}else{
				layoutParams = getRelativeParams();
			}
			mAdLayout.addView(mAdView, layoutParams);
			// Create the Banner Request for adMob
//			AdRequest request = mContext.getRequest();

			// Load the ad
	    	mContext.log(CLASS+"load adview Request");
			mAdView.loadAD();
		}

		/**
		 * Remove the Ad
		 *
		 */
		public void remove() {
	    	mContext.log(CLASS+"remove");
			// If a Layout is available remove the banner view
			if(mAdLayout != null)
			{
				// Remove views
				mAdLayout.removeView(mAdView);
				// Clear the adView instance
				if( mAdView != null ){
					mAdView.destroy();
				}
				mAdView = null;
			}
		}

		/**
		 * Show the Ad
		 *
		 */
		public void show() {
	    	mContext.log(CLASS+"show");
			// Set the adView visibility
			if( mAdView!=null ){
//				mAdView.setVisibility(View.VISIBLE);
				mAdView.setRefresh(40);
			}

		}

		/**
		 * Hide the Ad
		 *
		 */
		public void hide() {
	    	mContext.log(CLASS+"hide, unsurport");
			// Set the adView visibility
			if( mAdView != null ){
//				mAdView.setVisibility(View.GONE);
				mAdView.setRefresh(0);
			}
		}

		/**
		 * Get the view Absolute Layout Parameters
		 *
		 * @return Calculated Layout Parameters
		 */
		private RelativeLayout.LayoutParams getAbsoluteParams() {
	    	mContext.log(CLASS+"getAbsoluteParams");
			// Define returning instance
			RelativeLayout.LayoutParams params;

	    	// Set banner coordinates
//			int bannerWidth = mAdMobSize.getWidth();
//			int bannerHeight = mAdMobSize.getHeight();

	    	// Create the Parameters
			params = new RelativeLayout.LayoutParams (-2, -2);
	    	// Set the Layout Margins
			params.leftMargin = mAdAbsPositionX;
			params.topMargin = mAdAbsPositionY;

	    	mContext.log(CLASS+"Absolute Params = width: "+0+", height: "+0+", x: "+mAdAbsPositionX+", y: "+mAdAbsPositionY);
	        // Return the Parameters
	        return params;
		}

		/**
		 * Get the view Relative Layout Parameters
		 *
		 * @return Calculated Layout Parameters
		 */
		private RelativeLayout.LayoutParams getRelativeParams() {
	    	mContext.log(CLASS+"getRelativeParams");
			// Define instances
	        int firstVerb;
	        int secondVerb;
	        int anchor = RelativeLayout.TRUE;
			RelativeLayout.LayoutParams params;

			// Create the Parameters
			params = new RelativeLayout.LayoutParams (-2, -2);

	        // Set the alignment verbs according to the given position
	        switch(mAdRelPosition)
	        {
	        case 1: // TOP_LEFT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_TOP;
	        	secondVerb	= RelativeLayout.ALIGN_PARENT_LEFT;
	            break;
	        case 2: // TOP_CENTER
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_TOP;
	        	secondVerb	= RelativeLayout.CENTER_HORIZONTAL;
	            break;
	        case 3: // TOP_RIGHT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_TOP;
	        	secondVerb	= RelativeLayout.ALIGN_PARENT_RIGHT;
	            break;
	        case 4: // MIDDLE_LEFT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_LEFT;
	        	secondVerb	= RelativeLayout.CENTER_VERTICAL;
	            break;
	        case 5: // MIDDLE_CENTER
	        	firstVerb	= RelativeLayout.CENTER_HORIZONTAL;
	        	secondVerb	= RelativeLayout.CENTER_VERTICAL;
	            break;
	        case 6: // MIDDLE_RIGHT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_RIGHT;
	        	secondVerb	= RelativeLayout.CENTER_VERTICAL;
	            break;
	        case 7: // BOTTOM_LEFT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_LEFT;
	        	secondVerb	= RelativeLayout.ALIGN_PARENT_BOTTOM;
	            break;
	        case 8: // BOTTOM_CENTER
	        	firstVerb	= RelativeLayout.CENTER_HORIZONTAL;
	        	secondVerb	= RelativeLayout.ALIGN_PARENT_BOTTOM;
	            break;
	        case 9: // BOTTOM_RIGHT
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_RIGHT;
	        	secondVerb	= RelativeLayout.ALIGN_PARENT_BOTTOM;
	            break;
	        default: // TOP_CENTER
	        	firstVerb	= RelativeLayout.ALIGN_PARENT_TOP;
	        	secondVerb	= RelativeLayout.CENTER_HORIZONTAL;
	            break;
	        }
	        // Set the alignment rules
	    	params.addRule(firstVerb,anchor);
	    	params.addRule(secondVerb,anchor);
	    	mContext.log(CLASS+"Relative Params = first Verb: "+firstVerb+", second Verb: "+secondVerb+", anchor: "+anchor);
	        // Return the Parameters
	        return params;
		}

//		/**
//		 * Get the respective adSize according to the given index
//		 *
//		 * @param idx adSize index to be searched
//		 *
//		 * @return found adSize instance
//		 */
//		private AdSize getAdSize(int idx) {
//	    	mContext.log(CLASS+"getAdSize, idx: "+idx);
//	        // Return the AdSize according to the given index
//			if(idx == 0)		return AdSize.BANNER;
//	        else if(idx == 1)	return AdSize.MEDIUM_RECTANGLE;
//	        else if(idx == 2)	return AdSize.FULL_BANNER;
//	        else if(idx == 3)	return AdSize.LEADERBOARD;
//	        else if(idx == 4)	return AdSize.WIDE_SKYSCRAPER;
//	        else if(idx == 5)	return AdSize.SMART_BANNER;
//	        else if(idx == 6)	return AdSize.SMART_BANNER;
//	        else if(idx == 7)	return AdSize.SMART_BANNER;
//	        // Return the default if not found
//	        return AdSize.BANNER;
//		}

	@Override
	public void onNoAD(AdError adError) {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_FAILED_TO_LOAD, adError.getErrorMsg());
	}

	@Override
	public void onADReceive() {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_RECEIVE, mBannerId);
	}

	@Override
	public void onADExposure() {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_AD_PRESENT, mBannerId);
	}

	@Override
	public void onADClosed() {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_AD_CLOSED, mBannerId);
	}

	@Override
	public void onADClicked() {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_AD_CLICK, mBannerId);
	}

	@Override
	public void onADLeftApplication() {
		mContext.dispatchStatusEventAsync(ExtensionEvents.BANNER_LEFT_APPLICATION, mBannerId);
	}

	@Override
	public void onADOpenOverlay() {
		mContext.dispatchStatusEventAsync("onADOpenOverlay", mBannerId);
	}

	@Override
	public void onADCloseOverlay() {
		mContext.dispatchStatusEventAsync("onADCloseOverlay", mBannerId);
	}
}

