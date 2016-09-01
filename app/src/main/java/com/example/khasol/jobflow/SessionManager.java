package com.example.khasol.jobflow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "JobFlow";

	public static int NotificationID = 0;
	public static String CSRF_VALUE;
	
	// All Shared Preferences Keys
	public static final String IS_LOGIN = "user_IsLoggedIn";
	public static final String KEY_USER_ID ="user_id";
	public static final String USER_NAME = "user_name";
	public static final String User_Password = "user_password";
	
	// Constructor
	@SuppressLint("CommitPrefEdits")
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String user_id, String user_name,String password)
	{
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
				
		//Storing user id in pref
		editor.putString(User_Password, password);
		
		// Storing email in pref
		editor.putString(USER_NAME, user_name);

		// storing user id

		editor.putString(KEY_USER_ID, user_id);

		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			/*Intent i = new Intent(_context, LoginRegisterActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			_context.startActivity(i);*/
		}
		
	}
	
	
//	createLoginSession(String login_source, String user_id, String user_name, 
//			String user_email, String user_fb_id, String user_fb_name, String user_gender,
//			String user_age, String user_image, String user_role )
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_USER_ID ,pref.getString(KEY_USER_ID, null));
		// user email id
		user.put(USER_NAME, pref.getString(USER_NAME, null));
		user.put(User_Password, pref.getString(User_Password, null));
		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
	public void logout(){

		editor.clear();
		editor.commit();
	}

}
