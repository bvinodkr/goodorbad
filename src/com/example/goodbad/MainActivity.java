package com.example.goodbad;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.goodbad.fragments.HomeListFragment;
import com.example.goodbad.fragments.MyListFragment;
import com.example.goodbad.listeners.FragmentTabListener;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs ();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Good/Bad");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Global")
		    .setIcon(R.drawable.ic_global)
		    .setTag("GlobalFragment")
		    .setTabListener(new FragmentTabListener<HomeListFragment>(R.id.flContainer, this,
                        "home", HomeListFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Favorite")
		    .setIcon(R.drawable.ic_favorite)
		    .setTag("FavoriteFragment")
		    .setTabListener(new FragmentTabListener<MyListFragment>(R.id.flContainer, this,
                        "mylist", MyListFragment.class));
		actionBar.addTab(tab2);
	}
}
