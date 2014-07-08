package com.example.goodbad;

import java.util.ArrayList;

public class TreeNodeAPI {

public TreeNode getSibling (TreeNode node, int direction)
{
	TreeNode ret = null;
	ArrayList<TreeNode> children = node.getParentNode().getChildren();
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
	return node.getParentNode().getChildren().size() - 1;
}

public ArrayList<TreeNode> getPathContaining (TreeNode node)
{
	ArrayList<TreeNode> path = new ArrayList <TreeNode> ();
	//find path from node to root
	TreeNode root = node.getParentNode();
	while (root != null)
	{
		path.add (0, root);
		root = root.getParentNode();
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

public void addChild (TreeNode parent, String text)
{
	TreeNode child = new TreeNode ();
	child.setLeaf(true);
	child.setText(text);
	child.setRootId(parent.getRootId());
	child.setNumLikes(0);
	child.setTotalLikes(parent.getTotalLikes());
	parent.setLeaf(false);
	parent.getChildren().add(child);
}



}
