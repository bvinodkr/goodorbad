package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

public class StoryLineListFragment extends BaseListFragment {

	private ArrayList<TreeNode> storyLineNodeList = new ArrayList<TreeNode> ();
	private ListView lvNodes;
	private TreeNodeArrayAdapter aaNodes;
	private TreeNode selectedStory;
	
	
	public void newInstance(TreeNode selectedStory) {
		this.selectedStory = selectedStory;
	}
	
	/*private StoryLineListFragmentListener listener;
	
	public interface StoryLineListFragmentListener {
		void onSelectedTrendingItem();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof StoryLineListFragmentListener) {
			listener = (StoryLineListFragmentListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + "must implement ComposeTweetFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		 //populateTreeNodes("");
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View storyLineView = inflater.inflate(R.layout.fragment_story_line, container, false);
		
		//addNodestoList();
		aaNodes = new TreeNodeArrayAdapter(getActivity(), storyLineNodeList, 1);
		aaNodes.addAll(storyLineNodeList);
		//addNodestoAdapter(storyLineNodeList);
		
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("storyid", selectedStory.getStoryId());
		query.include("user");
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
		
		lvNodes = (ListView) storyLineView.findViewById(R.id.lvStoryLineFragment);
		lvNodes.setAdapter(aaNodes);
		
		return storyLineView;		
	}
	
	public void addParas (List<TreeNode> nodes)
	{
		for (TreeNode n: nodes)
		{
			storyLineNodeList.add(n);
			Log.d ("DEBUG", "addParas called with text = " + n.getText ());
			aaNodes.notifyDataSetChanged();
		}
	}
	
	@Override
	public void populateTreeNodes(String max_id) {
		// TODO Auto-generated method stub
		
	}
	
	private void addNodestoList() {
		TreeNode n1 = new TreeNode ();
		n1.setText("This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1");
		storyLineNodeList.add(n1);
		
		TreeNode n2 = new TreeNode ();
		n2.setText("This is para 2");
		storyLineNodeList.add(n2);
		
		TreeNode n3 = new TreeNode ();
		n3.setText("This is para 3");
		storyLineNodeList.add(n3);
		
		TreeNode n4 = new TreeNode ();
		n4.setText("This is para 4");
		storyLineNodeList.add(n4);
		
		TreeNode n5 = new TreeNode ();
		n5.setText("This is para 5");
		storyLineNodeList.add(n5);
		
		TreeNode n6 = new TreeNode ();
		n6.setText("This is para 6");
		storyLineNodeList.add(n6);
		
		TreeNode n7 = new TreeNode ();
		n7.setText("This is para 7");
		storyLineNodeList.add(n7);
		
		TreeNode n8 = new TreeNode ();
		n8.setText("This is para 8");
		storyLineNodeList.add(n8);
		
		TreeNode n9 = new TreeNode ();
		n9.setText("This is para 9");
		storyLineNodeList.add(n9);
		
		TreeNode n10 = new TreeNode ();
		n10.setText("This is para 10");
		storyLineNodeList.add(n10);
	}

}
