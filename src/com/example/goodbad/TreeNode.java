package com.example.goodbad;

import java.util.ArrayList;

//db schema: table containing the list of stories
//storyid, treelevellikes, #of leaf nodes (number of endings), #comments, treenodeid, #numberofcontributors
// we will need info button for story:
// provides info about when it got created, by whom, #of contributors, longest story line etc


//db schema: 
//nodeid, parentid, storyid, text, user, numberoflikes, list of comments

//get whole tree by doing select with storyid

//need utility function to convert from adjacency list to tree
// this function can also index the leaf nodes
//need function to find most popular story line: leaf node contains popularity count
// 1. find leaf node that contains most likes for the path
//2. fetch the path from leaf node to root



public class TreeNode {

	private TreeNode parentNode;
	private ArrayList<TreeNode> children;
	private TreeNode rootId; //story id
	private int totalLikes;
	private int numLikes;
	//candidate for making generic later
	private String text;

	public TreeNode getParentNode() {
		return parentNode;
	}
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public int getNumLikes() {
		return numLikes;
	}
	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}
	public TreeNode getRootId() {
		return rootId;
	}
	public void setRootId(TreeNode rootId) {
		this.rootId = rootId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
	
}
