package com.example.goodbad;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//before initialize
		ParseObject.registerSubclass(TreeNode.class);
		
		Parse.initialize(this, "i6BacXY95FaVkJ8D71BFxZxE8ONXjYfOCpE3oZlt", "IFQWFl3V8CkVofzZ7FNzDG5B5Ud9Ud49a8W0oOun");
		
	} 
}
