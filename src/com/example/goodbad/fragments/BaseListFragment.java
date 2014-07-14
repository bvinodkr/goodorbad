package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeArrayAdapter;

public abstract class BaseListFragment extends Fragment {
	private ArrayList<TreeNode> nodesList;
	private TreeNodeArrayAdapter aaNodes;
	private ListView lvNodes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		nodesList = new ArrayList<TreeNode> ();
		aaNodes = new TreeNodeArrayAdapter(getActivity(), nodesList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nodes_list, container, false);

		lvNodes = (ListView)v.findViewById(R.id.lvNodes);
		lvNodes.setAdapter(aaNodes);

	
		return v;
	}
	

	public void addNodestoAdapter(ArrayList<TreeNode> extenderNodesList) {
		aaNodes.addAll(extenderNodesList);
	}
	
	public ListView getListView ()
	{
		return lvNodes;
	}
    
    public abstract void populateTreeNodes (String max_id);

}
