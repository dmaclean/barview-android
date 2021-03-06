package com.barview.constants;

public class BarviewConstants {
	public static final String LOCALHOST			= "10.0.2.2";
	
	public static final String FACEBOOK_APP_ID_DEV	= "177771455596726";
	public static final String FACEBOOK_APP_ID_DEMO	= "261480677235705";
	public static final String FACEBOOK_APP_ID_TEST	= "224918730913535";
	public static final String FACEBOOK_APP_ID_PROD	= "187483778004162";
	
	public static final String DEV_MODE				= "DEV";		// LOCALHOST
	public static final String BVDEV_MODE			= "BVDEV";
	public static final String TEST_MODE			= "TEST";
	public static final String DEMO_MODE			= "DEMO";
	public static final String PROD_MODE			= "PROD";
	public static	    String RUN_MODE				= DEMO_MODE;
	
	// LOCALHOST
	public static final String FAVORITES_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/favorites";
	public static final String FAVORITE_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/favorite";		// For POST and DELETE
	public static final String BARIMAGES_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/barimage";
	public static final String NEARBYBARS_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/nearbybars";
	public static final String BARVIEWLOGIN_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/mobilelogin";
	public static final String BARVIEWLOGOUT_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/mobilelogin/logout";
	public static final String EVENTS_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/events";
	public static final String FBUPDATE_URL_DEV = "http://" + LOCALHOST + ":8888/barview/index.php?/rest/fbupdate";
	
	// DEV
	public static final String FAVORITES_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/favorites";
	public static final String FAVORITE_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/favorite";		// For POST and DELETE
	public static final String BARIMAGES_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/barimage";
	public static final String NEARBYBARS_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/nearbybars";
	public static final String BARVIEWLOGIN_URL_BVDEV = "http://dev.bar-view.com/index.php?/mobilelogin";
	public static final String BARVIEWLOGOUT_URL_BVDEV = "http://dev.bar-view.com/index.php?/mobilelogin/logout";
	public static final String EVENTS_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/events";
	public static final String FBUPDATE_URL_BVDEV = "http://dev.bar-view.com/index.php?/rest/fbupdate";
	
	// TEST
	public static final String FAVORITES_URL_TEST = "http://test.bar-view.com/index.php?/rest/favorites";
	public static final String FAVORITE_URL_TEST = "http://test.bar-view.com/index.php?/rest/favorite";		// For POST and DELETE
	public static final String BARIMAGES_URL_TEST = "http://test.bar-view.com/index.php?/rest/barimage";
	public static final String NEARBYBARS_URL_TEST = "http://test.bar-view.com/index.php?/rest/nearbybars";
	public static final String BARVIEWLOGIN_URL_TEST = "http://test.bar-view.com/index.php?/mobilelogin";
	public static final String BARVIEWLOGOUT_URL_TEST = "http://test.bar-view.com/index.php?/mobilelogin/logout";
	public static final String EVENTS_URL_TEST = "http://test.bar-view.com/index.php?/rest/events";
	public static final String FBUPDATE_URL_TEST = "http://test.bar-view.com/index.php?/rest/fbupdate";
	
	// DEMO
	public static final String FAVORITES_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/favorites";
	public static final String FAVORITE_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/favorite";		// For POST and DELETE
	public static final String BARIMAGES_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/barimage";
	public static final String NEARBYBARS_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/nearbybars";
	public static final String BARVIEWLOGIN_URL_DEMO = "http://demo.bar-view.com/index.php?/mobilelogin";
	public static final String BARVIEWLOGOUT_URL_DEMO = "http://demo.bar-view.com/index.php?/mobilelogin/logout";
	public static final String EVENTS_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/events";
	public static final String FBUPDATE_URL_DEMO = "http://demo.bar-view.com/index.php?/rest/fbupdate";
	
	// PROD
	public static final String FAVORITES_URL = "http://bar-view.com/index.php?/rest/favorites";
	public static final String FAVORITE_URL = "http://bar-view.com/index.php?/rest/favorite";		// For POST and DELETE
	public static final String BARIMAGES_URL = "http://bar-view.com/index.php?/rest/barimage";
	public static final String NEARBYBARS_URL = "http://bar-view.com/index.php?/rest/nearbybars";
	public static final String BARVIEWLOGIN_URL = "http://bar-view.com/index.php?/mobilelogin";
	public static final String BARVIEWLOGOUT_URL = "http://bar-view.com/index.php?/mobilelogin/logout";
	public static final String EVENTS_URL = "http://bar-view.com/index.php?/rest/events";
	public static final String FBUPDATE_URL = "http://bar-view.com/index.php?/rest/fbupdate";
	
	// Favorite constants for the XML
	public static final String FAVORITE_AGGREGATE	= "favorite";
	public static final String FAVORITE_BAR_ID		= "bar_id";
	public static final String FAVORITE_ADDRESS		= "address";
	public static final String FAVORITE_NAME		= "name";
	
	// BarImage constants for the XML
	public static final String BARIMAGE_AGGREGATE	= "barimage";
	public static final String BARIMAGE_BAR_ID		= "bar_id";
	public static final String BARIMAGE_IMAGE		= "image";
	
	// Nearby bar constants for the XML
	public static final String NEARBY_BAR_AGGREGATE	= "bar";
	public static final String NEARBY_BAR_ID		= "bar_id";
	public static final String NEARBY_BAR_NAME		= "name";
	public static final String NEARBY_BAR_ADDRESS	= "address";
	public static final String NEARBY_BAR_LAT		= "lat";
	public static final String NEARBY_BAR_LNG		= "lng";
	
	// Deals/events constants for the XML
	public static final String EVENTS_AGGREGATE		= "events";
	public static final String EVENT_AGGREGATE		= "event";
	public static final String EVENT_BARNAME		= "bar";
	public static final String EVENT_DETAIL			= "detail";
	
	// REST stuff
	public static final String REST_USER_ID			= "User_id";
	
	// Barview Mobile login
	public static final String BARVIEW_LOGIN		= "Login";
	public static final String BARVIEW_LOGOUT		= "Logout";
	public static final String BARVIEW_USERNAME		= "BV_USERNAME";
	public static final String BARVIEW_PASSWORD		= "BV_PASSWORD";
	public static final String BARVIEW_TOKEN_HEADER	= "BV_TOKEN";
	public static final String BARVIEW_USER			= "user";
	public static final String BARVIEW_FIRST_NAME	= "firstname";
	public static final String BARVIEW_LAST_NAME	= "lastname";
	public static final String BARVIEW_EMAIL		= "email";
	public static final String BARVIEW_DOB			= "dob";
	public static final String BARVIEW_CITY			= "city";
	public static final String BARVIEW_STATE		= "state";
	public static final String BARVIEW_TOKEN		= "token";
	
	// Extras parameters
	public static final String BAR_ID				= "bar_id";
	public static final String BAR_NAME				= "bar_name";
	
	public static final int GEOPOINT_MULT			= 1000000;
	
	public static final int BARIMAGE_REFRESH_TIME	= 5000;		// Time in milliseconds
	
	public static final String LOGIN_TYPE			= "Login-Type";
	public static final String LOGIN_TYPE_FACEBOOK	= "Facebook";
	public static final String LOGIN_TYPE_BARVIEW	= "Barview";
	
	public static final String PREFS_NAME			= "Barview";
}
