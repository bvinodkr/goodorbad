package com.example.goodbad.fragments;

import java.util.ArrayList;

import android.os.Bundle;

import com.example.goodbad.TreeNode;

public class HomeListFragment extends TreeNodeListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ArrayList<TreeNode> a = new ArrayList<TreeNode> ();
		TreeNode n = new TreeNode ();
		n.setText("a");
		a.add(n);
		
		TreeNode m = new TreeNode ();
		m.setText("b");
		a.add(m);
		
		addAll(a);
	}
}
