package com.example.goodbad;

import java.util.ArrayList;

//data model: table containing the list of stories
//storyid, treenodeid, treelevellikes, #of leaf nodes (number of endings), #comments, , #numberofcontributors
// we will need info button for story:
// provides info about when it got created, by whom, #of contributors, longest story line etc

//above information can be obtained from the tree db
// select nodes where parentid = null
//treelevellikes = sum of likes where storyid = X
//#leafnodes = sum of nodes where isLeafNode=true and storyid = X
//


//data model: 
//nodeid, parentid, storyid, text, isLeafNode, numberoflikes, userid, list of comments, 

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
