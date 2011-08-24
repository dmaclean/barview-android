package com.barview;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.barview.utilities.FacebookUtility;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

public class FacebookActivity extends Activity {
	
	Facebook facebook = FacebookUtility.getFacebook();
	
	FacebookActivity activity = this;
	
	Button loginButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook);
		
		loginButton = (Button) findViewById(R.id.fblogin);
		loginButton.setText( (facebook.isSessionValid()) ? R.string.logout : R.string.login );
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/*
				 * We're logged into Facebook, so this click will log us out
				 * and change the button text back to "Log in".
				 */
				if(facebook.isSessionValid()) {
					try {
						facebook.logout(activity);
						loginButton.setText(R.string.login);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				/*
				 * We're currently logged out of Facebook, so this click will
				 * bring up the dialog window to log us in and, if successful,
				 * change the button text to "Log out".
				 */
				else {
					facebook.authorize(activity, new DialogListener() {
			            public void onComplete(Bundle values) {
			            	Log.i(FacebookActivity.class.getName(), "Logged into Facebook and got user id of " + 
			            			FacebookUtility.getAttribute(FacebookUtility.FB_ID));
			            	loginButton.setText(R.string.logout);
			            }
	
			            public void onFacebookError(FacebookError error) {
			            	showFacebookErrorToast();
			            	Log.e(FacebookActivity.class.getName(), error.getMessage());
			            	error.printStackTrace();
			            }
	
			            public void onError(DialogError e) {
			            	showFacebookErrorToast();
			            	Log.e(FacebookActivity.class.getName(), e.getMessage());
			            	e.printStackTrace();
			            }
	
			            public void onCancel() {
			            	Log.i(FacebookActivity.class.getName(), "Authorization request was cancelled.");
			            }
					});
				}
			}
		});
		
	}
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    private void showFacebookErrorToast() {
    	Toast toast = Toast.makeText(this, "Unable to log into Facebook.  Please try again later.", Toast.LENGTH_SHORT);
    	toast.show();
    }
}
