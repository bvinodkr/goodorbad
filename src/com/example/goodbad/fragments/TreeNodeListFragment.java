package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeArrayAdapter;

public abstract class TreeNodeListFragment extends Fragment {
	private ArrayList<TreeNode> nodes;
	private ArrayAdapter<TreeNode> aNodes;
	//private PullToRefreshListView lvTweets;
	private ListView lvNodes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nodes_list, container, false);
		
		lvNodes = (ListView)v.findViewById(R.id.lvNodes);
		lvNodes.setAdapter(aNodes);

		return v;
	}
	
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			nodes = new ArrayList<TreeNode> ();
			aNodes = new TreeNodeArrayAdapter(getActivity(), nodes);
	}
    public abstract void populateTreeNodes (String max_id);
    
	public void addAll (List<TreeNode> nodes)
	{
		aNodes.addAll(nodes);
	}
}
