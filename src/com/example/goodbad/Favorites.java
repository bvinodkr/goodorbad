package com.example.goodbad;

import java.util.ArrayList;

import org.json.JSONObject;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

//data model: table containing the list of stories
//storyid, treenodeid, treelevellikes, #of leaf nodes (number of endings), #comments, , #numberofcontributors
// we will need info button for story:
// provides info about when it got created, by whom, #of contributors, longest story line etc

//above information can be obtained from the tree db
// select nodes where parentid = null
//treelevellikes = sum of likes where storyid = X
//#leafnodes = sum of nodes where isLeafNode=true and storyid = X
// unfortunately parse does not support sum operation.
//hence best way is to always update this entry every time the tree gets updated


//data model: 
//nodeid, parentid, storyid, text, isLeafNode, numberoflikes, userid, list of comments, 

//access control: which user can see it. use unix permissions user, group, global
//initial version everything is global. can make it private in the future

//get whole tree by doing select with storyid

//need utility function to convert from adjacency list to tree
// this function can also index the leaf nodes
//need function to find most popular story line: leaf node contains popularity count
// 1. find leaf node that contains most likes for the path
//2. fetch the path from leaf node to root


@ParseClassName("Favorites")
public class Favorites extends ParseObject {

	private ArrayList<Favorites> children;
	private Favorites parent;
	
	//tree level stats, only stored in root node
//	private int numTreeLeafNodes;
//	private int numTreeComments;
//	private int numTreeContributors;
	
	//leaf level stats, only stored in leaf node

	 public String getParentId ()
	 {
		 return getString("parentid");
	 }
	 
	 public void setParentId (Favorites parent)
	 {
		 if (parent == null)
		 {
			put ("parentid", JSONObject.NULL); 
		 }
		 else
		 {
			 put ("parentid", parent.getObjectId());
		 }
	 }
	 
	 public Favorites getParent ()
	 {
		 return parent;
	 }
	 
	 public void setParent (Favorites p)
	 {
		 parent = p;
	 }
	 
	 public int getLikes ()
	 {
		 return getInt("likes");
	 }
	 
	 public void setLikes (int likes)
	 {
		 put ("likes", likes);
	 }
	 
	 public String getStoryId ()
	 {
		 return getString ("storyid");
	 }
	 
	 public void setStoryId (String storyid)
	 {
		 put ("storyid", storyid);
	 }
	 
	 public String getText ()
	 {
		 return      getString("text");
	 }
	 
	 public String getImageUrl ()
	 {
		 return getString ("imageurl");
	 }
	 
	 public void setImageUrl (String url)
	 {
		 put ("imageurl", url);
	 }
	 
	 public void setText (String text)
	 {
		 put ("text", text);
	 }
	 
	 public boolean isLeafNode ()
	 {
		 return getBoolean("isLeafNode");
	 }
	 
	 public void setLeafNode (boolean set)
	 {
		 put ("isLeafNode", set);
	 }
	 
	 public void setTitle (String title)
	 {
		 put ("title", title);
	 }
	 
	 public String getTitle ()
	 {
		 return  getParseObject("FavNodeId").getString ("title");
	 }
	 
	public int getNumComments() {
		return getInt ("numComments");
	}
	public void setNumComments(int numComments) {
		put ("numComments",  numComments);
	}
	public int getNumTreeLeafNodes() {
		return getInt ("numTreeLeafNodes");
	}
	public void setNumTreeLeafNodes(int numTreeLeafNodes) {
		put ("numTreeLeafNodes", numTreeLeafNodes);
	}
	
	public int getNumTreeComments() {
		return getInt("numTreeComments");
	}
	public void setNumTreeComments(int numTreeComments) {
		put ("numTreeComments", numTreeComments);
	}
	public int getNumTreeContributors() {
		return getInt ("numTreeContributors");
	}
	public void setNumTreeContributors(int numTreeContributors) {
		put ("numTreeContributors",numTreeContributors);
	}
	public int getStoryLikes() {
		return getInt ("storyLikes");
	}
	public void setStoryLikes(int storyLikes) {
		put ("storyLikes", storyLikes);
	}
	
	public void setUser (ParseUser user)
	{
		put ("user", user);
	}
	
	public ParseUser getUser ()
	{
		//Object obj=get("FavUserId");
		//return  getString("obj.user")  ; 
		return getParseUser("FavUserId");
		// return getParseObject("");
		//return getParseUser(getParseObject (""));
	}
	
	public void setFavStory (ParseObject favstory)
	{
		put ("FavNodeId", favstory);
	}
	
	public ParseObject getFavStory ()
	{
		ParseObject obj=  getParseObject("FavNodeId");
		//return  getString("obj.user")  ; 
		return  getParseObject("FavNodeId");
		// return getParseObject("");
		//return getParseUser(getParseObject (""));
	}
	
	public ArrayList<Favorites> getChildren ()
	{
		return children;
	}
	
	public void addChild (Favorites child)
	{
		children.add(child);
	}
	
	public Favorites ()
	{
		super ();
		children = new ArrayList<Favorites> ();
	}
	
	public Favorites (String text, Favorites parent)
	{
		setText(text);
		setParentId(parent);
		setLeafNode(true);
		setUser (ParseUser.getCurrentUser());
		if (parent == null)
		{
			setNumTreeLeafNodes(1);
		}
		children = new ArrayList<Favorites> ();
	}
	
	public Favorites (String text, Favorites parent, String rootid)
	{
		setText(text);
		setParentId(parent);
		setLeafNode(true);
		setStoryId(rootid);
		setUser (ParseUser.getCurrentUser());
		children = new ArrayList<Favorites> ();
	}
	
}