package net.ekuwang.cordova.plugin.statusbar;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.WindowManager.LayoutParams;

public class StatusbarTransparent extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
		// Create/Set toolbar as actionbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		// Check if the version of Android is Lollipop or higher
		if (Build.VERSION.SDK_INT >= 21) {
		
		    // Set the status bar to dark-semi-transparentish
		    	cordova.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		
		    // Set paddingTop of toolbar to height of status bar.
		    // Fixes statusbar covers toolbar issue
		    toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
		}
		// A method to find height of the status bar
		public int getStatusBarHeight() {
		    int result = 0;
		    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		    if (resourceId > 0) {
		        result = getResources().getDimensionPixelSize(resourceId);
		    }
		    return result;
		}
		// grab the correct methods
		if(action.equalsIgnoreCase("enable")) {
			if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						cordova.getActivity().getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
					}
				});
				callback.success();
			} else {
				callback.error("not supported");
			}
			return true;
		} else if(action.equalsIgnoreCase("disable")) {
			if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						cordova.getActivity().getWindow().clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
					}
				});
				callback.success();
			} else {
				callback.error("not supported");
			}
			return true;
		} else {
			callback.error("Unknown Action: " + action);
			return false;
		}
	}
}
