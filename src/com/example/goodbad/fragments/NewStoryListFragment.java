package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.example.goodbad.TreeNodeArrayAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class NewStoryListFragment extends BaseListFragment {
	
	private ArrayList<TreeNode> newStoryItemList = new ArrayList<TreeNode>();
	//private TrendingStoryListFragmentListener listener;	
	private ListView lvNodes;
	private TreeNodeArrayAdapter aaNodes;
	
	/*public interface TrendingStoryListFragmentListener {
		void onSelectedTrendingItem(TreeNode selectedTrendingItem);
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		 populateTreeNodes("");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View newStoriesView = inflater.inflate(R.layout.fragment_story_line, container, false);
		
		aaNodes = new TreeNodeArrayAdapter(getActivity(), newStoryItemList, 1);
			
		//addNodestoAdapter(storyLineNodeList);
		
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("storyid", "l7o9gDTsIu");

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
		
		aaNodes.addAll(newStoryItemList);
		lvNodes = (ListView) newStoriesView.findViewById(R.id.lvStoryLineFragment);
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
	
	public void addParas(List<TreeNode> nodes)
	{
		for (TreeNode n: nodes)
		{
			newStoryItemList.add(n);
			Log.d ("DEBUG", "addParas called with text = " + n.getText ());
			aaNodes.notifyDataSetChanged();
		}
	}

	@Override
	public void populateTreeNodes(String max_id) {
		// TODO Auto-generated method stub
		
	}
}
