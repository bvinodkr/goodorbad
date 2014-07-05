package com.example.goodbad;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//before initialize
//		ParseObject.registerSubclass(TodoItem.class);
		
		Parse.initialize(this, "nRGrjc1BxXdf1VMDJ67L7QXLr6sBR7pwpmAt99kl", "eUBjW0bnGVSNei7TBCioOsiELPrILcuG46qjf76k");
		

	}
}
