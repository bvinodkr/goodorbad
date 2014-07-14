package com.example.goodbad;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.goodbad.fragments.ComposeStoryFragment;
import com.example.goodbad.fragments.ComposeStoryFragment.ComposeStoryFragmentListener;
import com.example.goodbad.fragments.MyStoryListFragment;
import com.example.goodbad.fragments.TrendingStoryListFragment;
import com.example.goodbad.listeners.FragmentTabListener;

public class MainActivity extends ActionBarActivity implements ComposeStoryFragmentListener {

	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//setBehindContentView(R.layout.activity_main);
		setupTabs ();

		//		SlidingMenu sm = getSlidingMenu();
		//		sm.setShadowWidthRes(R.dimen.shadow_width);
		//		sm.setShadowDrawable(R.drawable.shadow);
		//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//		sm.setFadeDegree(0.35f);
		//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_items, menu);

		/*MenuItem miActionBarShareIcon = menu.findItem(R.id.miActionBarShareIcon);
		ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(miActionBarShareIcon);
		Log.d("debug ", "is null " + shareActionProvider);
        shareActionProvider.setShareIntent(getDefaultShareIntent());*/

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.miPopUpIcon:
				onPopUpIconCick(item);
				return true;
			case R.id.miActionBarComposeIcon:
				onComposeIconClick(item);
				return true;
			case R.id.miPostStoryIcon:
				// Not implemented here
				return false;
			default:
				break;
		}

	    return super.onOptionsItemSelected(item);
	}


	public void onPopUpIconCick(MenuItem mi) {

		// Inflate the popup_layout.xml		
		View popUpView = getLayoutInflater().inflate(R.layout.pop_up_layout, null);

		// Creating the PopupWindow
		final PopupWindow popupWindow = new PopupWindow(this);
		popupWindow.setContentView(popUpView);
		popupWindow.setWidth(330);
		popupWindow.setHeight(170);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);

		/*// Clear the default translucent background
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(getCurrentFocus());*/
		

		// Displaying the popup at the specified location, + offsets.
		popupWindow.showAtLocation(popUpView,  Gravity.NO_GRAVITY, 100, 110);
		
		populatePopUpWindowItems();

		GridView gridview = (GridView) popUpView.findViewById(R.id.gvPopUp);		
		gridview.setAdapter(new PopUpImageAdapter(this, popUpWindowItemList));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getBaseContext(), "" + position, Toast.LENGTH_SHORT).show();
			}
		});

		/*View miPopUp = findViewById(R.id.miPopUp); // SAME ID AS MENU ID
	    PopupMenu popupMenu = new PopupMenu(this, miPopUp); 
	    popupMenu.getMenuInflater().inflate(R.menu.popup_items, popupMenu.getMenu());
	    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	        public boolean onMenuItemClick(MenuItem item) {
	            switch (item.getItemId()) {
	            case R.id.menu_keyword:
	              Toast.makeText(MainActivity.this, "Keyword!", Toast.LENGTH_SHORT).show();
	              return true;
	            case R.id.menu_popularity:
	              Toast.makeText(MainActivity.this, "Popularity!", Toast.LENGTH_SHORT).show();
	              return true;
	            default:
	              return false;
	            }
	        }
	    });
	    popupMenu.show();*/
	}
	
	private void populatePopUpWindowItems() {
		popUpWindowItemList.clear();
		popUpWindowItemList.add(new PopUpWindowItem("Gallery", R.drawable.gallery));
		popUpWindowItemList.add(new PopUpWindowItem("Photo", R.drawable.camera));
		popUpWindowItemList.add(new PopUpWindowItem("Video", R.drawable.video));
		popUpWindowItemList.add(new PopUpWindowItem("Audio", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
	}

	/*private Intent getDefaultShareIntent(){
    Intent intent = new Intent(Intent.ACTION_SEND);       
    intent.setType("image/*");
    return intent;
	}*/

	

	public void onComposeIconClick(MenuItem mi) {
		launchComposeDialog();
	} 

	private void launchComposeDialog() {
		FragmentManager fm = getSupportFragmentManager();

		ComposeStoryFragment composeStoryFragment = ComposeStoryFragment.newInstance("Compose Story");
		composeStoryFragment.show(fm, "fragment_compose_tweet");
	}

	private void setupTabs() { 
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Good/Bad");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Trending")
				.setIcon(R.drawable.ic_global)
				.setTag("GlobalFragment")
				.setTabListener(new FragmentTabListener<TrendingStoryListFragment>(R.id.flContainer, this,
						"home", TrendingStoryListFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Favorites")
				.setIcon(R.drawable.ic_favorite)
				.setTag("FavoriteFragment")
				.setTabListener(new FragmentTabListener<MyStoryListFragment>(R.id.flContainer, this,
						"mylist", MyStoryListFragment.class));
		actionBar.addTab(tab2);

		/*Tab tab3 = actionBar
			    .newTab()
			    .setText("New")
			    .setIcon(R.drawable.ic_favorite)
			    .setTag("FavoriteFragment")
			    .setTabListener(new FragmentTabListener<MyStoryListFragment>(R.id.flContainer, this,
	                        "mylist", MyStoryListFragment.class));
			actionBar.addTab(tab3); */
	}

	@Override
	public void onFinishComposeDialog() {
		// TODO Auto-generated method stub

	}
}
