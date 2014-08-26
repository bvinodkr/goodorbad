package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeArrayAdapter;

public abstract class BaseListFragment extends Fragment {
	/*private ArrayList<TreeNode> nodesList;
	private TreeNodeArrayAdapter aaNodes;*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//nodesList = new ArrayList<TreeNode> ();		
	}

	/*public void addNodestoAdapter(List<TreeNode> extenderNodesList) {
		//aaNodes.addAll(extenderNodesList);
	}
		
	public TreeNodeArrayAdapter getTreeNodeArrayAdapter() {
		return aaNodes;
	}*/

    
    public abstract void populateTreeNodes (String max_id);

}
