package com.example.goodbad;

import android.support.v4.app.FragmentManager;
 import android.support.v4.app.FragmentTransaction;
 import android.os.Bundle;

import com.example.goodbad.fragments.ComposeStoryFragment;
import com.parse.ui.ParseLoginDispatchActivity;
 
public class ComposeDispatchActivity extends ParseLoginDispatchActivity {
 		  @Override
		  protected Class<?> getTargetClass() {
 		 	  
 			  MainActivity.onParseLogin(true);
 			  
		    return 	MainActivity.class;
		  }
// 		 private void launchComposeDialog() {
//				FragmentManager fm = getFragmentManager();
//
//				ComposeStoryFragment composeStoryFragment = ComposeStoryFragment.newInstance("Compose Story");
//				composeStoryFragment.show(fm, "fragment_compose_tweet");
//			}
}
