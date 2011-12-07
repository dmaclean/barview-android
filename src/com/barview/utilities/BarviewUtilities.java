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
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.FAVORITES_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.FAVORITES_URL_DEMO;
		
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
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.FAVORITE_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.FAVORITE_URL_DEMO;
		
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
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.NEARBYBARS_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.NEARBYBARS_URL_DEMO;
		
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
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.BARIMAGES_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.BARIMAGES_URL_DEMO;
		
		return BarviewConstants.BARIMAGES_URL;
	}
	
	/**
	 * Get the appropriate Login URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getBarviewLoginURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.BARVIEWLOGIN_URL_DEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.BARVIEWLOGIN_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.BARVIEWLOGIN_URL_DEMO;
		
		return BarviewConstants.BARVIEWLOGIN_URL;
	}
	
	/**
	 * Get the appropriate logout URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getBarviewLogoutURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.BARVIEWLOGOUT_URL_DEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.BARVIEWLOGOUT_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.BARVIEWLOGOUT_URL_DEMO;
		
		return BarviewConstants.BARVIEWLOGOUT_URL;
	}
	
	/**
	 * Get the appropriate events URL for the current Run mode.
	 * 
	 * @return
	 */
	public static String getEventsURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.EVENTS_URL_DEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.EVENTS_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.EVENTS_URL_DEMO;
		
		return BarviewConstants.EVENTS_URL;
	}
	
	public static String getFacebookUpdateURLForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.FBUPDATE_URL_DEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.BVDEV_MODE))
			return BarviewConstants.FBUPDATE_URL_BVDEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.FBUPDATE_URL_DEMO;
		
		return BarviewConstants.FBUPDATE_URL;
	}
}
