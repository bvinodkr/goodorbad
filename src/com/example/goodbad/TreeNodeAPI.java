package com.example.goodbad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeNodeAPI {

	public TreeNode getSibling (TreeNode node, int direction)
	{
		TreeNode ret = null;
		if (node.getParent () == null)
		{
			return ret;
		}
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
		path.add(node);
		//find leftmost child
		TreeNode leftMost = null;
		if (node.getChildren() != null)
		{
			leftMost = node.getChildren().get(0);
		}
		while (leftMost != null)
		{
			path.add(leftMost);
			if (leftMost.getChildren() != null && leftMost.getChildren().size() > 0)
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
		parent.addChild(child);		
		return child;
	}


	public TreeNode toTree (List<TreeNode> nodes)
	{
		TreeNode root = null;
		HashMap <String, TreeNode> adjTreeMap = new HashMap<String, TreeNode> ();
		for (int i = 0; i < nodes.size(); i++)
		{
			adjTreeMap.put(nodes.get(i).getObjectId(),nodes.get(i));
//			Log.d ("DEBUG", "story id: " + nodes.get(i).getStoryId() + " object id = " + nodes.get(i).getObjectId());
		}

		for (int i = 0; i < nodes.size(); i++)
		{
			TreeNode child = nodes.get(i);

			String paId = child.getParentId();
			if (paId != null)
			{
				TreeNode paTree = adjTreeMap.get(paId);
				child.setParent(paTree);
//				Log.d ("DEBUG", "add child " + child.getObjectId() + "  to parent:" + paTree.getObjectId());
				paTree.addChild(child);
//				Log.d("DEBUG", "num of children for parent = " + paTree.getChildren().size());
			}
			else
			{
//				Log.d ("DEBUG", "found root: " + child.getObjectId() + " for storyid=" + child.getStoryId());
				root = child;
			}
		}

		return root;
	}
	

	

}


