package com.example.goodbad;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class StoryLineViewActivity extends Activity {

	ListView lvNodes;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	
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
			}
		});
		
		lvNodes = (ListView)findViewById(R.id.listView1);
		items = new ArrayList<String> ();
		itemsAdapter = new ArrayAdapter<String> (this,
				android.R.layout.simple_list_item_1, items);
		lvNodes.setAdapter(itemsAdapter);
	}
	
	public void addParas (List<TreeNode> nodes)
	{
		for (TreeNode n: nodes)
		{
			items.add(n.getText());
			Log.d ("DEBUG", "addParas called with text = " + n.getText ());
			itemsAdapter.notifyDataSetChanged();
		}
	}
}
