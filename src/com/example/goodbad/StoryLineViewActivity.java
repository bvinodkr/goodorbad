package com.example.goodbad;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.goodbad.fragments.StoryLineListFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class StoryLineViewActivity extends FragmentActivity {

	TreeNode root;
	int numEndings;
	List<TreeNode> leafNodes;
	ListView lvNodes;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_line_view);
		String storyId = getIntent().getStringExtra("storyId");
		Log.d ("DEBUG", "story id in storyline view =" + storyId);
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("storyid", storyId);

		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					TreeNodeAPI api = new TreeNodeAPI ();
					TreeNode root = api.toTree(items);
					Log.d ("DEBUG", "root object id" + root.getObjectId());
					List<TreeNode> leafNodes = api.getLeafNodes(items);
					Log.d ("DEBUG", "num of lead nodes " + leafNodes.size());
					addParas (api.getPathContaining(leafNodes.get(0)));
				}
				else
				{
					Log.d ("DEBUG", "error = " + arg1.getMessage());
				}
			}
		});
		
		lvNodes = (ListView)findViewById(R.id.listView1);
		items = new ArrayList<String> ();
		itemsAdapter = new ArrayAdapter<String> (this,
				android.R.layout.simple_list_item_1, items);
		lvNodes.setAdapter(itemsAdapter);
	}
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_line_main);

		String title = getIntent().getStringExtra("title");
		ActionBar actionBar = getActionBar();
		
		if (title != null && !title.isEmpty())
		{	getActionBar().setTitle(title);
			String s ="<font color='#FFFFFF'>"+ actionBar.getTitle().toString()+ "</font>";
			actionBar.setTitle(Html.fromHtml((s)));
		}
		else
		{
			actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Story Tellers</font>"));
		}
//		LinearLayout llFollowers;
//		llFollowers= (LinearLayout) findViewById(R.id.llFollowers);
//		llFollowers.setVisibility(0);
				//actionBar.setTitle("Story Tellers");
	//	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		 actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

		 
		String storyId = getIntent().getStringExtra("storyId");
		Log.d ("DEBUG", "story id in storyline view =" + storyId);
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("storyid", storyId);
		query.include("user");
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					TreeNodeAPI api = new TreeNodeAPI ();
					root = api.toTree(items);

					Log.d ("DEBUG", "root object id" + root.getObjectId());
					leafNodes = api.getLeafNodes(items);
					numEndings = leafNodes.size();
					Log.d ("DEBUG", "num of leaf nodes " + leafNodes.size());

					ViewPager vpPager = (ViewPager)findViewById(R.id.vpPager);
					ContentPagerAdapter adapter = new ContentPagerAdapter(getSupportFragmentManager());
					vpPager.setAdapter(adapter);
				}
				else
				{
					Log.d ("DEBUG", "error = " + arg1.getMessage());
				}
			}
		});
	
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
			playMenu.setIcon(R.drawable.ic_read  ) ;
		}	else {playMenu.setIcon(R.drawable.ic_read  ) ;

		}   
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.miActionBarComposeIcon) {
		//	onReadClick(item);
			LinearLayout llFollowers;
			llFollowers= (LinearLayout) findViewById(R.id.llFollowers);
			llFollowers.setVisibility(0);
			return true;
		} 

		return super.onOptionsItemSelected(item);
	}
//	private void onReadClick(MenuItem item) {
// 		 Intent i = new Intent (this, ViewableStoryActivity.class);
//		 //pass data
//		 i.putExtra("storyId", "l7o9gDTsIu");
//		 i.putExtra("title", "The Horse");
//		 startActivity(i);
//
//	}
	public class ContentPagerAdapter extends FragmentPagerAdapter {

		public ContentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			if (leafNodes != null && leafNodes.size() > position)
			{
				StoryLineListFragment storyLineFragment = new StoryLineListFragment();
				storyLineFragment.newInstance(leafNodes.get(position), root);
				return storyLineFragment;
			}
			else
			{
				Log.d ("DEBUG", "leaf node is null");
			}
			return null;
		}

		@Override
		public int getCount() {
			if (leafNodes != null)
			{
				return leafNodes.size();
			}
			else
			{
				return 0;
			}
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
 			return "Story version " + (position + 1);
 		}
		
	}
	/*
	public void addParas (List<TreeNode> nodes)
	{
		for (TreeNode n: nodes)
		{
			items.add(n.getText());
			Log.d ("DEBUG", "addParas called with text = " + n.getText ());
			itemsAdapter.notifyDataSetChanged();
		}
	}
	*/
}
