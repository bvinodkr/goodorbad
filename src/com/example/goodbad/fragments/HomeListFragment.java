package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class HomeListFragment extends BaseListFragment {
	
	List<TreeNode> stories;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		populateTreeNodes ("");
	}
	
	private void getStories ()
	{
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parentid", JSONObject.NULL);
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					addNodestoAdapter (items);
					stories = items;
					for (TreeNode treeRoot: items)
					{
						Log.d ("DEBUG", "storyid = " + treeRoot.getObjectId());
						ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
						query.whereEqualTo("storyid", treeRoot.getStoryId());

						query.findInBackground( new FindCallback<TreeNode>() {

							public void done(List<TreeNode> items, ParseException arg1) {
								// TODO Auto-generated method stub
								if (arg1 == null)
								{
//									Log.d ("DEBUG", "check parent returned " + items.get(1).getParent().getObjectId());
									TreeNodeAPI api = new TreeNodeAPI ();
									TreeNode tree = api.toTree(items);
									Log.d ("DEBUG", "root.children count = " + tree.getChildren().size());
									TreeNode siblingRoot = api.getSibling (tree, 1);
									if (siblingRoot == null)
									{
										Log.d ("DEBUG", "expected root sibling is null");
									}
									TreeNode firstChild = tree.getChildren().get(0).getChildren().get(0);
									Log.d ("DEBUG", "first child id " + firstChild.getObjectId());
									TreeNode siblingFirstChild = api.getSibling(firstChild, 1);
									if (siblingFirstChild != null)
									{
										Log.d ("DEBUG", "sibling id " + siblingFirstChild.getObjectId());
									}
									Log.d ("DEBUG", api.getSiblingCount(firstChild) + "");
									
									ArrayList<TreeNode> path = api.getPathContaining(tree.getChildren().get(0));
									for (TreeNode p: path)
									{
										Log.d ("DEBUG", "- " + p.getObjectId());
									}
									 path = api.getPathContaining(tree);
									for (TreeNode p: path)
									{
										Log.d ("DEBUG", "- " + p.getObjectId());
									}
									
								}
							}
						});
						break;
					}

				}
			}
		});
	}

	@Override
	public void populateTreeNodes(String max_id) {
		getStories ();
	}
	
	
}
