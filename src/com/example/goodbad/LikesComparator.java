package com.example.goodbad;

import java.util.Comparator;

public class LikesComparator implements Comparator<TreeNode> {

	@Override
	public int compare(TreeNode lhs, TreeNode rhs) {
		if (lhs.getStoryLikes() == rhs.getStoryLikes())
		{
			return 0;
		}
		else if (lhs.getStoryLikes() > rhs.getStoryLikes())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

}
