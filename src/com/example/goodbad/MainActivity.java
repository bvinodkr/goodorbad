package com.example.goodbad;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.goodbad.fragments.NewStoryListFragment;
import com.example.goodbad.fragments.TrendingStoryListFragment;
import com.example.goodbad.fragments.TrendingStoryListFragment.TrendingStoryListFragmentListener;
import com.example.goodbad.listeners.FragmentTabListener;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;

public class MainActivity extends ActionBarActivity implements ComposeStoryFragmentListener, TrendingStoryListFragmentListener {

	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private ComposeStoryFragment composeStoryFragment;
	private static boolean mReturnedFromParse = false;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//setBehindContentView(R.layout.activity_main);
		//	addDummyData();
		//setupTabs();

		if(mReturnedFromParse) {
			//launchComposeDialog();
		}
		setupTabs(1);

		Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

		Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

		// Optional - If you don't want to allow Facebook login, you can
		// remove this line (and other related ParseFacebookUtils calls)
		ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

		// Optional - If you don't want to allow Twitter login, you can
		// remove this line (and other related ParseTwitterUtils calls)
		ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
				getString(R.string.twitter_consumer_secret));

	}


	public static void onParseLogin(boolean returnedFromParse) {
		mReturnedFromParse = returnedFromParse;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_items, menu);

		/*MenuItem miActionBarShareIcon = menu.findItem(R.id.miActionBarShareIcon);
		ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(miActionBarShareIcon);
		Log.d("debug ", "is null " + shareActionProvider);
        shareActionProvider.setShareIntent(getDefaultShareIntent());*/
		MenuItem  playMenu = menu.findItem(R.id.miActionBarComposeIcon);
		if( ParseUser.getCurrentUser() != null ) {
			playMenu.setIcon(R.drawable.compose  ) ;
		}	else {playMenu.setIcon(R.drawable.login_icon  ) ;

		}   
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.miActionBarComposeIcon) {
			onComposeIconClick(item);
			return true;
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

			firstPara.setUser(ParseUser.getCurrentUser());
			firstPara.setImageUrl("http://practicalveterinarytips.com/wp-content/uploads/2013/04/suspicious-puppy.jpg");

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
		ParseUser user = ParseUser.getCurrentUser();


		if(ParseUser.getCurrentUser() != null  ) {

			composeStoryFragment = ComposeStoryFragment.newInstance("Compose Story");
			//composeStoryFragment.show(fm, "fragment_compose_tweet");

			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flContainer, composeStoryFragment);
			ft.addToBackStack(null);
			ft.commit();

		}else{
			Intent i = new Intent(MainActivity.this,
					ComposeDispatchActivity.class);
			//	String	username =user.getUsername();
			//	i.putExtra("result", username);

			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);

		}
	}

	private void addDummyData()
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

	private void setupTabs(int tabNo) {
		Log.d("debug ", "crapped out 1");

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Good/Bad");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Log.d("debug ", "crapped out 2");

		actionBar.removeAllTabs();

		Log.d("debug ", "crapped out 3");

		Tab tab1 = actionBar
				.newTab()
				.setText("Trending")
				.setIcon(R.drawable.ic_global)
				.setTag("TrendingFragment")
				.setTabListener(new FragmentTabListener<TrendingStoryListFragment>(R.id.flContainer, this,
						"home", TrendingStoryListFragment.class));
		actionBar.addTab(tab1);	
		actionBar.selectTab(tab1);
		Log.d("debug ", "crapped out 4");

		/*Tab tab2 = actionBar
				.newTab()
				.setText("Favorites")
				.setIcon(R.drawable.ic_favorite)
				.setTag("FavoriteFragment")
				.setTabListener(new FragmentTabListener<MyStoryListFragment>(R.id.flContainer, this,
						"mylist", MyStoryListFragment.class));
		actionBar.addTab(tab2);*/

		Log.d("debug ", "crapped out 5");



		Tab tab3 = actionBar
				.newTab()
				.setText("New")
				.setIcon(R.drawable.ic_favorite)
				.setTag("NewFragment")
				.setTabListener(new FragmentTabListener<NewStoryListFragment>(R.id.flContainer, this,
						"mylist", NewStoryListFragment.class));
		actionBar.addTab(tab3); 
		Log.d("debug ", "crapped out 6");

		switch(tabNo) {
		case 1:
			actionBar.selectTab(tab1);
			break;
		/*case 2:
			actionBar.selectTab(tab2);
			break;*/
		case 3:
			actionBar.selectTab(tab3);
			break;
		default:
			break;
		}	
		Log.d("debug ", "crapped out 7");

	}

	@Override
	public void onFinishComposeDialog(String composeData, String composeStoryTitle, String imageUrl, boolean fromPost) {
		if(!fromPost) {

			setupTabs(1);
		} else {
			getSupportFragmentManager().beginTransaction().remove(composeStoryFragment).commit();

			/*
			 * put data into parse here
			 */

			final TreeNode root = new TreeNode (composeData, null);

			root.setUser(ParseUser.getCurrentUser());
			root.setImageUrl(imageUrl);
			root.setTitle(composeStoryTitle);
			root.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException arg0) {
					root.setStoryId(root.getObjectId());
				}
			});

			setupTabs(3);

			/*NewStoryListFragment newStoryFragment = new NewStoryListFragment();
			newStoryFragment.newInstance(root);
			//composeStoryFragment.show(fm, "fragment_compose_tweet");

			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flContainer, newStoryFragment);
			ft.addToBackStack(null);
			ft.commit();*/
		}
	}

	/*
	@Override
	public void onSelectedTrendingItem(TreeNode selectedTrendingItem) {

		StoryLineListFragment storyLineFragment = new StoryLineListFragment();
		storyLineFragment.newInstance(selectedTrendingItem);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flContainer, storyLineFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	 */

	@Override
	public void onSelectedTrendingItem (TreeNode selectedTrendingItem)
	{

		 Intent i = new Intent (this, StoryLineViewActivity.class);
		 //pass data
		 i.putExtra("storyId", selectedTrendingItem.getStoryId());
		 i.putExtra("title", selectedTrendingItem.getTitle());
		 startActivity(i);
	}
}
