package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.example.goodbad.TreeNodeArrayAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class TrendingStoryListFragment extends BaseListFragment {
	
	private ArrayList<TreeNode> storyItemList = new ArrayList<TreeNode>();
	private TrendingStoryListFragmentListener listener;
	private TreeNode selectedTrendingItem;
	private ListView lvNodes;
	private TreeNodeArrayAdapter aaNodes;
	
	public interface TrendingStoryListFragmentListener {
		void onSelectedTrendingItem(TreeNode selectedTrendingItem);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		 populateTreeNodes("");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View trendingView = inflater.inflate(R.layout.fragment_trending_list, container, false);
		
		aaNodes = new TreeNodeArrayAdapter(getActivity(), storyItemList, 0);
		lvNodes = (ListView) trendingView.findViewById(R.id.lvTrendingListFragment);
		lvNodes.setAdapter(aaNodes);

		return trendingView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof TrendingStoryListFragmentListener) {
			listener = (TrendingStoryListFragmentListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + "must implement ComposeTweetFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//addNodestoList();
		//addNodestoAdapter(storyList);
		
		lvNodes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				selectedTrendingItem = storyItemList.get(position);
				Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
				
				listener.onSelectedTrendingItem(selectedTrendingItem);
			}
		});		
	}
	
	private void addNodestoList() {
		TreeNode n1 = new TreeNode ();
		n1.setText("This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1 This is story 1");
		storyItemList.add(n1);
		
		TreeNode n2 = new TreeNode ();
		n2.setText("This is story 2");
		storyItemList.add(n2);
		
		TreeNode n3 = new TreeNode ();
		n3.setText("This is story 3");
		storyItemList.add(n3);
		
		TreeNode n4 = new TreeNode ();
		n4.setText("This is story 4");
		storyItemList.add(n4);
		
		TreeNode n5 = new TreeNode ();
		n5.setText("This is story 5");
		storyItemList.add(n5);
		
		TreeNode n6 = new TreeNode ();
		n6.setText("This is story 6");
		storyItemList.add(n6);
		
		TreeNode n7 = new TreeNode ();
		n7.setText("This is story 7");
		storyItemList.add(n7);
		
		TreeNode n8 = new TreeNode ();
		n8.setText("This is story 8");
		storyItemList.add(n8);
		
		TreeNode n9 = new TreeNode ();
		n9.setText("This is story 9");
		storyItemList.add(n9);
		
		TreeNode n10 = new TreeNode ();
		n10.setText("This is story 10");
		storyItemList.add(n10);
	}

	@Override
	public void populateTreeNodes(String max_id) {
		getStories();
	}
	
	private void getStories() {
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parentid", JSONObject.NULL); 
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {			
				if (arg1 == null) {
					aaNodes.addAll((ArrayList<TreeNode>)items);
					storyItemList = (ArrayList<TreeNode>)items;
					for (TreeNode treeRoot: items) {
						Log.d ("DEBUG", "storyid = " + treeRoot.getObjectId());
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
								} else {
									Log.d("debug", "arg1 is not null");
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
