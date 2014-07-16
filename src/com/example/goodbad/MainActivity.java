package com.example.goodbad;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import com.example.goodbad.fragments.StoryLineListFragment;
import com.example.goodbad.fragments.TrendingStoryListFragment;
import com.example.goodbad.fragments.TrendingStoryListFragment.TrendingStoryListFragmentListener;
import com.example.goodbad.listeners.FragmentTabListener;
import com.parse.ParseException;

import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends ActionBarActivity implements ComposeStoryFragmentListener, TrendingStoryListFragmentListener {

	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//setBehindContentView(R.layout.activity_main);
		addDummyData();
		setupTabs();

		//		SlidingMenu sm = getSlidingMenu();
		//		sm.setShadowWidthRes(R.dimen.shadow_width);
		//		sm.setShadowDrawable(R.drawable.shadow);
		//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//		sm.setFadeDegree(0.35f);
		//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
/*		ParseObject testObj = new ParseObject ("testingParse");
		testObj.put ("test", "foo");
		testObj.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				if (arg0 == null)
				{
					Log.d ("DEBUG", "save succeeded");
				}
				else
				{
					Log.d ("DEBUG", "error in save " + arg0.getMessage()); 
				}
				
			}
		});
		*/
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

	private void addStory(String x)
	{
		TreeNode root = new TreeNode (x  + " , good-root", null);
		


		//		Log.d("DEBUG", root.getObjectId());
		try {
			root.save ();
			root.setStoryId(root.getObjectId());

			TreeNodeAPI api = new TreeNodeAPI ();
			TreeNode firstPara = api.addChild(root, x + " bad-para1");
			
			//save both root and firstPara
			root.save();
			firstPara.save();
			
			TreeNode secondPara = api.addChild(firstPara, x + " good-para2");
			
			firstPara.save();
			secondPara.save();
			
			TreeNode secondParav1 = api.addChild(firstPara, x + " good-para2-v1");
			secondParav1.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private void addDummyData ()
	{
		/*addStory ("story1");
		addStory ("story2");
		addStory("story3");
	
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parent", JSONObject.NULL);
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{

					TreeNode item = items.get(0);
					Toast.makeText(MainActivity.this, items.size() + "", Toast.LENGTH_SHORT).show ();
					TreeNode firstPara = new TreeNode ("This is second line, bad", item);
					firstPara.setStoryId(item);
					firstPara.saveInBackground();

				}
				else
				{
					Toast.makeText(MainActivity.this, "error:" + arg1.getMessage(), Toast.LENGTH_SHORT).show ();
				}
			}
		});*/
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
				.setTag("TrendingFragment")
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

	@Override
	public void onSelectedTrendingItem(TreeNode selectedTrendingItem) {
		/*FragmentManager fm = getSupportFragmentManager();
		TweetListFragment tweetListFragment = (TweetListFragment) fm.findFragmentById(R.id.flContainer);
		
		tweetListFragment.clearAdapter(); 
		tweetListFragment.addTweetsToAdapterAtIndex(composedTweet, 0);*/
		
		StoryLineListFragment storyLineFragment = new StoryLineListFragment();
		storyLineFragment.newInstance(selectedTrendingItem);
		/*Bundle args = new Bundle();
		args.putInt(StoryLineListFragment.ARG_POSITION, position);
		newFragment.setArguments(args);*/
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flContainer, storyLineFragment);
		ft.addToBackStack(null);
		ft.commit();
	}
}
