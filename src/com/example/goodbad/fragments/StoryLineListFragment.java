package com.example.goodbad.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;

public class StoryLineListFragment extends BaseListFragment {

	private ArrayList<TreeNode> storyLineNodeList = new ArrayList<TreeNode> ();
	private ListView lvNodes;
	
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
		
		addNodestoList();
		addNodestoAdapter(storyLineNodeList);
		
		lvNodes = (ListView) storyLineView.findViewById(R.id.lvStoryLineFragment);
		lvNodes.setAdapter(getTreeNodeArrayAdapter());
		
		return storyLineView;		
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
