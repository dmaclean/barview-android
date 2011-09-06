package com.barview.utilities;

import com.barview.constants.BarviewConstants;

public class BarviewUtilities {
	/**
	 * Get the appropriate Favorites (for GET calls) URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getFavoritesURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.FAVORITES_URL_DEV;
		
		return BarviewConstants.FAVORITES_URL;
	}
	
	/**
	 * Get the appropriate Favorites (for POST/DELETE calls) URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getFavoriteURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.FAVORITE_URL_DEV;
		
		return BarviewConstants.FAVORITE_URL;
	}
	
	/**
	 * Get the appropriate NearbyBars (for GET calls) URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getNearbyBarsURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.NEARBYBARS_URL_DEV;
		
		return BarviewConstants.NEARBYBARS_URL;
	}
	
	/**
	 * Get the appropriate BarImages (for GET calls) URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getBarImageURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.BARIMAGES_URL_DEV;
		
		return BarviewConstants.BARIMAGES_URL;
	}
	
	public static String getBarviewLoginURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.BARVIEWLOGIN_URL_DEV;
		
		return BarviewConstants.BARVIEWLOGIN_URL;
	}
	
	public static String getBarviewLogoutURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.BARVIEWLOGOUT_URL_DEV;
		
		return BarviewConstants.BARVIEWLOGOUT_URL;
	}
}
