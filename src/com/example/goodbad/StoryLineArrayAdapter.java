package com.example.goodbad;

import java.util.List;

import com.example.goodbad.fragments.InlineComposeDialogFragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class StoryLineArrayAdapter extends ArrayAdapter<TreeNode> {
	
	private int mScreenNo;
	private FragmentManager mFragmentManager;

	public StoryLineArrayAdapter(Context context, List<TreeNode> treeNodes, int screenNo, FragmentManager fragmentManager) {
		super(context, R.layout.treenode_item, treeNodes);
		this.mScreenNo = screenNo;
		this.mFragmentManager = fragmentManager;
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
		
		if(mScreenNo == 0) {
			convertView.setBackgroundResource(R.drawable.border_ui);
			convertView.findViewById(R.id.llFollowers).setBackgroundColor(Color.LTGRAY);
		} else if(mScreenNo == 1) {
			convertView.setBackgroundResource(R.drawable.round_edges);
			//convertView.findViewById(R.id.llFollowers).setBackgroundResource(R.drawable.footer);
		}
		
		ImageView ivItemImage = (ImageView) convertView.findViewById(R.id.ivItemImage);
		final VideoView vvItemVideo = (VideoView) convertView.findViewById(R.id.vvItemVideo);
		if(position==1) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.yosemite);
		} else if (position==3) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.football);			
		} /*else if (position == 3) { 
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.VISIBLE);
			vvItemVideo.setVideoPath("http://ia600204.us.archive.org/2/items/Pbtestfilemp4videotestmp4/video_test.mp4");
			MediaController mediaController = new MediaController(getContext());
			mediaController.setAnchorView(vvItemVideo);
			vvItemVideo.setMediaController(mediaController);
			vvItemVideo.requestFocus();
			vvItemVideo.setOnPreparedListener(new OnPreparedListener() {
			    // Close the progress bar and play the video
			    public void onPrepared(MediaPlayer mp) {
			    	vvItemVideo.start();
			    }
			});
		}*/ else {
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.GONE);
		}
				
		 
		//ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		final ImageView ivEmptyHeart = (ImageView) convertView.findViewById(R.id.ivEmptyHeart);
		ivEmptyHeart.setContentDescription("false");
		
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
		tvBody.setText (node.getText());
		
		ImageView ivForkStory = (ImageView) convertView.findViewById(R.id.ivForkStory);
		ivForkStory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InlineComposeDialogFragment inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance("InLineCompose");
				inlineComposeStoryFragment.show(mFragmentManager, "dialog_fragment");				
			}
		});
		
		
		Log.d("debug", node.getText()); 
		/*if (position%2 == 0) {
			convertView.setBackgroundColor(0xFF99CCFF);
		} */
		
		return convertView;

	}

	
}
