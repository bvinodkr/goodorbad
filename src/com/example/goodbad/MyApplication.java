package com.example.goodbad;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//before initialize
		ParseObject.registerSubclass(TreeNode.class);
		 	ParseObject.registerSubclass(Favorites.class);

		Parse.initialize(this, "i6BacXY95FaVkJ8D71BFxZxE8ONXjYfOCpE3oZlt", "IFQWFl3V8CkVofzZ7FNzDG5B5Ud9Ud49a8W0oOun");

//		ParseUser.enableAutomaticUser();
//		ParseUser.getCurrentUser().increment("RunCount");
//		ParseUser.getCurrentUser().saveInBackground();

		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

	 //	 ParseUser.logOut();
		Toast.makeText(this, "Not logged in.", Toast.LENGTH_SHORT).show();
	//	PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
 	}
}
