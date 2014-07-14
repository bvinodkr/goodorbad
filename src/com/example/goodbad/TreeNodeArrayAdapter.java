package com.example.goodbad;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TreeNodeArrayAdapter extends ArrayAdapter<TreeNode> {

	public TreeNodeArrayAdapter(Context context, List<TreeNode> objects) {
		super(context, 0, objects);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TreeNode node = getItem (position);
		View v;
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.treenode_item, parent, false);
		}
		else
		{
			v = convertView;
		}
	
		ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
//		ivProfileImage.setTag(tweet.getUser().getScreenName());
		/*
		ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View imageView) {
				
//				Intent i = new Intent (getContext(), ProfileActivity.class); 
//				i.putExtra("screenName", (String)imageView.getTag());
//				imageView.getContext().startActivity (i);
			}
		});
		*/
		TextView tvUserName = (TextView)v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView)v.findViewById(R.id.tvBody);
//		TextView tvRelativeTime = (TextView)v.findViewById(R.id.tvRelativeTime);
		ivProfileImage.setImageResource(android.R.color.transparent);
		tvUserName.setText("Aaroosh");
		tvBody.setText (node.getText());
		if (position%2 == 0)
		{
			v.setBackgroundColor(v.getResources().getColor (android.R.color.holo_orange_light));
		}
//		tvRelativeTime.setText("5 m");
		return v;
	}

	
}
