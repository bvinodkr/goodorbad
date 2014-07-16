package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class TrendingStoryListFragment extends BaseListFragment {
	
	ArrayList<TreeNode> storyList = new ArrayList<TreeNode> ();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		populateTreeNodes("");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		addNodestoList();
//		addNodestoAdapter(storyList);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
			}
				
		});
	}
	
	private void addNodestoList() {
		TreeNode n1 = new TreeNode ();
		n1.setText("This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1");
		storyList.add(n1);
		
		TreeNode n2 = new TreeNode ();
		n2.setText("This is story 2");
		storyList.add(n2);
		
		TreeNode n3 = new TreeNode ();
		n3.setText("This is story 3");
		storyList.add(n3);
		
		TreeNode n4 = new TreeNode ();
		n4.setText("This is story 4");
		storyList.add(n4);
		
		TreeNode n5 = new TreeNode ();
		n5.setText("This is story 5");
		storyList.add(n5);
		
		TreeNode n6 = new TreeNode ();
		n6.setText("This is story 6");
		storyList.add(n6);
		
		TreeNode n7 = new TreeNode ();
		n7.setText("This is story 7");
		storyList.add(n7);
		
		TreeNode n8 = new TreeNode ();
		n8.setText("This is story 8");
		storyList.add(n8);
		
		TreeNode n9 = new TreeNode ();
		n9.setText("This is story 9");
		storyList.add(n9);
		
		TreeNode n10 = new TreeNode ();
		n10.setText("This is story 10");
		storyList.add(n10);
	}

	@Override
	public void populateTreeNodes(String max_id) {
		getStories();
	}
	
	private void getStories() {
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parentid", JSONObject.NULL);
		query.include("user");
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {			
				if (arg1 == null) {
					addNodestoAdapter((ArrayList<TreeNode>)items);
					storyList = (ArrayList<TreeNode>)items;
					for (TreeNode treeRoot: items) {
						Log.d ("DEBUG", "storyid = " + treeRoot.getObjectId());
						if (treeRoot.getUser () != null)
						{
							Log.d ("DEBUG", "user name" + treeRoot.getUser().getUsername());
						}
						else
						{
							Log.d ("DEBUG", "user name null");
						}
						ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
						query.whereEqualTo("storyid", treeRoot.getStoryId());

						query.findInBackground( new FindCallback<TreeNode>() {
							public void done(List<TreeNode> items, ParseException arg1) {					
								if (arg1 == null) {
									//Log.d ("DEBUG", "check parent returned " + items.get(1).getParent().getObjectId());
									TreeNodeAPI api = new TreeNodeAPI();
									TreeNode tree = api.toTree(items);
									Log.d ("DEBUG", "root.children count = " + tree.getChildren().size());
									
									TreeNode siblingRoot = api.getSibling (tree, 1);
									if (siblingRoot == null) {
										Log.d ("DEBUG", "expected root sibling is null");
									}
									
									TreeNode firstChild = tree.getChildren().get(0).getChildren().get(0);
									Log.d ("DEBUG", "first child id " + firstChild.getObjectId());
									
									TreeNode siblingFirstChild = api.getSibling(firstChild, 1);
									if (siblingFirstChild != null) {
										Log.d ("DEBUG", "sibling id " + siblingFirstChild.getObjectId());
									}
									Log.d ("DEBUG", api.getSiblingCount(firstChild) + "");
									
									ArrayList<TreeNode> path = api.getPathContaining(tree.getChildren().get(0));
									for (TreeNode p: path) {
										Log.d ("DEBUG", "- " + p.getObjectId());
									}
									
									path = api.getPathContaining(tree);
									
									for (TreeNode p: path) {
										Log.d ("DEBUG", "- " + p.getObjectId());
									}									
								}
							}
						});
						break;
					}
				}
				else
				{
					Log.d ("DEBUG", "error in getting stories " + arg1.getMessage());
				}
			}
		});
	}
}
