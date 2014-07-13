package com.example.goodbad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class TreeNodeAPI {

	public TreeNode getSibling (TreeNode node, int direction)
	{
		TreeNode ret = null;
		ArrayList<TreeNode> children = ((TreeNode)node.getParent()).getChildren();
		int i = 0;
		if (direction > 0)
		{
			for (; i < children.size(); i++)
			{
				if (children.get(i) == node)
				{
					break;
				}
			}		
		}
		else
		{
			i = children.size() - 1;
			for (; i >=0; i--)
			{
				if (children.get(i) == node)
				{
					break;
				}
			}
		}

		//i+1 
		if ((i + 1) < children.size ())
		{
			ret = children.get(i+1);
		}
		return ret;
	}

	public int getSiblingCount (TreeNode node)
	{
		return ((TreeNode)node.getParent()).getChildren().size() - 1;
	}

	public ArrayList<TreeNode> getPathContaining (TreeNode node)
	{
		ArrayList<TreeNode> path = new ArrayList <TreeNode> ();
		//find path from node to root
		TreeNode root = (TreeNode)node.getParent();
		while (root != null)
		{
			path.add (0, root);
			root = (TreeNode)root.getParent();
		}
		//find leftmost child
		TreeNode leftMost = null;
		if (node.getChildren() != null)
		{
			leftMost = node.getChildren().get(0);
		}
		while (leftMost != null)
		{
			path.add(leftMost);
			if (leftMost.getChildren() != null)
			{
				leftMost = leftMost.getChildren().get(0);
			}
			else
			{
				leftMost = null;
			}
		}
		return path;
	}

	public TreeNode addChild (TreeNode parent, String text)
	{
		TreeNode child = new TreeNode (text, parent, parent.getStoryId());
//		child.setTotalLikes(parent.getTotalLikes());
		if (parent.isLeafNode())
		{
			parent.setLeafNode(false);
		}
		parent.getChildren().add(child);		
		return child;
	}

	/* convert from adjacent list form to tree 
	 * 1. create 2 hashmaps, one indexes adjacentnodes, other the treenodes
	 * for each adjacentreenode, assign node as child of parent using other hashmap
	 */
	public TreeNode toTree (List<TreeNode> nodes)
	{
		TreeNode root = null;
		HashMap <String, TreeNode> adjTreeMap = new HashMap<String, TreeNode> ();
		for (int i = 0; i < nodes.size(); i++)
		{
			adjTreeMap.put(nodes.get(i).getObjectId(),nodes.get(i));
		}

		for (int i = 0; i < nodes.size(); i++)
		{
			ParseObject parentObj = nodes.get(i).getParent();
			TreeNode paTree;
			if (parentObj == JSONObject.NULL)
			{
				paTree = null;
			}
			else
			{
				String parent = parentObj.getObjectId();
				paTree = adjTreeMap.get(parent);
			}
			String id = nodes.get(i).getObjectId();

			TreeNode child = adjTreeMap.get(id);
			if (paTree != null)
			{
				paTree.getChildren().add(child);
			}
			else
			{
				root = child;
			}
		}

		return root;
	}
	
	public ArrayList<TreeNode> getLeafNodes (List<TreeNode> nodes)
	{
		ArrayList<TreeNode> ret = new ArrayList<TreeNode> ();
		
		
		return ret;
	}
	
	public void getStoriesFromDb (FindCallback<TreeNode> callback)
	{
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parent", JSONObject.NULL);
		query.findInBackground(callback);
	}
	


}


