package com.example.goodbad;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

//data model: 
//nodeid, parentid, storyid, text, isLeafNode, numberoflikes, userid, list of comments, 

//access control: which user can see it. use unix permissions user, group, global
//initial version everything is global. can make it private in the future

//get whole tree by doing select with storyid
@ParseClassName("AdjacentTreeNode")
public class AdjacentTreeNode extends ParseObject {

	public AdjacentTreeNode ()
	{
		super ();
	}
	
	 public ParseObject getParent ()
	 {
		 return getParseObject("parent");
	 }
	 
	 public int getLikes ()
	 {
		 return getInt("likes");
	 }
	 
	 public ParseObject getStoryId ()
	 {
		 return getParseObject("storyid");
	 }
	 
	 public String getText ()
	 {
		 return getString("text");
	 }
	 
	 public boolean isLeafNode ()
	 {
		 return getBoolean("isLeafNode");
	 }
	 
	 public ParseUser getUser ()
	 {
		 return getParseUser("user");
	 }
	 
	 public AdjacentTreeNode (String text, ParseObject parent, ParseObject storyId)
	 {
		 put ("text", text);
		 put ("parent", parent);
		 put ("storyid", storyId);
		 put ("isLeafNode", true);
	 }
}
