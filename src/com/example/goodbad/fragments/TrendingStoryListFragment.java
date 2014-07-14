package com.example.goodbad.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.goodbad.TreeNode;

public class TrendingStoryListFragment extends BaseListFragment {
	
	ArrayList<TreeNode> nodesList = new ArrayList<TreeNode> ();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		addNodestoList();
		addNodestoAdapter(nodesList);
		
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
		nodesList.add(n1);
		
		TreeNode n2 = new TreeNode ();
		n2.setText("This is story 2");
		nodesList.add(n2);
		
		TreeNode n3 = new TreeNode ();
		n3.setText("This is story 3");
		nodesList.add(n3);
		
		TreeNode n4 = new TreeNode ();
		n4.setText("This is story 4");
		nodesList.add(n4);
		
		TreeNode n5 = new TreeNode ();
		n5.setText("This is story 5");
		nodesList.add(n5);
		
		TreeNode n6 = new TreeNode ();
		n6.setText("This is story 6");
		nodesList.add(n6);
		
		TreeNode n7 = new TreeNode ();
		n7.setText("This is story 7");
		nodesList.add(n7);
		
		TreeNode n8 = new TreeNode ();
		n8.setText("This is story 8");
		nodesList.add(n8);
		
		TreeNode n9 = new TreeNode ();
		n9.setText("This is story 9");
		nodesList.add(n9);
		
		TreeNode n10 = new TreeNode ();
		n10.setText("This is story 10");
		nodesList.add(n10);
	}
	
}
