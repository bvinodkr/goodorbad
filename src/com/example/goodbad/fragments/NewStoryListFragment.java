package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.example.goodbad.TreeNodeArrayAdapter;
import com.example.goodbad.fragments.TrendingStoryListFragment.TrendingStoryListFragmentListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class NewStoryListFragment extends BaseListFragment {
	
	private ArrayList<TreeNode> newStoryItemList = new ArrayList<TreeNode>();
	//private TrendingStoryListFragmentListener listener;	
	private ListView lvNodes;
	private TreeNodeArrayAdapter aaNodes;
	private TreeNode mAddedTreeNode;
	private TreeNode selectedTrendingItem;
	private ArrayList<TreeNode> storyItemList = new ArrayList<TreeNode>();
	private TrendingStoryListFragmentListener listener;

	/*public interface TrendingStoryListFragmentListener {
		void onSelectedTrendingItem(TreeNode selectedTrendingItem);
	}*/
	public interface TrendingStoryListFragmentListener {
		void onSelectedTrendingItem(TreeNode selectedTrendingItem);
	}
	public NewStoryListFragment newInstance(TreeNode treeNode) {
		
		mAddedTreeNode = treeNode;
		
		return null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		 populateTreeNodes("");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View newStoriesView = inflater.inflate(R.layout.fragment_new_list, container, false);
		
		aaNodes = new TreeNodeArrayAdapter(getActivity(), newStoryItemList, 3);
		
		lvNodes = (ListView) newStoriesView.findViewById(R.id.lvNewListFragment);
		lvNodes.setAdapter(aaNodes);

		return newStoriesView;
	}
	
	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof TrendingStoryListFragmentListener) {
			listener = (TrendingStoryListFragmentListener) activity;
		} else { 
			throw new ClassCastException(activity.toString() + "must implement NewStoryListFragment");
		}
	}
 
	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}*/ 
	
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
				//Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
				
				listener.onSelectedTrendingItem(selectedTrendingItem);
			}
		});		
	}
	
	
	@Override
	public void populateTreeNodes(String max_id) {
		getStories();
	}

	private void getStories() {
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		//query.whereGreaterThanOrEqualTo("createdAt", new Date(System.currentTimeMillis() - 36*60*60*1000l));
		query.orderByDescending("createdAt");
		query.whereEqualTo("parentid", JSONObject.NULL);
		query.include("user");

		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {			
				if (arg1 == null) {
					aaNodes.addAll((ArrayList<TreeNode>)items);
					newStoryItemList = (ArrayList<TreeNode>)items;
					
					for (TreeNode treeRoot: items) {
						Log.d ("DEBUG", "storyid = " + treeRoot.getObjectId());
						if (treeRoot.getUser () != null)
						{
							Log.d ("DEBUG", "user name " + treeRoot.getUser().getUsername());
						}
						else
						{
							Log.d ("DEBUG", "user name null");
						}
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
