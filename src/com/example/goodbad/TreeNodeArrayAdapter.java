package com.example.goodbad;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TreeNodeArrayAdapter extends ArrayAdapter<TreeNode> {

	public TreeNodeArrayAdapter(Context context, List<TreeNode> treeNodes) {
		super(context, R.layout.treenode_item, treeNodes);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TreeNode node = getItem (position);
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			convertView = inflator.inflate(R.layout.treenode_item, parent, false);
		} 
	
		/*RelativeLayout rlTreeNodeItem = (RelativeLayout) convertView.findViewById(R.id.rlTreeNodeItem);	
		RelativeLayout rlTreeNodeItemBody = (RelativeLayout) convertView.findViewById(R.id.rlBody);	
		if(position==1) {

			rlTreeNodeItem.getLayoutParams().height = 250;
			rlTreeNodeItemBody.getLayoutParams().height = 185;
			
			RelativeLayout.LayoutParams lpImageView = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 190);
			
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(R.drawable.sample_0);
			imageView.setId(2);
			imageView.setPadding(0, 2, 0, 5);
			lpImageView.addRule(RelativeLayout.BELOW, convertView.findViewById(R.id.tvBody).getId());
			imageView.setLayoutParams(lpImageView);
			rlTreeNodeItemBody.addView(imageView);
		}*/
		
		ImageView ivItemImage = (ImageView) convertView.findViewById(R.id.ivItemImage);
		if(position==1) { 			
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.yosemite);
		} else if (position==5) { 			
				ivItemImage.setVisibility(View.VISIBLE);
				ivItemImage.setImageResource(R.drawable.football);			
		} else {
			ivItemImage.setVisibility(View.GONE);
		}
				
		 
		//ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);		
		final ImageView ivEmptyHeart = (ImageView) convertView.findViewById(R.id.ivEmptyHeart);
		ivEmptyHeart.setContentDescription("false");
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		ivEmptyHeart.setOnClickListener(new OnClickListener() {
			
			@Override 
			public void onClick(View v) {
				int likes = Integer.parseInt(tvLikes.getText().toString());
				boolean isLiked = Boolean.parseBoolean(ivEmptyHeart.getContentDescription().toString());
				if(isLiked) {
					if(likes > 0) { 
						likes--;
						ivEmptyHeart.setContentDescription("false");
						ivEmptyHeart.setImageResource(R.drawable.ic_action_empty_heart);
						tvLikes.setText(likes+"");
					}
				} else {
					likes++;
					ivEmptyHeart.setContentDescription("true");				
					ivEmptyHeart.setImageResource(R.drawable.ic_action_filled_heart);
					tvLikes.setText(likes+"");
				}
			}
		}); 
		
		tvUserName.setText("Vinod");		
		//ivProfileImage.setImageResource(android.R.color.transparent);
		//tvUserName.setText("Aaroosh");
		tvBody.setText (node.getText());

		if (position%2 == 0)
		{
			convertView.setBackgroundColor(convertView.getResources().getColor (android.R.color.holo_orange_light));
		}
		convertView.setTag(node.getStoryId());
//		tvRelativeTime.setText("5 m");
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent (getContext(), StoryLineViewActivity.class);
				i.putExtra("storyId", (String)v.getTag());
				v.getContext().startActivity(i);
			}
		});
		

		
		tvBody.setVisibility(0);
		
		Log.d("debug", node.getText()); 
		/*if (position%2 == 0) {
			convertView.setBackgroundColor(0xFF99CCFF);
		} */
		
		return convertView;

	}

	
}
