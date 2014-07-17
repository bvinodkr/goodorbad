package com.example.goodbad;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.goodbad.fragments.StoryLineListFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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
		if (title != null && !title.isEmpty())
		{
			getActionBar().setTitle(title);
		}
		else
		{
			getActionBar().setTitle("Story Browse");
		}
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
				storyLineFragment.newInstance(leafNodes.get(position));
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
