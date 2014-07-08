package com.example.goodbad;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

//data model: table containing the list of stories
//storyid, treenodeid, treelevellikes, #of leaf nodes (number of endings), #comments, , #numberofcontributors
//we will need info button for story:
//provides info about when it got created, by whom, #of contributors, longest story line etc

//above information can be obtained from the tree db
//select nodes where parentid = null
//treelevellikes = sum of likes where storyid = X
//#leafnodes = sum of nodes where isLeafNode=true and storyid = X
//unfortunately parse does not support sum operation.
//hence best way is to always update this entry every time the tree gets updated

//steps in creating story node
//1. create tree node 
//2. create story node with above tree node



@ParseClassName("StoryNode")
public class StoryNode extends ParseObject {

	public StoryNode ()
	{
		super ();
	}
	
	public ParseObject getTreeNode ()
	{
		return getParseObject("treenode");
	}
	
	public int getLikes ()
	{
		return getInt("likes");
	}
	
	public int getEndings ()
	{
		return getInt("endings");
	}
	
	public int getComments ()
	{
		return getInt("comments");
	}
	
	public int getContributors ()
	{
		return getInt("contributors");
	}
	
	public ParseUser getCreator ()
	{
		return getParseUser("creator");
	}
	
	public StoryNode (ParseUser creator)
	{
		put ("creator", creator);
	}
	
	public void setTreeNode (ParseObject treeNode)
	{
		put ("treenode", treeNode);
	}
}
