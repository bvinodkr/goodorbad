package com.example.goodbad.fragments;

import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;

import com.example.goodbad.TreeNode;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class HomeListFragment extends TreeNodeListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		populateTreeNodes ("");
	}
	
	private void getStories ()
	{
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parent", JSONObject.NULL);
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					addAll (items);
				}
			}
		});
	}

	@Override
	public void populateTreeNodes(String max_id) {
		getStories ();
		
	}
}
